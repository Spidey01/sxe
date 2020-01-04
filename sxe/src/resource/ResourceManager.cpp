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

#include "sxe/resource/ResourceManager.hpp"

#include <sxe/GameEngine.hpp>
#include <sxe/common/Utils.hpp>
#include <sxe/filesystem.hpp>
#include <sxe/logging.hpp>
#include <sxe/sys/FileSystem.hpp>

using std::invalid_argument;
using sxe::common::Utils::ends_with;
using sxe::common::Utils::split;
using sxe::common::Utils::split_str;
using sxe::common::Utils::starts_with;

namespace sxe { namespace resource {

const ResourceManager::string_type ResourceManager::TAG = "ResourceManager";

ResourceManager::ResourceManager()
    : Subsystem(TAG)
    , mPrefix()
    , mSearchLocations()
{
}


ResourceManager::~ResourceManager()
{
}


bool ResourceManager::initialize(GameEngine& engine)
{
    Log::xtrace(TAG, "initialize()");

    if (!Subsystem::initialize(engine))
        return false;

    mPrefix = engine.getGame().lock()->getName() + ".resources";

    /*
     * First time setup normally catches this via SettingsListener when
     * GameEngine integrates the settings, but reinitialization could be
     * different.
     */
    for (string_type dir : split_str(engine.getSettings().getString(mPrefix + ".path"), ':')) {
        Log::d(TAG, "initialize(): adding " + dir + " to mSearchLocations.");
        addResourceLocation(dir);
    }

    getSettingsListener().setFilter(mPrefix);

    return true;
}


bool ResourceManager::uninitialize()
{
    Log::xtrace(TAG, "uninitialize()");

    if (!Subsystem::uninitialize())
        return false;

    mSearchLocations.clear();
    mPrefix.clear();

    return true;
}


void ResourceManager::addResourceLocation(const string_type& path)
{
    Log::xtrace(TAG, "addResourceLocation(string_type): path: " + path);
    mSearchLocations.emplace_back(path);
}


void ResourceManager::addResourceLocation(const path_type& path)
{
    Log::xtrace(TAG, "addResourceLocation(path_type): path: " + path.string());
    mSearchLocations.push_back(path);
}


void ResourceManager::removeResourceLocation(const string_type& path)
{
    Log::xtrace(TAG, "addResourceLocation(string_type): path: " + path);

    path_type p(path);
    auto& c = mSearchLocations;

    c.erase(std::remove(c.begin(), c.end(), p),
            c.end());
}


void ResourceManager::removeResourceLocation(const path_type& path)
{
    Log::xtrace(TAG, "removeResourceLocation(path_type): path: " + path.string());

    auto& c = mSearchLocations;

    c.erase(std::remove(c.begin(), c.end(), path),
            c.end());
}


void ResourceManager::onSettingChanged(string_type key)
{
    Subsystem::onSettingChanged(key);

    if (ends_with(key, ".resources.path")) {
        string_type value = getSettings().getString(key);

        if (value.empty()) {
            Log::d(TAG, "onSettingChanged(): clearing mSearchLocations.");
            mSearchLocations.clear();
        } else {
            for (string_type dir : split_str(value, ':')) {
                Log::d(TAG, "onSettingChanged(): adding " + dir + " to mSearchLocations.");
                addResourceLocation(dir);
            }
        }
    }
}


} }

