<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="container-fluid">
			<h1 class="mt-4" style="font-weight: bold; color: black">불량현황
				모니터링</h1>
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
						</div>
					</div>
					<!-- END PANEL HEADLINE -->
				</div>
			</div>
		</div>
	</div>
	<!-- END MAIN -->
</div>
<script src="js/monitoring/defectMonitoring.js"></script>