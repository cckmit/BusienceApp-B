// 연별 설비 작업 현황
var EQUIPMENT_INFO_TBL1 = new Tabulator(
	"#EQUIPMENT_INFO_TBL1",
	{
		height: "calc(100% - 137px)",
		//복사하여 엑셀 붙여넣기 가능
		clipboard: true,
		ajaxURL: "worktdListRest/One_Grid_init", //ajax URL
		rowClick: function(e, row) {
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

			if (isNaN($("#year2").val())) {
				alert("숫자를 입력하여 주십시오.");
				return;
			}

			if ($("#year2").val().length != 4) {
				alert("연도를 다시 선택하여 주십시오.");
				return;
			}

			if ($("#year1").val() > $("#year2").val()) {
				alert("끝 연도는 시작 연도보다 작게 선택하여 주십시오.");
				return;
			}

			console.log(row.getData());
			$
				.ajax({
					method: "GET",
					async: true,
					url: "worktdListRest/Year_Select?year1="
						+ $("#year1").val() + "&year2="
						+ $("#year2").val()
						+ "&equipment_INFO_CODE="
						+ row.getData().equipment_INFO_CODE
						+ "&equipment_TYPE="
						+ row.getData().equipment_TYPE,
					success: function(datas) {
						console.log(datas);
						WorkOrder_tbl1.setData(datas);

						if (datas.length > 0) {

							drawArray3 = [];
							drawArray3.push(['Year', '생산량']);

							if (datas[0]._children.length == 1) {
								drawArray3
									.push([
										datas[0]._children[0].ym,
										parseInt(datas[0]._children[0].production_Volume)]);

								if (datas[0]._children[0].production_Volume > 150000) {
									drawChart2(
										datas[0]._children[0].percent,
										datas[0]._children[0].production_Volume,
										10);
								} else {
									drawChart2(
										datas[0]._children[0].percent,
										datas[0]._children[0].production_Volume,
										0);
								}
							} else {
								for (i = 0; i < datas[0]._children.length; i++)
									drawArray3
										.push([
											datas[0]._children[i].ym,
											parseInt(datas[0]._children[i].production_Volume)]);

								drawChart(datas[0]._children[0].percent);
							}

							// ---------------------------------------------------------------

							/*
							if(datas[0]._children.length == 1)
							{
								console.log("++++++++++++++");
								console.log(row.getData());
								drawChart2(datas[0].ym,datas[0].production_Volume);
							}
							else
							{
								drawArray3= [];
								drawArray3.push(['Year', '생산량']);
								
								for(i=0;i<row.getData()._children.length;i++)
									drawArray3.push([row.getData()._children[i].ym,parseInt(row.getData()._children[i].production_Volume)]);
									
								drawChart(row.getData().ym);
							}
							 */

							// ------------------------------------------------------------------
							WorkOrder_tbl1.getRows()[0].select();
						} else {
							drawArray3 = [];
							drawArray3.push(['Year', '생산량']);
							drawArray3.push([2021, 0]);
							drawChart(2021);
						}

					}
				});
		},

		columns: [{
			title: "설비코드",
			field: "equipment_INFO_CODE",
			headerHozAlign: "center",
			width: 100,
			visible: true
		}, {
			title: "설비이름",
			field: "equipment_INFO_NAME",
			headerHozAlign: "center",
			width: 150,
			visible: true
		}, {
			title: "설비종류",
			field: "equipment_TYPE",
			headerHozAlign: "center",
			width: 150,
			visible: true
		}]
	});

$('.this_year').datepicker({
	minViewMode: 2,
	format: 'yyyy'
});

var WorkOrder_tbl1 = new Tabulator(
	"#WorkOrder_tbl1",
	{
		height: "calc(100% - 137px)",
		//복사하여 엑셀 붙여넣기 가능
		clipboard: true,
		dataTree: true,
		dataTreeStartExpanded: true,
		rowClick: function(e, row) {
			WorkOrder_tbl1.deselectRow();
			row.select();

			console.log(row.getData());

			if (row.getData().ym.length <= 4) {
				console.log("------");

				console.log(row.getData()._children);

				//drawChart3year = row.getData().ym;
				//drawChart3equip = 

				drawArray3 = [];
				drawArray3.push(['Year', '생산량']);

				for (i = 0; i < row.getData()._children.length; i++)
					drawArray3
						.push([
							row.getData()._children[i].ym,
							parseInt(row.getData()._children[i].production_Volume)]);

				if (row.getData()._children.length == 1) {
					console.log("++++++++++++++");
					console.log(row.getData());

					if (row.getData().production_Volume > 150000) {
						drawChart2(row.getData().ym,
							row.getData().production_Volume, 10);
					} else {
						drawChart2(row.getData().ym,
							row.getData().production_Volume, 0);
					}
				} else {
					drawChart(row.getData().ym);
				}
			}
		},

		columns: [{
			title: "날짜",
			field: "ym",
			headerHozAlign: "center",
			width: 200,
			visible: true
		}, {
			title: "생산량",
			field: "production_Volume",
			headerHozAlign: "center",
			width: 120,
			visible: true,
			hozAlign: "right",
			formatter: "money",
			formatterParams: {
				precision: false
			}
		}]
	});

google.charts.load('current', {
	packages: ['corechart']
});

var drawArray3 = [['Year', '생산량'], ['1월', 0], ['2월', 0],
['3월', 0], ['4월', 0], ['5월', 0], ['6월', 0], ['7월', 0],
['8월', 0], ['9월', 0], ['10월', 0], ['11월', 0], ['12월', 0]];

var drawChart3year = '2021'
var drawChart3equip = 'x'
function drawChart(value) {
	if (typeof value == "undefined") {
		var now = new Date(); // 현재 날짜 및 시간
		var year = now.getFullYear(); // 연도
		value = year;
	}

	// Define the chart to be drawn.
	var data = google.visualization.arrayToDataTable(drawArray3);

	var view = new google.visualization.DataView(data);
	view.setColumns([0, 1, {
		calc: "stringify",
		sourceColumn: 1,
		type: "string",
		role: "annotation"
	}]);

	var options = {
		title: value + '년 (연별)설비 작업 현황',
		bar: {
			groupWidth: "20%"
		}
	};

	var chart = new google.visualization.BarChart(document
		.getElementById('Graph1'));
	chart.draw(view, options);
}

function drawChart2(value, range, rangevalue) {
	if (typeof value == "undefined") {
		var now = new Date(); // 현재 날짜 및 시간
		var year = now.getFullYear(); // 연도
		value = year;
	}

	// Define the chart to be drawn.
	var data = google.visualization.arrayToDataTable(drawArray3);

	var view = new google.visualization.DataView(data);
	view.setColumns([0, 1, {
		calc: "stringify",
		sourceColumn: 1,
		type: "string",
		role: "annotation"
	}]);

	var options = {
		title: value + '년 (연별)설비 작업 현황',
		bar: {
			groupWidth: "20%"
		},
		hAxis: {
			viewWindow: {
				min: parseInt(range) - rangevalue,
				max: parseInt(range) + rangevalue
			}
		}
	};

	var chart = new google.visualization.BarChart(document
		.getElementById('Graph1'));
	chart.draw(view, options);
}

google.charts.setOnLoadCallback(drawChart);