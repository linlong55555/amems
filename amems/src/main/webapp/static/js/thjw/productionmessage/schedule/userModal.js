UserMultiModal = {
	id:'UserMultiModal',
	tbodyId:'um_userlist',
	paginationId:'um_pagination',
	viewUserId:'um_selectUser',
	isLoad:false,
	param: {
		checkUserList:null,
		clearUser:true,
		callback:function(){},
		state:1,
		localDepartment:true
	},
	data:[],
	show : function(param){
		//初始化时加载数据
		if(!this.isLoad){
			this.initMulti();
			this.isLoad = true;
		}
		this.clearData();//清空数据
		if(param){
			this.param.checkUserList=param;
			$.extend(true,this.param, param);
		}
		if(param.clearUser==false){
			$("#userModal_btn_clear").hide();
		}
		//回显数据
		if(this.param.checkUserList){
			this.returnViewData(this.param.checkUserList);
		}
		$("#"+this.id).modal("show");
		
		this.goPage(1,"auto","desc");
	},
	initMulti : function(){
		$("#"+this.viewUserId).tagsinput({
		  itemValue: 'id',
		  itemText: 'displayName'
		});
	},
	clearData : function(){
		$("#"+this.viewUserId).tagsinput('removeAll');
		$("#um_realname_search").val('');
	},
	returnViewData : function(checkUserList){
		var this_ = this;
		$.each(checkUserList, function(i, row){
			$("#"+this_.viewUserId).tagsinput('add', row);
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
		var searchParam = this_.gatherSearchParam();
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
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
				   this_.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : this_.paginationId,
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
					}); 
				   signByKeyword($.trim($("#um_realname_search").val()),[2],"#"+this_.tbodyId+" tr td");
			   } else {
				   this_.data = [];
				   $("#"+this_.tbodyId, $("#"+this_.id)).empty();
				   $("#"+this_.paginationId, $("#"+this_.id)).empty();
				   $("#"+this_.tbodyId, $("#"+this_.id)).append("<tr><td colspan=\"3\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.realname = $.trim($("#um_realname_search").val());
		 if(this.param.state){
			 searchParam.state = this.param.state;
		 }
		 if(this.param.localDepartment){
			 searchParam.jgdm = $("#dprtcode").val();
		 } 
		 return searchParam;
	},
	appendContentHtml: function(list){
		var this_ = this;
		var htmlContent = '';
		$.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  onclick='"+this_.id+".checkRow("+index+")'>";
		   } else {
			   htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='"+this_.id+".checkRow("+index+")'>";
		   }
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(index+1)+"</td>";	
		   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.username)+" "+StringUtil.escapeStr(row.realname)+"'>"+StringUtil.escapeStr(row.username)+" "+StringUtil.escapeStr(row.realname)+"</td>";  
		   htmlContent = htmlContent +"<td>"+(row.dprt_bm?formatUndefine(row.dprt_bm.dprtname):"")+"</td>";  
		   htmlContent = htmlContent + "</tr>";  
		    
	   });
	   $("#"+this_.tbodyId, $("#"+this_.id)).empty();
	   $("#"+this_.tbodyId, $("#"+this_.id)).html(htmlContent);
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkRow: function(index){
		var user=this.data[index];
		var username=user.username;
		var realname=user.realname;
		if(username.length>10){
			username=username.substring(0,10);
		}
		if(realname.length>0){
			realname=realname.substring(0,10);
		}
		user.realname=realname;
		user.username=username;
		user.displayName=username+" "+realname;
			$("#"+this.viewUserId).tagsinput('add', user);
		
	},
	setData: function(){
		
		var data = $("#"+this.viewUserId).tagsinput('items');
		if(data == null || data == ""){
			AlertUtil.showErrorMessage("请选择用户");
			return false;
		}
		
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback(data);
		}
		$("#"+this.id).modal("hide");
	},
	clearUser:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
				this.param.callback({});
		}
		$("#"+this.id).modal("hide");
	}
}