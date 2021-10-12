// 월별 설비 작업 현황

var EQUIPMENT_INFO_TBL1 = new Tabulator("#EQUIPMENT_INFO_TBL1", {
	height: "calc(100% - 137px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	ajaxURL:"worktdListRest/One_Grid_init", //ajax URL
	rowClick: function (e, row) {
		EQUIPMENT_INFO_TBL1.deselectRow();
		row.select();
		
		if(isNaN($("#year1").val()))
		{
			alert("숫자를 입력하여 주십시오.");
			return;
		}
		
		if($("#year1").val().length!=4)
		{
			alert("연도를 다시 선택하여 주십시오.");
			return;
		}
		
		console.log(row.getData());
		$.ajax({
			method: "GET",
			async: true,
			url: "worktdListRest/Month_Select?year="+$("#year1").val()+"&equipment_INFO_CODE="+row.getData().equipment_INFO_CODE,
			success: function (datas) {
				console.log(datas);
				WorkOrder_tbl1.setData(datas);
				
				drawArray= [];
				drawArray.push(['Year', '생산량']);
				
				for(i=0;i<datas.length;i++)
					drawArray.push([datas[i].ym,parseInt(datas[i].production_Volume)]);
				
				console.log(drawArray);
				
				drawChart();
				
				$.ajax({
					method: "GET",
					async: false,
					url: "worktdListRest/Month_Select_Detail?year="+$("#year1").val()+"&equipment_INFO_CODE="+row.getData().equipment_INFO_CODE,
					success: function (datas) {
						console.log("OK?");
						console.log(datas);
						WorkOrder_tbl1.setData(datas);
					}
				});
			}
		});
	},

	columns: [
		{ title: "설비코드", field: "equipment_INFO_CODE", headerHozAlign: "center", width: 100, visible: true },
		{ title: "설비이름", field: "equipment_INFO_NAME", headerHozAlign: "center", width: 150, visible: true }
	]
});

$('.this_year').datepicker({
        minViewMode: 2,
        format: 'yyyy'
});

var WorkOrder_tbl1 = new Tabulator("#WorkOrder_tbl1", {
	height: "calc(100% - 137px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	dataTree:true,
    dataTreeStartExpanded:true,
	rowClick: function (e, row) {
	},

	columns: [
		{ title: "날짜", field: "ym", headerHozAlign: "center", width: 200, visible: true },
		{ title: "생산량", field: "production_Volume", headerHozAlign: "center", width: 120, visible: true, hozAlign:"right",formatter:"money", formatterParams: {precision: false} }
	]
});

 google.charts.load('current', { packages: ['corechart'] });

 var drawArray = [
            ['Year', '생산량'],
            ['1월', 0],
            ['2월', 0],
            ['3월', 0],
            ['4월', 0],
            ['5월', 0],
            ['6월', 0],
            ['7월', 0],
            ['8월', 0],
            ['9월', 0],
            ['10월', 0],
            ['11월', 0],
            ['12월', 0]
 ];
 
 function drawChart() {
            // Define the chart to be drawn.
            var data = google.visualization.arrayToDataTable(drawArray);

			var view = new google.visualization.DataView(data);
			view.setColumns([0, 1,
                          { calc: "stringify",
                            sourceColumn: 1,
                            type: "string",
                            role: "annotation" }]);

            var options = { title: '(월별)설비 작업 현황' };

            // Instantiate and draw the chart.
            var chart = new google.visualization.BarChart(document.getElementById('Graph1'));
            chart.draw(view, options);
}

google.charts.setOnLoadCallback(drawChart);

// -----------------------------------------------------------------------------------------------------

// 분기별 설비 작업 현황
var EQUIPMENT_INFO_TBL2 = new Tabulator("#EQUIPMENT_INFO_TBL2", {
	height: "calc(100% - 137px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	async: true,
	ajaxURL:"worktdListRest/One_Grid_init", //ajax URL
	rowClick: function (e, row) {
		EQUIPMENT_INFO_TBL2.deselectRow();
		row.select();
		
		if(isNaN($("#year2").val()))
		{
			alert("숫자를 입력하여 주십시오.");
			return;
		}
		
		if($("#year2").val().length!=4)
		{
			alert("연도를 다시 선택하여 주십시오.");
			return;
		}
		
		console.log(row.getData());
		$.ajax({
			method: "GET",
			async: true,
			url: "worktdListRest/Quarter_Select?year="+$("#year2").val()+"&equipment_INFO_CODE="+row.getData().equipment_INFO_CODE,
			success: function (datas) {
				console.log(datas);
				WorkOrder_tbl2.setData(datas);
				
				drawArray2= [];
				drawArray2.push(['Year', '생산량']);
				
				for(i=0;i<datas.length;i++)
					drawArray2.push([datas[i].ym,parseInt(datas[i].production_Volume)]);
				
				console.log(drawArray);
				
				drawChart2();
				
				$.ajax({
					method: "GET",
					async: false,
					url: "worktdListRest/Quarter_Select_Detail?year="+$("#year2").val()+"&equipment_INFO_CODE="+row.getData().equipment_INFO_CODE,
					success: function (datas) {
						console.log("-------");
						console.log(datas);
						WorkOrder_tbl2.setData(datas);
					}
				});
			}
		});
	},

	columns: [
		{ title: "설비코드", field: "equipment_INFO_CODE", headerHozAlign: "center", width: 100, visible: true },
		{ title: "설비이름", field: "equipment_INFO_NAME", headerHozAlign: "center", width: 150, visible: true }
	]
});

var WorkOrder_tbl2 = new Tabulator("#WorkOrder_tbl2", {
	height: "calc(100% - 137px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	dataTree:true,
    dataTreeStartExpanded:true,
	rowClick: function (e, row) {
	},

	columns: [
		{ title: "날짜", field: "ym", headerHozAlign: "center", width: 200, visible: true },
		{ title: "생산량", field: "production_Volume", headerHozAlign: "center", width: 120, visible: true, hozAlign:"right",formatter:"money", formatterParams: {precision: false} }
	]
});

 var drawArray2 = [
            ['Year', '생산량'],
            ['1월', 0],
            ['2월', 0],
            ['3월', 0],
            ['4월', 0],
            ['5월', 0],
            ['6월', 0],
            ['7월', 0],
            ['8월', 0],
            ['9월', 0],
            ['10월', 0],
            ['11월', 0],
            ['12월', 0]
 ];
 
 function drawChart2() {
            // Define the chart to be drawn.
            var data = google.visualization.arrayToDataTable(drawArray2);

			var view = new google.visualization.DataView(data);
			view.setColumns([0, 1,
                          { calc: "stringify",
                            sourceColumn: 1,
                            type: "string",
                            role: "annotation" }]);

            var options = { title: '(분기별)설비 작업 현황' };

            // Instantiate and draw the chart.
            var chart = new google.visualization.BarChart(document.getElementById('Graph2'));
            chart.draw(view, options);
}

google.charts.setOnLoadCallback(drawChart2);

// -----------------------------------------------------------------------------------------------------

// 연별 설비 작업 현황
var EQUIPMENT_INFO_TBL3 = new Tabulator("#EQUIPMENT_INFO_TBL3", {
	height: "calc(100% - 137px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	async: true,
	ajaxURL:"worktdListRest/One_Grid_init", //ajax URL
	rowClick: function (e, row) {
		EQUIPMENT_INFO_TBL3.deselectRow();
		row.select();
		
		if(isNaN($("#year2").val()))
		{
			alert("숫자를 입력하여 주십시오.");
			return;
		}
		
		if($("#year2").val().length!=4)
		{
			alert("연도를 다시 선택하여 주십시오.");
			return;
		}
		
		console.log(row.getData());
		drawChart3equip = row.getData().equipment_INFO_NAME.substr(3);
		$.ajax({
			method: "GET",
			async: true,
			url: "worktdListRest/Year_Select?year="+$("#year3").val()+"&equipment_INFO_CODE="+row.getData().equipment_INFO_CODE,
			success: function (datas) {
				console.log(datas);
				WorkOrder_tbl3.setData(datas);
				
				/*
				drawArray3= [];
				drawArray3.push(['Year', '생산량']);
				
				for(i=0;i<datas.length;i++)
					drawArray3.push([datas[i].ym,parseInt(datas[i].production_Volume)]);
				
				console.log(drawArray3);
				
				drawChart3();
				
				$.ajax({
					method: "GET",
					async: false,
					url: "worktdListRest/Quarter_Select_Detail?year="+$("#year2").val()+"&equipment_INFO_CODE="+row.getData().equipment_INFO_CODE,
					success: function (datas) {
						console.log("-------");
						console.log(datas);
						WorkOrder_tbl3.setData(datas);
					}
				});
				*/
			}
		});
	},

	columns: [
		{ title: "설비코드", field: "equipment_INFO_CODE", headerHozAlign: "center", width: 100, visible: true },
		{ title: "설비이름", field: "equipment_INFO_NAME", headerHozAlign: "center", width: 150, visible: true }
	]
});

var WorkOrder_tbl3 = new Tabulator("#WorkOrder_tbl3", {
	height: "calc(100% - 137px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	dataTree:true,
    dataTreeStartExpanded:true,
	rowClick: function (e, row) {
		WorkOrder_tbl3.deselectRow();
		row.select();
		
		console.log(row.getData());
		
		if(row.getData().ym.length <= 4)
		{
			console.log(row.getData()._children);
			
			//drawChart3year = row.getData().ym;
			//drawChart3equip = 
			
			drawArray3= [];
				drawArray3.push(['Year', '생산량']);
				
				for(i=0;i<row.getData()._children.length;i++)
					drawArray3.push([row.getData()._children[i].ym,parseInt(row.getData()._children[i].production_Volume)]);
					
			drawChart3();
		}
	},

	columns: [
		{ title: "날짜", field: "ym", headerHozAlign: "center", width: 200, visible: true },
		{ title: "생산량", field: "production_Volume", headerHozAlign: "center", width: 120, visible: true, hozAlign:"right",formatter:"money", formatterParams: {precision: false} }
	]
});

 var drawArray3 = [
            ['Year', '생산량'],
            ['1월', 0],
            ['2월', 0],
            ['3월', 0],
            ['4월', 0],
            ['5월', 0],
            ['6월', 0],
            ['7월', 0],
            ['8월', 0],
            ['9월', 0],
            ['10월', 0],
            ['11월', 0],
            ['12월', 0]
 ];
 
 var drawChart3year = '2021'
 var drawChart3equip = 'x'
 function drawChart3() {
            // Define the chart to be drawn.
            var data = google.visualization.arrayToDataTable(drawArray3);

			var view = new google.visualization.DataView(data);
			view.setColumns([0, 1,
                          { calc: "stringify",
                            sourceColumn: 1,
                            type: "string",
                            role: "annotation" }]);

            var options = { title: drawChart3year+'년 '+drawChart3equip+'호기 (연별)설비 작업 현황',bar: {groupWidth: "95%"} };
            
            /*
            var options = {
	           title: '(연별)설비 작업 현황',
	            width: 600,
	            height: 400,
	            bar: {groupWidth: "95%"},
	            legend: { position: "none" },
	          };
			*/
			
            // Instantiate and draw the chart.
            var chart = new google.visualization.BarChart(document.getElementById('Graph3'));
            chart.draw(view, options);
}

google.charts.setOnLoadCallback(drawChart3);

// -----------------------------------------------------------------------------------------

var qflag = true;
$('#q').click(function(){
	if(qflag)
	{
		qflag = false;
	
		document.getElementById("Graph2").style.visibility = "hidden";
		setTimeout(function() {
		  	drawArray2= [];
			drawArray2.push(['Year', '생산량']);
			drawArray2.push(['xxxx년 1분기', 0]);
			drawArray2.push(['xxxx년 2분기', 0]);
			drawArray2.push(['xxxx년 3분기', 0]);
			drawArray2.push(['xxxx년 4분기', 0]);
				
			google.charts.setOnLoadCallback(drawChart2);
			
			document.getElementById("Graph2").style.visibility = "visible";
		}, 500);
	}
});

// 연별 설비 작업 현황
var yflag = true;
$('#y').click(function(){
	if(yflag)
	{
		yflag = false;
	
		document.getElementById("Graph3").style.visibility = "hidden";
		setTimeout(function() {
		  	drawArray3= [];
			drawArray3.push(['Year', '생산량']);
			drawArray3.push(['xxxx년 1분기', 0]);
			drawArray3.push(['xxxx년 2분기', 0]);
			drawArray3.push(['xxxx년 3분기', 0]);
			drawArray3.push(['xxxx년 4분기', 0]);
				
			google.charts.setOnLoadCallback(drawChart3);
			
			document.getElementById("Graph3").style.visibility = "visible";
		}, 500);
	}
});

// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

var EQUIPMENT_INFO_TBL11 = new Tabulator("#EQUIPMENT_INFO_TBL11", {
		height: "calc(100% - 137px)",
		//복사하여 엑셀 붙여넣기 가능
		clipboard: true,
		ajaxURL:"worktdListRest/One_Grid_init", //ajax URL
		rowClick: function (e, row) {
			EQUIPMENT_INFO_TBL11.deselectRow();
			row.select();
			
			if(isNaN($("#year1").val()))
			{
				alert("숫자를 입력하여 주십시오.");
				return;
			}
			
			if($("#year1").val().length!=4)
			{
				alert("연도를 다시 선택하여 주십시오.");
				return;
			}
			
			$.ajax({
				method: "GET",
				async: false,
				url: "worktdListRest/Month_Select?year="+$("#year11").val()+"&equipment_INFO_CODE="+row.getData().equipment_INFO_CODE,
				success: function (datas) {
					//console.log(datas);
					WorkOrder_tbl11.setData(datas);
					
					$.ajax({
						method: "GET",
						async: false,
						url: "worktdListRest/Month_Select_Detail?year="+$("#year1").val()+"&equipment_INFO_CODE="+row.getData().equipment_INFO_CODE,
						success: function (datas) {
							//console.log(datas);
							WorkOrder_tbl11.setData(datas);
						}
					});
					
					//console.log(WorkOrder_tbl11.getData());
					
					var oldData = WorkOrder_tbl11.getData();
					var newData = [];
					var newData2 = [];
					for(i=0;i<oldData.length;i++)
					{
						if(oldData[i]._children.length > 0)
						{
							newData.push(oldData[i]);
							newData2.push(oldData[i]._children);
						}
					}
					
					var nameData = [];
					for(i=0;i<newData.length;i++)
					{
						for(j=0;j<newData[i]._children.length;j++){
							//console.log(newData[i]._children[j].ym);
							nameData.push(newData[i]._children[j].ym);
						}
					}
					
					//console.log(nameData);
					var uniqueArr = [];

					for (var i=0; i<nameData.length; i++) {
					  if (uniqueArr.indexOf(nameData[i]) === -1) uniqueArr.push(nameData[i]);
					}
					//console.log(uniqueArr);
					
					drawArray= [];
					drawArray.push("Year");
					for (var i=0; i<uniqueArr.length; i++){
						drawArray.push(uniqueArr[i]);
						var rol = { role : 'annotation'};
						drawArray.push(rol);
					}
					
					//console.log(newData2);
					
					totalArray= [];
					totalArray.push(drawArray);
					var arr = null;
					for(i=0;i<newData.length;i++){
						arr = [];
						arr.push(newData[i].ym);
						console.log(newData[i]);
						//console.log(newData2[i]);
						
						//for(j=0;j<totalArray[0].length-1;j++)
							//arr.push(0);
						
						var datas = newData2[i];
						
						//console.log(uniqueArr);
						for (var j=0; j<uniqueArr.length; j++)
						{
							/*
							for(j=0;j<datas.length;j++)
							{
								//console.log(datas);
								
								if(datas[j].ym == uniqueArr[j])
								{
									
								}
							}
							*/
							
							var flag = true;
							
							for(m=0;m<datas.length;m++)
							{
								//console.log(datas[m]);
								
								if(datas[m].ym == uniqueArr[j])
								{
									arr.push(parseInt(datas[m].production_Volume));
									arr.push(parseInt(datas[m].production_Volume));
									flag = false;
									break;
								}
							}
							
							if(flag)
							{
								arr.push(0);
								arr.push(0);
							}
						}
						
						console.log("-----------------------------------");
						
						totalArray.push(arr);
					}
					console.log(totalArray);
					
					var options = {
						      title: 'Sales and Purchase Comparsion'    
					};  

					var data = google.visualization.arrayToDataTable(totalArray);
					// Instantiate and draw the chart.
					var chart = new google.visualization.ColumnChart(document.getElementById('Graph11'));
					chart.draw(data, options);
			}
					
			});
		},
	
		columns: [
			{ title: "설비코드", field: "equipment_INFO_CODE", headerHozAlign: "center", width: 100, visible: true },
			{ title: "설비이름", field: "equipment_INFO_NAME", headerHozAlign: "center", width: 150, visible: true }
		]
	});
	
	var WorkOrder_tbl11 = new Tabulator("#WorkOrder_tbl11", {
		height: "calc(100% - 137px)",
		//복사하여 엑셀 붙여넣기 가능
		clipboard: true,
		dataTree:true,
	    dataTreeStartExpanded:true,
		rowClick: function (e, row) {
		},

		columns: [
			{ title: "날짜", field: "ym", headerHozAlign: "center", width: 200, visible: true },
			{ title: "생산량", field: "production_Volume", headerHozAlign: "center", width: 120, visible: true, hozAlign:"right",formatter:"money", formatterParams: {precision: false} }
		]
	});
	
	function name_export(list,value) {
		
		for(i=0;i<list.length;i++)
			if(list[i] == value)
				return true;
		
		return false;
	}