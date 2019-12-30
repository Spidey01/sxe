#ifndef SXE_CONFIG_SETTINGSFILE__HPP
#define SXE_CONFIG_SETTINGSFILE__HPP
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
#include <sxe/config/Settings.hpp>
#include <sxe/config/SettingsMap.hpp>
#include <sxe/sys/FileSystem.hpp>

namespace sxe {  namespace config {

    /** Textual implementation of Settings.
     *
     * Note: files may not be interchangeable between SxE 1.x and 2.x. In
     * version 1.x compatibility was java.util.properties. In Version 2,
     * hand written code is used.
     */
    class SXE_PUBLIC SettingsFile : public SettingsMap
    {
      public:
        using path_type = sxe::sys::FileSystem::path;

        /** Creates an empty settings file.
         */
        SettingsFile();

        /** Loads settings from file.
         */
        SettingsFile(const path_type& path);

        /** Loads settings from stream.
         */
        SettingsFile(std::istream& stream);

        virtual ~SettingsFile();

        /** Saves to path from ctor.
         */
        bool save() override;

        /** Saves to specified path.
         *
         * This does not modify the void version.
         */
        bool save(const path_type& path);

        bool save(std::ostream& stream);

      private:
        static const string_type TAG;
        path_type mPath;

        void parse(std::istream& stream);
    };

} }

#endif // SXE_CONFIG_SETTINGSFILE__HPP
