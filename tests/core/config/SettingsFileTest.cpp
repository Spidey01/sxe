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

#include "./SettingsFileTest.hpp"

#include <sxe/core/config/SettingsFile.hpp>
#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

using std::string;
using sxe::core::config::SettingsFile;

static const string TAG = "SettingsFileTest";

CPPUNIT_TEST_SUITE_NAMED_REGISTRATION(SettingsFileTest, TAG);
CPPUNIT_REGISTRY_ADD_TO_DEFAULT(TAG);


SettingsFileTest::settings_ptr SettingsFileTest::make_settings() const
{
    return std::make_unique<SettingsFile>();
}


void SettingsFileTest::cfgstream()
{
    Log::xtrace(TAG, "cfgstream()");

    std::stringstream cfg;

    using std::endl;
    using std::quoted;

    cfg
        << "# non preserved comments and whitespace here" << endl
        << endl
        << "foo.some_number=2" << endl
        << "foo.nested.stuff=true" << endl
        << "foo.some_string=Hello, foo" << endl
        << "bar.float=5.0" << endl
        << endl
        ;

    Log::test(TAG, "cfg.str(): " + cfg.str());
    SettingsFile file(cfg);

    CPPUNIT_ASSERT(file.getInt("foo.some_number") == 2);
    CPPUNIT_ASSERT(file.getString("foo.some_string") == "Hello, foo");
    CPPUNIT_ASSERT(file.getBool("foo.nested.stuff") == true);
    float fpn = file.getFloat("bar.float");
    CPPUNIT_ASSERT(fpn >= 4.9 && fpn <= 5.1);
}

