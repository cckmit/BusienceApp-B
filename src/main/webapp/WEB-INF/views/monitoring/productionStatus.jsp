<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
	Date nowTime = new Date();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy년 MM월 dd일 a hh:mm:ss");
%>


<!DOCTYPE html>
<html lang="ko"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>8 Dang Monitoring System</title>
   
<head>
    <style>

        .box1 {
            width:200px;height:60px;font-size:25px;text-align:center;color:#ffffff; font-weight:bold;background-color: transparent;
        }

        .box2 {
            width:200px;height:60px;font-size:25px;text-align:center;color:#99ffcc; font-weight:bold;background-color: transparent;
        }

        .box3 {
            width:200px;height:60px;font-size:25px;text-align:center;color:#ffff99; font-weight:bold;background-color: transparent;
        }

        .box4 {
            width:150px;height:60px;font-size:25px;text-align:center;color:#ffffff; font-weight:bold;background-color: transparent;
        }

        .box5 {
            width:150px;height:60px;font-size:25px;text-align:center;color:#99ffcc; font-weight:bold;background-color: transparent;
        }

        .box6 {
            width:150px;height:60px;font-size:25px;text-align:center;color:#ffff99; font-weight:bold;background-color: transparent;
        }

        .bos1 {
            bgcolor="green"; width=150; height="60"; align="center";
        }
    
    </style>

</head>

<!--  <body style="background-color:#001000"> -->
 
<body style="background-color:#303030">


<center>
<br>
<table border=0>
<tr>
<td width=200 align="left">&nbsp;</td>		
<td width=1000  align="center">
<font size="10" style="color: rgb(88,221,178);"> 작업 모니터링</font></p>
</td>
<td width=200 align="right">&nbsp;</td>
</tr>
</table>

<p id="curentTime" style="font-size: 30px" align="right"><font style="color: rgb(188,188,188);"> <%= sf.format(nowTime) %></font> 17:19:49&nbsp;&nbsp;&nbsp;&nbsp;</p>
 
</center>

<br>

<center>
<table border=0>

<tr>
<td>
<table border=0 style="vertical-align:top"/>
<tr>
<td height=50>&nbsp;</td> <td align="center"><font size="5" style="color: rgb(220,221,178);">[  생 산  ]</font></td><td>&nbsp;</td>
</tr>
<tr>
<td bgcolor="#0011ee" width=200 height="60" align="center"><font size="5" color="#ffffff"><b>호기</b></td>
<td bgcolor="#FFff88" width=200 height="60" align="center"><font size="5" color="#000000"><b>모델</b></td>
<td bgcolor="#9900FF" width=200 height="60" align="center"><font size="5" color="#ffffff"><b>생산량</b></td>
</tr>

<tr>
<td bgcolor="003300" width=200 border=0><input id="M001" type="text"  class="box1" readonly></input></td>
<td bgcolor="003300" width=200><input id="itemCode_M001" type="text"  class="box2" readonly></input></td>
<td bgcolor="003300" width=200><input id="qty_M001" type="text"  class="box3" readonly></input></td>
</tr>

<tr>
<td bgcolor="003300" width=200 border=0><input id="M002" type="text"  class="box1" readonly></input></td>
<td bgcolor="003300" width=200><input id="itemCode_M002" type="text"  class="box2" readonly></input></td>
<td bgcolor="003300" width=200><input id="qty_M002" type="text"  class="box3" readonly></input></td>
</tr>

<tr>
<td bgcolor="003300" width=200 border=0><input id="M003" type="text"  class="box1" readonly></input></td>
<td bgcolor="003300" width=200><input id="itemCode_M003" type="text"  class="box2" readonly></input></td>
<td bgcolor="003300" width=200><input id="qty_M003" type="text"  class="box3" readonly></input></td>
</tr>

<tr>
<td bgcolor="003300" width=200 border=0><input id="M004" type="text"  class="box1" readonly></input></td>
<td bgcolor="003300" width=200><input id="itemCode_M004" type="text"  class="box2" readonly></input></td>
<td bgcolor="003300" width=200><input id="qty_M004" type="text"  class="box3" readonly></input></td>
</tr>

<tr>
<td bgcolor="003300" width=200 border=0><input id="M005" type="text"  class="box1" readonly></input></td>
<td bgcolor="003300" width=200><input id="itemCode_M005" type="text"  class="box2" readonly></input></td>
<td bgcolor="003300" width=200><input id="qty_M005" type="text"  class="box3" readonly></input></td>
</tr>

<tr>
<td bgcolor="003300" width=200 border=0><input id="M006" type="text"  class="box1" readonly></input></td>
<td bgcolor="003300" width=200><input id="itemCode_M006" type="text"  class="box2" readonly></input></td>
<td bgcolor="003300" width=200><input id="qty_M006" type="text"  class="box3" readonly></input></td>
</tr>

<tr>
<td bgcolor="003300" width=200 border=0><input id="M007" type="text"  class="box1" readonly></input></td>
<td bgcolor="003300" width=200><input id="itemCode_M007" type="text"  class="box2" readonly></input></td>
<td bgcolor="003300" width=200><input id="qty_M007" type="text"  class="box3" readonly></input></td>
</tr>

<tr>
<td bgcolor="003300" width=200 border=0><input id="M008" type="text"  class="box1" readonly></input></td>
<td bgcolor="003300" width=200><input id="itemCode_M008" type="text"  class="box2" readonly></input></td>
<td bgcolor="003300" width=200><input id="qty_M008" type="text"  class="box3" readonly></input></td>
</tr>

<tr>
<td bgcolor="003300" width=200 border=0><input id="M009" type="text"  class="box1" readonly></input></td>
<td bgcolor="003300" width=200><input id="itemCode_M009" type="text"  class="box2" readonly></input></td>
<td bgcolor="003300" width=200><input id="qty_M009" type="text"  class="box3" readonly></input></td>
</tr>

<tr>
<td bgcolor="003300" width=200 border=0><input id="M010" type="text"  class="box1" readonly></input></td>
<td bgcolor="003300" width=200><input id="itemCode_M010" type="text"  class="box2" readonly></input></td>
<td bgcolor="003300" width=200><input id="qty_M010" type="text"  class="box3" readonly></input></td>
</tr>

</table>
</td>

<td width="150">&nbsp;</td>

<td style="vertical-align:top"/> 

<table>
<tr>
<td height=50>&nbsp;</td> <td align="center" colspan=2><font size="5" style="color: rgb(220,221,178);">[  포 장  ]</font></td><td>&nbsp;</td>
</tr>

<tr>
<td bgcolor="#112233" width=100 height="60" align="center"><font size="5" color="#ffffff"><b>대기수량</b></td>
<td bgcolor="#0011ee" width=80 height="60" align="center"><font size="5" color="#ffffff"><b>호기</b></td>
<td bgcolor="#FFff99" width=120 height="60" align="center"><font size="5" color="#000000"><b>모델</b></td>
<td bgcolor="#FF0099" width=150 height="60" align="center"><font size="5" color="#ffffff"><b>생산량</b></td>
</tr>

<tr>
<td bgcolor="#000055"><input id="T101" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="M101" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="itemCode_M101" type="text"  class="box5" readonly></input></td>
<td bgcolor="#000055"><input id="qty_M101" type="text"  class="box6" readonly></input></td>
</tr>

<tr>
<td bgcolor="#000055"><input id="T102" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="M102" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="itemCode_M102" type="text"  class="box5" readonly></input></td>
<td bgcolor="#000055"><input id="qty_M102" type="text"  class="box6" readonly></input></td>
</tr>

<tr>
<td bgcolor="#000055"><input id="T103" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="M103" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="itemCode_M103" type="text"  class="box5" readonly></input></td>
<td bgcolor="#000055"><input id="qty_M103" type="text"  class="box6" readonly></input></td>
</tr>

<tr>
<td bgcolor="#000055"><input id="T104" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="M104" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="itemCode_M104" type="text"  class="box5" readonly></input></td>
<td bgcolor="#000055"><input id="qty_M104" type="text"  class="box6" readonly></input></td>
</tr>

<tr>
<td bgcolor="#000055"><input id="T105" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="M105" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="itemCode_M105" type="text"  class="box5" readonly></input></td>
<td bgcolor="#000055"><input id="qty_M105" type="text"  class="box6" readonly></input></td>
</tr>

<tr>
<td bgcolor="#000055"><input id="T106" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="M106" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="itemCode_M106" type="text"  class="box5" readonly></input></td>
<td bgcolor="#000055"><input id="qty_M106" type="text"  class="box6" readonly></input></td>
</tr>

<tr>
<td bgcolor="#000055"><input id="T107" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="M107" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="itemCode_M107" type="text"  class="box5" readonly></input></td>
<td bgcolor="#000055"><input id="qty_M107" type="text"  class="box6" readonly></input></td>
</tr>

<tr>
<td bgcolor="#000055"><input id="T108" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="M108" type="text"  class="box4" readonly></input></td>
<td bgcolor="#000055"><input id="itemCode_M108" type="text"  class="box5" readonly></input></td>
<td bgcolor="#000055"><input id="qty_M108" type="text"  class="box6 sampleColor" readonly></input></td>
</tr>
</table>

</td>
</tr>
</table>
</div>



</center>

</body>

<script src="/js/monitoring/productionStatus.js?v=<%=System.currentTimeMillis() %>"></script>
<script>
var machineList = ["M001","M002","M003","M004","M005",
	"M006","M007","M008","M009","M010",
	"M101","M102","M103","M104","M105",
	"M106","M107","M108"]

function productStatus(machineCode){
	var ajaxResult = $.ajax({
		method : "get",
		data : {machineCode : machineCode},
		url : "/productionStatusRest/productionStatusSelect",
		success : function(result) {
			if(result instanceof Object){
				$("#"+machineCode).val(machineCode)
				$("#itemCode_"+machineCode).val(result.cl_ItemCode)
				$("#qty_"+machineCode).val(result.cl_Qty)
				
				//대기수량
				var standby_qty = machineCode.replace('M','T');
				$("#"+standby_qty).val(100);
				
				//색상변경
				if(result.cl_Qty > 10){
					$("#qty_"+machineCode).css("color","red")
				}
			}
		}
	})
	return ajaxResult;
}
	
window.onload = function(){
	for(let i=0; i<machineList.length;i++){
		productStatus(machineList[i])
	
		setInterval(function(){
			productStatus(machineList[i]);
		},10000);
	}
}
</script>