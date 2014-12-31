package cn.com.cml.dbl.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public abstract class BaseDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return getCustomDialog();
	}

	protected abstract Dialog getCustomDialog();

}
