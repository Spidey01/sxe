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

#include "./XdgTest.hpp"

#include <sxe/core/sys/FileSystem.hpp>
#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

using std::string;
using sxe::core::sys::FileSystem::path;
using sxe::core::sys::Xdg;

static const string TAG = "XdgTest";

CPPUNIT_TEST_SUITE_NAMED_REGISTRATION(XdgTest, TAG);
CPPUNIT_REGISTRY_ADD_TO_DEFAULT(TAG);


void XdgTest::members()
{
    Log::xtrace(TAG, "members()");

    Xdg xdg;

    /*
     * +/- if there is a home directory, only APP_HOME and XDG_RUNTIME_DIR
     * should be empty, even if no $XDG_* vars are set. Everyone else at least
     * gets a default if the var is unset.
     */

    // CPPUNIT_ASSERT_MESSAGE("Xdg::APP_HOME", !xdg.APP_HOME.empty());
    CPPUNIT_ASSERT_MESSAGE("Xdg::HOME", !xdg.HOME.empty());
    CPPUNIT_ASSERT_MESSAGE("Xdg::USER_DIR", !xdg.USER_DIR.empty());
    CPPUNIT_ASSERT_MESSAGE("Xdg::XDG_DATA_HOME", !xdg.XDG_DATA_HOME.empty());
    CPPUNIT_ASSERT_MESSAGE("Xdg::XDG_DATA_DIRS", !xdg.XDG_DATA_DIRS.empty());
    CPPUNIT_ASSERT_MESSAGE("Xdg::XDG_CONFIG_HOME", !xdg.XDG_CONFIG_HOME.empty());
    CPPUNIT_ASSERT_MESSAGE("Xdg::XDG_CONFIG_DIRS", !xdg.XDG_CONFIG_DIRS.empty());
    CPPUNIT_ASSERT_MESSAGE("Xdg::XDG_CACHE_HOME", !xdg.XDG_CACHE_HOME.empty());
    // CPPUNIT_ASSERT_MESSAGE("Xdg::XDG_RUNTIME_DIR", !xdg.XDG_RUNTIME_DIR.empty());
}


void XdgTest::getDataHomeDir()
{
    Log::xtrace(TAG, "getDataHomeDir()");

    Xdg xdg;

    const string foo = "foo";
    path inHome;
    CPPUNIT_ASSERT_NO_THROW(inHome = xdg.getDataHomeDir(foo));
    CPPUNIT_ASSERT(!inHome.empty());
    CPPUNIT_ASSERT(inHome.string().find(foo) != string::npos);
}


void XdgTest::getConfigHomeDir()
{
    Log::xtrace(TAG, "getConfigHomeDir()");

    Xdg xdg;

    const string foo = "foo";
    path inHome;
    CPPUNIT_ASSERT_NO_THROW(inHome = xdg.getConfigHomeDir(foo));
    CPPUNIT_ASSERT(!inHome.empty());
    CPPUNIT_ASSERT(inHome.string().find(foo) != string::npos);
}


void XdgTest::getCacheDir()
{
    Log::xtrace(TAG, "getCacheDir()");

    Xdg xdg;

    const string foo = "foo";
    path inHome;
    CPPUNIT_ASSERT_NO_THROW(inHome = xdg.getCacheDir(foo));
    CPPUNIT_ASSERT(!inHome.empty());
    CPPUNIT_ASSERT(inHome.string().find(foo) != string::npos);
}


void XdgTest::getDataDir()
{
    Log::xtrace(TAG, "getDataDir()");

    Xdg xdg;

    #if 0 // This requires making and deleting a file in one of the XDG_DATA_DIRS.
    const string foo = "foo";
    path inHome;
    CPPUNIT_ASSERT_NO_THROW(inHome = xdg.getDataDir(foo));
    CPPUNIT_ASSERT(!inHome.empty());
    CPPUNIT_ASSERT(inHome.string().find(foo) != string::npos);
    #endif
}


void XdgTest::getConfigDir()
{
    Log::xtrace(TAG, "getConfigDir()");

    #if 0 // This requires making and deleting a file in one of the XDG_CONFIG_DIRS.
    Xdg xdg;

    const string foo = "foo";
    path inHome;
    CPPUNIT_ASSERT_NO_THROW(inHome = xdg.getConfigDir(foo));
    CPPUNIT_ASSERT(!inHome.empty());
    CPPUNIT_ASSERT(inHome.string().find(foo) != string::npos);
    #endif
}

