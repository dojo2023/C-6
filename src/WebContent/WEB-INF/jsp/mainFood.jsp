<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>食材詳細 | れしぴろーくん</title>
<link rel="stylesheet" href="/NMW/css/common.css">
</head>
<body>
	<!-- ヘッダーここから -->
	<header>
		<h1 class="logo"><img src = "/NMW/img/logo/logo.png" alt="れしぴろーくん"></h1>
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
		<c:forEach var="m" items="${ mainFood }" >
			<img src="/NMW/img${ m.image }">

	<p>おいしい食材の見分け方</p>
			${ m.identify }
	<p>ーー 保存方法 ーー</p>
			${ m.strage_method }
	<p>ーー 保存期間 ーー</p>
			${ m.retention_period }
	<p>ーー 旬 ーー</p>
			${ m.season }
		</c:forEach>

	<!-- フッターここから -->
	<footer>
		<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
	</footer>
	<!-- フッターここまで -->
</body>
</html>