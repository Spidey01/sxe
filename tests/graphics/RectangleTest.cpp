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

#include "./RectangleTest.hpp"

#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

using std::string;

static const string TAG = "RectangleTest";

CPPUNIT_TEST_SUITE_NAMED_REGISTRATION(RectangleTest, TAG);
CPPUNIT_REGISTRY_ADD_TO_DEFAULT(TAG);

/** Simple test that each field of r == n. */
template <typename R>
void TestCtor(const R& r, typename R::value_type n)
{
    CPPUNIT_ASSERT(r.x == n);
    CPPUNIT_ASSERT(r.y == n);
    CPPUNIT_ASSERT(r.width == n);
    CPPUNIT_ASSERT(r.height == n);
}

void RectangleTest::default_ctor()
{
    Log::xtrace(TAG, "default_ctor()");

    sxe::graphics::rect r;
    TestCtor(r, 0.0f);

    sxe::graphics::drect dr;
    TestCtor(dr, 0.0);

    sxe::graphics::irect ir;
    TestCtor(ir, 0);

    sxe::graphics::urect ur;
    TestCtor(ur, 0);
}

void RectangleTest::conversion_ctor()
{
    Log::xtrace(TAG, "conversion_ctor()");

    Log::xtrace(TAG, "create drect dr");
    sxe::graphics::drect dr = { 1.0, 2.0, 3.0, 4.0 };
    Log::xtrace(TAG, "create irect ir");
    sxe::graphics::irect ir = { 1, 2, 3, 4 };
    Log::xtrace(TAG, "create irect c from dr");
    sxe::graphics::irect c = dr;

    CPPUNIT_ASSERT(c.x == ir.x);
    CPPUNIT_ASSERT(c.y == ir.y);
    CPPUNIT_ASSERT(c.width == ir.width);
    CPPUNIT_ASSERT(c.height == ir.height);
    CPPUNIT_ASSERT(c == ir);
}

void RectangleTest::equality()
{
    Log::xtrace(TAG, "equality()");

    sxe::graphics::rect r1 = { 1.0f, 2.0f, 3.0f, 4.0f };
    sxe::graphics::rect r2 = { 1.0f, 2.0f, 3.0f, 4.0f };
    sxe::graphics::rect r3 = { 4.0f, 3.0f, 2.0f, 1.0f };

    CPPUNIT_ASSERT_MESSAGE("float operator==", r1 == r2);
    CPPUNIT_ASSERT_MESSAGE("float operator!=", r2 != r3);

    sxe::graphics::drect dr1 = { 1.0, 2.0, 3.0, 4.0 };
    sxe::graphics::drect dr2 = { 1.0, 2.0, 3.0, 4.0 };
    sxe::graphics::drect dr3 = { 4.0, 3.0, 2.0, 1.0 };
    CPPUNIT_ASSERT_MESSAGE("double operator==", dr1 == dr2);
    CPPUNIT_ASSERT_MESSAGE("double operator!=", dr2 != dr3);

    sxe::graphics::irect ir1 = { 1, 2, 3, 4 };
    sxe::graphics::irect ir2 = { 1, 2, 3, 4 };
    sxe::graphics::irect ir3 = { 4, 3, 2, 1 };
    CPPUNIT_ASSERT_MESSAGE("int operator==", ir1 == ir2);
    CPPUNIT_ASSERT_MESSAGE("int operator!=", ir2 != ir3);

    sxe::graphics::urect ur1 = { 1, 2, 3, 4 };
    sxe::graphics::urect ur2 = { 1, 2, 3, 4 };
    sxe::graphics::urect ur3 = { 4, 3, 2, 1 };
    CPPUNIT_ASSERT_MESSAGE("unsigned int operator==", ur1 == ur2);
    CPPUNIT_ASSERT_MESSAGE("unsigned int operator!=", ur2 != ur3);

    CPPUNIT_ASSERT_MESSAGE("int to unsigned int operator==", r1 == ur2);
    CPPUNIT_ASSERT_MESSAGE("int to unsigned int operator!=", r2 != ur3);
}


void RectangleTest::assignment()
{
    Log::xtrace(TAG, "assignment");

    sxe::graphics::rect r1 = { 4.0f, 3.0f, 2.0f, 1.0f };
    sxe::graphics::rect r2 = { 1.0f, 2.0f, 3.0f, 4.0f };
    CPPUNIT_ASSERT_NO_THROW_MESSAGE("float assignment action", r1 = r2);
    CPPUNIT_ASSERT_MESSAGE("float assignment tested", r1 == r2);

    sxe::graphics::drect dr1 = { 4.0, 3.0, 2.0, 1.0 };
    sxe::graphics::drect dr2 = { 1.0, 2.0, 3.0, 4.0 };
    CPPUNIT_ASSERT_NO_THROW_MESSAGE("double assignment action", dr1 = dr2);
    CPPUNIT_ASSERT_MESSAGE("double assignment tested", dr1 == dr2);

    sxe::graphics::irect ir1 = { 4, 3, 2, 1 };
    sxe::graphics::irect ir2 = { 1, 2, 3, 4 };
    CPPUNIT_ASSERT_NO_THROW_MESSAGE("int assignment action", ir1 = ir2);
    CPPUNIT_ASSERT_MESSAGE("int assignment tested", ir1 == ir2);

    sxe::graphics::urect ur1 = { 4, 3, 2, 1 };
    sxe::graphics::urect ur2 = { 1, 2, 3, 4 };
    CPPUNIT_ASSERT_NO_THROW_MESSAGE("unsigned int assignment action", ur1 = ur2);
    CPPUNIT_ASSERT_MESSAGE("unsigned int assignment tested", ur1 == ur2);

    CPPUNIT_ASSERT_NO_THROW_MESSAGE("float assigned from int action", r1 = ir1);
    CPPUNIT_ASSERT_MESSAGE("float assigned from int tested", r1 == ir1);
}