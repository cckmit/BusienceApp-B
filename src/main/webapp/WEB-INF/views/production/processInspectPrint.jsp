<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="/css/production/processInspectPrint.css" rel="stylesheet" />

<div class="main">
	<div class="container">
	<br><br><br>
		<table class="tg table table-bordered"">
			<thead>
				<tr>
					<td rowspan="3" colspan="5"></td>
					<td colspan="10" rowspan="3" style="height: 40px !important;">
						<h2 style="font-family: '고딕'; font-weight: 900; letter-spacing: -0.10em;">공정검사일지</h2>
					</td>
					<td colspan="3"  style="height: 15px !important;">작 성</td>
					<td colspan="3">검 토</td>
					<td colspan="3">승 인</td>
				</tr>
				<tr>
					<td colspan="3" style="height: 70px !important;"></td>
					<td colspan="3"></td>
					<td colspan="3"></td>
				</tr>
				<tr>
					<td colspan="3" style="height: 15px !important;">/</td>
					<td colspan="3">/</td>
					<td colspan="3">/</td>
				</tr>
				<tr>
					<td colspan="5">생산라인</td>
					<td colspan="7"><input type="text" id="subproInspectEquipName" readonly></td>
					<td colspan="5">제품명</td>
					<td colspan="7"><input type="text" id="subproInspectItemName" readonly></td>
				</tr>
				<tr>
					<td colspan="5">생산일자/검사일자</td>
					<td colspan="7"><input type="text" id="subproductionDate" readonly>&nbsp;/&nbsp; <input type="text" id="subprocessDate" readonly></td>
					<td colspan="5">검사자</td>
					<td colspan="7"><input type="text" id="subpqcWorker" readonly></td>
				</tr>
				<tr>
					<td colspan="5">검사기준/검사수량</td>
					<td colspan="7">KS Q ISO 2859-1</td>
					<td colspan="5">시료수</td>
					<td colspan="7"><input type="text" id="subprocessQty">개 /&nbsp;&nbsp;<input type="text" id="subitemColorType">
					</td>
				</tr>
			</thead>	
			<tbody>
				<tr>
					<td colspan="5">검사항목</td>
					<td colspan="7">검사기준</td>
					<td colspan="2">X1</td>
					<td colspan="2">X2</td>
					<td colspan="2">X3</td>
					<td colspan="2">X4</td>
					<td colspan="2">X5</td>
					<td colspan="2">결과</td>
				</tr>
				<tr>
					<td colspan="5">성상</td>
					<td colspan="7" style="font-size: 10px;">(흰색, 검정색)의 3단 가로접이식 본체에 코편이 있고, 양측면에 (흰색, 검정색)의
					끈이 있는 부직포 마스크
					<input type="hidden" id="substnd1" style="width: 20%">
					<input type="hidden" id="substnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="subvalue1"></td>
					<td colspan="2"><input type="text" id="subvalue2"></td>
					<td colspan="2"><input type="text" id="subvalue3"></td>
					<td colspan="2"><input type="text" id="subvalue4"></td>
					<td colspan="2"><input type="text" id="subvalue5"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td rowspan="4" colspan="2">형상</td>
					<td colspan="3">가로</td>
					<td colspan="7">
						<input type="text" id="substnd1" style="width: 20%">
						±
						<input type="text" id="substnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="subvalue1"></td>
					<td colspan="2"><input type="text" id="subvalue2"></td>
					<td colspan="2"><input type="text" id="subvalue3"></td>
					<td colspan="2"><input type="text" id="subvalue4"></td>
					<td colspan="2"><input type="text" id="subvalue5"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="3">세로</td>
					<td colspan="7">
						<input type="text" id="substnd1" style="width: 20%">
						±
						<input type="text" id="substnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="subvalue1"></td>
					<td colspan="2"><input type="text" id="subvalue2"></td>
					<td colspan="2"><input type="text" id="subvalue3"></td>
					<td colspan="2"><input type="text" id="subvalue4"></td>
					<td colspan="2"><input type="text" id="subvalue5"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="3">머리끈(좌)</td>
					<td colspan="7">
						<input type="text" id="substnd1" style="width: 20%">
						±
						<input type="text" id="substnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="subvalue1"></td>
					<td colspan="2"><input type="text" id="subvalue2"></td>
					<td colspan="2"><input type="text" id="subvalue3"></td>
					<td colspan="2"><input type="text" id="subvalue4"></td>
					<td colspan="2"><input type="text" id="subvalue5"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="3">머리끈(우)</td>
					<td colspan="7">
						<input type="text" id="substnd1" style="width: 20%">
						±
						<input type="text" id="substnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="subvalue1"></td>
					<td colspan="2"><input type="text" id="subvalue2"></td>
					<td colspan="2"><input type="text" id="subvalue3"></td>
					<td colspan="2"><input type="text" id="subvalue4"></td>
					<td colspan="2"><input type="text" id="subvalue5"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="5">코편</td>
					<td colspan="7">
						<input type="text" id="substnd1" style="width: 20%">
						±
						<input type="text" id="substnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="subvalue1"></td>
					<td colspan="2"><input type="text" id="subvalue2"></td>
					<td colspan="2"><input type="text" id="subvalue3"></td>
					<td colspan="2"><input type="text" id="subvalue4"></td>
					<td colspan="2"><input type="text" id="subvalue5"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="5">중량</td>
					<td colspan="7">
						<input type="text" id="substnd1" style="width: 20%">
						±
						<input type="text" id="substnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="subvalue1"></td>
					<td colspan="2"><input type="text" id="subvalue2"></td>
					<td colspan="2"><input type="text" id="subvalue3"></td>
					<td colspan="2"><input type="text" id="subvalue4"></td>
					<td colspan="2"><input type="text" id="subvalue5"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="5">인장강도</td>
					<td colspan="7" style="font-size: 10px;">
						인장시험기(30 cm/분)로 잡아당겨
						머리끈과 마스크의 접착부위가 절단될
						때까지의 최대 하중을 측정할 때, 10N
						<input type="hidden" id="substnd1" style="width: 20%">
						<input type="hidden" id="substnd2" style="width: 20%">
					</td>
					<td colspan="2"><input type="text" id="subvalue1"></td>
					<td colspan="2"><input type="text" id="subvalue2"></td>
					<td colspan="2"><input type="text" id="subvalue3"></td>
					<td colspan="2"><input type="text" id="subvalue4"></td>
					<td colspan="2"><input type="text" id="subvalue5"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="16"><b>최종 검사결과</b></td>
					<td colspan="8"><input type="text" id="subresult"></td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="5">비고</td>
					<td colspan="19">
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
<script
	src="/js/production/processInspectionPrint.js?v=<%=System.currentTimeMillis()%>"></script>