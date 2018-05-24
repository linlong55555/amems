/**
 * 航材工具需求清单列表
 */
material_tool_detail_list = {
	id:'material_tool_detail_list',
	tableDivId_material : 'material_detail_list_top_div',
	tableId_material : 'material_detail_list_table',
	thead_material : 'material_detail_list_thead',
	tbodyId_material : 'material_detail_list_tbody',
	tableDivId_tool : 'tool_detail_list_top_div',
	tableId_tool : 'tool_detail_list_table',
	thead_tool : 'tool_detail_list_thead',
	tbodyId_tool : 'tool_detail_list_tbody',
	type : 1,//1航材,2工具
	materialList : [],//航材
	toolList : [],//工具
	param: {
		idList : [],
		type : 1,//1表示已选中的监控数据,2表示135工包,3表示135工单,4表示145工包
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
		this.setThead(this.thead_material, this.tableDivId_material);
		this.setThead(this.thead_tool, this.tableDivId_tool);
		this.multiselect();
		this.load();
	},
	/**
	 * 初始化多选下拉框
	 */
	multiselect : function(){
		var this_ = this;
		var storeHtml = "";
		$.each(userStoreList, function(index, row){
			if(this_.param.dprtcode == row.dprtcode){
				storeHtml += "<option value=\""+row.id+"\">"+StringUtil.escapeStr(row.ckmc)+"</option>"
			}
		});
		$("#ck_search",$("#"+this_.id)).html(storeHtml);
		//生成多选下拉框区域
		$("#ck_search", $("#"+this_.id)).multiselect({
			buttonClass: 'btn btn-default',
			buttonWidth:'100%' ,
			buttonHeight: '34px',
			buttonContainer: '<div class="btn-group" style="min-width:100%;" />',
			numberDisplayed:100,
			includeSelectAllOption: true,
			nonSelectedText:'显示全部 All',
		    allSelectedText:'显示全部 All',
			selectAllText: '选择全部 Select All',
			onChange : function(element, checked) {
			}
		});
	},
	//加载数据
	load : function(){
		var this_ = this;
		var searchParam ={};
		$.extend(searchParam, this_.getParams());
		if(this_.param.idList.length == 0){
			this_.setNoData(this_.tbodyId_material, this_.tableDivId_material);
			this_.setNoData(this_.tbodyId_tool, this_.tableDivId_tool);
			return;
		}
		var url = basePath+"/project2/materialtool/queryAllToolList";
		if(this_.param.type == 1){
			url = basePath+"/project2/materialtool/queryAllToolList";
		}
//		url = basePath+"/project2/materialtool/queryToolList4Maintenance";

		if(this_.param.type == 2 || this_.param.type == 12){
			url = basePath+"/project2/materialtool/queryToolList4Package";
//			url = basePath+"/project2/materialtool/queryToolList4EO";
		}
		if(this_.param.type == 3){
			url = basePath+"/project2/materialtool/queryToolList4WorkOrder";
		}
		if(this_.param.type == 4){
			url = basePath+"/project2/materialtool/queryToolList4WP145";
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
				this_.materialList = [];
				this_.toolList = [];
				if(data != null && data.length > 0){
					this_.initMaterialAndToolList(data);
					this_.appendContentHtml(this_.materialList, this_.tbodyId_material, this_.tableDivId_material);
					this_.appendContentHtml(this_.toolList, this_.tbodyId_tool, this_.tableDivId_tool);
				} else {
					this_.setNoData(this_.tbodyId_material, this_.tableDivId_material);
					this_.setNoData(this_.tbodyId_tool, this_.tableDivId_tool);
				}
	      }
	    }); 
	},	
	getParams : function(){//将搜索条件封装 
		var searchParam = {};
		var paramsMap = {};
		var keyword = $("#keyword_search", $("#"+this.id)).val();
		var isWarning = $("#isWarning_search", $("#"+this.id)).val();
		var ck = $.trim($("#ck_search", $("#"+this.id)).val());
		searchParam.dprtcode = this.param.dprtcode;
		paramsMap.keyword = keyword;
		paramsMap.fjzch = this.param.fjzch;
		paramsMap.idList = this.param.idList;
		if(isWarning != null && isWarning != ''){
			paramsMap.isWarning = isWarning;
		}
		if(ck != null && ck.length > 0){
			var ckidArr = ck.split(",");
			var ckidList = [];
			for(var i = 0 ; i < ckidArr.length ; i++){
				if('multiselect-all' != ckidArr[i]){
					ckidList.push(ckidArr[i]);
				}
			}
			if(ckidList.length > 0){
				paramsMap.ckidList = ckidList;
			}
		}
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
	/**
	 * 初始化航材工具设备集合
	 */
	initMaterialAndToolList : function(data){
		var this_ = this;
		$.each(data,function(index,row){
			if(row.bjlx == 1 || row.bjlx == 4){
				this_.materialList.push(row);
			}
			if(row.bjlx == 2 || row.bjlx == 3){
				this_.toolList.push(row);
			}
		});
	},
	/**
	 * 拼接列表
	 */
	appendContentHtml: function(list, tbodyId, tableDivId){
		var this_ = this;
		var htmlContent = '';
		$("#"+tbodyId, $("#"+tableDivId)).empty();
		$.each(list,function(index,row){
			var paramsMap = row.paramsMap?row.paramsMap:{};
			
			var hclx = DicAndEnumUtil.getEnumName('materialTypeEnum', paramsMap.hclx);
			if(paramsMap.hclx == 1 && paramsMap.hclxEj != null && paramsMap.hclxEj != '' && DicAndEnumUtil.getEnumName('materialSecondTypeEnum', paramsMap.hclxEj) != ''){
				hclx = DicAndEnumUtil.getEnumName('materialSecondTypeEnum', paramsMap.hclxEj);
			}
			
			var isWarning = (paramsMap.isWarning == 1?"是":"否");
			var warningStyle = (paramsMap.isWarning == 1?"color: red":"");
			
			var kysl = paramsMap.kcsl - paramsMap.djsl;
			
			htmlContent += "<tr onclick=SelectUtil.clickRow("+index+",'"+tbodyId+"','"+tbodyId+"_row')>";
			
			htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='checkbox' name='"+tbodyId+"_row' index="+index+"  onclick=SelectUtil.selectCheckbox(event,"+index+",'"+tbodyId+"','"+tbodyId+"_row',this) /></td>";
			
			htmlContent += "<td title='"+hclx+"' style='text-align:center;vertical-align:middle;'>"+hclx+"</td>";
			
			htmlContent += "<td title='"+StringUtil.escapeStr(row.jh)+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += "<a jh='"+StringUtil.escapeStr(row.jh)+"' href='javascript:void(0);' onclick="+this_.id+".viewMaterial(this)>"+StringUtil.escapeStr(row.jh)+"</a>";
			htmlContent += "</td>";
			
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.xh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.xh)+"</td>";
			htmlContent += "<td title='"+(StringUtil.escapeStr(paramsMap.bjywms)+" "+ StringUtil.escapeStr(paramsMap.bjzwms))+"' style='text-align:left;vertical-align:middle;'>"+(StringUtil.escapeStr(paramsMap.bjywms)+" "+ StringUtil.escapeStr(paramsMap.bjzwms))+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.jhly)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.jhly)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.xqsl)+"' name='xqsl' style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.xqsl)+"</td>";
			
			htmlContent += "<td jh='"+StringUtil.escapeStr(row.jh)+"' title='"+StringUtil.escapeStr(paramsMap.kcsl)+"' onclick="+this_.id+".openStorageDetailWin(this) style='text-align:right;vertical-align:middle;cursor:pointer;'>";
			htmlContent += "<a jh='"+StringUtil.escapeStr(row.jh)+"' style='"+warningStyle+"' href='javascript:void(0);' onclick="+this_.id+".openStorageDetailWin(this)>"+StringUtil.escapeStr(paramsMap.kcsl)+"</a>";
			htmlContent += "</td>";
			
			
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(paramsMap.jldw) +"</td>";
			htmlContent += "<td title='' style='text-align:center;vertical-align:middle;'>"+isWarning+"</td>";
			
			htmlContent += "<td style='text-align:left;vertical-align:middle;' name='thj'>";
			
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
		    
//		    htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bz)+"</td>";	   
			
			htmlContent += "<td title='' style='text-align:center;vertical-align:middle;'>";
			htmlContent += "<a jh='"+StringUtil.escapeStr(row.jh)+"' href='javascript:void(0);' onclick="+this_.id+".viewRwxx(this)>查看详情</a>";
			htmlContent += "</td>";
			
			htmlContent += "<input type='hidden' name='bjid' value='"+StringUtil.escapeStr(row.bjid)+"'>"; 
			htmlContent += "</tr>"; 
		});
		if(htmlContent != ''){
			$("#"+tbodyId, $("#"+tableDivId)).html(htmlContent);
			// 标记关键字
			signByKeyword($("#keyword_search", $("#"+this_.id)).val(),[3,4,5,6],"#"+tbodyId+" tr td");
		}else{
			this_.setNoData(tbodyId, tableDivId);
		}
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
		var ck = $.trim($("#ck_search", $("#"+this.id)).val());
		if(ck != null && ck.length > 0){
			var ckidArr = ck.split(",");
			for(var i = 0 ; i < ckidArr.length ; i++){
				if('multiselect-all' != ckidArr[i]){
					ckidList.push(ckidArr[i]);
				}
			}
		}
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
	},
	/**
	 * 查看任务信息
	 */
	viewRwxx : function(obj){
		var this_ = this;
		var bjh = $(obj).attr("jh");
		//打开任务信息对活框
		task_info_list_view.show({
			type : this_.param.type,
			bjh : bjh,
			idList:this_.param.idList,
			dprtcode:this_.param.dprtcode
		})
	},
	/**
	 * 选择航材页签
	 */
	checkedMaterial : function(){
		this.type = 1;
	},
	/**
	 * 选择工具页签
	 */
	checkedTool : function(){
		this.type = 2;
	},
	/**
	 * 设置表头
	 */
	setThead : function(theadId, topDiv){
		var tr = this.getTheadTr(topDiv);
		$("#"+theadId, $("#"+topDiv)).html(tr);
	},
	/**
	 * 获取表头tr
	 */
	getTheadTr : function(topDiv){
		var htmlContent = "";
		htmlContent += "<tr>";
		
		htmlContent += "<th class='colwidth-5 selectAllImg'>";
		htmlContent += "<a href='#' onclick=SelectUtil.performSelectAll('"+topDiv+"') ><img src='"+basePath+"/static/assets/img/d1.png' alt='全选' /></a>";
		htmlContent += "<a href='#' class='margin-left-5' onclick=SelectUtil.performSelectClear('"+topDiv+"') ><img src='"+basePath+"/static/assets/img/d2.png' alt='不全选' /></a>";
		htmlContent += "</th>";
		
		htmlContent += "<th class='colwidth-7'>";
		htmlContent += "<div class='font-size-12 line-height-12'>类型</div>";
		htmlContent += "<div class='font-size-9 line-height-12'>Type</div>";
		htmlContent += "</th>";
		
		htmlContent += "<th class='colwidth-15'>";
		htmlContent += "<div class='important'>";
		htmlContent += "<div class='font-size-12 line-height-12'>件号</div>";
		htmlContent += "<div class='font-size-9 line-height-12'>P/N</div>";
		htmlContent += "</div>";
		htmlContent += "</th>";
		
		htmlContent += "<th class='colwidth-10'>";
		htmlContent += "<div class='important'>";
		htmlContent += "<div class='font-size-12 line-height-12'>型号</div>";
		htmlContent += "<div class='font-size-9 line-height-12'>Model</div>";
		htmlContent += "</div>";
		htmlContent += "</th>";
		
		htmlContent += "<th class='colwidth-20'>";
		htmlContent += "<div class='important'>";
		htmlContent += "<div class='font-size-12 line-height-12'>名称</div>";
		htmlContent += "<div class='font-size-9 line-height-12'>Name</div>";
		htmlContent += "</div>";
		htmlContent += "</th>";
		
		htmlContent += "<th class='colwidth-10'>";
		htmlContent += "<div class='important'>";
		htmlContent += "<div class='font-size-12 line-height-12'>件号来源</div>";
		htmlContent += "<div class='font-size-9 line-height-12'>Source</div>";
		htmlContent += "</div>";
		htmlContent += "</th>";
		
		htmlContent += "<th class='colwidth-5'>";
		htmlContent += "<div class='font-size-12 line-height-12'>需求数量</div>";
		htmlContent += "<div class='font-size-9 line-height-12'>QTY</div>";
		htmlContent += "</th>";
		
		htmlContent += "<th class='colwidth-5'>";
		htmlContent += "<div class='font-size-12 line-height-12'>库存数</div>";
		htmlContent += "<div class='font-size-9 line-height-12'>QTY</div>";
		htmlContent += "</th>";
		
		htmlContent += "<th class='colwidth-5'>";
		htmlContent += "<div class='font-size-12 line-height-12'>单位</div>";
		htmlContent += "<div class='font-size-9 line-height-12'>Unit</div>";
		htmlContent += "</th>";
		
		htmlContent += "<th class='colwidth-5'>";
		htmlContent += "<div class='font-size-12 line-height-12'>缺件</div>";
		htmlContent += "<div class='font-size-9 line-height-12'>M/P</div>";
		htmlContent += "</th>";
		
		htmlContent += "<th class='colwidth-20'>";
		htmlContent += "<div class='font-size-12 line-height-12'>互换信息</div>";
		htmlContent += "<div class='font-size-9 line-height-12'>Swap Info</div>";
		htmlContent += "</th>";
		
//		htmlContent += "<th class='colwidth-13'>";
//		htmlContent += "<div class='font-size-12 line-height-12'>说明</div>";
//		htmlContent += "<div class='font-size-9 line-height-12'>Remark</div>";
//		htmlContent += "</th>";
		
		htmlContent += "<th class='colwidth-7'>";
		htmlContent += "<div class='font-size-12 line-height-12'>任务信息</div>";
		htmlContent += "<div class='font-size-9 line-height-12'>Task Info</div>";
		htmlContent += "</th>";
		
		htmlContent += "</tr>";
		
		return htmlContent;
	},
	/**
	 * 清空数据
	 */
	setNoData : function(tbodyId, tableDivId){
		var this_ = this;
		$("#"+tbodyId, $("#"+tableDivId)).empty();
		$("#"+tbodyId, $("#"+tableDivId)).append("<tr><td colspan=\"12\" class='text-center'>暂无数据 No data.</td></tr>");
	},
	search : function(){
		this.load();
	},
	exportExcel : function(){
		var this_ = this;
		var searchParam ={};
		$.extend(searchParam, this_.getParams());
		searchParam.paramsMap.type = this_.param.type;
		window.open(basePath+"/project2/materialtool/toolDetail?paramjson="+JSON.stringify(searchParam));
	},
	
	/**
	 * 需求提报
	 */
	toDemandApply : function(){
		this.openPostWindow(basePath+"/material/demand/apply",JSON.stringify(this.getDemandApplyParams()),"xxx");
	},
	
	/**
	 * 获取需求提报的参数
	 */
	getDemandApplyParams : function(){
		var this_ = this;
		var obj = {};
		obj.details = [];
		obj.dprtcode = this_.param.dprtcode||userJgdm;
		obj.paramsMap = {};
		obj.paramsMap.type = this_.param.type,
		obj.paramsMap.idList = this_.param.idList;
		$("#" + this_.tbodyId_material + " input:checkbox:checked, #" + this_.tbodyId_tool + " input:checkbox:checked").each(function(i, checkbox){
			var $tr = $(checkbox).parents("tr");
			var detail = {};
			detail.bjid = $tr.find("[name='bjid']").val();
			detail.xqsl = $tr.find("[name='xqsl']").text();
			detail.thj = $tr.find("[name='thj']>span").map(function(){
				return $(this).text();
			}).get().join(";");
			if(detail.thj.length > 300){
				detail.thj = detail.thj.substr(0, 300);
			}
			obj.details.push(detail);
		});
		return obj;
	},
	
	/**
	 * 以post方式打开新窗口
	 */
	openPostWindow : function(url, data, name){
		
		var this_ = this;
		var tempForm = document.createElement("form");    
		
	    tempForm.id="tempForm1";    
	    tempForm.method="post";    
	    tempForm.action=url;    
	    tempForm.target=name;    
	  
	    var hideInput = document.createElement("input");    
	    hideInput.type="hidden";    
	    hideInput.name= "content";
	    hideInput.value= data;  
	  
	    tempForm.appendChild(hideInput);     
	    if(window.addEventListener) {
	    	tempForm.addEventListener("onsubmit",function(){this_.openWindow(name);});
	    }else if(window.attachEvent){
	    	tempForm.attachEvent("onsubmit",function(){this_.openWindow(name);});
	    }
	    document.body.appendChild(tempForm);    
	  
	    if (document.createEvent) { // DOM Level 2 standard    
	         evt = document.createEvent("MouseEvents");    
	         evt.initMouseEvent("submit", true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);    
	         tempForm.dispatchEvent(evt);    
	    } else if (tempForm.fireEvent) { // IE    
	         tempForm.fireEvent('onsubmit');    
	    }   
	    tempForm.submit();  
	    document.body.removeChild(tempForm);  
	},
	  
	openWindow : function(name){
		window.open('about:blank',name,'height=400, width=400, top=0, left=0, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=yes, status=yes'); 
	},
};