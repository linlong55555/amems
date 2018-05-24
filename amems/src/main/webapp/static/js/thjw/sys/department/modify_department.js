var savetype='';
var treeid='';
$(document).ready(function(){
	Navigation(menuCode,"修改组织机构","Edit Orgainazations");
	validation();
	ztree();//部门树
	$('#yxqks').datepicker({
		autoclose : true,
		clearBtn : true
	});
	$('#yxqjs').datepicker({
		autoclose : true,
		clearBtn : true
	});
	var superdprtcode=$("#supperdprtcode").val();
	var orgcode=$("#orgcode").val();
	if(superdprtcode===orgcode){
		$("#zczh_max").attr("disabled",false);
		$("#zcfj_max").attr("disabled",false);
		$("#yxqks").attr("disabled",false);
		$("#yxqjs").attr("disabled",false);
		$("#pxh").attr("disabled",false);
		$("#dprtType").attr("disabled",false);
	}
});



function adddprtment() {
	$('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return ;
	  }
	  $('#form1').data('bootstrapValidator').validate();
	  if(!$('#form1').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return ;
	  }
	  	var params={};
	  	var obj={};
	  	obj.id= $.trim($("#id").val());

		obj.lxr1 = $.trim($("#lxr1").val());
		obj.lxdh1 = $.trim($("#lxdh1").val());
		obj.fax1 = $.trim($("#fax1").val());
		obj.email1 = $.trim($("#email1").val());
		obj.lxr2 = $.trim($("#lxr2").val());
		obj.lxdh2 = $.trim($("#lxdh2").val());
		obj.fax2 = $.trim($("#fax2").val());
		obj.email2 = $.trim($("#email2").val());
		obj.dz=$.trim($("#dz").val());
		obj.remark= $.trim($('#remark').val());
		obj.zczh_max=$.trim($('#zczh_max').val());
		obj.zcfj_max=$.trim($('#zcfj_max').val());
		obj.pxh=$.trim($('#pxh').val());		
		obj.yxqks=$.trim($('#yxqks').val());
		obj.yxqjs=$.trim($('#yxqjs').val());
		if($.trim($('#yxqjs').val()) != '' && $.trim($('#yxqks').val())>$.trim($('#yxqjs').val())){
			AlertUtil.showErrorMessage("开始日期不能大于结束日期");
			return false
		}
		obj.deptType=$.trim($('#dprtType').val());
		obj.whbmid=$.trim($('#whbmid').val());
		obj.whrid=$.trim($('#whrid').val());
		var param={};
		param.dprtcode=$.trim($('#bmdm').val());
		param.dprtname=$.trim($('#bmmc').val());
		param.parentid=$.trim($('#sjid').val());
		param.pxh=$.trim($('#dprtpxh').val());
		if(param.parentid==''){
			return ;
		}
		param.id=$.trim($('#id').val());
		param.dprttype=2;
		param.remark=$.trim($('#bz').val());
		param.jdbs = $("#jd input:radio[name='jd']:checked").val(); 	//基地
		$('#form1').data('bootstrapValidator').validate();
		if(savetype=='1'){
			if(checkUpdMt(param)){
				  if(!$('#form1').data('bootstrapValidator').isValid()){
					  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
					return ;
				  }
				params=save();
			}
			else{
				return ;
			}
		}
		if(savetype=='0'){
			if(!$('#form1').data('bootstrapValidator').isValid()){
				  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
				return ;
			  }
			params=EditDprtment();
		}
		if(params==undefined||params==''){
			console.info();
			AjaxUtil.ajax({
				type : 'post',
				url:basePath+"/sys/department/modifyDepart", 
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify(obj),
				success : function(data) {
						AlertUtil.showMessage('操作成功!');
//						ztree($("#id").val());
						savetype='';
						ztree(data);//部门树
						validation();
				}
			});	
		}else{
		params.deptInfo=obj;
		AjaxUtil.ajax({
			type : 'post',
			url:basePath+"/sys/department/modifyDepartment", 
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(params),
			success : function(data) {
					AlertUtil.showMessage('操作成功!');
//					ztree($("#id").val());
					savetype='';
					ztree(data);//部门树
					validation();
			}
		});	
		}
	}
	
function save(){
	var param={};
	param.dprtcode=$.trim($('#bmdm').val());
	param.dprtname=$.trim($('#bmmc').val());
	param.parentid=$.trim($('#sjid').val());
	param.pxh=$.trim($('#dprtpxh').val());
	if(param.parentid==''){
//		AlertUtil.showErrorMessage("请选择上级部门");
		return ;
	}
	param.dprttype=2;
	param.remark=$.trim($('#bz').val());
	param.jdbs = $("#jd input:radio[name='jd']:checked").val(); 	//基地
	return param;
}

//检查角色code是否重复
function checkUpdMt(param){
	var flag = false;
	AjaxUtil.ajax({
		url : basePath+'/sys/department/getDepartmentConut',                     //验证组织机构是否重复
		contentType:"application/json;charset=utf-8",
		data :JSON.stringify(param),
		type:"post",
		async: false,
		dataType : 'json',
		success:function(data){
			if (data.state == "success") {
				flag = true;
			} else {
				finishWait();//结束Loading
				AlertUtil.showErrorMessage(data.message);
			}
		}
	});
	return flag;
}

function ztree(id){
	var menus =''; 
	AjaxUtil.ajax({
		   url:basePath+"/sys/department/queryRoleMenuTree?roleId="+ $("#id").val(), 
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:"",
		   success:function(data){
			   var setting = {view: {showIcon: false, nameIsHTML: false,selectedMulti: false}, check: {enable: false,autoCheckTrigger: true}, data: {
				   simpleData: {
					   enable: true,
					   idKey: "id",
					   pIdKey: "pid",
					   rootPId: null
				   }
			   },
			   callback:{
				   beforeClick : beforeClick,
				   onClick: loadDetail
			   }};
			   var treeObj =   $.fn.zTree.init($("#treeDemo"), setting, data);
			   
			   treeObj.expandAll(true);
			   
			   var nodes = treeObj.transformToArray(treeObj.getNodes());
			   $.each(nodes, function(index, node){
				   node.title = StringUtil.escapeStr(node.name);  
				   treeObj.updateNode(node); 
			   });
			   
			   
			   if(id){
				   // 找到id对应节点
				   var node = treeObj.getNodeByParam("id", id, null);
				   $("#"+node.tId+"_a").click();				  
			   }else{
				   var root = getRoot(); 
				   if(root){
					   $("#"+getRoot().tId+"_a").click();
				   }
			   }
	      }
	    }); 
}
function beforeClick(treeId, treeNode, clickFlag){
	 return treeNode.id != '0'
}	
function loadDetail(event, treeId, treeNode){
	$("#form").data('bootstrapValidator').destroy();
	$('#form').data('bootstrapValidator', null);
	$("#form1").data('bootstrapValidator').destroy();
	$('#form1').data('bootstrapValidator', null);
	treeid=treeNode.id;
	$("#bmmc").attr("disabled", true);
	$("#bmdm").attr("disabled", true);
	savetype='0';
	$("#sjdprtcode").val("");
	$("#sjdprtname").val("");
	$("#sjid").val("");//点击节点id
	if(treeNode.level!=0){
		var obj={};
		obj.id=treeNode.id
		AjaxUtil.ajax({
			type : 'post',
			url:basePath+"/sys/department/getParentDprt", 
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(obj),
			success : function(data) {
				finishWait();//结束Loading
				$("#sjdprtcode").val(data.dprtcode);
				$("#sjdprtname").val(data.dprtname);
			}
		});	
	}
	$("#sjid").val(treeNode.id);//点击节点id
	if(treeNode.id==$("#id").val()){
		$("#dprtpxh").attr("disabled", true);
		$("#bz").attr("disabled", true);
		$("#jd").hide();
	}else{
		$("#dprtpxh").attr("disabled", false);
		$("#bz").attr("disabled", false);
		$("#jd").show();
	}
	var param={};
	param.id=treeNode.id;
	AjaxUtil.ajax({
		type : 'post',
		url:basePath+"/sys/department/getDprt", 
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		data:JSON.stringify(param),
		success : function(data) {
			finishWait();//结束Loading
			$("#bmdm").val(data.dprtcode);
			$("#bmmc").val(data.dprtname);
			$("#bz").val(data.remark);
			$("#jd input:radio[name='jd'][value='"+data.jdbs+"']").attr("checked",true); //基地
			$("#dprtpxh").val(data.pxh);
			validation();
		}
	});	
	
}
function getRoot() {  
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");  
     //返回一个根节点  
    return treeObj.getNodesByFilter(function (node) { return node.level == 0 }, true);  
 }  

function addXjDprtment(){
	$("#jd").show();
	$("#bmmc").attr("disabled", false);
	$("#bmdm").attr("disabled", false);
	$("#dprtpxh").attr("disabled", false);
	$("#bz").attr("disabled", false);
	savetype='1';
	var sjid=$("#sjid").val();
	$("#bmdm").val("");
	$("#bmmc").val("");
	$("#jd input[name='jd']").get(1).checked=true;
	$("#bz").val("");
	$("#dprtpxh").val("");
	var obj={};
	obj.id=treeid;
	AjaxUtil.ajax({
		type : 'post',
		url:basePath+"/sys/department/getDprt", 
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		data:JSON.stringify(obj),
		success : function(data) {
			finishWait();//结束Loading
			$("#sjdprtcode").val(data.dprtcode);
			$("#sjdprtname").val(data.dprtname);			
			validation();
		}
	});
}
//删除
function InvalidDprtment(){
	var param={};
	var xjid=$("#sjid").val();//当前点击节点id
	if(xjid==''){
		AlertUtil.showErrorMessage("请选择部门!");
		return ;
	}
	var id=$("#id").val();
	if(xjid==id){
		param.dprttype='1';
		AlertUtil.showErrorMessage("不能删除组织结构!");
		return ;
	}else{
		param.dprttype='2';
	}
	param.id=xjid;
	var sjid="";//上级部门或组织机构id
	var obj={};
	obj.id=xjid;
	AlertUtil.showConfirmMessage("您确定要删除吗？", {
		callback : function() {
	AjaxUtil.ajax({
		type : 'post',
		url:basePath+"/sys/department/getParentDprt", 
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		data:JSON.stringify(obj),
		success : function(data) {
			finishWait();//结束Loading
			sjid=data.id;
			AjaxUtil.ajax({
				type : 'post',
				url:basePath+"/sys/department/InvalidDepartment", 
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify(param),
				success : function(data) {
					finishWait();//结束Loading
					AlertUtil.showMessage('删除成功!');
					ztree(sjid);
					validation();
				}
			});	
			}
		});	
	  }
	});
}
//修改
function EditDprtment(){
	var param={};
	var xjid=$("#sjid").val();
	if(xjid==''){
//		AlertUtil.showErrorMessage("请选择部门!");
		return ;
	}
	var id=$("#id").val();
	if(xjid==id){
		param.dprttype='1';
//		AlertUtil.showErrorMessage("不能操作组织结构!");
		return ;
	}else{
		param.dprttype='2';
	}

	param.pxh=$.trim($('#dprtpxh').val());
	if(param.parentid==''){
//		AlertUtil.showErrorMessage("请选择上级部门");
		return ;
	}
	param.dprttype=2;
	param.remark=$.trim($('#bz').val());
	param.jdbs = $("#jd input:radio[name='jd']:checked").val(); 	//基地
	startWait();//开始Loading
	param.id=xjid;
	return param;
}

function back(){
	window.location = basePath+"/sys/department/main/";
}
function validation(){
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	dprtcode: {
                validators: {
                	notEmpty: {
                        message: '组织机构代码不能为空'
                    },
                    regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
                        message: '不能输入中文'
                    }
                }
            },
            dprtname: {
                validators: {
                    notEmpty: {
                        message: '组织机构名称不为空'
                    }
                }
            },
            dprttype: {
                validators: {
                	 regexp: {
	                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
	                        message: '不能输入中文'
	                    },
                	   stringLength: {
	                        max: 2,
	                        message: '长度最多不超过2个字符'
	                    }
                }
            },
            lxdh1:{
            	 validators: {
                 	regexp: {
                 		 regexp: /^[^\u4e00-\u9fa5]{0,}$/,	     
 		                 message: '不能输入中文'
                     }
                 }
            },
            lxdh2:{
            	 validators: {
                 	regexp: {
                 		 regexp: /^[^\u4e00-\u9fa5]{0,}$/,	     
 		                 message: '不能输入中文'
                     }
                 }
            },
            fax1:{
                validators: {
                	regexp: {
                		 regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                 message: '传真格式不正确'
                    }
                }
            },
            fax2:{
                validators: {
                	regexp: {
                		 regexp: /^[^\u4e00-\u9fa5]{0,}$/,
		                 message: '传真格式不正确'
                    }
                }
            },
            email1:{
                validators: {
                	regexp: {
                		 regexp: /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/,
		                 message: '邮箱格式不正确'
                    }
                }
            },
            email2:{
                validators: {
                	regexp: {
                		 regexp: /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/,
		                 message: '邮箱格式不正确'
                    }
                }
            },
            pxh:{
                validators: {
                	regexp: {
                        regexp: /^(0|[1-9][0-9]*)$/,
                        message: '请输入整数'
                    }
                }
            },
            zcfj_max:{
                validators: {
                	regexp: {
                        regexp: /^(0|[1-9][0-9]*)$/,
                        message: '请输入整数'
                    }
                }
            },
            zczh_max:{
                validators: {
                	regexp: {
                        regexp: /^(0|[1-9][0-9]*)$/,
                        message: '请输入整数'
                    }
                }
            }	
        }
        });
	validatorForm =  $('#form1').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	bmdm: {
                validators: {
                	notEmpty: {
                        message: '部门代码不能为空'
                    },
                    regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
                        message: '不能输入中文'
                    }
                }
            },
            bmmc: {
                validators: {
                    notEmpty: {
                        message: '部门名称不为空'
                    }
                }
            },
            dprtpxh:{
                validators: {
                	regexp: {
                        regexp: /^(0|[1-9][0-9]*)$/,
                        message: '请输入整数'
                    }
                }
            }
        }
        });
}
$("#alertModal").on("hidden.bs.modal", function() {
	$("#form").data('bootstrapValidator').destroy();
	$('#form').data('bootstrapValidator', null);
	$("#form1").data('bootstrapValidator').destroy();
	$('#form1').data('bootstrapValidator', null);
	validation();
});