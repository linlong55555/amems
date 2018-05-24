
$(document).ready(function(){
	
	Navigation(menuCode,"修改按钮","Edit Button");
	zTree();
	$('#saveUpdateButton').click(function() {
		saveUpdateButton() ;
	});
	 validatorForm =  $('#form').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	         	buttonName: {
	                validators: {
	                    notEmpty: {
	                        message: '按钮名称不为空'
	                    },
	                    stringLength: {
	                        max: 30,
	                        message: '长度最多不超过30个字符'
	                    }
	                }
	            },
		        	path: {
		                validators: {
		                	regexp: {
		                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                        message: '不包含中文'
		                    },
		                    stringLength: {
		                        max: 90,
		                        message: '长度最多不超过90个字符'
		                    }
		                }
		            },
	            remark: {
	                validators: {
	                	 stringLength: {
		                        max: 65,
		                        message: '长度最多不超过65个字符'
		                    }
	                }
	            }
	        }
	    });
});

function  zTree(){
	var id=$("#id").val();
	 AjaxUtil.ajax({
		   url:basePath+"/sys/button/queryButtonTree1?id="+ id, 
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:"",
		   
		   success:function(data){
			   var setting = {
					   view: {showIcon: false},
					   check: {enable: false,autoCheckTrigger: true}, 
					   data: {simpleData: {enable: true}},
					   callback:{
						   onClick: onCheck
					   }
			   };
			   var treeObj =   $.fn.zTree.init($("#treeDemo"), setting, data);
			   treeObj.expandAll(true); 
			   treeObj.selectNode(treeObj.getNodeByParam("id",id, null));               //让树形菜单的子节点出于选中状态
			   $('#menuId').val(id);                //  更新不选择 直接赋值menuid
		   },
	      error:function(){
	    	     AlertUtil.showErrorMessage("system error.");
	      }
	    }); 
}
function onCheck(e,treeId,treeNode){
  	   $('#menuId').val(treeNode.id);             //将当前节点的文本赋给menuId
 }



function saveUpdateButton() {
	
     var id=$('#buttonId').val(); 
	 var obj={};
	 obj.id=$('#buttonId').val();                                              // 按钮id
	 obj.buttonCode=$('#buttonCode').val();                    // 按钮代码
	 obj.buttonName =$('#buttonName').val();               // 按钮名称
	 obj.path= $('#path').val();                                              //路径
	 obj.menuId= $('#menuId').val();                                 //所属菜单id
	 obj.remark= $('#remark').val();                                  // 按钮备注
	
	 $('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	  }	
	 // 提交数据
		AjaxUtil.ajax({
			type : 'post',
			url:basePath+"/sys/button/updateButton", 
			contentType:"application/json;charset=utf-8",
			 dataType:"json",
			data:JSON.stringify(obj),			
			success : function(data) {
				if (data == 1) {
					AlertUtil.showMessage("修改成功",'/sys/button/main?id='+id+'&pageParam='+pageParam);
				} else {
					AlertUtil.showErrorMessage(data.text);
				}
			}
		});
}

//返回前页面
function back(){
	 window.location.href =basePath+"/sys/button/main?pageParam="+pageParam;
}
