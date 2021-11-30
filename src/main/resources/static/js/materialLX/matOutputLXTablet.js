		var WorkOrder_tbl = new Tabulator("#WorkOrder_tbl", {
			height:"75%",
			placeholder: "No Data Set",
			resizableColumns: false,
			rowClick: function(e, row) {
			},
			columns: [
				{title:"순번", field:"id", headerHozAlign: "center",  hozAlign: "center", width:70},
				{title:"출고일자", field:"outMat_Date", headerHozAlign:"center", hozAlign:"left", width:150, formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"},hozAlign:"right",width:180},
			 	{title:"출고구분", field:"outMat_Send_Clsfc_Name", headerHozAlign:"center", hozAlign:"left", width:100},
			 	{title:"부서명", field:"outMat_Dept_Name", headerHozAlign:"center", hozAlign:"left", width:100,visible:false},
				{title:"수취인", field:"outMat_Consignee_Name", headerHozAlign:"center", hozAlign:"left", width:100,visible:false},
			 	{title:"품목코드", field:"outMat_Code", headerHozAlign:"center", hozAlign:"left", width:100},
			 	{title:"품명", field:"outMat_Name", headerHozAlign:"center",hozAlign:"left", width:170},
			 	{title:"규격", field:"outMat_STND_1", headerHozAlign:"center",hozAlign:"left", width:120},
			 	{title:"단위", field:"outMat_UNIT", headerHozAlign:"center", hozAlign:"left", width:100},
			 	{title:"수량", field:"outMat_Qty", headerHozAlign:"center", hozAlign:"right", width:100},
			 	{title:"등록자", field:"outMat_Modifier", headerHozAlign:"center", hozAlign:"left", width:100},
			 	{title:"등록일자", field:"outMat_dInsert_Time", headerHozAlign:"center", hozAlign:"left", width:140, formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"},hozAlign:"right",width:180}
			],
		});

		//검색
		function search(){
			data = {
				startDate : $("#matOutputList_startDate").val(),
				endDate : $("#matOutputList_endDate").val(),
				outMat_Code : "",
				outMat_Send_Clsfc_Name : "all",
				outMat_Dept_Name : "all"
			}

			$.ajax({
				method : "GET",
				dataType : "json",
				url : "../matOutputReportLXRest/tablet/MO_ListView?data="+ encodeURI(JSON.stringify(data)),
				success : function(data) {
					console.log(data);
					WorkOrder_tbl.setData(data);
				}
			});
		}

		document.getElementById("pdselect").onclick = function(){
			pdselectFun();
		}

		function pdselectFun()
		{
			var selectList = document.getElementById("pdselect");
			document.getElementById("pdselect2").innerHTML = selectList.options[selectList.selectedIndex].innerHTML;
			document.getElementById("pdselect2").setAttribute("code",selectList.options[selectList.selectedIndex].value);
			qtyselectFun();
		}

		document.getElementById("dtselect").onclick = function(){
			dtselectFun();
		}

		function dtselectFun()
		{
			var selectList = document.getElementById("dtselect");
			document.getElementById("dtselect2").innerHTML = selectList.options[selectList.selectedIndex].innerHTML;
			document.getElementById("dtselect2").setAttribute("code",selectList.options[selectList.selectedIndex].value);
			
		}

		function qtyselectFun()
		{
			data = {
				code : document.getElementById("pdselect2").getAttribute("code")
			};

			$.get("../matOutputLXTabletRest/Current_Save",data,function(idata){
				document.getElementById("current_qty").innerHTML = parseInt(idata);
			});
		}

		function reload(){
			$("#pdselect option:eq(0)").prop("selected",true);
			$("#dtselect option:eq(0)").prop("selected",true);
			pdselectFun();
			dtselectFun();
			search();

			document.getElementById("d_len").value = "1";
			document.getElementById("d_len2").innerHTML = "1";
		}

		window.onload = function(){
			var today = new Date();
			var year = today.getFullYear();
			var month = ('0' + (today.getMonth() + 1)).slice(-2);
			var day = ('0' + today.getDate()).slice(-2);
			document.getElementById("today").innerHTML = year + '-' + month  + '-' + day;
		}

		setTimeout(function() {
			reload();
		}, 1000);

		document.getElementById("d_len").onchange = function(){
			document.getElementById("d_len2").innerHTML = document.getElementById("d_len").value;
		}

		document.getElementById("d_len").onclick = function(){
			CommandRun.run("/Users/Oliver/Desktop/keyboardViewer", []);
			CommandRun.run("C:\\WINDOWS\\system32\\osk.exe", []);

			//alert("OK");
		}

		document.getElementById("okbtn").onclick = function(){
			data = {
				pdcode : document.getElementById("pdselect2").getAttribute("code"),
				dtcode : document.getElementById("dtselect2").getAttribute("code"),
				qty : document.getElementById("d_len2").innerHTML
			};

			if(data.qty == 0)
			{
				alert("수량을 입력하시오.");
				return;
			}

			var cqty = parseInt(document.getElementById("current_qty").innerHTML);
			if(data.qty > cqty)
			{
				alert("재고가 부족합니다.");
				return;
			}

			$.get("../matOutputLXTabletRest/MOM_Save",data,function(){
				reload();
			});
		}

		document.getElementById("cancelbtn").onclick = function(){
			reload();
		}