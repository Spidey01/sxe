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

#include "sxe/pc/PcConfiguration.hpp"

#include <sxe/config/SettingsMap.hpp>
#include <sxe/config/SettingsXMLFile.hpp>
#include <sxe/sys/Platform.hpp>
#include <sxe/pc/PcDisplay.hpp>
#include <sxe/pc/PcInputManager.hpp>

using std::make_shared;
using std::make_unique;
using std::move;
using std::string;
using std::to_string;
using sxe::Game;
using sxe::GameEngine;
using sxe::config::Settings;
using sxe::config::SettingsMap;
using sxe::config::SettingsXMLFile;
using sxe::sys::Platform;

using namespace sxe::sys::FileSystem;

namespace sxe { namespace pc { 

const string PcConfiguration::TAG = "PcConfiguration";

GameEngine::unique_ptr PcConfiguration::setup(Game::shared_ptr game)
{
    return setup(0, nullptr, game);
}


GameEngine::unique_ptr PcConfiguration::setup(int argc, char* argv[], Game::shared_ptr game)
{
    argc -= 1;
    argv += 1;

    sxe::sys::Platform platform;

    return std::make_unique<sxe::GameEngine>
    (
        game
        , make_unique<SettingsMap>(argc, argv)
        , make_unique<sxe::pc::PcDisplay>()
        , nullptr // scene manager
        , make_unique<sxe::pc::PcInputManager>()
        , nullptr // resource manager
        , nullptr // logging manager
        , settings(game, platform)
        , platform
    );
}


GameEngine::Settings_ptr PcConfiguration::settings(Game::shared_ptr game, Platform platform)
{
    if (platform.isWindows()) {
        char* p = std::getenv("LOCALAPPDATA");
        if (p == nullptr)
            return nullptr;
        string localAppData = p;

        if (!game)
            return nullptr;
        string publisher = game->getPublisher();

        /*
         * 1.x had a compat kludge for XP/2K that would fallback to APPDATA.
         * 2.x does not support those systems.
         */

        if (localAppData.empty() || publisher.empty())
            return nullptr;

        /*
         * 1.x did .cfg then .xml check. Here we just check XML, as only
         *   settings parser currently available.
         */

        path dir;
        dir /= localAppData;
        dir /= publisher;

        path xml = dir / (game->getName() + ".xml");

        if (exists(xml))
            return make_unique<SettingsXMLFile>(xml);
    }

    return nullptr;
}

} }

