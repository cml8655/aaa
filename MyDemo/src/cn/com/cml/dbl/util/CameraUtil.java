package cn.com.cml.dbl.util;

import java.io.IOException;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.SurfaceHolder;

public class CameraUtil {

	private static CameraUtil cameraUtil = new CameraUtil();

	private Camera camera;

	private CameraUtil() {
	}

	public static CameraUtil getInstance() {
		return cameraUtil;
	}

	public boolean initCamera() {

		int number = Camera.getNumberOfCameras();

		if (number == 0) {
			return false;
		}

		if (camera != null) {
			camera.release();
		}

		camera = Camera.open();
		if (null != camera) {
			camera.setDisplayOrientation(90);
		}

		return this.isOpen();
	}

	public void orientationChange(int degrees) {
		if (isOpen()) {
			this.camera.setDisplayOrientation(degrees);
		}

	}

	public boolean startPreview(SurfaceHolder holder) {

		try {
			this.camera.setPreviewDisplay(holder);
			this.camera.startPreview();
		} catch (IOException e) {

			return false;
		}
		return true;
	}

	public boolean isOpen() {
		return null != camera;
	}

	public void release() {
		if (isOpen()) {
			camera.release();
			this.camera = null;
		}
	}

	public Parameters bestCameraParameter(int width, int height) {

		Parameters params = this.camera.getParameters();
		params.setPictureFormat(ImageFormat.JPEG);
		params.setPreviewSize(width, height);
		params.setColorEffect("#123456");

		return params;
	}
}
