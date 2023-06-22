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
		<a href ="/NMW/LoginServlet"><img class="logo" src="/NMW/img/logo/logo.png" alt="れしぴろーくん"></a>
	</header>
	<!-- ヘッダーここまで -->

	<!-- メインここから -->
	<form id="register" method="POST" action="/NMW/RegisterServlet">
		<br>ID<br><input type="text" name="ID">
		<br>パスワード<br><input type="text" name="PW">
		<br>パスワードをもう一度入力してください<br>
		<input type="text" name="c_PW">
		<br>好きな食べ物 <br>
		<input type="text" name="likefood">
		<br>嫌いな食べ物 <br>
		<input type="text" name="dislikefood"><br>
	</form>

	<input class="btn btn--orange" type="button" name="likefood" value="好きな食べ物">
	<input class="btn btn--orange" type="button" name="dislikefood" value="嫌いな食べ物">
	<br>
	<c:forEach var="mainFood" items="${ mainFood }" varStatus="status">

		<button type="submit" name="foods" value="${ mainFood.f_id }">
			<img src="/NMW/img/${ mainFood.image }" width="50">
		</button>
		<c:if test="${ ((status.index+1)%7)==0 }">
			<br>
		</c:if>

	</c:forEach>

		<input class="btn btn--orange" type="submit" name="REGISTER" value="新規登録">

	<!-- メインここまで -->
	<!-- フッターここから -->
	<footer>
		<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
	</footer>
	<!-- フッターここまで -->
</body>
</html>