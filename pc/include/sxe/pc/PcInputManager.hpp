#ifndef SXE_PC_PCINPUTMANAGER__HPP
#define SXE_PC_PCINPUTMANAGER__HPP
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
#include <sxe/core/input/InputManager.hpp>

struct GLFWwindow;

namespace sxe { namespace pc {

    class SXE_PUBLIC PcInputManager : public sxe::core::input::InputManager
    {
      public:

        /** Creates an InputManager for PC.
         */
        PcInputManager();
        virtual ~PcInputManager();

        bool uninitialize() override;

        void poll() override;

        /** Callback for GLFW key events.
         *
         * @param window The window that received the event.
         * @param key The keyboard key that was pressed or released.
         * @param scancode The system-specific scancode of the key.
         * @param action GLFW_PRESS, GLFW_RELEASE or GLFW_REPEAT.
         * @param mods Bit field describing which modifier keys were held down
         */
        static void key_callback(GLFWwindow* window, int key, int scancode, int action, int mods);

      private:
        static const string_type TAG;

        bool mCallbackSet;
    };

} }

#endif // SXE_PC_PCINPUTMANAGER__HPP
