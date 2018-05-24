/**
 * 库存分布详情
 */
open_win_inventory_distribution_details = {
	id:'open_win_inventory_distribution_details',
	tableDivId : 'open_win_inventory_distribution_details_top_div',
	tableId : 'open_win_inventory_distribution_details_table',
	tbodyId : 'open_win_inventory_distribution_details_list',
	data:[],
	param: {
		bjh : '',
		ckidList : [],
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	show : function(param, isReload){
		$("#"+this.id).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		this.initInfo();
	},
	initInfo : function(){
		this.initMaterial();
		this.load();
	},
	/**
	 * 初始化部件信息
	 */
	initMaterial : function(){
		var this_ = this;
		this_.selectByBjhAndDprcode(this_.param.bjh, this_.param.dprtcode, function(obj){
			obj = obj?obj:{};
			var mc = StringUtil.escapeStr(obj.ywms) + " " + StringUtil.escapeStr(obj.zwms);
			$("#bjh_v", $("#"+this_.id)).val(obj.bjh);
			$("#xh_v", $("#"+this_.id)).val(obj.xingh);
			$("#mc_v", $("#"+this_.id)).val(mc);
			$("#dw_v", $("#"+this_.id)).val(obj.jldw);
		});
	},
	//加载数据
	load : 	function(){
		var this_ = this;
		var obj ={};
		$.extend(obj, this.getParams());
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
			url: basePath+"/aerialmaterial/stock/queryInventoryDetailList",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(obj),
			success:function(data){
				if(data != null && data.length >0){
					this_.appendContentHtml(data);
				} else {
					this_.setNoData();
				}
				finishWait();
		    }
		}); 
	},	
	getParams : function(){
		var searchParam = {};
		var paramsMap = {};
		searchParam.bjh = this.param.bjh;
		searchParam.dprtcode = this.param.dprtcode;
		if(this.param.ckidList.length > 0){
			paramsMap.ckidList = this.param.ckidList;
		}
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		var this_ = this;
		$.each(list,function(index,row){
			
			var ck = StringUtil.escapeStr(row.ckh) + " " + StringUtil.escapeStr(row.ckmc);
			htmlContent += "<tr>";
			
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
			htmlContent += 	  "<span name='orderNumber'>"+(index+1)+"</span>";
			htmlContent += "</td>";
			htmlContent += "<td title='"+ck+"' style='text-align:left;vertical-align:middle;'>"+ck+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.kwh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.kwh)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.kcsl)+"' style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.kcsl)+"</td>";
			
			htmlContent += "</tr>";
		});
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).html(htmlContent);
	},
	/**
	 * 清空数据
	 */
	setNoData : function(){
		var this_ = this;
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).append("<tr><td colspan=\"4\" class='text-center'>暂无数据 No data.</td></tr>");
	},
	close : function(){
		$("#"+this.id).modal("hide");
	},
	/**
	 * 通过部件号、机构代码查询部件信息
	 */
	selectByBjhAndDprcode : function(bjh, dprtcode, obj){
		AjaxUtil.ajax({
			url:basePath+"/material/material/selectByBjhAndDprcode",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify({
				'bjh':bjh,
				'dprtcode':dprtcode
			}),
			success:function(data){
				if(typeof(obj)=="function"){
					obj(data.hCMainData); 
				}
		    }
		}); 
	}
};