package cn.com.cml.dbl.view;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

@EFragment
public class DefaultDialogFragment extends DialogFragment {

	@FragmentArg
	Integer positiveBtnText;

	@FragmentArg
	Integer negativeBtnText;

	@FragmentArg
	Integer title;

	@FragmentArg
	Integer text;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		OnClickListener listener = null;

		Fragment target = getTargetFragment();
		Activity activity = getActivity();

		if (target instanceof OnClickListener) {
			listener = (OnClickListener) target;
		} else if (activity instanceof OnClickListener) {
			listener = (OnClickListener) activity;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		if (null != positiveBtnText) {
			builder.setPositiveButton(positiveBtnText, listener);
		}

		if (null != negativeBtnText) {
			builder.setNegativeButton(negativeBtnText, listener);
		}

		if (null != title) {
			builder.setTitle(title);
		}

		builder.setMessage(text);

		return builder.create();
	}

}
