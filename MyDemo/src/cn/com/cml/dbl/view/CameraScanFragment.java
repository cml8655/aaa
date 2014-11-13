package cn.com.cml.dbl.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import cn.com.cml.dbl.util.CameraUtil;
import cn.com.cml.pets.R;

@EFragment(R.layout.fragment_camerascan)
public class CameraScanFragment extends Fragment {

	private static final String TAG = CameraScanFragment.class.getSimpleName();

	@ViewById(R.id.cameraView)
	protected CameraScannerView cameraView;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			CameraUtil.getInstance().orientationChange(90);
		} else if (newConfig.orientation == Configuration.ORIENTATION_SQUARE) {
			CameraUtil.getInstance().orientationChange(0);
		}

		super.onConfigurationChanged(newConfig);
	}

	@AfterViews
	protected void initConfig() {
	}
}
