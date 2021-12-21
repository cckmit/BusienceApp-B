<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<div class="soloView">
	   	<!-- matOrderSub -->
		<!-- MAIN -->
		<div class="main">
			<div class="top-var">
					<!-- 버튼 -->
				<div class="input-button">
					<img id="WIL_SearchBtn" src="images/button/Search.png" />
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<span><strong>입고일</strong></span>
					<input id="startDate" class="today" type="date">
					<span style="text-align: center"><strong>~</strong></span>
					<input id="endDate" class="tomorrow" type="date">
				</div>
			</div>
				<!-- 그리드 생성 장소 -->
			<div id="wipInputListTable"></div>
		</div>
		<!-- END MAIN -->
	</div>
<!-- Javascript -->
<script src="/js/wip/wipInputList.js"></script>
</body>
</html>