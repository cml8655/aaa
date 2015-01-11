package cn.com.cml.dbl.contant;

public interface Constant {

	String COMMAND_SPERATOR = "#";

	String JINBAO = "jb";
	String JINGBAO_RING = "jbring";// 警报响起
	long JINGBAO_RING_EXPIRES = 300000;// 警报响起

	/**
	 * 30s内有效
	 */
	long JINBAO_EXPIRES = 30000;

	String LOCATION_MONITOR = "LOCATION_MONITOR";
	long LOCATION_MONITOR_EXPIRES = 30000;

	// 定位结果返回
	String LOCATION_MONITOR_RESULT = "LOCATION_MONITOR_RESULT";
	long LOCATION_MONITOR_RESULT_EXPIRES = 30000;

	long LOCATION_CLIENT_EXPIRES = 600000;// 用户接收到定位后10分钟内自动定位

	String JINGBAO_STOP = "jbtz";
	/**
	 * 5分钟内有效
	 */
	long JINGBAO_STOP_EXPIRES = 300000;

	public static enum Command {

		JINGBAO_ENUM(JINBAO, JINBAO_EXPIRES), //
		JINGBAO_STOP_ENUM(JINGBAO_STOP, JINGBAO_STOP_EXPIRES), //
		DINGWEI_ENUM(LOCATION_MONITOR, LOCATION_MONITOR_EXPIRES), //
		LOCATION_MONITOR_RESULT_ENUM(LOCATION_MONITOR_RESULT,
				LOCATION_MONITOR_RESULT_EXPIRES), //
		JINGBAO_RING_ENUM(JINGBAO_RING, JINGBAO_RING_EXPIRES);// 警报响起

		private String command;
		private long endTime;
		private String bindPass;
		private String from;
		private String extra;

		private Command(String command, long endTime) {
			this.command = command;
			this.endTime = endTime;
		}

		public static Command getByCommand(String command) {

			Command[] values = Command.values();

			for (Command value : values) {
				if (value.getCommand().equals(command)) {
					return value;
				}
			}

			return null;
		}

		public String getExtra() {
			return extra;
		}

		public void setExtra(String extra) {
			this.extra = extra;
		}

		public String getBindPass() {
			return bindPass;
		}

		public void setBindPass(String bindPass) {
			this.bindPass = bindPass;
		}

		public String getCommand() {
			return command;
		}

		public long getEndTime() {
			return endTime;
		}

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
		}

	}

	interface Checking {
		int CHECKING_ERROR = -123456;
		int BASE_SCORE = 1;
		int BASE_SERIES = 0;
		int MAX_SERIES = 4;
		int YESTERDAY = 1;
	}

	interface Alarm {
		int TYPE_COMMAND = 1001;
		int TYPE_SMS = 1002;
	}

	interface Url {
		String URL_ABOUT_US = "file:///android_asset/html_files/about_us.html";
		String URL_AGREEMENT = "file:///android_asset/html_files/agreement.html";
		String URL_HTLP = "file:///android_asset/html_files/help.html";
	}

	interface Gloable {
		int REMOTE_PASS_QUERY_SCORE = 15;// 远程密码查询积分
	}
}
