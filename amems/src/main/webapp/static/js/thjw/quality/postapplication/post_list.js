var post_list = {
	param: {
		selected:null,
		clearUser: true,
		callback:function(){},
		state:1,
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	show : function(param){
		
		if(param){
			$.extend(this.param, param);
		}
		if(param.clearUser==false){
			$("#Post_Modal_btn_clear").hide();
		}
		this.clearData();
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
		var searchParam = this.gatherSearchParam();
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		var this_ = this;
		startWait();
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
						renderTo : 'post_pagination',
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
					}); 
				signByKeyword($.trim($("#u_realname_search1").val()),[2,3,4],"#postlist tr td");
			   } else {
				  $("#postlist").empty();
				  $("#user_pagination").empty();
				  $("#postlist").append("<tr><td colspan=\"4\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#u_realname_search1").val());
		 searchParam.dprtcode = this.param.dprtcode;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  onclick='post_list.checkUser(this)'>";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='post_list.checkUser(this)'>";
		   }
		   if(post_list.param.selected && post_list.param.selected == row.id){
			   checked = "checked";
		   }
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='radio' name='name' index='"+index+"' "+checked+" /></td>";
		   htmlContent += "<td style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.dgbh)+"</td>";  
		   htmlContent += "<td style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.dgmc)+"</td>";  
		   htmlContent += "<td style='text-align:left;vertical-align:middle;' >"+StringUtil.escapeStr(row.bz)+"</td>";  
		   htmlContent += + "</tr>";  
		    
	   });
	   $("#postlist", $("#Post_Modal")).empty();
	   $("#postlist", $("#Post_Modal")).html(htmlContent);
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkUser: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	setUser: function(){
		var $checkedRadio = $("#postlist", $("#Post_Modal")).find("input:checked");
		var index = $checkedRadio.attr("index");	
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data = this.data;
			if(data.length > 0){
				this.param.callback(this.data[index]);
			}else{
				this.param.callback({});
			}
		}
		$("#Post_Modal").modal("hide");
	},
	clearUser:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
				this.param.callback({});
		}
		$("#Post_Modal").modal("hide");
	}
}