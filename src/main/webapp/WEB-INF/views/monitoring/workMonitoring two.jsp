<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<h1>작업 모니터링</h1>
	
	<div style="float:left; width: 50%;height:100%; border: solid 1px;">
		<div id="m001_table"></div>
	</div>
	<div style="float:left; width: 50%;height:100%; border-top: solid 1px;">
		<div id="m002_table"></div>
	</div>

	<input id="startDate" class="today" type="date" style="width: 180px; height: 35px; font-size: 20px;"> 
	<input id="endDate" class="tomorrow" type="date" style="width: 180px; height: 35px; font-size: 20px;">
	
	<script type="text/javascript" >
	//Define some test data
	//document.getElementById("").style.fontSize = "50px";
	var m001_table = new Tabulator("#m001_table", {
	    height:"100%",
	    layout:"fitColumns",
	    resizableColumns:false,
	    columns:[
	        {title:"", field:"workOrder_ONo"},
	    ],
	    rowFormatter:function(row){
	    	var element = row.getElement(),
	        data = row.getData(),
	        width = element.offsetWidth,
	        rowTable, cellContents;

	        //clear current row data
	        while(element.firstChild) element.removeChild(element.firstChild);

	        //define a table layout structure and set width of row
	        rowTable = document.createElement("table")
	        rowTable.style.width = (width - 18) + "px";
			rowTable.style.margin = "10px";

	        rowTabletr = document.createElement("tr");
	        cellContents = "<td style='font-size:65px;'><strong>생산량 : <br/><p style='color:red;'>"+data.workOrder_RQty+"</p></strong></td>";
			rowTabletr.innerHTML = cellContents;
			rowTable.appendChild(rowTabletr);

	        rowTabletr = document.createElement("tr");
	        cellContents = "<td style='font-size:65px;'><strong>작업지시번호 : <br/><p style='color:red;'>"+data.workOrder_ONo+"</p></strong></td>";
			rowTabletr.innerHTML = cellContents;
			rowTable.appendChild(rowTabletr);

			rowTabletr = document.createElement("tr");
	        cellContents = "<td style='font-size:65px;'><strong>제품코드 : <br/><p style='color:red;'>"+data.workOrder_ItemCode+"</p></strong></td>";
			rowTabletr.innerHTML = cellContents;
			rowTable.appendChild(rowTabletr);

			rowTabletr = document.createElement("tr");
	        cellContents = "<td style='font-size:65px;'><strong>제품이름 : <br/><p style='color:red;'>"+data.workOrder_ItemName+"</p></strong></td>";
			rowTabletr.innerHTML = cellContents;
			rowTable.appendChild(rowTabletr);

	        //append newly formatted contents to the row
	        element.append(rowTable);
	    },
	});

	var m002_table = new Tabulator("#m002_table", {
	    height:"100%",
	    layout:"fitColumns",
	    resizableColumns:false,
	    columns:[
	        {title:"", field:"workOrder_ONo"},
	    ],
	    rowFormatter:function(row){
	    	var element = row.getElement(),
	        data = row.getData(),
	        width = element.offsetWidth,
	        rowTable, cellContents;

	        //clear current row data
	        while(element.firstChild) element.removeChild(element.firstChild);

	        //define a table layout structure and set width of row
	        rowTable = document.createElement("table")
	        rowTable.style.width = (width - 18) + "px";
			rowTable.style.margin = "10px";

	        rowTabletr = document.createElement("tr");
	        cellContents = "<td style='font-size:65px;'><strong>생산량 : <br/><p style='color:red;'>"+data.workOrder_RQty+"</p></strong></td>";
			rowTabletr.innerHTML = cellContents;
			rowTable.appendChild(rowTabletr);

	        rowTabletr = document.createElement("tr");
	        cellContents = "<td style='font-size:65px;'><strong>작업지시번호 : <br/><p style='color:red;'>"+data.workOrder_ONo+"</p></strong></td>";
			rowTabletr.innerHTML = cellContents;
			rowTable.appendChild(rowTabletr);

			rowTabletr = document.createElement("tr");
	        cellContents = "<td style='font-size:65px;'><strong>제품코드 : <br/><p style='color:red;'>"+data.workOrder_ItemCode+"</p></strong></td>";
			rowTabletr.innerHTML = cellContents;
			rowTable.appendChild(rowTabletr);

			rowTabletr = document.createElement("tr");
	        cellContents = "<td style='font-size:65px;'><strong>제품이름 : <br/><p style='color:red;'>"+data.workOrder_ItemName+"</p></strong></td>";
			rowTabletr.innerHTML = cellContents;
			rowTable.appendChild(rowTabletr);

	        //append newly formatted contents to the row
	        element.append(rowTable);
	    },
	});
	
	setInterval(function(){
			$.get("workMonitoringRest/WorkOrder_select?WorkOrder_EquipCode=m001"+"&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
				console.log(data);
				//console.log("1 오픈");
				m001_table.setData(data);
			});

			$.get("workMonitoringRest/WorkOrder_select?WorkOrder_EquipCode=m002"+"&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
				console.log("2 오픈");
				m002_table.setData(data);
			});
	},1000);

	</script>
	
	<style>
		/*Style row formatter contents*/
		.tabulator-col-title {
		}
		
		.tabulator-row table{
		    vertical-align: middle;
		    border-collapse:collapse;
		}
		
		.tabulator-row table img{
		    border:2px solid #ccc;
		}
		
		.tabulator-row table tr td{
		     border:none;
		}
		
		.tabulator-row table tr td:first-of-type{
		    width:60px;
		}
		
		.tabulator-row table tr td div{
		    padding:5px;
		}
	</style>