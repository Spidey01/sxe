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

#include "./CommandTest.hpp"

#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

using std::string;
using sxe::cmds::Command;

static const string TAG = "CommandTest";

CPPUNIT_TEST_SUITE_NAMED_REGISTRATION(CommandTest, TAG);
CPPUNIT_REGISTRY_ADD_TO_DEFAULT(TAG);

class TestCommand : public Command
{
  public:
    bool invoked;

    TestCommand()
        : Command("test")
        , invoked(false)
    {
    }

    bool operator() () override
    {
        Log::xtrace(::TAG, "invoked = true");
        invoked = true;

        for (const string_type& arg : getArgs())
            Log::xtrace(::TAG, "args... " + arg);

        return Command::operator()();
    }
};


void CommandTest::name()
{
    Log::xtrace(TAG, "name()");

    TestCommand test;

    CPPUNIT_ASSERT(test.getName() == "test");
}


void CommandTest::compare()
{
    Log::xtrace(TAG, "compare()");

    TestCommand test;
    Command not_test("not_test");
    TestCommand other;

    CPPUNIT_ASSERT(test != not_test);
    CPPUNIT_ASSERT(test == other);
}


void CommandTest::invoke()
{
    Log::xtrace(TAG, "invoke()");

    TestCommand test;

    CPPUNIT_ASSERT(test.getArgs().empty());
    CPPUNIT_ASSERT_NO_THROW(test());
    CPPUNIT_ASSERT(test.invoked == true);
}

void CommandTest::setArgs()
{
    Log::xtrace(TAG, "setArgs()");

    TestCommand test;

    CPPUNIT_ASSERT(test.getArgs().empty());
    CPPUNIT_ASSERT_NO_THROW(test.setArgs("Hello World!"));
    CPPUNIT_ASSERT(test.getArgs().size() > 0);

    CPPUNIT_ASSERT_NO_THROW(test());
    CPPUNIT_ASSERT(test.invoked == true);

    CPPUNIT_ASSERT_MESSAGE("args should be cleared after invocation.",
                           test.getArgs().empty());
}

