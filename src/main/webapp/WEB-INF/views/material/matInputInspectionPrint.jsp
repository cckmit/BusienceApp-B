<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
pageContext.setAttribute("newLineChar", "\n");
%>
<link href="/css/material/matInputInspectPrint.css" rel="stylesheet" />
<!-- MAIN -->
<%
request.setCharacterEncoding("UTF-8");
%>
<div class="main">
	<div style="height: 59px">
		<!-- matOrderPrint -->
		<div class="container">
			<div></div>
			<table class="table table-bordered"
				style="height: 100px; width: 870px; font-size: 12px; margin-top: 35px;">
				<tr>
					<td rowspan="4"
						style="width: 35px; text-align: center; border: 2px solid #444 !important;"><br>협력업체</td>
					<td style="text-align: center; border: 2px  solid #444 !important;">작 성</td>
					<td style="text-align: center; border: 2px solid #444 !important;">등 록</td>
					<td style="text-align: center; border: 2px solid #444 !important;">승 인</td>
					<td rowspan="2"
						style="width: 60px; height: 80px; text-align: center; border: 2px solid #444 !important;">
						<h1
							style="font-family: 돋움체; font-size: 28px; width: 300px; text-align: center; font-weight: bolder;">&nbsp;&nbsp;&nbsp;검사
							기준 / 성적서</h1>
						<br>
						<h5
							style="text-align: center; margin-top: -15px; font-size: 17px;">INSPECTION
							CRITERION / CERTIFICATE</h5>
					</td>
					<td rowspan="4" style="width: 35px; text-align: center; border: 2px solid #444 !important;"><br>결<br>
					<br>
					<br>재</td>
					<td style="text-align: center; border: 2px solid #444 !important;">작 성</td>
					<td style="text-align: center; border: 2px solid #444 !important;">검 토</td>
					<td style="text-align: center; border: 2px solid #444 !important;">승 인</td>
				</tr>
				<tr>
					<td style="width: 60px; height: 80px; text-align: center; border: 2px solid #444 !important;"></td>
					<td style="width: 60px; height: 80px; text-align: center; border: 2px solid #444 !important;"></td>
					<td style="width: 60px; height: 80px; text-align: center; border: 2px solid #444 !important;"></td>
					<td style="width: 60px; height: 80px; text-align: center; border: 2px solid #444 !important;"></td>
					<td style="width: 60px; height: 80px; text-align: center; border: 2px solid #444 !important;"></td>
					<td style="width: 60px; height: 80px; text-align: center; border: 2px solid #444 !important;"></td>
				</tr>
				<tr>
					<td style="border: 2px solid #444 !important;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/</td>
					<td style="border: 2px solid #444 !important;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/</td>
					<td style="border: 2px solid #444 !important;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/</td>
					<td style="border: 2px solid #444 !important;"></td>
					<td style="border: 2px solid #444 !important;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/</td>
					<td style="border: 2px solid #444 !important;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/</td>
					<td style="border: 2px solid #444 !important;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;/</td>
				</tr>
			</table>
			<table class="table table-bordered"
				style="margin-top: -22px; border: 2px solid #444; height: 100px; width: 870px; font-size: 12px;">
				<tr>
					<td colspan="3" style="text-align: center; border: 2px solid #444 !important;">품명</td>
					<td style="border: 2px solid #444 !important;"><input type="text" id="submatInspectItemName"
						style="border: none;" readonly></td>
					<td colspan="2" style="text-align: center; border: 2px solid #444 !important;">검사일자</td>
					<td colspan="1" style="border: 2px solid #444 !important;"><input type="text" id="submatInspectDate"
						readonly></td>

				</tr>
				<tr>
					<td colspan="3" style="text-align: center; border: 2px solid #444 !important;">수량</td>
					<td style="border: 2px solid #444 !important;"><input type="text" id="submatInspectQty" readonly></td>
					<td colspan="2" style="text-align: center; border: 2px solid #444 !important;">검사자</td>
					<td colspan="1" style="border: 2px solid #444 !important;"><input type="text" id="submatInspectWorker" readonly></td>
				</tr>
				<tr>
					<td colspan="3" style="text-align: center; border: 2px solid #444 !important;">검사기준</td>
					<td style="border: 2px solid #444 !important;">&nbsp;KS Q ISO 2859-1</td>
					<td colspan="2" style="text-align: center; border: 2px solid #444 !important;">공급업체</td>
					<td colspan="1" style="border: 2px solid #444 !important;"><input type="text" id="submatInspectCustomer"
						readonly></td>
				</tr>
				<tr>
					<td colspan="7" style="border: 2px solid #444 !important;"><textarea id="subinspectionText"
							name="inspectionText" rows="4" cols="92.5" style="resize: none;" readonly></textarea>
					</td>
				</tr>
			</table>
			<table class="table table-bordered"
				style="margin-top: -22px; height: 250px; width: 870px; font-size: 12px; text-align: center; border: 2px solid #444 !important;">
				<tr>
					<td rowspan="2" style="border: 2px solid #444 !important;">번호</td>
					<td rowspan="2" style="border: 2px solid #444 !important;">검사항목</td>
					<td rowspan="2" style="border: 2px solid #444 !important;">검사규격</td>
					<td colspan="5" style="border: 2px solid #444 !important;">측정 DATA</td>
					<td rowspan="2" style="border: 2px solid #444 !important;">판정</td>
				</tr>
				<tr>
					<td style="border: 2px solid #444 !important;">X1</td>
					<td style="border: 2px solid #444 !important;">X2</td>
					<td style="border: 2px solid #444 !important;">X3</td>
					<td style="border: 2px solid #444 !important;">X4</td>
					<td style="border: 2px solid #444 !important;">X5</td>
				</tr>
				<tr>
					<td style="border: 2px; width: 30px; border: 2px solid #444 !important;">1</td>
					<td style="width: 100px; border: 2px solid #444 !important;">성상</td>
					<td style="width: 300px; border: 2px solid #444 !important;">청결하고 자극성이 없으며 이물이 함유되어 있지 않고 섬유의 탈락이
						거의 없는 흰색의 포로서 냄새는 없다.</td>
					<td style="width: 40px; border: 2px solid #444 !important;">
						<input style="width: 60px; text-align: center;" type="text" name="subInspect_Value_1[]" id="check1_val1"></td>
					<td style="width: 40px; border: 2px solid #444 !important;">
						<input style="width: 60px; text-align: center;" type="text" name="subInspect_Value_2[]" id="check1_val2"></td>
					<td style="width: 40px; border: 2px solid #444 !important;">
						<input style="width: 60px; text-align: center;" type="text" name="subInspect_Value_3[]" id="check1_val3"></td>
					<td style="width: 40px; border: 2px solid #444 !important;">
						<input style="width: 60px; text-align: center;" type="text" name="subInspect_Value_4[]" id="check1_val4"></td>
					<td style="width: 40px; border: 2px solid #444 !important;">
						<input style="width: 60px; text-align: center;" type="text" name="subInspect_Value_5[]" id="check1_val5"></td>
					<td style="width: 50px; border: 2px solid #444 !important;">
						<input type="text" name="substatus[]" style="width: 50px; text-align: center;" readonly></td>
				</tr>
				<tr>
					<td style="border: 2px solid #444 !important;">2</td>
					<td style="border: 2px solid #444 !important;">색소</td>
					<td style="border: 2px solid #444 !important;">부직포의 순도시험 중 1)색소에 따라 시험한다.</td>
					<td colspan="5" style="border: 2px solid #444 !important;">
						<input style="width: 400px; text-align: center;" type="text" name="subInspect_Value_1[]" id="check2_val1" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_2[]" id="check2_val2" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_3[]" id="check2_val3" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_4[]" id="check2_val4" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_5[]" id="check2_val5" readonly></td>
					<td style="width: 50px; border: 2px solid #444 !important;">
						<input type="text" name="substatus[]" style="width: 50px; text-align: center;" readonly>
					</td>
				</tr>
				<tr>
					<td style="border: 2px solid #444 !important;">3</td>
					<td style="border: 2px solid #444 !important;">산 또는 알칼리</td>
					<td style="border: 2px solid #444 !important;">부직포의 순도시험 중 2)산 또는 알칼리에 따라 시험한다.</td>
					<td colspan="5" style="border: 2px solid #444 !important;">
						<input style="width: 400px; text-align: center;" type="text" name="subInspect_Value_1[]" id="check3_val1" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_2[]" id="check3_val2" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_3[]" id="check3_val3" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_4[]" id="check3_val4" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_5[]" id="check3_val5" readonly>
					</td>
					<td style="width: 50px; border: 2px solid #444 !important;">
						<input type="text" name="substatus[]" style="width: 50px; text-align: center;" readonly>
					</td>
				</tr>
				<tr>
					<td style="border: 1px; border: 2px solid #444 !important;">4</td>
					<td style="border: 2px solid #444 !important;">형광증백제</td>
					<td style="border: 2px solid #444 !important;">부직포의 순도시험 중 3)형광증백제에 따라 시험한다.</td>
					<td colspan="5" style="border: 2px solid #444 !important;">
						<input style="width: 400px; text-align: center;" type="text" name="subInspect_Value_1[]" id="check4_val1" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_2[]" id="check4_val2" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_3[]" id="check4_val3" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_4[]" id="check4_val4" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_5[]" id="check4_val5" readonly>
					</td>
					<td style="width: 50px; border: 2px solid #444 !important;">
						<input type="text" name="substatus[]" style="width: 50px; text-align: center;" readonly>
					</td>
				</tr>
				<tr>
					<td style="border: 2px solid #444 !important;">5</td>
					<td style="border: 2px solid #444 !important;">회분</td>
					<td style="border: 2px solid #444 !important;">부직포의 회분에 따라 시험한다.</td>
					<td colspan="5" style="border: 2px solid #444 !important;">
						<input style="width: 400px; text-align: center;" type="text" name="subInspect_Value_1[]" id="check5_val1" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_2[]" id="check5_val2" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_3[]" id="check5_val3" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_4[]" id="check5_val4" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_5[]" id="check5_val5" readonly>
					</td>
					<td style="width: 50px; border: 2px solid #444 !important;">
						<input type="text" name="substatus[]" style="width: 50px; text-align: center;" readonly>
					</td>
				</tr>
				<tr>
					<td style="border: 2px solid #444 !important;">6</td>
					<td style="border: 2px solid #444 !important;">포름알데히드</td>
					<td style="border: 2px solid #444 !important;">부직포의 포름알데히드에 따라 시험한다.</td>
					<td colspan="5" style="border: 2px solid #444 !important;">
						<input style="width: 400px; text-align: center;" type="text" name="subInspect_Value_1[]" id="check6_val1" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_2[]" id="check6_val2" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_3[]" id="check6_val3" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_4[]" id="check6_val4" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_5[]" id="check6_val5" readonly>
					</td>
					<td style="width: 50px; border: 2px solid #444 !important;">
						<input type="text" name="substatus[]" style="width: 50px; text-align: center;" readonly>
					</td>
				</tr>
				<tr>
					<td style="border: 2px solid #444 !important;">7</td>
					<td style="border: 2px solid #444 !important;">강도</td>
					<td style="border: 2px solid #444 !important;">부직포의 강도에 따라 시험한다.</td>
					<td colspan="5" style="border: 2px solid #444 !important;">
						<input style="width: 400px; text-align: center;" type="text" name="subInspect_Value_1[]" id="check7_val1" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_2[]" id="check7_val2" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_3[]" id="check7_val3" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_4[]" id="check7_val4" readonly> 
						<input style="width: 60px; text-align: center;" type="hidden" name="subInspect_Value_5[]" id="check7_val5" readonly>
					</td>
					<td style="width: 50px; border: 2px solid #444 !important;">
						<input type="text" name="substatus[]" style="width: 50px; text-align: center;" readonly>
					</td>
				</tr>
				<tr>
					<td style="border: 2px solid #444 !important;">8</td>
					<td style="border: 2px solid #444 !important;">치수(A)</td>
					<td style="border: 2px solid #444 !important;">
						<input type="text" name="subInspect_STND_1[]" style="width: 50px; text-align: center;" readonly> ± <input type="text" name="subInspect_STND_2[]" style="width: 80px; text-align: center;" readonly>
					</td>
					<td style="width: 40px; border: 2px solid #444 !important;">
						<input style="width: 60px; text-align: center;"
						type="text" name="subInspect_Value_1[]" id="check8_val1" readonly>
					</td>
					<td style="width: 40px; border: 2px solid #444 !important;">
						<input style="width: 60px; text-align: center;"
						type="text" name="subInspect_Value_2[]" id="check8_val2" readonly>
					</td>
					<td style="width: 40px; border: 2px solid #444 !important;">
						<input style="width: 60px; text-align: center;"
						type="text" name="subInspect_Value_3[]" id="check8_val3" readonly>
					</td>
					<td style="width: 40px; border: 2px solid #444 !important;">
						<input style="width: 60px; text-align: center;"
						type="text" name="subInspect_Value_4[]" id="check8_val4" readonly>
					</td>
					<td style="width: 40px; border: 2px solid #444 !important;"> 
						<input style="width: 60px; text-align: center;"
						type="text" name="subInspect_Value_5[]" id="check8_val5" readonly>
					</td>
					<td style="width: 50px; border: 2px solid #444 !important;">
						<input type="text" name="substatus[]" style="width: 50px; text-align: center;" readonly>
					</td>
				</tr>
				<tr>
					<td style="border: 2px solid #444 !important;">9</td>
					<td style="border: 2px solid #444 !important;">중량</td>
					<td style="border: 2px solid #444 !important;">
						<input type="text" name="subInspect_STND_1[]" style="width: 50px; text-align: center;" readonly> ± <input type="text" name="subInspect_STND_2[]" style="width: 80px; text-align: center;" readonly></td>
					<td style="width: 40px; border: 2px solid #444 !important;">
						<input style="width: 60px; text-align: center;"
						type="text" name="subInspect_Value_1[]" id="check9_val1" readonly>
					</td>
					<td style="width: 40px; border: 2px solid #444 !important;">
						<input style="width: 60px; text-align: center;"
						type="text" name="subInspect_Value_2[]" id="check9_val2" readonly>
					</td>
					<td style="width: 40px; border: 2px solid #444 !important;">
						<input style="width: 60px; text-align: center;"
						type="text" name="subInspect_Value_3[]" id="check9_val3" readonly>
					</td>
					<td style="width: 40px; border: 2px solid #444 !important;">
						<input style="width: 60px; text-align: center;"
						type="text" name="subInspect_Value_4[]" id="check9_val4" readonly>
					</td>
					<td style="width: 40px; border: 2px solid #444 !important;">
						<input style="width: 60px; text-align: center;"
						type="text" name="subInspect_Value_5[]" id="check9_val5" readonly>
					</td>
					<td style="width: 50px; border: 2px solid #444 !important;">
						<input type="text" name="substatus[]" style="width: 50px; text-align: center;" readonly>
					</td>
				</tr>
				<tr>
					<td style="border: 2px solid #444 !important;">10</td>
					<td style="border: 2px solid #444 !important;">두께</td>
					<td style="border: 2px solid #444 !important;">
						<input type="text" name="subInspect_STND_1[]" style="width: 50px; text-align: center;" readonly> ± <input type="text" name="subInspect_STND_2[]" style="width: 80px; text-align: center;" readonly>
					</td>
					<td style="width: 40px; border: 2px solid #444 !important;">
						<input style="width: 60px; text-align: center;"
						type="text" name="subInspect_Value_1[]" id="check10_val1" readonly>
					</td>
					<td style="width: 40px; border: 2px solid #444 !important;">
						<input style="width: 60px; text-align: center;;"
						type="text" name="subInspect_Value_2[]" id="check10_val2" readonly>
					</td>
					<td style="width: 40px; border: 2px solid #444 !important;">
						<input style="width: 60px; text-align: center;"
						type="text" name="subInspect_Value_3[]" id="check10_val3" readonly>
					</td>
					<td style="width: 40px; border: 2px solid #444 !important;">
						<input style="width: 60px; text-align: center;"
						type="text" name="subInspect_Value_4[]" id="check10_val4" readonly>
					</td>
					<td style="width: 40px; border: 2px solid #444 !important;">
						<input style="width: 60px; text-align: center;"
						type="text" name="subInspect_Value_5[]" id="check10_val5" readonly>
					</td>
					<td style="width: 50px; border: 2px solid #444 !important;">
						<input type="text" name="substatus[]" style="width: 50px; text-align: center;" readonly>
					</td>
				</tr>
			</table>
			<table class="table table-bordered"
				style="margin-top: -22px; height: 100px; width: 870px; font-size: 12px; text-align: center; border: 2px solid #444 !important;">
				<tr>
					<td colspan="4" style="width: 97px; align-contents: center; border: 2px solid #444 !important; "><p>비고</p></td>
					<td colspan="4" style="border: 2px solid #444 !important;"><textarea
							id="subinspectionRemark" name="subinspectionRemark" rows="3" cols="60"
							style="resize: none;" readonly></textarea></td>
				</tr>
			</table>
			<br> <br> <br>
			<div class="footer">
				<button class="printBtn" style="text-align: center;">인쇄하기</button>
			</div>
		</div>
		<!-- matOrderSub -->
	</div>
	<!-- END MAIN -->
</div>
<script
	src="/js/material/matInputInspectionPrint.js?v=<%=System.currentTimeMillis()%>"></script>