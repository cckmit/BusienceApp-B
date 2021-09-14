<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>비지언스 MES</title>
<link rel="stylesheet" type="text/css" href="/css/main.css">
</head>
<body>
<div class="limiter">
	<div class="container-login100">
		<div class="wrap-login100">
			<div class="login100-pic">
				<img src="/images/bsLogo2.png">
			</div>
			
			<form method="post">
				<span class="login100-form-title"><strong> Member Login </strong></span>

				<div class="wrap-input100 validate-input">
					<input class="input100" type="text" id="username" name="username" placeholder="id">
					<span class="focus-input100"></span>
					<span class="symbol-input100">
						<i class="fas fa-id-badge" aria-hidden="true"></i>
					</span>
				</div>

				<div class="wrap-input100 validate-input">
					<input class="input100" type="password" id="password" name="password" placeholder="password">
					<span class="focus-input100"></span>
					<span class="symbol-input100">
						<i class="fa fa-lock" aria-hidden="true"></i>
					</span>
				</div>

				<div class="container-login100-form-btn">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
					<button type="submit" class="login100-form-btn">Login</button>
				</div>

				<div class="text-center" style="padding-top: 12px"></div>
			</form>
			<div class="text-center" style="padding-top: 136px">
				<a class="txt2" style="float: left; margin-left: 300%; height: 5px;">
					<img src="/images/bsLogo5.jpg">
				</a>
			</div>
		</div>
	</div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/js/all.min.js"></script>
</body>
</html>