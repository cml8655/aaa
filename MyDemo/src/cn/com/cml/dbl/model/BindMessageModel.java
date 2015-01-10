package cn.com.cml.dbl.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

@Table(name = "t_mobile_bind")
public class BindMessageModel extends Model {

	@Column
	public String username;

	@Column
	public String bindPass;

	public static void clear() {
		new Delete().from(BindMessageModel.class).execute();
	}

	public static boolean checkExists(String bindPass) {

		return new Select().from(BindMessageModel.class)
				.where(" bindPass=?", bindPass).exists();

	}

	public static boolean checkExists(String username, String bindPass) {

		return new Select().from(BindMessageModel.class)
				.where("username=? ", username).and("bindPass=?", bindPass)
				.exists();

	}

}
