package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable {
	private int rec_id;
	private String r_name;
	private String time;
	private String image;
	private boolean wanpan;
	private boolean save_time;
	private boolean microwave_oven;
	private String recipe;
	private int cooking_expenses;
	private int eating_out_expenses;
	private String u_id;
	private Date r_date;
	private int r_count;
	private int i_id;
	private int f_id;
	private List<String> ingredient = new ArrayList<>();
	private Double r_i_count;
	private int unit;	// 0：個、1：g、2：枚、3：少々、4：本
;


	// 2種類のコンストラクタの追加
	public Recipe(int rec_id, String r_name, String time, String image,
			boolean wanpan, boolean save_time, boolean microwave_oven,
			String recipe, int cooking_expenses, int eating_out_expenses,
			String u_id, Date r_date, int r_count, int i_id, int f_id, String ingredient, Double r_i_count, int unit) {
		Date sqlNow = new Date(System.currentTimeMillis()); // util.date => sql.dateへの変換
		java.util.Date utilDate = sqlNow;

		this.rec_id = rec_id;
		this.r_name = r_name;
		this.time = time;
		this.image = image;
		this.wanpan = wanpan;
		this.save_time = save_time;
		this.microwave_oven = microwave_oven;
		this.recipe = recipe;
		this.cooking_expenses = cooking_expenses;
		this.eating_out_expenses = eating_out_expenses;
		this.u_id = u_id;
		this.r_date = new Date(utilDate.getTime());
		this.r_count = r_count;
		this.i_id = i_id;
		this.f_id = f_id;
		this.ingredient.add(ingredient);
		this.r_i_count = r_i_count;
		this.unit = unit;
	}

	public Recipe(String r_name, boolean wanpan, boolean save_time, boolean microwave_oven) {
		this.rec_id = -1;
		this.r_name = r_name;
		this.time = "";
		this.image = "";
		this.wanpan = wanpan;
		this.save_time = save_time;
		this.microwave_oven = microwave_oven;
		this.recipe = "";
		this.cooking_expenses = -1;
		this.eating_out_expenses = -1;
		this.u_id = "";
		this.r_date = null;
		this.r_count = -1;
		this.i_id = -1;
		this.f_id = -1;
		this.ingredient.add("");
		this.r_i_count = -1.0;
		this.unit = 0;
	}
	public Recipe(int rec_id, String u_id, Date r_date) {

		this.rec_id = rec_id;
		this.r_name = "";
		this.time = "";
		this.image = "";
		this.wanpan = false;
		this.save_time = false;
		this.microwave_oven = false;
		this.recipe = "";
		this.cooking_expenses = -1;
		this.eating_out_expenses = -1;
		this.u_id = u_id;
		this.r_date = r_date;
		this.r_count = -1;
		this.i_id = -1;
		this.f_id = -1;
		this.ingredient.add("");
		this.r_i_count = -1.0;
		this.unit = 0;
	}
	public Recipe() {
		Date sqlNow = new Date(System.currentTimeMillis()); // util.date => sql.dateへの変換
		java.util.Date utilDate = sqlNow;

		this.rec_id = -1;
		this.r_name = "";
		this.time = "";
		this.image = "";
		this.wanpan = false;
		this.save_time = false;
		this.microwave_oven = false;
		this.recipe = "";
		this.cooking_expenses = -1;
		this.eating_out_expenses = -1;
		this.u_id = "";
		this.r_date = new Date(utilDate.getTime());
		this.r_count = -1;
		this.i_id = -1;
		this.f_id = -1;
		this.ingredient.add("");
		this.r_i_count = -1.0;
		this.unit = 0;
	}
	//レシピ一覧表示時の空検索用コンストラクタの追加
	public Recipe(int rec_id) {

		this.rec_id = rec_id;
		this.r_name = "";
		this.time = "";
		this.image = "";
		this.wanpan = false;
		this.save_time = false;
		this.microwave_oven = false;
		this.recipe = "";
		this.cooking_expenses = -1;
		this.eating_out_expenses = -1;
		this.u_id = "";
		this.r_date = null;
		this.r_count = -1;
		this.i_id = -1;
		this.f_id = -1;
		this.ingredient.add("");
		this.r_i_count = -1.0;
		this.unit = 0;
	}
	// get, setメソッドの追加
	public int getRec_id() {
		return rec_id;
	}

	public void setRec_id(int rec_id) {
		this.rec_id = rec_id;
	}

	public String getR_name() {
		return r_name;
	}

	public void setR_name(String r_name) {
		this.r_name = r_name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean getWanpan() {
		return wanpan;
	}

	public void setWanpan(boolean wanpan) {
		this.wanpan = wanpan;
	}

	public boolean getSave_time() {
		return save_time;
	}

	public void setSave_time(boolean save_time) {
		this.save_time = save_time;
	}

	public boolean getMicrowave_oven() {
		return microwave_oven;
	}

	public void setMicrowave_oven(boolean microwave_oven) {
		this.microwave_oven = microwave_oven;
	}

	public String getRecipe() {
		return recipe;
	}

	public void setRecipe(String recipe) {
		this.recipe = recipe;
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

	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public Date getR_date() {
		return r_date;
	}

	public void setR_date(Date r_date) {
		this.r_date = r_date;
	}

	public int getR_count() {
		return r_count;
	}

	public void setR_count(int r_count) {
		this.r_count = r_count;
	}

	public int getI_id() {
		return i_id;
	}

	public void setI_id(int i_id) {
		this.i_id = i_id;
	}

	public List<String> getIngredient() {
		return ingredient;
	}

	public void setIngredient(List<String> ingredient) {
		this.ingredient = ingredient;
	}

	public Double getR_i_count() {
		return r_i_count;
	}

	public void setR_i_count(Double r_i_count) {
		this.r_i_count = r_i_count;
	}

	public int getF_id() {
		return f_id;
	}

	public void setF_id(int f_id) {
		this.f_id = f_id;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

}
