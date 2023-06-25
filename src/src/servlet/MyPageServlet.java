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

import dao.UserDAO;
import model.LoginUser;
import model.User;

/**
 * Servlet implementation class MyPageServlet
 */
@WebServlet("/MyPageServlet")
public class MyPageServlet extends HttpServlet {
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


/*		int edit = -1;
		if (request.getAttribute("edit") != null) {
			edit = Integer.parseInt(request.getAttribute("edit").toString());

		}
*/

		// user idからユーザー情報(マイページ)をsetしてる
		UserDAO uDAO = new UserDAO();
		LoginUser loginUser = (LoginUser) session.getAttribute("id");
//		String id= (String) session.getAttribute("id");
		String id = loginUser.getId();
		List<User> user_inf = uDAO.selectLfDf(new User(id));

<<<<<<< Updated upstream
//		System.out.println("=============================================================--");
//		System.out.println(user_inf);

//		List<MainFood> lf_name = uDAO.selectF_name( new User(user_inf.get(0).getU_id(), user_inf.get(0).getPassword() , user_inf.get(0).getLf_id(), -1));
//		List<MainFood> df_name = uDAO.selectF_name( new User(user_inf.get(0).getU_id(), user_inf.get(0).getPassword() , null, user_inf.get(0).getDf_id()));

		request.setAttribute("user_inf", user_inf);
//		request.setAttribute("lf_name", lf_name);
//		request.setAttribute("df_name", df_name);
=======

<<<<<<< HEAD

//		List<MainFood> lf_name = uDAO.selectF_name( new User(user_inf.get(0).getU_id(), user_inf.get(0).getPassword() , user_inf.get(0).getLf_id(), -1));
//		List<MainFood> df_name = uDAO.selectF_name( new User(user_inf.get(0).getU_id(), user_inf.get(0).getPassword() , null, user_inf.get(0).getDf_id()));

//		System.out.println("=============================================================--");
//		System.out.println(user_inf);


		request.setAttribute("user_inf", user_inf);
//		request.setAttribute("lf_name", lf_name);
//		request.setAttribute("df_name", df_name);

//		System.out.println("=============================================================--");
//		System.out.println(user_inf);

=======
		List<MainFood> lf_name = uDAO.selectF_name( new User(user_inf.get(0).getU_id(), user_inf.get(0).getPassword() , user_inf.get(0).getLf_id(), -1));
//		List<MainFood> df_name = uDAO.selectF_name( new User(user_inf.get(0).getU_id(), user_inf.get(0).getPassword() , null, user_inf.get(0).getDf_id()));

		request.setAttribute("user_inf", user_inf);
		request.setAttribute("lf_name", lf_name);
//		request.setAttribute("df_name", df_name);
>>>>>>> 8a51b4b4ab8367287ca6db999d02990a96da1e84
>>>>>>> Stashed changes

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/myPage.jsp");
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
				response.sendRedirect("/NMW/LoginServlet");
				return;
		}

		request.setCharacterEncoding("UTF-8");
		//編集ボタンを押してユーザー情報へ遷移

		request.getParameter("edit");

		//myPageEditサーブレットへユーザーIDを持った状態で遷移
		LoginUser loginUser = (LoginUser) session.getAttribute("id");
		String id = loginUser.getId();

		UserDAO uDAO = new UserDAO();
		List<User> user_inf = uDAO.selectLfDf(new User(id));
        //結果を表示
		request.setAttribute("user_inf", user_inf);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/myPageEdit.jsp");
		dispatcher.forward(request, response);



/*		request.setAttribute("doPost", "doPost");

		// 結果ページにフォワードする
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/NMW/jsp/myPageEdit.jsp");
	    dispatcher.forward(request, response);
*/

	}

}
