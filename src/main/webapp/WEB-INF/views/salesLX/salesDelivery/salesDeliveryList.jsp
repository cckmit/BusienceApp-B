<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="main">
	<div class="top-var">
		<!-- 버튼 -->
		<div class="input-button">
			<img src="images/button/Search.png" id="SDL_SearchBtn"/>
		</div>
		<!-- 버튼 -->
		<div class="input-box">
			<span><strong>해당월</strong></span> 
			<input type="month" id="selectedMonth" class="this_month">
		</div>
	</div>
	<div id="salesDeliveryListTable" class="itemMaster"></div>
	<div id="salesDeliveryCustomerTable" class="itemSub"></div>
</div>
<!-- END MAIN -->
