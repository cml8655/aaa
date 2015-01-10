package cn.com.cml.dbl.view;

import org.androidannotations.annotations.EFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import cn.com.cml.dbl.R;

@EFragment
public class RemotePassInputDialogFragment extends DefaultDialogFragment {

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

		View v = LayoutInflater.from(getActivity()).inflate(
				R.layout.view_remotepass_input, null);

		builder.setView(v);

		return builder.create();
	}

}
