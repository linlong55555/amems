	
	var PreViewUtil = (function(){
		
		// table枚举
		var TableEnum = {
				D_011 : 1,
				B_G_00101 : 2,
				B_G_00603 : 3
		}
		
		// 预览的后缀名
		var previewSuffix = ['jpg','png','pdf'];
		
		/**
		 * 获取附件对象
		 */
		var getAttachmentObj = function(id, type){
			var attachmentObj;
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/common/loadAttachment",
				type:"post",
				data:{
					id : id,
					type : type
				},
				success:function(result){
					if(result.success){
						attachmentObj = result.attachments;
					}else{
						throw new Error("获取附件对象失败！！！");
					}
				}
			});
			return attachmentObj;
		}
		
		/**
		 * 预览
		 */
		var preview = function(attachment){
			var hzm= attachment.hzm ? (attachment.hzm).toLowerCase() : "";
			// pdf预览
			if(hzm === 'pdf'){
				var url = basePath+'/common/preview/'+attachment.id+"_"+attachment.type+'?fileName='+attachment.wbwjm;
				window.open(basePath+'/static/lib/pdf.js/web/viewer.html?file='+encodeURIComponent(url),
						'PDF','width:50%;height:50%;top:100;left:100;');
			} 
			// 图片预览
/*			else if(hzm === 'jpg' || hzm === 'png'){			
				var url = basePath+'/common/preview/'+attachment.id+"_"+attachment.type;
				loadImg($("#previewImage")[0], url, function(img){
					// 图片限制最大长度宽度
					var maxHeight = $(window).height() - 100;
					var maxWidth = $(window).width() - 100;
				
					if(img.height >= maxHeight || img.width >= maxWidth){
						$("#previewImage").css("max-height",maxHeight).css("max-width",maxWidth);
						if(img.height >= maxHeight){
							$("#imageModal>div").css("height",maxHeight);
						}
						if(img.width >= maxWidth){
							$("#imageModal>div").css("width",maxWidth)
						}
					}else{
						$("#previewImage").css("max-height","").css("max-width","");
					}
					if(img.height < minHeight || img.width < minWidth){
						$("#imageModal>div").css("height",minHeight).css("width",minHeight+40);
					}else{
						$("#imageModal>div").css("height",img.height).css("width",img.width+40);
					}
				});
				$("#imageModal").modal("show");
			}
 */			
			//预览图片
			else if(hzm === 'jpg' || hzm === 'png'){
				var url = basePath+'/common/preview/'+attachment.id+"/"+attachment.type;
				window.open(url);
			}
			// word预览
			else if(hzm === 'docx'){
				var url=basePath + "/common/previewDocx/" + attachment.id + "_" + attachment.type;
				window.open(url,'blank_','scrollbars=no,resizable=no,width=10,height=10,menubar=no'); 
			}
			
		};
		
		/**
		 * 加载图片
		 */
		var loadImg = function(img, url, fCallback)  
		{     
		    img.src = url+ '?t=' + Math.random();
		    var userAgent = navigator.userAgent;
		    if (userAgent.indexOf("compatible") > -1 
		    		&& userAgent.indexOf("MSIE") > -1 
		    		&& userAgent.indexOf("Opera") == -1) {
		        img.onreadystatechange = function() {
		            if (this.readyState=="loaded" || this.readyState=="complete") {  
		                fCallback({width:img.width, height:img.height});  
		            }  
		        };  
		    }else {  
		        img.onload = function() {  
		            fCallback({width:img.width, height:img.height});  
		        };  
		    } 
		};  
		
		/**
		 * 下载
		 */
		var download = function(attachment){
			window.open(basePath + "/common/downloadAttachment/" + attachment.id + "_" + attachment.type);
		};
		
		/**
		 * 根据后缀对文件进行不同的处理
		 */
		var handle = function(id, type){
			// 获取附件对象
			var attachment = getAttachmentObj(id, type);
			// 预览
			if(attachment.hzm && $.inArray(attachment.hzm.toLowerCase()||"", previewSuffix) !== -1){
				preview(attachment);
			}
			// 下载
			else{
				download(attachment);
			}
		};
		
		return {
			// 预览
			preview : preview,
			// 下载
			download : download,
			// 根据后缀对文件进行不同的处理
			handle : handle,
			// table枚举
			Table : TableEnum
		};
		
	})();