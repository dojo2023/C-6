package model;

import java.io.Serializable;
import java.sql.Date;

public class Calendar implements Serializable {
	private String u_id;
	private int rec_id;
	private Date date;
	private int c_count;
	private String r_name;
	private int cooking_expenses;
	private int eating_out_expenses;

	// 2種類のコンストラクタの追加
	public Calendar(String u_id, int rec_id, int c_count, String r_name, int cooking_expenses, int eating_out_expenses) {
		Date sqlNow = new Date(System.currentTimeMillis()); // util.date => sql.dateへの変換
		java.util.Date utilDate = sqlNow;

		this.u_id = u_id;
		this.rec_id = rec_id;
		this.date = new Date(utilDate.getTime());	// YYYY-MM-DD
		this.c_count = c_count;
		this.r_name = r_name;
		this.cooking_expenses = cooking_expenses;
		this.eating_out_expenses = eating_out_expenses;
	}

	public Calendar(String u_id, int rec_id, Date date, int c_count, String r_name, int cooking_expenses, int eating_out_expenses) {
		this.u_id = u_id;
		this.rec_id = rec_id;
		this.date = date;
		this.c_count = c_count;
		this.r_name = r_name;
		this.cooking_expenses = cooking_expenses;
		this.eating_out_expenses = eating_out_expenses;
	}

	public Calendar(String u_id, Date date) {
		this.u_id = u_id;
		this.rec_id = -1;
		this.date = date;
		this.c_count = -1;
		this.r_name = "";
		this.cooking_expenses = -1;
		this.eating_out_expenses = -1;
	}

	public Calendar(String u_id) {
		this.u_id = u_id;
		this.rec_id = -1;
		this.date = null;
		this.c_count = -1;
		this.r_name = "";
		this.cooking_expenses = -1;
		this.eating_out_expenses = -1;
	}

	public Calendar() {
		Date sqlNow = new Date(System.currentTimeMillis()); // util.date => sql.dateへの変換
		java.util.Date utilDate = sqlNow;

		this.u_id = "";
		this.rec_id = -1;
		this.date = new Date(utilDate.getTime());
		this.c_count = -1;
		this.r_name = "";
		this.cooking_expenses = -1;
		this.eating_out_expenses = -1;
	}

	public Calendar(String u_id, int rec_id, Date date, int c_count) {
		this.u_id = u_id;
		this.rec_id = rec_id;
		this.date = date;
		this.c_count = c_count;
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

	public String getR_name() {
		return r_name;
	}

	public void setR_name(String r_name) {
		this.r_name = r_name;
	}

	public int getCooking_expenses() {
		return cooking_expenses;
	}

	public void setCooking_expenses(int cooking_expenses) {
		this.cooking_expenses = cooking_expenses;
	}

	public int getEating_out_expenses() {
		return eating_out_expenses;
	}

	public void setEating_out_expenses(int eating_out_expenses) {
		this.eating_out_expenses = eating_out_expenses;
	}
}
