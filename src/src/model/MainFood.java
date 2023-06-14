package model;

import java.io.Serializable;

//「implements Serializable」の追加
public class MainFood implements Serializable{
	// DC定義書から追加
	private int f_id ;
	private String f_name;
	private String image;
	private String identify;
	private String strage_method;
	private String retention_period;
	private String season;

	// 2種類のコンストラクタの追加
	public MainFood(int f_id, String f_name, String image , String identify , String strage_method, String retention_period, String season) {
		this.f_id = f_id;
		this.f_name = f_name;
		this.image = image;
		this.identify = identify;
		this.strage_method= strage_method;
		this.retention_period = retention_period;
		this.season = season;
	}
	public MainFood() {
		this.f_id = -1;
		this.f_name = "";
		this.image = "";
		this.identify = "";
		this.strage_method= "";
		this.retention_period = "";
		this.season = "";
	}
	public MainFood(int f_id) {
		this.f_id = f_id;
		this.f_name = "";
		this.image = "";
		this.identify = "";
		this.strage_method= "";
		this.retention_period = "";
		this.season = "";
	}

	public int getF_id() {
		return f_id;
	}
	public void setF_id(int f_id) {
		this.f_id = f_id;
	}
	public String getF_name() {
		return f_name;
	}
	public void setF_name(String f_name) {
		this.f_name = f_name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	public String getStrage_method() {
		return strage_method;
	}
	public void setStrage_method(String strage_method) {
		this.strage_method = strage_method;
	}
	public String getRetention_period() {
		return retention_period;
	}
	public void setRetention_period(String retention_period) {
		this.retention_period = retention_period;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}

}