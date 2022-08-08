var prod_dict = {};
var wrap_dict = {};

productStatus();

function productStatus() {
	var ajaxResult = $.ajax({
		method: "get",
		data: {},
		url: "maskProductionFacilityRest/getProductionInfoFromMachine",
		success: function(result) {
			//console.log(result);
			//console.log(prod_dict);
			var missing_prod_dict = {};
			result.forEach(res => {
				var prod_MachineNum = parseInt(res.prod_MachineCode.slice(-2)) - 1;			
				$("#prodRow_machineCode_M00" + prod_MachineNum).attr("style", "background-color:rgb(242,242,242);");
				if (prod_MachineNum in prod_dict) { //생산 키가 있을 때
					if (res.prod_Qty > prod_dict["" + prod_MachineNum]) { //현재 생산량이 10초전 생산량보다 많을 때 Row 초록색으로 업데이트
						$("#prodRow_machineCode_M00" + prod_MachineNum).attr("style", "background-color:rgb(195,230,203);");						
					}				
				}						
				$("#itemDesc_M00" + prod_MachineNum).html(res.prod_Desc)
				$("#itemStnd1_M00" + prod_MachineNum).html(res.prod_Measure1)
				$("#itemMaterial_M00" + prod_MachineNum).html(res.prod_Material)
				$("#itemType1_M00" + prod_MachineNum).html(res.prod_Type1)
				$("#itemType2_M00" + prod_MachineNum).html(res.prod_Type2)
				$("#itemProdQty_M00" + prod_MachineNum).html(res.prod_Qty)
				missing_prod_dict["" + prod_MachineNum] = res.prod_MachineCode;
				prod_dict["" + prod_MachineNum] = res.prod_Qty;				
			});
			for(let i = 0; i<=9; i++) {
				if(!("" + i in missing_prod_dict)) {
					$("#itemDesc_M00"+i).html("미사용")	
				}
			}
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
		url: "maskProductionFacilityRest/getWrappingInfoFromMachine",
		success: function(result) {
		console.log(result);
		//console.log(wrap_dict);
		var missing_wrap_dict = {};
			result.forEach(res => {
				$("#wrapRow_machineCode_M10" + res.right_Wrap_MachineCode).attr("style", "background-color:rgb(242,242,242);");				
				if ("M10" + res.right_Wrap_MachineCode in wrap_dict) { //생산 키가 있을 때
					if (res.wrap_Qty > wrap_dict["M10" + res.right_Wrap_MachineCode]) { //현재 생산량이 10초전 생산량보다 많을 때 Row 초록색으로 업데이트
						$("#wrapRow_machineCode_M10" + res.right_Wrap_MachineCode).attr("style", "background-color:rgb(195,230,203);");
					}
				}
				$("#itemWrapRemainQty_M10" + res.right_Wrap_MachineCode).html(res.wrap_RemainQty)
				$("#itemWrapQty_M10" + res.right_Wrap_MachineCode).html(res.wrap_Qty)
				$("#itemDesc_M10" + res.right_Wrap_MachineCode).html(res.wrap_Desc)
				$("#itemStnd1_M10" + res.right_Wrap_MachineCode).html(res.wrap_Measure1)
				$("#itemStnd2_M10" + res.right_Wrap_MachineCode).html(res.wrap_Measure2)
				$("#itemMaterial_M10" + res.right_Wrap_MachineCode).html(res.wrap_Material)
				$("#itemType1_M10" + res.right_Wrap_MachineCode).html(res.wrap_Type1)
				$("#itemType2_M10" + res.right_Wrap_MachineCode).html(res.wrap_Type2)
				missing_wrap_dict["" + res.right_Wrap_MachineCode] = res.right_Wrap_MachineCode;				
				wrap_dict["M10" + res.right_Wrap_MachineCode] = res.wrap_Qty;
				
			});
			for(let i = 1; i<=8; i++) {
				if(!("" + i in missing_wrap_dict)) {
					$("#itemDesc_M10"+i).html("미사용")									
				}
			}			
		}
	})
	return ajaxResult;
}

changedProductStatus();

function changedProductStatus() {
	var ajaxResult = $.ajax({
		method: "get",
		data: {},
		url: "maskProductionFacilityRest/getChangedProductionInfoFromMachine",
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
		url: "maskProductionFacilityRest/getChangedWrappingInfoFromMachine",
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