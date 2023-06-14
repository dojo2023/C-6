package model_adm;

//以下2行の追加
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//「implements Serializable」の追加
public class AdmUsers implements Serializable{
	// Userを元に追加
	private List<AdmUser> admUsers = new ArrayList<AdmUser>();

	// コンストラクタの追加
	public AdmUsers(AdmUser admUser) {
		this.admUsers.add(admUser);
	}

	// get, setメソッドの追加
	public List<AdmUser> getUsers() {
		return admUsers;
	}

	public void setUsers(List<AdmUser> admUser) {
		this.admUsers = admUser;
	}
}
