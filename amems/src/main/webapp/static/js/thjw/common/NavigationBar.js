var Navigation = function(menuCode,cn,fn,functionCode,creator, createTime, modifier, modifyTime){
	var documentTitle = "首页";
	var htmlContent = '';
	htmlContent += "<ul class=\"page-breadcrumb breadcrumb\">";
	htmlContent += "<li>";
	htmlContent += "<i class=\"icon-home pull-left\"></i>";
	
	
	if(checkMenuPermissions('index')){
		htmlContent += "<a href=\""+basePath+"/index\" class=\"pull-left\">";
		htmlContent += "<div class=\"font-size-12 line-height-12\">首页</div>";
		htmlContent += "<div class=\"font-size-9 \">Home</div>";
		htmlContent += "</a>";
	}else{
		htmlContent += "<div class=\"pull-left\">";
		htmlContent += "<div class=\"font-size-12 line-height-12\">首页</div>";
		htmlContent += "<div class=\"font-size-9 \">Home</div>";
		htmlContent += "</div>";
	}
	
	if(menuCode){
		var _menuCodeArray = menuCode.split(",");
		if(_menuCodeArray){
			var childMenuList = userMenuListJson;
			var childMenu = null;
			$.each(_menuCodeArray, function(i, menuId){
				$.each(childMenuList, function(index, row){
					if(menuId == row.id){
						childMenu = row;
						//FIXME 不再重复拼接首页，此处首页的menuCode是硬编码，当首页的code更改后需要调整
						if(row.menuCode == 'index'){
							return false;
						}
						htmlContent += "<i class=\"icon-angle-right pull-left\"></i></li>";
						if(row.menuType == 1){
							htmlContent += "<li><div class=\"pull-left\">";
							htmlContent += "<div class=\"font-size-12 line-height-12\">"+row.menuName+"</div>";
							htmlContent += "<div class=\"font-size-9 \">"+row.menuFname+"</div>";
							htmlContent += "</div>";
						}else{
							htmlContent += "<li><a href=\""+basePath+"/"+row.path+"\" class=\"pull-left\">";
							htmlContent += "<div class=\"font-size-12 line-height-12\">"+row.menuName+"</div>";
							htmlContent += "<div class=\"font-size-9 \">"+row.menuFname+"</div></a>";
						}
						documentTitle = row.menuName;
						return false;
					}
				});
				childMenuList = childMenu.children;
			});
		}
	}
	if(cn != "" && cn != undefined && cn != null){
		htmlContent += "<i class=\"icon-angle-right pull-left\"></i></li>";
		htmlContent += "<li >";
		htmlContent += "<a href=\"javascript:void(0);\" class=\"pull-left\">";
		htmlContent += "<div class=\"font-size-12 line-height-12\">"+cn+"</div>";
		htmlContent += "<div class=\"font-size-9 \">"+fn+"</div>";
		htmlContent += "</a></li>";
		documentTitle = cn;
	}else{
		htmlContent += "</li>";
	}
//	htmlContent += "<div class='pull-right'><i id='creator_info_btn' class='icon-info-sign cursor-pointer'/></div></ul>";
	document.title = documentTitle;
	$("#NavigationBar").empty();
	$("#NavigationBar").html(htmlContent);
//	if(functionCode == undefined || functionCode == ""){
//		$('#creator_info_btn').hide();
//		return;
//	}
//	$('#creator_info_btn').webuiPopover({
//		placement:'auto',
//		container: document.body,
//		style:'',
//		padding: 10,
//		trigger: 'click',
//		type:'html',
//		width:180,
//		height:'auto',
//		backdrop:false,
//		content: generateAuthorInfo(functionCode,creator, createTime, modifier, modifyTime)// 这里可以直接写字符串，也可以 是一个函数，该函数返回一个字符串；
//	});
}

function buildNavigationBar(){
	
}


function generateAuthorInfo(functionCode, creator, createTime, modifier, modifyTime){
	var _html = "<ul class='bd list-group margin-bottom-0'>";
	if(functionCode){
		_html += "<li>功能编码："+functionCode+"</li>";
	}
	if(creator){
		_html += "<li>作者："+creator+"</li>";
	}
	if(createTime){
		_html += "<li>日期："+createTime+"</li>";
	}
	if(modifier){
		_html += "<li>修改人："+modifier+"</li>";
	}
	if(modifyTime){
		_html += "<li>修改日期："+modifyTime+"</li>";
	}
	_html += "</ul>";
	return _html;
}