var zy=["","内部人员","外部人员"];
var xxlx={
		1:'授权',
		21:'维修执照',
		22:'机型执照'
};

$(document).ready(function(){
	Navigation(menuCode);
//	logModal.init({code:'B_Z_00101'});
	planemonitor.init();
	refreshPermission();
});

var planemonitor = {
		id:'planemonitor',
		monitorsettings: {},
		data: {},
		init: function() {
			$(".date-picker").datepicker({
				autoclose : true,
				clearBtn : true
			});
			
			this.search();
		},
		search: function() {
			this.load(1);
			TableUtil.resetTableSorting("planemonitor_Table");
		},
		load : 	function(pageNumber, sortColumn, orderType){
			if(typeof(pageNumber) == "undefined"){
				pageNumber = 1;
			}
			if(typeof(sortColumn) == "undefined"){
				sortColumn = "auto";
			} 
			if(typeof(orderType) == "undefined"){
				orderType = "desc";
			} 
			
			var obj ={};
			obj.pagination = {page:pageNumber, sort:sortColumn, order:orderType, rows:20};
			$.extend(obj, this.getParams());
			startWait();
			var this_ = this;
			AjaxUtil.ajax({
				url: basePath+"/quality/planemonitor/page",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify(obj),
				success:function(data){
					 
					if(data.total >0){
						this_.monitorsettings = data.monitorsettings;
						this_.data = data.rows;
						this_.appendContentHtml(data.rows);
						new Pagination({
							renderTo : this_.id+"_pagination",
							data: data,
							sortColumn : sortColumn,
							orderType : orderType,
							controller: this_
						}); 
						// 标记关键字
						signByKeyword($("#"+this_.id+"_keyword_search").val(),[2,3,4], "#"+this_.id+"_list tr td");
					} else {
						$("#"+this_.id+"_list").empty();
						$("#"+this_.id+"_pagination").empty();
						$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"14\">暂无数据 No data.</td></tr>");
					}
					//new Sticky({tableId:"open_win_regist_basic_table"});
					this_.loaded = true;
					finishWait();
			    }
			}); 
		},	
		getParams : function(){
			var searchParam = {};
			var paramsMap = {};
			var keyword = $.trim($("#"+this.id+"_keyword_search").val());
			var dprtcode = $.trim($("#dprtcode").val());
			 if('' != dprtcode){
				 paramsMap.dprtcode = dprtcode;
			 }
			 
			 if('' != keyword){
				 paramsMap.keyword = keyword;
			 }
			 
			 var dqrq = $.trim($("#dqrq").val());
			 if(dqrq != ''){
				 paramsMap.dqrqStart = dqrq.substring(0,10)+" 00:00:00";
				 //paramsMap.dqrqEnd = dqrq.substring(13,23)+" 23:59:59";
			 }
			 
			 searchParam.paramsMap = paramsMap;
			 searchParam.dprtcode = dprtcode;
			 searchParam.xxlx = 22
			 return searchParam;
		},
		getWarningColor: function (syts){
			var htmlappend = "<td class='text-center'></td>";
			//有剩余天数，计算，否则直接返回TD
				var yjtsJb1 = this.monitorsettings.yjtsJb1;
				var yjtsJb2 = this.monitorsettings.yjtsJb2;
				var yjtsJb3 = this.monitorsettings.yjtsJb3;
				
				if(yjtsJb1 < Number(syts) && Number(syts) <= yjtsJb2){
					bgcolor = warningColor.level2;//黄色
					htmlappend = "<td class='text-center' title='"+yjtsJb1+"&lt;剩余评估期限&lt;="+yjtsJb2+"天"+"' ><i class='iconnew-lightbulbnew' style='text-vertical:middle;font-size:16px;color:" + bgcolor+ "' /></td>";
				}
				if(Number(syts) <= yjtsJb1){
					bgcolor = warningColor.level1;//红色
					htmlappend =  "<td class='text-center' title='剩余评估期限&lt;="+yjtsJb1+"天"+"' ><i class='iconnew-lightbulbnew' style='text-vertical:middle;font-size:16px;color:" + bgcolor+ "' /></td>";
				}
			return htmlappend;
			
		},
		appendContentHtml: function(list){
			var htmlContent = '';
			var this_ = this;
			$.each(list,function(index,row){
				
				if (index % 2 == 0) {
					htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  >";
				} else {
					htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\"   >";
				}
				
				var syts = (row.paramsMap && (row.paramsMap.syts) && (row.paramsMap.syts >= 0)) ? row.paramsMap && row.paramsMap.syts : null
						 
				var yjhtml = this_.getWarningColor(row.paramsMap.syts);
				var mp  = row.maintenancePersonnel||{};
				htmlContent += yjhtml;
				
				htmlContent += "<td title='"+StringUtil.escapeStr(mp.rybh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(mp.rybh)+"</td>";  
				htmlContent += "<td title='"+StringUtil.escapeStr(mp.xm)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(mp.xm)+"</td>";  
				
				htmlContent += "<td title='"+StringUtil.escapeStr(row.bfdw)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bfdw)+"</td>";
				htmlContent += "<td title='"+StringUtil.escapeStr(row.fjjx)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.fjjx)+"</td>";
				htmlContent += "<td title='"+StringUtil.escapeStr(row.zy)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.zy)+"</td>";
				
				htmlContent += "<td title='"+StringUtil.escapeStr(row.zjbh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.zjbh)+"</td>";
				htmlContent += "<td title='"+StringUtil.escapeStr(formatUndefine((row.bfrq||'').substring(0,10)))+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(formatUndefine((row.bfrq||'').substring(0,10)))+"</td>";
				htmlContent += "<td title='"+StringUtil.escapeStr(formatUndefine((row.yxqJs||'').substring(0,10)))+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(formatUndefine((row.yxqJs||'').substring(0,10)))+"</td>";
				
				var businesses = row.businesses||{};
				var businessNames = [];
				$.each(businesses,function(idx,item) {
					if(item.dgmc) {
						businessNames.push(formatUndefine(item.dgmc));
					}
				});
				
				htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.syts)+"' style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.paramsMap.syts)+"</td>";
				htmlContent += "<td title='"+StringUtil.escapeStr(zy[mp.wbbs] )+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(zy[mp.wbbs])+"</td>";
				htmlContent += "<td title='"+StringUtil.escapeStr(businessNames.join())+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(businessNames.join())+"</td>";
				
				htmlContent += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
				if((row.paramsMap.attachCount != null && row.paramsMap.attachCount > 0) ){
					htmlContent += '<i zzid="'+row.id+'" xxlx="'+row.xxlx+'"  class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
				}
				htmlContent += "</td>";
				
				var dept  = row.department ||{};
				htmlContent += "<td title='"+StringUtil.escapeStr(dept.dprtname)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(dept.dprtname)+"</td>";
				
				
			   	htmlContent += "</tr>";
		   });
		   $("#"+this_.id+"_list").html(htmlContent);
		   //SelectUtil.selectNode('selectAllId',this_.id+"_list");
		   this_.initWebuiPopover();
		},
		orderBy: function (obj){
			// 字段排序
			var orderStyle = $("#" + obj + "_order").attr("class");
			$("th[id$=_order]").each(function() { //重置class
				$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
			});
			$("#" + obj + "_" + "order").removeClass("sorting");
			var orderType = "asc";
			if (orderStyle.indexOf("sorting_asc") >= 0) {
				$("#" + obj + "_" + "order").addClass("sorting_desc");
				orderType = "desc";
			} else {
				$("#" + obj + "_" + "order").addClass("sorting_asc");
				orderType = "asc";
			}
			orderStyle = $("#" + obj + "_order").attr("class");
			var currentPage = $("#pagination li[class='active']").text();
			this.load(currentPage,obj.replace('-','.'),orderStyle.split("_")[1]);
		},
		initWebuiPopover : function(){//初始化附件
			var this_ = this;
			WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
				return this_.getHistoryAttachmentList(obj);
			});
			$("#table_pagination").scroll(function(){
				$('.attachment-view').webuiPopover('hide');
			});
		},
		getHistoryAttachmentList : function(obj){//获取历史附件列表
			
			var jsonData = [
		         {mainid : $(obj).attr('zzid'), type : StringUtil.escapeStr(xxlx[$(obj).attr('xxlx')]) },
		    ];
			return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
		},

}
 
function exportExcel(){
	var keyword = $.trim($("#planemonitor_keyword_search").val());
	var dprtcode = $("#dprtcode").val();
	 var dqrq = $.trim($("#dqrq").val());
	 var dqrqStart='';
	 var dqrqEnd='';
	 if(dqrq != ''){
		 dqrqStart = dqrq.substring(0,10)+" 00:00:00";
		 dqrqEnd = dqrq.substring(13,23)+" 23:59:59";
	 }
	window.open(basePath+"/quality/planemonitor/LicenseValidMonitor.xls?dprtcode="
 				+ dprtcode 
 				+ "&dqrqStart="+encodeURIComponent(dqrqStart)
 				+ "&dqrqEnd="+encodeURIComponent(dqrqEnd)
 				+ "&xxlx="+encodeURIComponent(22)
 				+"&keyword="+ encodeURIComponent(keyword));
	
}
