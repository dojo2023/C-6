package model;

// 以下1行の追加
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//「implements Serializable」の追加
public class Refrigerator implements Serializable{
	// DC定義書から追加
	private int ref_id;
	private String u_id;
	private int f_id;
	private double f_count;
	private List<String> text = new ArrayList<>();
	private List<Double> num = new ArrayList<>();

	// 2種類のコンストラクタの追加
	public Refrigerator(int ref_id, String u_id, int f_id, double f_count, List<String> text, List<Double> num) {
		this.ref_id = ref_id;
		this.u_id = u_id;
		this.f_id = f_id;
		this.f_count = f_count;
		this.text = text;
		this.num = num;
	}

	public Refrigerator() {
		this.ref_id = -1;
		this.u_id = "";
		this.f_id = -1;
		this.f_count = -1;
		this.text.add ("");
		this.num.add((double)-1);
	}

	public Refrigerator(String u_id) {
		this.ref_id = -1;
		this.u_id = u_id;
		this.f_id = -1;
		this.f_count = -1;
		this.text.add("");
		this.num.add((double)-1);
	}

	public Refrigerator(int f_id) {
		this.ref_id = -1;
		this.u_id = "";
		this.f_id = f_id;
		this.f_count = -1;
		this.text.add("");
		this.num.add((double)-1);
	}

	// get, setメソッドの追加

	public int getRef_id() {
		return ref_id;
	}

	public void setRef_id(int ref_id) {
		this.ref_id = ref_id;
	}

	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public int getF_id() {
		return f_id;
	}

	public void setF_id(int f_id) {
		this.f_id = f_id;
	}

	public double getF_count() {
		return f_count;
	}

	public void setF_count(double f_count) {
		this.f_count = f_count;
	}

	public List<String> getText() {
		return text;
	}

	public void setText(List<String> text) {
		this.text = text;
	}

	public List<Double> getNum() {
		return num;
	}

	public void setNum(List<Double> num) {
		this.num = num;
	}

}