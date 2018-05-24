
/**
 * 收货来源信息
 */
open_win_receipt_info_material = {
	id:'open_win_receipt_info_material',
	loaded: false,//是否已加载
	data:[],
	param: {
		multi:false, //是否多选 默认单选
		clearProject: false,
		shlx:null,//收货类型
		lyid:null,//来源id
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
		AjaxUtil.ajax({
			url: basePath+"/material/material/queryWinAllPageList",
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
					signByKeyword($("#"+this_.id+"_keyword_search").val(),[3,4,5], "#"+this_.id+"_list tr td");
				} else {
					$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"10\">暂无数据 No data.</td></tr>");
				}
				this_.loaded = true;
				finishWait();
		    }
		}); 
	},	
	getParams : function(){
		 var searchParam = {};
		 var paramsMap = {};
		 searchParam.dprtcode = this.param.dprtcode;
		 var keyword = $.trim($("#"+this.id+"_keyword_search").val());
		 if('' != keyword){
			 searchParam.keyword = keyword;
		 }
		 var hclx = $.trim($("#open_win_receipt_info_material_hclx").val());
		 if('' != hclx){
			 searchParam.hclx = hclx;
		 }
		 var existsIdList = this.param.existsIdList;
		 if(existsIdList != null && existsIdList != '' && existsIdList.length > 0){
			 paramsMap.idList = existsIdList;
		 }
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
			
			htmlContent += "<td title='"+DicAndEnumUtil.getEnumName('materialTypeEnum', row.hclx)+"' style='text-align:center;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('materialTypeEnum', row.hclx)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.bjh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bjh)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.ywms)+" "+StringUtil.escapeStr(row.zwms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.ywms)+" "+StringUtil.escapeStr(row.zwms)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.cjjh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.cjjh)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.xingh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xingh)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gg)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gg)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.jhly)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.jhly)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.fixChapter?(row.fixChapter.displayName):'')+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.fixChapter?(row.fixChapter.displayName):'')+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.bzjh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bzjh)+"</td>";  
			
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
};