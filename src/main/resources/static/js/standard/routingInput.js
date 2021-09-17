var dtl_arr = new Object();

$.ajax({
   method : "GET",
   async: false,
   url : "dtl_tbl_select?NEW_TBL_CODE=32",
   success : function(datas) {
      for(i=0;i<datas.length;i++){
      	dtl_arr[datas[i].child_TBL_NO] = datas[i].child_TBL_TYPE;
      }
   }
});

var dtl_arr2 = new Object();

$.ajax({
   method : "GET",
   async: false,
   url : "dtl_tbl_select?NEW_TBL_CODE=33",
   success : function(datas) {
      for(i=0;i<datas.length;i++){
      	dtl_arr2[datas[i].child_TBL_NO] = datas[i].child_TBL_TYPE;
      }
   }
});

var product_ITEM_CODE = null;

var PRODUCT_INFO_TBL = new Tabulator("#PRODUCT_INFO_TBL", {
	layoutColumnsOnNewData : true,
	//페이징
	pagination: "local",
	paginationSize: 20,
	paginationAddRow: "table",
	height: "calc(100% - 175px)",
	headerFilterPlaceholder: null,
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
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
			
		
	},
	rowSelected:function(row){
    },
	//행추가시 기능
	rowAdded: function(row) {
	},
	cellEditing: function(cell) {
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
							
						if(datas.length > 0)
							max_Routing_Order = datas[datas.length-1].routing_Order;
						else
							max_Routing_Order = 0;
						
						max = 10;
							
						if(datas.length<10)
						{
							max = max - datas.length;
							
							for(i=0;i<max;i++)
								newRow_Add();	
						}
					}
				});
}

var BOMListTable = new Tabulator("#BOMListTable", {
	layoutColumnsOnNewData : true,
	index:"bom_ItemCode",
	dataTree:true,
    dataTreeStartExpanded:true,
	dataTreeChildColumnCalcs:true,
	height:"calc(60% - 175px)",
	
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

var Routing_tbl = new Tabulator("#Routing_tbl", {
	layout:"fitDataStretch",
	height:"calc(58.85% - 175px)",
	columns: [ //Define Table Columns
		{formatter:"rowSelection", titleFormatter:"rowSelection", headerHozAlign:"center", hozAlign:"center", headerSort:false, width:40},
		{ title: "제품코드", field: "routing_ItemCode" , visible:false },
		{ title: "공정순서v", field: "routing_Orderv", visible:false},
		{ title: "공정코드v", field: "routing_FairCodev", visible:false},
		{ title: "공정순서", field: "routing_Order",headerHozAlign: "center", editor:"input", hozAlign:"right"
			,cellEdited: function(cell)
			{
				Right_Move(cell,"right");
				cell.getRow().select();
			}
		},
		{ title: "공정코드", field: "routing_FairCode", headerHozAlign: "center", editor:"select"
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
		      },editorParams:{values:dtl_arr}
		},
		{ title: "검사방법", field: "routing_HowTest", headerHozAlign: "center",width:150, editor:"select"
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
		{ title: "영업입고", field: "routing_BsnsRcvng", headerHozAlign: "center",width:150, editor:"select", hozAlign:"center"
			,cellEdited: function(cell)
			{
				Right_Move(cell,"right");
				cell.getRow().select();
			},
			editorParams:{values:["N", "Y"]}
		},
		{ title: "비고", field: "routing_Remarks", headerHozAlign: "center", editor:"input"
			,cellEdited: function(cell)
			{
				Right_Move(cell,"bottom");
				cell.getRow().select();
			}
			,cellEditCancelled:function(cell){
		        Right_Move(cell,"bottom");
				cell.getRow().select();
		    }
		}
	],
});

$('#Product_SearchBtn').click(function(){
	Product_Search();
});

$('#Bom_Save').click(function(){
	Bom_Save();
});

$('#Bom_Delete').click(function(){
	Bom_Delete();
});

//제품팝업창		(입력값,input or grid, 탭기능이 있을떄 1부터 없으면 '',검색조건제약(all,material,sales,dtl_tbl 숫자코드))
function itemPopup2(input_value,type_value,tab_value,search_value) {
	//제품명 팝업
	localStorage.setItem('PRODUCT_ITEM_NAME', input_value);
	//창의 주소
	var url = "itemPopup?input_value="+input_value
								+"&type_value="+type_value
								+"&tab_value="+tab_value
								+"&search_value="+search_value;
	//창의 이름
	var name = "itemPopup";
	//창의 css
	var option = "width = 500, height = 500, top = 100, left = 200, location = no"
	openWin = window.open(url, name, option);
	
	var interval = window.setInterval(function() {
        try {
            if (openWin == null || openWin.closed) {
            	Product_Search();
            	
                clearInterval(interval);
            }
        }
        catch (e) {
        }
    }, 1000);
}

function Bom_Delete(){
	if(Routing_tbl.getSelectedData().length <= 0)
	{
		alert("데이터를 선택 후에 시도하여 주십시오.");
		return;
	}
	else
	{
		flag = true;
	
		for(i=0;i<Routing_tbl.getSelectedData().length;i++)
		{
			if(typeof Routing_tbl.getSelectedData()[i].routing_FairCodev != "undefined")
			{
				if(confirm("해당 데이터를 삭제하시겠습니까?"))
				{
					dataList = {};
					dataList.data = Routing_tbl.getSelectedData();
				
					$.ajax({
						method: "GET",
						async: false,
						url: "routingInputRest/Routing_tbl_delete?data=" + encodeURI(JSON.stringify(dataList)),
						success: function(datas) {
							if(datas=="foreign")
							{
								alert("원자재 모델 Mapping에 등록된 공정입니다.\r\nMapping을 먼저 해제 한 후에 시도하여 주십시오.");
								flag = false;
							}
						}
					});
					
					break;
				}
				else
					return;
			}
		}
	}
	
	if(flag)
	{
		rows = Routing_tbl.getSelectedRows();
			
		for(j=0;j<rows.length;j++)
			rows[j].delete();
	}
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

function Product_Search(){
	$.ajax({
		method: "GET",
		async: false,
		url: "routingInputRest/Product_Search?PRODUCT_ITEM_CODE=" + $("#salesCode").val(),
		success: function(datas) {
			console.log(datas);
			
			PRODUCT_INFO_TBL.setData(datas);
		}
	});
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
