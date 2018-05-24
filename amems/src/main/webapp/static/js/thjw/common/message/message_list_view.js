
MessageListViewUtil = {
	id:'MessageListViewUtil',
	tbodyId:'messageListViewTable',
	messageHeadId : 'messageHeadView',
	param: {
		djid:null,//关联单据id
		lx:1,//关联单据类型
		messageHead : true
	},
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		//显示或隐藏头
		if(this.param.messageHead){
			$("#"+this.messageHeadId, $("#"+this.id)).show();
		}else{
			$("#"+this.messageHeadId, $("#"+this.id)).hide();
		}
		//回显数据
		if(this.param.djid){
			this.updateStatusByDjIdUId();
			this.initMessageList();
		}else{
			$("#" + this.tbodyId).append("<tr><td colspan='5'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}
		$("#"+this.id).show();
	},
	initMessageList : function(){
		var this_ = this;
		var searchParam = {};
		searchParam.djid = this.param.djid;
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/aerialmaterial/message/queryMessageList",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(searchParam),
			dataType:"json",
			success:function(data){
				if(data != null && data.length > 0){
					this_.appendMessageHtml(data);
			   } else {
				   this_.clear();
			   }
			}
		});
	},
	updateStatusByDjIdUId : function(){
		AjaxUtil.ajax({
			url:basePath + "/aerialmaterial/message/updateViewStatusByDjIdUId",
			type:"post",
			async: false,
			data:{
				djid : this.param.djid
			},
			dataType:"json",
			success:function(data){
				
			}
		});
	},
	appendMessageHtml : function(list){
		var this_ = this;
		var userId = $("#userId").val();
		var htmlContent = '';
		$.each(list,function(index,row){
			
			var jsrnames = formatUndefine(row.jsrnames);
			var lynr = StringUtil.escapeStr(row.nbwjm);
			if(this_.checkUser(row.messageRecipientsList)){
			   lynr = '<span style="color: red">' + lynr + "</span>";
			}
		   if (index % 2 == 0) {
			   htmlContent += "<tr bgcolor=\"#f9f9f9\" >";
			   
		   } else {
			   htmlContent += "<tr bgcolor=\"#fefefe\"  >";
		   }
		   
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(index+1)+"</td>";	
		   htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+StringUtil.escapeStr(row.wbwjm)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:'')+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:'')+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.nbwjm)+"' style='text-align:left;vertical-align:middle;' >"+lynr+"</td>";
		   htmlContent += "<td title='"+this_.formartTiele(jsrnames)+"' style='text-align:left;vertical-align:middle;'>"+this_.formartJsr(jsrnames)+"</td>"; 
		   htmlContent += "</tr>";  
			    
		});
		$("#" + this.tbodyId).empty();
		$("#" + this.tbodyId).html(htmlContent);

	},
	clear : function(){
		$("#" + this.tbodyId).empty();
		$("#" + this.tbodyId).append("<tr><td colspan='5'  style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	},
	checkUser : function(messageRecipientsList){
		var flag = false;
		if(messageRecipientsList != null && messageRecipientsList.length > 0){
			$.each(messageRecipientsList, function(i, obj){
				if($("#userId").val() == obj.jsrid){
					flag = true;
					return flag;
				}
			});
		}
		return flag;
	},
	formartTiele : function(jsrnames){
		var jsrnameArr = jsrnames.split(",");
		var str = '';
		$.each(jsrnameArr, function(i, jname){
			var zt = jname.substring(0,1);
			var name = jname.substring(1,jname.length);
			if(zt == 0){
				str += name + "(未读),";
			}else{
				str += name + "(已读),";
			}
		});
		
		if(str != ''){
			str = str.substring(0,str.length - 1);
		}
		return str;
	},
	formartJsr : function(jsrnames){
		var jsrnameArr = jsrnames.split(",");
		var str = '';
		$.each(jsrnameArr, function(i, jname){
			var zt = jname.substring(0,1);
			var name = jname.substring(1,jname.length);
			if(zt == 0){
				str += name + ",";
			}else{
				str += '<span style="color: blue">' + name + "</span>,";
			}
		});
		
		if(str != ''){
			str = str.substring(0,str.length - 1);
		}
		return str;
	}
}
