<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<div style="width:100%;height:100%; padding: 10px;">
		<%
			for(int i=1;i<2;i++)
			{
		%>
				<div style="height: 100%; border: solid;">
		<%
				for(int j=1;j<3;j++)
				{
					int jj = j;
					
					if(i==2)
					{
						jj += 0;
					}
		%>
				<div style="width:100%; height:100%; float: left; border-right: solid;" id="m<%=jj%>t">
				<div style="height: 20%; display: flex; border-bottom: solid;">
					<p style="font-size: 130px; margin: auto;">
						<%=jj%>호기
					</p>
				</div>
					<div style="height: 80%; font-size: 50px;">
							<div id="m00<%=jj%>_table"></div>
					</div>
				</div>
		<%
				}
		%>
				</div>
		<%
			}
		%>
		
		</div>
		
		<input id="startDate" class="today" type="date" style="width: 180px; height: 35px; font-size: 20px;"> 
		<input id="endDate" class="tomorrow" type="date" style="width: 180px; height: 35px; font-size: 20px;">
		
		<script>
			
		
			<%
				for(int i=1;i<2;i++)
				{
			%>
					var m00<%=i%>_table = new Tabulator("#m00<%=i%>_table", {
					    height:"100%",
					    layout:"fitColumns",
					    resizableColumns:false,
					    columns:[
					        {title:"작업지시번호", field:"defect_ONo",visible:true, width: 180},
					        { title: "제품이름", field: "defect_CODE", headerHozAlign: "center", width: 180 },
					        { title: "지시수량", field: "workOrder_PQty", headerHozAlign: "center", width: 100, align: "right",
								formatter:"money", formatterParams: {precision: false}
							},
					        { title: "생산량", field: "workOrder_RQty", headerHozAlign: "center", width: 100, align: "right",
								formatter:"money", formatterParams: {precision: false}
							},
							{ title: "불량수", field: "defect_DQty", headerHozAlign: "center", width: 100, align: "right",
								formatter:"money", formatterParams: {precision: false}
							},
							{ title: "작업시작일", field: "workOrder_StartTime", align: "right", headerHozAlign: "center", width: 160,visible:false},
							{ title: "검사일", field: "defect_TestTime", align: "right", headerHozAlign: "center", width: 160},
							{ title: "특이사항", field: "workOrder_Remark", align: "right", headerHozAlign: "center",visible:false}
							,{title:"미성형", field:"d001",visible:true, width: 180, headerHozAlign: "center", align: "right"}
							,{title:"흑점", field:"d002",visible:true, width: 180, headerHozAlign: "center", align: "right"}
							,{title:"버", field:"d003",visible:true, width: 180, headerHozAlign: "center", align: "right"}
							,{title:"이물질", field:"d004",visible:true, width: 180, headerHozAlign: "center", align: "right"}
							,{title:"정지", field:"d997",visible:true, width: 180, headerHozAlign: "center", align: "right"}
					    ],
					});
					
					setInterval(function(){
						$.get("defectMonitoringRest/defect_view?WorkOrder_EquipCode=m00<%=i%>"+"&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
							console.log("<%=i%>");
							console.log(data);
							
							m00<%=i%>_table.setData(data);
						});
					},10000);
			<%
				}
			%>
		</script>
		
		<style>
			.tabulator { font-size: 16px; }
		</style>
