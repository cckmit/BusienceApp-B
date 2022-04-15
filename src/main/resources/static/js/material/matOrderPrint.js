$('.printBtn').on('click', function() {
	window.print();
});


$(document).ready(function() {

	datas = {
		OrderNo: $('#order_lCus_No').val()
	}

	//orderlist 검색
	$.ajax({
		method: "GET",
		MOL_dataType: "json",
		url: "matOrderRest/MOL_Search",
		data: datas,
		success: function(MOL_data) {
			console.log(MOL_data);
			var total_price = 0;
			if (MOL_data.length < 20) {
				for (i = 0; i < MOL_data.length; i++) {
					//코드, 명, 규격, 수량, 단가, 금액, 비고
					$("table").append("<tr class='list_MOL_data'><td>" + MOL_data[i].order_lNo
						+ "</td><td class='td_center'>" + MOL_data[i].order_lCode
						+ "</td><td class='td_center'>" + MOL_data[i].order_lName
						+ "</td><td class='td_center'>" + MOL_data[i].order_STND_1
						+ "</td><td class='td_center'>" + MOL_data[i].order_Product_Unit
						+ "</td><td class='td_right'>" + Number(MOL_data[i].order_lQty).toLocaleString('en')
						+ "</td><td class='td_right'>" + Number(MOL_data[i].order_lUnit_Price).toLocaleString('en')
						+ "</td><td class='td_center'>" + MOL_data[i].order_lInfo_Remark + "</td></tr>");
					//합계금액
					total_price += parseInt(MOL_data[i].order_lPrice);
				}

				for (i = MOL_data.length; i < 20; i++) {
					$("table").append("<tr class='list_MOL_data'><td></td><td></td><td></td><td colspan='2'></td><td></td><td></td><td></td></tr>")
				}

			} else {
				for (i = 0; i < 20; i++) {
					//코드, 명, 규격, 수량, 단가, 금액, 비고
					$("table").append("<tr class='list_MOL_data' style='border-right: 2px solid #444;'><td>" + MOL_data[i].order_lNo
						+ "</td><td class='td_center'>" + MOL_data[i].order_lCode
						+ "</td><td class='td_center'>" + MOL_data[i].order_lName
						+ "</td><td class='td_center'>" + MOL_data[i].order_STND_1
						+ "</td><td class='td_center'>" + MOL_data[i].order_Product_Unit
						+ "</td><td class='td_right'>" + Number(MOL_data[i].order_lQty).toLocaleString('en')
						+ "</td><td class='td_right'>" + Number(MOL_data[i].order_lUnit_Price).toLocaleString('en')
						+ "</td><td class='td_center'>" + MOL_data[i].order_lInfo_Remark + "</td></tr>");
					//합계금액
					total_price += parseInt(MOL_data[i].order_lPrice);
				}
				for (j = 0; j < parseInt(MOL_data.length / 20); j++) {
					$("table").append("<tr style='page-break-before:always; border-top: 2px solid #444;'></tr>"
						+ "<th width='20'><span>No</span></th>"
						+ "<th width='80'><span>품번</span></th>"
						+ "<th width='200'><span>제품명</span></th>"
						+ "<th width='130'><span>규격</span></th>"
						+ "<th width='80'><span>단위</span></th>"
						+ "<th width='50'><span>수량</span></th>"
						+ "<th width='70'><span>단가</span></th>"
						+ "<th width='130'><span>비고</span></th></tr>");

					for (i = parseInt(MOL_data.length / 20) * 20; i < MOL_data.length; i++) {
						$("table").append("<tr class='list_MOL_data' style='border-right: 2px solid #444;'><td>" + MOL_data[i].order_lNo
							+ "</td><td class='td_center'>" + MOL_data[i].order_lCode
							+ "</td><td class='tdcenter'>" + MOL_data[i].order_lName
							+ "</td><td class='td_center'>" + MOL_data[i].order_STND_1
							+ "</td><td class='td_center'>" + MOL_data[i].order_Product_Unit
							+ "</td><td class='td_right'>" + Number(MOL_data[i].order_lQty).toLocaleString('en')
							+ "</td><td class='td_right'>" + Number(MOL_data[i].order_lUnit_Price).toLocaleString('en')
							+ "</td><td class='td_center'>" + MOL_data[i].order_lInfo_Remark + "</td></tr>");
						//합계금액
						total_price += parseInt(MOL_data[i].order_lPrice);
					}

				}
			}
			console.log(total_price);
			console.log(Number(total_price).toLocaleString('en'));
			//합계금액 출력
			$('#total_price').text(Number(total_price).toLocaleString('en') + " 원");
		}
	});
})
