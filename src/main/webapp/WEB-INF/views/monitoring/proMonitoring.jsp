<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="container-fluid">
			<h1 class="mt-4" style="font-weight: bold; color: black">생산현황 모니터링</h1>
			<p id="today">Today</p>
			<div class="row">
				<div class="col-md-6">
					<!-- PANEL HEADLINE -->
					<div class="panel panel-headline">
						<div class="panel-heading">
							<h3 class="panel-title">1호기</h3>
							<!-- 
									<p class="panel-subtitle"></p>
									 -->
						</div>
						<div class="panel-body">
							<div id="example-table1"></div>
							<table style="width: 100%;">
								<tr>
									<td style="width: 40%;" align="right">
										모델명&nbsp;&nbsp;&nbsp;</td>
									<td style="width: 60%;"><input type="text"
										id="product_ITEM_NAME" readonly></td>
								</tr>
								<tr>
									<td style="width: 40%;" align="right">시작
										시간&nbsp;&nbsp;&nbsp;</td>
									<td style="width: 60%;"><input type="text"
										id="production_MODIFY_D" readonly></td>
								</tr>
								<tr>
									<td style="width: 40%;" align="right">생산
										수량&nbsp;&nbsp;&nbsp;</td>
									<td style="width: 60%;"><input type="text"
										id="production_VOLUME" readonly></td>
								</tr>
								<tr>
									<td style="width: 40%;" align="right">
										불량&nbsp;&nbsp;&nbsp;</td>
									<td style="width: 60%;"><input type="text"
										id="production_DEFECT_CODE" readonly></td>
								</tr>
							</table>
						</div>
					</div>
					<!-- END PANEL HEADLINE -->
				</div>

				<div class="col-md-6">
					<!-- PANEL HEADLINE -->
					<div class="panel panel-headline">
						<div class="panel-heading">
							<h3 class="panel-title">2호기</h3>
							<!-- 
									<p class="panel-subtitle"></p>
									 -->
						</div>
						<div class="panel-body">
							<div id="example-table2"></div>
							<table style="width: 100%;">
								<tr>
									<td style="width: 40%;" align="right">
										모델명&nbsp;&nbsp;&nbsp;</td>
									<td style="width: 60%;"><input type="text"
										id="product_ITEM_NAME2" readonly></td>
								</tr>
								<tr>
									<td style="width: 40%;" align="right">시작
										시간&nbsp;&nbsp;&nbsp;</td>
									<td style="width: 60%;"><input type="text"
										id="production_MODIFY_D2" readonly></td>
								</tr>
								<tr>
									<td style="width: 40%;" align="right">생산
										수량&nbsp;&nbsp;&nbsp;</td>
									<td style="width: 60%;"><input type="text"
										id="production_VOLUME2" readonly></td>
								</tr>
								<tr>
									<td style="width: 40%;" align="right">
										불량&nbsp;&nbsp;&nbsp;</td>
									<td style="width: 60%;"><input type="text"
										id="production_DEFECT_CODE2" readonly></td>
								</tr>
							</table>
						</div>
					</div>
					<!-- END PANEL HEADLINE -->
				</div>
			</div>
		</div>
	</div>
	<!-- END MAIN -->
</div>
<!-- Javascript -->
<script src="/js/monitoring/proMonitoring.js"></script>