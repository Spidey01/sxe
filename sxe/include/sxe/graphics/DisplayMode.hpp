#ifndef SXE_GRAPHICS_DISPLAYMODE__HPP
#define SXE_GRAPHICS_DISPLAYMODE__HPP
/*-
 * Copyright (c) 2019-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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
#include <sxe/stdheaders.hpp>

namespace sxe {  namespace graphics {

    /** Structure defining modes for Display.
     */
    class SXE_PUBLIC DisplayMode
    {
      public:

        /** Define a simple display mode.
         */
        DisplayMode(int width, int height, bool fs);

        /** Define a complete display mode.
         *
         * @param width in pixels.
         * @param height in pixels.
         * @param bpp be kind.
         * @param refresh in hz.
         * @param fs true = fullscreen.
         */
        DisplayMode(int width, int height, int bpp, int refresh, bool fs);

        /** Define a display mode from a string.
         *
         * @param mode A string in the format "width x height x bpp @refresh". E.g.
         * "640 x 480 x 16 @60". Omitted parts will be undefined.
         * @param fs true = fullscreen.
         */
        DisplayMode(const std::string& mode, bool fs);

        int width() const;
        int height() const;
        int bpp() const;
        int refresh() const;
        bool fullscreen() const;

        /** Converts to a string mode suitable for the ctor.
         */
        operator std::string() const;

      private:
        int mWidth;
        int mHeight;
        int mBpp;
        int mRefresh;
        bool mFullscreen;
    };

} }

#endif // SXE_GRAPHICS_DISPLAYMODE__HPP
