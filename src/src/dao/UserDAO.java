package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class UserDAO {
	// 引数paramで検索項目を指定し、検索結果のリストを返す
	public boolean selectId(User param) {
		Connection conn = null;
		boolean loginResult = false;
		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");
			// SELECT文を準備する
			String sql = "select count(*) from users where u_id = ? and password = ? position = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, param.getU_id());
			pStmt.setString(2, param.getPassword());
			pStmt.setString(3, param.getPosition());
			// SELECT文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();
			// ユーザーIDとパスワードが一致するユーザーがいたかどうかをチェックする
			rs.next();
			if (rs.getInt("count(*)") == 1) {
				loginResult = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			loginResult = false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			loginResult = false;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					loginResult = false;
				}
			}
		}
		// 結果を返す
		return loginResult;
	}

	public List<User> select(User param) {
		Connection conn = null;
		List<User> cardList = new ArrayList<User>();
		try {
			// JDBCドライバを読み込む  Javaによるデータベース接続
			Class.forName("org.h2.Driver");
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");
			// SQL文を準備する  （検索するSQL文）検索する項目増やせる
			String sql = "select users.u_id, user_likefoods.lf_id,  from users left join user_likefoods on users.u_id = user_likefoods.u_id "
					+ "left join user_dislikefoods on users.u_id = user_dislikefoods.u_id "
					+ "left join foods on foods.f_id = user_likefoods.lf_id or foods.f_id = user_dislikefoods.df_id"
					+ "where users.u_id = ? ";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			// SQL文を完成させる(?を埋める)
			if (param.getU_id() != null) {
				pStmt.setString(1, param.getU_id()); // %はSQLのあいまい検索のやつ ?を"%" + param.getNumber() + "%"にしてる
			} else {
				pStmt.setString(1, "%");
			}
			// ここから何も触ってないです
			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();
			// 結果表をコレクションにコピーする （通常のArrayリストに入れている）
			while (rs.next()) {
				Bc card = new Bc(
						rs.getString("NUMBER"),
						rs.getString("COMPANY_NAME"),
						rs.getString("DEPT_NAME"),
						rs.getString("POSITION_NAME"),
						rs.getString("NAME"),
						rs.getString("ZIPCODE"),
						rs.getString("ADDRESS"),
						rs.getString("TEL_NUMBER"),
						rs.getString("FAX"),
						rs.getString("EMAIL"),
						rs.getString("NOTE"));
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

	public boolean insert(User card) {
		Connection conn = null;
		boolean result = false;
		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");
			// SQL文を準備する
			String sql = "insert into User (u_id, password,position,lf_id ,df_id) values (?, ?, ?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			// SQL文を完成させる
			if (card.getU_id() != null && !card.getU_id().equals("")) {
				pStmt.setString(1, card.getU_id);
			} else {
				pStmt.setString(1, null);
			}
			if (card.getPassword() != null && !card.getPassword().equals("")) {
				pStmt.setString(2, card.getPassword());
			} else {
				pStmt.setString(2, null);
			}
			if (card.getPosition() != null && !card.getPosition().equals("")) {
				pStmt.setString(3, card.getPosition());
			} else {
				pStmt.setString(3, null);
			}
			if (card.getLf_id() != null && !card.getLf_id().equals("")) {
				pStmt.setString(4, card.getLf_id());
			} else {
				pStmt.setString(4, null);
			}
			if (card.getDf_id() != null && !card.getDf_id().equals("")) {
				pStmt.setString(5, card.getDf_id());
			} else {
				pStmt.setString(5, null);
			}
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

	// 引数cardで指定されたレコードを更新し、成功したらtrueを返す
	public boolean update(User user) {
		Connection conn = null;
		boolean result = false;
		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");
			// SQL文を準備する
			String sql = "update users set password=?, lf_id=?, df_id=? where u_id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			// SQL文を完成させる
			if (user.getPassword() != null && !user.getPassword().equals("")) {
				pStmt.setString(1, user.getPassword());
			} else {
				pStmt.setString(1, null);
			}
			if (user.getLf_id() != null && !user.getLf_id().equals("")) {
				pStmt.setString(2, user.getLf_id());
			} else {
				pStmt.setString(2, null);
			}
			if (user.getDf_id() != null && !user.getDf_id().equals("")) {
				pStmt.setString(3, user.getDf_id());
			} else {
				pStmt.setString(3, null);
			}
			pStmt.setString(4, user.getU_id());
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

	// 引数numberで指定されたレコードを削除し、成功したらtrueを返す
	public boolean delete(String number) {
		Connection conn = null;
		boolean result = false;
		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMV", "sa", "");
			// SQL文を準備する
			String sql = "delete from users where u_id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			// SQL文を完成させる
			pStmt.setString(1, number);
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