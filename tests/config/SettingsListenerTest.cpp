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

#include "./SettingsListenerTest.hpp"

#include <sxe/config/Settings.hpp>
#include <sxe/config/SettingsListener.hpp>
#include <sxe/config/SettingsMap.hpp>
#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

using std::string;
using sxe::config::SettingsListener;
using sxe::config::Settings;
using sxe::config::SettingsMap;

static const string TAG = "SettingsListenerTest";

CPPUNIT_TEST_SUITE_NAMED_REGISTRATION(SettingsListenerTest, TAG);
CPPUNIT_REGISTRY_ADD_TO_DEFAULT(TAG);

void SettingsListenerTest::all()
{
    Log::xtrace(TAG, "all()");

    SettingsMap settings;

    string last;

    auto cb = [&last](string key) -> void
    {
        last.assign(key);
    };

    {
        SettingsListener listener(settings, cb);

        settings.setString("notification", "broadcast");
        CPPUNIT_ASSERT(last == "notification");
        last.clear();
    }

    settings.setString("ears", "plugged");
    CPPUNIT_ASSERT(last.empty());
}


void SettingsListenerTest::filtered()
{
    Log::xtrace(TAG, "filtered()");

    SettingsMap settings;

    string last;

    auto cb = [&last](string key) -> void
    {
        last.assign(key);
    };

    SettingsListener listener(settings, cb, "specific");

    settings.setString("no", "one listening");
    CPPUNIT_ASSERT_MESSAGE("No notifications before initialize.",
                           last.empty());

    listener.setFilter("specific");

    settings.setString("not", "it");
    CPPUNIT_ASSERT_MESSAGE("SettingsListener::setFilter is broken if this doesn't work.",
                   last.empty());

    settings.setString("specific.prefix", "only");
    CPPUNIT_ASSERT(last == "specific.prefix");
}
