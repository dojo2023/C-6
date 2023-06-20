<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン | れしぴろーくん</title>
</head>
<body>
<!-- ヘッダーここから -->
<header>
	<h1>れしぴろーくん</h1>
</header>
<!-- ヘッダーここまで -->

<!-- メインここから -->
<h2>ログイン</h2>
<!-- <form id="login_form" method="POST" action="/NMW/LoginServlet" onSubmit="return check()"> -->
<!-- 	<table> -->
<!-- 		<tr> -->
<!-- 			<td> -->
<!-- 				<p id="error_message"></p> -->
<!-- 				<label>ID<br> -->
<!-- 				<input type="text" name="ID" value="DOJO"> -->
<!-- 				</label> -->
<!-- 			</td> -->
<!-- 		</tr> -->
<!-- 		<tr> -->
<!-- 			<td> -->
<!-- 				<label>パスワード<br> -->
<!-- 				<input type="password" name="PW" value="password"> -->
<!-- 				</label> -->
<!-- 			</td> -->
<!-- 		</tr> -->
<!-- 		<tr> -->
<!-- 			<td colspan="2"> -->
<!-- 				<input id="login" type="submit" name="LOGIN" value="ログイン"> -->
<!-- 				<input id="register"  type="submit" name="REGISTER" value="新規登録"> -->
<!-- 			</td> -->
<!-- 		</tr> -->
<!-- 	</table> -->
<!-- </form> -->
<form id="login_form" method="POST" action="/NMW/LoginServlet" onSubmit="return check()">
	<ul>
		<li>
			<p id="error_message">${error}</p>
			<label>ID<br>
			<input type="text" name="ID" value="DOJO">
			</label>
		</li>
		<li>
			<label>パスワード<br>
			<input type="password" name="PW" value="password">
			</label>
		</li>
		<li>
			<input id="login" type="submit" name="LOGIN" value="ログイン">
		</li>
	</ul>
</form>
<a href="/NMW/RegisterServlet">新規登録</a>
<!-- メインここまで -->
<!-- フッターここから -->
<footer>
	<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
</footer>
<!-- フッターここまで -->
<script type="text/javascript" src="js/login.js"></script>
</body>
</html>