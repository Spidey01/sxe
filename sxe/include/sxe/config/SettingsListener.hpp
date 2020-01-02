#ifndef SXE_CONFIG_SETTINGSLISTENER__HPP
#define SXE_CONFIG_SETTINGSLISTENER__HPP
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
#include <sxe/stdheaders.hpp>
#include <sxe/config/Settings.hpp>

namespace sxe { namespace config {

    /** Helper class for using Settings::OnChangedListener and Settings::SettingsManager.
     *
     * Simply pass the correct data through the constructor and destroy the
     * object when you're done.
     */
    class SXE_PUBLIC SettingsListener
    {
      public:

        /** RAII binding of broadcast listener.
         */
        SettingsListener(Settings& settings, Settings::OnChangedListener listener);

        /** Only call listener if the key starts with prefix.
         */
        SettingsListener(Settings& settings, Settings::OnChangedListener listener, const Settings::string_type& prefix);
        ~SettingsListener();

        void setFilter(const Settings::string_type& prefix);
        void clearFilter();
        Settings::string_type getFilter() const;

      private:
        static const std::string TAG;

        Settings& mSettings;

        Settings::OnChangedListener mListener;
        Settings::SettingsManager::size_type mId;

        Settings::string_type mPrefix;

        void onChangedFilter(Settings::string_type key);
    };

} }

#endif // SXE_CONFIG_SETTINGSLISTENER__HPP
