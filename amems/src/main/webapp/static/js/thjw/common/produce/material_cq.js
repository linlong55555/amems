cqModal = {
	param: {
		selected:null,
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	show : function(param){
		$("#alertModalCq").modal("show");
		if(param){
			$.extend(this.param, param);
		}
		this.clearData();
		this.goPage(1,"auto","desc");
	},
	data:[],
	clearData : function(){
		$("#keyword_material_search").val('');
	},
 	goPage : function(pageNumber, sortType, sequence){
 		this.AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
	},	
	load: function(pageNumber,sortType,sequence){
		this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	},
	AjaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
		var searchParam ={};
		var paramsMap={};
		paramsMap.keyword=$.trim($("#keyword_material_search").val());
		searchParam.dprtcode=this.param.dprtcode;
		searchParam.paramsMap =paramsMap
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		var this_ = this;
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/basic/propertyright/queryAll",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   if(data.total >0){
				   this_.data = data.rows;
				   cqModal.appendContentHtml(data.rows);
				   new Pagination({
						renderTo : 'cqpagination',
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						controller: this_
					}); 
				signByKeyword($.trim($("#keyword_material_search").val()),[2],"#cqlist tr td");
			   } else {
				  $("#cqlist").empty();
				  $("#cqpagination").empty();
				  $("#cqlist").append("<tr><td colspan=\"5\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     }
	   }); 
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		$.each(list,function(index,row){
			var checked = "";
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  onclick='cqModal.checkCq(this)'>";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='cqModal.checkCq(this)'>";
		   }
		   if(cqModal.param.selected && cqModal.param.selected == row.id){
			   checked = "checked";
		   }
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'><input type='hidden' id='id' /><input type='radio' value='"+row.id+"' cqbh='"+row.cqbh+"' fjzch='"+row.fjzch+"' bz='"+row.bz+"' name='name' index='"+index+"' "+checked+" /></td>";
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.cqbh)+"</td>";  
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.fjzch)+"</td>";  
		   htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.customer?row.customer.khbm +" "+ row.customer.khmc:'')+"</td>";  
		   htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bz)+"</td>"; 
		   
		   //htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.whr.realname)+"</td>";  
		   //htmlContent += "<td style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.jg_dprt.dprtname)+"</td>";  
		   //htmlContent += + "</tr>";  
		    
	   });
	   $("#cqlist", $("#alertModalCq")).empty();
	   $("#cqlist", $("#alertModalCq")).html(htmlContent);
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	checkCq: function(objradio){
		$(objradio).find("input[type='radio']").attr("checked",true);
	},
	setCq: function(){
		if($("input[type='radio']:checked").size()== 0){
			AlertUtil.showErrorMessage("请选择产权号");
			return;
		}
		if(this.param.callback && typeof(this.param.callback) == "function"){
			if($("#cqlist input[type='radio']:checked").size()> 0){
				var checkedObj=$("#cqlist input[type='radio']:checked");
				var returnValue={};
				returnValue.id=$(checkedObj).val();
				returnValue.cqbh=$(checkedObj).attr("cqbh");
				returnValue.fjzch=$(checkedObj).attr("fjzch");
				returnValue.bz=$(checkedObj).attr("bz");
				this.param.callback(returnValue);
			}
		}
		$("#alertModalCq").modal("hide");
	},
	clearCq:function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){			
				this.param.callback({});
		}	
	}
}