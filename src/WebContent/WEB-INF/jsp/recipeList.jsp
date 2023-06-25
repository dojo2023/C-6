<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>レシピ一覧 | れしぴろーくん</title>
<link rel="stylesheet" href="/NMW/css/common.css">
</head>
<body>
	<!-- ヘッダーここから -->
	<header>
		<a href ="/NMW/RefrigeratorServlet"><img class="logo" src="/NMW/img/logo/logo.png" alt="れしぴろーくん"></a>
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
	<% List<String> recipeList = (List<String>)request.getAttribute("recipeList"); %>
	<!-- 検索条件 -->
	<form name="r_select" method="POST" action="/NMW/RecipeListServlet" value="1">
	<c:forEach var="search" items="${ mainFood }" varStatus="status">

		<button class="search_btn" type="submit" name="foods" value="${ mainFood.f_id }">
			<img src="/NMW/img/${mainFood.image}" width="50">
		</button>
	</c:forEach>
	<input type="image" src="/NMW/img/recipe/kensaku.png"width="50" alt="検索">
	</form>


	<ul>
		<c:forEach var="r_l" items="${ recipeList }" varStatus="status">
				<li>
				<form  action="/NMW/RecipeServlet" method="post">
				<a href="http://localhost:8080/NMW/RecipeServlet?search_key=${r_l.rec_id}"><img src="/NMW/img${ r_l.image } "width="300"></a>
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