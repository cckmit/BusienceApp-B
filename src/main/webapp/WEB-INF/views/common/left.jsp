<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<link rel="stylesheet" href="/css/common/left.css">

<div class="sidebar">
	<!-- 현재 페이지 타이틀 -->
	<div class="pageName">
		<span>${pageName}</span>
	</div>
	<nav class="sidebar-busience">
		<ul class="sidebar-menu">
		</ul>
	</nav>
	<div class="account-settings">
		<span style="font-size: 15px;">사용자 : <sec:authentication property="principal.member.USER_NAME" /></span>
		<div>
			
		</div>
		<form action="/logout" method="POST" style="margin-top:5px;">
			<input type="button" class="btn btn-primary" onclick="javascript:window.open('/pwchange', 'logout', 'width=500,height=500,top=250,left=800');" value="회원정보변경">
			
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			<input type="submit" class="btn btn-primary" value="로그아웃"/>
		</form>
	</div>
</div>

<script src="/js/common/left.js"></script>