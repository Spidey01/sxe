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

#include "sxe/common/Subsystem.hpp"

#include <sxe/GameEngine.hpp>
#include <sxe/logging.hpp>

namespace sxe {  namespace common {

const Subsystem::string_type Subsystem::TAG = "Subsystem";

Subsystem::Subsystem(const string_type& name)
    : mIsInitialized(false)
    , mName(name)
    , mGame()
{
}


Subsystem::~Subsystem()
{
    if (mIsInitialized)
        Log::e(TAG, "~Subsystem(): " + mName + " not uninitialzed before destruction!");
}


const Subsystem::string_type& Subsystem::name() const
{
    return mName;
}


bool Subsystem::initialize(GameEngine& data)
{
    Log::xtrace(TAG, "initialize(): " + name());
    mGame = data.getGame();

    return Initializable::initialize(data);
}


bool Subsystem::reinitialize(GameEngine& engine)
{
    Log::xtrace(TAG, "reinitialize(): " + name());

    if (!uninitialize()) {
        Log::xtrace(TAG, "reinitialize(): unitialize() of " + name() + " failed");
        return false;
    }

    if (!initialize(engine)) {
        Log::xtrace(TAG, "reinitialize(): unitialize() of " + name() + " failed");
        return false;
    }

    return true;
}


bool Subsystem::uninitialize()
{
    Log::xtrace(TAG, "uninitialize(): " + name());

    bool ok = Initializable::uninitialize();

    mGame.reset();

    return ok;
}


void Subsystem::update()
{
}


Game::shared_ptr Subsystem::getGame() const
{
    return mGame.lock();
}


GameEngine& Subsystem::getGameEngine() const
{
    Game::shared_ptr g = getGame();

    if (!g)
        throw std::logic_error(TAG + "::getEngine() called on " + name() + " before " + TAG + "initialize().");

    return g->getGameEngine();
}


config::Settings& Subsystem::getSettings() const
{
    return getGameEngine().getSettings();
}


} }

