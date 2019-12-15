#ifndef SXE_CORE_CONFIG_SETTINGS__HPP
#define SXE_CORE_CONFIG_SETTINGS__HPP
/*-
 * Copyright (c) 2012-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

namespace sxe { namespace core { namespace config {

    /** Access to persistent settings.
     *
     * Settings are presented as a simple key/value store. The actual storage
     * method is platform specific: it could be anything from a property file, a
     * database, or even a hashtable in ram provided by a game console.
     *
     * Values are bound to a key, where the key is a string. To create an artifical
     * relationship between values, use a form of hierarchal naming. SxE uses the
     * period for this purpose, e.g. quux.foo, quux.bar. This mapping method makes
     * things simple to process in code and minimizes requirements on the storage
     * engine.
     *
     * @see SettingsFile.
     * @see SettingsMap.
     */
    class SXE_PUBLIC Settings {
      public:

        using string_type = std::string;

        Settings() = default;
        virtual ~Settings() = default;

        #if 0 // 1.x
        /** Interface for subscribing to settings changes.
         *
         * Use this if you must be kept up to date with the current value of a key.
         */
        public interface OnChangedListener extends EventListener {
            /** Handle change notification.
             *
             * You are provided an instance of the Settings so that you can query
             * its value using the provided methods.  It is not recommended to
             * modify the Settings instance unless you must update related settings
             * for e.g. consistency.
             *
             * No guarantee of thread safety is required for implementations of
             * this interface.
             *
             * @param key - the key associated with the change.
             */
            void onChanged(String key);
        }

        void addChangeListener(OnChangedListener listener);
        void addChangeListener(String key, OnChangedListener listener);
        void removeChangeListener(OnChangedListener listener);
        // void removeChangeListener(String key, OnChangedListener listener);
        #endif

        using KeyList = std::vector<string_type>;

        /** Sequence of all keys that are set. */
        virtual KeyList keys() const = 0;

        /** Test if preference is set. */
        virtual bool contains(const string_type& key) const = 0;

        /** Get key as a bool value.
         *
         * @return the value of key or false if not set.
         */
        virtual bool getBool(const string_type& key) const;

        /** Get key as a float value.
         *
         * @return the value of key or 0 if not set.
         */
        float getFloat(const string_type& key) const;

        /** Get key as a int value.
         *
         * @return the value of key or 0 if not set.
         */
        int getInt(const string_type& key) const;

        /** Get key as a long value.
         *
         * @return the value of key or 0 if not set.
         */
        long getLong(const string_type& key) const;

        /** Get key as a const string_type& value.
         *
         * @return the value of key or "" if not set.
         */
        virtual string_type getString(const string_type& key) const = 0;

        /** Set key as a bool value.  */
        Settings& setBool(const string_type& key, bool value);

        /** Set key as a float value.  */
        Settings& setFloat(const string_type& key, float value);

        /** Set key as a int value.  */
        Settings& setInt(const string_type& key, int value);

        /** Set key as a long value.  */
        Settings& setLong(const string_type& key, long value);

        /** Set key as a const string_type& value.  */
        virtual Settings& setString(const string_type& key, const string_type& value) = 0;

        /** Clear loaded settings.
        */
        virtual void clear() = 0;

        /** Commit changes back to storage. */
        virtual bool save() = 0;

        /** Merge contents of other Settings. */
        void merge(const Settings& s);

      protected:

      private:
        static const string_type TAG;
    };

} } }

#endif // SXE_CORE_CONFIG_SETTINGS__HPP
