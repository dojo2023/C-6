<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規登録 | れしぴろーくん</title>
<link rel="stylesheet" href="/NMW/css/common.css">
<link rel="stylesheet" href="/NMW/css/register.css">
</head>
<body>
	<!-- ヘッダーここから -->
	<header>
		<a href ="/NMW/LoginServlet"><img class="logo" src="/NMW/img/logo/logo.png" alt="れしぴろーくん"></a>
	</header>
	<!-- ヘッダーここまで -->

	<!-- メインここから -->
	<form id="register" method="POST" action="/NMW/RegisterServlet">
		<ul>
			<li>ID<br><input type="text" name="ID"></li>

<!--		<input id="password" type="password" name="PW" placeholder="パスワード" required>
			<br><label for="confirm_password">パスワードをもう一度入力してください</label>
				<br>
			<input id="confirm_password" type="password" name="c_PW" placeholder="パスワード" required>-->
			<li>パスワード<br><input type="password" name="PW"></li>
			<li>パスワードをもう一度入力してください<br>
			<input type="password" name="c_PW"></li>
			<div class="lfdf">
			<li>好きな食べ物 <br>
			<input type="text" name="Lf_id" ></li>
			<span class="df">
			<li>嫌いな食べ物 <br>
			<input type="text" name="Df_id"></li>
			</span>
			</div>
		</ul>

	<div class="lfdf_btn">
	<input class="btn btn--orange" type="button" name="likefood" value="好きな食べ物">
	<input class="btn btn--orange" type="button" name="dislikefood" value="嫌いな食べ物">
	</div>
	<br>
	<div class="mainfood">
	<c:forEach var="mainFood" items="${ mainFood }" varStatus="status">

		<button class="mf_btn" type="submit" name="foods" value="${ mainFood.f_id }">
			<img src="/NMW/img/${ mainFood.image }" width="50">
		</button>

		<c:if test="${ ((status.index+1)%7)==0 }">
			<br>
		</c:if>

	</c:forEach>
	</div>
	<div class="reg_btn">
		<input class="btn btn--orange" type="submit" name="REGISTER" value="新規登録">
	</div>
	</form>
	<!-- メインここまで -->
	<!-- フッターここから -->
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
</script>-->
</body>
</html>