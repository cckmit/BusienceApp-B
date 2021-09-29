<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
		<div class="soloView">

			<div class="tabs-wrap">

		<ul id="navigation">
			<li class="one selected"><a href="#div1">(월별)설비 작업현황</a></li>
			<li class="one2"><a href="#div1">(월별)설비 작업현황2</a></li>
			<li class="two"><a id="q" href="#div2">(분기별)설비 작업현황</a></li>
			<li class="shadow"></li>
		</ul>

		<div id="content">

					<ol>
						<li>
							<div id="div1">
								<jsp:include page="worktdListMonth.jsp" />
							</div>
						</li>
						<li>
							<div id="div11">
								<jsp:include page="worktdListMonth2.jsp" />
							</div>
						</li>
						<li>
							<div id="div2">
								<jsp:include page="worktdListQuarter.jsp" />
							</div>
						</li>
						<li><div id="div4"></div></li>
					</ol>
					
				</div>
				<!-- .demo-nav -->
			</div>
			<!-- inline wrapper -->
			
		</div>
	<script src="/js/productionLX/worktdList.js"></script>
	<script src="/js/tabMenu.js"></script>

	<script>
	 /* worktdListMonth2 */
	
	var EQUIPMENT_INFO_TBL11 = new Tabulator("#EQUIPMENT_INFO_TBL11", {
		height: "calc(100% - 137px)",
		//복사하여 엑셀 붙여넣기 가능
		clipboard: true,
		ajaxURL:"worktdListRest/One_Grid_init", //ajax URL
		rowClick: function (e, row) {
			EQUIPMENT_INFO_TBL11.deselectRow();
			row.select();
			
			if(isNaN($("#year1").val()))
			{
				alert("숫자를 입력하여 주십시오.");
				return;
			}
			
			if($("#year1").val().length!=4)
			{
				alert("연도를 다시 선택하여 주십시오.");
				return;
			}
			
			$.ajax({
				method: "GET",
				async: false,
				url: "worktdListRest/Month_Select?year="+$("#year11").val()+"&equipment_INFO_CODE="+row.getData().equipment_INFO_CODE,
				success: function (datas) {
					//console.log(datas);
					WorkOrder_tbl11.setData(datas);
					
					$.ajax({
						method: "GET",
						async: false,
						url: "worktdListRest/Month_Select_Detail?year="+$("#year1").val()+"&equipment_INFO_CODE="+row.getData().equipment_INFO_CODE,
						success: function (datas) {
							//console.log(datas);
							WorkOrder_tbl11.setData(datas);
						}
					});
					
					//console.log(WorkOrder_tbl11.getData());
					
					var oldData = WorkOrder_tbl11.getData();
					var newData = [];
					var newData2 = [];
					for(i=0;i<oldData.length;i++)
					{
						if(oldData[i]._children.length > 0)
						{
							newData.push(oldData[i]);
							newData2.push(oldData[i]._children);
						}
					}
					
					var nameData = [];
					for(i=0;i<newData.length;i++)
					{
						for(j=0;j<newData[i]._children.length;j++){
							//console.log(newData[i]._children[j].ym);
							nameData.push(newData[i]._children[j].ym);
						}
					}
					
					//console.log(nameData);
					var uniqueArr = [];

					for (var i=0; i<nameData.length; i++) {
					  if (uniqueArr.indexOf(nameData[i]) === -1) uniqueArr.push(nameData[i]);
					}
					//console.log(uniqueArr);
					
					drawArray= [];
					drawArray.push("Year");
					for (var i=0; i<uniqueArr.length; i++){
						drawArray.push(uniqueArr[i]);
						var rol = { role : 'annotation'};
						drawArray.push(rol);
					}
					
					//console.log(newData2);
					
					totalArray= [];
					totalArray.push(drawArray);
					var arr = null;
					for(i=0;i<newData.length;i++){
						arr = [];
						arr.push(newData[i].ym);
						console.log(newData[i]);
						//console.log(newData2[i]);
						
						//for(j=0;j<totalArray[0].length-1;j++)
							//arr.push(0);
						
						var datas = newData2[i];
						
						//console.log(uniqueArr);
						for (var j=0; j<uniqueArr.length; j++)
						{
							/*
							for(j=0;j<datas.length;j++)
							{
								//console.log(datas);
								
								if(datas[j].ym == uniqueArr[j])
								{
									
								}
							}
							*/
							
							var flag = true;
							
							for(m=0;m<datas.length;m++)
							{
								//console.log(datas[m]);
								
								if(datas[m].ym == uniqueArr[j])
								{
									arr.push(parseInt(datas[m].production_Volume));
									arr.push(parseInt(datas[m].production_Volume));
									flag = false;
									break;
								}
							}
							
							if(flag)
							{
								arr.push(0);
								arr.push(0);
							}
						}
						
						console.log("-----------------------------------");
						
						totalArray.push(arr);
					}
					console.log(totalArray);
					
					var options = {
						      title: 'Sales and Purchase Comparsion'    
					};  

					var data = google.visualization.arrayToDataTable(totalArray);
					// Instantiate and draw the chart.
					var chart = new google.visualization.ColumnChart(document.getElementById('Graph11'));
					chart.draw(data, options);
			}
					
			});
		},
	
		columns: [
			{ title: "설비코드", field: "equipment_INFO_CODE", headerHozAlign: "center", width: 100, visible: true },
			{ title: "설비이름", field: "equipment_INFO_NAME", headerHozAlign: "center", width: 150, visible: true }
		]
	});
	
	var WorkOrder_tbl11 = new Tabulator("#WorkOrder_tbl11", {
		height: "calc(100% - 137px)",
		//복사하여 엑셀 붙여넣기 가능
		clipboard: true,
		dataTree:true,
	    dataTreeStartExpanded:true,
		rowClick: function (e, row) {
		},

		columns: [
			{ title: "날짜", field: "ym", headerHozAlign: "center", width: 200, visible: true },
			{ title: "생산량", field: "production_Volume", headerHozAlign: "center", width: 120, visible: true, hozAlign:"right",formatter:"money", formatterParams: {precision: false} }
		]
	});
	
	function name_export(list,value) {
		
		for(i=0;i<list.length;i++)
			if(list[i] == value)
				return true;
		
		return false;
	}
	</script>
</body>
</html>