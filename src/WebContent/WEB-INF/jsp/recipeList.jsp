<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>レシピ一覧 | れしぴろーくん</title>
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
	<img src="/NMW/img/recipe/kensaku.png">
	<!--
	<img src="">からあげ丼 所要時間15分t
	-->
	<ul>
		<c:forEach var="r_l" items="${ recipeList }" varStatus="status">
				<li>
				<form  action="/NMW/RecipeServlet" method="post">

				<input type="submit" name = "r_select" value="2">
				${ r_l.image }
				</form>
				</li>
				<li>
				${ r_l.r_name }
				</li>
				<li>
				${ r_l.time }
				</li>
				<li>
				${ r_l.wanpan }
				</li>
				<li>
				${ r_l.save_time }
				</li>
				<li>
				${ r_l.microwave_oven }
				</li>



		</c:forEach>
	</ul>

	<!-- メインここまで -->
	<!-- フッターここから -->
	<footer>
		<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
	</footer>
	<!-- フッターここまで -->
</body>
</html>