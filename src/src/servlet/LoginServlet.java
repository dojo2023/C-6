package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import model.LoginUser;
import model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		request.setAttribute("error", "");
		// ログインページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメータを取得する
		request.setCharacterEncoding("UTF-8");

		// inputのLOGINを押されたとき、以下の式にLOGINが返される
		if (request.getParameter("LOGIN") != null) {
			String id = request.getParameter("ID");
			String pw = request.getParameter("PW");

			// ログイン処理を行う
			UserDAO uDAO = new UserDAO();
			if (uDAO.selectIdPwPo(new User(id, pw))) { // ログイン成功
				// セッションスコープにIDを格納する
				HttpSession session = request.getSession();
				session.setAttribute("id", new LoginUser(id));

				// メニューサーブレットにリダイレクトする
				response.sendRedirect("/NMW/RefrigeratorServlet");
			} else { // ログイン失敗
				// リクエストスコープに、タイトル、メッセージ、戻り先を格納する
//				request.setAttribute("result",
//				new Result("ログイン失敗！", "IDまたはPWに間違いがあります。", "/NMW/LoginServlet"));

				request.setAttribute("error", "ID、パスワードが一致しません。");

				// 結果ページにフォワードする
//				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
				dispatcher.forward(request, response);
			}
		} else {
			response.sendRedirect("/NMW/RegisterServlet");
		}


	}
}
