package cn.com.cml.dbl.util;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.widget.Toast;
import cn.com.cml.dbl.view.NotifyDialogFragment;
import cn.com.cml.dbl.view.NotifyDialogFragment_;

public class DialogUtil {

	public static void showTip(Context context, String str) {

		if (str.endsWith("\n")) {
			str = str.substring(0, str.length() - 1);
		}

		Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);

		toast.setGravity(Gravity.TOP, 0, 0);

		toast.show();

	}

	public static void toast(Context context, int str) {
		Toast.makeText(context, context.getString(str), Toast.LENGTH_LONG)
				.show();
	}

	public static DialogFragment notifyDialogBuild(int icon, int msg) {

		NotifyDialogFragment dialog = NotifyDialogFragment_.builder()
				.iconRes(icon).msgRes(msg).build();

		return dialog;

	}
}
