<%@page import="java.util.ArrayList"%>
<%@page import="com.busience.standard.Dto.EQUIPMENT_INFO_TBL"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<style type="text/css">
.easy-pie-chart {
    position: relative;
    width: 300px;
    margin: 0 auto;
    margin-bottom: 130px;
    text-align: center;
}
</style>

<h1 id="today" style="font-size: 40px; color: white; font-weight: 600;">작업현황 모니터링</h1>

<div class="row">

<%
	Class.forName("com.mysql.jdbc.Driver");
	Connection con = DriverManager.getConnection("jdbc:mysql://busience2.cafe24.com:3306/busience2","busience2","business12!!");
	
	String sql = "select * from EQUIPMENT_INFO_TBL";
	PreparedStatement pstmt = con.prepareStatement(sql);
	ResultSet rs = pstmt.executeQuery();
	List<EQUIPMENT_INFO_TBL> equip_List = new ArrayList<EQUIPMENT_INFO_TBL>();
	
	EQUIPMENT_INFO_TBL equip;
	while(rs.next())
	{
		equip = new EQUIPMENT_INFO_TBL();
		equip.setEQUIPMENT_INFO_CODE(rs.getString("EQUIPMENT_INFO_CODE"));
		equip.setEQUIPMENT_INFO_NAME(rs.getString("EQUIPMENT_INFO_NAME"));
		equip_List.add(equip);
	}
	rs.close();
	pstmt.close();
	con.close();	
	
	for(int i=0;i<equip_List.size();i++)
	{
%>
		<div class="col-md-3">
			<!-- REALTIME CHART -->
			<div class="panel" id="<%=equip_List.get(i).getEQUIPMENT_INFO_CODE()%>">
				<div class="panel-heading">
					<h3 class="panel-title" style="font-size: 40px; text-align: center; color: white; font-weight: 600;"><%=equip_List.get(i).getEQUIPMENT_INFO_NAME()%>&nbsp;<font id="n<%=equip_List.get(i).getEQUIPMENT_INFO_CODE()%>"></font></h3>
					<div class="right">
						<button type="button" class="btn-toggle-collapse"><i class="lnr lnr-chevron-up"></i></button>
						<button type="button" class="btn-remove"><i class="lnr lnr-cross"></i></button>
					</div>
				</div>
				
				<div class="panel-body">
					<div id="system-load<%=equip_List.get(i).getEQUIPMENT_INFO_CODE()%>" class="easy-pie-chart" data-percent="0">
						<span class="percent" style="font-size: 40px; padding-left: 4px;padding-top: 100px; color: white;">달성율 <br/>0</span>
					</div>
					
					<ul class="list-unstyled list-justify">
						<li><h4 style="font-size: 30px; color: white;">작업지시번호 <span id="workOrder_ONo_<%=equip_List.get(i).getEQUIPMENT_INFO_CODE()%>"></span></h4></li>						
						<li><h4 style="font-size: 30px; color: white;">지시수량 <span id="workOrder_PQty_<%=equip_List.get(i).getEQUIPMENT_INFO_CODE()%>"></span></h4></li>
						<li><h4 style="font-size: 30px; color: white;">생산량 <span id="workOrder_RQty_<%=equip_List.get(i).getEQUIPMENT_INFO_CODE()%>"></span></h4></li>
						<li><h4 style="font-size: 30px; color: white;">제품 <span id="workOrder_ItemName_<%=equip_List.get(i).getEQUIPMENT_INFO_CODE()%>"></span></h4></li>
						<li><h4 style="font-size: 30px; color: white;">작업시작일 <span id="workOrder_StartTime_<%=equip_List.get(i).getEQUIPMENT_INFO_CODE()%>"></span></h4></li>
					</ul>
				</div>
			</div>
		</div>	
<%		
	}
%>
</div>
<!-- END WRAPPER -->

<script type="text/javascript">
function Data_Load(chart,equipcode)
{
	$.ajax({
		method : "GET",
		dataType : "json",
		url : "workMonitoringRest/moni?WorkOrder_EquipCode="+equipcode,
		success : function(data) {
			//console.log(data);
			
			if(data.percent != null)
			{
				//dbdata_flag
				const equip_Code_Node = document.getElementById(data.dbdata_flag);
				//console.log(equip_Code_Node);
				equip_Code_Node.style.background  = "black";
				equip_Code_Node.style.borderColor = "red";
				
				const equip_Code_Node2 = document.getElementById("n"+data.dbdata_flag);
				equip_Code_Node2.innerText = "(가동중)";
				
				chart.data('easyPieChart').update(data.percent);
				chart.find('.percent').text("달성율 "+data.percent);
				
				//console.log('workOrder_ONo_'+data.workOrder_EquipCode);
				
				const workOrder_ONo = document.getElementById('workOrder_ONo_'+data.workOrder_EquipCode);
				workOrder_ONo.innerText  = data.workOrder_ONo;
				
				//workOrder_PQty_
				const workOrder_PQty = document.getElementById('workOrder_PQty_'+data.workOrder_EquipCode);
				workOrder_PQty.innerText  = data.workOrder_PQty;
				
				//workOrder_RQty_
				const workOrder_RQty = document.getElementById('workOrder_RQty_'+data.workOrder_EquipCode);
				workOrder_RQty.innerText  = data.workOrder_RQty;
				
				//oqcinspect_DQty
				//const oqcinspect_DQty = document.getElementById('oqcinspect_DQty_'+data.workOrder_EquipCode);
				//oqcinspect_DQty.innerText  = data.oqcinspect_DQty;
				
				//workOrder_StartTime_
				const workOrder_StartTime = document.getElementById('workOrder_StartTime_'+data.workOrder_EquipCode);
				workOrder_StartTime.innerText  = data.workOrder_StartTime;
				
				//workOrder_ItemName
				const workOrder_ItemName = document.getElementById('workOrder_ItemName_'+data.workOrder_EquipCode);
				workOrder_ItemName.innerText  = data.workOrder_ItemName;
			}
			else
			{
				//dbdata_flag
				const equip_Code_Node = document.getElementById(data.dbdata_flag);
				//console.log(equip_Code_Node);
				equip_Code_Node.style.background  = "black";
				equip_Code_Node.style.borderColor = "red";
				
				const equip_Code_Node2 = document.getElementById("n"+data.dbdata_flag);
				equip_Code_Node2.innerText = "(비가동중)";
				
				chart.data('easyPieChart').update(0);
				chart.find('.percent').text("달성율 0");
				
				//console.log('workOrder_ONo_'+data.workOrder_EquipCode);
				
				const workOrder_ONo = document.getElementById('workOrder_ONo_'+data.dbdata_flag);
				workOrder_ONo.innerText  = "";
				
				//workOrder_PQty_
				const workOrder_PQty = document.getElementById('workOrder_PQty_'+data.dbdata_flag);
				workOrder_PQty.innerText  = "";
				
				//workOrder_RQty_
				const workOrder_RQty = document.getElementById('workOrder_RQty_'+data.dbdata_flag);
				workOrder_RQty.innerText  = "";
				
				//oqcinspect_DQty
				//const oqcinspect_DQty = document.getElementById('oqcinspect_DQty_'+data.workOrder_EquipCode);
				//oqcinspect_DQty.innerText  = data.oqcinspect_DQty;
				
				//workOrder_StartTime_
				const workOrder_StartTime = document.getElementById('workOrder_StartTime_'+data.dbdata_flag);
				workOrder_StartTime.innerText  = "";
				
				//workOrder_ItemName
				const workOrder_ItemName = document.getElementById('workOrder_ItemName_'+data.dbdata_flag);
				workOrder_ItemName.innerText  = "";
			}
		}
	});
}
</script>

<%
	for(int i=0;i<equip_List.size();i++)
	{
%>
		<script>
			var <%=equip_List.get(i).getEQUIPMENT_INFO_CODE()%> = $('#system-load<%=equip_List.get(i).getEQUIPMENT_INFO_CODE()%>').easyPieChart({
				size: 300,
				barColor: function(percent) {
					return "rgb(" + Math.round(200 * percent / 100) + ", " + Math.round(200 * (1.1 - percent / 100)) + ", 0)";
				},
				trackColor: 'rgba(245, 245, 245, 0.8)',
				scaleColor: false,
				lineWidth: 30,
				lineCap: "square",
				animate: 800
			});
			
			Data_Load(<%=equip_List.get(i).getEQUIPMENT_INFO_CODE()%>,"<%=equip_List.get(i).getEQUIPMENT_INFO_CODE()%>");
			
			setInterval(function() {
				Data_Load(<%=equip_List.get(i).getEQUIPMENT_INFO_CODE()%>,"<%=equip_List.get(i).getEQUIPMENT_INFO_CODE()%>");
			}, 10000);
		</script>
<%
	}
%>

	<script>
	$(function() {
		// real-time pie chart
		var sysLoad = $('#system-load').easyPieChart({
			size: 500,
			barColor: function(percent) {
				return "rgb(" + Math.round(200 * percent / 100) + ", " + Math.round(200 * (1.1 - percent / 100)) + ", 0)";
			},
			trackColor: 'rgba(245, 245, 245, 0.8)',
			scaleColor: false,
			lineWidth: 100,
			lineCap: "square",
			animate: 800
		});

		/*
		var updateInterval = 3000; // in milliseconds

		setInterval(function() {
			var randomVal;
			randomVal = getRandomInt(0, 100);

			sysLoad.data('easyPieChart').update(randomVal);
			sysLoad.find('.percent').text(randomVal);
		}, updateInterval);

		function getRandomInt(min, max) {
			return Math.floor(Math.random() * (max - min + 1)) + min;
		}
		

		setTimeout(function() {
			sysLoad.data('easyPieChart').update(32);
			sysLoad.find('.percent').text("달성율 32");
		}, 3000);
		*/
		
	});
	
	const todays = document.getElementById('today');
	todays.innerText  = "작업현황 모니터링 "+today.toISOString().substring(0, 10);
	</script>