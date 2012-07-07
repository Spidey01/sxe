package com.spidey01.sxe.android;

import com.spidey01.sxe.core.Display;
import com.spidey01.sxe.android.OpenGles2Renderer;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class AndroidDisplay 
    extends GLSurfaceView
    implements Display
{
    public AndroidDisplay(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        setRenderer(new OpenGles2Renderer());
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public boolean create() {
        return true;
    }

    public void destroy() {
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

}

