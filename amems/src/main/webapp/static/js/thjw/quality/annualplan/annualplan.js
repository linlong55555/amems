var yjtsJb1 = '';	
var yjtsJb2 = '';
var yjtsJb3 = '';

/**
 *初始化当前js
 */
$(function(){
	annual_plan.load();
});

annual_plan = {
	id : "annual_plan",
	bbh : 0,
	maxbbh : 0,
	zt : 0,
	load: function(){
		var this_=this;
		Navigation(menuCode, '', '', 'SC12-1 ', '林龙', '2017-09-27', '林龙', '2017-10-10');//加载导航栏
		this_.initControl(); 			//初始化控件
	   
	},
	/**
	 * 加载数据
	 */
	init: function(){
		this.loadlatestVersion();		//初始化版本
		this.changeDate();				//初始化版本相关数据
		course_list_table_div.decodePageParam(); 		//年度审核计划
		year_list_table_div.decodePageParam(); 		   //年度视图
		change_record.load();							//变更记录
	},
	/**
	 * 初始化控件
	 */
	initControl: function(){
		var this_=this;
		//默认当前年
		TimeUtil.getCurrentDate(function(date) {
			var d = new Date(date);
			$("#year_search").val(d.getFullYear());
			
			if(todo_nf){
				$("#year_search").val(todo_nf);
				todo_nf = null;
			}
			
			//日期控件
			$("#year_search").datepicker({
		        language: "zh-CN",
		        todayHighlight: true,
		        format: 'yyyy',
		        autoclose: true,
		        startView: 'years',
		        maxViewMode:'years',
		        minViewMode:'years'
			});
			
			this_.loadlatestVersion();		//初始化版本
			this_.changeDate();				//初始化版本相关数据
			course_list_table_div.decodePageParam(); //年度审核计划
			year_list_table_div.decodePageParam(); 		    //年度视图
			change_record.load();							//变更记录
		});
		
	},
	/**
	 * 初始化版本
	 */
	loadlatestVersion: function(){
		
		AjaxUtil.ajax({
			url:basePath + "/quality/annualplan/getLatestVersionList",
			type:"post",
			async: false,
			data:{
				dprtcode : $("#dprtcode").val(),
				nf 		 : $("#year_search").val(),
			},
			dataType:"json",
			success:function(data){
					$("#bbh").empty();
					if(data.length > 0){
						for(var i = 0; i < data.length; i++){
							$("#bbh").append("<option value='"+StringUtil.escapeStr(data[i].bbh)+"'>"+StringUtil.escapeStr(data[i].bbh)+"</option>");
						}
					}else{
						$("#bbh").html("<option value='' >-</option>");
					}
			}
		});
	}
	,
	/**
	 *	修改查询条件时变更年度计划数据
	 */
	changeDate: function(){
		var this_ = this;
		AjaxUtil.ajax({
			url:basePath + "/quality/annualplan/getLatestVersion",
			type:"post",
			async: false,
			data:{
				dprtcode : $("#dprtcode").val(),
				nf 		 : $("#year_search").val(),
				bbh 		 : $("#bbh").val()
			},
			dataType:"json",
			success:function(data){
				var ndjhid = "";
				if(data!=null){
					$("#ndjhsm").text(StringUtil.escapeStr(data.ndjhsm));   												//年度计划说明
					$("#fjnum").text(data.paramsMap?data.paramsMap.fjnum:"0");						//附件数量
					$("#oldzt").val(data.zt);   													//状态
					$("#zt").text(DicAndEnumUtil.getEnumName('annualPlanStatusEnum',data.zt));	    //状态
					$("#id").val(data.id);  														//年度计划id
					this_.bbh = data.bbh;
					this_.maxbbh = data.paramsMap?data.paramsMap.maxbbh:0;
					this_.zt = data.zt;
					ndjhid = data.id;
				}else{
					$("#ndjhsm").text("");   				//年度计划说明
					$("#fjnum").text("");					//附件数量
					$("#zt").text("");	    				//状态
					$("#id").val("");   	
					$("#bbh").val("");   	
					this_.bbh = 0;
					this_.maxbbh = 0;
					this_.zt = 0;
				}
				course_list_table_div.search();
				process_record.load(ndjhid);//流程记录
			}
		});
	}
	,
	/**
	 *	搜索
	 */
	searchRevision: function(){
		this.loadlatestVersion();		//初始化版本
		this.changeDate();				//初始化版本相关数据
		course_list_table_div.decodePageParam(); 		//年度审核计划
		year_list_table_div.decodePageParam(); 		    //年度视图
		change_record.load();							//变更记录
	}
	,
	/**
	 * 切换组织机构
	 */
	onchangeDprtcode: function(){
		this.loadlatestVersion();		//初始化版本
		this.changeDate();				//初始化版本相关数据
		course_list_table_div.decodePageParam(); 		//年度审核计划
		year_list_table_div.decodePageParam(); 		    //年度视图
		change_record.load();							//变更记录
	}
	,
	/**
	 * 搜索条件显示与隐藏 
	 */
	search: function(){
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

/**
 * 搜索条件重置
 */
function searchreset(){
	 $("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	 });
	 $("#divSearch").find("textarea").each(function(){
		 $(this).val("");
	 });
	 $("#divSearch").find("select").each(function(){
			$(this).val("");
	 });
	 $("input:checkbox[name='ztList']").attr("checked",true);
	 $("#keyword_search").val("");
	 $("#dprtcode").val(userJgdm);
}


