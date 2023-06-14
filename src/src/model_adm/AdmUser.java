package model_adm;

import java.io.Serializable;

public class AdmUser implements Serializable{
	// Userを元に追加
	private String u_id;
	private String password;
	private int position = 2; // 管理者：2

	// コンストラクタの追加
	public AdmUser(String u_id, String password) {
		this.u_id = u_id;
		this.password = password;
	}

	// get, setメソッドの追加
	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}
