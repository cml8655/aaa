package cn.com.cml.pets.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import cn.com.cml.pets.util.CameraUtil;

public class CameraScannerView extends SurfaceView implements
		SurfaceHolder.Callback {

	protected static final String TAG = "CameraScanFragment";

	private SurfaceHolder mHolder;

	public CameraScannerView(Context context) {
		super(context);
		mHolder = getHolder();
		mHolder.addCallback(this);
		CameraUtil.getInstance().initCamera();
	}

	public CameraScannerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mHolder = getHolder();
		mHolder.addCallback(this);
		CameraUtil.getInstance().initCamera();
	}

	public CameraScannerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mHolder = getHolder();
		mHolder.addCallback(this);
		CameraUtil.getInstance().initCamera();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

		CameraUtil cameraUtil = CameraUtil.getInstance();

		if (!cameraUtil.isOpen()) {
			return;
		}

		boolean preview = cameraUtil.startPreview(mHolder);

		cameraUtil.bestCameraParameter(width, height);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		CameraUtil.getInstance().release();
	}

	public SurfaceHolder getSurfaceHolder() {
		return mHolder;
	}
}
