#ifndef SXE_TESTS_INPUT_INPUTFACETTEST__HPP
#define SXE_TESTS_INPUT_INPUTFACETTEST__HPP
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
#include <sxe/input/InputFacet.hpp>
#include <sxe/input/InputManager.hpp>

class InputFacetTest : public CPPUNIT_NS::TestFixture
{
  public:

    void setUp() override;
    void tearDown() override;

  protected:
    CPPUNIT_TEST_SUITE(InputFacetTest);

    CPPUNIT_TEST(bindOne);
    CPPUNIT_TEST(bindMany);

    CPPUNIT_TEST_SUITE_END();

    void bindOne();
    void bindMany();

  private:

    std::unique_ptr<sxe::input::InputManager> mInputManager;

    sxe::input::InputManager& getInputManager() const;
};

#endif // SXE_TESTS_INPUT_INPUTFACETTEST__HPP
