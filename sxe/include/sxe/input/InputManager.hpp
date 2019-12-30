#ifndef SXE_INPUT_INPUTMANAGER__HPP
#define SXE_INPUT_INPUTMANAGER__HPP
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
#include <sxe/common/Subsystem.hpp>
#include <sxe/input/InputCode.hpp>
#include <sxe/input/KeyEventManager.hpp>

namespace sxe { namespace input {

    class KeyEvent;

    /** Interface for managing game input.
     *
     * Allows registering listeners for key events, etc. The input manager will
     * only pass on input to the listeners when the appropriate notify method is
     * executed.
     *
     * Platform specific implementations of this class will provide the backend
     * for obtaining input that can then be passed on to an appropriate notify
     * method.  The reccomended way to implement this is through poll(), which
     * is the how GameEngine.mainLoop() expects it.  You should just subclass
     * this on most platforms and implement poll().
     *
     * @see sxe::testing::NullInputManager.
     */
    class SXE_PUBLIC InputManager : public common::Subsystem
    {
      public:

        /** Create InputManager.
         *
         * @param name passed to Subsystem ctor.
         */
        InputManager(const string_type& name);

        virtual ~InputManager() = default;

        bool uninitialize() override;

        /** Calls poll().
         */
        void update() override;

        /** Poll for new input and notify listeners.
         *
         */
        virtual void poll();

        /** Inject key events.
         *
         * Note that this injects the event to key listeners, not the host
         * operating system or hardware environment.
         */
        void inject(KeyEvent event);


        /** Inject key event by InputCode.
         *
         * @param key the InputCode of the event.
         * @param isDown whether this is a key down or up event.
         */
        void inject(InputCode key, bool isDown);

        /** Inject KeyEvents for each character in String.
         *
         * Each key is a key up and key down event. If the key is A-Z a shift key
         * event will occur before and after. The event is terminated be an enter
         * key up and down event.
         *
         * @param keyName key name of the event.
         * @param isDown whether this is a key down or up event.
         */
        void inject(const string_type& line);


        /** Add a KeyListener to recieve all KeyEvent.
         *
         * This will register a KeyListener that will be called whenever a key
         * event occurs. <strike>Note that in the default implementation of
         * NotifyKeyListeners, the first KeyListener to return true from onKey
         * consumes the event and it will <strong>not</strong> be passed on to
         * subsequent KeyListeners.</strike>
         *
         * @returns id for removeKeyListener() for broadcast listeners.
         *
         * @see KeyEvent, NotificationManager
         */
        KeyEventManager::Id addKeyListener(KeyListener listener);


        /** Add a KeyListener for a specified key.
         *
         * This provides a way to register a key listener for a specific key. A key
         * may have many listeners. They will be called in the order the were
         * added<strike>, until a KeyListener returns true</strike>.
         *
         * You can use this to implement keybinds for game play.
         *
         * @param inputCode what key to listen for.
         * @param listener the KeyListener for keyName.
         *
         * @returns id for removeKeyListener() for this specific InputCode.
         *
         * @see KeyEvent, NotificationManager
         */
        KeyEventManager::Id addKeyListener(InputCode inputCode, KeyListener listener);

        void removeKeyListener(KeyEventManager::Id id);
        void removeKeyListener(InputCode inputCode, KeyEventManager::Id id);

      protected:

        KeyEventManager mKeyEventManager;
        
      private:
        static const string_type TAG;
    };

} }

#endif // SXE_INPUT_INPUTMANAGER__HPP
