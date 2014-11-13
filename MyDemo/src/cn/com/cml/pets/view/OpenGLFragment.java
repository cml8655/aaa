package cn.com.cml.pets.view;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.support.v4.app.Fragment;
import cn.com.cml.pets.R;
import cn.com.cml.pets.ui.MyOpenGLSurfaceView;

@EFragment(R.layout.fragment_opengl)
public class OpenGLFragment extends Fragment {

	@ViewById(R.id.gl_surface)
	MyOpenGLSurfaceView surfaceView;

	@Override
	public void onResume() {
		super.onResume();
		if (null != surfaceView) {
			surfaceView.onResume();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (null != surfaceView) {
			surfaceView.onPause();
		}
	}
}
