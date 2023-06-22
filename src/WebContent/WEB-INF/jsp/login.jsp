<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン | れしぴろーくん</title>
<!-- スタイルシートの挿入 -->
<link rel= "stylesheet" href="/NMW/css/common.css">
<style>
</style>
</head>
<body>
<!-- ヘッダーここから -->
<header>
	<a href ="/NMW/LoginServlet"><img class="logo" src="/NMW/img/logo/logo.png" alt="れしぴろーくん"></a>
</header>
<!-- ヘッダーここまで -->

<!-- メインここから -->
<h2>ログイン</h2>

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
			<input id="login" class="btn btn--orange" type="submit" name="LOGIN" value="ログイン">
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

<!-- JavaScriptここから -->
<script type="text/javascript" src="js/login.js"></script>
</body>
</html>