﻿var delAttachements = [];	
function showInfo(pageNumber, sortType, sequence){
	var searchParam={};
	searchParam.mainid=$("#manidinfo").val();
	searchParam.dprtcode=$("#dprtId").val();
	pagination = {
			page : pageNumber,
			sort : sequence,
			order : sortType,
			rows : 20
		};
	AjaxUtil.ajax({
		url : basePath + "/productionplan/planefaultmonitoring/getInfoList",
		type : "post",
		data : JSON.stringify(searchParam),
		contentType : "application/json;charset=utf-8",
		dataType : "json",
		success : function(data) {
			finishWait();
			if (data.total > 0) {
				appendContentHtmlInfo(data.rows);
				new Pagination({
					renderTo : "fjgzjkInfo_pagination",
					data : data,
					sortColumn : sortType,
					orderType : sequence,
					goPage : function(a, b, c) {
						showInfo(a, b, c);
					}
				});
				// 替换null或undefined
				FormatUtil.removeNullOrUndefined();
				// 标记关键字
			} else {
				$("#dcgzcl_list").empty();
				$("#fjgzjkInfo_pagination").empty();
				$("#dcgzcl_list").append(
						"<tr ><td  class='text-center'  colspan=\"10\">暂无数据 No data.</td></tr>");
			}
			new Sticky({
				tableId : 'flight_record_sheet_table'
			});
		}
	});
}

	var choses=[];
	function appendContentHtmlInfo(list){
		choses=[];
			var htmlContent = '';
			$.each(list,function(index,row){
				if (index % 2 == 0) {
					  htmlContent +="<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
				   } else {
					   htmlContent +="<tr  style=\"cursor:pointer\" bgcolor=\"#fefefe\">";;
				   }				
				htmlContent += "<td style=\"vertical-align: middle;\" class='text-center'><i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='productionplan:planefaultmonitoring:main:05' title='修改 Edit' onclick='editInfo(\""
					+ row.id+ "\")' ></i>&nbsp;&nbsp;<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='productionplan:planefaultmonitoring:main:06' title='作废  Invalid' onClick='deleteInfo(\""
					+ row.id+ "\")' ></i></td>";
			    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.hbh)+"'>"+StringUtil.escapeStr(row.hbh)+"</td>";  
			    htmlContent += "<td style=\"vertical-align: middle;\" class='text-center' title='"+(StringUtil.escapeStr(row.hbrq)!=""?StringUtil.escapeStr(row.hbrq).substring(0,10):"")+"'>"+(StringUtil.escapeStr(row.hbrq)!=""?StringUtil.escapeStr(row.hbrq).substring(0,10):"")+"</td>";
			    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.pgsl)+"'>"+(StringUtil.escapeStr(row.pgsl))+"</td>";
			    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.cljg)+"'>"+(StringUtil.escapeStr(row.cljg))+"</td>";
			    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.zlh)+"'>"+StringUtil.escapeStr(row.zlh)+"</td>";
			    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.cxjxx)+"'>"+(StringUtil.escapeStr(row.cxjxx))+"</td>";
			    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.zsjxx)+"'>"+(StringUtil.escapeStr(row.zsjxx))+"</td>";
			    htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' title='"+StringUtil.escapeStr(row.bz)+"'>"+(StringUtil.escapeStr(row.bz))+"</td>";
			    if (index % 2 == 0) {
			    	htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' id='"+row.id+"'>"+getAttacments(row.attachmentList,"#f9f9f9")+"</td>";
			    }else{
			    	htmlContent += "<td style=\"vertical-align: middle;\" class='text-left' id='"+row.id+"'>"+getAttacments(row.attachmentList,"#fefefe")+"</td>";
			    }
			    
			    
			    htmlContent += "</tr>"
			    $("#dcgzcl_list").empty();
			    $("#dcgzcl_list").html(htmlContent);
				 
		});
	}
		
	
	
     
	 function addDcgz(){
		 	clearGzcl();
		 	$("#infotitle").html("新增故障处理记录");
		 	$("#infotitlefy").html("Add Fault Handling Record")
			$("#alertModalDcgz").modal("show");
		}
     function openHb(){
    	 hbModal.show({
				selected : $("#fxjldid").val(),
				callback : function(data) {
					$("#hbh").val(formatUndefine(data.hbh))
					$("#hbrq").val(formatUndefine(data.fxrq.substring(0,10)));
					$("#fxjldid").val(data.id);
					$("#form").data('bootstrapValidator').destroy();
					$('#form').data('bootstrapValidator', null);
					validation();
				},
				
			})
     }
     var zsjxxjl="";
	 var cxjxxjl="";
     function openZlh(){
    	 zsjxxjl="";
    	 cxjxxjl="";
    	 var list = [];
    		var a = $("#zlhid").val();
    		var b = $("#zlh").val();
    		for (var i = 0; i < a.split(",").length; i++) {
    			var p = {
    				xgdjid : a.split(",")[i],
    				whnr : b.split(",")[i]
    			};
    			list.push(p);
    		}
    		if (a == "") {
    			list = "";
    		}
    		alertModalZl.show({
    		 	checkUserList : list,
				callback : function(data) {
					var zlhid='';//工单id
					var zlh = '';//工单编号
					if (data != null && data != "") {
						$.each(data, function(i, row) {
							zlh=row.whnr+",";
							zlhid+=row.xgdjid+",";
							getZsCx(row.id);
						})
					}
					if (zlh != '') {
						zlh = zlh.substring(0, zlh.length - 1);
						$("#zlh").val(zlh);
					}
					if (zlhid != '') {
						zlhid = zlhid.substring(0, zlhid.length - 1);
						$("#zlhid").val(zlhid);
					}
					if(cxjxxjl !=''){
						cxjxxjl = cxjxxjl.substring(0, cxjxxjl.length -1);
						$("#cxjxx").val(cxjxxjl);
					}
					if(zsjxxjl !=''){
						zsjxxjl = zsjxxjl.substring(0, zsjxxjl.length -1);
						$("#zsjxx").val(zsjxxjl);
					}
				},
				
			})
     }
     
     function getZsCx(id){
    	 var param={};
    	 param.fxjldid=id;
    	 AjaxUtil.ajax({
   			url : basePath
   					+ "/productionplan/planefaultmonitoring/getZsCx",
   			type : "post",
   			data : JSON.stringify(param),
   			async: false,
   			contentType : "application/json;charset=utf-8",
   			dataType : "json",
   			success : function(data) {
   				
   				if(data.zs!=null&&data.zs!=""&&data.zs!=undefined){
   						zsjxxjl+=(data.zs+",");
   					}
   				if(data.cx!=null&&data.cx!=""&&data.cx!=undefined){
   						cxjxxjl+=(data.cx+",");
   				}
   			
   			}
   		});
     }
     
     function saveinfo(){
    	 startWait($("#alertModalDcgz"));
    		$('#form').data('bootstrapValidator').validate();
    		if (!$('#form').data('bootstrapValidator').isValid()) {
    			finishWait($("#alertModalDcgz"));
    			return false;
    		}
    	 var attachments = [];
    		var responses = uploadObj.responses;
    		var len = uploadObj.responses.length;
    		if (len != undefined && len > 0) {
    			for (var i = 0; i < len; i++) {
    				attachments.push({
    					'yswjm' : responses[i].attachments[0].yswjm,
    					'wbwjm' : responses[i].attachments[0].wbwjm,
    					'nbwjm' : responses[i].attachments[0].nbwjm,
    					'wjdx' : responses[i].attachments[0].wjdx,
    					'cflj' : responses[i].attachments[0].cflj,
    					'id' : responses[i].attachments[0].id,
    					'operate' : responses[i].attachments[0].operate

    				});
    			}
    		}
    		var dellen = delAttachements.length;
    		if (dellen != undefined && dellen > 0) {
    			for (var i = 0; i < dellen; i++) {
    				attachments.push({
    					'id' : delAttachements[i].id,
    					'operate' : 'DEL'

    				});
    			}
    		}
    	 var param = {};
    	 param.attachments = attachments;
    	 param.mainid=$("#manidinfo").val();
    	 param.fjzch=$("#fjzchid").val();
    	 param.fxjldid=$("#fxjldid").val();
    	 param.hbh=$("#hbh").val();
    	 param.hbrq=$("#hbrq").val();
    	 param.zlh=$("#zlh").val();
    	 param.pgsl=$("#pgsl").val();
    	 param.cljg=$("#cljg").val();
    	 param.cxjxx=$("#cxjxx").val();
    	 param.zsjxx=$("#zsjxx").val();
    	 param.bz=$("#bz").val();
    	 param.gdid=$("#zlhid").val();
    	 param.zt=1;
    	 param.dprtcode=$("#dprtId").val();
    	 param.zddwid=$("#zddwid").val();
    	 param.zdrid=$("#gbrid").val();
    	 if($("#infoId").val()!=''){
    		 param.id=$("#infoId").val();
    	 }else{
    		 param.id="";
    	 }
    	 AjaxUtil.ajax({
  			url : basePath
  					+ "/productionplan/planefaultmonitoring/addGzcl",
  			type : "post",
  			data : JSON.stringify(param),
  			contentType : "application/json;charset=utf-8",
  			dataType : "json",
  			modal : $("#alertModalDcgz"),
  			success : function(data) {
//  				AlertUtil.showMessage('保存成功!',
//  						'/productionplan/planefaultmonitoring/main?id='+$("#manidinfo").val()+'&pageParam=' + encodePageParam());
  				$("#alertModalDcgz").modal("hide");
  				$("#alertModalZl").modal("hide");
  				$("#alertModalHb").modal("hide");
  				finishWait($("#alertModalDcgz"));
  				pagination=mainpagination;
  				pageParam=encodePageParam()
  				finishWait();
  				showInfo(1, "desc", "auto");
  				decodePageParam();
  				refreshPermission();
  			}
  		});
     }
     
     var uploadObj = $("#fileuploader").uploadFile(
 		{
 			url : basePath + "/common/ajaxUploadFile",
 			multiple : true,
 			dragDrop : false,
 			showStatusAfterSuccess : false,
 			showDelete : false,
 			maxFileCount : 10,
 			formData : {
 				"fileType" : "planefaultmonitoringInfo",
 				"wbwjm" : function() {
 					return $('#wbwjm').val()
 				}
 			},
 			fileName : "myfile",
 			returnType : "json",
 			removeAfterUpload : true,				
 			showStatusAfterError: false,
 			uploadStr : "<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
 			uploadButtonClass : "ajax-file-upload_ext",
 			onSuccess : function(files, data, xhr, pd) // 上传成功事件，data为后台返回数据
 			{
 				var trHtml = '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''
 						+ data.attachments[0].uuid + '\'>';
 				trHtml = trHtml + '<td><div>';
 				trHtml = trHtml
 						+ '<i class="icon-trash color-blue cursor-pointer"  onclick="delfile(\''
 						+ data.attachments[0].uuid
 						+ '\')" title="删除 Delete">  ';
 				trHtml = trHtml + '</div></td>';
 				trHtml = trHtml + '<td title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'" class="text-left">'
 						+ StringUtil.escapeStr(data.attachments[0].wbwjm) + '</td>';
 				trHtml = trHtml + '<td class="text-left">'
 						+ data.attachments[0].wjdxStr + ' </td>';
 				trHtml = trHtml + '<td class="text-left" title="'+data.attachments[0].user.username + " "
					+ data.attachments[0].user.realname+'" >'
 						+ data.attachments[0].user.username + " "
 						+ data.attachments[0].user.realname + '</td>';
 				trHtml = trHtml + '<td>' + data.attachments[0].czsj
 						+ '</td>';
 				trHtml = trHtml
 						+ '<input type="hidden" name="relativePath" value=\''
 						+ data.attachments[0].relativePath + '\'/>';

 				trHtml = trHtml + '</tr>';
 				$('#filelist').append(trHtml);
 			}
 			,onSubmit : function (files, xhr) {
 				var wbwjm  = $.trim($('#wbwjm').val());
 				if(wbwjm.length>0){
 					if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
 		            	$('#wbwjm').focus();
 		            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \ | : \" * ?");
 		            	
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
 * 添加时删除附件
 * 
 * @param uuid
 */
 function delfile(uuid) {
 var responses = uploadObj.responses;
 var len = uploadObj.responses.length;
 var fileArray = [];
 var waitDelArray = [];
 if (len > 0) {
 for (var i = 0; i < len; i++) {
 	if (responses[i].attachments[0].uuid != uuid) {
 		fileArray.push(responses[i]);
 	}
 }
 uploadObj.responses = fileArray;
 uploadObj.selectedFiles -= 1;
 $('#' + uuid).remove();
 }
 }

 function deletefile(uuid) {
 	var key = $('#' + uuid).attr('key');
 	if (key == undefined || key == null || key == '') {
 		var responses = uploadObj.responses;
 		var len = uploadObj.responses.length;
 		var fileArray = [];
 		var waitDelArray = [];
 		if (len > 0) {
 			for (var i = 0; i < len; i++) {
 				if (responses[i].attachments[0].uuid != uuid) {
 					fileArray.push(responses[i]);
 				}
 			}
 			uploadObj.responses = fileArray;
 			uploadObj.selectedFiles -= 1;
 		}
 		$('#' + uuid).remove();

 	} else {
 		$('#' + uuid).remove();
 		delAttachements.push({
 			id : key
 		});
 	}
 }
 function downloadfile(id) {
 	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
 }
 
 function getAttacments(attachments,str){
	 if(attachments!=null){
		 var attachment='';
		 var len = attachments.length;
		 if (len > 0) {
			 for (var i = 0; i < len; i++) {
					choses.push(attachments[i].id);
					if (i == 1) {
						attachment = attachment
								+ "　<i class='icon-caret-down' id='"
								+ attachments[i].id
								+ "icon' onclick=switchZjqk('"
								+ attachments[i].id + "')></i><div id='"
								+ attachments[i].id
								+ "' style='display:none'>";
						}
					if(i==0){
						attachment +="<a href='javascript:void(0);' title='"+StringUtil.escapeStr(attachments[i].wbwjm)+"' onclick=\"downloadfile('"+attachments[i].id+"')\" >"+substring0To7(StringUtil.escapeStr(attachments[i].wbwjm))+"</a>";
					}
					if (i != 0) {
						attachment +="<a href='javascript:void(0);' title='"+StringUtil.escapeStr(attachments[i].wbwjm)+"' onclick=\"downloadfile('"+attachments[i].id+"')\" >"+substring0To10(StringUtil.escapeStr(attachments[i].wbwjm))+"</a>";
						attachment += "<br>";

					}
					if (i != 0 && i == len - 1) {
						attachment += "</div>";
					}
			 }
		 }
		 return attachment;
	 }
		
	}
 function substring0To10(str){
		if(str!="" && str!=null ){
			if(str.length>=10){
				return str.substring(0,10)+"...";
			}
			return str;
		}
		return str;
	}
 	function clearGzcl(){
 		$("#hbh").val("");
 		$("#fxjldid").val("");
 		$("#hbrq").val("");
 		$("#zlh").val("");
 		$("#zlhid").val("");
 		$("#cxjxx").val("");
 		$("#zsjxx").val("");
 		$("#pgsl").val("");
 		$("#cljg").val("");
 		$("#bz").val("");
 		$("#infoId").val("");
 		$('#filelist').html("");
 		$("#wbwjm").val("");
 		zsjxxjl="";
 		cxjxxjl="";
 		uploadObj.responses.length=0;
 	}
 	
 	function editInfo(id){
 		clearGzcl();
 		$("#infotitle").html("修改故障处理记录");
 		$("#infotitlefy").html("edit Fault Handling Record");
 		var param={};
 		param.id=id;
 		AjaxUtil.ajax({
  			url : basePath
  					+ "/productionplan/planefaultmonitoring/editInfo",
  			type : "post",
  			data : JSON.stringify(param),
  			contentType : "application/json;charset=utf-8",
  			dataType : "json",
  			success : function(data) {
  				$("#hbh").val(data.hbh);
  		 		$("#fxjldid").val(data.fxjldid);
  		 		$("#hbrq").val(data.hbrq.substring(0,10));
  		 		$("#zlh").val(data.zlh);
  		 		$("#zlhid").val(data.zlhid);
  		 		$("#cxjxx").val(data.cxjxx);
  		 		$("#zsjxx").val(data.zsjxx);
  		 		$("#pgsl").val(data.pgsl);
  		 		$("#cljg").val(data.cljg);
  		 		$("#bz").val(data.bz);
  		 		$("#infoId").val(data.id);
  		 		var attachments = data.attachmentList;
  		 		if(attachments!=null&&attachments.length>0){
  		 			var len=attachments.length;
					var trHtml = '';
					for (var i = 0; i < len; i++) {
						trHtml = trHtml
								+ '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''
								+ attachments[i].uuid + '\' key=\''
								+ attachments[i].id + '\' >';
						trHtml = trHtml + '<td><div>';
						trHtml = trHtml
								+ '<i class="icon-trash color-blue cursor-pointer" onclick="deletefile(\''
								+ attachments[i].uuid
								+ '\')" title="删除 Delete">  ';
						trHtml = trHtml + '</div></td>';
						trHtml = trHtml
								+ '<td class="text-left" title="'+StringUtil.escapeStr(attachments[i].wbwjm)+'" > <a onclick="downloadfile(\''
								+ attachments[i].id + '\')" >'
								+ StringUtil.escapeStr(attachments[i].wbwjm)
								+ '</a></td>';
						trHtml = trHtml + '<td class="text-left">'
								+ attachments[i].wjdxStr + ' </td>';
						trHtml = trHtml + '<td class="text-left" title="'+StringUtil.escapeStr(attachments[i].czrname)+'">'
								+ StringUtil.escapeStr(attachments[i].czrname) + '</td>';
						trHtml = trHtml + '<td>'
								+ attachments[i].czsj + '</td>';
						trHtml = trHtml
								+ '<input type="hidden" name="relativePath" value=\''
								+ attachments[i].relativePath
								+ '\'/>';
						trHtml = trHtml + '</tr>';
					}
					$('#filelist').append(trHtml);
				}
  				$("#alertModalDcgz").modal("show");
  			}
  		});
 	}
 	
 	function deleteInfo(id){
 		var param={};
 		param.mainid=$("#manidinfo").val();
 		param.zddwid=$("#zddwid").val();
 		param.zdrid=$("#gbrid").val();
 		param.id=id;
 		AlertUtil.showConfirmMessage("您确定要作废吗？", {
			callback : function() {
				AjaxUtil.ajax({
					url : basePath
  						+ "/productionplan/planefaultmonitoring/deleteInfo",
  					type : "post",
  					data : JSON.stringify(param),
  					contentType : "application/json;charset=utf-8",
  					dataType : "json",
  					success : function(data) {
  						pagination=mainpagination;
  						pageParam=encodePageParam(); 		  				 		  				
  		  				showInfo(1, "desc", "auto");
  		  				decodePageParam();
  		  				refreshPermission();
  					}
				});
			}
		});
 	}
 	
 	function validation() {
 		validatorForm = $("#form").bootstrapValidator({
 			message : '数据不合法',
 			feedbackIcons : {
 				// valid: 'glyphicon glyphicon-ok',
 				invalid : 'glyphicon glyphicon-remove',
 				validating : 'glyphicon glyphicon-refresh'
 			},
 			fields : {
 				hbh : {
 					validators : {
 						notEmpty : {
 							message : '航班号不能为空'
 						},
						 regexp: {
	 	                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
	 	                        message: '不能输入中文'
	 	                    }
 					}
 				},
 				hbrq : {
 					validators : {
 						notEmpty : {
 							message : '航班日期不能为空'
 						}
 					}
 				},
 				zlh : {
 					validators : {
 						notEmpty : {
 							message : '指令号不能为空'
 						},
 						 regexp: {
 	                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
 	                        message: '不能输入中文'
 	                    }
 					}
 				},
 				cxjxx : {
 					validators : { 						
 						 regexp: {
 	                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
 	                        message: '不能输入中文'
 	                    }
 					}
 				},
 				zsjxx : {
 					validators : {						
 						 regexp: {
 	                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
 	                        message: '不能输入中文'
 	                    }
 					}
 				}
 			}
 		}).on('hide', function(e) {
 			  $('#form').data('bootstrapValidator')  
 			     .updateStatus('hbrq', 'NOT_VALIDATED',null)  
 			     .validateField('hbrq');  
 	  });;
 	}
 	
 	$("#alertModalDcgz").on("hidden.bs.modal", function() {
 		$("#form").data('bootstrapValidator').destroy();
 		$('#form').data('bootstrapValidator', null);
 		validation();
 	});
 	
 	function vieworhideZjqkContentAll(){
 		new Sticky({tableId:'flight_record_sheet_table'});
 		var obj = $("th[name='gzfj']");
		var flag = obj.hasClass("downward");
		if(flag){
			obj.removeClass("downward").addClass("upward");
		}else{
			obj.removeClass("upward").addClass("downward");
		}
		for(var i=0;i<choses.length;i++){
			if(flag){				
				$("#"+choses[i]).fadeIn(500);
				$("#"+choses[i]+'icon').removeClass("icon-caret-down");
				$("#"+choses[i]+'icon').addClass("icon-caret-up");
			}else{
				$("#"+choses[i]).hide();
				$("#"+choses[i]+'icon').removeClass("icon-caret-up");
				$("#"+choses[i]+'icon').addClass("icon-caret-down");
			}
		}
	}
 	
 	function switchZjqk(id) {
 		new Sticky({tableId:'flight_record_sheet_table'});
 		if ($("#" + id).is(":hidden")) {
 			$("#" + id).fadeIn(500);
 			$("#" + id + 'icon').removeClass("icon-caret-down");
 			$("#" + id + 'icon').addClass("icon-caret-up");
 		} else {
 			$("#" + id).hide();
 			$("#" + id + 'icon').removeClass("icon-caret-up");
 			$("#" + id + 'icon').addClass("icon-caret-down");
 		}
 	}