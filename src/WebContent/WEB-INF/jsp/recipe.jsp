<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>レシピ</title>
<link rel="stylesheet" href="/NMW/css/common.css">
<link rel="stylesheet" href="/NMW/css/recipe.css">
</head>
<body>
	<!-- ヘッダーここから -->
	<!-- ランダム表示はjsで数字を決めさせてそれをもってきて表示 -->
	<header>
		<a href="/NMW/RefrigeratorServlet"><img class="logo"
			src="/NMW/img/logo/logo.png" alt="れしぴろーくん"></a>
			<div class="logout">
<input class="btn btn--orange" type="submit" name="LOOUT" value="ログアウト"
onclick="location.href='/NMW/LogoutServlet'">
</div>
		<nav>
			<ul>
				<li><a href="/NMW/RefrigeratorServlet">冷蔵庫</a></li>
				<li><a href="/NMW/RecipeListServlet">レシピ</a></li>
				<li><a href="/NMW/MainFoodListServlet">食材管理</a></li>
				<li><a href="/NMW/CalendarServlet">カレンダー</a></li>
				<li><a href="/NMW/MyPageServlet">マイページ</a></li>
			</ul>
		</nav>
	</header>
	<!-- ヘッダーここまで -->
	<!-- メインここから -->
	<!-- RecipeServletからレシピ内容のリスト受け取り -->
	<c:forEach var="r" items="${ recipe }" varStatus="status">

		<ul>
			<li>
				<p class="menuName">${r.r_name}</p>
			</li>
			<div class="L_custom_1">
				<c:if test="${ (r.wanpan == true) }">
						<button class="c_search_btn" type="submit" name="wanpan"
							value="false">
							<img src="/NMW/img/recipe/wanpan.png" width="50">
						</button>
					</c:if>
				<c:if test="${ (r.microwave_oven == true) }">
						<button class="c_search_btn" type="submit" name="renzi"
							value="false">
							<img src="/NMW/img/recipe/renzi.png" width="50">
						</button>
					</c:if>
				<c:if test="${ (r.save_time == true) }">
						<button class="c_search_btn" type="submit" name="zitan"
							value="false">
							<img src="/NMW/img/recipe/zitan.png" width="50">
						</button>
					</c:if>
					<img src="/NMW/img/recipe/zitan.png" width="50" height="50" hspace="30">
					${ r.time }
					<img src="/NMW/img/recipe/zisui.png" width="70" height="50" hspace="30">
					\ ${ r.cooking_expenses }
					<img src="/NMW/img/recipe/gaisyoku.png" width="40" height="40" hspace="30">
					\ ${ r.eating_out_expenses }
			</div>
		</ul>
		<div class="L_custom_2">
		<img src="/NMW/img${r.image}" width="500">

		<p>材料 (1人前)<br>
		${r.ingredient}</p>
		</div>


		<p class="make_title">作り方</p>
		<div class="make">${ r.recipe }</div>



	<!-- レシピ使用ボタンクリック時 -->
		<form name="r_recipe" method="POST" action="/NMW/RecipeServlet">
			<input type="hidden" name="rec_id" value="${ r.rec_id }"> <input
				type="hidden" name="r_recipe" value="2"> <input
				type="submit" name="r_recipe" value="レシピを使用"><br>
		</form>

		<!-- レシピ使用回数リセットボタンクリック時 -->
		<form name="r_recipe" method="POST" action="/NMW/RecipeServlet">
			<input type="hidden" name="rec_id" value="${ r.rec_id }"> <input
				type="hidden" name="r_recipe" value="3">
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