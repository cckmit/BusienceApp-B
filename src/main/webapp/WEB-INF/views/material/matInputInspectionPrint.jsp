<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link href="/css/material/matInputInspectPrint.css" rel="stylesheet" />
<!-- MAIN -->
<div class="main">
	<!-- matOrderPrint -->
	<div class="container">
		<table class="tg table table-bordered">
			<thead>
				<tr style="height: 40px;">
					<td rowspan="3">협<br>력<br>업<br>체</td>
					<td colspan="2">작 성</td>
					<td colspan="2">등 록</td>
					<td colspan="2">승 인</td>
					<td colspan="8" rowspan="2">
						<h3 style="font-family: 돋움체; font-weight: bolder;">검사 기준 / 성적서</h3>
						<h6 style="font-size: 11px">INSPECTION CRITERION / CERTIFICATE</h6>
					</td>
					<td rowspan="3">결<br><br><br>재</td>
					<td colspan="2">작 성</td>
					<td colspan="2">검 토</td>
					<td colspan="2">승 인</td>
				</tr>
				<tr>
					<td colspan="2"></td>
					<td colspan="2"></td>
					<td colspan="2"></td>
					<td colspan="2"></td>
					<td colspan="2"></td>
					<td colspan="2"></td>
				</tr>
				<tr style="height: 40px;">
					<td colspan="2">/</td>
					<td colspan="2">/</td>
					<td colspan="2">/</td>
					<td colspan="8"><input type="checkbox" checked onclick="return false;" style="width: 30px">입고</td>
					<td colspan="2">/</td>
					<td colspan="2">/</td>
					<td colspan="2">/</td>
				</tr>
				<tr>
					<td colspan="3">품명</td>
					<td colspan="8"><input type="text" id="submatInspectItemName" readonly></td>
					<td colspan="3">검사일자</td>
					<td colspan="8"><input type="text" id="submatInspectDate" readonly></td>
				</tr>
				<tr>
					<td colspan="3">수량</td>
					<td colspan="8"><input type="text" id="submatInspectQty" readonly></td>
					<td colspan="3">검사자</td>
					<td colspan="8"><input type="text" id="submatInspectWorker" readonly></td>
				</tr>
				<tr>
					<td colspan="3">검사기준</td>
					<td colspan="8">KS Q ISO 2859-1</td>
					<td colspan="3">공급업체</td>
					<td colspan="8">
						<input type="text" id="submatInspectCustomer" readonly>
					</td>
				</tr>
				<tr>
					<td colspan="22">
						<textarea id="subinspectionText" name="inspectionText" rows="4" cols="92.5" readonly></textarea>
					</td>
				</tr>
			</thead>			
			<tbody>
				<tr>
					<td rowspan="2">번호</td>
					<td colspan="2" rowspan="2">검사항목</td>
					<td colspan="7" rowspan="2">검사규격</td>
					<td colspan="10">측정 DATA</td>
					<td colspan="2" rowspan="2">판정</td>
				</tr>
				<tr>
					<td colspan="2">X1</td>
					<td colspan="2">X2</td>
					<td colspan="2">X3</td>
					<td colspan="2">X4</td>
					<td colspan="2">X5</td>
				</tr>
				<tr>
					<td>1</td>
					<td colspan="2">성상</td>
					<td colspan="7">청결하고 자극성이 없으며 이물이 함유되어 있지 않고 섬유의 탈락이
						거의 없는 흰색의 포로서 냄새는 없다.</td>
					<td colspan="2">
						<input  type="text" name="subInspect_Value_1[]" id="check1_val1"></td>
					<td colspan="2">
						<input  type="text" name="subInspect_Value_2[]" id="check1_val2"></td>
					<td colspan="2">
						<input  type="text" name="subInspect_Value_3[]" id="check1_val3"></td>
					<td colspan="2">
						<input  type="text" name="subInspect_Value_4[]" id="check1_val4"></td>
					<td colspan="2">
						<input  type="text" name="subInspect_Value_5[]" id="check1_val5"></td>
					<td colspan="2">
						<input type="text" name="substatus[]" style="width: 20px;" readonly></td>
				</tr>
				<tr>
					<td>2</td>
					<td colspan="2">색소</td>
					<td colspan="7">부직포의 순도시험 중 1)색소에 따라 시험한다.</td>
					<td colspan="10">
						<input type="text" name="subInspect_Value_1[]" id="check2_val1" readonly> 
						<input type="hidden" name="subInspect_Value_2[]" id="check2_val2" readonly> 
						<input type="hidden" name="subInspect_Value_3[]" id="check2_val3" readonly> 
						<input type="hidden" name="subInspect_Value_4[]" id="check2_val4" readonly> 
						<input type="hidden" name="subInspect_Value_5[]" id="check2_val5" readonly></td>
					<td colspan="2">
						<input type="text" name="substatus[]" style="width: 20px;" readonly>
					</td>
				</tr>
				<tr>
					<td>3</td>
					<td colspan="2">산 또는 알칼리</td>
					<td colspan="7">부직포의 순도시험 중 2)산 또는 알칼리에 따라 시험한다.</td>
					<td colspan="10">
						<input type="text" name="subInspect_Value_1[]" id="check3_val1" readonly> 
						<input type="hidden" name="subInspect_Value_2[]" id="check3_val2" readonly> 
						<input type="hidden" name="subInspect_Value_3[]" id="check3_val3" readonly> 
						<input type="hidden" name="subInspect_Value_4[]" id="check3_val4" readonly> 
						<input type="hidden" name="subInspect_Value_5[]" id="check3_val5" readonly>
					</td>
					<td colspan="2">
						<input type="text" name="substatus[]" style="width: 20px;" readonly>
					</td>
				</tr>
				<tr>
					<td>4</td>
					<td colspan="2">형광증백제</td>
					<td colspan="7">부직포의 순도시험 중 3)형광증백제에 따라 시험한다.</td>
					<td colspan="10">
						<input type="text" name="subInspect_Value_1[]" id="check4_val1" readonly> 
						<input type="hidden" name="subInspect_Value_2[]" id="check4_val2" readonly> 
						<input type="hidden" name="subInspect_Value_3[]" id="check4_val3" readonly> 
						<input type="hidden" name="subInspect_Value_4[]" id="check4_val4" readonly> 
						<input type="hidden" name="subInspect_Value_5[]" id="check4_val5" readonly>
					</td>
					<td colspan="2">
						<input type="text" name="substatus[]" style="width: 20px;" readonly>
					</td>
				</tr>
				<tr>
					<td>5</td>
					<td colspan="2">회분</td>
					<td colspan="7">부직포의 회분에 따라 시험한다.</td>
					<td colspan="10">
						<input type="text" name="subInspect_Value_1[]" id="check5_val1" readonly> 
						<input type="hidden" name="subInspect_Value_2[]" id="check5_val2" readonly> 
						<input type="hidden" name="subInspect_Value_3[]" id="check5_val3" readonly> 
						<input type="hidden" name="subInspect_Value_4[]" id="check5_val4" readonly> 
						<input type="hidden" name="subInspect_Value_5[]" id="check5_val5" readonly>
					</td>
					<td colspan="2">
						<input type="text" name="substatus[]" style="width: 20px;" readonly>
					</td>
				</tr>
				<tr>
					<td>6</td>
					<td colspan="2">포름알데히드</td>
					<td colspan="7">부직포의 포름알데히드에 따라 시험한다.</td>
					<td colspan="10">
						<input type="text" name="subInspect_Value_1[]" id="check6_val1" readonly> 
						<input type="hidden" name="subInspect_Value_2[]" id="check6_val2" readonly> 
						<input type="hidden" name="subInspect_Value_3[]" id="check6_val3" readonly> 
						<input type="hidden" name="subInspect_Value_4[]" id="check6_val4" readonly> 
						<input type="hidden" name="subInspect_Value_5[]" id="check6_val5" readonly>
					</td>
					<td colspan="2">
						<input type="text" name="substatus[]" style="width: 20px;" readonly>
					</td>
				</tr>
				<tr>
					<td>7</td>
					<td colspan="2">강도</td>
					<td colspan="7">부직포의 강도에 따라 시험한다.</td>
					<td colspan="10">
						<input type="text" name="subInspect_Value_1[]" id="check7_val1" readonly> 
						<input type="hidden" name="subInspect_Value_2[]" id="check7_val2" readonly> 
						<input type="hidden" name="subInspect_Value_3[]" id="check7_val3" readonly> 
						<input type="hidden" name="subInspect_Value_4[]" id="check7_val4" readonly> 
						<input type="hidden" name="subInspect_Value_5[]" id="check7_val5" readonly>
					</td>
					<td colspan="2">
						<input type="text" name="substatus[]" style="width: 20px;" readonly>
					</td>
				</tr>
				<tr>
					<td>8</td>
					<td colspan="2">치수(A)</td>
					<td colspan="7">
						<input type="text" name="subInspect_STND_1[]" style="width: 20%" readonly>
						±
						<input type="text" name="subInspect_STND_2[]" style="width: 20%" readonly>
					</td>
					<td colspan="2">
						<input type="text" name="subInspect_Value_1[]" id="check8_val1" readonly>
					</td>
					<td colspan="2">
						<input type="text" name="subInspect_Value_2[]" id="check8_val2" readonly>
					</td>
					<td colspan="2">
						<input type="text" name="subInspect_Value_3[]" id="check8_val3" readonly>
					</td>
					<td colspan="2">
						<input type="text" name="subInspect_Value_4[]" id="check8_val4" readonly>
					</td>
					<td colspan="2"> 
						<input type="text" name="subInspect_Value_5[]" id="check8_val5" readonly>
					</td>
					<td colspan="2">
						<input type="text" name="substatus[]" style="width: 20px;" readonly>
					</td>
				</tr>
				<tr>
					<td>9</td>
					<td colspan="2">중량</td>
					<td colspan="7">
						<input type="text" name="subInspect_STND_1[]" style="width: 20%" readonly>
						±
						<input type="text" name="subInspect_STND_2[]" style="width: 20%" readonly>
					</td>
					<td colspan="2">
						<input 
						type="text" name="subInspect_Value_1[]" id="check9_val1" readonly>
					</td>
					<td colspan="2">
						<input 
						type="text" name="subInspect_Value_2[]" id="check9_val2" readonly>
					</td>
					<td colspan="2">
						<input 
						type="text" name="subInspect_Value_3[]" id="check9_val3" readonly>
					</td>
					<td colspan="2">
						<input 
						type="text" name="subInspect_Value_4[]" id="check9_val4" readonly>
					</td>
					<td colspan="2">
						<input 
						type="text" name="subInspect_Value_5[]" id="check9_val5" readonly>
					</td>
					<td colspan="2">
						<input type="text" name="substatus[]" style="width: 20px;" readonly>
					</td>
				</tr>
				<tr>
					<td>10</td>
					<td colspan="2">두께</td>
					<td colspan="7">
						<input type="text" name="subInspect_STND_1[]" style="width: 20%" readonly>
						±
						<input type="text" name="subInspect_STND_2[]" style="width: 20%" readonly>
					</td>
					<td colspan="2">
						<input type="text" name="subInspect_Value_1[]" id="check10_val1" readonly>
					</td>
					<td colspan="2">
						<input type="text" name="subInspect_Value_2[]" id="check10_val2" readonly>
					</td>
					<td colspan="2">
						<input type="text" name="subInspect_Value_3[]" id="check10_val3" readonly>
					</td>
					<td colspan="2">
						<input type="text" name="subInspect_Value_4[]" id="check10_val4" readonly>
					</td>
					<td colspan="2">
						<input type="text" name="subInspect_Value_5[]" id="check10_val5" readonly>
					</td>
					<td colspan="2">
						<input type="text" name="substatus[]" style="width: 20px;" readonly>
					</td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="3">비고</td>
					<td colspan="19">
						<textarea id="subinspectionRemark" name="subinspectionRemark" rows="3" cols="60" readonly>
						</textarea>
					</td>
				</tr>
			</tfoot>
		</table>
		<div class="footer">
			<button class="printBtn">인쇄하기</button>
		</div>
	</div>
	<!-- END MAIN -->
</div>
<script
	src="/js/material/matInputInspectionPrint.js?v=<%=System.currentTimeMillis()%>"></script>