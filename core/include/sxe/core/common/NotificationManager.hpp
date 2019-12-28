#ifndef SXE_CORE_COMMON_NOTIFICATIONMANAGER__HPP
#define SXE_CORE_COMMON_NOTIFICATIONMANAGER__HPP
/*-
 * Copyright (c) 2014-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the
 * use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 *	1. The origin of this software must not be misrepresented; you must
 *	   not claim that you wrote the original software. If you use this
 *	   software in a product, an acknowledgment in the product
 *	   documentation would be appreciated but is not required.
 *
 *	2. Altered source versions must be plainly marked as such, and must
 *	   not be misrepresented as being the original software.
 *
 *	3. This notice may not be removed or altered from any source
 *	   distribution.
 */

#include <sxe/api.hpp>
#include <sxe/core/logging/Log.hpp>

namespace sxe { namespace core { namespace common {

    /** A simple notification manager.
     *
     * NotificationManager is based on mapping a message to a callable to
     * receive notification.
     *
     * Notifications can be subscribed to as an "Any key" general broadcast, or
     * to a "Specofic key" receiver. Separate receiver lists are maintained for
     * these.
     *
     * Receiver_T should be an callable in the sense of operator(Message_T).
     * std::function<> and std::mem_fn<> are recommended. If using a custom
     * functor type, consider implementing a operator string() for debug purposes.
     *
     * Message_Ts are assumed to be mappable to keys for dispatch purposes.
     * Such that toKey(msg) -> subscriber key -> call receiver.
     *
     * If Message_T is convetable to string, toKey() uses string keys to
     * dispatch messages. User defined types can implement <samp>operator
     * string(Message_T)</samp> to handle that.
     *
     * Otherwise Message_T will be converted using std::to_string() over the
     * numerical Hash of the message dispatch. Specializations of std::hash<>
     * can be provided or the Hash parameter set accordingly.
     *
     * If all else fails, extend NotificationManager and override toKey().
     *
     * The value -1 or SIZE_MAX can be used as an uninitialized/invalid id value.
     *
     * Historical:
     *   - SxE 1.x used a dedicated listener object / event object interface.
     *   - SxE 2.x uses modern C++ functors, and makes old compilers cry.
     *
     * @see #invoke(java.lang.Object, java.lang.Object)
     */
    template <class Receiver_T, class Message_T, class Hash=std::hash<Message_T>>
    class NotificationManager
    {
      public:

        using mutex_type = std::recursive_mutex;
        using lock_type = std::lock_guard<mutex_type>;
        using list_type = std::list<Receiver_T>;
        using value_type = typename list_type::value_type;
        using size_type = typename list_type::size_type;
        using string_type = std::string;
        using map_type = std::map<string_type, list_type>;

        /** Add a receiver to the broadcast list.
         *
         * @param rx the listener to receive the broadcast.
         * @returns id for unsubscribe().
         */
        size_type subscribe(Receiver_T rx)
        {
            lock_type guard(mMutex);

            sxe::core::logging::Log::log(mLevel, mTag, "subscribe(): rx: " + asString(rx));

            mNotificationReceivers.push_back(rx);

            size_type id = mNotificationReceivers.size() - 1;
            if (id == SIZE_MAX)
                throw std::runtime_error("subscribe(): mNotificationReceivers.push_back(rx) failed.");
            return id;
        }


        /** Add a receiver to the specific event list.
         *
         * @param rx the listener to receive the event.
         * @param key subscription target.
         * @returns SIZE_MAX.
         * @see asString().
         */
        size_type subscribe(Receiver_T rx, const string_type& key)
        {
            lock_type guard(mMutex);

            sxe::core::logging::Log::log(mLevel, mTag, "subscribe(): rx: " + asString(rx) + " key: " + key);

            list_type& list = mSubscribers[toKey(key)];
            list.push_back(rx);

            size_type id = list.size() - 1;
            if (id == SIZE_MAX)
                throw std::runtime_error("subscribe(): mSubscribers[key].push_back(rx) failed.");

            return id;
        }

        /** Remove receiver from broadcast notifications list.
         */
        void unsubscribe(size_type id)
        {
            sxe::core::logging::Log::log(mLevel, mTag, "unsubscribe(): id: " + std::to_string(id));

            list_type& list = mNotificationReceivers;

            auto it = std::next(list.begin(), id);
            if (it == list.end())
                return;

            list.erase(it);
        }


        /** Remove receiver from specific bnotification list.
         * @param key the key associated.
         * @param id the id associated.
         */
        void unsubscribe(const string_type& key, size_type id)
        {
            sxe::core::logging::Log::log(mLevel, mTag,
                                         "unsubscribe(): key: " + key + " id: " + std::to_string(id));

            auto pair = mSubscribers.find(toKey(key));
            if (pair == mSubscribers.end())
                return;

            list_type& list = pair->second;

            auto it = std::next(list.begin(), id);
            if (it == list.end())
                return;

            list.erase(it);
        }


        /** Send a message event to subscribers.
         *
         * @param message dispatched to broadcast subscribers, and subscribers to to asString(message).
         */
        void notifyListeners(Message_T message)
        {
            sxe::core::logging::Log::log(mLevel, mTag, "notifyListeners(): " + toKey(message));

            lock_type guard(mMutex);

            sxe::core::logging::Log::log(mLevel, mTag, "notifyListeners(): all messages subscribers: " + toKey(message));
            for (auto rx : notificationReceivers()) {
                invoke(rx, message);
            }

            sxe::core::logging::Log::log(mLevel, mTag, "notifyListeners(): specific message subscribers: " + toKey(message));
            auto it = subscribers().find(toKey(message));
            if (it == subscribers().cend()) {
                return;
            }
            list_type& bindings = it->second;

            for (auto rx : bindings) {
                invoke(rx, message);
            }
        }


        NotificationManager()
            : mTag("NotificationManager")
            , mLevel(sxe::core::logging::Log::TEST)
            , mNotificationReceivers()
            , mSubscribers()
        {
            sxe::core::logging::Log::log(mLevel, mTag, "NotificationManager<>()");
        }


        virtual ~NotificationManager()
        {
            sxe::core::logging::Log::log(mLevel, mTag, "~NotificationManager<>()");
        }

      protected:

        /** Tag used for logging.
         */
        string_type mTag;

        /** Level used for logging.
         */
        int mLevel;

        /** For protecting notificationReceivers() and subscribers().
         *
         * If you don't hold this mutex: someone can pull the rug out from
         * under you.
         *
         * Just do: 'lock_type guard(mMutex);'
         */
        mutex_type mMutex;

        list_type& notificationReceivers() { return mNotificationReceivers; }
        const list_type& notificationReceivers() const { return mNotificationReceivers; }

        map_type& subscribers() { return mSubscribers; }
        const map_type& subscribers() const { return mSubscribers; }

        /** Adapter method for custom messages.
         *
         * Whenever a Receiver_T is invoked for a Message_T, this virtual
         * method is used. Default behavior is to log and invoke it as a
         * callable. If you need something fancier: override this.
         *
         * invoke() will only be called while the mutex is held.
         */
        virtual void invoke(Receiver_T rx, Message_T message)
        {
            sxe::core::logging::Log::log(mLevel, mTag, "invoke(): rx: " + asString(rx) + " message: " + toKey(message));
            rx(message);
        }

        /** Adapter for logging receiver.
         *
         * By default we assume this is an opaque value type, like
         * std::function. It'll be logged using std::to_string() and
         * std::hash().
         *
         * When extending NotificationManager: you can override this to provide
         * better logging if your Receiver_t has a method for that. Good ideas
         * are implementing your own string conversion or a std::hash<>
         * specialization for your type.
         */
        virtual string_type asString(const Receiver_T& rx) const
        {
            (void)rx;
            if constexpr(std::is_convertible<Receiver_T, std::string>::value)
                return rx;

            return "";
        }

        /** Adapter for Message_T => string Key mappings.
         *
         * If Message_T is convertable to a string, such as a user defiend type
         * with operator string(): keys will be that. This provides the best
         * result.
         *
         * Otherwise Hash and std::to_string() will be used. By default Hash is
         * std::hash<>, meaning Message_T's that you have specialized will just
         * work. Numeric keys are effective but less friendly.
         *
         * Alternatively: override toKey() and provide your own voodoo for
         * mapping the keys to the Message_T.
         */
        virtual string_type toKey(const Message_T& msg) const
        {
            if constexpr(std::is_convertible<Message_T, std::string>::value) {
                string_type k = msg;
                sxe::core::logging::Log::log(mLevel, mTag, "toKey(msg): operator string(): " + k);
                return k;
            } else {
                Hash h;
                size_t k = h(msg);
                sxe::core::logging::Log::log(mLevel, mTag, "toKey(msg): Hash{}(): " + std::to_string(k));
                return std::to_string(k);
            }
        }


      private:

        /** List of receivers for broadcast notification.
         */
        list_type mNotificationReceivers;

        /** Map of receivers for specific notifications.
         */
        map_type mSubscribers;

    };

} } }

#endif // SXE_CORE_COMMON_NOTIFICATIONMANAGER__HPP
