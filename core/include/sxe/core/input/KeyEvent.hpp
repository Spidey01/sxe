#ifndef SXE_CORE_INPUT_KEYEVENT__HPP
#define SXE_CORE_INPUT_KEYEVENT__HPP
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
#include <sxe/core/input/InputCode.hpp>

namespace sxe { namespace core { namespace input {

    class InputManager;

    /** Value class representing a KeyEvent.
     *
     * Notes:
     *   - 1.x considered events from different sources equal.
     *   - 2.x thinks you shouldn't have to care.
     */
    class SXE_PUBLIC KeyEvent
    {
      public:

        KeyEvent(InputManager* source, InputCode keyCode, const std::string& keyName, bool isDown);


        InputManager* getSource() const;

        InputCode getKeyCode() const;

        std::string getKeyName() const;

        bool isKeyDown() const;

        bool isKeyUp() const;

        std::string toString() const;

      protected:

      private:

        InputManager* mSource;
        const InputCode mKeyCode;
        const std::string mKeyName;
        const bool mIsDown;
    };

} } }

#endif // SXE_CORE_INPUT_KEYEVENT__HPP
