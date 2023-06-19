<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>レシピ</title>
</head>
<body>
<!-- ヘッダーここから -->
<h1>れしぴろーくん</h1>

	<a href="/NMW/RefrigeratorServlet">冷蔵庫</a>
	<a href="/NMW/RecipeListServlet">レシピ</a>
	<a href="/NMW/MainFoodListServlet">食材管理</a>
	<a href="/NMW/CalendarServlet">カレンダー</a>
	<a href="/NMW/MyPageServlet">マイページ</a>
<!-- ヘッダーここまで -->
<!-- メインここから -->
<h2>からあげ丼</h2>
	<img src = "/NMW/img/zitan.png">10-15分
	<img src = "/NMW/img/zisui.png">\300
	<img src = "/NMW/img/gaisyoku.png">\1200

	<p>材料　(1人前)</p>
	材料を表示

	<p>作り方</p>
	作り方を表示


	<button type="submit">レシピを使用</button>
	<button type="reset">回数リセット</button>

	<img src="/NMW/img/logo_resipiro-.png">
	<p>本日1回料理したピヨ</p>

<!-- メインここまで -->
<!-- フッターここから -->
<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
<!-- フッターここまで -->
</body>
</html>