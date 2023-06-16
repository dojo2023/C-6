<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン | れしぴろーくん</title>
</head>
<body>
<!-- ヘッダーここから -->
<h1>れしぴろーくん</h1>
<!-- ヘッダーここまで -->

<!-- メインここから -->
<h2>ログイン</h2>
<form id= login method="POST" action="/NMW/LoginServlet">
	<table>
		<tr>
			<td>
				<label>ID<br>
				<input type="text" name="ID">
				</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>パスワード<br>
				<input type="password" name="PW">
				</label>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" name="LOGIN" value="ログイン">
				<input type="submit" name="REGISTER" value="新規登録">
			</td>
		</tr>
	</table>
</form>
<!-- メインここまで -->
<!-- フッターここから -->
<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
<!-- フッターここまで -->
</body>
</html>