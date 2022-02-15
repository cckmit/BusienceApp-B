$("#reset_table").click(function(){
	testCheckTable.replaceData();
})

$("#uploadBtn").click(function(){
	if(confirm("업로드 하시겠습니까?")){
		uploadFile("upload-file-form");
	}
})

function checkFileName(str){
	//1. 확장자 체크
	if($.inArray(str.split('.').pop().toLowerCase(), ['pdf']) == -1) {

	    alert('pdf 파일만 업로드 하실 수 있습니다.');
	    return false;
	}

	//2. 파일명에 특수문자 체크
	var pattern =   /[\{\}\/?,;:|*~`!^\+<>@\#$%&\\\=\'\"]/gi;
	if(pattern.test(str) ){
	    alert('파일명에 특수문자를 제거해주세요.');
	    return false;
	}
	return true;
}

function uploadFile(contentID) {
	var formData = new FormData()
	var files = $("#"+contentID)[0].files
	
	//첨부파일이 없는경우
	if(files.length == 0){
		return true;
	}
	//첨부파일이 pdf가 맞는지 확인
	for(var i=0;i<files.length;i++){
		formData.append("uploadFile", files[i]);
		if(!checkFileName(files[i].name)){
			return false;
		}
	}
	var ajaxResult = $.ajax({
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
	    	//초기화
	    	$("#"+contentID).val("");
	    }
	});
	
	return ajaxResult;
}

$("#loadBtn").click(function(){
	pdfViewer($("#load-file").val());
})
function pdfViewer(value){
	//value 는 경로 + 파일명
	var str = "";
	var uri = value.replaceAll("\\", "/");
	
	var fileCallPath = encodeURIComponent(uri)
	
	str += '<iframe style="height: 100%; width: 100%" src="/js/pdfjs/web/viewer.html?file=/display?filePath='+fileCallPath+'"></iframe>'
	
	$(".pdfViewer").append(str);
}