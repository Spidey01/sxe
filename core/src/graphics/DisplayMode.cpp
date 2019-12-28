/*-
 * Copyright (c) 2019-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

#include "sxe/core/graphics/DisplayMode.hpp"

#include <sxe/stdheaders.hpp>
#include <sxe/logging.hpp>

using std::string;
using std::to_string;
using std::stoi;
using std::atoi;

static const string TAG = "DisplayMode";

namespace sxe { namespace core { namespace graphics {

DisplayMode::DisplayMode(int width, int height, bool fs)
    : mWidth(width)
    , mHeight(height)
    , mBpp(0)
    , mRefresh(0)
    , mFullscreen(fs)
{
}


DisplayMode::DisplayMode(int width, int height, int bpp, int refresh, bool fs)
    : mWidth(width)
    , mHeight(height)
    , mBpp(bpp)
    , mRefresh(refresh)
    , mFullscreen(fs)
{
}


DisplayMode::DisplayMode(const std::string& mode, bool fs)
    : mWidth(0)
    , mHeight(0)
    , mBpp(0)
    , mRefresh(0)
    , mFullscreen(fs)
{
    auto x_1 = mode.find("x");
    auto x_2 = mode.find("x", x_1 + 1);
    auto at = mode.find("@");

    Log::test(TAG, "mWidth");
    mWidth = stoi(mode.substr(0, x_1));

    Log::test(TAG, "mHeight");
    if (x_1 != string::npos)
        mHeight = stoi(mode.substr(x_1 + 1, x_2));

    Log::test(TAG, "mBpp");
    if (x_2 != string::npos)
        mBpp = stoi(mode.substr(x_2 + 1, at));

    Log::test(TAG, "mRefresh");
    if (at != string::npos)
        mRefresh = stoi(mode.substr(at + 1));
}


int DisplayMode::width() const
{
    return mWidth;
}


int DisplayMode::height() const
{
    return mHeight;
}


int DisplayMode::bpp() const
{
    return mBpp;
}


int DisplayMode::refresh() const
{
    return mRefresh;
}


bool DisplayMode::fullscreen() const
{
    return mFullscreen;
}


DisplayMode::operator std::string() const
{
    return
        to_string(mWidth) + " x " + to_string(mHeight)
        + " x " + to_string(mBpp) + " @" + to_string(mRefresh)
        ;
}

} } }

