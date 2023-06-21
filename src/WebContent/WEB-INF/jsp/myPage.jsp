<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/NMW/css/myPage.css">
<link rel="stylesheet" href="/NMW/css/common.css">
</head>
<body>
	<!-- ヘッダーここから -->
	<header>
		<h1>れしぴろーくん</h1>
		<nav>
			<a href="/NMW/RefrigeratorServlet">冷蔵庫</a>
			<a href="/NMW/RecipeListServlet">レシピ</a>
			<a href="/NMW/MainFoodListServlet">食材管理</a>
			<a href="/NMW/CalendarServlet">カレンダー</a>
			<a href="/NMW/MyPageServlet">マイページ</a>
		</nav>
	</header>
	<!-- ヘッダーここまで -->
	<!-- メインここから -->
	<c:forEach var="m" items="${user_inf}" >
		<form method="POST" action="">

	       <ul>
	         <li>
			      ID<br>
			     <input type="text" name="ID" value="${m.number}">
			 </li>
			 <li>
			     パスワード<br>
			     <input type="text" name="PW" value=value="${e.number}">
			 </li>

			     好きな食べ物<br>
			     <input type="text" name="likefood" value="${e.number}">
			 <li>

			     嫌いな食べ物<br>
			     <input type="text" name="dislikefood" value="${e.number}">
		     </li>
		     <li>
			     <br><button type="button" class="btn">
			     </button>
			 </li>
		  </ul>
		      <input type="submit" name="edit" value="編集">
		</form>
	</c:forEach>

	<!-- メインここまで -->
	<!-- フッターここから -->
	<footer>
		<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
	</footer>
	<!-- フッターここまで -->
</body>
</html>