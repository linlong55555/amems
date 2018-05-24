
$(document).ready(function(){
	Navigation(menuCode,"","");//初始化导航
	
	//回车事件控制
	$(this).keydown(function(event) {
		e = event ? event :(window.event ? window.event : null); 
		if(e.keyCode==13){
			var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
			if(formatUndefine(winId) != ""){
				$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
			}else{
				searchRevision();//调用主列表页查询
			}
		}
	});
	
	initStoreSelect();
	initDate();
	refreshPermission();
});

function initDate(){
	$('#eksrq').datepicker({
		 autoclose: true,
		 clearBtn:true
	});
	$('#hchjsm').datepicker({
		 autoclose: true,
		 clearBtn:true
	});
	$('#vhchjsm').datepicker({
		 autoclose: true,
		 clearBtn:true
	});
}

//初始化差异维护
function initDiff(){
	clearTakeStockDiff(0);
	stockShowMaterialHide();
	inithcly();
	$("#search_value").attr("placeholder","件号/英文名称/序列号/批次号/适航证号");
	$("input[name='DiffType'][value=1]").attr("checked",'checked');
}
function inithcly(){
	$("#hchcly").empty();
	DicAndEnumUtil.registerDic('85',"hchcly",$("#dprtcode_search").val());
}

function initStoreSelect(){
	$("#ckid").empty();
	var storeHtml = "";
	var dprtcode = $("#dprtcode_search").val();
	$.each(userStoreList, function(index, row){
		if(dprtcode == row.dprtcode){
			storeHtml += "<option value=\""+row.id+"\">"+StringUtil.escapeStr(row.ckmc)+"</option>"
		}
	});
	$("#ckid").append(storeHtml);
	hideButton();
	changeStore(); 
}

//仓库改变时,重新加载盘点单下拉框信息
function changeStore(){
	clearTakeStockDetail();
	clearButton();
 	var ckid = $.trim($("#ckid").val());
 	AjaxUtil.ajax({
 		url:basePath+"/aerialmaterial/takestock/queryListByCkid",
 		type: "post",
 		dataType:"json",
 		async:false,
 		data:{ckid:ckid},
 		success:function(data){
	    	$("#takeStockSelect").empty();
	    	var option = '<option value="0">请选择</option>';
	    	if(checkBtnPermissions('aerialmaterial:takestock:main:01')){
	    		option += '<option value="1">新增盘点单</option>';
	    	}
	    	if(data != null && data.length >0){
			  	for(var i = 0 ; i < data.length ; i++){
			  		var takeStock = data[i];
			  		var ckStr = '全仓库';
			  		if(takeStock.takeStockScopeList != null && takeStock.takeStockScopeList.length > 0){
			  			ckStr = '库位/部件';
			  		}
			  		var str = StringUtil.escapeStr(takeStock.pddh)+" "+ckStr+" "+DicAndEnumUtil.getEnumName('takeStockStatusEnum',takeStock.zt)+" "+indexOfTime(takeStock.ksrq);
			  		option += '<option value="'+takeStock.id+'">'+str+'</option>';
			  	}
	    	}
	    	$("#takeStockSelect").append(option);
 		}
 	}); 
 	
}

//盘点单改变时,执行
function changeTakeStock(){
	clearTakeStockDetail();
 	var pdid = $.trim($("#takeStockSelect").val());
 	var ckid = $.trim($("#ckid").val());
 	if(pdid == 0){
 		clearButton();
 	}
 	if(pdid == 1 && ckid != null && ckid != ''){
 		openEditTakeStockWin();
 	}
 	if(pdid != 0 && pdid != 1 && ckid != null && ckid != ''){
 		initTakeDetail();
 	}
}
//选择盘点单时初始化盘点详情信息
function initTakeDetail(){
	initTakeStockForm();//初始化盘点单
	initTakeScopeList();//初始化盘点范围
	goPage(1,"auto","desc");//初始化盘点清单
}
//初始化盘点表单
function initTakeStockForm(){
	var pdid = $.trim($("#takeStockSelect").val());
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
	    		$("#vbz").attr("title",StringUtil.title(data.bz,15));
	    		$("#vbz").text(StringUtil.subString(data.bz,15));
	    		$("#vbzValue").val(data.bz);
	    		$("#vpdzt").val(data.zt);
	    		if(data.zt == 1){
	    			viewClearButton();
	    		}else{
	    			clearButton();
	    		}
	    	}
 		}
 	}); 
}

//初始化盘点范围列表
function initTakeScopeList(){
	var pdid = $.trim($("#takeStockSelect").val());
	AjaxUtil.ajax({
 		url:basePath+"/aerialmaterial/takestock/queryTakeScopeListByMainId",
 		type: "post",
 		dataType:"json",
 		async:false,
 		data:{mainid:pdid},
 		success:function(data){
 			$("#takeScope").empty();
 			var pddzt = $("#vpdzt").val();
	    	if(data != null && data.length > 0){
	    		var privilege = checkBtnPermissions('aerialmaterial:takestock:main:02');
	    		var takeScopeStr = '<ul class="pandian">';
	    		$.each(data,function(index,row){
	    			
	    			takeScopeStr += "<li class='text-center' dxid='"+row.dxid+"'>";
	    			if(pddzt == 1 && privilege){
	    				takeScopeStr += "<i class='icon-minus cursor-pointer color-blue cursor-pointer pull-left' onclick=delTakeScope('"+row.id+"')></i>";
	    			}
	    			
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
	
	//检查是否选择盘点单
	if(!checkTakeStock()){
		return false;
	}
	
	var pdid = $.trim($("#takeStockSelect").val());
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
	    clearTakeStockRecord();
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
			   signByKeyword($("#keyword_search").val(),[2,3,4,5,7,8],"#takeStockDetailList tr td");
		   } else {
			  $("#takeStockDetailList").empty();
			  $("#takeStockDetailPagination").empty();
			  $("#takeStockDetailList").append("<tr><td colspan=\"11\" class='text-center'>暂无数据 No data.</td></tr>");
		   }
		   new Sticky({tableId:'take_main_table'});
     }
   }); 
	
}

function appendContentHtml(list){
	   var htmlContent = '';
	   var pdjlsNum = 0;
	   var czcyjlsNum = 0;
	   var pdpzArr = [];
	   var pddzt = $("#vpdzt").val();
	   $.each(list,function(index,row){
		   pdjlsNum++;
		   var bjh = row.hcMainData?StringUtil.escapeStr(row.hcMainData.bjh):'';
		   var kcsl = row.stock?(row.stock.kcsl?row.stock.kcsl:0):0;
		   var pdsl = row.cksl?row.cksl:0;
		   var xy = pdsl - kcsl;
		   if(xy != 0){
			   czcyjlsNum++;
		   }
		   if(pdpzArr.indexOf(bjh) == -1){
			   pdpzArr.push(bjh);
		   }
		   
		   if (index % 2 == 0) {
			   if(pddzt == 1){
				   htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" ondblclick=openDiffEditWin('"+row.id+"','"+row.kcllid+"','"+row.kcsl+"')>";
			   }else{
				   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\" >";
			   }
		   } else {
			   if(pddzt == 1){
				   htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" ondblclick=openDiffEditWin('"+row.id+"','"+row.kcllid+"','"+row.kcsl+"')>";
			   }else{
				   htmlContent = htmlContent + "<tr bgcolor=\"#f9f9f9\" >";
			   }
		   }
		   
		   htmlContent += "<td class='text-center'>"+(index+1)+"</td>";
		   htmlContent += "<td title='"+(row.stock?StringUtil.escapeStr(row.stock.kwh):'')+"' class='text-left'>"+(row.stock?StringUtil.escapeStr(row.stock.kwh):'')+"</td>";  
		   htmlContent += "<td title='"+bjh+"' class='text-left'>"+bjh+"</td>"; 
		   htmlContent += "<td title='"+(row.hcMainData?StringUtil.escapeStr(row.hcMainData.ywms):'')+"' class='text-left'>"+(row.hcMainData?StringUtil.escapeStr(row.hcMainData.ywms):'')+"</td>";
		   htmlContent += "<td title='"+(row.hcMainData?StringUtil.escapeStr(row.hcMainData.zwms):'')+"' class='text-left'>"+(row.hcMainData?StringUtil.escapeStr(row.hcMainData.zwms):'')+"</td>";  
		   htmlContent += "<td class='text-left'>"+(row.hcMainData?DicAndEnumUtil.getEnumName('supervisoryLevelEnum',row.hcMainData.gljb):'')+"</td>";
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.stock?(row.stock.sn?row.stock.sn:''):'')+"' class='text-left'>"+StringUtil.escapeStr(row.stock?(row.stock.sn?row.stock.sn:''):'')+"</td>";  
		   htmlContent += "<td title='"+StringUtil.escapeStr(row.stock?(row.stock.pch?row.stock.pch:''):'')+"' class='text-left'>"+StringUtil.escapeStr(row.stock?(row.stock.pch?row.stock.pch:''):'')+"</td>";  
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
//查询差异所需航材信息
function searchMaterial(){
	var pdid = $.trim($("#takeStockSelect").val());
	var type = $("input[name='DiffType']:checked").val();
	var searchValue = $.trim($("#search_value").val());

	if(searchValue == null || searchValue == ''){
		AlertUtil.showMessageCallBack({
			message : '请输入查询条件!',
			option : 'search_value',
			callback : function(option){
				$("#"+option).focus();
			}
		});
		return false;
	}
	
	var url = basePath+"/material/material/queryMaterialListByPdid";
	if(type == 1){
		url = basePath+"/aerialmaterial/stock/queryStockListByPdid";
	}
	
	AjaxUtil.ajax({
 		url:url,
 		type: "post",
 		dataType:"json",
 		async:false,
 		data:{pdid:pdid,keyword:searchValue},
 		success:function(data){
 			$('#search_value').typeahead('destroy');
	    	if(data != null && data.length > 0){
	    		if(data.length == 1){
	    			if(type == 1){
	    				var obj = data[0];
	    				obj.kcpdsl = '';
	    				setStockData('',obj);
	    			}else{
	    				clearTakeStockDiff(type);
	    				setMaterialData(data[0]);
	    			}
	    		}else{
	    			refreshAutoComplete(data);
	    			$('#search_value').typeahead('lookup');
	    			$('#search_value').focus();
	    		}
	    	}else{
	    		AlertUtil.showErrorMessage("未找到相关数据!");
	    	}
 		}
 	}); 
}
//编辑维护差异(库存)
function viewDiffEditKC(id){
	var url = basePath+"/aerialmaterial/stock/queryById";
	AjaxUtil.ajax({
 		url:url,
 		type: "post",
 		dataType:"json",
 		async:false,
 		data:{id:id},
 		success:function(data){
	    	if(data != null){
	    		data.kcpdsl = '';
	    		setStockData('v', data);
	    		$("#alertVDiffKCModalForm").modal("show");
	    	}
 		}
 	}); 
}

//编辑维护差异(航材)
function viewDiffEditHC(id,kcllid,kcsl){
	$("#pdcyid").val(id);
	$("#kcllid").val(kcllid);
	var url = basePath+"/aerialmaterial/takestock/queryByDetailId";
	AjaxUtil.ajax({
 		url:url,
 		type: "post",
 		dataType:"json",
 		async:false,
 		data:{id:id},
 		success:function(data){
	    	if(data != null){
	    		var obj = data.materialHistory;
    			obj.kcpdsl = data.cksl;
    			obj.gljb = data.hcMainData?data.hcMainData.gljb:2;
	    		if(kcsl != 0){
	    			obj.kcsl = data.stock?data.stock.kcsl:0;
		    		setStockData('v', obj);
	    			$("#alertVDiffKCModalForm").modal("show");
	    		}else{
	    			setMaterialEditData(obj);
	    			$("#alertVDiffHCModalForm").modal("show");
	    		}
	    	}
 		}
 	}); 
}

function openEditTakeStockWin(){
	var pdid = $.trim($("#takeStockSelect").val());
	var ckid = $("#ckid").find("option:selected").val();
 	var ckh = $("#ckid").find("option:selected").text();
 	$("#epdid").val(pdid);
 	$("#eckid").val(ckid);
 	$("#eckname").val(ckh);
 	if(pdid == 1){
 		$("#epdrid").val($("#userId").val());
 		$("#epdbmid").val('');
 		$("#epdrname").val($("#username").val());
 		$("#eksrq").val('');
 		$('#eksrq').datepicker( 'setDate' , new Date());
 		$("#ebz").val('');
 	}else{
 		$("#epdrid").val($.trim($("#vpdrid").val()));
 		$("#epdbmid").val($.trim($("#vpdbmid").val()));
 		$("#epdrname").val($.trim($("#vpdrname").html()));
 		$("#eksrq").val($.trim($("#vksrq").html()));
 		$("#ebz").val($.trim($("#vbzValue").val()));
 	}
	$("#alertModalForm").modal("show");
}
//打开维护差异弹出框
function openDiffWin(){
	
	//检查是否选择盘点单
	if(!checkTakeStock()){
		return false;
	}
	initDiff();//初始化维护差异
	$("#alertDiffModalForm").modal("show");
}

//打开维护差异编辑弹出框
function openDiffEditWin(id,kcllid,kcsl){
	$("#pdcyid").val('');
	if(kcllid == null || kcllid == '' || kcllid == 'null'){
		viewDiffEditKC(id);//初始化维护差异(库存)
	}else{
		viewDiffEditHC(id,kcllid,kcsl);//初始化维护差异(航材)
	}
	
}

function openUserModalWin(){
	userModal.show({
		selected:$.trim($("#epdrid").val()),//原值，在弹窗中回显
		dprtcode:$.trim($("#dprtcode_search").val()),
		callback: function(data){//回调函数
			if(data != null && data != ""){
				$("#epdrid").val(formatUndefine(data.id));
				$("#epdbmid").val((data.dprt_bm?data.dprt_bm.id:""));
		 		$("#epdrname").val(StringUtil.escapeStr(data.userName)+' '+StringUtil.escapeStr(data.realname));
			}
		}
	})
}

function openTakeScopeWin(type){
	var pdid = $.trim($("#takeStockSelect").val());
	if(pdid == 0 || pdid == 1){
		AlertUtil.showErrorMessage("请选择盘点单!");
		return false;
	}
	if(type == 1){
		loadStorageSelect(pdid);
		$("#alertStorageModal").modal("show");
	}
	if(type == 2){
		var existsIdList = [];
		$("#takeScope").find("ul li").each(function(){
			var id = $(this).attr("dxid");
			existsIdList.push(id);
		});
		open_win_material_basic.show({
			multi : true,
			loaded : false,
			dprtcode:$.trim($("#dprtcode_search").val()),
			existsIdList : existsIdList,//过滤已经选择的
			callback: function(data){//回调函数
				if(data != null && data != ""){
					saveTakeScope(2,data);
				}
			}
		},true)
	}
}

//加载库位下拉框
function loadSelect(){
	var pdid = $.trim($("#takeStockSelect").val());
	AjaxUtil.ajax({
	   url:basePath+"/material/store/queryStorageSelectByPdid",
	   async: false,
	   type: "post",
	   dataType:"json",
	   data:{pdid : pdid},
	   success:function(data){
		   $('#hckw').empty();
		   $("#vhckw").empty;
		   var storageOption = '';
		   if(data != null && data.length >0){
			   $.each(data,function(i,storage){
				 	var tempOption = "<option value='"+storage.id+"' >"+StringUtil.escapeStr(storage.kwh)+"</option>";
				 	storageOption += tempOption;
			   });
		   }
		   $("#hckw").append(storageOption);
		   $("#vhckw").append(storageOption);
	   }
   }); 
}

//加载多选库位下拉框
function loadStorageSelect(pdid){
	AjaxUtil.ajax({
	   url:basePath+"/material/store/queryStorageListByPdid",
	   async: false,
	   type: "post",
	   dataType:"json",
	   data:{pdid : pdid},
	   success:function(data){
		   var storageOption = '';
		   if(data != null && data.length >0){
			   $.each(data,function(i,storage){
				 	var tempOption = "<option value='"+storage.id+"' >"+StringUtil.escapeStr(storage.kwh)+"</option>";
				 	storageOption += tempOption;
			   });
		   }
		   loadMultiselect(storageOption);
	   }
   }); 
}
//加载多选库位下拉框
function loadMultiselect(storageOption){
	//生成多选下拉框(库位)
	$('#StorageDiv').empty();
	$('#StorageDiv').html("<select multiple='multiple' id='StorageSelect' ></select>");
	$("#StorageSelect").append(storageOption);
	$('#StorageSelect').multiselect({
	    buttonClass: 'btn btn-default',
	    buttonWidth: 'auto',
	    numberDisplayed:4,
	    includeSelectAllOption: true,
	    onChange:function(element,checked){
  	    }
    });
}
//保存盘点单
function saveTakeStock(){
	
	var pdid = $.trim($("#epdid").val());
	var ckid = $.trim($("#eckid").val());
	var pdrid = $.trim($("#epdrid").val());
	var pdbmid = $.trim($("#epdbmid").val());
	var ksrq = $.trim($("#eksrq").val());
	var bz = $.trim($("#ebz").val());
	var dprtcode = $.trim($("#dprtcode_search").val());
	
	if(ckid  == null || ckid == ''){
		AlertUtil.showErrorMessage("仓库不能为空!");
		return false;
	}
	
	if(pdrid  == null || pdrid == ''){
		AlertUtil.showErrorMessage("请选择盘点人!");
		return false;
	}
	
	if(ksrq  == null || ksrq == ''){
		AlertUtil.showErrorMessage("请选择盘点日期!");
		return false;
	}
	
	var url = basePath+"/aerialmaterial/takestock/addTakeStock";
	if(pdid != 1 && pdid != 0){
		url = basePath+"/aerialmaterial/takestock/editTakeStock";
	}
	
	var param = {};
	param.id = pdid;
	param.ckid = ckid;
	param.pdrid = pdrid;
	param.pdbmid = pdbmid;
	param.ksrq = ksrq;
	param.bz = bz;
	param.dprtcode = dprtcode;
	
	startWait($("#alertModalForm"));
	// 提交数据
	AjaxUtil.ajax({
		url:url,
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(param),
		dataType:"json",
		modal:$("#alertModalForm"),
		success:function(data){
			finishWait($("#alertModalForm"));
			AlertUtil.showMessage('保存成功!');
			$("#alertModalForm").modal("hide");
			if(data != null){
				if(pdid == 1){
					changeStore();
					$("#takeStockSelect").val(data);
					goPage(1,"auto","desc");//初始化盘点清单
				}
				viewClearButton();
				initTakeStockForm(data);
			}
		}
	});

}

//提交盘点单
function submitTakeStock(){
	
	var pdid = $.trim($("#takeStockSelect").val());
	
	if(pdid  == null || pdid == '' || pdid == 0 || pdid == 1 ){
		AlertUtil.showErrorMessage("请选择盘点单!");
		return false;
	}
	
	var zt = $.trim($("#vpdzt").val());
	if(zt != 1){
		AlertUtil.showErrorMessage("只有保存状态的盘点单才能提交!");
		return false;
	}
	
	AlertUtil.showConfirmMessage("确定要提交吗？", {
		callback: function(){
			
			var url = basePath+"/aerialmaterial/takestock/submitTakeStock";
			
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
					AlertUtil.showMessage('提交成功!');
					changeStore();
					changeTakeStock();
					clearButton();
				}
			});
			
			
		}
	});
}
//保存盘点范围
function saveTakeScope(type,obj){
	var pdid = $.trim($("#takeStockSelect").val());
	var takeStockScopeList = [];
	if(type == 1){
		var storageIds = $.trim($("#StorageSelect").val());
		if(storageIds != null && storageIds.length > 0){
			var storageIdArr = storageIds.split(",");
			for(var i = 0 ; i < storageIdArr.length ; i++){
				if('multiselect-all' != storageIdArr[i]){
					var takeScope = {};
					takeScope.mainid = pdid;
					takeScope.pdlx = 2;
					takeScope.dxid = storageIdArr[i];
					takeStockScopeList.push(takeScope);
				}
			}
		}
	}
	
	if(type == 2){
		var flag = true;
		if(obj == null){
			flag = false;
		}
		$.each(obj,function(index,row){
			$("#takeScope").find("ul li").each(function(){
				if(row != null && row.id == $(this).attr("dxid")){
					flag = false;
				}
			});
			var takeScope = {};
			takeScope.mainid = pdid;
			takeScope.pdlx = 3;
			takeScope.dxid = row.id;
			takeStockScopeList.push(takeScope);
		});
		if(!flag){
			return false;
		}
	}
	
	if(takeStockScopeList.length == 0){
		return false;
	}
	
	var param = {};
	param.id = pdid;
	param.takeStockScopeList = takeStockScopeList;
	
	startWait();
	// 提交数据
	AjaxUtil.ajax({
		url:basePath+"/aerialmaterial/takestock/addTakeScope",
		contentType:"application/json;charset=utf-8",
		type:"post",
		data:JSON.stringify(param),
		dataType:"json",
		success:function(data){
			finishWait();
			AlertUtil.showMessage('保存成功!');
			initTakeScopeList();
			goPage(1,"auto","desc");//初始化盘点清单
		}
	});

}

//删除盘点范围
function delTakeScope(id){
	
	AlertUtil.showConfirmMessage("确定要删除吗？", {
		callback: function(){
			startWait();
			AjaxUtil.ajax({
				url:basePath + "/aerialmaterial/takestock/delTakeScope",
				type:"post",
				async: false,
				data:{
					takeScopeId : id,
					mainId : $.trim($("#takeStockSelect").val())
				},
				dataType:"json",
				success:function(data){
					finishWait();
					AlertUtil.showMessage('删除成功!');
					initTakeScopeList();
					goPage(1,"auto","desc");//初始化盘点清单
				}
			});
		}
	})
}

//保存盘点差异
function saveTakeStockDetail(saveType){
	
	//检查是否选择盘点单
	if(!checkTakeStock()){
		return false;
	}
	var regu = /^[0-9]+\.?[0-9]*$/;
	var renum = /^[0-9]*$/;
	var url = basePath+"/aerialmaterial/takestock/addDetailFromStock";
	var pdid = $.trim($("#takeStockSelect").val());
	var type = $("input[name='DiffType']:checked").val();
	var param = {};
	var stock = {};
	var materialHistory = {};
	param.mainid = pdid;
	param.dprtcode = $.trim($("#dprtcode_search").val());
	if(type == 1){
		var cksl = $.trim($("#kcpdsl").val());
		if(cksl == null || cksl == ''){
			AlertUtil.showErrorMessage("请输入盘点数量!");
			return false;
		}
		
		if (!regu.test(cksl)) {
	        AlertUtil.showErrorMessage("盘点数量只能输入数字！");
	        return false;
	    }
		
		url = basePath+"/aerialmaterial/takestock/addDetailFromStock";
		var kcid = $.trim($("#kcid").val());
		if(kcid == null || kcid == ''){
			AlertUtil.showErrorMessage("请查询并选择库存!");
			return false;
		}
		stock.id = kcid;
		param.cksl = cksl;
	}else{
		url = basePath+"/aerialmaterial/takestock/addDetailFromMaterial";
		var cksl = $.trim($("#hcpdsl").val());
		if(cksl == null || cksl == '' || cksl == 0){
			AlertUtil.showErrorMessage("请输入盘点数量,盘点数量不能为0!");
			return false;
		}
		
		if (!regu.test(cksl)) {
	        AlertUtil.showErrorMessage("盘点数量只能输入数字！");
	        return false;
	    }
		
		var bjid = $.trim($("#hcbjid").val());
		if(bjid == null || bjid == ''){
			AlertUtil.showErrorMessage("请查询并选择航材!");
			return false;
		}
		materialHistory.bjid = $.trim($("#hcbjid").val());
		materialHistory.bjh = $.trim($("#hcbjh").text());
		materialHistory.zwms = $.trim($("#hczwms").text());
		materialHistory.ywms = $.trim($("#hcywms").text());
		materialHistory.ckid = $("#ckid").find("option:selected").val();
		materialHistory.kwid = $.trim($("#hckw").val());
		materialHistory.kwh = $("#hckw").find("option:selected").text();
		var hcgljb = $.trim($("#hcgljb").val());
		if(hcgljb == 2){
			materialHistory.pch = $.trim($("#hcsnpch").val());
		}
		if(hcgljb == 3){
			materialHistory.sn = $.trim($("#hcsnpch").val());
		}
		materialHistory.hcly = $.trim($("#hchcly").val());
		materialHistory.xkzh = $.trim($("#hcxkzh").val());
		materialHistory.hjsm = $.trim($("#hchjsm").val());
		materialHistory.shzh = $.trim($("#hcshzh").val());
		materialHistory.shzwz = $.trim($("#hcshzwz").val());
		var tsn = $.trim($("#hctsn").val());
		var tso = $.trim($("#hctso").val());
		if (!renum.test(tsn)) {
	        AlertUtil.showErrorMessage("TSN只能输入数字！");
	        return false;
	    }
		if (!renum.test(tso)) {
	        AlertUtil.showErrorMessage("TSO只能输入数字！");
	        return false;
	    }
		materialHistory.tsn = tsn;
		materialHistory.tso = tso;
		materialHistory.cfyq = $.trim($("#hccfyq").val());
		
		param.cksl = cksl;
	}
	param.stock = stock;
	param.materialHistory = materialHistory;
	performAction(url,param,saveType);
}

//编辑盘点差异
function editTakeStockDetail(type){
	var regu = /^[0-9]+\.?[0-9]*$/;
	var renum = /^[0-9]*$/;
	var pdcyid = $.trim($("#pdcyid").val());
	var url = '';
	var param = {};
	var stock = {};
	var materialHistory = {};
	param.mainid = $.trim($("#takeStockSelect").val());
	if(pdcyid == null || pdcyid == ''){
		var cksl = $.trim($("#vkcpdsl").val());
		if(cksl == null || cksl == ''){
			AlertUtil.showErrorMessage("请输入盘点数量!");
			return false;
		}
		if (!regu.test(cksl)) {
	        AlertUtil.showErrorMessage("盘点数量只能输入数字！");
	        return false;
	    }
		
		url = basePath+"/aerialmaterial/takestock/addDetailFromStock";
		var kcid = $.trim($("#vkcid").val());
		if(kcid == null || kcid == ''){
			AlertUtil.showErrorMessage("请查询并选择库存!");
			return false;
		}
		stock.id = kcid;
		param.cksl = cksl;
		param.stock = stock;
		performAction(url,param,2);
	}else{
		url = basePath+"/aerialmaterial/takestock/eidtTakeStockDetail";
		param.id = pdcyid;
		if(type == 1){
			var cksl = $.trim($("#vkcpdsl").val());
			param.cksl = cksl;
			
			if (!regu.test(cksl)) {
		        AlertUtil.showErrorMessage("盘点数量只能输入数字！");
		        return false;
		    }
			
		}else{
			param.kcllid = $.trim($("#kcllid").val());
			var cksl = $.trim($("#vhcpdsl").val());
			
			if (!regu.test(cksl)) {
		        AlertUtil.showErrorMessage("盘点数量只能输入数字！");
		        return false;
		    }
			
			materialHistory.id = $.trim($("#kcllid").val());
			materialHistory.kwid = $.trim($("#vhckw").val());
			materialHistory.kwh = $("#vhckw").find("option:selected").text();
			var hcgljb = $.trim($("#vhcgljb").val());
			if(hcgljb == 2){
				materialHistory.pch = $.trim($("#vhcsnpch").val());
			}
			if(hcgljb == 3){
				materialHistory.sn = $.trim($("#vhcsnpch").val());
			}
			materialHistory.hcly = $.trim($("#vhchcly").val());
			materialHistory.xkzh = $.trim($("#vhcxkzh").val());
			materialHistory.hjsm = $.trim($("#vhchjsm").val());
			materialHistory.shzh = $.trim($("#vhcshzh").val());
			materialHistory.shzwz = $.trim($("#vhcshzwz").val());
			var tsn = $.trim($("#vhctsn").val());
			var tso = $.trim($("#vhctso").val());
			if (!renum.test(tsn)) {
		        AlertUtil.showErrorMessage("TSN只能输入数字！");
		        return false;
		    }
			if (!renum.test(tso)) {
		        AlertUtil.showErrorMessage("TSO只能输入数字！");
		        return false;
		    }
			materialHistory.tsn = tsn;
			materialHistory.tso = tso;
			materialHistory.cfyq = $.trim($("#vhccfyq").val());
			param.cksl = cksl;
			param.materialHistory = materialHistory;
		}
		performActionEdit(url,param);
	}
}

//保存差异
function performAction(url,param,saveType){
	var type = $("input[name='DiffType']:checked").val();
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
			AlertUtil.showMessage('保存成功!');
			if(saveType == 1){
				clearTakeStockDiff(type);
			}else{
				$("#alertDiffModalForm").modal("hide");
				$("#alertVDiffKCModalForm").modal("hide");
			}
			searchRevision();
		}
	});
}

//修改差异
function performActionEdit(url,param){
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
			AlertUtil.showMessage('保存成功!');
			$("#alertVDiffKCModalForm").modal("hide");
			$("#alertVDiffHCModalForm").modal("hide");
			searchRevision();
		}
	});
}

/**
 * 初始化输入框自动完成控件
 */
function refreshAutoComplete(data){
	$('#search_value').typeahead({
		displayText : function(item){
			return item.displayName;
		},
	    source: function (query, process) {
	    	process(data);
	    },

	    highlighter: function (item) {
          return "&nbsp;&nbsp;" + item + "&nbsp;&nbsp;";
	    },
	    updater: function (item) {
	    	var type = $("input[name='DiffType']:checked").val();
	    	if(type == 1){
	    		var obj = item;
	    		obj.kcpdsl = '';
	    		setStockData('',obj);
	    	}else{
	    		clearTakeStockDiff(type);
	    		setMaterialData(item);
	    	}
	    	return item;
      	}
	});
}
//选中库存或新增
function checkDiff(){
	var type = $("input[name='DiffType']:checked").val();
	clearTakeStockDiff(type);
	if(type == 1){
		$("#search_value").attr("placeholder","件号/英文名称/序列号/批次号/适航证号");
		stockShowMaterialHide();
	}else{
		$("#search_value").attr("placeholder","件号/英文名称");
		loadSelect();
		stockHideMaterialShow();
	}
}


//检查是否选择盘点单
function checkTakeStock(){
	var flag = true;
	var pdid = $.trim($("#takeStockSelect").val());
	if(pdid == 0 || pdid == 1 ){
		AlertUtil.showErrorMessage("请选择盘点单!");
		flag = false;
	}
	return flag;
}

//显示按钮
function showButton(){
	if(checkBtnPermissions('aerialmaterial:takestock:main:02')){
		$("#editTakeStockButton").show();
		$("#editTakeScopeButton").show();
	}
}
//隐藏库存显示航材
function stockHideMaterialShow(){
	$("#kc_sl").hide();
	$("#kc_form").hide();
	$("#hc_form").show();
}

//显示库存隐藏航材
function stockShowMaterialHide(){
	$("#kc_sl").show();
	$("#kc_form").show();
	$("#hc_form").hide();
}

//隐藏按钮
function hideButton(){
	$("#editTakeStockButton").hide();
	$("#editTakeScopeButton").hide();
}

//设置库存数据
function setStockData(prefix,obj){
	
	$("#"+prefix+"kcpdsl").val(obj.kcpdsl);
	$("#"+prefix+"kcid").val(obj.id);
	$("#"+prefix+"kcbjh").html(obj.bjh);
	$("#"+prefix+"kczwms").html(obj.zwms);
	$("#"+prefix+"kcywms").html(obj.ywms);
	$("#"+prefix+"kcck").html($("#ckid").find("option:selected").text());
	$("#"+prefix+"kcgljb").val(obj.gljb);
	$("#"+prefix+"kcgljbName").html(DicAndEnumUtil.getEnumName('supervisoryLevelEnum',obj.gljb));
	$("#"+prefix+"kckwh").html(obj.kwh);
	$("#"+prefix+"kckcsl").html(obj.kcsl);
	$("#"+prefix+"kcsnpch").attr("title",StringUtil.title(StringUtil.escapeStr(obj.pch?obj.pch:(obj.sn?obj.sn:'')),25));
	$("#"+prefix+"kcsnpch").html(StringUtil.subString(StringUtil.escapeStr(obj.pch?obj.pch:(obj.sn?obj.sn:'')),25));
	$("#"+prefix+"kchcly").html(obj.hcly);
	$("#"+prefix+"kcxkzh").attr("title",StringUtil.title(StringUtil.escapeStr(obj.xkzh),25));
	$("#"+prefix+"kcxkzh").html(StringUtil.subString(StringUtil.escapeStr(obj.xkzh),25));
	$("#"+prefix+"kchjsm").html(indexOfTime(obj.hjsm));
	$("#"+prefix+"kcshzh").attr("title",StringUtil.title(StringUtil.escapeStr(obj.shzh),25));
	$("#"+prefix+"kcshzh").html(StringUtil.subString(StringUtil.escapeStr(obj.shzh),25));
	$("#"+prefix+"kcshzwz").attr("title",StringUtil.title(StringUtil.escapeStr(obj.shzwz),20));
	$("#"+prefix+"kcshzwz").html(StringUtil.subString(StringUtil.escapeStr(obj.shzwz),20));
	$("#"+prefix+"kctsn").attr("title",StringUtil.title(StringUtil.escapeStr(obj.tsn),25));
	$("#"+prefix+"kctsn").html(StringUtil.subString(StringUtil.escapeStr(obj.tsn),25));
	$("#"+prefix+"kctso").attr("title",StringUtil.title(StringUtil.escapeStr(obj.tso),25));
	$("#"+prefix+"kctso").html(StringUtil.subString(StringUtil.escapeStr(obj.tso),25));
	$("#"+prefix+"kccfyq").html(obj.cfyq);
}

//设置航材数据
function setMaterialData(obj){
	$("#hcbjid").val(obj.id);
	$("#hcgljb").val(obj.gljb);
	$("#hcbjh").html(obj.bjh);
	$("#hczwms").html(obj.zwms);
	$("#hcywms").html(obj.ywms);
	$("#hcck").html($("#ckid").find("option:selected").text());
	$("#hcgljbName").html(DicAndEnumUtil.getEnumName('supervisoryLevelEnum',obj.gljb));
}

//设置航材数据(编辑)
function setMaterialEditData(obj){

	$("#vhcgljb").val(obj.gljb);
	$("#vhcbjh").html(obj.bjh);
	$("#vhczwms").html(obj.zwms);
	$("#vhcywms").html(obj.ywms);
	$("#vhcck").html($("#ckid").find("option:selected").text());
	$("#vhcgljbName").html(DicAndEnumUtil.getEnumName('supervisoryLevelEnum',obj.gljb));
	$("#vhckw").empty();
	loadSelect();
	$("#vhckw").val(obj.kwid);
	$("#vhcpdsl").val(obj.kcpdsl);
	if(obj.gljb == 2){
		$("#vhcsnpch").val(obj.pch?obj.pch:'');
	}else{
		$("#vhcsnpch").val(obj.sn?obj.sn:'');
	}
	$("#vhcsnpch").val(obj.pch?obj.pch:(obj.sn?obj.sn:''));
	$("#vhchcly").val(obj.hcly);
	$("#vhcxkzh").val(obj.xkzh);
	$("#vhchjsm").val(indexOfTime(obj.hjsm));
	$("#vhcshzh").val(obj.shzh);
	$("#vhcshzwz").val(obj.shzwz);
	$("#vhctsn").val(obj.tsn);
	$("#vhctso").val(obj.tso);
	$("#vhccfyq").val(obj.cfyq);
	inithcly1();
}

function inithcly(){
	$("#vhchcly").empty();
	DicAndEnumUtil.registerDic('85',"vhchcly",$("#dprtcode_search").val());
}

//清空盘点差异(库存)
function clearTakeStockDiff(type){
	$("#search_value").val('');
	$('#search_value').typeahead('destroy');
	if(type != 1){
		$("#hcbjid").val('');
		$("#hcgljb").val('');
		$("#hcpdsl").val('');
		$("#hcbjh").html('');
		$("#hczwms").html('');
		$("#hcywms").html('');
		$("#hcck").html('');
		$("#hcgljbName").html('');
		$("#hckw").empty();
		loadSelect();
		$("#hcsnpch").val('');
		$("#hchcly").val(1);
		$("#hcxkzh").val('');
		$("#hchjsm").val('');
		$("#hcshzh").val('');
		$("#hcshzwz").val('');
		$("#hctsn").val('');
		$("#hctso").val('');
		$("#hccfyq").val('');
	}
	if(type != 2){
		$("#kcid").val('');
		$("#kcpdsl").val('');
		$("#kcbjh").html('');
		$("#kczwms").html('');
		$("#kcywms").html('');
		$("#kcck").html('');
		$("#kcgljb").val('');
		$("#kcgljbName").html('');
		$("#kckwh").html('');
		$("#kckcsl").html('');
		$("#kcsnpch").html('');
		$("#kchcly").html('');
		$("#kcxkzh").html('');
		$("#kchjsm").html('');
		$("#kcshzh").html('');
		$("#kcshzwz").html('');
		$("#kctsn").html('');
		$("#kctso").html('');
		$("#kccfyq").html('');
	}
}

//清空盘点单
function clearTakeStockForm(){
	$("#vpddh").html('');
	$("#vpdrid").val('');
	$("#vpdbmid").val('');
	$("#vpdrname").html('');
	$("#vksrq").html('');
	$("#vckname").html('');
	$("#vbz").html('');
	$("#vpdzt").val('');
}

//清空盘点详情
function clearTakeStockDetail(){
	clearTakeStockForm();
	$("#takeScope").empty();
	$("#takeStockDetailList").empty();
	$("#takeStockDetailPagination").empty();
	$("#pdpz").html("0");
	$("#pdjls").html("0");
	$("#czcyjls").html("0");
	$("#takeStockDetailList").append("<tr><td colspan=\"11\" class='text-center'>暂无数据 No data.</td></tr>");
	hideButton();
}

//清空记录
function clearTakeStockRecord(){
	$("#pdpz").html(0);
	$("#pdjls").html(0);
	$("#czcyjls").html(0);
}
//清空插件
function checkValue(obj){
	if(obj.value == null || obj.value == ''){
		$('#search_value').typeahead('destroy');
	}
}
//提交状态的盘点单隐藏按钮
function clearButton(){
	hideButton();
	$('#cywh').hide();
	$('#submit').hide();
}

//保存状态的盘点单显示按钮
function viewClearButton(){
	showButton();
	if(checkBtnPermissions('aerialmaterial:takestock:main:04')){
		$('#cywh').show();
	}
	if(checkBtnPermissions('aerialmaterial:takestock:main:05')){
		$('#submit').show();
	}
}

//维护差异输入数字和小数,保留两位
function clearFromDiff(obj){
	clearNoNumTwo(obj);
	var type = $("input[name='DiffType']:checked").val();
	if(type == 1){
		var kcgljb = $("#kcgljb").val();
		if(kcgljb == 3){
	  		obj.value = 0;
		}
		var kcid = $("#kcid").val();
		if(kcid == null || kcid == ''){
			obj.value = '';
		}
	}else{
		var hcgljb = $("#hcgljb").val();
		if(hcgljb == 3){
	  		obj.value = 1;
		}
		var hcbjid = $("#hcbjid").val();
		if(hcbjid == null || hcbjid == ''){
			obj.value = '';
		}
	}
}

//维护库存输入数字和小数,保留两位
function clearStockDiffNum(obj){
	clearNoNumTwo(obj);
	var kcgljb = $("#vkcgljb").val();
	if(kcgljb == 3 && obj.value > 1){
  		obj.value = 0;
	}
}

//维护航材输入数字和小数,保留两位
function clearMaterialDiffNum(obj){
	clearNoNumTwo(obj);
	var hcgljb = $("#vhcgljb").val();
	if(hcgljb == 3 && obj.value > 1){
  		obj.value = 1;
	}
}

function clearNoNumber(obj){
    //先把非数字的都替换掉，除了数字
    obj.value = obj.value.replace(/[^\d]/g,"");
    if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
 		obj.value = 0;
 	 }
}

//只能输入数字和小数,保留两位
function clearNoNumTwo(obj){
     //先把非数字的都替换掉，除了数字和.
     obj.value = obj.value.replace(/[^\d.]/g,"");
     //必须保证第一个为数字而不是.
     obj.value = obj.value.replace(/^\./g,"");
     //保证只有出现一个.而没有多个.
     obj.value = obj.value.replace(/\.{2,}/g,".");
     //保证.只出现一次，而不能出现两次以上
     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
     
     var str = obj.value.split(".");
     if(str.length > 1){
    	 if(str[1].length > 2){
    		 obj.value = str[0] +"." + str[1].substring(0,2);
    	 }
     }
     if(obj.value.length > 1 && obj.value.substring(0,1) == 0){
  		 if(obj.value.substring(1,2) != "."){
  			obj.value = 0;
  		 }
  	 }
}

//只能输入数字和小数
function clearNoNum(obj){
     //先把非数字的都替换掉，除了数字和.
     obj.value = obj.value.replace(/[^\d.]/g,"");
     //必须保证第一个为数字而不是.
     obj.value = obj.value.replace(/^\./g,"");
     //保证只有出现一个.而没有多个.
     obj.value = obj.value.replace(/\.{2,}/g,".");
     //保证.只出现一次，而不能出现两次以上
     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}

/*//回车事件控制
document.onkeydown = function(event){
	e = event ? event :(window.event ? window.event : null); 
	if(e.keyCode==13){
		if($("#search_value").is(":focus")){
			searchMaterial();   
		}
	}
}*/
