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

#include "sxe/RateCounter.hpp"
#include "sxe/logging.hpp"

using std::chrono::steady_clock;
using std::chrono::seconds;
using std::string;

namespace sxe {

const string RateCounter::TAG = "RateCounter";

RateCounter::RateCounter(const string& name, bool debug)
    : mName(name)
    , mDebuggingEnabled(debug)
    , mLast(steady_clock::now())
    , mPerSecond(0)
{
}

RateCounter::RateCounter(const string& name)
    : RateCounter(name, true)
{
}

std::string RateCounter::getName() const
{
    return mName;
}


void RateCounter::disableDebugging()
{
    mDebuggingEnabled = false;
}


void RateCounter::enableDebugging()
{
    mDebuggingEnabled = true;
}


bool RateCounter::isDebuggingEnabled()
{
    return mDebuggingEnabled;
}


void RateCounter::update()
{
    auto now = steady_clock::now();

    if (now > (mLast + seconds(1))) {
        if (isDebuggingEnabled()) {
            Log::log(Log::VERBOSE, mName + " " + TAG, getName() + ": " + std::to_string(mPerSecond-1) + " per second");
        }
        mLast = now;
        mPerSecond = 0;
    }

    mPerSecond++;
}

}

