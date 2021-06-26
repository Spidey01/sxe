#ifndef SXE_TESTS_RECTANGLETEST__HPP
#define SXE_TESTS_RECTANGLETEST__HPP
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

#include <cppunit/extensions/HelperMacros.h>
#include <sxe/graphics/Rectangle.hpp>

class RectangleTest : public CPPUNIT_NS::TestFixture
{
    CPPUNIT_TEST_SUITE(RectangleTest);

    CPPUNIT_TEST(default_ctor);
    CPPUNIT_TEST(conversion_ctor);
    CPPUNIT_TEST(equality);
    CPPUNIT_TEST(assignment);
    CPPUNIT_TEST(area);
    CPPUNIT_TEST(perimeter);
    CPPUNIT_TEST(is_square);
    CPPUNIT_TEST(intersections);

    CPPUNIT_TEST_SUITE_END();

  protected:

    void default_ctor();
    void conversion_ctor();
    void equality();
    void assignment();
    void area();
    void perimeter();
    void is_square();
    void intersections();

  private:
};
#endif // SXE_TESTS_RECTANGLETEST__HPP