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

#include "./InputManagerTest.hpp"

#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

using std::string;
using std::to_string;

using namespace sxe::input;

static const string TAG = "InputManagerTest";

CPPUNIT_TEST_SUITE_NAMED_REGISTRATION(InputManagerTest, TAG);
CPPUNIT_REGISTRY_ADD_TO_DEFAULT(TAG);

void InputManagerTest::injectString()
{
    Log::xtrace(TAG, "injectString()");

    InputManager input(TAG);

    /* Inject key up events for H,e,l,l, o. */
    input.inject("Hello");
}

