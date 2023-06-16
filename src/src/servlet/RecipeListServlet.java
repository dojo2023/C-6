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
import javax.servlet.http.HttpSession;

import dao.RecipeDAO;
import model.Recipe;
import model.Recipes;


/**
 * Servlet implementation class RecipeListServlet
 */
@WebServlet("/RecipeListServlet")
public class RecipeListServlet extends HttpServlet {
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

		int rec_id = Integer.parseInt(request.getParameter("rec_id"));

		// 検索処理を行う
		RecipeDAO rDao = new RecipeDAO();
		List<Recipe> recipeList = rDao.select(new Recipe(rec_id));

		// 検索結果をリクエストスコープに格納する
		request.setAttribute("recipeList", recipeList);

		// 結果ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/recipeList.jsp");
		dispatcher.forward(request, response);

		// setした値をgetAttributeで取得して、selectで検索する（主要食材）
		Recipes recipes = (Recipes)request.getAttribute("recipes");
		// 複数要素によるレシピ検索処理を行う
				RecipeDAO reDao = new RecipeDAO();
				List<Recipe> recipesList = reDao.select(recipes);

		// 検索の時と、詳細表示の2つのformの識別(valueで)
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("/NMW/LoginServlet");
			return;
		}
		request.setCharacterEncoding("UTF-8");
		// jspから検索条件をgetParameterで抽出する
		// setAttribute()で設定する
		// RecipeListServletに送る

		// jspから検索条件をgetParameterで抽出する（主要食材）
		String[] f_name = request.getParameterValues("f_name");
//		List<String> f_name = new ArrayList<>();
//		for(String f_list: f_name_list) {
//			f_name.add(f_list);
//		}
		// jspから検索条件をgetParameterで抽出する（ワンパン等）
		String[] c_id_list = request.getParameterValues("c_id");
		List<Boolean> c_id = new ArrayList<Boolean>();
		for(String c_list: c_id_list) {
			c_id.add(Boolean.parseBoolean(c_list));
		}

		Recipes recipes = new Recipes();
		List<Recipe> recipe_list = new ArrayList<Recipe>();

		for(int i=0; i<f_name.length; i++) {
			recipe_list.add(new Recipe(f_name[i], c_id.get(0), c_id.get(1), c_id.get(2)));
		}
		recipes.setRecipes(recipe_list);

		// setAttribute()で設定する
		request.setAttribute("recipes", recipes);

		//  同サーブレット内にフォワードする
		RequestDispatcher dispatcherC = request.getRequestDispatcher("/NMW/src/servlet/RecipeListServlet.java");
		dispatcherC.forward(request, response);

		// レシピ一覧からレシピ詳細へフォワード処理
		// リクエストパラメータを取得する

		String rec_id = (request.getParameter("rec_id"));

		request.setAttribute("rec_id", rec_id);

		// レシピ詳細ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/NMW/RecipeServlet.java");
		dispatcher.forward(request, response);

		// ↓流れ
		// jspから検索条件をgetParameterで抽出する（完成）

		// setAttribute()で設定する
		// setした値をgetAttributeで取得して、selectで検索する
		// RecipeListServletに送る
	}

}
