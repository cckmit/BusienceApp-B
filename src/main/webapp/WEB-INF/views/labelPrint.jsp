<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

Selected Device: <select id="selected_device" onchange=onDeviceSelected(this);></select> <br/><br/> 
<input type="button" value="print" onclick="writeToSelectedPrinter()"><br/><br/>
 
<a href="https://www.zebra.com/kr/ko/support-downloads/eula/unrestricted-eula.-227178c9720c025483893483886ea540bd07dd0f9873752cf891686eb495040ba85f97bf163f9fdbc62ce8bbe325bdf8d7c463f51a4ad0a6a906cd075f933a451ef3005a5bc81cd05c75f414a9073d41f63373e50271064.html">
zebra 드라이버 다운로드
</a>
<br/>
 <a href="https://www.zebra.com/kr/ko/support-downloads/printer-software/by-request-software.html#browser-print">
Windows PC용 Browser Print 다운로드
</a>

<script src="/js/labelPrint.js"></script>