package model;

//以下2行の追加
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//「implements Serializable」の追加
public class Users implements Serializable{
	// Userを元に追加
	private List<User>users = new ArrayList<User>();

	// コンストラクタの追加
	public Users(User user) {
		this.users.add(user);
	}

	// get, setメソッドの追加
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> user) {
		this.users = user;
	}
}
