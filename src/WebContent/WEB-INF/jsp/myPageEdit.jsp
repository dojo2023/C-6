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
	ID
	<br>
	<input type="text" name="ID" value="id">
	<br><p id="error_msg" class="font-red"></p>
	<br><label for="password">新しいパスワード</label>
	<br>
	<input id="password" type="password" name="PW" value="password" placeholder="パスワード" required>
	<br><label for="confirm_password">パスワードをもう一度入力してください</label>
	<br>
	<input id="confirm_password" type="password" name="c_PW" value="confirm_password" placeholder="パスワード" required>
	<br> 好きな食べ物
	<br>
	<input type="text" name="likefood" value=""> 嫌いな食べ物
	<br>
	<input type="text" name="dislikefood" value="">

	<button type="submit" class="btn btn--orange" name="likefood" value="好きな食べ物ボタン"></button>
	<button type="submit" class="btn btn--orange" name="dislikefood" value="嫌いな食べ物ボタン"></button>
	<br>
	<c:forEach var="mainFood" items="${ mainFood }" varStatus="status">
		<form action="/NMW/MainFoodListServlet" method="post">
			<button type="submit" name="foods" value="${ mainFood.f_id }">
				<img src="/NMW/img/${ mainFood.image }" width="50">
			</button>
		</form>
			<c:if test="${ ((status.index+1)%7)==0 }">
				<br>
			</c:if>
	</c:forEach>
	<!--
	<button type="submit" name="food" value="negi"></button>
	<button type="submit" name="food" value="kyuuri"></button>
	<button type="submit" name="food" value="pi-man"></button>
	<button type="submit" name="food" value="jagaimo"></button>
	<button type="submit" name="food" value="tamanegi"></button>
	<button type="submit" name="food" value="daikonn"></button>
	<button type="submit" name="food" value="ninnjinn"></button>
	<button type="submit" name="food" value="kyabetu"></button>
	<button type="submit" name="food" value="hourennsou"></button>
	<button type="submit" name="food" value="hakusai"></button>
	<button type="submit" name="food" value="retasu"></button>
	<button type="submit" name="food" value="tomato"></button>
	<button type="submit" name="food" value="nasu"></button>
	<button type="submit" name="food" value="moyasi"></button>
	<button type="submit" name="food" value="burokkori-"></button>
	<button type="submit" name="food" value="simeji"></button>
	<button type="submit" name="food" value="enoki"></button>
	<button type="submit" name="food" value="tamago"></button>
	<button type="submit" name="food" value="gyuuniku"></button>
	<button type="submit" name="food" value="butaniku"></button>
	<button type="submit" name="food" value="toriniku"></button>
 -->
	<input type="submit" class="btn btn--orange" name="SAVE" value="保存">
	<input type="submit" class="btn btn--orange" name="DELETE" value="アカウント削除">
	<!-- メインここまで -->
	<!-- フッターここから -->
	<footer>
		<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
	</footer>
	<!-- フッターここまで -->

<!-- JavaScriptここから -->
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
</body>
</html>