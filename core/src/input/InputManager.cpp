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

#include "sxe/core/input/InputManager.hpp"

#include <sxe/core/input/InputCode.hpp>
#include <sxe/core/input/KeyEvent.hpp>
#include <sxe/logging.hpp>

namespace sxe { namespace core { namespace input {

const InputManager::string_type InputManager::TAG = "InputManager";

InputManager::InputManager(const string_type& name)
    : Subsystem(name)
    , mKeyEventManager()
{
}


bool InputManager::uninitialize()
{
    mKeyEventManager.clear();
    return Subsystem::uninitialize();
}


void InputManager::update()
{
    poll();
}


void InputManager::poll()
{
    Log::test(TAG, "poll()");
}


void InputManager::inject(KeyEvent event)
{
    Log::test(TAG, "inject(): event: " + event.toString());

    mKeyEventManager.notifyListeners(event);
}


void InputManager::inject(InputCode key, bool isDown)
{
    Log::test(TAG, "inject(): key: " + std::to_string(key.code()) + " isDown: " + string_type(isDown ? "true" : "false"));
    inject(KeyEvent(this, key, "", isDown));
}


void InputManager::inject(const string_type& line)
{
    Log::test(TAG, "inject(): line: " + line);

    /*
     * 1.x split line into whitespace delimited words, and injected the
     *   resulting chars with one IC_SPACE injected between.
     *
     * 2.x takes a literal char in, event out approach.
     */

    for (char ch : line) {
        InputCode code = InputCode::fromChar(ch);
        bool isShiftDown = code.upper() == ch;

        std::ostringstream debug;

        debug << "inject():"
            << " isShiftDown => " << isShiftDown
            << " code.upper() => " << code.upper()
            << " code.lower() => " << code.lower()
            << " char => " << ch
            ;

        Log::test(TAG, debug.str());

        if (isShiftDown) {
            inject(InputCode::IC_SHIFT_LEFT, true);
        }
        inject(code, true);
        inject(code, false);
        if (isShiftDown) {
            inject(InputCode::IC_SHIFT_LEFT, false);
        }
    }
}


KeyEventManager::Id InputManager::addKeyListener(KeyListener listener)
{
    return mKeyEventManager.addKeyListener(listener);
}


KeyEventManager::Id InputManager::addKeyListener(InputCode inputCode, KeyListener listener)
{
    return mKeyEventManager.addKeyListener(inputCode, listener);
}


void InputManager::removeKeyListener(KeyEventManager::Id id)
{
    mKeyEventManager.removeKeyListener(id);
}


void InputManager::removeKeyListener(InputCode inputCode, KeyEventManager::Id id)
{
    mKeyEventManager.removeKeyListener(inputCode, id);
}


} } }

