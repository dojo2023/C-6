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
			<a href="/NMW/RefrigeratorServlet">冷蔵庫</a>
			<a href="/NMW/RecipeListServlet">レシピ</a>
			<a href="/NMW/MainFoodListServlet">食材管理</a>
			<a href="/NMW/CalendarServlet">カレンダー</a>
			<a href="/NMW/MyPageServlet">マイページ</a>
		</nav>
	</header>
	<!-- ヘッダーここまで -->

	<!-- メインここから -->

    <form id="register" method="POST" action="/NMW/MyPageServlet">
        <c:forEach var="m" items="${user_inf}" >
		<ul>
			<li>ID<br><input type="text" name="ID" value="${m.u_id}"></li>
			<br>
			<li>
			パスワード
			<br>
			<input type="text" name="ID" value="${m.password}">
			</li>
	    </c:forEach>
			<br>
			<li>
			新しいパスワード
			<br>
			<input type="password" name="PW">
			</li>
			<br>
			<li>
			パスワードをもう一度入力してください<br>
			<input type="password" name="c_PW">
			</li>




	<!-- <input id="password" type="password" name="PW" value="password" placeholder="パスワード" required>
	<br><label for="confirm_password">パスワードをもう一度入力してください</label>
	<br>
	<input id="confirm_password" type="password" name="c_PW" value="confirm_password" placeholder="パスワード" required>
	 -->
			<br>
			好きな食べ物
			<br>
			<input type="text" name="likefood" value="じゃがいも">
			<br>
			嫌いな食べ物
			<br>
			<input type="text" name="dislikefood" value="にんじん">

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