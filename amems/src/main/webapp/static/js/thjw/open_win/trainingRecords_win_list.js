trainingRecords_main = {
	tableId:'trainingRecords_table_list',
	tbodyId:'trainingRecords_tbody_list',
	paginationId:'trainingRecords_Pagination',
	id:'trainingRecords_main',
	param: {
		wxrydaid:'',
		kcbm:'',
		wxry:'',
		callback:function(){},
		dprtcode:userJgdm,//默认登录人当前机构代码
		id:null,
	},
	show : function(param){
		$("#trainingRecords_main").modal("show");
		if(param){
			$.extend(this.param, param);
		}
		this.clearData();
		this.goPage(1,"auto","desc");
	},
	clearData : function(){
		var this_ = this;
		$("#kcdm").val(this_.param.kcbm);
		$("#wxry").val(this_.param.wxry);
	},
 	goPage : function(pageNumber, sortType, sequence){
 		this.AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
	},	
	load: function(pageNumber,sortType,sequence){
		this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	},
	AjaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
		var searchParam = this.gatherSearchParam();
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
		   url : basePath + "/training/course/queryAllByBhAndRy",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   if(data.total >0){
				   this_.data = data.rows;
				   this_.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : "trainingRecords_Pagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
					}); 
				   // 标记关键字
				   //signByKeyword($("#keyword_trainingRecords_search").val(),[2,3,4],"#trainingRecords_tbody_list tr td");
			   } else {
				  $("#trainingRecords_tbody_list").empty();
				  $("#trainingRecords_Pagination").empty();
				  $("#trainingRecords_tbody_list").append("<tr><td colspan=\"16\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   //new Sticky({tableId:"open_win_chapter_basic_table"});
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var this_ = this;
		 var searchParam = {};
		 var paramsMap = {};
		 searchParam.dprtcode = this_.param.dprtcode;
		 searchParam.wxrydaid = this_.param.wxrydaid;
		 searchParam.kcbm = this_.param.kcbm;
		 searchParam.paramsMap=paramsMap;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			htmlContent += "<tr >";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.kcmc)+"'>"+StringUtil.escapeStr(row.kcmc)+"</td>";
			
			var sjrqq=formatUndefine(row.sjKsrq).substring(0,10)+(formatUndefine(row.sjKssj)==""?"":" "+formatUndefine(row.sjKssj).substring(0,5));
			var sjrqh=formatUndefine(row.sjJsrq).substring(0,10)+(formatUndefine(row.sjJssj)==""?"":" "+formatUndefine(row.sjJssj).substring(0,5));
			var sjrq=sjrqq==""?sjrqh:sjrqq+(sjrqh==""?"":"~"+sjrqh);
			htmlContent += "<td  style=\"vertical-align: middle;\" class='text-center' title='"+sjrq+"'>"+sjrq+"</td>";
			
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.fjjx)+"'>"+StringUtil.escapeStr(row.fjjx)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.zy)+"'>"+StringUtil.escapeStr(row.zy)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.pxlb)+"'>"+StringUtil.escapeStr(row.pxlb)+"</td>";
			htmlContent += "<td  style=\"vertical-align: middle;\" class='text-center' title='"+StringUtil.escapeStr(row.fxbs == 1?"初训":"复训")+"'>"+StringUtil.escapeStr(row.fxbs == 1?"初训":"复训")+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.pxxs)+"'>"+StringUtil.escapeStr(row.pxxs)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.pxgh)+"'>"+StringUtil.escapeStr(row.pxgh)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.kcdd)+"'>"+StringUtil.escapeStr(row.kcdd)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.jsxm)+"'>"+StringUtil.escapeStr(row.jsxm)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.cql?row.cql:'0')+"%"+"'>"+StringUtil.escapeStr(row.cql?row.cql:'0')+"%"+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.cj)+"'>"+StringUtil.escapeStr(row.cj)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.zs)+"'>"+StringUtil.escapeStr(row.zs)+"</td>";
			htmlContent += "<td  style=\"vertical-align: middle;\" class='text-center' title='"+StringUtil.escapeStr(row.khjg== 1?"通过":"未通过")+"'>"+StringUtil.escapeStr(row.khjg== 1?"通过":"未通过")+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>";
			
			htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
			if(row.paramsMap != null && row.paramsMap.attachCount > 0){
				htmlContent += '<i fjid="'+row.id+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
			}
			htmlContent += "</td>",
			htmlContent += "</tr>"; 
		    
		});
		$("#trainingRecords_tbody_list", $("#trainingRecords_main")).empty();
		$("#trainingRecords_tbody_list", $("#trainingRecords_main")).html(htmlContent);
		initWebuiPopover();
	}
};
function initWebuiPopover(){//初始化
	WebuiPopoverUtil.initWebuiPopover('attachment-view','#trainingRecords_main',function(obj){
		return getHistoryAttachmentList(obj);
	});
	$("#trainingRecords_table_list").scroll(function(){
		$('.attachment-view').webuiPopover('hide');
	});
}
function getHistoryAttachmentList(obj){//获取历史附件列表
	var jsonData = [
         {mainid : $(obj).attr('fjid'), type : '附件'},
    ];
	return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
}
