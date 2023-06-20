package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.MainFood;
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
					+ "where r.u_id like ? or r.f_id like ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			if (param.getRef_id() != -1) {
				pStmt.setString(1, "%" + param.getU_id() + "%");
			} else {
				pStmt.setString(1, "%");
			}
			if (param.getF_id() != -1) {
				pStmt.setString(2, "%" + param.getF_id() + "%");
			} else {
				pStmt.setString(2, "%");
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

	public List<MainFood> selectImg(Refrigerator param, MainFood mainfood) {
		Connection conn = null;
		List<MainFood> cardList = new ArrayList<MainFood>();

		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

			// SQL文を準備する
			String sql = "select m.f_id, m.image"
					+ "from refrigerators as r"
					+ "left join foods as m on r.f_id == m.f_id";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();

			// 結果表をコレクションにコピーする
			while (rs.next()) {
				MainFood mainFood = new MainFood(
						rs.getInt("foods.f_id"),
						rs.getString("foods.f_name"),
						rs.getString("foods.image"),
						rs.getString("foods.identify"),
						rs.getString("foods.strage_method"),
						rs.getString("foods.retention_period"),
						rs.getString("foods.season"));
				;
				cardList.add(mainFood);
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
	public boolean insert(Refrigerator refrigerator) {
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
			if (refrigerator.getRef_id() != -1) {
				pStmt_r.setInt(1, refrigerator.getRef_id());
			} else {
				pStmt_r.setInt(1, -1);
			}
			if (refrigerator.getU_id() != null && !refrigerator.getU_id().equals("")) {
				pStmt_r.setString(2, refrigerator.getU_id());
			} else {
				pStmt_r.setString(2, null);
			}
			if (refrigerator.getF_id() != -1) {
				pStmt_r.setInt(3, refrigerator.getF_id());
			} else {
				pStmt_r.setInt(3, -1);
			}
			if (refrigerator.getF_count() != -1) {
				pStmt_r.setDouble(4, refrigerator.getF_count());
			} else {
				pStmt_r.setDouble(4, -1);
			}

			if (refrigerator.getRef_id() != -1) {
				pStmt_t.setInt(1, refrigerator.getRef_id());
			} else {
				pStmt_t.setInt(1, -1);
			}

			int i = 2;
			for (String text : refrigerator.getText()) {
				pStmt_t.setString(i, text);
				i++;
			}

			i = 3;
			for (double num : refrigerator.getNum()) {
				pStmt_t.setDouble(i, num);
				i++;
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
	public boolean update(Refrigerator refrigerator, Refrigerator pre_refrigerator) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

			// SQL文を準備する
			String sql_r = "update refrigerators set u_id=?, f_id=?, f_count=? where ref_id like ? and u_id like ? and f_id like ?";
			String sql_t = "update refrigerator_texts set text1=?, text2=?, text3=?, text4=?, text5=?, text6=?, text7=?, text8=?, text9=?, "
					+ "num1=?, num2=?, num3=?, num4=?, num5=?, num6=?, num7=?, num8=?, num9=? where ref_id like ?";

			PreparedStatement pStmt_r = conn.prepareStatement(sql_r);
			PreparedStatement pStmt_t = conn.prepareStatement(sql_t);

			// SQL文を完成させる
			if (refrigerator.getU_id() != null && !refrigerator.getU_id().equals("")) {
				pStmt_r.setString(1, refrigerator.getU_id());
			} else {
				pStmt_r.setString(1, null);
			}
			if (refrigerator.getF_id() != -1) {
				pStmt_r.setInt(2, refrigerator.getF_id());
			} else {
				pStmt_r.setInt(2, -1);
			}
			if (refrigerator.getF_count() != -1) {
				pStmt_r.setDouble(3, refrigerator.getF_count());
			} else {
				pStmt_r.setDouble(3, -1);
			}
			if (refrigerator.getRef_id() != -1) {
				pStmt_r.setString(4, "%" + refrigerator.getRef_id() + "%");
			} else {
				pStmt_r.setString(4, "%");
			}
			if (pre_refrigerator.getU_id() != null && !pre_refrigerator.getU_id().equals("")) {
				pStmt_r.setString(5, "%" + pre_refrigerator.getU_id() + "%");
			} else {
				pStmt_r.setString(5, "%");
			}
			if (pre_refrigerator.getF_id() != -1) {
				pStmt_r.setString(6, "%" + pre_refrigerator.getF_id() + "%");
			} else {
				pStmt_r.setString(6, "%");
			}

			int i = 1;
			for (String text : refrigerator.getText()) {
				pStmt_t.setString(i, text);
				i++;
			}

			i = 2;
			for (double num : refrigerator.getNum()) {
				pStmt_t.setDouble(i, num);
				i++;
			}

			if (refrigerator.getRef_id() != -1) {
				pStmt_t.setString(19, "%" + refrigerator.getRef_id() + "%");
			} else {
				pStmt_t.setString(19, "%");
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
			String sql = "delete from refrigerators where ref_id=?";
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
