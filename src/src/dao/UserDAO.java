package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.MainFood;
import model.User;

public class UserDAO {
	// 引数paramで検索項目を指定し、検索結果のリストを返す
	public boolean selectIdPwPo(User param) {
		Connection conn = null;
		boolean loginResult = false;
		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");
			// SELECT文を準備する
			String sql = "select count(*) from users where u_id = ? and password = ? and position = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, param.getU_id());
			pStmt.setString(2, param.getPassword());
			pStmt.setInt(3, param.getPosition());
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

	public List<User> selectLfDf(User param) {
		Connection conn = null;
		List<User> cardList = new ArrayList<User>();
		try {
			// JDBCドライバを読み込む  Javaによるデータベース接続
			Class.forName("org.h2.Driver");
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

			// SQL文を準備する  （検索するSQL文）検索する項目増やせる
			String sql_lf = "select users.u_id, users.password, user_likefoods.lf_id from users "
					+ "left join user_likefoods on users.u_id = user_likefoods.u_id "
					+ "where users.u_id = ?";
			String sql_df = "select users.u_id, users.password, user_dislikefoods.df_id from users "
					+ "left join user_dislikefoods on users.u_id = user_dislikefoods.u_id "
					+ "where users.u_id = ?";

			PreparedStatement pStmt_lf = conn.prepareStatement(sql_lf);
			PreparedStatement pStmt_df = conn.prepareStatement(sql_df);

			// SQL文を完成させる(?を埋める)
			if (param.getU_id() != null) {
				pStmt_lf.setString(1, param.getU_id()); // %はSQLのあいまい検索のやつ ?を"%" + param.getNumber() + "%"にしてる
				pStmt_df.setString(1, param.getU_id());
			} else {
				pStmt_lf.setString(1, "");
				pStmt_df.setString(1, "");
			}



			// SQL文を実行し、結果表を取得する
			ResultSet rs_lf = pStmt_lf.executeQuery();
			ResultSet rs_df = pStmt_df.executeQuery();

			// 結果表をコレクションにコピーする （通常のArrayリストに入れている）
			List<Integer> rs_lf_l = new ArrayList<Integer>();
			List<Integer> rs_df_l = new ArrayList<Integer>();

			while (rs_lf.next()) {
				rs_lf_l.add(rs_lf.getInt("user_likefoods.lf_id"));
				System.out.println(rs_lf.getString("user_likefoods.lf_id"));
			}

			while (rs_df.next()) {
				rs_df_l.add(rs_df.getInt("user_dislikefoods.df_id"));
				System.out.println(rs_df.getString("user_dislikefoods.df_id"));
			}

			// 再度代入
			rs_lf = pStmt_lf.executeQuery();
			rs_df = pStmt_df.executeQuery();
			rs_lf.next();
			rs_df.next();

			User card = null;
			if(rs_lf_l != null && rs_df_l != null) {
				card = new User(
				rs_lf.getString("users.u_id"),
				rs_lf.getString("users.password"),
				rs_lf_l,
				rs_df_l);
			} else if (rs_lf_l != null) {
				card = new User(
						rs_lf.getString("users.u_id"),
						rs_lf.getString("users.password"),
						rs_lf_l,
						-1);
			} else if (rs_df_l != null) {
				card = new User(
						rs_lf.getString("users.u_id"),
						rs_lf.getString("users.password"),
						-1,
						rs_df_l);
			} else {
				card = new User(
						rs_lf.getString("users.u_id"),
						rs_lf.getString("users.password"),
						-1,
						-1);
			}
			cardList.add(card);

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

	public List<MainFood> selectF_name(User param) {
		Connection conn = null;
		List<MainFood> cardList = new ArrayList<MainFood>();
		try {
			// JDBCドライバを読み込む  Javaによるデータベース接続
			Class.forName("org.h2.Driver");
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");

			// SQL文を準備する  （検索するSQL文）検索する項目増やせる
			String sql_lf = "select users.u_id, users.password, user_likefoods.lf_id user_dislikefoods.df_id, foods.f_name "
					+ "from users "
					+ "left join user_likefoods on users.u_id = user_likefoods.u_id "
					+ "left join user_dislikefoods on users.u_id = user_dislikefoods.u_id "
					+ "left join foods on foods.f_id = user_likefoods.lf_id "
					+ "where user_likefoods.lf_id = ? and user_dislikefoods.df_id = ?";

			PreparedStatement pStmt = conn.prepareStatement(sql_lf);

			int max_l = param.getLf_id().size();
			if (max_l < param.getDf_id().size()) {
				max_l = param.getDf_id().size();
			}

			for (int i=0; i<max_l; i++) {
				// SQL文を完成させる(?を埋める)
				if (param.getLf_id().get(i) != -1) {
					pStmt.setInt(1, param.getLf_id().get(i)); // %はSQLのあいまい検索のやつ ?を"%" + param.getNumber() + "%"にしてる
				} else {
					pStmt.setString(1, "");
				}
				if (param.getDf_id().get(i) != null) {
					pStmt.setInt(2, param.getDf_id().get(i)); // %はSQLのあいまい検索のやつ ?を"%" + param.getNumber() + "%"にしてる
				} else {
					pStmt.setString(2, "");
				}

				// SQL文を実行し、結果表を取得する
				ResultSet rs = pStmt.executeQuery();

				// 結果表をコレクションにコピーする （通常のArrayリストに入れている）
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

	public boolean insert(User user) {
		Connection conn = null;
		boolean result = false;
		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");
			// SQL文を準備する
			String sql_u = "insert into users (u_id, password, position) values (?, ?, ?)";
			String sql_l = "insert into user_likefoods (u_id, lf_id) values (?, ?)";
			String sql_d = "insert into user_dislikefoods (u_id, df_id) values (?, ?)";

			PreparedStatement pStmt_u = conn.prepareStatement(sql_u);
			PreparedStatement pStmt_l = conn.prepareStatement(sql_l);
			PreparedStatement pStmt_d = conn.prepareStatement(sql_d);

			int flag_l = -1;
			int flag_d = -1;

			// SQL文を完成させる
			if (user.getU_id() != null && !user.getU_id().equals("")) {
				pStmt_u.setString(1, user.getU_id());
			} else {
				pStmt_u.setString(1, null);
			}
			if (user.getPassword() != null && !user.getPassword().equals("")) {
				pStmt_u.setString(2, user.getPassword());
			} else {
				pStmt_u.setString(2, null);
			}
			if (user.getPosition() != -1) {
				pStmt_u.setInt(3, user.getPosition());
			} else {
				pStmt_u.setInt(3, -1);
			}

			for (int lf_id : user.getLf_id()) {
				if (user.getU_id() != null && !user.getU_id().equals("")) {
					pStmt_l.setString(1, user.getU_id());
				} else {
					pStmt_l.setString(1, null);
				}
				if (lf_id != -1) {
					pStmt_l.setInt(2, lf_id);
				} else {
					pStmt_l.setInt(2, -1);
				}
				flag_l = pStmt_l.executeUpdate();
			}

			for (int df_id : user.getDf_id()) {
				if (user.getU_id() != null && !user.getU_id().equals("")) {
					pStmt_d.setString(1, user.getU_id());
				} else {
					pStmt_d.setString(1, null);
				}
				if (df_id != -1) {
					pStmt_d.setInt(2, df_id);
				} else {
					pStmt_d.setInt(2, -1);
				}
				flag_d = pStmt_d.executeUpdate();
			}

			// SQL文を実行する
			if (pStmt_u.executeUpdate() == 1 && flag_l == 1 && flag_d == 1) {
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
	// user：変更後のデータ、pre_user:変更前のデータ
	public boolean update(User user, User pre_user) {
		Connection conn = null;
		boolean result = false;
		try {
			// JDBCドライバを読み込む
			Class.forName("org.h2.Driver");
			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:h2:file:C:/pleiades/workspace/data/NMW", "sa", "");
			// SQL文を準備する
			String sql_u = "update users set password=?, where u_id=?";
			String sql_l = "update user_likefoods set lf_id=? where u_id like ? and lf_id like ?";
			String sql_d = "update user_dislikefoods set df_id=? where u_id like ? and df_id like ?";

			PreparedStatement pStmt_u = conn.prepareStatement(sql_u);
			PreparedStatement pStmt_l = conn.prepareStatement(sql_l);
			PreparedStatement pStmt_d = conn.prepareStatement(sql_d);

			int flag_l = -1;
			int flag_d = -1;

			// SQL文を完成させる
			if (user.getPassword() != null && !user.getPassword().equals("")) {
				pStmt_u.setString(1, user.getPassword());
			} else {
				pStmt_u.setString(1, null);
			}
			if (user.getU_id() != null && !user.getU_id().equals("")) {
				pStmt_u.setString(2, "%" + user.getU_id() + "%");
			} else {
				pStmt_u.setString(2, "%");
			}

			for (int i=0; i< user.getLf_id().size(); i++) {
				if (user.getLf_id() != null) {
					pStmt_l.setInt(1, user.getLf_id().get(i));
				} else {
					pStmt_l.setInt(1, -1);
				}
				if (user.getU_id() != null && !user.getU_id().equals("")) {
					pStmt_l.setString(2, user.getU_id());
				} else {
					pStmt_l.setString(2, null);
				}
				if (pre_user.getDf_id() != null) {
					pStmt_l.setString(3, "%" + pre_user.getLf_id().get(i) + "%");
				} else {
					pStmt_l.setString(3, "%");
				}
				flag_l = pStmt_l.executeUpdate();
			}

			for (int i=0; i< user.getDf_id().size(); i++) {
				if (user.getDf_id() != null) {
					pStmt_d.setInt(1, user.getDf_id().get(i));
				} else {
					pStmt_d.setInt(1, -1);
				}
				if (user.getU_id() != null && !user.getU_id().equals("")) {
					pStmt_d.setString(2, "%" + user.getU_id() + "%");
				} else {
					pStmt_d.setString(2, "%");
				}
				if (pre_user.getDf_id() != null) {
					pStmt_d.setString(3, "%" + pre_user.getDf_id().get(i) + "%");
				} else {
					pStmt_d.setString(3, "%");
				}
				flag_d = pStmt_d.executeUpdate();
			}
			// SQL文を実行する
			if (pStmt_u.executeUpdate() == 1 && flag_l == 1 && flag_d == 1) {
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
			if (number != null && ! number.equals("")) {
				pStmt.setString(1, number);
			} else {
				pStmt.setString(1, null);
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
}
