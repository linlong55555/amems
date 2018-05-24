outStockModel = {
    id : "outStockModel",
	paginationId:'outStockModel_Pagination',
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
		var url = "/material/outin/queryAllPageList"; 	
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
				   signByKeyword($("#"+this_.id+"_keyword").val(),[1,2,8],"#outStockModel_List tr td");
			   } else {
				  $("#"+this_.id+"_List").empty();
				  $("#"+this_.id+"_Pagination").empty();
				  $("#"+this_.id+"_List").append("<tr><td colspan=\"8\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:"outStockModel_table"});
	     }
	   }); 
	},
	gatherSearchParam : function(){
		var this_=this;
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#"+this_.id+"_keyword").val());
		 searchParam.dprtcode = this.param.dprtcode;
		 searchParam.fjzch = $.trim($("#"+this_.id+"_fjzch").val());
		 searchParam.shlx = $.trim($("#"+this_.id+"_shlx").val());
		 var paramsMap={};
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var this_=this;
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
			htmlContent += "<tr onclick='outStockModel.checkRow(this)'>";
			if(outStockModel.param.selected && outStockModel.param.selected == row.id){
				checked = "checked";
			}
			htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='radio' name='name' index='"+index+"' "+checked+" /></td>";  
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'><a href=\"javascript:outStockModel.findView('"+row.id+"')\">"+StringUtil.escapeStr(row.shdh)+"</a></td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('outboundTypeEnum',row.shlx)+"</td>";  
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('outboundOrderStatusEnum',row.zt)+"</td>";  
			htmlContent += "<td  style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.shrq).substring(0,10)+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.lydw)+"</td>";
			htmlContent += "<td  style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.fjzch)+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bz)+"</td>";  
			htmlContent += "</tr>";  
		    
		});
		$("#"+this_.id+"_List").empty();
		$("#"+this_.id+"_List").html(htmlContent);
		 TableUtil.addTitle("#"+this_.id+"_List tr td"); //加载td title
	},
	findView: function(id){
		window.open(basePath+"/material/outin/viewOut?id="+id);
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkRow: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	clearData : function(){
		$("#"+this.id+"_keyword").val('');
	},
	setData: function(){
		var this_ =this;
		var $checkedRadio = $("#"+this_.id+"_List").find("input:checked");
		var index = $checkedRadio.attr("index");	
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data = this.data;
			if(data.length > 0&&index!=undefined){			
				var chapter = this.data[index];
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