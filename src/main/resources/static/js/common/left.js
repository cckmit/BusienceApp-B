 $(".sidebar-busience .sidebar-menu").on("click","a", function() {
    $(".sidebar-busience li a").removeClass("active");
    $(this).addClass("active");
});

function dynamic_sidebar_menu(originData){
	var sidbarData = new Array();
	var parents_menu = new Map();
	
	//사용자 커스텀 메뉴 모음
	for(let k=0;k<originData.length;k++){
			
		if(originData[k].user_Code == $('#principal_userCode').text()){
			sidbarData.push(originData[k]);
		}
	}
	//상위메뉴 모음
	for(let k=0;k<sidbarData.length;k++){
		parents_menu.set(sidbarData[k].menu_Parent_No, [sidbarData[k].menu_Parent_Name, sidbarData[k].menu_Icon, sidbarData[k].menu_Parent_No]);
	}
	
	var tag = ""
	
	//동적메뉴 생성
	for(let [pKey,pValue] of parents_menu) {
		tag += '<li>'
				+'<a href="#subPages'+pValue[2]+'" data-toggle="collapse" class="collapsed"><span>'+pValue[0]+'</span> <i class="icon-submenu lnr lnr-chevron-left"></i></a>'
					+'<ul id="subPages'+pValue[2]+'" class="collapse">'
					
		for(let j=0;j<sidbarData.length;j++){
			
			let menu_num = parseInt(sidbarData[j].menu_Parent_No)
			
			if(menu_num == pKey){
				tag += '<li><a href="/'+sidbarData[j].menu_PageName+'"><span>'+sidbarData[j].menu_Name+'</span></a></li>'
			}
		}
						
		tag +="</ul>"
			+"</li>"
	}
	$(".sidebar-busience .sidebar-menu").append(tag);
}