$('.printBtn').on('click', function() {
	window.print();
});

$(document).ready(function() {
	var firstLine = 26;
	var secondLine = 34;
	//orderlist 검색
	$.ajax({
		method: "GET",
		url: "matOrderRest/MOL_Search",
		data: {orderNo: $('#order_lCus_No').val()},
		success: function(MOL_data) {
			var total_price = 0;
			
			for (let i = 0; i < MOL_data.length; i++) {
				//코드, 명, 규격, 수량, 단가, 금액, 비고
				$("table").append("<tr class='list_MOL_data'><td>" + MOL_data[i].order_lNo
					+ "</td><td class='td_center'>" + MOL_data[i].order_lCode
					+ "</td><td class='td_center'>" + MOL_data[i].order_lName
					+ "</td><td class='td_center'>" + MOL_data[i].order_STND_1
					+ "</td><td class='td_center'>" + MOL_data[i].order_Product_Unit
					+ "</td><td class='td_right'>" + Number(MOL_data[i].order_lQty).toLocaleString('en')
					+ "</td><td class='td_right'>" + Number(MOL_data[i].order_lUnit_Price).toLocaleString('en')
					+ "</td><td class='td_center'>" + MOL_data[i].order_lInfo_Remark + "</td></tr>");
					
				if((i+1-firstLine) % secondLine == 0 && i+1 != MOL_data.length){
					$("table").append("<tr></tr>"
							+ "<th width='20'><span>No</span></th>"
							+ "<th width='80'><span>품번</span></th>"
							+ "<th width='200'><span>제품명</span></th>"
							+ "<th width='130'><span>규격</span></th>"
							+ "<th width='80'><span>단위</span></th>"
							+ "<th width='50'><span>수량</span></th>"
							+ "<th width='70'><span>단가</span></th>"
							+ "<th width='130'><span>비고</span></th></tr>");
				}
			
				//합계금액
				total_price += parseInt(MOL_data[i].order_lPrice);
			}
			
			var endLine = (MOL_data.length-firstLine)/secondLine
			if((MOL_data.length-firstLine)%secondLine != 0){
				endLine++
			}
			
			for (let j = MOL_data.length; j < endLine*secondLine+firstLine; j++) {
				$("table").append("<tr class='list_MOL_data'><td></td><td></td><td></td><td colspan='2'></td><td></td><td></td><td></td></tr>")
			}
			
			//합계금액 출력
			$('#total_price').text(Number(total_price).toLocaleString('en') + " 원");
		}
	});
})