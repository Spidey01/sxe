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

#include "./InputFacetTest.hpp"

#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

using std::string;
using std::to_string;

using namespace sxe::input;

static const string TAG = "InputFacetTest";

CPPUNIT_TEST_SUITE_NAMED_REGISTRATION(InputFacetTest, TAG);
CPPUNIT_REGISTRY_ADD_TO_DEFAULT(TAG);

void InputFacetTest::setUp()
{
    mInputManager = std::make_unique<InputManager>("InputFacetTest::getInputManager");
}


void InputFacetTest::tearDown()
{
    mInputManager.reset();
}


void InputFacetTest::bindOne()
{
    Log::xtrace(TAG, "bindOne()");

    InputFacet input(getInputManager());

    bool done = false;

    KeyListener doit = [&done](KeyEvent event) -> bool
    {
        Log::d(TAG, "doit(): event: " + event.toString());
        done = true;
        return true;
    };

    CPPUNIT_ASSERT(input.addKeyListener(InputCode::IC_A, doit) == true);

    getInputManager().inject("a");

    CPPUNIT_ASSERT(done == true);

    done = false;

    CPPUNIT_ASSERT_NO_THROW(input.removeKeyListener(InputCode::IC_A));

    getInputManager().inject("a");

    CPPUNIT_ASSERT(done == false);
}


void InputFacetTest::bindMany()
{
    Log::xtrace(TAG, "bindMany()");

    InputFacet input(getInputManager());

    bool done = false;

    KeyListener doit = [&done](KeyEvent event) -> bool
    {
        Log::d(TAG, "doit(): event: " + event.toString());
        done = true;
        return true;
    };

    std::vector<InputCode> codes{
        InputCode::IC_W,
        InputCode::IC_A,
        InputCode::IC_S,
        InputCode::IC_D,
    };

    CPPUNIT_ASSERT(input.addKeyListeners(codes.begin(), codes.end(), doit) == true);

    getInputManager().inject("a");

    CPPUNIT_ASSERT(done == true);

    done = false;

    for (InputCode code : codes) Log::d(TAG, "codes to remove: " + code.dump());
    CPPUNIT_ASSERT_NO_THROW(input.removeKeyListeners(codes.begin(), codes.end()));

    getInputManager().inject("a");

    CPPUNIT_ASSERT(done == false);
}


InputManager& InputFacetTest::getInputManager() const
{
    return *mInputManager;
}

