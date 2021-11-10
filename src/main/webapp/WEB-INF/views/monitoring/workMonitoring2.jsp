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
				<div style="width:50%;height:100%; float: left; border-right: solid;" id="m<%=jj%>t">
				<div style="height: 20%; display: flex; border-bottom: solid;">
					<p style="font-size: 110px; margin: auto;">
						<%=jj%>호기
					</p>
				</div>
				<div style="height: 70%; padding: 10px; font-size: 55px;">
						<table style="width: 100%;height: 100%; margin-top: 55px;">
							<tr>
								<td>
									작업지시번호
								</td>
								<td style="text-align:right;" id="m<%=jj%>num">
									
								</td>
							</tr>
							<tr>
								<td>
									제품명
								</td>
								<td style="text-align:right;" id="m<%=jj%>n">
									
								</td>
							</tr>
							<tr>
								<td>
									생산량
								</td>
								<td style="text-align:right;" id="m<%=jj%>q">
									
								</td>
							</tr>
							<tr>
								<td>
									지시수량
								</td>
								<td style="text-align:right;" id="m<%=jj%>o">
									
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
				for(int i=1;i<3;i++)
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
