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

		//レシピ一覧の表示
		List<Integer> rec_id = new ArrayList<Integer>();;
		List<Recipe> recipes1 = (List<Recipe>)request.getAttribute("recipes");
		if (recipes1.get(0) != null) {
			for (Recipe r : recipes1) {
				rec_id.add(r.getRec_id());
			}
		} else {
			rec_id.add(-1);
		}

		List<Recipe> recipes2 = new ArrayList<Recipe>();
		for (int r : rec_id) {
			recipes2.add(new Recipe(r));
		}
		Recipes rs = new Recipes();
		rs.setRecipes(recipes2);

		// 検索処理を行う
		RecipeDAO rDao = new RecipeDAO();
		List<Recipe> recipeList = rDao.select(rs);

		// 検索結果をリクエストスコープに格納する
		request.setAttribute("recipeList", recipeList);

		// 結果ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/recipeList.jsp");
		dispatcher.forward(request, response);

		// setした値をgetAttributeで取得して、selectで検索する（主要食材）
/*
 		Recipes recipes = (Recipes) request.getAttribute("recipes");
		// 複数要素によるレシピ検索処理を行う
		RecipeDAO reDao = new RecipeDAO();
		List<Recipe> recipesList = reDao.select(recipes);
*/
		/* 検索の時と、詳細表示の2つのformの識別(valueで)
		 * <form name = "" value="">を使ってサーブレットのgetAttriで受け取れる
		 * form name（呼び出し名）は統一。value（要素）を分ける。 */
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

		// レシピ検索から検索結果に行くほうのform文をif文で作成
		if ((request.getParameter("r_select")) == "1") {

			// jspから検索条件をgetParameterで抽出する（主要食材）
			String[] f_name = request.getParameterValues("f_name");

			// jspから検索条件をgetParameterで抽出する（ワンパン等）
			String[] c_id_list = request.getParameterValues("c_id");
			List<Boolean> c_id = new ArrayList<Boolean>();
			for (String c_list : c_id_list) {
				c_id.add(Boolean.parseBoolean(c_list));
			}

			Recipes recipes = new Recipes();
			List<Recipe> r_l = new ArrayList<Recipe>();

			for (int i = 0; i < f_name.length; i++) {
				r_l.add(new Recipe(f_name[i], c_id.get(0), c_id.get(1), c_id.get(2)));
			}
			recipes.setRecipes(r_l);

			// 複数要素によるレシピ検索処理を行う
			RecipeDAO reDao = new RecipeDAO();
			List<Recipe> recipe_list = reDao.select(recipes);

			// setAttribute()で設定する
			request.setAttribute("recipes", recipe_list);

			//  同サーブレット内にフォワードする
			RequestDispatcher dispatcherC = request.getRequestDispatcher("/NMW/RecipeListServlet");
			dispatcherC.forward(request, response);

		// レシピ一覧からレシピ詳細へフォワード処理
		// リクエストパラメータを取得する
		// レシピ検索から検索結果に行くほうのform文をif文で作成
		} else if ((request.getParameter("r_select")) == "2") {
			String rec_id = (request.getParameter("rec_id"));

			request.setAttribute("rec_id", rec_id);

			// レシピ詳細ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/NMW/RecipeServlet");
			dispatcher.forward(request, response);
		}
	}

}
