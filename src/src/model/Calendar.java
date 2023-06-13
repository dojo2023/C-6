package model;

import java.io.Serializable;
import java.sql.Date;

public class Calendar implements Serializable {
	private String u_id;
	private int rec_id;
	private Date date;
	private int c_count;

	// 2種類のコンストラクタの追加
	public Calendar(String u_id, int rec_id, int c_count) {
		Date sqlNow = new Date(System.currentTimeMillis()); // util.date => sql.dateへの変換
		java.util.Date utilDate = sqlNow;

		this.u_id = u_id;
		this.rec_id = rec_id;
		this.date = new Date(utilDate.getTime());
		this.c_count = c_count;
	}

	public Calendar() {
		Date sqlNow = new Date(System.currentTimeMillis()); // util.date => sql.dateへの変換
		java.util.Date utilDate = sqlNow;

		this.u_id = "";
		this.rec_id = -1;
		this.date = new Date(utilDate.getTime());
		this.c_count = -1;
	}

	// get, setメソッドの追加
	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public int getRec_id() {
		return rec_id;
	}

	public void setRec_id(int rec_id) {
		this.rec_id = rec_id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getC_count() {
		return c_count;
	}

	public void setC_count(int c_count) {
		this.c_count = c_count;
	}
}
