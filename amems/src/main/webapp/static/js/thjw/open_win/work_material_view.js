Work_Material_View_Modal = {
		alertId:'Work_Material_View_Modal',
		tbodyId:'Work_Material_View_List',
		paginationId:'Work_Material_View_Pagination',
		param: {
			bjh:'',
			dprtcode:userJgdm//默认登录人当前机构代码
		},
		data:[],
		show : function(param){
			$("#"+this.alertId).modal("show");
			if(param){
				$.extend(this.param, param);
			}
			this.initParam();
			this.goPage(1,"auto","desc");
		},
		initParam : function(){
			$("#keyword_win_search",$("#"+this.alertId)).val('');
		},
		changeType : function(){
			this.goPage(1,"auto","desc");
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
			startWait();
			var this_ = this;
			AjaxUtil.ajax({
			   url:basePath+"/project/workorder/selectBjhAndDprt",
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
					   // 标记关键字
//					   signByKeyword($("#keyword_material_reserve_search").val(),[2,3,4,5],"#"+this_.tbodyId+" tr td");
				   } else {
					   $("#"+this_.tbodyId, $("#"+this_.alertId)).empty();
					   $("#"+this_.paginationId, $("#"+this_.alertId)).empty();
					   $("#"+this_.tbodyId, $("#"+this_.alertId)).append("<tr><td colspan=\"20\" class='text-center'>暂无数据 No data.</td></tr>");
				   }
		     }
		   }); 
		},
		gatherSearchParam : function(){
			 var searchParam = {};
			 var keyword = $.trim($("#keyword_win_search",$("#"+this.alertId)).val());
			 searchParam.bjh = this.param.bjh;
			 searchParam.dprtcode = this.param.dprtcode;
			 return searchParam;
		},
		appendContentHtml: function(list){
			var htmlContent = '';
			var this_ = this;
			$.each(list,function(index,row){
				if (index % 2 == 0) {
					htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='"+this_.alertId+".clickRow(this)' >";
				} else {
					htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='"+this_.alertId+".clickRow(this)' >";
			  	}
			   
				htmlContent += "<td style='vertical-align: middle;'  align='center'>"+formatUndefine(index+1)+"</td>";  
				htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>";  
				htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
				htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>";  
				htmlContent += "<td class='text-right'>"+formatUndefine(row.kykcsl==null?0:row.kykcsl)+"</td>";
				htmlContent += "<td class='text-left' >"+formatUndefine(row.jldw)+"</td>";  
				htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>";  
				
				htmlContent += "</tr>";  
			});
			$("#"+this_.tbodyId, $("#"+this_.alertId)).empty();
			$("#"+this_.tbodyId, $("#"+this_.alertId)).html(htmlContent);
		},
		search: function(){
			this.goPage(1,"auto","desc");
		}
	}