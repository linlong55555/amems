history_certificate_alert_Modal = {
	id:'history_certificate_alert_Modal',
	param: {
		callback:function(){},
		dprtcode:userJgdm,//默认登录人当前机构代码
		jh:null,
		xlh:null,
		parentid:null//第一层modal
	},
	data:[],
	show : function(param){
		var this_ = this;
		$("#history_certificate_alert_Modal").modal("show");
		if(param){
			$.extend(this.param, param);
		}
		this_.goPage(1,"auto","desc");
	},
 	goPage : function(pageNumber, sortType, sequence){
 		this.AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
	},
	load: function(pageNumber,sortType,sequence){
		this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	},
	AjaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
		var searchParam = this.gatherSearchParam();
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
			url:basePath+"/produce/componenthistory/queryCertificateList",
			type:"post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(searchParam),
			dataType:"json",
			success:function(data){
				finishWait();
				if(data.rows.length > 0){
					this_.appendContentHtml(data.rows);
				}else{
					$("#certigicateList").empty();
					$("#certigicatepagination").empty();
					$("#certigicateList").append("<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>");
				}
			}
		}); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.dprtcode = this.param.dprtcode;
		 searchParam.jh = this.param.jh;
		 searchParam.xlh = this.param.xlh;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			var parent=row;
				htmlContent += "<tr onclick='history_certificate_alert_Modal.showChildTR(this,\""+row.id+"\")' bgcolor='#f9f9f9'>";
				htmlContent += "<td>";
				htmlContent += "&nbsp;&nbsp;&nbsp;&nbsp;<span><i rowid='"+row.id+"' class='fa fa-angle-down'></i></span>&nbsp;&nbsp;&nbsp;&nbsp;";
				htmlContent += "<span class='badge' style='background:#3598db;'>"+(row.attachments?row.attachments.length:0)+"</span>";
				htmlContent += "</td>";
				htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.zjlx))+"'>"+StringUtil.escapeStr(formatUndefine(row.zjlx))+"</td>";
				htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.zsbh))+"'>"+StringUtil.escapeStr(formatUndefine(row.zsbh))+"</td>";
				htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.zscfwz))+"'>"+StringUtil.escapeStr(formatUndefine(row.zscfwz))+"</td>";
				htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(formatUndefine(row.qsrq?row.qsrq.substring(0,10):''))+"'>"+StringUtil.escapeStr(formatUndefine(row.qsrq?row.qsrq.substring(0,10):''))+"</td>";
				htmlContent += "<td class='text-left' title='添加附件'><a>添加附件</a></td>";
			    htmlContent += "</tr>"
		    	$.each(row.attachments,function(index,row){
		    		htmlContent +="<tr name='detailRow' id='"+row.id+"' class='"+parent.id+" "+(row.yxbs == 0?"delete-lineoragne":"")+"' bgcolor='#fefefe' style='display:none;text-decoration:' onclick='maintenanceItemList.showBottomDiv(event,this.id)'>";
					htmlContent +="<td class='text-left' colspan='6'><a href=\"javascript:history_certificate_alert_Modal.downloadfile('"+row.id+"')\">"+row.wbwjm+"."+row.hzm+"</a></td>";
					htmlContent +="</tr>";
		    	});
			    	
		});
		$("#certigicateList").empty();
		$("#certigicateList").html(htmlContent);
	},
	// 展开/收缩所有行
	toggleAll : function(){
		var th = $("th[name='ope_td']");
		if(th.hasClass("downward")){
			this.expend();
		}else{
			this.collapse();
		}
		
		new Sticky({tableId:'maintenance_item_table'});
	},
	// 展开
	expend : function(){
		$("th[name='ope_td']").removeClass("downward").addClass("upward");
		$('#certigicateList .fa.fa-angle-down').attr("class","fa fa-angle-up cursor-pointer");
		$('tr[name="detailRow"]').css("display","table-row");
	},
	
	// 收缩
	collapse : function(){
		$("th[name='ope_td']").removeClass("upward").addClass("downward");
		$('#certigicateList .fa.fa-angle-up').attr("class","fa fa-angle-down cursor-pointer");
		$('tr[name="detailRow"]').css("display","none");
	},
	showChildTR:function(obj,name){
		if($(obj).find("[rowid='"+name+"']").hasClass("fa fa-angle-down")){
			$('[rowid="'+name+'"]').attr("class","fa fa-angle-up cursor-pointer");
			$("."+name).css("display","table-row");
		}else{
			$('[rowid="'+name+'"]').attr("class","fa fa-angle-down cursor-pointer");
			$("."+name).css("display","none");
		}
		new Sticky({tableId:'maintenance_item_table'});
	},
	setData: function(){
		var data = $("#"+this.viewPositionId).tagsinput('items');
		
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback(data);
		}
		$("#"+this.id).modal("hide");
	},
	downloadfile : function(id){
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	}
}


