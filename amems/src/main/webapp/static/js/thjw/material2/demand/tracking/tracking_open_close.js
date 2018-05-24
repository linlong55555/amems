/**
 *初始化当前js
 */
$(function(){
	tracking_open_alert.load();
	 AlertUtil.hideModalFooterMessage();    	   //初始化错误信息 
});

tracking_open_alert = {
	id : "tracking_open_alert",
	idList : [],
	load: function(){
		this.initdata();					//初始化基础数据
	    this.decodePageParam();				//解码页面查询条件和分页 并加载数据
	    refreshPermission();				//权限初始化
	},
	/**
	 * 初始化基础数据
	 */
	initdata: function(){
		this.initFjzchOption();				//初始化飞机注册号
		this.initDemandType();				//初始化需求类型
		
	},
	/**
	 * 初始化需求类型
	 */
	initDemandType: function(){
		$("#"+this.id+"_xqlb").empty();
		$("#"+this.id+"_xqlb").html("<option value='' >显示全部All</option>");
		DicAndEnumUtil.registerDic('84',this.id+'_xqlb',$("#dprtcode").val());
	},
	/**
	 * 初始化飞机注册号
	 */
	initFjzchOption: function(){
		$("#"+this.id+"_fjzch").html("<option value='' >显示全部All</option><option value='null'>N/A</option>");
		var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE : $("#dprtcode").val()});
		if(planeDatas.length > 0){
			for(var i = 0; i < planeDatas.length; i++){
				$("#"+this.id+"_fjzch").append("<option value='"+StringUtil.escapeStr(planeDatas[i].FJZCH)+"'>"+StringUtil.escapeStr(planeDatas[i].FJZCH)+ " " + StringUtil.escapeStr(planeDatas[i].XLH)+"</option>");
			}
		}else{
			$("#"+this.id+"_fjzch").html("<option value=''>显示全部All</option><option value='null'>N/A</option>");
		}
	},
	/**
	 *	解码页面查询条件和分页并加载数据
	 */
	decodePageParam: function(){
		var this_=this;
		this_.AjaxGetDatasWithSearch(1,"auto","desc");
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
		this.AjaxGetDatasWithSearch(1,"auto","desc");
	},
	/**
	 *	查询条件
	 */
	gatherSearchParam: function(){
		var this_=this;
		var searchParam={};
		var paramsMap={};
		
		searchParam.keyword=$.trim($('#'+this_.id+'_keyword_search').val()); //关键字
		searchParam.fjzch=$.trim($('#'+this_.id+'_fjzch').val()); 			//飞机注册号
		searchParam.xqlb=$.trim($('#'+this_.id+'_xqlb').val()); 				//需求类别
		searchParam.dprtcode=$.trim($("#dprtcode").val());		//组织机构
		
		 var isYxfxList = [];
		 $('input[name="'+this_.id+'_isYxfx_search"]').each(function(i){     //影响放行
			 if(this.checked){
				 isYxfxList.push($(this).val());
			 }
			});
		 if(isYxfxList.length == 0){
			 isYxfxList.push(2);
		 }
		 paramsMap.isYxfx = isYxfxList;
		 var isFjhtcList = [];
		 $('input[name="'+this_.id+'_isFjhtc_search"]').each(function(i){	//非计划停场
			 if(this.checked){
				 isFjhtcList.push($(this).val());
			 }
		 });
		 if(isFjhtcList.length == 0){
			 isFjhtcList.push(2);
		 }
		 paramsMap.isFjhtc = isFjhtcList;
		
		
		var jhsyrq=$.trim($("#"+this_.id+"_jhsyrq_search").val()); 	//计划使用日期
	    var jhsyrqBeginDate="";				 	//开始日期 
	    var jhsyrqEndDate="";						//结束日期
		if(null != jhsyrq && "" != jhsyrq){
			jhsyrqBeginDate= jhsyrq.substring(0,4)+"-"+jhsyrq.substring(5,7)+"-"+jhsyrq.substring(8,10)+" 00:00:00";
			jhsyrqEndDate= jhsyrq.substring(12,17)+"-"+jhsyrq.substring(18,20)+"-"+jhsyrq.substring(21,23)+" 23:59:59";
		}
		
		var sqsj=$.trim($("#"+this_.id+"_sqsj_search").val()); 	//停报日期
		var sqsjBeginDate="";				 	//开始日期 
		var sqsjEndDate="";						//结束日期
		if(null != sqsj && "" != sqsj){
			sqsjBeginDate= sqsj.substring(0,4)+"-"+sqsj.substring(5,7)+"-"+sqsj.substring(8,10)+" 00:00:00";
			sqsjEndDate= sqsj.substring(12,17)+"-"+sqsj.substring(18,20)+"-"+sqsj.substring(21,23)+" 23:59:59";
		}
		paramsMap.jhsyrqBeginDate=jhsyrqBeginDate;
		paramsMap.jhsyrqEndDate=jhsyrqEndDate;
		paramsMap.sqsjBeginDate=sqsjBeginDate;
		paramsMap.sqsjEndDate=sqsjEndDate;
		paramsMap.zts="9";
		searchParam.paramsMap=paramsMap;
		
		return searchParam;
	},
	/**
	 * 查询主单列表
	 */
	AjaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
		var this_=this;
		var searchParam = this_.gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		searchParam.pagination = pagination;
		AjaxUtil.ajax({
		   url:basePath+"/material/demand/queryAllClosePageList",
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
						sortColumn : "auto",
						orderType : "desc",
						extParams:{},
						goPage: function(a,b,c){
							this_.AjaxGetDatasWithSearch(a,b,c);
						}
					});
	 	 			// 标记关键字
					signByKeyword($("#tracking_open_alert_keyword_search").val(), [7]);
	 			}else{
		 			$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"9\">暂无数据 No data.</td></tr>");
	 			}
	 		
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
				htmlContent += "<tr >";
				htmlContent += "<td class='text-left' ><a href='javascript:;' onClick=\"tracking_open_alert.findView('"+row.id+"')\">"+StringUtil.escapeStr(row.bh)+"</a></td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.xqlb)+"</td>";
			    htmlContent += "<td class='text-center' >"+StringUtil.escapeStr(row.jhsyrq).substring(0,10)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.fjzch)+"</td>";
			    htmlContent += "<td class='text-center' >"+StringUtil.escapeStr(row.isYxfx==1?"是":"否")+"</td>";
			    htmlContent += "<td class='text-center' >"+StringUtil.escapeStr(row.isFjhtc==1?"是":"否")+"</td>";//
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.xqyy)+"</td>";//
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.gmjy)+"</td>";//
			    htmlContent += "<td class='text-center' >"+StringUtil.escapeStr(row.sqsj)+"</td>";
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
	findView: function(id){
		window.open(basePath+"/material/demand/find?id="+id);
	},
	/**
	 * 搜索条件重置
	 */
	searchreset: function(){
		var this_=this;
//		 $("#divSearchModal").find("input").each(function() {
//				$(this).attr("value", "");
//		 });
		 $("#divSearchModal").find("textarea").each(function(){
			 $(this).val("");
		 });
		 $("#divSearchModal").find("select").each(function(){
				$(this).val("");				
		 });
		 var SelectArr1 = $("#"+this_.id+"_fjzchDiv select"); //飞机注册号重置为第一个
		 if(SelectArr1[0].options[0] != undefined){
			 SelectArr1[0].options[0].selected = true;
		 }
		 
		 var SelectArr3 = $("#"+this_.id+"_xqlbDiv select"); //需求类别重置为第一个
		 if(SelectArr3[0].options[0] != undefined){
			 SelectArr3[0].options[0].selected = true;
			 
		 }
		 
		 $("input:checkbox[name='"+this_.id+"_isYxfx_search']").attr("checked",true);
		 $("input:checkbox[name='"+this_.id+"_isFjhtc_search']").attr("checked",true);
		 
		 $("#"+this_.id+"_jyXcrq").val("");
		 $("#"+this_.id+"_jhsyrq_search").val("");
		 $("#"+this_.id+"_sqsj_search").val("");
		 $("#"+this_.id+"_keyword_search").val("");
		 this_.initdata();			//初始化基础数据
	},
	/**
	 * 搜索条件显示与隐藏 
	 */
	openOrHide: function(bgcolor,syts){
		if ($("#divSearch").css("display") == "none") {
			$("#divSearch").css("display", "block");
			$("#icon").removeClass("icon-caret-down");
			$("#icon").addClass("icon-caret-up");
		} else {
			$("#divSearch").css("display", "none");
			$("#icon").removeClass("icon-caret-up");
			$("#icon").addClass("icon-caret-down");
		}
	}
};


