#ifndef SXE_COMMON_SUBSYSTEM__HPP
#define SXE_COMMON_SUBSYSTEM__HPP
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

#include <sxe/Game.hpp>
#include <sxe/api.hpp>
#include <sxe/common/Initializable.hpp>
#include <sxe/config/Settings.hpp>
#include <sxe/config/SettingsListener.hpp>
#include <sxe/filesystem.hpp>

namespace sxe {
    class GameEngine;
}

namespace sxe {  namespace common {

    class SXE_PUBLIC Subsystem : public Initializable<GameEngine>
    {
      public:
        using GameEngine = sxe::GameEngine;
        using string_type = std::string;
        using path_type = sxe::filesystem::path;

        Subsystem(const string_type& name);
        virtual ~Subsystem();

        const string_type& name() const;

        bool initialize(GameEngine& engine) override;
        bool reinitialize(GameEngine& engine) override;
        bool uninitialize() override;
        virtual void update();

        Game::shared_ptr getGame() const;
        GameEngine& getGameEngine() const;
        config::Settings& getSettings() const;
        config::SettingsListener& getSettingsListener() const;

      protected:

        /** Callback when a setting is changed.
         *
         * Use getSettingsListener().setFilter() if you want to restrict
         * notifications to a given prefix.
         */
        virtual void onSettingChanged(string_type key);

      private:
        static const string_type TAG;

        bool mIsInitialized;
        string_type mName;

        sxe::Game::weak_ptr mGame;
        std::unique_ptr<config::SettingsListener> mSettingsListener;
    };

} }

#endif // SXE_COMMON_SUBSYSTEM__HPP
