<%@page import="java.util.ArrayList"%>
<%@page import="com.busience.standard.dto.EQUIPMENT_INFO_TBL"%>
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