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

#include "./SettingsMapTest.hpp"

#include <sxe/core/config/SettingsMap.hpp>
#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

using std::string;
using sxe::core::config::SettingsMap;

static const string TAG = "SettingsMapTest";

CPPUNIT_TEST_SUITE_NAMED_REGISTRATION(SettingsMapTest, TAG);
CPPUNIT_REGISTRY_ADD_TO_DEFAULT(TAG);


SettingsMapTest::settings_ptr SettingsMapTest::make_settings() const
{
    return std::make_unique<SettingsMap>();
}

void SettingsMapTest::ctor()
{
    Log::xtrace(TAG, "ctor");


    static char* argv[] = {
        "a1=str1",
        "a2=2",
    };
    int argc = 2;

    SettingsMap map(argc, argv);

    CPPUNIT_ASSERT(map.getString("a1") == "str1");
    CPPUNIT_ASSERT(map.getInt("a2") == 2);
}
