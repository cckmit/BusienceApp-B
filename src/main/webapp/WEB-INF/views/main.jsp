<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!doctype html>
<html lang="en">
<head>
<title>비지언스 MES</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<jsp:include page="common_css.jsp"></jsp:include>
</head>
<body style="overflow : hidden">
	<!-- WRAPPER -->
	<div id="wrapper">
		<!-- NAVBAR -->
		<jsp:include page="navbar.jsp"></jsp:include>
		<!-- END NAVBAR -->
		<!-- LEFT SIDEBAR -->
		<!-- END LEFT SIDEBAR -->
		<div class="soloView">
			<!-- MAIN -->
			<div class="main">
				<div style="text-align: center;">
					<font size="2">본 홈페이지는 크롬 브라우저에 최적화 되어 있습니다.<br />크롬브라우저는 아래의 링크에서 다운받으실 수 있습니다. <br />
					<a href="https://www.google.co.kr/intl/ko/chrome/">다운로드</a></font>
				</div>
				
				<form action="/logout" method="POST">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
					<input type="submit" class="btn btn-primary" value="Logout"/>
				</form>
			</div>
			<!-- END MAIN -->
		</div>
	</div>
	<!-- END WRAPPER -->
</body>
<jsp:include page="common_js.jsp"></jsp:include>
</html>