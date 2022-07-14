//정전개 페이지
var BOMitemListTable1 = new Tabulator("#BOMitemListTable1", {
	ajaxConfig : "get",
	ajaxContentType:"json",
	ajaxURL : "BOMRest/BOMitemList2",
	ajaxParams : {itemCode : $('.Item_Code1').val(), condition : $('.Item_Type1').val()},
	rowClick:function(e, row){
		//테이블 초기화
		BOMExpListTable.clearData();
		//선택된행 표시
		BOMitemListTable1.deselectRow();
		row.select();
		
		//하위 품목이 상위품목에 포함되 있으므로 가장 상위 품목만 나타냄
		BOMExpListTable.setData([BOM_Child(row,0,'Exp')])
    },
	height:"calc(100% - 175px)",
	columns: [ //Define Table Columns
		{ title: "순번", field: "id", headerHozAlign: "center", hozAlign: "center"},
		{ title: "규격2", field: "product_INFO_STND_2", headerHozAlign: "center"},
		{ title: "규격명", field: "product_INFO_STND_2_NAME", headerHozAlign: "center"},		
		{ title: "품목코드", field: "product_ITEM_CODE", headerHozAlign: "center" },
		{ title: "품목명", field: "product_ITEM_NAME", headerHozAlign: "center"},
		{ title: "품목종류", field: "product_MTRL_CLSFC_NAME", headerHozAlign: "center"},
		{ title: "규격1", field: "product_INFO_STND_1", headerHozAlign: "center"},
		{ title: "분류1", field: "product_ITEM_CLSFC_1_NAME", headerHozAlign: "center"},
		{ title: "분류2", field: "product_ITEM_CLSFC_2_NAME", headerHozAlign: "center"},
		{ title: "단위", field: "product_UNIT_NAME", visible:false}
	],
});

//BIL_Search
function BIL_Search1(){
	$.ajax({
		method : "GET",
		dataType : "json",
		async : false,
		url : "BOMRest/BOMitemList2",
		data : {itemCode : $('.Item_Code1').val(), condition : $('.Item_Type1').val()},
		success : function(data) {
			BOMitemListTable1.setData(data);
		}
	});
}

var BOMExpListTable = new Tabulator("#BOMExpListTable", {
	layoutColumnsOnNewData : true,
	dataTree:true,
    dataTreeStartExpanded:true,
	dataTreeChildColumnCalcs:true,
	rowClick:function(e,row){
		row.toggleSelect();
	},
	height:"calc(100% - 175px)",
	columns: [ //Define Table Columns
		{ title: "level", field: "bom_level",headerHozAlign: "center"},
		{ title: "부모품목코드", field: "bom_Parent_ItemCode", visible:false},
		{ title: "품목코드", field: "bom_ItemCode", headerHozAlign: "center" },
		{ title: "품목명", field: "bom_ItemName", headerHozAlign: "center" },
		{ title: "규격1", field: "bom_STND_1", headerHozAlign: "center"},
		{ title: "규격2", field: "bom_STND_2", headerHozAlign: "center"},
		{ title: "분류1", field: "bom_CLSFC_1_NAME", headerHozAlign: "center"},
		{ title: "분류2", field: "bom_CLSFC_2_NAME", headerHozAlign: "center"},
		{ title: "수량", field: "bom_Qty", headerHozAlign: "center", hozAlign: "right"},
		{ title: "단위", field: "bom_Unit_Name", headerHozAlign: "center"},
		{ title: "품목종류", field: "bom_State", headerHozAlign: "center"},
		{ title: "작업자", field: "bom_Modifier", headerHozAlign: "center" },
		{ title: "작업날짜", field: "bom_Modify_Date", headerHozAlign: "center", formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}}
	],
});

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

function BOM_Child(row,level,type) {
	
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
						bom_STND_1 : row.getData().product_INFO_STND_1,
						bom_Unit_Name : row.getData().product_UNIT_NAME})

	//레벨을 보관할 배열
	treeLevel = [];
	//레벨값을 저장한다.
	treeLevel.push(level);
			
	//어미아이디를 보관할 배열
	tree_pid = [];

	//선택한 품목의 하위 품목 전부 검색
	while(search_arr.length>0){
		//검색어 추출하여 하위 품목검색
		shift = search_arr.shift();
		if(type == 'Exp'){
			search = BBL_Search(shift);	
		}else if(type == 'Imp'){
			search = BBL_Search2(shift);
		}
		
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
		//각 행을 코드값을 키로 하여 인덱스값을 저장
		map[BOM_treeData[i].bom_ID] = i;
		//각 행에 하위 배열을 저장할수 있도록 정의
		BOM_treeData[i]._children = [];
		
		//어미코드가 있을경우
		if (BOM_treeData[i].bom_Parent_ItemCode != null) {
			//어미코드가 담겨있는 값의 인덱스값
			BOM_treeData[map[BOM_treeData[i].bom_Parent_ID]]._children.push(BOM_treeData[i]);
		}
	}
	//첫행만 반환(모든데이터가 첫행안에 포함되어있음)
	return BOM_treeData[0];
}

//역전개 페이지
var BOMitemListTable2 = new Tabulator("#BOMitemListTable2", {
	ajaxConfig : "get",
	ajaxContentType:"json",
	ajaxURL : "BOMRest/BOMitemList2",
	ajaxParams : {itemCode : $('.Item_Code2').val(), condition : $('.Item_Type2').val()},
	rowClick:function(e, row){
		//테이블 초기화
		BOMImpListTable.clearData();
		//선택된행 표시
		BOMitemListTable2.deselectRow();
		row.select();

		//하위 품목이 상위품목에 포함되 있으므로 가장 상위 품목만 나타냄
		BOMImpListTable.setData([BOM_Child(row,0,'Imp')]);
    },
	height:"calc(100% - 175px)",
	columns: [ //Define Table Columns
		{ title: "순번", field: "id", headerHozAlign: "center", hozAlign: "center"},
		{ title: "규격2", field: "product_INFO_STND_2", headerHozAlign: "center"},
		{ title: "규격명", field: "product_INFO_STND_2_NAME", headerHozAlign: "center"},		
		{ title: "품목코드", field: "product_ITEM_CODE", headerHozAlign: "center" },
		{ title: "품목명", field: "product_ITEM_NAME", headerHozAlign: "center"},
		{ title: "품목종류", field: "product_MTRL_CLSFC_NAME", headerHozAlign: "center"},
		{ title: "규격1", field: "product_INFO_STND_1", headerHozAlign: "center"},
		{ title: "분류1", field: "product_ITEM_CLSFC_1_NAME", headerHozAlign: "center"},
		{ title: "분류2", field: "product_ITEM_CLSFC_2_NAME", headerHozAlign: "center"},
		{ title: "단위", field: "product_UNIT_NAME", visible:false}
	],
});

//BIL_Search
function BIL_Search2(){
	$.ajax({
		method : "GET",
		dataType : "json",
		async : false,
		url : "BOMRest/BOMitemList2",
		data : {itemCode : $('.Item_Code2').val(), condition : $('.Item_Type2').val()},
		success : function(data) {
			BOMitemListTable2.setData(data);
		}
	});
}

var BOMImpListTable = new Tabulator("#BOMImpListTable", {
	layoutColumnsOnNewData : true,
	dataTree:true,
    dataTreeStartExpanded:true,
	dataTreeChildColumnCalcs:true,
	rowClick:function(e,row){
		row.toggleSelect();
	},
	height:"calc(100% - 175px)",
	columns: [ //Define Table Columns
		{ title: "level", field: "bom_level",headerHozAlign: "center"},
		{ title: "부모품목코드", field: "bom_Parent_ItemCode", visible:false},
		{ title: "품목코드", field: "bom_ItemCode", headerHozAlign: "center" },
		{ title: "품목명", field: "bom_ItemName", headerHozAlign: "center" },
		{ title: "품목종류", field: "bom_State", headerHozAlign: "center"},
		{ title: "규격1", field: "bom_STND_1", headerHozAlign: "center"},
		{ title: "규격2", field: "bom_STND_2", headerHozAlign: "center"},
		{ title: "분류1", field: "bom_CLSFC_1_NAME", headerHozAlign: "center"},
		{ title: "분류2", field: "bom_CLSFC_2_NAME", headerHozAlign: "center"},
		{ title: "작업자", field: "bom_Modifier", headerHozAlign: "center" },
		{ title: "작업날짜", field: "bom_Modify_Date", headerHozAlign: "center", formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}}
	],
});

function BBL_Search2(value){
	$.ajax({
		method : "GET",
		dataType : "json",
		async : false,
		url : "BOMRest/BOMImpList?BOM_ItemCode=" + value,
		success : function(data) {
			console.log(data)
			result =  data;
		}
	});
	return result;
}