package cn.com.cml.pets.model;

import android.net.Uri;
import android.provider.BaseColumns;

public class SmsModel implements BaseColumns {

	public static final Uri SMS_CONTENT_URI = Uri.parse("content://sms");

	public static final String DATE = "date";
	public static final String BODY = "body";

	private String body;
	private String date;
	private int id;

	public SmsModel() {
	}

	public SmsModel(String body, String date) {
		super();
		this.body = body;
		this.date = date;
	}

	public SmsModel(String body, int id) {
		super();
		this.body = body;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
