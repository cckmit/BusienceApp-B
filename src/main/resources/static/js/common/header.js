var parents_menu
var child_menu
var division_line = ["불량 정보 관리", "출고 반품 관리", "생산 실적 관리(연별)", "수주 조회", "판매 반품 관리", "납품 현황 조회", "작업지시 Tablet", "생산 실적 관리(연별)X"]

$.ajax({
	method: "GET",
	url: "dtl_tbl_select?NEW_TBL_CODE=16",
	success: function(data) {
		parents_menu = data
		
		$.ajax({
			method: "GET",
			url: "userMenuManageRest/userMenuSearch",
			success: function(data) {
				child_menu = data
				
				$(".navbar-busience .navbar-menu").append(
					dynamic_menu(parents_menu, child_menu)
				)
			}
		});
	}
});

function dynamic_menu(parents, child){
	
	var tag = ""
	
	for(let i=0;i<parents.length;i++){
		tag += "<li class='dropdown'>"
				+"<a class='dropdown-toggle' data-toggle='dropdown'><i class='fas "+parents[i].child_TBL_RMARK+"'></i>&nbsp;<span><Strong>"+parents[i].child_TBL_TYPE+"</Strong></span></a>"
				+"<ul class='dropdown-menu'>"
				
		for(let j=0;j<child.length;j++){
			
			let menu_num = parseInt(child[j].menu_Parent_No)
			
			if(menu_num == parents[i].child_TBL_NUM){
				tag += "<li><a href='/"+child[j].menu_PageName+"'>"+child[j].menu_Name+"</a></li>"
				
				if($.inArray(child[j].menu_Name,division_line) != -1){
					tag += "<li class='divider'></li>"
				}
			}
		}
		tag +="</ul>"
			+"</li>"
	}
	
	return tag
}