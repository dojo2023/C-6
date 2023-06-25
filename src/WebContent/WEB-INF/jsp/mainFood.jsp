<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>食材詳細 | れしぴろーくん</title>
<link rel="stylesheet" href="/NMW/css/common.css">
<link rel="stylesheet" href="/NMW/css/mainFood.css">
</head>
<body>
	<!-- ヘッダーここから -->
	<header>
		<a href ="/NMW/RefrigeratorServlet"><img class="logo" src="/NMW/img/logo/logo.png" alt="れしぴろーくん"></a>
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
	<div class="Mainfood">
	<c:forEach var="m" items="${ mainFood }" >
		<div class="fn">
		${ m.f_name }
		</div>
		<div class="food">
			<img src="/NMW/img${ m.image }" width="200">
			<div>
			<span class="p">おいしい食材の見分け方</span><br>
				${ m.identify }
			</div>
		</div>
		<p class="p">ーー 保存方法 ーー</p>
			${ m.strage_method }
		<p class="p">ーー 保存期間 ーー</p>
			${ m.retention_period }
		<p class="p">ーー 旬 ーー</p>
			${ m.season }
	</c:forEach>
	</div>

	<!-- フッターここから -->
	<footer>
		<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
	</footer>
	<!-- フッターここまで -->
</body>
</html>