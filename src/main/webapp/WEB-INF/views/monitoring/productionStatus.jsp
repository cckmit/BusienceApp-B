<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="main">
	<input id="Machine001" class="machineCode" value="M001"></input>
	<input class="itemCode"></input>
	<input class="qty"></input>
</div>

<script>
function productStatus(machineCode){
	var ajaxResult = $.ajax({
        method : "get",
		data : {machineCode : machineCode},
        url : "/productionStatusRest/productionStatusSelect",
        success : function(result) {
        	$(".itemCode").val(result[0].cl_ItemCode)
        	$(".qty").val(result[0].cl_Qty)
        }
    })
	return ajaxResult;
}

window.onload = function(){
	productStatus($("#Machine001").val())
	
	setInterval(function(){
		productStatus($("#machineCode").val());
	},10000);
}
</script>

<script src="js/monitoring/productionStatus.js?v=<%=System.currentTimeMillis() %>"></script>