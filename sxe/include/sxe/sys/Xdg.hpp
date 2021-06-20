#ifndef SXE_SYS_XDG__HPP
#define SXE_SYS_XDG__HPP
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
#include <sxe/sys/FileSystem.hpp>

namespace sxe { namespace sys {

    class SXE_PUBLIC Xdg
    {
      public:

        using string = sxe::sys::FileSystem::string;
        using path = sxe::sys::FileSystem::path;
        using list = std::vector<path>;

        Xdg();

        /** Application installation prefix.
         *
         * This environment variable should be set by the wrapper scripts installed
         * with the application.  If you use the Gradle application plugin like the
         * demos do, this is taken care of for you.
         */
        path APP_HOME;

        /** What passes for the user's home directory.
         *
         * @see sxe::sys::FileSystem.
         */
        path HOME;

        /** Alias for HOME. */
        path USER_DIR;

        /** Base directory for user-specific data files. */
        path XDG_DATA_HOME;

        /** Preference-ordered set of base directories to search for data files. 
         *
         * This is the system search path version of XDG_DATA_HOME.
         */
        list XDG_DATA_DIRS;

        /** Base directory for user specific configuration files. */
        path XDG_CONFIG_HOME;

        /** Preference-ordered set of base directories to search for configuration files.
         *
         * This is the system search path version of XDG_CONFIG_HOME.
         */
        list XDG_CONFIG_DIRS;

        /** Base directory for user specific non-essential data files. */
        path XDG_CACHE_HOME;

        /** Base directory for user-specific non-essential runtime files and other file objects.
         *
         * There is no default value. So this likely will be null.
         */
        path XDG_RUNTIME_DIR;


        path getDataHomeDir(const string& relative) const;
        path getDataHomeDir(const path& relative) const;

        path getConfigHomeDir(const string& relative) const;
        path getConfigHomeDir(const path& relative) const;

        path getCacheDir(const string& relative) const;
        path getCacheDir(const path& relative) const;

        path getDataDir(const string& relative) const;
        path getDataDir(const path& relative) const;

        path getConfigDir(const string& relative) const;
        path getConfigDir(const path& relative) const;

    };

} }

#endif // SXE_SYS_XDG__HPP
