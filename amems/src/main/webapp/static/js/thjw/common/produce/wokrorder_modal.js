/**
 * 选择不在工包和预组包中的工单
 */
open_win_workorder_modal = {
	id:'open_win_workorder_modal',
	paginationId:'"open_win_workorder_modal_pagination"',
	loaded: false,//是否已加载
	data:[],
	param: {
		workpackageId:null,
		fjzch:null,
		lx:1,
		gdlx:[],
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
		AlertUtil.hideModalFooterMessage();		
		this.load();
		if(!this.loaded || isReload === true){
		}
	},
	//初始化参数
	initParam: function(){
		$("#"+this.id+"_keyword_search").val('');
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
		obj=this.getParams();
		obj.pagination = {page:pageNumber, sort:sortColumn, order:orderType, rows:10};
		startWait();
		var url ="";
		if(this.param.lx==1){
			url = basePath+"/produce/workorder/getWorkorderList";
		}
		if(this.param.lx==2){
			url = basePath+"/produce/workorder145/getWorkorderList"
		}
		var this_ = this;
		AjaxUtil.ajax({
			url: url,
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(obj),
			success:function(data){
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
					signByKeyword($("#"+this_.id+"_keyword_search").val(),[2], "#"+this_.id+"_list tr td");
				} else {
					$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"8\">暂无数据 No data.</td></tr>");
				}
				this_.loaded = true;
				finishWait();
		    }
		}); 
	},		
	getParams : function(){		
		 var param = {};
		 var keyword = $.trim($("#"+this.id+"_keyword_search").val());
		 param.dprtcode = this.param.dprtcode;
		 param.fjzch=this.param.fjzch;
		 param.gbid=this.param.workpackageId;
		 if('' != keyword){
			 param.keyword = keyword;
		 }
		 if(this.param.gdlx.length>0){
			 var paramsMap ={};
			 paramsMap.gdlx =this.param.gdlx;
			 param.paramsMap = paramsMap;
		 }
		 return param;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		var this_ = this;
		$.each(list,function(index,row){
			
			if (index % 2 == 0) {
				htmlContent += "<tr  bgcolor=\"#f9f9f9\" onclick='"+this_.id+".rowonclick(event);' >";
			} else {
				htmlContent += "<tr  bgcolor=\"#fefefe\" onclick='"+this_.id+".rowonclick(event);' >";
			}
		   
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"; 			
			htmlContent += "<input type='checkbox' name='"+this_.id+"_list_radio' value='"+index+"' onclick=\"SelectUtil.checkRow(this,'selectAllId','"+this_.id+"_list')\" >"; 					
			htmlContent += "</td>";		   
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gdbh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gdbh)+"</td>"; 
		    htmlContent += "<td title='"+StringUtil.escapeStr(row.lyrwh)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.lyrwh)+"</td>";
		    htmlContent += "<td title='"+StringUtil.escapeStr(row.gdbt)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gdbt)+"</td>";
		    htmlContent += "<td title='"+formatUndefine(DicAndEnumUtil.getEnumName('workorderTypeEnum', StringUtil.escapeStr(row.gdlx)))+"'  style='text-align:left;vertical-align:middle;'>"+formatUndefine(DicAndEnumUtil.getEnumName('workorderTypeEnum', StringUtil.escapeStr(row.gdlx)))+"</td>";
		    htmlContent += "<td title='"+formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum', StringUtil.escapeStr(row.zt)))+"' style='text-align:left;vertical-align:middle;'>"+formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum', StringUtil.escapeStr(row.zt)))+"</td>";
		    htmlContent += "<td title='"+StringUtil.escapeStr(row.jhKsrq).substring(0, 10)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.jhKsrq).substring(0, 10)+"</td>";
		    htmlContent += "<td title='"+StringUtil.escapeStr(row.jhJsrq).substring(0, 10)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.jhJsrq).substring(0, 10)+"</td>";
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
		SelectUtil.checkRow($(e.target).parent().find("input[type='checkbox']"),'selectAllId',this.id+"_list");		
	},
	save: function(){
		
		if(this.param.callback && typeof(this.param.callback) == "function"){			
			var this_ = this;
			var data = [];
			$("#"+this_.id+"_list", $("#"+this_.id)).find("tr input:checked").each(function(){
				var index = $(this).attr("value");	
				data.push(this_.data[index]);
			});
			if(data==null||data==undefined||data.length==0){
				AlertUtil.showModalFooterMessage("请选择工单!",this_.id);
				return false;
			}
			AlertUtil.showConfirmMessage("确定要将选择的工单添加到工包中吗？", {
				callback : function() {
					this_.param.callback(data);
				}
			});		
		}
	}
};