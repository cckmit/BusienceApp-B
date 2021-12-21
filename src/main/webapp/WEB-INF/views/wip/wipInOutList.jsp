<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<div class="soloView">
	   	<!-- matOrderSub -->
		<!-- MAIN -->
		<div class="main">
			<div class="top-var">
					<!-- 버튼 -->
				<div class="input-button">
					<img id="WIOL_SearchBtn" src="images/button/Search.png" />
				</div>
				<!-- 버튼 -->
				<div class="input-box">
					<span><strong>LotNo</strong></span>
					<input id="wip_LotNo" type="text" autofocus>
				</div>
			</div>
				<!-- 그리드 생성 장소 -->
			<div id="wipInOutListTable"></div>
		</div>
		<!-- END MAIN -->
	</div>
<!-- Javascript -->
<script src="/js/wip/wipInOutList.js"></script>
</body>
</html>