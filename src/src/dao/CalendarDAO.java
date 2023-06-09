package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Calendar;
import model.Recipe;

public class CalendarDAO {
	// 引数userで検索項目を指定し、検索結果のリストを返す
	public List<Calendar> select(Calendar calendar) {
		Connection conn = null;
		List<Calendar> CalendarList = new ArrayList<Calendar>();
		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");
			// SELECT文を準備する
//			c_count以降にエラー
			String sql = "select calendars.u_id, calendars.rec_id, date, "
					+ "r_name, cooking_expenses, eating_out_expenses, recipe_counts.r_count "
					+ "from calendars "
					+ "left join recipes "
					+ "on calendars.rec_id = recipes.rec_id "
					+ "left join recipe_counts "
					+ "on calendars.rec_id = recipe_counts.rec_id "
					+ "where calendars.u_id = ? and date like ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる(?を埋める)
			if (calendar.getU_id() != null) {
				pStmt.setString(1, calendar.getU_id());
			} else {
				pStmt.setString(1, null);
			}
			if (calendar.getDate() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				String now = sdf.format(calendar.getDate());	// 年-月のフォーマットに変換
				pStmt.setString(2, now+"%");
			} else {
				pStmt.setString(2, "%");
			}
			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				Calendar c = new Calendar(
						rs.getString("calendars.u_id"),
						rs.getInt("calendars.rec_id"),
						rs.getDate("calendars.date"),
//						rs.getInt("calendars.c_count"),
						rs.getString("recipes.r_name"),
						rs.getInt("recipes.cooking_expenses"),
						rs.getInt("recipes.eating_out_expenses"));

				CalendarList.add(c);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			CalendarList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			CalendarList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					CalendarList = null;
				}
			}
		}
		// 結果を返す
		return CalendarList;
	}

	public List<Calendar> selectDate(Calendar calendar) {
		Connection conn = null;
		List<Calendar> CalendarList = new ArrayList<Calendar>();
		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");
			// SELECT文を準備する
//			c_count以降にエラー
			String sql = "select calendars.u_id, calendars.rec_id, date, "
					+ "r_name, cooking_expenses, eating_out_expenses, recipe_counts.r_count "
					+ "from calendars "
					+ "left join recipes "
					+ "on calendars.rec_id = recipes.rec_id "
					+ "left join recipe_counts "
					+ "on calendars.rec_id = recipe_counts.rec_id "
					+ "where calendars.u_id = ? and date like ? and calendars.rec_id like ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる(?を埋める)
			if (calendar.getU_id() != null) {
				pStmt.setString(1, calendar.getU_id());
			} else {
				pStmt.setString(1, null);
			}
			if (calendar.getDate() != null) {
				pStmt.setDate(2, calendar.getDate());
			} else {
				pStmt.setString(2, "%");
			}
			if (calendar.getRec_id() != -1) {
				pStmt.setInt(3, calendar.getRec_id());
			} else {
				pStmt.setString(3, "%");
			}
			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				Calendar c = new Calendar(
						rs.getString("calendars.u_id"),
						rs.getInt("calendars.rec_id"),
						rs.getDate("calendars.date"),
//						rs.getInt("calendars.c_count"),
						rs.getString("recipes.r_name"),
						rs.getInt("recipes.cooking_expenses"),
						rs.getInt("recipes.eating_out_expenses"));

				CalendarList.add(c);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			CalendarList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			CalendarList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					CalendarList = null;
				}
			}
		}
		// 結果を返す
		return CalendarList;
	}

	public List<Recipe> selectR_Count(Calendar calendar) {
		Connection conn = null;
		List<Recipe> cardList = new ArrayList<Recipe>();
		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");
			// SELECT文を準備する
//			c_count以降にエラー
			String sql = "select r_count "
					+ "from calendars "
					+ "left join recipe_counts "
					+ "on calendars.rec_id = recipe_counts.rec_id "
					+ "where calendars.u_id = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる(?を埋める)
			if (calendar.getU_id() != null) {
				pStmt.setString(1, calendar.getU_id());
			} else {
				pStmt.setString(1, null);
			}
			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				Recipe card = new Recipe(
						-1,
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
						rs.getInt("recipe_counts.r_count"),
						-1,
						-1,
						"",
						-1.0,
						-1);
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

	public boolean insert(Calendar calendar) {
		Connection conn = null;
		boolean calendarResult = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

			// SQL文を準備する
			String sql = "insert into calendars (u_id, rec_id, date)"
					   + " values (?, ?, ?)";


			PreparedStatement pStmt = conn.prepareStatement(sql);


			// SQL文を完成させる
			if (calendar.getU_id() != null && !calendar.getU_id().equals("")) {
				pStmt.setString(1, calendar.getU_id());
			} else {
				pStmt.setString(1, null);
			}
			if (calendar.getRec_id() != -1 ) {
				pStmt.setInt(2, calendar.getRec_id());
			} else {
				pStmt.setInt(2, -1);
			}
			if (calendar.getDate() != null) {
				pStmt.setDate(3, calendar.getDate());
			} else {
				pStmt.setString(3, null);
			}
//			if (calendar.getC_count() != -1) {
//				pStmt.setInt(4, calendar.getC_count());
//			} else {
//				pStmt.setInt(4, -1);
//			}


			// SQL文を実行する
			if (pStmt.executeUpdate() == 1) {
				calendarResult = true;
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
		return calendarResult;
	}

//	// 引数calenderで指定されたレコードを更新し、成功したらtrueを返す
//	public boolean update(Calendar calendar) {
//		Connection conn = null;
//		boolean calendarResult = false;
//
//		try {
//			// JDBCドライバを読み込む
//			Class.forName("org.h2.Driver");
//
//			// データベースに接続する
//			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");
//
//			// SQL文を準備する
//			String sql = "update calendars set c_count=? where u_id like ? and rec_id like ? and date like ?";
//
//			PreparedStatement pStmt = conn.prepareStatement(sql);
//
//
//			// SQL文を完成させる
//			if (calendar.getC_count() != -1) {
//				pStmt.setInt(1, calendar.getC_count());
//			} else {
//				pStmt.setInt(1, -1);
//			}
//			if (calendar.getU_id() != null && !calendar.getU_id().equals("")) {
//				pStmt.setString(2, "%" + calendar.getU_id() + "%");
//			} else {
//				pStmt.setString(2, "%");
//			}
//			if (calendar.getRec_id() != -1) {
//				pStmt.setString(3, "%" + calendar.getRec_id() + "%");
//			} else {
//				pStmt.setString(3, "%");
//			}
//			if (calendar.getDate() != null) {
//				pStmt.setString(4, "%" + calendar.getDate() + "%");
//			} else {
//				pStmt.setString(4, "%");
//			}
//
//			// SQL文を実行する
//			if (pStmt.executeUpdate() == 1 ) {
//				calendarResult = true;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} finally {
//			// データベースを切断
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//		// 結果を返す
//		return calendarResult;
}
