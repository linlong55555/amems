OpenotherStockModal = {
	alertId:'OpenotherStockModal',
	tbodyId:'otherStockAdd',
	paginationId:'otherStockAddPagination',
	param: {
		existsIdList:null,
		htlx : 1,
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
		if(this.param.htlx == 1){
			$("#materialHead tr th:eq(1)", $("#"+this.alertId)).show();
			$("#materialHead tr th:eq(8)", $("#"+this.alertId)).show();
			$("#materialHead tr th:eq(2)", $("#"+this.alertId)).hide();
			$("#materialHead tr th:eq(7)", $("#"+this.alertId)).hide();
		}else{
			$("#materialHead tr th:eq(1)", $("#"+this.alertId)).hide();
			$("#materialHead tr th:eq(8)", $("#"+this.alertId)).hide();
			$("#materialHead tr th:eq(2)", $("#"+this.alertId)).show();
			$("#materialHead tr th:eq(7)", $("#"+this.alertId)).show();
		}
		$("#yqqx_search").val('');
		$("#keyword_material_contract_search").val('');
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",
		function() {
			$(this).next().focus();
		});
	},
 	goPage : function(pageNumber, sortType, sequence){
 		this.AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
	},	
	load: function(pageNumber,sortType,sequence){
		this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	},
	AjaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
		var this_ = this;
		var url = basePath+"/aerialmaterial/stock/otherstockList";
		if(this_.param.htlx == 2){
			url = basePath+"/aerialmaterial/materialrepair/queryRepairMaterialPageList";
		}
		var searchParam = this.gatherSearchParam();
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		startWait();
		AjaxUtil.ajax({
		   url:url,
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
				   signByKeyword($("#keyword_material_contract_search").val(),[2,3,4,5,6],"#"+this_.tbodyId+" tr td");
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
		 var yqqx = $.trim($("#yqqx_search").val());
		 var keyword = $.trim($("#keyword_material_contract_search").val());
		 searchParam.dprtcode = this.param.dprtcode;
		 if('' != keyword){
			 searchParam.keyword = keyword;
		 }
		 if(null != yqqx && "" != yqqx){
			 var yqqxBegin = yqqx.substring(0,10);
			 var yqqxEnd = yqqx.substring(13,23);
			 paramsMap.yqqxBegin = yqqxBegin;
			 paramsMap.yqqxEnd = yqqxEnd;
		 }
		 var existsIdList = this.param.existsIdList;
		 if(existsIdList != '' && existsIdList.length > 0){
			 searchParam.idList = existsIdList;
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
		   
			htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='checkbox' index='"+index+"' onclick=\"SelectUtil.checkRow(this,'selectAllId','"+this.tbodyId+"')\" /></td>";
			
			htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+formatUndefine(row.SQDH)+"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.BJH)+"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.YWMS)+"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.ZWMS)+"</td>";
			htmlContent += "<td style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.CJJH)+"</td>";  
			if(this_.param.htlx == 1){
				htmlContent += "<td style='text-align:right;vertical-align:middle;' >"+formatUndefine(row.SL)+"</td>";
			}else{
				htmlContent += "<td style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.SN)+"</td>";
			}
			htmlContent += "<td style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.USERNAME)+" "+StringUtil.escapeStr(row.REALNAME)+"</td>"; 
			htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+formatUndefine(row.SQSJ)+"</td>"; 
			htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+indexOfTime(formatUndefine(row.YQQX))+"</td>";
		   
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
		SelectUtil.checkRow($(row).find("input[type='checkbox']"),'selectAllId',this.tbodyId);
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