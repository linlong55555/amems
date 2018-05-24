
WorkCardUtil = {
	id:'WorkCardUtil',
	tbodyId:'work_card_list',
	colOptionheadClass : "colOptionhead",
	rowCount : 0,
	param: {
		djid:null,//关联单据id
		gkh:null,//关联单据编号
		parentWinId : '',
		jx : '',//机型
		colOptionhead : true,
		changeCss:false,
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	init : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.initInfo();
	},
	/**
	 * 改变机型时调用
	 */
	changeParam : function(param){
		this.rowCount = 0;
		if(param){
			$.extend(this.param, param);
		}
		this.setNoData();
	},
	initInfo : function(){
		this.rowCount = 0;
		if(this.param.changeCss){
			$("#work_card_div", $("#"+this.id)).removeClass("padding-right-8").addClass("padding-right-0");
		}
		this.initViewOrHide();
		if(this.param.djid){
			this.initDataList();
		}else{
			this.setNoData();
		}
	},
	initViewOrHide : function(){
		//显示或隐藏操作列
		if(this.param.colOptionhead){
			$("."+this.colOptionheadClass, $("#"+this.id)).show();
		}else{
			$("."+this.colOptionheadClass, $("#"+this.id)).hide();
		}
	},
	initDataList : function(){
		var this_ = this;
		var searchParam = {};
		var paramsMap = {};
		searchParam.mainid = this.param.djid;
		paramsMap.dprtcode = this.param.dprtcode;
		searchParam.paramsMap = paramsMap;
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/project2/workcard2related/queryAllList",
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
			if(null != row.workCard){
				row.gzbt = row.workCard.gzbt;
				row.fixChapter = row.workCard.fixChapter;
				row.wxxmbh = row.workCard.wxxmbh;
				row.gzlx = row.workCard.gzlx;
				row.zy = row.workCard.zy;
				row.jhgsRs = row.workCard.jhgsRs;
				row.jhgsXss = row.workCard.jhgsXss;
			}
			this_.addRow(row);
		});
	},
	openWinAdd : function(){
		var this_ = this;
		var dprtcode = this_.param.dprtcode;
		var existsGkhList = [];
		if(this_.param.gkh){
			existsGkhList.push(this_.param.gkh);
		}
		var len = $("#"+this_.tbodyId, $("#"+this_.id)).length;
		if (len == 1) {
			if($("#"+this_.tbodyId,$("#"+this_.id)).find("td").length != 1){
				$("#"+this_.tbodyId,$("#"+this_.id)).find("tr").each(function(){
					var index = $(this).index(); //当前行
					var gkh =$.trim($(this).find("input[name='gkh']").val());
					existsGkhList.push(gkh);
				});
			}
		}
		open_win_work_card.show({
			multi : true,
			parentWinId : this_.param.parentWinId,
			jx : this_.param.jx,//机型
			existsGkhList:existsGkhList,//已经选择的集合,工卡号
			dprtcode:dprtcode,
			callback: function(data){//回调函数
				if(data != null && data.length > 0){
					//先移除暂无数据一行
					var len = $("#"+this_.tbodyId, $("#"+this_.id)).length;
					if (len == 1) {
						if($("#"+this_.tbodyId,$("#"+this_.id)).find("td").length == 1){
							$("#"+this_.tbodyId, $("#"+this_.id)).empty();
						}
					}
					$.each(data,function(index,row){
						this_.rowCount++;
						row.id = '';
						this_.addRow(row);
					});
				}
			}
		},true)
	},
	addRow : function(row){
		var this_ = this;
		var tr = "";
		tr += "<tr>";
		
		if(this_.param.colOptionhead){
			tr += "<td style='text-align:center;vertical-align:middle;'>";
			tr += "<button type='button' class='line6 line6-btn' onclick='"+this_.id+".removeRow(this)'>";
		    tr += "    <i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i>";
		    tr += "</button>";
			tr += "</td>";
		}
		
		tr += "<td style='text-align:center;vertical-align:middle;'>";
		tr += 	  "<span name='orderNumber'>"+this_.rowCount+"</span>";
		tr += 	  "<input type='hidden' name='hiddenid' value='"+row.id+"'/>";
		tr += 	  "<input type='hidden' name='gkh' value='"+row.gkh+"'/>";
		tr += "</td>";
		
		tr += "<td title='"+StringUtil.escapeStr(row.gkh)+"' style='text-align:left;vertical-align:middle;'>";
		tr += StringUtil.escapeStr(row.gkh);
		tr += "</td>";
		tr += "<td title='"+StringUtil.escapeStr(row.gzbt)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gzbt)+"</td>";
		tr += "<td title='"+StringUtil.escapeStr(row.wxxmbh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.wxxmbh)+"</td>";
	
	    var zjh = '';
	    if(row.fixChapter != null){
		   zjh = StringUtil.escapeStr(row.fixChapter.displayName);
	    }
	    tr += "<td title='"+zjh+"' style='text-align:left;vertical-align:middle;'>"+zjh+"</td>";
	    tr += "<td title='"+StringUtil.escapeStr(row.gzlx)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gzlx)+"</td>";
   
	    var bzgs = '';
	    if(row.jhgsRs != null && row.jhgsRs != "" && row.jhgsXss != null && row.jhgsXss != ""){
		    bzgs = row.jhgsRs+"人x"+row.jhgsXss+"时="+(row.jhgsRs*row.jhgsXss).toFixed(2)+"时";
	    }
	    tr += "<td title='"+bzgs+"' style='text-align:left;vertical-align:middle;'>"+bzgs+"</td>";  
	    tr += "<td title='"+StringUtil.escapeStr(row.zy)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.zy)+"</td>";
		   
		tr += "</tr>";
		$("#"+this_.tbodyId,$("#"+this_.id)).append(tr);
	},
	/**
	 * 删除一行
	 */
	removeRow : function(obj){
		var this_ = this;
		var id = $(obj).parent().parent().find("input[name='hiddenid']").val();
		if ('' != id && null != id) {
			
			AlertUtil.showConfirmMessage("确定删除此行吗？", {callback: function(){
				
				$(obj).parent().parent().remove();
				this_.resRowCount();
				this_.rowCount--;
			}});
			
		} else {
			$(obj).parent().parent().remove();
			this_.resRowCount();
			this_.rowCount--;
		}
	},
	/**
	 * 重置序号
	 */
	resRowCount : function(){
		var this_ = this;
		var len = $("#"+this_.tbodyId,$("#"+this_.id)).find("tr").length;
		if (len >= 1){
			$("#"+this_.tbodyId,$("#"+this_.id)).find("tr").each(function(index){
				$(this).find("span[name='orderNumber']").html(index+1);
			});
		}else{
			this_.setNoData();
		}
	},
	/**
	 * 设置暂无数据
	 */
	setNoData : function(){
		$("#"+this.tbodyId, $("#"+this.id)).empty();
		if(this.param.colOptionhead){
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='9' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}else{
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='8' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}
	},
	/**
	 * 获取工卡数据
	 */
	getData : function(){
		var this_ = this;
		var param = [];
		var len = $("#"+this.tbodyId,$("#"+this.id)).find("tr").length;
		if (len == 1) {
			if($("#"+this.tbodyId,$("#"+this.id)).find("td").length ==1){
				return param;
			}
		}
		if (len > 0){
			$("#"+this.tbodyId,$("#"+this.id)).find("tr").each(function(){
				var infos ={};
				var hiddenid = $(this).find("input[name='hiddenid']").val(); //当前行，隐藏id的值
				var gkh = $(this).find("input[name='gkh']").val(); //当前行，隐藏id的值
				infos.id = hiddenid;
				infos.gkh = gkh;
				infos.jx = this_.param.jx;
				param.push(infos);
			});
		}
		return param;
	}
}
