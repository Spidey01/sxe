#ifndef SXE_PC_PCDISPLAY__HPP
#define SXE_PC_PCDISPLAY__HPP
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
#include <sxe/GameEngine.hpp>
#include <sxe/graphics/Display.hpp>

struct GLFWwindow;

namespace sxe { namespace pc {

    class SXE_PUBLIC PcDisplay : public sxe::graphics::Display
    {
      public:

        /** Create the display based on the desired parameters.
         *
         * @param desired A string like that used with setMode().
         */
        PcDisplay(const string_type& desired);

        /** Create the Display based on the desktops current DisplayMode.
         *
         * This should generally get you a full screen Display instance that
         * matches the users desktop environment. E.g. 1080p@60hz.
         */
        PcDisplay();

        virtual ~PcDisplay();

        bool initialize(sxe::GameEngine& engine) override;
        bool uninitialize() override;
        void update() override;

        bool create() override;
        void destroy() override;
        bool isCloseRequested() const override;
        bool setMode(sxe::graphics::DisplayMode mode) override;

        /** Returns access to the window.
         *
         * This pointer will be valid from when create() returns to when
         * destroy() is called. Do not call glfwDestroyWindow() on it, or
         * otherwise abuse this.
         */
        GLFWwindow* getWindow() const;

      protected:

        string_type getError() const;

      private:

        static const string_type TAG;

        GLFWwindow* mWindow;

        static void error_callback(int code, const char* description);
    };

} }

#endif // SXE_PC_PCDISPLAY__HPP
