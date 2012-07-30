package com.spidey01.sxe.core;

public interface Display {

    public boolean create();
    public void destroy();
    public void update();
    public boolean isCloseRequested();

    /** Add a FrameListener for frame events.
     *
     * This will register a FrameListener that will be notified on rendering evenets.
     */
    public void addFrameListener(FrameListener listener);
    public void addFrameStartedListener(FrameStartedListener listener);
    public void addFrameEndedListener(FrameEndedListener listener);
}

