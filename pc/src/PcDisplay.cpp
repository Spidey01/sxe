/*-
 * Copyright (c) 2013-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

#include "sxe/pc/PcDisplay.hpp"

#include <GLFW/glfw3.h>
#include <sxe/logging.hpp>

using std::runtime_error;
using sxe::core::GameEngine;

using namespace sxe::core::graphics;

namespace sxe { namespace pc {

const PcDisplay::string_type PcDisplay::TAG = "PcDisplay";

PcDisplay::PcDisplay(const string_type& desired)
    : Display(TAG)
    , mWindow(nullptr)
{
    Log::xtrace(TAG, "PcDisplay(): desired: " + desired);

    DisplayMode mode(desired, false);
    if (!setMode(mode)) {
        throw runtime_error(TAG + ": Can't set displaymode to " + desired);
    }
}


PcDisplay::PcDisplay()
    : Display(TAG)
    , mWindow(nullptr)
{
    Log::xtrace(TAG, "PcDisplay()");
}


PcDisplay::~PcDisplay()
{
    Log::xtrace(TAG, "~PcDisplay()");
}


bool PcDisplay::initialize(GameEngine& engine)
{
    if (!Display::initialize(engine))
        return false;

    if (!glfwInit()) {
        Log::e(TAG, "glfwInit() failed!");
        return false;
    }

    glfwSetErrorCallback(PcDisplay::error_callback);

    using std::to_string;

    Log::d(TAG, "Compiled against GLFW version "
           + to_string(GLFW_VERSION_MAJOR)
           + "." + to_string(GLFW_VERSION_MINOR)
           + "." + to_string(GLFW_VERSION_REVISION));

    int major = 0;
    int minor = 0;
    int revision = 0;
    glfwGetVersion(&major, &minor, &revision);
    Log::d(TAG, "Running against GLFW version "
           + to_string(major)
           + "." + to_string(minor)
           + "." + to_string(revision));

    Log::v(TAG, "glfwGetVersionString(): " + string_type(glfwGetVersionString()));

    return true;
}


bool PcDisplay::uninitialize()
{
    Log::xtrace(TAG, "uninitialize()");

    glfwSetErrorCallback(nullptr);

    Log::xtrace(TAG, "glfwTerminate()");
    glfwTerminate();

    return Display::uninitialize();
}


void PcDisplay::update()
{
    Display::update();
    glfwPollEvents();
}


bool PcDisplay::create()
{
    Log::d(TAG, "create()");

    DisplayMode requested = getMode();

    Log::xtrace(TAG, "get the monitor.");
    GLFWmonitor* monitor = isWindowed() ? nullptr : glfwGetPrimaryMonitor();

    int width = requested.width();
    int height = requested.height();
    int bpp = requested.bpp();
    int refresh = requested.refresh();

    if (monitor != nullptr) {
        /* Only relevant to fullscreen mode. */
        Log::xtrace(TAG, "get the monitor's video mode.");
        const GLFWvidmode* native = glfwGetVideoMode(monitor);

        if (width == 0)
            width = native->width;

        if (height == 0)
            height = native->height;

        if (bpp == 0)
            bpp = native->redBits + native->greenBits + native->blueBits;

        if (refresh == 0)
            refresh = native->refreshRate;

        if (bpp > 0) {
            Log::xtrace(TAG, "set hint for color depth.");
            glfwWindowHint(GLFW_RED_BITS, bpp / 3);
            glfwWindowHint(GLFW_GREEN_BITS, bpp / 3);
            glfwWindowHint(GLFW_BLUE_BITS, bpp / 3);
        }
        Log::xtrace(TAG, "set hint for refresh rate.");
        glfwWindowHint(GLFW_REFRESH_RATE, refresh);
    }

    string_type title = getGame()->getName();
    GLFWwindow* shared = nullptr;

    Log::xtrace(TAG, "glfwCreateWindow()");
    // XXX: does GLFW copy the title?
    mWindow = glfwCreateWindow(width, height, title.c_str(), monitor, shared);

    if (mWindow == nullptr) {
        Log::e(TAG, getError());
        return false;
    }

    return true;
}


void PcDisplay::destroy()
{
    Log::d(TAG, "destroy()");

    glfwDestroyWindow(mWindow);
    mWindow = nullptr;
}


bool PcDisplay::isCloseRequested() const
{
    return glfwWindowShouldClose(mWindow);
}


bool PcDisplay::setMode(DisplayMode mode)
{
    if (!Display::setMode(mode))
        return false;

    if (mWindow == nullptr || !isInitialized()) {
        return true;
    }

    /* Runtime change. */

    DisplayMode target = getMode();

    GLFWmonitor* monitor = glfwGetWindowMonitor(mWindow);

    int xpos = 0;
    int ypos = 0;
    glfwGetWindowPos(mWindow, &xpos, &ypos);

    glfwSetWindowMonitor(mWindow, monitor,
                         xpos, ypos,
                         target.width(), target.height(),
                         target.refresh());

    return true;
}


PcDisplay::string_type PcDisplay::getError() const
{
    const char* str;
    int code = glfwGetError(&str);

    string_type r = "glfwGetError(): code: " + std::to_string(code) + " description: ";
    if (str)
        r.append(str);

    return r;
}


void PcDisplay::error_callback(int code, const char* description)
{
    string_type str = "glfwGetError(): code: " + std::to_string(code) + " description: ";
    if (description)
        str.append(description);
    Log::e(TAG, str);
}


} }

