<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>食材管理 | れしぴろーくん</title>
<link rel="stylesheet" href="/NMW/css/common.css">
<link rel="stylesheet" href="/NMW/css/mainFoodList.css">
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
	<c:forEach var="mainFood" items="${ mainFood }" varStatus="status">
		<form action="/NMW/MainFoodListServlet" method="post">
			<button class="mf_btn" type="submit" name="foods" value="${ mainFood.f_id }">
				<img src="/NMW/img/${ mainFood.image }" width="50">
			</button>
		</form>
			<c:if test="${ ((status.index+1)%7)==0 }">
				<br>
			</c:if>
	</c:forEach>

	<div class="balloon">
	  <div class="icon">
	    <img src = "/NMW/img/logo/resipiro-right.png">
	  </div>
	  <div class="chatting">
	    <div class="says">
	      <p>クリックすると、食材の詳細を閲覧できるピヨ</p>
	    </div>
	  </div>
	</div>

	<div class="balloon2">
	  <div class="icon2">
	    <img src = "/NMW/img/logo/resipiko2.png">
	  </div>
	  <div class="chatting2">
	    <div class="says2">
	      	<p>これで食材保存も完璧だっピ！</p>
	    </div>
	  </div>
	</div>

	<!-- メインここまで -->
	<!-- フッターここから -->
	<footer>
		<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
	</footer>
	<!-- フッターここまで -->
</body>
</html>