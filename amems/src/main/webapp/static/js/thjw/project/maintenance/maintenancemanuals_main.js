
$(document).ready(function(){
	Navigation(menuCode);//加载导航
	refreshPermission();//刷新按钮权限
	sjtree("");//菜单树
	
	calcLineHeight();
	$(window).resize(function () {
		calcLineHeight();
    });
	
	//清空文件列表
	$("#list").empty();
	$("#list").append("<tr class='text-center'><td colspan=\"7\">暂无数据 No data.</td></tr>");

	//验证目录
	validatorForm1 =  $('#form1').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	mlmc1: {
                validators: {
                	notEmpty: {
                        message: '目录名称不能为空'
                    },
                    stringLength: {
                        max: 100,
                        message: '长度最多不超过100个字符'
                    }
                }
            }
        }
    });
	
	//验证文件
	validatorForm1 =  $('#form2').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	fileuploader: {
                validators: {
                	notEmpty: {
                        message: '文件不能为空'
                    }
                }
            },
        	wbwjm: {
                validators: {
                	notEmpty: {
                        message: '文件名称不能为空'
                    },
                    stringLength: {
                        max: 100,
                        message: '长度最多不超过100个字符'
                    }
                }
            }
        }
    });
	
	//文件
	validatorForm1 =  $('#form3').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	wbwjms: {
                validators: {
                	notEmpty: {
                        message: '文件名称不能为空'
                    },
                    stringLength: {
                        max: 100,
                        message: '长度最多不超过100个字符'
                    }
                }
            }
        }
    });
	
	//初始化日志功能
	logModal.init({code:'D_014'});
	
	});

//验证目录
function validation(){
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            mlmc: {
                validators: {
                	notEmpty: {
                        message: '目录名称不能为空'
                    },
                    stringLength: {
                        max: 100,
                        message: '长度最多不超过个100字符'
                    }
                }
            }
        }
    });
}



var date;
//上传文件
var uploadObj = $("#fileuploader").uploadFile({
	url:basePath+"/project/technicalfile/ajaxUploadFile",
	multiple:true,
	dragDrop:false,
	showDelete:true,
	showStatusAfterSuccess: false,
	formData: {"fileType":"WXSCML"},//参考FileType枚举（技术文件类型）
	fileName: "myfile",
	returnType: "json",
	removeAfterUpload:true,
	showStatusAfterError: false,
	uploadStr:"选择文件",
	statusBarWidth:150,
	dragdropWidth:150,
	onSuccess:function(files,data,xhr,pd)  //上传成功事件，data为后台返回数据
    {
		var sm=data.attachments[0].fileName;
		sm=sm.substring(0,sm.lastIndexOf ('.'));
		var trHtml = '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''+formatUndefine(data.attachments[0].uuid)+'\'>';
		 trHtml = trHtml+'<td><div>';
		 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+formatUndefine(data.attachments[0].uuid)+ '\')" title="删除附件 Delete File">  ';
		 trHtml = trHtml+'</div></td>';
		 trHtml = trHtml+"<td class='text-left'><input type='hidden' name='relativePath' value='"+StringUtil.escapeStr(data.attachments[0].relativePath)+"'><input type='hidden' name='fileName' value='"+StringUtil.escapeStr(data.attachments[0].fileName)+"'>"+StringUtil.escapeStr(data.attachments[0].fileName)+"</td>";
		 trHtml = trHtml+"<td class='text-left'><input type='hidden' name='sm' value='"+StringUtil.escapeStr(sm)+"'><input type='hidden' name='newFileName' value='"+StringUtil.escapeStr(data.attachments[0].newFileName)+"'><input type='hidden' name='fileSize' value='"+formatUndefine(data.attachments[0].wjdx)+"'>"+formatUndefine(bytesToSize(data.attachments[0].wjdx))+"</td>";
		 trHtml = trHtml+"</tr>";	 
		 $('#filelist').append(trHtml);
		 refreshPermission();
    }	,onSubmit : function (files, xhr) {
		var wbwjms  = $.trim($('#wbwjms').val());
		if(wbwjms.length>0){
			if(/^[\\<>/|:\"*?]*$/.test(wbwjms)){
            	$('#wbwjms').focus();
            	AlertUtil.showErrorMessage("文件说明不能包含特殊字符");
            	
            	$('.ajax-file-upload-container').html("");
				uploadObj.selectedFiles -= 1;
				return false;
            } 
            else{
            	return true; 
            }
		}else{
			return true;
		}
	}
	
}); 

/**
 * 删除附件
 * @param newFileName
 */
function delfile(uuid) {
	var  responses = uploadObj.responses;
	var len = uploadObj.responses.length;
	var fileArray = [];
	var waitDelArray = [];
	if(len > 0 ) {
		for(var i =0;i<len;i++){
			if(responses[i].attachments[0].uuid != uuid){
				fileArray.push(responses[i]);
			}
		}
		uploadObj.responses = fileArray;
		uploadObj.selectedFiles -= 1;
		$('#'+uuid).remove();
	}
}

function sjtree(id){
	 $("#using_json").jstree("destroy"); 
	 AjaxUtil.ajax({
		   url:basePath+"/maintenance/maintenancemanuals/directorylist?id="+id+"&mkdm="+$("#mkdm").val(), 
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   success:function(data){
			   if(data!=""){
				   for (var i = 0; i < data.length; i++) {
					   if(data[i].state.selected==true){
						   $('#id').val(data[i].id);
						   AjaxGetDatasWithSearch("auto","desc");
					   }
				   }
			   }
			   createPermitTree(data);  
			    date=data;
	      },
	    }); 
}

function createPermitTree(datastr) {  
    $('#using_json').jstree({
        'core': {
            'data':   datastr ,
            "force_text": true
        }
    }); 

    $('#using_json').bind("select_node.jstree", function (e, data) {
    	   var a = data.node.text.split("(");
    	   var str="";
			for (var i = 0; i < a.length-1; i++) {
				str+=a[i];
			}
		
    	   $('#mlmc1').val(str);
    	   $('#id').val(data.node.id);
    	   AjaxGetDatasWithSearch("auto","desc");
    });
    
}  

//新增文件夹初始化界面
function AssignStore(){
	validation();
	var	sel = $('#using_json').jstree(true).get_selected();
	
	$('#mlmc').val("");
	if(date==""){
			 $('#fmlid').val("#");
				$("#alertModalStore").modal("show");
	}else{
		if(sel!=""){
		 $('#fmlid').val(sel);
			$("#alertModalStore").modal("show");
		}else{
			AlertUtil.showErrorMessage("请选择文件目录!");
		}
	}
	AlertUtil.hideModalFooterMessage();
}

//隐藏Modal时验证销毁重构
$("#alertModalStore").on("hidden.bs.modal", function() {
	 $("#form").data('bootstrapValidator').destroy();
     $('#form').data('bootstrapValidator', null);
     validation();
});

//修改文件夹初始化界面
function updateFile(){
	var	sel = $('#using_json').jstree(true).get_selected();
	
		if(sel!=""){
			$("#Modifyfolder").modal("show");
			AlertUtil.hideModalFooterMessage();
		}else{
			AlertUtil.showErrorMessage("请选择文件目录!");
		}
}

//删除文件夹
function deleteFile(){
	var	sel = $('#using_json').jstree(true).get_selected();
		if(sel!=""){
			
			AlertUtil.showConfirmMessage("您确定要删除吗？",{callback: function(){
			startWait();
			if(checkUpdMt1()){
				
				var fixedCheckItem = {
						id :$.trim($('#id').val()),//id
					};
			// 提交数据
			AjaxUtil.ajax({
				url : basePath+"/maintenance/maintenancemanuals/deleFile",
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify(fixedCheckItem),
				dataType:"json",
				success:function(data){
						finishWait();//结束Loading
					if (data.state == "Success") {
						sjtree("");
						//AlertUtil.showMessage('删除成功!','/maintenance/maintenancemanuals/manage');
						refreshPermission();
					} else {
						AlertUtil.showErrorMessage("删除失败");
					}
				}
				
			});
			}
				
			
		}});
			
		}else{
			AlertUtil.showErrorMessage("请选择文件目录!");
		}
}

//保存文件夹
function saveFile(){
	
	$('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		return false;
	  }	
	
	var fixedCheckItem = {
			id :$.trim($('#id').val()),//父目录ID
			fmlid :$.trim($('#fmlid').val()),//父目录ID
			sm :$.trim($('#sm').val()),//说明
			mlmc : $.trim($('#mlmc').val()),//目录名称
			mkdm : $.trim($('#mkdm').val())//目录代码
		};
	startWait($("#alertModalStore"));
	if(checkUpdMt($('#mlmc').val())){
	// 提交数据
	AjaxUtil.ajax({
		url : basePath+"/maintenance/maintenancemanuals/saveFile",
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(fixedCheckItem),
		dataType:"json",
		modal:$("#alertModalStore"),
		success:function(data){
				finishWait($("#alertModalStore"));//结束Loading
			if (data.state == "Success") {
				$("#alertModalStore").modal("hide");
				
				sjtree($.trim($('#id').val()));
				refreshPermission();
			} else {
				$("#alertModalStore").modal("hide");
				AlertUtil.showErrorMessage("保存失败");
			}
		}
	});
	}

}

//变更文件夹
function ModifyFile(){
	
	$('#form1').data('bootstrapValidator').validate();
	  if(!$('#form1').data('bootstrapValidator').isValid()){
		return false;
	  }	
	
	var fixedCheckItem = {
			id :$.trim($('#id').val()),//id
			mlmc :$.trim($('#mlmc1').val()),//目录名称
		};
	startWait($("#Modifyfolder"));
	// 提交数据
	AjaxUtil.ajax({
		url : basePath+"/maintenance/maintenancemanuals/updateFile",
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(fixedCheckItem),
		dataType:"json",
		modal:$("#Modifyfolder"),
		success:function(data){
				finishWait($("#Modifyfolder"));//结束Loading
			if (data.state == "Success") {
				$("#Modifyfolder").modal("hide");
				sjtree($.trim($('#id').val()));
				refreshPermission();
			} else {
				$("#Modifyfolder").modal("hide");
				AlertUtil.showErrorMessage("保存失败");
			}
		}
	});
}


//检查模块代码是否重复
function checkUpdMt(mlmc){
	var flag = false;
	AjaxUtil.ajax({
		url:basePath+"/maintenance/maintenancemanuals/checkUpdMt",
		type:"post",
		async: false,
		data:{
			id:$("#id").val(),
			mlmc : mlmc,
			mkdm : $("#mkdm").val()
		},
		dataType:"json",
		modal:$("#alertModalStore"),
		success:function(data){
			
			if (data.state == "success") {
				flag = true;
			} else {
				finishWait();//结束Loading
				$("#alertModalStore").modal("hide");
				AlertUtil.showErrorMessage(data.message);
			}
		}
	});
	return flag;
}

//检查模块下是否有文件夹
function checkUpdMt1(){
	var flag = false;
	AjaxUtil.ajax({
		url:basePath+"/maintenance/maintenancemanuals/checkdel",
		type:"post",
		async: false,
		data:{
			'id' : $.trim($('#id').val()),//id
		},
		dataType:"json",
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

function AjaxGetDatasWithSearch(sortType,sequence){
	
	var obj ={};
	
	obj.pagination = {sort:sortType,order:sequence};
	obj.keyword = $.trim($("#keyword_search").val());
	
	obj.id = $('#id').val();
	startWait();
	
	AjaxUtil.ajax({
	   url:basePath+"/maintenance/maintenancemanuals/maintenanceList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(obj),
	   success:function(data){
		    finishWait();
		    if(data.rows !=""){
				   appendContentHtml(data.rows);
				   
				// 标记关键字
				   signByKeyword($("#keyword_search").val(),[3,6,7]);
				   
			   } else {
			
				  $("#list").empty();
				  $("#list").append("<tr class='text-center'><td colspan=\"7\">暂无数据 No data.</td></tr>");
			   }
		    new Sticky({tableId:'wjlb'});
     }
   }); 
	
}

//搜索
function searchRevision(){
	var	sel = $('#using_json').jstree(true).get_selected();
	
	if(sel!=""){
		AjaxGetDatasWithSearch("auto","desc");
	}else{
		AlertUtil.showErrorMessage("请选择文件目录!");
	}
	
}

function appendContentHtml(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   
			if (index % 2 == 0) {
				htmlContent += "<tr bgcolor=\"#f9f9f9\" >";
			} else {
				htmlContent += "<tr bgcolor=\"#fefefe\" >";
			}
		   htmlContent = htmlContent + "<td class='text-center'><input class='monitorItem'  monitorclass='calendar' name='ids' id='ids' type='checkbox' value='"+formatUndefine(row.id)+"' onclick=selectNode('store','list') /><input type='hidden' value='"+StringUtil.escapeStr(row.wbwjm)+"' /><input type='hidden' value='"+StringUtil.escapeStr(row.sm)+"' /></td>";  
		   htmlContent += "<td style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.wbwjm)+"'><a  href='javascript:void(0);' onclick=\"downloadfile('"+row.id+"')\"><img onerror='errorImg(this)' src='"+basePath+"/static/assets/img/"+StringUtil.escapeStr(row.hzm.toLowerCase())+".png' alt='file'><span name='keyword'>"+StringUtil.escapeStr(row.wbwjm)+"</span></a></td>";  
		   htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(bytesToSize(row.wjdx))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.czrid)+"'>"+StringUtil.escapeStr(row.czrid)+"</td>";  
		   htmlContent = htmlContent + "<td class='text-center'>"+StringUtil.escapeStr(row.czsj.substring(0,row.czsj.length-3))+"</td>";  
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.sm)+"'>"+StringUtil.escapeStr(row.sm)+"</td>";  
		   htmlContent = htmlContent + "</tr>";  
	   });
	   $("#list").empty();
	   $("#list").html(htmlContent);
	   refreshPermission();
}

//附件下载
function downloadfile(id){
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
}

function bytesToSize(bytes) {  
 
	if(Math.floor(bytes)>1024){
		bytes=(Math.floor(bytes)/1024).toPrecision(3)+"MB";
	}else{
		bytes=bytes+"KB";
	}
     return bytes;   
}  

//字节自动转化
function errorImg(img) {
    img.src = basePath+"/static/assets/img/txt.png";
    img.onerror = null;
}
//字段排序
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(function() { //重置class
		$(this).removeClass().addClass("sorting");
	});
	$("#" + obj + "_" + "order").removeClass("sorting");
	if (orderStyle == "sorting_asc") {
		$("#" + obj + "_" + "order").addClass("sorting_desc");
	} else {
		$("#" + obj + "_" + "order").addClass("sorting_asc");
	}
	orderStyle = $("#" + obj + "_order").attr("class");
	orderStyle=orderStyle.split("_")[1];
	AjaxGetDatasWithSearch(obj,orderStyle);
}	 

//上传文件
function uploading(){
	 $('#filelist').empty();
	var fileArray = [];
	uploadObj.responses = fileArray;
	uploadObj.selectedFiles = 0;
	
	var	sel = $('#using_json').jstree(true).get_selected();
	
	if(sel!=""){
		$("#uploading").modal("show");
		AlertUtil.hideModalFooterMessage();
	}else{
		AlertUtil.showErrorMessage("请选择文件目录!");
	}
	
}


//保存上传文件
function uploadingFile(){

	var workContentParam = [];
	$("#filelist").find("tr").each(function(){
		var infos ={};
		var index=$(this).index(); //当前行
		var fileName =$("input[name='fileName']").eq(index).val(); //执行对象
		var relativePath =$("input[name='relativePath']").eq(index).val(); //执行对象
		var newFileName =$("input[name='newFileName']").eq(index).val(); //飞机注册号
		var sm =$("input[name='sm']").eq(index).val(); //部件号
		var fileSize =$("input[name='fileSize']").eq(index).val(); //部件号
		
		infos.mainid  = $("#id").val();
		infos.nbwjm  = newFileName;
		infos.cflj  = relativePath;
		infos.yswjm  = fileName;
		infos.wbwjm  = fileName;
		infos.wjdxs  = fileSize;
		workContentParam.push(infos);
	});
	  
		if(workContentParam==""||workContentParam==null){
			AlertUtil.showErrorMessage("上传文件不能为空");
			return false;
		}
	 
	var fixedCheckItem = workContentParam;
	startWait($("#uploading"));
	// 提交数据
	AjaxUtil.ajax({
		url : basePath+"/maintenance/maintenancemanuals/uploadingFile",
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(fixedCheckItem),
		dataType:"json",
		modal:$("#uploading"),
		success:function(data){
				finishWait($("#uploading"));//结束Loading
			if (data.state == "Success") {
				$("#uploading").modal("hide");
				 sjtree($("#id").val());//菜单树
			
				 AlertUtil.showErrorMessage("上传成功");
				 refreshPermission();
				
			} else {
				$("#uploading").modal("hide");
				AlertUtil.showErrorMessage("上传失败");
			}
		}
	});
}

//更名
function updtaeuploading(){
	
	var	sel = $('#using_json').jstree(true).get_selected();
	
	if(sel!=""){
		var ids = [];
		$("#list").find("td input[type='checkbox']:checked").each(function(){
			ids.push( $(this).val());
		});
		if(ids==""){
			AlertUtil.showErrorMessage("请勾选需要修改的项");
			return;
		}
		
		if(ids.length>1){
			AlertUtil.showErrorMessage("只能选择一个项");
			return;
		}
		
		
		$('#wbwjms').val($("#list").find("td input[type='checkbox']:checked").next().val());
		
		$('#description').val($("#list").find("td input[type='checkbox']:checked").next().next().val());
		
		$('#ids').val($("#list").find("td input[type='checkbox']:checked").val());
		$("#updtaeuploading1").modal("show");
		AlertUtil.hideModalFooterMessage();
	}else{
		AlertUtil.showErrorMessage("请选择文件目录!");
	}
	
}

//修改文件名
function updtaeuplFile(){
	$('#form3').data('bootstrapValidator').validate();
	  if(!$('#form3').data('bootstrapValidator').isValid()){
		return false;
	  }
	  
	var fixedCheckItem = {
			'mainid'  : $("#id").val(),
			'id' :$('#ids').val(),//id
			'wbwjm' : $('#wbwjms').val(),//外部文件名
			'sm' : $('#description').val()//外部文件名
		};
	
	startWait($("#updtaeuploading1"));
	// 提交数据
	AjaxUtil.ajax({
		url : basePath+"/maintenance/maintenancemanuals/updtaeuploadingFile",
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(fixedCheckItem),
		dataType:"json",
		modal:$("#updtaeuploading1"),
		success:function(data){
				finishWait($("#updtaeuploading1"));//结束Loading
			if (data.state == "Success") {
				$("#updtaeuploading1").modal("hide");
				sjtree($("#id").val());//菜单树
				 AlertUtil.showErrorMessage("保存成功");
				 refreshPermission();
			} else {
				$("#updtaeuploading1").modal("hide");
				AlertUtil.showErrorMessage("保存失败");
			}
		}
	});
}

//删除文件
function deleteuploading(){
	
	
	var	sel = $('#using_json').jstree(true).get_selected();
	
	if(sel!=""){
		var ids = [];
		$("#list").find("td input[type='checkbox']:checked").each(function(){
			ids+=$(this).val()+",";
		});
		
		if(ids==""){
			AlertUtil.showErrorMessage("请勾选需要删除的项");
			return;
		}
		
		var fixedCheckItem = {
				'mainid'  : $("#id").val(),
				'ids' :ids
			};
		
		 AlertUtil.showConfirmMessage("您确定要删除吗？",{callback: function(){
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url : basePath+"/maintenance/maintenancemanuals/deleteuploadingFile",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(fixedCheckItem),
			dataType:"json",
			success:function(data){
					finishWait();//结束Loading
				if (data.state == "Success") {
					sjtree($("#id").val());//菜单树
				 AlertUtil.showErrorMessage("删除成功");
				 refreshPermission();
					
				} else {
					AlertUtil.showErrorMessage("删除失败");
				}
			}
		});
		 }});
	}else{
		AlertUtil.showErrorMessage("请选择文件目录!");
	}
}


//搜索条件重置
function Close(){
	
	
	 $("#uploading").find("input").each(function() {
		$(this).attr("value", "");
	});
	
	 $("#uploading").find("textarea").each(function(){
		 $(this).val("");
	 });
}

//全选
function checkAll(){
		$("[name=ids]:checkbox").each(function() { 
			 $(this).attr("checked", 'checked'); 
		 });
		 
	}
//全不选
function notCheckAll(){
		 $("[name=ids]:checkbox").each(function() { 
			 $(this).removeAttr("checked"); 
		 });
	}

// 计算分割线高度
function calcLineHeight(){
	// 头高度
    var headerHeight = $('.header').outerHeight();
    // 头部菜单高度
    var headerDownHeight = $('.header-down').outerHeight();
    // 屏幕高度
    var windowHeight = $(window).height();
    // 底部高度
    var footerHeight = $('.footer').outerHeight()||0;
    // 面板标题高度
    var panelHeadingHeight = $('.panel-heading').outerHeight();
    // 版权高度
    var copyrightHeight = $("#copyright:visible").outerHeight()||0;
	var height = windowHeight - headerHeight - headerDownHeight - footerHeight - panelHeadingHeight - copyrightHeight;
	$("#seperateLine").height(height + 8);
	
	$("#leftDiv").height(height - 50);
	$("#rightDiv").height(height - 50);
}
