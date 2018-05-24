/**
 * id : 主div的id
 * attachment : 附件
 * fileType : fileType文件夹存放路径
 * isEdit : 是否可编辑,true可编辑,false不可编辑
 */
attachmentsSingleUtil = {
		attachment : {},
		isVaildMap : {},//验证是否通过,key:true表示验证通过,key:false表示验证未通过
		initAttachment : function(id, attachment, fileType, isEdit, message){
			var this_ = this;
			if(isEdit){
				$("#edit_attach_div", $("#"+id)).show();
				$("#view_attach_div", $("#"+id)).hide();
				this_.loadAttachMap(id, attachment, message);
				this_.loadAttachment(id, fileType);
			}else{
				$("#edit_attach_div", $("#"+id)).hide();
				$("#view_attach_div", $("#"+id)).show();
				var t = "";
				if(attachment != null && attachment.wbwjm != null){
					t += "<a title='"+(StringUtil.escapeStr(attachment.wbwjm)+"."+attachment.hzm)+"' href='javascript:void(0);' onclick=attachmentsSingleUtil.downloadfile('"+attachment.id+"')>"+(StringUtil.escapeStr(attachment.wbwjm)+"."+attachment.hzm)+"</a>";
		 		}
				$("#view_attach_div", $("#"+id)).html(t);
			}
		},
		loadAttachMap : function(id, attachment, message){
			var this_ = this;
			var obj = {};
			if(attachment != null && attachment.wbwjm != null){
	 			obj.id = attachment.id;
	 			obj.yswjm = attachment.yswjm;
	 			obj.wbwjm = attachment.wbwjm;
	 			obj.nbwjm = attachment.nbwjm;
	 			obj.cflj = attachment.cflj;
	 			obj.wjdx = attachment.wjdx;
	 			obj.hzm = attachment.hzm;
	 			$(".wbwjmSingle", $("#"+id)).html(this_.createAttachment(attachment.id,attachment.wbwjm,attachment.hzm,id));
				this_.existsAttach(id);
	 		}else{
	 			this_.noExistsAttach(id);
	 		}
			var paramsMap = {};
			paramsMap.message = message;
			obj.paramsMap = paramsMap;
	 		this_.attachment[id] = obj;
	 		this_.isEllipsis(id);
	 		$(window).resize(function(){
	 			this_.isEllipsis(id);
	 		});
		},
		createAttachment:function(attachmentid,wbwjm,hzm,id){
			var wbwjmStr = "";
 			wbwjmStr += "<div class='pull-left padding-left-0 padding-right-0' style='max-width:100%;'>";
 			wbwjmStr +="<div style='display:table'>"
 			wbwjmStr +="<div style='display:table-cell'>"
 			wbwjmStr += "<a class='cursor-pointer' style='display:table;table-layout: fixed;' title='"+(StringUtil.escapeStr(wbwjm)+"."+hzm)+"'  href='javascript:void(0)' onclick=attachmentsSingleUtil.downloadfile('"+attachmentid+"')  ><div  style='display:table-cell;vertical-align: middle;word-break: break-all;word-wrap:break-word;padding-top:7px;'><label id='"+id+"_label'>"+(StringUtil.escapeStr(wbwjm)+"."+hzm)+"</label></div>";
 			wbwjmStr += "<div  style='display:none;vertical-align: middle;'><label  id='"+id+"_label_ellipsis' >...</label></div></a></div>" ;
 			wbwjmStr +=	"<div  style='display:table-cell;vertical-align: middle;padding-top:7px;'><i class='pull-left icon-remove-sign cursor-pointer color-blue cursor-pointer' style='font-size:15px;margin-left:4px;' title='删除 Delete' onclick=attachmentsSingleUtil.removeFile('"+id+"')></i></div>";
 			wbwjmStr += "</div></div>";
 			return wbwjmStr;
		},
		createHtmlText:function(id,wbwjm,hzm){
			var wbwjmStr = "";
 			wbwjmStr += "<div class='pull-left padding-left-0 padding-right-0' style='max-width:100%;'>";
 			wbwjmStr += "<div style='display:table;table-layout: fixed;'><div  style='display:table-cell;vertical-align: middle;word-break: break-all;word-wrap:break-word;padding-top:7px;'><label id='"+id+"_label' title='"+(wbwjm+"."+hzm)+"'>"+(wbwjm+"."+hzm)+"</label></div>";
 			wbwjmStr += "<div  style='display:none;vertical-align: middle;'><label  id='"+id+"_label_ellipsis' >...</label></div>" ;
 			wbwjmStr +=	"<div  style='display:table-cell;vertical-align: middle;padding-top:7px;'><i class='pull-left icon-remove-sign cursor-pointer color-blue cursor-pointer' style='font-size:15px;margin-left:4px;' title='删除 Delete' onclick=attachmentsSingleUtil.removeFile('"+id+"')></i></div>";
 			wbwjmStr += "</div></div>";
 			return wbwjmStr;
		},
		isEllipsis:function(id){
			   if(parseInt($("#"+id+"_label").css("height"))>18){
	 				$("#"+id+"_label").attr("style","height:17px;overflow:hidden;");
	 				$("#"+id+"_label_ellipsis").parent("div").css("display","table-cell");
	 			};
		},
		/**
		 * 加载附件
		 */
		loadAttachment : function(id, fileType){
			var this_ = this;
			//上传
			var uploadObjSingle = $("#fileuploaderSingle",$("#"+id)).uploadFile({
				url:basePath+"/common/ajaxUploadFile",
				multiple:true,
				dragDrop:false,
				showStatusAfterSuccess: false,
				showDelete: false,
				formData: {
					"fileType":fileType
					,"wbwjmSingle" : function(){return ''}
					},//参考FileType枚举（技术文件类型）
				fileName: "myfile",
				returnType: "json",
				removeAfterUpload:true,
				showStatusAfterSuccess : false,
				showStatusAfterError: false,
//				uploadStr:"<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
//				uploadButtonClass: "ajax-file-upload_ext",
				uploadStr:"<i class='fa fa-upload'></i>",
				/*ajax-file-upload_ext*/
				uploadButtonClass:"btn btn-default btn-uploadnew",
				onSuccess:function(files,data,xhr,pd)  //上传成功事件，data为后台返回数据
				{
					this_.attachment[id].yswjm = data.attachments[0].yswjm;
					this_.attachment[id].wbwjm = data.attachments[0].wbwjm;
					this_.attachment[id].nbwjm = data.attachments[0].nbwjm;
					this_.attachment[id].cflj = data.attachments[0].cflj;
					this_.attachment[id].wjdx = data.attachments[0].wjdx;
					this_.attachment[id].hzm = data.attachments[0].hzm;
		 			$(".wbwjmSingle", $("#"+id)).html(this_.createHtmlText(id,data.attachments[0].wbwjm,data.attachments[0].hzm));
					this_.existsAttach(id);
					this_.isEllipsis(id);
					$(window).resize(function(){
						this_.isEllipsis(id);
					});
				}
			
				
				//附件特殊字符验证（文件说明限制字符和windows系统保持一致）
				,onSubmit : function (files, xhr) {
					var wbwjm  = $.trim($('.wbwjmSingle').val());
					if(wbwjm.length>0){
						if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
			            	$('.wbwjmSingle').focus();
			            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \\ | : \" * ?");
			            	
			            	$('.ajax-file-upload-container').html("");
							uploadObjSingle.selectedFiles -= 1;
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
		},
		/**
		 * 执行附件存在的方法
		 */
		existsAttach : function(id){
			$("#fileuploaderSingle", $("#"+id)).hide();
			$(".wbwjmSingle", $("#"+id)).show();
			$("input[name='attachBox']", $("#"+id)).attr("disabled","disabled");
			$("input[name='attachBox']", $("#"+id)).attr("checked",'true');//选中
		},
		/**
		 * 执行附件存在的方法
		 */
		noExistsAttach : function(id){
			$("#fileuploaderSingle", $("#"+id)).hide();
			$(".wbwjmSingle", $("#"+id)).html('');
			$(".wbwjmSingle", $("#"+id)).hide();
			$("input[name='attachBox']", $("#"+id)).removeAttr("disabled");
			$("input[name='attachBox']", $("#"+id)).removeAttr("checked");//不选中
		},
		/**
		 * 删除附件
		 */
		removeFile : function(id){
			var this_ = this;
			AlertUtil.showConfirmMessage("确定要删除附件吗？", {callback: function(){
				 
				$("input[name='attachBox']", $("#"+id)).removeAttr("disabled");
				$(".wbwjmSingle", $("#"+id)).hide();
				$(".wbwjmSingle", $("#"+id)).html('');
				$("#fileuploaderSingle", $("#"+id)).show();
				this_.attachment[id].yswjm = '';
				this_.attachment[id].wbwjm = '';
				this_.attachment[id].nbwjm = '';
				this_.attachment[id].cflj = '';
				this_.attachment[id].wjdx = '';
				this_.attachment[id].hzm = '';
			}});
		},
		/**
		 * 附件显示或隐藏
		 */
		showOrHideAttach : function(obj){
			if($(obj).is(":checked")){
				$(obj).parent().parent().parent().find("#fileuploaderSingle").show();
			}else{
				$(obj).parent().parent().parent().find("#fileuploaderSingle").hide();
			}
		},
		/**
		 * 下载附件
		 */
		downloadfile : function(id){
			PreViewUtil.handle(id, PreViewUtil.Table.D_011);
		},
		/**
		 * 通过id获取附件
		 */
		getAttachment : function(id){
			this.isVaildMap[id] = true;
			if($("input[name='attachBox']", $("#"+id)).is(":checked")){
				if(this.attachment[id] == null || this.attachment[id].wbwjm == null || this.attachment[id].wbwjm == ''){
					this.isVaildMap[id] = false;
					var message = "请上传附件!";
					if(this.attachment[id] != null && this.attachment[id].paramsMap.message != null){
						message = this.attachment[id].paramsMap.message;
					}
					AlertUtil.showModalFooterMessage(message);
					return {};
				}
				return this.attachment[id];
			}else{
				return {};
			}
		}
};



