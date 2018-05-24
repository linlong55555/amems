Post_Open_Modal_License = {
	id : "Post_Open_Modal",
	parentId : '',
	scrollDivId : '',
	/**
	 * 初始化维修执照数据
	 */
	setHoldingData : function(data, parentId, scrollDivId){
		var this_ = this;
		this_.parentId = parentId;
		this_.scrollDivId = scrollDivId;
		if(data.length>0){
			var htmlContent = '';
			$.each(data,function(index,row){
				htmlContent += "<tr>";
				htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.bfdw)+"</td>"; 
				htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.zy)+"</td>"; 
				htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.zjbh)+"</td>"; 
				htmlContent += "<td class='text-center' >"+StringUtil.escapeStr(row.bfrq).substring(0,10)+"</td>"; 
				var timestr="";
				if(row.yxqKs){
					timestr = formatUndefine(row.yxqKs).substring(0,10) + "~" + formatUndefine(row.yxqJs).substring(0,10);
				}else{
					if(formatUndefine(row.yxqJs)!=''){
						timestr = "有效期至" + formatUndefine(row.yxqJs).substring(0,10);
					}
				}
				htmlContent += "<td class='text-center' >"+StringUtil.escapeStr(timestr)+"</td>"; 
				htmlContent += "<td title='附件 Attachment' class='text-center'>";
				if((row.attachments.length>0 )){
					htmlContent += '<i djid="'+row.id+'"  class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
				}
				htmlContent += "</td>";
				htmlContent += "</tr>" ;
				$("#maintenance_license_list").empty();
				$("#maintenance_license_list").html(htmlContent);
				this_.initFile();
			});
		}else{
			$("#maintenance_license_list").empty();
			$("#maintenance_license_list").append("<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>");
		}
	},
	//点击附件展开附件列表
	initFile: function(){
		var this_=this;
		WebuiPopoverUtil.initWebuiPopover('attachment-view',"#"+this_.parentId,function(obj){
			return this_.getHistoryAttachmentList(obj);
		});
		$("#"+this_.scrollDivId).scroll(function(){
			$('.attachment-view').webuiPopover('hide');
		});
	},
	getHistoryAttachmentList: function(obj){
		var jsonData = [
			   	         {mainid : $(obj).attr('djid'), type : '附件'}
			   	    ];
			return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
	},
	//附件下载
	downfile : function(id){
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	},
	/**
	 * 初始化机型执照数据
	 */
	setActypeData : function(data, parentId, scrollDivId){
		var this_ = this;
		this_.parentId = parentId;
		this_.scrollDivId = scrollDivId;
		if(data.length>0){
			var htmlContent = '';
			$.each(data,function(index,row){
				htmlContent += "<tr>";
				htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.bfdw)+"</td>"; 
				htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.fjjx)+"</td>"; 
				htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.zy)+"</td>"; 
				htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.sqdj)+"</td>"; 
				htmlContent += "<td class='text-center' >"+StringUtil.escapeStr(row.bfrq).substring(0,10)+"</td>"; 
				var timestr="";
				if(row.yxqKs){
					timestr = formatUndefine(row.yxqKs).substring(0,10) + "~" + formatUndefine(row.yxqJs).substring(0,10);
				}else{
					if(formatUndefine(row.yxqJs)!=''){
						timestr = "有效期至" + formatUndefine(row.yxqJs).substring(0,10);
					}
				}
				htmlContent += "<td class='text-center' >"+StringUtil.escapeStr(timestr)+"</td>"; 
				htmlContent += "<td title='附件 Attachment' class='text-center'>";
				if((row.attachments.length>0 )){
					htmlContent += '<i djid="'+row.id+'"  class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
				}
				htmlContent += "</td>";
				htmlContent += "</tr>" ;
				$("#type_license_list").empty();
				$("#type_license_list").html(htmlContent);
				this_.initFile();
			});
		}else{
			$("#type_license_list").empty();
			$("#type_license_list").append("<tr><td class='text-center' colspan=\"7\">暂无数据 No data.</td></tr>");
		}
	},
};

