package com.spidey01.sxe.core;

public interface Display {

    public boolean create();

    public void destroy();
    public void update();

    public boolean isCloseRequested();
}

