package cn.com.cml.dbl.model;

import cn.bmob.v3.BmobObject;

public class BaseModel extends BmobObject {

	public static interface State {
		int OK = 0;
		int FAIL = 1;
	}

	/** 0:ok,1:fail */
	private int state;
	/** state=1 时msg */
	private String msg;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
