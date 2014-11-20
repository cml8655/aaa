package cn.com.cml.dbl.util;

import android.support.v4.app.DialogFragment;
import cn.com.cml.dbl.view.NotifyDialogFragment;
import cn.com.cml.dbl.view.NotifyDialogFragment_;

public class DialogUtil {

	public static DialogFragment notifyDialogBuild(int icon, int msg) {

		NotifyDialogFragment dialog = NotifyDialogFragment_.builder()
				.iconRes(icon).msgRes(msg).build();

		return dialog;

	}
}
