package com.spidey01.sxe.snakegame.android;

import android.app.Activity;
import android.os.Bundle;

import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.content.Context;
import android.opengl.GLES20;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;

public class SnakeGameActivity extends Activity {

    public class MyGLSurfaceView extends GLSurfaceView {
        public MyGLSurfaceView(Context context) {
            super(context);
            setEGLContextClientVersion(2);
            setRenderer(new MyGL20Renderer());
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }
    }
    public class MyGL20Renderer implements GLSurfaceView.Renderer {
        public void onSurfaceCreated(GL10 unused, EGLConfig config) {
            GLES20.glClearColor(0.5f, 0.0f, 0.5f, 1.0f);
        }
        public void onDrawFrame(GL10 unused) {
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        }
        public void onSurfaceChanged(GL10 unused, int width, int height) {
            GLES20.glViewport(0, 0, width, height);
        }
    }

    private GLSurfaceView mGLView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.main);

        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
    }
}
