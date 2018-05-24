userModal = {
	param: {
		selected:null,
		clearUser: true,
		callback:function(){},
		state:1,
		page:"",//调用页面
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	show : function(param){
		$("#userModal").modal("show");
		if(param){
			$.extend(this.param, param);
		}
		if(param.clearUser==false){
			$("#userModal_btn_clear").hide();
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
		var searchParam = this.gatherSearchParam();
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		var this_ = this;
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/sys/user/queryUserList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   if(data.total >0){
				   this_.data = data.rows;
				   userModal.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : 'user_pagination',
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
					}); 
				// 鏍囪鍏抽敭瀛�
				signByKeyword($.trim($("#u_realname_search1").val()),[2],"#userlist1 tr td");
			   } else {
				  $("#userlist1").empty();
				  $("#user_pagination").empty();
				  $("#userlist1").append("<tr><td colspan=\"3\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.realname = $.trim($("#u_realname_search1").val());
		 if(this.param.state){
			 searchParam.state = this.param.state;
		 } 
		 if(this.param.page){
			 var paramsMap = {};
			 paramsMap.page = this.param.page;
			 searchParam.paramsMap = paramsMap;
		 } 
		 searchParam.jgdm = this.param.dprtcode;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  onclick='userModal.checkUser(this)'>";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='userModal.checkUser(this)'>";
		   }
		   if(userModal.param.selected && userModal.param.selected == row.id){
			   checked = "checked";
		   }
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='radio' name='name' index='"+index+"' "+checked+" /></td>";
		   htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.username)+" "+StringUtil.escapeStr(row.realname)+"' >"+StringUtil.escapeStr(row.username)+" "+StringUtil.escapeStr(row.realname)+"</td>";  
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+(row.dprt_bm?StringUtil.escapeStr(row.dprt_bm.dprtname):"")+"</td>";  
		   htmlContent += + "</tr>";  
		    
	   });
	   $("#userlist1", $("#userModal")).empty();
	   $("#userlist1", $("#userModal")).html(htmlContent);
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkUser: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	setUser: function(){
		var $checkedRadio = $("#userlist1", $("#userModal")).find("input:checked");
		var index = $checkedRadio.attr("index");	
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data = this.data;
			if(data.length > 0){
				this.param.callback(this.data[index]);
			}else{
				this.param.callback({});
			}
		}
		$("#userModal").modal("hide");
	},
	clearUser:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
				this.param.callback({});
		}
		$("#userModal").modal("hide");
	}
}