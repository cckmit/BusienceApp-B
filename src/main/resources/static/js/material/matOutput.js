var matOutputTable = new Tabulator("#matOutputTable", {
    height:"calc(50% - 85px)",
	headerFilterPlaceholder: null,
	layoutColumnsOnNewData : true,
	//행클릭 이벤트
	rowFormatter:function(row){
		//rm_RequestNo가 Y면 빨간색 I면 파란색으로 바꿔준다
        if(row.getData().rm_RequestNo == "Y"){
            row.getElement().style.color = "red";
        }else if(row.getData().rm_RequestNo == "I"){
            row.getElement().style.color = "blue";
		}
    },
	rowClick:function(e, row){
		matOutputTable.deselectRow();
		row.select();
	},
	rowSelected:function(row){
    	MRL_Search(row.getData().rm_RequestNo);
    },
	ajaxResponse:function(url, params, response){
		if(response.length == 0){
			toastr.info("목록이 없습니다.");	
		}
		return response;
    },
 	columns:[ 
 		{title:"요청No", field:"rm_RequestNo", headerHozAlign:"center", hozAlign:"right", headerFilter:true},
		{title:"요청자코드", field:"rm_UserCode", visible:false},
 		{title:"요청자", field:"rm_UserName", headerHozAlign:"center", headerFilter:true},
		{title:"부서코드", field:"rm_DeptCode", visible:false},
		{title:"부서", field:"rm_DeptName", headerHozAlign:"center", headerFilter:true},
 		{title:"요청일", field:"rm_Date", headerHozAlign:"center", hozAlign:"right", headerFilter:true},
 		{title:"특이사항", field:"rm_Remark", headerHozAlign:"center", headerFilter:true},
 		{title:"목록확인", field:"rm_Check", visible:false}
	],
});

//matRequest 검색버튼
function MR_Search(){
	
	var data = {
		startDate : $("#startDate").val(),
		endDate : $("#endDate").val(),
		om_Dept_Code : $("#Dept_Name option:selected").text()
	}
	
	matOutputTable.setData("matRequestRest/MRM_Search", data)
	.then(function(){
		//list와 stock의 데이터를 없에준다
		matOutputSubTable.clearData();
		LotMasterTable.clearData();
		matOutMatTable.clearData();
		
		MOM_total = 0;
		LotMasterTable.redraw();
		ResetBtn()
	})
}

$("#MR_SearchBtn").click(function(){
	MR_Search();
})

// 출고구분 select를 구성하기위한 ajax
var output_dtl = dtlSelectList(18);

var matOutputSubTable = new Tabulator("#matOutputSubTable", {
	layoutColumnsOnNewData : true,
	height:"calc(50% - 90px)",
	rowFormatter:function(row){
		//미출고가 없으면 빨간색으로
        if(row.getData().rs_Not_Stocked == 0){
            row.getElement().style.color = "red";
        }
    },
	//행을 클릭하면 matLotMasterTable에 리스트가 나타남
	rowClick:function(e, row){
		//미출고재고가 있다면
		if(row.getData().rs_Not_Stocked != 0){
			matOutputSubTable.deselectRow();
			row.select();
			add_mode = false;
		}
    },
	rowSelected:function(row){
		//LotMaster 품목코드로 검색
		LM_Search(row.getData().rs_ItemCode);
		matOutMatTable.clearData();
		
		ResetBtn();
	},
	ajaxResponse:function(url, params, response){
		if(response.length == 0){
			toastr.info("목록이 없습니다.");	
		}
		return response;
    },
	columns:[
	{formatter:"rowSelection", titleFormatter:"rowSelection", headerHozAlign:"center", hozAlign:"center", headerSort:false},
	{title:"순번", field:"rownum", headerHozAlign:"center", hozAlign:"center", formatter: "rownum"},
	{title:"요청No", field:"rs_RequestNo", visible:false},
 	{title:"코드", field:"rs_ItemCode", headerHozAlign:"center"},
 	{title:"품목명", field:"rs_ItemName", headerHozAlign:"center"},
	{title:"요청수량", field:"rs_Qty", headerHozAlign:"center", hozAlign:"right"},
 	{title:"출고수량", field:"rs_Sum", headerHozAlign:"center", hozAlign:"right"},
 	{title:"미출고재고", field:"rs_Not_Stocked", headerHozAlign:"center", hozAlign:"right"},
 	{title:"재고", field:"rs_Stock_Qty", headerHozAlign:"center", hozAlign:"right"},
	{title:"비고", field:"rs_Remark", headerHozAlign:"center"},
	{title:"구분", field:"rs_Send_Clsfc", headerHozAlign:"center", visible:false},
	{title:"구분", field:"rs_Send_Clsfc_Name", headerHozAlign:"center"}]
});

//OrderSub 목록검색
function MRL_Search(RequestNo){
	$("#RS_RequestNo").val(RequestNo)
	
	var data = {
		OrderNo : RequestNo
	}
	//발주넘버
	matOutputSubTable.setData("matRequestRest/MRS_Search", data)
	.then(function(){
		LotMasterTable.clearData();
		matOutMatTable.clearData();
	});
	
}
//부서이름 선택했을때 코드도 같이 변경
$("#Dept_Name").change(function(){
	$("#Dept_Code").val($("#Dept_Name").val())
});

// 현재 출고합계수량
var MOM_total = 0;
//4번째 테이블에 행 추가모드 확인
var add_mode = false;

var LotMasterTable = new Tabulator("#LotMasterTable", {
	height:"calc(50% - 124.1px)",
	layoutColumnsOnNewData : true,
	rowClick:function(e, row){
		//matOutputSubTable에서 선택된 행
		MOS_selectedRow = matOutputSubTable.getData("selected")[0]
		//미출고재고 - 현재 출고 합계수량이 0보다 크면 선택함
		if(MOS_selectedRow.rs_Not_Stocked-MOM_total > 0){
			row.toggleSelect();
		}else{
			row.deselect();
		}
    },
	rowSelected:function(row){
		if(!add_mode){
			matOutMatTable.clearData();
			add_mode = true;
			UseBtn();
		}
		//matOutputSubTable에서 선택된 행
		MOS_selectedRow = matOutputSubTable.getData("selected")[0]
		//출고수량 정하기
			
		//미출고재고 - 현재 출고합계수량 < LotMaster 수량
		if(MOS_selectedRow.rs_Not_Stocked-MOM_total < row.getData().lm_Qty){
			OP_QTY = MOS_selectedRow.rs_Not_Stocked-MOM_total
		}else{
			OP_QTY = row.getData().lm_Qty
		}
		//salesOutMatTable 반영
		matOutMatTable.addRow({
			"om_RequestNo": matOutputSubTable.getData("selected")[0].rs_RequestNo,
			"om_LotNo": row.getData().lm_LotNo,
			"om_ItemCode": row.getData().lm_ItemCode,
			"om_Qty": OP_QTY,
			"om_DeptCode": $("#Dept_Code").val(),
			"om_DeptName": $("#Dept_Name option:checked").text(),
			"om_OutDate" : moment(new Date()).format('YYYY-MM-DD HH:mm:ss'),			
			"om_Send_Clsfc": matOutputSubTable.getData("selected")[0].rs_Send_Clsfc
		});
		MOM_total = MOM_total + OP_QTY;
		LotMasterTable.redraw();
    },
	rowDeselected:function(row){
		//클릭한 행과 같은 랏번호를 찾아서 삭제해줌
		for(i=0;i<matOutMatTable.getDataCount();i++){
			if(matOutMatTable.getData()[i].om_LotNo == row.getData().lm_LotNo){
				MOM_total = MOM_total - matOutMatTable.getData()[i].om_Qty;
				matOutMatTable.getRows()[i].delete();
				LotMasterTable.redraw();
			}
		}
    },
 	columns:[
	{title:"LotNo", field:"lm_LotNo", headerHozAlign:"center"},
	{title:"품목코드", field:"lm_ItemCode", headerHozAlign:"center", bottomCalc:function(){return "출고합계수량"}},
	{title:"수량", field:"lm_Qty", headerHozAlign:"center", hozAlign:"right", formatter:"money", formatterParams: {precision: false},
		bottomCalc:function(){return MOM_total;}},
	{title:"거래처코드", field:"lm_ClientCode", headerHozAlign:"center"},
	{title:"거래처명", field:"lm_ClientName", headerHozAlign:"center"}
	]
});

//LotMaster 목록검색
function LM_Search(itemCode){
	
	LotMasterTable.setData("matOutputRest/LM_Search", {itemCode : itemCode})
	.then(function(){
		/*
		if(LM_datas.length == 0){
			alert("해당 품목은 재고량이 없습니다.")
		}*/
		MOM_total = 0;
		LotMasterTable.redraw();
	})
}

var matOutMatTable = new Tabulator("#matOutMatTable", {
	height:"calc(50% - 124.1px)",
	layoutColumnsOnNewData : true,
	rowAdded : function (row) {
		row.getTable().deselectRow();
		row.select();
	},
 	columns:[
 	{title:"순번", field:"om_No", headerHozAlign:"center", formatter:"rownum", hozAlign:"center"},
	{title:"요청No", field:"om_RequestNo", headerHozAlign:"center", width: 122},
	{title:"LotNo", field:"om_LotNo", headerHozAlign:"center", width: 122},
 	{title:"품목코드", field:"om_ItemCode", headerHozAlign:"center"},
 	{title:"수량", field:"om_Qty", headerHozAlign:"center", hozAlign:"right", 
		formatter:"money", formatterParams: {precision: false}},
	{title:"부서코드", field:"om_DeptCode", visible:false},
	{title:"부서명", field:"om_DeptName", headerHozAlign:"center"},
	{title:"출고일자", field:"om_OutDate", visible:false},
	{title:"출고구분", field:"om_Send_Clsfc", visible:false}
	]
});

function MOM_Search(requestNo,itemCode){
	var datas = {
		om_RequestNo : requestNo,
		om_ItemCode : itemCode
	}
	$.ajax({
		method : "GET",
		async: false,
		url : "matOutputRest/MOM_Search",
		data : datas,
		success : function(MOM_datas) {
			matOutMatTable.setData(MOM_datas);
		}
	});
}

//SOM_Save
function MOM_Save() {
	if(matOutMatTable.getData().length == 0){
		alert("저장할 데이터가 없습니다.")
		return;
	}
	var selectedRow = matOutMatTable.getData();
	
	//OrderSub 저장부분
	$.ajax({
		method: "post",
		url: "matOutputRest/matOutputSave",
		data: JSON.stringify(matOutMatTable.getData()),
		contentType:'application/json',
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function(result) {
			if (result) {
				alert("저장되었습니다.");
				lCode_select(selectedRow[0].om_RequestNo)
			} else {
				alert("오류")
			}
		}
	});
}

//SOM_SaveBtn
$('#MOM_SaveBtn').click(function(){
	MOM_Save();
})

//품목코드로 matOutputSubTable 선택하는 코드
function lCode_select(value){
	var rowCount = matOutputTable.getDataCount("active");
	//컬럼값을 검색해서 입력값을 포함하는 값이 있으면 선택한다. 
	for(i=0;i<rowCount;i++){
		var lCode = matOutputTable.getData()[i].rm_RequestNo;
		//발주번호가 입력내용을 포함하면 코드 실행
		if(lCode == value){
			//발주번호가 같은 행 선택
			matOutputTable.deselectRow();
			matOutputTable.getRows()[i].select();
			break;
		}
	}
}
$("#barcodelotNo").change(function(){
	var rows = LotMasterTable.getRows();
	var sum = MOM_total;
	var full = matOutputSubTable.getData("selected")[0].rs_Not_Stocked;
	var value = $(this).val();
	for(var i=0;i<LotMasterTable.getDataCount();i++){
		if(rows[i].getData().lm_LotNo == value){
			if(full > sum){
				LotMasterTable.selectRow(rows[i]);
				break;
			}else{
				toastr.warning("더이상 선택할 수 없습니다.")
				break;
			}
		}
	}
	$(this).val("");
})

$("#autoOutput").click(function(){
	var rows = LotMasterTable.getRows();
	var sum = MOM_total;
	var full = matOutputSubTable.getData("selected")[0].rs_Not_Stocked;
	for(var i=0;i<LotMasterTable.getDataCount();i++){
		if(full > sum){
			LotMasterTable.selectRow(rows[i]);
		}else{
			break;
		}
		sum += rows[i].getData().lm_Qty
	}
})
