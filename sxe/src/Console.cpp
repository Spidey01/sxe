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

#include "sxe/Console.hpp"

#include <sxe/common/Utils.hpp>
#include <sxe/input/InputManager.hpp>
#include <sxe/input/KeyListener.hpp>
#include <sxe/logging.hpp>

using std::to_string;
using sxe::cmds::Command;
using sxe::input::InputCode;
using sxe::input::InputManager;
using sxe::input::KeyEvent;

namespace sxe {

const int Console::DEFAULT_REPEAT_DELAY = 2;

const InputCode Console::DEFAULT_TOGGLE_KEY = InputCode::IC_GRAVE;

const Console::SymbolsList Console::VALID_SYMBOLS = {
    InputCode::IC_TILDE,
    InputCode::IC_EXCLAMATION_MARK,
    InputCode::IC_AT_SIGN,
    InputCode::IC_NUMBER_SIGN,
    InputCode::IC_DOLLAR_SIGN,
    InputCode::IC_PERCENT_SIGN,
    InputCode::IC_CARET,
    InputCode::IC_AMPERSAND,
    InputCode::IC_ASTERISK, InputCode::IC_NUMPAD_MULTIPLY,
    InputCode::IC_LEFT_PAREN, InputCode::IC_NUMPAD_LEFT_PAREN,
    InputCode::IC_RIGHT_PAREN, InputCode::IC_NUMPAD_RIGHT_PAREN,
    InputCode::IC_UNDERSCORE,
    InputCode::IC_PLUS_SIGN, InputCode::IC_NUMPAD_ADD,
    InputCode::IC_HYPHEN_MINUS, InputCode::IC_NUMPAD_SUBTRACT,
    InputCode::IC_EQUAL_SIGN, InputCode::IC_NUMPAD_EQUAL_SIGN,
    InputCode::IC_LEFT_CURLY_BRACE,
    InputCode::IC_RIGHT_CURLY_BRACE,
    InputCode::IC_LEFT_SQUARE_BRACKET,
    InputCode::IC_RIGHT_SQUARE_BRACKET,
    InputCode::IC_COLON,
    InputCode::IC_SEMICOLON,
    InputCode::IC_DOUBLE_QUOTE,
    InputCode::IC_APOSTROPHE,
    InputCode::IC_GRAVE,
    InputCode::IC_LESS_THAN_SIGN,
    InputCode::IC_GREATER_THAN_SIGN,
    InputCode::IC_PERIOD,
    InputCode::IC_QUESTION_MARK,
    InputCode::IC_BACKSLASH,
    InputCode::IC_SLASH, InputCode::IC_NUMPAD_DIVIDE,
    InputCode::IC_SPACE,
    InputCode::IC_0, InputCode::IC_NUMPAD_0,
    InputCode::IC_1, InputCode::IC_NUMPAD_1,
    InputCode::IC_2, InputCode::IC_NUMPAD_2,
    InputCode::IC_3, InputCode::IC_NUMPAD_3,
    InputCode::IC_4, InputCode::IC_NUMPAD_4,
    InputCode::IC_5, InputCode::IC_NUMPAD_5,
    InputCode::IC_6, InputCode::IC_NUMPAD_6,
    InputCode::IC_7, InputCode::IC_NUMPAD_7,
    InputCode::IC_8, InputCode::IC_NUMPAD_8,
    InputCode::IC_9, InputCode::IC_NUMPAD_9,
    InputCode::IC_A,
    InputCode::IC_B,
    InputCode::IC_C,
    InputCode::IC_D,
    InputCode::IC_E,
    InputCode::IC_F,
    InputCode::IC_G,
    InputCode::IC_H,
    InputCode::IC_I,
    InputCode::IC_J,
    InputCode::IC_K,
    InputCode::IC_L,
    InputCode::IC_M,
    InputCode::IC_N,
    InputCode::IC_O,
    InputCode::IC_P,
    InputCode::IC_Q,
    InputCode::IC_R,
    InputCode::IC_S,
    InputCode::IC_T,
    InputCode::IC_U,
    InputCode::IC_V,
    InputCode::IC_W,
    InputCode::IC_X,
    InputCode::IC_Y,
    InputCode::IC_Z,
};

const Console::string_type Console::TAG = "Console";

Console::Console()
    : Console(InputCode::IC_GRAVE)
{
}


Console::Console(input::InputCode toggleKey)
    : mToggleKey(toggleKey)
    , mInputManager(nullptr)
    , mKeyListenerId(SIZE_MAX)
    , mLastKeyEvent()
    , mHandleRepeat(true)
    , mRepeatDelay(2/*TODO*/)
    , mRepeatCount(0/*TODO*/)
    , mIsShiftDown(false)
    , mVisible(false)
    , mBuffer()
    , mCommands()
{
}


Console::~Console()
{
    setInputManager(nullptr);
}


void Console::setInputManager(InputManager* inputManager)
{
    Log::xtrace(TAG, "setInputManager(): inputManager: " + to_string((uintptr_t)inputManager));

    lock_guard g(mMutex);

    mInputManager = inputManager;
    
    if (inputManager) {
        input::KeyListener callback = std::bind(&Console::onKey, this, std::placeholders::_1);
        mKeyListenerId = mInputManager->addKeyListener(callback);

        Log::v(TAG, "setInputManager(): mKeyListenerId = " + to_string(mKeyListenerId));
    } else if (mKeyListenerId != SIZE_MAX)  {
        Log::v(TAG, "setInputManager(): clearing mKeyListenerId = " + to_string(mKeyListenerId));

        mInputManager->removeKeyListener(mKeyListenerId);

        mKeyListenerId = SIZE_MAX;
    }
}


bool Console::isVisible() const
{
    return mVisible;
}


void Console::setVisible(bool visible)
{
    Log::v(TAG, "setVisible(): " + string_type(visible ? "opened" : "closed"));
    mVisible = visible;
}


void Console::setToggleKey(InputCode toggleKey)
{
    lock_guard g(mMutex);
    mToggleKey = toggleKey;
}


InputCode Console::getToggleKey() const
{
    lock_guard g(mMutex);
    auto k = mToggleKey;
    return k;
}


void Console::allowRepeating(bool repeating)
{
    Log::xtrace(TAG, "mHandleRepeat = " + string_type(repeating ? "true" : "false"));
    mHandleRepeat = repeating;
}


bool Console::repeatingAllowed() const
{
    return mHandleRepeat;
}


size_t Console::getRepeatDelay() const
{
    return mRepeatDelay;
}


void Console::setRepeatDelay(size_t count)
{
    mRepeatDelay = count;
}


bool Console::onKey(KeyEvent event)
{
    /*
     * House keeping for toggling the console.
     *
     * Must be first so it will pre-empty included it in the buffer.
     */
    if (event.isKeyUp()) {
        if (event == mToggleKey) {
            setVisible(!isVisible());
            return true;
        }
    }
    if (!isVisible()) {
        return false;
    }

    // stuff that only fires if the key was released
    if (event.isKeyUp()) {
        Log::test(TAG, "onKey(): handle key released");

        if (event.getKeyCode() == InputCode::IC_SHIFT_LEFT ||
            event.getKeyCode() == InputCode::IC_SHIFT_RIGHT)
        {
            mIsShiftDown = false;
        }

        if (event.getKeyCode() == InputCode::IC_ENTER ||
            event.getKeyCode() == InputCode::IC_NUMPAD_ENTER)
        {
            execute(mBuffer.str());
            Log::test(TAG, "Clearing buffer.");
            mBuffer.str("");
            return true;
        }

        if (mHandleRepeat) {
            // mLastKeyEvent = ...
            mRepeatCount = 0;
        }
    }

    if (event.isKeyDown()) {
        Log::test(TAG, "onKey(): handle key pressed");

        if (event.getKeyCode() == InputCode::IC_SHIFT_LEFT ||
            event.getKeyCode() == InputCode::IC_SHIFT_RIGHT)
        {
            mIsShiftDown = true;
        }
    }

    if (!mHandleRepeat && event.isKeyDown()) {
        // consume but don't repeat
        return true;
    } else if (mHandleRepeat) {
        // house keeping for repeat
        // mLastKeyEvent = event;

        if (event.isKeyDown()) {

            if (mRepeatCount < getRepeatDelay()) {
                // not long enough to trigger a repeat.
                mRepeatCount++;
                return true;
            } else {
                // clear repeat count so we repeat at smoother rate.
                mRepeatCount = 0;
            }
        }
    }

    InputCode thisCode = event.getKeyCode();
    for (InputCode code : VALID_SYMBOLS) {
        if (code == thisCode) {
            char c = mIsShiftDown ? code.upper() : code.lower();

            std::stringstream debug;
            debug << "Append '" << c << "' to " << std::quoted(mBuffer.str());
            Log::xtrace(TAG, debug.str());

            mBuffer << c;
            break;
        }
    }

    // consume even if not added to buffer, because the console is OPEN.
    return true;
}


void Console::execute(const string_type& line)
{
    Log::xtrace(TAG, "execute(): line: \"" + line + "\"");

    using sxe::common::Utils::trim;

    auto split = line.find(" ");
    string_type name = trim(line.substr(0, split));
    string_type args;
    if (split != string_type::npos)
        args = trim(line.substr(split));
    Command::argv argv;
    // Don't have Utils::tokenize() yet.
    argv.push_back(args);

    lock_guard g(mMutex);

    auto it = mCommands.find(name);
    if (it != mCommands.end()) {
        auto ptr = it->second;
        if (ptr) {
            (*ptr)(argv);
            return;
        }
    }

    Log::i(TAG, "No such console command: " + name);
}


void Console::addCommand(Command::shared_ptr cmd)
{
    if (!cmd)
        return;

    Log::v(TAG, "Added command " + cmd->getName());
    lock_guard g(mMutex);
    mCommands[cmd->getName()] = cmd;
}


void Console::removeCommand(const string_type& name)
{
    lock_guard g(mMutex);
    Log::v(TAG, "Removed command " + name);
    mCommands.erase(name);
}

}

