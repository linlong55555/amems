var bzzt=[];
bzzt[1]="待处理";
bzzt[2]="处理中";
bzzt[3]="完成";


/**
 *初始化当前js
 */
$(function(){
	tracking.load();
});

tracking = {
	id : "tracking",
	yjtsJb1 : '',	
	yjtsJb2 : '',
	yjtsJb3 : '',
	idList : [],
	load: function(){
		Navigation(menuCode, '', '', 'SC12-1 ', '林龙', '2017-09-27', '林龙', '2017-10-10');//加载导航栏
		 $("#"+this.id+"_info_list").append("<tr><td class='text-center' colspan=\"19\">暂无数据 No data.</td></tr>");
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
		$("#xqlb").empty();
		$("#xqlb").html("<option value='' >显示全部All</option>");
		DicAndEnumUtil.registerDic('84','xqlb',$("#dprtcode").val());
	},
	/**
	 * 初始化飞机注册号
	 */
	initFjzchOption: function(){
		$("#fjzch").html("<option value='' >显示全部All</option><option value='null'>N/A</option>");
		var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE : $("#dprtcode").val()});
		if(planeDatas.length > 0){
			for(var i = 0; i < planeDatas.length; i++){
				$("#fjzch").append("<option value='"+StringUtil.escapeStr(planeDatas[i].FJZCH)+"'>"+StringUtil.escapeStr(planeDatas[i].FJZCH)+ " " + StringUtil.escapeStr(planeDatas[i].XLH)+"</option>");
			}
		}else{
			$("#fjzch").html("<option value=''>显示全部All</option><option value='null'>N/A</option>");
		}
	},
	/**
	 *	解码页面查询条件和分页并加载数据
	 */
	decodePageParam: function(){
		var this_=this;
		this_.AjaxGetDatasWithSearch();
		$("#"+this.id+"_info_list").empty();
		$("#"+this.id+"_pagination").empty();
		$("#"+this.id+"_info_list").append("<tr><td class='text-center' colspan=\"19\">暂无数据 No data.</td></tr>");
		this_.idList=[];
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
		this.decodePageParam();
		hideBottom(); //重置流程信息
		TableUtil.resetTableSorting("tracking_table");
	},
	/**
	 *	查询条件
	 */
	gatherSearchParam: function(){
		var searchParam={};
		var paramsMap={};
		
		searchParam.keyword=$.trim($('#keyword_search').val()); //关键字
		searchParam.fjzch=$.trim($('#fjzch').val()); 			//飞机注册号
		searchParam.zt=$.trim($('#zts').val()); 				//状态
		searchParam.xqlb=$.trim($('#xqlb').val()); 				//需求类别
		searchParam.dprtcode=$.trim($("#dprtcode").val());		//组织机构
		searchParam.sqrid=userId;								//申请人id = 当前登录人id
		
		 var isYxfxList = [];
		 $('input[name="isYxfx_search"]').each(function(i){     //影响放行
			 if(this.checked){
				 isYxfxList.push($(this).val());
			 }
			});
		 if(isYxfxList.length == 0){
			 isYxfxList.push(2);
		 }
		 paramsMap.isYxfx = isYxfxList;
		 
		 var isFjhtcList = [];
		 $('input[name="isFjhtc_search"]').each(function(i){	//非计划停场
			 if(this.checked){
				 isFjhtcList.push($(this).val());
			 }
		 });
		 if(isFjhtcList.length == 0){
			 isFjhtcList.push(2);
		 }
		 paramsMap.isFjhtc = isFjhtcList;
		
		
		var jhsyrq=$.trim($("#jhsyrq_search").val()); 	//计划使用日期
	    var jhsyrqBeginDate="";				 	//开始日期 
	    var jhsyrqEndDate="";						//结束日期
		if(null != jhsyrq && "" != jhsyrq){
			jhsyrqBeginDate= jhsyrq.substring(0,4)+"-"+jhsyrq.substring(5,7)+"-"+jhsyrq.substring(8,10)+" 00:00:00";
			jhsyrqEndDate= jhsyrq.substring(12,17)+"-"+jhsyrq.substring(18,20)+"-"+jhsyrq.substring(21,23)+" 23:59:59";
		}
		
		var sqsj=$.trim($("#sqsj_search").val()); 	//停报日期
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
		paramsMap.zts="1,2,4,6";
		searchParam.paramsMap=paramsMap;
		
		return searchParam;
	},
	/**
	 * 查询主单列表
	 */
	AjaxGetDatasWithSearch: function(){
		var this_=this;
		var searchParam = this_.gatherSearchParam();
	
		AjaxUtil.ajax({
		   url:basePath+"/material/demand/queryAllList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
	 			if(data.length > 0){
	 				this_.appendContentHtml(data);
		 			// 标记关键字
	 				signByKeyword();
	 				
	 				
	 				 var s = $("#keyword_search").val();  
	 			    var reg = new RegExp("(" + s + ")", "g");  
	 				
	 			}else{
		 			$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_list").append("<div class='col-xs-12 padding-left-0 padding-right-0 text-center' style='height:30px;line-height:30px;border-bottom:1px solid #d5d5d5;'>暂无数据 No data.</div>");
	 			}
		   }
	  }); 	
	},
	/**
	 * 表格拼接
	 */
	appendContentHtml: function(list){
		var this_=this;
		var html = "";
		 $.each(list,function(index,row){
			 html += "<div class='tracking_main' onclick=\"tracking.showRightContent("+index+",this,'"+row.id+"')\">";
			 html += "<div class='tracking_first_content margin-bottom-0'>";
			 html += "<label class='tracking_label'>";
			 html += "<i class='icon-circle color-blue font-size-9' ></i></label>";
			 html += "<label class='tracking_label'>&nbsp;";
			 html += "<input type='checkbox' style='vertical-align:-3px;' name='checkrow' onclick=\"tracking.checkRow("+index+",this,'"+row.id+"')\"></label>";
			 html += "<label class='margin-left-3 tracking_label'>"+StringUtil.escapeStr(row.xqlb)+"&nbsp;"+StringUtil.escapeStr(row.fjzch)+"</label>";
			 html += "<label class='margin-left-3 tracking_label'>"+StringUtil.escapeStr(row.sqsj).substring(0,10)+"</label>";
			 if(StringUtil.escapeStr(row.jjcd) == 9){
				 html += "<label class='margin-left-3 tracking_label'><i class='fa fa-exclamation-triangle color-blue cursor-pointer' title='紧急'></i></label>";
			 }
			 if(StringUtil.escapeStr(row.isYxfx) == 1){
				 html += "<label class='margin-left-3 tracking_label'><i class='spacing iconnew-forbid color-blue cursor-pointer' title='影响放行'></i></label>";
			 }
			 if(StringUtil.escapeStr(row.isFjhtc) == 1){
				 html += "<label class='margin-left-3 tracking_label'><i class='fa fa-calendar color-blue cursor-pointer' title='计划停场'></i></label>";
			 }
			 if(row.jjcd == 1 && row.isYxfx == 0 && row.isFjhtc == 0){
				 html += "<label class='margin-left-3 tracking_label'></label>";
			 }
			 
			 html += "<label class='margin-left-3 pull-right tracking_label' > "+DicAndEnumUtil.getEnumName('demandStatusEnum',row.zt)+"</label>";
			 html += "<div class='clearfix'></div>	</div>";
			 html += "<div  class='tracking_second_content'>";
			 html += "<label class='tracking_label' title='"+StringUtil.escapeStr(row.xqyy)+"'><a href='javascript:;' onClick=\"tracking.findView('"+row.id+"')\">"+StringUtil.escapeStr(row.bh)+"</a> <a href='javascript:;' onClick=\"tracking.viewSource('"+row.lyid+"','"+row.lylx+"','"+row.bs145+"')\">"+StringUtil.escapeStr(row.lybh)+"</a> "+StringUtil.escapeStr(StringUtil.subString(row.xqyy,10))+"</label>";
			 html += "<label class='pull-right tracking_label' >";
			 
			 if(row.zt != 9){
				 html += "<i class=' icon-edit color-blue cursor-pointer ' onClick=\"tracking.update('"+row.id+"')\" title='修改 Edit'></i>&nbsp;";
			 }
			 if(row.zt == 1){
				 html += "<i class='glyphicon glyphicon-trash color-blue cursor-pointer' onClick=\"tracking.invalid('"+ row.id + "','"+ row.zt +"')\" title='删除 Delete'></i> ";
			 }
			 if(row.zt != 1 && row.zt != 9){
				 html += "<i class='icon-off color-blue cursor-pointer cursor-pointer'  onClick=\"tracking.closebatch('"+ row.id + "','"+ row.zt +"')\" title='关闭 Close'></i> ";
			 }
			 
			 html += "</label> <div class='clearfix'></div> </div> </div>";
			    
		     $("#"+this_.id+"_list").empty();
		     $("#"+this_.id+"_list").html(html);
		     refreshPermission();							//权限初始化
		 });
	},
	/**
	 * 点击勾选 
	 */
	checkRow: function(index,this_,id){
		var this__=this;
		var idnewlist=[];
			var flag = $(this_).is(":checked");
			if(flag){
				$(this_).removeAttr("checked");
				this__.idList.push(id);
			}else{
				$(this_).attr("checked",true);
				for(var i=0;i<this__.idList.length;i++){
					if(this__.idList[i] != id){
						idnewlist.push(this__.idList[i]);
					}
				}
				this__.idList=idnewlist;
			}
			hideBottom(); //重置流程信息
			tracking.ondemandinfo(1,"auto","desc"); //加载需求信息
	},
	/**
	 * 点击行选择 
	 */
	showRightContent: function(index,this_,id){
		var this__=this;
		var $checkbox1 = $("#tracking_list :checkbox[name='checkrow']:eq("+index+")");
		var $checkbox2 = $(".sticky-col :checkbox[name='checkrow']:eq("+index+")");
		var checked = $checkbox1.is(":checked");
		$checkbox1.attr("checked", !checked);
		$checkbox2.attr("checked", !checked);
		
		var idnewlist=[];
		//加载需求信息
		if(checked == false ){
			this__.idList.push(id);
		}else{
			for(var i=0;i<this__.idList.length;i++){
				if(this__.idList[i] != id){
					idnewlist.push(this__.idList[i]);
				}
			}
			this__.idList=idnewlist;
		}
		hideBottom(); //重置流程信息
		tracking.ondemandinfo(1,"auto","desc"); //加载需求信息
		
	},
	/**
	 * 加载需求信息
	 */
	ondemandinfo: function(pageNumber,sortType,sequence){
		
		var this_=this;
		var searchParam={};
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		searchParam.pagination = pagination;
		var paramsMap={};
		if(this_.idList.length > 0){
			paramsMap.idList=this_.idList;
		}
		paramsMap.dprtcode=$("#dprtcode").val();
		searchParam.paramsMap=paramsMap;
		AjaxUtil.ajax({
		   url:basePath+"/material/demand/queryAllDemandInfoList",
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
						sortColumn : "auto",
						orderType : "desc",
						extParams:{},
						goPage: function(a,b,c){
							this_.ondemandinfo(a,b,c);
						}
					});
	 			}else{
		 			$("#"+this_.id+"_info_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_info_list").append("<tr><td class='text-center' colspan=\"19\">暂无数据 No data.</td></tr>");
	 			}
	 			//new Sticky({tableId:this_.id+"_Table"}); //初始化表头浮动
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
				htmlContent += "<tr onclick=\"tracking_log.showHiddenContent('"+row.id+"',this)\">";
				htmlContent += "<td class='fixed-column text-center'>";
				if(row.paramsMap.zt != 1){
					if(row.xqbs == 1 || row.xqbs == 2){
						htmlContent += "<i class=' icon-off color-blue cursor-pointer ' " 
							+ "  onClick=\"tracking.close('"+row.id+"','"+row.xqbs+"')\" title='关闭 Close'></i>&nbsp;&nbsp;";//修改
					}
				}
			    htmlContent += "</td>";
			    htmlContent += "<td class='text-center' >"+DicAndEnumUtil.getEnumName('demandDetailsLogoEnum',row.xqbs)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.bjh)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.bjmc)+"</td>";
			    htmlContent += "<td class='text-right' >"+StringUtil.escapeStr(row.xqsl)+"</td>";
			    var nums="";
			    if(row.paramsMap?row.paramsMap.kcsl:'' != ''&& row.paramsMap?row.paramsMap.djsl:'' != ''){
			    	nums=row.paramsMap?row.paramsMap.kcsl:''-row.paramsMap?row.paramsMap.djsl:'';
			    }else{
			    	nums="0";
			    }
			    htmlContent += "<td class='text-right' >"+nums+"</td>";//
			    htmlContent += "<td class='text-center' >"+StringUtil.escapeStr(bzzt[row.paramsMap?row.paramsMap.bzzt:''])+"</td>";//
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.paramsMap?row.paramsMap.bzbz:'')+"</td>";//
			    htmlContent += "<td class='text-center' >"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.wllb) +"</td>";
			    htmlContent += "<td class='text-center' >"+StringUtil.escapeStr(row.paramsMap.jhsyrq).substring(0,10)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.xingh)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.gg)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.xlh)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.jhly)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.zjh)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.bzjh)+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.thj)+"</td>";
			    var strs1='';
			    if(row.paramsMap?row.paramsMap.tdjxx:'' != ""){
			    	var str =row.paramsMap.tdjxx.split(",");
			    	if(str!=''){
			    		for (var i = 0; i < str.length; i++) {
			    			 str1 =str[i].split("#_#");
			    			 if(str1[0] !=''){
			    				 var js=0;
			    				 if(str1[1]!=''){
			    					 js=parseInt(str1[1]);
			    				 }
			    				 strs1=(parseInt(str1[0])-js)+parseInt(strs1);
			    			 }else{
			    				 strs1="0";
			    			 }
			    		}
			    	}else{
			    		strs1="0";
			    	}
			    }else{
			    	strs1="";
			    }
			    htmlContent += "<td class='text-right' >"+strs1+"</td>";//
			    htmlContent += "<td class='text-left' ><a href='javascript:;' onClick=\"tracking.findView('"+row.paramsMap.demandid+"')\">"+StringUtil.escapeStr(row.paramsMap.bh)+"</a></td>";
			    htmlContent += "</tr>" ;
			    
			    $("#"+this_.id+"_info_list").empty();
			    $("#"+this_.id+"_info_list").html(htmlContent);
			    refreshPermission();							//权限初始化
			    TableUtil.addTitle("#"+this_.id+"_info_list tr td"); //加载td title
		 });
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
		this_.ondemandinfo(currentPage,obj,orderStyle.split("_")[1]);
	},
	/**
	 * 跳转到新增
	 */
	add: function(id){
		window.open(basePath+"/material/demand/apply");
	},
	/**
	 * 查看当前页面
	 */
	findView: function(id){
		window.open(basePath+"/material/demand/find?id="+id);
	},
	/**
	 * 跳转到修改
	 */
	update: function(id){
		window.open(basePath+"/material/demand/apply?id="+id);
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
				url:basePath + "/material/demand/delete",
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
	 * 主表关闭
	 */
	closebatch: function(id,zt){
		var this_=this;
		var obj = {};
		var paramsMap = {};
		paramsMap.currentZt =zt; //当前状态
		obj.paramsMap = paramsMap;
		obj.id=id;
		AlertUtil.showConfirmMessage("确定要关闭吗？", {callback: function(){
			startWait();
			AjaxUtil.ajax({
				url:basePath + "/material/demand/closebatch",
				contentType:"application/json;charset=utf-8",
				type:"post",
				async: false,
				data:JSON.stringify(obj),
				dataType:"json",
				success:function(data){
					finishWait();
					AlertUtil.showMessage("关闭成功!");
					this_.decodePageParam();
					tracking_open_alert.load();
				}
			});
		}});
	},
	/**
	 * 明细表关闭
	 */
	close: function(id,xqbs){
		var this_=this;
		var obj = {};
		var paramsMap = {};
		paramsMap.currentXqbs =xqbs; //当前状态
		obj.paramsMap = paramsMap;
		obj.id=id;
		AlertUtil.showConfirmMessage("确定要关闭吗？", {callback: function(){
			startWait();
			AjaxUtil.ajax({
				url:basePath + "/material/demand/close",
				contentType:"application/json;charset=utf-8",
				type:"post",
				async: false,
				data:JSON.stringify(obj),
				dataType:"json",
				success:function(data){
					finishWait();
					AlertUtil.showMessage("关闭成功!");
					this_.ondemandinfo(1,"auto","desc");
					hideBottom();
				}
			});
		}});
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
		 
		 var SelectArr2 = $("#ztDiv select"); //状态重置为第一个
		 if(SelectArr2[0].options[0] != undefined){
			 SelectArr2[0].options[0].selected = true;
		 }
		 
		 var SelectArr3 = $("#xqlbDiv select"); //需求类别重置为第一个
		 if(SelectArr3[0].options[0] != undefined){
			 SelectArr3[0].options[0].selected = true;
			 
		 }
		 
		 $("input:checkbox[name='isYxfx_search']").attr("checked",true);
		 $("input:checkbox[name='isFjhtc_search']").attr("checked",true);
		 
		 $("#jyXcrq").val("");
		 $("#keyword_search").val("");
		 $("#dprtcode").val(userJgdm);
		 $("#jhsyrq_search").val("");
		 $("#sqsj_search").val("");
		 this_.initdata();			//初始化基础数据
		 TableUtil.resetTableSorting("tracking_table");
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
	 * 查看来源
	 * @param lyid
	 * @param lylx
	 * @param bs145
	 */
	viewSource : function(lyid, lylx, bs145){
		if(lylx == 1){
			if(bs145 == 0){
				window.open(basePath + "/produce/workpackage/view?id=" + lyid);
			}else{
				window.open(basePath + "/produce/workpackage145/view?id=" + lyid);
			}
		}
		
		if(lylx == 2){
			if(bs145 == 0){
				window.open(basePath + "/produce/workorder/woView?gdid=" + lyid);
			}else{
				window.open(basePath + "/produce/workorder145/woView?gdid=" + lyid);
			}
		}
	},
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

function toRed(content){
    var bodyHtml = $("#tracking_list").html();
    var x = bodyHtml.replace(new RegExp(content,"gm"),"<font color='red' >"+content+"</font>")
    $("#tracking_list").html(x);
    console.info(x);
}

