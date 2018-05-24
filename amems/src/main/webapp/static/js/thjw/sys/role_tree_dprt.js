
$(document).ready(function(){
	ztree();//菜单树
	});

function ztree(){
	var menus =''; 
	AjaxUtil.ajax({
		   url:basePath+"/sys/role/queryRoleDprtTree?roleId="+ $("#id").val(), 
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:"",
		   success:function(data){
			   var setting = {view: {showIcon: false},check: {enable: true,
					chkStyle: "checkbox",
					chkboxType: { "Y": "", "N": "" }},data: {simpleData: {enable: true}}};
			   var treeObj =   $.fn.zTree.init($("#treeDemo"), setting, data);
			   treeObj.expandAll(true); 

			   var nodes = treeObj.getCheckedNodes(true);
			   for (var i = 0; i < nodes.length; i++) {
					menus+=nodes[i].id+',';
				}
				if(menus != '')
				menus.substring(0,menus.length-1);
				$("#dprts").val(menus);
			   
	      },
	    
	    }); 
}

//分配组织机构
function save(){
	   
	  var list = '';  
	   
	   var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	   var nodes = treeObj.getCheckedNodes(true);
	   
	   for (var i = 0; i < nodes.length; i++) {
		   list+=nodes[i].id+',';
		}
		if(list != '')
		list.substring(0,list.length-1);
	
	
		startWait();//开始Loading
		// 提交数据
		AjaxUtil.ajax({
			type : 'post',
			cache : false,
			async: false,
			traditional: true,  
			url : basePath+"/sys/role/addRoleDprt",
			data : {
				'nodes' : list,// 跟新前的组织
				'dprts' : $('#dprts').val(),// 跟新后组织
				'roleId' : $('#id').val()//角色id
			},
			dataType : 'json',
			success : function(data) {
				
				if (data.state == "Success") {
					finishWait();//结束Loading
					AlertUtil.showMessage('分配成功!','/sys/role/main');
				} else {
					finishWait();//结束Loading
					AlertUtil.showErrorMessage(data.state);
				}
			}
		});
		
		
}

	

	  
