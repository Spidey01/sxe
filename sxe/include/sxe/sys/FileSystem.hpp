#ifndef SXE_SYS_FILESYSTEM__HPP
#define SXE_SYS_FILESYSTEM__HPP
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

#include <sxe/filesystem.hpp>
#include <sxe/api.hpp>

namespace sxe { namespace sys {

    namespace FileSystem
    {
        using string = std::string;

        /*
         * using statements for things in std::filesystem that came from
         * boost::filesystem, and that can be dropped in.
         */

        using path = sxe::filesystem::path;

        /** Get the users personal directory.
         *
         * This is assumed to be HOME or USERPROFILE, which ever is found first.
         */
        SXE_PUBLIC string getUserDir();

        /*
         * SxE 1.x had a getBitBucketFile() function that returned a handle
         * here. Which was written well before the VFS.
         */

        /** Return the path to the systems bit bucket. */
        SXE_PUBLIC path getBitBucketPath();

        using sxe::filesystem::exists;

        /** Search list of directories for a file.
         *
         * @param first first directory to search
         * @param last last directory to search.
         * @param name File or directory to find in dirs.
         *
         * @return Absolute path to name if found; else null.
         */
        template <class InputIt> path find(InputIt first, InputIt last, const string& name)
        {
            for (; first != last; ++first) {
                path p;

                p /= *first;
                p /= name;

                if (exists(p))
                    return sxe::filesystem::absolute(p);
            }

            return path();
        }

    };

} }


#endif // SXE_SYS_FILESYSTEM__HPP
