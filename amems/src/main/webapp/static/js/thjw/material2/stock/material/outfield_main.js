/**
 * 维修项目初始化
 */
var outfield_main = {
	id:'outfield_main',
	tableDivId : 'outfield_main_top_div',
	tableId : 'outfield_main_table',
	tbodyId : 'outfield_main_tbody',
	paginationId:'outfield_main_Pagination',
	operationId : '',
	pagination : {},
	currentDate : '',
	param: {
		fjzch : '',
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	init : function(param){
		hclxSelect = '<option value="" selected="true">显示全部All</option>';
		if($("#hclxType").val() == 1){
			hclxSelect += '<option value="1" >航材</option>';
			hclxSelect += '<option value="4" >化工品</option>';
			hclxSelect += '<option value="5" >低值易耗品</option>';
			hclxSelect += '<option value="6" >松散件</option>';
			hclxSelect += '<option value="0" >其他</option>';
		}else{
			hclxSelect += '<option value="21" >工具</option>';
			hclxSelect += '<option value="22" >设备</option>';
		}
		$("#wllb_search").empty();
		$("#wllb_search").append(hclxSelect);
		$("#qczt_search").html('<option value="">显示全部All</option>');
		DicAndEnumUtil.registerDic("86", "qczt_search", $("#dprtcode_search").val());
		if(param){
			$.extend(this.param, param);
		}
		//重置排序图标
		TableUtil.resetTableSorting(this.tableDivId);
		this.load(1,"auto","desc");
	},
	search: function(){
		this.load(1,"auto","desc");
	},
	/**
	 * 加载数据
	 */
	load : function(pageNumber, sortColumn, orderType){
		var this_ = this;
		this_.mainData = [];
		if(typeof(pageNumber) == "undefined"){
			pageNumber = 1;
		}
		if(typeof(sortColumn) == "undefined"){
			sortColumn = "auto";
		} 
		if(typeof(orderType) == "undefined"){
			orderType = "desc";
		} 
		
		var searchParam ={};
		this_.pagination = {page:pageNumber,sort:sortColumn,order:orderType,rows:20};
		searchParam.pagination = this_.pagination;
		$.extend(searchParam, this_.getParams());
		startWait();
		AjaxUtil.ajax({
			url:basePath+"/material/stock/material/queryAll",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				this_.planUsageList = [];
				finishWait();
				if(data.total >0){
					this_.mainData = data.rows;
					this_.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : this_.paginationId,
						data: data,
						sortColumn : sortColumn,
						orderType : orderType,
						controller: this_
					}); 
					// 标记关键字
					signByKeyword($("#keyword_search", $("#"+this_.id)).val(),[3,4,5,6,12,13,14,15,18],"#"+this_.tbodyId+" tr td");
				} else {
					this_.setNoData();
				}
				new Sticky({tableId:this_.tableId});
	      }
	    }); 
			
	},	
	/**
	 * 将搜索条件封装 
	 */
	getParams : function(){
		var this_ = this;
		var searchParam = {};
		var paramsMap = {};
		var keyword = $.trim($("#keyword_search" ,$("#"+this_.id)).val());
		var cqid = $.trim($("#cqid_search" ,$("#"+this_.id)).val());
		var wlly = $.trim($("#wlly_search" ,$("#"+this_.id)).val());
		var bj = $.trim($("#bj_search" ,$("#"+this_.id)).val());
		var pch = $.trim($("#pch_search" ,$("#"+this_.id)).val());
		var xlh = $.trim($("#xlh_search" ,$("#"+this_.id)).val());
		var gg = $.trim($("#gg__search" ,$("#"+this_.id)).val());
		var wllb = $.trim($("#wllb_search" ,$("#"+this_.id)).val());
		var gljb = $.trim($("#gljb_search" ,$("#"+this_.id)).val());
		var cjjh = $.trim($("#cjjh_search" ,$("#"+this_.id)).val());
		var zzcs = $.trim($("#zzcs_search" ,$("#"+this_.id)).val());
		var qczt = $.trim($("#qczt_search" ,$("#"+this_.id)).val());
		var dprtcode = $.trim($("#dprtcode_search",$("#"+this_.id)).val());
		var htbhCg = $.trim($("#htbh_search",$("#"+this_.id)).val());
		if(wllb == "" || wllb == null){
			if($("#hclxType").val() ==1){
				paramsMap.hclx = [0,1,4,5,6];
			}else{
				paramsMap.hclx = [2,3];
			}
		}else{
			if(wllb == 21 || wllb == 22){
				paramsMap.hclx = [2,3];
				paramsMap.hclx_ej = wllb;
			}else{
				paramsMap.hclx = [wllb];
			}
		}
		if($("#isTool").val()=='true'){
			paramsMap.isTool=true
		}else if($("#isTool").val()=='false'){
			paramsMap.isTool=false
		} 
		searchParam.dprtcode = dprtcode;
		paramsMap.keyword = keyword;
		searchParam.cqid = cqid;
		searchParam.qczt = qczt;
		searchParam.htbhCg = htbhCg;
		paramsMap.wlly = wlly;
		paramsMap.bj = bj;
		paramsMap.pch = pch;
		paramsMap.xlh = xlh;
		paramsMap.gg = gg;
		paramsMap.wllb = wllb;
		paramsMap.gljb = gljb;
		paramsMap.cjjh = cjjh;
		paramsMap.zzcs = zzcs;
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
	/**
	 * 拼接列表
	 */
	appendContentHtml: function(list){
		var this_ = this;
		var htmlContent = '';
		$.each(list,function(index,row){
		   
			var paramsMap = row.paramsMap?row.paramsMap:{};
			htmlContent = htmlContent + "<tr>";
			htmlContent = htmlContent + "<td class='fixed-column text-center'><a href=\"javascript:viewBulletin('"
			+row.ckh + "','" + row.id + "')\">" + (index+1)+ "</a></td>";
			htmlContent += "<td class='text-center' title='"+StringUtil.escapeStr(row.qczt)+"'>"+StringUtil.escapeStr(row.qczt)+"</td>";
			htmlContent += "<td class='fixed-column text-left' title='"+StringUtil.escapeStr(row.bjh)+"'><a href=\"javascript:viewBj('"
			+row.bjid + "')\">" +StringUtil.escapeStr(row.bjh)+"</a></td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.ywms)+" "+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.ywms)+" "+StringUtil.escapeStr(row.zwms)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.pch)+"'>"+StringUtil.escapeStr(row.pch)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.sn)+"'>"+StringUtil.escapeStr(row.sn)+"</td>";
			if(row.sjly == 1){
				htmlContent += "<td class='text-left' title='库房'>库房</td>";
			}else if(row.sjly == 2){
				htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.fjzch)+"'>"+StringUtil.escapeStr(row.fjzch)+"</td>";
			}else{
				htmlContent += "<td class='text-left'></td>";
			}
			if(row.djsl && row.djsl > 0 ){
				htmlContent += "<td class='text-right' title='"+(row.kcsl-row.djsl)+"/"+StringUtil.escapeStr(row.kcsl)+" "+StringUtil.escapeStr(row.jldw)+"'><a href='javascript:void(0);' onclick=\"viewhis('"+row.id + "')\">"+(row.kcsl-ow.djsl)+"/"+StringUtil.escapeStr(row.kcsl)+" "+StringUtil.escapeStr(row.jldw)+"</a></td>";
			}else{
				htmlContent += "<td class='text-right' title='"+StringUtil.escapeStr(row.kcsl)+" "+StringUtil.escapeStr(row.jldw)+"'>"+StringUtil.escapeStr(row.kcsl)+" "+StringUtil.escapeStr(row.jldw)+"</td>";
			}
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.kccb)+" "+StringUtil.escapeStr(row.biz)+"'>"+StringUtil.escapeStr(row.kccb)+" "+StringUtil.escapeStr(row.biz)+"</td>";
//			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.jz)+" "+StringUtil.escapeStr(row.jzbz)+"'>"+StringUtil.escapeStr(row.jz)+" "+StringUtil.escapeStr(row.jzbz)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(paramsMap.cqbh) +" "+ StringUtil.escapeStr(paramsMap.cfjzch) +" "+ StringUtil.escapeStr(paramsMap.gsmc)+"'>"+StringUtil.escapeStr(paramsMap.cqbh)+"</td>";
			if($("#isTool").val()=='false'){//如果是航材库外查询
				htmlContent = htmlContent + "<td class='text-center' title='" + DicAndEnumUtil.getEnumName("materialTypeEnum",paramsMap.hclx)+ "'>"+ DicAndEnumUtil.getEnumName("materialTypeEnum",paramsMap.hclx) + "</td>";
			}else{//是工具库外查询
				htmlContent = htmlContent + "<td class='text-center' title='" + DicAndEnumUtil.getEnumName("materialToolSecondTypeEnum",paramsMap.hclx_ej)+ "'>"+ DicAndEnumUtil.getEnumName("materialToolSecondTypeEnum",paramsMap.hclx_ej) + "</td>";

			}
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(paramsMap.cjjh)+"'>"+StringUtil.escapeStr(paramsMap.cjjh)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.xh)+"'>"+StringUtil.escapeStr(row.xh)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.gg)+"'>"+StringUtil.escapeStr(row.gg)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.grn)+"'>"+StringUtil.escapeStr(row.grn)+"</td>";
			if(row.cghtid){
				htmlContent += "<td class='text-center' title='" + StringUtil.escapeStr(row.htbhCg) + "'><a href='javascript:void(0);' onclick=\"viewContract('"+row.cghtid + "')\">"+ StringUtil.escapeStr(row.htbhCg) + "</a></td>";
			}else{
				htmlContent += "<td class='text-center' title='" + StringUtil.escapeStr(row.htbhCg) + "'>"+ StringUtil.escapeStr(row.htbhCg) + "</td>";
			}
			htmlContent += "<td class='text-left' title='"+DicAndEnumUtil.getEnumName('supervisoryLevelEnum',paramsMap.gljb)+"'>"+DicAndEnumUtil.getEnumName('supervisoryLevelEnum',paramsMap.gljb)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.zzcs)+"'>"+StringUtil.escapeStr(row.zzcs)+"</td>";
			htmlContent += "</tr>"; 
		    
		});
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).html(htmlContent);
		refreshPermission();
	},
	/**
	 * 清空数据
	 */
	setNoData : function(){
		var this_ = this;
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).empty();
		$("#"+this_.paginationId, $("#"+this_.id)).empty();
		$("#"+this_.tbodyId, $("#"+this_.tableDivId)).append("<tr><td colspan=\"18\" class='text-center'>暂无数据 No data.</td></tr>");
	},
	/**
	 * 查看维修历史记录
	 */
	viewHistory : function(id, dprtcode){
		window.open(basePath+"/produce/maintenance/initialization/project/history?id=" + id + "&fjzch=" + this.param.fjzch + "&dprtcode="+ dprtcode);
	},
	/**
	 * 排序
	 */
	orderBy : function(sortColumn, prefix){//字段排序
		var this_ = this;
		var $obj = $("th[name='column_"+sortColumn+"']", $("#"+this_.tableDivId));
		var orderStyle = $obj.attr("class");
		$(".sorting_desc", $("#"+this_.tableDivId)).removeClass("sorting_desc").addClass("sorting");
		$(".sorting_asc", $("#"+this_.tableDivId)).removeClass("sorting_asc").addClass("sorting");
		var orderType = "asc";
		if (orderStyle.indexOf ("sorting_asc")>=0) {
			$obj.removeClass("sorting").addClass("sorting_desc");
			orderType = "desc";
		} else {
			$obj.removeClass("sorting").addClass("sorting_asc");
			orderType = "asc";
		}
		var currentPage = $("#"+this_.paginationId+" li[class='active']", $("#"+this_.id)).text();
		if(currentPage == ""){currentPage = 1;}
		/*if(prefix != null && prefix != '' && typeof prefix != undefined){
			sortColumn = prefix+"."+sortColumn;
		}*/
		this_.load(currentPage, sortColumn, orderType);
	},
	/**
	 * 刷新页面
	 */
	refreshPage : function(){//刷新页面
		$("#"+this.tableDivId).prop('scrollTop','0');
		//重置排序图标
		TableUtil.resetTableSorting(this.tableDivId);
		this.load(this.pagination.page, this.pagination.sort, this.pagination.order);
	},
	/**
	 * 搜索
	 */
	search : function(){
		$("#"+this.tableDivId).prop('scrollTop','0');
		//重置排序图标
		TableUtil.resetTableSorting(this.tableDivId);
		this.load(1,"auto","desc");
	},
	openList : function(){
		var this_ = this;
		var dprtcode = $.trim($("#dprtcode_search", $("#"+this_.id)).val());
		var cqId = $("#cqid_search", $("#"+this_.id)).val();
		cqModal.show({
			selected:cqId,//原值，在弹窗中默认勾选
			dprtcode:dprtcode,
			callback: function(data){//回调函数
				var cqText = '';
				var cqid = '';
				if(data != null ){
					cqText = data.cqbh;
					cqid = data.id;
				}
				$("#cqText_search", $("#"+this_.id)).val(cqText);
				$("#cqid_search", $("#"+this_.id)).val(cqid);
			}
		});
	},
	moreSearch : function(){
		var this_ = this;
		if ($("#divSearch", $("#"+this_.id)).css("display") == "none") {
			$("#divSearch", $("#"+this_.id)).css("display", "block");
			App.resizeHeight();
			$("#icon", $("#"+this_.id)).removeClass("icon-caret-down");
			$("#icon", $("#"+this_.id)).addClass("icon-caret-up");
		} else {
			$("#divSearch", $("#"+this_.id)).css("display", "none");
			App.resizeHeight();
			$("#icon", $("#"+this_.id)).removeClass("icon-caret-up");
			$("#icon", $("#"+this_.id)).addClass("icon-caret-down");
		}
	},
	//搜索条件重置
	searchreset : function(){
		var this_ = this;
		 $("#divSearch", $("#"+this_.id)).find("input").each(function() {
			$(this).attr("value", "");
		});
		
		 $("#divSearch", $("#"+this_.id)).find("textarea").each(function(){
			 $(this).val("");
		 });
		 $("#divSearch", $("#"+this_.id)).find("select").each(function(){
				$(this).val("");
			});
		 $("#keyword_search", $("#"+this_.id)).val("");
		 $("#dprtcode_search", $("#"+this_.id)).val(userJgdm);
		 $("#wlly_search", $("#"+this_.id)).val("");
		 $("#cqText_search", $("#"+this_.id)).val("");
		 $("#cqid_search", $("#"+this_.id)).val("");
		 $("#qczt_search", $("#"+this_.id)).val("");
		 $("#htbh_search", $("#"+this_.id)).val("");
	},
	/**
	 * 导出
	 */
	exportExcel : function(){
		var param = this.getParams();
		param.pagination = {page:1,sort:"auto",order:"desc",rows:100000};
		//param.keyword = encodeURIComponent(param.keyword);
		window.open(basePath+"/material/stock/material/outfield.xls?paramjson="+JSON.stringify(param));
	},
	//搜索条件重置
	 searchreset :function (){
		 var this_ = this;
		 $("#divSearch", $("#"+this_.id)).find("input").each(function() {
			$(this).attr("value", "");
		});
		
		 $("#divSearch", $("#"+this_.id)).find("textarea").each(function(){
			 $(this).val("");
		 });
		 $("#divSearch", $("#"+this_.id)).find("select").each(function(){
				$(this).val("");
			});
		 $("#keyword_search", $("#"+this_.id)).val("");
		 $("#cqText_search", $("#"+this_.id)).val("");
		 $("#cqid_search", $("#"+this_.id)).val("");
		 $("#wlly_search", $("#"+this_.id)).val("");
		 $("#qczt_search", $("#"+this_.id)).val("");
		 $("#dprtcode_search", $("#"+this_.id)).val(userJgdm);
	}
}	

function viewBulletin(ckh,id){
	var permissioncode = 'material:stock:material:outside:save';  //进入编辑页面权限按钮
	if (checkBtnPermissions(permissioncode)) {// 如果有编辑权限,进入编辑页面
		inside_Modal.show({
			id : id,
			dprtcode : $("#dprtcode_search").val(),
			callback : function(data) {
			AlertUtil.showMessage('保存成功!');
			
			}
		});
		
		/*$("input[id$='view'],select[id$='view'],textarea[id$='view']")
		.not("#kccb_view,#hcly_view,#biz_view,#cqbh_view,#hjsm_view,#scrq_view,#grn_view,#tsn_view,#tso_view,#csn_view,#cso_view,#cfyq_view,#bz_view")
		.attr("disabled", "disabled");
		$("#cqbutton").removeAttr("disabled");
		inside_Modal.show({
			id : id,
			dprtcode : $("#dprtcode_search").val(),
			callback : function(data) {
				if (data)
			AlertUtil.showMessage('保存成功!');
				search();
				refreshPermission();
			}
		});*/
	} else {// 查看界面A
		 window.open(basePath +"/material/stock/material/kc_view?id="+id);
	}
}
function viewBj(id){
	window.open(basePath +"/material/material/view?id="+id);
}
function viewContract(htid){
	window.open(basePath+"/material/contract/view/"+htid);	
}
function viewhis(kcid){
	//查看冻结履历
	frozenHistoryModal.show({
	        id:kcid       //库存id	
	})
}