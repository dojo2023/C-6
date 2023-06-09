package servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CalendarDAO;
import dao.RecipeDAO;
import dao.RefrigeratorDAO;
import model.Calendar;
import model.LoginUser;
import model.Recipe;
import model.Recipes;
import model.Refrigerator;

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

//		Recipe r_c = new Recipe(rec_id, loginUser.getId(), new Date(utilDate.getTime()));
//		Recipes recipeR_c = new Recipes(r_c);
//
//		List<Recipe> recipeR_count = rDAO.select(recipeR_c);
//
//		for (Recipe recipe2 : recipeR_count) {
//			System.out.println(recipe2.getR_count());
//		}


		//		System.out.println("============================");
		//		System.out.println(recipe);
		//		System.out.println(recipe.get(0).getImage());
		//		System.out.println("============================");

		// 検索結果をリクエストスコープに格納する
		request.setAttribute("recipe", recipe);
//		request.setAttribute("recipeR_count", recipeR_count);

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
		if ((request.getParameter("r_recipe")).equals("1")) {
			request.setAttribute("foods", request.getParameter("ingredient"));
			// 結果ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/NMW/MainFoodServlet");
			dispatcher.forward(request, response);



			// レシピ回数カウント処理
			// レシピ詳細にレシピ使用回数を送るDoPostを使用する為のif文
		} else if ((request.getParameter("r_recipe")).equals("2")) {
			HttpSession session = request.getSession();
			LoginUser loginUser = (LoginUser) session.getAttribute("id");

			Date sqlNow = new Date(System.currentTimeMillis()); // util.date => sql.dateへの変換
			java.util.Date utilDate = sqlNow;

			// リクエストパラメータを取得する
			int rec_id = -1;
			if (request.getParameter("rec_id") != null) {
				rec_id = Integer.parseInt(request.getParameter("rec_id").toString());
			}

			// (r_count)の有無によるinsertとupdateをわけるif文
			RecipeDAO rDAO = new RecipeDAO();
			Recipe r = new Recipe(rec_id, loginUser.getId(), new Date(utilDate.getTime()));
			Recipes recipes = new Recipes(r);
			List<Recipe> recipe = rDAO.selectDate(recipes);

//			System.out.println("=====================");
//			for (Recipe recipe2 : recipe) {
//				System.out.println("getF_id:"+recipe2.getRec_id());
//				System.out.println("getU_id:"+recipe2.getU_id());
//				System.out.println("getR_date:"+recipe2.getR_date());
//				System.out.println("getR_date:"+recipe2.getR_count());
//			}
//			System.out.println("=====================");
//			System.out.println("getRec_id:"+r.getRec_id());
//			System.out.println("getU_id:"+r.getU_id());
//			System.out.println("getR_date:"+r.getR_date());

			// ない場合は追加
			if (recipe==null || recipe.size() == 0) {
				System.out.println("追加します");
				rDAO.insertRecipe_counts(r);
				recipe = rDAO.selectDate(new Recipes(r));
				recipe.get(0).setR_count(0);
			}

			// 回数の増加
			recipe.get(0).setR_count(recipe.get(0).getR_count() + 1);
			rDAO.updateCount(recipe.get(0), recipe.get(0));

			// カレンダーへの追加
			CalendarDAO cDAO = new CalendarDAO();
			RefrigeratorDAO refDAO = new RefrigeratorDAO();
			List<Refrigerator> refrigerator = refDAO.select(new Refrigerator(loginUser.getId()));
			List<Calendar> calendars =  cDAO.selectDate(new model.Calendar(loginUser.getId(), r.getRec_id(), new Date(utilDate.getTime())));
			// 存在しない場合は追加
			if (calendars == null || calendars.size()<=0) {
				cDAO.insert(new model.Calendar(loginUser.getId(), refrigerator.get(0).getRef_id(), new Date(utilDate.getTime())));
			}

			// レシピ使用回数をリクエストスコープに格納する
			request.setAttribute("recipe", recipe.get(0).getR_count());

			r = new Recipe(rec_id, loginUser.getId(), new Date(utilDate.getTime()));
			recipes = new Recipes(r);

			rDAO = new RecipeDAO();
			recipe = rDAO.selectDate(recipes);

			// 検索結果をリクエストスコープに格納する
			request.setAttribute("rec_id", rec_id);
			request.setAttribute("recipe", recipe);

			// 冷蔵庫から在庫の減少
			//冷蔵庫のデータを算出する
			LoginUser loginUser_f = (LoginUser) session.getAttribute("id");
			RefrigeratorDAO rfDAO = new RefrigeratorDAO();
			List<Refrigerator> refrigerator_f = rfDAO.select(new Refrigerator(loginUser_f.getId()));
			List<Refrigerator> pre_refrigerator_f = rfDAO.select(new Refrigerator(loginUser_f.getId()));

			// f_idのリストの作成
			// aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
			List<Integer> f_id = new ArrayList<Integer>();

			for (String ing : recipe.get(0).getIngredient()) {
				Recipe rec = new Recipe();
				rec.setIngredient(ing);

				List<Recipe> rec_l = rDAO.select(new Recipes(rec));
				f_id.add(rec_l.get(0).getF_id());
			}

			//冷蔵庫の食材の個数からレシピの食材の個数を引く
			for (Refrigerator refrigerator_f_list : refrigerator_f) {
				for (int i = 0; i < f_id.size(); i++) {
					//レシピの食材IDと冷蔵庫の食材IDが同じだったら
					if (f_id.get(i) != -1 && f_id.get(i) == refrigerator_f_list.getF_id()) {
						if (refrigerator_f_list.getF_count() - recipe.get(0).getR_i_count().get(i) < 0) {
							refrigerator_f_list.setF_count(0);	// 0以下の時は0に
						} else {
							refrigerator_f_list.setF_count(refrigerator_f_list.getF_count() - recipe.get(0).getR_i_count().get(i));
						}
						break;
					}

				}
			}

			//変更があった食材一覧から一つずつ取り出して、冷蔵庫の中身を更新した
			for (int i = 0; i < refrigerator_f.size(); i++) {
				rfDAO.update(refrigerator_f.get(i), pre_refrigerator_f.get(i));
			}

			// レシピ詳細.jspにフォワードする
			RequestDispatcher dispatcher1 = request.getRequestDispatcher("/WEB-INF/jsp/recipe.jsp");
			dispatcher1.forward(request, response);



			// レシピ使用回数リセット時の処理
		} else if ((request.getParameter("r_recipe")).equals("3")) {
			HttpSession session = request.getSession();
			LoginUser loginUser = (LoginUser) session.getAttribute("id");

			Date sqlNow = new Date(System.currentTimeMillis()); // util.date => sql.dateへの変換
			java.util.Date utilDate = sqlNow;

			// リクエストパラメータを取得する
			int rec_id = -1;
			if (request.getParameter("rec_id") != null) {
				rec_id = Integer.parseInt(request.getParameter("rec_id").toString());
			}

			// (r_count)の有無によるinsertとupdateをわけるif文
			RecipeDAO rDAO = new RecipeDAO();
			Recipe r = new Recipe(rec_id, loginUser.getId(), new Date(utilDate.getTime()));
			Recipes recipes = new Recipes(r);
			List<Recipe> recipe = rDAO.select(recipes);

			// ない場合は追加
			if (recipe==null || recipe.size() == 0) {
				rDAO.insert(r);
				recipe = rDAO.select(new Recipes(r));
				recipe.get(0).setR_count(0);
			}

			// 回数の減少
			if (recipe.get(0).getR_count() > 0) {
				recipe.get(0).setR_count(recipe.get(0).getR_count() - 1);
			}
			rDAO.updateCount(recipe.get(0), recipe.get(0));

			// レシピ使用回数をリクエストスコープに格納する
			request.setAttribute("recipe", recipe.get(0).getR_count());

			r = new Recipe(rec_id);
			recipes = new Recipes(r);

			rDAO = new RecipeDAO();
			recipe = rDAO.select(recipes);

			// 検索結果をリクエストスコープに格納する
			request.setAttribute("rec_id", rec_id);
			request.setAttribute("recipe", recipe);

			// レシピ詳細.jspにフォワードする
			RequestDispatcher dispatcher1 = request.getRequestDispatcher("/WEB-INF/jsp/recipe.jsp");
			dispatcher1.forward(request, response);
		}
	}
}
