
$(document).ready(function(){
	Navigation(menuCode);//加载导航
	refreshPermission();//刷新按钮权限
	sjtree("");//菜单树
	refreshRecycledData();//回收站
	//清空文件列表
	$("#list").empty();
	$("#list").append("<tr class='text-center'><td colspan=\"7\">暂无数据 No data.</td></tr>");

	//验证目录
	validatorForm1 =  $('#form1').bootstrapValidator({
        message: '数据不合法!',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	mlmc1: {
                validators: {
                	notEmpty: {
                        message: '目录名称不能为空!'
                    },
                    stringLength: {
                        max: 100,
                        message: '长度最多不超过100个字符!'
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
                        message: '文件不能为空!'
                    }
                }
            },
        	wbwjm: {
                validators: {
                	notEmpty: {
                        message: '文件名称不能为空!'
                    },
                    stringLength: {
                        max: 100,
                        message: '长度最多不超过100个字符!'
                    }
                }
            }
        }
    });
	
	//文件
	validatorForm1 =  $('#form3').bootstrapValidator({
        message: '数据不合法!',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	wbwjms: {
                validators: {
                	notEmpty: {
                        message: '文件名称不能为空!'
                    },
                    stringLength: {
                        max: 100,
                        message: '长度最多不超过100个字符!'
                    }
                }
            }
        }
    });
	//初始化日志功能
	logModal.init({code:'D_014'});
});
var parentId = "";	

//验证目录
function validation(){
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法!',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            mlmc: {
                validators: {
                	notEmpty: {
                        message: '目录名称不能为空!'
                    },
                    stringLength: {
                        max: 100,
                        message: '长度最多不超过个100字符!'
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
	uploadStr:"<i class='fa fa-upload'></i>",
	uploadButtonClass:"btn btn-default btn-uploadnew",
	statusBarWidth:150,
	dragdropWidth:150,
	onSuccess:function(files,data,xhr,pd)  //上传成功事件，data为后台返回数据
    {
		var len = $("#filelist").find("tr").length;
		if (len == 1) {
			if($("#filelist").find("td").length ==1){
				$("#filelist").empty();
			}
		}
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
    }	
	,onSubmit : function (files, xhr) {
		var wbwjms  = $.trim($('#wbwjms').val());
		if(wbwjms.length>0){
			if(/^[\\<>/|:\"*?]*$/.test(wbwjms)){
            	$('#wbwjms').focus();
            	AlertUtil.showErrorMessage("文件说明不能包含特殊字符!");
            	
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
		   url:basePath+"/maintenance/maintenancemanuals/directorylist?id="+encodeURIComponent(id)+"&mkdm="+$("#mkdm").val(), 
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   success:function(data){
			   if(data!=""){
				   for (var i = 0; i < data.length; i++) {
					   if(data[i].state.selected==true){
						   $('#id').val(data[i].id);
						   parentId = data[i].parent=="#"?"":data[i].parent;
						   var a = data[i].text.split("(");
				    	   var str="";
							for (var j = 0; j < a.length-1; j++) {
								str+=a[j];
							}
				    	   $('#mlmc1').val(str);
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
//改变每个节点open属性
if(datastr&&datastr instanceof Array){
  for(var i=0;i<datastr.length;i++){
	  datastr[i].state['opened']=false;
  }
	
	
}
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
    	   parentId = data.node.parent=="#"?"":data.node.parent;
    	   showFile();
    	   AjaxGetDatasWithSearch("auto","desc");
    });
    
}  

//新增一级目录初始化界面
function AssignStoreOne(){
	validation();
	$('#mlmc').val("");
	$('#fmlid').val("#");
	$("#alertModalStore").modal("show");
	AlertUtil.hideModalFooterMessage();
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
			//添加顶级目录
			//$('#fmlid').val("#");
			//$("#alertModalStore").modal("show");
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
			
			if(sel == $("#mkdm").val()){
				AlertUtil.showErrorMessage("根文件夹不能更名！");
			}else{
				$("#Modifyfolder").modal("show");
				AlertUtil.hideModalFooterMessage();
			}
		}else{
			AlertUtil.showErrorMessage("请选择文件目录!");
		}
}

//删除文件夹
function deleteFile(){
	var	sel = $('#using_json').jstree(true).get_selected();
	var	parent = $('#using_json').jstree(true).get_parent(sel);
		if(sel!=""){
			
			if(sel == $("#mkdm").val()){
				AlertUtil.showErrorMessage("根文件夹不能删除！");
			}else{
				AlertUtil.showConfirmMessage("将删除文件夹以及其下面的所有文件、文件夹，您确定要删除吗？",{callback: function(){
					
					startWait();
						
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
								sjtree(parent);
								refreshRecycledData();
								//AlertUtil.showMessage('删除成功!','/maintenance/maintenancemanuals/manage');
								refreshPermission();
							} else {
								AlertUtil.showErrorMessage("删除失败!");
							}
						}
						
					});
					
				}});
			}
		}else{
			AlertUtil.showErrorMessage("请选择文件目录!");
		}
}

var moveAlertModal = {
		id : 'moveAlertModal',
		type : 1,
		folderId : '',
		fileIdList : [],
		fileNameList : [],
		moveFile : function(type){
			this.type = type;
			if(type == 1){
				this.loadFolder();
			}else{
				this.loadFile();
			}
		},
		loadFolder : function(){
			var this_ = this;
			var	sel = $('#using_json').jstree(true).get_selected();
			if(sel != ""){
				if(sel == $("#mkdm").val()){
					AlertUtil.showErrorMessage("根文件夹不能移动 ！");
				}else{
					this_.folderId = sel[0];
					$('#moveCH', $("#"+this_.id)).html('目录名称：');
					$('#moveEng', $("#"+this_.id)).html('Directory Name  ');
					$('#bmlmc', $("#"+this_.id)).html($('#mlmc1').val());
					$("#moveAlertModal").modal("show");
					AlertUtil.hideModalFooterMessage();
					this_.sjtree('');
				}
			}else{
				AlertUtil.showErrorMessage("请选择需要移动的文件目录!");
			}
		},
		loadFile : function(){
			var this_ = this;
			var	sel = $('#using_json').jstree(true).get_selected();
			if(sel!=""){
				var ids = [];
				var fileNameList = [];
				var mlmc1 = '';
				$("#list").find("td input[type='checkbox']:checked").each(function(){
					ids.push($(this).val());
					fileNameList.push($(this).parent().parent().find("input[name='mwbwjm']").val());
					mlmc1 += $(this).parent().parent().find("input[name='mwbwjm']").val() + "<br>";
				});
				
				if(ids.length == 0){
					AlertUtil.showErrorMessage("请勾选需要移动的项!");
					return;
				}
				this_.fileNameList = fileNameList;
				this_.fileIdList = ids;
				$('#moveCH', $("#"+this_.id)).html('文件名称：');
				$('#moveEng', $("#"+this_.id)).html('File Name  ');
				$('#bmlmc', $("#"+this_.id)).html(mlmc1);
				$("#moveAlertModal").modal("show");
				AlertUtil.hideModalFooterMessage();
				this_.sjtree('');
			}else{
				AlertUtil.showErrorMessage("请选择文件目录!");
			}
		},
		sjtree : function(){
			var this_ = this;
			$("#using_alert_json").jstree("destroy"); 
			var searchParam = {
				mkdm : $("#mkdm").val()
			}
			if(this_.type == 1){
				var paramsMap = {};
				if(parentId != ""){
					paramsMap.parentId = parentId;
				}
				paramsMap.hideId = this_.folderId;
				searchParam.paramsMap = paramsMap;
			}
			 AjaxUtil.ajax({
				   url:basePath+"/maintenance/maintenancemanuals/queryWinList", 
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(searchParam),
				   success:function(data){
					   this_.createPermitTree(data);  
			       },
			    }); 
		},
		createPermitTree : function(datastr){
			var this_ = this;
			//改变每个节点open属性
			if(datastr&&datastr instanceof Array){
				 for(var i=0;i<datastr.length;i++){
					  datastr[i].state['opened']=false;
				 }
			}
		   $('#using_alert_json').jstree({
		       'core': {
		           'data': datastr ,
		           "force_text": true

		       }
		   }); 
		},
		doMoveFile : function(){
			var this_ = this;
			var	sel = $('#using_alert_json').jstree(true).get_selected();
			if(sel == ''){
				AlertUtil.showErrorMessage("请选择文件目录!");
				return;
			}
			var url = basePath+"/maintenance/maintenancemanuals/updateFmlid";
			var param = {};
			if(this_.type == 1){
				param.id = this_.folderId;
				param.mkdm = $("#mkdm").val();
				param.mlmc = $("#mlmc1").val();
				param.dprtcode = userJgdm;
				if(sel[0] == "gml"){
					param.fmlid = "#";
				}else{
					param.fmlid = sel[0];
				}
			}else{
				var paramsMap = {};
				paramsMap.idList = this_.fileIdList;
				paramsMap.fileNameList = this_.fileNameList;
				param.paramsMap = paramsMap;
				param.mainid = sel[0];
				url = basePath+"/maintenance/maintenancemanuals/updateMainid";
			}
			
			startWait($("#moveAlertModal"));
			// 提交数据
			AjaxUtil.ajax({
				url :url,
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify(param),
				dataType:"json",
				modal:$("#moveAlertModal"),
				success:function(data){
						finishWait($("#moveAlertModal"));//结束Loading
					if (data.state == "Success") {
						$("#moveAlertModal").modal("hide");
						sjtree($.trim($('#id').val()));
						refreshPermission();
					} else {
//						$("#moveAlertModal").modal("hide");
						AlertUtil.showErrorMessage(data.message);
					}
				}
			});
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
	if(checkUpdMt($('#mlmc').val(), $('#fmlid').val())){
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
				sjtree(data.id);
				refreshPermission();
			} else {
				$("#alertModalStore").modal("hide");
				AlertUtil.showErrorMessage("保存失败!");
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
				AlertUtil.showErrorMessage("保存失败!");
			}
		}
	});
}


//检查模块代码是否重复
function checkUpdMt(mlmc, fmlid){
	var flag = false;
	AjaxUtil.ajax({
		url:basePath+"/maintenance/maintenancemanuals/checkUpdMt",
		type:"post",
		contentType:"application/json;charset=utf-8",
		async: false,
		data:JSON.stringify({
			id:$("#id").val(),
			mlmc : mlmc,
			mkdm : $("#mkdm").val(),
			fmlid : fmlid,
			dprtcode : userJgdm
		}),
		dataType:"json",
		modal:$("#alertModalStore"),
		success:function(data){
			
			if (data.state == "success") {
				flag = true;
			} else {
				finishWait($("#alertModalStore"));//结束Loading
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

//检查文件夹是否存在
function checkExist(obj){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/maintenance/maintenancemanuals/checkExist",
		type:"post",
		data:{id:$("#id").val()},
		dataType:"json",
		success:function(){
			if(typeof(obj)=="function"){
				obj(); 
			}
		}
	});
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
	//重置排序图标
	TableUtil.resetTableSorting("wjlb");
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
		   htmlContent += "<td style='vertical-align: middle;'  align='center'>";
		   htmlContent += formatUndefine(index+1);
		   htmlContent += "<input type='hidden' name='mwbwjm' value='"+StringUtil.escapeStr(row.wbwjm)+"'>";
		   htmlContent += "</td>";  
		   if(row.hzm){
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.wbwjm)+"."+StringUtil.escapeStr(row.hzm)+"'><a  href='javascript:void(0);' onclick=\"downloadfile('"+row.id+"')\"><img onerror='errorImg(this)' src='"+basePath+"/static/assets/img/"+StringUtil.escapeStr((row.hzm||"").toLowerCase())+".png' alt='file'><span name='keyword'>"+StringUtil.escapeStr(row.wbwjm)+"."+StringUtil.escapeStr(row.hzm)+"</span></a></td>";  
		   }else{
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.wbwjm)+"'><a  href='javascript:void(0);' onclick=\"downloadfile('"+row.id+"')\"><img onerror='errorImg(this)' src='"+basePath+"/static/assets/img/"+StringUtil.escapeStr((row.hzm||"").toLowerCase())+".png' alt='file'><span name='keyword'>"+StringUtil.escapeStr(row.wbwjm)+"</span></a></td>";  
		   }
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
	$('#filelist').append("<tr><td colspan=\"3\" class='text-center'>暂无数据 No data.</td></tr>");
	var fileArray = [];
	uploadObj.responses = fileArray;
	uploadObj.selectedFiles = 0;
	
	var	sel = $('#using_json').jstree(true).get_selected();
	if(sel!=""){
		if(sel == $("#mkdm").val()){
			AlertUtil.showErrorMessage("根文件夹不能上传文件，请重新选择文件目录!");
		}else{
			$("#uploading").modal("show");
			AlertUtil.hideModalFooterMessage();
		}
	}else{
		AlertUtil.showErrorMessage("请选择文件目录!");
	}
	
}


//保存上传文件
function uploadingFile(){

	checkExist(function(obj){
		
		var workContentParam = [];
		var wjmList = [];
		var isValid = true;
		
		var len = $("#filelist").find("tr").length;
		if (len == 1) {
			if($("#filelist").find("td").length ==1){
				AlertUtil.showErrorMessage("上传文件不能为空!");
				return false;
			}
		}
		
		$("#filelist").find("tr").each(function(){
			var infos ={};
			var index=$(this).index(); //当前行
			var fileName =$("input[name='fileName']").eq(index).val(); //外部文件名
			var relativePath =$("input[name='relativePath']").eq(index).val(); //上传路径
			var newFileName =$("input[name='newFileName']").eq(index).val(); //内部文件名
			var sm =$("input[name='sm']").eq(index).val(); //说明
			var fileSize =$("input[name='fileSize']").eq(index).val(); //文件大小
			
			if(wjmList.indexOf(fileName) != -1){
				isValid = false; 
				AlertUtil.showErrorMessage("文件名称不能重复!");
				return false;
			}
			wjmList.push(fileName);
			
			infos.mainid  = $("#id").val();
			infos.nbwjm  = newFileName;
			infos.cflj  = relativePath;
			infos.yswjm  = fileName;
			infos.wbwjm  = fileName;
			infos.wjdxs  = fileSize;
			workContentParam.push(infos);
		});
		  
		if(workContentParam==""||workContentParam==null){
			AlertUtil.showErrorMessage("上传文件不能为空!");
			return false;
		}
		if(!isValid){
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
					 AlertUtil.showErrorMessage("上传成功!");
					 refreshPermission();
				} else {
//					$("#uploading").modal("hide");
					AlertUtil.showErrorMessage(data.message);
				}
			}
		});
		
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
			AlertUtil.showErrorMessage("请勾选需要修改的项!");
			return;
		}
		
		if(ids.length>1){
			AlertUtil.showErrorMessage("只能选择一个项!");
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
				 AlertUtil.showErrorMessage("保存成功!");
				 refreshPermission();
			} else {
				$("#updtaeuploading1").modal("hide");
				AlertUtil.showErrorMessage("保存失败!");
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
			AlertUtil.showErrorMessage("请勾选需要删除的项!");
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
					refreshRecycledData();
				 AlertUtil.showErrorMessage("删除成功!");
				 refreshPermission();
					
				} else {
					AlertUtil.showErrorMessage("删除失败!");
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

function documentToggle(obj){
	var i = $(obj);
	if(i.hasClass("icon-caret-left")){
		i.removeClass("icon-caret-left").addClass("icon-caret-right");
		$("#left_div").hide();
		$("#right_div").removeClass("col-lg-9 col-sm-8 col-xs-12").addClass("col-xs-12");
	}else{
		i.removeClass("icon-caret-right").addClass("icon-caret-left");
		$("#left_div").show();
		$("#right_div").removeClass("col-xs-12").addClass("col-lg-9 col-sm-8 col-xs-12");
	}
	App.resizeHeight();
}
//展示回收站信息
function showTrash(){
	if($("#removeInfo").css("display")=="none"){
		$("#fileInfo").css("display","none");
		$("#removeInfo").css("display","block");
		App.resizeHeight();
	}
}
//刷新回收站数据
function refreshRecycledData(){
	loadRecycledData("auto","desc");
}
//加载回收站数据
function loadRecycledData(sortType, sequence){
	startWait();
	AjaxUtil.ajax({
	   url:basePath+"/maintenance/maintenancemanuals/recycledlist",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify({
		   mkdm : $("#mkdm").val(),
		   dprtcode : userJgdm,
		   paramsMap : {
			   keyword : $.trim($("#recycled_keyword_search").val()),
		   },
		   pagination : {
			   sort:sortType,
			   order:sequence
		   },
	   }),
	   success:function(data){
		    finishWait();
		    buildRecycledHtml(data);
		    // 标记关键字
		    signByKeyword($.trim($("#recycled_keyword_search").val()),[4]);
		    new Sticky({tableId:'recycled_table'});
	   }
   }); 
}
//拼接回收站数据
function buildRecycledHtml(list){
	var htmlContent = '';
	if(list && list.length > 0){
		$.each(list,function(index,row){
			
			// 小图标路径
			var imgsrc = '';
			if(row.wjlx == 1){
				imgsrc = basePath+"/static/assets/img/sf.png";
			}else if(row.wjlx == 2){
				imgsrc = basePath+"/static/assets/img/"+StringUtil.escapeStr((row.hzm||"").toLowerCase())+".png'";
			}
			
			// 原始位置
			var yswz = row.yswz;
			if(yswz && yswz.length > 0){
				// 截取第一个/
				yswz = yswz.replace("/","");
				// 文件夹删除最后一个本身路径
				if(row.wjlx == 1){
					yswz = yswz.substr(0, yswz.lastIndexOf("/"+row.wjmc));
				}
			}
			
			htmlContent += "<tr>";
		    htmlContent += "<td class='text-center'><input name='ids' type='checkbox' value='"+StringUtil.escapeStr(row.id)+"' wjlx='"+StringUtil.escapeStr(row.wjlx)+"' onclick=selectNode('store','list') /></td>";  
		    htmlContent += "<td style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
		    htmlContent += "<td class='text-center'>"+DicAndEnumUtil.getEnumName('documentTypeEnum', row.wjlx)+"</td>";  
		    htmlContent += "<td class='text-left'><img onerror='errorImg(this)' src='"+imgsrc+"' alt='file'><span name='keyword'>"+row.wjmc+"</span></td>";  
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(yswz)+"</td>";  
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.czr ? row.czr.displayName : '')+"</td>";  
		    htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.czsj)+"</td>";  
		    htmlContent += "</tr>";  
	    });
	}else{
		htmlContent += "<tr class='text-center'><td colspan=\"7\">暂无数据 No data.</td></tr>";
	}
	$("#recycleCount").html(list.length);
	$("#recycled_list").html(htmlContent);
}
//字段排序
function recycledOrderBy(sortColumn) {
	var $obj = $("th[name='column_"+sortColumn+"']");
	var orderStyle = $obj.attr("class");
	$(".sorting_desc", $("#recycled_table")).removeClass("sorting_desc").addClass("sorting");
	$(".sorting_asc", $("#recycled_table")).removeClass("sorting_asc").addClass("sorting");
	var orderType = "asc";
	if (orderStyle.indexOf ("sorting_asc")>=0) {
		$obj.removeClass("sorting").addClass("sorting_desc");
		orderType = "desc";
	} else {
		$obj.removeClass("sorting").addClass("sorting_asc");
		orderType = "asc";
	}
	loadRecycledData(sortColumn, orderType);
}
//撤销
function restore(){
	var recycles = [];
	$("#recycled_list").find("td input[type='checkbox']:checked").each(function(){
		var obj = {};
		obj.id = $(this).val();
		obj.wjlx = $(this).attr("wjlx");
		recycles.push(obj);
	});
	
	if(recycles.length == 0){
		AlertUtil.showErrorMessage("请勾选需要撤销的项!");
		return;
	}
	
	AlertUtil.showConfirmMessage("您确定要撤销吗？",{callback: function(){
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url : basePath+"/maintenance/maintenancemanuals/resotre",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(recycles),
			dataType:"json",
			success:function(data){
				finishWait();
				sjtree($("#id").val());//菜单树
				refreshRecycledData();
				AlertUtil.showErrorMessage("撤销成功!");
				refreshPermission();
			}
		});
	}});
}
//确认删除
function confirmDelete(){
	var recycles = [];
	$("#recycled_list").find("td input[type='checkbox']:checked").each(function(){
		var obj = {};
		obj.id = $(this).val();
		obj.wjlx = $(this).attr("wjlx");
		recycles.push(obj);
	});
	
	if(recycles.length == 0){
		AlertUtil.showErrorMessage("请勾选需要删除的项!");
		return;
	}
	
	AlertUtil.showConfirmMessage("您确定要删除吗？",{callback: function(){
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url : basePath+"/maintenance/maintenancemanuals/confirmdelete",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(recycles),
			dataType:"json",
			success:function(data){
				finishWait();
				refreshRecycledData();
				AlertUtil.showErrorMessage("删除成功!");
				refreshPermission();
			}
		});
	}});
}
//清空全部
function empty(){
	AlertUtil.showConfirmMessage("您确定要清空吗？",{callback: function(){
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url : basePath+"/maintenance/maintenancemanuals/empty",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify({
				mkdm : $("#mkdm").val(),
			    dprtcode : userJgdm,
			}),
			dataType:"json",
			success:function(data){
				finishWait();
				refreshRecycledData();
				AlertUtil.showErrorMessage("清空成功!");
				refreshPermission();
			}
		});
	}});
}
//展示文件列表
function showFile(){
	if($("#fileInfo").css("display")=="none"){
		$("#removeInfo").css("display","none");
		$("#fileInfo").css("display","block");
		App.resizeHeight();
	}
}
function customResizeHeight(bodyHeight, tableHeight){
	var panelFooter=$("#document_leftBody").siblings(".panel-footer").outerHeight()||0;
	$("#document_leftBody").css({"height":""});
	var leftRowHeight=$("#document_leftBody .row-height").outerHeight()||0+8;
	var using_json_parent=bodyHeight+8-panelFooter-leftRowHeight;
	$("#using_json_parent").css({"height":using_json_parent+"px","overflow":"auto"});
	var rowHeight=$(".document_rightBody:visible .row-height").outerHeight()||0;
	var panelHeadingHeight = $('.panel-heading').outerHeight()||0;
	if(panelHeadingHeight<35){
		if(navigator.userAgent.indexOf("Chrome") > -1){
			bodyHeight = bodyHeight + panelHeadingHeight - 39;
		}else{
			bodyHeight = bodyHeight + panelHeadingHeight - 35;
		}
	
    }
	
	bodyHeight=bodyHeight-10;
	 if(navigator.userAgent.indexOf("Chrome") > -1){
		 bodyHeight=bodyHeight+10
	 }
	var tableHeight=bodyHeight+17-rowHeight-10;
	 // chrome浏览器
    if(navigator.userAgent.indexOf("Chrome") > -1){
    	tableHeight -= 10;
    	
    }
    $(".document_rightBody:visible ").removeAttr("style");
    $(".document_rightBody:visible ").css({"height":bodyHeight+"px !important","overflow":"auto"});
	$(".document_rightBody:visible ").find("table").parent("div").css({"height":tableHeight+"px"});	
	 $("#floatDiv").height($(".page-content:first>div").height()-13);
	    try{
	    	$("#logTableDiv").height($("#log_pagination").offset().top-$("#logTableDiv").offset().top - 5);
	    }catch(e){}
}

//导入文件夹
var importAlertModal = {
		id : 'importAlertModal',
		type : 1,
		folderId : '',
		fileIdList : [],
		fileNameList : [],
		importFile : function(type){
			//加载目录
			this.loadFolder();
			//记载上传组件
			attachmentsSingleZipUtil.initAttachment(
					"doczip_attachments_single"//主div的id
					,null//附件
					,'doczip'//文件夹存放路径
					,true//true可编辑,false不可编辑
			);
			$("#fileuploaderSingle").show();
		},
		loadFolder : function(){
			var this_ = this;
			$("#importAlertModal").modal("show");
			AlertUtil.hideModalFooterMessage();
			this_.sjtree('');
		},
		sjtree : function(){
			var this_ = this;
			$("#import_using_alert_json").jstree("destroy"); 
			var searchParam = {
					mkdm : $("#mkdm").val()
			}
			if(this_.type == 1){
				var paramsMap = {};
				if(parentId != ""){
					paramsMap.parentId = parentId;
				}
				paramsMap.hideId = this_.folderId;
				searchParam.paramsMap = paramsMap;
			}
			AjaxUtil.ajax({
				url:basePath+"/maintenance/maintenancemanuals/queryWinList", 
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify(searchParam),
				success:function(data){
					this_.createPermitTree(data);  
				},
			}); 
		},
		createPermitTree : function(datastr){
			var this_ = this;
			//改变每个节点open属性
			if(datastr&&datastr instanceof Array){
				for(var i=0;i<datastr.length;i++){
					datastr[i].state['opened']=false;
				}
			}
			$('#import_using_alert_json').jstree({
				'core': {
					'data': datastr ,
					"force_text": true
		
				}
			}); 
		},
		doImportFile : function(){
			var this_ = this;
			
			var param = {mkdm : $("#mkdm").val()}
			
			//获取父目录
			var	sel = $('#import_using_alert_json').jstree(true).get_selected();
			if(sel == ''){
				AlertUtil.showModalFooterMessage("请选择文件目录!");
				return;
			}
			//获取上传文件压缩包
			var doczipAttachment = attachmentsSingleZipUtil.getAttachment('doczip_attachments_single');
			if(doczipAttachment != null && doczipAttachment.wjdx != null && doczipAttachment.wjdx != ''){
				param.docZipAttachment = doczipAttachment;
			}else{
				AlertUtil.showModalFooterMessage("请上传文件压缩包!");
				return;
			}
			
			var url = basePath+"/maintenance/maintenancemanuals/saveImportZipFile";//导入文档压缩包
			if(this_.type == 1){
				param.dprtcode = userJgdm;
				if(sel[0] == "gml"){
					param.fmlid = "#";
				}else{
					param.fmlid = sel[0];
				}
			}
			
			startWait($("#moveAlertModal"));
			// 提交数据
			AjaxUtil.ajax({
				url :url,
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify(param),
				dataType:"json",
				modal:$("#importAlertModal"),
				success:function(data){
					finishWait($("#importAlertModal"));//结束Loading
					
					if (data.state == "success") {
						$("#importAlertModal").modal("hide");
						sjtree($.trim($('#id').val()));
						refreshPermission();
					} else {
						AlertUtil.showModalFooterMessage(data.message);
					}
				}
			});
		}
}

var exportModal = {
		id : 'exportModal',
		data:[],
		exportFile : function(){
			this.data = [];
			this.loadFolder();
		},
		loadFolder : function(){
			var this_ = this;
			$("#exportModal").modal("show");
			AlertUtil.hideModalFooterMessage();
			this_.sjtree('');				
		},
		sjtree : function(){
			var this_ = this;
			$("#exportModal_using_alert_json").jstree("destroy"); 
			var searchParam = {
				mkdm : $("#mkdm").val(),
				id : "",
			}
			 AjaxUtil.ajax({
				   url:basePath+"/maintenance/maintenancemanuals/exportList", 
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(searchParam),
				   success:function(data){
					   this_.data = data;
					   this_.createPermitTree(data);  
			       },
			    }); 
		},
		createPermitTree : function(datastr){
			var this_ = this;
			//改变每个节点open属性
		   $('#exportModal_using_alert_json').jstree({
		       'core': {
		           'data': datastr ,
		           "force_text": true

		       }
		   }); 
		},
		doSave : function(){
			var this_ = this;
			var	sel = $('#exportModal_using_alert_json').jstree(true).get_selected()[0];
			if(null == sel || undefined ==sel || "" == sel){
				AlertUtil.showModalFooterMessage("请选择文件目录!","exportModal");
				return;
			}			
			var list = this_.data;
			var param = {};
			if(sel != "gml"){
				var paramsMap = {};
				$.each(list,function(i,obj){
					if(obj.id == sel){
						paramsMap.wjm = obj.data.mlmc;
					}
				})
				param.paramsMap = paramsMap;
			}
			
			param.dprtcode = userJgdm;
			param.nbsbm = $("#mkdm").val();
			param.rwdxid = sel;
			console.info(param);			
			startWait($("#exportModal"));
			// 提交数据
			AjaxUtil.ajax({
				url :basePath+"/maintenance/maintenancemanuals/doExportFile",
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify(param),
				dataType:"json",
				modal:$("#exportModal"),
				success:function(data){
					finishWait($("#exportModal"));//结束Loading
					if (data == "success") {
						$("#exportModal").modal("hide");
						AlertUtil.showMessage('任务提交成功，请到任务处理页面查看！');
						refreshPermission();
					} else {					
						AlertUtil.showErrorMessage(data.message);
					}
				}
			});
		}
}

var taskModal = {
		id : 'taskModal',
		showTask : function(){
			this.loadTask();
		},
		loadTask : function(){
			var this_ = this;
			$("#taskModal").modal("show");
			
			AlertUtil.hideModalFooterMessage();
			this_.goPage(1, "auto", "desc");
			$("#taskModal").on("shown.bs.modal",function(){
				this_.calculationHeight();
			})
		},
		calculationHeight:function(){
			var this_=this;
			var modalHeight=parseInt($("#"+this_.id).find(".modal-body").css("max-height"));
			var pageHeight=$("#"+this_.id+" .page-height").eq(0).outerHeight();
			var tableHeight=modalHeight-pageHeight-10;
			$("#"+this_.id).find("table").parent().css({"overflow":"auto","max-height":tableHeight+"px"});
		},
		goPage:function(pageNumber, sortType, sequence) {
			this.AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
		},
		AjaxGetDatasWithSearch : function(pageNumber, sortType, sequence){
			var this_ = this;			
			var searchParam = {};
			pagination = {
					page : pageNumber,
					sort : sortType,
					order : sequence,
					rows : 10
			};
			searchParam.pagination = pagination;
			searchParam.nbsbm = $("#mkdm").val();
			searchParam.dprtcode = userJgdm;
			 AjaxUtil.ajax({
				   url:basePath+"/maintenance/maintenancemanuals/taskList", 
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(searchParam),
				   success:function(data){
					 finishWait();
					if (data.total > 0) {
						this_.appendHtml(data.rows);
						var page_ = new Pagination({
							renderTo : "pagination_list",
							data : data,
							sortColumn : sortType,
							orderType : sequence,
							extParams : {},
							goPage : function(a, b, c) {
								this_.AjaxGetDatasWithSearch(a, b, c);
							}

						});						
					} else {
						$("#pagination_list").empty();
						$("#taskModal_list").empty();
						$("#taskModal_list").append("<tr class='text-center'><td colspan=\"5\">暂无任务 No data.</td></tr>");
					}
					/*new Sticky({
						tableId : 'task_table'
					});*/
			       },
			    }); 
		},
		appendHtml:function(list){
			var htmlContent = '';
			$.each(list,function(index, row) {
				htmlContent += "<tr >";
				if(row.zt == 10 || row.zt == 9){
					htmlContent += "<td style='text-align:center;vertical-align:middle;'><i class='icon-trash color-blue cursor-pointer' onClick=taskModal.openWinDel('"+ row.id + "') title='删除 Delete'></i></i></td>";	
				}else{
					htmlContent += "<td></td>"
				}
				htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.rwbm)+"\n"+StringUtil.escapeStr(row.rwms)+"' ><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.rwbm)+"</p><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.rwms)+"</p></td>"; 									
				htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.sqr==null?"":row.sqr.username)+" "+StringUtil.escapeStr(row.sqr==null?"":row.sqr.realname)+"\n"+formatUndefine(row.sqsj)+"' ><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.sqr==null?"":row.sqr.username)+" "+StringUtil.escapeStr(row.sqr==null?"":row.sqr.realname)+"</p><p  class='margin-bottom-0 '>"+formatUndefine(row.sqsj)+"</p></td>"; 									
				if(row.zt == 10){
					htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(DicAndEnumUtil.getEnumName('taskStatusEnum', row.zt))+"\n"+StringUtil.escapeStr(row.fksj)+"' ><p  class='margin-bottom-0 '>"+formatUndefine(DicAndEnumUtil.getEnumName('taskStatusEnum', row.zt))+"</p><p  class='margin-bottom-0 '>"+StringUtil.escapeStr(row.fksj)+"</p></td>"; 
					htmlContent += "<td style='text-align:left;vertical-align:middle;' title='下载' ><a href='#' onClick=taskModal.downLoadFile('"+ StringUtil.escapeStr(row.wjdz) + "') >下载</a></td>";	
				}else if(row.zt == 9){
					htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(DicAndEnumUtil.getEnumName('taskStatusEnum', row.zt))+"' >"+ formatUndefine(DicAndEnumUtil.getEnumName('taskStatusEnum', row.zt))+ "</td>";	
					htmlContent += "<td></td>"
				}else{
					htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+formatUndefine(DicAndEnumUtil.getEnumName('taskStatusEnum', 2))+"' >"+ formatUndefine(DicAndEnumUtil.getEnumName('taskStatusEnum', 2))+ "</td>";
					htmlContent += "<td></td>"
				}
				htmlContent += "</tr>";
			});
			$("#taskModal_list").empty();
			$("#taskModal_list").html(htmlContent);
			refreshPermission();
		},
		downLoadFile:function(url){
			if(null != url && undefined != url && "" != url ){
				window.open(url);
			}else{
				AlertUtil.showModalFooterMessage("任务下载失败!","taskModal");
			}
			
		},
		openWinDel:function(id){
			var this_ = this;
			var param = {};
			param.id = id;
			AlertUtil.showConfirmMessage("确定要删除吗？", {
				callback : function() {
					AjaxUtil.ajax({
						url : basePath + "/maintenance/maintenancemanuals/deleteTask",
						type : "post",
						async : false,
						contentType : "application/json;charset=utf-8",
						data : JSON.stringify(param),
						dataType : "json",
						success : function(data) {
							if("success" == data){
								this_.goPage(1, "auto", "desc");
							}else{
								AlertUtil.showModalFooterMessage("删除任务失败!","taskModal");
							}
							refreshPermission();
						}
					});
				}
			});
		}
}