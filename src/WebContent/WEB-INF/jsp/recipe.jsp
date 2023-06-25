<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>レシピ</title>
<link rel="stylesheet" href="/NMW/css/common.css">
</head>
<body>
	<!-- ヘッダーここから -->
	<!-- ランダム表示はjsで数字を決めさせてそれをもってきて表示 -->
	<header>
		<a href="/NMW/RefrigeratorServlet"><img class="logo"
			src="/NMW/img/logo/logo.png" alt="れしぴろーくん"></a>
		<nav>
			<a href="/NMW/RefrigeratorServlet">冷蔵庫</a> <a
				href="/NMW/RecipeListServlet">レシピ</a> <a
				href="/NMW/MainFoodListServlet">食材管理</a> <a
				href="/NMW/CalendarServlet">カレンダー</a> <a href="/NMW/MyPageServlet">マイページ</a>
		</nav>
	</header>
	<!-- ヘッダーここまで -->
	<!-- メインここから -->
	<!-- RecipeServletからレシピ内容のリスト受け取り -->
	<%
		List<String> list = (List<String>) request.getAttribute("recipe");
	%>
	<ul>
		<c:forEach var="r" items="${ recipe }" varStatus="status">
			<li><img src="/NMW/img${r.image}" width="500">
				<h2>${r.r_name}</h2> ${r.time} ${r.wanpan} ${r.save_time}
				${r.microwave_oven}</li>
	</ul>

	<p>材料 (1人前)</p>
	${r.ingredient}
	<p>作り方</p>


	<!-- レシピ使用ボタンクリック時 -->
	<form name="r_recipe" method="POST" action="/NMW/RecipeServlet" value="2">
		<input type="submit" name="r_recipe" value="レシピを使用"><br>
	</form>

	<!-- レシピ使用回数リセットボタンクリック時 -->
	<form name="r_recipe" method="POST" action="/NMW/RecipeServlet" value="3">
		<button type="submit" name="r_recipe" value="回数リセット"></button>
	</form>

	<img src="/NMW/img/logo_resipiro-.png">
	<p>本日${r.r_count}回料理したピヨ</p>
	</c:forEach>
	<!-- メインここまで -->
	<!-- フッターここから -->
	<footer>
		<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
	</footer>
	<!-- フッターここまで -->
</body>
</html>