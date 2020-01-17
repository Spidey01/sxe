#ifndef SXE_TESTS_RESOURCE_ARCHIVESTREAMTEST__HPP
#define SXE_TESTS_RESOURCE_ARCHIVESTREAMTEST__HPP
/*-
 * Copyright (c) 2020-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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
#include <sxe/resource/ArchiveStream.hpp>

class ArchiveStreamTest : public CPPUNIT_NS::TestFixture
{
    CPPUNIT_TEST_SUITE(ArchiveStreamTest);

    CPPUNIT_TEST(ctors);
    CPPUNIT_TEST(move_ctor);
    CPPUNIT_TEST(move_assign);
    CPPUNIT_TEST(swap_method);
    CPPUNIT_TEST(swap_function);
    CPPUNIT_TEST(read);
    CPPUNIT_TEST(getline);

    CPPUNIT_TEST_SUITE_END();

  protected:

    void ctors();
    void move_ctor();
    void move_assign();
    void swap_method();
    void swap_function();
    void read();
    void getline();

  private:
};

#endif // SXE_TESTS_RESOURCE_ARCHIVESTREAMTEST__HPP
