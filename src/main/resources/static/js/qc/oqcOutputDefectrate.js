var OQCInspect_tbl = new Tabulator("#OQCInspect_tbl", {
	headerFilterPlaceholder: null,
	height:"calc(100% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	layout:"fitDataStretch",
	rowDblClick:function(e, row){
		OQCInspect_tbl.deselectRow();
		row.select();
    },
  	//Sub Total 색상
    rowFormatter: function(row){
		if(row.getData().oqcinspect_OqcInNo == "Sub Total" ||
				row.getData().oqcinspect_OqcInNo == "Total"){
            row.getElement().style.backgroundColor = "#c0c0c0";
            }
    },

	columns:[
		{title:"순번", field:"num",headerHozAlign:"center",align:"center"},
		{title:"LotNo", field:"oqcinspect_Lot_No",headerHozAlign:"center",align:"center",headerFilter:true,visible:false},
		{title:"출하검사번호", field:"oqcinspect_OqcInNo",headerHozAlign:"center",align:"center",headerFilter:true},
		{title:"출하검사일", field:"oqcinspect_Date",headerHozAlign:"center",align:"right",headerFilter:true},
		{title:"거래처", field:"cus_Code",headerHozAlign:"center",align:"left",visible:false},
		{title:"거래처명", field:"cus_Name",headerHozAlign:"center",align:"left",visible:false},
		{title:"제품코드", field:"product_ITEM_CODE",headerHozAlign:"center",align:"left",headerFilter:true},
		{title:"제품명", field:"product_ITEM_NAME",headerHozAlign:"center",align:"left",headerFilter:true},
		{title:"규격", field:"product_INFO_STND_1",headerHozAlign:"center",align:"left",headerFilter:true},
		{title:"검사자", field:"oqcinspect_Worker_Name",headerHozAlign:"center",align:"left",headerFilter:true},
		{title:"처리구분", field:"oqcinspect_Prcsn_Clsfc_Name",headerHozAlign:"center",align:"left",headerFilter:true},
		{title:"입고수량", field:"oqcinspectType_IQty",headerHozAlign:"center",align:"right",headerFilter:true, formatter:"money", formatterParams: {precision: false}},
		{title:"불량수량", field:"oqcinspect_DQty",headerHozAlign:"center",align:"right",headerFilter:true, formatter:"money", formatterParams: {precision: false}},
		{title:"불량률", field:"oqcinspect_Defectrate",headerHozAlign:"center",align:"right",headerFilter:true},
		{title:"합격수량", field:"oqcinspect_PQty",headerHozAlign:"center",align:"right",headerFilter:true, formatter:"money", formatterParams: {precision: false}},
		{title:"특채수량", field:"oqcinspect_SQty",headerHozAlign:"center",align:"right",headerFilter:true, formatter:"money", formatterParams: {precision: false}},
		{title:"Sample수량", field:"oqcinspect_SaQty",headerHozAlign:"center",align:"right",headerFilter:true, formatter:"money", formatterParams: {precision: false}},
		{title:"문제점", field:"oqcinspect_Problem",headerHozAlign:"center",align:"left",headerFilter:true},
		{title:"비고", field:"remark",headerHozAlign:"center",align:"left",headerFilter:true}
	]
});

var OQCInspect_tbl2 = new Tabulator("#OQCInspect_tbl2", {
	headerFilterPlaceholder: null,
	height:"calc(100% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	layout:"fitDataStretch",
	rowDblClick:function(e, row){
		OQCInspect_tbl2.deselectRow();
		row.select();
    },
  	//Sub Total 색상
    rowFormatter: function(row){
		if(row.getData().oqcinspect_OqcInNo == "Sub Total" ||
				row.getData().oqcinspect_OqcInNo == "Total"){
            row.getElement().style.backgroundColor = "#c0c0c0";
            }
    },

	columns:[
		{title:"순번", field:"num",headerHozAlign:"center",align:"center"},
		{title:"LotNo", field:"oqcinspect_Lot_No",headerHozAlign:"center",align:"center",headerFilter:true,visible:false},
		{title:"출하검사번호", field:"oqcinspect_OqcInNo",headerHozAlign:"center",align:"center",headerFilter:true},
		{title:"출하검사일", field:"oqcinspect_Date",headerHozAlign:"center",align:"right",headerFilter:true},
		{title:"거래처", field:"cus_Code",headerHozAlign:"center",align:"left",visible:false},
		{title:"거래처명", field:"cus_Name",headerHozAlign:"center",align:"left",visible:false},
		{title:"제품코드", field:"product_ITEM_CODE",headerHozAlign:"center",align:"left",headerFilter:true},
		{title:"제품명", field:"product_ITEM_NAME",headerHozAlign:"center",align:"left",headerFilter:true},
		{title:"규격", field:"product_INFO_STND_1",headerHozAlign:"center",align:"left",headerFilter:true},
		{title:"검사자", field:"oqcinspect_Worker_Name",headerHozAlign:"center",align:"left",headerFilter:true},
		{title:"처리구분", field:"oqcinspect_Prcsn_Clsfc_Name",headerHozAlign:"center",align:"left",headerFilter:true},
		{title:"입고수량", field:"oqcinspectType_IQty",headerHozAlign:"center",align:"right",headerFilter:true, formatter:"money", formatterParams: {precision: false}},
		{title:"불량수량", field:"oqcinspect_DQty",headerHozAlign:"center",align:"right",headerFilter:true, formatter:"money", formatterParams: {precision: false}},
		{title:"불량률", field:"oqcinspect_Defectrate",headerHozAlign:"center",align:"right",headerFilter:true},
		{title:"합격수량", field:"oqcinspect_PQty",headerHozAlign:"center",align:"right",headerFilter:true, formatter:"money", formatterParams: {precision: false}},
		{title:"특채수량", field:"oqcinspect_SQty",headerHozAlign:"center",align:"right",headerFilter:true, formatter:"money", formatterParams: {precision: false}},
		{title:"Sample수량", field:"oqcinspect_SaQty",headerHozAlign:"center",align:"right",headerFilter:true, formatter:"money", formatterParams: {precision: false}},
		{title:"문제점", field:"oqcinspect_Problem",headerHozAlign:"center",align:"left",headerFilter:true},
		{title:"비고", field:"remark",headerHozAlign:"center",align:"left",headerFilter:true}
	]
});

function MI_searchBtn1(){
	data = {
		startDate : $("#startDate").val(),
		endDate : $("#endDate").val(),
		product_item_code : $("#PRODUCT_ITEM_CODE1").val(),
		client_code : $("#Client_Code1").val()
		,OQCInspect_Prcsn_Clsfc : $("#OQCInspect_Prcsn_Clsfc1").val()
	}
	
	$.ajax({
		method : "GET",
		dataType : "json",
		url : "oqcOutputDefectrateRest/Search?data="+ encodeURI(JSON.stringify(data)),
		success : function(data) {
			OQCInspect_tbl.setData(oqcinspect_DQty_Set(data));
		}
	});
}

function MI_searchBtn2(){
	if($("#startMonthDate1").val()=="")
	{
		alert("출하검사월을 입력하여 주십시오.");
		return;
	}
	
	data = {
			startMonthDate : $("#startMonthDate1").val(),
			product_item_code : $("#PRODUCT_ITEM_CODE2").val()
			,OQCInspect_Prcsn_Clsfc : $("#OQCInspect_Prcsn_Clsfc2").val()
	}
	
	$.ajax({
		method : "GET",
		dataType : "json",
		url : "oqcOutputDefectrateRest/Search2?data="+ encodeURI(JSON.stringify(data)),
		success : function(data) {
			OQCInspect_tbl2.setData(oqcinspect_DQty_Set(data));
		}
	});
}

function oqcinspect_DQty_Set(data)
{
	for(i=0;i<data.length;i++)
	{
		// 입고수량
		oqcinspectType_IQty = parseInt(data[i].oqcinspectType_IQty);
		// 불량수량
		oqcinspect_DQty = parseInt(data[i].oqcinspect_DQty);
		
		t = oqcinspect_DQty / oqcinspectType_IQty; 
		
		oqcinspect_Defectrate = t * 100;
		
		if(isNaN(oqcinspect_Defectrate))
			oqcinspect_Defectrate = 0;
		
		data[i].oqcinspect_Defectrate = oqcinspect_Defectrate.toFixed(2)+"%";
	}
	
	return data;
}

//팝업창으로부터 특정 파라미터 값으로 데이터를 받는다 
function item_gridInit(PCode,PName,status) {
	$('#PRODUCT_ITEM_CODE'+status).val(PCode);
	$('#PRODUCT_ITEM_NAME'+status).val(PName);
}

//monthInit(1);