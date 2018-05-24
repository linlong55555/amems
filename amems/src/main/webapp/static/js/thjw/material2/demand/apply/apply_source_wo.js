
/**
 * 需求来源-工单
 */
open_win_demand_source_wo = {
	id:'open_win_demand_source_wo',
	loaded: false,//是否已加载
	data:[],
	param: {
		multi:false, //是否多选 默认单选
		clearProject: false,
		fjzch:null,//飞机注册号
		type:"135",//类型
		selected:null, //已经选择的
		existsIdList:null,//已经选择的集合
		dprtcode:userJgdm,//默认登录人当前机构代码
		callback:function(){}//回调函数
	},
	//显示弹窗 isReload = true 表示强制加载
	show : function(param, isReload){
		if(param){
			$.extend(this.param, param);
		}
		this.initParam();
		this.load();
		var this_ = this;
	},
	//初始化参数
	initParam: function(){
		$(".defaultChecked>input").prop("checked", true);
		$(".defaultNotChecked>input").removeAttr("checked");
		$("#"+this.id+"_keyword_search").val('');
	},
	
	//加载数据
	load : function(pageNumber, sortColumn, orderType){
		if(typeof(pageNumber) == "undefined"){
			pageNumber = 1;
		}
		if(typeof(sortColumn) == "undefined"){
			sortColumn = "auto";
		} 
		if(typeof(orderType) == "undefined"){
			orderType = "desc";
		} 
		
		var obj = this.getParams();
		obj.pagination = {page:pageNumber, sort:sortColumn, order:orderType, rows:10};
		startWait();
		var this_ = this;
		
		var url = basePath+"/produce/workorder/queryAllPageListWin";;
		if(this_.param.type == "145"){
			url = basePath+"/produce/workorder145/queryAllPageListWin";
		}
		
		AjaxUtil.ajax({
			url: url,
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(obj),
			success:function(data){
				SelectUtil.selectNode('selectAllId',this_.id+"_list");
				if(data.total >0){
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
					signByKeyword($("#"+this_.id+"_keyword_search").val(),[2,4], "#"+this_.id+"_list tr td");
				} else {
					$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
				}
				this_.loaded = true;
				finishWait();
		    }
		}); 
	},	
	getParams : function(){
		 var searchParam = {};
		 var paramsMap = {};
		 searchParam.fjzch = this.param.fjzch||"-1";
		 searchParam.dprtcode = this.param.dprtcode;
		 var keyword = $.trim($("#"+this.id+"_keyword_search").val());
		 if('' != keyword){
			 paramsMap.keyword = keyword;
		 }
		 var existsIdList = this.param.existsIdList;
		 if(existsIdList != null && existsIdList != '' && existsIdList.length > 0){
			 paramsMap.idList = existsIdList;
		 }
		 var gdlx = $("[name='source_gdlx']:checked").map(function() {
			 return $(this).val();
		 }).get();
		 if(gdlx.length > 0){
			 paramsMap.gdlx = gdlx;
		 }else{
			 paramsMap.gdlx = [-1];
		 }
		 paramsMap.gdzt = [2,7];
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		var this_ = this;
		$.each(list,function(index,row){
			if (index % 2 == 0) {
				htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='"+this_.id+".rowonclick(event);' >";
			} else {
				htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='"+this_.id+".rowonclick(event);' >";
			}
		   
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"; 
			if(this_.param.multi){
				htmlContent += "<input type='checkbox' name='"+this_.id+"_list_radio' value='"+index+"' onclick=\"SelectUtil.checkRow(this,'selectAllId','"+this_.id+"_list')\" >"; 
			}else{
				var checked = "";
				if(this_.param.selected && this_.param.selected == row.id){
					checked = "checked";
				}
				htmlContent += "<input type='radio' name='"+this_.id+"_list_radio' value='"+index+"' "+checked+" >"; 
			}
			htmlContent += "</td>";
			
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gdbh)+"' style='text-align:left;vertical-align:middle;'><a href='#' onclick='open_win_demand_source_wo.viewWorkOrder(\""+row.id+"\")'>"+StringUtil.escapeStr(row.gdbh)+"</a></td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.lyrwh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.lyrwh)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gdbt)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gdbt)+"</td>";  
			htmlContent += "<td title='"+(row.jhKsrq || "").substr(0, 10)+"' style='text-align:center;vertical-align:middle;'>"+(row.jhKsrq || "").substr(0, 10)+"</td>";  
			htmlContent += "<td title='"+(row.jhJsrq || "").substr(0, 10)+"' style='text-align:center;vertical-align:middle;'>"+(row.jhJsrq || "").substr(0, 10)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.zjh)+" "+StringUtil.escapeStr(row.paramsMap.zjhywms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.zjh)+" "+StringUtil.escapeStr(row.paramsMap.zjhywms)+"</td>";  
			htmlContent += "<td title='"+DicAndEnumUtil.getEnumName('workorderTypeEnum', StringUtil.escapeStr(row.gdlx))+"' style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('workorderTypeEnum', StringUtil.escapeStr(row.gdlx))+"</td>";  
			
		   	htmlContent += "</tr>";
	   });
	   $("#"+this_.id+"_list").html(htmlContent);
	   SelectUtil.selectNode('selectAllId',this_.id+"_list");
	},
	
	//搜索
	search: function(){
		this.load();
	},
	//确认
	rowonclick: function(e){
		if(!this.param.multi){
			$(e.target).parent().find("input[type='radio']").attr("checked",true);
		}else{
			SelectUtil.checkRow($(e.target).parent().find("input[type='checkbox']"),'selectAllId',this.id+"_list");
		}
	},
	// 工单查看
	viewWorkOrder:function(id){
		if(this.param.type == "135"){
			window.open(basePath + "/produce/workorder/woView?gdid=" + id);
		}else{
			window.open(basePath + "/produce/workorder145/woView?gdid=" + id);
		}
	},
};