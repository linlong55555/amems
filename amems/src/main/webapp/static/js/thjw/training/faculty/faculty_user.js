var zzztMap = {
		1 : "在职",
		0 : "离职"
}

var wbbsMap = {
		1 : "内部",
		2 : "外部"
}

var xbMap = {
		1 : "男",
		2 : "女"
}

var faculty_user = {
	param: {
		selected:null,
		wbbs:null,
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
		   url:basePath+"/quality/maintenancepersonnel/queryPage",
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
						renderTo : 'user_pagination',
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
					}); 
				// 鏍囪鍏抽敭瀛�
				signByKeyword($.trim($("#u_realname_search").val()),[2],"#userlist tr td");
			   } else {
				  $("#userlist").empty();
				  $("#user_pagination").empty();
				  $("#userlist").append("<tr><td colspan=\"6\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#u_realname_search").val());
		 if(this.param.state){
			 searchParam.state = this.param.state;
		 } 
		 if(this.param.wbbs){
			 searchParam.wbbs = this.param.wbbs;
		 } 
		 searchParam.dprtcode = this.param.dprtcode;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  onclick='faculty_user.checkUser(this)'>";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='faculty_user.checkUser(this)'>";
		   }
		   if(faculty_user.param.selected && faculty_user.param.selected == row.id){
			   checked = "checked";
		   }
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='radio' name='name' index='"+index+"' "+checked+" /></td>";
		   htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.xm)+"' >"+StringUtil.escapeStr(row.xm)+"</td>";  
		   htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+wbbsMap[row.wbbs]+"' >"+wbbsMap[row.wbbs]+"</td>"; //归属
		   htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+xbMap[row.xb]+"' >"+xbMap[row.xb]+"</td>";//性别
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+(row.szdw?StringUtil.escapeStr(row.szdw):"")+"</td>";  
		   htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+zzztMap[row.zzzt]+"' >"+zzztMap[row.zzzt]+"</td>";//是否在职
		   htmlContent += + "</tr>";  
		    
	   });
	   $("#userlist", $("#userModal")).empty();
	   $("#userlist", $("#userModal")).html(htmlContent);
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkUser: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	emptied : function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
			this.param.callback({});
	}
	},
	setUser: function(){
		var $checkedRadio = $("#userlist", $("#userModal")).find("input:checked");
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