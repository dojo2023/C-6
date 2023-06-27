<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List"%>
	<%
		List<String> recipeList = (List<String>) request.getAttribute("recipeList");
	%>
	<%
		List<String> recipesList = (List<String>) request.getAttribute("recipesList");
	%>
	<%
		List<String> mainFood = (List<String>) request.getAttribute("mainFood");
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>レシピ一覧 | れしぴろーくん</title>
<link rel="stylesheet" href="/NMW/css/common.css">
<link rel="stylesheet" href="/NMW/css/recipeList.css">
</head>
<body>
	<!-- ヘッダーここから -->
	<header>
		<a href="/NMW/RefrigeratorServlet"><img class="logo"
			src="/NMW/img/logo/logo.png" alt="れしぴろーくん"></a>
		<div class="logout">
			<input class="btn btn--orange" type="submit" name="LOOUT"
				value="ログアウト" onclick="location.href='/NMW/LogoutServlet'">
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
	<!-- 検索条件 -->

	<form method="POST" action="/NMW/RecipeListServlet">
		<input type="hidden" name="r_select" value="1">
		<input type="hidden" id="b_select" name="button" value="null">
		<button class="c_search_btn" type="submit" name="wanpan" value="true">
			<img src="/NMW/img/recipe/wanpan.png" width="50" name="wanpan">
		</button>
		<button class="c_search_btn" type="submit" name="renzi" value="true">
			<img src="/NMW/img/recipe/renzi.png" width="50" name="renzi">
		</button>
		<button class="c_search_btn" type="submit" name="zitan" value="true">
			<img src="/NMW/img/recipe/zitan.png" width="50"  name="zitan">
		</button>
		<button id="random" class="c_search_btn" type="submit" name="random" value="true">
			<img src="/NMW/img/recipe/randamu.png" width="50" height="50" name="random">
		</button>

		<br>
		<c:forEach var="search" items="${ mainFood }" varStatus="status">

			<button class="search_btn" type="submit" name="f_id"
				value="${ search.f_id }">
				<img src="/NMW/img/${search.image}" width="50">
			</button>

		</c:forEach>


	</form>

	<!-- 食材から検索したときの結果表示 -->

	<ul>
		<div class="RecipeList">
<c:forEach var="r_s" items="${ recipesList }" varStatus="status">
				<hr>
				<label>
					<div class="custom">
						<li>
							<form action="/NMW/RecipeServlet" method="post">
								<a
									href="http://localhost:8080/NMW/RecipeServlet?search_key=${r_s.rec_id}"><img
									src="/NMW/img${ r_s.image } " width="200"></a>
							</form>
						</li>
						<p class="text">${ r_s.r_name }<br>${ r_s.time }</p>
						<div class="c_image">
							<li><c:if test="${ (r_s.wanpan == true) }">
									<button class="c_search_btn" type="submit" name="wanpan"
										value="false">
										<img src="/NMW/img/recipe/wanpan.png" width="50">
									</button>
								</c:if></li>
							<li><c:if test="${ (r_s.microwave_oven == true) }">
									<button class="c_search_btn" type="submit" name="renzi"
										value="false">
										<img src="/NMW/img/recipe/renzi.png" width="50">
									</button>
								</c:if></li>
							<li><c:if test="${ (r_s.save_time == true) }">
									<button class="c_search_btn" type="submit" name="zitan"
										value="false">
										<img src="/NMW/img/recipe/zitan.png" width="50">
									</button>
								</c:if></li>
						</div>

					</div>

		</label>

		</c:forEach>
	</ul>
	<ul>
		<c:forEach var="r_l" items="${ recipeList }" varStatus="status">
			<hr>
			<label>
				<div class="custom">
					<li>
						<form action="/NMW/RecipeServlet" method="post">
							<a
								href="http://localhost:8080/NMW/RecipeServlet?search_key=${r_l.rec_id}"><img
								src="/NMW/img${ r_l.image } " width="200"></a>
						</form>
					</li>
					<p class="text">${ r_l.r_name }<br>${ r_l.time }</p>
					<div class="c_image">
					<li><c:if test="${ (r_l.wanpan == true) }">
							<button class="c_search_btn" type="submit" name="wanpan"
								value="false">
								<img src="/NMW/img/recipe/wanpan.png" width="50">
							</button>
						</c:if></li>
					<li><c:if test="${ (r_l.microwave_oven == true) }">
							<button class="c_search_btn" type="submit" name="renzi"
								value="false">
								<img src="/NMW/img/recipe/renzi.png" width="50">
							</button>
						</c:if></li>
					<li><c:if test="${ (r_l.save_time == true) }">
							<button class="c_search_btn" type="submit" name="zitan"
								value="false">
								<img src="/NMW/img/recipe/zitan.png" width="50">
							</button>
						</c:if></li>
					</div>
				</div>
			</label>

		</c:forEach>

		</div>

	</ul>


	<!-- メインここまで -->
	<!-- フッターここから -->
	<footer>
		<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
	</footer>
	<!-- フッターここまで -->
	<script type="text/javascript" src="js/recipeList.js"></script>
</body>
</html>