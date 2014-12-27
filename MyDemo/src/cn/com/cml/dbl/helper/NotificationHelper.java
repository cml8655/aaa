package cn.com.cml.dbl.helper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.SystemService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.service.NotificationClickService_;

@EBean
public class NotificationHelper {

	public static final int STATUS_BAR_ID = 1001;

	@SystemService
	NotificationManager notifyManager;

	@RootContext
	Context context;

	public void addOrUpdateNotification(String title, String content) {

		Notification notification = new Notification();

		notification.flags = Notification.FLAG_ONGOING_EVENT;
		notification.icon = R.drawable.launcher;
		notification.when = System.currentTimeMillis();

		PendingIntent intent = PendingIntent.getService(context, 0,
				NotificationClickService_.intent(context).get(),
				PendingIntent.FLAG_UPDATE_CURRENT);

		notification.setLatestEventInfo(context, "dddd", "ssss", intent);

		notifyManager.notify(STATUS_BAR_ID, notification);
	}

	public void removeNotification() {

	}

}
