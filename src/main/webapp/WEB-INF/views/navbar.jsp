<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<link rel="stylesheet" href="/css/navbar.css">

<nav class="navbar-busience">
	<a class="navbar-logo" href="/main"><img src="/images/bsLogo5.jpg"></a>

	<ul class="navbar-menu">
		<li class="dropdown">
			<a class="dropdown-toggle" data-toggle="dropdown" href="#"><i class="fas fa-cogs"></i>
			<span>시스템관리</span></a>
			<ul class="dropdown-menu">
				<li><a href="#">Page 1-1</a></li>
				<li><a href="#">Page 1-2</a></li>
				<li><a href="#">Page 1-3</a></li>
			</ul>
		</li>
		<li class="dropdown">
			<a class="dropdown-toggle" data-toggle="dropdown" href="#"><i class="fas fa-scroll"></i>
			<span>기준 정보 관리</span></a>
			<ul class="dropdown-menu">
				<li><a href="#">Page 1-1</a></li>
				<li><a href="#">Page 1-2</a></li>
				<li><a href="#">Page 1-3</a></li>
			</ul>
		</li>
	</ul>
	
</nav>

<div class="sidebar">
	<!-- 현재 페이지 타이틀 -->
	<div class="pageName">
		<span>Home</span>
	</div>
	<div class="account-settings">
		<span style="font-size: 15px;">사용자:관리자님</span>
		
		<form action="/logout" method="POST">
			<input type="button" class="btn btn-primary" onclick="javascript:window.open('/pwchange', 'logout', 'width=500,height=650,top=250,left=1000');" value="회원정보변경">
			
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			<input type="submit" class="btn btn-primary" value="로그아웃"/>
		</form>
	</div>
</div>