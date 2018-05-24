var oldRoles =[];
var oldModelRoles =[];
var oldWarehouseRoles =[];
var dualList = null;
$(document).ready(function(){
	Navigation(menuCode,"修改用户","Edit");
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username1: {
                validators: {
                	notEmpty: {
                        message: '用户代码不能为空'
                    },
                    regexp: {
                        regexp: /^[\x00-\xff]*$/,
                        message: '不能包含中文'
                    }
                }
            },
            realname: {
                validators: {
                    notEmpty: {
                        message: '姓名不能为空'
                    },                  
                    stringLength: {
                        max: 100,
                        message: '长度最多不超过100个字符'
                    }
                }
            },
            cellphone: {
                validators: {
                	regexp: {
                		regexp: /^[^\u4e00-\u9fa5]{0,20}$/,
                        message: '手机格式不正确'
                    }
                }
            },
            phone: {
                validators: {
		            regexp: {
		            	regexp: /^[^\u4e00-\u9fa5]{0,20}$/,
	                    message: '电话格式不正确'
	                }
                }
            },
            email: {
            	validators: {
            		regexp: {
            			regexp: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
            			message: '电子邮箱格式不正确'
            		}
            	}
            }
            	            
        }
    });
	
	$("input[type=radio][name=sex]").each(function() {
		if ($(this).val() == $("#sex").val()) {
		   $(this).attr("checked", "checked");
	    } 
	});	
	
	if($("#drzhid").val() == ""){
		$("#account_untie_btn").hide();
		$("#account_select_btn").show();
		$("#username").attr("disabled", false);
	}else{
		$("#account_select_btn").hide();
		$("#account_untie_btn").show();
	}
	
	dualList = $('.demo2').bootstrapDualListbox({
        nonSelectedListLabel: '未选角色：',
        selectedListLabel: '已选角色：',
        preserveSelectionOnMove: 'moved',
        moveOnSelect: false,
        nonSelectedFilter: ''
    });
	initNav();
	initRoleSelect();
	 if(attId != null && attId !=""){
    	 //var url = basePath+'/common/preview/'+attId+"_"+1+'?fileName=aa';
    	 
    	/* var url = basePath +"/common/preview/"+attId+"/1";*/
    	 /*$('#dzqm').fileinput({
    		 initialPreview: [
    		                  //预览图片的设置  
    		                  "<img src="+url+" class='file-preview-image' alt='电子签名' title='电子签名'>",
    		          ]
    	 }); */ 
     }
	 var path = basePath+"/common/ajaxUploadImg";   
     initFileInput1("dzqm",path); 
     
    
});

var userImgData = {};
var userImgVail = false;
var userImgType = 1;
function initFileInput1(ctrlName,uploadUrl) {  
	var attId = $("#attId").val();
	var url = basePath+'/common/preview/'+attId+"_"+1+'?fileName=aa';
	var initialPreview =[];
	if(attId){
		initialPreview =[
         //预览图片的设置  
         "<img  src="+url+" class='file-preview-image' alt='电子签名' title='电子签名' style='width:100%'>",
 ]
	}
    var control = $('#' + ctrlName);   
    control.fileinput({  
        language: 'zh', //设置语言  
        uploadUrl: uploadUrl,  //上传地址  
        showUpload: false, //是否显示上传按钮  
        showRemove:true,  
        dropZoneEnabled: false,  
        showCaption: true,//是否显示标题  
        minImageWidth: 1, //图片的最小宽度
        minImageHeight: 1,//图片的最小高度
        maxImageWidth: 300,//图片的最大宽度
        maxImageHeight: 150,//图片的最大高度
        maxFileSize:50,//单位为kb，如果为0表示不限制文件大小
        allowedPreviewTypes: ['image'],  
        allowedFileTypes: ['image'],  
        allowedFileExtensions:  ['jpg', 'png'],  
        layoutTemplates : {
        	actionDelete : '',
        	actionUpload : ''
        },
        maxFileCount: 1,
        initialPreview: initialPreview
		}).on("filebatchselected", function(event, files) {
			userImgVail = true;
			if(files !=""){
				if(userImgData.yswjm){
		    		$(".kv-preview-thumb.file-preview-success").eq(0).remove();
		    	}
				$(this).fileinput("upload");  
				userImgVail = false;
				userImgType = 1;
			}
		    })  
		    .on("fileuploaded", function(event, data) { 
		    	if(data){
		    		userImgData.yswjm = data.response.attachments[0].yswjm;
		    		userImgData.wbwjm = data.response.attachments[0].wbwjm;
		        	userImgData.nbwjm = data.response.attachments[0].nbwjm;
		        	userImgData.cflj = data.response.attachments[0].cflj;
		        	userImgData.wjdx = data.response.attachments[0].wjdx;
		        	userImgData.hzm = data.response.attachments[0].hzm;
		    	}
		        $("#path").attr("value",data.response); 
		}).on("fileerror",function(event, data, msg){
			
		}).on("fileclear",function(event, data, msg){
			userImgVail = false;
			userImgType = 2;
			$(".fileinput-remove-button").css("display","none");
		})
} 

	/**
	 * 初始化页面sheet功能
	 */
	function initNav(){
		$('.nav.nav-tabs > li > a').click(function(){
		    var href=$(this).attr('href');
		    $('#tablist a[href="'+href+'"]').tab('show');
		}); 
	}

	//初始化功能角色清单
	function initRoleSelect(){
		var userToRoleIdsArray = [];
		if(userToRoleIds.length > 0){
			userToRoleIdsArray = userToRoleIds.split(",");
			oldRoles = userToRoleIdsArray;
		}
		
		//切换部门
		var id=$("#departmentId").val();
		AjaxUtil.ajax({
			   url:basePath+"/sys/user/queryDprtTrees/"+$("#jgdm").val(), //请求路径 
			   type: "post",
			   dataType:"json",
			   success:function(data){
				   var setting = {
						   check: {enable: false,autoCheckTrigger: true}, view: {showIcon: false}, 
						   data: {simpleData: {enable: true}},
						   callback:{
							   onClick: onCheck
						   }
				   };
				   var treeObj =   $.fn.zTree.init($("#treeDemo"), setting, data);
				   treeObj.expandAll(true); 
				   treeObj.selectNode(treeObj.getNodeByParam("id",id, null));               //让树形菜单的子节点出于选中状态
		      }
		    }); 
		
		//功能角色
		AjaxUtil.ajax({
		  type:"POST",
		  url:basePath+"/sys/role/list/dprtcode/"+$("#jgdm").val(), //请求路径 
		  dataType: 'json',   //返回值类型  
		  success:function(data){
			  if(data && data.rows && data.rows.length > 0){
					var htmlContent = "";
					$.each(data.rows, function(index,data){
						if($.inArray(data.id+"", userToRoleIdsArray) == -1){
							htmlContent += "<option value=\""+data.id+"\">"+StringUtil.escapeStr(data.roleCode)+" "+StringUtil.escapeStr(data.roleName)+" "+StringUtil.escapeStr(cutstr(data.roleRemark,15))+"</option>"; 
						}else{
							htmlContent += "<option value=\""+data.id+"\" selected=\"selected\">"+StringUtil.escapeStr(data.roleCode)+" "+StringUtil.escapeStr(data.roleName)+" "+StringUtil.escapeStr(cutstr(data.roleRemark,15))+"</option>"; 
						}
					});
					$("#duallistbox_demo2").html(htmlContent);
			  }else{
				  $("#duallistbox_demo2").empty(); 
			  }
			  dualList.bootstrapDualListbox('refresh');
		  }
		});
		
		var userToRoleIdsArray1 = [];
		if(userToModelRoleIds.length > 0){
			userToRoleIdsArray1 = userToModelRoleIds.split(",");
			oldModelRoles = userToRoleIdsArray1;
		}
		
		//机型角色
		AjaxUtil.ajax({
		  type:"POST",
		  url:basePath+"/sys/role/list/modeldprtcode/"+$("#jgdm").val(), //请求路径 
		  dataType: 'json',   //返回值类型  
		  success:function(data){
			  if(data && data.rows && data.rows.length > 0){
					var htmlContent = "";
					$.each(data.rows, function(index,data){
						if($.inArray(data.id+"", userToRoleIdsArray1) == -1){
							htmlContent += "<option value=\""+data.id+"\">"+StringUtil.escapeStr(data.roleCode)+" "+StringUtil.escapeStr(data.roleName)+" "+StringUtil.escapeStr(cutstr(data.roleRemark,15))+"</option>"; 
						}else{
							htmlContent += "<option value=\""+data.id+"\" selected=\"selected\">"+StringUtil.escapeStr(data.roleCode)+" "+StringUtil.escapeStr(data.roleName)+" "+StringUtil.escapeStr(cutstr(data.roleRemark,15))+"</option>"; 
						}
					});
					$("#duallistbox_demo3").html(htmlContent);
			  }else{
				  $("#duallistbox_demo3").empty(); 
			  }
			  dualList.bootstrapDualListbox('refresh');
		  }
		});
		
		var userToRoleIdsArray2 = [];
		if(userToWarehouseRoleIds.length > 0){
			userToRoleIdsArray2 = userToWarehouseRoleIds.split(",");
			oldWarehouseRoles = userToRoleIdsArray2;
		}
		
		//库房角色
		AjaxUtil.ajax({
		  type:"POST",
		  url:basePath+"/sys/role/list/warehousedprtcode/"+$("#jgdm").val(), //请求路径 
		  dataType: 'json',   //返回值类型  
		  success:function(data){
			  if(data && data.rows && data.rows.length > 0){
					var htmlContent = "";
					$.each(data.rows, function(index,data){
						if($.inArray(data.id+"", userToRoleIdsArray2) == -1){
							htmlContent += "<option value=\""+data.id+"\">"+StringUtil.escapeStr(data.roleCode)+" "+StringUtil.escapeStr(data.roleName)+" "+StringUtil.escapeStr(cutstr(data.roleRemark,15))+"</option>"; 
						}else{
							htmlContent += "<option value=\""+data.id+"\" selected=\"selected\">"+StringUtil.escapeStr(data.roleCode)+" "+StringUtil.escapeStr(data.roleName)+" "+StringUtil.escapeStr(cutstr(data.roleRemark,15))+"</option>"; 
						}
					});
					$("#duallistbox_demo4").html(htmlContent);
			  }else{
				  $("#duallistbox_demo4").empty(); 
			  }
			  dualList.bootstrapDualListbox('refresh');
		  }
		});
	}

	/** 
	 * js截取字符串，中英文都能用 
	 * @param str：需要截取的字符串 
	 * @param len: 需要截取的长度 
	 */
	function cutstr(str, len) {
	    var str_length = 0;
	    var str_len = 0;
	    str_cut = new String();
	    if(str==null||str==""){
	    	 return str;
	    }
	    str_len = str.length;
	    for (var i = 0; i < str_len; i++) {
	        a = str.charAt(i);
	        str_length++;
	        if (escape(a).length > 4) {
	            //中文字符的长度经编码之后大于4  
	            str_length++;
	        }
	        str_cut = str_cut.concat(a);
	        if (str_length >= len) {
	            str_cut = str_cut.concat("...");
	            return  "("+str_cut+")";
	        }
	    }
	    //如果给定字符串小于指定长度，则返回源字符串；  
	    if (str_length < len) {
	        return "("+str+")";
	    }
	}

	//初始化角色清单
	function initRoleSelect1(){
		var userToRoleIdsArray = [];
		if(userToRoleIds.length > 0){
			userToRoleIdsArray = userToRoleIds.split(",");
			oldRoles = userToRoleIdsArray;
		}
		
		var id=$("#departmentId").val();
		//切换部门
		AjaxUtil.ajax({
			   url:basePath+"/sys/user/queryDprtTrees/"+$("#jgdm").val(), //请求路径 
			   type: "post",
			   dataType:"json",
			   success:function(data){
				   var setting = {
						   check: {enable: false,autoCheckTrigger: true}, view: {showIcon: false}, 
						   data: {simpleData: {enable: true}},
						   callback:{
							   onClick: onCheck
						   }
				   };
				   var treeObj =   $.fn.zTree.init($("#treeDemo"), setting, data);
				   treeObj.expandAll(true); 
		      }
		    }); 
		
		//功能角色
		AjaxUtil.ajax({
		  type:"POST",
		  url:basePath+"/sys/role/list/dprtcode/"+$("#jgdm").val(), //请求路径 
		  dataType: 'json',   //返回值类型  
		  success:function(data){
			  if(data && data.rows && data.rows.length > 0){
					var htmlContent = "";
					$.each(data.rows, function(index,data){
							htmlContent += "<option value=\""+data.id+"\">"+StringUtil.escapeStr(data.roleCode)+" "+StringUtil.escapeStr(data.roleName)+" "+StringUtil.escapeStr(cutstr(data.roleRemark,15))+"</option>"; 
					});
					$("#duallistbox_demo2").html(htmlContent);
			  }else{
				  $("#duallistbox_demo2").empty(); 
			  }
			  dualList.bootstrapDualListbox('refresh');
		  }
		});
		
		var userToRoleIdsArray1 = [];
		if(userToModelRoleIds.length > 0){
			userToRoleIdsArray1 = userToModelRoleIds.split(",");
			oldModelRoles = userToRoleIdsArray1;
		}
		
		//机型角色
		AjaxUtil.ajax({
		  type:"POST",
		  url:basePath+"/sys/role/list/modeldprtcode/"+$("#jgdm").val(), //请求路径 
		  dataType: 'json',   //返回值类型  
		  success:function(data){
			  if(data && data.rows && data.rows.length > 0){
					var htmlContent = "";
					$.each(data.rows, function(index,data){
							htmlContent += "<option value=\""+data.id+"\">"+StringUtil.escapeStr(data.roleCode)+" "+StringUtil.escapeStr(data.roleName)+" "+StringUtil.escapeStr(cutstr(data.roleRemark,15))+"</option>"; 
					});
					$("#duallistbox_demo3").html(htmlContent);
			  }else{
				  $("#duallistbox_demo3").empty(); 
			  }
			  dualList.bootstrapDualListbox('refresh');
		  }
		});
		
		var userToRoleIdsArray2 = [];
		if(userToWarehouseRoleIds.length > 0){
			userToRoleIdsArray2 = userToWarehouseRoleIds.split(",");
			oldWarehouseRoles = userToRoleIdsArray2;
		}
		
		//库房角色
		AjaxUtil.ajax({
		  type:"POST",
		  url:basePath+"/sys/role/list/warehousedprtcode/"+$("#jgdm").val(), //请求路径 
		  dataType: 'json',   //返回值类型  
		  success:function(data){
			  if(data && data.rows && data.rows.length > 0){
					var htmlContent = "";
					$.each(data.rows, function(index,data){
							htmlContent += "<option value=\""+data.id+"\">"+StringUtil.escapeStr(data.roleCode)+" "+StringUtil.escapeStr(data.roleName)+" "+StringUtil.escapeStr(cutstr(data.roleRemark,15))+"</option>"; 
					});
					$("#duallistbox_demo4").html(htmlContent);
			  }else{
				  $("#duallistbox_demo4").empty(); 
			  }
			  dualList.bootstrapDualListbox('refresh');
		  }
		});
		
		//清空账号id
		$("#drzhid").val("");
	}



	function onCheck(e,treeId,departmentId){
	 	$('#departmentId').val(departmentId.id);             //将当前节点的值赋给departmentId
	}
	//所有父节点的单选框隐藏
	function filter(){
		 var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		   var nodes = zTree.transformToArray(zTree.getNodes());
		   for(var i = 0;i < nodes.length;i++){
			   var id = nodes[i].id;
			   for(var j = 0;j < nodes.length;j++){
				   if(id == nodes[j].pId){
					   node = zTree.getNodeByParam("id", id, null);
					   node.nocheck = true;
					   zTree.updateNode(node);
				   }
			   }
		   } 
	}


	//获取用户填写的基本信息
	function UserMassage(){
		var user ={
				id:$('#userId').val(),
				drzhid:$('#drzhid').val(),
				accountName:$.trim($('input[name="username"]').val()),
				username:$.trim($('input[name="username1"]').val()),
				realname:$.trim($('input[name="realname"]').val()),
				cellphone:$('input[name="cellphone"]').val(),
				phone:$('input[name="phone"]').val(),
				email:$('input[name="email"]').val(),
				sex:$("input:radio[name='sex']:checked").val(),
				departmentId:$("#departmentid").val(),
				rolesId:getRolesId(),
				modelRolesId:getModelRolesId(),
				warehouseRolesId:getWarehouseRolesId(),
				oldRolesId:oldRoles,
				oldModelRolesId:oldModelRoles,
				oldWarehouseRolesId:oldWarehouseRoles
		};
		return user;
	}
	
	//获取选择的功能角色id
	function getRolesId(){
		var select = document.getElementById("duallistbox_demo2");
		var str = [];
		    for(i=0;i<select.length;i++){
		        if(select.options[i].selected){
		            str.push(select[i].value);
		        }
		    }
		return str;
	}
	
	//获取选择的机型角色id
	function getModelRolesId(){
		var select = document.getElementById("duallistbox_demo3");
		var str = [];
		for(i=0;i<select.length;i++){
			if(select.options[i].selected){
				str.push(select[i].value);
			}
		}
		return str;
	}
	//获取选择的库房角色id
	function getWarehouseRolesId(){
		var select = document.getElementById("duallistbox_demo4");
		var str = [];
		for(i=0;i<select.length;i++){
			if(select.options[i].selected){
				str.push(select[i].value);
			}
		}
		return str;
	}

	//修改提交
	function save(){
		  $('#form').data('bootstrapValidator').validate();
		  if(!$('#form').data('bootstrapValidator').isValid()){
			return false;
		  }
		  if(userImgVail){
			  AlertUtil.showErrorMessage("请根据提示填写正确的信息");
			 return false; 
		  }
		  var jgdm=$('#jgdm').val();
		  
		  if(jgdm==null || jgdm=="" ){
			  AlertUtil.showErrorMessage("请选择组织机构");
			  return ;
		  }
		  var departmentId=$('#departmentId').val();
		  var user=UserMassage();
		  user.bmdm=departmentId;
		  user.jgdm=jgdm;
		  if(userImgType == 1 && userImgData.yswjm){
			  user.attachment = userImgData;
		  }
		  user.attId = userImgType;
		  AjaxUtil.ajax({
			url:basePath+"/sys/user/updateUserMessage",
			type: "post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(user),
			dataType:"json",
			success:function(data){
				if (data.status == "success") {
					AlertUtil.showMessage('修改成功','/sys/user/main?id='+data.id+'&pageParam='+pageParam);
				}
				if (data.state == "error") {
					AlertUtil.showErrorMessage(data.message);
				} 
		}
		});
	}
	
	//登入验证
	function validation(){
		if($('#username').val()==null|| $('#username').val()==""){
			AlertUtil.showMessage("用户名不能为空");
			return true;
		}
		
		if($('#realname').val()==null|| $('#realname').val()==""){
			AlertUtil.showMessage("真实姓名不能为空");
			return true;
		}
		return false;
	}	
	
	//返回
	function back(){
		window.location.href =basePath+"/sys/user/main?pageParam="+pageParam;
	}
	
	/**
	 * 打开账号选择界面
	 * @returns
	 */
	function openAccountSelect(){
		open_win_account_select.show({callback:function(data){
			if(data){
				$("#username").val(data.username);
				$("#drzhid").val(data.id);
			}
		}});
	}

	/**
	 * 用户名改变事件
	 * @returns
	 */
	function onUsernameChanged(){
		$("#drzhid").val("");
	}

	function accountuntie(){
	 AlertUtil.showConfirmMessage("确定要解除绑定吗？", {callback: function(){
		$("#account_select_btn").show();
		$("#username").attr("disabled", false);
		$("#account_untie_btn").hide();
		$("#drzhid").val("");
	 }});
	}

