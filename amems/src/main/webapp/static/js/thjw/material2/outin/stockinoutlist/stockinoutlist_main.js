
/**
 *初始化当前js
 */
$(function(){
	stockinout.load();
});

stockinout = {
	id : "stockinout",
	load: function(){
		Navigation(menuCode, '', '', 'SC12-1 ', '林龙', '2017-09-27', '林龙', '2017-10-10');//加载导航栏
	    this.decodePageParam();				//解码页面查询条件和分页 并加载数据
	    refreshPermission();				//权限初始化
	    this.initStockHistorySubtype();
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
		var stockHistory ={};
		stockHistory.czlx=$.trim($('#czlx_search').val()); 		//操作类型
		stockHistory.czzlx=$.trim($('#czzlx_search').val());    //操作子类型
		stockHistory.czr=$.trim($('#czr_search').val());        //操作人
		
		var hcMainData ={};
		hcMainData.hclx=$.trim($('#hclx_search').val());       //航材类型
		searchParam.cqid=$.trim($('#cqid_search').val());       //产权id
		
		var czsjrq=$.trim($("#czsj_search").val()); 		//操作日期
	    var czsjBeginDate="";				 	//开始日期 
	    var czsjEndDate="";						//结束日期
		if(null != czsjrq && "" != czsjrq){
			czsjBeginDate= czsjrq.substring(0,4)+"-"+czsjrq.substring(5,7)+"-"+czsjrq.substring(8,10)+" 00:00:00";
			czsjEndDate= czsjrq.substring(12,17)+"-"+czsjrq.substring(18,20)+"-"+czsjrq.substring(21,23)+" 23:59:59";
		}
	
		paramsMap.czsjBeginDate=czsjBeginDate;
		paramsMap.czsjEndDate=czsjEndDate;
		searchParam.paramsMap=paramsMap;
		searchParam.stockHistory=stockHistory;
		searchParam.hcMainData=hcMainData;
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
	
		AjaxUtil.ajax({
		   url:basePath+"/material/outin/materialHistoryList",
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
					signByKeyword($("#keyword_search").val(), [3,6,7],"#stockinout_list tr td");
	 			}else{
		 			$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
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
				 if(StringUtil.escapeStr(row.stockHistory?row.stockHistory.czzlx:'') == ''){
					 htmlContent += "<td class='text-left' >"+StringUtil.escapeStr( DicAndEnumUtil.getEnumName('stockHistoryTypeEnum',row.stockHistory?row.stockHistory.czlx:'') )+"</td>";
				 }else{
					 htmlContent += "<td class='text-left' >"+StringUtil.escapeStr( DicAndEnumUtil.getEnumName('stockHistorySubtypeEnum',row.stockHistory?row.stockHistory.czzlx:'') )+"</td>";
				 }
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(DicAndEnumUtil.getEnumName('materialTypeEnum',row.hcMainData?row.hcMainData.hclx:'') )+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.bjh)+"</td>";
			    htmlContent += "<td class='text-right' >"+StringUtil.escapeStr(row.kcsl)+"</td>";
			    htmlContent += "<td class='text-center' >"+StringUtil.escapeStr(row.stockHistory?row.stockHistory.czsj:'')+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.pch)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.sn)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.cqbh:'')+"</td>";
				var kccb = StringUtil.escapeStr(row.kccb);
				if(kccb == ''){
					htmlContent += "<td ></td>"; 
				}else{
					kccb = kccb.toFixed(2);
					htmlContent += "<td class='text-right' >"+kccb+" "+formatUndefine(row.biz)+"</td>"; 
				}
//				var jz = StringUtil.escapeStr(row.jz);
//				if(jz == ''){
//					htmlContent += "<td ></td>"; 
//				}else{
//					jz = jz.toFixed(2);
//					htmlContent += "<td class='text-right' >"+jz+" "+formatUndefine(row.jzbz)+"</td>"; 
//				}
			    htmlContent += "<td class='text-left' ><a href=\"javascript:stockinout.findView('"+StringUtil.escapeStr(row.stockHistory?row.stockHistory.ywid:'')+"','"+StringUtil.escapeStr(row.stockHistory?row.stockHistory.czlx:'')+"')\">"+StringUtil.escapeStr(row.stockHistory?row.stockHistory.ywbh:'')+"</a></td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.stockHistory?row.stockHistory.czr:'')+"</td>";
			    htmlContent += "</tr>" ;
			    $("#"+this_.id+"_list").empty();
			    $("#"+this_.id+"_list").html(htmlContent);
			    refreshPermission();							//权限初始化
			    TableUtil.addTitle("#"+this_.id+"_list tr td"); //加载td title
		 });
	},
	/**
	 * 跳转到查看页面
	 */
	findView: function(id,lx){
		
		if(lx==10){
			window.open(basePath+"/material/outin/receipt?id="+id+"&type=view");
		}
		if(lx==20){
			window.open(basePath+"/material/outin/viewOut?id="+id);
		}
		if(lx ==50){
			 window.open(basePath + "/material/scrapped/apply/view?id=" + id);
		}
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
		 $("#czsj_search").val("");
		 $("#czlx_search").val("");
		 $("#czzlx_search").val("");
		 $("#keyword_search").val("");
		 $("#dprtcode").val(userJgdm);
	    this.initStockHistorySubtype();
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
	},
	//产权
	loadCq : function(){
		var this_=this;
		cqModal.show({
			selected:$("#"+this_.id+"_cqid").val(),
			dprtcode:$("#dprtcode").val(),
			callback:function(data){
				$("#cqid_search").val(data.id);
				$("#cqbh_search").val(data.cqbh);
			}
			
		});
	},
	//初始化弹出库位
	initStockHistorySubtype : function(){
		if($("#czlx_search").val()!=""){
			var czlx = parseInt($("#czlx_search").val());
			var list = DicAndEnumUtil.getEnum("stockHistorySubtypeEnum");
			var kwOptionHtml = "<option value=\"\">显示全部</option>";
			for (var i = 0; i < list.length; i++) {
				if(parseInt(list[i].id) > czlx && parseInt(list[i].id) < (czlx+10)){
						kwOptionHtml += "<option value='"+list[i].id+"'>"+StringUtil.escapeStr(list[i].name)+"</option>";
				}
			}
			$("#czzlx_search").html(kwOptionHtml);
		}else{
			var htmlContent1 = '<option value=\"\">显示全部</option>';
			$("#czzlx_search").html(htmlContent1);
		}
		stockinout.search();//搜索
	},
	/**
	 * 当前页面导出
	 */
	exportExcel: function(){
		var this_=this;
		var param = this_.gatherSearchParam();
		window.open(basePath+"/material/outin/MaterialHistoryInfo.xls?json="+JSON.stringify(param));
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
