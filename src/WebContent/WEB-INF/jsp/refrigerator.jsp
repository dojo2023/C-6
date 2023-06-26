<%@page import="java.lang.reflect.Array"%>
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
<link rel="stylesheet" href="/NMW/css/common.css">
<link rel="stylesheet" href="/NMW/css/refrigerator.css">
</head>
<body>
	<!-- ヘッダーここから -->
	<header>
		<a href ="/NMW/RefrigeratorServlet"><img class="logo" src="/NMW/img/logo/logo.png" alt="れしぴろーくん"></a>
		<nav>
			<ul>
				<li><a href="/NMW/RefrigeratorServlet">冷蔵庫</a></li>
				<li><a href="/NMW/RecipeListServlet">レシピ</a></li>
				<li><a href="/NMW/MainFoodListServlet">食材管理</a></li>
				<li><a href="/NMW/CalendarServlet">カレンダー</a></li>
				<li><a href="/NMW/MyPageServlet">マイページ</a></li>
			</ul>
		</nav>
	</header>
	<!-- ヘッダーここまで -->
	<!-- メインここから -->
	<!-- 	冷蔵庫の一覧表示 -->
<div class="ref">
	<form action="/NMW/RefrigeratorServlet" method="post" name="form_num">
		<% double count; %>
		<% int c_br = 0; %>
		<input type="hidden" id="submit_jsp_f_id" name="f_id" value="-1">
		<input type="hidden" id="submit_jsp_num" name="num" value="0">
		<% if(refrigerator != null){ %>
			<% for(int i=0; i<refrigerator.size();i++){ %>
				<% count = refrigerator.get(i).getF_count(); %>
				<% if(count != 0) {%>
					<img src="/NMW/img<%= mainFoodImg.get(i).getImage() %>" width="50">
					<% c_br++; %>
					<% if(mainFoodUnit.get(i).getUnit() != 1) {%>
						<select class="select" name="<%= mainFood.get(i).getF_id() %>">
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
					<% if(((c_br) % 5) == 0){ %>
						<br>
					<% } %>
				<% } %>
			<% } %>
		<% } %>

		<div class="text">
		<% for(int i=0; i<3;i++) { %>
			<% String text[] = {"", "", ""}; %>
			<% for(int j=0; j<3;j++) { %>
				<% if(refrigerator.get(0).getText().get((i*3)+j) != null) { %>
					<% text[j] = refrigerator.get(0).getText().get((i*3)+j);%>
				<% } %>
			<% } %>
			<div>
				<input type="text" size="10" value="<%= text[0] %>">
				<select name=”item”>
				  <option value=”item1”>1.0</option>
				  <option value=”item2”>2.0</option>
				  <option value=”item3”>3.0</option>
				</select>
				<input type="text" size="10" value="<%= text[1] %>">
				<select name=”item”>
				  <option value=”item1”>1.0</option>
				  <option value=”item2”>2.0</option>
				  <option value=”item3”>3.0</option>
				</select>
				<input type="text" size="10" value="<%= text[2] %>">
				<select name=”item”>
				  <option value=”item1”>1.0</option>
				  <option value=”item2”>2.0</option>
				  <option value=”item3”>3.0</option>
				</select>
			</div>
		<% } %>
		</div>
	</form>
</div>
<!-- 	一覧表示 -->
	<div class="mainfood">
	<c:forEach var="mainFood" items="${ mainFood }" varStatus="status">
		<form action="/NMW/RefrigeratorServlet" method="post">
			<button class="mf_btn" type="submit" name="food" value="${ mainFood.f_id }">
				<img src="/NMW/img/${ mainFood.image }" width="50">
			</button>
		</form>
			<c:if test="${ ((status.index+1)%7)==0 }">
				<br>
			</c:if>
	</c:forEach>
	</div>
	<!-- メインここまで -->
	<!-- フッターここから -->
	<footer>
		<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
	</footer>
	<!-- フッターここまで -->
	<script type="text/javascript" src="js/refrigerator.js"></script>
</body>
</html>