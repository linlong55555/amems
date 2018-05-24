MaterialRepairModal = {
	paginationId:'materialRepairPagination',
	param: {
		selected:null,
		dprtcode:userJgdm,//默认登录人当前机构代码
		callback:function(){}
	},
	data:[],
	show : function(param){
		$("#MaterialRepairModal").modal("show");
		if(param){
			$.extend(this.param, param);
		}
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
		   url:basePath+"/aerialmaterial/stock/queryRepairStockList",
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
						renderTo : this_.paginationId,
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
					}); 
				   // 标记关键字
				   signByKeyword($("#keyword_material_repair_search").val(),[2,3,4,5,6],"#materialRepairList tr td");
			   } else {
				  $("#materialRepairList").empty();
				  $("#materialRepairPagination").empty();
				  $("#materialRepairList").append("<tr><td colspan=\"9\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#keyword_material_repair_search").val());
		 searchParam.dprtcode = this.param.dprtcode;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
			if (index % 2 == 0) {
				htmlContent += "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  onclick='MaterialRepairModal.checkRow(this)'>";
			} else {
				htmlContent += "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='MaterialRepairModal.checkRow(this)'>";
			}
			if(MaterialRepairModal.param.selected && MaterialRepairModal.param.selected == row.id){
				checked = "checked";
			}
			htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='radio' name='name' index='"+index+"' "+checked+" /></td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.hCMainData?row.hCMainData.bjh:"")+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.hCMainData?row.hCMainData.bjh:"")+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.hCMainData?row.hCMainData.ywms:"")+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.hCMainData?row.hCMainData.ywms:"")+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.hCMainData?row.hCMainData.zwms:"")+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.hCMainData?row.hCMainData.zwms:"")+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.hCMainData?row.hCMainData.cjjh:"")+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.hCMainData?row.hCMainData.cjjh:"")+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.sn)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.sn)+"</td>";
			
			var zjhstr = '';
			if(row.hCMainData != null && row.hCMainData.fixChapter != null){
				zjhstr = formatUndefine(row.hCMainData.fixChapter.zjh)+" "+formatUndefine(row.hCMainData.fixChapter.zwms);
			}
			htmlContent += "<td title='"+zjhstr+"' style='text-align:left;vertical-align:middle;'>";
			htmlContent += zjhstr;
			htmlContent += "</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',(row.hCMainData?row.hCMainData.hclx:"")) +"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.hCMainData?row.hCMainData.jldw:"") +"</td>";
			htmlContent += "</tr>";  
		    
		});
		$("#materialRepairList", $("#MaterialRepairModal")).empty();
		$("#materialRepairList", $("#MaterialRepairModal")).html(htmlContent);
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkRow: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	setData: function(){
		var $checkedRadio = $("#materialRepairList", $("#MaterialRepairModal")).find("input:checked");
		var index = $checkedRadio.attr("index");	
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data = this.data;
			if(data.length > 0){
				this.param.callback(this.data[index]);
			}else{
				this.param.callback({});
			}
		}
	}
	
}