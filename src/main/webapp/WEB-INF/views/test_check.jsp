<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="soloView" style="background-color: white;">
	<!-- MAIN -->
	<div class="main">
		<div id="testCheckTable"></div>
	</div>
	<!-- END MAIN -->
	<script type="text/javascript">
		var testCheckTable = new Tabulator("#testCheckTable", {
			height:"calc(100% - 175px)",
			layoutColumnsOnNewData : true,
			ajaxConfig : "get",
			ajaxContentType:"json",
			ajaxURL : "testCheck",
		 	columns:[ 
		 		{title:"순번", field:"iid", headerHozAlign:"center", hozAlign:"right"},
		 		{title:"설비코드", field:"iequip", headerHozAlign:"center",},
				{title:"값", field:"ivalue", headerHozAlign:"center"},
		 		{title:"삽입날짜", field:"idatetime", headerHozAlign:"center", hozAlign:"right"}
		 	]
		});
	</script>
</div>

