package model;

// 以下3行の追加
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//「implements Serializable」の追加
public class User implements Serializable{
	// DC定義書から追加
	private String u_id;
	private String password;
	private int position = 1; // 一般ユーザー：1
	private List<Integer> lf_id = new ArrayList<>();
	private List<Integer> df_id = new ArrayList<>();

	// 4種類のコンストラクタの追加
	public User(String u_id, String password, int position, int lf_id, int df_id) {
		this.u_id = u_id;
		this.password = password;
		this.lf_id.add(lf_id);
		this.df_id.add(df_id);
	}

	public User(String u_id, String password, List<Integer> lf_id, List<Integer> df_id) {
		this.u_id = u_id;
		this.password = password;
		this.lf_id = lf_id;
		this.df_id = df_id;
	}

	public User(String u_id, String password, int lf_id, int df_id) {
		this.u_id = u_id;
		this.password = password;
		this.lf_id.add(lf_id);
		this.df_id.add(df_id);
	}

	public User(String u_id, String password) {
		this.u_id = u_id;
		this.password = password;
	}

	public User() {
		this.u_id = "";
		this.password = "";
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

//	public void setPosition(int position) {
//		this.position = position;
//	}

	public List<Integer> getLf_id() {
		return lf_id;
	}

	public void setLf_id(List<Integer> lf_id) {
		this.lf_id = lf_id;
	}

	public List<Integer> getDf_id() {
		return df_id;
	}

	public void setDf_id(List<Integer> df_id) {
		this.df_id = df_id;
	}
}
