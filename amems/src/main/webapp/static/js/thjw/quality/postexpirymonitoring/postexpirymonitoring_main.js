$(function(){
	Navigation(menuCode, '', '', 'SC11-1', '孙霁', '2017-10-12', '孙霁', '2017-10-14');//加载导航栏
	$('input[name=date-range-picker]').daterangepicker().prev().on("click",
			function() {
				$(this).next().focus();
	});
	postexpirymonitoringMain.reload();
});
var yjtsJb1 = '';
var yjtsJb2 = '';
var yjtsJb3 = '';
var postexpirymonitoringMain={
		pagination : {},
		reload:function(){
			this.goPage(1,"auto","desc");
		},
		gatherSearchParam:function(){
			var searchParam={};
			 searchParam.dprtcode=$("#dprtcode").val();
			 searchParam.keyword=$.trim($('#keyword_search').val());
			 var pgrq=$("#pgrq").val();
			 var paramsMap={};
			 paramsMap.userId = userId;
			 paramsMap.userType = userType;
			 if($("#yxq_search").prop("checked")==true){
				 paramsMap.yxq='yxq';
			 }
			 //授权日期
			 if(null != pgrq && "" != pgrq){
				 var pgrqBegin = pgrq.substring(0,10)+" 00:00:00";
				 var pgrqEnd = pgrq.substring(13,23)+" 23:59:59";
				 paramsMap.pgrqBegin = pgrqBegin;
				 paramsMap.pgrqEnd = pgrqEnd;
			 }
			 searchParam.paramsMap=paramsMap;
			 return searchParam;
		},
		goPage:function(pageNumber,sortType,sequence){
			this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
		},
		AjaxGetDatasWithSearch:function(pageNumber,sortType,sequence){
			 var _this = this;
			 _this.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
			 var searchParam=_this.gatherSearchParam();
			 searchParam.pagination = _this.pagination;
			 AjaxUtil.ajax({
				 url:basePath+"/quality/post/expiry/queryAll",
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(searchParam),
				   success:function(data){
			 			if(data.rows.length > 0){
		 				 yjtsJb1 =data.monitorsettings.yjtsJb1;
						 yjtsJb2 =data.monitorsettings.yjtsJb2;
						 yjtsJb3 =data.monitorsettings.yjtsJb3;
						 
			 			_this.appendContentHtml(data.rows);
			 			//分页
			 			var page_ = new Pagination({
							renderTo : "pagination",
							data: data,
							sortColumn : sortType,
							orderType : sequence,
							extParams:{},
							goPage: function(a,b,c){
								_this.AjaxGetDatasWithSearch(a,b,c);
							}
						});
			 		// 标记关键字
						   signByKeyword($("#keyword_search").val(),[2,3,5]);
			 		
			 		}else{
			 			$("#list").empty();
						$("#pagination").empty();
						$("#list").append("<tr><td class='text-center' colspan=\"13\">暂无数据 No data.</td></tr>");
						
					}
			 			new Sticky({tableId:'quality_check_list_table'});
			 		}
			 });
		},
		reload:function(){
			TableUtil.resetTableSorting("quality_check_list_table");
			this.goPage(1,"auto","desc");
		},
		// 表格拼接
		appendContentHtml:function(list){
			var _this = this;
			var htmlContent = '';
			$.each(list,function(index,row){
				var paramsMap = row.paramsMap?row.paramsMap:{};
					htmlContent += "<tr >";
					htmlContent += getWarningColor(row.paramsMap?row.paramsMap.syts:'');  
				    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.rybh)+"'>"+StringUtil.escapeStr(row.rybh)+"</td>"; 
				    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.xm)+"'>"+StringUtil.escapeStr(row.xm)+"</td>"; 
				    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(paramsMap.fjjx)+"'>"+StringUtil.escapeStr(paramsMap.fjjx)+"</td>"; 
				    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.dgbh:'')+" "+StringUtil.escapeStr(row.paramsMap?row.paramsMap.dgmc:'')+"'>"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.dgbh:'')+" "+StringUtil.escapeStr(row.paramsMap?row.paramsMap.dgmc:'')+"</td>"; 
				    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.paramsMap?(row.paramsMap.sqsj?row.paramsMap.sqsj.substring(0,10):''):'')+"'>"+StringUtil.escapeStr(row.paramsMap?(row.paramsMap.sqsj?row.paramsMap.sqsj.substring(0,10):''):'')+"</td>"; 
				    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.paramsMap?(row.paramsMap.pgsj?row.paramsMap.pgsj.substring(0,10):''):'')+"'>"+StringUtil.escapeStr(row.paramsMap?(row.paramsMap.pgsj?row.paramsMap.pgsj.substring(0,10):''):'')+"</td>"; 
				    var str = '';
				    if(row.paramsMap){
				    	var	ks = row.paramsMap.ksrq?row.paramsMap.ksrq:'';
				    	var	js = row.paramsMap.jzrq?row.paramsMap.jzrq:'';
				    	if(ks != ''){
				    		str = ks.substring(0,10);
				    	}
				    	if(js != ''){
				    		str = js.substring(0,10);
				    	}
				    	if(ks !='' && js !=''){
				    		str = ks.substring(0,10) + "~" + js.substring(0,10);
				    	}
				    }
				    htmlContent += "<td class='text-center' title='"+str+"'>"+str+"</td>"; 
				    htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.syts:'')+"'>"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.syts:'')+"</td>"; 
				    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.wbbs == 1?'内部人员':'外部人员')+"'>"+StringUtil.escapeStr(row.wbbs == 1?'内部人员':'外部人员')+"</td>"; 
				    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:'')+"'>"+StringUtil.escapeStr(row.zdr?row.zdr.displayName:'')+"</td>"; 
				    htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.whsj)+"'>"+StringUtil.escapeStr(row.whsj)+"</td>"; 
				    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.jg_dprt.dprtname)+"'>"+StringUtil.escapeStr(row.jg_dprt.dprtname)+"</td>"; 
				    htmlContent += "</tr>" ;
		   $("#list").empty();
		   $("#list").html(htmlContent);
		   refreshPermission(); 
		 });
		},
		/**
		 * 导出
		 */
		exportExcel : function(){
			var param = this.gatherSearchParam();
			param.pagination = {page:1,sort:"auto",order:"desc",rows:100000};
			param.keyword = encodeURIComponent(param.keyword);
			window.open(basePath+"/quality/post/expiry/postmonitor.xls?paramjson="+JSON.stringify(param));
		}
};

//获取预警颜色
function getWarningColor(syts){
	var htmlappend = "<td class='text-center'></td>";
	if(!syts&&syts!=0){
		return htmlappend;
	}
	if(yjtsJb1 < Number(syts) && Number(syts) <= yjtsJb2){
		bgcolor = warningColor.level2;//黄色
		htmlappend = "<td class='text-center' title='"+yjtsJb1+"&lt;剩余&lt;="+yjtsJb2+"天"+"' ><i class='iconnew-lightbulbnew' style='text-vertical:middle;font-size:16px;color:" + bgcolor+ "' /></td>";
	}
	if(Number(syts) <= yjtsJb1){
		bgcolor = warningColor.level1;//红色
		htmlappend =  "<td class='text-center' title='剩余&lt;="+yjtsJb1+"天"+"' ><i class='iconnew-lightbulbnew' style='text-vertical:middle;font-size:16px;color:" + bgcolor+ "' /></td>";
	}
	return htmlappend;
}
function orderBy(obj){
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
		postexpirymonitoringMain.goPage(currentPage,obj,orderStyle.split("_")[1]);
}

