<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="soloView" style="background-color: white;">
	<!-- MAIN -->
	<div class="main">
		<label for="upload-file-input">Upload your file:</label>
		<div class="uploadDiv">
			<input id="upload-file-form" type="file" multiple/>
		</div>
		<button type="button" id="uploadBtn">업로드</button>
		<input id="load-file" type="text">
		<button type="button" id="loadBtn">불러오기</button>
	</div>
	<!-- END MAIN -->
</div>
<script src="/js/fileUpload.js"></script>
