
	//上传
	$(".fileuploader").each(function(){
		var $modal = $(this).parents("#fjdivmark");
		$(this).uploadFile(
				{
					url : basePath + "/common/ajaxUploadFile",
					multiple : true,
					dragDrop : false,
					showStatusAfterSuccess : false,
					showDelete : false,
					maxFileCount : 100,
					formData : {
						"fileType" : "maintenancepersonnel",
						"wbwjm" : function() {
							return $('[name="wbwjm"]:visible').val();
						}
					},
					fileName : "myfile",
					returnType : "json",
					removeAfterUpload : true,
//					uploadStr:"<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
//					uploadButtonClass: "ajax-file-upload_ext",
					uploadStr:"<i class='fa fa-upload'></i>",
					/*ajax-file-upload_ext*/
					uploadButtonClass:"btn btn-default btn-uploadnew",
					onSuccess : function(files, data, xhr, pd) //上传成功事件，data为后台返回数据
					{
						var trHtml = '<tr bgcolor="#f9f9f9" id=\''+data.attachments[0].uuid+'\'>';
						 trHtml = trHtml+'<td><div>';
						 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+data.attachments[0].uuid+ '\')" title="删除 Delete">  ';
						 trHtml = trHtml+'</div></td>';
						 var wjmc = StringUtil.escapeStr(data.attachments[0].wbwjm+ (data.attachments[0].hzm ? "."+data.attachments[0].hzm : ""));
						 trHtml = trHtml+'<td class="text-left" title="'+wjmc+'">'+wjmc+'</td>';
						 trHtml = trHtml+'<td class="text-left">'+data.attachments[0].wjdxStr+' </td>';
						 trHtml = trHtml+'<td class="text-left" title="'+StringUtil.escapeStr(data.attachments[0].user.username+" "+data.attachments[0].user.realname)+'">'+StringUtil.escapeStr(data.attachments[0].user.username+" "+data.attachments[0].user.realname)+'</td>';
						 trHtml = trHtml+'<td>'+(data.attachments[0].czsj||data.attachments[0].realname)+'</td>';
						 trHtml = trHtml+'<input type="hidden" name="relativePath" value=\''+data.attachments[0].relativePath+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="yswjm" value=\''+StringUtil.escapeStr(data.attachments[0].yswjm)+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="nbwjm" value=\''+StringUtil.escapeStr(data.attachments[0].nbwjm)+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="wbwjm" value=\''+StringUtil.escapeStr(data.attachments[0].wbwjm)+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="cflj" value=\''+StringUtil.escapeStr(data.attachments[0].cflj)+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="wjdx" value=\''+StringUtil.escapeStr(data.attachments[0].wjdx)+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="uuid" value=\''+StringUtil.escapeStr(data.attachments[0].uuid)+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="hzm" value=\''+StringUtil.escapeStr(data.attachments[0].hzm)+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="wjdxStr" value=\''+StringUtil.escapeStr(data.attachments[0].wjdxStr)+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="czrname" value=\''+StringUtil.escapeStr(data.attachments[0].user.username+" "+data.attachments[0].user.realname)+'\'/>';
						 trHtml = trHtml+'<input type="hidden" name="czsj" value=\''+StringUtil.escapeStr(data.attachments[0].czsj)+'\'/>';
						 trHtml = trHtml+'</tr>';	 
						 $modal.find('[name="filelist"]>.non-choice').remove();
						 $modal.find('[name="filelist"]').append(trHtml);
					}
					
					//附件特殊字符验证（文件说明限制字符和windows系统保持一致）
					,onSubmit : function (files, xhr) {
						var wbwjm  = $.trim($('[name="wbwjm"]:visible').val());
						if(wbwjm.length>0){
							if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
								$('[name="wbwjm"]:visible').focus();
				            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \\ | : \" * ?");
				            	$('.ajax-file-upload-container').html("");
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
	});
	
	
	/**
	* 删除附件
	* @param newFileName
	*/
	function delfile(uuid) {
		var key = $('#' + uuid).attr('key');
		$('#' + uuid).remove();
		if(key){
			personnel.attachment.remove(key);
		}
		if($("[name='filelist']:visible>tr").length == 0){
			$("[name='filelist']:visible").append('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
		}
	}
	
	/**
	 * 加载附件
	 * @param id
	 */
	function loadAttachments(modal, id) {
		AjaxUtil.ajax({
					url : basePath + "/common/loadAttachments",
					type : "post",
					data : {
						mainid : id
					},
					success : function(data) {
						if (data.success) {
							var attachments = data.attachments;
							var obj = personnel.attachment.findByRowid($("#current_rowid").val());
							attachments = attachments.concat(obj);
							var delAttachments = personnel.attachment.getAllRemoveAttachments();
							for(var i = 0; i < attachments.length; i++){
								if($.inArray(attachments[i].uuid, delAttachments) != -1){
									attachments.splice(i, 1);
								}
							}
							$(modal + ' [name="filelist"]>tr').remove();
							if (attachments.length > 0) {
								var trHtml = '';
								for (var i = 0; i < attachments.length; i++) {
									var wjmc = StringUtil.escapeStr(attachments[i].wbwjm+(attachments[i].hzm ? "."+attachments[i].hzm : ""));
									if(attachments[i].id){
										var czrname = attachments[i].user ? 
												(attachments[i].user.username+" "+data.attachments[i].user.realname) : attachments[i].czrname;	
										trHtml = trHtml
										+ '<tr bgcolor="#f9f9f9" id=\''
										+ attachments[i].uuid + '\' key=\''
										+ attachments[i].id + '\' >';
										trHtml = trHtml + '<td><div>';
										trHtml = trHtml
										+ '<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''
										+ attachments[i].uuid
										+ '\')" title="删除 Delete">  ';
										trHtml = trHtml + '</div></td>';
										trHtml = trHtml
										+ '<td class="text-left" name="wbwjm" title="'+wjmc+'"> <a class="cursor-pointer" onclick="downloadfile(\''
										+ attachments[i].id + '\')" >'
										+ wjmc + '</a></td>';
										trHtml = trHtml + '<td class="text-left">'
										+ attachments[i].wjdxStr + ' </td>';
										trHtml = trHtml + '<td class="text-left" title="'+StringUtil.escapeStr(attachments[i].czrname)+'">'
										+ StringUtil.escapeStr(attachments[i].czrname) + '</td>';
										trHtml = trHtml + '<td>' + (attachments[i].czsj||attachments[i].realname)
										+ '</td>';
										trHtml = trHtml
										+ '<input type="hidden" name="relativePath" value=\''
										+ attachments[i].relativePath + '\'/>';
										trHtml = trHtml+'<input type="hidden" name="yswjm" value=\''+StringUtil.escapeStr(attachments[i].yswjm)+'\'/>';
										trHtml = trHtml+'<input type="hidden" name="nbwjm" value=\''+StringUtil.escapeStr(attachments[i].nbwjm)+'\'/>';
										trHtml = trHtml+'<input type="hidden" name="wbwjm" value=\''+StringUtil.escapeStr(attachments[i].wbwjm)+'\'/>';
										trHtml = trHtml+'<input type="hidden" name="cflj" value=\''+StringUtil.escapeStr(attachments[i].cflj)+'\'/>';
										trHtml = trHtml+'<input type="hidden" name="wjdx" value=\''+StringUtil.escapeStr(attachments[i].wjdx)+'\'/>';
										trHtml = trHtml+'<input type="hidden" name="uuid" value=\''+StringUtil.escapeStr(attachments[i].uuid)+'\'/>';
										trHtml = trHtml+'<input type="hidden" name="hzm" value=\''+StringUtil.escapeStr(attachments[i].hzm)+'\'/>';
										trHtml = trHtml+'<input type="hidden" name="wjdxStr" value=\''+StringUtil.escapeStr(attachments[i].wjdxStr)+'\'/>';
										trHtml = trHtml+'<input type="hidden" name="czrname" value=\''+StringUtil.escapeStr(czrname)+'\'/>';
										trHtml = trHtml+'<input type="hidden" name="czsj" value=\''+StringUtil.escapeStr(attachments[i].czsj)+'\'/>';
										trHtml = trHtml+'<input type="hidden" name="id" value=\''+StringUtil.escapeStr(attachments[i].id)+'\'/>';
										trHtml = trHtml + '</tr>';
									}else{
										 trHtml = trHtml+'<tr bgcolor="#f9f9f9" id=\''+attachments[i].uuid+'\'>';
										 trHtml = trHtml+'<td><div>';
										 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+attachments[i].uuid+ '\')" title="删除 Delete">  ';
										 trHtml = trHtml+'</div></td>';
										 trHtml = trHtml+'<td class="text-left" title="'+wjmc+'">'+wjmc+'</td>';
										 trHtml = trHtml+'<td class="text-left">'+attachments[i].wjdxStr+' </td>';
										 trHtml = trHtml+'<td class="text-left" title="'+StringUtil.escapeStr(attachments[i].czrname)+'">'+StringUtil.escapeStr(attachments[i].czrname)+'</td>';
										 trHtml = trHtml+'<td>'+(attachments[i].czsj||attachments[i].realname)+'</td>';
										 trHtml = trHtml+'<input type="hidden" name="relativePath" value=\''+attachments[i].relativePath+'\'/>';
										 trHtml = trHtml+'<input type="hidden" name="yswjm" value=\''+StringUtil.escapeStr(attachments[i].yswjm)+'\'/>';
										 trHtml = trHtml+'<input type="hidden" name="nbwjm" value=\''+StringUtil.escapeStr(attachments[i].nbwjm)+'\'/>';
										 trHtml = trHtml+'<input type="hidden" name="wbwjm" value=\''+StringUtil.escapeStr(attachments[i].wbwjm)+'\'/>';
										 trHtml = trHtml+'<input type="hidden" name="cflj" value=\''+StringUtil.escapeStr(attachments[i].cflj)+'\'/>';
										 trHtml = trHtml+'<input type="hidden" name="wjdx" value=\''+StringUtil.escapeStr(attachments[i].wjdx)+'\'/>';
										 trHtml = trHtml+'<input type="hidden" name="uuid" value=\''+StringUtil.escapeStr(attachments[i].uuid)+'\'/>';
										 trHtml = trHtml+'<input type="hidden" name="hzm" value=\''+StringUtil.escapeStr(attachments[i].hzm)+'\'/>';
										 trHtml = trHtml+'<input type="hidden" name="wjdxStr" value=\''+StringUtil.escapeStr(attachments[i].wjdxStr)+'\'/>';
										 trHtml = trHtml+'<input type="hidden" name="czrname" value=\''+StringUtil.escapeStr(attachments[i].czrname)+'\'/>';
										 trHtml = trHtml+'<input type="hidden" name="czsj" value=\''+StringUtil.escapeStr(attachments[i].czsj)+'\'/>';
										 trHtml = trHtml+'</tr>';	 
									}
								}
								$(modal + ' [name="filelist"]').append(trHtml);
							}else{
								if($("#type").val() == 1 || $("#type").val() == 2){
									$(modal + ' [name="filelist"]').append('<tr class="non-choice"><td colspan="5">暂无数据 No data.</td></tr>');
								}else{
									$(modal + ' [name="filelist"]').append('<tr class="non-choice"><td colspan="4">暂无数据 No data.</td></tr>');
								}
							}
						}
					}
				});
	}
	
	/**
	 * 下载附件
	 */
	function downloadfile(id) {
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	}
	
	/**
	 * 构造表单中的td附件内容
	 * @param list
	 * @returns {String}
	 */
	function buildAttachmentContent(list,type){
		var html = "";
		list = list || [];
		for(var i = 0; i < list.length; i++){
			if(type=="jyjl"){
				jyjl.push(list[i].uuid);
			}else if(type=="wysp"){
				wysp.push(list[i].uuid);
			}else if(type=="przc"){
				przc.push(list[i].uuid);
			}else if(type=="gzll"){
				gzll.push(list[i].uuid);
			}else if(type=="gwzw"){
				gwzw.push(list[i].uuid);
			}else if(type=="jsll"){
				jsll.push(list[i].uuid);
			}else if(type=="wxzz"){
				wxzz.push(list[i].uuid);
			}else if(type=="jxzz"){
				jxzz.push(list[i].uuid);
			}else if(type=="wxjssjjl"){
				wxjssjjl.push(list[i].uuid);
			}else if(type=="pxjl"){
				pxjl.push(list[i].uuid);
			}else if(type=="ywkhjl"){
				ywkhjl.push(list[i].uuid);
			}else if(type=="xscj"){
				xscj.push(list[i].uuid);
			}else if(type=="jjjl"){
				jjjl.push(list[i].uuid);
			}else if(type=="sgzhqk"){
				sgzhqk.push(list[i].uuid);
			}else if(type=="baqsj"){
				baqsj.push(list[i].uuid);
			}else if(type=="bcxxw"){
				bcxxw.push(list[i].uuid);
			}else{
				
			}
		    if (i == 1) {
		    	html = html
						+ "　<i class='icon-caret-down' style='position:absolute; right: 9px; cursor: pointer;' id='"
						+ list[i].uuid
						+ "icon' name='attachment_"+type+"' onclick=switchAttachmentContent('"
						+ list[i].uuid + "')></i><div id='"
						+ list[i].uuid
						+ "' style='display:none;'>";
			}
		    var wjmc = StringUtil.escapeStr(list[i].wbwjm+(list[i].hzm ? "."+list[i].hzm : ""));
		    if(list[i].id){
		    	html += "<a class='cursor-pointer' onclick='downloadfile(\""+list[i].id+"\")'>"+wjmc+"</a>";
		    }else{
		    	html += wjmc;
		    }
			if (i != 0) {
				html += "<br>";
			}
			if (i != 0 && i == list.length - 1) {
				html += "</div>";
			}
		}
		return html;
	}
	
	/**
	 * 构造表单中的td附件title
	 * @param list
	 * @returns {String}
	 */
	function buildAttachmentTitle(list,type){
		var html = "";
		list = list || [];
		for(var i = 0; i < list.length; i++){
		    html += StringUtil.escapeStr(list[i].wbwjm+(list[i].hzm ? "."+list[i].hzm : ""));
		    html += "\n";
		}
		return html;
	}
	
	/**
	 * 收缩/展开附件
	 * @param id
	 */
	function switchAttachmentContent(id) {
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
	
	/**
	 * 获取附件
	 */
	function getAttachments(type){
		var attachments = [];
		$('[name="filelist"]:visible>tr:not(.non-choice)').each(function(){
			var row = $(this);
			if(type != "17" || type == row.find("input[name='type']").val()){
				attachments.push({
					'yswjm':row.find("input[name='yswjm']").val()
					,'wbwjm':row.find("input[name='wbwjm']").val()
					,'nbwjm':row.find("input[name='nbwjm']").val()
					,'cflj':row.find("input[name='cflj']").val()
					,'wjdx':row.find("input[name='wjdx']").val()
					,'uuid':row.find("input[name='uuid']").val()
					,'hzm':row.find("input[name='hzm']").val()
			 		,'wjdxStr':row.find("input[name='wjdxStr']").val()
			 		,'czrname':row.find("input[name='czrname']").val()
			 		,'czsj':row.find("input[name='czsj']").val()
			 		,'type':type
			 		,'id':row.find("input[name='id']").val()
				});
			}
		});
		return attachments;
	}
	
	function vieworhideFj(str){
		var obj = $("th[name='th_"+str+"']");
		var flag = obj.hasClass("downward");
		if(flag){
			obj.removeClass("downward").addClass("upward");
			$("i[name='attachment_"+str+"']").removeClass("icon-caret-down").addClass("icon-caret-up");
			$("i[name='attachment_"+str+"']").next().fadeIn(500);
		}else{
			obj.removeClass("upward").addClass("downward");
			$("i[name='attachment_"+str+"']").removeClass("icon-caret-up").addClass("icon-caret-down");
			$("i[name='attachment_"+str+"']").next().hide();
		}
	}