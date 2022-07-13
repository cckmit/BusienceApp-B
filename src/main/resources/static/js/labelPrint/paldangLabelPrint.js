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
			
		},
		function(error){
				toastr.warning("라벨 프린터를 사용하시려면 'zebra browser print settings'를 실행하여 프린터를 연결해주세요.")
			}
		)
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

function RawMaterialPrinter(jsonDatas)
{	
	var printCode = ""
	for(let j=0;j<jsonDatas.length;j++){
		printCode += "^XA"
					+"^CFJ,50^SEE:UHANGUL.DAT^FS"
					+"^CW1,E:KFONT3.FNT^CI28^FS"
					+"^FT150,40^A1N,30,20^FD"+jsonDatas[j].itemName+"^FS"
					+"^FT150,72^A1N,30,20^FD"+jsonDatas[j].itemSTND1+"^FS"
					+"^FT150,104^A1N,30,20^FD^FS"
					+"^FT150,136^A1N,30,20^FD"+jsonDatas[j].clientName+"^FS"
					+"^FT150,168^A1N,30,20^FD"+jsonDatas[j].itemCode+"^FS"
					+"^FT150,200^A1N,30,20^FD"+jsonDatas[j].lotNo+"^FS"
					+"^FO360,90^BQN,2,5"
					+"^FH^FDLA,"+jsonDatas[j].lotNo+"^FS"
					+"^XZ"
	}
	selected_device.send(printCode, undefined, errorCallback);
}

function CratePrinter(jsonDatas)
{	
	
	var printCode = ""
	
	for(let j=0;j<jsonDatas.length;j++){
		
		printCode += "^XA"
					+"^CFJ,50^SEE:UHANGUL.DAT^FS"
					+"^CW1,E:KFONT3.FNT^CI28^FS"
					+"^FT180,150^A0N,90,90^FB603,1,0^FR^FH^FD*"+jsonDatas[j].c_CrateCode+"*^FS"
					+"^BY5,4,90^FT75,290^B3N,N,,N,N"
					+"^FD"+jsonDatas[j].c_CrateCode+"^FS"
					+"^XZ";
					
		printCode += "^XA"
					+"^CFJ,50^SEE:UHANGUL.DAT^FS"
					+"^CW1,E:KFONT3.FNT^CI28^FS"
					+"^FT180,150^A0N,90,90^FB603,1,0^FR^FH^FD*"+jsonDatas[j].c_CrateCode+"*^FS"
					+"^BY5,4,90^FT75,290^B3N,N,,N,N"
					+"^FD"+jsonDatas[j].c_CrateCode+"^FS"
					+"^XZ";
	}
	selected_device.send(printCode, undefined, errorCallback);
}

function smallPackagingPrinter(LotNo)
{
	
	var printCode = ""
	
	printCode += "^XA"
				+"^CFJ,50^SEE:UHANGUL.DAT^FS"
				+"^CW1,E:KFONT3.FNT^CI28^FS"
				+"^FT240,170^A0N,56,69^FB603,1,0^FR^FH^FD"+LotNo+"^FS"
				+"^BY3,3,69^FT160,270^B3N,N,,N,N"
				+"^FD"+LotNo+"^FS"
				+"^XZ"
	selected_device.send(printCode, undefined, errorCallback);
}

function productionPrinter(jsonDatas)
{	
	
	var printCode = ""
	
		printCode += "^XA"
				+"^CFJ,50^SEE:UHANGUL.DAT^FS"
				+"^CW1,E:KFONT3.FNT^CI28^FS"
				+"^FO4,18^GB604,0,1^FS"
				+"^FO4,18^GB0,370,1^FS"
				+"^FO608,18^GB0,370,1^FS"
				+"^FO4,388^GB604,0,1^FS"
				+"^FO4,88^GB604,0,1^FS"
				+"^FO4,148^GB450,0,1^FS"
				+"^FO4,208^GB604,0,1^FS"
				+"^FO4,268^GB450,0,1^FS"
				+"^FO4,328^GB450,0,1^FS"
				
				+"^FO114,88^GB0,300,1^FS"
				+"^FO454,88^GB0,300,1^FS"
				+"^FO229,268^GB0,60,1^FS"
				+"^FO339,268^GB0,60,1^FS"
				+"^FT470,380^BQN,3,6"
				+"^FH^FDLA,"+jsonDatas.small_Packaging_LotNo+"^FS"
				
				+"^FT27,130^A1,35,25^FD코 드^FS"
				+"^FT27,190^A1,35,25^FH^FD규격1^FS"
				+"^FT27,250^A1,35,25^FH^FD규격2^FS"
				+"^FT27,310^A1,35,25^FH^FD재 질^FS"
				+"^FT255,310^A1,35,25^FH^FD분류1^FS"
				+"^FT27,370^A1,35,25^FH^FD분류2^FS"
				
				+"^FT120,68^A1,50,40^FD"+jsonDatas.itemName+"^FS"
				+"^FT135,130^A1,35,25^FD"+jsonDatas.itemCode+"^FS"
				+"^FT135,190^A1,35,25^FH^FD"+jsonDatas.itemSTND1+"^FS"
				+"^FT135,250^A1,35,25^FH^FD"+jsonDatas.itemSTND2+"^FS"
				+"^FT135,310^A1,35,25^FH^FD"+jsonDatas.itemMaterial_Name+"^FS"
				+"^FT360,310^A1,35,25^FH^FD"+jsonDatas.itemClsfc1_Name+"^FS"
				+"^FT135,370^A1,35,25^FH^FD"+jsonDatas.itemClsfc2_Name+"^FS"
				+"^FT480,165^A1,50,40^FD"+Number(jsonDatas.qty)+"^FS"
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