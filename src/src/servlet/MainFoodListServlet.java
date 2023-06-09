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
 * Servlet implementation class MainFoodListServlet
 */
@WebServlet("/MainFoodListServlet")
public class MainFoodListServlet extends HttpServlet {
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
		MainFoodDAO mDAO = new MainFoodDAO();
		List<MainFood> mainFood = mDAO.select(new MainFood());
		request.setAttribute("mainFood", mainFood);

		// 結果ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/mainFoodList.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータを取得する
		request.setCharacterEncoding("UTF-8");

		int f_id = Integer.parseInt(request.getParameter("foods"));

//		request.setCharacterEncoding("UTF-8");
//		request.setAttribute("f_id", f_id);
//		MainFoodDAO mDAO = new MainFoodDAO();
//		List<MainFood> mainFood = mDAO.select(new MainFood());
//		request.setAttribute("mainFood", mainFood);

		// リクエストパラメータを取得する

		MainFoodDAO mDAO = new MainFoodDAO();
		List<MainFood> mainFood = mDAO.select(new MainFood(f_id));

		// 検索結果をリクエストスコープに格納する
		request.setAttribute("mainFood", mainFood);

		// 結果ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/mainFood.jsp");
		dispatcher.forward(request, response);

		// 結果ページにフォワードする
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/MainFoodServlet");
//		dispatcher.forward(request, response);


	}

}
