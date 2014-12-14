package cn.com.cml.dbl.service;

import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.SystemService;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import cn.com.cml.dbl.ui.WindowAlarmView_;

@EService
public class WindowAlarmService extends Service {

	private boolean alarmShow;
	private View windowView;

	@SystemService
	WindowManager manager;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if (!alarmShow) {

			windowView = WindowAlarmView_.build(getApplicationContext());
			
			WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
			// wmParams.type = LayoutParams.TYPE_SYSTEM_ALERT; // 设置window type
			wmParams.type = LayoutParams.TYPE_PHONE; // 设置window type
			wmParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
			wmParams.gravity = Gravity.CENTER; // 调整悬浮窗口至右侧中间
			wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
			wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;
			wmParams.systemUiVisibility = View.INVISIBLE;

			manager.addView(windowView, wmParams);
			alarmShow = true;
		}

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		alarmShow = false;
		if (null != windowView) {
			manager.removeView(windowView);
		}
		super.onDestroy();
	}

}
