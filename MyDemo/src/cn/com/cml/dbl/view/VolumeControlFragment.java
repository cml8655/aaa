package cn.com.cml.dbl.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.support.v4.app.Fragment;
import android.widget.TextView;
import cn.com.cml.dbl.service.RingtoneService_;
import cn.com.cml.dbl.util.RingtoneUtil;
import cn.com.cml.pets.R;

@EFragment(R.layout.media_volume_fragment)
public class VolumeControlFragment extends Fragment {

	@ViewById(R.id.msg_show)
	TextView msgShowView;

	@Bean
	RingtoneUtil ringtoneUtil;
	

	@AfterViews
	public void initConfig() {
		ringtoneUtil.initDefaultRingtone();
	}

	@Click(R.id.media_volume_btn)
	public void mediaValumeControl() {
	}

	@Click(R.id.ring_volume_btn)
	public void ringValumeControl() {
	}

	@Click(R.id.play_ringtone_btn)
	public void playRingtone() {
		RingtoneService_.intent(getActivity()).start();
	}

	@Click(R.id.stop_ringtone_btn)
	public void stopRingtone() {
		RingtoneService_.intent(getActivity()).stop();
	}
}
