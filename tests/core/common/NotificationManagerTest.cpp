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

#include "./NotificationManagerTest.hpp"

#include <sxe/core/sys/FileSystem.hpp>
#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

using std::string;
using sxe::core::common::NotificationManager;
using sxe::core::sys::FileSystem::path;

static const string TAG = "NotificationManagerTest";

CPPUNIT_TEST_SUITE_NAMED_REGISTRATION(NotificationManagerTest, TAG);
CPPUNIT_REGISTRY_ADD_TO_DEFAULT(TAG);


/** Helper event class.
 *
 * NotificationManager shouldn't need to care what this is.
 *
 * Just wrapping a string because K.I.S.S. More serious examples would be
 * something like an event from input device.
 */
class SXE_PRIVATE TestEvent
{
  private:
    string mText;

  public:
    TestEvent(const string& text)
        : mText(text)
    {
    }

    string toString() const
    {
        return mText;
    }

    void clear()
    {
        mText.clear();
    }

    operator string() const
    {
        return mText;
    }
};

/** Helper notification interface.
 *
 * NotificationManager just wants a invokable that takes the event.
 */
using TestListener = std::function<void(TestEvent)>;


/** Helper event manager. */
class SXE_PRIVATE TestEventManager : public NotificationManager<TestListener, TestEvent> {

    string_type asString(const TestEvent& event)
    {
        return event.toString();
    }

};


/** Helper user class.
 */
class SXE_PRIVATE TestUser
{
  public:

    bool called;
    TestEvent lastTestEvent;
    TestEventManager mTestEventManager;

    TestUser()
        : called(false)
        , lastTestEvent("")
        , mTestEventManager()
    {
        Log::xtrace(TAG, "TestUser ctor");
    }

    ~TestUser()
    {
        Log::xtrace(TAG, "TestUser dtor");
    }


    /** Callback to handle the event notification. */
    void onTest(TestEvent event)
    {
        string text = event.toString();

        Log::v(TAG, "TestUser::onTest(TestEvent => " + text + ")");

        called = true;
        lastTestEvent = event;
    }


    /** Injects a TestEvent to the manager. */
    void inject(const string& dummy)
    {
        mTestEventManager.notifyListeners(TestEvent(dummy));
    }
};


void NotificationManagerTest::broadcast()
{
    Log::xtrace(TAG, "broadcast()");

    static const string str = "foo";

    TestUser user;

    reset(user);

    Log::test(TAG, "Subscribing to broadcasts.");
    // Should work using a bind, etc.
    TestListener listener = std::bind(&TestUser::onTest, &user, std::placeholders::_1);
    auto id = user.mTestEventManager.subscribe(listener);

    assertNotCalled(user, str);
    Log::test(TAG, "Injecting str");
    user.inject(str);
    assertCalled(user, str);

    reset(user);

    Log::test(TAG, "Unsubscribing to broadcasts.");
    user.mTestEventManager.unsubscribe(id);

    assertNotCalled(user, str);
    user.inject(str);
    assertNotCalled(user, str);
}


void NotificationManagerTest::subscribe()
{
    Log::xtrace(TAG, "subscribe()");

    static const string not_used_str = "foo";
    static const string used_str = "bar";

    TestUser user;

    reset(user);

    // And with a lambda
    TestListener listener = [&user](auto str) -> void { user.onTest(TestEvent(str)); };

    Log::test(TAG, "Subscribing to used_str");
    auto id = user.mTestEventManager.subscribe(listener, used_str);

    Log::test(TAG, "Injecting a not_used_str.");
    assertNotCalled(user, not_used_str);
    user.inject(not_used_str);
    assertNotCalled(user, not_used_str);

    reset(user);

    Log::test(TAG, "Unsubscribing from all");
    user.mTestEventManager.unsubscribe(used_str, id);

    Log::test(TAG, "Injecting used_str after unsubscribe.");
    assertNotCalled(user, used_str);
    user.inject(used_str);
    assertNotCalled(user, used_str);
}


void NotificationManagerTest::reset(TestUser& u)
{
    Log::xtrace(TAG, "reset()");

    u.called = false;
    u.lastTestEvent.clear();
}


void NotificationManagerTest::assertCalled(const TestUser& u, const string& s)
{
    CPPUNIT_ASSERT_MESSAGE("TestUser::called is set when called.", 
                           u.called == true);
    CPPUNIT_ASSERT_MESSAGE("TestUser::lastTestEvent as a string should match the event string.",
                           s == u.lastTestEvent.toString());
}


void NotificationManagerTest::assertNotCalled(const TestUser& u, const string& s)
{
    CPPUNIT_ASSERT_MESSAGE("TestUser::called is unset until called.", 
                           u.called == false);
    CPPUNIT_ASSERT_MESSAGE("TestUser::lastTestEvent is nullptr until called.", 
                           u.lastTestEvent.toString().empty());
}
