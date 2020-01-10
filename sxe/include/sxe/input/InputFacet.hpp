#ifndef SXE_INPUT_INPUTFACET__HPP
#define SXE_INPUT_INPUTFACET__HPP
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
#include <sxe/input/InputCode.hpp>
#include <sxe/input/InputManager.hpp>
#include <sxe/input/KeyListener.hpp>

namespace sxe { namespace input {

    /** Facet enabling binding keys to.
     *
     * Rather than writing your own input handling code around InputManager,
     * you can utilize this facet to manage input. Composition ftw?
     */
    class SXE_PUBLIC InputFacet
    {
      public:
        using IdList = std::deque<KeyEventManager::Id>;
        using IdMap = std::map<int, IdList>;

        /** Create without any bound keys.
         *
         * @param input the InputManager.
         */
        InputFacet(InputManager& inputManager);

        /** Create with one key/action binding.
         *
         * @param input the InputManager.
         * @param code the code to bind to.
         * @param action the listener for the event.
         */
        InputFacet(InputManager& inputManager, InputCode code, KeyListener action);

        /** Create with multiple keys bound to action.
         *
         * @param input the InputManager.
         * @param first first InputCode to bind to.
         * @param last as per STL.
         * @param action the listener for the event.
         */
        template <class InputIt>
        InputFacet(InputManager& inputManager, InputIt first, InputIt last, KeyListener action)
            : InputFacet(inputManager)
        {
            addKeyListeners(first, last, action);
        }

        ~InputFacet();

        /** Binds a KeyListener to a specific key code.
         *
         * @param code the InputCode to listen for.
         * @param action callback to take action on code.
         * @param returns true on success.
         */
        bool addKeyListener(InputCode code, KeyListener action);

        /** Unbinds KeyListeners for this code.
         *
         * @param code the InputCode to unbind.
         */
        void removeKeyListener(InputCode code);

        /** Binds a KeyListener to several codes.
         *
         * @param first first InputCode to bind to.
         * @param last as per STL.
         * @param action callback to take action on these codes.
         * @param returns false if any bindings failed.
         */
        template <class InputIt>
        bool addKeyListeners(InputIt first, InputIt last, KeyListener action)
        {
            lock_guard g(mMutex);

            while (first != last) {
                if (!addKeyListener(*first, action))
                    return false;
                first++;
            }

            return true;
        }

        /** Unbinds KeyListeners from several codes.
         *
         * @param first first InputCode to unbind.
         * @param last as per STL.
         */
        template <class InputIt>
        void removeKeyListeners(InputIt first, InputIt last)
        {
            lock_guard g(mMutex);

            for (; first != last; ++first) {
                removeKeyListener(*first);
            }
        }


      private:

        static const std::string TAG;

        InputManager& mInputManager;
        IdMap mIdMap;

        using lock_guard = std::lock_guard<std::recursive_mutex>;
        mutable lock_guard::mutex_type mMutex;
    };

} }

#endif // SXE_INPUT_INPUTFACET__HPP
