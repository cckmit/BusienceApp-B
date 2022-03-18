<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="soloView" style="background-color: white;">
	<!-- MAIN -->
	<div class="main">
		<div id="testCheckTable" style="width: 500px; float: left"></div>
		<div>
			<nav class="navbar-busience">
				<a class="navbar-logo" href="/main"><img src="/images/logo/headerLogo.png"></a>
			</nav>
			<br/>
			<button type="button" id="reset_table">새로고침</button>
		</div>
	</div>
	<!-- END MAIN -->
	<script type="text/javascript">
		var testCheckTable = new Tabulator("#testCheckTable", {
			height:"100%",
			layoutColumnsOnNewData : true,
			ajaxConfig : "get",
			ajaxContentType:"json",
			ajaxURL : "tablet/testCheck",
		 	columns:[
		 		{title:"설비코드", field:"iot_EquipCode", headerHozAlign:"center"},
				{title:"값", field:"iot_Value", headerHozAlign:"center"},
		 		{title:"삽입날짜", field:"iot_Datetime", headerHozAlign:"center", hozAlign:"right"}
		 	]
		});
		$("#reset_table").click(function(){
			testCheckTable.getData();
			testCheckTable.replaceData();
		})
	</script>
</div>

