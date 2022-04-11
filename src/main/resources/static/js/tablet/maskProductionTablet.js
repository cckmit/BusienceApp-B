function resetInput() { 
		document.getElementById("barcodeInput").value = '';
	}
	

function printName() {
		var input = document.getElementById('barcodeInput').value;
		
		if(document.getElementById("mainLotInput1").value == '') {
			
			document.getElementById("mainLotInput1").value = input;
			
			resetInput();
			
			return;
			
		} else if(document.getElementById("mainLotInput1").value != ''
				&& document.getElementById("mainLotInput2").value == ''){
			
			document.getElementById("mainLotInput2").value = input;
			
			resetInput();
			
			return;
			
		} else if(document.getElementById("mainLotInput1").value != ''
				&& document.getElementById("mainLotInput2").value != ''
				&& document.getElementById("mainLotInput3").value == '') {
			
			document.getElementById("mainLotInput3").value = input;
			
			resetInput();
			
			return;
			
		} else if(document.getElementById("mainLotInput1").value != '' 
				&& document.getElementById("mainLotInput2").value != '' 
				&& document.getElementById("mainLotInput3").value != '' 
				&& document.getElementById("mainLotInput4").value == ''){
			
			document.getElementById("mainLotInput4").value = input;
			
			resetInput();
			
			return;
			
		} else if(document.getElementById("mainLotInput1").value != '' 
				&& document.getElementById("mainLotInput2").value != '' 
				&& document.getElementById("mainLotInput3").value != '' 
				&& document.getElementById("mainLotInput4").value != ''
				&& document.getElementById("mainLotInput5").value == ''){
			
			document.getElementById("mainLotInput5").value = input;
			
			resetInput();
			
			return;
			
		}
	}