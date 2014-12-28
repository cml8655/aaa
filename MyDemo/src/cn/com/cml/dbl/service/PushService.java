package cn.com.cml.dbl.service;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EIntentService;
import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.ServiceAction;

import cn.com.cml.dbl.PetApplication;
import cn.com.cml.dbl.contant.Constant;
import cn.com.cml.dbl.net.ApiRequestService;
import cn.com.cml.dbl.net.ApiRequestServiceClient;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

@EIntentService
public class PushService extends IntentService {

	@Bean
	ApiRequestServiceClient apiClient;

	public PushService() {
		super("PushService");
	}

	@ServiceAction
	public void pushRingMessage(String imei) {
		if (null == imei) {
			return;
		}
		apiClient.sendPushCommand(Constant.Command.JINGBAO_RING_ENUM, imei,
				null);
	}

	@ServiceAction
	public void pushAlarmExitMessage(String imei) {
		if (null == imei) {
			return;
		}
		apiClient.sendPushCommand(Constant.Command.JINGBAO_RING_ENUM, imei,
				null);
	}

	@ServiceAction
	public void pushLocationMonitorData(String imei, String location) {

		if (null == imei) {
			return;
		}

		Constant.Command command = Constant.Command.LOCATION_MONITOR_RESULT_ENUM;
		command.setExtra(location);

		apiClient.sendPushCommand(command, imei, null);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// do nothing
	}

}
