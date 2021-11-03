var division_line = ["불량 정보 관리", "출고 반품 관리", "생산 실적 관리(연별)", "수주 조회", "판매 반품 관리", "납품 현황 조회", "작업지시 Tablet", "생산 실적 관리(연별)X"]

$.ajax({
	method: "GET",
	url: "menuList",
	success: function(data) {
		dynamic_menu(data);
		dynamic_sidebar_menu(data);
	}
});

function dynamic_menu(originData){
	var parents_menu = new Map();
	
	//상위메뉴 모음
	for(let k=0;k<originData.length;k++){
		parents_menu.set(originData[k].menu_Parent_No, [originData[k].menu_Parent_Name, originData[k].menu_Icon, originData[k].menu_Parent_No]);
	}
	var tag = ""
	
	//동적메뉴 생성
	for(let [pKey,pValue] of parents_menu) {
		tag += "<li class='dropdown'>"
				+"<a class='dropdown-toggle' data-toggle='dropdown'><i class='fas "+pValue[1]+"'></i>&nbsp;<span><Strong>"+pValue[0]+"</Strong></span></a>"
				+"<ul class='dropdown-menu'>"
				
		for(let j=0;j<originData.length;j++){
			
			let menu_num = parseInt(originData[j].menu_Parent_No)
			
			if(menu_num == pKey){
				tag += "<li><a href='/"+originData[j].menu_PageName+"'>"+originData[j].menu_Name+"</a></li>"
				
				if($.inArray(originData[j].menu_Name,division_line) != -1){
					tag += "<li class='divider'></li>"
				}
			}
		}
		tag +="</ul>"
			+"</li>"
	}
	
	$(".navbar-busience .navbar-menu").append(tag);
}