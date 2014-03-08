/*-
 * Copyright (c) 2013-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

package com.spidey01.sxe.core.common;

import com.spidey01.sxe.core.logging.Log;
import com.spidey01.sxe.core.testing.UnitTest;

import org.junit.*;

import java.util.EventObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class NotificationManagerTest extends UnitTest {
    private static final String TAG = "NotificationManagerTest";


    /** Helper event class. */
    public class TestEvent extends EventObject {
        private final String mText;

        public TestEvent(Object source, String text) {
            super(source);
            mText = text;
        }

        public String toString() { return mText; }
    }


    /** Helper notification interface. */
    public interface TestListener {
        void onTest(TestEvent event);
    }


    /** Helper event manager. */
    public class TestEventManager extends NotificationManager<TestListener, TestEvent> {
        @Override
        protected void invoke(TestListener test, TestEvent event) {
            test.onTest(event);
        }
    }


    public class TestUser implements TestListener {
        public boolean called;
        public TestEvent lastTestEvent;

        public TestEventManager mTestEventManager = new TestEventManager();


        public void onTest(TestEvent event) {
            Log.i(TAG, "OnTest(", event.getClass().getName(), "=>", event, ")");
            called = true;
            lastTestEvent = event;
        }


        public void inject(String dummy) {
            mTestEventManager.notifyListeners(new TestEvent(this, dummy));
        }
    }


    @BeforeClass
    public static void beforeClass() {
        UnitTest.setup();
    }


    private TestUser mTestUser;


    @Before
    public void before() {
        Log.i(TAG, "before()");
        mTestUser = new TestUser();
    }


    @Test
    public void broadcast() {
        final String str = "foo";

        mTestUser.called = false;
        mTestUser.lastTestEvent = null;

        mTestUser.mTestEventManager.subscribe(mTestUser);


        assertNotCalled(mTestUser, str);
        mTestUser.inject(str);
        assertCalled(mTestUser, str);

        mTestUser.called = false;
        mTestUser.lastTestEvent = null;

        mTestUser.mTestEventManager.unsubscribe(mTestUser);

        assertNotCalled(mTestUser, str);
        mTestUser.inject(str);
        assertNotCalled(mTestUser, str);
    }


    @Test
    public void subscribed() {
        final String not_used_str = "foo";
        final String used_str = "bar";

        mTestUser.called = false;
        mTestUser.lastTestEvent = null;

        mTestUser.mTestEventManager.subscribe(used_str, mTestUser);


        assertNotCalled(mTestUser, not_used_str);
        mTestUser.inject(not_used_str);
        assertNotCalled(mTestUser, not_used_str);

        assertNotCalled(mTestUser, used_str);
        mTestUser.inject(used_str);
        assertCalled(mTestUser, used_str);

        mTestUser.called = false;
        mTestUser.lastTestEvent = null;

        mTestUser.mTestEventManager.unsubscribe(mTestUser);

        assertNotCalled(mTestUser, used_str);
        mTestUser.inject(used_str);
        assertNotCalled(mTestUser, used_str);
    }


    private void assertCalled(TestUser u, String s) {
        Assert.assertTrue(u.called);
        Assert.assertNotNull(u.lastTestEvent);
        Assert.assertEquals(s, u.lastTestEvent.toString());
    }


    private void assertNotCalled(TestUser u, String s) {
        Assert.assertFalse(mTestUser.called);
        Assert.assertNull(mTestUser.lastTestEvent);
    }

}

