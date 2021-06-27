/*-
 * Copyright (c) 2021-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

#include "./SettingsAdapterTest.hpp"

#include <sxe/config/SettingsAdapter.hpp>
#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

using std::string;
using sxe::config::SettingsAdapter;
using sxe::config::SettingsMap;

static const string TAG = "SettingsAdapterTest";

CPPUNIT_TEST_SUITE_NAMED_REGISTRATION(SettingsAdapterTest, TAG);
CPPUNIT_REGISTRY_ADD_TO_DEFAULT(TAG);

void SettingsAdapterTest::setUp()
{
    SettingsFixture::setUp();
    mSettingsMap = std::make_unique<SettingsMap>();
}

void SettingsAdapterTest::tearDown()
{
    SettingsFixture::tearDown();
    mSettingsMap.reset();
}

SettingsAdapterTest::settings_ptr SettingsAdapterTest::make_settings() const
{
    static const SettingsAdapter::string_list prefixes = {
        TAG
    };
    return std::make_unique<SettingsAdapter>(*mSettingsMap, prefixes);
}

void SettingsAdapterTest::ctor()
{
    Log::xtrace(TAG, "ctor");

    SettingsAdapterTest env;
}

void SettingsAdapterTest::prefix_adapter()
{
    Log::xtrace(TAG, "prefix_adpater()");

    settings_ptr s = make_settings();
    CPPUNIT_ASSERT_MESSAGE("make_settings() returned nullptr", s != nullptr);

    static const string some_key = "some_key";
    static const string some_value = "some_value";
    static const string sep = ".";
    const string prefixed_some_key = TAG + sep + some_key;

    CPPUNIT_ASSERT_NO_THROW(s->setString(prefixed_some_key, some_value));
    CPPUNIT_ASSERT(s->getString(prefixed_some_key) == some_value);
    CPPUNIT_ASSERT(s->getString(some_key) == some_value);

    CPPUNIT_ASSERT(s->contains(prefixed_some_key));
    CPPUNIT_ASSERT(s->contains(some_key));

    /*
     * This test is easier if the real key lacks prefix.
     */
    Log::d(TAG, "prefixe_adapter(): changing the real key from " + prefixed_some_key + " to " + some_key);
    s->clear();
    CPPUNIT_ASSERT_NO_THROW(s->setString(some_key, some_value));

    sxe::config::Settings::KeyList kl;
    SettingsAdapter* sa = (SettingsAdapter*)s.get();

    sa->keysReturnsAllPossibleForms(false);
    kl = s->keys();
    CPPUNIT_ASSERT_MESSAGE("keysReturnsAllPossibleForms(false) - only the real key.", kl.size() == 1 && kl.at(0) == some_key);

    sa->keysReturnsAllPossibleForms(true);
    kl = s->keys();
    CPPUNIT_ASSERT_MESSAGE("keysReturnsAllPossibleForms(true) - include real keys.", kl.size() == 2 && std::find(kl.begin(), kl.end(), prefixed_some_key) != kl.end());
    CPPUNIT_ASSERT_MESSAGE("keysReturnsAllPossibleForms(true) - include prefixed keys.", kl.size() == 2 && std::find(kl.begin(), kl.end(), some_key) != kl.end());
}
