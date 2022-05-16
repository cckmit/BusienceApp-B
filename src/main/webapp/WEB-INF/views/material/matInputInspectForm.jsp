<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<style>
.tabulator {
	font-size: 13px;
}

html, body {
	margin: 0;
	height: 100%;
	overflow: hidden;
}

#totalDiv {
	margin: 0;
	height: 100%;
	overflow: hidden;
}

td {
 border: 1px solid black;
}

</style>
</head>
<body>
	<div class="soloView1">
		<!-- MAIN -->
		<div class="main">
			<!-- 그리드 생성 장소 -->
			<div class="master-table" style="padding-left: 10px;">
				<table class="table table-bordered" style="padding-left: 10px; height: 100px; width: 870px; font-size: 12px;">
					<tr>
						<td colspan="2">품명</td>
						<td><input type="text" id="matInspectItemName" readonly></td>
						<td colspan="2">검사일자</td>
						<td colspan="2"><input type="text" id="matInspectDate" readonly></td>
						
					</tr>
					<tr>
						<td colspan="2">수량</td>
						<td><input type="text" id="matInspectQty" readonly></td>
						<td colspan="2">검사자</td>
						<td colspan="2"><input type="text" id="matInspectWorker"></td>
					</tr>
					<tr>
						<td colspan="2">검사기준</td>
						<td>KS Q ISO 2859-1</td>
						<td colspan="2">공급업체</td>
						<td colspan="2"><input type="text" id="matInspectCustomer" readonly></td>
					</tr>
					<tr>
						<td colspan="2" style="width: 100px; font-size: 13px;" width="80"
							class="col1"></td>
						<td colspan="4" class="col2">
							<textarea id="inspectionText" name="inspectionText" rows="4" cols="94" style="resize: none;"></textarea>
						</td>
					</tr>
				</table>
				<table class="table table-bordered"
					style="padding-left: 10px; margin-top: -17px; height: 250px; width: 870px; font-size: 12px; text-align: center;">
					<tr>
						<td rowspan="2" style="border: 1px;">번호</td>
						<td rowspan="2">검사항목</td>
						<td rowspan="2">검사규격</td>
						<td colspan="5">측정 DATA</td>
						<td rowspan="2">판정</td>
					</tr>
					<tr>
						<td>X1</td>
						<td>X2</td>
						<td>X3</td>
						<td>X4</td>
						<td>X5</td>
					</tr>
					<tr>
						<td style="border: 2px; 5width: 30px;">1</td>
						<td style="width: 100px;">성상</td>
						<td style="width: 300px;">청결하고 자극성이 없으며 이물이 함유되어 있지 않고 섬유의
							탈락이 거의 없는 흰색의 포로서 &nbsp;&nbsp;&nbsp; 냄새는 없다.</td>
						<td style="width: 40px;"><input style="width: 60px;" type="text" name="Inspect_Value_1[]" id="check1_val1"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text" name="Inspect_Value_2[]" id="check1_val2"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text" name="Inspect_Value_3[]" id="check1_val3"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text" name="Inspect_Value_4[]" id="check1_val4"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text" name="Inspect_Value_5[]" id="check1_val5"></td>
						<td style="width: 50px;">
							<select style="height: 24px;" name="status[]" id="check1_val6">
								<option value="check1_True">적</option>
								<option value="check1_False">부</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="border: 1px;">2</td>
						<td>색소</td>
						<td>부직포의 순도시험 중 1)색소에 따라 시험한다.</td>
						<td colspan="5"><input style="width: 400px;" type="text" name="Inspect_Value_1[]" id="check2_val1">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_2[]" id="check2_val2">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_3[]" id="check2_val3">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_4[]" id="check2_val4">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_5[]" id="check2_val5">
						</td>
						<td style="width: 50px;">
							<select style="height: 24px;" name="status[]" id="check2_val2">
								<option value="check2_True">적</option>
								<option value="check2_False">부</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="border: 1px;">3</td>
						<td>산 또는 알칼리</td>
						<td>부직포의 순도시험 중 2)산 또는 알칼리에 따라 시험한다.</td>
						<td colspan="5"><input style="width: 400px;" type="text" name="Inspect_Value_1[]" id="check3_val1">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_2[]" id="check3_val2">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_3[]" id="check3_val3">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_4[]" id="check3_val4">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_5[]" id="check3_val5">
						</td>
						<td style="width: 50px;">
							<select style="height: 24px;" name="status[]" id="check3_val2">
								<option value="check3_True">적</option>
								<option value="check3_False">부</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="border: 1px;">4</td>
						<td>형광증백제</td>
						<td>부직포의 순도시험 중 3)형광증백제에 따라 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 시험한다.</td>
						<td colspan="5"><input style="width: 400px;" type="text" name="Inspect_Value_1[]" id="check4_val1">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_2[]" id="check4_val2">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_3[]" id="check4_val3">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_4[]" id="check4_val4">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_5[]" id="check4_val5"></td>
						<td style="width: 50px;">
							<select style="height: 24px;" name="status[]" id="check4_val2">
								<option value="check4_True">적</option>
								<option value="check4_False">부</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="border: 1px;">5</td>
						<td>회분</td>
						<td>부직포의 회분에 따라 시험한다.</td>
						<td colspan="5"><input style="width: 400px;" type="text" name="Inspect_Value_1[]" id="check5_val1">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_2[]" id="check5_val2">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_3[]" id="check5_val3">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_4[]" id="check5_val4">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_5[]" id="check5_val5">
						</td>
						<td style="width: 50px;">
							<select style="height: 24px;" name="status[]" id="check5_val2">
								<option value="check5_True">적</option>
								<option value="check5_False">부</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="border: 1px;">6</td>
						<td>포름알데히드</td>
						<td>부직포의 포름알데히드에 따라 시험한다.</td>
						<td colspan="5"><input style="width: 400px;" type="text" name="Inspect_Value_1[]" id="check6_val1">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_2[]" id="check6_val2">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_3[]" id="check6_val3">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_4[]" id="check6_val4">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_5[]" id="check6_val5">
						</td>
						<td style="width: 50px;">
							<select style="height: 24px;" name="status[]" id="check6_val2">
								<option value="check6_True">적</option>
								<option value="check6_False">부</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="border: 1px;">7</td>
						<td>강도</td>
						<td>부직포의 강도에 따라 시험한다.</td>
						<td colspan="5"><input style="width: 400px;" type="text" name="Inspect_Value_1[]" id="check7_val1">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_2[]" id="check7_val2">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_3[]" id="check7_val3">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_4[]" id="check7_val4">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_5[]" id="check7_val5">
						</td>
						<td style="width: 50px;">
							<select style="height: 24px;" name="status[]" id="check7_val2">
								<option value="check7_True">적</option>
								<option value="check7_False">부</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="border: 1px;">8</td>
						<td>치수(A)</td>
						<td><input type="text" name="Inspect_STND_1[]" style="width: 50px;"> ± <input type="text" name="Inspect_STND_2[]" style="width: 80px;"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text" name="Inspect_Value_1[]" id="check8_val1"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text" name="Inspect_Value_2[]" id="check8_val2"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text" name="Inspect_Value_3[]" id="check8_val3"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text" name="Inspect_Value_4[]" id="check8_val4"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text" name="Inspect_Value_5[]" id="check8_val5"></td>
						<td style="width: 50px;">
							<select style="height: 24px;" name="status[]" id="check8_val6">
								<option value="check8_True">적</option>
								<option value="check8_False">부</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="border: 1px;">9</td>
						<td>중량</td>
						<td><input type="text" name="Inspect_STND_1[]" style="width: 50px;"> ± <input type="text" name="Inspect_STND_2[]" style="width: 80px;"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text" name="Inspect_Value_1[]" id="check9_val1"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text" name="Inspect_Value_2[]" id="check9_val2"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text" name="Inspect_Value_3[]" id="check9_val3"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text" name="Inspect_Value_4[]" id="check9_val4"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text" name="Inspect_Value_5[]" id="check9_val5"></td>
						<td style="width: 50px;">
							<select style="height: 24px;" name="status[]" id="check9_val6">
								<option value="check9_True">적</option>
								<option value="check9_False">부</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="border: 1px;">10</td>
						<td>두께</td>
						<td><input type="text" name="Inspect_STND_1[]" style="width: 50px;"> ± <input type="text" name="Inspect_STND_2[]" style="width: 80px;"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text" name="Inspect_Value_1[]" id="check10_val1"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text" name="Inspect_Value_2[]" id="check10_val2"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text" name="Inspect_Value_3[]" id="check10_val3"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text" name="Inspect_Value_4[]" id="check10_val4"></td>
						<td style="width: 40px;"><input style="width: 60px;" type="text" name="Inspect_Value_5[]" id="check10_val5"></td>
						<td style="width: 50px;">
							<select style="height: 24px;" name="status[]" id="check10_val6">
								<option value="check10_True">적</option>
								<option value="check10_False">부</option>
							</select>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- END MAIN -->
	</div>
</body>

<!-- END WRAPPER -->
<!-- Javascript -->