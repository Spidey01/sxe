package com.spidey01.sxe.android;

import com.spidey01.sxe.core.FrameEndedListener;
import com.spidey01.sxe.core.FrameListener;
import com.spidey01.sxe.core.FrameStartedListener;
import com.spidey01.sxe.core.RateCounter;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.util.List;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGles2Renderer implements GLSurfaceView.Renderer {

    private RateCounter mFrameCounter = new RateCounter("Frames");
    private List<FrameStartedListener> mFrameStartedListeners = new ArrayList<FrameStartedListener>();
    private List<FrameEndedListener> mFrameEndedListeners = new ArrayList<FrameEndedListener>();
    private static final String TAG = "OpenGles2Renderer";

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        for (FrameStartedListener o : mFrameStartedListeners) {
            o.frameStarted();
        }

        // 128,0,128 -> for my Firefly.
        GLES20.glClearColor(0.5f, 0.0f, 0.5f, 1.0f);

        for (FrameEndedListener o : mFrameEndedListeners) {
            o.frameEnded();
        }
    }

    public void onDrawFrame(GL10 unused) {
        mFrameCounter.update();
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    public void addFrameListener(FrameListener listener) {
        mFrameStartedListeners.add(listener);
        mFrameEndedListeners.add(listener);
    }

    public void addFrameStartedListener(FrameStartedListener listener) {
        mFrameStartedListeners.add(listener);
    }

    public void addFrameEndedListener(FrameEndedListener listener) {
        mFrameEndedListeners.add(listener);
    }
}

