<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="master-table" style="padding-left: 10px;">
<table class="tg table table-bordered">
<tbody>
	<tr>
		<td colspan="3" rowspan="2" style="border: 2px; width: 40px;">NO</td>
		<td colspan="2" rowspan="2">검사항목</td>
		<td colspan="4" rowspan="2">검사기준</td>
		<td colspan="2" rowspan="2">검사방법</td>
		<td colspan="10">검사수량</td>
		<td colspan="2" rowspan="2">판정</td>
	</tr>
	<tr>
		<td colspan="1">X1</td>
		<td colspan="1">X2</td>
		<td colspan="1">X3</td>
		<td colspan="1">X4</td>
		<td colspan="1">X5</td>
		<td colspan="1">X6</td>
		<td colspan="1">X7</td>
		<td colspan="1">X8</td>
		<td colspan="1">X9</td>
		<td colspan="1">X10</td>
	</tr>
	<tr>
		<td colspan="3" style="border: 2px; width: 30px;">1</td>
		<td colspan="2" rowspan="4">외관상태</td>
		<td colspan="4" id="stndpart" style="font-size: 10px;">코판 길이, 탈착여부, 융착선 확인</td>
		<td colspan="2" rowspan="16">육안검사
		<input type="hidden" id="stnd1" style="width: 20%">
		<input type="hidden" id="stnd2" style="width: 20%">
		</td>
		<td colspan="1"><input type="text" id="value1" value="OK"></td>
		<td colspan="1"><input type="text" id="value2" value="OK"></td>
		<td colspan="1"><input type="text" id="value3" value="OK"></td>
		<td colspan="1"><input type="text" id="value4" value="OK"></td>
		<td colspan="1"><input type="text" id="value5" value="OK"></td>
		<td colspan="1"><input type="text" id="value6" value="OK"></td>
		<td colspan="1"><input type="text" id="value7" value="OK"></td>
		<td colspan="1"><input type="text" id="value8" value="OK"></td>
		<td colspan="1"><input type="text" id="value9" value="OK"></td>
		<td colspan="1"><input type="text" id="value10" value="OK"></td>
		<td colspan="2">
			<select id="status">
				<option value="true">적</option>
				<option value="false">부</option>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="3" style="border: 2px; width: 30px;">2</td>
		<td colspan="4" id="stndpart">마스크 성상, 형상
		<input type="hidden" id="stnd1" style="width: 20%">
		<input type="hidden" id="stnd2" style="width: 20%">
		</td>
		<td colspan="1"><input type="text" id="value1" value="OK"></td>
		<td colspan="1"><input type="text" id="value2" value="OK"></td>
		<td colspan="1"><input type="text" id="value3" value="OK"></td>
		<td colspan="1"><input type="text" id="value4" value="OK"></td>
		<td colspan="1"><input type="text" id="value5" value="OK"></td>
		<td colspan="1"><input type="text" id="value6" value="OK"></td>
		<td colspan="1"><input type="text" id="value7" value="OK"></td>
		<td colspan="1"><input type="text" id="value8" value="OK"></td>
		<td colspan="1"><input type="text" id="value9" value="OK"></td>
		<td colspan="1"><input type="text" id="value10" value="OK"></td>
		<td colspan="2">
			<select id="status">
				<option value="true">적</option>
				<option value="false">부</option>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="3" style="border: 2px; width: 30px;">3</td>
		<td colspan="4" id="stndpart" style="font-size: 12px;">머리끈 융착 상태, 위치 확인
		<input type="hidden" id="stnd1" style="width: 20%">
		<input type="hidden" id="stnd2" style="width: 20%">
		</td>
		<td colspan="1"><input type="text" id="value1" value="OK"></td>
		<td colspan="1"><input type="text" id="value2" value="OK"></td>
		<td colspan="1"><input type="text" id="value3" value="OK"></td>
		<td colspan="1"><input type="text" id="value4" value="OK"></td>
		<td colspan="1"><input type="text" id="value5" value="OK"></td>
		<td colspan="1"><input type="text" id="value6" value="OK"></td>
		<td colspan="1"><input type="text" id="value7" value="OK"></td>
		<td colspan="1"><input type="text" id="value8" value="OK"></td>
		<td colspan="1"><input type="text" id="value9" value="OK"></td>
		<td colspan="1"><input type="text" id="value10" value="OK"></td>
		<td colspan="2">
			<select id="status">
				<option value="true">적</option>
				<option value="false">부</option>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="3" style="border: 2px; width: 30px;">4</td>
		<td colspan="4" id="stndpart" style="font-size: 12px;">마스크 내, 외부 오염 확인
		<input type="hidden" id="stnd1" style="width: 20%">
		<input type="hidden" id="stnd2" style="width: 20%">
		</td>
		<td colspan="1"><input type="text" id="value1" value="OK"></td>
		<td colspan="1"><input type="text" id="value2" value="OK"></td>
		<td colspan="1"><input type="text" id="value3" value="OK"></td>
		<td colspan="1"><input type="text" id="value4" value="OK"></td>
		<td colspan="1"><input type="text" id="value5" value="OK"></td>
		<td colspan="1"><input type="text" id="value6" value="OK"></td>
		<td colspan="1"><input type="text" id="value7" value="OK"></td>
		<td colspan="1"><input type="text" id="value8" value="OK"></td>
		<td colspan="1"><input type="text" id="value9" value="OK"></td>
		<td colspan="1"><input type="text" id="value10" value="OK"></td>
		<td colspan="2">
			<select id="status">
				<option value="true">적</option>
				<option value="false">부</option>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="3" style="border: 2px; width: 30px;">5</td>
		<td colspan="2" rowspan="2">패키지상태</td>
		<td colspan="4" id="stndpart">밀봉 상태 확인
		<input type="hidden" id="stnd1" style="width: 20%">
		<input type="hidden" id="stnd2" style="width: 20%">
		</td>
		<td colspan="1"><input type="text" id="value1" value="OK"></td>
		<td colspan="1"><input type="text" id="value2" value="OK"></td>
		<td colspan="1"><input type="text" id="value3" value="OK"></td>
		<td colspan="1"><input type="text" id="value4" value="OK"></td>
		<td colspan="1"><input type="text" id="value5" value="OK"></td>
		<td colspan="1"><input type="text" id="value6" value="OK"></td>
		<td colspan="1"><input type="text" id="value7" value="OK"></td>
		<td colspan="1"><input type="text" id="value8" value="OK"></td>
		<td colspan="1"><input type="text" id="value9" value="OK"></td>
		<td colspan="1"><input type="text" id="value10" value="OK"></td>
		<td colspan="2">
			<select id="status">
				<option value="true">적</option>
				<option value="false">부</option>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="3" style="border: 2px; width: 30px;">6</td>
		<td colspan="4" id="stndpart" style="font-size: 11px;">제조일 인쇄 및 인쇄 상태 확인
		<input type="hidden" id="stnd1" style="width: 20%">
		<input type="hidden" id="stnd2" style="width: 20%">
		</td>
		<td colspan="1"><input type="text" id="value1" value="OK"></td>
		<td colspan="1"><input type="text" id="value2" value="OK"></td>
		<td colspan="1"><input type="text" id="value3" value="OK"></td>
		<td colspan="1"><input type="text" id="value4" value="OK"></td>
		<td colspan="1"><input type="text" id="value5" value="OK"></td>
		<td colspan="1"><input type="text" id="value6" value="OK"></td>
		<td colspan="1"><input type="text" id="value7" value="OK"></td>
		<td colspan="1"><input type="text" id="value8" value="OK"></td>
		<td colspan="1"><input type="text" id="value9" value="OK"></td>
		<td colspan="1"><input type="text" id="value10" value="OK"></td>
		<td colspan="2">
			<select id="status">
				<option value="true">적</option>
				<option value="false">부</option>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="3" style="border: 2px; width: 30px;">7</td>
		<td colspan="2" rowspan="2">박스상태</td>
		<td colspan="4" id="stndpart" style="font-size: 9px;">제품과 포장박스의 규격 동일여부
		<input type="hidden" id="stnd1" style="width: 20%">
		<input type="hidden" id="stnd2" style="width: 20%">
		</td>
		<td colspan="1"><input type="text" id="value1" value="OK"></td>
		<td colspan="1"><input type="text" id="value2" value="OK"></td>
		<td colspan="1"><input type="text" id="value3" value="OK"></td>
		<td colspan="1"><input type="text" id="value4" value="OK"></td>
		<td colspan="1"><input type="text" id="value5" value="OK"></td>
		<td colspan="1"><input type="text" id="value6" value="OK"></td>
		<td colspan="1"><input type="text" id="value7" value="OK"></td>
		<td colspan="1"><input type="text" id="value8" value="OK"></td>
		<td colspan="1"><input type="text" id="value9" value="OK"></td>
		<td colspan="1"><input type="text" id="value10" value="OK"></td>
		<td colspan="2">
			<select id="status">
				<option value="true">적</option>
				<option value="false">부</option>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="3" style="border: 2px; width: 30px;">8</td>
		<td colspan="4" id="stndpart" style="font-size: 11px;">테이핑 및 박스 포장 상태 확인
		<input type="hidden" id="stnd1" style="width: 20%">
		<input type="hidden" id="stnd2" style="width: 20%">
		</td>
		<td colspan="1"><input type="text" id="value1" value="OK"></td>
		<td colspan="1"><input type="text" id="value2" value="OK"></td>
		<td colspan="1"><input type="text" id="value3" value="OK"></td>
		<td colspan="1"><input type="text" id="value4" value="OK"></td>
		<td colspan="1"><input type="text" id="value5" value="OK"></td>
		<td colspan="1"><input type="text" id="value6" value="OK"></td>
		<td colspan="1"><input type="text" id="value7" value="OK"></td>
		<td colspan="1"><input type="text" id="value8" value="OK"></td>
		<td colspan="1"><input type="text" id="value9" value="OK"></td>
		<td colspan="1"><input type="text" id="value10" value="OK"></td>
		<td colspan="2">
			<select id="status">
				<option value="true">적</option>
				<option value="false">부</option>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="3" style="border: 2px; width: 30px;">9</td>
		<td colspan="2">Bar Code</td>
		<td colspan="4" id="stndpart" style="font-size: 11px;">종목과 동일하게 인쇄되어 있는지 확인
		<input type="hidden" id="stnd1" style="width: 20%">
		<input type="hidden" id="stnd2" style="width: 20%">
		</td>
		<td colspan="1"><input type="text" id="value1" value="OK"></td>
		<td colspan="1"><input type="text" id="value2" value="OK"></td>
		<td colspan="1"><input type="text" id="value3" value="OK"></td>
		<td colspan="1"><input type="text" id="value4" value="OK"></td>
		<td colspan="1"><input type="text" id="value5" value="OK"></td>
		<td colspan="1"><input type="text" id="value6" value="OK"></td>
		<td colspan="1"><input type="text" id="value7" value="OK"></td>
		<td colspan="1"><input type="text" id="value8" value="OK"></td>
		<td colspan="1"><input type="text" id="value9" value="OK"></td>
		<td colspan="1"><input type="text" id="value10" value="OK"></td>
		<td colspan="2">
			<select id="status">
				<option value="true">적</option>
				<option value="false">부</option>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="3" style="border: 2px; width: 30px;">10</td>
		<td colspan="2" rowspan="7">포장단위</td>
		<td colspan="4" id="stndpart">
			<select id="unit1">
				<option value="보건용">보건용</option>
				<option value="비말용">비말용</option>
			</select>&nbsp;&nbsp;
			<input type="text" id="stnd1" style="width: 20%">
			×
			<input type="text" id="stnd2" style="width: 20%">
		</td>
		<td colspan="1"><input type="text" id="value1" class="tempValue1"></td>
		<td colspan="1"><input type="text" id="value2" class="tempValue2"></td>
		<td colspan="1"><input type="text" id="value3" class="tempValue3"></td>
		<td colspan="1"><input type="text" id="value4" class="tempValue4"></td>
		<td colspan="1"><input type="text" id="value5" class="tempValue5"></td>
		<td colspan="1"><input type="text" id="value6" class="tempValue6"></td>
		<td colspan="1"><input type="text" id="value7" class="tempValue7"></td>
		<td colspan="1"><input type="text" id="value8" class="tempValue8"></td>
		<td colspan="1"><input type="text" id="value9" class="tempValue9"></td>
		<td colspan="1"><input type="text" id="value10" class="tempValue10"></td>
		<td colspan="2">
			<select id="status">
				<option value="true">적</option>
				<option value="false">부</option>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="3" style="border: 2px; width: 30px;">11</td>
		<td colspan="4" id="stndpart">
			<select id="unit1">
				<option value="보건용">보건용</option>
				<option value="비말용">비말용</option>
			</select>&nbsp;&nbsp;
			<input type="text" id="stnd1" style="width: 20%">
			×
			<input type="text" id="stnd2" style="width: 20%">
		</td>
		<td colspan="1"><input type="text" id="value1" class="tempValue1"></td>
		<td colspan="1"><input type="text" id="value2" class="tempValue2"></td>
		<td colspan="1"><input type="text" id="value3" class="tempValue3"></td>
		<td colspan="1"><input type="text" id="value4" class="tempValue4"></td>
		<td colspan="1"><input type="text" id="value5" class="tempValue5"></td>
		<td colspan="1"><input type="text" id="value6" class="tempValue6"></td>
		<td colspan="1"><input type="text" id="value7" class="tempValue7"></td>
		<td colspan="1"><input type="text" id="value8" class="tempValue8"></td>
		<td colspan="1"><input type="text" id="value9" class="tempValue9"></td>
		<td colspan="1"><input type="text" id="value10" class="tempValue10"></td>
		<td colspan="2">
			<select id="status">
				<option value="true">적</option>
				<option value="false">부</option>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="3" style="border: 2px; width: 30px;">12</td>
		<td colspan="4" id="stndpart">
			<select id="unit1">
				<option value="보건용">보건용</option>
				<option value="비말용">비말용</option>
			</select>&nbsp;&nbsp;
			<input type="text" id="stnd1" style="width: 20%">
			×
			<input type="text" id="stnd2" style="width: 20%">
		</td>
		<td colspan="1"><input type="text" id="value1" class="tempValue1"></td>
		<td colspan="1"><input type="text" id="value2" class="tempValue2"></td>
		<td colspan="1"><input type="text" id="value3" class="tempValue3"></td>
		<td colspan="1"><input type="text" id="value4" class="tempValue4"></td>
		<td colspan="1"><input type="text" id="value5" class="tempValue5"></td>
		<td colspan="1"><input type="text" id="value6" class="tempValue6"></td>
		<td colspan="1"><input type="text" id="value7" class="tempValue7"></td>
		<td colspan="1"><input type="text" id="value8" class="tempValue8"></td>
		<td colspan="1"><input type="text" id="value9" class="tempValue9"></td>
		<td colspan="1"><input type="text" id="value10" class="tempValue10"></td>
		<td colspan="2">
			<select id="status">
				<option value="true">적</option>
				<option value="false">부</option>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="3" style="border: 2px; width: 30px;">13</td>
		<td colspan="4" id="stndpart">
			<select id="unit1">
				<option value="보건용">보건용</option>
				<option value="비말용">비말용</option>
			</select>&nbsp;&nbsp;
			<input type="text" id="stnd1" style="width: 20%">
			×
			<input type="text" id="stnd2" style="width: 20%">
		</td>
		<td colspan="1"><input type="text" id="value1" class="tempValue1"></td>
		<td colspan="1"><input type="text" id="value2" class="tempValue2"></td>
		<td colspan="1"><input type="text" id="value3" class="tempValue3"></td>
		<td colspan="1"><input type="text" id="value4" class="tempValue4"></td>
		<td colspan="1"><input type="text" id="value5" class="tempValue5"></td>
		<td colspan="1"><input type="text" id="value6" class="tempValue6"></td>
		<td colspan="1"><input type="text" id="value7" class="tempValue7"></td>
		<td colspan="1"><input type="text" id="value8" class="tempValue8"></td>
		<td colspan="1"><input type="text" id="value9" class="tempValue9"></td>
		<td colspan="1"><input type="text" id="value10" class="tempValue10"></td>
		<td colspan="2">
			<select id="status">
				<option value="true">적</option>
				<option value="false">부</option>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="3" style="border: 2px; width: 30px;">14</td>
		<td colspan="4" id="stndpart">
			<select id="unit1">
				<option value="보건용">보건용</option>
				<option value="비말용">비말용</option>
			</select>&nbsp;&nbsp;
			<input type="text" id="stnd1" style="width: 20%">
			×
			<input type="text" id="stnd2" style="width: 20%">
		</td>
		<td colspan="1"><input type="text" id="value1" class="tempValue1"></td>
		<td colspan="1"><input type="text" id="value2" class="tempValue2"></td>
		<td colspan="1"><input type="text" id="value3" class="tempValue3"></td>
		<td colspan="1"><input type="text" id="value4" class="tempValue4"></td>
		<td colspan="1"><input type="text" id="value5" class="tempValue5"></td>
		<td colspan="1"><input type="text" id="value6" class="tempValue6"></td>
		<td colspan="1"><input type="text" id="value7" class="tempValue7"></td>
		<td colspan="1"><input type="text" id="value8" class="tempValue8"></td>
		<td colspan="1"><input type="text" id="value9" class="tempValue9"></td>
		<td colspan="1"><input type="text" id="value10" class="tempValue10"></td>
		<td colspan="2">
			<select id="status">
				<option value="true">적</option>
				<option value="false">부</option>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="3" style="border: 2px; width: 30px;">15</td>
		<td colspan="4" id="stndpart">
			<select id="unit1">
				<option value="보건용">보건용</option>
				<option value="비말용">비말용</option>
			</select>&nbsp;&nbsp;
			<input type="text" id="stnd1" style="width: 20%">
			×
			<input type="text" id="stnd2" style="width: 20%">
		</td>
		<td colspan="1"><input type="text" id="value1" class="tempValue1"></td>
		<td colspan="1"><input type="text" id="value2" class="tempValue2"></td>
		<td colspan="1"><input type="text" id="value3" class="tempValue3"></td>
		<td colspan="1"><input type="text" id="value4" class="tempValue4"></td>
		<td colspan="1"><input type="text" id="value5" class="tempValue5"></td>
		<td colspan="1"><input type="text" id="value6" class="tempValue6"></td>
		<td colspan="1"><input type="text" id="value7" class="tempValue7"></td>
		<td colspan="1"><input type="text" id="value8" class="tempValue8"></td>
		<td colspan="1"><input type="text" id="value9" class="tempValue9"></td>
		<td colspan="1"><input type="text" id="value10" class="tempValue10"></td>
		<td colspan="2">
			<select id="status">
				<option value="true">적</option>
				<option value="false">부</option>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="3" style="border: 2px; width: 30px;">16</td>
		<td colspan="4" id="stndpart">
			<select id="unit1" style="margin-left: -5px !important; width: 77px !important;">
				<option value="보건용">보건용</option>
				<option value="비말용">비말용</option>
				<option value="비말용무">비말용,무</option>
			</select>&nbsp;
			<input type="text" id="stnd1" style="width: 20%">
			×
			<input type="text" id="stnd2" style="width: 20%">
			</td>
			<td colspan="1"><input type="text" id="value1" class="tempValue1"></td>
			<td colspan="1"><input type="text" id="value2" class="tempValue2"></td>
			<td colspan="1"><input type="text" id="value3" class="tempValue3"></td>
			<td colspan="1"><input type="text" id="value4" class="tempValue4"></td>
			<td colspan="1"><input type="text" id="value5" class="tempValue5"></td>
			<td colspan="1"><input type="text" id="value6" class="tempValue6"></td>
			<td colspan="1"><input type="text" id="value7" class="tempValue7"></td>
			<td colspan="1"><input type="text" id="value8" class="tempValue8"></td>
			<td colspan="1"><input type="text" id="value9" class="tempValue9"></td>
			<td colspan="1"><input type="text" id="value10" class="tempValue10"></td>
			<td colspan="2">
				<select id="status">
					<option value="true">적</option>
					<option value="false">부</option>
				</select>
			</td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="5">비고</td>
			<td colspan="18">
				<textarea id="oqcRemark" rows="2" cols="60"></textarea>
			</td>
		</tr>
	</tfoot>
</table>
</div>