<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div class="soloView">
	<!-- MAIN -->
	<div class="main">
		<div class="top-var">
			<!-- 버튼 -->
			<div class="input-box">
				<div class="col-sm-12 col-md-12">
					<span><strong>연도</strong></span> <input class="this_year"
						id="year1" type="text">
				</div>
			</div>
		</div>
		<div id="EQUIPMENT_INFO_TBL1"
			style="float: left; width: 20%; border-right: solid;"></div>
		<div id="WorkOrder_tbl1"
			style="float: left; width: 20%; border-right: solid;"></div>
		<div id="Graph1"
			style="float: left; width: 60%; height: calc(100% - 137px); margin: 0 auto;"></div>
	</div>
	<!-- END MAIN -->

</div>
<script>
	// 월별 설비 작업 현황
	var equipment_INFO_NAME = "";

	var EQUIPMENT_INFO_TBL1 = new Tabulator("#EQUIPMENT_INFO_TBL1", {
		height : "calc(100% - 137px)",
		//복사하여 엑셀 붙여넣기 가능
		clipboard : true,
		ajaxURL : "worktdListRest/One_Grid_init", //ajax URL
		rowClick : function(e, row) {
			EQUIPMENT_INFO_TBL1.deselectRow();
			row.select();

			if (isNaN($("#year1").val())) {
				alert("숫자를 입력하여 주십시오.");
				return;
			}

			if ($("#year1").val().length != 4) {
				alert("연도를 다시 선택하여 주십시오.");
				return;
			}

			equipment_INFO_NAME = row.getData().equipment_INFO_NAME;

			console.log(row.getData());
			$.ajax({
				method : "GET",
				async : true,
				url : "worktdListRest/Month_Select?year=" + $("#year1").val()
						+ "&equipment_INFO_CODE="
						+ row.getData().equipment_INFO_CODE,
				success : function(datas) {
					console.log(datas);
					WorkOrder_tbl1.setData(datas);

					drawArray = [];
					drawArray.push([ 'Year', '생산량' ]);

					for (i = 0; i < datas.length; i++)
						drawArray.push([ datas[i].ym,
								parseInt(datas[i].production_Volume) ]);

					//console.log(row.getData().equipment_INFO_CODE);

					drawChart($("#year1").val() + "년 "
							+ row.getData().equipment_INFO_NAME
							+ ' (월별)설비 작업 현황');

					$.ajax({
						method : "GET",
						async : false,
						url : "worktdListRest/Month_Select_Detail?year="
								+ $("#year1").val() + "&equipment_INFO_CODE="
								+ row.getData().equipment_INFO_CODE,
						success : function(datas) {
							console.log("OK?");
							console.log(datas);
							WorkOrder_tbl1.setData(datas);
						}
					});
				}
			});
		},

		columns : [ {
			title : "설비코드",
			field : "equipment_INFO_CODE",
			headerHozAlign : "center",
			width : 100,
			visible : true
		}, {
			title : "설비이름",
			field : "equipment_INFO_NAME",
			headerHozAlign : "center",
			width : 150,
			visible : true
		} ]
	});

	$('.this_year').datepicker({
		minViewMode : 2,
		format : 'yyyy'
	});

	var WorkOrder_tbl1 = new Tabulator("#WorkOrder_tbl1", {
		height : "calc(100% - 137px)",
		//복사하여 엑셀 붙여넣기 가능
		clipboard : true,
		dataTree : true,
		dataTreeStartExpanded : true,
		rowClick : function(e, row) {
			WorkOrder_tbl1.deselectRow();
			row.select();

			if (row.getData().ym.length != 7) {
				$.ajax({
					method : "GET",
					async : false,
					url : "worktdListRest/Month_Select_Detail2?year="
							+ $("#year1").val() + "&production_Equipment_Code="
							+ row.getData().production_Equipment_Code
							+ "&production_Item_Code="
							+ row.getData().production_Item_Code,
					success : function(datas) {
						drawArray = [];
						drawArray.push([ 'Year', '생산량' ]);

						for (i = 0; i < datas.length; i++)
							drawArray.push([ datas[i].ym,
									parseInt(datas[i].production_Volume) ]);

						console.log(drawArray);

						console.log("---------");
						console.log(row.getData());
						drawChart(row.getData().percent.split("-")[0] + "년 / "
								+ equipment_INFO_NAME + " / "
								+ row.getData().ym + ' / (월별)설비 작업 현황');
					}
				});
			}
		},

		columns : [ {
			title : "날짜",
			field : "ym",
			headerHozAlign : "center",
			width : 200,
			visible : true
		}, {
			title : "생산량",
			field : "production_Volume",
			headerHozAlign : "center",
			width : 120,
			visible : true,
			hozAlign : "right",
			formatter : "money",
			formatterParams : {
				precision : false
			}
		} ]
	});

	google.charts.load('current', {
		packages : [ 'corechart' ]
	});

	var drawArray = [ [ 'Year', '생산량' ], [ '1월', 0 ], [ '2월', 0 ], [ '3월', 0 ],
			[ '4월', 0 ], [ '5월', 0 ], [ '6월', 0 ], [ '7월', 0 ], [ '8월', 0 ],
			[ '9월', 0 ], [ '10월', 0 ], [ '11월', 0 ], [ '12월', 0 ] ];

	function drawChart(value) {
		// Define the chart to be drawn.
		var data = google.visualization.arrayToDataTable(drawArray);

		var view = new google.visualization.DataView(data);
		view.setColumns([ 0, 1, {
			calc : "stringify",
			sourceColumn : 1,
			type : "string",
			role : "annotation"
		} ]);

		var options = {
			title : value
		};

		// Instantiate and draw the chart.
		var chart = new google.visualization.BarChart(document
				.getElementById('Graph1'));
		chart.draw(view, options);
	}

	google.charts.setOnLoadCallback(drawChart);
</script>
</body>
</html>