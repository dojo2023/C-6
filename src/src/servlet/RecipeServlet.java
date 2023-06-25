package servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.RecipeDAO;
import model.LoginUser;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("/NMW/LoginServlet");
			return;
		}
		request.setCharacterEncoding("UTF-8");

		LoginUser loginUser = (LoginUser) session.getAttribute("id");

		Date sqlNow = new Date(System.currentTimeMillis()); // util.date => sql.dateへの変換
		java.util.Date utilDate = sqlNow;

		// リクエストパラメータを取得する
		// r_count=0は非表示

		int rec_id = -1;
		if (request.getParameter("search_key") != null) {
			rec_id = Integer.parseInt(request.getParameter("search_key").toString());
		}

//		Recipe r = new Recipe(rec_id, loginUser.getId(), new Date(utilDate.getTime()));
//		Recipes recipes = new Recipes(r);

		Recipe r = new Recipe(rec_id);
		Recipes recipes = new Recipes(r);

		RecipeDAO rDAO = new RecipeDAO();
		List<Recipe> recipe = rDAO.select(recipes);
		for (Recipe recipe2 : recipe) {
			System.out.println("R_name:" + recipe2.getR_name());

		}

//		System.out.println("============================");
//		System.out.println(recipe);
//		System.out.println(recipe.get(0).getImage());
//		System.out.println("============================");

		// 検索結果をリクエストスコープに格納する
		request.setAttribute("recipe", recipe);

		// 結果ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/recipe.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// レシピ詳細(Recipe)DoPost→主要食材(MainFood?)DoGet
		// レシピ詳細→主要食材のDoPostを使用する為のif文
		if ((request.getParameter("r_recipe")) == "1") {
			request.setAttribute("foods", request.getParameter("ingredient"));
			// 結果ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/NMW/MainFoodServlet");
			dispatcher.forward(request, response);
		}

		// レシピ回数カウント処理
		// レシピ詳細にレシピ使用回数を送るDoPostを使用する為のif文
		else if ((request.getParameter("r_recipe")) == "2") {
			HttpSession session = request.getSession();
			LoginUser loginUser = (LoginUser) session.getAttribute("id");

			Date sqlNow = new Date(System.currentTimeMillis()); // util.date => sql.dateへの変換
			java.util.Date utilDate = sqlNow;

			// リクエストパラメータを取得する
			int rec_id = -1;
			if (request.getAttribute("rec_id") != null) {
				rec_id = Integer.parseInt(request.getAttribute("rec_id").toString());
			}

			Recipe r = new Recipe(rec_id, loginUser.getId(), new Date(utilDate.getTime()));
			RecipeDAO rDAO = new RecipeDAO();

			// (r_count)の有無によるinsertとupdateをわけるif文
			Recipes recipes = new Recipes(r);
			List<Recipe> recipe = rDAO.select(recipes);

			if (recipe.get(0).getR_count() == -1.0) {
				rDAO.insert(r);
			} else {
				recipe.get(0).setR_count(recipe.get(0).getR_count() + 1);
				rDAO.update(recipe.get(0), recipe.get(0));
			}
			// レシピ使用回数をリクエストスコープに格納する
			request.setAttribute("recipe", recipe.get(0).getR_count());
			// レシピ詳細.jspにフォワードする
			RequestDispatcher dispatcher1 = request.getRequestDispatcher("/WEB-INF/jsp/recipe.jsp");
			dispatcher1.forward(request, response);


			//食材の増減
			//レシピID(rec_id)を受け取る
			// レシピID(rec_id)をリクエストスコープに格納する
			request.setAttribute("rec_id",rec_id );
			// 冷蔵庫サーブレットにフォワードする
			RequestDispatcher dispatcher2 = request.getRequestDispatcher("/NMW/RefrigeratorServlet");
			dispatcher2.forward(request, response);


			// レシピ使用回数リセット時の処理
		} else if ((request.getParameter("r_recipe")) == "3") {
			HttpSession session = request.getSession();
			LoginUser loginUser = (LoginUser) session.getAttribute("id");

			Date sqlNow = new Date(System.currentTimeMillis()); // util.date => sql.dateへの変換
			java.util.Date utilDate = sqlNow;

			// リクエストパラメータを取得する
			int rec_id = -1;
			if (request.getAttribute("rec_id") != null) {
				rec_id = Integer.parseInt(request.getAttribute("rec_id").toString());
			}

			Recipe r = new Recipe(rec_id, loginUser.getId(), new Date(utilDate.getTime()));
			RecipeDAO rDAO = new RecipeDAO();
			r.setR_count(r.getR_count() - 1);
			rDAO.update(r, r);
		}
	}
}
