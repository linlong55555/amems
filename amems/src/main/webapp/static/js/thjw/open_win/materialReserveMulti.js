MaterialReserveModal = {
	alertId:'MaterialReserveModal',
	tbodyId:'materialReserveList',
	paginationId:'materialReservePagination',
	param: {
		existsIdList:null,
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	data:[],
	show : function(param){
		$("#"+this.alertId).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		this.initParam();
		this.goPage(1,"auto","desc");
	},
	initParam : function(){
		$("#hclx_search").val('');
		$("#keyword_material_reserve_search").val('');
	},
	changeType : function(){
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
		   url:basePath+"/material/material/queryWinAllPageList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   SelectUtil.selectNode('selectAllId',this.tbodyId);
			   finishWait();
			   if(data.total >0){
				   this_.data = data.rows;
				   this_.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : this_.paginationId,
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
					}); 
				   // 标记关键字
				   signByKeyword($("#keyword_material_reserve_search").val(),[2,3,4,5],"#"+this_.tbodyId+" tr td");
			   } else {
				   $("#"+this_.tbodyId, $("#"+this_.alertId)).empty();
				   $("#"+this_.paginationId, $("#"+this_.alertId)).empty();
				   $("#"+this_.tbodyId, $("#"+this_.alertId)).append("<tr><td colspan=\"11\" class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 var paramsMap = {};
		 var hclx = $.trim($("#hclx_search").val());
		 var keyword = $.trim($("#keyword_material_reserve_search").val());
		 searchParam.dprtcode = this.param.dprtcode;
		 if('' != hclx){
			 searchParam.hclx = hclx;
		 }
		 if('' != keyword){
			 searchParam.keyword = keyword;
		 }
		 var existsIdList = this.param.existsIdList;
		 if(existsIdList != '' && existsIdList.length > 0){
			 paramsMap.idList = existsIdList;
		 }
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		var this_ = this;
		$.each(list,function(index,row){
			if (index % 2 == 0) {
				htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='"+this_.alertId+".clickRow(this)' >";
			} else {
				htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='"+this_.alertId+".clickRow(this)' >";
		  	}
		   
			htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='checkbox' index='"+index+"' onclick=\"SelectUtil.checkRow(this,'selectAllId','materialReserveList')\" /></td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.bjh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bjh)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.ywms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.ywms)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.zwms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.zwms)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.cjjh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.cjjh)+"</td>";  
			if('00000' == StringUtil.escapeStr(row.xh)){
				htmlContent += "<td style='text-align:left;vertical-align:middle;'>通用Currency</td>";
			}else{
				htmlContent += "<td title='"+StringUtil.escapeStr(row.xh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xh)+"</td>";
			}
			htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('supervisoryLevelEnum',row.gljb) +"</td>";
			htmlContent += "<td title='"+formatUndefine((row.fixChapter?(StringUtil.escapeStr(row.fixChapter.zjh)):'')+" "+(row.fixChapter?(StringUtil.escapeStr(row.fixChapter.zwms)):''))+"' style='text-align:left;vertical-align:middle;'>"+(row.fixChapter?(StringUtil.escapeStr(row.fixChapter.zjh)):'')+" "+(row.fixChapter?(StringUtil.escapeStr(row.fixChapter.zwms)):'')+"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx) +"</td>";
			htmlContent += "<td style='text-align:right;vertical-align:middle;'>"+(row.kcsl?row.kcsl:0)+"</td>"; 
			htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.jldw) +"</td>";
			
			htmlContent += "</tr>";  
		});
		$("#"+this_.tbodyId, $("#"+this_.alertId)).empty();
		$("#"+this_.tbodyId, $("#"+this_.alertId)).html(htmlContent);
		SelectUtil.selectNode('selectAllId',this.tbodyId);
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	clickRow: function(row){
		SelectUtil.checkRow($(row).find("input[type='checkbox']"),'selectAllId','materialReserveList');
	},
	setData: function(){
		var this_ = this;
		var data = [];
		$("#"+this_.tbodyId, $("#"+this_.alertId)).find("tr input:checked").each(function(){
			var index = $(this).attr("index");	
			data.push(this_.data[index]);
			
		});
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback(data);
		}
	}
}