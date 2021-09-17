var table = null;

// DB의 데이터를 변수에 담아서 화면에 뿌여줄 용도
var datas = "";

// 선택한 행을 변수에 담아서 더블클릭한 행에 색깔을 칠하는 변수
var element = null;

// 행을 더블클릭하여서 해당 행의 인덱스를 저장하는 변수
var rowindex = 0;

// 행을 더블클릭하여서 해당 행의 데이터를 저장했다가 화면에서 뿌려주는 변수
var user_CODE = null;
var user_NAME = null;
var user_TYPE_NAME = null;
var user_COMPANY_NAME = null;
var user_DEPT_NAME = null;
var user_MODIFY_D = null;
var user_MODIFIER = null;
var user_USE_STATUS = null;

// 행을 더블클릭했는지 안했는지 아는 변수
var user_FLAG = false;

function updatedeleteView()
		{
			if(!user_FLAG)
			{
				alert("행을 선택한 후에 수정 및 삭제를 시도하여 주십시오.");
				return;
			}
			
			$("#updateModal").modal("show");
		}



window.onload = function() {
	$
		.ajax({
			method: "GET",
			url: "userManageRest/userManageRestSelect",
			success: function(data) {
				datas = data;
				console.log(datas);
				var userManageTable = new Tabulator(
					"#userManageTable",
					{
						//페이징
						layout: "fitColumns",
						pagination: "local",
						paginationSize: 20,
						headerFilterPlaceholder: null,
						height: "calc(100% - 175px)",
						rowClick:function(e, row){
							user_FLAG = true;
							row.select();
							if (element == null) {
								element = row.getElement();
								element.style.color = "red";
							}
							else {
								element.style.color = "black";
								row.getElement().style.color = "red";
								element = row.getElement();
							}

							rowindex = row.getIndex();
							
							// 더블클릭할때 데이터를 저장
							user_CODE = row.getData().user_CODE;
							user_NAME = row.getData().user_NAME;
							user_TYPE_NAME = row.getData().user_TYPE_NAME;
							user_COMPANY_NAME = row.getData().company_NAME;
							user_DEPT_NAME = row.getData().dept_NAME;
							user_MODIFY_D = row.getData().user_MODIFY_D;
							user_MODIFIER = row.getData().user_MODIFIER;
							user_USE_STATUS = row.getData().user_USE_STATUS;
							
							document.getElementById("update_user_CODE").value = user_CODE;
							document.getElementById("update_user_NAME").value = user_NAME;
							
							var company = row.getData().company
							var companylast = String(company).charAt(company.length-1)-1;
							
							$("#update_user_COMPANY option:eq("+companylast+")").prop("selected", true);
							
							var user_TYPE = row.getData().user_TYPE
							var user_TYPElast = String(user_TYPE).charAt(user_TYPE.length-1)-1;
							
							$("#update_user_TYPE option:eq("+user_TYPElast+")").prop("selected", true);
							
							var dept_CODE = row.getData().dept_CODE
							var dept_CODElast = String(dept_CODE).charAt(dept_CODE.length-1);
							
							$("#update_user_DEPT option:eq("+dept_CODElast+")").prop("selected", true);
									
							if (row.getData().user_USE_STATUS == "true")
								document.getElementById("update_user_USE_STATUS").checked = true;
							else
								document.getElementById("update_user_USE_STATUS").checked = false;
    					}, 
						rowDblClick: function(e, row) {
							user_FLAG = true;
							row.select();
							if (element == null) {
								element = row.getElement();
								element.style.color = "red";
							}
							else {
								element.style.color = "black";
								row.getElement().style.color = "red";
								element = row.getElement();
							}

							rowindex = row.getIndex();

							console.log(row.getData());

							// 더블클릭할때 데이터를 저장
							user_CODE = row.getData().user_CODE;
							user_NAME = row.getData().user_NAME;
							user_TYPE_NAME = row.getData().user_TYPE_NAME;
							user_COMPANY_NAME = row.getData().company_NAME;
							user_DEPT_NAME = row.getData().dept_NAME;
							user_MODIFY_D = row.getData().user_MODIFY_D;
							user_MODIFIER = row.getData().user_MODIFIER;
							user_USE_STATUS = row.getData().user_USE_STATUS;
							console.log(user_USE_STATUS);

							//행에 색변경		
							$("#updateModal").modal("show");
							
							document.getElementById("update_user_CODE").value = user_CODE;
							document.getElementById("update_user_NAME").value = user_NAME;
							
							var company = row.getData().company
							var companylast = String(company).charAt(company.length-1)-1;
							
							$("#update_user_COMPANY option:eq("+companylast+")").prop("selected", true);
							
							var user_TYPE = row.getData().user_TYPE
							var user_TYPElast = String(user_TYPE).charAt(user_TYPE.length-1)-1;
							
							$("#update_user_TYPE option:eq("+user_TYPElast+")").prop("selected", true);
							
							var dept_CODE = row.getData().dept_CODE
							var dept_CODElast = String(dept_CODE).charAt(dept_CODE.length-1);
							
							$("#update_user_DEPT option:eq("+dept_CODElast+")").prop("selected", true);
							
							if (row.getData().user_USE_STATUS == "true")
								document.getElementById("update_user_USE_STATUS").checked = true;
							else
								document.getElementById("update_user_USE_STATUS").checked = false;
								
							
						},
						data: datas, //assign data to table
						selectable:1, 
						columns: [ //Define Table Columns
							{
								title: "번호",
								field: "id",
								hozAlign: "center",
								headerHozAlign: "center",
								width: 80
							}, {
								title: "사용자코드",
								field: "user_CODE",
								width: 150,
								headerHozAlign: "center",
								headerFilter: "input"
							}, {
								title: "사용자명",
								field: "user_NAME",
								width: 150,
								headerHozAlign: "center",
								headerFilter: "input"
							}, {
								title: "사용자타입",
								field: "user_TYPE_NAME",
								width: 150,
								headerHozAlign: "center",
								headerFilter: "input"
							}, {
								title: "사업장",
								field: "company_NAME",
								width: 150,
								headerHozAlign: "center",
								headerFilter: "input"
							}, {
								title: "부서",
								field: "dept_NAME",
								width: 150,
								headerHozAlign: "center",
								headerFilter: "input"
							}, {
								title: "사용유무",
								field: "user_USE_STATUS",
								width: 150,
								headerHozAlign: "center",
								hozAlign: "center",
								formatter: "tickCross",
								editor: "text",
								editorParams: {
									values: {
										"true": "사용",
										"false": "미사용"
									}
								},
								headerFilter: true,
								headerFilterParams: {
									values: {
										"true": "사용",
										"false": "미사용"
									}
								}
							}, {
								title: "수정일자",
								field: "user_MODIFY_D",
								width: 150,
								headerHozAlign: "center",
								hozAlign: "right",
								formatter: "datetime", formatterParams : {outputFormat : "YYYY-MM-DD HH:mm:ss"},
								sorter: "date",
								headerFilter: "input"
							}, {
								title: "수정자",
								field: "user_MODIFIER",
								width: 150,
								headerHozAlign: "center",
								headerFilter: "input"
							}],
					});
			}
		});

	SubmenuSelector("2", "13201");
}

function nextFocus(next) {
	if (event.keyCode == 13) {
		console.log(next);
		$('#' + next).focus();
	}
}

		function modviewBtn() {
			console.log(user_CODE);
			console.log(user_COMPANY_NAME);
		
			// 수정,삭제 모달창에 더블클릭한 데이터를 렌더링함
			document.getElementById("update_user_CODE").value = user_CODE;
			document.getElementById("update_user_NAME").value = user_NAME;
			
			document.getElementById("update_user_COMPANY").options[3].selected = true;
			console.log(companywhy);
			document.getElementById("update_user_TYPE").value = user_TYPE_NAME;
			document.getElementById("update_user_DEPT").value = user_DEPT_NAME;
			
			if(user_USE_STATUS=="true")
				document.getElementById("update_user_USE_STATUS").checked = true;
        	else
        		document.getElementById("update_user_USE_STATUS").checked = false;
			
			var user_COMPANY_NAME = row.getData().user_COMPANY_NAME;
			console.log(user_COMPANY_NAME);
		}

		function modBtn() {
			datas = {
				USER_CODE : user_CODE,
				USER_NAME : document.getElementById("update_user_NAME").value,
				COMPANY: $("#update_user_COMPANY option:selected").val(),
				USER_TYPE: $("#update_user_TYPE option:selected").val(),
				USER_USE_STATUS : document.getElementById("update_user_USE_STATUS").checked,
				DEPT_CODE : $("#update_user_DEPT option:selected").val()
			};

			$.ajax({
				method : "POST",
				data : datas,
				url : "userManageRest/userManageUpdate?data="
						+ encodeURI(JSON.stringify(datas)),
				success : function(data, testStatus) {
				}
			});

			location.reload();
		}

		// 수정 모달창에서 취소버튼을 누를때 수정 모달창 데이터를 초기화
		function modResetBtn() {
			document.getElementById("update_user_CODE").value = "";
			document.getElementById("update_user_NAME").value = "";
			document.getElementById("update_user_COMPANY").options[0].selected = true;
			document.getElementById("update_user_DEPT").options[0].selected = true;
			document.getElementById("update_user_TYPE").options[0].selected = true;
			document.getElementById("update_user_USE_STATUS").checked = false;
		}

		// 입력버튼을 클릭을 할때 모달창을 여는 이벤트
		$("#custom-btn-default").click(function() {
			$("#insertModal").modal("show");
		});
		
		// 입력모달창이 열릴때 가장 상단의 input에 포커스를 주는 이벤트
		$("#insertModal").on("shown.bs.modal", function () {
			$("#insert_user_CODE").focus();
		});
		
		// 수정모달창이 열릴때 가장 상단의 input에 포커스를 주는 이벤트
		$("#updateModal").on("shown.bs.modal", function () {
			$("#update_user_NAME").focus();
		});
		
		// 저장할지 말지 여부를 묻는 모달창 열기
		function insertModal()
		{
			console.log("test");
			$("#insertYesNo").modal("show");	
		}
		
		function insBtn() {
			var userCode = document.getElementById("insert_user_CODE").value;

			alert(userCode);	

			if (userCode == "") {
				alert("사용자코드는 반드시 입력하셔야 합니다.");
				document.getElementById("insert_user_CODE").focus();
				return;
			}

			if (userCode.length > 10) {
				alert("사용자코드는 10글자 이하로 입력하여주세요.");
				document.getElementById("insert_user_CODE").focus();
				return;
			}

			datas = {
				USER_CODE : userCode,
				USER_NAME: document.getElementById("insert_user_NAME").value,
				COMPANY: $("#insert_user_COMPANY option:selected").val(),
				USER_TYPE: $("#insert_user_TYPE option:selected").val(),
				USER_USE_STATUS : document.getElementById("insert_user_USE_STATUS").checked,
				DEPT_CODE : $("#insert_user_DEPT option:selected").val()
			};

			$.ajax({
				method : "POST",
				data : datas,
				url : "userManageRest/userManageInsert?data="
						+ encodeURI(JSON.stringify(datas)),
				success : function(data, testStatus) {
					if (data == "Overlap")
					{
						alert("중복코드를 입력하셨습니다. 다른 코드를 입력해주세요.");
						document.getElementById("insert_user_CODE").focus();
					}
					else if (data == "Success") {
						//alert("저장 완료 하였습니다.");

						location.reload();
					}
				}
			});
		}

		// 입력 모달창에서 취소버튼을 누를때 입력 모달창 데이터를 초기화
		function insResetBtn() {
			document.getElementById("insert_user_CODE").value = "";
			document.getElementById("insert_user_NAME").value = "";
			document.getElementById("insert_user_COMPANY").options[0].selected = true;
			document.getElementById("insert_user_DEPT").options[0].selected = true;
			document.getElementById("insert_user_TYPE").options[0].selected = true;
			document.getElementById("insert_user_USE_STATUS").checked = false;
		}
		
		// 비번 초기화
function pwReset() {
	conf = confirm("초기화 하시겠습니까?");
	if (!conf)
		return;

	$.ajax({
		method: "POST",
		data: null,
		url: "userManageRest/userManagePW?update_user_CODE=" + document.getElementById("update_user_CODE").value,
		success: function(data, testStatus) {
			console.log(data);
			alert("초기화 성공 하였습니다.");
			location.reload();
		}
	});

}