
/**
 * 需求来源
 */
open_win_demand_source = {
	id:'open_win_demand_source',
	loaded: false,//是否已加载
	data:[],
	param: {
		multi:false, //是否多选 默认单选
		cleanData: false,
		fjzch:null,//飞机注册号
		type:"135",//类型
		selected:null, //已经选择的
		existsIdList:null,//已经选择的集合
		dprtcode:userJgdm,//默认登录人当前机构代码
		callback:function(){}//回调函数
	},
	//显示弹窗 isReload = true 表示强制加载
	show : function(param, isReload){
		var this_ = this;
		if(param){
			$.extend(this.param, param);
		}
		$("#"+this.id).modal("show");
		this.initParam();
		this.load();
		open_win_demand_source_wo.show({
			multi : false,
			loaded : false,
			cleanData : false,
			fjzch : this_.param.fjzch,
			dprtcode : this_.param.dprtcode,
			selected : this_.param.selected,
			type : this_.param.type,
		});
		if(!param.cleanData){
			$("#source_btn_clear").hide();
		}
		if(!this.loaded || isReload === true){
		}
		var this_ = this;
	},
	//初始化参数
	initParam: function(){
		$("#"+this.id+"_keyword_search").val('');
		if(this.param.multi){
			$("#checkAll", $("#"+this.id)).show();
			$("#checkSingle", $("#"+this.id)).hide();
		}else{
			$("#checkAll", $("#"+this.id)).hide();
			$("#checkSingle", $("#"+this.id)).show();
		}
		
		if(this.param.type == "135"){
			$("#source_tab_wp").text("工包135 Work Package 135");
			$("#source_tab_wo").text("工单135 Work Order 135");
		}else{
			$("#source_tab_wp").text("工包145 Work Package 145");
			$("#source_tab_wo").text("工单145 Work Order 145");
		}
	},
	//加载数据
	load : 	function(pageNumber, sortColumn, orderType){
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
		
		var url = basePath+"/produce/workpackage/getWorkpackageList";
		if(this_.param.type == "145"){
			url = basePath+"/produce/workpackage145/getWorkpackage145List";
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
					signByKeyword($("#"+this_.id+"_keyword_search").val(),[2,3,8], "#"+this_.id+"_list tr td");
				} else {
					$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"9\">暂无数据 No data.</td></tr>");
				}
				//new Sticky({tableId:"open_win_regist_basic_table"});
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
			 searchParam.keyword = keyword;
		 }
		 var existsIdList = this.param.existsIdList;
		 if(existsIdList != null && existsIdList != '' && existsIdList.length > 0){
			 paramsMap.existsIdList = existsIdList;
		 }
		 paramsMap.ztList = [2,7];
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
			
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gbbh)+"' style='text-align:left;vertical-align:middle;'><a href='#' onclick='open_win_demand_source.viewWorkPackage(\""+row.id+"\")'>"+StringUtil.escapeStr(row.gbbh)+"</a></td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gbmc)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gbmc)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.wxlx)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.wxlx)+"</td>";  
			htmlContent += "<td title='"+(row.jhKsrq || "").substr(0, 10)+"' style='text-align:center;vertical-align:middle;'>"+(row.jhKsrq || "").substr(0, 10)+"</td>";  
			htmlContent += "<td title='"+(row.jhJsrq || "").substr(0, 10)+"' style='text-align:center;vertical-align:middle;'>"+(row.jhJsrq || "").substr(0, 10)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.xfdwDepartment==null?"":row.xfdwDepartment.dprtname)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xfdwDepartment==null?"":row.xfdwDepartment.dprtname)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.jhZxdw)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.jhZxdw)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gzyq)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gzyq)+"</td>";  
			
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
	save: function(){
		
		if(this.param.callback && typeof(this.param.callback) == "function"){
			if(this.param.multi){
				var this_ = this;
				var data = [];
				this.param.callback(data);
			}else{
				var obj = {};
				if($("#open_win_demand_source_list").is(":visible")){
					var $checkedRadio = $("#"+this.id+"_list").find("input:checked");
					var index = $checkedRadio.attr("value");	
					var data = this.data;
					if(data.length > 0 && index != undefined){		
						obj.lylx = 1;
						obj.lyid = this.data[index].id;
						obj.lybh = this.data[index].gbbh;
						obj.bs145 = this.param.type == "145" ? 1 : 0;
						this.param.callback(obj);
					}
				}
				
				if($("#open_win_demand_source_wo_list").is(":visible")){
					var $checkedRadio = $("#"+open_win_demand_source_wo.id+"_list").find("input:checked");
					var index = $checkedRadio.attr("value");	
					var data = open_win_demand_source_wo.data;
					if(data.length > 0 && index != undefined){
						obj.lylx = 2;
						obj.lyid = open_win_demand_source_wo.data[index].id;
						obj.lybh = open_win_demand_source_wo.data[index].gdbh;
						obj.bs145 = this.param.type == "145" ? 1 : 0;
						this.param.callback(obj);
					}
				}
			}
		}
	},
	clearProject:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
				this.param.callback({});
		}
	},
	// 工包查看
	viewWorkPackage:function(id){
		if(this.param.type == "135"){
			window.open(basePath + "/produce/workpackage/view?id=" + id);
		}else{
			window.open(basePath + "/produce/workpackage145/view?id=" + id);
		}
	},
};