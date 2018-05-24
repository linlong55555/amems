
$(document).ready(function(){
	Navigation(menuCode, "分配菜单按钮", "Assign menu buttons");
	ztree();//菜单树	
});

//分配菜单按钮
function save(){
	
	startWait();
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getChangeCheckedNodes();
	
	if(nodes.length == 0){
		finishWait();
		AlertUtil.showMessage("保存成功!",'/sys/role/main');
		return;
	}
	
	var addBtns = [];
	var delBtns = [];
	var addMenus = [];
	var delMenus = [];
	
	$.each(nodes, function(index, row){
		if(row.type == 2){
			if(row.checked){
				addBtns.push(row.id);
			}else{
				delBtns.push(row.id);
			}
		}else if(row.type == 1){
			if(row.checked){
				addMenus.push(row.id);
			}else{
				delMenus.push(row.id);
			}
		}
	});
	
	AjaxUtil.ajax({
		type : 'post',
 		url :  basePath+"/sys/role/roleMenu/save",
		data : {
			addBtns: addBtns,
			delBtns: delBtns,
			addMenus: addMenus,
			delMenus: delMenus,
			roleId : $("#id").val()
		},
		dataType : 'json',
		success : function(data) {
			$.each(nodes, function(index, row){
				row.checkedOld = row.checked;
			});
			finishWait();//结束Loading
			AlertUtil.showMessage("保存成功!",'/sys/role/main');
		}
	});
}

//加载树
function ztree(){
	var menus =''; 
	AjaxUtil.ajax({
		   url:basePath+"/sys/role/queryRoleMenuTree?roleId="+ $("#id").val(), 
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:"",
		   success:function(data){
			   var setting = {view: {showIcon: false, nameIsHTML: true}, check: {enable: true,autoCheckTrigger: true, chkboxType: { "Y" : "ps", "N" : "s" }},data: {simpleData: {enable: true}}};
			   var treeObj =   $.fn.zTree.init($("#treeDemo"), setting, data);
	      }
	    }); 
	
}

