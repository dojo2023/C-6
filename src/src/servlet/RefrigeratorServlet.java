package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.RefrigeratorDAO;
import model.Bc;
import model.Refrigerator;



/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/RefrigeratorServlet")
public class RefrigeratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/// もしもログインしていなかったらログインサーブレットにリダイレクトする
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("/simpleBC/LoginServlet");
			return;
		}

		request.setCharacterEncoding("UTF-8");

		RefrigeratorDAO rDAO = new RefrigeratorDAO();
		List<Refrigerator> refrigerator = rDAO.select(null)


		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/refrigerator.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメータを取得する
		request.setCharacterEncoding("UTF-8");
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
//			request.setAttribute("result",
//			new Result("ログイン失敗！", "IDまたはPWに間違いがあります。", "/NMW/LoginServlet"));

			// 結果ページにフォワードする
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
		}
	}
}
