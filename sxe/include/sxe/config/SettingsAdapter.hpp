#ifndef SXE_CONFIG_SETTINGSADAPTER__HPP
#define SXE_CONFIG_SETTINGSADAPTER__HPP
/*-
 * Copyright (c) 2021-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

namespace sxe {  namespace config {

    /** Settings wrapper that adapts "key" to "someprefix.key".
     *
     * This allows adapting an instance of settings such as the global runtime
     * Settings from Game::getSettings(), such that looking up "key" will first
     * check for a list of prefixes, e.g. "prefix1.key" -> "prefix2.key" ->
     * "key".
     * 
     * Setting keys however works without adaption. That's the grenade launcher.
     * I don't think you want to mess with that.
     */
    class SXE_PUBLIC SettingsAdapter : public Settings
    {
      public:
        using string_list = std::vector<string_type>;

        /** Create the settings adapter.
         * 
         * @param settings the parent settings to be adapted.
         * @param prefixes the list of prefixes to adapt.
         * @param sep the separtor to combine keys with prefixes.
         */
        SettingsAdapter(Settings& settings, const string_list& prefixes, const string_type& sep);

        /** Equal to SettingsAdapter(settings, prefix, "."").
         */
        SettingsAdapter(Settings& settings, const string_list& prefixes);

        virtual ~SettingsAdapter() = default;

        /** Adapted or unadapted keys().
         * 
         * @see keysReturnsAllPossibleForms.
         */
        KeyList keys() const override;

        /** Controls whether keys() is unadapted, or fully adapted.
         * 
         * By default this is false.
         * 
         * @param yes When true: insert each key contains() and getString() will look for.  When false: unadapted.
         */
        void keysReturnsAllPossibleForms(bool yes);

        /** Adapted contains().
         * 
         * Each prefix will be tried in order before trying key as is.
         */
        bool contains(const string_type& key) const override;

        /** Adapted getString().
         * 
         * Each prefix will be tried in order before trying key as is.
         */
        string_type getString(const string_type& key) const override;

        /** Unadapted setString().
         */
        SettingsAdapter& setString(const string_type& key, const string_type& value) override;

        /** Unadapted clear().
         */
        void clear() override;

        /** Unadapted save().
         */
        bool save() override;

      protected:
      private:
        static const string_type TAG;

        string_list mPrefixList;
        string_type mSeperator;
        std::reference_wrapper<Settings> mSettings;
        bool mKeysReturnsAllPossibleForms;
    };
} }

#endif // SXE_CONFIG_SETTINGSADAPTER__HPP
