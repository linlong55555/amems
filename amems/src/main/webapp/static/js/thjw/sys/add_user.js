var dualList = null;
$(document).ready(function(){
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
	
	dualList = $('.demo2').bootstrapDualListbox({
        nonSelectedListLabel: '未选角色：',
        selectedListLabel: '已选角色：',
        preserveSelectionOnMove: 'moved',
        moveOnSelect: false,
        nonSelectedFilter: ''
    });
	initRoleSelect();
	initNav();
	
	 var path = basePath+"/common/ajaxUploadImg";   
     initFileInput1("dzqm",path); 
});
var userImgData = {};
var userImgVail = false;
function initFileInput1(ctrlName,uploadUrl) {      
    var control = $('#' + ctrlName);   
    control.fileinput({  
        language: 'zh', //设置语言  
        uploadUrl: uploadUrl,  //上传地址  
        showUpload: false, //是否显示上传按钮  
        showRemove:true,  
        dropZoneEnabled: false,  
        minImageWidth: 1, //图片的最小宽度
        minImageHeight: 1,//图片的最小高度
        maxImageWidth: 300,//图片的最大宽度
        maxImageHeight: 150,//图片的最大高度
        maxFileSize:50,//单位为kb，如果为0表示不限制文件大小
        maxFileCount: 1, 
        showCaption: true,//是否显示标题  
        autoReplace : true,//是否自动替换当前图片，设置为true时，再次选择文件， 会将当前的文件替换掉。
        allowedPreviewTypes: ['image'],  
        allowedFileTypes: ['image'],  
        allowedFileExtensions:  ['jpg', 'png'],  
        msgFilesTooMany: "选择上传的文件数量 超过允许的最大数值！",
        layoutTemplates : {
        	actionDelete : '',
        	actionUpload : ''
        },
        validateInitialCount:true,
       // initialPreview: initialPreview
	}).on("filebatchselected", function(event, files) {
			userImgVail = false;
			if(files !=""){
				if(userImgData.yswjm){
		    		$(".kv-preview-thumb.file-preview-success").eq(0).remove();
		    	}
				$(this).fileinput("upload");  
			}
	    })  
	    .on("fileuploaded", function(event, data, previewId, index) { 
	    	
	    	if(data){
	    		userImgData.yswjm = data.response.attachments[0].yswjm;
	    		userImgData.wbwjm = data.response.attachments[0].wbwjm;
	        	userImgData.nbwjm = data.response.attachments[0].nbwjm;
	        	userImgData.cflj = data.response.attachments[0].cflj;
	        	userImgData.wjdx = data.response.attachments[0].wjdx;
	        	userImgData.hzm = data.response.attachments[0].hzm;
	        	userImgVail = true;
	    	}
	        $("#path").attr("value",data.response); 
	}).on("fileerror",function(event, data, msg){
		
	}).on("fileclear",function(event, data, msg){
		userImgVail = false;
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

function onCheck(e,treeId,departmentId){
  	$('#departmentId').val(departmentId.id);             //将当前节点的值赋给departmentId
}

//初始化角色清单
function initRoleSelect(){
	//切换部门
	AjaxUtil.ajax({
		   url:basePath+"/sys/user/queryDprtTrees/"+$("#jgdm").val(), 
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
	  dataType: 'json',   //返回值类型  
	  url:basePath+"/sys/role/list/dprtcode/"+$("#jgdm").val(), //请求路径 
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
}


	/** 
	 * js截取字符串，中英文都能用 
	 * @param str：需要截取的字符串 
	 * @param len: 需要截取的长度 
	 */
	function cutstr(str, len) {
	  if(str==null||str==""){
		   	 return str;
	  }
		
     var str_length = 0;
     var str_len = 0;
     str_cut = new String();
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
            return "("+str_cut+")";
        }
	    }
	    //如果给定字符串小于指定长度，则返回源字符串；  
	    if (str_length < len) {
	        return "("+str+")";
	    }
	}

	//获取用户填写的基本信息
	function UserMassage(){
		var user ={
				accountName:$('input[name="username"]').val(),
				username:$('input[name="username1"]').val(),
				drzhid:$('#drzhid').val(),
				realname:$('input[name="realname"]').val(),
				cellphone:$('input[name="cellphone"]').val(),
				phone:$('input[name="phone"]').val(),
				email:$('input[name="email"]').val(),
				sex:$("input:radio[name='sex']:checked").val(),
				rolesId:getRolesId(),
				modelRolesId:getModelRolesId(),
				warehouseRolesId:getWarehouseRolesId()
		};
		return user;
	}
	
	//获取选择的功能角色id
	function getRolesId(){
		var select = document.getElementById("duallistbox_demo2");
		var str = [];
		    for(var i=0;i<select.length;i++){
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
		    for(var i=0;i<select.length;i++){
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
		    for(var i=0;i<select.length;i++){
		        if(select.options[i].selected){
		            str.push(select[i].value);
		        }
		    }
		return str;
	}

//保存添加
function save(){
	  $('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  return false;
	  }
	
	  var jgdm=$('#jgdm').val();
	  var departmentId=$('#departmentId').val();
	  var user=UserMassage();
	  
	  if(jgdm==null || jgdm=="" ){
		  AlertUtil.showErrorMessage("请选择组织机构");
		  return ;
	  }
	  user.departmentId=departmentId;
	  user.jgdm=jgdm;
	  if(userImgVail){
		  user.attachment = userImgData;
	  }
	  startWait();//开始Loading
	 
	  AjaxUtil.ajax({
			url:basePath+"/sys/user/saveUser",
			type: "post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(user),
			dataType:"json",
			success:function(data){
				finishWait();//结束Loading
				if (data.state == "success") {
					AlertUtil.showMessage('添加成功','/sys/user/main?id='+data.id+'&pageParam='+pageParam);
				} 
				if (data.state == "error") {
					AlertUtil.showErrorMessage(data.message);
				} 
			}
	   });
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
				$("#username1").val(data.username);
				$("#drzhid").val(data.id);
				
			  $('#form').data('bootstrapValidator')  
		      .updateStatus('username', 'NOT_VALIDATED',null)  
		      .validateField('username'); 
			  
			  $('#form').data('bootstrapValidator')  
			  .updateStatus('username1', 'NOT_VALIDATED',null)  
			  .validateField('username1'); 
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
	
	$("#username").keyup(function(){
		 $("#username1").val($("#username").val());
	});
