#ifndef SXE_CORE_TESTING_NULLDISPLAY__HPP
#define SXE_CORE_TESTING_NULLDISPLAY__HPP
/*-
 * Copyright (c) 2013-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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
#include <sxe/core/graphics/Display.hpp>
#include <sxe/core/graphics/DisplayMode.hpp>

namespace sxe { namespace core { namespace testing {

    class SXE_PUBLIC NullDisplay : public sxe::core::graphics::Display
    {
      public:

        /** Creates a NULL Display.
         *
         * @param GL implementation of OpenGL to utilize.
         * @param answer whether to succeed or fail operations.
         */
        NullDisplay(/*OpenGL GL,*/ bool answer);

        bool create() override;
        void destroy() override;
        bool isCloseRequested() const override;
        bool setMode(sxe::core::graphics::DisplayMode mode) override;

      protected:

      private:
        static const string_type TAG;
        const bool mDefaultAnswer;
    };

} } }

#endif // SXE_CORE_TESTING_NULLDISPLAY__HPP
