var selected_device;
var devices = [];
function setup()
{
	//Get the default device from the application as a first step. Discovery takes longer to complete.
	BrowserPrint.getDefaultDevice("printer", function(device)
		{
			//Add device to list of devices and to html select element
			selected_device = device;
			devices.push(device);
			var html_select = document.getElementById("selected_device");
			var option = document.createElement("option");
			option.text = device.name;
			html_select.add(option);
			
			//Discover any other devices available to the application
			BrowserPrint.getLocalDevices(function(device_list){
				for(var i = 0; i < device_list.length; i++)
				{
					//Add device to list of devices and to html select element
					var device = device_list[i];
					if(!selected_device || device.uid != selected_device.uid)
					{
						devices.push(device);
						var option = document.createElement("option");
						option.text = device.name;
						option.value = device.uid;
						html_select.add(option);
					}
				}
				
			}, function(){alert("Error getting local devices")},"printer");
			
		}, function(error){
			alert("'zebra browser print settings'를 설치하여 프린터를 연결해주세요.\r\n 인터넷 설정 'block-insecure-private-network-requests'를 'disabled'로 변경해주세요.");
		})
}
function writeToSelectedPrinter()
{
	var printCode = "^XA"
					+"^CFJ,50^SEE:UHANGUL.DAT^FS"
					+"^CW1,E:KFONT3.FNT^CI28^FS"
					+"^FO6,20^GB603,71,71^FS"
					+"^FT155,77^A0N,56,69^FB603,1,0^FR^FH\^FD S21111801 ^FS"
					+"^FT27,132^A1N,39,39^FD발행일: 2021-11-18 11:29:08^FS"
					+"^FO6,150^GB603,0,1^FS"
					+"^FO6,261^GB603,0,1^FS"
					+"^FO6,306^GB603,0,1^FS"
					+"^FO6,150^GB0,156,1^FS"
					+"^FO126,150^GB0,156,1^FS"
					+"^FO246,150^GB0,156,1^FS"
					+"^FO367,150^GB0,156,1^FS"
					+"^FO488,150^GB0,156,1^FS"
					+"^FO608,150^GB0,156,1^FS"
					+"^FT9,295^A1N,32,28^FDabcdefg^FS"
					+"^FT129,295^A1N,30,28^FDaaaaaaa^FS"
					+"^FT249,295^A1N,30,28^FD가나다라^FS"
					+"^FT370,295^A1N,30,28^FDa b c d^FS"
					+"^FT491,295^A1N,30,28^FD   쌀^FS"
					+"^BY3,3,69^FT68,384^B3N,N,,N,N"
					+"^FDS2111802^FS"
					+"^XZ";
					
	selected_device.send(printCode, undefined, errorCallback);
}
var errorCallback = function(errorMessage){
	alert(errorMessage+"");	
}

function onDeviceSelected(selected)
{
	for(var i = 0; i < devices.length; ++i){
		if(selected.value == devices[i].uid)
		{
			selected_device = devices[i];
			return;
		}
	}
}

window.onload = setup;