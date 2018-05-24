Hc_List_Modal = {
	id : "Hc_List_Modal",
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
	/**
	 * 初始化航材工具需求清单
	 */
	inItAdd: function(param){
		var hcgjid=$("#hcgjid").val();
		if(hcgjid==null||hcgjid==''){
			AlertUtil.showMessage("请选择一行数据!");
			return;
		}
		var this_ = this;
		AlertUtil.hideModalFooterMessage();
		$("#"+this_.id+"_modalName").html("航材工具需求清单");
		$("#"+this_.id+"_modalEname").html("Add A/C Type");
		$("#"+this_.id+"").modal("show");
		
		this_.param.idList.push($("#hcgjid").val());
		this_.initInfo();
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
		var url = basePath+"/project2/materialtool/queryToolList4WP145";
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
	getParams : function(param){//将搜索条件封装 
		var searchParam = {};
		var paramsMap = {};
		var keyword = $("#keyword_search", $("#"+this.id)).val(); //关键字
		var isWarning = $("#isWarning_search", $("#"+this.id)).val(); //缺件
		var ck = $.trim($("#ck_search", $("#"+this.id)).val()); //仓库
		searchParam.dprtcode = this.param.dprtcode; //组织机构
		paramsMap.keyword = keyword;
		
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
			if(row.bjlx == 1){
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
			
			var isWarning = (paramsMap.isWarning == 1?"是":"否");
			var warningStyle = (paramsMap.isWarning == 1?"color: red":"");
			
			htmlContent += "<tr onclick=SelectUtil.clickRow("+index+",'"+tbodyId+"','"+tbodyId+"_row')>";
			
			htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='checkbox' name='"+tbodyId+"_row' index="+index+"  onclick=SelectUtil.selectCheckbox(event,"+index+",'"+tbodyId+"','"+tbodyId+"_row',this) /></td>";
			
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.bjlx)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.jh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.jh)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.xh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.xh)+"</td>";
			htmlContent += "<td title='"+(StringUtil.escapeStr(paramsMap.bjywms)+" "+ StringUtil.escapeStr(paramsMap.bjzwms))+"' style='text-align:left;vertical-align:middle;'>"+(StringUtil.escapeStr(paramsMap.bjywms)+" "+ StringUtil.escapeStr(paramsMap.bjzwms))+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.jhly)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.jhly)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.xqsl)+"' style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.xqsl)+"</td>";
			
			htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.kcsl)+"' style='text-align:right;vertical-align:middle;'><span style='"+warningStyle+"'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".openStorageDetailWin('"+StringUtil.escapeStr(row.jh)+"')>"+StringUtil.escapeStr(paramsMap.kcsl)+"</a>";
			htmlContent += "</span></td>";
			
			
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(paramsMap.jldw) +"</td>";
			htmlContent += "<td title='' style='text-align:center;vertical-align:middle;'>"+isWarning+"</td>";
			
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>";
		    var tdjxx = paramsMap.tdjxx;
			var str = tdjxx==null?"":tdjxx.split(",");
			for (var i = 0; i < str.length; i++) {
				if(i==str.length-1){
					htmlContent += "<span title='"+StringUtil.escapeStr(str[i].split("#_#"))+"'>"+str[i].split("#_#")+"</span>";
				}else{
					htmlContent += "<span title='"+StringUtil.escapeStr(str[i].split("#_#"))+"'>"+str[i].split("#_#")+"</span>"+"<br>";	
				}								
			}				
		    htmlContent += "</td>";
		    
//		    htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bz)+"</td>";	   
			
			htmlContent += "<td title='' style='text-align:center;vertical-align:middle;'>";
			htmlContent += "<a href='javascript:void(0);' onclick="+this_.id+".viewRwxx(this)>查看详情</a>";
			htmlContent += "</td>";
			
			htmlContent += "</tr>"; 
		});
		if(htmlContent != ''){
			$("#"+tbodyId, $("#"+tableDivId)).html(htmlContent);
			// 标记关键字
			signByKeyword($("#keyword_search", $("#"+this_.id)).val(),[2,3,4,5],"#"+tbodyId+" tr td");
		}else{
			this_.setNoData(tbodyId, tableDivId);
		}
	},
	/**
	 * 打开库存分布详情对话框
	 */
	openStorageDetailWin : function(bjh){
		var this_ = this;
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
		open_win_inventory_distribution_details.show({
			bjh : bjh,
			ckidList:ckidList,
			dprtcode:this_.param.dprtcode
		})
	},
	/**
	 * 查看任务信息
	 */
	viewRwxx : function(obj){
		alert("查看详情!");
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
		
		htmlContent += "<th class='colwidth-5'>";
		htmlContent += "<div class='font-size-12 line-height-12'>类型</div>";
		htmlContent += "<div class='font-size-9 line-height-12'>Type</div>";
		htmlContent += "</th>";
		
		htmlContent += "<th class='colwidth-10'>";
		htmlContent += "<div class='important'>";
		htmlContent += "<div class='font-size-12 line-height-12'>件号</div>";
		htmlContent += "<div class='font-size-9 line-height-12'>P/N</div>";
		htmlContent += "</div>";
		htmlContent += "</th>";
		
		htmlContent += "<th class='colwidth-9'>";
		htmlContent += "<div class='important'>";
		htmlContent += "<div class='font-size-12 line-height-12'>型号</div>";
		htmlContent += "<div class='font-size-9 line-height-12'>Model</div>";
		htmlContent += "</div>";
		htmlContent += "</th>";
		
		htmlContent += "<th class='colwidth-10'>";
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
		
		htmlContent += "<th class='colwidth-7'>";
		htmlContent += "<div class='font-size-12 line-height-12'>需求数量</div>";
		htmlContent += "<div class='font-size-9 line-height-12'>Demand</div>";
		htmlContent += "</th>";
		
		htmlContent += "<th class='colwidth-7'>";
		htmlContent += "<div class='font-size-12 line-height-12'>库存数</div>";
		htmlContent += "<div class='font-size-9 line-height-12'>Inventory</div>";
		htmlContent += "</th>";
		
		htmlContent += "<th class='colwidth-7'>";
		htmlContent += "<div class='font-size-12 line-height-12'>单位</div>";
		htmlContent += "<div class='font-size-9 line-height-12'>Unit</div>";
		htmlContent += "</th>";
		
		htmlContent += "<th class='colwidth-7'>";
		htmlContent += "<div class='font-size-12 line-height-12'>缺件</div>";
		htmlContent += "<div class='font-size-9 line-height-12'>M/P</div>";
		htmlContent += "</th>";
		
		htmlContent += "<th class='colwidth-10'>";
		htmlContent += "<div class='font-size-12 line-height-12'>互换信息</div>";
		htmlContent += "<div class='font-size-9 line-height-12'>Swap Info</div>";
		htmlContent += "</th>";
		
//		htmlContent += "<th class='colwidth-13'>";
//		htmlContent += "<div class='font-size-12 line-height-12'>说明</div>";
//		htmlContent += "<div class='font-size-9 line-height-12'>Remark</div>";
//		htmlContent += "</th>";
		
		htmlContent += "<th class='colwidth-10'>";
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
		$("#"+tbodyId, $("#"+tableDivId)).empty();
		$("#"+tbodyId, $("#"+tableDivId)).append("<tr><td colspan=\"12\" class='text-center'>暂无数据 No data.</td></tr>");
	},
	search : function(){
		this.load();
	}
};


