$('#password').keypress(function(e){
	if(e.keyCode = 13){
		$('#passwordCheck').focus();
	}
})
$('#passwordCheck').keypress(function(e){
	if(e.keyCode = 13){
		$('#passwordCheck').focus();
	}
})

function passwordChange(){
	if($('#password').val().length < 4 || $('#password').val().length > 15){
		alert("비밀번호는 4글자 이상, 15글자 이하만 입력할 수 있습니다.");
		return false;
	}
	if($('#password').val() != $('#passwordCheck').val()){
		alert("비밀번호가 일치하지 않습니다.");
		return false;
	}
	
	var datas = {User_Code : $("#inputId").val(),
				User_Password : $("#password").val()}

	$.ajax({
		method: "put",
		url: "userManageRest/userManagePW",
		data : datas,
		beforeSend: function (xhr) {
           var header = $("meta[name='_csrf_header']").attr("content");
           var token = $("meta[name='_csrf']").attr("content");
           xhr.setRequestHeader(header, token);
		},
		success: function(data) {
			alert("변경 되었습니다.");
			window.close();
		}
	});
}

$('#pwChangeBtn').click(function(){
	passwordChange();
})

$(document).ready(function(){
	$('#password').focus();
})