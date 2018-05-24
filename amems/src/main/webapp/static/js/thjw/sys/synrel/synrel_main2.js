
/**
 * 系统同步关系
 */
var synrel_main = {
	id:'synrel_main',
	loaded: false,//是否已加载
	data:[],
	 
	//初始化参数
	initParam: function(){
		$("#"+this.id+"_keyword_search").val('');
	},
	//加载数据
	load : 	function(pageNumber, sortColumn, orderType) {
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
			url: basePath+"/sys/synrel/page",
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
					//signByKeyword($("#"+this_.id+" keyword_search").val(),[3,4], "#"+this_.id+"_list tr td");
				} else {
					$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>");
				}
				 
				this_.loaded = true;
				finishWait();
		    }
		}); 
	},	
	getParams : function() {
		var searchParam = {};
		 var paramsMap = {};
		 var keyword = $.trim($("#"+this.id+" keyword_search").val());
		 /*searchParam.dprtcode = this.param.dprtcode;
		 if(this.param.jx){
			 searchParam.jx = this.param.jx;
		 }*/
		 if('' != keyword){
			 searchParam.keyword = keyword;
		 }
		 
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	},
	appendContentHtml: function(list) {
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
		   
			htmlContent += "<td title='"+StringUtil.escapeStr(row.jx)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.jx)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gdbh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gdbh)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.gzzt)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gzzt)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.bb)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bb)+"</td>";  
		   
		   	htmlContent += "</tr>";
	   });
	   $("#"+this_.id+"_list").html(htmlContent);
	    
	},
	//搜索
	search: function() {
		this.load();
	},
	//确认
	rowonclick: function(e){
		SelectUtil.checkRow($(e.target).parent().find("input[type='checkbox']"),'selectAllId',this.id+"_list");
	},
	save: function(){ 
		
	}
};