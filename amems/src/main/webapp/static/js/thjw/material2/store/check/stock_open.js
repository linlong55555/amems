stock_open_alert = {
	id : "stock_open_alert",
	paginationId : "stock_open_alert_pagination",
	ckidList : [],
	param: {
		selected:null,
		multi:false, //是否多选 默认单选
		type : 0,//0，全部，1航材，2工具设备
		hclxList : [],//航材类型集合
		ckidList : [],
		modalHeadCN : '选择库存',
		modalHeadENG : 'Stock',
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	data:[],
	show : function(param){
		var this_ =this;
		$("#"+this.id).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		AlertUtil.hideModalFooterMessage();    	   //初始化错误信息 
		this.init();
		
	},
	init : function(){
		this.loadMaterialSelect();
		this.initModelHead();
		this.initDivSearch();
		this.searchreset();
		this.inithcly();
		this.initqczt();
		this.AjaxGetDatasWithStock(1,"auto","desc");//加载库存
	},
	//初始化航材来源
	inithcly: function(){
		var this_ = this;
		$("#"+this_.id+"_hcly").empty();
		$("#"+this_.id+"_hcly").html("<option value=''>显示全部</option>");
		DicAndEnumUtil.registerDic('85',this_.id+"_hcly",this_.param.dprtcode);
	},
	//初始化器材状态
	initqczt: function(){
		var this_ = this;
		$("#"+this_.id+"_qczt").empty();
		$("#"+this_.id+"_qczt").html("<option value=''>显示全部</option>");
		DicAndEnumUtil.registerDic('86',this_.id+"_qczt",this_.param.dprtcode);
	},
	initDivSearch : function(){
		$("#divSearch").css("display", "none");
		$("#icon").removeClass("icon-caret-up");
		$("#icon").addClass("icon-caret-down");
	},
	/**
	 * 初始化对话框头部
	 */
	initModelHead : function(){
		$("#modalHeadCN", $("#"+this.id)).html(this.param.modalHeadCN);
		$("#modalHeadENG", $("#"+this.id)).html(this.param.modalHeadENG);
	},
	loadMaterialSelect : function(){
		$("#"+this.id+"_hclx", $("#"+this.id)).empty();
		var option = '<option value="" >显示全部All</option>';
		$("#"+this.id+"_hclx", $("#"+this.id)).append(option);
		DicAndEnumUtil.registerEnum("materialTypeEnum", this.id+"_hclx");
		if(this.param.type == 1){
			$("#"+this.id+"_hclx", $("#"+this.id)).empty();
			var items = DicAndEnumUtil.getEnum("materialTypeEnum");
			$.each(items, function(index, item){
				if(item.id != 2 && item.id != 3){
					option += "<option value='"+item.id+"' >"+StringUtil.escapeStr(item.name)+"</option>";
				}
			});
			$("#"+this.id+"_hclx", $("#"+this.id)).append(option);
		}else if(this.param.type == 2){
			$("#"+this.id+"_hclx", $("#"+this.id)).empty();
			var items = DicAndEnumUtil.getEnum("materialToolSecondTypeEnum");
			$.each(items, function(index, item){
				option += "<option value='"+item.id+"' >"+StringUtil.escapeStr(item.name)+"</option>";
			});
			$("#"+this.id+"_hclx", $("#"+this.id)).append(option);
		}
		
	},
	initStoreSelect : function(){
		var this_ = this;
		this_.ckidList = [];
		$("#"+this_.id+"_ckid", $("#"+this_.id)).empty();
		var storeHtml = "<option value=''>显示全部All</option>";
		var dprtcode = this_.param.dprtcode;
		$.each(userStoreList, function(index, row){
			if(dprtcode == row.dprtcode){
				storeHtml += "<option value='"+row.id+"'>"+StringUtil.escapeStr(row.ckh)+" " + StringUtil.escapeStr(row.ckmc)+"</option>"
				this_.ckidList.push(row.id);
			}
		});
		$("#"+this_.id+"_ckid", $("#"+this_.id)).append(storeHtml);
		this_.loadStorage();
	},
	changeStore : function(){
		this.loadStorage();
		this.searchkc();
	},
	loadStorage : function(){
		var this_ = this;
		var ckid = $.trim($("#"+this_.id+"_ckid", $("#"+this_.id)).val());
	 	AjaxUtil.ajax({
			async: false,
			url:basePath+"/material/store/queryStorageListByStoreId",
			type:"post",
			data:{storeId : ckid},
			dataType:"json",
			success:function(data){
				this_.buildStorage(data);	
			}
		});
	},
	buildStorage : function(storageList){
		var this_ = this;
		$("#"+this_.id+"_kwid", $("#"+this_.id)).empty();
	 	var option = '<option value="">显示全部All</option>';
	 	for(var i = 0 ; i < storageList.length ; i++){
	 		var storage = storageList[i];
	 		option += '<option value="'+storage.id+'">'+StringUtil.escapeStr(storage.kwh)+'</option>';
	 	}
	 	$("#"+this_.id+"_kwid", $("#"+this_.id)).append(option);
	 	$("#"+this_.id+"_kwid", $("#"+this_.id)).selectpicker('refresh');
	 	
	},
	gatherSearchParamStock : function(){
		
		 var searchParam = {};
		 searchParam.bjh=$.trim($("#"+this.id+"_bjh").val());
		 searchParam.sn=$.trim($("#"+this.id+"_xlh").val());
		 searchParam.pch=$.trim($("#"+this.id+"_pch").val());
		 var hclx=$.trim($("#"+this.id+"_hclx").val());
		 searchParam.gljb=$.trim($("#"+this.id+"_gljb").val());
		 searchParam.kwid=$.trim($("#"+this.id+"_kwid").val());
		 searchParam.grn=$.trim($("#"+this.id+"_grn").val());
		 searchParam.hcly=$.trim($("#"+this.id+"_hcly").val());
		 searchParam.cqid=$.trim($("#"+this.id+"_cqid").val());
		 searchParam.keyword=$.trim($("#"+this.id+"_keyword").val());
		 searchParam.ckid=$.trim($("#"+this.id+"_ckid").val());
		 searchParam.qczt=$.trim($("#"+this.id+"_qczt").val());
		 
		 var rksjrq=$.trim($("#"+this.id+"_rksj").val()); 	//上架时间
	     var rksjrqBeginDate="";				 	//开始日期 
	     var rksjrqEndDate="";						//结束日期
		 if(null != rksjrq && "" != rksjrq){
			rksjrqBeginDate= rksjrq.substring(0,4)+"-"+rksjrq.substring(5,7)+"-"+rksjrq.substring(8,10)+" 00:00:00";
			rksjrqEndDate= rksjrq.substring(12,17)+"-"+rksjrq.substring(18,20)+"-"+rksjrq.substring(21,23)+" 23:59:59";
		 }
		 
		 var scrqrq=$.trim($("#"+this.id+"_scrq").val()); 	//生产时间
		 var scrqrqBeginDate="";				 	//开始日期 
		 var scrqrqEndDate="";						//结束日期
		 if(null != scrqrq && "" != scrqrq){
			 scrqrqBeginDate= scrqrq.substring(0,4)+"-"+scrqrq.substring(5,7)+"-"+scrqrq.substring(8,10)+" 00:00:00";
			 scrqrqEndDate= scrqrq.substring(12,17)+"-"+scrqrq.substring(18,20)+"-"+scrqrq.substring(21,23)+" 23:59:59";
		 }
		 
		 searchParam.zzcs=$.trim($("#"+this.id+"_zzcs").val());
		 searchParam.cjjh=$.trim($("#"+this.id+"_cjjh").val());
		 var paramsMap={};
		 paramsMap.ggxh=$.trim($("#"+this.id+"_ggxh").val());
		 paramsMap.dprtcode=this.param.dprtcode;
		 
		 paramsMap.rksjrqBeginDate=rksjrqBeginDate;
		 paramsMap.rksjrqEndDate=rksjrqEndDate;
		 paramsMap.scrqrqBeginDate=scrqrqBeginDate;
		 paramsMap.scrqrqEndDate=scrqrqEndDate;
		 
		 if('' != hclx){
			 if(this.param.type == 2){
				 paramsMap.hclxEj = hclx;
			 }else{
				 searchParam.hclx = hclx;	 
			 }
		 }
		 
		 var hclxList = this.param.hclxList;
		 if(hclxList != null && hclxList != '' && hclxList.length > 0){
			 paramsMap.hclxList = hclxList;
		 }
		 
		 if(this.ckidList != null && this.ckidList.length > 0){
			paramsMap.ckidList = this.ckidList;
		 }
		 
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	},
	/**
	 * 库存列表
	 */
	AjaxGetDatasWithStock: function(pageNumber,sortType,sequence){
		var this_ = this;
		var url = "/material/outin/stockList"; 	
		
		if(this_.ckidList.length == 0){
			this_.setNoData();
			return;
		}
		
		var searchParam = this.gatherSearchParamStock();
		var pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		searchParam.pagination = pagination;
		startWait();
		AjaxUtil.ajax({
			url:basePath+url,
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   this_.data = [];
			   if(data.total >0){
				   this_.data = data.rows;
				   this_.appendContentHtmlStock(data.rows);
				   new Pagination({
						renderTo : this_.paginationId,
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						extParams:{},
						goPage: function(a,b,c){
							this_.AjaxGetDatasWithStock(a,b,c);
						}
					}); 
				   // 标记关键字
				   signByKeyword($("#"+this_.id+"_keyword").val(),[2,3,4,5,16,17,18,19],"#"+this_.id+"_stocklist tr td");
			   } else {
				   this_.setNoData();
			   }
			   new Sticky({tableId:'stocklist_table'}); //初始化表头浮动
	     }
	   }); 
	},
	setNoData : function(){
		$("#"+this.id+"_stocklist").empty();
		  $("#"+this.id+"_pagination").empty();
		  $("#"+this.id+"_stocklist").append("<tr><td colspan=\"20\"  class='text-center'>暂无数据 No data.</td></tr>");
	},
	appendContentHtmlStock: function(list){
		var this_=this;
		var htmlContent = '';
		$.each(list,function(index,row){
			htmlContent += "<tr  style=\"cursor:pointer\">";
			
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"; 
			   
			if(this_.param.multi){
				htmlContent += "<input type='checkbox' name='"+this_.id+"_list_radio' value='"+index+"' onclick=\"SelectUtil.checkRow(this,'selectAllId','"+this_.id+"_list')\" >"; 
			}else{
				if(this_.param.selected == row.id){
					htmlContent += "<input type='radio' checked='checked' name='"+this_.id+"_list_checkbox' value='"+index+"'  >"; 
				}else{
					htmlContent += "<input type='radio'  name='"+this_.id+"_list_checkbox' value='"+index+"'  >"; 
				}
			}
		   
			htmlContent += "</td>";
			
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bjh)+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.ywms)+" "+StringUtil.escapeStr(row.zwms)+"</td>";//部件名称
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.pch)+"</td>"; 
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.sn)+"</td>"; 
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.ckh)+" "+formatUndefine(row.ckmc)+" "+formatUndefine(row.kwh))+"</td>"; 
			if(row.djsl && row.djsl > 0){
				htmlContent += "<td class='text-right' >"+formatUndefine(row.kcsl-row.djsl)+"/"+formatUndefine(row.kcsl)+" "+row.jldw+"</td>"; 
			}else{
				htmlContent += "<td class='text-right' >"+formatUndefine(row.kcsl)+" "+row.jldw+"</td>"; 
			}
			if(formatUndefine(row.hjsm) =='' && formatUndefine(row.syts)==''){
				htmlContent += "<td class='text-center'></td>";
			}else{
				htmlContent += "<td class='text-center'>"+formatUndefine(row.hjsm).substring(0,10)+"/"+formatUndefine(row.syts)+"</td>";
			}
			var cb = '';
			var jz = '';
			if(formatUndefine(row.kccb) != ''){
				cb = formatUndefine(row.kccb).toFixed(2)+" "+formatUndefine(row.biz);
			}
			if(formatUndefine(row.jz) != ''){
				jz = formatUndefine(row.jz).toFixed(2)+" "+formatUndefine(row.jzbz);
			}
		    htmlContent += "<td class='text-right' >"+cb+"</td>";
//		    htmlContent += "<td class='text-right' >"+jz+"</td>";
		    htmlContent += "<td class='text-left' >"+formatUndefine(row.paramsMap?row.paramsMap.cqbh:'')+"</td>";//产权 
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.grn)+"</td>"; 
//		    htmlContent += "<td class='text-center'>"+DicAndEnumUtil.getEnumName('stockStatusEnum',row.zt)+"</td>"; 
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.hcly)+"</td>"; 
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.bz)+"</td>"; 
		    htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.rksj).substring(0,10)+"</td>"; //上架日期
		    var hclx = '';
		    if(this_.param.type == 2){
		    	hclx = DicAndEnumUtil.getEnumName('materialToolSecondTypeEnum',row.hclxEj);	 
			}else{
				hclx = DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx);	 
			}
		    htmlContent += "<td class='text-center'>"+hclx+"</td>"; 
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.cjjh)+"</td>"; 
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.xh)+"</td>"; 
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.gg)+"</td>"; 
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.zzcs)+"</td>"; 
		    htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.qczt)+"</td>"; 
			htmlContent += "</tr>";  
		    
		});
		$("#"+this_.id+"_stocklist").empty();
		$("#"+this_.id+"_stocklist").html(htmlContent);
		TableUtil.addTitle("#"+this_.id+"_stocklist tr td"); //加载td title
		this_.rowonclick();
	},
	rowonclick: function(){
		var this_ = this;
		$("#"+this.id+"_stocklist tr").click(function(event){
			if(this_.param.multi){
				SelectUtil.checkRow($(event.target).parent().find("input[type='checkbox']"),'selectAllId',this_.id+"_stocklist");
			}else{
				$(this).find("input[type='radio']").attr("checked",true);
			}
		});
	},
	/**
	 * 搜索条件重置
	 */
	searchreset: function(){
		var this_=this;
		 $("#divSearch").find("input").each(function() {
				$(this).attr("value", "");
		 });
		 $("#divSearch").find("textarea").each(function(){
			 $(this).val("");
		 });
		 $("#divSearch").find("select").each(function(){
				$(this).val("");				
		 });
		 $("#"+this_.id+"_keyword").val("");
		 $("#"+this_.id+"_cqid").val("");
		 $("#"+this_.id+"_cqbh").val("");
		 this.initStoreSelect();
	},
	searchkc: function(){
		this.AjaxGetDatasWithStock(1,"auto","desc");//加载库存
	},
	setData: function(){
		var this_ = this;
		if(this.param.callback && typeof(this.param.callback) == "function"){
			
			var data;
			if(this_.param.multi){
				data = [];
				$("#"+this.id+"_stocklist").find("input:checked").each(function(){
					var index = $(this).attr("value");	
					data.push(this_.data[index]);
				});
			}else{
				var $checkedRadio = $("#"+this.id+"_stocklist").find("input:checked");
				data = this.data[$checkedRadio.attr("value")];
			}
			if(data){
				this.param.callback(data);
			}
		}
		this.close();
	},
	clear : function(){
		if(this.param.callback && typeof(this.param.callback) == "function"){
			this.param.callback({});
		}
		this.close();
	},
	close : function(){
		$("#"+this.id).modal("hide");
	},
	more: function() {//查询条件更多 展开/收缩
		if ($("#divSearch", $("#"+this.id)).css("display") == "none") {
			$("#divSearch", $("#"+this.id)).css("display", "block");
			$("#icon", $("#"+this.id)).removeClass("icon-caret-down");
			$("#icon", $("#"+this.id)).addClass("icon-caret-up");
		} else {
			$("#divSearch", $("#"+this.id)).css("display", "none");
			$("#icon", $("#"+this.id)).removeClass("icon-caret-up");
			$("#icon", $("#"+this.id)).addClass("icon-caret-down");
		}
	},
	//产权
	loadCq : function(){
		var this_=this;
		cqModal.show({
			selected:$("#"+this_.id+"_cqid").val(),
			callback:function(data){
				$("#"+this_.id+"_cqid").val(data.id);
				$("#"+this_.id+"_cqbh").val(data.cqbh);
				this_.searchkc();
			}
			
		});
	}
}
