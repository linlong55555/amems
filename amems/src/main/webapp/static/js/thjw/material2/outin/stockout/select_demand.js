demandModal = {
		id : "demandModal",
	paginationId:'demandPagination',
	param: {
		parentWinId : '',
		selected:null,
		clearZjh: true,
		type : null,
		fjzch : null,
		zt : null,
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	data:[],
	show : function(param){
		$("#"+this.id).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		if(param.clearZjh == false){
			$("#zjh_btn_clear").hide();
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
		var this_ = this;
		var url = "/material/demand/queryAllDemandpprotectionPageList"; 	
		var searchParam = this.gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		searchParam.pagination = pagination;
		startWait();
		AjaxUtil.ajax({
			 url:basePath+url,
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
						extParams:{},
						goPage: function(a,b,c){
							this_.AjaxGetDatasWithSearch(a,b,c);
						}
					}); 
				   // 标记关键字
				   signByKeyword($("#keyword_demand").val(),[2],"#demandList tr td");
			   } else {
				  $("#demandList").empty();
				  $("#demandPagination").empty();
				  $("#demandList").append("<tr><td colspan=\"8\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:"open_win_chapter_basic_table"});
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#keyword_demand").val());
		 searchParam.dprtcode = this.param.dprtcode;
		 searchParam.fjzch = this.param.fjzch;
		 var paramsMap={};
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
			htmlContent += "<tr onclick='demandModal.checkRow(this)'>";
			if(demandModal.param.selected && demandModal.param.selected == row.id){
				checked = "checked";
			}
			htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='radio' name='name' index='"+index+"' "+checked+" /></td>";  
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bh)+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xqlb)+"</td>";  
			htmlContent += "<td  style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.jhsyrq).substring(0,10)+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.sjbjh)+"</td>";  
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.fjzch)+"</td>";
			var fxsj='';
			if(StringUtil.escapeStr(row.fxsj)!=''){
				fxsj=TimeUtil.convertToHour(StringUtil.escapeStr(row.fxsj), TimeUtil.Separator.COLON)||"";
			}
			htmlContent += "<td  style='text-align:right;vertical-align:middle;'>"+fxsj+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xqyy)+"</td>";  
			htmlContent += "</tr>";  
		    
		});
		$("#demandList", $("#demandModal")).empty();
		$("#demandList", $("#demandModal")).html(htmlContent);
		 TableUtil.addTitle("#demandList tr td"); //加载td title
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkRow: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	clearData : function(){
		$("#keyword_demand").val('');
	},
	setData: function(){
		var $checkedRadio = $("#demandList", $("#demandModal")).find("input:checked");
		var index = $checkedRadio.attr("index");	
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data = this.data;
			if(data.length > 0&&index!=undefined){			
				var chapter = this.data[index];
				chapter.lybh = formatUndefine(chapter.bh);
				chapter.lyid = formatUndefine(chapter.id);
				chapter.fjzch = formatUndefine(chapter.fjzch);
				this.param.callback(chapter);
			}else{
				
			}
		}
		this.close();
	},
	close : function(){
		$("#"+this.id).modal("hide");
	},
	clearZjh:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
				this.param.callback({});
		}
		this.close();
	}
}