/**
 *初始化当前js
 */
$(function(){
	if(paramJgdm != null && paramJgdm != ''){
		$("#dprtcode").val(paramJgdm);
		paramJgdm = null;
	}
	toolcheck.load();
});

toolcheck = {
	id : "toolcheck",
	yjtsJb1 : '',	
	yjtsJb2 : '',
	yjtsJb3 : '',
	load: function(){
		Navigation(menuCode, '', '', 'SC12-1 ', '林龙', '2017-09-27', '林龙', '2017-10-10');//加载导航栏
	    this.loadMonitorsettings();			//初始化预警阀值
	    this.decodePageParam();				//解码页面查询条件和分页 并加载数据
	    refreshPermission();				//权限初始化
	},
	/**
	 *	执行预警阀值（机构代码）
	 */
	loadMonitorsettings: function(){
		var this_=this;
		AjaxUtil.ajax({
			url:basePath + "/quality/toolcheck/getTechnicalThreshold",
			type:"post",
			async: false,
			data:{
				dprtcode : $("#dprtcode").val()
			},
			dataType:"json",
			success:function(data){
				if(data != null && data.monitorsettings != null){
					this_.yjtsJb1 = data.monitorsettings.yjtsJb1;
					this_.yjtsJb2 = data.monitorsettings.yjtsJb2;
					this_.yjtsJb3 = data.monitorsettings.yjtsJb3;
				}
				isLoadMonitor = true;
			}
		});
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
		this.loadMonitorsettings();			//初始化预警阀值
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
		hideBottom(); //重置流程信息
		var searchParam={};
		var paramsMap={};
		
		searchParam.keyword=$.trim($('#keyword_search').val()); //关键字
		
		var jyXcrq=$.trim($("#jyXcrq").val()); 	//下次校验日期
	    var jyXcBeginDate="";				 	//开始日期 
	    var jyXcEndDate="";						//结束日期
		if(null != jyXcrq && "" != jyXcrq){
			jyXcBeginDate= jyXcrq.substring(0,4)+"-"+jyXcrq.substring(5,7)+"-"+jyXcrq.substring(8,10)+" 00:00:00";
			jyXcEndDate= jyXcrq.substring(12,17)+"-"+jyXcrq.substring(18,20)+"-"+jyXcrq.substring(21,23)+" 23:59:59";
		}
		
		var jyScqx=$.trim($("#jyScrq").val()); 	//最近校验日期
		var jyScBeginDate="";				 	//开始日期 
		var jyScEndDate="";						//结束日期
		if(null != jyScqx && "" != jyScqx){
			jyScBeginDate= jyScqx.substring(0,4)+"-"+jyScqx.substring(5,7)+"-"+jyScqx.substring(8,10)+" 00:00:00";
			jyScEndDate= jyScqx.substring(12,17)+"-"+jyScqx.substring(18,20)+"-"+jyScqx.substring(21,23)+" 23:59:59";
		}
		if(paramYjbs != null && paramYjbs != ''){
			paramsMap.yjbs = paramYjbs;
			paramYjbs = null;
		}
		paramsMap.dprtcode=$.trim($("#dprtcode").val());		//组织机构
		paramsMap.jyXcBeginDate=jyXcBeginDate;
		paramsMap.jyXcEndDate=jyXcEndDate;
		paramsMap.jyScBeginDate=jyScBeginDate;
		paramsMap.jyScEndDate=jyScEndDate;
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
		   url:basePath+"/quality/toolcheck/queryAllPageList",
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
					signByKeyword($("#keyword_search").val(), [3,4,5,6,15,16],"#toolcheck_list tr td");
	 			}else{
		 			$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"21\">暂无数据 No data.</td></tr>");
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
				htmlContent += "<tr  onclick=\"tracking_log.showHiddenContent('"+row.id+"',this)\">";
				htmlContent += "<td class='fixed-column text-center'>";
					htmlContent += "<i class=' icon-edit color-blue cursor-pointer checkPermission' " 
						+ " permissioncode='produce:reservation:defect:main:02' onClick=\"toolcheck_update_modal.open('"+row.id+"')\" title='修改 Edit'></i>&nbsp;&nbsp;";//修改
					htmlContent += "<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='produce:reservation:defect:main:03' " 
		    			+ " onClick=\"toolcheck.invalid('"+ row.id + "','"+ row.zt +"')\" title='删除 Delete'></i>&nbsp;&nbsp;"; 
			    htmlContent += "</td>";
			    
		    	htmlContent +=this_.getWarningColor("#f9f9f9",parseInt(row.paramsMap?row.paramsMap.syts:'')); 
			    htmlContent += "<td class='fixed-column text-left' ><a href=\"javascript:toolcheck.findView('"+row.id+"')\">"+StringUtil.escapeStr(row.bjxlh)+"</a></td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.ywms)+" "+StringUtil.escapeStr(row.zwms)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.gg)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.xh)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.jlfs)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.jldj)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.jyZq)+StringUtil.escapeStr((row.jyZqdw == 11) ? '天':'月' )+"</td>";
			    htmlContent += "<td class='text-center' >"+formatUndefine(row.jyScrq).substring(0,10)+"</td>";
			    htmlContent += "<td class='text-center' >"+formatUndefine(row.jyXcrq).substring(0,10)+"</td>";
			    htmlContent += "<td class='text-right' >"+StringUtil.escapeStr(row.paramsMap.syts)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.bz)+"</td>";
			    htmlContent += "<td title='附件 Attachment' class='text-center'>";
				if(row.paramsMap.attachCount != null && row.paramsMap.attachCount > 0){
					htmlContent += '<i qtid="'+row.id+'"  class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
				}
				htmlContent += "</td>";						
										
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.mes.bjxlh)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.mes.bjid)+"</td>";
			    if(StringUtil.escapeStr(row.paramsMap.ckh) !=''){
			    	htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.paramsMap.ckh)+" "+StringUtil.escapeStr(row.paramsMap.ckmc)+" "+StringUtil.escapeStr(row.paramsMap.kwh)+"</td>"; 
			    }else{
			    	htmlContent += "<td class='text-left' >不在库</td>"; 
			    }
			    htmlContent += "<td class='text-center' >"+formatUndefine(row.paramsMap.rksj).substring(0,10)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.whr?row.whr.id:'')+"</td>";
			    htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.whsj)+"</td>"; 
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.dprt?row.dprt.dprtname:'')+"</td>"; 
			    htmlContent += "</tr>" ;
			    
			    $("#"+this_.id+"_list").empty();
			    $("#"+this_.id+"_list").html(htmlContent);
			    refreshPermission();							//权限初始化
			    TableUtil.addTitle("#"+this_.id+"_list tr td"); //加载td title
			    this_.init();
		 });
	},
	/**
	 * 点击附件展开附件列表
	 */
	init: function(id){
		var this_=this;
		WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
			return this_.getHistoryAttachmentList(obj);
		});
		$("#work_card_main_table_top_div").scroll(function(){
			$('.attachment-view').webuiPopover('hide');
		});
	},
	/**
	 * 点击附件展开附件列表
	 */
	getHistoryAttachmentList: function(obj){
		var jsonData = [
			   	         {mainid : $(obj).attr('qtid'), type : '附件'}
			   	    ];
			return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
	},
	/**
	 * 查看当前页面
	 */
	findView: function(id){
		window.open(basePath+"/quality/toolcheck/find/"+id);
	},
	/**
	 * 删除
	 */
	invalid: function(id,zt){
		var this_=this;
		var obj = {};
		var paramsMap = {};
		paramsMap.currentZt =zt; //当前状态
		obj.paramsMap = paramsMap;
		obj.id=id;
		AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
			startWait();
			AjaxUtil.ajax({
				url:basePath + "/quality/toolcheck/delete",
				contentType:"application/json;charset=utf-8",
				type:"post",
				async: false,
				data:JSON.stringify(obj),
				dataType:"json",
				success:function(data){
					finishWait();
					AlertUtil.showMessage("删除成功!");
					this_.decodePageParam();
				}
			});
		}});
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
//		 var SelectArr1 = $("#jxdiv select"); //机型重置为第一个
//		 SelectArr1[0].options[0].selected = true;
//		 $("input:checkbox[name='ztList']").attr("checked",true);
		 $("#jyXcrq").val("");
		 $("#keyword_search").val("");
		 $("#dprtcode").val(userJgdm);
		 this_.loadMonitorsettings();			//初始化预警阀值
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
	 * 熏染预警颜色
	 */
	getWarningColor: function(bgcolor,syts){
		var this_=this;
		if (this_.yjtsJb1 < Number(syts) && Number(syts) <= this_.yjtsJb2) {
			bgcolor = warningColor.level2;// 黄色		 
			return "<td  class='fixed-column text-center ' title='"+this_.yjtsJb1+"天&lt;剩余&lt;="+this_.yjtsJb2+"天"+"'>"+ "<i class='iconnew-lightbulbnew' style='font-size:16px;color:" + bgcolor+ "' />" + "</td>";
		}
		if (Number(syts) <= this_.yjtsJb1) {
			bgcolor = warningColor.level1;// 红色
			return "<td  class='fixed-column text-center ' title='剩余&lt;="+this_.yjtsJb1+"天"+"' >"+ "<i class='iconnew-lightbulbnew' style='font-size:16px;color:" + bgcolor+ "' />" + "</td>";
		}
		return "<td  class='text-center fixed-column'></td>";
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
	},
	/**
	 * 当前页面导出
	 */
	exportExcel: function(){
		var fjzch=$.trim($("#fjzch").val()); 			//飞机注册号
		var dprtcode=$.trim($("#dprtcode").val());		//组织机构
		var keyword=$.trim($('#keyword_search').val()); //关键字
		
		var sqrq=$.trim($("#sqrqDate").val()); 	//获取申请日期
	    var sqBeginDate="";				 		//开始日期 
	    var sqEndDate="";						//结束日期
		if(null != sqrq && "" != sqrq){
			sqBeginDate= sqrq.substring(0,4)+"-"+sqrq.substring(5,7)+"-"+sqrq.substring(8,10)+" 00:00:00";
			sqEndDate= sqrq.substring(12,17)+"-"+sqrq.substring(18,20)+"-"+sqrq.substring(21,23)+" 23:59:59";
		}
		
		var xfqx=$.trim($("#xfqxDate").val()); 	//获取修复日期
		var xfBeginDate="";				 		//开始日期 
		var xfEndDate="";						//结束日期
		if(null != xfqx && "" != xfqx){
			xfBeginDate= xfqx.substring(0,4)+"-"+xfqx.substring(5,7)+"-"+xfqx.substring(8,10)+" 00:00:00";
			xfEndDate= xfqx.substring(12,17)+"-"+xfqx.substring(18,20)+"-"+xfqx.substring(21,23)+" 23:59:59";
		}
		
		var sqr=$.trim($("#sqr1").val());		//申请人
		window.open(basePath+"/produce/reservation/defect/DefectReservation.xls?dprtcode="
	 				+ dprtcode 
	 				+ "&fjzch="+encodeURIComponent(fjzch)+"&sqBeginDate="+encodeURIComponent(sqBeginDate)
	 				+ "&sqEndDate="+encodeURIComponent(sqEndDate)+"&xfBeginDate="+encodeURIComponent(xfBeginDate)
	 				+ "&xfEndDate="+encodeURIComponent(xfEndDate)+ "&sqr="+encodeURIComponent(sqr)
	 				+ "&keyword="+ encodeURIComponent(keyword));
	}
};

$('.date-picker').datepicker({
	format:'yyyy-mm-dd',
	autoclose : true
}).next().on("click", function() {
	$(this).prev().focus();
});
$('input[name=date-range-picker]').daterangepicker({"opens":"left"}).prev().on("click",function() {
	$(this).next().focus();
});
$('#example27').multiselect({
	buttonClass : 'btn btn-default',
	buttonWidth : 'auto',
	includeSelectAllOption : true
}); 

function showData(){
	NoMaintenanceModal.show({
		callback:function(data){
			if(data.bjh){
				toolcheck_add_modal.open();
				$("#bjxlh",$("#toolcheck_open_modal")).val(data.sn||"");
				$("#bjid",$("#toolcheck_open_modal")).val(data.bjid||"");
				$("#bjh",$("#toolcheck_open_modal")).val(data.bjh||"");
				$("#bjmc",$("#toolcheck_open_modal")).val(((data.zwms||"")+" "+(data.ywms||"")));
				$("#wz",$("#toolcheck_open_modal")).val(((data.kwh||"")+" "+(data.ckh||"")+" "+(data.ckmc||"")));
				$("#rksj",$("#toolcheck_open_modal")).val((data.rksj||"").substr(0,10));
				$("#pch",$("#toolcheck_open_modal")).val(data.pch||"");
				$("#kcsl",$("#toolcheck_open_modal")).val(((data.kcsl||"")+" "+(data.jldw||"")));
				$("#zbjxlh",$("#toolcheck_open_modal")).val(data.sn||"");	
				$("#zzwms",$("#toolcheck_open_modal")).val(data.zwms||"");
				$("#zywms",$("#toolcheck_open_modal")).val(data.ywms||"");
				$("#zxh",$("#toolcheck_open_modal")).val(data.xh||"");
				$("#zgg",$("#toolcheck_open_modal")).val(data.gg||"");

				
			}
			
		}		
	});
	
}
//未维护数据导出
function exportTool(){
	var param={};
	param.keyword=$.trim($("#keyword_no_maintenance_search").val());
	param.dprtcode=userJgdm;
	window.open(basePath+"/material/stock/material/NoMaintenanceInfo.xls?json="+JSON.stringify(param));
}
function customResizeHeight(bodyHeight, tableHeight){
	
	if($(".displayContent").css("display")=="block"){
		var panelBody=$("#toolcheckBody").outerHeight();
		var searchHeight=$("#toolcheckBody .searchContent").eq(0).outerHeight();
		var tableHeight=$("#toolcheckBody .table-height").eq(0).outerHeight();
		var displayHeight=panelBody-searchHeight-tableHeight-50;
		$(".displayContent").find("table").parent().css({"height":displayHeight+"px","overflow":"auto"})
	}
}