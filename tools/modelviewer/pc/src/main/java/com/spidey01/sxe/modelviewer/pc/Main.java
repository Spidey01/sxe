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

package com.spidey01.sxe.modelviewer.pc;

// import com.spidey01.sxe.core.GameEngine;
// import com.spidey01.sxe.pc.PcConfiguration;

import com.spidey01.sxe.modelviewer.lib.*;

import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.LogSink;

import java.awt.event.WindowEvent;
import javax.swing.SwingUtilities;

class Main extends java.awt.event.WindowAdapter implements Runnable {
    private static final String TAG = "Main";

    private MainWindow mMainWindow;

    public static void main(String[] args) {
        Log.add(new LogSink(System.out, Log.DEBUG));
        SwingUtilities.invokeLater(new Main());
    }

    @Override
    public void run() {
        mMainWindow = new MainWindow(TAG);

        mMainWindow.addWindowListener(this);
        // mMainWindow.setDefaultCloseOperation(MainWindow.DO_NOTHING_ON_CLOSE);
        mMainWindow.setDefaultCloseOperation(MainWindow.DISPOSE_ON_CLOSE);
        // mMainWindow.setDefaultCloseOperation(MainWindow.EXIT_ON_CLOSE);
        mMainWindow.setVisible(true);
    }


    @Override
    public void windowClosed(WindowEvent e) {
        Log.i(TAG, "windowClosed()");
        if (e.getComponent() == mMainWindow) {
            Log.i(TAG, "yes, right window!");
        }
        super.windowClosed(e);
    }


    @Override
    public void windowClosing(WindowEvent e) {
        Log.i(TAG, "windowClosing()");
        if (e.getComponent() == mMainWindow) {
            Log.i(TAG, "yes, right window!");
        }
        super.windowClosed(e);
    }
}

