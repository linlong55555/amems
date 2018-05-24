var hclb=["其它","航材","设备","工具","危险品","低值易耗品"];
opeen_win_returnthepackage = {
	param: {
		selected:null,
		clearUser: true,
		callback:function(){}
	},
	show : function(param){
		$("#returnthepackage").modal("show");
		if(param){
			$.extend(this.param, param);
		}
		if(param.clearUser==false){
			$("#opeen_win_returnthepackage_clear").hide();
		}
		this.goPage(1,"auto","desc");
	},
 	goPage : function(pageNumber, sortType, sequence){
 		this.AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
	},	
	AjaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
		var searchParam = this.gatherSearchParam();
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		searchParam.wpdxid=$("#jddx").val();
		searchParam.gljb=$("#gljb").val();
		searchParam.hclx=$("#hclx").val();
		searchParam.dprtcode=$("#dprtcode").val();
		searchParam.keyword=$.trim($("#inventorykeyword_search").val());
		
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/aerialmaterial/outstock/givebackList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   if(data.total >0){
				   opeen_win_returnthepackage.appendContentHtml(data.rows);
					var page_ = new Pagination({
						renderTo : "pagination",
						data: data,
						sortColumn : sortColumn,
						orderType : orderType,
						controller: this_
					});
				   signByKeyword($("#inventorykeyword_search").val(),[0,1,2,3,4,5,6,7,8]);
			   } else {
				  $("#userlist1").empty();
				  $("#pagination").empty();
				  $("#userlist1").append("<tr><td colspan=\"9\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 return searchParam;
	},
	appendContentHtml: function(list){
		
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  onclick='opeen_win_returnthepackage.checkUser(this)'>";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='opeen_win_returnthepackage.checkUser(this)'>";
		   }
		   if(opeen_win_returnthepackage.param.selected && opeen_win_returnthepackage.param.selected == row.id){			   
			   checked = "checked";
		   }
		   if((index+1)==1){
			   htmlContent = htmlContent + "<td class='text-center'><input name='1' ghid='"+row.id+"' bjh='"+StringUtil.escapeStr(row.bjh)+"'  dghsl='"+formatUndefine(row.dghsl)+"' type='radio'"+checked+" /></td>";
		   }else{
			   htmlContent = htmlContent + "<td class='text-center'><input name='1' ghid='"+row.id+"' bjh='"+StringUtil.escapeStr(row.bjh)+"'  dghsl='"+formatUndefine(row.dghsl)+"' type='radio'"+checked+" /></td>";
		   }
		   htmlContent = htmlContent + "<td>"+formatUndefine(index+1)+"</td>"; 
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.bjh)+"'><input type='hidden' name='bjh' value='"+row.bjh+"'><span name='keyword'>"+StringUtil.escapeStr(row.bjh)+"</span></td>";  
		   htmlContent = htmlContent +"<td class='text-left' title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
		   htmlContent = htmlContent +"<td class='text-left' title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>";  
		   htmlContent = htmlContent +"<td class='text-left' title='"+StringUtil.escapeStr(row.cjjh)+"'>"+StringUtil.escapeStr(row.cjjh)+"</td>";  
		   htmlContent = htmlContent +"<td>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
		   htmlContent = htmlContent +"<td>"+StringUtil.escapeStr(row.jldw)+"</td>";  
		   htmlContent = htmlContent +"<td><input type='hidden' name='dghsl' value='"+formatUndefine(row.dghsl)+"'>"+formatUndefine(row.dghsl)+"</td>";  
		   htmlContent = htmlContent + "</tr>";  
		    
	   });
	   $("#userlist1", $("#returnthepackage")).empty();
	   $("#userlist1", $("#returnthepackage")).html(htmlContent);
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkUser: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	setUser: function(){
		var $checkedRadio = $("#userlist1", $("#returnthepackage")).find("input:checked");
		var ghid = $checkedRadio.attr("ghid");
		var bjh = $checkedRadio.attr("bjh");
		var dghsl = $checkedRadio.attr("dghsl");
		
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback({
				ghid: ghid,
				bjh: bjh,
				dghsl: dghsl
			});
		}
	},
	clearUser:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
				this.param.callback({});
		}
		$("#returnthepackage").modal("hide");
	}
}