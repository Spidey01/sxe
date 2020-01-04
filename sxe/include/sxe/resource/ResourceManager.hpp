#ifndef SXE_RESOURCE_RESOURCEMANAGER__HPP
#define SXE_RESOURCE_RESOURCEMANAGER__HPP
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
#include <sxe/common/NotificationManager.hpp>
#include <sxe/common/Subsystem.hpp>
#include <sxe/config/Settings.hpp>

namespace sxe { namespace resource {

    class SXE_PUBLIC ResourceManager : public common::Subsystem
    {
      public:

        ResourceManager();
        ~ResourceManager();

        bool initialize(GameEngine& engine) override;
        bool uninitialize() override;

        void addResourceLocation(const string_type& path);
        void addResourceLocation(const path_type& path);

        void removeResourceLocation(const string_type& path);
        void removeResourceLocation(const path_type& path);

      protected:

        void onSettingChanged(string_type key) override;

      private:
        static const string_type TAG;

        /** {Game::getName()}.resources.
         */
        string_type mPrefix;

        config::Settings::SettingsManager::size_type mOnChangedListenerId;

        using LocationList = std::deque<path_type>;

        /** Locations to load resources from. */
        LocationList mSearchLocations;
    };

} }

#endif // SXE_RESOURCE_RESOURCEMANAGER__HPP
