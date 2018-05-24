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
		var searchParam = {};
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		
		searchParam.keyword = $.trim($("#"+this.id+"_username_search").val());
		searchParam.dprtcode = $.trim($("#dprtcode").val());
		searchParam.gyslb = $.trim($("#gyslb").val());

		searchParam.pagination = pagination;
		var this_ = this;
		startWait();
		   console.info(searchParam);
		AjaxUtil.ajax({
		   url:basePath+"/aerialmaterial/aerialmaterialfirm/queryAllPageList",
		   type : "post",
			data : JSON.stringify(searchParam),
			contentType : "application/json;charset=utf-8",
			dataType : "json",
		   success:function(data){
			   console.info(data);
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
				signByKeyword($.trim($("#"+this_.id+"_username_search").val()),[2,3,4,5,9,10],"#"+this_.id+"_list tr td");
			   } else {
				  $("#"+this_.id+"_list").empty();
				  $("#"+this_.id+"_pagination").empty();
				  $("#"+this_.id+"_list").append("<tr><td colspan=\"12\"  class='text-center'>暂无数据 No data.</td></tr>");
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
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.gysbm)+"' class='text-left'>";
		   htmlContent += "<a href='javascript:void(0);' onclick=\"viewDetail('"+row.id+"')\">"+StringUtil.escapeStr(row.gysbm)+"</a>";
		   htmlContent += "</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.gysmc)+"' class='text-left'>"+StringUtil.escapeStr(row.gysmc)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.pzh)+"' class='text-left'>"+StringUtil.escapeStr(row.pzh)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.zssm)+"' class='text-left'>"+StringUtil.escapeStr(row.zssm)+"</td>";
		   htmlContent += "<td class='text-center'>"+indexOfTime(row.sqkssj)+"</td>";
		   htmlContent += "<td class='text-center'>"+indexOfTime(row.sqjssj)+"</td>";
		   htmlContent += "<td class='text-right'>"+formatUndefine(row.syts)+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' class='text-left'>"+StringUtil.escapeStr(row.bz)+"</td>"; 
		   if(null != row.zdr){
			   htmlContent += "<td title='"+StringUtil.escapeStr(row.zdr.displayName)+"' class='text-left'>"+StringUtil.escapeStr(row.zdr.displayName)+"</td>";
		   }else{
			   htmlContent += "<td class='text-left'></td>";
		   }
		   htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.cjsj)+"</td>";
		   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
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