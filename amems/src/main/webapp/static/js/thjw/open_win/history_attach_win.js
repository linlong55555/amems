
history_attach_alert_Modal = {
	id : 'history_attach_alert_Modal',
	attachMap : {},
	getHistoryAttachmentList : function(jsonData){
		var this_ = this;
		this_.attachMap = {};
		var mainidList = [];
		$.each(jsonData,function(index,row){
			mainidList.push(row.mainid);
			this_.attachMap[row.mainid] = row.type;
		});
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath + "/common/loadPlaneDataAttachmentsByMainIds",
			type:"post",
			async : false,
			data:{
				idList:mainidList
			},
			dataType:"json",
			success:function(data){
				finishWait();
				if(data.success){
					this_.appendContentHtml(data.attachments);
				}
			}
		});
		return $("#"+this.id).html();
	},
	appendContentHtml: function(list){
		var this_ = this;
		var htmlContent = '';
		$.each(list,function(index,row){
		   
			var fj = StringUtil.escapeStr(row.wbwjm);
			if(StringUtil.escapeStr(row.hzm) != ''){
				fj += "."+StringUtil.escapeStr(row.hzm);
			}
			htmlContent = htmlContent + "<tr>";
			htmlContent += "<td title='"+this_.attachMap[row.mainid]+"' class='text-center' style='padding-left:0px;'>"+this_.attachMap[row.mainid]+"</td>";
			htmlContent += '<td title="'+fj+'" class="text-left">';
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".downloadfile('"+row.id+"') >"+fj+"</a>";
			htmlContent += '</td>';
			htmlContent += "</tr>"; 
		    
		});
		$("#history_list", $("#"+this_.id)).empty();
		$("#history_list", $("#"+this_.id)).html(htmlContent);
	},
	downloadfile : function(id){
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	},
}