package servlet;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MainFoodDAO;
import dao.RecipeDAO;
import model.MainFood;
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
		int rec_id = -1;
		if (request.getAttribute("rec_id") != null) {
			rec_id = Integer.parseInt(request.getAttribute("rec_id").toString());
		}
		Recipe r = new Recipe(rec_id);
		Recipes rs = new Recipes(r);

		// 検索処理を行う
		RecipeDAO rDao = new RecipeDAO();
		List<Recipe> recipeList = rDao.select(rs);

		// レシピ検索時のボタン作成
		MainFoodDAO mDAO = new MainFoodDAO();
		List<MainFood> mainFood = mDAO.select(new MainFood());

		request.setCharacterEncoding("UTF-8");

		// 検索結果をリクエストスコープに格納する
		request.setAttribute("recipeList", recipeList);
		request.setAttribute("mainFood", mainFood);

		// 結果ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/recipeList.jsp");
		dispatcher.forward(request, response);

//		// setした値をgetAttributeで取得して、selectで検索する（主要食材）
//		Recipes recipes = (Recipes)request.getAttribute("recipes");
//		// 複数要素によるレシピ検索処理を行う
//		RecipeDAO reDao = new RecipeDAO();
//		List<Recipe> recipesList = reDao.select(recipes);//	これなに？？？？？？？？？？？？？？？？？？？？？
//		Recipes recipes = (Recipes)request.getAttribute("recipes");
//		// 複数要素によるレシピ検索処理を行う
//		RecipeDAO reDao = new RecipeDAO();
//		List<Recipe> recipesList = reDao.select(recipes);

//		// setした値をgetAttributeで取得して、selectで検索する（主要食材）
//		Recipes recipes = (Recipes)request.getAttribute("f_name");
//		RecipeDAO reDao = new RecipeDAO();
//		List<Recipe> recipesList = reDao.select(recipes);
//		request.setAttribute("recipesList", recipesList);
//		RequestDispatcher dispatcher4 = request.getRequestDispatcher("/WEB-INF/jsp/recipeList.jsp");
//		dispatcher4.forward(request, response);


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
		if (request.getParameter("r_select").equals("1")) {
			if (request.getParameter("f_id") != null) {
				// jspから検索条件をgetParameterで抽出する（主要食材）
				int f_id = Integer.parseInt(request.getParameter("f_id"));
//				System.out.println(Integer.parseInt(request.getParameter("f_id")));
				Recipe r = new Recipe();
				r.setF_id(f_id);
//				System.out.println(r.getF_id());
				Recipes f_id_s = new Recipes(r);
				// jspから検索条件をgetParameterで抽出する（ワンパン等）
//			String[] c_id_list = request.getParameterValues("c_id");
//			List<Boolean> c_id = new ArrayList<Boolean>();
//			for (String c_list : c_id_list) {
//				c_id.add(Boolean.parseBoolean(c_list));
//			}
//
//			Recipes recipes = new Recipes();
//			List<Recipe> recipe_list = new ArrayList<Recipe>();
//
//			for (int i = 0; i < f_name.length; i++) {
//				recipe_list.add(new Recipe(f_name[i], c_id.get(0), c_id.get(1), c_id.get(2)));
//			}
//			recipes.setRecipes(recipe_list);

				// setAttribute()で設定する
//			request.setAttribute("recipes", recipes);
//			request.setAttribute("f_name", f_name);
				// System.out.println("aaaa");
//			//  同サーブレット内にフォワードする
//			RequestDispatcher dispatcherC = request.getRequestDispatcher("/NMW/RecipeListServlet");
//			dispatcherC.forward(request, response);
				// setした値をgetAttributeで取得して、selectで検索する（主要食材）
//			Recipes recipes = (Recipes)request.getAttribute("f_name");
				RecipeDAO reDao = new RecipeDAO();
				List<Recipe> recipesList = reDao.selectF_id(f_id_s);

				// レシピ検索時のボタン作成
				MainFoodDAO mDAO = new MainFoodDAO();
				List<MainFood> mainFood = mDAO.select(new MainFood());

				request.setAttribute("recipesList", recipesList);
				request.setAttribute("mainFood", mainFood);
				RequestDispatcher dispatcher4 = request.getRequestDispatcher("/WEB-INF/jsp/recipeList.jsp");
				dispatcher4.forward(request, response);

			// buttonを押したときの処理
			} else if(request.getParameter("button") != null){
				// jspから検索条件をgetParameterで抽出する（主要食材）
				String button = request.getParameter("button");
//				System.out.println("button:"+request.getParameter("button"));
				RecipeDAO reDao = new RecipeDAO();
				Recipe r = new Recipe();
				List<Recipe> recipesList = reDao.select(new Recipes(new Recipe()));

				// 最大値の更新
				int max = 0;
				for (Recipe recipe : recipesList) {
					if (recipe.getRec_id() > max) {
						max = recipe.getRec_id();
					}
				}

				switch(button) {
					case "wanpan":
						r.setWanpan(true);
						recipesList = reDao.selectWanpan(new Recipes(r));
						break;
					case "renzi":
						r.setMicrowave_oven(true);
						recipesList = reDao.selectMicrowave_oven(new Recipes(r));
						break;
					case "zitan":
						r.setSave_time(true);
						recipesList = reDao.selectSave_time(new Recipes(r));
						break;
					default:
						Random rand = new Random();
					    r.setRec_id(rand.nextInt(max)+1);
					    recipesList = reDao.select(new Recipes(r));
					    break;
				}


				// レシピ検索時のボタン作成
				MainFoodDAO mDAO = new MainFoodDAO();
				List<MainFood> mainFood = mDAO.select(new MainFood());

				request.setAttribute("recipesList", recipesList);
				request.setAttribute("mainFood", mainFood);
				RequestDispatcher dispatcher4 = request.getRequestDispatcher("/WEB-INF/jsp/recipeList.jsp");
				dispatcher4.forward(request, response);
			}


		} if ((request.getParameter("r_select")) == "2") {
			String rec_id = (request.getParameter("rec_id"));

			request.setAttribute("rec_id", rec_id);

			// レシピ詳細ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/NMW/RecipeServlet");
			dispatcher.forward(request, response);
		}
	}

}