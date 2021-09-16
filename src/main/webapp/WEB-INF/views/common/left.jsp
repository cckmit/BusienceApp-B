<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<link rel="stylesheet" href="/css/common/left.css">

<div class="sidebar">
	<!-- 현재 페이지 타이틀 -->
	<div class="pageName">
		<span>${pageName}</span>
		
	</div>
	<div class="account-settings">
		<span style="font-size: 15px;">사용자 : ${user_name}</span>
		<div>
			
		</div>
		<form action="/logout" method="POST" style="margin-top:5px;">
			<input type="button" class="btn btn-primary" onclick="javascript:window.open('/pwchange', 'logout', 'width=500,height=650,top=250,left=1000');" value="회원정보변경">
			
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			<input type="submit" class="btn btn-primary" value="로그아웃"/>
		</form>
	</div>
</div>