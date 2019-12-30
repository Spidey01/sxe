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

#include "./DisplayModeTest.hpp"

#include <sxe/sys/FileSystem.hpp>
#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

using std::string;
using std::to_string;
using sxe::graphics::DisplayMode;

static const string TAG = "DisplayModeTest";

CPPUNIT_TEST_SUITE_NAMED_REGISTRATION(DisplayModeTest, TAG);
CPPUNIT_REGISTRY_ADD_TO_DEFAULT(TAG);

constexpr bool FS = true;
constexpr bool WIN = false;

void DisplayModeTest::raw()
{
    Log::xtrace(TAG, "raw()");

    DisplayMode vga_fs_mode(640, 480, 32, 60, FS);

    string mode;
    CPPUNIT_ASSERT_NO_THROW(mode = vga_fs_mode);
    Log::test(TAG, mode);

    CPPUNIT_ASSERT(vga_fs_mode.width() == 640);
    CPPUNIT_ASSERT(vga_fs_mode.height() == 480);
    CPPUNIT_ASSERT(vga_fs_mode.bpp() == 32);
    CPPUNIT_ASSERT(vga_fs_mode.refresh() == 60);
    CPPUNIT_ASSERT(vga_fs_mode.fullscreen() == FS);
}


void DisplayModeTest::str()
{
    Log::xtrace(TAG, "str()");

    DisplayMode vga_win_mode("1920 x 1080 x 32 @60", false);

    string mode;
    CPPUNIT_ASSERT_NO_THROW(mode = vga_win_mode);
    Log::test(TAG, mode);

    CPPUNIT_ASSERT(vga_win_mode.width() == 1920);
    CPPUNIT_ASSERT(vga_win_mode.height() == 1080);
    CPPUNIT_ASSERT(vga_win_mode.bpp() == 32);
    CPPUNIT_ASSERT(vga_win_mode.refresh() == 60);
    CPPUNIT_ASSERT(vga_win_mode.fullscreen() == WIN);
}


void DisplayModeTest::no_refresh()
{
    Log::xtrace(TAG, "no_refresh()");

    DisplayMode no_refresh("1024 x 768 x 16", false);

    string mode;
    CPPUNIT_ASSERT_NO_THROW(mode = no_refresh);
    Log::test(TAG, mode);

    CPPUNIT_ASSERT(no_refresh.width() == 1024);
    CPPUNIT_ASSERT(no_refresh.height() == 768);
    CPPUNIT_ASSERT(no_refresh.bpp() == 16);
    CPPUNIT_ASSERT(no_refresh.refresh() == 0);
}


void DisplayModeTest::no_bpp_or_refresh()
{
    Log::xtrace(TAG, "no_bpp_or_refresh()");

    DisplayMode no_bpp("1024 x 768", false);

    string mode;
    CPPUNIT_ASSERT_NO_THROW(mode = no_bpp);
    Log::test(TAG, mode);

    CPPUNIT_ASSERT(no_bpp.width() == 1024);
    CPPUNIT_ASSERT(no_bpp.height() == 768);
    CPPUNIT_ASSERT(no_bpp.bpp() == 0);
    CPPUNIT_ASSERT(no_bpp.refresh() == 0);
}


void DisplayModeTest::no_width()
{
    Log::xtrace(TAG, "no_width()");

    DisplayMode no_width("3840", false);

    string mode;
    CPPUNIT_ASSERT_NO_THROW(mode = no_width);
    Log::test(TAG, mode);

    CPPUNIT_ASSERT(no_width.width() == 3840);
    CPPUNIT_ASSERT(no_width.height() == 0);
    CPPUNIT_ASSERT(no_width.bpp() == 0);
    CPPUNIT_ASSERT(no_width.refresh() == 0);
}

