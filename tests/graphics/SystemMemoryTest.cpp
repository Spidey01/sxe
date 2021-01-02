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

#include "./SystemMemoryTest.hpp"

#include <sxe/graphics/SystemMemory.hpp>
#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

using std::string;
using std::to_string;
using namespace sxe::graphics;

static const string TAG = "SystemMemoryTest";

CPPUNIT_TEST_SUITE_NAMED_REGISTRATION(SystemMemoryTest, TAG);
CPPUNIT_REGISTRY_ADD_TO_DEFAULT(TAG);

void SystemMemoryTest::setUp()
{
    Log::xtrace(TAG, "setUp()");

    mTag = TAG;
    mSize = 4096;
    mBuffer = std::make_unique<SystemMemory>();

    CPPUNIT_ASSERT(mBuffer->pool() == 0);
    CPPUNIT_ASSERT(mBuffer->id() >= 0);
    CPPUNIT_ASSERT(mBuffer->empty());
}

void SystemMemoryTest::tearDown()
{
    Log::xtrace(TAG, "tearDown()");

    mTag.clear();
    mSize = 0;
    mBuffer = nullptr;
}

