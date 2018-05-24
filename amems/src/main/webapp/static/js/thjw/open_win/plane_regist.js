/**
 * 飞机注册号弹窗
 */
open_win_plane_regist = {
	id:'open_win_plane_regist',
	loaded: false,//是否已加载
	data:[],
	param: {
		multi:false, //是否多选 默认单选
		fjjx:'', //机型
		selected:null, //已经选择的
		existsIdList:null,//已经选择的集合
		dprtcode:userJgdm,//默认登录人当前机构代码
		callback:function(){}//回调函数
	},
	//显示弹窗 isReload = true 表示强制加载
	show : function(param, isReload){
		$("#"+this.id).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		this.initParam();
		this.load();
		if(!this.loaded || isReload === true){
		}
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
			url: basePath+"/productionplan/planeData/queryAllPageWinList",
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
					signByKeyword($("#"+this_.id+"_keyword_search").val(),[2,3,4,5], "#"+this_.id+"_list tr td");
				} else {
					$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
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
		 var keyword = $.trim($("#"+this.id+"_keyword_search").val());
		 searchParam.dprtcode = this.param.dprtcode;
		 if(this.param.fjjx){
			 searchParam.fjjx = this.param.fjjx;
		 }
		 if('' != keyword){
			 searchParam.keyword = keyword;
		 }
		 var existsIdList = this.param.existsIdList;
		 if(existsIdList != null && existsIdList.length > 0){
			 paramsMap.codeList = existsIdList;
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
				if(this_.param.selected && this_.param.selected == row.fjzch){
					checked = "checked";
				}
				htmlContent += "<input type='radio' name='"+this_.id+"_list_radio' value='"+index+"' "+checked+" >"; 
			}
			htmlContent += "</td>";
		   
			htmlContent += "<td title='"+StringUtil.escapeStr(row.fjjx)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.fjjx)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.fjzch)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.fjzch)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.xlh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xlh)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.bzm)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bzm)+"</td>";  
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+indexOfTime(row.ccrq)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gjdjzh)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.gjdjzh)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.shzh)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.shzh)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.wxdtxkzh)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.wxdtxkzh)+"</td>";  
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+indexOfTime(row.dtzzjkrq)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.jsgzjl)+"' style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.jsgzjl)+"</td>";  
		   
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
					data.push(this_.data[index]);
				});
				this.param.callback(data);
			}else{
				var $checkedRadio = $("#"+this.id+"_list").find("input:checked");
				this.param.callback(this.data[$checkedRadio.attr("value")]);
			}
		}
	}
};