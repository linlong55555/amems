/**
 * 航材主数据弹窗
 */
open_win_material_tools = {
	id:'open_win_material_tools',
	paginationId:'open_win_material_tools_pagination',
	loaded: false,//是否已加载
	data:[],
	param: {
		parentWinId : '',
		multi:false, //是否多选 默认单选
		selected:null, //已经选择的
		existsIdList:null,//已经选择的集合,id
		existsBjhList:null,//已经选择的集合,部件号
		hclxlist:null,//已经选择的集合,航材类型
		modalHeadChina : '航材列表',
		modalHeadEnglish : 'Material List',
		search_criteria : false,
		callback:function(){},//回调函数
		dprtcode:userJgdm,//默认登录人当前机构代码
		showStock:true//显示库存
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
//		ModalUtil.showSearchModal(this.param.parentWinId,this.id,this.paginationId);
	},
	//初始化参数
	initParam: function(){
		$("#"+this.id+"_hclx_search", $("#"+this.id)).val('');
		$("#"+this.id+"_keyword_search", $("#"+this.id)).val('');
		if(this.param.multi){
			$("#checkAll", $("#"+this.id)).show();
			$("#checkSingle", $("#"+this.id)).hide();
		}else{
			$("#checkAll", $("#"+this.id)).hide();
			$("#checkSingle", $("#"+this.id)).show();
		}
		$(".search-criteria", $("#"+this.id)).hide();
		if(this.param.search_criteria){
			$(".search-criteria", $("#"+this.id)).show();
		}
		$("#modalHeadChina", $("#"+this.id)).html(this.param.modalHeadChina);
		$("#modalHeadEnglish", $("#"+this.id)).html(this.param.modalHeadEnglish);
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
			url:basePath+"/material/material/selectWinList",
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
					signByKeyword($("#"+this_.id+"_keyword_search").val(),[3,4,5], "#"+this_.id+"_list tr td");
				} else {
					$("#"+this_.id+"_list").empty();
					$("#"+this_.paginationId).empty();
					if(this_.param.showStock){
						$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"9\">暂无数据 No data.</td></tr>");
					}else{
						$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"7\">暂无数据 No data.</td></tr>");
					}
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
		 var keyword = $.trim($("#"+this.id+"_keyword_search", $("#"+this.id)).val());
		 var hclx = $.trim($("#"+this.id+"_hclx_search", $("#"+this.id)).val());
		 searchParam.dprtcode = this.param.dprtcode;
		 searchParam.hclxs =this.param.hclxs;//航材类型
		 if('' != keyword){
			 searchParam.keyword = keyword;
		 }
		 var existsIdList = this.param.existsIdList;
		 if(existsIdList != null && existsIdList != '' && existsIdList.length > 0){
			 paramsMap.existsIdList = existsIdList;
		 }
		 var existsBjhList = this.param.existsBjhList;
		 if(existsBjhList != null && existsBjhList != '' && existsBjhList.length > 0){
			 paramsMap.existsBjhList = existsBjhList;
		 }
		 var hclxlist = this.param.hclxlist;
		 if(hclxlist != null && hclxlist != '' && hclxlist.length > 0){
			 paramsMap.hclxlist = hclxlist;
		 }
		 if(hclx != null && hclx != ''){
			 paramsMap.hclx = hclx;
		 }
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		var this_ = this;
		$.each(list,function(index,row){
			
			var hclx = DicAndEnumUtil.getEnumName('materialTypeEnum', row.hclx);
			if(row.hclx == 1 && row.hclxEj != null && row.hclxEj != ''){
				hclx = DicAndEnumUtil.getEnumName('materialSecondTypeEnum', row.hclxEj);
			}
			
			htmlContent += "<tr style=\"cursor:pointer\" >";
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"; 
		   
			if(this_.param.multi){
				htmlContent += "<input type='checkbox' name='"+this_.id+"_list_checkbox' value='"+index+"'>";  
			}else{
				if(this_.param.selected == row.id){
					htmlContent += "<input type='radio' checked='checked' name='"+this_.id+"_list_checkbox' value='"+index+"'  >"; 
				}else{
					htmlContent += "<input type='radio'  name='"+this_.id+"_list_checkbox' value='"+index+"'  >"; 
				}
			}
		   
			htmlContent += "</td>";
		   
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+ hclx +"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.bjh)+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.bjh)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.ywms)+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.ywms)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.zwms)+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.zwms)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.xingh)+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.xingh)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.jhly)+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.jhly)+"</td>";
			if(this_.param.showStock){
				htmlContent += "<td style='text-align:right;vertical-align:middle;'>"+formatUndefine(row.kykcsl?row.kykcsl:0)+"</td>"; 
				htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.jldw) +"</td>";
			}else{
				$(".stock_column").remove();
			}
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
	close : function(){
		$("#"+this.id).modal("hide");
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
		this.close();
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