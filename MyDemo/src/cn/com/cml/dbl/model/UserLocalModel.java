package cn.com.cml.dbl.model;

import java.util.Date;

import cn.com.cml.dbl.util.CommonUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

@Table(name = "t_user_local_table")
public class UserLocalModel extends Model {

	@Column
	public String username;

	@Column
	public String lastChecking;

	@Column
	public String lastCheckingAddress;

	public static boolean todayChecking(String username) {

		Date today = new Date(System.currentTimeMillis());

		String todayStr = CommonUtils.formatDate(today, CommonUtils.FORMAT_YMD);

		int todayCheckingCount = new Select().from(UserLocalModel.class)
				.where("lastChecking=? and username=?", todayStr, username)
				.count();

		return todayCheckingCount > 0;
	}

	public static void insertOrUpdate(UserLocalModel model) {

		UserLocalModel localModel = new Select().from(UserLocalModel.class)
				.where("username=?", model.username).executeSingle();

		if (null == localModel) {
			model.save();
		} else {

			localModel.lastChecking = model.lastChecking;
			localModel.lastCheckingAddress = model.lastCheckingAddress;

			localModel.save();
		}

	}
}
