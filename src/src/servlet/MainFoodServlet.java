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

import dao.MainFoodDAO;
import model.MainFood;

/**
 * Servlet implementation class MainFoodServlet
 */
@WebServlet("/MainFoodServlet")
public class MainFoodServlet extends HttpServlet {
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
		// リクエストパラメータを取得する
		request.setCharacterEncoding("UTF-8");

		String f_id_s = request.getParameter("f_id");
		int f_id = Integer.parseInt(f_id_s);

		MainFoodDAO mDAO = new MainFoodDAO();
		List<MainFood> cardList = mDAO.select(new MainFood(f_id));

		// 結果ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/mainFood.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
