package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Recipe;
import model.Refrigerator;

public class RefrigeratorDAO {
	// 引数paramで検索項目を指定し、検索結果のリストを返す
	public List<Refrigerator> select(Refrigerator param) {
		Connection conn = null;
		List<Refrigerator> cardList = new ArrayList<Refrigerator>();

		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

			// SQL文を準備する
			String sql = "select r.ref_id, r.u_id, r.f_id, r.f_count"
					+ "r_t.text1, r_t.text2, r_t.text3, r_t.text4, r_t.text5, r_t.text6, r_t.text7, r_t.text8, r_t.text9, "
					+ "r_t.num1, r_t.num2, r_t.num3, r_t.num4, r_t.num5, r_t.num6, r_t.num7, r_t.num8, r_t.num9"
					+ "from refrigerators as r"
					+ "left join refrigerator_texts as r_t on r.ref_id == r_t.ref_id"
					+ "where r.ref_id == ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			if (param.getRef_id() != -1) {
				pStmt.setString(1, "%" + param.getRef_id() + "%");
			} else {
				pStmt.setString(1, "%");
			}

			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();

			List<String> c_t = new ArrayList<>();
			List<Double> c_n = new ArrayList<>();
			for (int i = 0; i < 9; i++) {
				c_t.add(rs.getString("refrigerator_texts.text" + i + 1));
				c_n.add(rs.getDouble("refrigerator_texts.num" + i + 1));
			}

			// 結果表をコレクションにコピーする
			while (rs.next()) {
				Refrigerator card = new Refrigerator(
						rs.getInt("refrigerators.ref_id"),
						rs.getString("refrigerators.u_id"),
						rs.getInt("refrigerators.f_id"),
						rs.getDouble("refrigerators.f_count"),
						c_t,
						c_n);
				;
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

	// 引数cardで指定されたレコードを登録し、成功したらtrueを返す
	public boolean insert(Refrigerator card) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

			// SQL文を準備する
			String sql_r = "insert into refrigerators (ref_id, u_id, f_id, f_count) values (?, ?, ?, ?)";
			String sql_t = "insert into refrigerator_texts (ref_id, "
					+ "text1, text2, text3, text4, text5, text6, text7, text8, text9, "
					+ "num1, num2, num3, num4, num5, num6, num7, num8, num9) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement pStmt_r = conn.prepareStatement(sql_r);
			PreparedStatement pStmt_t = conn.prepareStatement(sql_t);

			// SQL文を完成させる
			if (card.getRef_id() != -1) {
				pStmt_r.setInt(1, card.getRef_id());
			} else {
				pStmt_r.setInt(1, -1);
			}
			if (card.getU_id() != null && !card.getU_id().equals("")) {
				pStmt_r.setString(2, card.getU_id());
			} else {
				pStmt_r.setString(2, null);
			}
			if (card.getF_id() != -1) {
				pStmt_r.setInt(3, card.getF_id());
			} else {
				pStmt_r.setInt(3, -1);
			}
			if (card.getF_count() != -1) {
				pStmt_r.setDouble(4, card.getF_count());
			} else {
				pStmt_r.setDouble(4, -1);
			}

			if (card.getRef_id() != -1) {
				pStmt_t.setInt(1, card.getRef_id());
			} else {
				pStmt_t.setInt(1, -1);
			}

			for (String text : card.getText()) {
				pStmt_t.setString(2, text);
			}

			for (double num : card.getNum()) {
				pStmt_t.setDouble(3, num);
			}

			// SQL文を実行する
			if (pStmt_r.executeUpdate() == 1 && pStmt_t.executeUpdate() == 1) {
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
	public boolean update(Recipe card) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

			// SQL文を準備する
			String sql_r = "update recipes set r_name=?, time=?, image=?, wanpan=?, save_time=?, microwave_oven=?, recipe=?, "
					+ "+ \"cooking_expenses=?, eating_out_expenses=? where rec_id=?";
			String sql_c = "update recipes set u_id=?, rec_id=?, r_date=?, r_count=? where rec_id=?";
			String sql_i = "update recipes set i_id=?, rec_id=?, ingredient=? where rec_id=?";

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
			if (card.getRec_id() != -1) {
				pStmt_r.setInt(10, card.getRec_id());
			} else {
				pStmt_r.setInt(10, -1);
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
			if (card.getR_date() != null) {
				pStmt_c.setDate(3, card.getR_date());
			} else {
				pStmt_c.setDate(3, null);
			}
			if (card.getR_count() != -1) {
				pStmt_c.setInt(4, card.getR_count());
			} else {
				pStmt_c.setInt(4, -1);
			}
			if (card.getRec_id() != -1) {
				pStmt_r.setInt(5, card.getRec_id());
			} else {
				pStmt_r.setInt(5, -1);
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
				if (ingredient != null && !ingredient.equals("")) {
					pStmt_i.setString(3, ingredient);
				} else {
					pStmt_i.setString(3, null);
				}
				if (card.getRec_id() != -1) {
					pStmt_r.setInt(4, card.getRec_id());
				} else {
					pStmt_r.setInt(4, -1);
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
