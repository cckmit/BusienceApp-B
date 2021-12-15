<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<div class="row">
		<div class="col-md-9" style="font-size: 50px; text-align: center; width: 100%;">
						작업 <font style="color: rgb(88,221,178);">모니터링</font>
		</div>
		<div class="col-md-3" style="font-size: 40px; text-align: center; width: 100%;">
			<p id="day">00:00:00</p>
			<p id="time">00:00:00</p>
		</div>
	</div>
	
	<%
		for(int i=1;i<2;i++)
		{
	%>
			<div class="row">
	<%
			for(int j=1;j<2;j++)
			{
				int jj = j;
				
				if(i==2)
				{
					jj += 0;
				}
	%>
			<div class="col-md-12" style="background: rgb(57,62,82);">
				<p style="font-size: 60px;text-align: center;">
						<%=jj%>호기
				</p>
				<table style="width: 100%;font-size: 28.5px;">
							<tr>
								<td>
									작업지시번호
								</td>
								<td style="text-align:left;" id="m<%=jj%>num">
									
								</td>
							</tr>
							<tr>
								<td>
									제&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;품&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;명
								</td>
								<td style="text-align:left;" id="m<%=jj%>n">
									
								</td>
							</tr>
							<tr>
								<td>
									지&nbsp;&nbsp;시&nbsp;&nbsp;&nbsp;수&nbsp;&nbsp;량
								</td>
								<td style="text-align:center;" id="m<%=jj%>o">
									
								</td>
							</tr>
							<tr>
								<td>
									생&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;산&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;량
								</td>
								<td style="text-align:center;" id="m<%=jj%>q">
									
								</td>
							</tr>
							
							
							<tr>
								<td colspan="2">
									<div class="progress" style="height: 70px;border: solid;">
									  <div id="m<%=jj%>p" class="progress-bar" role="progressbar" style="width: 0%; font-size: 65px;" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100"><p style="margin-top: 25px; color: black;" id="m<%=jj%>p2">0%</p></div>
									</div>
								</td>
							</tr>
						</table>
			</div>
	<%
			}
	%>
			</div>
	<%
		}
	%>
	
	<div style="visibility: hidden;">
		<input id="startDate" class="today" type="date" style="width: 180px; height: 35px; font-size: 20px;"> 
		<input id="endDate" class="tomorrow" type="date" style="width: 180px; height: 35px; font-size: 20px;">
		</div>
		<script>
			
		
			<%
				for(int i=1;i<9;i++)
				{
			%>
					var m00<%=i%>_table = new Tabulator("#m00<%=i%>_table", {
					    height:"75%",
					    resizableColumns:false,
					    columns:[
					        {title:"작업지시번호", field:"workOrder_ONo",visible:false, headerSort:false, width: 170, headerHozAlign: "center"},
					        { title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center", width: 150, headerSort:false },
					        { title: "생산", field: "workOrder_RQty", headerHozAlign: "center", width: 60, align: "right",
								formatter:"money", formatterParams: {precision: false}, headerSort:false,visible:true
							},
							{ title: "작업시작일", field: "workOrder_StartTime", align: "right", headerHozAlign: "center", width: 140,visible:true, headerSort:false},
							{ title: "작업완료일", field: "workOrder_CompleteTime", align: "right", headerHozAlign: "center", width: 108, headerSort:false,visible:true},
							{ title: "특이사항", field: "workOrder_Remark", align: "right", headerHozAlign: "center",visible:false, headerSort:true}
					    ],
					});
					
					setInterval(function(){
							$.get("workOrderTABRestXO/MI_Searchd?WorkOrder_EquipCode=m00<%=i%>"+"&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val(), function(data) {
								
								sum_workOrder_RQty = 0;
								
								data.forEach(function(element){
									sum_workOrder_RQty += parseInt(element.workOrder_RQty);
								});
								
								if(data.length > 0)
								{
									result = {};
									result.workOrder_RQty = sum_workOrder_RQty;
									result.workOrder_ItemName = "총 생산량";
									data.push(result);
								}
								
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
		
		
		<script>
			
		
			<%
				for(int i=1;i<9;i++)
				{
			%>
				setInterval(function(){
					$.get("/workMonitoringRest/WorkOrder_select2?code=m00<%=i%>",function(data){
						
						//console.log(data);
	
						if(data[0].workOrder_ItemName != null)
						{
							document.getElementById("m<%=i%>num").innerHTML = data[0].workOrder_No;
							document.getElementById("m<%=i%>n").innerHTML = data[0].workOrder_ItemName;
							document.getElementById("m<%=i%>q").innerHTML = data[0].qty;
							document.getElementById("m<%=i%>o").innerHTML = data[0].workOrder_PQty;
	
							document.getElementById("m<%=i%>p").style.width = data[0].percent+"%";
							document.getElementById("m<%=i%>p2").innerHTML = data[0].percent+"%";
						}
						else
						{
							document.getElementById("m<%=i%>num").innerHTML = "";
							document.getElementById("m<%=i%>n").innerHTML = "";
							document.getElementById("m<%=i%>q").innerHTML = "";
							document.getElementById("m<%=i%>o").innerHTML = "";
	
							document.getElementById("m<%=i%>p").style.width = "0%";
							document.getElementById("m<%=i%>p2").innerHTML = "0%";
						}
					});
				},5000);
			<%
				}
			%>
		</script>
		
		<script>
		
			setInterval(function(){
				vtoday = new Date();
				
				let year = vtoday.getFullYear(); // 년도
				let month = vtoday.getMonth() + 1;  // 월
				let date = vtoday.getDate();  // 날짜
				let day = vtoday.getDay();  // 요일
				
				if(day==0)
					day = "일요일"
				else if(day==1)
					day = "월요일"
				else if(day==2)
					day = "화요일"
				else if(day==3)
					day = "수요일"
				else if(day==4)
					day = "목요일"
				else if(day==5)
					day = "금요일"
				else if(day==6)
					day = "토요일"
					
				let value = year + '-' + month + '-' + date + ' ' + day;
				
				document.getElementById("day").innerHTML = value;
				
				let hours = vtoday.getHours(); // 시
				let minutes = vtoday.getMinutes();  // 분
				let seconds = vtoday.getSeconds();  // 초
				
				document.getElementById("time").innerHTML = hours + ':' + minutes + ':' + seconds;
			},1000);
		</script>
		
		<style>
			.progress-bar{
				background-color: #B0DEFC;
			}
		</style>