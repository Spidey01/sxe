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

import com.spidey01.sxe.modelviewer.lib.ModelViewer;

import com.spidey01.sxe.pc.PcConfiguration;

import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.LogSink;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.SwingUtilities;

/** Main controller of things outside the Game.
 *
 * Chiefly this deals with Swing UI.
 */
class Main extends WindowAdapter implements Runnable {
    private static final String TAG = "Main";

    private GameEngine mGameEngine;
    private MainWindow mMainWindow;

    public static void main(String[] args) {
        // Log.add(new LogSink(System.out, Log.DEBUG));
        SwingUtilities.invokeLater(new Main());
    }


    @Override
    public void run() {
        mGameEngine = PcConfiguration.setup(new ModelViewer());
        mMainWindow = new MainWindow(TAG);

        /*
         * Give us enough control over shutdown.
         */
        mMainWindow.addWindowListener(this);
        mMainWindow.setDefaultCloseOperation(
                // MainWindow.DO_NOTHING_ON_CLOSE
                MainWindow.DISPOSE_ON_CLOSE
                // MainWindow.EXIT_ON_CLOSE
        );

        /*
         * Parent mus tbe visible before adding Display as a child of the
         * canvas. Unless you like terrible failures ;).
         */
        mMainWindow.setVisible(true);
        try {
            Display.setParent(mMainWindow.getCanvas());
        } catch (LWJGLException e) {
            Log.e(TAG, "Failed setting parentage for LWJGL.", e);
            e.printStackTrace();
            return;
        }
        mMainWindow.pack();

        mGameEngine.start();
    }


    @Override
    public void windowClosed(WindowEvent e) {
        Log.i(TAG, "windowClosed()");
        super.windowClosed(e);
        if (e.getComponent() == mMainWindow) {
            Log.i(TAG, "Exiting now");
            System.exit(0);
        }
    }


    @Override
    public void windowClosing(WindowEvent e) {
        Log.i(TAG, "windowClosing()");
        super.windowClosed(e);
        if (e.getComponent() == mMainWindow) {
            Log.v(TAG, "Window is closing, killing the engine.");
            mGameEngine.stop();
        }
    }
}

