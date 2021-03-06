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

#include "NullDemo.h"

#include <sxe/GameEngine.hpp>
#include <sxe/input/InputCode.hpp>
#include <sxe/input/InputFacet.hpp>
#include <sxe/input/KeyEvent.hpp>
#include <sxe/input/KeyListener.hpp>
#include <sxe/logging.hpp>

#include <functional>

using sxe::input::InputCode;
using sxe::input::KeyEvent;
using sxe::input::KeyListener;

namespace demos {

NullDemo::string_type NullDemo::TAG = "NullDemo";

NullDemo::string_type NullDemo::getName() const
{
    return TAG;
}


bool NullDemo::start()
{
    if (!sxe::Game::start())
        return false;

    Log::v(TAG, "Null demo is starting.");

    KeyListener listener = std::bind(&NullDemo::onKey, this, std::placeholders::_1);
    getInputFacet().addKeyListener(InputCode::IC_Q, listener);

    return true;
}


void NullDemo::stop()
{
    sxe::Game::stop();

    Log::v(TAG, "Null demo is stopping");
}


void NullDemo::update()
{
    Log::xtrace(TAG, "tick()");
}


bool NullDemo::onKey(KeyEvent event)
{
    if (event.isKeyUp()) {
        Log::d(TAG, event.toString());

        if (event.getKeyCode() == InputCode::IC_Q) {
            Log::d(TAG, "Q key released");
            requestStop();
            return true;
        }
    }
    return false;
}

}

