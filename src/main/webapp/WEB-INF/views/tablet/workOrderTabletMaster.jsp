<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
.light_point{
	background-color: red;
    border: 1px;
    width: 10px;
    height: 10px;
    position: relative;
    border-radius: 100%;
    left: 120px;
    top: 12px;
}

@keyframes blink-effect {
	50% {
		opacity: 0;
	}
}
.light_point.starting{
	background-color: green;
	animation: blink-effect 2s step-end infinite;
}

</style>
<div class="soloView" style="width: 100%; height: 100%;">
	<div class="tabs-wrap">
		<ul id="navigation">
		<li class="one selected">
			<a href="#div1">설비명1</a> <div class="light_point starting"></div>
			
		</li>
		<li class="two">
			<a href="#div2">설비명2</a> <div class="light_point"></div>
		</li>
		<li class="two">
			<a href="#div2">설비명3</a> <div class="light_point"></div>
		</li>
		<li class="two">
			<a href="#div2">설비명4</a> <div class="light_point"></div>
		</li>
		<li class="two">
			<a href="#div2">설비명5</a> <div class="light_point"></div>
		</li> 
		<li class="shadow"></li>
	</ul>
	<div id="content">
		<ol>
			<li>
				<div id="div1">
					<div id="front">
						<jsp:include page="workOrderTabletSub.jsp"/>
					</div>
					<div id="end" class="none">
						<jsp:include page="workOrderTabletSub2.jsp"/>
					</div>
				</div>
			</li>
			<li>
				<div id="div2">
					<jsp:include page="workOrderTabletSub.jsp"/>
				</div>
			</li>
			<li>
				<div id="div3">
					<jsp:include page="workOrderTabletSub.jsp"/>
				</div>
			</li>
			<li>
				<div id="div4">
					<jsp:include page="workOrderTabletSub.jsp"/>
				</div>
			</li>
			<li>
				<div id="div5">
					<jsp:include page="workOrderTabletSub.jsp"/>
				</div>
			</li>
		</ol>
	</div>
		<!-- .demo-nav -->
	</div>
	<!-- inline wrapper -->
</div>
<!-- Javascript -->
<script src="/js/tabMenu.js"></script>

<script src="/js/tablet/workOrderTabletSub.js"></script>
<script src="/js/tablet/workOrderTabletSub2.js"></script>