var dg1;
var dg2;

$(function() {
	$("#dg1").width(($(window).width() - 30));
	dg1 = datagrid1('dg1');
});

function datagrid1(id) {
	return $('#' + id).tree({    
	    url: 'queryMenuList', 
	    method:'post',
	    animate : true, //定义节点在展开或折叠的时候是否显示动画效果。
	    checkbox:false,	//定义是否在每一个借点之前都显示复选框。
	    onBeforeExpand:function(node,param){
	    	alert(node.id);
	    	 //$('#'+ id).tree('options').url = "queryRoleMenuTree?parentId=" + node.id;
	    },
	    onClick: function(node){
	    	 $("#menuId").val(node.id); // 
	    	 reload1();
		},
	    loadFilter: function(data){    
	        if (data.d){    
	            return data.d;    
	        } else {    
	            return data;    
	        }    
	    }    
	}); 
}


