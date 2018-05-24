stockout_open_alert = {
	id : "stockout_open_alert",
	stockid : "chosen_stock_alert",
	paginationId : "stockout_open_alert_pagination",
	dataList : [],
	ids : [],
	backData :{},//返回数据
	chooseData :{},//右边数据
	bjh : null,
	xlh : null,
	mxid : null,
	param: {
		parentWinId : '',
		selected:null,
		id : null,
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	data:[],
	show : function(param){
		var this_ =this;
		$("#"+this_.id+"_bjh").val("");
		$("#"+this_.id+"_xlh").val("");
		$("#"+this_.id+"_bjh").attr("disabled",false);//部件号可编辑
		$("#"+this_.id+"_xlh").attr("disabled",false);//序列号可编辑
		
		$("#"+this.id).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		 AlertUtil.hideModalFooterMessage();    	   //初始化错误信息 
		this.clearData();
		
		this.backData={};
		$("#chosenstock").text("已选择（0）");
		$("#chosen_stock_alert_stocklist").html("");
		$("#pply").show(); //匹配来源隐藏
		this.AjaxGetDatasWithSearch();
		stockout_body.initStoreSelectck();//初始化弹出框仓库
		$("#"+this.id+"_ckid").val($("#ckid").val());
		stockout_body.initStorek();
		this_.inithcly();
		this_.initqczt();
		$("#divSearch").css("display", "none");
		modalLeftRightResize();
		$("#icon").removeClass("icon-caret-up");
		$("#icon").addClass("icon-caret-down");
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
	AjaxGetDatasWithSearch: function(){
		var this_=this;
		var url ="";
		if(this_.param.bs == 1){
			url = "/material/demand/queryDemandProtectionInfoList"; //需求	
		}else{
			url = "/material/outin/queryContractdetailsList"; //合同
		}
		var searchParam = this.gatherSearchParam();
		startWait();
		AjaxUtil.ajax({
			 async: false,
			 url:basePath+url,
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   if(data.length > 0){
				   
				   this_.appendContentHtml(data);
					
				   // 标记关键字
				   signByKeyword($("#"+this_.id+"_keyword_search").val(),[1,2],"#"+this_.id+"_detailslist tr td");
			   } else {
					$("#modalPanelLeft").css("display","none");
					$("#modalPanelRight").removeAttr("class");
					$("#modalPanelRight").addClass("col-xs-12 padding-left-0 padding-right-0");  //隐藏左边数据
					this.backData={};
					$("#pply").hide(); //匹配来源隐藏
					this_.AjaxGetDatasWithStock(1,"auto","desc");//加载库存
			   }
	     }
	   }); 
	},
	gatherSearchParam : function(){
		
		 var searchParam = {};
		 searchParam.mainid = this.param.id;
		 var paramsMap={};
		 var idList=[];
		 idList.push(this.param.id);
		 paramsMap.idList=idList;
		 paramsMap.keyword=$.trim($("#"+this.id+"_keyword_search").val());
		 paramsMap.dprtcode=this.param.dprtcode;
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	},
	appendContentHtml: function(list){
		var this_=this;
		var htmlContent = '';
		$.each(list,function(index,row){
			htmlContent += "<tr  id='"+row.id+"' bjh='"+row.bjh+"' xlh='"+row.xlh+"' onclick=\"stockout_open_alert.onContent(this)\">";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bjh)+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xlh)+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.wllb)+"</td>";
			htmlContent += "</tr>";  
		    
		});
		$("#"+this_.id+"_detailslist").empty();
		$("#"+this_.id+"_detailslist").html(htmlContent);
		 TableUtil.addTitle("#"+this_.id+"_detailslist tr td"); //加载td title
		 
		 if(list.length>0){
			 $("#pplyid").attr("checked","checked");
			 $("#"+this_.id+"_bjh").attr("disabled",true);//部件号不可编辑
			 $("#"+this_.id+"_xlh").attr("disabled",true);//序列号不可编辑
			 $("#"+list[0].id).addClass('bg_tr');//第一个选中
			 $("#"+this_.id+"_bjh").val(StringUtil.escapeStr(list[0].bjh));
			 $("#"+this_.id+"_xlh").val(StringUtil.escapeStr(list[0].xlh));
			 this_.bjh=list[0].bjh;
			 this_.xlh=list[0].xlh;
			 this_.mxid=list[0].id;
			 this_.AjaxGetDatasWithStock(1,"auto","desc");//加载库存
		 }
		 
	},
	onContent : function(e){
		var this_=this;
		var id = $(e).attr("id");
		var bjh = $(e).attr("bjh");
		var xlh = $(e).attr("xlh");
		$("#"+id).addClass('bg_tr').siblings().removeClass('bg_tr');
		this_.mxid=id;
		var pply=$("#pplyid").is(":checked");
		
		this_.appendContentHtmlStock(this_.data);
		if(pply==true){
			if(bjh == 'null'){
				bjh="";
			}
			$("#"+this_.id+"_bjh").val(bjh);
			
			if(xlh == 'null'){
				xlh="";
			}
			$("#"+this_.id+"_xlh").val(xlh);
			this_.bjh=bjh;
			this_.xlh=xlh;
			this_.AjaxGetDatasWithStock(1,"auto","desc");//加载库存
		}
	},
	
	gatherSearchParamStock : function(){
		
		 var searchParam = {};
		 searchParam.bjh=$.trim($("#"+this.id+"_bjh").val());
		 searchParam.sn=$.trim($("#"+this.id+"_xlh").val());
		 searchParam.pch=$.trim($("#"+this.id+"_pch").val());
		 searchParam.hclx=$.trim($("#"+this.id+"_hclx").val());
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
		 searchParam.paramsMap = paramsMap;
		 return searchParam;
	},
	/**
	 * 库存列表
	 */
	AjaxGetDatasWithStock: function(pageNumber,sortType,sequence){
		var this_ = this;
		var url = "/material/outin/stockList"; 	
		var searchParam = this.gatherSearchParamStock();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		searchParam.pagination = pagination;
		startWait();
		AjaxUtil.ajax({
		   async: false,
		   url:basePath+url,
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(searchParam),
		   success:function(data){
			   finishWait();
			   if(data.total >0){
				   this_.data = data.rows;
				   this_.appendContentHtmlStock(data.rows,id);
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
				  $("#"+this_.id+"_stocklist").empty();
				  $("#"+this_.id+"_pagination").empty();
				  $("#"+this_.id+"_stocklist").append("<tr><td colspan=\"20\"  class='text-center'>暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'stocklist_table'}); //初始化表头浮动
	     }
	   }); 
	},
	appendContentHtmlStock: function(list){
		var this_=this;
		var htmlContent = '';
		$.each(list,function(index,row){
			htmlContent += "<tr onclick=\"stockout_open_alert.showHiddenContent('"+row.id+"','"+this_.mxid+"')\">";
			var checked='';
			if(StringUtil.escapeStr(this_.backData[row.id+this_.mxid]) != ''){
				checked="checked";
			}
			htmlContent += "<td class='text-center ' ><input type='checkbox' name='checkrow'  "+checked+" id='"+row.id+"' onclick=\"SelectUtil.checkRow(this,'selectAllId')\"/></td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bjh)+"</td>";
			htmlContent += "<td  style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.ywms)+" "+StringUtil.escapeStr(row.zwms)+"</td>";//部件名称
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.pch)+"</td>"; 
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.sn)+"</td>"; 
			htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.ckh)+" "+StringUtil.escapeStr(row.ckmc)+" "+StringUtil.escapeStr(row.kwh)+"</td>"; 
			if(row.djsl && row.djsl >0){
				htmlContent += "<td class='text-right' >"+formatUndefine(row.kcsl-row.djsl)+"/"+formatUndefine(row.kcsl)+" "+StringUtil.escapeStr(row.jldw)+"</td>"; 
			}else{
				htmlContent += "<td class='text-right' >"+formatUndefine(row.kcsl)+" "+StringUtil.escapeStr(row.jldw)+"</td>"; 
			}	
			if(formatUndefine(row.hjsm) =='' && formatUndefine(row.syts)==''){
				htmlContent += "<td class='text-center'></td>";
			}else{
				htmlContent += "<td class='text-center'>"+formatUndefine(row.hjsm).substring(0,10)+"/"+formatUndefine(row.syts)+"</td>";
			}
			var kccb = StringUtil.escapeStr(row.kccb);
			if(kccb == ''){
				htmlContent += "<td ></td>"; 
			}else{
				kccb = kccb.toFixed(2);
				htmlContent += "<td class='text-right' >"+kccb+" "+formatUndefine(row.biz)+"</td>"; 
			}
//			var jz = StringUtil.escapeStr(row.jz);
//			if(jz == ''){
//				htmlContent += "<td ></td>"; 
//			}else{
//				jz = jz.toFixed(2);
//				htmlContent += "<td class='text-right' >"+jz+" "+formatUndefine(row.jzbz)+"</td>"; 
//			}
		    htmlContent += "<td class='text-left' >"+formatUndefine(row.paramsMap?row.paramsMap.cqbh:'')+"</td>";//产权 
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.grn)+"</td>"; 
//		    htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('stockStatusEnum',row.zt)+"</td>"; 
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.hcly)+"</td>"; 
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.bz)+"</td>"; 
		    htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.rksj).substring(0,10)+"</td>"; //上架日期
		    htmlContent += "<td class='text-left'>"+DicAndEnumUtil.getEnumName('materialTypeEnum',row.hclx)+"</td>"; 
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.cjjh)+"</td>"; 
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.xh)+"</td>"; 
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.gg)+"</td>"; 
		    htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(row.zzcs)+"</td>"; 
		    htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.qczt)+"</td>"; 
			htmlContent += "</tr>";  
			this_.chooseData[row.id]=row;
		});
		$("#"+this_.id+"_stocklist").empty();
		$("#"+this_.id+"_stocklist").html(htmlContent);
		 TableUtil.addTitle("#"+this_.id+"_stocklist tr td"); //加载td title
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
		 $("#"+this_.id+"_ckid").val($("#ckid").val());
	},
	showHiddenContent: function(id,mxid){
		var this_=this;
		if($("#"+id).attr("checked")){
			$("#"+id).attr("checked",false);
			this_.del(id,mxid);
		}else{
			$("#"+id).attr("checked",true);
			chosen_stock_alert.appendContentHtmlStock(this.chooseData[id]);
		}
		var data = [];				
		for(key in this_.backData){
			data.push(this_.backData[key]);
		}
		$("#chosenstock").text("已选择（"+data.length+"）");
	},
	search: function(){
		this.backData={};
		$("#chosenstock").text("已选择（0）");
		this.AjaxGetDatasWithSearch();
	},
	searchkc: function(){
		this.backData={};
		$("#chosenstock").text("已选择（0）");
		this.AjaxGetDatasWithStock(1,"auto","desc");//加载库存
	},
	clearData : function(){
		$("#keyword_SelectSourceorder").val('');
	},
	setData: function(){
		var this_ = this;
		if(this.param.callback && typeof(this.param.callback) == "function"){		
			var data = [];				
			for(key in this_.backData){
				var dataObj = jQuery.extend(true, {}, this_.backData[key]);//深度拷贝
				
				var id=	this_.backData[key].id;
				var mxid=strs=key.split(id); //字符分割
				dataObj.paramsMap.mxid=mxid[1];
				
				data.push(dataObj);
			}
			if(data == null ||data==undefined || data.length == 0){
				$("#stockout_main_list").empty();
				$("#stockout_main_list").append("<tr><td colspan=\"15\"  class='text-center'>暂无数据 No data.</td></tr>");
			}else{
				this_.param.callback(data);
			}
			
			$("#"+this_.id).modal("hide");
	}
	},
	close : function(){
		$("#"+this.id).modal("hide");
	},
	/**
	 * 选择匹配来源
	 */
	onpply : function(){
		var this_=this;
		var pply=$("#pplyid").is(":checked");
		if(pply==false){
			$("#"+this_.id+"_bjh").val("");
			$("#"+this_.id+"_xlh").val("");
			$("#"+this_.id+"_bjh").attr("disabled",false);//部件号可编辑
			$("#"+this_.id+"_xlh").attr("disabled",false);//序列号可编辑
		}else{
			$("#"+this_.id+"_bjh").val(this_.bjh);
			$("#"+this_.id+"_xlh").val(this_.xlh);
			$("#"+this_.id+"_bjh").attr("disabled",true);//部件号不可编辑
			$("#"+this_.id+"_xlh").attr("disabled",true);//序列号不可编辑
		}
		this.ids=[];
		$("#chosenstock").text("已选择（0）");
		this_.AjaxGetDatasWithStock(1,"auto","desc");//加载库存
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
	},
	del:function(id,mxid){
		$("#"+this.stockid+"_stocklist #"+id+mxid+"1").remove();
		delete(this.backData[id+mxid]);
		
	}
}
$('.date-picker').datepicker({
	format:'yyyy-mm-dd',
	autoclose : true
}).next().on("click", function() {
	$(this).prev().focus();
});

$('input[name=date-range-picker]').daterangepicker().prev().on("click",
		function() {
			$(this).next().focus();
		});
 $('#example27').multiselect({
	buttonClass : 'btn btn-default',
	buttonWidth : 'auto',

	includeSelectAllOption : true
}); 