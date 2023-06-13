package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainFoods implements Serializable{
	// Userを元に追加
	private List<MainFood>mainfoods = new ArrayList<MainFood>();

	// コンストラクタの追加
	public MainFoods(MainFood mainfood) {
		this.mainfoods.add(mainfood);
	}

	// get, setメソッドの追加
	public List<MainFood> getMainFoods() {
		return mainfoods;
	}

	public void setUsers(List<MainFood> mainfood) {
		this.mainfoods = mainfood;
	}
}
