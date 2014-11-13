package cn.com.cml.dbl.model;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "user")
public class User extends Model {

	@Column(length = 12, index = true, unique = true)
	private String username;
	@Column
	private String password;
	@Column
	private int age;
	@Column
	private int high;

	public static User findByUsername(String username) {

		return new Select().from(User.class).where("username=?", username)
				.executeSingle();
	}

	public static int userSize() {
		return new Select().from(User.class).count();
	}

	public static List<User> findAll() {
		return new Select().all().from(User.class).execute();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password
				+ ", age=" + age + ", high=" + high + "]";
	}

}
