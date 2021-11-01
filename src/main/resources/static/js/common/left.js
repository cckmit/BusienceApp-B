 $(".sidebar-busience .sidebar-menu").on("click","a", function() {
    $(".sidebar-busience li a").removeClass("active");
    $(this).addClass("active");
});
var parents_sidebar_menu = new Array();
var child_sidebar_menu = new Array();

$.ajax({
	method: "GET",
	url: "menuManageRest/MMSP_Search",
	success: function(data) {		
	
		for(let k=0;k<data.length;k++){
			
			if(data[k].user_Code == 'test01'){
				parents_sidebar_menu.push(data[k]);
			}
		}
		
		$.ajax({
			method: "GET",
			url: "userMenuManageRest/userMenuSearch",
			success: function(data) {
				
				for(let j=0;j<data.length;j++){
					
					if(data[j].user_Code == 'test01'){
						child_sidebar_menu.push(data[j]);
					}
				}
				
				
				
				$(".sidebar-busience .sidebar-menu").append(
							
					dynamic_sidebar_menu(parents_sidebar_menu, child_sidebar_menu)
				)
			}
		});
	}
});

function dynamic_sidebar_menu(parents, child){
	var tag = ""
	for(let i=0;i<parents.length;i++){
		tag += '<li>'
				+'<a href="#subPages'+parents[i].child_TBL_NUM+'" data-toggle="collapse" class="collapsed"><span>'+parents[i].child_TBL_TYPE+'</span> <i class="icon-submenu lnr lnr-chevron-left"></i></a>'
					+'<ul id="subPages'+parents[i].child_TBL_NUM+'" class="collapse">'
					
		for(let j=0;j<child.length;j++){
			
			let menu_num = parseInt(child[j].menu_Parent_No)
			
			if(menu_num == parents[i].child_TBL_NUM){
				tag += '<li><a href="/'+child[j].menu_PageName+'"><span>'+child[j].menu_Name+'</span></a></li>'
			}
		}
						
		tag +="</ul>"
			+"</li>"
	}
	return tag
}