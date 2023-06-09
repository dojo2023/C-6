package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Recipe;
import model.Recipes;

public class RecipeDAO {
	// 引数paramで検索項目を指定し、検索結果のリストを返す
	public List<Recipe> select(Recipes recipes) {
		Connection conn = null;
		List<Recipe> cardList = new ArrayList<Recipe>();
		Recipe param = new Recipe();
//		System.out.println("Rec_id:"+recipes.getRecipes().get(0).getR_date()==null);
		if (recipes.getRecipes().size() > 0) {
			param = recipes.getRecipes().get(0);
		}

		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

			// SQL文を準備する
			String sql = "select r.rec_id, r.r_name, r.time, r.image, r.wanpan, r.save_time, r.microwave_oven, "
					+ "r.recipe, r.cooking_expenses, r.eating_out_expenses, r_c.u_id, r_c.r_date, r_c.r_count, "
					+ "r_i.i_id, r_i.f_id, r_i.ingredient, r_i.r_i_count, r_i.unit "
					+ "from recipes as r "
					+ "left join recipe_ingredients as r_i on r.rec_id = r_i.rec_id "
					+ "left join recipe_counts as r_c on r.rec_id = r_c.rec_id "
					+ "where r.rec_id like ? and r.r_name like ? and r_i.i_id like ? and r_i.f_id like ? ";

			// f_name ver.を作る
			for (String i : param.getIngredient()) {
				if (i != null || i != "") {
					// "" で囲う必要ないかも
					sql = sql + "and r_i.ingredient like \'%" + i + "%\' ";
				} else {
					break;
				}
			}

					sql += "and r_i.r_i_count like ? and r_i.unit like ? and r.time like ? and r.wanpan like ? and "
					+ "r.save_time like ? and r.microwave_oven like ? and r.recipe like ? and r.cooking_expenses like ? "
					+ "and r.eating_out_expenses like ? and (CASE WHEN r_c.u_id IS NULL THEN '' ELSE r_c.u_id END) "
					+ "like ? and (CASE WHEN r_c.r_count IS NULL THEN -1 ELSE r_c.r_count END) like ?";
			// 条件式がu_idとrec_idのみなので、他の検索条件の追加
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			if (param.getRec_id() != -1) {
				pStmt.setInt(1, param.getRec_id());
			} else {
				pStmt.setString(1, "%");
			}
			if (param.getR_name() != null && !param.getR_name().equals("")) {
				pStmt.setString(2, "%" + param.getR_name() + "%");
			} else {
				pStmt.setString(2, "%");
			}
			if (param.getI_id() != -1) {
				pStmt.setString(3, "%" + param.getI_id() + "%");
			} else {
				pStmt.setString(3, "%");
			}
//			System.out.println("F_id:"+param.getF_id());
			if (param.getF_id() != -1) {
				pStmt.setString(4, "%" + param.getF_id() + "%");
			} else {
				pStmt.setString(4, "%");
			}
			if (param.getR_i_count() != null && param.getR_i_count().get(0) != -1.0) {
				pStmt.setString(5, "%" + param.getR_i_count() + "%");
			} else {
				pStmt.setString(5, "%");
			}
			if (param.getUnit() != null && param.getUnit().get(0) != -1) {
				pStmt.setString(6, "%");
			} else {
				pStmt.setString(6, "%");
			}
			if (param.getTime() != null && !param.getTime().equals("")) {
				pStmt.setString(7, "%" + param.getTime() + "%");
			} else {
				pStmt.setString(7, "%");
			}
//			pStmt.setBoolean(8, param.getWanpan());
//			pStmt.setBoolean(9, param.getSave_time());
//			pStmt.setBoolean(10, param.getMicrowave_oven());
			pStmt.setString(8, "%");
			pStmt.setString(9, "%");
			pStmt.setString(10, "%");
			if (param.getRecipe() != null && !param.getRecipe().equals("")) {
				pStmt.setString(11, "%" + param.getRecipe() + "%");
			} else {
				pStmt.setString(11, "%");
			}
			if (param.getCooking_expenses() != -1) {
				pStmt.setString(12, "%" + param.getCooking_expenses() + "%");
			} else {
				pStmt.setString(12, "%");
			}
			if (param.getEating_out_expenses() != -1) {
				pStmt.setString(13, "%" + param.getEating_out_expenses() + "%");
			} else {
				pStmt.setString(13, "%");
			}
			if (param.getU_id() != null && !param.getU_id().equals("")) {
				pStmt.setString(14, "%" + param.getU_id() + "%");
			} else {
				pStmt.setString(14, "%");
			}
			if (param.getR_count() != -1.0) {
				pStmt.setString(15, "%" + param.getR_count() + "%");
			} else {
				pStmt.setString(15, "%");
			}

			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();

			// 結果表をコレクションにコピーする
			int rec_id =-111;
			int num = -1;
			while (rs.next()) {
				if (rec_id != rs.getInt("recipes.rec_id")) {
					rec_id = rs.getInt("recipes.rec_id");
					Recipe card = new Recipe(
							rs.getInt("recipes.rec_id"),
							rs.getString("recipes.r_name"),
							rs.getString("recipes.time"),
							rs.getString("recipes.image"),
							rs.getBoolean("recipes.wanpan"),
							rs.getBoolean("recipes.save_time"),
							rs.getBoolean("recipes.microwave_oven"),
							rs.getString("recipes.recipe"),
							rs.getInt("recipes.cooking_expenses"),
							rs.getInt("recipes.eating_out_expenses"),
							rs.getString("recipe_counts.u_id"),
							rs.getDate("recipe_counts.r_date"),
							rs.getInt("recipe_counts.r_count"),
							rs.getInt("recipe_ingredients.i_id"),
							rs.getInt("recipe_ingredients.f_id"),
							rs.getString("recipe_ingredients.ingredient"),
							rs.getDouble("recipe_ingredients.r_i_count"),
							rs.getInt("recipe_ingredients.unit"));
					num++;
					cardList.add(card);
				} else {
					List<String> ing = cardList.get(num).getIngredient();
					List<Double> r_i_count = cardList.get(num).getR_i_count();
					List<Integer> unit = cardList.get(num).getUnit();
					ing.add(rs.getString("recipe_ingredients.ingredient"));
					r_i_count.add(rs.getDouble("recipe_ingredients.r_i_count"));
					unit.add(rs.getInt("recipe_ingredients.unit"));
					cardList.get(num).setIngredient(ing);
					cardList.get(num).setR_i_count(r_i_count);
					cardList.get(num).setUnit(unit);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			cardList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			cardList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					cardList = null;
				}
			}
		}

		// 結果を返す
		return cardList;
	}

	public List<Recipe> selectF_id(Recipes recipes) {
		Connection conn = null;
		List<Recipe> cardList = new ArrayList<Recipe>();
		Recipe param = new Recipe();
//		System.out.println("Rec_id:"+recipes.getRecipes().get(0).getR_date()==null);
		if (recipes.getRecipes().size() > 0) {
			param = recipes.getRecipes().get(0);
		}

		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

			// SQL文を準備する
			String sql = "select r.rec_id, r.r_name, r.time, r.image, r.wanpan, r.save_time, r.microwave_oven, "
					+ "r.recipe, r.cooking_expenses, r.eating_out_expenses, r_c.u_id, r_c.r_date, r_c.r_count, "
					+ "r_i.i_id, r_i.f_id, r_i.ingredient, r_i.r_i_count, r_i.unit "
					+ "from recipes as r "
					+ "left join recipe_ingredients as r_i on r.rec_id = r_i.rec_id "
					+ "left join recipe_counts as r_c on r.rec_id = r_c.rec_id "
					+ "where r.rec_id like ? and r.r_name like ? and r_i.i_id like ? and r_i.f_id = ? ";

			// f_name ver.を作る
			for (String i : param.getIngredient()) {
				if (i != null || i != "") {
					// "" で囲う必要ないかも
					sql = sql + "and r_i.ingredient like \'%" + i + "%\' ";
				} else {
					break;
				}
			}

					sql += "and r_i.r_i_count like ? and r_i.unit like ? and r.time like ? and r.wanpan like ? and "
					+ "r.save_time like ? and r.microwave_oven like ? and r.recipe like ? and r.cooking_expenses like ? "
					+ "and r.eating_out_expenses like ? and (CASE WHEN r_c.u_id IS NULL THEN '' ELSE r_c.u_id END) like ? and (CASE WHEN r_c.r_count IS NULL THEN -1 ELSE r_c.r_count END) like ?";
			// 条件式がu_idとrec_idのみなので、他の検索条件の追加
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			if (param.getRec_id() != -1) {
				pStmt.setInt(1, param.getRec_id());
			} else {
				pStmt.setString(1, "%");
			}
			if (param.getR_name() != null && !param.getR_name().equals("")) {
				pStmt.setString(2, "%" + param.getR_name() + "%");
			} else {
				pStmt.setString(2, "%");
			}
			if (param.getI_id() != -1) {
				pStmt.setString(3, "%" + param.getI_id() + "%");
			} else {
				pStmt.setString(3, "%");
			}
//			System.out.println("F_id:"+param.getF_id());
			if (param.getF_id() != -1) {
				pStmt.setInt(4, param.getF_id());
			} else {
				pStmt.setString(4, "");
			}
			if (param.getR_i_count() != null && param.getR_i_count().get(0) != -1.0) {
				pStmt.setString(5, "%" + param.getR_i_count() + "%");
			} else {
				pStmt.setString(5, "%");
			}
			if (param.getUnit() != null && param.getUnit().get(0) != -1) {
				pStmt.setString(6, "%");
			} else {
				pStmt.setString(6, "%");
			}
			if (param.getTime() != null && !param.getTime().equals("")) {
				pStmt.setString(7, "%" + param.getTime() + "%");
			} else {
				pStmt.setString(7, "%");
			}
//			pStmt.setBoolean(8, param.getWanpan());
//			pStmt.setBoolean(9, param.getSave_time());
//			pStmt.setBoolean(10, param.getMicrowave_oven());
			pStmt.setString(8, "%");
			pStmt.setString(9, "%");
			pStmt.setString(10, "%");
			if (param.getRecipe() != null && !param.getRecipe().equals("")) {
				pStmt.setString(11, "%" + param.getRecipe() + "%");
			} else {
				pStmt.setString(11, "%");
			}
			if (param.getCooking_expenses() != -1) {
				pStmt.setString(12, "%" + param.getCooking_expenses() + "%");
			} else {
				pStmt.setString(12, "%");
			}
			if (param.getEating_out_expenses() != -1) {
				pStmt.setString(13, "%" + param.getEating_out_expenses() + "%");
			} else {
				pStmt.setString(13, "%");
			}
			if (param.getU_id() != null && !param.getU_id().equals("")) {
				pStmt.setString(14, "%" + param.getU_id() + "%");
			} else {
				pStmt.setString(14, "%");
			}
			if (param.getR_count() != -1.0) {
				pStmt.setString(15, "%" + param.getR_count() + "%");
			} else {
				pStmt.setString(15, "%");
			}

			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();

			// 結果表をコレクションにコピーする
			int rec_id =-111;
			int num = 0;
			while (rs.next()) {
				if (rec_id != rs.getInt("recipes.rec_id")) {
					rec_id = rs.getInt("recipes.rec_id");
					Recipe card = new Recipe(
							rs.getInt("recipes.rec_id"),
							rs.getString("recipes.r_name"),
							rs.getString("recipes.time"),
							rs.getString("recipes.image"),
							rs.getBoolean("recipes.wanpan"),
							rs.getBoolean("recipes.save_time"),
							rs.getBoolean("recipes.microwave_oven"),
							rs.getString("recipes.recipe"),
							rs.getInt("recipes.cooking_expenses"),
							rs.getInt("recipes.eating_out_expenses"),
							rs.getString("recipe_counts.u_id"),
							rs.getDate("recipe_counts.r_date"),
							rs.getInt("recipe_counts.r_count"),
							rs.getInt("recipe_ingredients.i_id"),
							rs.getInt("recipe_ingredients.f_id"),
							rs.getString("recipe_ingredients.ingredient"),
							rs.getDouble("recipe_ingredients.r_i_count"),
							rs.getInt("recipe_ingredients.unit"));
					cardList.add(card);
				} else {
					List<String> ing = cardList.get(num).getIngredient();
					List<Double> r_i_count = cardList.get(num).getR_i_count();
					List<Integer> unit = cardList.get(num).getUnit();
					ing.add(rs.getString("recipe_ingredients.ingredient"));
					r_i_count.add(rs.getDouble("recipe_ingredients.r_i_count"));
					unit.add(rs.getInt("recipe_ingredients.unit"));
					cardList.get(num).setIngredient(ing);
					cardList.get(num).setR_i_count(r_i_count);
					cardList.get(num).setUnit(unit);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			cardList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			cardList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					cardList = null;
				}
			}
		}

		// 結果を返す
		return cardList;
	}

	public List<Recipe> selectDate(Recipes recipes) {
		Connection conn = null;
		List<Recipe> cardList = new ArrayList<Recipe>();
		Recipe param = new Recipe();
		if (recipes.getRecipes().get(0).getRec_id() != -1) {
			param = recipes.getRecipes().get(0);
		}

		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

			// SQL文を準備する
			String sql = "select r.rec_id, r.r_name, r.time, r.image, r.wanpan, r.save_time, r.microwave_oven, "
					+ "r.recipe, r.cooking_expenses, r.eating_out_expenses, r_c.u_id, r_c.r_date, r_c.r_count, "
					+ "r_i.i_id, r_i.f_id, r_i.ingredient, r_i.r_i_count, r_i.unit "
					+ " from recipes as r "
					+ "left join recipe_ingredients as r_i on r.rec_id = r_i.rec_id "
					+ "left join recipe_counts as r_c on r.rec_id = r_c.rec_id "
					+ "where r.rec_id like ? "
					+ "and (CASE WHEN r_c.u_id IS NULL THEN '' ELSE r_c.u_id END) like ? "
					+ "and r_c.r_date like ?";
			// 条件式がu_idとrec_idのみなので、他の検索条件の追加
			PreparedStatement pStmt = conn.prepareStatement(sql);

//			System.out.println(param.getU_id());
//			System.out.println(param.getRec_id());
//			System.out.println(param.getR_date());

			// SQL文を完成させる
			if (param.getRec_id() != -1) {
				pStmt.setString(1, "%" + param.getRec_id() + "%");
			} else {
				pStmt.setString(1, "%");
			}
			if (param.getU_id() != null && !param.getU_id().equals("")) {
				pStmt.setString(2, "%" + param.getU_id() + "%");
			} else {
				pStmt.setString(2, "%");
			}
			if (param.getR_date() != null) {
				pStmt.setDate(3, param.getR_date());
			} else {
				pStmt.setString(3, "%");
			}

			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();

			// 結果表をコレクションにコピーする
			int rec_id =-111;
			int num = 0;
			while (rs.next()) {
				if (rec_id != rs.getInt("recipes.rec_id")) {
					rec_id = rs.getInt("recipes.rec_id");
					Recipe card = new Recipe(
							rs.getInt("recipes.rec_id"),
							rs.getString("recipes.r_name"),
							rs.getString("recipes.time"),
							rs.getString("recipes.image"),
							rs.getBoolean("recipes.wanpan"),
							rs.getBoolean("recipes.save_time"),
							rs.getBoolean("recipes.microwave_oven"),
							rs.getString("recipes.recipe"),
							rs.getInt("recipes.cooking_expenses"),
							rs.getInt("recipes.eating_out_expenses"),
							rs.getString("recipe_counts.u_id"),
							rs.getDate("recipe_counts.r_date"),
							rs.getInt("recipe_counts.r_count"),
							rs.getInt("recipe_ingredients.i_id"),
							rs.getInt("recipe_ingredients.f_id"),
							rs.getString("recipe_ingredients.ingredient"),
							rs.getDouble("recipe_ingredients.r_i_count"),
							rs.getInt("recipe_ingredients.unit"));
					cardList.add(card);
				} else {
					List<String> ing = cardList.get(num).getIngredient();
					List<Double> r_i_count = cardList.get(num).getR_i_count();
					List<Integer> unit = cardList.get(num).getUnit();
					ing.add(rs.getString("recipe_ingredients.ingredient"));
					r_i_count.add(rs.getDouble("recipe_ingredients.r_i_count"));
					unit.add(rs.getInt("recipe_ingredients.unit"));
					cardList.get(num).setIngredient(ing);
					cardList.get(num).setR_i_count(r_i_count);
					cardList.get(num).setUnit(unit);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			cardList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			cardList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					cardList = null;
				}
			}
		}

		// 結果を返す
		return cardList;
	}

	public List<Recipe> selectRecipe(Recipes recipes) {
		Connection conn = null;
		List<Recipe> cardList = new ArrayList<Recipe>();
		Recipe param = recipes.getRecipes().get(0);

		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

			// SQL文を準備する
			String sql = "select rec_id, r_name, time, image, wanpan, save_time, microwave_oven, "
					+ "recipe, cooking_expenses, eating_out_expenses "
					+ "from recipes "
					+ "where rec_id like ? or r_name like ? or time like ? or wanpan like ? or "
					+ "save_time like ? or microwave_oven like ? or recipe like ? or cooking_expenses like ? "
					+ "or eating_out_expenses like ? or i_id like ?";
			// 条件式がu_idとrec_idのみなので、他の検索条件の追加
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			if (param.getRec_id() != -1) {
				pStmt.setString(1, "%" + param.getRec_id() + "%");
			} else {
				pStmt.setString(1, "%");
			}
			if (param.getR_name() != null && !param.getR_name().equals("")) {
				pStmt.setString(2, "%" + param.getR_name() + "%");
			} else {
				pStmt.setString(2, "%");
			}
			if (param.getTime() != null && !param.getTime().equals("")) {
				pStmt.setString(3, "%" + param.getTime() + "%");
			} else {
				pStmt.setString(3, "%");
			}
			pStmt.setBoolean(4, param.getWanpan());
			pStmt.setBoolean(5, param.getSave_time());
			pStmt.setBoolean(6, param.getMicrowave_oven());
			if (param.getRecipe() != null && !param.getRecipe().equals("")) {
				pStmt.setString(7, "%" + param.getRecipe());
			} else {
				pStmt.setString(7, "%");
			}
			if (param.getCooking_expenses() != -1) {
				pStmt.setString(8, "%" + param.getCooking_expenses() + "%");
			} else {
				pStmt.setString(8, "%");
			}
			if (param.getEating_out_expenses() != -1) {
				pStmt.setString(9, "%" + param.getEating_out_expenses() + "%");
			} else {
				pStmt.setString(9, "%");
			}
			if (param.getI_id() != -1) {
				pStmt.setString(10, "%" + param.getI_id() + "%");
			} else {
				pStmt.setString(10, "%");
			}

			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();

			// 結果表をコレクションにコピーする
			while (rs.next()) {
				Recipe card = new Recipe(
						rs.getInt("recipes.rec_id"),
						rs.getString("recipes.r_name"),
						rs.getString("recipes.time"),
						rs.getString("recipes.image"),
						rs.getBoolean("recipes.wanpan"),
						rs.getBoolean("recipes.save_time"),
						rs.getBoolean("recipes.microwave_oven"),
						rs.getString("recipes.recipe"),
						rs.getInt("recipes.cooking_expenses"),
						rs.getInt("recipes.eating_out_expenses"),
						rs.getString("recipe_counts.u_id"),
						rs.getDate("recipe_counts.r_date"),
						rs.getInt("recipe_counts.r_count"),
						rs.getInt("recipe_ingredients.i_id"),
						rs.getInt("recipe_ingredients.f_id"),
						rs.getString("recipe_ingredients.ingredient"),
						rs.getDouble("recipe_ingredients.r_i_count"),
						rs.getInt("recipe_ingredients.unit"));
				cardList.add(card);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			cardList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			cardList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					cardList = null;
				}
			}
		}

		// 結果を返す
		return cardList;
	}

	public List<Recipe> selectRecipeIngredient(Recipes recipes) {
		Connection conn = null;
		List<Recipe> cardList = new ArrayList<Recipe>();
		Recipe param = recipes.getRecipes().get(0);

		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

			// SQL文を準備する
			String sql = "select * from recipe_ingredients where i_id like ? and rec_id like ? and f_id like ?";


					// 条件式がu_idとrec_idのみなので、他の検索条件の追加
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			for (Recipe recipe : recipes.getRecipes()) {
				if (recipe.getI_id() != -1) {
					pStmt.setString(1, "%" + recipe.getI_id() + "%");
				} else {
					pStmt.setString(1, "%");
				}
				if (recipe.getRec_id() != -1) {
					pStmt.setString(2, "%" + recipe.getRec_id() + "%");
				} else {
					pStmt.setString(2, "%");
				}
				if (recipe.getF_id() != -1) {
					pStmt.setInt(3, recipe.getF_id());
				} else {
					pStmt.setString(3, "%");
				}

				// SQL文を実行し、結果表を取得する
				ResultSet rs = pStmt.executeQuery();

				// 結果表をコレクションにコピーする
				while (rs.next()) {
					Recipe card = new Recipe(
							rs.getInt("recipe_ingredients.rec_id"),
							"",
							"",
							"",
							false,
							false,
							false,
							"",
							-1,
							-1,
							"",
							null,
							-1,
							rs.getInt("recipe_ingredients.i_id"),
							rs.getInt("recipe_ingredients.f_id"),
							rs.getString("recipe_ingredients.ingredient"),
							rs.getDouble("recipe_ingredients.r_i_count"),
							rs.getInt("recipe_ingredients.unit"));
					cardList.add(card);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			cardList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			cardList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					cardList = null;
				}
			}
		}

		// 結果を返す
		return cardList;
	}

	// 引数paramで検索項目を指定し、検索結果のリストを返す
	public List<Recipe> selectWanpan(Recipes recipes) {
		Connection conn = null;
		List<Recipe> cardList = new ArrayList<Recipe>();
		Recipe param = new Recipe();
		if (recipes.getRecipes().size() > 0) {
			param = recipes.getRecipes().get(0);
		}

		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

			// SQL文を準備する
			String sql = "select r.rec_id, r.r_name, r.time, r.image, r.wanpan, r.save_time, r.microwave_oven, "
					+ "r.recipe, r.cooking_expenses, r.eating_out_expenses, r_c.u_id, r_c.r_date, r_c.r_count, "
					+ "r_i.i_id, r_i.f_id, r_i.ingredient, r_i.r_i_count, r_i.unit "
					+ "from recipes as r "
					+ "left join recipe_ingredients as r_i on r.rec_id = r_i.rec_id "
					+ "left join recipe_counts as r_c on r.rec_id = r_c.rec_id "
					+ "where r.wanpan like ?";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
				pStmt.setBoolean(1, param.getWanpan());

			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();

			// 結果表をコレクションにコピーする
			int rec_id =-111;
			int num = 0;
			while (rs.next()) {
				if (rec_id != rs.getInt("recipes.rec_id")) {
					rec_id = rs.getInt("recipes.rec_id");
					Recipe card = new Recipe(
							rs.getInt("recipes.rec_id"),
							rs.getString("recipes.r_name"),
							rs.getString("recipes.time"),
							rs.getString("recipes.image"),
							rs.getBoolean("recipes.wanpan"),
							rs.getBoolean("recipes.save_time"),
							rs.getBoolean("recipes.microwave_oven"),
							rs.getString("recipes.recipe"),
							rs.getInt("recipes.cooking_expenses"),
							rs.getInt("recipes.eating_out_expenses"),
							rs.getString("recipe_counts.u_id"),
							rs.getDate("recipe_counts.r_date"),
							rs.getInt("recipe_counts.r_count"),
							rs.getInt("recipe_ingredients.i_id"),
							rs.getInt("recipe_ingredients.f_id"),
							rs.getString("recipe_ingredients.ingredient"),
							rs.getDouble("recipe_ingredients.r_i_count"),
							rs.getInt("recipe_ingredients.unit"));
					cardList.add(card);
				} else {
					List<String> ing = cardList.get(num).getIngredient();
					List<Double> r_i_count = cardList.get(num).getR_i_count();
					List<Integer> unit = cardList.get(num).getUnit();
					ing.add(rs.getString("recipe_ingredients.ingredient"));
					r_i_count.add(rs.getDouble("recipe_ingredients.r_i_count"));
					unit.add(rs.getInt("recipe_ingredients.unit"));
					cardList.get(num).setIngredient(ing);
					cardList.get(num).setR_i_count(r_i_count);
					cardList.get(num).setUnit(unit);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			cardList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			cardList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					cardList = null;
				}
			}
		}
		// 結果を返す
		return cardList;
	}

	// 引数paramで検索項目を指定し、検索結果のリストを返す
	public List<Recipe> selectSave_time(Recipes recipes) {
		Connection conn = null;
		List<Recipe> cardList = new ArrayList<Recipe>();
		Recipe param = new Recipe();
		if (recipes.getRecipes().size() > 0) {
			param = recipes.getRecipes().get(0);
		}

		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

			// SQL文を準備する
			String sql = "select r.rec_id, r.r_name, r.time, r.image, r.wanpan, r.save_time, r.microwave_oven, "
					+ "r.recipe, r.cooking_expenses, r.eating_out_expenses, r_c.u_id, r_c.r_date, r_c.r_count, "
					+ "r_i.i_id, r_i.f_id, r_i.ingredient, r_i.r_i_count, r_i.unit "
					+ "from recipes as r "
					+ "left join recipe_ingredients as r_i on r.rec_id = r_i.rec_id "
					+ "left join recipe_counts as r_c on r.rec_id = r_c.rec_id "
					+ "where r.save_time like ?";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setBoolean(1, param.getSave_time());

			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();

			// 結果表をコレクションにコピーする
			int rec_id =-111;
			int num = 0;
			while (rs.next()) {
				if (rec_id != rs.getInt("recipes.rec_id")) {
					rec_id = rs.getInt("recipes.rec_id");
					Recipe card = new Recipe(
							rs.getInt("recipes.rec_id"),
							rs.getString("recipes.r_name"),
							rs.getString("recipes.time"),
							rs.getString("recipes.image"),
							rs.getBoolean("recipes.wanpan"),
							rs.getBoolean("recipes.save_time"),
							rs.getBoolean("recipes.microwave_oven"),
							rs.getString("recipes.recipe"),
							rs.getInt("recipes.cooking_expenses"),
							rs.getInt("recipes.eating_out_expenses"),
							rs.getString("recipe_counts.u_id"),
							rs.getDate("recipe_counts.r_date"),
							rs.getInt("recipe_counts.r_count"),
							rs.getInt("recipe_ingredients.i_id"),
							rs.getInt("recipe_ingredients.f_id"),
							rs.getString("recipe_ingredients.ingredient"),
							rs.getDouble("recipe_ingredients.r_i_count"),
							rs.getInt("recipe_ingredients.unit"));
					cardList.add(card);
				} else {
					List<String> ing = cardList.get(num).getIngredient();
					List<Double> r_i_count = cardList.get(num).getR_i_count();
					List<Integer> unit = cardList.get(num).getUnit();
					ing.add(rs.getString("recipe_ingredients.ingredient"));
					r_i_count.add(rs.getDouble("recipe_ingredients.r_i_count"));
					unit.add(rs.getInt("recipe_ingredients.unit"));
					cardList.get(num).setIngredient(ing);
					cardList.get(num).setR_i_count(r_i_count);
					cardList.get(num).setUnit(unit);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			cardList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			cardList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					cardList = null;
				}
			}
		}
		// 結果を返す
		return cardList;
	}

	// 引数paramで検索項目を指定し、検索結果のリストを返す
	public List<Recipe> selectMicrowave_oven(Recipes recipes) {
		Connection conn = null;
		List<Recipe> cardList = new ArrayList<Recipe>();
		Recipe param = new Recipe();
		if (recipes.getRecipes().size() > 0) {
			param = recipes.getRecipes().get(0);
		}

		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

			// SQL文を準備する
			String sql = "select r.rec_id, r.r_name, r.time, r.image, r.wanpan, r.save_time, r.microwave_oven, "
					+ "r.recipe, r.cooking_expenses, r.eating_out_expenses, r_c.u_id, r_c.r_date, r_c.r_count, "
					+ "r_i.i_id, r_i.f_id, r_i.ingredient, r_i.r_i_count, r_i.unit "
					+ "from recipes as r "
					+ "left join recipe_ingredients as r_i on r.rec_id = r_i.rec_id "
					+ "left join recipe_counts as r_c on r.rec_id = r_c.rec_id "
					+ "where r.microwave_oven like ?";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setBoolean(1, param.getMicrowave_oven());

			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();

			// 結果表をコレクションにコピーする
			int rec_id =-111;
			int num = 0;
			while (rs.next()) {
				if (rec_id != rs.getInt("recipes.rec_id")) {
					rec_id = rs.getInt("recipes.rec_id");
					Recipe card = new Recipe(
							rs.getInt("recipes.rec_id"),
							rs.getString("recipes.r_name"),
							rs.getString("recipes.time"),
							rs.getString("recipes.image"),
							rs.getBoolean("recipes.wanpan"),
							rs.getBoolean("recipes.save_time"),
							rs.getBoolean("recipes.microwave_oven"),
							rs.getString("recipes.recipe"),
							rs.getInt("recipes.cooking_expenses"),
							rs.getInt("recipes.eating_out_expenses"),
							rs.getString("recipe_counts.u_id"),
							rs.getDate("recipe_counts.r_date"),
							rs.getInt("recipe_counts.r_count"),
							rs.getInt("recipe_ingredients.i_id"),
							rs.getInt("recipe_ingredients.f_id"),
							rs.getString("recipe_ingredients.ingredient"),
							rs.getDouble("recipe_ingredients.r_i_count"),
							rs.getInt("recipe_ingredients.unit"));
					cardList.add(card);
				} else {
					List<String> ing = cardList.get(num).getIngredient();
					List<Double> r_i_count = cardList.get(num).getR_i_count();
					List<Integer> unit = cardList.get(num).getUnit();
					ing.add(rs.getString("recipe_ingredients.ingredient"));
					r_i_count.add(rs.getDouble("recipe_ingredients.r_i_count"));
					unit.add(rs.getInt("recipe_ingredients.unit"));
					cardList.get(num).setIngredient(ing);
					cardList.get(num).setR_i_count(r_i_count);
					cardList.get(num).setUnit(unit);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			cardList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			cardList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					cardList = null;
				}
			}
		}
		// 結果を返す
		return cardList;
	}



	// 引数cardで指定されたレコードを登録し、成功したらtrueを返す
	public boolean insert(Recipe recipe) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

			// SQL文を準備する
			String sql_r = "insert into recipes (r_name, time, image, wanpan, save_time, microwave_oven, recipe, "
					+ "cooking_expenses, eating_out_expenses) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			String sql_c = "insert into recipe_counts (u_id, rec_id, r_date, r_count) values (?, ?, ?, ?)";
			String sql_i = "insert into recipe_ingredients (i_id, rec_id, ingredient, r_i_count, unit) values (?, ?, ?, ?, ?)";

			PreparedStatement pStmt_r = conn.prepareStatement(sql_r);
			PreparedStatement pStmt_c = conn.prepareStatement(sql_c);
			PreparedStatement pStmt_i = conn.prepareStatement(sql_i);

			int flag = -1;

			// SQL文を完成させる
			if (recipe.getR_name() != null && !recipe.getR_name().equals("")) {
				pStmt_r.setString(1, recipe.getR_name());
			} else {
				pStmt_r.setString(1, null);
			}
			if (recipe.getTime() != null && !recipe.getTime().equals("")) {
				pStmt_r.setString(2, recipe.getTime());
			} else {
				pStmt_r.setString(2, null);
			}
			if (recipe.getImage() != null && !recipe.getImage().equals("")) {
				pStmt_r.setString(3, recipe.getImage());
			} else {
				pStmt_r.setString(3, null);
			}
			pStmt_r.setBoolean(4, recipe.getWanpan());
			pStmt_r.setBoolean(5, recipe.getSave_time());
			pStmt_r.setBoolean(6, recipe.getMicrowave_oven());
			if (recipe.getRecipe() != null && !recipe.getRecipe().equals("")) {
				pStmt_r.setString(7, recipe.getRecipe());
			} else {
				pStmt_r.setString(7, null);
			}
			if (recipe.getCooking_expenses() != -1) {
				pStmt_r.setInt(8, recipe.getCooking_expenses());
			} else {
				pStmt_r.setInt(8, -1);
			}
			if (recipe.getEating_out_expenses() != -1) {
				pStmt_r.setInt(9, recipe.getEating_out_expenses());
			} else {
				pStmt_r.setInt(9, -1);
			}

			if (recipe.getU_id() != null && !recipe.getU_id().equals("")) {
				pStmt_c.setString(1, recipe.getU_id());
			} else {
				pStmt_c.setString(1, null);
			}
			if (recipe.getRec_id() != -1) {
				pStmt_c.setInt(2, recipe.getRec_id());
			} else {
				pStmt_c.setInt(2, -1);
			}
			if (recipe.getR_date() != null && !recipe.getR_date().equals("")) {
				pStmt_c.setDate(3, recipe.getR_date());
			} else {
				pStmt_c.setDate(3, null);
			}
			if (recipe.getR_count() != -1) {
				pStmt_c.setInt(4, recipe.getR_count());
			} else {
				pStmt_c.setInt(4, 0);
			}

			for (String ingredient : recipe.getIngredient()) {
				if (recipe.getI_id() != -1) {
					pStmt_i.setInt(1, recipe.getI_id());
				} else {
					pStmt_i.setInt(1, -1);
				}
				if (recipe.getRec_id() != -1) {
					pStmt_i.setInt(2, recipe.getRec_id());
				} else {
					pStmt_i.setInt(2, -1);
				}
				if (ingredient != null && ! ingredient.equals("")) {
					pStmt_i.setString(3, ingredient);
				} else {
					pStmt_i.setString(3, null);
				}
				if (recipe.getR_i_count() != null && recipe.getR_i_count().get(0) != -1.0) {
					pStmt_i.setString(4, "%" + recipe.getR_i_count() + "%");
				} else {
					pStmt_i.setString(4, "%");
				}
				if (recipe.getUnit() != null && recipe.getUnit().get(0) != -1) {
					pStmt_i.setString(5, "%");
				} else {
					pStmt_i.setString(5, "%");
				}

				flag = pStmt_i.executeUpdate();
			}


			// SQL文を実行する
			if (pStmt_r.executeUpdate() == 1 && pStmt_c.executeUpdate() == 1 && flag == 1) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		// 結果を返す
		return result;
	}

	// 引数cardで指定されたレコードを登録し、成功したらtrueを返す
		public boolean insertRecipe_counts(Recipe recipe) {
			Connection conn = null;
			boolean result = false;

			try {
				// JDBCドライバを読み込む
				Class.forName("org.h2.Driver");

				// データベースに接続する
				conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

				// SQL文を準備する
				String sql_c = "insert into recipe_counts (u_id, rec_id, r_date, r_count) values (?, ?, ?, ?)";

				PreparedStatement pStmt_c = conn.prepareStatement(sql_c);

//				System.out.println(recipe.getU_id());
//				System.out.println(recipe.getRec_id());
//				System.out.println(recipe.getR_date());
//				System.out.println(recipe.getR_count());

				// SQL文を完成させる

				if (recipe.getU_id() != null && !recipe.getU_id().equals("")) {
					pStmt_c.setString(1, recipe.getU_id());
				} else {
					pStmt_c.setString(1, null);
				}
				if (recipe.getRec_id() != -1) {
					pStmt_c.setInt(2, recipe.getRec_id());
				} else {
					pStmt_c.setInt(2, -1);
				}
				if (recipe.getR_date() != null && !recipe.getR_date().equals("")) {
					pStmt_c.setDate(3, recipe.getR_date());
				} else {
					pStmt_c.setDate(3, null);
				}
				if (recipe.getR_count() != -1) {
					pStmt_c.setInt(4, recipe.getR_count());
				} else {
					pStmt_c.setInt(4, 0);
				}



				// SQL文を実行する
				if (pStmt_c.executeUpdate() == 1) {
					result = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				// データベースを切断
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

			// 結果を返す
			return result;
		}

	// 引数cardで指定されたレコードを更新し、成功したらtrueを返す
	public boolean update(Recipe recipe, Recipe pre_recipe) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

			// SQL文を準備する
			String sql_r = "update recipes set r_name=?, time=?, image=?, wanpan=?, save_time=?, microwave_oven=?, recipe=?, "
					+ "+ \"cooking_expenses=?, eating_out_expenses=? where rec_id like ?";
			String sql_c = "update recipe_counts set u_id=?, rec_id=?, r_date=?, r_count=? where u_id like ? and rec_id like ? and r_date like ?";
			String sql_i = "update recipe_ingredients set i_id=?, rec_id=?, f_id=?,ingredient=?, r_i_count=?, unit=? where i_id like ? and rec_id like ?";

			PreparedStatement pStmt_r = conn.prepareStatement(sql_r);
			PreparedStatement pStmt_c = conn.prepareStatement(sql_c);
			PreparedStatement pStmt_i = conn.prepareStatement(sql_i);

			int flag = -1;

			// SQL文を完成させる
			if (recipe.getR_name() != null && !recipe.getR_name().equals("")) {
				pStmt_r.setString(1, recipe.getR_name());
			} else {
				pStmt_r.setString(1, null);
			}
			if (recipe.getTime() != null && !recipe.getTime().equals("")) {
				pStmt_r.setString(2, recipe.getTime());
			} else {
				pStmt_r.setString(2, null);
			}
			if (recipe.getImage() != null && !recipe.getImage().equals("")) {
				pStmt_r.setString(3, recipe.getImage());
			} else {
				pStmt_r.setString(3, null);
			}
			pStmt_r.setBoolean(4, recipe.getWanpan());
			pStmt_r.setBoolean(5, recipe.getSave_time());
			pStmt_r.setBoolean(6, recipe.getMicrowave_oven());
			if (recipe.getRecipe() != null && !recipe.getRecipe().equals("")) {
				pStmt_r.setString(7, recipe.getRecipe());
			} else {
				pStmt_r.setString(7, null);
			}
			if (recipe.getCooking_expenses() != -1) {
				pStmt_r.setInt(8, recipe.getCooking_expenses());
			} else {
				pStmt_r.setInt(8, -1);
			}
			if (recipe.getEating_out_expenses() != -1) {
				pStmt_r.setInt(9, recipe.getEating_out_expenses());
			} else {
				pStmt_r.setInt(9, -1);
			}
			if (recipe.getRec_id() != -1) {
				pStmt_r.setString(10, "%" + recipe.getRec_id() + "%");
			} else {
				pStmt_r.setString(10, "%");
			}

			if (recipe.getU_id() != null && !recipe.getU_id().equals("")) {
				pStmt_c.setString(1, recipe.getU_id());
			} else {
				pStmt_c.setString(1, null);
			}
			if (recipe.getRec_id() != -1) {
				pStmt_c.setInt(2, recipe.getRec_id());
			} else {
				pStmt_c.setInt(2, -1);
			}
			if (recipe.getR_date() != null) {
				pStmt_c.setDate(3, recipe.getR_date());
			} else {
				pStmt_c.setDate(3, null);
			}
			if (recipe.getR_count() != -1) {
				pStmt_c.setInt(4, recipe.getR_count());
			} else {
				pStmt_c.setInt(4, -1);
			}
			if (pre_recipe.getU_id() != null && !pre_recipe.getU_id().equals("")) {
				pStmt_c.setString(5, "%" + pre_recipe.getU_id() + "%");
			} else {
				pStmt_c.setString(5, "%");
			}
			if (pre_recipe.getRec_id() != -1) {
				pStmt_c.setString(6, "%" + pre_recipe.getRec_id() + "%");
			} else {
				pStmt_c.setString(6, "%");
			}
			if (pre_recipe.getR_date() != null) {
				pStmt_c.setString(7, "%" + pre_recipe.getR_date() + "%");
			} else {
				pStmt_c.setString(7, "%");
			}

			for (String ingredient : recipe.getIngredient()) {
				if (recipe.getI_id() != -1) {
					pStmt_i.setInt(1, recipe.getI_id());
				} else {
					pStmt_i.setInt(1, -1);
				}
				if (recipe.getRec_id() != -1) {
					pStmt_i.setInt(2, recipe.getRec_id());
				} else {
					pStmt_i.setInt(2, -1);
				}
				if (recipe.getRec_id() != -1) {
					pStmt_i.setInt(3, recipe.getF_id());
				} else {
					pStmt_i.setInt(3, -1);
				}
				if (ingredient != null && ! ingredient.equals("")) {
					pStmt_i.setString(4, ingredient);
				} else {
					pStmt_i.setString(4, null);
				}
				if (pre_recipe.getR_i_count() != null && pre_recipe.getR_i_count().get(0) != -1.0) {
					pStmt_i.setString(4, "%" + pre_recipe.getR_i_count() + "%");
				} else {
					pStmt_i.setString(4, "%");
				}
				if (recipe.getUnit() != null && recipe.getUnit().get(0) != -1) {
					pStmt_i.setString(5, "%");
				} else {
					pStmt_i.setString(5, "%");
				}
				if (pre_recipe.getI_id() != -1) {
					pStmt_i.setString(7, "%" + pre_recipe.getI_id() + "%");
				} else {
					pStmt_i.setString(7, "%");
				}
				if (pre_recipe.getRec_id() != -1) {
					pStmt_r.setString(8, "%" + pre_recipe.getRec_id() + "%");
				} else {
					pStmt_r.setString(8, "%");
				}
				flag = pStmt_i.executeUpdate();
			}

			// SQL文を実行する
			if (pStmt_r.executeUpdate() == 1 && pStmt_c.executeUpdate() == 1 && flag == 1) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		// 結果を返す
		return result;
	}

	// 引数cardで指定されたレコードを更新し、成功したらtrueを返す
		public boolean updateCount(Recipe recipe, Recipe pre_recipe) {
			Connection conn = null;
			boolean result = false;

			try {
				// JDBCドライバを読み込む
				Class.forName("org.h2.Driver");

				// データベースに接続する
				conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

				// SQL文を準備する
				String sql_c = "update recipe_counts set u_id=?, rec_id=?, r_date=?, r_count=? where u_id like ? and rec_id like ? and r_date like ?";

				PreparedStatement pStmt_c = conn.prepareStatement(sql_c);

				// SQL文を完成させる

				if (recipe.getU_id() != null && !recipe.getU_id().equals("")) {
					pStmt_c.setString(1, recipe.getU_id());
				} else {
					pStmt_c.setString(1, null);
				}
				if (recipe.getRec_id() != -1) {
					pStmt_c.setInt(2, recipe.getRec_id());
				} else {
					pStmt_c.setInt(2, -1);
				}
				if (recipe.getR_date() != null) {
					pStmt_c.setDate(3, recipe.getR_date());
				} else {
					pStmt_c.setDate(3, null);
				}
				if (recipe.getR_count() != -1) {
					pStmt_c.setInt(4, recipe.getR_count());
				} else {
					pStmt_c.setInt(4, -1);
				}
				if (pre_recipe.getU_id() != null && !pre_recipe.getU_id().equals("")) {
					pStmt_c.setString(5, "%" + pre_recipe.getU_id() + "%");
				} else {
					pStmt_c.setString(5, "%");
				}
				if (pre_recipe.getRec_id() != -1) {
					pStmt_c.setString(6, "%" + pre_recipe.getRec_id() + "%");
				} else {
					pStmt_c.setString(6, "%");
				}
				if (pre_recipe.getR_date() != null) {
					pStmt_c.setString(7, "%" + pre_recipe.getR_date() + "%");
				} else {
					pStmt_c.setString(7, "%");
				}


				// SQL文を実行する
				if (pStmt_c.executeUpdate() == 1) {
					result = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				// データベースを切断
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

			// 結果を返す
			return result;
		}

	// 引数numberで指定されたレコードを削除し、成功したらtrueを返す
	public boolean delete(int number) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

			// SQL文を準備する
			String sql = "delete from recipes where rec_id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setInt(1, number);

			// SQL文を実行する
			if (pStmt.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		// 結果を返す
		return result;
	}
}
