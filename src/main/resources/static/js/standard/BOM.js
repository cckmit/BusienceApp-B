var BOMitemListTable = new Tabulator("#BOMitemListTable", {
	ajaxConfig : "get",
	ajaxContentType:"json",
	ajaxURL : "BOMRest/BOMitemList",
	rowClick:function(e, row){
		//선택된행 표시
		BOMitemListTable.deselectRow();
		row.select();
	},
	rowDblClick:function(e, row){
		//하위 품목이 상위품목에 포함되 있으므로 가장 상위 품목만 나타냄
		BOMBOMListTable.setData([BOM_Child(row.getData().product_ITEM_CODE,0)])
    },
	height:"calc(100% - 175px)",
	columns: [ //Define Table Columns
		{ title: "순번", field: "id", headerHozAlign: "center", hozAlign: "center"},
		{ title: "품목코드", field: "product_ITEM_CODE", headerHozAlign: "center" },
		{ title: "품목명", field: "product_ITEM_NAME", headerHozAlign: "center" },
		{ title: "분류1", field: "product_ITEM_CLSFC_1_NAME", headerHozAlign: "center"},
		{ title: "분류2", field: "product_ITEM_CLSFC_2_NAME", headerHozAlign: "center"},
		{ title: "품목종류", field: "product_MTRL_CLSFC_NAME", headerHozAlign: "center"},
		{ title: "규격1", field: "product_INFO_STND_1", visible:false},
		{ title: "단위", field: "product_UNIT_NAME", visible:false}
	],
});

//BIL_Search
function BIL_Search(){
	$.ajax({
		method : "GET",
		dataType : "json",
		async : false,
		url : "BOMRest/BOMitemList?PRODUCT_ITEM_CODE="+ $('.Item_Code').val()
								+"&Item_Type="+$('.Item_Type').val(),
		success : function(data) {
			BOMitemListTable.setData(data);
		}
	});
}

//레벨 1만 편집 가능하게 하는 확인 기능
var editCheck = function(cell){
    //cell - the cell component for the editable cell
    //get row data
    var data = cell.getRow().getData();
    return data.bom_level == 1;
}

var BOMBOMListTable = new Tabulator("#BOMBOMListTable", {
	layoutColumnsOnNewData : true,
	index:"bom_ItemCode",
	dataTree:true,
    dataTreeStartExpanded:true,
	dataTreeChildColumnCalcs:true,
	rowClick:function(e,row){
		//트리 레벨 1만 클릭 되게 한다. (삭제연계)
		level_0 = BOMBOMListTable.getData()[0];
		
		if(level_0.bom_ItemCode == row.getData().bom_Parent_ItemCode){
			row.toggleSelect();
		}
	},
	height:"calc(100% - 175px)",
	columns: [ //Define Table Columns
		{ title: "level", field: "bom_level",headerHozAlign: "center"},
		{ title: "부모품목코드", field: "bom_Parent_ItemCode", visible:false},
		{ title: "품목코드", field: "bom_ItemCode", headerHozAlign: "center" },
		{ title: "품목명", field: "bom_ItemName", headerHozAlign: "center" },
		{ title: "규격1", field: "bom_STND_1", headerHozAlign: "center"},
		{ title: "분류1", field: "bom_CLSFC_1_NAME", headerHozAlign: "center"},
		{ title: "분류2", field: "bom_CLSFC_2_NAME", headerHozAlign: "center"},
		{ title: "수량", field: "bom_Qty", headerHozAlign: "center", hozAlign: "right", editor:"input", editable:editCheck},
		{ title: "단위", field: "bom_Unit_Name", headerHozAlign: "center"},
		{ title: "품목종류", field: "bom_State", headerHozAlign: "center"},
		{ title: "작업자", field: "bom_Modifier", headerHozAlign: "center"},
		{ title: "작업날짜", field: "bom_Modify_Date", headerHozAlign: "center", formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}}
	],
});

function BBL_Add(){
	
	//선택된행
	selectedRow = BOMitemListTable.getRows("selected")

	if(selectedRow.length == 0){
		alert("선택된 행이 없습니다.")
		return false;
	}
	//클릭한행이 이미 레벨1에 포함 되있다면 추가되지않음, 똑같은것이 없어야 추가
	level_0 = BOMBOMListTable.getRow(BOMBOMListTable.getData()[0].bom_ItemCode);
	level_1 = level_0.getTreeChildren();

	nothing = true;
	
	//레벨0에 포함 되어 있는지 확인
	if(BOMBOMListTable.getData()[0].bom_ItemCode == selectedRow[0].getData().product_ITEM_CODE){
		nothing=false;
		alert("해당 품목은 상위품목과 같은 품목입니다.")
	}
	
	//레벨1에 포함 되어 있는지 확인
	for(i=0;i<level_1.length;i++){
		//추가된행 선택								
		if(level_1[i].getData().bom_ItemCode == selectedRow[0].getData().product_ITEM_CODE){
			nothing=false;
			BOMBOMListTable.deselectRow();
			BOMBOMListTable.selectRow(level_1[i]);
			alert("이미 추가된 품목 입니다.")
			break;
		}
	}
	if(nothing){
		//있을경우 하위 품목들을 검색한 후에 자식으로 추가
		level_0.addTreeChild(BOM_Child(selectedRow[0].getData().product_ITEM_CODE,1),false)
		
		//level_1이 추가 됬으므로 새롭게 정의
		level_1 = level_0.getTreeChildren();
		//추가된 행 선택
		BOMBOMListTable.selectRow(level_1[level_1.length-1]);
		//수량셀에 포커스이동
		level_1[level_1.length-1].getCell("bom_Qty").edit();
	}	
}

//BBL_Save
$("#BBL_AddBtn").click(function(){
	BBL_Add();
})

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

function BOM_Child(item,level) {
	//해당 제품의 정보 찾기
	$.ajax({
		method : "GET",
		dataType : "json",
		async : false,
		url : "BOMRest/BOMItemInform?BOM_ItemCode=" + item,
		success : function(data) {
			itemInform = data
			console.log(itemInform)
		}		
	});
	
	//검색어를 저장할 배열
	search_arr = [];
	//클릭한 품목 검색배열에 저장
	search_arr.push(itemInform[0].bom_ItemCode);
	
	//트리형식으로 만들기 위해 데이터를 보관할 배열
	BOM_treeData = [];

	//클릭한 행을 저장한다.
	BOM_treeData.push({bom_level : level,
						bom_ItemCode : itemInform[0].bom_ItemCode,
						bom_ItemName : itemInform[0].bom_ItemName,
						bom_Qty : 1,
						bom_State : itemInform[0].bom_State,
						bom_STND_1 : itemInform[0].bom_STND_1,
						bom_Unit_Name : itemInform[0].bom_Unit_Name})

	
	//레벨을 보관할 배열
	treeLevel = [];
	//레벨값을 저장한다.
	treeLevel.push(level);
	baseLevel = level
	
	//수량을 보관할 배열
	treeQty = [];
	//수량을 저장한다.
	treeQty.push(1);
			
	//어미아이디를 보관할 배열
	tree_pid = [];
	
	//선택한 품목의 하위 품목 전부 검색
	while(search_arr.length>0){
		//검색어 추출하여 하위 품목검색
		shift = search_arr.shift();
		search = BBL_Search(shift);
		
		//다음 레벨을 지정할 값을 추출
		level = treeLevel.shift()+1;
		//다음 레벨을 지정할 수량값을 추출
		Qty = treeQty.shift();
		//어미 id값을 추출
		parent_id = tree_pid.shift();
		
		//검색한 결과값 하위 품목들을 저장함
		for(i=0;i<search.length;i++){
			//검색한 목록에 레벨값 추가
			search[i].bom_level = level
			//검색한 목록에 수량값 추가
			search[i].bom_Qty = search[i].bom_Qty*Qty
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
				//다음 레벨을 정해줄 수량값 저장
				treeQty.push(search[i].bom_Qty)
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

function BBL_Save(){
	//저장할 데이터 레벨1 리스트
	if(BOMBOMListTable.getData()[0]._children.length==0){
		alert("저장할 level 1 데이터가 없습니다.");
		return false;
	}
	$.ajax({
		method : "post",
		async : false,
		url : "BOMRest/BBL_Save?data=" + encodeURI(JSON.stringify(BOMBOMListTable.getData()[0]._children)),
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success : function(data) {
			if(data == "success"){
				alert("저장되었습니다.");
				BOMBOMListTable.setData([BOM_Child(BOMBOMListTable.getData()[0].bom_ItemCode,0)])
			}
		}		
	});
}

//BBL_Save
$("#BBL_SaveBtn").click(function(){
	BBL_Save();
})

function BBL_Delete(){
	
	//삭제할 데이터 레벨1 리스트
	selectedData = BOMBOMListTable.getData("selected");
	
	if(selectedData.length == 0){
		alert("선택한 행이 없습니다.");
		return false;
	}
	
	if(confirm("선택한 행이 삭제됩니다. 삭제하시겠습니까?")){
		$.ajax({
			method : "post",
			url : "BOMRest/BBL_Delete?data=" + encodeURI(JSON.stringify(selectedData)),
			beforeSend: function (xhr) {
	           var header = $("meta[name='_csrf_header']").attr("content");
	           var token = $("meta[name='_csrf']").attr("content");
	           xhr.setRequestHeader(header, token);
			},
			success : function(data) {
				//기본 배열 임시 저장
				origin = BOMBOMListTable.getData()[0]._children
				
				while(selectedData.length>0){
					
					//선택한 데이터와 일치하는 데이터 배열에서 제거
					if(origin[0].bom_ItemCode == selectedData[0].bom_ItemCode){
						origin.shift();
						selectedData.shift();
					}else{
						origin.push(origin.shift());
					}
				}
				
				if(data == "success"){
					//최상위 행을 업데이트 하여 자식행 변경
					BOMBOMListTable.updateRow(BOMBOMListTable.getData()[0].bom_ItemCode,{_children:origin})
					//선택 되있던 or 되있는 행 선택취소
					BOMBOMListTable.deselectRow();	
				}
			}
		});
	}	
}

//BBL_Delete
$("#BBL_DeleteBtn").click(function(){
	BBL_Delete();
})