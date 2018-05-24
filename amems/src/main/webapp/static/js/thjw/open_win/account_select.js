open_win_account_select = {
	id:"open_win_account_select",
	param: {
		selected:null,
		callback:function(){},
	},
	show : function(param){
		$("#"+this.id+"_modal").modal("show");
		if(param){
			$.extend(this.param, param);
		}
		this.clearData();//娓呯┖鏁版嵁
		this.goPage(1,"auto","desc");
	},
	data:[],
	clearData : function(){
		$("#u_realname_search").val('');
	},
 	goPage : function(pageNumber, sortType, sequence){
 		this.AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
	},	
	load: function(pageNumber,sortType,sequence){
		this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	},
	AjaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
		var searchParam = {};
		searchParam.paramsMap = {};
		searchParam.paramsMap.keyword = $.trim($("#"+this.id+"_username_search").val());
		searchParam.paramsMap.jgdm = $.trim($("#jgdm").val());
		searchParam["pagination.page"] = pageNumber;
		searchParam["pagination.sort"] = sortType;
		searchParam["pagination.order"] = sequence;
		searchParam["pagination.rows"] = 10;
		var this_ = this;
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/sys/account/unbound",
		   type: "post",
		   dataType:"json",
		   data:searchParam,
		   success:function(data){
			   finishWait();
			   if(data.total >0){
				   this_.data = data.rows;
				   this_.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : this_.id+'_pagination',
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
					}); 
				signByKeyword($.trim($("#"+this_.id+"_username_search").val()),[3],"#"+this_.id+"_list tr td");
			   } else {
				  $("#"+this_.id+"_list").empty();
				  $("#"+this_.id+"_pagination").empty();
				  $("#"+this_.id+"_list").append("<tr><td colspan=\"4\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	appendContentHtml: function(list){
		var this_ = this;
		var htmlContent = '';
		$.each(list,function(index,row){
		   htmlContent = htmlContent + "<tr onclick='"+this_.id+".checkUser(this)'>";
		   var checked = "";
		   if(this_.param.selected && this_.param.selected == row.id){
			   checked = "checked";
		   }
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='radio' name='name' index='"+index+"' "+checked+" /></td>";
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(index+1)+"</td>";  
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.username)+" "+StringUtil.escapeStr(row.realname)+"</td>";  
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.jgdm)+"</td>";
		   htmlContent += + "</tr>";  
		    
	   });
	   $("#"+this_.id+"_list").empty();
	   $("#"+this_.id+"_list").html(htmlContent);
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkUser: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	save: function(){
		var $checkedRadio = $("#"+this.id+"_modal").find("input:checked");
		var index = $checkedRadio.attr("index");	
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data = this.data;
			if(data.length > 0){
				this.param.callback(this.data[index]);
			}else{
				this.param.callback({});
			}
		}
		$("#"+this.id+"_modal").modal("hide");
	}
}