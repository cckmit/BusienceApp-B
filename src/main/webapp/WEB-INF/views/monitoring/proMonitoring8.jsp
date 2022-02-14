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
			for(int j=1;j<5;j++)
			{
				int jj = j;
				
				if(i==2)
				{
					jj += 4;
				}
	%>
			<div class="col-md-3" style="height: 350px; background: rgb(57,62,82);">
				<p style="font-size: 60px; margin: auto; text-align: center;">
						<%=jj%>호기
				</p>
				<div id="M00<%=jj%>_table"></div>
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
			for(let i=1;i<9;i++){
				new Tabulator("#M00"+i+"_table", {
				    height:"75%",
					layoutColumnsOnNewData : true,
				    resizableColumns:false,
				    ajaxLoader:false,
				    ajaxURL:"workOrderTABRestXO/WOT_Search",
				    ajaxConfig:"get",
				    ajaxContentType:"json",
				    ajaxParams:{
				    	machineCode : "M00"+i,
				    	startDate : today.toISOString().substring(0, 10),
				    	endDate : tomorrow.toISOString().substring(0, 10),
				    	statusCodeArr : "245"
				    },
				    columns:[
				        {title:"작업지시번호", field:"workOrder_ONo",visible:false, headerSort:false, headerHozAlign: "center"},
				        { title: "제품이름", field: "workOrder_ItemName", headerHozAlign: "center", headerSort:false},
				        { title: "생산", field: "workOrder_RQty", headerHozAlign: "center", align: "right",
							formatter:"money", formatterParams: {precision: false}, headerSort:false, bottomCalc:"sum"					
						},
						{ title: "작업시작일", field: "workOrder_StartTime", align: "right", headerHozAlign: "center", headerSort:false,
							formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm" }},
						{ title: "작업완료일", field: "workOrder_CompleteTime", align: "right", headerHozAlign: "center", headerSort:false,
							formatter: "datetime", formatterParams: { outputFormat: "YYYY-MM-DD HH:mm" }}
				    ]
				});	
			}

		function setClock(){
			vtoday = new Date();
			
			let year = vtoday.getFullYear(); // 년도
			
			let month = pluszero(vtoday.getMonth() + 1, 2);  // 월
			let date = pluszero(vtoday.getDate(), 2);  // 날짜
			let day = pluszero(vtoday.getDay(), 2);  // 요일
			
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
			
			let hours = pluszero(vtoday.getHours(), 2); // 시
			let minutes = pluszero(vtoday.getMinutes(), 2);  // 분
			let seconds = pluszero(vtoday.getSeconds(), 2);  // 초
			
			document.getElementById("time").innerHTML = hours + ':' + minutes + ':' + seconds;
			
		}
		setClock();
		setInterval(setClock,1000);
		</script>
		
		<style>
			.tabulator { font-size: 16px; }
		</style>