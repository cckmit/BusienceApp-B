var machineList = ["M001","M002","M003","M004","M005",
					"M006","M007","M008","M009","M010",
					"M101","M102","M103","M104","M105",
					"M106","M107","M108"]

function productStatus(machineCode){
	var ajaxResult = $.ajax({
        method : "get",
		data : {machineCode : machineCode},
        url : "/productionStatusRest/productionStatusSelect",
        success : function(result) {
			if(result.length>0){
	        	$("#"+machineCode).val(machineCode)
	        	$("#itemCode_"+machineCode).val(result[0].cl_ItemCode)
	        	$("#qty_"+machineCode).val(result[0].cl_Qty)
			}
        }
    })
	return ajaxResult;
}

window.onload = function(){
	for(let i=0; i<machineList.length;i++){
		productStatus(machineList[i])
		
		setInterval(function(){
			productStatus(machineList[i]);
		},10000);
	}
}