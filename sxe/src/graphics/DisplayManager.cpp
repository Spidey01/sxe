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

#include "sxe/graphics/DisplayManager.hpp"

#include <sxe/Game.hpp>
#include <sxe/GameEngine.hpp>
#include <sxe/config/Settings.hpp>
#include <sxe/logging.hpp>

namespace sxe {  namespace graphics {

const DisplayManager::string_type DisplayManager::TAG = "DisplayManager";

DisplayManager::DisplayManager(const string_type& name)
    : Subsystem(name)
    , mFrameCounter("Frames")
    , mDisplayMode("640 x 480 x 32 @60", false)
    , mFullscreen(false)
    , mOnChangedListenerId(SIZE_MAX)
    , mModeSettingKey()
    , mFpsSettingKey()
    , mFullscreenSettingKey()
{
    Log::xtrace(TAG, "DisplayManager(): name: " + name);
}


DisplayManager::~DisplayManager()
{
    Log::xtrace(TAG, "~DisplayManager()");
}


bool DisplayManager::initialize(GameEngine& engine)
{
    Log::xtrace(TAG, "initialize()");

    /* Handle runtime configuration Settings. */
    auto settingsListener = std::bind(&DisplayManager::onSettingChanged, this, std::placeholders::_1);

    Log::v(TAG, "Adding onSettingChanged to listen for settings changes.");
    mOnChangedListenerId = engine.getSettings().addChangeListener(settingsListener);

    string_type prefix = engine.getGame().lock()->getName();

    mModeSettingKey = prefix + ".mode";
    mFpsSettingKey = prefix + ".fps";
    mFullscreenSettingKey = prefix + ".fullscreen";

    return Subsystem::initialize(engine);
}


bool DisplayManager::uninitialize()
{
    Log::xtrace(TAG, "uninitialize()");

    getSettings().removeChangeListener(mOnChangedListenerId);
    mOnChangedListenerId = SIZE_MAX;

    mModeSettingKey.clear();
    mFpsSettingKey.clear();
    mFullscreenSettingKey.clear();

    return Subsystem::uninitialize();
}


void DisplayManager::update()
{
    try {
        // update framestarted

        mFrameCounter.update();

        // update frameended

    } catch(std::exception& ex) {
        Log::wtf(TAG, "Exception under DisplayManager::update(), halting.", ex);
        destroy();
    }
}



bool DisplayManager::isCloseRequested() const
{
    return false;
}


bool DisplayManager::setMode(const string_type& mode, bool fs)
{
    Log::xtrace(TAG, "setMode(): mode: " + mode + " fs: " + string_type(fs ? "true" : "false"));

    DisplayMode parsed(mode, fs);

    return setMode(parsed);
}


bool DisplayManager::setMode(DisplayMode mode)
{
    string_type m = mode;
    Log::xtrace(TAG, "setMode(): (str)mode: " + m);

    mDisplayMode = mode;

    return true;
}


const DisplayMode& DisplayManager::getMode() const
{
    return mDisplayMode;
}


void DisplayManager::setFullscreen(bool fs)
{
    Log::xtrace(TAG, "setFullscreen(): fs: " + string_type(fs ? "true" :"false"));

    DisplayMode mode(mDisplayMode.width(), mDisplayMode.width(),
                     mDisplayMode.bpp(), mDisplayMode.refresh(),
                     fs);

    if (isFullscreen())
        return;

    Log::v(TAG, "setFullscreen(): forwarding to setMode().");
    setMode(mode);
}


bool DisplayManager::isWindowed() const
{
    return !getMode().fullscreen();
}


bool DisplayManager::isFullscreen() const
{
    return getMode().fullscreen();
}


void DisplayManager::onSettingChanged(string_type key)
{
    Log::xtrace(TAG, "onSettingChanged(): key: " + key);

    config::Settings& settings = getSettings();

    if (key == mModeSettingKey) {
        /* Support setting resolution from runtime configuration. */
        setMode(settings.getString(key), settings.getBool(mFullscreenSettingKey));
    }
    else if (key == mFpsSettingKey) {
        /* Support toggling display of FPS from runtime configuration. */
        if (settings.getBool(key)) {
            mFrameCounter.enableDebugging();
        } else {
            mFrameCounter.disableDebugging();
        }
    }
    else if (key == mFullscreenSettingKey) {
        /* Support toggling between fullscreen / windowed mode. */
        setFullscreen(settings.getBool(key));
    }
}


RenderingApi DisplayManager::renderingApi() const
{
    string_type fromConfig = getSettings().getString("sxe.graphics.api");

    if (fromConfig.empty()) {
        string_type key = getGame()->getName() + ".graphics.api";
        fromConfig = getSettings().getString(key);
    }

    if (fromConfig == "Vulkan")
        return RenderingApi::Vulkan;
    else if (fromConfig == "OpenGLES")
        return RenderingApi::OpenGLES;

    if (supportsVulkan())
        return RenderingApi::Vulkan;

    return RenderingApi::OpenGLES;
}


bool DisplayManager::supportsVulkan() const
{
    return false;
}

} }

