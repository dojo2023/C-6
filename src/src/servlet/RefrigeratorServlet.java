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
import model.LoginUser;
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

		// user idからその人の冷蔵庫をsetしてる
		LoginUser loginUser = (LoginUser) session.getAttribute("id");
		List<Refrigerator> refrigerator = rDAO.select(new Refrigerator(loginUser.getId()));

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
		HttpSession session = request.getSession();

		RefrigeratorDAO rDAO = new RefrigeratorDAO();

		// user idからその人の冷蔵庫をsetしてる
		LoginUser loginUser = (LoginUser) session.getAttribute("id");
		List<Refrigerator> refrigerator = rDAO.select(new Refrigerator(loginUser.getId()));

		int f_id = ((Integer) (request.getAttribute("foods"))).intValue();
		List<Refrigerator> pre_refrigerator = rDAO.select(new Refrigerator(f_id));

		// 値を変更する処理
		// set~~~~~

		// updateする
		rDAO.update(refrigerator.get(0), pre_refrigerator.get(0));

		RequestDispatcher dispatcher = request.getRequestDispatcher("/NMW/RefrigeratorServlet");
		dispatcher.forward(request, response);
	}
}
