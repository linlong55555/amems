business_main = {
	tableId:'business_table_list',
	tbodyId:'business_tbody_list',
	paginationId:'business_Pagination',
	id:'business_main',
	param: {
		ids:[],
		callback:function(){},
		dprtcode:userJgdm,//默认登录人当前机构代码
		id:null,
	},
	data:[],
	show : function(param){
		$("#business_main").modal("show");
		if(param){
			$.extend(this.param, param);
		}
		this.clearData();
		this.goPage(1,"auto","desc");
	},
 	goPage : function(pageNumber, sortType, sequence){
 		this.AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
	},	
	load: function(pageNumber,sortType,sequence){
		this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	},
	AjaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
		var searchParam = this.gatherSearchParam();
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		startWait();
		var this_ = this;
		AjaxUtil.ajax({
		   url:basePath+"/training/business/businessList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   if(data.total >0){
				   this_.data = data.rows;
				   this_.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : "business_Pagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
					}); 
				   // 标记关键字
				   signByKeyword($("#keyword_business_search").val(),[2,3,4],"#business_tbody_list tr td");
			   } else {
				  $("#business_tbody_list").empty();
				  $("#business_Pagination").empty();
				  $("#business_tbody_list").append("<tr><td colspan=\"4\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   //new Sticky({tableId:"open_win_chapter_basic_table"});
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var this_ = this;
		 var searchParam = {};
		 var paramsMap = {};
		 searchParam.keyword = $.trim($("#keyword_business_search").val());
		 searchParam.dprtcode = this_.param.dprtcode;
		 if(this_.param.ids != null && this_.param.ids.length > 0){
			 paramsMap.ids = this_.param.ids;
		 }
		 
		 searchParam.paramsMap=paramsMap;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			htmlContent += "<tr index = "+index+" onclick=SelectUtil.clickRow("+index+",'business_tbody_list','business_row')>";
			htmlContent += "<td class='fixed-column' style='text-align:center;vertical-align:middle;'><input value='"+row.id+"' type='checkbox' name='business_row' index="+index+"  onclick=selectworkcard(event,"+index+",'business_table_list','business_row',this) /></td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.dgbh)+"'>"+StringUtil.escapeStr(row.dgbh)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.dgmc)+"'>"+StringUtil.escapeStr(row.dgmc)+"</td>";
			htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.bz)+"'>"+StringUtil.escapeStr(row.bz)+"</td>";
			htmlContent += "</tr>"; 
		    
		});
		$("#business_tbody_list", $("#business_main")).empty();
		$("#business_tbody_list", $("#business_main")).html(htmlContent);
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkRow: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	clearData : function(){
		$("#keyword_business_search").val('');
	},
	setData: function(){
		var this_ = this;
		var data = this.data;
		var list = [];
		$("#"+this_.tbodyId).find("tr input:checked").each(function(){
			var index = $(this).attr("index");
			list.push(data[index]);
		});
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback(list);
		}
	}
	
};
