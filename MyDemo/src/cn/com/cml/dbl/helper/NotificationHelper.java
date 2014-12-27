package cn.com.cml.dbl.helper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.SystemService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import cn.com.cml.dbl.R;
import cn.com.cml.dbl.service.NotificationClickService_;

@EBean
public class NotificationHelper {

	public static final int STATUS_BAR_ID = 1001;

	@SystemService
	NotificationManager notifyManager;

	@RootContext
	Context context;

	public void addRingtoneStartNotify(Context context, String broadcastAction) {

		notifyManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification notification = new Notification();

		notification.flags = Notification.FLAG_NO_CLEAR;
		notification.icon = R.drawable.launcher;
		notification.when = System.currentTimeMillis();

		PendingIntent intent = PendingIntent.getBroadcast(context, 1,
				new Intent(broadcastAction), PendingIntent.FLAG_CANCEL_CURRENT);

		notification.setLatestEventInfo(context,
				context.getString(R.string.shoutdown_tip),
				context.getString(R.string.shoutdown_action), intent);

		notifyManager.notify(STATUS_BAR_ID, notification);
	}

	public void removeNotify(Context context) {
		notifyManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notifyManager.cancel(STATUS_BAR_ID);
	}

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
