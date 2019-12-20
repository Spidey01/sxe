#ifndef SXE_CORE_CONFIG_SETTINGSMAP__HPP
#define SXE_CORE_CONFIG_SETTINGSMAP__HPP
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

#include <sxe/core/config/Settings.hpp>

namespace sxe { namespace core { namespace config {

    /** Basic std::map based implementation of Settings.
     *
     * Storage is purely in memory as a map of string_type. Thus save() does nothing.
     */
    class SXE_PUBLIC SettingsMap : public Settings
    {
      public:

        SettingsMap();

        /** Create SettingsMap from a sequence of strings.
         *
         * This is in the format of command line arguments. Namely
         * <samp>"key=value"</samp>.
         */
        SettingsMap(int count, char** args);

        virtual ~SettingsMap() = default;

        KeyList keys() const override;
        bool contains(const string_type& key) const override;

        string_type getString(const string_type& key) const override;

        SettingsMap& setString(const string_type& key, const string_type& value) override;

        void clear() override;

        bool save() override;

      protected:

      private:

        static const string_type TAG;

        std::map<string_type, string_type> mMap;
    };

} } }

#endif // SXE_CORE_CONFIG_SETTINGSMAP__HPP
