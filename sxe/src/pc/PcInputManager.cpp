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

#include "sxe/pc/PcInputManager.hpp"

#include <GLFW/glfw3.h>
#include <sxe/input/KeyEvent.hpp>
#include <sxe/logging.hpp>
#include <sxe/pc/PcDisplayManager.hpp>

using namespace sxe::input;

static sxe::pc::PcInputManager* singleton = nullptr;

/** Maps GLFW's macros for key code to InputCode::IC_* equivalents.
 */
InputCode toSxE(int key)
{
    switch (key)
    {
        case GLFW_KEY_SPACE: return InputCode(InputCode::IC_SPACE);
        case GLFW_KEY_APOSTROPHE: return InputCode(InputCode::IC_APOSTROPHE);
        case GLFW_KEY_COMMA: return InputCode(InputCode::IC_COMMA);
        case GLFW_KEY_MINUS: return InputCode(InputCode::IC_HYPHEN_MINUS);
        case GLFW_KEY_PERIOD: return InputCode(InputCode::IC_PERIOD);
        case GLFW_KEY_SLASH: return InputCode(InputCode::IC_SLASH);
        case GLFW_KEY_0: return InputCode(InputCode::IC_0);
        case GLFW_KEY_1: return InputCode(InputCode::IC_1);
        case GLFW_KEY_2: return InputCode(InputCode::IC_2);
        case GLFW_KEY_3: return InputCode(InputCode::IC_3);
        case GLFW_KEY_4: return InputCode(InputCode::IC_4);
        case GLFW_KEY_5: return InputCode(InputCode::IC_5);
        case GLFW_KEY_6: return InputCode(InputCode::IC_6);
        case GLFW_KEY_7: return InputCode(InputCode::IC_7);
        case GLFW_KEY_8: return InputCode(InputCode::IC_8);
        case GLFW_KEY_9: return InputCode(InputCode::IC_9);
        case GLFW_KEY_SEMICOLON: return InputCode(InputCode::IC_SEMICOLON);
        case GLFW_KEY_EQUAL: return InputCode(InputCode::IC_EQUAL_SIGN);
        case GLFW_KEY_A: return InputCode(InputCode::IC_A);
        case GLFW_KEY_B: return InputCode(InputCode::IC_B);
        case GLFW_KEY_C: return InputCode(InputCode::IC_C);
        case GLFW_KEY_D: return InputCode(InputCode::IC_D);
        case GLFW_KEY_E: return InputCode(InputCode::IC_E);
        case GLFW_KEY_F: return InputCode(InputCode::IC_F);
        case GLFW_KEY_G: return InputCode(InputCode::IC_G);
        case GLFW_KEY_H: return InputCode(InputCode::IC_H);
        case GLFW_KEY_I: return InputCode(InputCode::IC_I);
        case GLFW_KEY_J: return InputCode(InputCode::IC_J);
        case GLFW_KEY_K: return InputCode(InputCode::IC_K);
        case GLFW_KEY_L: return InputCode(InputCode::IC_L);
        case GLFW_KEY_M: return InputCode(InputCode::IC_M);
        case GLFW_KEY_N: return InputCode(InputCode::IC_N);
        case GLFW_KEY_O: return InputCode(InputCode::IC_O);
        case GLFW_KEY_P: return InputCode(InputCode::IC_P);
        case GLFW_KEY_Q: return InputCode(InputCode::IC_Q);
        case GLFW_KEY_R: return InputCode(InputCode::IC_R);
        case GLFW_KEY_S: return InputCode(InputCode::IC_S);
        case GLFW_KEY_T: return InputCode(InputCode::IC_T);
        case GLFW_KEY_U: return InputCode(InputCode::IC_U);
        case GLFW_KEY_V: return InputCode(InputCode::IC_V);
        case GLFW_KEY_W: return InputCode(InputCode::IC_W);
        case GLFW_KEY_X: return InputCode(InputCode::IC_X);
        case GLFW_KEY_Y: return InputCode(InputCode::IC_Y);
        case GLFW_KEY_Z: return InputCode(InputCode::IC_Z);
        case GLFW_KEY_LEFT_BRACKET: return InputCode(InputCode::IC_LEFT_SQUARE_BRACKET);
        case GLFW_KEY_BACKSLASH: return InputCode(InputCode::IC_BACKSLASH);
        case GLFW_KEY_RIGHT_BRACKET: return InputCode(InputCode::IC_RIGHT_SQUARE_BRACKET);
        case GLFW_KEY_GRAVE_ACCENT: return InputCode(InputCode::IC_GRAVE);
        // case GLFW_KEY_WORLD_1: return InputCode(InputCode::IC_WORLD_1);
        // case GLFW_KEY_WORLD_2: return InputCode(InputCode::IC_WORLD_2);
        case GLFW_KEY_ESCAPE: return InputCode(InputCode::IC_ESCAPE);
        case GLFW_KEY_ENTER: return InputCode(InputCode::IC_ENTER);
        case GLFW_KEY_TAB: return InputCode(InputCode::IC_TAB);
        case GLFW_KEY_BACKSPACE: return InputCode(InputCode::IC_BACKSPACE);
        case GLFW_KEY_INSERT: return InputCode(InputCode::IC_INSERT);
        case GLFW_KEY_DELETE: return InputCode(InputCode::IC_DELETE);
        case GLFW_KEY_RIGHT: return InputCode(InputCode::IC_RIGHT_ARROW);
        case GLFW_KEY_LEFT: return InputCode(InputCode::IC_LEFT_ARROW);
        case GLFW_KEY_DOWN: return InputCode(InputCode::IC_DOWN_ARROW);
        case GLFW_KEY_UP: return InputCode(InputCode::IC_UP_ARROW);
        case GLFW_KEY_PAGE_UP: return InputCode(InputCode::IC_PAGE_UP);
        case GLFW_KEY_PAGE_DOWN: return InputCode(InputCode::IC_PAGE_DOWN);
        case GLFW_KEY_HOME: return InputCode(InputCode::IC_HOME);
        case GLFW_KEY_END: return InputCode(InputCode::IC_END);
        case GLFW_KEY_CAPS_LOCK: return InputCode(InputCode::IC_CAPS_LOCK);
        case GLFW_KEY_SCROLL_LOCK: return InputCode(InputCode::IC_SCROLL_LOCK);
        case GLFW_KEY_NUM_LOCK: return InputCode(InputCode::IC_NUM_LOCK);
        case GLFW_KEY_PRINT_SCREEN: return InputCode(InputCode::IC_PRINTSCREEN);
        case GLFW_KEY_PAUSE: return InputCode(InputCode::IC_PAUSE);
        case GLFW_KEY_F1: return InputCode(InputCode::IC_F1);
        case GLFW_KEY_F2: return InputCode(InputCode::IC_F2);
        case GLFW_KEY_F3: return InputCode(InputCode::IC_F3);
        case GLFW_KEY_F4: return InputCode(InputCode::IC_F4);
        case GLFW_KEY_F5: return InputCode(InputCode::IC_F5);
        case GLFW_KEY_F6: return InputCode(InputCode::IC_F6);
        case GLFW_KEY_F7: return InputCode(InputCode::IC_F7);
        case GLFW_KEY_F8: return InputCode(InputCode::IC_F8);
        case GLFW_KEY_F9: return InputCode(InputCode::IC_F9);
        case GLFW_KEY_F10: return InputCode(InputCode::IC_F10);
        case GLFW_KEY_F11: return InputCode(InputCode::IC_F11);
        case GLFW_KEY_F12: return InputCode(InputCode::IC_F12);
        // case GLFW_KEY_F13: return InputCode(InputCode::IC_F13);
        // case GLFW_KEY_F14: return InputCode(InputCode::IC_F14);
        // case GLFW_KEY_F15: return InputCode(InputCode::IC_F15);
        // case GLFW_KEY_F16: return InputCode(InputCode::IC_F16);
        // case GLFW_KEY_F17: return InputCode(InputCode::IC_F17);
        // case GLFW_KEY_F18: return InputCode(InputCode::IC_F18);
        // case GLFW_KEY_F19: return InputCode(InputCode::IC_F19);
        // case GLFW_KEY_F20: return InputCode(InputCode::IC_F20);
        // case GLFW_KEY_F21: return InputCode(InputCode::IC_F21);
        // case GLFW_KEY_F22: return InputCode(InputCode::IC_F22);
        // case GLFW_KEY_F23: return InputCode(InputCode::IC_F23);
        // case GLFW_KEY_F24: return InputCode(InputCode::IC_F24);
        // case GLFW_KEY_F25: return InputCode(InputCode::IC_F25);
        case GLFW_KEY_KP_0: return InputCode(InputCode::IC_NUMPAD_0);
        case GLFW_KEY_KP_1: return InputCode(InputCode::IC_NUMPAD_1);
        case GLFW_KEY_KP_2: return InputCode(InputCode::IC_NUMPAD_2);
        case GLFW_KEY_KP_3: return InputCode(InputCode::IC_NUMPAD_3);
        case GLFW_KEY_KP_4: return InputCode(InputCode::IC_NUMPAD_4);
        case GLFW_KEY_KP_5: return InputCode(InputCode::IC_NUMPAD_5);
        case GLFW_KEY_KP_6: return InputCode(InputCode::IC_NUMPAD_6);
        case GLFW_KEY_KP_7: return InputCode(InputCode::IC_NUMPAD_7);
        case GLFW_KEY_KP_8: return InputCode(InputCode::IC_NUMPAD_8);
        case GLFW_KEY_KP_9: return InputCode(InputCode::IC_NUMPAD_9);
        // case GLFW_KEY_KP_DECIMAL: return InputCode(InputCode::IC_NUMPAD_DECIMAL);
        case GLFW_KEY_KP_DIVIDE: return InputCode(InputCode::IC_NUMPAD_DIVIDE);
        case GLFW_KEY_KP_MULTIPLY: return InputCode(InputCode::IC_NUMPAD_MULTIPLY);
        case GLFW_KEY_KP_SUBTRACT: return InputCode(InputCode::IC_NUMPAD_SUBTRACT);
        case GLFW_KEY_KP_ADD: return InputCode(InputCode::IC_NUMPAD_ADD);
        case GLFW_KEY_KP_ENTER: return InputCode(InputCode::IC_NUMPAD_ENTER);
        case GLFW_KEY_KP_EQUAL: return InputCode(InputCode::IC_NUMPAD_EQUAL_SIGN);
        case GLFW_KEY_LEFT_SHIFT: return InputCode(InputCode::IC_SHIFT_LEFT);
        case GLFW_KEY_LEFT_CONTROL: return InputCode(InputCode::IC_CTRL_LEFT);
        case GLFW_KEY_LEFT_ALT: return InputCode(InputCode::IC_ALT_LEFT);
        // case GLFW_KEY_LEFT_SUPER: return InputCode(InputCode::IC_SUPER_LEFT);
        case GLFW_KEY_RIGHT_SHIFT: return InputCode(InputCode::IC_SHIFT_RIGHT);
        case GLFW_KEY_RIGHT_CONTROL: return InputCode(InputCode::IC_CTRL_RIGHT);
        case GLFW_KEY_RIGHT_ALT: return InputCode(InputCode::IC_ALT_RIGHT);
        // case GLFW_KEY_RIGHT_SUPER: return InputCode(InputCode::IC_SUPER_RIGHT);
        case GLFW_KEY_MENU: return InputCode(InputCode::IC_MENU);

        case GLFW_KEY_UNKNOWN:
        default:
            return InputCode(InputCode::IC_UNKNOWN);
    }

}

namespace sxe { namespace pc {

const PcInputManager::string_type PcInputManager::TAG = "PcInputManager";

PcInputManager::PcInputManager()
    : InputManager(TAG)
    , mCallbackSet(false)
{
    if (singleton != nullptr)
        throw std::logic_error("There can only be one PcInputManager!");
    singleton = this;
}


PcInputManager::~PcInputManager()
{
}


bool PcInputManager::uninitialize()
{
    if (!InputManager::uninitialize())
        return false;

    /*
     * Ugly assumptions:
     *  - Our pal, PcDisplayManager clears the callback for us.
     *  - That GLFW3 probably doesn't care if cleared before terminate.
     *  - That you won't [Pc]DisplayManager::destroy() outside our lifecycle.
     *    + I.e. let GameEngine do create/destroy.
     */

    mCallbackSet = false;

    return true;
}


void PcInputManager::poll()
{
    if (!mCallbackSet) {
        PcDisplayManager& display = dynamic_cast<PcDisplayManager&>(getGameEngine().getDisplayManager());
        GLFWwindow* window = display.getWindow();
        if (window == nullptr) {
            Log::w(TAG, "poll() called before PcDisplayManager::create()");
            return;
        }

        Log::xtrace(TAG, "poll(): glfwSetKeyCallback()");
        glfwSetKeyCallback(window, &PcInputManager::key_callback);

        mCallbackSet = true;
    }
}


void PcInputManager::key_callback(GLFWwindow* window, int key, int scancode, int action, int mods)
{
    if (!singleton) {
        Log::w(TAG, "key_callback() called with an empty singleton pointer!");
        return;
    }

    using std::to_string;

    Log::test(singleton->TAG, "key_callback(): window: " + to_string((uintptr_t)window)
              + " key: " + to_string(key) + " scancode: " + to_string(scancode)
              + " action: " + to_string(action) + " mods: " + to_string(mods));

    string_type name;
    const char* friendly = glfwGetKeyName(key, scancode);
    if (friendly != nullptr)
        name.append(friendly);

    bool isDown = action == GLFW_RELEASE ? true : false;

    singleton->inject(KeyEvent(singleton, toSxE(key), friendly, isDown));
    // singleton->inject(toSxE(key), isDown);
}



} }

