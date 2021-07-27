<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cpath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <script type="text/javascript">
	function goDelAjax(index) {
		var idx = $("#idx"+index).text();
		$.ajax({ 
  			url : "${cpath}/boardDeleteAjax.do",
  			type : "get",
  			data : {"idx": idx},
  			success : goJson,
  		    error : function() {alert("error");}
  		});
	}
  	function goWrite() {
		location.href="${cpath}/boardForm.do";
	}
  	function goJson() {
		$.ajax({
			url : "${cpath}/boardListAjax.do",
			type : "get",
			datatype : "json",
			success : resultHtml,
			error : function () {alert("error");}	
		});
	}
  	function resultHtml(data) { //data안에 [{},{}...]  --Array형태
  		//alert(data);
  		var html="<table class='table'>";
  		html+="<tr>";	//동적으로 붙히기때문에 여기서부터는 +를 붙힘
  		html+="<td>번호</td>";
  		html+="<td>제목</td>";
  		html+="<td>조회수</td>";
  		html+="<td>작성자</td>";
  		html+="<td>작성일</td>";
  		html+="</tr>";
  		//반복문
  		$.each(data, (index, obj)=> {
  			html+="<tr>";
  			html+="<td id='idx"+index+"'>"+obj.idx+"</td>";
  			html+="<td>"+obj.title+"</td>";
  			html+="<td>"+obj.count+"</td>";
  			html+="<td>"+obj.writer+"</td>";
  			html+="<td>"+obj.indate+"</td>";
  			html+="<td><button class='btn btn-warning btn-sm' onclick='goDelAjax("+index+")'>삭제(Ajax)</button></td>";
  			html+="</tr>";
		});
  		
  		html+="</table>";
  		$("#list").html(html);
	}
/*    	function delBtn(idx) {
  		if(confirm("삭제하시겠습니까?")==true){
  		$.ajax({ 
  			url : "${cpath}/boardDeleteAjax.do",
  			type : "get",
  			data : {"idx": idx},
  			success : goJson,
  		    error : function() {alert("error");}
  		});
  	 }else{
  		return false;
  	 }
  	} */
  	

  </script>
  
</head>
<body>
<div class="container">
  <h2>Spring MVC BOARD</h2>
  <div class="panel panel-default">
    <div class="panel-heading">BOARD LIST</div>
    <div class="panel-body">
      <table class="table">
      <tr>
          <td>번호</td>
          <td>제목</td>
          <td>조회수</td>
          <td>작성자</td>
          <td>작성일</td>
      </tr>
      <c:forEach var="vo" items="${list}">
      <tr>
          <td>${vo.idx}</td>
          <td><a href="${cpath}/boardContent.do?idx=${vo.idx}">${vo.title}</a></td>
          <td>${vo.count}</td>
          <td>${vo.writer}</td>
          <td>${vo.indate}</td>
      </tr>
      </c:forEach>
  </table>
  <button class="btn btn-info btn-sm" onclick="goWrite()">글쓰기</button><br><br>
  <button class="btn btn-success btn-sm" onclick="goJson()">JSON DATA 가져오기(Ajax)</button>
  <div id="list">여기에 게시판 리스트를 출력하시오</div>
  </div>
  <div class="panel-footer">빅데이터분석 4차 (이준혁)</div>
  </div>
</div>
</body>
</html>