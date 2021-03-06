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

#include "./SubsystemTest.hpp"

#include <sxe/GameEngine.hpp>
#include <sxe/common/Subsystem.hpp>
#include <sxe/config/Settings.hpp>
#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

using std::string;
using sxe::GameEngine;
using sxe::common::Subsystem;
using sxe::config::Settings;
using sxe::sys::FileSystem::path;

static const string TAG = "SubsystemTest";
static GameEngine sGameEngine;

CPPUNIT_TEST_SUITE_NAMED_REGISTRATION(SubsystemTest, TAG);
CPPUNIT_REGISTRY_ADD_TO_DEFAULT(TAG);

class TestSubsystem : public Subsystem
{
  public:
    TestSubsystem()
        : Subsystem("TestSubsystem")
    {
    }

    string_type lastSettingChanged;

  protected:

    void onSettingChanged(string_type key) override
    {
        lastSettingChanged = key;
    }

};


void SubsystemTest::name()
{
    Log::xtrace(TAG, "name()");

    TestSubsystem subsystem;

    CPPUNIT_ASSERT_MESSAGE("Subsystem::name() should match its ctor.",
                           subsystem.name() == "TestSubsystem");
}


void SubsystemTest::initialization()
{
    Log::xtrace(TAG, "initialization()");

    TestSubsystem subsystem;

    CPPUNIT_ASSERT_MESSAGE("isInitialized() is false until initialize() is called.",
                           subsystem.isInitialized() == false);

    CPPUNIT_ASSERT_NO_THROW(subsystem.initialize(sGameEngine));

    CPPUNIT_ASSERT_MESSAGE("isInitialized() is true until uninitialize() is called.",
                           subsystem.isInitialized() == true);

    CPPUNIT_ASSERT_NO_THROW(subsystem.reinitialize(sGameEngine));

    CPPUNIT_ASSERT_MESSAGE("isInitialized() is true after reinitialize() is called.",
                           subsystem.isInitialized() == true);

    CPPUNIT_ASSERT_NO_THROW(subsystem.uninitialize());
    CPPUNIT_ASSERT_MESSAGE("isInitialized() is false after uninitialize() is called.",
                           subsystem.isInitialized() == false);
}


void SubsystemTest::onSettingChanged()
{
    Log::xtrace(TAG, "onSettingChanged()");

    TestSubsystem subsystem;
    Settings& settings = sGameEngine.getSettings();

    settings.setString("no", "one listening");
    CPPUNIT_ASSERT_MESSAGE("No notifications before initialize.",
                           subsystem.lastSettingChanged.empty());

    subsystem.initialize(sGameEngine);

    settings.setString("any", "key");
    CPPUNIT_ASSERT(subsystem.lastSettingChanged == "any");
    subsystem.lastSettingChanged.clear();

    subsystem.getSettingsListener().setFilter("specific");
    settings.setString("not", "it");
    CPPUNIT_ASSERT_MESSAGE("SettingsListener::setFilter is broken if this doesn't work.",
                   subsystem.lastSettingChanged.empty());
    subsystem.lastSettingChanged.clear();


    subsystem.uninitialize();
    settings.setString("any", "key");

    CPPUNIT_ASSERT_MESSAGE("No notifications after uninitialize.",
                           subsystem.lastSettingChanged.empty());
}
