package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.MainFood;

public class MainFoodDAO {
	// 引数paramで検索項目を指定し、検索結果のリストを返す
	public List<MainFood> select(MainFood mainFood) {
		Connection conn = null;
		List<MainFood> cardList = new ArrayList<MainFood>();

		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

			// SQL文を準備する
			String sql = "select * from foods where f_id like ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			if (mainFood.getF_id() != -1) {
				pStmt.setString(1, "%" + mainFood.getF_id() + "%");
			} else {
				pStmt.setString(1, "%");
			}

			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();

			// 結果表をコレクションにコピーする
			while (rs.next()) {
				MainFood card = new MainFood(
						rs.getInt("foods.f_id"),
						rs.getString("foods.f_name"),
						rs.getString("foods.image"),
						rs.getString("foods.identify"),
						rs.getString("foods.strage_method"),
						rs.getString("foods.retention_period"),
						rs.getString("foods.season"));
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
/*
	 // 引数cardで指定されたレコードを登録し、成功したらtrueを返す
	public boolean insert(MainFood card) {
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
			String sql_i = "insert into recipe_ingredients (i_id, rec_id, ingredient) values (?, ?, ?)";

			PreparedStatement pStmt_r = conn.prepareStatement(sql_r);
			PreparedStatement pStmt_c = conn.prepareStatement(sql_c);
			PreparedStatement pStmt_i = conn.prepareStatement(sql_i);

			int flag = -1;

			// SQL文を完成させる
			if (card.getR_name() != null && !card.getR_name().equals("")) {
				pStmt_r.setString(1, card.getR_name());
			} else {
				pStmt_r.setString(1, null);
			}
			if (card.getTime() != null && !card.getTime().equals("")) {
				pStmt_r.setString(2, card.getTime());
			} else {
				pStmt_r.setString(2, null);
			}
			if (card.getImage() != null && !card.getImage().equals("")) {
				pStmt_r.setString(3, card.getImage());
			} else {
				pStmt_r.setString(3, null);
			}
			pStmt_r.setBoolean(4, card.getsWanpan());
			pStmt_r.setBoolean(5, card.getsSave_time());
			pStmt_r.setBoolean(6, card.getsMicrowave_oven());
			if (card.getRecipe() != null && !card.getRecipe().equals("")) {
				pStmt_r.setString(7, card.getRecipe());
			} else {
				pStmt_r.setString(7, null);
			}
			if (card.getCooking_expenses() != -1) {
				pStmt_r.setInt(8, card.getCooking_expenses());
			} else {
				pStmt_r.setInt(8, -1);
			}
			if (card.getEating_out_expenses() != -1) {
				pStmt_r.setInt(9, card.getEating_out_expenses());
			} else {
				pStmt_r.setInt(9, -1);
			}

			if (card.getU_id() != null && !card.getU_id().equals("")) {
				pStmt_c.setString(1, card.getU_id());
			} else {
				pStmt_c.setString(1, null);
			}
			if (card.getRec_id() != -1) {
				pStmt_c.setInt(2, card.getRec_id());
			} else {
				pStmt_c.setInt(2, -1);
			}
			if (card.getR_date() != null && !card.getR_date().equals("")) {
				pStmt_c.setDate(3, card.getR_date());
			} else {
				pStmt_c.setDate(3, null);
			}
			if (card.getR_count() != -1) {
				pStmt_c.setInt(4, card.getR_count());
			} else {
				pStmt_c.setInt(4, -1);
			}

			for (String ingredient : card.getIngredient()) {
				if (card.getI_id() != -1) {
					pStmt_i.setInt(1, card.getI_id());
				} else {
					pStmt_i.setInt(1, -1);
				}
				if (card.getRec_id() != -1) {
					pStmt_i.setInt(2, card.getRec_id());
				} else {
					pStmt_i.setInt(2, -1);
				}
				if (ingredient != null && ! ingredient.equals("")) {
					pStmt_i.setString(3, ingredient);
				} else {
					pStmt_i.setString(3, null);
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
*/

	// 引数cardで指定されたレコードを更新し、成功したらtrueを返す
	public boolean update(MainFood mainFood) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

			// SQL文を準備する
			String sql = "update foods set f_name=?, image=?, identify=?, strage_method=?,"
					+ "retention_period=?, season=? where f_id like ?";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			if (mainFood.getF_name() != null && !mainFood.getF_name().equals("")) {
				pStmt.setString(1, mainFood.getF_name());
			} else {
				pStmt.setString(1, null);
			}
			if (mainFood.getImage() != null && !mainFood.getImage().equals("")) {
				pStmt.setString(2, mainFood.getImage());
			} else {
				pStmt.setString(2, null);
			}
			if (mainFood.getIdentify() != null && !mainFood.getIdentify().equals("")) {
				pStmt.setString(3, mainFood.getIdentify());
			} else {
				pStmt.setString(3, null);
			}
			if (mainFood.getStrage_method() != null && !mainFood.getStrage_method().equals("")) {
				pStmt.setString(4, mainFood.getStrage_method());
			} else {
				pStmt.setString(4, null);
			}
			if (mainFood.getRetention_period() != null && !mainFood.getRetention_period().equals("")) {
				pStmt.setString(5, mainFood.getRetention_period());
			} else {
				pStmt.setString(5, null);
			}
			if (mainFood.getSeason() != null && !mainFood.getSeason().equals("")) {
				pStmt.setString(6, mainFood.getSeason());
			} else {
				pStmt.setString(6, null);
			}
			if (mainFood.getF_id() != -1) {
				pStmt.setString(7, "%" + mainFood.getF_id() + "%");
			} else {
				pStmt.setString(7, "%");
			}

			pStmt.executeUpdate();

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

	// 引数f_idで指定されたレコードを削除し、成功したらtrueを返す
	public boolean delete(int f_id) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

			// SQL文を準備する
			String sql = "delete from foods where f_id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setInt(1, f_id);

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
