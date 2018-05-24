
$(document).ready(function(){
	Navigation(menuCode,"盘点审核","Audit");//初始化导航
	initDate();
	initData(); 
	refreshPermission();
});

function initDate(){
	
	TimeUtil.getCurrentDate(function(date){
		$('#shsj').text(date);
	});
}

//初始化盘点信息
function initData(){
	initTakeStockForm();//初始化盘点单
	initTakeScopeList();//初始化盘点范围
	goPage(1,"auto","desc");//初始化盘点清单
}
//初始化盘点表单
function initTakeStockForm(){
	var pdid = $.trim($("#pdid").val());
	AjaxUtil.ajax({
 		url:basePath+"/aerialmaterial/takestock/getById",
 		type: "post",
 		dataType:"json",
 		async:false,
 		data:{id:pdid},
 		success:function(data){
	    	if(data != null){
	    		$("#vpddh").text(data.pddh);
	    		$("#vpdrid").val(data.pdrid);
	    		$("#vpdbmid").val(data.pdbmid);
	    		$("#vpdrname").text(data.pdr?data.pdr.displayName:'');
	    		$("#vksrq").html(indexOfTime(data.ksrq));
	    		var vckname = formatUndefine(data.ckh) + " " + formatUndefine(data.ckmc);
	    		$("#vckname").attr("title",StringUtil.title(vckname,15));
	    		$("#vckname").text(StringUtil.subString(vckname,15));
	    		$("#vbz").attr("title",StringUtil.title(data.bz));
	    		$("#vbz").text(StringUtil.subString(data.bz));
	    		$("#pdzt").val(data.zt);
	    		if(data.zt == 3){
	    			$("input[name='viewType'][value=1]").hide();
	    			$("input[name='viewType'][value=2]").attr("checked",'checked');
	    			$("#viewAll").hide();
	    		}
	    	}
 		}
 	}); 
}

//初始化盘点范围列表
function initTakeScopeList(){
	var pdid = $.trim($("#pdid").val());
	AjaxUtil.ajax({
 		url:basePath+"/aerialmaterial/takestock/queryTakeScopeListByMainId",
 		type: "post",
 		dataType:"json",
 		async:false,
 		data:{mainid:pdid},
 		success:function(data){
 			$("#takeScope").empty();
	    	if(data != null && data.length > 0){
	    		var takeScopeStr = '<ul class="pandian">';
	    		$.each(data,function(index,row){
	    			
	    			takeScopeStr += "<li class='text-center' dxid='"+row.dxid+"'>";
	    			
	    			var content = '';
	    			var rightStype = '';
	    			if(row.pdlx == 2){
	    				content = StringUtil.escapeStr(row.storage?row.storage.kwh:'');
	    				rightStype = "<i class='icon-home color-black pull-right'></i>";
	    			}
	    			if(row.pdlx == 3){
	    				content = StringUtil.escapeStr(row.hcMainData?(row.hcMainData.ywms?row.hcMainData.ywms:'&nbsp;'):'');
	    				rightStype = "<i class='icon-cog color-blue pull-right'></i>";
	    			}
	    			takeScopeStr += '<span title="'+StringUtil.title(content,15)+'" class="font-size-12">'+StringUtil.subString(content,15)+'</span>';
	    			takeScopeStr += rightStype;
	    			
	    			takeScopeStr += "</li>";
	    			
				});
	    		
	    		takeScopeStr += '</ul>';
	    		$("#takeScope").append(takeScopeStr);
	    	}
 		}
 	}); 
}

//信息检索
function searchRevision(){
	goPage(1,"auto","desc");
}

//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}	

//带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var pdid = $.trim($("#pdid").val());
	var url = '';
	var searchParam = {};
	var paramsMap = {};
	var type = $("input[name='viewType']:checked").val();
	var keyword = $.trim($("#keyword_search").val());
	searchParam.keyword = keyword;
	searchParam.mainid = pdid;
	if(type == 1){
		url = basePath+"/aerialmaterial/takestock/queryStockDetailPageList";
	}else{
		var condition = type == 2?'all':(type == 3?'more':'lose');
		paramsMap.condition = condition;
		url = basePath+"/aerialmaterial/takestock/queryAllDetailPageList";
	}
	searchParam.paramsMap = paramsMap;
	searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	startWait();
	AjaxUtil.ajax({
	   url:url,
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(searchParam),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   appendContentHtml(data.rows);
			   new Pagination({
					renderTo : "takeStockDetailPagination",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					goPage:function(pageNumber,sortType,sequence){
						AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
					}
				}); 
			   // 标记关键字
			   signByKeyword($("#keyword_search").val(),[2,3,4,5,7],"#takeStockDetailList tr td");
		   } else {
			  $("#takeStockDetailList").empty();
			  $("#takeStockDetailPagination").empty();
			  $("#takeStockDetailList").append("<tr><td colspan=\"10\" class='text-center'>暂无数据 No data.</td></tr>");
		   }
		   new Sticky({tableId:'take_audit_table'});
     }
   }); 
	
}

function appendContentHtml(list){
	   var htmlContent = '';
	   var pdjlsNum = 0;
	   var czcyjlsNum = 0;
	   var pdpzArr = [];
	   var pddzt = $("#pdzt").val();
	   $.each(list,function(index,row){
		   pdjlsNum++;
		   var bjh = row.hcMainData?formatUndefine(row.hcMainData.bjh):'';
		   var kcsl = row.stock?(row.stock.kcsl?row.stock.kcsl:0):0;
		   if(pddzt == 3){
			   kcsl = row.materialHistory?(row.materialHistory.kcsl?row.materialHistory.kcsl:0):0;
		   }
		   var pdsl = row.cksl?row.cksl:0;
		   var xy = pdsl - kcsl;
		   if(xy != 0){
			   czcyjlsNum++;
		   }
		   if(pdpzArr.indexOf(bjh) == -1){
			   pdpzArr.push(bjh);
		   }
		   
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\" >";
		   } else {
			   htmlContent = htmlContent + "<tr bgcolor=\"#fefefe\" >";
		   }
		   
		   htmlContent += "<td class='text-center'>"+(index+1)+"</td>";
		   htmlContent += "<td title='"+(row.stock?StringUtil.escapeStr(row.stock.kwh):'')+"' class='text-left'>"+(row.stock?StringUtil.escapeStr(row.stock.kwh):'')+"</td>";  
		   htmlContent += "<td title='"+StringUtil.escapeStr(bjh)+"' class='text-left'>"+StringUtil.escapeStr(bjh)+"</td>"; 
		   htmlContent += "<td title='"+(row.hcMainData?StringUtil.escapeStr(row.hcMainData.ywms):'')+"' class='text-left'>"+(row.hcMainData?StringUtil.escapeStr(row.hcMainData.ywms):'')+"</td>";
		   htmlContent += "<td title='"+(row.hcMainData?StringUtil.escapeStr(row.hcMainData.zwms):'')+"' class='text-left'>"+(row.hcMainData?StringUtil.escapeStr(row.hcMainData.zwms):'')+"</td>";  
		   htmlContent += "<td class='text-left'>"+(row.hcMainData?DicAndEnumUtil.getEnumName('supervisoryLevelEnum',row.hcMainData.gljb):'')+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.stock?(row.stock.pch?row.stock.pch:(row.stock.sn?row.stock.sn:'')):'')+"' class='text-left'>"+StringUtil.escapeStr(row.stock?(row.stock.pch?row.stock.pch:(row.stock.sn?row.stock.sn:'')):'')+"</td>";  
		   htmlContent += "<td class='text-right'>"+(kcsl==0?'-':kcsl)+"</td>";  
		   htmlContent += "<td class='text-right'>"+pdsl+"</td>"; 
		   htmlContent += "<td class='text-right'>"+xy+"</td>";  
		   htmlContent += "</tr>";  
		    
	   });
	   $("#pdpz").html(pdpzArr.length);
	   $("#pdjls").html(pdjlsNum);
	   $("#czcyjls").html(czcyjlsNum);
	   $("#takeStockDetailList").empty();
	   $("#takeStockDetailList").html(htmlContent);
}

//字段排序
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(function() { //重置class
		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
	});
	$("#" + obj + "_" + "order").removeClass("sorting");
	if (orderStyle.indexOf("sorting_asc") >= 0) {
		$("#" + obj + "_" + "order").addClass("sorting_desc");
	} else {
		$("#" + obj + "_" + "order").addClass("sorting_asc");
	}
	orderStyle = $("#" + obj + "_order").attr("class");
	var currentPage = $("#takeStockDetailPagination li[class='active']").text();
	goPage(currentPage,obj,orderStyle.split("_")[1]);
}

//审核盘点单
function audit(){
	
	var pdid = $.trim($("#pdid").val());
	
	if(pdid  == null || pdid == '' || pdid == 0 || pdid == 1 ){
		AlertUtil.showErrorMessage("请选择盘点单!");
		return false;
	}
	
	var zt = $.trim($("#pdzt").val());
	if(zt != 2){
		AlertUtil.showErrorMessage("只有提交状态的盘点单才能审核!");
		return false;
	}
	
	AlertUtil.showConfirmMessage("确定审核吗？", {
		callback: function(){
			
			var url = basePath+"/aerialmaterial/takestock/audit";
			
			var param = {};
			param.id = pdid;
			
			startWait();
			// 提交数据
			AjaxUtil.ajax({
				url:url,
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify(param),
				dataType:"json",
				success:function(data){
					finishWait();
					AlertUtil.showMessage('确认成功!', '/aerialmaterial/takestock/history?id='+pdid+'&pageParam='+pageParam);
				}
			});
			
			
		}
	});
}

