package cn.com.cml.dbl.view;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

@EFragment
public class DefaultDialogFragment extends DialogFragment implements
		OnClickListener {

	public static interface OnItemClickListener {
		public void onClick(DialogInterface dialog, int id, int requestId);
	}

	@FragmentArg
	Integer positiveBtnText;

	@FragmentArg
	Integer negativeBtnText;

	@FragmentArg
	Integer title;

	@FragmentArg
	Integer text;

	@FragmentArg
	int requestId;

	private OnItemClickListener listener;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		Fragment target = getTargetFragment();
		Activity activity = getActivity();

		if (target instanceof OnItemClickListener) {
			listener = (OnItemClickListener) target;
		} else if (activity instanceof OnItemClickListener) {
			listener = (OnItemClickListener) activity;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		if (null != positiveBtnText) {
			builder.setPositiveButton(positiveBtnText, this);
		}

		if (null != negativeBtnText) {
			builder.setNegativeButton(negativeBtnText, this);
		}

		if (null != title) {
			builder.setTitle(title);
		}

		builder.setMessage(text);

		return builder.create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

		if (null != listener) {
			listener.onClick(dialog, which, requestId);
		}
	}

}
