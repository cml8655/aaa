package cn.com.cml.dbl.contant;

public interface Constant {

	String JINBAO = "jb";
	/**
	 * 30s内有效
	 */
	long JINBAO_EXPIRES = 30000;

	String DINGWEI = "dw";
	long DINGWEI_EXPIRES = 30000;

	String JINGBAO_STOP = "jbtz";
	/**
	 * 5分钟内有效
	 */
	long JINGBAO_STOP_EXPIRES = 300000;

	enum Command {

		JINGBAO_ENUM(JINBAO, JINBAO_EXPIRES), JINGBAO_STOP_ENUM(JINGBAO_STOP,
				JINGBAO_STOP_EXPIRES), DINGWEI_ENUM(DINGWEI, DINGWEI_EXPIRES);

		private String command;
		private long endTime;

		private Command(String command, long endTime) {
			this.command = command;
			this.endTime = endTime;
		}

		public String getCommand() {
			return command;
		}

		public long getEndTime() {
			return endTime;
		}

	}

}
