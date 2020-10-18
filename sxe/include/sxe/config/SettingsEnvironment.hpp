#ifndef SXE_CONFIG_SETTINGSENVIRONMENT__HPP
#define SXE_CONFIG_SETTINGSENVIRONMENT__HPP
/*-
 * Copyright (c) 2020-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

#include <sxe/config/Settings.hpp>

namespace sxe
{
    namespace config
    {

        /** Settings backed by $EnvironmentVariables.
         *
         * Gets use std::getenv().
         * 
         * Sets use setenv(), or similar platform specific functionality.
         * 
         * Depending on your target platform: some functionality may be ommitted.
         * For example UWP applications do not have environment variables.
         */
        class SXE_PUBLIC SettingsEnvironment
            : public Settings
        {
          public:

            /** By default do not precache.
             */
            SettingsEnvironment();

            /** Initialize the settings.
             * 
             * @param cache when true: calls cacheEnvironment().
             */
            SettingsEnvironment(bool cache);

            virtual ~SettingsEnvironment() = default;

            /** Cache all the keys.
             * 
             * The cache will be purged, and all currently set environment
             * variables will be added to the key cache.
             */
            void cacheEnvironment();

            /** Cache specified key.
             */
            void cacheEnvironment(const string_type& key);

            /** Returns list of cached names.
             */
            KeyList keys() const override;

            bool contains(const string_type& key) const override;

            string_type getString(const string_type& key) const override;

            /** @copydoc Settings::setString().
             * 
             * Cache of keys will be updated.
             */
            SettingsEnvironment& setString(const string_type& key, const string_type& value) override;

            void clear() override;

            bool save() override;

          protected:
          private:
            static const string_type TAG;

            /** Used to implement keys().
             * 
             * Because messing with environ is better avoided than ported, and unit tests assume default constructed Settings are empty...
             */
            std::set<string_type> mKeys;

            static string_type get_env(const string_type& var);
            static bool set_env(const string_type& var, const string_type& value);
            static void all_env(SettingsEnvironment& self);
        };

    } // namespace config
} // namespace sxe

#endif // SXE_CONFIG_SETTINGSENVIRONMENT__HPP