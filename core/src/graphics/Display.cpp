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

#include "sxe/core/graphics/Display.hpp"

#include <sxe/logging.hpp>

namespace sxe { namespace core { namespace graphics {

const Display::string_type Display::TAG = "Display";

Display::Display(const string_type& name)
    : Subsystem(name)
    , mFrameCounter("Frames")
    , mDisplayMode("640 x 480")
{
    Log::xtrace(TAG, "Display(): name: " + name);
}


Display::~Display()
{
    Log::xtrace(TAG, "~Display()");
}


void Display::update()
{
    try {
        // update framestarted

        mFrameCounter.update();

        // update frameended

    } catch(std::exception& ex) {
        Log::wtf(TAG, "Exception under Display::update(), halting.", ex);
        destroy();
    }
}



bool Display::isCloseRequested() const
{
    return false;
}


bool Display::setMode(const string_type& mode)
{
    Log::xtrace(TAG, "setMode(): mode: " + mode);

    mDisplayMode = mode;

    return true;
}

} } }

