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
	<!-- 	冷蔵庫の一覧表示 -->
	<p>冷蔵庫</p>
	<% double count; %>
	<% if(refrigerator != null){ %>
		<% for(int i=0; i<refrigerator.size();i++){ %>
			<img src="/NMW/img<%= mainFoodImg.get(i).getImage() %>" width="50">
			<% count = refrigerator.get(i).getF_count(); %>
			<% if(mainFoodUnit.get(i).getUnit() != 1) {%>
				<select>
				<% for (double j=2.0; j>=-2.0;j-=0.25) {%>
					<% if(count+j >= 0) {%>
						<% if(j != 0.0) {%>
							<option><%= count+j %></option>
						<% } else {%>
							<option selected><%= count+j %></option>
						<% } %>

					<% } else {%>
						<% j = -3.0; %>
					<% } %>
				<% } %>
				</select><span class="unit">個</span>
			<% } else {%>
				<select>
					<% for (int j=200; j>=-200;j-=25) {%>
						<% if(count+j >= 0) {%>
							<% if(j != 0) {%>
								<option><%= count+j %></option>
							<% } else {%>
								<option selected><%= count+j %></option>
							<% } %>

						<% } else {%>
							<% j = -300; %>
						<% } %>
				<% } %>
				</select><span class="unit">g</span>
			<% } %>
			<% if(((i+1) % 4) == 0){ %>
				<br>
			<% } %>
		<% } %>
	<% } %>

<!-- 	一覧表示 -->
	<c:forEach var="mainFood" items="${ mainFood }" varStatus="status">
		<form action="/NMW/RefrigeratorServlet" method="post">
			<button type="submit" name="food" value="${ mainFood.f_id }">
				<img src="/NMW/img/${ mainFood.image }" width="50">
			</button>
		</form>
	</c:forEach>
	<!-- メインここまで -->
	<!-- フッターここから -->
	<footer>
		<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
	</footer>
	<!-- フッターここまで -->
</body>
</html>