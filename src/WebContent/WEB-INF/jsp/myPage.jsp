<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/NMW/css/myPage.css">
<link rel="stylesheet" href="/NMW/css/common.css">
</head>
<body>
<!-- ヘッダーここから -->
<header>
	<a href ="/NMW/RefrigeratorServlet"><img class="logo" src="/NMW/img/logo/logo.png" alt="れしぴろーくん"></a>
</header>
<!-- ヘッダーここまで -->
	<header>

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
	<c:forEach var="m" items="${user_inf}" >
		<form method="POST" action="/NMW/MyPageEdit">
	       <ul>
	         <li>
			      ID<br>
			     <input type="text" name="ID" value="${m.u_id}">
			 </li>
			 <li>
			     パスワード<br>
			     <input type="text" name="PW" value="${m.password}">
    </c:forEach>
    <c:forEach var="m" items="${lf_name}" >
			 </li>
			     好きな食べ物<br>
			     <input type="text" name="likefood" value="${m.lf_id}">
	</c:forEach>
	<c:forEach var="m" items="${df_name}" >
			 <li>
			     嫌いな食べ物<br>
			     <input type="text" name="dislikefood" value="${m.df_id}">
		     </li>
	</c:forEach>
		     <li>
			     <br><input type="submit" class="btn" name="edit" value="編集">
			 </li>
		  </ul>



		</form>


	<!-- メインここまで -->
	<!-- フッターここから -->
	<footer>
		<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
	</footer>
	<!-- フッターここまで -->
</body>
</html>