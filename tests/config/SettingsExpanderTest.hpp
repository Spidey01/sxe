#ifndef SXE_TESTS_CONFIG_SETTINGSEXPANDERTEST__HPP
#define SXE_TESTS_CONFIG_SETTINGSEXPANDERTEST__HPP
/*-
 * Copyright (c) 2014-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

#include <cppunit/extensions/HelperMacros.h>
#include <sxe/config/SettingsExpander.hpp>

#include "SettingsFixture.hpp"

class SettingsExpanderTest
    : public CPPUNIT_NS::TestFixture
{
    CPPUNIT_TEST_SUITE(SettingsExpanderTest);
    CPPUNIT_TEST(noop);
    CPPUNIT_TEST(simple);
    CPPUNIT_TEST(nested);
    CPPUNIT_TEST(escaped);
    CPPUNIT_TEST_SUITE_END();

  protected:

    void noop();
    void simple();
    void nested();
    void escaped();
};

#endif // SXE_TESTS_CONFIG_SETTINGSEXPANDERTEST__HPP
