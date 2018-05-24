/**
 * 岗位
 */
post_list_view = {
	id:'post_list_view',
	tbodyId:'post_body',
	rowCount : 0,
	param: {
		djid:null,//关联单据id
		kcbh : null,//课程编号
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	init : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.initInfo();
	},
	initInfo : function(){
		this.rowCount = 0;
		if(this.param.djid){
			this.initDataList();
		}else{
			this.setNoData();
		}
	},
	initDataList : function(){
		var this_ = this;
		var searchParam = {};
		var paramsMap = {};
		searchParam.dprtcode = this_.param.dprtcode;
		paramsMap.kcid = this_.param.djid;
		paramsMap.kcbh = this_.param.kcbh;
		searchParam.paramsMap = paramsMap;
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/training/business/queryByKc",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(searchParam),
			dataType:"json",
			success:function(data){
				if(data != null && data.length > 0){
					this_.appendHtml(data);
				}else{
					this_.setNoData();
				}
			}
		});
	},
	appendHtml : function(list){
		var this_ = this;
		// 先移除暂无数据一行
		$("#"+this_.tbodyId, $("#"+this_.id)).empty();
		$.each(list, function(i, row) {
			this_.rowCount++;
			this_.addRow(row);
		});
	},
	addRow : function(obj){
		var this_ = this;
		var tr = "";
		tr += "<tr>";
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += 	  "<span name='orderNumber'>"+this_.rowCount+"</span>";
		tr += 	  "<input type='hidden' name='hiddenid' value='"+obj.id+"'/>";
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.dgbh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.dgbh)+"</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(obj.dgmc)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(obj.dgmc)+"</td>";
		
		tr += "</tr>";
		$("#"+this_.tbodyId,$("#"+this_.id)).append(tr);
	},
	/**
	 * 设置暂无数据
	 */
	setNoData : function(){
		$("#"+this.tbodyId, $("#"+this.id)).empty();
		$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='3' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
	},
}
