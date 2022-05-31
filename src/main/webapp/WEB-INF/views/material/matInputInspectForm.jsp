<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<div class="soloView1">
		<!-- MAIN -->
		<div class="main">
			<!-- 그리드 생성 장소 -->
			<div class="master-table" style="padding-left: 10px;">
				<table class="tg table table-bordered">
					<tbody>
					<tr>
						<td colspan="3" style="text-align: center;">품명</td>
						<td colspan="4"><input type="text" id="matInspectItemName" readonly></td>
						<td colspan="3" style="text-align: center;">검사일자</td>
						<td colspan="4"><input type="text" id="matInspectDate" readonly></td>
						
					</tr>
					<tr>
						<td colspan="3" style="text-align: center;">수량</td>
						<td colspan="4"><input type="text" id="matInspectQty" readonly></td>
						<td colspan="3" style="text-align: center;">검사자</td>
						<td colspan="4">
						<select id="matInspectWorker" onchange="">
							<c:forEach var="list" items="${matInputInspectList}">
								<option value="${list.CHILD_TBL_NO}">${list.CHILD_TBL_TYPE}</option>
							</c:forEach>			
						</select>
						</td>
					</tr>
					<tr>
						<td colspan="3" style="text-align: center;">검사기준</td>
						<td colspan="4">KS Q ISO 2859-1</td>
						<td colspan="3" style="text-align: center;">공급업체</td>
						<td colspan="4"><input type="text" id="matInspectCustomer" readonly></td>
					</tr>
					<tr>
						<td colspan="3" style="width: 100px; font-size: 13px;" width="80"
							class="col1"></td>
						<td colspan="11" class="col2">
							<textarea id="inspectionText" name="inspectionText" rows="2" cols="94" style="resize: none;"></textarea>
						</td>
					</tr>
					<tr>
						<td rowspan="2" colspan="1" style="border: 1px;">번호</td>
						<td rowspan="2" colspan="2">검사항목</td>
						<td rowspan="2" colspan="5">검사규격</td>
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
						<td colspan="1" style="border: 2px; width: 30px;">1</td>
						<td colspan="2" style="width: 100px;">성상</td>
						<td colspan="5">청결하고 자극성이 없으며 이물이 함유되어 있지 않고 섬유의
							탈락이 거의 없는 흰색의 포로서 냄새는 없다.</td>
						<td><input style="width: 45px;" type="text" name="Inspect_Value_1[]" id="check1_val1"></td>
						<td><input style="width: 45px;" type="text" name="Inspect_Value_2[]" id="check1_val2"></td>
						<td><input style="width: 45px;" type="text" name="Inspect_Value_3[]" id="check1_val3"></td>
						<td><input style="width: 45px;" type="text" name="Inspect_Value_4[]" id="check1_val4"></td>
						<td><input style="width: 45px;" type="text" name="Inspect_Value_5[]" id="check1_val5"></td>
						<td>
							<select style="height: 24px;" name="status[]" id="check1_val6">
								<option value="true">적</option>
								<option value="false">부</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="border: 1px;">2</td>
						<td colspan="2">색소</td>
						<td colspan="5">부직포의 순도시험 중 1)색소에 따라 시험한다.</td>
						<td colspan="5"><input style="width: 280px;" type="text" name="Inspect_Value_1[]" id="check2_val1">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_2[]" id="check2_val2">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_3[]" id="check2_val3">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_4[]" id="check2_val4">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_5[]" id="check2_val5">
						</td>
						<td style="width: 50px;">
							<select style="height: 24px;" name="status[]" id="check2_val6">
								<option value="true">적</option>
								<option value="false">부</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="border: 1px;">3</td>
						<td colspan="2">산 또는 알칼리</td>
						<td colspan="5">부직포의 순도시험 중 2)산 또는 알칼리에 따라 시험한다.</td>
						<td colspan="5"><input style="width: 280px;" type="text" name="Inspect_Value_1[]" id="check3_val1">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_2[]" id="check3_val2">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_3[]" id="check3_val3">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_4[]" id="check3_val4">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_5[]" id="check3_val5">
						</td>
						<td style="width: 50px;">
							<select style="height: 24px;" name="status[]" id="check3_val6">
								<option value="true">적</option>
								<option value="false">부</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="border: 1px;">4</td>
						<td colspan="2">형광증백제</td>
						<td colspan="5">부직포의 순도시험 중 3)형광증백제에 따라 시험한다.</td>
						<td colspan="5"><input style="width: 280px;" type="text" name="Inspect_Value_1[]" id="check4_val1">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_2[]" id="check4_val2">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_3[]" id="check4_val3">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_4[]" id="check4_val4">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_5[]" id="check4_val5"></td>
						<td style="width: 50px;">
							<select style="height: 24px;" name="status[]" id="check4_val6">
								<option value="true">적</option>
								<option value="false">부</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="border: 1px;">5</td>
						<td colspan="2">회분</td>
						<td colspan="5">부직포의 회분에 따라 시험한다.</td>
						<td colspan="5"><input style="width: 280px;" type="text" name="Inspect_Value_1[]" id="check5_val1">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_2[]" id="check5_val2">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_3[]" id="check5_val3">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_4[]" id="check5_val4">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_5[]" id="check5_val5">
						</td>
						<td style="width: 50px;">
							<select style="height: 24px;" name="status[]" id="check5_val6">
								<option value="true">적</option>
								<option value="false">부</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="border: 1px;">6</td>
						<td colspan="2">포름알데히드</td>
						<td colspan="5">부직포의 포름알데히드에 따라 시험한다.</td>
						<td colspan="5"><input style="width: 280px;" type="text" name="Inspect_Value_1[]" id="check6_val1">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_2[]" id="check6_val2">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_3[]" id="check6_val3">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_4[]" id="check6_val4">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_5[]" id="check6_val5">
						</td>
						<td style="width: 50px;">
							<select style="height: 24px;" name="status[]" id="check6_val6">
								<option value="true">적</option>
								<option value="false">부</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="border: 1px;">7</td>
						<td colspan="2">강도</td>
						<td colspan="5">부직포의 강도에 따라 시험한다.</td>
						<td colspan="5"><input style="width: 280px;" type="text" name="Inspect_Value_1[]" id="check7_val1">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_2[]" id="check7_val2">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_3[]" id="check7_val3">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_4[]" id="check7_val4">
						<input style="width: 60px;" type="hidden" name="Inspect_Value_5[]" id="check7_val5">
						</td>
						<td style="width: 50px;">
							<select style="height: 24px;" name="status[]" id="check7_val6">
								<option value="true">적</option>
								<option value="false">부</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="border: 1px;">8</td>
						<td colspan="2">치수(A)</td>
						<td colspan="5"><input type="text" name="Inspect_STND_1[]" style="width: 50px;"> ± <input type="text" name="Inspect_STND_2[]" style="width: 80px;"></td>
						<td style="width: 40px;"><input style="width: 45px;" type="text" name="Inspect_Value_1[]" id="check8_val1"></td>
						<td style="width: 40px;"><input style="width: 45px;" type="text" name="Inspect_Value_2[]" id="check8_val2"></td>
						<td style="width: 40px;"><input style="width: 45px;" type="text" name="Inspect_Value_3[]" id="check8_val3"></td>
						<td style="width: 40px;"><input style="width: 45px;" type="text" name="Inspect_Value_4[]" id="check8_val4"></td>
						<td style="width: 40px;"><input style="width: 45px;" type="text" name="Inspect_Value_5[]" id="check8_val5"></td>
						<td style="width: 50px;">
							<select style="height: 24px;" name="status[]" id="check8_val6">
								<option value="true">적</option>
								<option value="false">부</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="border: 1px;">9</td>
						<td colspan="2">중량</td>
						<td colspan="5"><input type="text" name="Inspect_STND_1[]" style="width: 50px;"> ± <input type="text" name="Inspect_STND_2[]" style="width: 80px;"></td>
						<td style="width: 40px;"><input style="width: 45px;" type="text" name="Inspect_Value_1[]" id="check9_val1"></td>
						<td style="width: 40px;"><input style="width: 45px;" type="text" name="Inspect_Value_2[]" id="check9_val2"></td>
						<td style="width: 40px;"><input style="width: 45px;" type="text" name="Inspect_Value_3[]" id="check9_val3"></td>
						<td style="width: 40px;"><input style="width: 45px;" type="text" name="Inspect_Value_4[]" id="check9_val4"></td>
						<td style="width: 40px;"><input style="width: 45px;" type="text" name="Inspect_Value_5[]" id="check9_val5"></td>
						<td style="width: 50px;">
							<select style="height: 24px;" name="status[]" id="check9_val6">
								<option value="true">적</option>
								<option value="false">부</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="border: 1px;">10</td>
						<td colspan="2">두께</td>
						<td colspan="5"><input type="text" name="Inspect_STND_1[]" style="width: 50px;"> ± <input type="text" name="Inspect_STND_2[]" style="width: 80px;"></td>
						<td style="width: 40px;"><input style="width: 45px;" type="text" name="Inspect_Value_1[]" id="check10_val1"></td>
						<td style="width: 40px;"><input style="width: 45px;" type="text" name="Inspect_Value_2[]" id="check10_val2"></td>
						<td style="width: 40px;"><input style="width: 45px;" type="text" name="Inspect_Value_3[]" id="check10_val3"></td>
						<td style="width: 40px;"><input style="width: 45px;" type="text" name="Inspect_Value_4[]" id="check10_val4"></td>
						<td style="width: 40px;"><input style="width: 45px;" type="text" name="Inspect_Value_5[]" id="check10_val5"></td>
						<td style="width: 50px;">
							<select style="height: 24px;" name="status[]" id="check10_val6">
								<option value="true">적</option>
								<option value="false">부</option>
							</select>
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="3" style="text-align: center;"><p>비고</p></td >
						<td colspan="11">
							<textarea id="inspectionRemark" name="inspectionRemark" rows="2" cols="94" style="resize: none;"></textarea>
						</td>
					</tr>
				</tfoot>
				</table>
			</div>
		</div>
		<!-- END MAIN -->
	</div>
</body>

<!-- END WRAPPER -->
<!-- Javascript -->