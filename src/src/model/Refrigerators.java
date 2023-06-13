 package model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//「implements Serializable」の追加
public class Refrigerators implements Serializable{

	// Refrigeratorを元に追加
	private List<Refrigerator>refrigerators = new ArrayList<Refrigerator>();

	// コンストラクタの追加
	public Refrigerators(Refrigerator refrigerator) {
		this.refrigerators.add(refrigerator);
	}

	public List<Refrigerator> getRefrigerators() {
		return refrigerators;
	}

	public void setRefrigerators(List<Refrigerator> refrigerator) {
		this.refrigerators = refrigerator;
	}

}

