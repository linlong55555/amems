/**
 * 航材工具列表
 */
material_tool_list = {
	id:'material_tool_list',
	tableDivId : 'material_tool_list_top_div',
	tableId : 'material_tool_list_table',
	tbodyId : 'material_tool_list_tbody',
	param: {
		id : '',
		type : 1,
		fjzch : '',
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	init : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.initInfo();
	},
	/**
	 * 初始化信息
	 */
	initInfo : function(){
		this.load();
	},
	//加载数据
	load : function(){
		var this_ = this;
		if(this_.param.dprtcode == null || this_.param.dprtcode == '' || this_.param.id == ''){
			this_.setNoData();
			return;
		}
		var searchParam ={};
		$.extend(searchParam, this_.getParams());
		
		var url = basePath+"/project2/materialtool/queryToolList4Maintenance";
		if(this_.param.type == 1){
			url = basePath+"/project2/materialtool/queryToolList4Maintenance";
		}

		if(this_.param.type == 2){
			url = basePath+"/project2/materialtool/queryToolList4EO";
		}
		if(this_.param.type == 3){
			url = basePath+"/project2/materialtool/queryToolList4WorkOrder";
		}
		startWait();
		AjaxUtil.ajax({
			url : url,
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				finishWait();
				if(data != null && data.length > 0){
					this_.appendContentHtml(data);
				} else {
					this_.setNoData();
				}
	      }
	    }); 
	},	
	getParams : function(){//将搜索条件封装 
		var searchParam = {};
		var paramsMap = {};
		searchParam.dprtcode = this.param.dprtcode;
		paramsMap.fjzch = this.param.fjzch;
		paramsMap.id = this.param.id;
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
	appendContentHtml :function(list){
		var this_ = this;
		var htmlContent = "";			
		$.each(list,function(index,row){
			var paramsMap = row.paramsMap?row.paramsMap:{};
			var kckys = paramsMap.kcsl - paramsMap.djsl;
			var isWarning = (paramsMap.isWarning == 1?"是":"否");
			var warningStyle = (paramsMap.isWarning == 1?"color: red":"");
			
			var hclx = DicAndEnumUtil.getEnumName('materialTypeEnum', paramsMap.hclx);
			if(paramsMap.hclx == 1 && paramsMap.hclxEj != null && paramsMap.hclxEj != '' && DicAndEnumUtil.getEnumName('materialSecondTypeEnum', paramsMap.hclxEj) != ''){
				hclx = DicAndEnumUtil.getEnumName('materialSecondTypeEnum', paramsMap.hclxEj);
			}
			
			htmlContent += "<tr>";
			htmlContent += "<td title='"+hclx+"' style='text-align:left;vertical-align:middle;'>"+hclx+"</td>";
			
			htmlContent += "<td title='"+StringUtil.escapeStr(row.jh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a jh='"+StringUtil.escapeStr(row.jh)+"' href='javascript:void(0);' onclick="+this_.id+".viewMaterial(this)>"+StringUtil.escapeStr(row.jh)+"</a>";
			htmlContent += "</td>";
			
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.xh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.xh)+"</td>";
			htmlContent += "<td title='"+(StringUtil.escapeStr(paramsMap.bjywms)+" "+ StringUtil.escapeStr(paramsMap.bjzwms))+"' style='text-align:left;vertical-align:middle;'>"+(StringUtil.escapeStr(paramsMap.bjywms)+" "+ StringUtil.escapeStr(paramsMap.bjzwms))+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.jhly)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.jhly)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.xqsl)+"' style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.xqsl)+"</td>";
			
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.kcsl)+"' style='text-align:right;vertical-align:middle;'>";
			htmlContent += "<a jh='"+StringUtil.escapeStr(row.jh)+"' style='"+warningStyle+"' href='javascript:void(0);' onclick="+this_.id+".openStorageDetailWin(this)>"+StringUtil.escapeStr(paramsMap.kcsl)+"</a>";
			htmlContent += "</td>";
			
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.jldw) +"</td>";
			
			htmlContent += "<td title='' style='text-align:center;vertical-align:middle;'>"+isWarning+"</td>";
			
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>";
			
			var tdjxx = paramsMap.tdjxx;
			var str = tdjxx==null?"":tdjxx.split(",");
			for (var i = 0; i < str.length; i++) {
				var dataArr = str[i].split("#_#")
				var bjh = dataArr[0];
				var sl = dataArr[1]?dataArr[1]:0;
				var ms = dataArr[3];
				var tdjtTitle = StringUtil.escapeStr(bjh) + " : " + sl + "," + StringUtil.escapeStr(ms);
				var tdj = "<a bjh='"+StringUtil.escapeStr(row.jh)+"' tdjh='"+StringUtil.escapeStr(bjh)+"' href='javascript:void(0);' onclick="+this_.id+".openSubstitutionWin(this)>"+StringUtil.escapeStr(bjh)+"</a>";
				tdj += " : ";
				tdj += "<a jh='"+StringUtil.escapeStr(bjh)+"' href='javascript:void(0);' onclick="+this_.id+".openStorageDetailWin(this)>"+sl+"</a>";
				tdj += " , " + StringUtil.escapeStr(ms);
				if(i==str.length-1){
					htmlContent += "<span title='"+tdjtTitle+"'>"+tdj+"</span>";
				}else{
					htmlContent += "<span title='"+tdjtTitle+"'>"+tdj+"</span>"+"<br>";	
				}								
			}			
			
		    htmlContent += "</td>";
		    
			htmlContent += "</tr>"; 
		});
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).append(htmlContent);
	},
	/**
	 * 清空数据
	 */
	setNoData : function(){
		$("#"+this.tbodyId, $("#"+this.tableDivId)).empty();
		$("#"+this.tbodyId, $("#"+this.tableDivId)).append("<tr><td colspan=\"10\" class='text-center'>暂无数据 No data.</td></tr>");
	},
	/**
	 * 查看替代件信息
	 */
	openSubstitutionWin : function(obj){
		var bjh = $(obj).attr("bjh");
		var tdjh = $(obj).attr("tdjh");
		var dprtcode = this.param.dprtcode;
		open_win_equivalent_substitution.show({
			bjh : bjh,
			tdjh : tdjh,
			isParamId : false,
			dprtcode:dprtcode
		});
	},
	/**
	 * 打开库存分布详情对话框
	 */
	openStorageDetailWin : function(obj){
		var this_ = this;
		var bjh = $(obj).attr("jh");
		var ckidList = [];
		//打开库存分布详情对活框
		open_win_inventory_distribution_details.show({
			bjh : bjh,
			ckidList:ckidList,
			dprtcode:this_.param.dprtcode
		});
	},
	/**
	 * 查看部件信息
	 */
	viewMaterial : function(obj){
		var bjh = $(obj).attr("jh");
		var dprtcode = this.param.dprtcode;
		window.open(basePath+"/material/material/viewByBjhAnddprtcode?bjh="+encodeURIComponent(bjh)+"&dprtcode="+dprtcode);
	}
};