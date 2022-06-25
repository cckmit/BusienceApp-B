<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="/css/qc/oqcInspectPrint.css" rel="stylesheet" />
		<!-- MAIN -->
	<div class="main">
			<!-- 그리드 생성 장소 -->
		<div class="container">
			<table class="tg table table-bordered">
			<thead>
				<tr>
					<td rowspan="3" colspan="5"></td>
					<td colspan="22" rowspan="3" style="height: 30px !important;">
						<p style="font-family: '고딕'; font-weight: 900; letter-spacing: -0.05em; font-size: 28px;">출하검사 성적서</p>
					</td>
					<td colspan="2" rowspan="4">결<br><br><br>재</td>
					<td colspan="3">작 성</td>
					<td colspan="3">검 토</td>
					<td colspan="3">승 인</td>
				</tr>
				<tr>
					<td colspan="3" style="height: 70px !important;"></td>
					<td colspan="3"></td>
					<td colspan="3"></td>
				</tr>
				<tr>
					<td colspan="3">/</td>
					<td colspan="3">/</td>
					<td colspan="3">/</td>
				</tr>
			</thead>	
			<tbody>
				<tr>
					<td colspan="5">문서번호</td>
					<td colspan="8"><input type="text" id="proInspectEquipName" readonly></td>
					<td colspan="5">고&nbsp;&nbsp;객&nbsp;&nbsp;사</td>
					<td colspan="8"><input type="text" id="proInspectItemName" readonly></td>
					<td colspan="5">검&nbsp;&nbsp;사&nbsp;&nbsp;자</td>
					<td colspan="7"><input type="text" id="proInspectItemName" readonly></td>
				</tr>
				<tr>
					<td colspan="5">품&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;명</td>
					<td colspan="8"><input type="text" id="productionDate" readonly></td>
					<td colspan="5">출고수량</td>
					<td colspan="8"><input type="text" id="productionDate" readonly></td>
					<td colspan="5">규&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;격</td>
					<td colspan="7"><input type="text" id="productionDate" readonly></td>
				</tr>
				<tr>
					<td colspan="5">검사일자</td>
					<td colspan="8"><input type="text" id="processQty"></td>
					<td colspan="5">검사수량</td>
					<td colspan="8"><input type="text" id="productionDate" readonly></td>
					<td colspan="5">검사기준</td>
					<td colspan="7">KS Q ISO 2859-1</td>
				</tr>
				<tr>
					<td colspan="2" rowspan="2" style="border: 2px; width: 40px;">NO</td>
					<td colspan="3" rowspan="2">검사<br>항목</td>
					<td colspan="8" rowspan="2">검사기준</td>
					<td colspan="3" rowspan="2">검사<br>방법</td>
					<td colspan="20">검사수량</td>
					<td colspan="2" rowspan="2">판정</td>
				</tr>
				<tr>
					<td colspan="2">X1</td>
					<td colspan="2">X2</td>
					<td colspan="2">X3</td>
					<td colspan="2">X4</td>
					<td colspan="2">X5</td>
					<td colspan="2">X6</td>
					<td colspan="2">X7</td>
					<td colspan="2">X8</td>
					<td colspan="2">X9</td>
					<td colspan="2">X10</td>
				</tr>
				<tr>
					<td colspan="2" style="border: 2px; width: 30px;">1</td>
					<td colspan="3" rowspan="4">외관<br>상태</td>
					<td colspan="8" id="stndpart" style="letter-spacing: -0.05em !important;">코판 길이, 탈착여부, 융착선 확인</td>
					<td colspan="3" rowspan="16">육안<br>검사
					<input type="hidden" id="stnd1" style="width: 20%">
					<input type="hidden" id="stnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="value1" value="OK"></td>
					<td colspan="2"><input type="text" id="value2"></td>
					<td colspan="2"><input type="text" id="value3"></td>
					<td colspan="2"><input type="text" id="value4"></td>
					<td colspan="2"><input type="text" id="value5"></td>
					<td colspan="2"><input type="text" id="value6"></td>
					<td colspan="2"><input type="text" id="value7"></td>
					<td colspan="2"><input type="text" id="value8"></td>
					<td colspan="2"><input type="text" id="value9"></td>
					<td colspan="2"><input type="text" id="value10"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="2" style="border: 2px; width: 30px;">2</td>
					<td colspan="8" id="stndpart">마스크 성상, 형상
					<input type="hidden" id="stnd1" style="width: 20%">
					<input type="hidden" id="stnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="value1"></td>
					<td colspan="2"><input type="text" id="value2"></td>
					<td colspan="2"><input type="text" id="value3"></td>
					<td colspan="2"><input type="text" id="value4"></td>
					<td colspan="2"><input type="text" id="value5"></td>
					<td colspan="2"><input type="text" id="value6"></td>
					<td colspan="2"><input type="text" id="value7"></td>
					<td colspan="2"><input type="text" id="value8"></td>
					<td colspan="2"><input type="text" id="value9"></td>
					<td colspan="2"><input type="text" id="value10"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="2" style="border: 2px; width: 30px;">3</td>
					<td colspan="8" id="stndpart">머리끈 융착 상태, 위치 확인
					<input type="hidden" id="stnd1" style="width: 20%">
					<input type="hidden" id="stnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="value1"></td>
					<td colspan="2"><input type="text" id="value2"></td>
					<td colspan="2"><input type="text" id="value3"></td>
					<td colspan="2"><input type="text" id="value4"></td>
					<td colspan="2"><input type="text" id="value5"></td>
					<td colspan="2"><input type="text" id="value6"></td>
					<td colspan="2"><input type="text" id="value7"></td>
					<td colspan="2"><input type="text" id="value8"></td>
					<td colspan="2"><input type="text" id="value9"></td>
					<td colspan="2"><input type="text" id="value10"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="2" style="border: 2px; width: 30px;">4</td>
					<td colspan="8" id="stndpart" style="font-size: 3px;">마스크 내, 외부 오염 확인
					<input type="hidden" id="stnd1" style="width: 20%">
					<input type="hidden" id="stnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="value1"></td>
					<td colspan="2"><input type="text" id="value2"></td>
					<td colspan="2"><input type="text" id="value3"></td>
					<td colspan="2"><input type="text" id="value4"></td>
					<td colspan="2"><input type="text" id="value5"></td>
					<td colspan="2"><input type="text" id="value6"></td>
					<td colspan="2"><input type="text" id="value7"></td>
					<td colspan="2"><input type="text" id="value8"></td>
					<td colspan="2"><input type="text" id="value9"></td>
					<td colspan="2"><input type="text" id="value10"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="2" style="border: 2px; width: 30px;">5</td>
					<td colspan="3" rowspan="2">패키지<br>상태</td>
					<td colspan="8" id="stndpart">밀봉 상태 확인
					<input type="hidden" id="stnd1" style="width: 20%">
					<input type="hidden" id="stnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="value1"></td>
					<td colspan="2"><input type="text" id="value2"></td>
					<td colspan="2"><input type="text" id="value3"></td>
					<td colspan="2"><input type="text" id="value4"></td>
					<td colspan="2"><input type="text" id="value5"></td>
					<td colspan="2"><input type="text" id="value6"></td>
					<td colspan="2"><input type="text" id="value7"></td>
					<td colspan="2"><input type="text" id="value8"></td>
					<td colspan="2"><input type="text" id="value9"></td>
					<td colspan="2"><input type="text" id="value10"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="2" style="border: 2px; width: 30px;">6</td>
					<td colspan="8" id="stndpart" style="font-size: 9px;">제조일 인쇄 및 인쇄 상태 확인
					<input type="hidden" id="stnd1" style="width: 20%">
					<input type="hidden" id="stnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="value1"></td>
					<td colspan="2"><input type="text" id="value2"></td>
					<td colspan="2"><input type="text" id="value3"></td>
					<td colspan="2"><input type="text" id="value4"></td>
					<td colspan="2"><input type="text" id="value5"></td>
					<td colspan="2"><input type="text" id="value6"></td>
					<td colspan="2"><input type="text" id="value7"></td>
					<td colspan="2"><input type="text" id="value8"></td>
					<td colspan="2"><input type="text" id="value9"></td>
					<td colspan="2"><input type="text" id="value10"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="2" style="border: 2px; width: 30px;">7</td>
					<td colspan="3" rowspan="2">박스<br>상태</td>
					<td colspan="8" id="stndpart" style="font-size: 9px;">제품과 포장박스의 규격 동일여부
					<input type="hidden" id="stnd1" style="width: 20%">
					<input type="hidden" id="stnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="value1"></td>
					<td colspan="2"><input type="text" id="value2"></td>
					<td colspan="2"><input type="text" id="value3"></td>
					<td colspan="2"><input type="text" id="value4"></td>
					<td colspan="2"><input type="text" id="value5"></td>
					<td colspan="2"><input type="text" id="value6"></td>
					<td colspan="2"><input type="text" id="value7"></td>
					<td colspan="2"><input type="text" id="value8"></td>
					<td colspan="2"><input type="text" id="value9"></td>
					<td colspan="2"><input type="text" id="value10"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="2" style="border: 2px; width: 30px;">8</td>
					<td colspan="8" id="stndpart">테이핑 및 박스 포장 상태 확인
					<input type="hidden" id="stnd1" style="width: 20%">
					<input type="hidden" id="stnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="value1"></td>
					<td colspan="2"><input type="text" id="value2"></td>
					<td colspan="2"><input type="text" id="value3"></td>
					<td colspan="2"><input type="text" id="value4"></td>
					<td colspan="2"><input type="text" id="value5"></td>
					<td colspan="2"><input type="text" id="value6"></td>
					<td colspan="2"><input type="text" id="value7"></td>
					<td colspan="2"><input type="text" id="value8"></td>
					<td colspan="2"><input type="text" id="value9"></td>
					<td colspan="2"><input type="text" id="value10"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="2" style="border: 2px; width: 30px;">9</td>
					<td colspan="3">BarCode</td>
					<td colspan="8" id="stndpart" style="font-size: 2px;">종목과 동일하게 인쇄되어 있는지 확인
					<input type="hidden" id="stnd1" style="width: 20%">
					<input type="hidden" id="stnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="value1"></td>
					<td colspan="2"><input type="text" id="value2"></td>
					<td colspan="2"><input type="text" id="value3"></td>
					<td colspan="2"><input type="text" id="value4"></td>
					<td colspan="2"><input type="text" id="value5"></td>
					<td colspan="2"><input type="text" id="value6"></td>
					<td colspan="2"><input type="text" id="value7"></td>
					<td colspan="2"><input type="text" id="value8"></td>
					<td colspan="2"><input type="text" id="value9"></td>
					<td colspan="2"><input type="text" id="value10"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="2" style="border: 2px; width: 30px;">10</td>
					<td colspan="3" rowspan="7">포장<br>단위</td>
					<td colspan="8" id="stndpart">
						<input type="text" id="subunit1">&nbsp;&nbsp;
						<input type="text" id="stnd1" style="width: 20%">
						×
						<input type="text" id="stnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="value1"></td>
					<td colspan="2"><input type="text" id="value2"></td>
					<td colspan="2"><input type="text" id="value3"></td>
					<td colspan="2"><input type="text" id="value4"></td>
					<td colspan="2"><input type="text" id="value5"></td>
					<td colspan="2"><input type="text" id="value6"></td>
					<td colspan="2"><input type="text" id="value7"></td>
					<td colspan="2"><input type="text" id="value8"></td>
					<td colspan="2"><input type="text" id="value9"></td>
					<td colspan="2"><input type="text" id="value10"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="2" style="border: 2px; width: 30px;">11</td>
					<td colspan="8" id="stndpart">
						<input type="text" id="subunit1">&nbsp;&nbsp;
						<input type="text" id="stnd1" style="width: 20%">
						×
						<input type="text" id="stnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="value1"></td>
					<td colspan="2"><input type="text" id="value2"></td>
					<td colspan="2"><input type="text" id="value3"></td>
					<td colspan="2"><input type="text" id="value4"></td>
					<td colspan="2"><input type="text" id="value5"></td>
					<td colspan="2"><input type="text" id="value6"></td>
					<td colspan="2"><input type="text" id="value7"></td>
					<td colspan="2"><input type="text" id="value8"></td>
					<td colspan="2"><input type="text" id="value9"></td>
					<td colspan="2"><input type="text" id="value10"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="2" style="border: 2px; width: 30px;">12</td>
					<td colspan="8" id="stndpart">
						<input type="text" id="subunit1">&nbsp;&nbsp;
						<input type="text" id="stnd1" style="width: 20%">
						×
						<input type="text" id="stnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="value1"></td>
					<td colspan="2"><input type="text" id="value2"></td>
					<td colspan="2"><input type="text" id="value3"></td>
					<td colspan="2"><input type="text" id="value4"></td>
					<td colspan="2"><input type="text" id="value5"></td>
					<td colspan="2"><input type="text" id="value6"></td>
					<td colspan="2"><input type="text" id="value7"></td>
					<td colspan="2"><input type="text" id="value8"></td>
					<td colspan="2"><input type="text" id="value9"></td>
					<td colspan="2"><input type="text" id="value10"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="2" style="border: 2px; width: 30px;">13</td>
					<td colspan="8" id="stndpart">
						<input type="text" id="subunit1">&nbsp;&nbsp;
						<input type="text" id="stnd1" style="width: 20%">
						×
						<input type="text" id="stnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="value1"></td>
					<td colspan="2"><input type="text" id="value2"></td>
					<td colspan="2"><input type="text" id="value3"></td>
					<td colspan="2"><input type="text" id="value4"></td>
					<td colspan="2"><input type="text" id="value5"></td>
					<td colspan="2"><input type="text" id="value6"></td>
					<td colspan="2"><input type="text" id="value7"></td>
					<td colspan="2"><input type="text" id="value8"></td>
					<td colspan="2"><input type="text" id="value9"></td>
					<td colspan="2"><input type="text" id="value10"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="2" style="border: 2px; width: 30px;">14</td>
					<td colspan="8" id="stndpart">
						<input type="text" id="subunit1">&nbsp;&nbsp;
						<input type="text" id="stnd1" style="width: 20%">
						×
						<input type="text" id="stnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="value1"></td>
					<td colspan="2"><input type="text" id="value2"></td>
					<td colspan="2"><input type="text" id="value3"></td>
					<td colspan="2"><input type="text" id="value4"></td>
					<td colspan="2"><input type="text" id="value5"></td>
					<td colspan="2"><input type="text" id="value6"></td>
					<td colspan="2"><input type="text" id="value7"></td>
					<td colspan="2"><input type="text" id="value8"></td>
					<td colspan="2"><input type="text" id="value9"></td>
					<td colspan="2"><input type="text" id="value10"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="2" style="border: 2px; width: 30px;">15</td>
					<td colspan="8" id="stndpart">
						<input type="text" id="subunit1">&nbsp;&nbsp;
						<input type="text" id="stnd1" style="width: 20%">
						×
						<input type="text" id="stnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="value1"></td>
					<td colspan="2"><input type="text" id="value2"></td>
					<td colspan="2"><input type="text" id="value3"></td>
					<td colspan="2"><input type="text" id="value4"></td>
					<td colspan="2"><input type="text" id="value5"></td>
					<td colspan="2"><input type="text" id="value6"></td>
					<td colspan="2"><input type="text" id="value7"></td>
					<td colspan="2"><input type="text" id="value8"></td>
					<td colspan="2"><input type="text" id="value9"></td>
					<td colspan="2"><input type="text" id="value10"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="2" style="border: 2px; width: 30px;">16</td>
					<td colspan="8" id="stndpart">
						<input type="text" id="subunit1">&nbsp;&nbsp;
						<input type="text" id="stnd1" style="width: 20%">
						×
						<input type="text" id="stnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="value1"></td>
					<td colspan="2"><input type="text" id="value2"></td>
					<td colspan="2"><input type="text" id="value3"></td>
					<td colspan="2"><input type="text" id="value4"></td>
					<td colspan="2"><input type="text" id="value5"></td>
					<td colspan="2"><input type="text" id="value6"></td>
					<td colspan="2"><input type="text" id="value7"></td>
					<td colspan="2"><input type="text" id="value8"></td>
					<td colspan="2"><input type="text" id="value9"></td>
					<td colspan="2"><input type="text" id="value10"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="13" style="width: 20px !important;"><b>최종 검사결과</b></td>
					<td colspan="25">
						<input type="text" id="subresult">
					</td>
				</tr>
				<tr>
					<td colspan="5">비고</td>
					<td colspan="33">
						<textarea id="subprocessRemark" rows="3" cols="60"></textarea>
					</td>
				</tr>
			</tfoot>
		</table>
		<div class="footer">
			<button class="printBtn">인쇄하기</button>
		</div>
		</div>
		</div>
		<!-- END MAIN -->

<!-- END WRAPPER -->
<!-- Javascript -->
<script src="/js/qc/oqcOutputInspectPrint.js?v=<%=System.currentTimeMillis()%>"></script>