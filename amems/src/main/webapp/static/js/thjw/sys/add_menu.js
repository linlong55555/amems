$(document).ready(function(){
	Navigation(menuCode,"新增菜单","Add Menu");
	
	onchangeSystem();
	$('#saveAddMenu').click(function() {
		addMenu() ;
	});
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
                       
        	menuCode: {
                validators: {
                    notEmpty: {
                        message: '菜单编号不能为空'
                    },
                    regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
                        message: '不能输入中文'
                    },
                    stringLength: {
                        max: 50,
                        message: '长度最多不超过50个字符'
                    }
                }
            },
            menuName: {
                validators: {
                    notEmpty: {
                        message: '菜单中文名称不能为空'
                    },
                    stringLength: {
                        max: 30,
                        message: '长度最多不超过30个字符'
                    }
                }
            },
            menuFname: {
                validators: {
                    notEmpty: {
                        message: '菜单英文名称不能为空'
                    },
                    regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
                        message: '不能输入中文'
                    },
                    stringLength: {
                        max:100,
                        message: '长度最多不超过100个字符'
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
		                max: 100,
		                message: '长度最多不超过100个字符'
		            }
                }
            },
            iconPath: {
            	validators: {
            		regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
                        message: '不包含中文'
                    },
                    stringLength: {
                        max: 100,
                        message: '长度最多不超过100个字符'
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


function onchangeSystem(){
	var obj ={};
	obj.xtlx = $('#xtlx').val();
	 AjaxUtil.ajax({
		   url:basePath+"/sys/menu/queryMenuTree1", 
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
			   var setting = {
					   view: {showIcon: false,nameIsHTML: true},
					   check: {enable: false,autoCheckTrigger: true}, 
					   data: {simpleData: {enable: true}},
					   callback:{
						   onClick: onCheck
					   }
			   };
			   var treeObj =   $.fn.zTree.init($("#treeDemo"), setting, data);
			   treeObj.expandAll(true); 
	      },
	      error:function(){
	    	     AlertUtil.showErrorMessage("system error.");
	      }
	    }); 
  	
 }


function  zTree(){

}

function onCheck(e,treeId,treeNode){
  	$('#parentId').val(treeNode.id);             //将当前节点的值赋给menuId
 }


function addMenu() {
	
	 $('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	  }
	var obj={};
	obj.xtlx=$('#xtlx').val();                     // 菜单代码
	obj.menuCode=$('#menuCode').val();                     // 菜单代码
	obj.menuName=$('#menuName').val();                   // 菜单名称
	obj.menuFname=$('#menuFname').val();                   // 菜单名称
	obj.path= $('#path').val();                                               // 菜单代码
	obj.iconPath = $('#iconPath').val();                               // 菜单名称
    obj.menuType=$('#menuType').val();                          //菜单类型
    obj.fullOrder= $('#fullOrder').val();                              // 菜单排序号
    obj.menuOrder=$('#menuOrder').val();                              // 顺序号
	obj.remark=$('#remark').val();                                    // 菜单备注
	obj.parentId=$('#parentId').val();
	// 提交数据
	AjaxUtil.ajax({
		type : 'post',
		url : basePath+'/sys/menu/getMenuConut',                                                             //验证菜单是否重复
		contentType:"application/json;charset=utf-8",
		data :JSON.stringify(obj),
		dataType : 'json',
		success : function(data) {
			if (data <1) {                                           //菜单不存在则可以执行增加操作
				AjaxUtil.ajax({
					type : 'post',
					url:basePath+"/sys/menu/addMenu", 
					contentType:"application/json;charset=utf-8",
					dataType:"json",
					data:JSON.stringify(obj),
					success : function(data2) {
						if (data2 != null) {
							AlertUtil.showMessage("增加成功",'/sys/menu/main?id='+data2+'&pageParam='+pageParam);
						} else {
							parent.showmsg(data.text);
						}
					}
				});
			} else {
				AlertUtil.showErrorMessage("菜单编号或名称已经存在");
			}
		}
	});
}

//返回前页面
function back(){
	 window.location.href =basePath+"/sys/menu/main?pageParam="+pageParam;
}
//只能输入数字和小数
function clearNoNum(obj){
     //先把非数字的都替换掉，除了数字和.
     obj.value = obj.value.replace(/[^\d.]/g,"");
     //必须保证第一个为数字而不是.
     obj.value = obj.value.replace(/^\./g,"");
     //保证只有出现一个.而没有多个.
     obj.value = obj.value.replace(/\.{2,}/g,".");
     //保证.只出现一次，而不能出现两次以上
     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}
