<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link href="/css/material/matOrderPrint.css" rel="stylesheet" />
<!-- MAIN -->
<%
request.setCharacterEncoding("UTF-8");
String mCus_No = request.getParameter("mCus_No");
String mCode = request.getParameter("mCode");
String mName = request.getParameter("mName");
//String mDate = request.getParameter("mDate").substring(0, 10);
String mDlvry_Date = request.getParameter("mDlvry_Date");
String MyCom_Rgstr_Nr = request.getParameter("MyCom_Rgstr_Nr");
String mCusCo = request.getParameter("mCusCo");
String mCusMng = request.getParameter("mCusMng");
String mCusRprsn = request.getParameter("mCusRprsn");
String mCusAdr = request.getParameter("mCusAdr");
String mCusRprsnPhNr = request.getParameter("mCusRprsnPhNr");
String mCusMngPhNr = request.getParameter("mCusMngPhNr");
String mTotal = request.getParameter("mTotal");
%> 
<div class="main">
	<div style="height: 59px">
		<!-- matOrderPrint -->
		<div class="container">
			<div>
				<h1 class="text-center" style="font-family: 돋움체; font-size: 30px;">발 주 서</h1>
				<b>주식회사 8당</b>
				<p>
					&#9654; No : <input id="order_lCus_No" value="<%=mCus_No%>"
						disabled>
				</p>
			</div>
			<table>
				<tr>
					<th width="100" colspan="2" rowspan="2"><span>회&ensp;사&ensp;명</span>
					</th>
					<td rowspan="2"><span id="CusCom_Name"><%=mName%></span></td>
					<th colspan="2"><span>상&ensp;호&ensp;명</span></th>
					<td colspan="3"><span id="MyCom_Co"></span><%=mCusCo%></td>
				</tr>
				<tr>
					<th colspan="2"><span>사업자등록번호</span></th>
					<td colspan="3"><span id="MyCom_Rgstr_Nr"><%=MyCom_Rgstr_Nr%></span></td>
				</tr>
				<tr>
					<th colspan="2"><span>담&ensp;당&ensp;자</span></th>
					<td><span id="CusCom_Mng"></span><%=mCusMng%></td>
					<th colspan="2"><span>대&ensp;표&ensp;자</span></th>
					<td colspan="3"><span id="MyCom_Rprsn"><%=mCusRprsn%></span></td>
				</tr>
				<tr>
					<th colspan="2"><span>주&emsp;&emsp;소</span></th>
					<td colspan="6"><span id="CusCom_Adr"><%=mCusAdr%></span></td>
				</tr>
				<%-- <tr>
						<th colspan="2"><span>발&ensp;주&ensp;일</span></th>
						<td><span><%=mDate%></span></td>
						<th colspan="2"><span>담&ensp;당&ensp;자</span></th>
						<td colspan="3"><span id="MyCom_Mng"></span><br> <span
							id="MyCom_Mng_Email"></span></td>
					</tr> --%>
				<tr>
					<th colspan="2"><span>납&ensp;기&ensp;일</span></th>
					<td><span><%=mDlvry_Date%></span></td>
					<th colspan="2"><span>연&ensp;락&ensp;처</span></th>
					<td colspan="3"><span id="MyCom_Mng_PhNr"><%=mCusMngPhNr%></span></td>
				</tr>
				<tr>
					<th colspan="2"><span>발주금액</span></th>
					<td colspan="6" class="td_left"><span id="total_price"><%=mTotal%></span>
					</td>
				</tr>
				<tr style="border-top: 2px solid #444;">
					<th width="20"><span>No</span></th>
					<th width="80"><span>제품코드</span></th>
					<th><span>제품명</span></th>
					<th width="70"><span>규격</span></th>
					<th width="50"><span>수량</span></th>
					<th width="70"><span>단가</span></th>
					<th><span>금액</span></th>
					<th><span>비고</span></th>
				</tr>
			</table>
			<br>
			<div>
				<div>
					<div style="border-top: 2px solid #444;">
						<div style="float:left; width:130px;">&#9654; 법인명 :</div>		
						<div id="" style="float:left; width:200px;"><span>sls</span></div>	
						<div style="float:left; width:130px;">&#9654; 법인주소 :</div>	
						<div id="" style="float:left; width:200px;"><span>sls</span></div>				
					</div>
					<div>
						<div style="float:left; width:130px;">&#9654; 대표이사 :</div>		
						<div id="" style="float:left; width:200px;"><span>sls</span></div>	
						<div style="float:left; width:130px;">&#9654; 대표전화 :</div>	
						<div id="" style="float:left; width:200px;"><span>sls</span></div>				
					</div>
					<div>
						<div style="float:left; width:130px;">&#9654; 담당자 :</div>		
						<div id="" style="float:left; width:200px;"><span>sls</span></div>
						<div style="float:left; width:130px;">&#9654; 전화번호 :</div>		
						<div id="" style="float:left; width:200px;"><span>sls</span></div>	
					</div>
					<div>
						<div style="float:left; width:130px;">&#9654; E-Mail :</div>	
						<div id="" style="float:left; width:200px;"><span>sls</span></div>		
					</div>
				</div>
				<br>
			</div>
			<br><br><br>
			<div class="footer">
				<button class="printBtn" style="text-align: center;">인쇄하기</button>
			</div>
		</div>
		<!-- matOrderSub -->
	</div>
	<!-- END MAIN -->
</div>
<script
	src="/js/materialLX/matOrderLX.js?v=<%=System.currentTimeMillis()%>"></script>