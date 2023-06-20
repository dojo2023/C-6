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
import dao.RefrigeratorDAO;
import model.LoginUser;
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

		// user idからその人の冷蔵庫をsetしてる
		LoginUser loginUser = (LoginUser) session.getAttribute("id");
		List<Refrigerator> refrigerator = rDAO.select(new Refrigerator(loginUser.getId()));

		request.setAttribute("refrigerator", refrigerator);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/refrigerator.jsp");
		dispatcher.forward(request, response);

		//冷蔵庫のサーブレットに送る
		//レシピIDを基にselect文でそのレシピに入っている複数の食材ID(f_id)とその食材の個数(r_i_count)を算出する（差分）
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

					refrigerator_f_list.setF_count(refrigerator_f_list.getF_count() - recipe_list.getR_i_count());
					break;

				}
			}
		}

		//変更があった食材一覧から一つずつ取り出して、冷蔵庫の中身を更新した
		for (int i = 0; i < refrigerator_f.size(); i++) {
			rfDAO.update(refrigerator_f.get(i), pre_refrigerator_f.get(i));
		}

		//レシピ使用ボタンを押す
		//レシピIDを受け取る
		//冷蔵庫のサーブレットに送る
		//レシピIDを基にselect文でそのレシピに入っている複数の食材ID(f_id)とその食材の個数(r_i_count)を算出する（差分）
		//算出した複数のf_idとユーザーID(u_id)を基にそのユーザーの冷蔵庫のデータを持ってくる（前のデータ）
		//
		//冷蔵庫のデータ.get(0).getf_countを
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
		int f_id = ((Integer) (request.getAttribute("foods"))).intValue();
		List<Refrigerator> refrigerator = rDAO.select(new Refrigerator(loginUser.getId(), f_id));
		List<Refrigerator> pre_refrigerator = rDAO.select(new Refrigerator(loginUser.getId(), f_id));

		pre_refrigerator.get(0).setF_count(pre_refrigerator.get(0).getF_count() + 1);

		// updateする
		rDAO.update(refrigerator.get(0), pre_refrigerator.get(0));

		RequestDispatcher dispatcher = request.getRequestDispatcher("/NMW/RefrigeratorServlet");
		dispatcher.forward(request, response);
	}
}
