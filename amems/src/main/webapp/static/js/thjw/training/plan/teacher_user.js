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

var lecturer_user = {
	param: {
		selected:null,
		clearUser: true,
		callback:function(){},
		state:1,
		dprtcode:userJgdm,//默认登录人当前机构代码
		sqksrq:null,
		kcid:null
	},
	show : function(param){
		$("#lectureruserModal").modal("show");
		if(param){
			$.extend(this.param, param);
		}
		if(param.clearUser==false){
			$("#lectureruserModal_btn_clear").hide();
		}
		this.clearData();//娓呯┖鏁版嵁
		this.goPage(1,"auto","desc");
	},
	data:[],
	clearData : function(){
		$("#sqrqcheck").attr("checked", "checked");
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
		   url:basePath+"/training/faculty/query/openlist",
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
						renderTo : 'teacheruser_pagination',
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
					}); 
				signByKeyword($.trim($("#u_realname_search").val()),[2],"#teacheruserlist tr td");
			   } else {
				  $("#teacheruserlist").empty();
				  $("#teacheruser_pagination").empty();
				  $("#teacheruserlist").append("<tr><td colspan=\"8\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#u_realname_search").val());
		 searchParam.dprtcode = this.param.dprtcode;
		 var paramsMap = {};
			 paramsMap.sqksrq = this.param.sqksrq;	//计划开始日期
			 paramsMap.kcid = this.param.kcid;		//课程id
		 if($("#sqrqcheck").is(":checked")){
			 paramsMap.sqrqcheck = "checked";
		 }
		 searchParam.paramsMap =paramsMap;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  onclick='lecturer_user.checkUser(this)'>";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='lecturer_user.checkUser(this)'>";
		   }
		   if(lecturer_user.param.selected && lecturer_user.param.selected == row.id){
			   checked = "checked";
		   }
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='radio' name='name' index='"+index+"' "+checked+" /></td>";
		   htmlContent += "<td style='text-align:left;vertical-align:middle;' >"+ StringUtil.escapeStr(formatUndefine(row.rybh))+ "</td>";
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+ StringUtil.escapeStr(formatUndefine(row.xm))+ "</td>";
		   htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+ (row.wbbs == 1 ? '内部' : '外部') + "</td>";
		   htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+ (row.xb == 1 ? '男' : '女') + "</td>";
		   htmlContent += "<td style='text-align:left;vertical-align:middle;' >"+ StringUtil.escapeStr(formatUndefine(row.lxdh))+ "</td>";
		   htmlContent += "<td style='text-align:left;vertical-align:middle;' >"+ StringUtil.escapeStr(formatUndefine(row.yxdz))+ "</td>";
		   htmlContent += "<td style='text-align:left;vertical-align:middle;' >"+ StringUtil.escapeStr(formatUndefine(row.jtcy))+ "</td>";
		   htmlContent += + "</tr>";  
		    
	   });
	   $("#teacheruserlist", $("#lectureruserModal")).empty();
	   $("#teacheruserlist", $("#lectureruserModal")).html(htmlContent);
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkUser: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	setUser: function(){
		var $checkedRadio = $("#teacheruserlist", $("#lectureruserModal")).find("input:checked");
		var index = $checkedRadio.attr("index");	
		if(this.param.callback && typeof(this.param.callback) == "function"){
			var data = this.data;
			if(data.length > 0){
				this.param.callback(this.data[index]);
			}else{
				this.param.callback({});
			}
		}
		$("#lectureruserModal").modal("hide");
	},
	clearUser:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
				this.param.callback({});
		}
		$("#lectureruserModal").modal("hide");
	}
}