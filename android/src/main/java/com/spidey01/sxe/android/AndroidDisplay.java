package com.spidey01.sxe.android;

import com.spidey01.sxe.android.OpenGles2Renderer;
import com.spidey01.sxe.core.Display;
import com.spidey01.sxe.core.FrameEndedListener;
import com.spidey01.sxe.core.FrameListener;
import com.spidey01.sxe.core.FrameStartedListener;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class AndroidDisplay 
    extends GLSurfaceView
    implements Display
{
    private OpenGles2Renderer mRenderer = new OpenGles2Renderer();
    private static final String TAG = "AndroidDisplay";

    public AndroidDisplay(Context context) {
        super(context);

        // required so OnKey works.
        setFocusable(true);

        // setup for OpenGL ES 2
        setEGLContextClientVersion(2);
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public boolean create() {
        return requestFocus();
    }

    public void destroy() {
    }

    public void update() {
        // except for the input polling, this ought to mimic the LWJGL
        // Display.update() well enough.
        invalidate();
        requestRender();
    }

    public boolean isCloseRequested() {
        // This is probably correct given that we expect to be created from an
        // Activity 97.9+% of the time.  But I'm not sure I want this to be
        // called frequently, nor do I really think it's a good way of doing
        // it. So for now we don't rely on this method under Android.
        /*
        if (${our saved Context} instanceof Activity) {
            return (Activity)${ctx^}.isFinishing();
        }
        */
        return false;
    }

    public void addFrameListener(FrameListener listener) {
        mRenderer.addFrameListener(listener);
    }

    public void addFrameStartedListener(FrameStartedListener listener) {
        mRenderer.addFrameStartedListener(listener);
    }

    public void addFrameEndedListener(FrameEndedListener listener) {
        mRenderer.addFrameEndedListener(listener);
    }
}

