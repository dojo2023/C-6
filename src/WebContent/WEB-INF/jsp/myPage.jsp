<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/simpleBC/css/myPage.css">
<link rel="stylesheet" href="/simpleBC/css/common.css">
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
	<form method="POST" action="">

       <ul>
         <li>
		      ID<br>
		     <input type="text" name="ID" value="id">
		 </li>
		 <li>
		      <br>
		     パスワード<br>
		     <input type="text" name="PW" value="password">
		 </li>
		     <br>
		     好きな食べ物<br>
		     <input type="text" name="likefood" value="">
		 <li>
		     <br>
		     嫌いな食べ物<br>
		     <input type="text" name="dislikefood" value="">
	     </li>
	     <li>
		     <br><button type="button" class="btn">
		     <input type="submit" name="edit" value="編集">
		     編集</button>
		 </li>
	  </ul>
	</form>

	<!-- メインここまで -->
	<!-- フッターここから -->
	<footer>
		<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
	</footer>
	<!-- フッターここまで -->
</body>
</html>