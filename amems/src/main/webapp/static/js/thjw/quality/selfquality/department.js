var type=[,"组织机构","部门机构"];
departmentModal = {
	param: {
		selected:null,
		callback:function(){},
		dprtcode:dprtcode//默认登录人当前机构代码
	},
	show : function(param){
		console.info(1);
		$("#departmentModal").modal("show");
		if(param){
			$.extend(this.param, param);
		}
		this.clearData();//娓呯┖鏁版嵁
		this.goPage(1,"auto","desc");
	},
	data:[],
	clearData : function(){
		$("#dprtname").val('');
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
		   url:basePath+"/sys/message/querydprtnameList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   console.info(data);
			   finishWait();
			   if(data.total >0){
				   this_.data = data.rows;
				   departmentModal.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : 'um_pagination',
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
					}); 
				// 鏍囪鍏抽敭瀛�
				signByKeyword($.trim($("#dprtname").val()),[2],"#um_dprtmentlist tr td");
			   } else {
				  $("#um_dprtmentlist").empty();
				  $("#um_pagination").empty();
				  $("#um_dprtmentlist").append("<tr><td colspan=\"3\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.dprtname = $.trim($("#dprtname").val());
		 searchParam.dprttype='2';
		 searchParam.id = this.param.dprtcode;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  onclick='departmentModal.checkUser(this)'>";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='departmentModal.checkUser(this)'>";
		   }
		   if(departmentModal.param.selected && departmentModal.param.selected == row.id){
			   checked = "checked";
		   }
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='radio' name='name' index='"+index+"' "+checked+" /></td>";
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.dprtname)+"</td>";  
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(type[row.dprttype])+"</td>";  
		   htmlContent += + "</tr>";  
		    
	   });
	   $("#um_dprtmentlist", $("#departmentModal")).empty();
	   $("#um_dprtmentlist", $("#departmentModal")).html(htmlContent);
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkUser: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	setUser: function(){
		var $checkedRadio = $("#um_dprtmentlist", $("#departmentModal")).find("input:checked");
		var index = $checkedRadio.attr("index");	
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data = this.data;
			if(data.length > 0){
				this.param.callback(this.data[index]);
			}else{
				this.param.callback({});
			}
		}
		$("#departmentModal").modal("hide");
	},
	clearUser:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
				this.param.callback({});
		}
		$("#departmentModal").modal("hide");
	}
}