<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="cpath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function goDown(uploadPath,uuid,fileName) {
		var fileCallPath = uploadPath+"\\"+uuid+"_"+fileName; //경로를 넣는 변수
		//alert(encodeURIComponent(fileCallPath));
		location.href="${cpath}/download.do?fileName="+encodeURIComponent(fileCallPath);
	}


</script>
</head>
<body>
<!-- 업로드한 파일의 리스트 나오게하는 jsp -->

<div>
	<c:forEach var="vo" items="${list}">
		<li><a href="javascript:goDown('${vo.uploadPath}','${vo.uuid}','${vo.fileName}')">${vo.fileName}</a></li>
	</c:forEach>
</div>

</body>
</html>