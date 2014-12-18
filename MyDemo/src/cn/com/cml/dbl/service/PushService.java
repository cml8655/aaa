package cn.com.cml.dbl.service;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EIntentService;
import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.ServiceAction;

import cn.com.cml.dbl.contant.Constant;
import cn.com.cml.dbl.net.ApiRequestService;
import cn.com.cml.dbl.net.ApiRequestServiceClient;
import android.app.IntentService;
import android.content.Intent;

@EIntentService
public class PushService extends IntentService {

	@Bean
	ApiRequestServiceClient apiClient;

	public PushService() {
		super("PushService");
	}

	@ServiceAction
	public void pushRingMessage(Constant.Command command) {
		apiClient.sendPushCommand(command, "", null);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// do nothing
	}

}
