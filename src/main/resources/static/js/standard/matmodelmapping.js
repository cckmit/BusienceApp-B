var dtl_arr = dtlSelectList(32);

var dtl_arr2 = dtlSelectList(33);

var product_ITEM_CODE = null;

var PRODUCT_INFO_TBL = new Tabulator("#PRODUCT_INFO_TBL", {
	layoutColumnsOnNewData : true,
	paginationAddRow: "table",
	height: "calc(100% - 175px)",
	headerFilterPlaceholder: null,
	rowClick: function(e, row) {	
		PRODUCT_INFO_TBL.deselectRow();
		row.select();
	
		BOMListTable.setData([BOM_Child(row,0)]);	
		
		if(row.getData().bom_Registered)
		{
			if(product_ITEM_CODE == null)
			{
				PRODUCT_INFO_TBL_NEW_ROW();
			}
			else
			{
				Routing_tbl.clearData();
				
				PRODUCT_INFO_TBL_NEW_ROW();
			}
		}
		else
		{
			Routing_tbl.clearData();
			
			PRODUCT_INFO_TBL_NEW_ROW();
		}
			
		Material_Model_Mapping_tbl.clearData();
		
		Routing_tbl_SelectRow = null; 
		
		product_ITEM_CODE = row.getData().product_ITEM_CODE;
	},
	ajaxResponse:function(url, params, response){
		if(response.length == 0){
			toastr.info("목록이 없습니다.");	
		}
		return response;
    },
	columns: [
		{ title: "품목코드", field: "product_ITEM_CODE", headerHozAlign: "center", hozAlign: "right", headerFilter: true },
		{ title: "품목명", field: "product_ITEM_NAME", headerHozAlign: "center", headerFilter: true , width: "200" },
		{ title: "규격", field: "product_INFO_STND_1", headerHozAlign: "center", hozAlign: "right", headerFilter: true },
		{ title: "자재분류", field: "product_MTRL_CLSFC_NAME", headerHozAlign: "center", hozAlign: "right", headerFilter: true },
		{ title: "BOM등록여부", field:"bom_Registered",  headerHozAlign: "center", hozAlign: "center",headerFilter: true, formatter:"tickCross"}
	]
});

var max_Routing_Order = 1;

function PRODUCT_INFO_TBL_NEW_ROW(){
	product_ITEM_CODE = PRODUCT_INFO_TBL.getSelectedRows()[0].getData().product_ITEM_CODE;
				
				$.ajax({
					method: "GET",
					async: false,
					url: "routingInputRest/Routing_tbl_Search?Routing_ItemCode=" + product_ITEM_CODE,
					success: function(datas) {
						for(i=0;i<datas.length;i++)
						{
							datas[i].routing_Orderv = datas[i].routing_Order;
							datas[i].routing_FairCodev = datas[i].routing_FairCode;
						}
						
						Routing_tbl.setData(datas);
					}
				});
}

var level_flag = 0;

var BOMListTable = new Tabulator("#BOMListTable", {
	layoutColumnsOnNewData : true,
	index:"bom_ItemCode",
	dataTree:true,
    dataTreeStartExpanded:true,
	dataTreeChildColumnCalcs:true,
	height:"calc(60% - 175px)",
	rowDblClick:function(e, row){
		console.log(row.getData());
	
		if(Routing_tbl_SelectRow == null)
		{
			alert("입력할 공정을 클릭 한 후에 새행을 추가하여 주십시오.");
			return;
		}
	
		if(row.getData().bom_State=="완제품")
		{
			alert("완제품은 등록할 수 없습니다.");
			return;
		}
	
		//BOMListTable.deselectRow();
		//row.select();
		
		flag = true;
		
		if(Material_Model_Mapping_tbl.getData()[0] != null)
		{
			if(row.getData().bom_level != Material_Model_Mapping_tbl.getData()[0].bom_level)
			{
				alert("레밸"+Material_Model_Mapping_tbl.getData()[0].bom_level+"과 동일한 레밸의 자재를 등록하여 주십시오.");
				return;
			}
		}
		
		
		$.ajax({
					method: "GET",
					async: false,
					url: "matmodelmappingRest/matmodelmapping_AddRow?Mat_Model_ItemCode=" + product_ITEM_CODE + "&Mat_Model_FairCode="+routing_FairCode+"&bom_ItemCode="+row.getData().bom_ItemCode+"&bom_Parent_ItemCode="+row.getData().bom_Parent_ItemCode,
					success: function(datas) {
						console.log(datas);
					
						if(datas.length > 0)
						{
							flag = false;
							alert("이미 등록된 자재입니다.");
						}
						else
						{
							datalist = Material_Model_Mapping_tbl.getData();
		
							for(i=0;i<datalist.length;i++)
							{
								console.log(datalist[i]);
								
								if(datalist[i].mat_Model_ItemBCode == row.getData().bom_ItemCode
								&& datalist[i].mat_Model_ItemFCode == row.getData().bom_Parent_ItemCode)
								{
									flag = false;
									alert("이미 등록된 자재입니다.");
									return;
								}
							}
						}
					}
		});
		
		if(flag)
		{
			Material_Model_Mapping_tbl.addRow({"routing_FairCode":routing_FairCode,"product_ITEM_CODE":product_ITEM_CODE,"mat_Model_ItemBCode":row.getData().bom_ItemCode,"mat_Model_ItemFCode":row.getData().bom_Parent_ItemCode,"mat_Model_ItemBName":row.getData().bom_ItemName,"bom_level":row.getData().bom_level});
			var row = Material_Model_Mapping_tbl.getRows()[Material_Model_Mapping_tbl.getDataCount()-1];
			row.select();
			console.log(row);
			return;
		}
	},
	columns: [ //Define Table Columns
		{ title: "level", field: "bom_level",headerHozAlign: "center"},
		{ title: "부모품목코드", field: "bom_Parent_ItemCode", visible:false},
		{ title: "품목코드", field: "bom_ItemCode", headerHozAlign: "center" },
		{ title: "품목명", field: "bom_ItemName", headerHozAlign: "center" , width: "200" },
		{ title: "수량", field: "bom_Qty", headerHozAlign: "center", hozAlign: "right", editor:"input"},
		{ title: "규격1", field: "bom_STND_1", headerHozAlign: "center"},
		{ title: "품목종류", field: "bom_State", headerHozAlign: "center"},
		{ title: "작업자", field: "bom_Modifier", headerHozAlign: "center", visible:false },
		{ title: "작업날짜", field: "bom_Modify_Date", headerHozAlign: "center", formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}, visible:false}
	],
});

var Routing_tbl_SelectRow = null;
var routing_FairCode = null;

function Routing_tbl_Select(){
	$.ajax({
				method: "GET",
				async: false,
				url: "matmodelmappingRest/Mat_Model_Mapping_Select?Mat_Model_ItemCode=" + product_ITEM_CODE + "&Mat_Model_FairCode="+routing_FairCode,
				success: function(datas) {
					console.log(datas);
					
					Material_Model_Mapping_tbl.clearData();
					Material_Model_Mapping_tbl.setData(datas);
					
					//if(datas.length < 10)
					//{
						//max = 10 - datas.length;
						
						//for(i=0;i<max;i++)
							//Material_Model_Mapping_tbl.addRow({"product_ITEM_CODE":product_ITEM_CODE,"routing_FairCode":routing_FairCode});
					//}
				}
		});	
}

var Routing_tbl = new Tabulator("#Routing_tbl", {
	layout:"fitDataStretch",
	height:"calc(58.85% - 175px)",
	rowClick: function(e, row) {	
		Routing_tbl.deselectRow();
		row.select();
		
		//Material_Model_Mapping_tbl.addRow();
		
		console.log(row.getData());
		
		Routing_tbl_SelectRow = row;
		routing_FairCode = row.getData().routing_FairCode;
		
		Routing_tbl_Select();	
	},
	columns: [ //Define Table Columns
		{ title: "제품코드", field: "routing_ItemCode" , visible:false },
		{ title: "공정순서v", field: "routing_Orderv", visible:false},
		{ title: "공정코드v", field: "routing_FairCodev", visible:false},
		{ title: "순서", field: "routing_Order",headerHozAlign: "center", hozAlign:"right"
			,cellEdited: function(cell)
			{
				Right_Move(cell,"right");
				cell.getRow().select();
			}
		},
		{ title: "코드", field: "routing_FairCode", headerHozAlign: "center"
			,cellEdited: function(cell)
			{
				Right_Move(cell,"right");
				cell.getRow().select();
			}
			,formatter:function(cell, formatterParams){
		         var value = cell.getValue();
		         if(dtl_arr[value] != null){
		               value = dtl_arr[value];   
		            }else{
		               value = "";
		            }
		          return value;
		      },
      		editorParams:{values:dtl_arr}
		},
		{ title: "검사방법", field: "routing_HowTest", headerHozAlign: "center",width:150, editor:"select", visible:false
			,cellEdited: function(cell)
			{
				Right_Move(cell,"right");
				cell.getRow().select();
			}
			,formatter:function(cell, formatterParams){
		         var value = cell.getValue();
		         if(dtl_arr2[value] != null){
		               value = dtl_arr2[value];   
		            }else{
		               value = "";
		            }
		          return value;
		      },
      		editorParams:{values:dtl_arr2}
		},
		{ title: "영업입고", field: "routing_BsnsRcvng", headerHozAlign: "center",width:150, editor:"select", hozAlign:"center", visible:false
			,cellEdited: function(cell)
			{
				Right_Move(cell,"right");
				cell.getRow().select();
			},
			editorParams:{values:["N", "Y"]}
		},
		{ title: "비고", field: "routing_Remarks", headerHozAlign: "center", editor:"input", visible:false
			,cellEdited: function(cell)
			{
				Right_Move(cell,"bottom");
				cell.getRow().select();
			}
		}
	],
});
var WorkOrder_tbl_workOrder_ItemName_cell = null;
var Material_Model_Mapping_tbl = new Tabulator("#Material_Model_Mapping_tbl", {
	height:"calc(58.85% - 175px)",
	columns: [ //Define Table Columns
		{formatter:"rowSelection", titleFormatter:"rowSelection", headerHozAlign:"center", hozAlign:"center", headerSort:false, width:40},		
		{ title: "품목 코드", field: "product_ITEM_CODE", visible:false },	
		{ title: "공정 코드", field: "routing_FairCode", visible:false },
		{ title: "품목 코드v", field: "mat_Model_ItemCode", visible:false },	
		{ title: "공정 코드v", field: "mat_Model_FairCode", visible:false },
		{ title: "순번", field: "mat_Model_Num", visible:true , hozAlign:"center" },
		{ title: "부모 코드", field: "mat_Model_ItemFCode", visible:false },
		{ title: "원자재 코드", field: "mat_Model_ItemBCode" },
		{ title: "원자재 명", field: "mat_Model_ItemBName",width: "200",headerHozAlign: "center"
			, cellEditing:function(cell){
				nametest(cell);
			}
			, cellEdited: function(cell)
			{
				WorkOrder_tbl_workOrder_ItemName_cell = cell;
				grid_itemPopup(WorkOrder_tbl_workOrder_ItemName_cell);
			}
			, cellEditCancelled: function(cell)
			{
				WorkOrder_tbl_workOrder_ItemName_cell = cell;
				grid_itemPopup(WorkOrder_tbl_workOrder_ItemName_cell);
			}
		},
		{ title: "level", field: "bom_level",headerHozAlign: "center", visible:false}
	],
});

function nametest(cell){
	var cellElement = cell.getElement();
				cellElement.addEventListener('keydown', function(e) {
					if (e.keyCode == 46 || e.keyCode == 8){
						cell.getRow().update({"mat_Model_ItemBCode":"","mat_Model_ItemBName":""});
					}
				});
}

$('#Bom_Save').click(function(){
	Bom_Save();
});

$('#SOL_NewBtn').click(function(){
	if(Routing_tbl_SelectRow == null)
	{
		alert("입력할 공정을 클릭 한 후에 자재를 추가하여 주십시오.");
		return;
	}
	
	Material_Model_Mapping_tbl.addRow();
});

$('#SOL_DeleteBtn').click(function(){
	datalist = {};
	datalist.data = Material_Model_Mapping_tbl.getSelectedData();
	
	if(datalist.data.length>0)
	{
		for(i=0;i<datalist.data.length;i++)
		{
			if(datalist.data[i].mat_Model_Num != null)
			{
				if(confirm("해당 데이터를 삭제하시겠습니까?"))
				{
					$.ajax({
						method: "GET",
						async: false,
						url: "matmodelmappingRest/matmodelmapping_delete?data=" + encodeURI(JSON.stringify(datalist)),
						success: function(datas) {
						}
					});
					
					break;
				}
				else
					return;
			}
		}
	}
	else
	{
		alert("삭제할 행을 선택한 후에 시도하여 주십시오.");
		return;
	}
	
	rows = Material_Model_Mapping_tbl.getSelectedRows();
			
	for(j=0;j<rows.length;j++)
		rows[j].delete();
});

$('#SOL_SaveBtn').click(function(){
	if(Routing_tbl_SelectRow == null)
	{
		alert("입력할 공정을 클릭 한 후에 저장을 시도하여 주십시오.");
		return;
	}
	else
	{
		console.log(Material_Model_Mapping_tbl.getSelectedData());
		datalist = {};
		datalist.data = Material_Model_Mapping_tbl.getSelectedData();
		
		$.ajax({
			method: "GET",
			async: false,
			url: "matmodelmappingRest/matmodelmapping_Insert?data=" + encodeURI(JSON.stringify(datalist)),
			success: function(datas) {
				if(datas == "empty")
				{
					alert("입력 할 행을 선택 한 후에 눌러주십시오.");
					return;
				}
				else if(datas == "workOrder_ItemCode_empty")
				{
					alert("원자재 코드는 반드시 입력하여 주십시오.");
					return;
				}
				else if(datas == "ok")
					alert("저장되었습니다.");
				
				Routing_tbl_Select();
			}
		});
	}
});

function grid_itemPopup(cell)
{
	cells = cell;
	var cellElement = cell.getElement();
	
	cellElement.addEventListener('keydown', function(e) {
		if (e.keyCode == 13) {
			var cellValue = cell.getValue();
			itemPopup(cell.getValue(),'grid','','material');
		}
	});
}

function Bom_Save(){
	if(Routing_tbl.getSelectedData().length == 0)
	{
		alert("데이터를 입력 후에 시도하여 주십시오.");
		return;
	}
	
	console.log(Routing_tbl.getSelectedData());
	
	const arr = [];
	const arr2 = [];
	
	for(i=0;i<Routing_tbl.getSelectedData().length;i++)
	{
		if(Routing_tbl.getSelectedData()[i].routing_Order == ""){
			alert("공정순서는 반드시 입력하여 주십시오.");
			return;
		}
		
		if (isNaN(Routing_tbl.getSelectedData()[i].routing_Order)){
			alert("공정순서는 숫자를 입력하여 주십시오.");
			return;
		}
		
		arr.push(Routing_tbl.getSelectedData()[i].routing_Order);
		
		if(Routing_tbl.getSelectedData()[i].routing_FairCode == null){
			alert("공정코드는 반드시 입력하여 주십시오.");
			return;
		}
		
		if(Routing_tbl.getSelectedData()[i].routing_HowTest == null){
			alert("검사방법 반드시 입력하여 주십시오.");
			return;
		}
		
		arr2.push(Routing_tbl.getSelectedData()[i].routing_FairCode);
	}
	
	const set = new Set(arr);
	if(arr.length !== set.size) {
	  alert("중복된 공정순서가 존재합니다. 수정 후 다시 시도하여 주십시오.");
	  return;
	}
	
	const set2 = new Set(arr2);
	if(arr2.length !== set2.size) {
	  alert("중복된 공정코드가 존재합니다. 수정 후 다시 시도하여 주십시오.");
	  return;
	}
	
	list = {};
	list.data = Routing_tbl.getSelectedData();
				
	$.ajax({
		method: "GET",
		async: false,
		url: "routingInputRest/Routing_tbl_Insert?data=" + encodeURI(JSON.stringify(list)),
		success: function(datas) {
			console.log(datas);
		
			if(datas != "no")
			{
				alert("저장되었습니다.");
				Routing_tbl.clearData();
				PRODUCT_INFO_TBL_NEW_ROW();
			}
			else
				alert("중복된 공정코드나 공정순서가 존재합니다. 다시 입력하여 주십시오.");
		},
		error:function(request,status,error){
			alert("에러 발생");
		}
	});
}

$("#routingItemSearchBtn").click(function(){
	routingItemSearch($("#salesCode").val());
})

function routingItemSearch(itemCode){
	PRODUCT_INFO_TBL.setData("routingInputRest/routingItemSearch", {itemCode: itemCode})
}

function BBL_Search(value){
	$.ajax({
		method : "GET",
		dataType : "json",
		async : false,
		url : "BOMRest/BOMBOMList?BOM_ItemCode=" + value,
		success : function(data) {
			result =  data;
		}		
	});
	return result;
}

function BOM_Child(row,level) {
	//검색어를 저장할 배열
	search_arr = [];
	//클릭한 품목 검색배열에 저장
	search_arr.push(row.getData().product_ITEM_CODE);
	
	//트리형식으로 만들기 위해 데이터를 보관할 배열
	BOM_treeData = [];

	//클릭한 행을 저장한다.
	BOM_treeData.push({bom_level : level,
						bom_ItemCode : row.getData().product_ITEM_CODE,
						bom_ItemName : row.getData().product_ITEM_NAME,
						bom_Qty : 1,
						bom_State : row.getData().product_MTRL_CLSFC_NAME,
						bom_STND_1 : row.getData().product_INFO_STND_1})

	
	//레벨을 보관할 배열
	treeLevel = [];
	//레벨값을 저장한다.
	treeLevel.push(level);
	baseLevel = level
			
	//어미아이디를 보관할 배열
	tree_pid = [];
	
	//선택한 품목의 하위 품목 전부 검색
	while(search_arr.length>0){
		//검색어 추출하여 하위 품목검색
		shift = search_arr.shift();
		search = BBL_Search(shift);
		
		//다음 레벨을 지정할 값을 추출
		level = treeLevel.shift()+1;
		//어미 id값을 추출
		parent_id = tree_pid.shift();
		
		//검색한 결과값 하위 품목들을 저장함
		for(i=0;i<search.length;i++){
			//검색한 목록에 레벨값 추가
			search[i].bom_level = level
			//검색한 목록에 어미 아이디값 추가
			search[i].bom_Parent_ID = parent_id
			//출력할 배열 데이터 추가
			BOM_treeData.push(search[i])
			//자식이 있는것만 검색하도록함
			if(search[i].bom_ChildExist != 'N'){
				//검색어 배열에 추가
				search_arr.push(search[i].bom_ItemCode)
				//다음 레벨을 정해줄 레벨값 저장
				treeLevel.push(level)
				//어미 id값을 저장
				tree_pid.push(search[i].bom_ID)
			}	
		}
	}	
	
	// 어디에 붙여야 할지 매칭 시켜주기위한 map
	map = {};
	
	//검색한 하위 품목 리스트들을 트리구조로 변환
	for (i = 0; i < BOM_treeData.length; i++) {
		//각 행을 id값을 키로 하여 인덱스값을 저장
		map[BOM_treeData[i].bom_ID] = i;
		//각 행에 하위 배열을 저장할수 있도록 정의
		BOM_treeData[i]._children = [];
		
		//어미코드가 있을경우
		if (BOM_treeData[i].bom_Parent_ItemCode != null) {
			//어미코드가 담겨있는 값의 인덱스값
			BOM_treeData[map[BOM_treeData[i].bom_Parent_ID]]._children.push(BOM_treeData[i]);
		}
	}
	
	if(baseLevel == 1){
		if(BOMBOMListTable.getData()[0] != null){
			//초기 레벨이 1이면 부모품목코드를 추가하여 반환한다. (저장기능 떄문에)
			BOM_treeData[0].bom_Parent_ItemCode = BOMBOMListTable.getData()[0].bom_ItemCode;	
		}
	}
	return BOM_treeData[0];
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

function newRow_Add()
{
	max_Routing_Order = parseInt(max_Routing_Order) + 10;
	
	Routing_tbl.addRow({"routing_BsnsRcvng":"N","routing_Order":max_Routing_Order,"routing_ItemCode":PRODUCT_INFO_TBL.getSelectedData()[0].product_ITEM_CODE});
	/*.then(function(row){
		row.update({"routing_BsnsRcvng":"N"});
		row.update({"routing_Order":max_Routing_Order*10});
		row.update({"routing_ItemCode":PRODUCT_INFO_TBL.getSelectedData()[0].product_ITEM_CODE});
		row.getCell("routing_Order").edit();
	})
	.catch(function(error){
	});*/
}

function item_gridInit(PCode, PName, PSTND_1, PPrice, SaveV){
	WorkOrder_tbl_workOrder_ItemName_cell.getRow().update({
			"mat_Model_ItemBCode": PCode,
			"mat_Model_ItemBName": PName
	});
}