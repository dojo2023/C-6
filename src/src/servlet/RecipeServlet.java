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

import dao.RecipeDAO;
import model.Recipe;
import model.Recipes;


/**
 * Servlet implementation class RecipeServlet
 */
@WebServlet("/RecipeServlet")
public class RecipeServlet extends HttpServlet {
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

				// リクエストパラメータを取得する
				  int rec_id=(Integer)(request.getAttribute("rec_id"));
				  Recipe r = new Recipe(rec_id);
				  Recipes recipes = new Recipes(r);

				  RecipeDAO rDAO = new RecipeDAO();
				  List<Recipe> recipe = rDAO.select(recipes);

					// 検索結果をリクエストスコープに格納する
				  request.setAttribute("recipe", recipe);

				// 結果ページにフォワードする
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/recipe.jsp");
				dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// レシピ詳細(Recipe)DoPost→主要食材(MainFood?)DoGet
		request.setAttribute("foods", request.getParameter("ingredient"));
		// 結果ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/NMW/MainFoodServlet");
		dispatcher.forward(request, response);
	}

}
