var selectedData;
document.addEventListener('DOMContentLoaded', function() {

	$('#reMonthSearchBtn').addClass('hide');



	var calendarEl = document.getElementById('calendar');
	var $lastRow = $("<tbody></tbody>");
	$addRow = $("<tr><td class='fc-day fc-widget-content fc-sat fc-past data-date='dd''></td></tr>");
	$('.fc-bg').append($addRow);

	registerModalShow();

	function registerModalShow() {

		$("#monthRegisterModal").modal("show").on("shown.bs.modal", function() {
		});

		$("#monthSearchBtn").click(function() {
			monthSearch();
			$("#monthRegisterModal").modal("hide");
		})

	};

	function monthSearch() {

		selectedData = $('#selectedMonth').val();
		console.log(selectedData);


		data = {
			startDate: selectedData
		}
		console.log(data);

		return new Promise((resolve, reject) => {
			setTimeout(() => {
				var calendar = new FullCalendar.Calendar(calendarEl, {
					plugins: ['interaction', 'dayGrid'],
					header: {
						left: "",
						center: "title",
						right: ""
					},
					defaultDate: new Date(selectedData),
					editable: false,
					eventLimit: false, // allow "more" link when too many events
					eventResourceEditable: true,
					events: function(info, successCallback, failureCallback, datas) {

						// 총생산량 구하기
						$.ajax({
							contentType: "application/json",
							dataType: "json",
							url: "proComparedInputRest/CW_ListView?data=" + encodeURI(JSON.stringify(data)),
							type: "get",
							async: false,
							success: function(data) {
								console.log(data);
								var events = [];
								var completeTime1;
								var titleName;
								var counData = 0;
								var num = 0;
								var outMat_data = date_calculation(selectedData);
								console.log("outMat_data = " + outMat_data);
								var outMat_dataCount = count_calculation(selectedData);

								if ($('#reMonthSearchBtn').hasClass('hide')) {
									$('#reMonthSearchBtn').removeClass('hide');
								}

								$("#reMonthSearchBtn").click(function() {

									$("#monthRegisterModal").modal("show");
								})

								$.each(data, function(index, element) {

									var totalQty = element.total_RQty;
									console.log(totalQty);
									var completeTime = element.workOrder_CompleteTime;
									console.log(completeTime);
									var total = moment(completeTime).format('MM/DD');
									var outmatDate = outMat_data.outMat_Date;

									//debugger;

									titleName = '총 생산량 (' + totalQty + ')';

									if (titleName != undefined) {
										events.push({
											title: titleName,
											color: "#008000",
											start: completeTime
										})
									}
								})

								for (var i = data.length - 1; i >= 0; i--) {


									if (i >= 0) {
										completeTime1 = data[i].workOrder_CompleteTime;
										// 자재출고
										for (var j = counData; j < outMat_data.length; j++) {
											var outmattitleName = '';
											// 출고일 표시
											var outmatDate = moment(outMat_data[j].outMat_Date).format('MM/DD');
											// 제품명 표시 
											var outmatName = outMat_data[j].outMat_Name.substr(0, 1);
											// 출고처
											var consignee = outMat_data[j].outMat_Consignee_Name.substr(0, 1);
											// 출고일당 카운트 갯수
											console.log("I = " + i + ", counData =" + counData + ", j = " + j + ", outmatDate = " + outmatDate + ", outMat_dataCount = " + outMat_dataCount[num].outMat_Count);
											for (var k = 1; k <= outMat_dataCount[num].outMat_Count; k++) {

												outmattitleName = outmatDate + " : " + outmatName + " - " + consignee + "(" + outMat_data[j].outMat_Qty + ")";

												events.push({
													title: outmattitleName,
													start: completeTime1
												})

												j++;

											}


											counData = j;
											num++;

											break;

											--i;

										};

									}

								}

								successCallback(events);
							},
							error: function() {
								alert("저장 중 에러가 발생했습니다. 다시 시도해 주세요.");
							}
						})
					}

				});

				calendar.render();

				return selectedData;
			}, 1000);
		});

	}

});

// 자재 출고 데이터
function date_calculation(month) {

	var monthData = month;
	var result;
	data = {
		startDate: monthData
	}

	//row에 따른 포문을 돈다.
	$.ajax({
		contentType: "application/json",
		dataType: "json",
		url: "proComparedInputRest/CWOM_ListView?data=" + encodeURI(JSON.stringify(data)),
		type: "get",
		async: false,
		success: function(datas) {


			console.log(datas);
			$.each(datas, function(indexa, element2) {
				var outMat_Qty = element2.outMat_Qty;
				//console.log("outMat_Qty = " + outMat_Qty);
			});
			console.log(datas);

			result = datas;
		}
	});

	return result;
}

// 자재 출고일 당 갯수 데이터
function count_calculation(selectedData) {

	console.log(selectedData);
	//monthSearch();
	//console.log(monthSetData);

	var countData;
	data = {
		startDate: selectedData
	}

	//row에 따른 포문을 돈다.
	$.ajax({
		contentType: "application/json",
		dataType: "json",
		url: "proComparedInputRest/CW_Count?data=" + encodeURI(JSON.stringify(data)),
		type: "get",
		async: false,
		success: function(datas) {

			console.log(datas);
			$.each(datas, function(indexa, element2) {
				var outMat_Qty = element2.outMat_Qty;
				//console.log("outMat_Qty = " + outMat_Qty);
			});
			console.log(datas);

			countData = datas;
		}
	});

	return countData;
}

Promise.all([
	
])


