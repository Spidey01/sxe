#ifndef SXE_CONFIG_SETTINGSEXPANDER__HPP
#define SXE_CONFIG_SETTINGSEXPANDER__HPP
/*-
 * Copyright (c) 2014-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

namespace sxe { namespace config {

    class Settings;

    /** Helper class for expanding settings.
     *
     * The value of a setting can be interpolated into value over another setting
     * by enclosing it within curly braces. Like so:
     *
     *      key     = stored value  = expanded value
     *      ham     = foo           = foo
     *      spam    = bar           = bar
     *      eggs    = ${ham}.${bar} = foo.bar
     */
    class SXE_PUBLIC SettingsExpander
    {
      public:
        using string_type = std::string;

        /**
         *
         * @param s source settings to be expanded.
         */
        SettingsExpander(Settings& s);

        /** Expand input according to setting
         *
         * This can be used to do Settings::getString(key), and recursively
         * expand settings in the form of "${some key}" and similar.
         *
         * @param input the input to be expanded.
         * @param prefix settings are prefixed by this char.
         * @param openMarker settings begin with prefix, and this char.
         * @param closeMarker settings end with this char.
         * @param escape use this to escape prefix, openMarker, and escapeMarker.
         */
        string_type expand(const string_type& input, char prefix, char openMarker, char closeMarker, char escape);

        /** Expand for a given key.
         *
         * Equivalent to expand(s.getString(key), '$', '{', '}', '\\').
         */
        string_type expand(const string_type& key);

      private:
        static const string_type TAG;

        Settings& mSettings;

        std::stringstream mBuffer;
    };

} }
#endif // SXE_CONFIG_SETTINGSEXPANDER__HPP
