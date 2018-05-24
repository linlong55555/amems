var bjly=[];
bjly[1]="库房";
bjly[2]="飞机";


/**
 *初始化当前js
 */
$(function(){
	materialList_main.load();
});

materialList_main = {
	id : "materialList_main",
	yjtsJb1 : '',	
	yjtsJb2 : '',
	yjtsJb3 : '',
	idList : [],
	load: function(){
		Navigation(menuCode, '', '', 'SC12-1 ', '林龙', '2017-09-27', '林龙', '2017-10-10');//加载导航栏
		this.initdata();					//初始化基础数据
	    this.decodePageParam();				//解码页面查询条件和分页 并加载数据
	    refreshPermission();				//权限初始化
	},
	/**
	 * 初始化基础数据
	 */
	initdata: function(){
		this.initFjzchOption();				//初始化飞机注册号
		
	},
	/**
	 * 初始化飞机注册号
	 */
	initFjzchOption: function(){
		$("#fjzch_search").html("<option value='' >显示全部All</option>");
		var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE : $("#dprtcode").val()});
		if(planeDatas.length > 0){
			for(var i = 0; i < planeDatas.length; i++){
				$("#fjzch_search").append("<option value='"+StringUtil.escapeStr(planeDatas[i].FJZCH)+"'>"+StringUtil.escapeStr(planeDatas[i].FJZCH)+ " " + StringUtil.escapeStr(planeDatas[i].XLH)+"</option>");
			}
		}else{
			$("#fjzch_search").html("<option value=''>显示全部All</option>");
		}
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
	 * 切换组织机构
	 */
	onchangeDprtcode: function(){
		this.initdata();					//初始化基础数据
	},
	/**
	 *	搜索
	 */
	search: function(){
		var this_=this;
		this_.goPage(1,"auto","desc");
		TableUtil.resetTableSorting(this_.id+"_Table");
	},
	/**
	 *	跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
	 */
	goPage: function(pageNumber,sortType,sequence){
		this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	},
	/**
	 *	查询条件
	 */
	gatherSearchParam: function(){
		var searchParam={};
		var paramsMap={};
		
		searchParam.keyword=$.trim($('#keyword_search').val()); //关键字
		searchParam.fjzch=$.trim($('#fjzch_search').val()); 			//飞机注册号
		searchParam.bjly=$.trim($('#bjly_search').val()); 				//部件类别
		searchParam.dprtcode=$.trim($("#dprtcode").val());		//组织机构
		
		 var sfkyList = [];
		 $('input[name="sfky_search"]').each(function(i){     //影响放行
			 if(this.checked){
				 sfkyList.push($(this).val());
			 }
			});
		 if(sfkyList.length == 0){
			 sfkyList.push(2);
		 }
		 paramsMap.sfky = sfkyList;
		 
		 if($("#isSh1").attr("checked") == "checked" && $("#isSh2").attr("checked") != "checked" ){
			paramsMap.sh = 1;
		 }
		 if($("#isSh1").attr("checked") != "checked" && $("#isSh2").attr("checked") == "checked"){
			 paramsMap.sh =2;
		 }
		 
		var tlrq=$.trim($("#tlrq_search").val()); 	//计划使用日期
	    var tlrqBeginDate="";				 		//开始日期 
	    var tlrqEndDate="";							//结束日期
		if(null != tlrq && "" != tlrq){
			tlrqBeginDate= tlrq.substring(0,4)+"-"+tlrq.substring(5,7)+"-"+tlrq.substring(8,10)+" 00:00:00";
			tlrqEndDate= tlrq.substring(12,17)+"-"+tlrq.substring(18,20)+"-"+tlrq.substring(21,23)+" 23:59:59";
		}
		paramsMap.tlrqBeginDate=tlrqBeginDate;
		paramsMap.tlrqEndDate=tlrqEndDate;
		paramsMap.tlr=$.trim($("#tlr_search").val());//退料人
		paramsMap.wllbs="2,3";
		searchParam.paramsMap=paramsMap;
		
		return searchParam;
	},
	/**
	 * 加载需求信息
	 */
	AjaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
		
		var this_=this;
		var searchParam=this_.gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		searchParam.pagination = pagination;
		if (id != "") {
			searchParam.id = id;
			id = "";
		}
		AjaxUtil.ajax({
		   url:basePath+"/material/return/queryAllPageList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   if(data.total >0){
	 				this_.appendContentdemandinfoHtml(data.rows);
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
	 	 			signByKeyword($("#keyword_search").val(), [4,5,6,7,12]);
	 			}else{
		 			$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"13\">暂无数据 No data.</td></tr>");
	 			}
	 			new Sticky({tableId:this_.id+"_Table"}); //初始化表头浮动
		   }
	  }); 	
		
		
	},
	/**
	 * 表格拼接
	 */
	appendContentdemandinfoHtml: function(list){
		var this_=this;
		 var htmlContent = '';
		 $.each(list,function(index,row){
				htmlContent += "<tr  id='"+row.id+"' onclick=\"materialList_main.showHiddenContent("+index+",this)\">";
				htmlContent += "<td class='text-center fixed-column' ><input type='checkbox' index='"+index+"' name='checkrow' dprtcode='"+row.dprtcode+"'  checkid='"+row.id+"' index='"+index+"' onclick=\"SelectUtil.checkRow(this,'selectAllId')\" /></td>";
			    
			    htmlContent += "<td class='text-center' ><a href='javascript:;' onclick=\"materialList_main.checkview('"+row.id+"')\">"+StringUtil.escapeStr(row.tlrq).substring(0,10)+"</a></td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.whr?row.whr.id:'')+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.bjh)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.bjmc)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.pch)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.xlh)+"</td>";
			    var str='';
			    if(row.bjly == 2){
			    	str=bjly[row.bjly]+" "+ row.fjzch;
			    }else{
			    	str=bjly[row.bjly];
			    }
			    htmlContent += "<td class='text-left' >"+str+"</td>";//
			    htmlContent += "<td class='text-right' >"+StringUtil.escapeStr(row.tlsl)+"</td>";//
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.dw)+"</td>";//
			    htmlContent += "<td class='text-center' >"+StringUtil.escapeStr((row.sfky == 1) ? '可用':'不可用')+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.sm)+"</td>";
			    
			    htmlContent += "<td class='text-left' >";
				    $.each(row.receivingRelationshiplist,function(index,row1){
	    				if(StringUtil.escapeStr(row1.paramsMap?row1.paramsMap.shr:' ') != ' '){
	    					if(row1.paramsMap?row1.paramsMap.sjkw:'' != ''){
	    						htmlContent += StringUtil.escapeStr(row1.paramsMap?row1.paramsMap.sjkw:'');//
	    					}else{
	    						htmlContent += StringUtil.escapeStr(row.paramsMap?row.paramsMap.ckh:'')+" "+StringUtil.escapeStr(row.paramsMap?row.paramsMap.kwh:'')+" "+StringUtil.escapeStr(row.paramsMap?row.paramsMap.lscfwz:'');//
	    					}
	    					
	    					htmlContent +="; "+StringUtil.escapeStr(row1.paramsMap?row1.paramsMap.sl:'')+"; "+StringUtil.escapeStr(row1.paramsMap?row1.paramsMap.shr:'')+"; "+StringUtil.escapeStr(row1.paramsMap?row1.paramsMap.shrq:'').substring(0,10);
	    					htmlContent += "<br/>";
			    		}
				    });
			    htmlContent += "</td>";
			    
			    htmlContent += "</tr>" ;
			    
			    $("#"+this_.id+"_list").empty();
			    $("#"+this_.id+"_list").html(htmlContent);
			    refreshPermission();							//权限初始化
			    TableUtil.addTitle("#"+this_.id+"_list tr td"); //加载td title
		 });
	},
	/**
	 * 查看当前页面
	 */
	checkview: function(id){
		window.open(basePath+"/material/return/find/"+id);
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
	 * 搜索条件重置
	 */
	searchreset: function(){
		var this_=this;
//		 $("#divSearch").find("input").each(function() {
//				$(this).attr("value", "");
//		 });
		 $("#divSearch").find("textarea").each(function(){
			 $(this).val("");
		 });
		 $("#divSearch").find("select").each(function(){
				$(this).val("");				
		 });
		 var SelectArr1 = $("#fjzchDiv select"); //飞机注册号重置为第一个
		 if(SelectArr1[0].options[0] != undefined){
			 SelectArr1[0].options[0].selected = true;
			 
		 }
		 
		 $("input:checkbox[name='sh_search']").attr("checked",true);
		 $("input:checkbox[name='sfky_search']").attr("checked",true);
		 
		 $("#tlrq_search").val("");
		 $("#bjly_search").val("");
		 $("#tlr_search").val("");
		 $("#keyword_search").val("");
		 $("#dprtcode").val(userJgdm);
		 this_.initdata();			//初始化基础数据
		 TableUtil.resetTableSorting(this_.id+"_Table");
	},
	/**
	 * 搜索条件显示与隐藏 
	 */
	openOrHide: function(){
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
	/**
	 * 全选
	 */
	performSelectAll: function(){
		$(":checkbox[name=checkrow]").attr("checked", true);
	},
	/**
	 * 取消全选
	 */
	performSelectClear: function(){
		$(":checkbox[name=checkrow]").attr("checked", false);
	},
	/**
	 * 点击行选择 
	 */
	showHiddenContent: function(index,this_){
		var $checkbox1 = $("#materialList_main_list :checkbox[name='checkrow']:eq("+index+")");
		var $checkbox2 = $(".sticky-col :checkbox[name='checkrow']:eq("+index+")");
		var checked = $checkbox1.is(":checked");
		$checkbox1.attr("checked", !checked);
		$checkbox2.attr("checked", !checked);
		
	},
	/**
	 * 导出
	 */
	exportExcel: function(){
		var param = this.gatherSearchParam();
		window.open(basePath+"/material/return/backTools.xls?paramjson="+JSON.stringify(param));
	},
	/**
	 * 收货
	 */
	receiving: function(){
		var this_=this;
		if($("#"+this_.id+"_list").find("tr input:checked").length > 0){
			this_.setParam();
			window.open(basePath+"/material/outin/receipt?type=returnData");
		}else{
			AlertUtil.showMessage("请选择数据!");
		}
	},
	/**
	 * 存入参数
	 */
	setParam: function(){
		var this_=this;
		var idList = [];
		var dprtcode="";
		$("#"+this_.id+"_list").find("tr input:checked").each(function(){
			var checkid = $(this).attr("checkid");
			dprtcode=$(this).attr("dprtcode");
			idList.push(checkid);
		});
		var params = {};
		params.dprtcode = dprtcode;
		params.idList = idList;
//		pageParam.params = params;
		localStorage.setItem("returnData",JSON.stringify(params));
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

