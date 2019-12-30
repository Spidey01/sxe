#ifndef SXE_SYS_PLATFORM__HPP
#define SXE_SYS_PLATFORM__HPP
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

namespace sxe { namespace sys {

    /** A bundle of platform information.
     *
     */
    class SXE_PUBLIC Platform 
    {
      public:

        static const std::string ANDROID;
        static const std::string LINUX;
        static const std::string MAC_OS;
        static const std::string WINDOWS;

        /** Guess the current platform name. */
        Platform();

        /** Initialize Platform.
         *
         * @param name The name of the platform. This should match a constant.
         */
        Platform(const std::string& name);

        /** Name of the platform. */
        std::string name() const;

        /** Architecture of the platform. */
        std::string arch() const;

        /** Operating System Version of the platform. */
        std::string version() const;

        bool isAndroid() const;
        bool isMacOS() const;
        bool isUnix() const;
        bool isWindows() const;

        std::string toString() const;

        static std::string guess();

      protected:

      private:

        static const std::string TAG;
        std::string mName;
    };

} }

#endif // SXE_SYS_PLATFORM__HPP
