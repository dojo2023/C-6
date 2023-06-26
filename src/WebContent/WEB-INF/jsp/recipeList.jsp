<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List"%>
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
		<nav>
			<a href="/NMW/RefrigeratorServlet">冷蔵庫</a> <a
				href="/NMW/RecipeListServlet">レシピ</a> <a
				href="/NMW/MainFoodListServlet">食材管理</a> <a
				href="/NMW/CalendarServlet">カレンダー</a> <a href="/NMW/MyPageServlet">マイページ</a>
		</nav>
	</header>
	<!-- ヘッダーここまで -->
	<!-- メインここから -->
	<%
		List<String> recipeList = (List<String>) request.getAttribute("recipeList");
	%>
	<%
		List<String> recipesList = (List<String>) request.getAttribute("recipesList");
	%>
	<%
		List<String> mainFood = (List<String>) request.getAttribute("mainFood");
	%>

	<!-- 検索条件 -->
	<div class="RecipeList">
	<form method="POST" action="/NMW/RecipeListServlet">
		<button class="c_search_btn" type="submit" name="wanpan" value="false">
			<img src="/NMW/img/recipe/wanpan.png" width="50">
		</button>
		<button class="c_search_btn" type="submit" name="renzi" value="false">
			<img src="/NMW/img/recipe/renzi.png" width="50">
		</button>
		<button class="c_search_btn" type="submit" name="zitan" value="false">
			<img src="/NMW/img/recipe/zitan.png" width="50">
		</button>
		<br> <input type="hidden" name="r_select" value="1">
		<c:forEach var="search" items="${ mainFood }" varStatus="status">

			<button class="search_btn" type="submit" name="f_id"
				value="${ search.f_id }">
				<img src="/NMW/img/${search.image}" width="50">
			</button>
		</c:forEach>


	</form>

	<!-- 食材から検索したときの結果表示 -->
	<ul>
		<c:forEach var="r_s" items="${ recipesList }" varStatus="status">
			<span>
				<li>
					<form action="/NMW/RecipeServlet" method="post">
						<a
							href="http://localhost:8080/NMW/RecipeServlet?search_key=${r_s.rec_id}"><img
							src="/NMW/img${ r_s.image } " width="300"></a>
					</form>
				</li>

				${ r_s.r_name }
				${ r_s.time }
			<div class="custom">
				<c:if test="${ (r_s.wanpan == true) }">
						<button class="c_search_btn" type="submit" name="wanpan"
							value="false">
							<img src="/NMW/img/recipe/wanpan.png" width="50">
						</button>
					</c:if>
				<c:if test="${ (r_s.microwave_oven == true) }">
						<button class="c_search_btn" type="submit" name="renzi"
							value="false">
							<img src="/NMW/img/recipe/renzi.png" width="50">
						</button>
					</c:if>
				<c:if test="${ (r_s.save_time == true) }">
						<button class="c_search_btn" type="submit" name="zitan"
							value="false">
							<img src="/NMW/img/recipe/zitan.png" width="50">
						</button>
					</c:if>
				</div>
			</span>
		</c:forEach>
	</ul>
	<ul>
		<c:forEach var="r_l" items="${ recipeList }" varStatus="status">
			<label>
				<li>
					<form action="/NMW/RecipeServlet" method="post">
						<a
							href="http://localhost:8080/NMW/RecipeServlet?search_key=${r_l.rec_id}"><img
							src="/NMW/img${ r_l.image } " width="300"></a>
					</form>
			</li>
				<li>${ r_l.r_name }</li>
				<li>${ r_l.time }</li>
				<div class="custom">
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
			</label>
		</c:forEach>
	</ul>
</div>


	<!-- メインここまで -->
	<!-- フッターここから -->
	<footer>
		<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
	</footer>
	<!-- フッターここまで -->
</body>
</html>