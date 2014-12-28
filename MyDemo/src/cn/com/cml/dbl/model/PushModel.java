package cn.com.cml.dbl.model;

import java.io.Serializable;

public class PushModel implements Serializable {

	private String fromUserName;
	private String bindPass;
	private String toUserName;
	private String command;
	private long endTime;// 有效的截止时间
	private String fromDevice;
	private String extraData;// 定位时使用

	public String getExtraData() {
		return extraData;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}

	public String getBindPass() {
		return bindPass;
	}

	public void setBindPass(String bindPass) {
		this.bindPass = bindPass;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getFromDevice() {
		return fromDevice;
	}

	public void setFromDevice(String fromDevice) {
		this.fromDevice = fromDevice;
	}

}
