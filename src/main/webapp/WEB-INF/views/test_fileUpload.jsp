<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="soloView" style="background-color: white;">
<!-- MAIN -->
<div class="main">
	<label for="upload-file-input">Upload your file:</label>
	<input id="upload-file-form" type="file" name="uploadfile" multiple/>
	<button type="button" id="uploadBtn">업로드</button>
</div>
<!-- END MAIN -->
<script type="text/javascript">

$("#reset_table").click(function(){
	testCheckTable.replaceData();
})

$("#uploadBtn").click(function(){
	if(confirm("업로드 하시겠습니까?")){
		uploadFile();
	}
})

function uploadFile() {
	var formData = new FormData()
	var files = $("#upload-file-form")[0].files
	
	var inputData = {
		workOrderONo : $("#workOrderONo").val(),
		itemCode : $("#itemCode").val()
	}
	formData.append("searchData",inputData)
	console.log(files)
	
	for(var i=0;i<files.length;i++){
		formData.append("uploadFile", files[i]);
	}
	console.log(formData);
$.ajax({
    url: "uploadAjax",
    type: "post",
    data: formData,
    processData: false,
    contentType: false,
    beforeSend: function (xhr) {
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
        xhr.setRequestHeader(header, token);
	},
    success: function (data) {
    	alert("업로드 되었습니다.")
    	console.log(data)
    },
    error: function () {
    	alert("오류가 발생했습니다.")
    }
  });
}

</script>
</div>

