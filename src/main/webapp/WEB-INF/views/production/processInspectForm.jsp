<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	<div class="soloView1">
		<!-- MAIN -->
		<div class="main">
			<!-- 그리드 생성 장소 -->
			<div class="master-table" style="padding-left: 10px; padding-right: 10px;">
			<table class="tg table table-bordered">
			<tbody>
				<tr>
					<td colspan="3">생산라인</td>
					<td colspan="4"><input type="text" id="proInspectEquipName" readonly></td>
					<td colspan="3">제품명</td>
					<td colspan="4"><input type="text" id="proInspectItemName" readonly></td>
				</tr>
				<tr>
					<td colspan="3">생산일자/검사일자</td>
					<td colspan="4"><input type="text" id="productionDate" readonly> / <input type="text" id="processDate" readonly></td>
					<td colspan="3">검사자</td>
					<td colspan="4">
					<select id="pqcWorkerList" onchange="">
						<c:forEach var="list" items="${processInspectList}">
							<option value="${list.CHILD_TBL_NO}">${list.CHILD_TBL_TYPE}</option>
						</c:forEach>			
					</select>
					</td>
				</tr>
				<tr>
					<td colspan="3">검사기준/검사수량</td>
					<td colspan="4">KS Q ISO 2859-1</td>
					<td colspan="3">시료수</td>
					<td colspan="4"><input type="text" id="processQty">&nbsp;&nbsp;
					<select id="itemColorType">
						<option value="화이트">화이트</option>
						<option value="블랙">블랙</option>
					</select>
					</td>
				</tr>
				<tr>
					<td colspan="3">검사항목</td>
					<td colspan="4">검사기준</td>
					<td colspan="1">X1</td>
					<td colspan="1">X2</td>
					<td colspan="1">X3</td>
					<td colspan="1">X4</td>
					<td colspan="1">X5</td>
					<td colspan="2">결과</td>
				</tr>
				<tr>
					<td colspan="3">성상</td>
					<td colspan="4">(흰색, 검정색)의 3단 가로접이식 본체에 코편이 있고, 양측면에 (흰색, 검정색)의
					끈이 있는 부직포 마스크
					<input type="hidden" id="stnd1" name="subInspect_STND_1[]" style="width: 20%">
					<input type="hidden" id="stnd2" name="subInspect_STND_2[]" style="width: 20%">
					</td>
					<td colspan="1"><input type="text" id="value1"></td>
					<td colspan="1"><input type="text" id="value2"></td>
					<td colspan="1"><input type="text" id="value3"></td>
					<td colspan="1"><input type="text" id="value4"></td>
					<td colspan="1"><input type="text" id="value5"></td>
					<td colspan="2">
						<select name="status[]" id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
				<tr>
					<td rowspan="4" colspan="1">형상</td>
					<td colspan="2">가로</td>
					<td colspan="4">
						<input type="text" id="stnd1" name="subInspect_STND_1[]" style="width: 20%">
						±
						<input type="text" id="stnd2" name="subInspect_STND_2[]" style="width: 20%">
					</td>
					<td colspan="1"><input type="text" id="value1"></td>
					<td colspan="1"><input type="text" id="value2"></td>
					<td colspan="1"><input type="text" id="value3"></td>
					<td colspan="1"><input type="text" id="value4"></td>
					<td colspan="1"><input type="text" id="value5"></td>
					<td colspan="2">
						<select name="status[]" id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="2">세로</td>
					<td colspan="4">
						<input type="text" id="stnd1" name="subInspect_STND_1[]" style="width: 20%">
						±
						<input type="text" id="stnd2" name="subInspect_STND_2[]" style="width: 20%">
					</td>
					<td colspan="1"><input type="text" id="value1"></td>
					<td colspan="1"><input type="text" id="value2"></td>
					<td colspan="1"><input type="text" id="value3"></td>
					<td colspan="1"><input type="text" id="value4"></td>
					<td colspan="1"><input type="text" id="value5"></td>
					<td colspan="2">
						<select name="status[]" id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="2">머리끈(좌)</td>
					<td colspan="4">
						<input type="text" id="stnd1" name="subInspect_STND_1[]" style="width: 20%">
						±
						<input type="text" id="stnd2" name="subInspect_STND_2[]" style="width: 20%">
					</td>
					<td colspan="1"><input type="text" id="value1"></td>
					<td colspan="1"><input type="text" id="value2"></td>
					<td colspan="1"><input type="text" id="value3"></td>
					<td colspan="1"><input type="text" id="value4"></td>
					<td colspan="1"><input type="text" id="value5"></td>
					<td colspan="2">
						<select name="status[]" id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="2">머리끈(우)</td>
					<td colspan="4">
						<input type="text" id="stnd1" name="subInspect_STND_1[]" style="width: 20%">
						±
						<input type="text" id="stnd2" name="subInspect_STND_2[]" style="width: 20%">
					</td>
					<td colspan="1"><input type="text" id="value1"></td>
					<td colspan="1"><input type="text" id="value2"></td>
					<td colspan="1"><input type="text" id="value3"></td>
					<td colspan="1"><input type="text" id="value4"></td>
					<td colspan="1"><input type="text" id="value5"></td>
					<td colspan="2">
						<select name="status[]" id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3">코편</td>
					<td colspan="4">
						<input type="text" id="stnd1" name="subInspect_STND_1[]" style="width: 20%">
						±
						<input type="text" id="stnd2" name="subInspect_STND_2[]" style="width: 20%">
					</td>
					<td colspan="1"><input type="text" id="value1"></td>
					<td colspan="1"><input type="text" id="value2"></td>
					<td colspan="1"><input type="text" id="value3"></td>
					<td colspan="1"><input type="text" id="value4"></td>
					<td colspan="1"><input type="text" id="value5"></td>
					<td colspan="2">
						<select name="status[]" id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3">중량</td>
					<td colspan="4">
						<input type="text" id="stnd1" name="subInspect_STND_1[]" style="width: 20%">
						±
						<input type="text" id="stnd2" name="subInspect_STND_2[]" style="width: 20%">
					</td>
					<td colspan="1"><input type="text" id="value1"></td>
					<td colspan="1"><input type="text" id="value2"></td>
					<td colspan="1"><input type="text" id="value3"></td>
					<td colspan="1"><input type="text" id="value4"></td>
					<td colspan="1"><input type="text" id="value5"></td>
					<td colspan="2">
						<select name="status[]" id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3">인장강도</td>
					<td colspan="4">
						인장시험기(30 cm/분)로 잡아당겨
						머리끈과 마스크의 접착부위가 절단될
						때까지의 최대 하중을 측정할 때, 10N
						<input type="hidden" id="stnd1" name="subInspect_STND_1[]" style="width: 20%">
						<input type="hidden" id="stnd2" name="subInspect_STND_2[]" style="width: 20%">
					</td>
					<td colspan="1"><input type="text" id="value1"></td>
					<td colspan="1"><input type="text" id="value2"></td>
					<td colspan="1"><input type="text" id="value3"></td>
					<td colspan="1"><input type="text" id="value4"></td>
					<td colspan="1"><input type="text" id="value5"></td>
					<td colspan="2">
						<select name="status[]" id="status">
							<option value="true">적</option>
							<option value="false">부</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="9"><b>최종 검사결과</b></td>
					<td colspan="5">
						<select name="status[]" id="result">
							<option value="true">적합</option>
							<option value="false">부적합</option>
						</select>
					</td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="3">비고</td>
					<td colspan="11">
						<textarea id="processRemark" name="subinspectionRemark" rows="3" cols="60"></textarea>
					</td>
				</tr>
			</tfoot>
		</table>
			</div>
		</div>
		<!-- END MAIN -->
	</div>

<!-- END WRAPPER -->
<!-- Javascript -->