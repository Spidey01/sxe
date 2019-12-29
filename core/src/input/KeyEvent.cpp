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

#include "sxe/core/input/KeyEvent.hpp"

using std::string;
using std::to_string;

namespace sxe { namespace core { namespace input {


KeyEvent::KeyEvent(InputManager* source, InputCode keyCode, const string& keyName, bool isDown)
    : mSource(source)
    , mKeyCode(keyCode)
    , mKeyName(keyName)
    , mIsDown(isDown)
{
}


InputManager* KeyEvent::getSource() const
{
    return mSource;
}


InputCode KeyEvent::getKeyCode() const
{
    return mKeyCode;
}


string KeyEvent::getKeyName() const
{
    return mKeyName;
}


bool KeyEvent::isKeyDown() const
{
    return mIsDown;
}


bool KeyEvent::isKeyUp() const
{
    return !mIsDown;
}


string KeyEvent::toString() const
{
    return "KeyEvent Input=" + to_string((uintptr_t)mSource)
        + " Code=" + to_string(mKeyCode.code())
        + " Name=" + mKeyName
        + " Down=" + string(mIsDown ? "true" : "false");
}

} } }

