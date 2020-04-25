#ifndef SXE_CONFIG_SETTINGSXMLFILE__HPP
#define SXE_CONFIG_SETTINGSXMLFILE__HPP
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

#include <sxe/haveboost.hpp>
#include <sxe/api.hpp>
#include <sxe/config/Settings.hpp>
#include <sxe/sys/FileSystem.hpp>

namespace sxe {  namespace config {

    /** XML implementation of Settings.
     *
     * Note: files may not be interchangeable between SxE 1.x and 2.x. In
     * version 1.x compatibility was java.util.properties. In Version 2,
     * boost::property_tree is used.
     */
    class SXE_PUBLIC SettingsXMLFile : public Settings
    {
      public:
        using path_type = sxe::sys::FileSystem::path;

        /** Creates an empty XML settings file.
         */
        SettingsXMLFile();

        /** Loads settings from XML file.
         */
        SettingsXMLFile(const path_type& path);

        /** Loads settings from stream.
         */
        SettingsXMLFile(std::istream& stream);

        virtual ~SettingsXMLFile();

        KeyList keys() const override;

        bool contains(const string_type& key) const override;

        string_type getString(const string_type& key) const override;

        SettingsXMLFile& setString(const string_type& key, const string_type& value) override;

        void clear() override;

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
        #if SXE_HAVE_BOOST
        boost::property_tree::ptree mProps;
        #endif
        path_type mPath;
    };

} }

#endif // SXE_CONFIG_SETTINGSXMLFILE__HPP
