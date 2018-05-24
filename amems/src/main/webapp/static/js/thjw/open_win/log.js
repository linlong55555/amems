logModal = {
	queryResult:{},	
	columns:[],
	param: {
		code:null,
		selected:null,
		callback:function(){},
		fieldsLoaded:false
	},
	resetParam:function(param){
		this.param = param;
	},	
	init:function(param){ 
		$('#floatIcon').show();
		if(param){
			$.extend(this.param, param);
		}
		
	},	
	show : function(){
		
		if(!logModal.param.fieldsLoaded){
			   this.loadFields();
			   logModal.param.fieldsLoaded = true;
	    }
		$("#floatDiv").show();
		$("#log_keyword").focus();
		this.search();
	},
	loadFields:function(){
		var searchParam = this.gatherSearchParam();
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/sys/log/queryFields",
		   type: "post",
		   async : false,
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(rows){
			   finishWait();
			   if(rows.length >0){
				   logModal.appendFieldHtml(rows);
			   } else {
				   alert("system error.");
			   }
	     } 
	   }); 
	},
	appendFieldHtml:function(rows){
		var htmlContent = '';
		if(rows.length>0){
			var _self = this;
			$.each(rows,function(index,row){
				if(row.showInList == true){
					var headClass = (row.headClass!=undefined && row.headClass!=null )?row.headClass:'';
					_self.columns.push(row);
					 htmlContent = htmlContent +"<th class=\""+headClass +" \">"; 
					 htmlContent = htmlContent +"<div class=\"font-size-12 line-height-18\">"+formatUndefine(row.fieldName)+"</div>";
					 htmlContent = htmlContent +"<div class=\"font-size-9 line-height-18\">"+formatUndefine(row.fieldEname)+"</div>"; 
					 htmlContent = htmlContent +"</th>"; 
				}
		   });
			$("#log_list_table #log_list_tr").empty();
			$("#log_list_table #log_list_tr").html(htmlContent); 
		}
		else{
			AlertUtil.showMessage("加载日志表头失败");
		}
		new Sticky({tableId : 'log_list_table'});
	},
	 
 	goPage : function(pageNumber, sortType, sequence){
 		this.AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
	},	
	AjaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
		var _self = this;
		var searchParam = this.gatherSearchParam();
		searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:15};
		searchParam.paramsMap = {ID:searchParam.id};
		searchParam.paramsMap.DPRTCODE = $('#log_dprtcode').val();
		searchParam.paramsMap.KEYWORD = $.trim($("#log_keyword").val());
		searchParam.paramsMap.LOGINED_USERTYPE = userType;
		searchParam.paramsMap.LOGINED_USERID = userId;
		
			
		if(_self.param.extparam!=undefined){
			$.extend(searchParam.paramsMap, _self.param.extparam);
		}
		
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/sys/log/queryList",
		   type: "post",
		   async : false,
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   
			   if(data.rows!=undefined && data.rows.length >0){ 
				   logModal.appendContentHtml(data.rows);
				   var page_ = new Pagination({
						renderTo : "log_pagination",
						ulAliasClass: "logAliasClass",
						data: data,
						viewpagecount:5,
						pageAlignLeft:true,//分页居左
						sortColumn : sequence,
						orderType : sortType,
						extParams:{},
						goPage: function(a,b,c){
							logModal.AjaxGetDatasWithSearch(a,c,b);
						}/*,
						controller: this_*/
					});
				   
				   
				   
			   } else {
					 
				   $("#log_list_row").empty();
				   $('#log_pagination').empty();
				  $("#log_list_table").append("<tr><td colspan=\""+_self.columns.length+"\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
	     },
	     error:function(){
			 finishWait();
	   	     alert("system error.");
	     }
	   }); 
	},
	gatherSearchParam : function(){
		 var searchParam = {};
		 searchParam.code = this.param.code;
		 searchParam.czls = this.param.czls;
		 searchParam.id = this.param.id;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var htmlContent = '';
		var fieldInList = this.columns;
		var _self = this;
		_self.queryResult = [];
		$.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr  style=\"cursor:pointer\" bgcolor=\"#f9f9f9\"  >";
		   } else {
			   htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" >";
		   }
		   
		   $.each(fieldInList,function(index,item){
			   var dataClass = (item.dataClass!=undefined && item.dataClass!=null )?item.dataClass:'text-left';
			   if(item.field == 'REC_HD'){
				   var id = encodeURIComponent(row.ID);
				   var recCzls = encodeURIComponent(row.REC_CZLS);
				   
				   var rec_hd = StringUtil.escapeStr(row[item.field]) +" " + DicAndEnumUtil.getEnumName("logOperationEnum", row.REC_CZSM);
				   
				   htmlContent = htmlContent 
				   +"<td style=\"vertical-align: middle;\" class=\""+dataClass+"\">"
				   + "<a onClick=\'logModal.viewDiff(this)\' " 
				   + " title='"+rec_hd+"' "
				   + " row_id="+id+""
				   + " row_czls="+recCzls+""
				   +">" 
				   + rec_hd
				   +"</a>"
				   +"</td>"; 
				   
				  
				    
			   }
			   else{
				   htmlContent = htmlContent +"<td style=\"vertical-align: middle;\" class='"+dataClass+"' title='"+StringUtil.escapeStr(row[item.field])+"'>"+formatUndefine(StringUtil.escapeStr(row[item.field]))+"</td>";
			   }
		   });
		  
		   _self.queryResult[row.ID+'_'+row.REC_CZLS] = row;
		   htmlContent = htmlContent + "</tr>";  
	   });
		$("#log_list_table #log_list_row").empty();
		$("#log_list_table #log_list_row").html(htmlContent);
	},
	search: function(){
		this.goPage(1,"auto","desc");
	},
	/**
	 * 根据当前日志ID,
	 * @param id
	 */
/*	viewDiff:function(id,czls){
		var param = this.queryResult[decodeURIComponent(id)+'_'+decodeURIComponent(czls)]
		param['code']=this.param.code;
		if(typeof this.param.diffCallback === 'function' ){
			param.diffCallback = this.param.diffCallback ;
		}
		logDiffModal.show(param)
	}*/
	
	viewDiff:function(obj){
		var id = decodeURIComponent($(obj).attr('row_id'));
		var czls = decodeURIComponent($(obj).attr('row_czls'));
		var param = this.queryResult[id+'_'+czls]
		param['code']=this.param.code;
		if(typeof this.param.diffCallback === 'function' ){
			param.diffCallback = this.param.diffCallback ;
		}
		logDiffModal.show(param)
	}
}

$(function(){
	
	
	 jQuery.fn.isChildAndSelfOf = function(b){
		 return (this.closest(b).length > 0);
	 }; 
	 $(window).on("click",function(event){ 
		 if($(event.target).isChildAndSelfOf($("#floatDiv")) 
				 || $(event.target).isChildAndSelfOf($("#floatIcon"))
				 || $(event.target).isChildAndSelfOf(".logAliasClass")
				 || $(event.target).isChildAndSelfOf("#logDiffModal")
				 || $(event.target).isChildAndSelfOf(".blockOverlay")
		 ){
			 return;
		 }
		 else{
			 $("#floatDiv").hide();
		 }
	 })
	 
	 $("#floatIcon").click(function(){
		 logModal.show();
		 try{
         	$("#logTableDiv").height($("#log_pagination").offset().top-$("#logTableDiv").offset().top - 5);
         }catch(e){}
	 });
	 
	 $('#floatIcon').hide();
	 
	 $('#log_dprtcode').on('change',function(){
		 logModal.search();
	 })
	
})