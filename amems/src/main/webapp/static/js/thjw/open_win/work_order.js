/**
 * 工单
 */
open_win_work_order = {
	id:'open_win_work_order',
	loaded: false,//是否已加载
	data:[],
	pageSize: 10,
	dicts:[],
	param: {
		multi:true, //是否多选 默认多选
		selected:null, //已经选择的
		fjzch:null,//飞机注册号
		dprtcode:userJgdm,//默认登录人当前机构代码
		gdzt :[],//工单状态
		type : 135,//工单类型
		userId : '',
		userType : '',
		callback:function(){}//回调函数
	},
	//显示弹窗 isReload = true 表示强制加载
	show : function(param, isReload){
		$("#"+this.id).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		if(isReload === true){
			$("#"+this.id+"_keyword_search").val("");
		}
		if(!this.loaded){
			this.lyfl_option = $("#lyfl_search", $("#"+this.id)).html();
		}
		if(!this.loaded || isReload === true){
			this.initParam();
			this.multiselect();
			this.load();
		}
	},
	//初始化参数
	initParam: function(){
		$("#zy2_search").empty();
		$("#zy2_search").append('<option value="" select="select">查看全部</option>');
		DicAndEnumUtil.registerDic('4','zy2_search',this.param.dprtcode);      //专业
		if(this.param.multi){
			$("#"+this.id+"_single_choice").hide();
			$("#"+this.id+"_multi_choice").show();
		}else{
			$("#"+this.id+"_single_choice").show();
			$("#"+this.id+"_multi_choice").hide();
		}	
	},
	/**
	 * 初始化多选下拉框
	 */
	multiselect : function(){
		var this_ = this;
		//生成多选下拉框区域
		$('#lyfl_div',$("#"+this_.id)).empty();
		$('#lyfl_div',$("#"+this_.id)).html("<select class='form-control' multiple='multiple' id='lyfl_search'></select>");
		var items = DicAndEnumUtil.getEnum("workorderTypeEnum");
		if(items != null && items.length > 0){
			for(i=0;i < items.length;i++){
				if(items[i].id != 9){
					$("#lyfl_search", $("#"+this_.id)).append("<option value='"+items[i].id+"' >"+StringUtil.escapeStr(items[i].name)+"</option>")
				}
			 }
			//生成多选来源分类
			$("#lyfl_search", $("#"+this_.id)).multiselect({
				buttonClass: 'btn btn-default',
				buttonWidth: 'auto',
				buttonWidth:'100%' ,
				buttonHeight: '34px',
				buttonContainer: '<div class="btn-group base-data-syjx" style="min-width:100%;" />',
				numberDisplayed:100,
				includeSelectAllOption: true,
				nonSelectedText:'显示全部 All',
			    allSelectedText:'显示全部 All',
				selectAllText: '选择全部 Select All',
				onChange : function(element, checked) {
				}
			});
		}
	},
	//加载数据
	load : 	function(pageNumber, sortColumn, orderType){
		var this_ = this;
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
		this_.pagination = {page: pageNumber, sort: sortColumn, order: orderType, rows: this_.pageSize};
		searchParam.pagination = this_.pagination;
		$.extend(searchParam, this_.getParams());
		
		var url = basePath+"/produce/workorder/queryAllPageListWin";
//		var dprtType = AccessDepartmentUtil.getDpartinfoType(this_.param.dprtcode);
		if(this_.param.type == 145){
			url = basePath+"/produce/workorder145/queryAllPageListWin";
		}
		
		startWait();
		AjaxUtil.ajax({
			url:url,
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(searchParam),
			success:function(data){
				finishWait();
				if(data.total >0){
					this_.dicts = data.dicts;
					this_.data = data.rows;
					this_.appendContentHtml(data.rows);
					new Pagination({
						renderTo : this_.id+"_pagination",
						data: data,
						sortColumn : sortColumn,
						orderType : orderType,
						goPage: function(a,b,c){
							this_.load(a,b,c);
						}
					});	  
					// 标记关键字
					signByKeyword($("#"+this_.id+"_keyword_search").val(),[3,6], "#"+this_.id+"_list tr td");
				} else {
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").html("<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>");
				}
				this_.loaded = true;
			}
		}); 
	},		
	/**将搜索条件封装*/
	getParams : function(){
		
		var this_ = this;
		var searchParam = {};
		var paramsMap = {};
		var zy = $.trim($("#zy2_search", $("#"+this_.id)).val());
		var lyfl = $.trim($("#lyfl_search", $("#"+this_.id)).val());
		searchParam.fjzch = this_.param.fjzch;
		paramsMap.keyword = $.trim($("#"+this_.id+"_keyword_search").val());
		searchParam.dprtcode = this_.param.dprtcode;
		if(this_.param.gdzt != null && this_.param.gdzt.length > 0){
			paramsMap.gdzt = this_.param.gdzt;
		}
		if(zy != null && zy != ''){
			searchParam.zy = zy;
		}
		if(lyfl != null && lyfl.length > 0){
			var gdlxArr = lyfl.split(",");
			var gdlxList = [];
			for(var i = 0 ; i < gdlxArr.length ; i++){
				if('multiselect-all' != gdlxArr[i]){
					gdlxList.push(gdlxArr[i]);
				}
			}
			if(gdlxList.length > 0){
				paramsMap.gdlx = gdlxList;
			}
		}
		paramsMap.userId = this_.param.userId;
		paramsMap.userType = this_.param.userType;
//		paramsMap.gdlx = this_.param.gdlx;
		searchParam.paramsMap = paramsMap;
		return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		var this_ = this;
		$.each(list,function(index,row){
			var checked = "";
			
			if(open_win_work_order.param.selected &&(open_win_work_order.param.selected).indexOf(row.id) !=-1){
				checked = "checked";
			}
			htmlContent = htmlContent + "<tr onclick='"+this_.id+".rowonclick(event)' >";
			if(this_.param.multi){
				htmlContent = htmlContent + "<td style='vertical-align: middle;' align='center'><input type=\"checkbox\" name='"+this_.id+"_checkbox' value='"+index+"' "+checked+"/></td>" ;
			}else{
				htmlContent = htmlContent + "<td style='vertical-align: middle;' align='center'><input type=\"radio\" name='glgd' value='"+index+"'/></td>" ;
			}
			htmlContent = htmlContent + "<td style='vertical-align: middle;' align='center'>"+(index+1)+"</td>";
			htmlContent = htmlContent + "<td style='vertical-align: middle;'>"+formatUndefine(row.gdbh)+"</td>";
			var lyfx = formatUndefine(DicAndEnumUtil.getEnumName('workorderTypeEnum', StringUtil.escapeStr(row.gdlx)));
			if(row.gdlx == 1 && row.paramsMap.gdzlx != null){
				lyfx = formatUndefine(DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum', StringUtil.escapeStr(row.paramsMap.gdzlx)))
			}
			htmlContent += "<td title='"+StringUtil.escapeStr(lyfx)+"'>"+StringUtil.escapeStr(lyfx)+"</td>";
			htmlContent = htmlContent + "<td style='vertical-align: middle;' title='"+StringUtil.escapeStr(row.zy)+"'>"+StringUtil.escapeStr(row.zy)+"</td>";  
			htmlContent = htmlContent + "<td style='vertical-align: middle;' title='"+StringUtil.escapeStr(row.gdbt)+"'>"+StringUtil.escapeStr(row.gdbt)+"</td>";  
				htmlContent = htmlContent + "</tr>"; 
	   });
	   $("#"+this_.id+"_list").html(htmlContent);
	},
	//确认
	rowonclick: function(e){
		if(this.param.multi){
			if(e.target.type!="checkbox"){
				var checkbox = $(e.target).parent().find("input:checkbox");
				checkbox.attr("checked",!checkbox.attr("checked"));
			}
			if($("input:checkbox[name='"+this.id+"_checkbox']:not(:checked)").length > 0){
				$("#"+this.id+"_checkall").attr("checked",false);
			}else{
				$("#"+this.id+"_checkall").attr("checked",true);
			}
		}else{
			$(e.target).parent().find("input:radio").attr("checked",true);
		}
	},
	checkAll: function(){
		if($("#"+this.id+"_checkall").is(":checked")){
			$("input:checkbox[name='"+this.id+"_checkbox']").attr("checked",true);
		}else{
			$("input:checkbox[name='"+this.id+"_checkbox']").attr("checked",false);
		}
	},
	close : function(){
		$("#"+this.id).modal("hide");
	},
	save: function(){
		var $checked = $("#"+this.id+"_list").find("input:checked");
		if($checked.length > 0){
			if(this.param.callback && typeof(this.param.callback) == "function"){
				if(this.param.multi){
					var datas = [];
					var this_ = this;
					$.each($checked, function(index, row){
						var row_data = this_.data[$(row).attr("value")];
						var data = {gdlx: row_data.gdlx, gdbh: row_data.gdbh, gdid: row_data.id};
						datas.push(data);
						$(this).attr("checked",false);
					})
					this.param.callback(datas);
				}else{
					var data = this.data[$checked.attr("value")];
					this.param.callback({gdlx: data.gdlx, gdbh: data.gdbh, gdid: data.id});
				}
			}
			$("#"+this.id).modal("hide");
			
		}else{
			AlertUtil.showMessage("请选择工单");
		}
	}
};