var prod_dict = {};
var wrap_dict = {};

productStatus();

function productStatus() {
	var ajaxResult = $.ajax({
		method: "get",
		data: {},
		url: "maskProductionRest/getProductionInfoFromMachine",
		success: function(result) {
			//console.log(result);
			//console.log(prod_dict);
			result.forEach(res => {
				if (res.prod_MachineCode in prod_dict) { //생산 키가 있을 때
					if (res.prod_Qty > prod_dict['' + res.prod_MachineCode]) { //현재 생산량이 10초전 생산량보다 많을 때 Row 초록색으로 업데이트
						$("#prodRow_machineCode_" + res.prod_MachineCode).attr("style", "background-color:rgb(195,230,203);");
						prod_dict['' + res.prod_MachineCode] = res.prod_Qty;
					}
					else { //현재 생산량이 10초전 생산량보다 적거나 같을 때 Row 빨간색으로 업데이트
						$("#prodRow_machineCode_" + res.prod_MachineCode).attr("style", "background-color:rgb(242,242,242);");
						prod_dict['' + res.prod_MachineCode] = res.prod_Qty;
						//console.log(prod_dict[''+res.prod_MachineCode]);
					}
				}
				else { //생산 키가 없을 때 딕셔너리에 할당만 해줌
					$("#prodRow_machineCode_" + res.prod_MachineCode).attr("style", "background-color:rgb(242,242,242);");
					prod_dict['' + res.prod_MachineCode] = res.prod_Qty;
				}

				$("#itemDesc_" + res.prod_MachineCode).html(res.prod_Desc)
				$("#itemStnd1_" + res.prod_MachineCode).html(res.prod_Measure1)
				$("#itemMaterial_" + res.prod_MachineCode).html(res.prod_Material)
				$("#itemType1_" + res.prod_MachineCode).html(res.prod_Type1)
				$("#itemType2_" + res.prod_MachineCode).html(res.prod_Type2)
				$("#itemProdQty_" + res.prod_MachineCode).html(res.prod_Qty)
			});
		}
	})
	return ajaxResult;
}

wrapStatus();

function wrapStatus() {
	//$("#" + machineCode).html(machineCode)
	var ajaxResult = $.ajax({
		method: "get",
		data: {},
		url: "maskProductionRest/getWrappingInfoFromMachine",
		success: function(result) {
		console.log(result);
		console.log(wrap_dict);
			result.forEach(res => {
							
				if ("M10" + res.right_Wrap_MachineCode in wrap_dict) { //생산 키가 있을 때
					if (res.wrap_Qty > wrap_dict["M10" + res.right_Wrap_MachineCode]) { //현재 생산량이 10초전 생산량보다 많을 때 Row 초록색으로 업데이트
						$("#wrapRow_machineCode_M10" + res.right_Wrap_MachineCode).attr("style", "background-color:rgb(195,230,203);");
						wrap_dict["M10" + res.right_Wrap_MachineCode] = res.wrap_Qty;
					}
					else { //현재 생산량이 10초전 생산량보다 적거나 같을 때 Row 빨간색으로 업데이트
						$("#wrapRow_machineCode_M10" + res.right_Wrap_MachineCode).attr("style", "background-color:rgb(242,242,242);");
						wrap_dict["M10" + res.right_Wrap_MachineCode] = res.wrap_Qty;
						//console.log(wrap_dict["M10" + res.right_Wrap_MachineCode]);
					}
				}
				else { //생산 키가 없을 때 딕셔너리에 할당만 해줌
					$("#wrapRow_machineCode_M10" + res.right_Wrap_MachineCode).attr("style", "background-color:rgb(242,242,242);");
					wrap_dict["M10" + res.right_Wrap_MachineCode] = res.wrap_Qty;
				}

				$("#itemWrapRemainQty_M10" + res.right_Wrap_MachineCode).html(res.wrap_RemainQty)
				$("#itemWrapQty_M10" + res.right_Wrap_MachineCode).html(res.wrap_Qty)
				$("#itemDesc_M10" + res.right_Wrap_MachineCode).html(res.wrap_Desc)
				$("#itemStnd1_M10" + res.right_Wrap_MachineCode).html(res.wrap_Measure1)
				$("#itemStnd2_M10" + res.right_Wrap_MachineCode).html(res.wrap_Measure2)
				$("#itemMaterial_M10" + res.right_Wrap_MachineCode).html(res.wrap_Material)
				$("#itemType1_M10" + res.right_Wrap_MachineCode).html(res.wrap_Type1)
				$("#itemType2_M10" + res.right_Wrap_MachineCode).html(res.wrap_Type2)
			});
		}
	})
	return ajaxResult;
}

changedProductStatus();

function changedProductStatus() {
	var ajaxResult = $.ajax({
		method: "get",
		data: {},
		url: "maskProductionRest/getChangedProductionInfoFromMachine",
		success: function(result) {
			var count = 1;
			result.forEach(res => {
				$("#changedProdItemMachineCode_" + count).html(res.right_Prod_MachineCode)
				$("#changedProdItemDesc_" + count).html(res.prod_Desc)
				$("#changedProdItemStnd1_" + count).html(res.prod_Measure1)
				$("#changedProdItemMaterial_" + count).html(res.prod_Material)
				$("#changedProdItemType1_" + count).html(res.prod_Type1)
				$("#changedProdItemType2_" + count).html(res.prod_Type2)
				$("#changedProdItemProdQty_" + count).html(res.prod_Qty)
				count++;
			});
		}
	})
	return ajaxResult;
}

changedWrapStatus();

function changedWrapStatus() {
	//$("#" + machineCode).html(machineCode)
	var ajaxResult = $.ajax({
		method: "get",
		data: {},
		url: "maskProductionRest/getChangedWrappingInfoFromMachine",
		success: function(result) {
			var count = 1;
			result.forEach(res => {
				$("#changedWrapItemWrapRemainQty_" + count).html(res.wrap_RemainQty)
				$("#changedWrapItemMachineCode_" + count).html(res.right_Wrap_MachineCode)
				$("#changedWrapItemWrapQty_" + count).html(res.wrap_Qty)
				$("#changedWrapItemDesc_" + count).html(res.wrap_Desc)
				$("#changedWrapItemStnd1_" + count).html(res.wrap_Measure1)
				$("#changedWrapItemStnd2_" + count).html(res.wrap_Measure2)
				$("#changedWrapItemMaterial_" + count).html(res.wrap_Material)
				$("#changedWrapItemType1_" + count).html(res.wrap_Type1)
				$("#changedWrapItemType2_" + count).html(res.wrap_Type2)
				count++;
			});
		}
	})
	return ajaxResult;
}

myTime();
setInterval(myTime, 1000);

function myTime() {
	const date = new Date();
	const options = {
		weekday: 'long',
		year: 'numeric',
		month: 'long',
		day: 'numeric'
	};
	document.getElementById("currentTime").innerHTML = date
		.toLocaleDateString(undefined, options)
		+ " " + date.toLocaleTimeString();
}

myTimer();
setInterval(myTimer, 1000);

function myTimer() {
	const date = new Date();
	const options = {
		second: '2-digit'
	};

	var timer_time = 60 - (parseInt(date.toLocaleTimeString(undefined, options)) % 60);
	document.getElementById("timer").innerHTML = String(timer_time) + "초후 업데이트";

	if (timer_time == 60) {
		productStatus();
		wrapStatus();
		changedProductStatus();
		changedWrapStatus();
	}
}

function myTimerReset() {
	productStatus();
	wrapStatus();
	changedProductStatus();
	changedWrapStatus();
}