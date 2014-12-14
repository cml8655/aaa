package cn.com.cml.dbl.model;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "t_mobile_bind")
public class BindMessageModel extends Model {

	@Column
	public String username;

	@Column
	public String bindPass;

	@Column
	public Long bindTime;

	public static boolean checkExists(String username, String bindPass) {

		return new Select().from(BindMessageModel.class)
				.where("username=? and bindPass=?", username, bindPass)
				.exists();

	}

	@Override
	public String toString() {
		return "BindMessageModel [username=" + username + ", bindPass="
				+ bindPass + ", bindTime=" + bindTime + "]";
	}

}
