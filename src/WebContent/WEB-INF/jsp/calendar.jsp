<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="servlet.CalendarServlet" %>
<% int[] c_e = (int[])request.getAttribute("c_e"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>カレンダー | れしぴろーくん</title>
<!-- スタイルシートの挿入 -->
<link rel= "stylesheet" href="/NMW/css/common.css">
<link rel= "stylesheet" href="/NMW/css/calendar.css">
</head>
<body>
	<!-- ヘッダーここから -->
	<header>
		<a href ="/NMW/RefrigeratorServlet"><img class="logo" src="/NMW/img/logo/logo.png" alt="れしぴろーくん"></a>
		<div class="logout">
		<input class="btn btn--orange" type="submit" name="LOOUT" value="ログアウト"
		onclick="location.href='/NMW/LogoutServlet'">
		</div>
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
	<div class="wrapper">
	<div hidden id="java_row_data">
<c:forEach var="r" items="${ c_e }" varStatus="status">
${ r }///
</c:forEach>
	</div>
    	<!-- 年月表示のヘッダー -->
    	<h2 id="header"></h2>

    	<!-- ボタンクリックで月移動 -->
    	<div id="next-prev-button">
        	<button id="prev" onclick="prev()">‹</button>
        	<button id="next" onclick="next()">›</button>
    	</div>

    	<!-- カレンダー -->
    	<div id="calendar"></div>
	</div>

	<table class="calculator">
    	<tr>
    		<th>週</th>
    		<th>自炊費</th>
    		<th>外食費</th>
    		<th>差額</th>
    	</tr>
    	<tr>
			<td>1</td>
			<td>${c_e_weekSum[0]}</td>
			<td>${e_o_weekSum[0]}</td>
			<td>${diff_weekSum[0]}</td>
		</tr>
		<tr>
			<td>2</td>
			<td>${c_e_weekSum[1]}</td>
			<td>${e_o_weekSum[1]}</td>
			<td>${diff_weekSum[1]}</td>
		</tr>
		<tr>
			<td>3</td>
			<td>${c_e_weekSum[2]}</td>
			<td>${e_o_weekSum[2]}</td>
			<td>${diff_weekSum[2]}</td>
		</tr>
		<tr>
			<td>4</td>
			<td>${c_e_weekSum[3]}</td>
			<td>${e_o_weekSum[3]}</td>
			<td>${diff_weekSum[3]}</td>
		</tr>
		<tr>
			<td>5</td>
			<td>${c_e_weekSum[4]}</td>
			<td>${e_o_weekSum[4]}</td>
			<td>${diff_weekSum[4]}</td>
		</tr>
		<tr>
			<td>6</td>
			<td>${c_e_weekSum[5]}</td>
			<td>${e_o_weekSum[5]}</td>
			<td>${diff_weekSum[5]}</td>
		</tr>
		<tr>
			<td>月合計</td>
			<td>${c_e_Sum}</td>
			<td>${e_o_Sum}</td>
			<td>${diff_Sum}</td>
		</tr>
	</table>

	<div class="balloon2">
	  <div class="icon2">
	    <img src = "/NMW/img/logo/logo_resipiro-.png">
	  </div>
	 	 <div class="chatting2">
	    <div class="says2">
	      	<p>${diff_Sum}円お得になってるピヨ！</p>
	    </div>
	  </div>
	</div>
	<!-- メインここまで -->
	<!-- フッターここから -->
	<footer>
	<p>&copy; Copyright recipiro-kun. All rights reserverd.</p>
	</footer>
	<!-- フッターここまで -->

<!-- JavaScriptここから -->
<script>
const week = ["日", "月", "火", "水", "木", "金", "土"];
const today = new Date();

const cd = [];
const row_date = [];
cd.push(document.getElementById('java_row_data').textContent.replace(/\r?\n/g, '').trim().split("///"));
// console.log(document.getElementById('java_row_data').textContent.replace(/\r?\n/g, '').split("///"));
// console.log(cd[0][2]);

// 月末だとずれる可能性があるため、1日固定で取得
var showDate = new Date(today.getFullYear(), today.getMonth(), 1);

// 初期表示
window.onload = function () {
    showProcess(today, calendar);
};
// 前の月表示
function prev(){
    showDate.setMonth(showDate.getMonth() - 1);
    showProcess(showDate);
}

// 次の月表示
function next(){
    showDate.setMonth(showDate.getMonth() + 1);
    showProcess(showDate);
}

// カレンダー表示
function showProcess(date) {
    var year = date.getFullYear();
    var month = date.getMonth();
    document.querySelector('#header').innerHTML = year + "年 " + (month + 1) + "月";

    var calendar = createProcess(year, month);
    document.querySelector('#calendar').innerHTML = calendar;
}

// カレンダー作成
function createProcess(year, month) {
    // 曜日
    var calendar = "<table><tr class='dayOfWeek'>";
    for (var i = 0; i < week.length; i++) {
        calendar += "<th>" + week[i] + "</th>";
    }
    calendar += "</tr>";

    var count = 0;
    var startDayOfWeek = new Date(year, month, 1).getDay();
    var endDate = new Date(year, month + 1, 0).getDate();
    var lastMonthEndDate = new Date(year, month, 0).getDate();
    var row = Math.ceil((startDayOfWeek + endDate) / week.length);

    // 1行ずつ設定
    for (var i = 0; i < row; i++) {
        calendar += "<tr>";
        // 1colum単位で設定
        for (var j = 0; j < week.length; j++) {
            if (i == 0 && j < startDayOfWeek) {
                // 1行目で1日まで先月の日付を設定
                calendar += "<td class='disabled'>" + (lastMonthEndDate - startDayOfWeek + j + 1) + "</td>";
            } else if (count >= endDate) {
                // 最終行で最終日以降、翌月の日付を設定
                count++;
                calendar += "<td class='disabled'>" + (count - endDate) + "</td>";
            } else {
                // 当月の日付を曜日に照らし合わせて設定
                count++;
                if(year == today.getFullYear()
                  && month == (today.getMonth())
                  && count == today.getDate()){
                    calendar += "<td class='today'>" + count +'<br>'+cd[0][count]+ "</td>";
                } else {
                    calendar += "<td>" + count +'<br>'+cd[0][count]+ "</td>";
                }
            }
        }
        calendar += "</tr>";
    }
    return calendar;
}
</script>
</body>
</html>