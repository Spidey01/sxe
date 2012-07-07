package com.spidey01.sxe.pc;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class PcDisplay implements com.spidey01.sxe.core.Display {
    public PcDisplay() {
        try {
            Display.setDisplayMode(new DisplayMode(640, 480));
        } catch (LWJGLException e) {
            System.out.println("PcDisplay.PcDisplay() couldn't set display mode for LWJGL!");
            e.printStackTrace();
        }
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

    public boolean isCloseRequested() {
        return Display.isCloseRequested();
    }
}

