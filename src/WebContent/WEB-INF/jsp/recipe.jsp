<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<c:forEach var="r" items="${ recipe }" varStatus="status">
		<ul>
			<li>
				<img src="/NMW/img${r.image}" width="500">
				<h2>${r.r_name}</h2>
			</li>
			<li>
				<c:if test="${ (r.wanpan == true) }">
					<button class="c_search_btn" type="submit" name="wanpan"
						value="false">
						<img src="/NMW/img/recipe/wanpan.png" width="50">
					</button>
				</c:if>
			</li>
			<li>
				<c:if test="${ (r.microwave_oven == true) }">
					<button class="c_search_btn" type="submit" name="renzi"
						value="false">
						<img src="/NMW/img/recipe/renzi.png" width="50">
					</button>
				</c:if>
			</li>
			<li>
				<c:if test="${ (r.save_time == true) }">
					<button class="c_search_btn" type="submit" name="zitan"
						value="false">
						<img src="/NMW/img/recipe/zitan.png" width="50">
					</button>
				</c:if>
			</li>
		</ul>

		<p>材料 (1人前)</p>
	${r.ingredient}
	<p>作り方</p>
	${ r.recipe }


	<!-- レシピ使用ボタンクリック時 -->
		<form name="r_recipe" method="POST" action="/NMW/RecipeServlet">
			<input type="hidden" name="rec_id" value="${ r.rec_id }">
			<input type="hidden" name="r_recipe" value="2">
			<input type="submit" name="r_recipe" value="レシピを使用"><br>
		</form>

		<!-- レシピ使用回数リセットボタンクリック時 -->
		<form name="r_recipe" method="POST" action="/NMW/RecipeServlet">
			<input type="hidden" name="rec_id" value="${ r.rec_id }">
			<input type="hidden" name="r_recipe" value="3">
			<button type="submit" name="r_recipe" value="回数リセット"></button>
		</form>
		<c:if test="${ r.r_count!=0 }">
			<img src="/NMW/img/logo/logo_resipiro-.png">
			<p>本日${r.r_count}回料理したピヨ</p>
		</c:if>

	</c:forEach>
	<!-- メインここまで -->
	<!-- フッターここから -->
	<footer>
		<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
	</footer>
	<!-- フッターここまで -->
</body>
</html>