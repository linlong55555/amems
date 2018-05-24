/**
 * 合同明细弹窗
 */
open_win_contract_info = {
	id:'open_win_contract_info',
	paginationId:'open_win_contract_info_pagination',
	loaded: false,//是否已加载
	data:[],
	param: {
		parentWinId : '',
		multi:false, //是否多选 默认单选
		selected : null, //已经选择的
		mainid : '',//合同id
		biz : '',//币种id
		modalHeadChina : '合同明细',
		modalHeadEnglish : 'Contract Detail',
		callback:function(){}//回调函数
	},
	//显示弹窗 isReload = true 表示强制加载
	show : function(param, isReload){
		$("#"+this.id).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		this.initParam();
		if(!this.loaded || isReload === true){
			this.load();
			this.loaded = true;
		}
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
			url:basePath+"/material/contractinfo/queryWinAllPageList",
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
					signByKeyword($("#"+this_.id+"_keyword_search").val(),[2,3], "#"+this_.id+"_list tr td");
				} else {
					$("#"+this_.id+"_list").empty();
					$("#"+this_.paginationId).empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"9\">暂无数据 No data.</td></tr>");
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
		 if('' != keyword){
			 searchParam.paramsMap.keyword = keyword;
		 }
		 searchParam.mainid = this.param.mainid;
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		var this_ = this;
		var biz = this_.param.biz;
		$.each(list,function(index,row){
			
			var sl = StringUtil.escapeStr(row.sl);
			var dw = StringUtil.escapeStr(row.dw);
			if(sl != null && sl != ''){
				sl += dw;
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
		   
			htmlContent += "<td title='"+StringUtil.escapeStr(row.bjh)+"' class='text-left'>";
			htmlContent += StringUtil.escapeStr(row.bjh);
			htmlContent += "</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.paramsMap.zywms)+"' class='text-left'>"+StringUtil.escapeStr(row.paramsMap.zywms)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.xlh)+"' class='text-left'>"+StringUtil.escapeStr(row.xlh)+"</td>";
			htmlContent += "<td title='"+(sl)+"' class='text-left'>"+(sl)+"</td>";
			
			var dj = formatUndefine(row.dj);
			if(dj != ''){
				dj = dj.toFixed(2) + biz;
			}
			
			htmlContent += "<td title='"+dj+"' class='text-left'>"+dj+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.wlzt)+"' class='text-left'>"+StringUtil.escapeStr(row.wlzt)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.sxyy)+"' class='text-left'>"+StringUtil.escapeStr(row.sxyy)+"</td>";
			var jhq = StringUtil.escapeStr(row.jhq);
			if(row.jhqdw == 11){
				jhq += "D";
			}
			if(row.jhqdw == 12){
				jhq += "M";
			}
			htmlContent += "<td title='"+jhq+"' class='text-left'>"+jhq+"</td>";
			
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
				var data = this.data[$checkedRadio.attr("value")];
				if(data){
					this.param.callback(data);
				}
			}
		}
		this.close();
	},
	clear: function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback({});
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