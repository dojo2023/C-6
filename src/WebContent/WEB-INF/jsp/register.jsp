<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規登録 | れしぴろーくん</title>
<link rel="stylesheet" href="/NMW/css/common.css">
</head>
<body>
	<!-- ヘッダーここから -->
	<header>
		<h1 class="logo"><img src = "/NMW/img/logo/logo.png" alt="れしぴろーくん"></h1>
	</header>
	<!-- ヘッダーここまで -->

	<!-- メインここから -->
	<form id="register" method="POST" action="/NMW/RegisterServlet">
		<br>ID<br><input type="text" name="ID" value="id">
		<br>パスワード<br><input type="text" name="PW" value="password">
		<br>パスワードをもう一度入力してください<br>
		<input type="text" name="c_PW" value="password">
		<br>好きな食べ物 <br>
		<input type="text" name="likefood" value="">
		<br>嫌いな食べ物 <br>
		<input type="text" name="dislikefood" value=""><br>

	<c:forEach var="mainFood" items="${ mainFood }" varStatus="status">
		<form action="/NMW/RegisterServlet" method="post">
			<button type="submit" name="foods" value="${ mainFood.f_id }">
				<img src="/NMW/img/${ mainFood.image }" width="50">
			</button>
		</form>
	</c:forEach>

		<input type="submit" name="REGISTER" value="新規登録">
	</form>
	<!-- メインここまで -->
	<!-- フッターここから -->
	<footer>
		<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
	</footer>
	<!-- フッターここまで -->
</body>
</html>