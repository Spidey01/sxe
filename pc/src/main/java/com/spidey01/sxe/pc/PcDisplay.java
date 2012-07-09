package com.spidey01.sxe.pc;

import com.spidey01.sxe.core.RateCounter;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.util.Arrays;

public class PcDisplay implements com.spidey01.sxe.core.Display {

    private RateCounter mFrameCounter = new RateCounter("Frames");

    public PcDisplay(String desired) {
        // default to VGA if desired not found
        DisplayMode wanted = new DisplayMode(640, 480);

        try {
            DisplayMode[] modes = Display.getAvailableDisplayModes();

            for (int i=0; i < modes.length; i++) {
                DisplayMode c = modes[i];
                if (c.isFullscreenCapable() && c.toString().startsWith(desired)) {
                    wanted = c;
                    break;
                }
            }

            Display.setDisplayMode(wanted);

        } catch (LWJGLException e) {
            System.out.println("PcDisplay.PcDisplay() couldn't set display mode for LWJGL!");
            e.printStackTrace();
        }
 
    }

    public PcDisplay() {
        this("640 x 480 x 16 @60");
    }

    public boolean create() {
        try {
            Display.create();
        } catch (LWJGLException e) {
            System.out.println("PcDisplay.create() can't create LWJGL display :'(");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void destroy() {
		Display.destroy();
    }
    public void update() {
            Display.update();
            mFrameCounter.update();
    }

    public boolean isCloseRequested() {
        return Display.isCloseRequested();
    }
}

