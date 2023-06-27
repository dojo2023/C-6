<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>マイページ | れしぴろーくん</title>
<link rel="stylesheet" href="/NMW/css/myPage.css">
<link rel="stylesheet" href="/NMW/css/common.css">
</head>
<body>
<!-- ヘッダーここから -->
    <header>
	     <a href ="/NMW/RefrigeratorServlet"><img class="logo" src="/NMW/img/logo/logo.png" alt="れしぴろーくん"></a>
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
	<c:forEach var="m" items="${user_inf}" >
		<form method="POST" action="/NMW/MyPageServlet">
	       <ul>
	         <li>
			      ID<br>
			     <input type="text" name="ID" value="${m.u_id}">
			 </li>
			 <li>
			     パスワード<br>
			     <input type="text" name="PW" value="${m.password}">
   			 </li>
   	    </c:forEach>
   	         <div class="lfdf">
   	         <li>好きな食べ物<br>
			     <input type="text" name="likefood" value="じゃがいも"></li>
                 <span class="df">
			 <li>嫌いな食べ物<br>
			     <input type="text" name="dislikefood" value="にんじん"></li>
                 </span>
			     </div>
		     <li>
			     <br><input type="submit" class="btn btn--orange" name="edit" value="編集">
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
