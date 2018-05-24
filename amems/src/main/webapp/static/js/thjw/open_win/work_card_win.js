/**
 * 工卡弹窗
 */
open_win_work_card = {
	id:'open_win_work_card',
	paginationId:'open_win_work_card_pagination',
	loaded: false,//是否已加载
	data:[],
	param: {
		parentWinId : '',
		multi:false, //是否多选 默认单选
		clearData:false,
		selected:null, //已经选择的
		jx : '',//机型
		existsGkhList:null,//已经选择的集合,工卡号
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
		if(!this.loaded || isReload === true){
			this.load();
			this.loaded = true;
		}
		if(!param.clearData){
			$("#open_win_work_card_clear_btn").hide();
		}
//		ModalUtil.showSearchModal(this.param.parentWinId,this.id,this.paginationId);
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
			url:basePath+"/project2/workcard/queryWinAllPageList",
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
					signByKeyword($("#"+this_.id+"_keyword_search").val(),[2,3,4], "#"+this_.id+"_list tr td");
				} else {
					$("#"+this_.id+"_list").empty();
					$("#"+this_.paginationId).empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
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
		 var keyword = $.trim($("#"+this.id+"_keyword_search").val());
		 searchParam.dprtcode = this.param.dprtcode;
		 searchParam.jx = this.param.jx;
		 if(null != keyword && '' != keyword){
			 paramsMap.keyword = keyword;
		 }
		 var existsGkhList = this.param.existsGkhList;
		 if(existsGkhList != null && existsGkhList != '' && existsGkhList.length > 0){
			 paramsMap.existsGkhList = existsGkhList;
		 }
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		var this_ = this;
		$.each(list,function(index,row){
			   htmlContent += "<tr style=\"cursor:pointer\" >";
			   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"; 
			   
			   if(this_.param.multi){
				   htmlContent += "<input type='checkbox' name='"+this_.id+"_list_checkbox' value='"+index+"'>";  
				}else{
					htmlContent += "<input type='radio' name='"+this_.id+"_list_checkbox' value='"+index+"'  >"; 
				}
			   
			   htmlContent += "</td>";
			  
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.gkh)+"' style='text-align:left;vertical-align:middle;'>";
			   htmlContent += StringUtil.escapeStr(row.gkh);
			   htmlContent += "</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.gzbt)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gzbt)+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.wxxmbh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.wxxmbh)+"</td>";
			
			   var zjh = '';
			   if(row.fixChapter != null){
				   zjh = StringUtil.escapeStr(row.fixChapter.displayName);
			   }
			   htmlContent += "<td title='"+zjh+"' style='text-align:left;vertical-align:middle;'>"+zjh+"</td>";
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.gzlx)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gzlx)+"</td>";
		   
			   var bzgs = '';
			   if(row.jhgsRs != null && row.jhgsRs != "" && row.jhgsXss != null && row.jhgsXss != ""){
				   var total = row.jhgsRs*row.jhgsXss;
				   if(total == ''){
					   total = 0;
				   }
				   if(String(total).indexOf(".") >= 0){
					   total = total.toFixed(2);
				   }
				   bzgs = row.jhgsRs+"人x"+row.jhgsXss+"时="+total+"时";
			   }
			   htmlContent += "<td title='"+bzgs+"' style='text-align:left;vertical-align:middle;'>"+bzgs+"</td>";  
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.zy)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.zy)+"</td>";
			   
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
	clearData:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
				this.param.callback({});
		}
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