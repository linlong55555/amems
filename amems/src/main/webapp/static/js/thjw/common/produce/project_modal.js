project_modal = {
	id:'project_modal',
	param: {
		selected:null,
		wbbs:0,
		khid:null,
		fjjx:null,
		fjzch:null,
		fjxlh:null,
		clearproject: true,//清空按钮
		callback:function(){},
		dprtcode:userJgdm,//默认登录人当前机构代码
		id:null,
	},
	data:[],
	init : function(param){
		var this_ = this;
		AlertUtil.hideModalFooterMessage();
		$("#"+this.id).modal("show");
		$("#"+this.id+"_search").val("");
		if(param){
			$.extend(this.param, param);
		}
		//不显示清空按钮
		if(this.param.clearproject==false){
			$("#"+this.id+"_clearbtn").hide();
		}else{
			$("#"+this.id+"_clearbtn").show();
		}
		this_.goPage(1,"auto","desc");
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
		   url:basePath+"/baseinfo/project/getProjectsList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   if(data.total != null &&  data.total > 0){
				   this_.data = data.rows;
				   this_.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : this_.id+"_pagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						extParams:{},
					    goPage: function(a,b,c){
					    	this_.AjaxGetDatasWithSearch(a,b,c);
						}
					}); 
				   // 标记关键字
				   signByKeyword($("#"+this_.id+"_keyword_search").val(),[2,3],"#"+this_.id+"_list tr td");
			   } else {
				  $("#"+this_.id+"_list").empty();
				  $("#"+this_.id+"_pagination").empty();
				  $("#"+this_.id+"_list").append("<tr><td colspan=\"3\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#"+this.id+"_keyword_search").val());
		 searchParam.dprtcode = this.param.dprtcode;
		 searchParam.khid=this.param.khid;
		 searchParam.wbbs=this.param.wbbs;
		 searchParam.fjjx=this.param.fjjx;
		 searchParam.fjzch=this.param.fjzch;
		 searchParam.fjxlh=this.param.fjxlh;	 
		 return searchParam;
	},
	appendContentHtml: function(list){
		var this_=this;
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
			if(this_.param.selected && this_.param.selected == row.id){
				checked = "checked";
			}
			htmlContent += "<tr onclick='customer_modal.checkRow(this)'>";
			htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='radio' name='name' index='"+index+"' "+checked+" /></td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.xmbm)+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.xmbm)+"</td>";
			htmlContent += "<td title='"+StringUtil.escapeStr(row.xmmc)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xmmc)+"</td>";  
			htmlContent += "</tr>";  
		    
		});
		$("#"+this_.id+"_list").empty();
		$("#"+this_.id+"_list").html(htmlContent);
	},
	search: function(){
		var this_ = this;
		this_.goPage(1,"auto","desc");
	},
	checkRow: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	clearData:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
			
		this.param.callback({});
		}
		$("#"+this.id).modal("hide");
	},
	setData: function(){
		var this_=this;
		var $checkedRadio = $("#"+this_.id+"_list", $("#"+this.id)).find("input:checked");
		var index = $checkedRadio.attr("index");	
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data = this.data;
			if(data.length > 0 && index != undefined){			
				var manufacturer = this.data[index];
				this.param.callback(manufacturer);
			}else{
				
			}
		}
		$("#"+this.id).modal("hide");
	}
}
