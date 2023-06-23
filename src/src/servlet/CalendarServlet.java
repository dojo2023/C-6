package servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CalendarDAO;
import model.Calendar;
import model.LoginUser;
import model.Recipe;

/**
 * Servlet implementation class CalendarServlet
 */
@WebServlet("/CalendarServlet")
public class CalendarServlet extends HttpServlet {
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

		// ユーザーのカレンダーの取得
		// その月のデータの取得
		LoginUser loginUser = (LoginUser) session.getAttribute("id");
		CalendarDAO caleDAO = new CalendarDAO();
		Date sqlNow = new Date(System.currentTimeMillis()); // util.date => sql.dateへの変換
		java.util.Date utilDate = sqlNow;

		// その月のカレンダーを取得
		List<Calendar> calendar = caleDAO.select(new Calendar(loginUser.getId(), new Date(utilDate.getTime())));
		System.out.println(calendar.get(0).getCooking_expenses());
		int cookingExpenses[] = new int[31];
		int eatingOutExpensese[] = new int[31];

		// 配列の初期化
		for (int i = 0; i < cookingExpenses.length; i++) {
			cookingExpenses[i] = 0;
			eatingOutExpensese[i] = 0;
		}

		//現在時刻でカレンダーのインスタンスを取得
		java.util.Calendar c = java.util.Calendar.getInstance();

		// 配列の日付番目に値段データの格納
		List<Recipe> recipe = caleDAO.selectR_Count(new Calendar(loginUser.getId()));
		int day;

		for (int i=0;i<calendar.size();i++) {
			if(recipe.get(i).getR_count() != -1) {
				// Date => String => int
				c.setTime(calendar.get(i).getDate());
				day = c.get(java.util.Calendar.DAY_OF_MONTH);

				cookingExpenses[day] = Integer
						.parseInt(String.valueOf(calendar.get(i).getCooking_expenses())) * recipe.get(i).getR_count();
//				System.out.println(i+"回目"+":"+calendar.get(i).getCooking_expenses()+"*"+recipe.get(i).getR_count()+"="+Integer
//						.parseInt(String.valueOf(calendar.get(i).getCooking_expenses())) * recipe.get(i).getR_count());
				eatingOutExpensese[day] = Integer
						.parseInt(String.valueOf(calendar.get(i).getEating_out_expenses())) * recipe.get(i).getR_count();
			} else {
				// Date => String => int
				c.setTime(calendar.get(i).getDate());
				day = c.get(java.util.Calendar.DAY_OF_MONTH);

				cookingExpenses[day] = Integer
						.parseInt(String.valueOf(calendar.get(i).getCooking_expenses()));
				eatingOutExpensese[day] = Integer
						.parseInt(String.valueOf(calendar.get(i).getEating_out_expenses()));
			}
		}
		//現在時刻でカレンダーのインスタンスを取得
		java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.DATE, 1);

        // 月の週の数の取得
        int weekMax = cal.getActualMaximum(java.util.Calendar.WEEK_OF_MONTH);
		int c_e_weekSum[] = new int[weekMax];
		int e_o_weekSum[] = new int[weekMax];
		int diff_weekSum[] = new int[weekMax];

		// 配列の初期化
		for (int i = 0; i < c_e_weekSum.length; i++) {
			c_e_weekSum[i] = 0;
			e_o_weekSum[i] = 0;
			diff_weekSum[i] = 0;
		}

		for (int i = cal.get(java.util.Calendar.MONTH)+1; i == cal.get(java.util.Calendar.MONTH)+1; i += 0) {
			for (int j = 0; j < weekMax; j++) {
				if (j == cal.get(java.util.Calendar.WEEK_OF_MONTH)) {
					c_e_weekSum[j] += cookingExpenses[cal.get(java.util.Calendar.DATE)];
					e_o_weekSum[j] += eatingOutExpensese[cal.get(java.util.Calendar.DATE)];
					break;
				}
			}
			System.out.println(cal.get(java.util.Calendar.DATE));
			cal.add(java.util.Calendar.DATE, 1);
//			System.out.println(cal.get(java.util.Calendar.YEAR)+"/"+(cal.get(java.util.Calendar.MONTH)+1) +"/"+cal.get(java.util.Calendar.DATE));
		}

		for (int i = 0; i < diff_weekSum.length; i++) {
			diff_weekSum[i] = c_e_weekSum[i] - e_o_weekSum[i];
		}

		request.setAttribute("c_e_weekSum", c_e_weekSum);
		request.setAttribute("c_e_Sum", Arrays.stream(c_e_weekSum).sum());
		request.setAttribute("e_o_weekSum", e_o_weekSum);
		request.setAttribute("e_o_Sum", Arrays.stream(e_o_weekSum).sum());
		request.setAttribute("diff_weekSum", diff_weekSum);
		request.setAttribute("diff_Sum", Arrays.stream(diff_weekSum).sum());

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/calendar.jsp");
		dispatcher.forward(request, response);
	}

	//	/**
	//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	//	 */
	//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	//			throws ServletException, IOException {
	//		// もしもログインしていなかったらログインサーブレットにリダイレクトする
	//		HttpSession session = request.getSession();
	//		if (session.getAttribute("id") == null) {
	//			response.sendRedirect("/NMW/LoginServlet");
	//			return;
	//		}
	//
	//	}
}
