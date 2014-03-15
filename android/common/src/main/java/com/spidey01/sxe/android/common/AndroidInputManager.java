/*-
 * Copyright (c) 2012-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

package com.spidey01.sxe.android.common;

import com.spidey01.sxe.android.common.AndroidDisplay;

import com.spidey01.sxe.core.graphics.Display;
import com.spidey01.sxe.core.input.AbstractInputManager;
import com.spidey01.sxe.core.input.KeyListener;

import android.view.KeyEvent;
import android.view.View.OnKeyListener;
import android.view.View;
import android.view.MotionEvent;
    import android.content.Context;
    import android.widget.Toast;


public class AndroidInputManager
    extends AbstractInputManager
{

    public class OnKeyListener
            implements View.OnKeyListener
    {
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            // only display on keyup, for ease of testing
            // if (event.getAction() == KeyEvent.ACTION_DOWN) {
            // return true;
            // }

            com.spidey01.sxe.core.input.KeyEvent e =
                new com.spidey01.sxe.core.input.KeyEvent(
                        AndroidInputManager.this,   // cool trick!
                        event,
                        InputCodeUtils.toSxE(keyCode),
                        "depreciated paramater",
                        (event.getAction() == KeyEvent.ACTION_DOWN));
            AndroidInputManager.this.notifyKeyListeners(e);

            // Context context = ((AndroidDisplay)mWidget).getContext();
            // String m = keyName+" was pressed from thread "+Thread.currentThread().getId();
            // int duration = Toast.LENGTH_LONG;

            // Toast toast = Toast.makeText(context, m, duration);
            // toast.show();

            return true;
        }
    }


    private static final String TAG = "AndroidInputManager";

    private AndroidDisplay mWidget;
    private OnKeyListener mOnKeyListener = new OnKeyListener();


    public AndroidInputManager(AndroidDisplay display) {
        super();
        mWidget = display;
        mWidget.setOnKeyListener(mOnKeyListener);

        /* works
        mWidget.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Context context = ((AndroidDisplay)mWidget).getContext();
                String m = "onTouch called from thread "+Thread.currentThread().getId();
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, m, duration);
                toast.show();
                return true;
            }
        });
        */
    }


    public void poll() {
        // is there something we can do to poke the GUI to do this, just in case?
        throw new UnsupportedOperationException("poll() not implemented on Android");
    }
}

