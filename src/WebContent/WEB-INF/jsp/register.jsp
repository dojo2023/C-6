<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規登録 | れしぴろーくん</title>
</head>
<body>
	<!-- ヘッダーここから -->
	<header>
		<h1>れしぴろーくん</h1>
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

		<button type="submit" name="likefood" value="好きな食べ物ボタン"></button>
		<button type="submit" name="dislikefood" value="嫌いな食べ物ボタン"></button>
		<br>
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