<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規登録 | れしぴろーくん</title>
<link rel="stylesheet" href="/NMW/css/myPageEdit.css">
<link rel="stylesheet" href="/NMW/css/common.css">

</head>
<body>
	<!-- ヘッダーここから -->
	<header>
		<a href ="/NMW/RefrigeratorServlet"><img class="logo" src="/NMW/img/logo/logo.png" alt="れしぴろーくん"></a>
		<nav>
			<li><a href="/NMW/RefrigeratorServlet">冷蔵庫</a></li>
			<li><a href="/NMW/RecipeListServlet">レシピ</a></li>
			<li><a href="/NMW/MainFoodListServlet">食材管理</a></li>
			<li><a href="/NMW/CalendarServlet">カレンダー</a></li>
			<li><a href="/NMW/MyPageServlet">マイページ</a></li>
		</nav>
	</header>
	<!-- ヘッダーここまで -->

	<!-- メインここから -->

    <form id="register" method="POST" action="/NMW/MyPageServlet">
	<ul>
        <c:forEach var="m" items="${user_inf}" >
			<li>ID<br><input type="text" name="ID" value="${m.u_id}"></li>
			<li>パスワード<br>
			<input type="text" name="ID" value="${m.password}"></li>
	    </c:forEach>
			<li>新しいパスワード<br>
			<input type="password" name="PW"></li>
			<li>
			パスワードをもう一度入力してください<br>
			<input type="password" name="c_PW"></li>
			好きな食べ物<br>
			<input type="text" name="likefood" value="じゃがいも"></li>
			<li>
			嫌いな食べ物<br>
			<input type="text" name="dislikefood" value="にんじん"></li>
	</ul>

	     <input class="btn btn--orange" type="button" name="likefood" value="好きな食べ物">
		 <input class="btn btn--orange" type="button" name="dislikefood" value="嫌いな食べ物">
	     <br>
	     <c:forEach var="mainFood" items="${ mainFood }" varStatus="status">

		 <button class="mf_btn" type="submit" name="foods" value="${ mainFood.f_id }">
			<img src="/NMW/img/${ mainFood.image }" width="50">
		 </button>

		<c:if test="${ ((status.index+1)%7)==0 }">
			<br>
		</c:if>

	   </c:forEach>


	<input type="submit" class="btn btn--orange" name="SAVE" value="保存">
	<input type="submit" class="btn btn--orange" name="DELETE" value="アカウント削除">
	<!-- メインここまで -->
	<!-- フッターここから -->
    </form>
	<footer>
		<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
	</footer>
	<!-- フッターここまで -->

<!-- JavaScriptここから
<script>
function confirmPassword() {
	  const password = document.getElementById('password').value;
	  const confirmPassword = document.getElementById('confirm_password').value;
	            const errorMsg = document.getElementById('error_msg');

	            if (password == confirmPassword) {
	                errorMsg.innerText = "";
	                return true;
	            } else {
	                errorMsg.innerText = "パスワードが一致しません";
	                return false;
	            }
}
</script>
-->
</body>
</html>