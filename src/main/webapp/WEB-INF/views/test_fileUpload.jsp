<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="soloView" style="background-color: white;">
<!-- MAIN -->
<div class="main">
	<label for="upload-file-input">Upload your file:</label>
	<div class="uploadDiv">
		<input id="upload-file-form" type="file" name="uploadfile" multiple/>
	</div>
	<button type="button" id="uploadBtn">업로드</button>
	<input id="load-file" type="text">
	<button type="button" id="loadBtn">불러오기</button>
</div>
<!-- END MAIN -->
<script type="text/javascript">
var cloneObj = $(".uploadDiv").clone();

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
    	console.log(data);
    	//초기화
    	$(".uploadDiv").html(cloneObj.html());
    },
    error: function () {
    	alert("오류가 발생했습니다.")
    }
  });
}

$("#loadBtn").click(function(){
	var str = "";
	
	var fileCallPath = encodeURIComponent($("#load-file").val())
	
	str += '<iframe src="/js/pdfjs/web/viewer.html?file=/display?filePath='+fileCallPath+'" width="500" height="375"></iframe>'
	console.log(fileCallPath)
	$(".main").append(str);
})
</script>
</div>

