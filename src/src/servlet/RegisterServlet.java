package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import model.User;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ログインしてたら登録ページにフォワードする（URL変わらない）
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメータを取得する
		request.setCharacterEncoding("UTF-8");
		String u_id = request.getParameter("ID");
		String password = request.getParameter("PW");

		List<Integer> lf_id = new ArrayList<Integer>();
		String lf_id_s = (String)request.getAttribute("Lf_id");
		if (lf_id_s != null) {	// null判定
			String lf_id_s_l[] = lf_id_s.split(",");
			for(int i = 0; i < lf_id_s_l.length;i++) {
				lf_id.add(Integer.parseInt(lf_id_s_l[i]));
			}
		} else {
			lf_id.add(-1);	// nullの場合は-1
		}


		List<Integer> df_id = new ArrayList<Integer>();
		String df_id_s = (String)request.getAttribute("Df_id");
		if (df_id_s != null) {
			String df_id_s_l[] = df_id_s.split(",");
			for(int i = 0; i < df_id_s_l.length;i++) {
				df_id.add(Integer.parseInt(df_id_s_l[i]));
			}
		} else {
			df_id.add(-1);
		}


		// 登録処理を行う
		// 登録成功
		UserDAO rDAO = new UserDAO();
		if (rDAO.insert(new User(u_id, password, lf_id, df_id))) {
			request.setAttribute("result", "/NMW/LoginServlet");
		}
		// 登録失敗
		else {
			request.setAttribute("result", "/NMW/RegisterServlet");
		}

	}

}
