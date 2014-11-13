package cn.com.cml.pets.ui;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.AttributeSet;
import android.view.KeyEvent;

public class MyOpenGLSurfaceView extends GLSurfaceView {

	private Renderer renderer = new MyRenderer();

	public MyOpenGLSurfaceView(Context context) {
		super(context);
		setRenderer(renderer);
		getHolder().setFormat(PixelFormat.TRANSLUCENT);
	}

	public MyOpenGLSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setRenderer(renderer);
		getHolder().setFormat(PixelFormat.TRANSLUCENT);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			queueEvent(new Runnable() {
				// This method will be called on the rendering
				// thread:
				public void run() {
					// render.handleDpadCenter();
				}
			});
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	class MyRenderer implements Renderer {

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			gl.glShadeModel(GL10.GL_SMOOTH);
			gl.glClearColor(0, 0, 0, 0);
			gl.glClearDepthf(1.0f);
			gl.glEnable(GL10.GL_DEPTH_TEST);
			gl.glDepthFunc(GL10.GL_LEQUAL);
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
			gl.glCullFace(GL10.GL_BACK);
			gl.glEnable(GL10.GL_LIGHT0);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			gl.glViewport(0, 0, width, height);

			// make adjustments for screen ratio
			float ratio = (float) width / height;
			gl.glMatrixMode(GL10.GL_PROJECTION); // set matrix to projection
													// mode
			gl.glLoadIdentity(); // reset the matrix to its default state
			gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7); // apply the projection
														// matrix
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			// Set GL_MODELVIEW transformation mode
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity(); // reset the matrix to its default state

			// When using GL_MODELVIEW, you must set the camera view
			GLU.gluLookAt(gl, 0, 0, -5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
		}
	}

}
