#ifndef SXE_CONSOLE__HPP
#define SXE_CONSOLE__HPP
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
#include <sxe/cmds/Command.hpp>
#include <sxe/input/InputCode.hpp>
#include <sxe/input/KeyEvent.hpp>
#include <sxe/input/KeyEventManager.hpp>
#include <sxe/stdheaders.hpp>

namespace sxe {

    namespace input {
        class InputManager;
    }

    class SXE_PUBLIC Console
    {
      public:

        using string_type = std::string;

        static const int DEFAULT_REPEAT_DELAY;

        /** Default InputCode for togglign console.
         *
         * Used by the default ctor. The default is the '`' or IC_GRAVE key.
         */
        static const input::InputCode DEFAULT_TOGGLE_KEY;

        using SymbolsList = std::vector<input::InputCode>;

        /** Sequence of valid characters that can be used in a console command.
         *
         */
        static const SymbolsList VALID_SYMBOLS;

        Console();

        Console(input::InputCode toggleKey);

        ~Console();

        /** Set the input manager to use.
         *
         * This will be automatically cleared in our dtor.
         *
         * @param inputManager nullptr to clear, actual to set.
         */
        void setInputManager(input::InputManager* inputManager);

        bool isVisible() const;

        void setVisible(bool visable);

        void setToggleKey(input::InputCode toggleKey);

        input::InputCode getToggleKey() const;

        /** Pass to InputManager::addKeyListener().
         */
        // input::KeyListener keyListener() const;

        /** Whether or not holding down a key repeats the input character */
        void allowRepeating(bool repeating);

        bool repeatingAllowed() const;

        size_t getRepeatDelay() const;

        void setRepeatDelay(size_t count);

        bool onKey(input::KeyEvent event);

        void execute(const string_type& line);


        void addCommand(cmds::Command::shared_ptr cmd);
        void removeCommand(const string_type& name);


      private:

        using lock_guard = std::lock_guard<std::recursive_mutex>;
        using mutex_type = lock_guard::mutex_type;

        mutable mutex_type mMutex;

        static const string_type TAG;

        /** InputCode used to toggle the console. */
        input::InputCode mToggleKey;

        input::InputManager* mInputManager;

        input::KeyEventManager::Id mKeyListenerId;

        input::KeyEvent mLastKeyEvent;

        bool mHandleRepeat;
        size_t mRepeatDelay;
        size_t mRepeatCount;

        /** For tracking whether the shift key is currently down. */
        bool mIsShiftDown;

        bool mVisible;

        std::stringstream mBuffer;

        using map_type = std::map<string_type, cmds::Command::shared_ptr>;

        map_type mCommands;

    };
}

#endif // SXE_CONSOLE__HPP
