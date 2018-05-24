var yjjg=[];
    yjjg[1]="合格Qualified";
    yjjg[2]="不合格Unqualified";
    yjjg[3]="让步接收Compromise";
/**
 *初始化当前js
 */
$(function(){
	testing.load();
	
	//执行待办
    if(todo_ywid && todo_jd == 33){
    	testing_open_alert.open(todo_ywid);
    	todo_ywid = null;
    }
	
});

testing = {
	id : "testing",
	load: function(){
		Navigation(menuCode, '', '', 'SC12-1 ', '林龙', '2017-09-27', '林龙', '2017-10-10');//加载导航栏
	    this.decodePageParam();				//解码页面查询条件和分页 并加载数据
	    refreshPermission();				//权限初始化
	},
	/**
	 *	解码页面查询条件和分页并加载数据
	 */
	decodePageParam: function(){
		var this_=this;
		TableUtil.resetTableSorting(this_.id+"_Table");
		try{
			var decodeStr = Base64.decode(pageParam);
			var pageParamJson = JSON.parse(decodeStr);
			var params = pageParamJson.params;
			$("#dprtcode").val(params.dprtcode);
			if(pageParamJson.pagination){
				this_.goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
			}else{
				this_.goPage(1,"auto","desc");
			}
		}catch(e){
			this_.goPage(1,"auto","desc");
		}
	},
	/**
	 *	编码页面查询条件和分页
	 */
	encodePageParam: function(){
		var pageParam = {};
		var params = {};
		params.dprtcode  = $.trim($("#dprtcode").val());       //组织机构
		params.keyword   = $.trim($("#keyword_search").val()); //关键字
		pageParam.params = params;
		pageParam.pagination = pagination;
		return Base64.encode(JSON.stringify(pageParam));
	},
	/**
	 *	跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
	 */
	goPage: function(pageNumber,sortType,sequence){
		
		this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	},
	/**
	 * 切换组织机构
	 */
	onchangeDprtcode: function(){
//		this.loadMonitorsettings();			//初始化预警阀值
	},
	/**
	 *	搜索
	 */
	search: function(){
		this.goPage(1,"auto","desc");
		TableUtil.resetTableSorting(this.id+"_Table");
		
	},
	/**
	 *	查询条件
	 */
	gatherSearchParam: function(){
		var searchParam={};
		var paramsMap={};
		
		searchParam.keyword=$.trim($('#keyword_search').val()); //关键字
		searchParam.dprtcode=$.trim($('#dprtcode').val());      //组织机构
		searchParam.jyjg=$.trim($('#jyjg_search').val());       //检验结果
		
		
		var ztList = [];
		$('input[name="zt_search"]').each(function(i){	//待检和已检
			 if(this.checked){
				 if($(this).val()==1){
					 ztList.push("-1");
					 ztList.push("1");
				 }else{
					 ztList.push("2");
				 }
			 }
		});
		if(ztList.length == 0){
			ztList.push(3);
		}
		paramsMap.ztList = ztList;
		
		var shrq=$.trim($("#shrq_search").val()); 		//收货日期
	    var shrqBeginDate="";				 	//开始日期 
	    var shrqEndDate="";						//结束日期
		if(null != shrq && "" != shrq){
			shrqBeginDate= shrq.substring(0,4)+"-"+shrq.substring(5,7)+"-"+shrq.substring(8,10)+" 00:00:00";
			shrqEndDate= shrq.substring(12,17)+"-"+shrq.substring(18,20)+"-"+shrq.substring(21,23)+" 23:59:59";
		}
		
		var jyrq=$.trim($("#jyrq_search").val()); 		//检验日期
		var jyrqBeginDate="";				 	//开始日期 
		var jyrqEndDate="";						//结束日期
		if(null != jyrq && "" != jyrq){
			jyrqBeginDate= jyrq.substring(0,4)+"-"+jyrq.substring(5,7)+"-"+jyrq.substring(8,10)+" 00:00:00";
			jyrqEndDate= jyrq.substring(12,17)+"-"+jyrq.substring(18,20)+"-"+jyrq.substring(21,23)+" 23:59:59";
		}
	
		paramsMap.shrqBeginDate=shrqBeginDate;
		paramsMap.shrqEndDate=shrqEndDate;
		paramsMap.jyrqBeginDate=jyrqBeginDate;
		paramsMap.jyrqEndDate=jyrqEndDate;
		searchParam.paramsMap=paramsMap;
		return searchParam;
	},
	/**
	 * 查询主单分页列表
	 */
	AjaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
		var this_=this;
		var searchParam = this_.gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		searchParam.pagination = pagination;
		if (id != "") {
			searchParam.id = id;
			id = "";
		}
		
		AjaxUtil.ajax({
		   url:basePath+"/material/inspection/inspectionList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
	 			if(data.total >0){
	 				this_.appendContentHtml(data.rows);
		 			new Pagination({
						renderTo : this_.id+"_pagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						extParams:{},
						goPage: function(a,b,c){
							this_.AjaxGetDatasWithSearch(a,b,c);
						}
					});
		 			// 标记关键字
					signByKeyword($("#keyword_search").val(), [3,4,5,6,7,9,15],"#"+this_.id+"_list tr td");
	 			}else{
		 			$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"18\">暂无数据 No data.</td></tr>");
	 			}
	 			new Sticky({tableId:this_.id+"_Table"}); //初始化表头浮动
		   }
	  }); 	
	},
	/**
	 * 表格拼接
	 */
	appendContentHtml: function(list){
		var this_=this;
		 var htmlContent = '';
		 $.each(list,function(index,row){
				htmlContent += "<tr  >";
				htmlContent += "<td class='fixed-column text-center'>";
				if(row.zt == -1 || row.zt == 1){
					htmlContent += "<i class='icon-random color-blue cursor-pointer  ' " 
						+ "   onClick=\"testing_open_alert.open('"+row.id+"')\" title='检验 Check'></i>&nbsp;&nbsp;";//修改
				}
			    htmlContent += "</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('inspectionStatusEnum',row.zt) )+"</td>";
			    htmlContent += "<td class='text-left' ><a href=\"javascript:testing.findView('"+row.id+"')\">"+StringUtil.escapeStr(row.jydh)+"</a></td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.bjh:'')+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.bjmc:'')+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.pch:'')+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.sn:'')+"</td>";
			    htmlContent += "<td class='text-right' >"+StringUtil.escapeStr(row.kysl)+" "+StringUtil.escapeStr(row.hcMainData?row.hcMainData.jldw:'')+"</td>";
			    htmlContent += "<td class='text-left' ><a href=\"javascript:testing.findShdView('"+row.shdid+"')\">"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.shdh:'')+"</a></td>";
			    htmlContent += "<td class='text-center' >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.shrq:'').substring(0,10)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.wz:'')+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.cqbh:'')+"</td>";
			    htmlContent += "<td class='text-center' >"+StringUtil.escapeStr(row.jyrq).substring(0,10)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(yjjg[row.jyjg])+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.jgsm)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.jyr:'')+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.whr:'')+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.zdsj)+"</td>";
			    htmlContent += "</tr>" ;
			    $("#"+this_.id+"_list").empty();
			    $("#"+this_.id+"_list").html(htmlContent);
			    refreshPermission();							//权限初始化
			    TableUtil.addTitle("#"+this_.id+"_list tr td"); //加载td title
		 });
	},
	/**
	 * 查看当前界面
	 */
	findView: function(id){
		window.open(basePath+"/material/inspection/find/"+id);
	},
	/**
	 * 查看收货单界面
	 */
	findShdView: function(id){
		window.open(basePath+"/material/outin/receipt?id="+id+"&type=view");
	},
	/**
	 * 搜索条件重置
	 */
	searchreset: function(){
		var this_=this;
		 $("#divSearch").find("input").each(function() {
				$(this).attr("value", "");
		 });
		 $("#divSearch").find("textarea").each(function(){
			 $(this).val("");
		 });
		 $("#divSearch").find("select").each(function(){
				$(this).val("");				
		 });
		 $("#shrq_search").val("");
		 $("#jyrq_search").val("");
		 $("#jyjg_search").val("");
		 $("#keyword_search").val("");
		 
		 $("input:checkbox[name='zt_search']:first").attr("checked",true);
		 $("input:checkbox[name='zt_search']:last").attr("checked",false);
		 $("#dprtcode").val(userJgdm);
	},
	/**
	 * 字段排序
	 */
	orderBy: function(obj){
		var this_=this;
		var orderStyle = $("#" + obj + "_order").attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
		});
		$("#" + obj + "_" + "order").removeClass("sorting");
		if (orderStyle.indexOf("sorting_asc")>=0) {
			$("#" + obj + "_" + "order").addClass("sorting_desc");
		} else {
			$("#" + obj + "_" + "order").addClass("sorting_asc");
		}
		orderStyle = $("#" + obj + "_order").attr("class");
		var currentPage = $("#pagination li[class='active']").text();
		this_.goPage(currentPage,obj,orderStyle.split("_")[1]);
	},
	/**
	 * 搜索条件显示与隐藏 
	 */
	moreSearch: function(bgcolor,syts){
		if ($("#divSearch").css("display") == "none") {
			$("#divSearch").css("display", "block");
			App.resizeHeight();
			$("#icon").removeClass("icon-caret-down");
			$("#icon").addClass("icon-caret-up");
		} else {
			$("#divSearch").css("display", "none");
			App.resizeHeight();
			$("#icon").removeClass("icon-caret-up");
			$("#icon").addClass("icon-caret-down");
		}
	}
	
};

$('.date-picker').datepicker({
	format:'yyyy-mm-dd',
	autoclose : true
}).next().on("click", function() {
	$(this).prev().focus();
});
$('input[name=date-range-picker]').daterangepicker().prev().on("click",function() {
	$(this).next().focus();
});
$('#example27').multiselect({
	buttonClass : 'btn btn-default',
	buttonWidth : 'auto',
	includeSelectAllOption : true
}); 

//下载
installationlist = {
		 downloadfile:function(id) {
			PreViewUtil.handle(id, PreViewUtil.Table.D_011);
		}
}
function certificateOpen(){
	$("#installation_certificate_modal").modal("show");
}