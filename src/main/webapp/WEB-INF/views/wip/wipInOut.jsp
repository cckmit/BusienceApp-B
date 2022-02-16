<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<div class="soloView">
	   	<!-- matOrderSub -->
		<!-- MAIN -->
		<div class="main">
			<div class="top-var">
				<!-- 버튼 -->
				
				<div class="input-button">
					<img src="images/button/Search.png" style="visibility:hidden;"/>
				</div>
				
				<!-- 버튼 -->
				<div class="input-box">
					<div>
						<span><strong>LotNo</strong></span>
						<input id="wipLotNo" type="text" placeholder="숫자만 입력" autofocus>
					</div>
				</div>
				
			</div>
				<!-- 그리드 생성 장소 -->
			<div id="wipManageTable" class="master-in"></div>
			<div class="sub-in" style="height: calc(100% - 175px);">
				<div id="wipInputTable"></div>
				<div id="wipOutputTable"></div>
			</div>
		</div>
		<!-- END MAIN -->
	</div>
<!-- Javascript -->
<script src="/js/wip/wipInOut.js?v=<%=System.currentTimeMillis() %>"></script>