<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<div class="row">
		<div class="col-md-9" style="font-size: 50px; text-align: center; width: 100%;">
						생산 현황 <font style="color: rgb(88,221,178);">모니터링</font>
		</div>
		<div class="col-md-3" style="font-size: 40px; text-align: center; width: 100%;">
			<p id="day">00:00:00</p>
			<p id="time">00:00:00</p>
		</div>
	</div>
	
	<%
		for(int i=1;i<3;i++)
		{
	%>
			<div class="row">
	<%
			for(int j=1;j<4;j++)
			{
				int jj = j;
				
				if(i==2)
				{
					jj += 3;
				}
	%>
			<div class="col-md-4" style="height: 350px; background: rgb(57,62,82);">
				<p style="font-size: 60px; margin: auto; text-align: center;">
						<%=jj%>호기
				</p>
				<div id="m00<%=jj%>_table"></div>
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
		for(int i=1;i<7;i++)
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
					$.get("workOrderTABRestXO/WOT_Search?machineCode=M00<%=i%>"+"&startDate="+$("#startDate").val() + "&endDate=" + $("#endDate").val() +"&statusCodeArr="+ 245, function(data) {
						
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