package cn.com.cml.dbl;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

@EActivity(R.layout.activity_modal)
public class ModalActivity extends FragmentActivity {

	@Extra
	Class<? extends Fragment> container;
}
