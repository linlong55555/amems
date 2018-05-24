
/**
 * 收货明细
 */
var receiptDetail = {
	data : [],
	merge : function(obj){
		var insert = true;
		var list = this.data;
		for(var i = 0; i < list.length; i++){
			if(list[i].rowid == obj.rowid){
				list[i] = obj;
				insert = false;
			}
		}
		if(insert){
			list.push(obj);
		}
	},
	remove : function(rowid){
		var list = this.data;
		for (var i = 0; i < list.length; i++) {
            if (list[i].rowid == rowid) {
            	list.splice(i, 1);
            }
        }
	} ,
	set : function(list){
		this.data = list;
	},
	get : function(rowid){
		var result;
		var list = this.data;
		for (var i = 0; i < list.length; i++) {
            if (list[i].rowid == rowid) {
            	result = list[i];
            }
        }
		return result;
	},
	getAll : function(){
		return this.data;
	},
};

/**
 * 收货来源信息
 */
open_win_receipt_info = {
	id:'open_win_receipt_info',
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
		var this_ = this;
		if(param){
			$.extend(this.param, param);
		}
		$("#"+this.id).modal("show");
		this.initParam();
		if(this.param.shlx != 90){
			this.load();
		}
		open_win_receipt_info_material.show({
			multi : true,
			loaded : false,
			cleanData : false,
			dprtcode : this_.param.dprtcode,
//			existsIdList : this_.param.existsBjList,
		});
		if(!param.clearProject){
			$("#project_btn_clear").hide();
		}
		if(!this.loaded || isReload === true){
			$("#open_win_receipt_info_source_hclx").html("<option value='' >显示全部All</option>");
			$("#open_win_receipt_info_material_hclx").html("<option value='' >显示全部All</option>");
			
			DicAndEnumUtil.registerEnum("materialTypeEnum", "open_win_receipt_info_source_hclx");
			DicAndEnumUtil.registerEnum("materialTypeEnum", "open_win_receipt_info_material_hclx");
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
		
		if(this.param.shlx == 90){
			$("#receipt_tab_materiel").click();
			$("#receipt_tab_origin").hide();
		}else{
			$("#receipt_tab_origin").click();
			$("#receipt_tab_origin").show();
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
		AjaxUtil.ajax({
			url: basePath+"/material/outin/originlist",
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
					signByKeyword($("#"+this_.id+"_keyword_search").val(),[3,4,12,13,14], "#"+this_.id+"_list tr td");
				} else {
					$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"14\">暂无数据 No data.</td></tr>");
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
		 searchParam.shlx = this.param.shlx;
		 searchParam.lyid = this.param.lyid;
		 searchParam.dprtcode = this.param.dprtcode;
		 var keyword = $.trim($("#"+this.id+"_keyword_search").val());
		 if('' != keyword){
			 paramsMap.keyword = keyword;
		 }
		 var hclx = $.trim($("#open_win_receipt_info_source_hclx").val());
		 if('' != hclx){
			 paramsMap.hclx = hclx;
		 }
		 var existsIdList = this.param.existsIdList;
		 if(existsIdList != null && existsIdList != '' && existsIdList.length > 0){
			 paramsMap.existsIdList = existsIdList;
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
			
			var sl = row.sl ? (StringUtil.escapeStr(row.sl)+" "+StringUtil.escapeStr(row.dw)) : "";
			
			htmlContent += "<td title='"+DicAndEnumUtil.getEnumName('materialTypeEnum', row.hclx)+"' style='text-align:center;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('materialTypeEnum', row.hclx)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.bjh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bjh)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.xlh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xlh)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.bjmc)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bjmc)+"</td>";  
			htmlContent += "<td title='"+sl+"' style='text-align:left;vertical-align:middle;'>"+sl+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.xingh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xingh)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gg)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gg)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.jhly)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.jhly)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.zjhms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.zjhms)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.bzjh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bzjh)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.cqbh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.cqbh)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.hth)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.hth)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.xgfms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xgfms)+"</td>";
			
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
				$("#"+this_.id+"_list", $("#"+this_.id)).find("tr input:checked").each(function(){
					var index = $(this).attr("value");	
					this_.data[index].sn = this_.data[index].xlh;
					this_.data[index].lymxid = this_.data[index].id;
					this_.data[index].lymxlx = this_.param.shlx == 60 ? 1 : 2;
					this_.data[index].dw = this_.data[index].jldw;
					delete this_.data[index].xlh;
					delete this_.data[index].id;
					data.push(this_.data[index]);
				});
				$("#"+open_win_receipt_info_material.id+"_list").find("tr input:checked").each(function(){
					var index = $(this).attr("value");	
					open_win_receipt_info_material.data[index].sn = open_win_receipt_info_material.data[index].xlh;
					open_win_receipt_info_material.data[index].bjid = open_win_receipt_info_material.data[index].id;
					open_win_receipt_info_material.data[index].dw = open_win_receipt_info_material.data[index].jldw;
					open_win_receipt_info_material.data[index].bjmc = StringUtil.escapeStr(open_win_receipt_info_material.data[index].ywms) + " " +  StringUtil.escapeStr(open_win_receipt_info_material.data[index].zwms);
					delete open_win_receipt_info_material.data[index].xlh;
					delete open_win_receipt_info_material.data[index].id;
					data.push(open_win_receipt_info_material.data[index]);
				});
				this.param.callback(data);
			}else{
				var $checkedRadio = $("#"+this.id+"_list").find("input:checked");
				var index = $checkedRadio.attr("value");	
				var data = this.data;
				if(data.length > 0 && index != undefined){			
					this.param.callback(this.data[index]);
				}else{
					
				}
			}
		}
	},
	clearProject:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
				this.param.callback({});
		}
	},
	// 维修项目查看
	viewProject:function(id){
		window.open(basePath + "/project2/maintenanceproject/view?id=" + id);
	},
};