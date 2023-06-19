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

import dao.UserDAO;
import model.LoginUser;
import model.User;

/**
 * Servlet implementation class MyPageServlet
 */
@WebServlet("/MyPageServlet")
public class MyPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
				response.sendRedirect("/NMW/LoginServlet");
				return;
		}
		request.setCharacterEncoding("UTF-8");

		UserDAO uDAO = new UserDAO();

		// user idからユーザー情報(マイページ)をsetしてる
		LoginUser loginUser = (LoginUser) session.getAttribute("id");
		List<User> user_inf = uDAO.selectLfDf(new User(loginUser.getId()));

		request.setAttribute("user_inf", user_inf);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/myPage.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
				response.sendRedirect("/NMW/LoginServlet");
				return;
		}
		//myPageEditサーブレットへユーザーIDを持った状態で遷移
		request.setAttribute("id" ,request.getParameter("id"));

		RequestDispatcher dispatcher = request.getRequestDispatcher("/NMW/myPageEditServlet");
		dispatcher.forward(request, response);
	}

}
