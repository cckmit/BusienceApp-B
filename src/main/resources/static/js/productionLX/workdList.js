// 출고구분 select를 구성하기위한 ajax
var dtl_arr = new Object();

$.ajax({
	method: "GET",
	async: false,
	url: "dtl_tbl_select?NEW_TBL_CODE=29",
	success: function (datas) {
		for (i = 1; i < 4; i++) {
			dtl_arr[datas[i].child_TBL_NO] = datas[i].child_TBL_TYPE;
		}
	}
});

var WorkOrder_tbl_DayP = new Tabulator("#WorkOrder_tbl_DayP", {
	height: "calc(99.5% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	rowClick: function (e, row) {
		WorkOrder_tbl_DayP.deselectRow();
		row.select();

		MI_searchBtn2("day", row.getData().workOrder_ONo);
	},

	columns: [
		{ title: "설비코드", field: "workOrder_EquipCode", headerHozAlign: "center", width: 100, visible: false },
		{ title: "설비이름", field: "workOrder_EquipName", headerHozAlign: "center", width: 100, visible: true },
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center", width: 160 },
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center", width: 100 },
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center", width: 150 },
		{ title: "지시수량", field: "workOrder_PQty", headerHozAlign: "center", align: "right" },
		{ title: "작업시작일", field: "workOrder_StartTime", headerHozAlign: "center", align: "right" },
		{
			title: "접수여부", field: "workOrder_WorkStatus", headerHozAlign: "center", align: "center"
			, formatter: function (cell, formatterParams) {
				var value = cell.getValue();
				if (dtl_arr[value] != null) {
					value = dtl_arr[value];
				} else {
					value = "";
				}
				return value;
			}
		}
	]
});

var WorkOrder_tbl_DayC = new Tabulator("#WorkOrder_tbl_DayC", {
	height: "calc(99.5% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	rowFormatter: function (row) {
		if (row.getData().production_Date == "Total") {
			row.getElement().style.backgroundColor = "#c0c0c0";
		}
	},

	columns: [
		{ title: "날짜", field: "production_Date", headerHozAlign: "center" },
		{ title: "생산량", field: "production_Volume", headerHozAlign: "center", align: "right" },
		{ title: "누적량", field: "production_Volume2", headerHozAlign: "center", align: "right" },
		{ title: "달성율", field: "percent", headerHozAlign: "center", align: "right" }
	]
});

/*
var WorkOrder_tbl_MonthP = new Tabulator("#WorkOrder_tbl_MonthP", {
	height: "calc(100% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	groupBy:"workOrder_EquipName",
	rowClick:function(e, row){
		WorkOrder_tbl_MonthP.deselectRow();
		row.select();
		
		MI_searchBtn2("month",row.getData().workOrder_ONo);
	},
	
	columns: [
		{ title: "설비코드", field: "workOrder_EquipCode", headerHozAlign: "center", width: 100 , visible:false},
		{ title: "설비이름", field: "workOrder_EquipName", headerHozAlign: "center", width: 180 , visible:false},	
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center", width: 160 },
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center", width: 100 },
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center", width: 180},
		{ title: "지시수량", field: "workOrder_PQty", headerHozAlign: "center", align:"right"},
		{ title: "작업시작일", field: "workOrder_StartTime", headerHozAlign: "center", align:"right"},
		{ title: "접수여부", field: "workOrder_WorkStatus", headerHozAlign: "center",editor:"select",align:"center"
			,formatter:function(cell, formatterParams){
				 var value = cell.getValue();
				 if(dtl_arr[value] != null){
					   value = dtl_arr[value];   
					}else{
					   value = "";
					}
				  return value;
			}
		}
	]
});

var WorkOrder_tbl_MonthC = new Tabulator("#WorkOrder_tbl_MonthC", {
	height: "calc(100% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	rowFormatter: function(row){
		if(row.getData().production_Date == "Total"){
			row.getElement().style.backgroundColor = "#c0c0c0";
		}
	},
	
	columns: [
		{ title: "날짜", field: "production_Date", headerHozAlign: "center"},
		{ title: "생산량", field: "production_Volume", headerHozAlign: "center",align:"right"},
		{ title: "달성율", field: "percent", headerHozAlign: "center",align:"right"}
	]
});
*/

function MI_searchBtn1() {
	datas = {
		startDate: $("#startDate1").val(),
		endDate: $("#endDate1").val(),
		PRODUCT_ITEM_CODE: $("#PRODUCT_ITEM_CODE1").val(),
		Machine_Code: $("#Machine_Code1").val()
	}

	$.ajax({
		method: "GET",
		dataType: "json",
		url: "workdListRest/MI_Search1?data=" + encodeURI(JSON.stringify(datas)),
		success: function (data) {
			for(i=0;i<data.length;i++)
			{
				var workOrder_StartTime = data[i].workOrder_StartTime;
				data[i].workOrder_StartTime = workOrder_StartTime.substring(0,workOrder_StartTime.length-2);
			}
			
			WorkOrder_tbl_DayP.setData(data);
		}
	});
}

function MI_searchBtn3() {
	data = {
		startDate: $("#startDate2").val(),
		endDate: $("#endDate2").val(),
		PRODUCT_ITEM_CODE: $("#PRODUCT_ITEM_CODE2").val(),
		Machine_Code: $("#Machine_Code2").val()
	}

	$.ajax({
		method: "GET",
		dataType: "json",
		url: "workdListRest/MI_Search1?data=" + encodeURI(JSON.stringify(data)),
		success: function (data) {
			//console.log("MI");
			console.log(data);
			WorkOrder_tbl_MonthP.setData(data);
		}
	});
}

function MI_searchBtn2(dayMonthFlag, workOrder_ONo) {
	if (dayMonthFlag == "day") {
		data2 = {
			dayMonthFlag: dayMonthFlag,
			workOrder_ONo: workOrder_ONo
		}

		$.ajax({
			method: "GET",
			dataType: "json",
			url: "workdListRest/MI_Search2?data=" + encodeURI(JSON.stringify(data2)),
			success: function (data) {
				//console.log("MI");
				if (data.length == 0) {
					alert("해당 작업지시No에는 아직 입력된 생산량이 없습니다.");
					WorkOrder_tbl_DayC.clearData();

					var chartgoogledatas = [];
					var chartgoogledata = [];
					//chartgoogledata.push(i);
					chartgoogledata.push("");
					//chartgoogledata.push(0);
					chartgoogledata.push(0);
					chartgoogledata.push('지시수량');
					//chartgoogledata.push(parseInt(WorkOrder_tbl_DayP.getSelectedData().workOrder_PQty));
					//console.log(WorkOrder_tbl_DayP.getSelectedData()[0].workOrder_PQty);
					chartgoogledata.push(0);
					chartgoogledata.push(0);
					chartgoogledata.push('생산량');
					chartgoogledata.push(0);
					chartgoogledata.push(0);
					chartgoogledata.push('누적량');

					chartgoogledatas.push(chartgoogledata);

					drawChart(chartgoogledatas);

					return;
				}

				PRODUCTION_Volume = 0;

				for (i = 0; i < data.length - 1; i++) {
					PRODUCTION_Volume += parseInt(data[i].production_Volume);

					data[i].production_Volume2 = PRODUCTION_Volume;
				}

				Percent = 0;
				for (i = 0; i < data.length - 1; i++) {
					Percent += parseFloat(data[i].percent);

					data[i].percent = Percent + "%";
				}

				data[data.length - 1].production_Volume2 = data[data.length - 1].production_Volume;
				data[data.length - 1].percent = Percent + "%";

				var chartlabel = [];
				var chartproductdata = [];
				var charttotaldata = [];
				var piedata = [];

				var chartgoogledatas = [];

				for (i = 0; i < data.length - 1; i++) {
					//console.log(data[i]);
					chartlabel.push(data[i].production_Date);
					chartproductdata.push(data[i].production_Volume);
					charttotaldata.push(data[i].production_Volume2);

					var chartgoogledata = [];
					//chartgoogledata.push(i);
					chartgoogledata.push(data[i].production_Date);
					//chartgoogledata.push(parseInt(WorkOrder_tbl_DayP.getSelectedData()[0].workOrder_PQty));
					chartgoogledata.push(parseInt(WorkOrder_tbl_DayP.getSelectedData()[0].workOrder_PQty));
					chartgoogledata.push('지시수량');
					//chartgoogledata.push(parseInt(WorkOrder_tbl_DayP.getSelectedData().workOrder_PQty));
					//console.log(WorkOrder_tbl_DayP.getSelectedData()[0].workOrder_PQty);
					chartgoogledata.push(parseInt(data[i].production_Volume));
					chartgoogledata.push(parseInt(data[i].production_Volume));
					chartgoogledata.push('생산량');
					chartgoogledata.push(data[i].production_Volume2);
					chartgoogledata.push(data[i].production_Volume2);
					chartgoogledata.push('누적량');

					chartgoogledatas.push(chartgoogledata);
				}

				drawChart(chartgoogledatas);

				WorkOrder_tbl_DayC.setData(data);
			}
		});
	}
	else if (dayMonthFlag == "month") {
		data = {
			dayMonthFlag: dayMonthFlag,
			workOrder_ONo: workOrder_ONo
		}

		$.ajax({
			method: "GET",
			dataType: "json",
			url: "workdListRest/MI_Search2?data=" + encodeURI(JSON.stringify(data)),
			success: function (data) {
				//console.log("MI");
				console.log(data);
				WorkOrder_tbl_MonthC.setData(data);
			}
		});
	}
}

google.load("visualization", "1", { packages: ["corechart"] });
//google.charts.load('current', { 'packages': ['bar'] });
google.setOnLoadCallback(drawChart);

function drawChart(chartgoogledata) {
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Day');
	data.addColumn('number', '지시수량');
	//data.addColumn({ type: 'number', role: 'annotation' });
	data.addColumn({ type: 'string', role: 'annotationText' });
	//data.addColumn('number', '생산량',{type:'number', role:'annotationText'});
	data.addColumn('number', '생산량', { type: 'number', role: 'annotation' });
	data.addColumn({ type: 'number', role: 'annotation' });
	data.addColumn({ type: 'string', role: 'annotationText' });
	data.addColumn('number', '누적량');
	data.addColumn({ type: 'number', role: 'annotation' });
	data.addColumn({ type: 'string', role: 'annotationText' });

	console.log(chartgoogledata);

	if (chartgoogledata != undefined)
		data.addRows(chartgoogledata);
	else {
		data.addRows([
			['0', 0, '주석 설명', 0, 0, '주석 설명', 0, 0, '주석 설명']
		]);
	}

	var options = {
		title: '세부작업현황 그래프',
		chartArea: {
			// leave room for y-axis labels
			width: '85%',
			height: '85%'
		},
		legend: { position: 'bottom' }
	};

	// line
	//var chart = new google.charts.Line(document.getElementById('material'));
	//chart.draw(data, google.charts.Line.convertOptions(options));

	// bar
	//var chart = new google.charts.Bar(document.getElementById('material'));
	//chart.draw(data, google.charts.Bar.convertOptions(options));

	// core
	var chart = new google.visualization.LineChart(document.getElementById('material'));
	chart.draw(data, options);
}

// onkeypress="javascript:if(event.keyCode==13) {itemFinishedPopup($(this).val(),'input','1')}"
document.getElementById("PRODUCT_ITEM_NAME1").onkeypress = function (event) {
	if (event.keyCode == 13) itemPopup(document.getElementById("PRODUCT_ITEM_NAME1").value, 'input', '1', '55');
}

document.getElementById("Machine_Name1").onkeypress = function (event) {
	if (event.keyCode == 13) machinePopup(document.getElementById("Machine_Name1").value, 'input', '1');
}