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
%>
<div class="main">
	<div class="top-var" style="height: 59px">
		<!-- matOrderPrint -->
		<div class="container">
			<div>
				<h1 class="text-center">자재발주서</h1>
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
					<td colspan="3"><span id="MyCom_Co"></span></td>
				</tr>
				<tr>
					<th colspan="2"><span>사업자등록번호</span></th>
					<td colspan="3"><span id="MyCom_Rgstr_Nr"></span></td>
				</tr>
				<tr>
					<th colspan="2"><span>담&ensp;당&ensp;자</span></th>
					<td><span id="CusCom_Mng"></span></td>
					<th colspan="2"><span>대&ensp;표&ensp;자</span></th>
					<td colspan="3"><span id="MyCom_Rprsn"></span></td>
				</tr>
				<tr>
					<th colspan="2"><span>주&emsp;&emsp;소</span></th>
					<td><span id="CusCom_Adr"></span></td>
					<th colspan="2"><span>주&emsp;&emsp;소</span></th>
					<td colspan="3"><span id="MyCom_Adr"></span></td>
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
					<td colspan="3"><span id="MyCom_Mng_PhNr"></span></td>
				</tr>
				<tr>
					<th colspan="2"><span>발주금액</span></th>
					<td colspan="6" class="td_left"><span id="total_price"></span>
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
			<div class="footer">
				<button class="printBtn">인쇄하기</button>
				<button id="emailBtn">메일발송</button>
			</div>
		</div>
		<!-- matOrderSub -->
	</div>
	<!-- END MAIN -->
</div>
<script
	src="/js/material/matOrder.js?v=<%=System.currentTimeMillis()%>"></script>