course_list_table_div = {
	id : "course_list_table_div",
	isHiden:true,
	/**
	 *  解码页面查询条件和分页 并加载数据
	 */
	decodePageParam: function(bl){
		var this_=this;
		if(bl){
			this_.isHiden = false;
		}
		TableUtil.resetTableSorting("annualplanTable");
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
		
		paramsMap.shdx=$.trim($("#annual_plan_module_shdx_searchbh").val());//审核对象
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
		this_.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		searchParam.pagination = this_.pagination;
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
 				$("#ndjhnums").text(data.total);    //数量标识
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
				if(this_.isHiden){
					signByKeyword($("#keyword_search").val(), [4,5]);
				}else{
					signByKeyword($("#keyword_search").val(), [3,4]);
				}
	 			}else{
	 				$("#ndjhnums").text(0);    //数量标识
		 			$("#"+this_.id+"_list").empty();
					$("#"+this_.id+"_pagination").empty();
					$("#"+this_.id+"_list").append("<tr><td class='text-center' colspan=\"10\">暂无数据 No data.</td></tr>");
	 			}
	 			new Sticky({tableId:'annualplanTable'}); //初始化表头浮动
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
				if(this_.isHiden){
					htmlContent += "<td class='fixed-column text-center operation_td'>";
					if( $("#oldzt").val() == 0 || $("#oldzt").val() == 5 || $("#oldzt").val() == 6 || ($("#oldzt").val() ==3 && annual_plan.bbh == annual_plan.maxbbh)){
						htmlContent += "<i class=' icon-edit color-blue cursor-pointer checkPermission' permissioncode='quality:annualplan:main:02' " 
							+ "  onClick=\"annual_plan_alert_Update.open('"+row.id+"')\" title='修改 Edit'></i>&nbsp;&nbsp;";//修改
						htmlContent += "<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='quality:annualplan:main:04' " 
			    			+ " onClick=\"course_list_table_div.invalid('"+ row.id + "','"+ row.zt +"')\" title='删除 Delete'></i>&nbsp;&nbsp;"; 
					}
					if($("#oldzt").val() == 3 && annual_plan.bbh == annual_plan.maxbbh){
						htmlContent += "<i class='iconnew-createProject color-blue cursor-pointer checkPermission' " 
							+ " permissioncode='quality:auditnotice:main:01' onClick=\"course_list_table_div.createAudit('"+row.id+"')\" title='创建审核通知单 Approvel'></i>&nbsp;&nbsp;";//批准
					}
			
				    htmlContent += "</td>";
				}
			    htmlContent += "<td class='text-center' >"+StringUtil.escapeStr(row.yf)+"</td>";
			 
			    htmlContent += "<td class='text-center' >"+StringUtil.escapeStr(row.lx==1?"内部":"外部")+"</td>";
			    var shdx = StringUtil.escapeStr(row.shdxmc);
			    if(row.lx==1){
			    	shdx = StringUtil.escapeStr(row.shdxbh) + " "+ shdx;
			    }
			    htmlContent += "<td class='text-left' >"+shdx+"</td>";
			    
			    var dataList = StringUtil.escapeStr(row.paramsMap?row.paramsMap.shcy:'').split(",");// 在每个逗号(,)处进行分解。
				var zrr="";
				for (var i = 0; i < dataList.length; i++) {
				var ry=dataList[i].split("#_#");
					zrr=ry[2]+" "+ry[3]+","+zrr;
				}
				zrr=zrr.substr(0,zrr.length-1);
			    htmlContent += "<td class='text-left' >"+zrr+"</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.bz)+"</td>";
			    htmlContent += "<td title='附件 Attachment' class='text-center'>";
				if((row.paramsMap.fjnum != null && row.paramsMap.fjnum > 0) ){
					htmlContent += '<i id="'+row.id+'"  class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
				}
				htmlContent += "</td>";
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.whr?row.whr.id:'')+"</td>";
			    htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.whsj)+"</td>"; 
			    htmlContent += "<td class='text-left' >"+StringUtil.escapeStr(row.dprt?row.dprt.dprtname:'')+"</td>"; 
			    htmlContent += "</tr>" ;
			    
			    $("#"+this_.id+"_list").empty();
			    $("#"+this_.id+"_list").html(htmlContent);
			    refreshPermission();			//权限初始化
			    this_.initFile();
			    TableUtil.addTitle("#"+this_.id+"_list tr td"); //加载td title
		 });
	}
	,
	/**
	 *  点击附件展开附件列表
	 */
	initFile: function(){
		var this_=this;
		WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
			return this_.getHistoryAttachmentList(obj);
		});
		$("#work_card_main_table_top_div").scroll(function(){
			$('.attachment-view').webuiPopover('hide');
		});
	}
	,
	/**
	 *  点击附件展开附件列表
	 */
	getHistoryAttachmentList: function(obj){
		var jsonData = [
			   	         {mainid : $(obj).attr('id'), type : '附件'}
			   	    ];
			return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
	},
	createAudit : function(id){
		var this_ = this;
		var obj = {};
		obj.id = id;
		obj.dprtcode = $("#dprtcode").val();
		//根据单据id查询信息
		startWait();
	   	AjaxUtil.ajax({
	 		url:basePath + "/quality/annualplan/getById",
	 		contentType:"application/json;charset=utf-8",
	 		type:"post",
	 		async: false,
	 		data:JSON.stringify(obj),
	 		dataType:"json",
	 		modal:$("#"+this_.id),
	 		success:function(data){
	 			finishWait();
	 			if(data!=null){
	 				if($("#oldzt").val() != 3){
	 					AlertUtil.showMessage("该数据已更新，请刷新后再进行操作!");
	 				}else{
	 					var handleUrl = basePath+"/quality/auditnotice/main?";
	 					handleUrl += 'plan_id='+data.id;
	 					handleUrl += 'plan_shdxid='+data.shdxid;
	 					handleUrl += '&plan_shdxbh='+data.shdxbh;
	 					handleUrl += '&plan_dprtcode='+data.dprtcode;
	 					window.open(handleUrl);
	 				}
				};
	 		}
		});
	},
	/**
	 * 删除
	 */
	invalid : function(id){
		var this_ = this;
		if(annual_plan.zt == 3 && annual_plan.bbh == annual_plan.maxbbh){
			var message = "因为该年度计划已经通过审批，所以您的此次操作系统会自动对该年度计划进行升级版本，升级版本后不会对您之前的数据造成影响，您是否继续？";
			AlertUtil.showConfirmMessage(message, {callback: function(){
				
				this_.doInvalid(id);
				
			}});
		}else{
			AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
				
				this_.doInvalid(id);
				
			}});
		}
	},
	doInvalid : function(id){
		var this_ = this;
		var param = {};
		param.id = id;
		param.dprtcode = $.trim($("#dprtcode").val());
		param.nf = $.trim($("#year_search").val());
		param.bbh = $.trim($("#bbh").val());
		param.ndjhid = $("#id").val();
		 AjaxUtil.ajax({
			 type:"post",
			 url:basePath+"/quality/annualplan/audit/delete",
			 dataType:"json",
			 contentType:"application/json;charset=utf-8",
			 data:JSON.stringify(param),
			 success:function(data) {
				AlertUtil.showMessage('删除成功!');
				if(data != null && data != ""){
					annual_plan.searchRevision();
				}else{
					this_.refreshPage();
				}
			 }
		 });
	},
	search : function(){
		this.goPage(1,"auto","desc");
	},
	refreshPage : function(){//刷新页面
		this.goPage(this.pagination.page, this.pagination.sort, this.pagination.order);
	},
	/**
	 *  字段排序
	 */
	orderBy: function(obj){
	  var this_=this;
		var orderStyle = $("#" + obj + "_order").attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
		});
		$("#" + obj + "_" + "order").removeClass("sorting");
		if (orderStyle.indexOf("sorting_asc")>=0) {
			$("#" + obj + "_" + "order").addClass("sorting_desc");
		} else {
			$("#" + obj + "_" + "order").addClass("sorting_asc");
		}
		orderStyle = $("#" + obj + "_order").attr("class");
		var currentPage = $("#"+this_.id+"_pagination li[class='active']").text();
		this_.goPage(currentPage,obj,orderStyle.split("_")[1]);
	},
	/**
	 * 导出
	 */
	exportExcel : function(){
		var param = this.gatherSearchParam();
		param.pagination = {page:1,sort:"auto",order:"desc",rows:100000};
		param.keyword = encodeURIComponent(param.keyword);
		window.open(basePath+"/quality/annualplan/annualPlan.xls?paramjson="+JSON.stringify(param));
	}
	
};


