<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="/css/qc/itemPackingInspectPrint.css" rel="stylesheet" />

<div class="main">
	<div class="container">
			<!-- 그리드 생성 장소 -->
			<table class="tg table table-bordered">
				<thead>
				<tr>
					<td rowspan="3" colspan="5"></td>
					<td colspan="10" rowspan="3" style="height: 30px !important;">
						<p style="font-family: '고딕'; font-weight: 900; letter-spacing: -0.03em; font-size: 26px;">완제품포장 검사성적서</p>
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
					<td colspan="5">LOT NO</td>
					<td colspan="7"><input type="text" id="subitemInspectLotNo" readonly></td>
					<td colspan="5">제품명</td>
					<td colspan="7"><input type="text" id="subitemInspectItemName" readonly></td>
				</tr>
				<tr>
					<td colspan="5">생산일자/검사일자</td>
					<td colspan="7"><input type="text" id="subitemPackingDate" readonly>&nbsp;/&nbsp; <input type="text" id="subitemInspectDate" readonly></td>
					<td colspan="5">검사자</td>
					<td colspan="7"><input type="text" id="subitemPackingWorkerList" readonly></td>
				</tr>
				<tr>
					<td colspan="5">검사기준/검사수량</td>
					<td colspan="7">KS Q ISO 2859-1</td>
					<td colspan="5">시료수</td>
					<td colspan="7"><input type="text" id="subitemInspectQty">개 /&nbsp;&nbsp;<input type="text" id="subitemColorType">
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
					<td colspan="7">마스크 좌우 상단, 우 하단에 글자 등 새김이 있는 (흰색, 검정색)의 2단 세로접이식 본체에 표면이 있고,
					양 측면에 (흰색, 검정색)의 끈이 있는 부직포 마스크
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
					<td colspan="5">순도시험</td>
					<td colspan="17">
					공인기관 시험성적서 참조
					</td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="5">분진포집효율</td>
					<td colspan="17">
					공인기관 시험성적서 참조
					</td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="5">안면부 흡기저항</td>
					<td colspan="17">
					공인기관 시험성적서 참조
					</td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="5">이어밴드<br> 접합부 인장강도</td>
					<td colspan="17">
					공인기관 시험성적서 참조
					</td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="24">외관검사</td>
				</tr>
				<tr>
					<td colspan="5">패키지상태</td>
					<td colspan="11">
					밀봉상태 및 인쇄상태 확인
					</td>
					<td colspan="6"><input type="text" id="subitemPackgeStatus"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="5">박스상태</td>
					<td colspan="11">
					테이핑 및 박스포장 상태 확인
					</td>
					<td colspan="6"><input type="text" id="subitemBoxStatus"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
				<tr>
					<td colspan="5">포장단위</td>
					<td colspan="11">
						<input type="text" id="subunit1">
					<!-- 보건용, 비말용 -->
					</td> 
					<td colspan="6"><input type="text" id="subitemPacking"></td>
					<td colspan="2"><input type="text" id="substatus"></td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="16"><b>최종 검사결과</b></td>
					<td colspan="8">
						<input type="text" id="subresult">
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
<script src="/js/qc/itemPackingInspectPrint.js?v=<%=System.currentTimeMillis()%>"></script>

