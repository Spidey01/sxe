/*-
 * Copyright (c) 2014-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

import com.spidey01.sxe.core.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/** A simple notification manager.
 *
 * Notifications and messages can be of any suitable interface/type. You may
 * wish to use EventListener and EventObject as base classes. There are two
 * forms of notification: a general broadcast and a specific subscription.
 *
 * To pass a payload of message/event data, etc. Extend this class and Override
 * the invoke() method appropriately for your interface.
 *
 * @see java.util.EventListener
 * @see Java.Util.EventObject
 * @see invoke
 */
public class NotificationManager<Receiver_T, Message_T> {
    private final static String TAG = "NotificationManager";

    /** List of receivers for a broadcast notification. */
    protected List<Receiver_T> mNotificationReceivers = new LinkedList<Receiver_T>();


    /** Map of receivers for specific notifications.*/
    protected Map<String, List<Receiver_T>> mSubscribers = new HashMap<String, List<Receiver_T>>();


    /** Add receiver to broadcast list. */
    public void subscribe(Receiver_T receiver) {
        mNotificationReceivers.add(receiver);
    }


    public void subscribe(String key, Receiver_T subscriber) {
        List<Receiver_T> bindings = mSubscribers.get(key);
        if (bindings == null) {
            bindings = new LinkedList<Receiver_T>();
            mSubscribers.put(key, bindings);
            Log.v(TAG, "created list of subscribers for ", key);

        }

        bindings.add(subscriber);
        Log.v(TAG, subscriber, " is now listening for ", key);
    }


    /** Add receiver for specific message.
     *
     * @param key toString() will be called to create a String key for an internal Map.
     */
    public void subscribe(Message_T key, Receiver_T subscriber) {
        subscribe(key.toString(), subscriber);
    }


    /** Remove receiver from broadcast notification list. */
    public void unsubscribe(Receiver_T receiver) {
        mNotificationReceivers.remove(receiver);

        for (List<Receiver_T> bindings : mSubscribers.values()) {
            bindings.remove(receiver);
        }
    }


    /** Send a broadcast to subscribers. */
    public void notifyListeners(Message_T message) {
        for (Receiver_T rx : mNotificationReceivers) {
            invoke(rx, message);
        }

        List<Receiver_T> bindings = mSubscribers.get(message.toString());
        if (bindings == null) {
            return;
        }

        for (Receiver_T subscriber : bindings) {
            invoke(subscriber, message);
        }
    }


    /** Adapter method for custom messages.
     *
     * Override this message in a subclass to handle the generic interface.
     *
     * Example:
     * <samp>
     *  interface MyEventListener {
     *      void onMyEvent(MyEvent e);
     *  }
     *
     *  class MyEventManager extends NotificationManager<MyEventListener, MyEvent> {
     *      @Override
     *      protected invoke(MyEventListener listener, MyEvent event) {
     *          listener.onMyEvent(event);
     *      }
     *  }
     * </samp>
     */
    protected void invoke(Receiver_T rx, Message_T message) {
    }

}
