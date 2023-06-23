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

import dao.MainFoodDAO;
import dao.RecipeDAO;
import dao.RefrigeratorDAO;
import model.LoginUser;
import model.MainFood;
import model.Recipe;
import model.Recipes;
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
			response.sendRedirect("/NMW/LoginServlet");
			return;
		}

		request.setCharacterEncoding("UTF-8");
		RefrigeratorDAO rDAO = new RefrigeratorDAO();
		MainFoodDAO mDAO = new MainFoodDAO();
		List<Recipe> mainFoodUnit;

		// user idからその人の冷蔵庫をsetしてる
		LoginUser loginUser = (LoginUser) session.getAttribute("id");
		List<Refrigerator> refrigerator = rDAO.select(new Refrigerator(loginUser.getId()));
		List<MainFood> mainFood = mDAO.select(new MainFood());
		List<MainFood> mainFoodImg = rDAO.selectImg(new Refrigerator(loginUser.getId()));	// imgの取得
		if (refrigerator != null) {
			mainFoodUnit = rDAO.selectUnit(refrigerator);// Unitの取得
		} else {
			mainFoodUnit = rDAO.selectUnit(new ArrayList<Refrigerator>());
		}

		request.setAttribute("refrigerator", refrigerator);
		request.setAttribute("mainFood", mainFood);
		request.setAttribute("mainFoodImg", mainFoodImg);
		request.setAttribute("mainFoodUnit", mainFoodUnit);
		if (refrigerator != null) {
			request.setAttribute("refrigeratorSize", refrigerator.size());
		} else if (mainFood != null) {
			request.setAttribute("mainFoodSize", mainFood.size());
		} else if (mainFoodImg != null) {
			request.setAttribute("mainFoodImgSize", mainFoodImg.size());
		}


		//冷蔵庫のサーブレットに送る
		//レシピIDを基にselect文でそのレシピに入っている複数の食材ID(f_id)とその食材の個数(r_i_count)を算出する（差分）
		if (request.getAttribute("rec_id") != null) {
			int rec_id = (Integer) (request.getAttribute("rec_id"));
			Recipe r = new Recipe(rec_id);
			Recipes recipes = new Recipes(r);

			RecipeDAO rIDAO = new RecipeDAO();
			List<Recipe> recipe = rIDAO.selectRecipeIngredient(recipes);

			//冷蔵庫のデータを算出する
			LoginUser loginUser_f = (LoginUser) session.getAttribute("id");
			RefrigeratorDAO rfDAO = new RefrigeratorDAO();
			List<Refrigerator> refrigerator_f = rfDAO.select(new Refrigerator(loginUser_f.getId()));
			List<Refrigerator> pre_refrigerator_f = rfDAO.select(new Refrigerator(loginUser_f.getId()));

			//冷蔵庫の食材の個数からレシピの食材の個数を引く
			for (Refrigerator refrigerator_f_list : refrigerator_f) {
				for (Recipe recipe_list : recipe) {
					//レシピの食材IDと冷蔵庫の食材IDが同じだったら
					if (recipe_list.getF_id() == refrigerator_f_list.getF_id()) {
						if (refrigerator_f_list.getF_count() - recipe_list.getR_i_count() < 0) {
							refrigerator_f_list.setF_count(0);	// 0以下の時は0に
						} else {
							refrigerator_f_list.setF_count(refrigerator_f_list.getF_count() - recipe_list.getR_i_count());
						}
						break;
					}
				}
			}

			//変更があった食材一覧から一つずつ取り出して、冷蔵庫の中身を更新した
			for (int i = 0; i < refrigerator_f.size(); i++) {
				rfDAO.update(refrigerator_f.get(i), pre_refrigerator_f.get(i));
			}
		}

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
		int f_id = Integer.parseInt(request.getParameter("food"));
		List<Refrigerator> refrigerator = rDAO.select(new Refrigerator(loginUser.getId(), f_id));
		List<Refrigerator> pre_refrigerator = rDAO.select(new Refrigerator(loginUser.getId(), f_id));

		RecipeDAO recDAO = new RecipeDAO();

		// (r_count)の有無によるinsertとupdateをわけるif文
		Recipes recipes = new Recipes(new Recipe(f_id));
		List<Recipe> recipe = recDAO.select(recipes);

		pre_refrigerator.get(0).setF_count(pre_refrigerator.get(0).getF_count() + 1);

		switch(recipe.get(0).getUnit()) {
			case 1:
				refrigerator.get(0).setF_count(refrigerator.get(0).getF_count() + 100);
			case 2:
			case 3:
			case 4:
			default:
				refrigerator.get(0).setF_count(refrigerator.get(0).getF_count() + 1);
		}

		// updateする
		rDAO.update(refrigerator.get(0), pre_refrigerator.get(0));

		view(request, response);

//		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/refrigerator.jsp");
//		dispatcher.forward(request, response);
	}

	public void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		request.setCharacterEncoding("UTF-8");

		RefrigeratorDAO rDAO = new RefrigeratorDAO();
		MainFoodDAO mDAO = new MainFoodDAO();
		List<Recipe> mainFoodUnit;

		// user idからその人の冷蔵庫をsetしてる
		LoginUser loginUser = (LoginUser) session.getAttribute("id");
		List<Refrigerator> refrigerator = rDAO.select(new Refrigerator(loginUser.getId()));
		List<MainFood> mainFood = mDAO.select(new MainFood());
		List<MainFood> mainFoodImg = rDAO.selectImg(new Refrigerator(loginUser.getId()));	// imgの取得
		if (refrigerator != null) {
			mainFoodUnit = rDAO.selectUnit(refrigerator);// Unitの取得
		} else {
			mainFoodUnit = rDAO.selectUnit(new ArrayList<Refrigerator>());
		}

		request.setAttribute("refrigerator", refrigerator);
		request.setAttribute("mainFood", mainFood);
		request.setAttribute("mainFoodImg", mainFoodImg);
		request.setAttribute("mainFoodUnit", mainFoodUnit);
		if (refrigerator != null) {
			request.setAttribute("refrigeratorSize", refrigerator.size());
		} else if (mainFood != null) {
			request.setAttribute("mainFoodSize", mainFood.size());
		} else if (mainFoodImg != null) {
			request.setAttribute("mainFoodImgSize", mainFoodImg.size());
		}


		//冷蔵庫のサーブレットに送る
		//レシピIDを基にselect文でそのレシピに入っている複数の食材ID(f_id)とその食材の個数(r_i_count)を算出する（差分）
		if (request.getAttribute("rec_id") != null) {
			int rec_id = (Integer) (request.getAttribute("rec_id"));
			Recipe r = new Recipe(rec_id);
			Recipes recipes = new Recipes(r);

			RecipeDAO rIDAO = new RecipeDAO();
			List<Recipe> recipe = rIDAO.selectRecipeIngredient(recipes);

			//冷蔵庫のデータを算出する
			LoginUser loginUser_f = (LoginUser) session.getAttribute("id");
			RefrigeratorDAO rfDAO = new RefrigeratorDAO();
			List<Refrigerator> refrigerator_f = rfDAO.select(new Refrigerator(loginUser_f.getId()));
			List<Refrigerator> pre_refrigerator_f = rfDAO.select(new Refrigerator(loginUser_f.getId()));

			//冷蔵庫の食材の個数からレシピの食材の個数を引く
			for (Refrigerator refrigerator_f_list : refrigerator_f) {
				for (Recipe recipe_list : recipe) {
					//レシピの食材IDと冷蔵庫の食材IDが同じだったら
					if (recipe_list.getF_id() == refrigerator_f_list.getF_id()) {
						if (refrigerator_f_list.getF_count() - recipe_list.getR_i_count() < 0) {
							refrigerator_f_list.setF_count(0);	// 0以下の時は0に
						} else {
							refrigerator_f_list.setF_count(refrigerator_f_list.getF_count() - recipe_list.getR_i_count());
						}
						break;
					}
				}
			}

			//変更があった食材一覧から一つずつ取り出して、冷蔵庫の中身を更新した
			for (int i = 0; i < refrigerator_f.size(); i++) {
				rfDAO.update(refrigerator_f.get(i), pre_refrigerator_f.get(i));
			}
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/refrigerator.jsp");
		dispatcher.forward(request, response);
	}

}
