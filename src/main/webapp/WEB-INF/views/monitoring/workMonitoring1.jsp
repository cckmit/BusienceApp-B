<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<div style="width:100%;height:20%; padding: 10px;">
		<div style="width:100%;height:100%; float: left; border: solid;">
			<table style="width:100%;">
				<tr>
					<td style="font-size: 120px; text-align: right; width: 70%;">
						작업 모니터링
					</td>
					<td style="font-size: 40px; text-align: right; width: 30%; padding-right: 10px;">
						<p id="day">00:00:00</p>
						<p id="time">00:00:00</p>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div style="width:100%;height:80%; padding: 10px;">
		<%
			for(int i=1;i<2;i++)
			{
		%>
				<div style="height: 100%; border: solid;">
		<%
				for(int j=1;j<2;j++)
				{
					int jj = j;
					
					if(i==2)
					{
						jj += 0;
					}
		%>
				<div style="width:100%;height:100%; float: left; border-right: solid;" id="m<%=jj%>t">
				<div style="height: 20%; display: flex; border-bottom: solid;">
					<p style="font-size: 120px; margin: auto;">
						<%=jj%>호기
					</p>
				</div>
				<div style="height: 70%; padding: 10px; font-size: 80px;">
						<table style="width: 100%;height: 100%; margin-top: 30px;">
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
				</div>
		<%
				}
		%>
				</div>
		<%
			}
		%>
		
		</div>
		
		<script>
			
		
			<%
				for(int i=1;i<2;i++)
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
			let vtoday = new Date();   
	
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
			
			setInterval(function(){
				vtoday = new Date();
				
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