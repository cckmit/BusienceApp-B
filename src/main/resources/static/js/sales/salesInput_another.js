/* 입고를 직접 입력하는 경우의 입고 페이지 */

var salesInputTable = new Tabulator("#salesInputTable", {
	//페이징
	pagination:"local",
	paginationSize:20,
    height:"calc(100% - 175px)",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	//order_lNo를 인덱스로 설정
	index:"sales_InMat_No",
	//복사하여 엑셀 붙여넣기 가능
	clipboard: true,
	/*ajaxConfig : "get",
	ajaxContentType:"json",
	ajaxURL : "salesInputRest/SI_Search",*/	
 	columns:[
		{formatter:"rowSelection", titleFormatter:"rowSelection", headerHozAlign:"center", hozAlign:"center", headerSort:false, width:40},
 		{title:"순번", field:"sales_InMat_No", headerHozAlign:"center", hozAlign:"right", width : 65},
 		{title:"LotNo", field:"sales_InMat_Lot_No", headerHozAlign:"center", width : 130},
		{title:"제품코드", field:"sales_InMat_Code", headerHozAlign:"center"},
 		{title:"제품명", field:"sales_InMat_Name", headerHozAlign:"center", width : 130},
 		{title:"수량", field:"sales_InMat_Qty", headerHozAlign:"center", hozAlign:"right", width : 85},
		{title:"입고일자", field:"sales_InMat_Date", headerHozAlign:"center", hozAlign:"right", width : 130,
			formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"}},
 		{title:"구분", field:"sales_InMat_Rcv_Clsfc", visible: false},
		{title:"구분", field:"sales_InMat_Rcv_Clsfc_Name", width : 80},
 	]
});

//fgoodsName
$('#fgoodsName').keydown(function(e){
	//엔터키를 눌렀을떄
	if(e.keyCode == 13){
		//제품팝업창을 띄우고
		product_check($('#fgoodsName').val());
		//다음 input으로 포커스
		$('#fgoodsQty').focus();				
	}
})

//fgoodsQty
$('#fgoodsQty').keydown(function(e){
	//엔터키를 눌렀을때
	if(e.keyCode == 13){
		//제품코드가 선택이 되었고 수량이 입력되어있으면 
		if($('#fgoodsCode').val().length >0 && $('#fgoodsQty').val() >0){
			//입력한 데이터를 기반으로 행 추가
			
			//fgoodsbundle 값이 존재하는경우
			//for(j=0;j<$('#fgoodsbundle').val();j++){} 으로 행추가 여러번 실행
			salesInputTable.addRow(
				{"sales_InMat_No": salesInputTable.getDataCount('active')+1,	
				"sales_InMat_Code": $('#fgoodsCode').val(),
				"sales_InMat_Name": $('#fgoodsName').val(),
				"sales_InMat_Qty": $('#fgoodsQty').val(),
				"sales_InMat_Date": moment(new Date()).format('YYYY-MM-DD HH:mm:ss'),
				"sales_InMat_Rcv_Clsfc": $('#fgoodsRcv_Clsfc').val(),
				"sales_InMat_Rcv_Clsfc_Name": $('#fgoodsRcv_Clsfc option:selected').text()}
			);
			//그 후 수량이 빈칸이 되고 제품명input으로 포커스
			$('#fgoodsQty').val("");
			$('#fgoodsRcv_Clsfc').val("171").prop("selected", true)
			$('#fgoodsName').focus();
		}					
	}
})

function product_check(value){
	//쿼리실행
	$.ajax({
		method : "GET",
		async: false,
		url : "product_check?PRODUCT_ITEM_CODE=" + value,
		dataType : "json",
		success : function(pc_data) {
			console.log(pc_data);			
			if(pc_data.length == 1){
				//검색어와 일치하는값이 있는경우
				$('#fgoodsCode').val(pc_data[0].product_ITEM_CODE);
				$('#fgoodsName').val(pc_data[0].product_ITEM_NAME);
			}else{
				//검색어와 일치하는값이 없는경우, 팝업창
				itemFinishedPopup(value,'input','');
			}
		}
	})
}

//add버튼
$('#SI_NewBtn').click(function(){
	salesInputTable.clearData();
	SI_buttonUse();
	trans_input_use()
	$('#fgoodsName').focus();
})

var ajaxResult = null;
//save버튼
$('#SI_SaveBtn').click(function(){
	//저장하는기능
	SI_Save();
})

//저장하는기능
function SI_Save(){
	rowCount = salesInputTable.getDataCount("active");
	for(i=0;i<rowCount;i++){
		//쿼리실행
		$.ajax({
			method : "post",
			async: false,
			url : "salesInputRest/SI_Save?data="+encodeURI(JSON.stringify(salesInputTable.getData()[i])),
			success : function(save_data) {
				console.log(save_data);
				ajaxResult = save_data;
			}
		})
	}
	if(ajaxResult == "error"){
		alert("중복된 값이 있습니다..");
	}else if(ajaxResult == "success"){
		alert("저장되었습니다.");
		SI_buttonReset();
		trans_input_unuse();
		salesInputTable.replaceData()
	}
}


//랏번호 입력시 (바코드 리드) 작동하는 쿼리예시
//LotNo Input에 Lot번호가 입력되면 자동으로 LotMaster에있는 값을 찾아서 그 데이터를 기반으로 행을 추가한다. 
//sgoodsLotNo

$('#sgoodsLotNo').keydown(function(e){
	//엔터키를 눌렀을떄
	if(e.keyCode == 13){
		salesInputTable.addRow(
					{"sales_InMat_No": salesInputTable.getDataCount('active')+1,	
					"sales_InMat_Lot_No": $(this).val(),
					"sales_InMat_Code": '',
					"sales_InMat_Name": '테스트',
					"sales_InMat_Qty": 10,
					"sales_InMat_Date": moment(new Date()).format('YYYY-MM-DD HH:mm:ss'),
					"sales_InMat_Rcv_Clsfc": '171'}
				);
		$(this).val('')
		/*
		//제품팝업창을 띄우고
		product_check($('#fgoodsName').val());
		//쿼리실행
		$.ajax({
			method : "GET",
			url : "salesInputRest/sgoodsLotNo_imsi?Lot_No=" + $('#sgoodsLotNo').val(),
			dataType : "json",
			success : function(data) {
				console.log(data);
				//검색된 데이터를 기반으로 행 추가	
				salesInputTable.addRow(
					{"sales_InMat_No": salesInputTable.getDataCount('active')+1,	
					"sales_InMat_Lot_No": data[0].sales_InMat_Lot_No,
					"sales_InMat_Code": data[0].sales_InMat_Code,
					"sales_InMat_Name": data[0].sales_InMat_Name,
					"sales_InMat_Qty": data[0].sales_InMat_Qty,
					"sales_InMat_Date": moment(new Date()).format('YYYY-MM-DD HH:mm:ss'),
					"sales_InMat_Rcv_Clsfc": '171'}
				);

			}
		})	*/			
	}
})


//delete버튼
$('#SI_DeleteBtn').click(function(){
	rowCount = salesInputTable.getDataCount("active");
	selectedData = salesInputTable.getSelectedData();
	//여러행 삭제할경우 위에서부터 하나씩 반복하여 삭제함
	if(selectedData.length < 1){
		alert("행을 선택하세요.")
	}else{
		//삭제
		salesInputTable.deleteRow(salesInputTable.getSelectedRows());
		//행번호를 업데이트 한다
		for(i=0;i<rowCount;i++){
			console.log(i);
			salesInputTable.updateRow(salesInputTable.getData()[i].sales_InMat_No,{"sales_InMat_No": i+1});			
		}
	}
})

function SI_buttonUse(){
	//SI_DeleteBtn 활성화
	if($('#SI_DeleteBtn').hasClass('unusebtn')){
		$('#SI_DeleteBtn').removeClass('unusebtn');	
	}
	//SI_SaveBtn 활성화
	if($('#SI_SaveBtn').hasClass('unusebtn')){
		$('#SI_SaveBtn').removeClass('unusebtn');	
	}
}

function SI_buttonReset(){
	//SI_DeleteBtn 비활성화
	if(!$('#SI_DeleteBtn').hasClass('unusebtn')){
		$('#SI_DeleteBtn').addClass('unusebtn');	
	}
	//SI_SaveBtn 비활성화
	if(!$('#SI_SaveBtn').hasClass('unusebtn')){
		$('#SI_SaveBtn').addClass('unusebtn');	
	}
}

function trans_input_use(){
	$('.trans_input').attr('disabled',false)
}

function trans_input_unuse(){
	$('.trans_input').attr('disabled',true)
}