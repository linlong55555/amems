year_list_table_div = {
	id : "year_list_table_div",
	/**
	 *  解码页面查询条件和分页 并加载数据
	 */
	decodePageParam: function(){
		var this_=this;
		TableUtil.resetTableSorting("yearTable");
		try{
			var decodeStr = Base64.decode(pageParam);
			var pageParamJson = JSON.parse(decodeStr);
			var params = pageParamJson.params;
			$("#dprtcode").val(params.dprtcode);
			if(pageParamJson.pagination){
				this_.goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
			}else{
				this_.goPage(1,"auto","desc");
			}
		}catch(e){
			this_.goPage(1,"auto","desc");
		}
		
	}
	,
	/**
	 *  跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
	 */
	goPage: function(pageNumber,sortType,sequence){
		var this_=this;
		this_.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	}
	,
	/**
	 * 查询条件
	 */
	gatherSearchParam: function(){
		var searchParam={};
		var paramsMap={};
		searchParam.dprtcode=$.trim($("#dprtcode").val());		//组织机构
		searchParam.keyword=$.trim($('#keyword_search').val()); //关键字
		
		searchParam.nf=$.trim($("#year_search").val()); 		//年度
		searchParam.bbh=$.trim($("#bbh").val()); 				//版本号
		
		paramsMap.shdx=$.trim($("#annual_plan_module_shdx_search").val()); 		//审核对象
		var shzrr = $.trim($("#annual_plan_module_shzrr_search").val());
		if(shzrr != null && shzrr != ""){
			var shzrrArr = shzrr.split(",");
			var shzrrList = [];
			for(var i = 0 ; i < shzrrArr.length ; i++){
				var shzrrStr = shzrrArr[i].split(" ");
				shzrrList.push(shzrrStr[0]);
			} 
			paramsMap.shzrr = shzrrList;//责任审核人
		}
		searchParam.paramsMap=paramsMap;
		return searchParam;
	}
	,
	/**
	 * 查询主单分页列表
	 */
	AjaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
		var this_=this;
		var searchParam = this_.gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		searchParam.pagination = pagination;
		/*if (id != "") {
			searchParam.id = id;
			id = "";
		}*/
		AjaxUtil.ajax({
		   url:basePath+"/quality/annualplan/queryAllPageList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
	 			if(data.total >0){
	 			this_.appendContentHtml(data.rows);
	 			//分页
				var page_=new Pagination({
					renderTo : this_.id+"_pagination",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					extParams:{},
					goPage: function(a,b,c){
						this_.AjaxGetDatasWithSearch(a,b,c);
					}
				});
	 			// 标记关键字
				signByKeyword($("#keyword_search").val(), [5,6]);
	 			}else{
		 			$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"15\">暂无数据 No data.</td></tr>");
	 			}
	 			new Sticky({tableId:'yearTable'}); //初始化表头浮动
		   }
	  }); 	
	}	
	,
	/**
	 * 表格拼接
	 */
	appendContentHtml: function(list){
		var this_=this;
		var htmlContent = '';
		 $.each(list,function(index,row){
				htmlContent += "<tr>";
			    
			 
			    htmlContent += "<td class='text-center' >"+StringUtil.escapeStr(row.lx==1?"内部":"外部")+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.shdxbh)+" "+StringUtil.escapeStr(row.shdxmc)+"</td>";
			    
			    var dataList = StringUtil.escapeStr(row.paramsMap?row.paramsMap.shcy:'').split(",");// 在每个逗号(,)处进行分解。
				var zrr="";
				for (var i = 0; i < dataList.length; i++) {
				var ry=dataList[i].split("#_#");
					zrr=ry[2]+" "+ry[3]+","+zrr;
				}
				zrr=zrr.substr(0,zrr.length-1);
			    htmlContent += "<td class='text-left' >"+zrr+"</td>";
			
			    for (var i = 1; i <= 12; i++) {
			    	if(StringUtil.escapeStr(row.yf)==i){
			    		 htmlContent +="<td class='text-center'><i class='fa fa-star' style='color:black;'></i></td>";
			    	}else{
			    		htmlContent += "<td class='text-left' ></td>";
			    	}
				}
			    
			    htmlContent += "</tr>" ;
			    
			    $("#"+this_.id+"_list").empty();
			    $("#"+this_.id+"_list").html(htmlContent);
			    refreshPermission();			//权限初始化
			    TableUtil.addTitle("#"+this_.id+"_list tr td"); //加载td title
		 });
	}
	,
	/**
	 *  字段排序
	 */
	orderBy: function(obj, _obj){
		var this_=this;
		$obj = $("#yearTable th[name='column_"+obj+"']");
		var orderStyle = $obj.attr("class");
		$("#yearTable .sorting_desc").removeClass("sorting_desc").addClass("sorting");
		$("#yearTable .sorting_asc").removeClass("sorting_asc").addClass("sorting");
		
		var orderType = "asc";
		if (orderStyle.indexOf("sorting_asc")>=0) {
			$obj.removeClass("sorting").addClass("sorting_desc");
			orderType = "desc";
		} else {
			$obj.removeClass("sorting").addClass("sorting_asc");
			orderType = "asc";
		}
		var currentPage = $("#"+this_.id+"_pagination li[class='active']").text();
		this_.goPage(currentPage,obj, orderType);
	}
};


