/**
 * 航材主数据弹窗
 */
open_win_material_basic = {
	id:'open_win_material_basic',
	paginationId:'open_win_material_basic_pagination',
	loaded: false,//是否已加载
	data:[],
	param: {
		multi:false, //是否多选 默认单选
		selected:null, //已经选择的
		existsIdList:null,//已经选择的集合
		callback:function(){},//回调函数
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	//显示弹窗 isReload = true 表示强制加载
	show : function(param, isReload){
		$("#"+this.id).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		this.initParam();
		if(!this.loaded){
			DicAndEnumUtil.registerEnum("materialTypeEnum", "open_win_material_basic_hclx_search");
		}
		if(!this.loaded || isReload === true){
			this.load();
			this.loaded = true;
		}
	},
	//初始化参数
	initParam: function(){
		$("#"+this.id+"_hclx_search").val('');
		$("#"+this.id+"_keyword_search").val('');
		if(this.param.multi){
			$("#checkAll", $("#"+this.id)).show();
			$("#checkSingle", $("#"+this.id)).hide();
		}else{
			$("#checkAll", $("#"+this.id)).hide();
			$("#checkSingle", $("#"+this.id)).show();
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
		
		var obj ={};
		obj.pagination = {page:pageNumber, sort:sortColumn, order:orderType, rows:10};
		$.extend(obj, this.getParams());
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
			url: basePath+"/aerialmaterial/stock/queryAllPageListWin",
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
						renderTo : this_.paginationId,
						data: data,
						sortColumn : sortColumn,
						orderType : orderType,
						controller: this_
					}); 
					// 标记关键字
					signByKeyword($("#"+this_.id+"_keyword_search").val(),[2,3,4,5,8,9], "#"+this_.id+"_list tr td");
				} else {
					$("#"+this_.id+"_list").empty();
					$("#"+this_.paginationId).empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"12\">暂无数据 No data.</td></tr>");
				}
				new Sticky({tableId:this_.id+"_table"});
				this_.loaded = true;
				finishWait();
		    }
		}); 
	},	
	getParams : function(){
		var searchParam = {};
		 var paramsMap = {};
		 var hclx = $.trim($("#"+this.id+"_hclx_search").val());
		 var keyword = $.trim($("#"+this.id+"_keyword_search").val());
		 searchParam.dprtcode = this.param.dprtcode;
		 if('' != hclx){
			 searchParam.hclx = hclx;
		 }
		 if('' != keyword){
			 searchParam.keyword = keyword;
		 }
		 var existsIdList = this.param.existsIdList;
		 if(existsIdList != null && existsIdList != '' && existsIdList.length > 0){
			 paramsMap.idList = existsIdList;
		 }
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		var this_ = this;
		$.each(list,function(index,row){
			   htmlContent += "<tr style=\"cursor:pointer\" >";
			   htmlContent += "<td class='text-center'>"; 
			   
			   if(this_.param.multi){
				   htmlContent += "<input type='checkbox' name='"+this_.id+"_list_checkbox' value='"+index+"'>";  
				}else{
					htmlContent += "<input type='radio' name='"+this_.id+"_list_checkbox' value='"+index+"'  >"; 
				}
			   
			   htmlContent += "</td>";
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>";
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>";
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>";
			  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.cjjh)+"'>"+StringUtil.escapeStr(row.cjjh)+"</td>";  
			
			   htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx) +"</td>";
			   htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('supervisoryLevelEnum',row.gljb) +"</td>";
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.sn)+"'>"+StringUtil.escapeStr(row.sn)+"</td>";
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.pch)+"'>"+StringUtil.escapeStr(row.pch)+"</td>";
			   htmlContent += "<td class='text-right'>"+row.kcsl+"</td>"; 
			   htmlContent += "<td class='text-right'>"+(row.kcsl - row.djsl)+"</td>"; 
			   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.jldw) +"</td>";
			   htmlContent += "</tr>";
		   });
	   $("#"+this_.id+"_list").html(htmlContent);
	   SelectUtil.selectNode('selectAllId',this_.id+"_list");
	   this.rowonclick();
	},
	//搜索
	search: function(){
		this.load();
	},
	//确认
	rowonclick: function(index){
		if(!this.param.multi){
			$("#"+this.id+"_list tr").click(function(event){
				$(this).find("input[type='radio']").attr("checked",true);
			});
		}else{
			$("#"+this.id+"_list tr").click(function(event){
				// 避免复选框重复选择
				if($(event.target).attr("type") == "checkbox"){
					return;
				}
				var checked = $(this).find("input[type='checkbox']").is(":checked");
				$(this).find("input[type='checkbox']").attr("checked",!checked);
			});
		}
	},
	save: function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){
			if(this.param.multi){
				var this_ = this;
				var datas = [];
				$("#"+this_.id+"_list").find("tr input:checked").each(function(){
					var index = $(this).attr("value");	
					datas.push(this_.data[index]);
				});
				this.param.callback(datas);
			}else{
				var $checkedRadio = $("#"+this.id+"_list").find("input:checked");
				this.param.callback(this.data[$checkedRadio.attr("value")]);
			}
		}
	},
	//全选/全不选
	performSelectAll:function(type){
		if(type){
			$("#"+this.id+"_table :checkbox[name='"+this.id+"_list_checkbox']").attr("checked", true);
			$(".sticky-col :checkbox[name='"+this.id+"_list_checkbox']").attr("checked", true);
		}else{
			$("#"+this.id+"_table :checkbox[name='"+this.id+"_list_checkbox']").attr("checked", false);
			$(".sticky-col :checkbox[name='"+this.id+"_list_checkbox']").attr("checked", false);
		}
	}
};