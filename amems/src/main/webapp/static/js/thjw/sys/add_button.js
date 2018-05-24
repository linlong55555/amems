$(document).ready(function(){
	zTree();
	Navigation(menuCode,"新增按钮","Add Button");
	
//	 goPage(1,"auto","desc");//开始的加载默认的内容 
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	buttonCode: {
                validators: {
                	notEmpty: {
                        message: '按钮编号不能为空'
                    },
                    regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
                        message: '不包含中文'
                    },
                    stringLength: {
                        max: 50,
                        message: '长度最多不超过50个字符'
                    }
                }
            },
                       
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
	 AjaxUtil.ajax({
		   url:basePath+"/sys/button/queryButtonTree1", 
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
	      },
	    }); 
}
	  function onCheck(e,treeId,treeNode){
	  	$('#menuId').val(treeNode.id);             //将当前节点的值赋给menuId
     }

function saveAddButton(){
	$('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	  }
	  
	  
	  var menuId=$('#menuId').val();        
	 if(menuId=="" || menuId==null){
		 AlertUtil.showErrorMessage("请选择所属菜单");
		 return;
	 } 
	  
	 var buttonCode = $.trim($("#buttonCode").val());
	 var obj={};
	  buttonCode=$('#buttonCode').val();
	 obj.buttonCode=buttonCode;                    // 按钮代码
	 obj.buttonName =$('#buttonName').val();                  // 按钮名称
	 obj.path= $('#path').val();                                             //路径
	 obj.menuId= $('#menuId').val();                              //所属菜单id
	 obj.remark= $('#remark').val();                  // 按钮备注
		AjaxUtil.ajax({
			type : 'post',
			url : basePath+"/sys/button/getbuttonConut?buttonCode="+buttonCode,                                                             //验证按钮是否重复
			contentType:"application/json;charset=utf-8",
			dataType : 'json',
			success : function(data) {
				if (data ==0) {                                           //按钮不存在则可以执行增加操作
					AjaxUtil.ajax({
						type : 'post',
						url:basePath+"/sys/button/addButton", 
						contentType:"application/json;charset=utf-8",
						 dataType:"json",
						data:JSON.stringify(obj),
						success : function(data2) {
							if (data2!= null) {
								AlertUtil.showMessage("增加成功",'/sys/button/main?id='+data2+'&pageParam='+pageParam);
							} else {
								AlertUtil.showErrorMessage(data.text);
							}
						}
					});
				} else {
					AlertUtil.showErrorMessage("按钮编号已经存在，请修改...");
				}
			}
		});
}
//返回前页面
function back(){
	 window.location.href =basePath+"/sys/button/main?pageParam="+pageParam;
}
