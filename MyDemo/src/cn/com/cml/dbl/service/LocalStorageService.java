package cn.com.cml.dbl.service;

import org.androidannotations.annotations.EIntentService;
import org.androidannotations.annotations.ServiceAction;

import com.activeandroid.query.Delete;

import cn.com.cml.dbl.model.BindMessageModel;

import android.app.IntentService;
import android.content.Intent;

@EIntentService
public class LocalStorageService extends IntentService {

	public LocalStorageService() {
		super("LocalStorageService");
	}

	@ServiceAction
	public void saveBindPass(String username, String bindPass) {

		BindMessageModel model = new BindMessageModel();

		model.username = username;
		model.bindPass = bindPass;
		model.bindTime = System.currentTimeMillis();

		model.save();
	}

	@ServiceAction
	public void removeBindPass(String username) {
		new Delete().from(BindMessageModel.class).where("username=?", username)
				.execute();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// do nothing
	}

}
