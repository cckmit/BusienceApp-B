WorkOrderTable_workOrder_ItemName_cell = null;

var WorkOrderTable = new Tabulator("#WorkOrderTable", {
	//페이징
	pagination: "local",
	paginationSize: 20,
	paginationAddRow: "table",
	height: "calc(62% - 100px)",
	layoutColumnsOnNewData : true,
	rowFormatter:function(row){
		if(row.getData().workOrder_ONo != null)
			row.getElement().style.color = "blue";
	},
	columns: [
		{formatter:"rowSelection", titleFormatter:"rowSelection", headerHozAlign:"center", hozAlign:"center", headerSort:false},
		{ title: "작업지시No", field: "workOrder_ONo", headerHozAlign: "center"},
		{ title: "제품코드", field: "workOrder_ItemCode", headerHozAlign: "center"},
		{ title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center", editor:"input",
			cellEdited: function(cell){itemPopupMaster(cell);},
			cellEditCancelled: function(cell){itemPopupMaster(cell);} 
		},
		{ title: "규격", field: "workOrder_Item_STND_1", headerHozAlign: "center"},
		{ title: "설비코드", field: "workOrder_EquipCode", headerHozAlign: "center"},
		{ title: "설비이름", field: "workOrder_EquipName", headerHozAlign: "center", editor:"input",
			cellEdited: function(cell){machinePopupMaster(cell)},
			cellEditCancelled: function(cell){machinePopupMaster(cell)} 
		},
		{ title: "창고재고", field: "workOrder_SQty", headerHozAlign: "center",align:"right"},
		{ title: "지시수량", field: "workOrder_PQty", headerHozAlign: "center", editor:"input",align:"right",
			formatter:"money", formatterParams: {precision: false},
			cellEdited: function(cell){workOrder_PQty_Check(cell);},
			cellEditCancelled: function(cell){workOrder_PQty_Check(cell);}  
		},
		{ title: "작업지시일", field: "workOrder_OrderTime", align: "right", headerHozAlign: "center", editor:"input",
			cellEdited: function(cell)
			{
				WorkOrderTable_workOrder_ItemName_cell = cell;
				Date_Check(cell);
				Right_Move(cell,"right");
			},
			cellEditCancelled: function(cell)
			{
				WorkOrderTable_workOrder_ItemName_cell = cell;
				Date_Check(cell);
				Right_Move(cell,"right");
			}
		},
		{ title: "완료예정일", field: "workOrder_CompleteOrderTime", align: "right", headerHozAlign: "center", editor:"input",
			cellEdited: function(cell)
			{
				WorkOrderTable_workOrder_ItemName_cell = cell;
				Date_Check(cell);
			},
			cellEditCancelled: function(cell)
			{
				WorkOrderTable_workOrder_ItemName_cell = cell;
				Date_Check(cell);
			}
		},
		{ title: "등록일", field: "workOrder_RegisterTime", align: "right", headerHozAlign: "center"},
		{ title: "특이사항", field: "workOrder_Remark", headerHozAlign: "center", editor:"input",
			cellEdited: function(cell)
			{
				Right_Move(cell,"bottom");
			}
		},
		{ title: "사용유무", field: "workOrder_Use_Status", headerHozAlign: "center", align: "center", formatter:"tickCross", editor:true}
	]
});

function itemPopupMaster(cell){
	var cellElement = cell.getElement();
				
	cellElement.addEventListener('keydown', function(e) {
		if (e.keyCode == 13) {
			$.ajax({
				method: "GET",
				url: "product_check?PRODUCT_ITEM_CODE=" + cell.getValue(),
				dataType: "json",
				success: function(data) {
					//console.log("쿼리실행");
					if (data.length == 1) {
						//검색어와 일치하는값이 있는경우
						var today = new Date();
										
						var montht = today.getMonth()+1;
						var month = "";
						if(montht <= 9)
							month = "0"+montht;
						else
							month = montht;
										
						var datet = today.getDate();
						var date = "";
						if(datet <= 9)
							date = "0"+datet;
						else
							date = datet;
						
						cell.getRow().update({
							"workOrder_ItemCode": data[0].product_ITEM_CODE,
							"workOrder_ItemName": data[0].product_ITEM_NAME
						});
						cell.getRow().getCell("workOrder_EquipName").edit();
					} else {
						//검색어와 일치하는값이 없는경우, 팝업창
						WorkOrderTable_workOrder_ItemName_cell = cell;
						itemPopup(cell.getValue(),'grid','','sales');
					}
				}
			})
		}
	});
}
function machinePopupMaster(cell){
	var cellElement = cell.getElement();
				
	cellElement.addEventListener('keydown', function(e) {
		if (e.keyCode == 13) {
			$.ajax({
				method: "GET",
				url: "equipment_check?EQUIPMENT_INFO_CODE=" + cell.getValue(),
				dataType: "json",
				success: function(data) {
					if (data.length == 1) {
						//검색어와 일치하는값이 있는경우
						cell.getRow().update({
							"workOrder_EquipCode": data[0].equipment_INFO_CODE,
							"workOrder_EquipName": data[0].equipment_INFO_NAME
						});
						cell.getRow().getCell("workOrder_PQty").edit();
					} else {
						//검색어와 일치하는값이 없는경우, 팝업창
						WorkOrderTable_workOrder_ItemName_cell = cell;
						var cellValue = cell.getValue();
						machinePopup(cell.getValue(),'grid','','sales');
					}
				}
			})
		}
	});
}

var SalesOrderMasterTable = new Tabulator("#SalesOrderMasterTable", {
	layoutColumnsOnNewData : true,
	height: "calc(38% - 75px)",
	columns: [
		{ title: "수주No", field: "sales_Order_mCus_No", headerHozAlign: "center"},
		{ title: "거래처", field: "sales_Order_mCode", headerHozAlign: "center"},
		{ title: "거래처명", field: "sales_Order_mName", headerHozAlign: "center"},
		{ title: "납기일자", field: "sales_Order_mDlvry_Date", headerHozAlign: "center"},
		{ title: "제품코드", field: "sales_Order_lCode", headerHozAlign: "center"},
		{ title: "제품명", field: "sales_Order_lName", headerHozAlign: "center"},
		{ title: "수주수량", field: "sales_Order_lQty", headerHozAlign: "center", formatter:"money", formatterParams: {precision: false}, align: "right" }
	]
});

$('#FI_NewBtn').click(function(){
	newRow_Add();
})

$('#FI_SearchBtn').click(function(){
	Search();
})

function Search(){
	jsonData = {
		startDate: $("#startDate").val(),
		endDate: $("#endDate").val()
	}
	
	WorkOrderTable.setData("workOrderRest/workOrderSelect",jsonData)
}

function newRow_Add()
{
	LRow = WorkOrderTable.getRows()[WorkOrderTable.getRows().length-1];
	index = 0;
		
	if(WorkOrderTable.getRows().length > 0)
	{
		newRow_flag = false;
		lastRowData = LRow.getData();
		index += lastRowData.index1+1;
			
		if(typeof lastRowData.workOrder_ItemCode=="undefined")
		{
			alert("제품이 입력되지 않았습니다.");
			return;
		}
			
		if(typeof lastRowData.workOrder_EquipCode=="undefined")
		{
			alert("설비가 입력되지 않았습니다.");
			return;
		}
	}
		
	day = today.toISOString().substring(0, 10);
	var nextday = new Date();
	var nextday = new Date(nextday.setDate(nextday.getDate()+1));
		
	//페이지 이동(전체 행 수/페이지당 행 수)
	WorkOrderTable.setPage(Math.ceil(WorkOrderTable.getDataCount("active") / WorkOrderTable.getPageSize()));
	
	WorkOrderTable.addRow({
		index1:index,workOrder_PQty:"0",
		workOrder_OrderTime:day,
		workOrder_CompleteOrderTime:nextday.toISOString().substring(0, 10),
		workOrder_RegisterTime:day,
		workOrder_Use_Status : true});
		
	LRow = WorkOrderTable.getRows()[WorkOrderTable.getRows().length-1];
	LRow.getCell("workOrder_ItemName").edit();
}

var cellElement = null;

//완료예정일을 입력했을경우 완료예정일까지 수주데이터를 검색하여 나타냄
function Date_Check(cell)
{

	cellElement = cell.getElement();
	
	cellElement.addEventListener('keydown', function(e) {
		if (e.keyCode == 13) {
			var cellValue = cell.getValue();
			
			var column = cell.getColumn();
			var columnField = column.getField();
			
			if(columnField == "workOrder_OrderTime")
			{
				if(cellValue.length != 10)
				{
					alert("날짜 입력형식이 잘못 되었습니다. ex)2021-05-15");
					cell.restoreOldValue();
					return;
				}
			}
			else
			{
				if(cellValue.length != 10)
				{
					alert("날짜 입력형식이 잘못 되었습니다. ex)2021-05-15");
					cell.restoreOldValue();
					return;
				}
			}

			data = {
				Sales_Order_lCode: WorkOrderTable_workOrder_ItemName_cell.getRow().getData().workOrder_ItemCode,
				endDate: WorkOrderTable_workOrder_ItemName_cell.getRow().getData().workOrder_CompleteOrderTime
			}
			
			$.ajax({
				method : "GET",
				dataType : "json",
				url : "workOrderRest/MI_Search2?data="+ encodeURI(JSON.stringify(data)),
				success : function(data) {
					////console.log("MI");
					//console.log(data);
					SalesOrderMasterTable.clearData();
					SalesOrderMasterTable.setData(data);
				}
			});
			
			cell.nav().right();
		}
	});

	cellElement = null;
}

function Right_Move(cell,flag)
{
	var cellElement = cell.getElement();
	
	cellElement.addEventListener('keydown', function(e) {
		if (e.keyCode == 13) {
		
			if(flag=="right")
				cell.nav().right();
			else
				newRow_Add();
		}
	});
}

$('#FI_SaveBtn').click(function(){
	var selectedData = WorkOrderTable.getSelectedData();
	
	if(selectedData.length == 0)
	{
		alert("선택된 행이 없습니다.");
		return;
	}
	
	for (var i = 0; i < selectedData.length; i++)
	{
		var workOrder_ItemCode = parseInt(selectedData[i].workOrder_ItemCode);
		if(workOrder_ItemCode == "")
		{
			alert("제품코드가 입력되지 않은 행이 존재합니다.");
			return;
		}
	
		var workOrder_PQty = parseInt(selectedData[i].workOrder_PQty);
		
		if(workOrder_PQty <= 0)
		{
			alert("지시수량이 입력되지 않은 행이 존재합니다.");
			return;
		}
	}
	
	$.ajax({
		method : "post",
		url : "workOrderRest/workOrderRegister",
		data : JSON.stringify(selectedData),
		contentType:'application/json',
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if(data){
				alert("작업지시가 등록 되었습니다.");
				Search();
			}
		}
	});
})

function workOrder_PQty_Check(cell)
{
	var cellElement = cell.getElement();
	
	cellElement.addEventListener('keydown', function(e) {
		if (e.keyCode == 13) {
			var cellValue = cell.getValue();
				
			if(cellValue=="")
			{
				cell.restoreOldValue();
				return;
			}
						
			if(isNaN(cellValue))
			{
				alert("지시수량은 숫자로 입력하여 주십시오.");
				cell.restoreOldValue();
				return;
			}
						
			var cellValue2 =  parseInt(cellValue);
						
			if(cellValue2 < 0)
			{
				alert("지시수량은 양수로 입력하여 주십시오.");
				cell.restoreOldValue();
				return;
			}
			
			cell.nav().right();
		}
	});
}

function machine_gridInit(code,name)
{
	WorkOrderTable_workOrder_ItemName_cell.getRow().update({
			"workOrder_EquipCode": code,
			"workOrder_EquipName": name
		});
		
	WorkOrderTable_workOrder_ItemName_cell.getRow().getCell("workOrder_PQty").edit();
	WorkOrderTable.selectRow(WorkOrderTable.getRows().length-1);
}

//품목선택시 재고수량 같이 나오게
function item_gridInit(PCode, PName, PSTND_1, PPrice, SaveV)
{
	var today = new Date();
					
	var montht = today.getMonth()+1;
	var month = "";
	if(montht <= 9)
		month = "0"+montht;
	else
		month = montht;
					
	var datet = today.getDate();
	var date = "";
	if(datet <= 9)
		date = "0"+datet;
	else
		date = datet;
	
	sales_SM_Total_Qty = 0;
		
	$.ajax({
				method : "GET",
				async: false,
				url : "workOrderRest/MI_Search1?workOrder_ItemCode="+ PCode,
				success : function(data) {
					sales_SM_Last_Qty = parseInt(data[0].sales_SM_Last_Qty);
					sales_SM_In_Qty = parseInt(data[0].sales_SM_In_Qty);
					sales_SM_Out_Qty = parseInt(data[0].sales_SM_Out_Qty);
					
					sales_SM_Total_Qty = sales_SM_Last_Qty + sales_SM_In_Qty - sales_SM_Out_Qty;
				}
	});
	
	dbdata_flag = WorkOrderTable_workOrder_ItemName_cell.getRow().getData().dbdata_flag;
	
	if(dbdata_flag=='y')
	{
		WorkOrderTable_workOrder_ItemName_cell.getRow().update({
			"workOrder_ItemCode": PCode,
			"workOrder_ItemName": PName,
			"product_INFO_STND_1": PSTND_1,
			"qty": sales_SM_Total_Qty
		});
	}
	else
	{
		//
	
		WorkOrderTable_workOrder_ItemName_cell.getRow().update({
			"workOrder_ItemCode": PCode,
			"workOrder_ItemName": PName,
			"product_INFO_STND_1": PSTND_1,
			"qty": sales_SM_Total_Qty
		});
	}
	
	//console.log(WorkOrderTable_workOrder_ItemName_cell.getRow().getData());
	
	data = {
		Sales_Order_lCode: PCode,
		endDate: WorkOrderTable_workOrder_ItemName_cell.getRow().getData().workOrder_CompleteOrderTime
	}
	
	$.ajax({
		method : "GET",
		dataType : "json",
		url : "workOrderRest/MI_Search2?data="+ encodeURI(JSON.stringify(data)),
		success : function(data) {
			////console.log("MI");
			//console.log(data);
			SalesOrderMasterTable.clearData();
			SalesOrderMasterTable.setData(data);
		}
	});
	
	WorkOrderTable_workOrder_ItemName_cell.getRow().getCell("workOrder_EquipName").edit();
	WorkOrderTable.selectRow(WorkOrderTable.getRows().length-1);
}

function grid_itemPopup(cell)
{
	cells = cell;
	var cellElement = cell.getElement();
	
	cellElement.addEventListener('keydown', function(e) {
		if (e.keyCode == 13) {
			var cellValue = cell.getValue();
			itemPopup(cell.getValue(),'grid','','sales');
		}
	});
}

function grid_equipPopup(cell)
{
	cells = cell;
	var cellElement = cell.getElement();
	
	cellElement.addEventListener('keydown', function(e) {
		if (e.keyCode == 13) {
			var cellValue = cell.getValue();
			machinePopup(cell.getValue(),'grid','');
		}
	});
}

$(document).ready(function(){
	Search();
})