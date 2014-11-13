package cn.com.cml.pets.service;

import org.androidannotations.annotations.EService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * app全局后台服务，用于启动短信的监听功能
 * 
 * @author 陈孟琳
 *
 *         2014年11月13日
 */
@EService
public class GlobalService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
