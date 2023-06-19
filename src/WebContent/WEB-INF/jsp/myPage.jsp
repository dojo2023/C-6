<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- ヘッダーここから -->
<h1>れしぴろーくん</h1>

	<a href="/NMW/RefrigeratorServlet.java">冷蔵庫</a>
	<a href="NMW/RecipeListServlet.java">レシピ</a>
	<a href="NMW/MainFoodListServlet.java">食材管理</a>
	<a href="NMW/CalendarServlet.java">カレンダー</a>
	<a href="NMW/MyPageServlet.java">マイページ</a>
<!-- ヘッダーここまで -->
<!-- メインここから -->
	ID<br>
	<input type="text" name="ID" value="id"> <br>
	パスワード<br>
	<input type="text" name="PW" value="password"> <br>

	好きな食べ物<br>
	<input type="text" name="likefood" value="">
	嫌いな食べ物<br>
	<input type="text" name="dislikefood" value="">

	<button type="button"> 編集</button>

<!-- メインここまで -->
<!-- フッターここから -->
<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
<!-- フッターここまで -->
</body>
</html>