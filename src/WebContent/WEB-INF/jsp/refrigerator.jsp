<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="servlet.RefrigeratorServlet" %>
<%@ page import="model.Refrigerator" %>
<%@ page import="model.MainFood" %>
<%@ page import="model.Recipe" %>
<%@ page import="java.util.List" %>
<% List<Refrigerator> refrigerator = (List<Refrigerator>)request.getAttribute("refrigerator"); %>
<% List<MainFood> mainFood = (List<MainFood>)request.getAttribute("mainFood"); %>
<% List<MainFood> mainFoodImg = (List<MainFood>)request.getAttribute("mainFoodImg"); %>
<% List<Recipe> mainFoodUnit = (List<Recipe>)request.getAttribute("mainFoodUnit"); %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>冷蔵庫 | れしぴろーくん</title>
</head>
<body>
	<!-- ヘッダーここから -->
	<header>
		<h1>れしぴろーくん</h1>
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
	<p>冷蔵庫</p>
	<% double count; %>
	<% if(refrigerator != null){ %>
		<% for(int i=0; i<refrigerator.size();i++){ %>
			<img src="/NMW/img/<% mainFoodImg.get(i).getImage(); %>" width="50">
			<% count = refrigerator.get(i).getF_count(); %>

			<% if(mainFoodUnit.get(i).getUnit() != 1) {%>
				<select>
				<% for (double j=2.0; j<=-2.0;j-=0.25) {%>
					<% if(count+j >= 0) {%>
						<option><%= count+j %></option>
					<% } else {%>
						<% break; %>
					<% } %>
				<% } %>
				</select>
			<% } else {%>
				<select>
					<% for (int j=200; j<=-200;j-=25) {%>
						<% if(count+j >= 0) {%>
							<option><%= count+j %></option>
						<% } else {%>
							<% break; %>
						<% } %>
				<% } %>
				</select>
			<% } %>
			<% if((i %=4) == 0){ %>
				<br>
			<% } %>
		<% } %>
	<% } %>
<%-- 	<c:forEach begin="0" end="${ refrigeratorSize }"> --%>

<%-- 	</c:forEach> --%>
<%-- 	<c:forEach var="refrigerator" items="${ refrigerator }" varStatus="status"> --%>
<%-- 		<img src="/NMW/img/${ refrigerator.image }" width="50"> --%>
<%-- 	</c:forEach> --%>

<!-- 	<select></select> -->

<!-- 	一覧表示 -->
	<c:forEach var="mainFood" items="${ mainFood }" varStatus="status">
		<form action="/NMW/RefrigeratorServlet" method="post">
			<button type="submit" name="food" value="${ mainFood.f_id }">
				<img src="/NMW/img/${ mainFood.image }" width="50">
			</button>
		</form>
	</c:forEach>

<!-- 	<button type="submit" name="food" value="negi"> -->
<!-- 		<img src="/NMW/img/refrigerator/naganegi.png" width="50"> -->
<!-- 	</button> -->
<!-- 	<button type="submit" name="food" value="kyuuri"> -->
<!-- 		<img src="/NMW/img/refrigerator/kyuuri.png" width="50"> -->
<!-- 	</button> -->
<!-- 	<button type="submit" name="food" value="pi-man"> -->
<!-- 		<img src="/NMW/img/refrigerator/pi-man.png" width="50"> -->
<!-- 	</button> -->
<!-- 	<button type="submit" name="food" value="zyagaimo"> -->
<!-- 		<img src="/NMW/img/refrigerator/zyagaimo.png" width="50"> -->
<!-- 	</button> -->
<!-- 	<button type="submit" name="food" value="tamanegi"> -->
<!-- 		<img src="/NMW/img/refrigerator/tamanegi.png" width="50"> -->
<!-- 	</button> -->
<!-- 	<button type="submit" name="food" value="daikonn"> -->
<!-- 		<img src="/NMW/img/refrigerator/daikon.png" width="50"> -->
<!-- 	</button> -->
<!-- 	<button type="submit" name="food" value="ninzin"> -->
<!-- 		<img src="/NMW/img/refrigerator/ninzin.png" width="50"> -->
<!-- 	</button> -->
<!-- 	<button type="submit" name="food" value="kyabetsu"> -->
<!-- 		<img src="/NMW/img/refrigerator/kyabetsu.png" width="50"> -->
<!-- 	</button> -->
<!-- 	<button type="submit" name="food" value="hourensou"> -->
<!-- 		<img src="/NMW/img/refrigerator/hourensou.png" width="50"> -->
<!-- 	</button> -->
<!-- 	<button type="submit" name="food" value="hakusai"> -->
<!-- 		<img src="/NMW/img/refrigerator/hakusai.png" width="50"> -->
<!-- 	</button> -->
<!-- 	<button type="submit" name="food" value="retasu"> -->
<!-- 		<img src="/NMW/img/refrigerator/retasu.png" width="50"> -->
<!-- 	</button> -->
<!-- 	<button type="submit" name="food" value="tomato"> -->
<!-- 		<img src="/NMW/img/refrigerator/tomato.png" width="50"> -->
<!-- 	</button> -->
<!-- 	<button type="submit" name="food" value="nasu"> -->
<!-- 		<img src="/NMW/img/refrigerator/nasu.png" width="50"> -->
<!-- 	</button> -->
<!-- 	<button type="submit" name="food" value="moyashi"> -->
<!-- 		<img src="/NMW/img/refrigerator/moyashi.png" width="50"> -->
<!-- 	</button> -->
<!-- 	<button type="submit" name="food" value="burokkori-"> -->
<!-- 		<img src="/NMW/img/refrigerator/burokkori-.png" width="50"> -->
<!-- 	</button> -->
<!-- 	<button type="submit" name="food" value="shimezi"> -->
<!-- 		<img src="/NMW/img/refrigerator/shimezi.png" width="50"> -->
<!-- 	</button> -->
<!-- 	<button type="submit" name="food" value="enoki"> -->
<!-- 		<img src="/NMW/img/refrigerator/enoki.png" width="50"> -->
<!-- 	</button> -->
<!-- 	<button type="submit" name="food" value="tamago"> -->
<!-- 		<img src="/NMW/img/refrigerator/egg.png" width="50"> -->
<!-- 	</button> -->
<!-- 	<button type="submit" name="food" value="gyuuniku"> -->
<!-- 		<img src="/NMW/img/refrigerator/gyuuniku.png" width="50"> -->
<!-- 	</button> -->
<!-- 	<button type="submit" name="food" value="butaniku"> -->
<!-- 		<img src="/NMW/img/refrigerator/butaniku.png" width="50"> -->
<!-- 	</button> -->
<!-- 	<button type="submit" name="food" value="toriniku"> -->
<!-- 		<img src="/NMW/img/refrigerator/toriniku.png" width="50"> -->
<!-- 	</button> -->
	<!-- メインここまで -->
	<!-- フッターここから -->
	<footer>
		<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
	</footer>
	<!-- フッターここまで -->
</body>
</html>