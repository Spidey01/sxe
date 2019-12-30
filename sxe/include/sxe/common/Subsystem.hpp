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

#include <sxe/api.hpp>

#include <sxe/Game.hpp>

namespace sxe {
    class GameEngine;
    namespace config {
        class Settings;
    }
}

namespace sxe {  namespace common {

    class SXE_PUBLIC Subsystem
    {
      public:
        using GameEngine = sxe::GameEngine;
        using string_type = std::string;

        Subsystem(const string_type& name);
        virtual ~Subsystem();

        const string_type& name() const;

        virtual bool isInitialized() const;
        virtual bool initialize(GameEngine& engine);
        virtual bool reinitialize(GameEngine& engine);
        virtual bool uninitialize();
        virtual void update();

        Game::shared_ptr getGame() const;
        GameEngine& getGameEngine() const;
        config::Settings& getSettings() const;

      private:
        static const string_type TAG;

        bool mIsInitialized;
        string_type mName;

        sxe::Game::weak_ptr mGame;
    };

} }

#endif // SXE_COMMON_SUBSYSTEM__HPP
