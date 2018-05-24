var yjtsJb1Hj = '';
var yjtsJb2Hj = '';
var yjtsJb3Hj = '';
var yjtsJb1Tool = '';
var yjtsJb2Tool= '';
var yjtsJb3Tool= '';
$(document).ready(function(){
		Navigation(menuCode,"","");//初始化导航
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",
				function() {
					$(this).next().focus();
		});
		$("input[name='date-picker']").datepicker({
			autoclose : true,
			clearBtn : true
		});
		//初始化管理级别,物料类别,来源基础数据
		initBasicData();
		//初始化当前用户有权限的仓库
		initStore(userJgdm)
		//初始化列表数据
		goPage(1, "auto", "desc");// 开始的加载默认的内容
		
	    // 初始化导入
	    importModal.init({
		    importUrl:"/aerialmaterial/stock/excelImport",
		    downloadUrl:"/common/templetDownfile/8",
			callback: function(data){
				// 刷新库存信息
				 goPage(1,"auto","desc");//开始的加载默认的内容 
				 $("#ImportModal").modal("hide");
			}
		});
	})
	
	function importExcel(){	
		 importModal.show();
	}
	
// 跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
function search(){
 TableUtil.resetTableSorting("inner_store_list_table"); //重置排序图标
	var pgd = $("th[name='pgd']");
	if(pgd.hasClass("upward")) {
		pgd.removeClass("upward").addClass("downward");
	}
	var lywj = $("th[name='lywj']");
	if(lywj.hasClass("upward")) {
		lywj.removeClass("upward").addClass("downward");
	}
	goPage(1, "auto", "desc");
}	
function goPage(pageNumber, sortType, sequence) {
	AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
}	
//带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber, sortType, sequence) {
	var obj=gatherSearchParam();
	pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
	obj.pagination = pagination;
	if($("#isTool").val()=='true'){//区分是航材库内查询还是工具库内查询
		obj.paramsMap.isTool=true	
	}else if($("#isTool").val()=='false'){
		obj.paramsMap.isTool=false
	}
	startWait();
	AjaxUtil
			.ajax({
				
				url : basePath + "/material/stock/material/queryInnerList",
				type : "post",
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				data : JSON.stringify(obj),
				success : function(data) {
					finishWait();
					if (data.total > 0) {
						 yjtsJb1Hj =data.monitorsettingsHj.yjtsJb1;
						 yjtsJb2Hj =data.monitorsettingsHj.yjtsJb2;
						 yjtsJb3Hj =data.monitorsettingsHj.yjtsJb3;
						 yjtsJb1Tool =data.monitorsettingsTool.yjtsJb1;
						 yjtsJb2Tool =data.monitorsettingsTool.yjtsJb2;
						 yjtsJb3Tool =data.monitorsettingsTool.yjtsJb3;
						 appendContentHtml(data.rows);
						  new Pagination({
							renderTo : "inner_search_pagination",
							data : data,
							sortColumn : sortType,
							orderType : sequence,
							extParams : {},
							goPage : function(a, b, c) {
								AjaxGetDatasWithSearch(a, b, c);
							}// ,
						});
						// 标记关键字
						   if($("#isTool").val()=='true'){//区分是航材库内查询还是工具库内查询
						     	signByKeyword($("#keyword").val(),[5,6,7,8,14,17,18,27,28,29,31],"#storeInnerList tr td");
							}else if($("#isTool").val()=='false'){
								signByKeyword($("#keyword").val(),[4,5,6,7,13,16,17,26,27,28,30],"storeInnerLists tr td");
							}
					
					} else {
						$("#storeInnerList").empty();
						$("#inner_search_pagination").empty();
						if($("#isTool").val()=='true'){
							$("#storeInnerList").append("<tr class='text-center'><td colspan=\"31\">暂无数据 No data.</td></tr>");
						}else{
							$("#storeInnerList").append("<tr class='text-center'><td colspan=\"30\">暂无数据 No data.</td></tr>");

						}
					}
					new Sticky({
						tableId : 'inner_store_list_table'
					});
				}
			});

}

function appendContentHtml(list) {
	var htmlContent = '';
	$.each(list,function(index, row) {
		htmlContent = htmlContent + "<tr onclick='showStyle(this)'>";
		htmlContent = htmlContent + "<td class='text-center'><a href=\"javascript:viewBulletin('"
		+row.ckh + "','" + row.id + "')\">" + (index+1)+ "</a></td>";
		//货架寿命标识
		htmlContent = htmlContent + getWarningColor(row.paramsMap.hjqx,yjtsJb1Hj,yjtsJb2Hj,yjtsJb3Hj);
		//工具校验标识
		if($("#isTool").val()=='true'){
		 htmlContent = htmlContent + getWarningColor(row.paramsMap.toolqx,yjtsJb1Tool,yjtsJb2Tool,yjtsJb3Tool);
		}
		htmlContent = htmlContent + "<td class='text-center' title='" + StringUtil.escapeStr(row.qczt) + "'>"+ StringUtil.escapeStr(row.qczt) + "</td>";		
		htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.bjh)+"'><a href='javascript:void(0);' onclick=\"viewDetail('"+row.bjid + "')\">"+StringUtil.escapeStr(row.bjh)+"</a></td>";
		htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(row.hCMainData?row.hCMainData.ywms:'')+" "+StringUtil.escapeStr(row.hCMainData?row.hCMainData.zwms:'') + "'>"+StringUtil.escapeStr(row.hCMainData?row.hCMainData.ywms:'')+" "+StringUtil.escapeStr(row.hCMainData?row.hCMainData.zwms:'') + "</td>";
		htmlContent = htmlContent + "<td class='text-left' title='" + StringUtil.escapeStr(row.pch) + "'>"+ StringUtil.escapeStr(row.pch) + "</td>";
		htmlContent = htmlContent + "<td class='text-left' title='" + StringUtil.escapeStr(row.sn) + "'>"+ StringUtil.escapeStr(row.sn) + "</td>";
		htmlContent = htmlContent + "<td class='text-left' title='" +  StringUtil.escapeStr(row.ckh) +" "+StringUtil.escapeStr(row.ckmc)+" "+StringUtil.escapeStr(row.kwh) + "'>"+ StringUtil.escapeStr(row.ckh) +" "+StringUtil.escapeStr(row.ckmc)+" "+StringUtil.escapeStr(row.kwh)+"</td>";
		if(row.djsl && row.djsl > 0){
			htmlContent = htmlContent+ "<td class='text-center' title='"+StringUtil.escapeStr(row.kcsl-row.djsl)+"/"+StringUtil.escapeStr(row.kcsl) +" "+(row.hCMainData?(row.hCMainData.jldw||''):'')+"'><a href='javascript:void(0);' onclick=\"viewhis('"+row.id + "')\">"+StringUtil.escapeStr(row.kcsl-row.djsl)+"/"+StringUtil.escapeStr(row.kcsl)+" "+(row.hCMainData?(row.hCMainData.jldw||''):'')+"</a></td>";	
		}else{
			htmlContent = htmlContent+ "<td class='text-center' title='"+StringUtil.escapeStr(row.kcsl) +" "+(row.hCMainData?(row.hCMainData.jldw||''):'')+"'>"+StringUtil.escapeStr(row.kcsl)+" "+(row.hCMainData?(row.hCMainData.jldw||''):'')+"</td>";	

		}
	  
		
		//成本
		htmlContent = htmlContent + "<td class='text-left' title='" + StringUtil.escapeStr((row.materialBatchInfo?(((row.materialBatchInfo.cb||row.materialBatchInfo.cb==0)?row.materialBatchInfo.cb.toFixed(2):"")||""):"")+" "+(row.materialBatchInfo?(row.materialBatchInfo.cbbz||""):"")) + "'>"+ StringUtil.escapeStr((row.materialBatchInfo?(((row.materialBatchInfo.cb||row.materialBatchInfo.cb==0)?row.materialBatchInfo.cb.toFixed(2):"")||""):"")+" "+(row.materialBatchInfo?(row.materialBatchInfo.cbbz||""):"")) + "</td>";	
		//价值
//		htmlContent = htmlContent + "<td class='text-left' title='" + StringUtil.escapeStr((row.materialBatchInfo?(((row.materialBatchInfo.jz||row.materialBatchInfo.jz==0)?row.materialBatchInfo.jz.toFixed(2):"")||""):"")+" "+(row.materialBatchInfo?(row.materialBatchInfo.jzbz||""):"")) + "'>"+ StringUtil.escapeStr((row.materialBatchInfo?(((row.materialBatchInfo.jz||row.materialBatchInfo.jz==0)?row.materialBatchInfo.jz.toFixed(2):"")||""):"")+" "+(row.materialBatchInfo?(row.materialBatchInfo.jzbz||""):"")) + "</td>";
		
		htmlContent = htmlContent + "<td class='text-center' title='" + StringUtil.escapeStr(row.hjsm == null ? "": row.hjsm.substring(0, 10)) + "'>" + StringUtil.escapeStr(row.hjsm == null ? "" : row.hjsm.substring(0, 10)) + "</td>";
		if(row.propertyRight){
			htmlContent = htmlContent + "<td class='text-left' title='" +  StringUtil.escapeStr(row.propertyRight.cqbh)+" "+StringUtil.escapeStr(row.propertyRight.fjzch) +" "+StringUtil.escapeStr(row.propertyRight.gsmc) + "'>"+ StringUtil.escapeStr(row.propertyRight.cqbh)+" "+StringUtil.escapeStr(row.propertyRight.fjzch) +" "+StringUtil.escapeStr(row.propertyRight.gsmc)+"</td>";

		}else{
			htmlContent = htmlContent +"<td class='text-center'></td>"

		}
		htmlContent = htmlContent + "<td class='text-left' title='" + StringUtil.escapeStr(row.grn) + "'>"+ StringUtil.escapeStr(row.grn) + "</td>";
		if(row.cghtid){
			htmlContent += "<td class='text-center' title='" + StringUtil.escapeStr(row.htbhCg) + "'><a href='javascript:void(0);' onclick=\"viewContract('"+row.cghtid + "')\">"+ StringUtil.escapeStr(row.htbhCg) + "</a></td>";
		}else{
			htmlContent += "<td class='text-center' title='" + StringUtil.escapeStr(row.htbhCg) + "'>"+ StringUtil.escapeStr(row.htbhCg) + "</td>";
		}
//		htmlContent = htmlContent + "<td class='text-center' title='" + DicAndEnumUtil.getEnumName("stockStatusEnum",StringUtil.escapeStr(row.zt)) + "'>"+ DicAndEnumUtil.getEnumName("stockStatusEnum",StringUtil.escapeStr(row.zt))+ "</td>";
		htmlContent = htmlContent + "<td class='text-center' title='" + StringUtil.escapeStr(row.hcly)+ "'>"+ StringUtil.escapeStr(row.hcly) + "</td>";
		htmlContent = htmlContent + "<td class='text-left' title='" + StringUtil.escapeStr(row.cfyq) + "'>"+ StringUtil.escapeStr(row.cfyq) + "</td>";
		htmlContent = htmlContent + "<td class='text-left' title='" + StringUtil.escapeStr(row.bz) + "'>"+ StringUtil.escapeStr(row.bz) + "</td>";
		htmlContent = htmlContent + "<td class='text-center' title='" + StringUtil.escapeStr(row.rksj == null ? "": row.rksj.substring(0, 10)) + "'>" + StringUtil.escapeStr(row.rksj == null ? "" : row.rksj.substring(0, 10)) + "</td>";
		htmlContent = htmlContent + "<td class='text-left' title='" +  StringUtil.escapeStr(row.rkr_user?row.rkr_user.realname:'')+" "+StringUtil.escapeStr(row.rkr_user?row.rkr_user.realname:'')+ "'>"+ StringUtil.escapeStr(row.rkr_user?row.rkr_user.username:'')+" "+StringUtil.escapeStr(row.rkr_user?row.rkr_user.realname:'')+"</td>";
		htmlContent = htmlContent + "<td class='text-center' title='" + StringUtil.escapeStr(row.scrq == null ? "": row.scrq.substring(0, 10)) + "'>" + StringUtil.escapeStr(row.scrq == null ? "" : row.scrq.substring(0, 10)) + "</td>";
		htmlContent = htmlContent + "<td class='text-right' title='" + StringUtil.escapeStr(TimeUtil.convertToHour(row.tsn, TimeUtil.Separator.COLON)) + "'>"+ StringUtil.escapeStr(TimeUtil.convertToHour(row.tsn, TimeUtil.Separator.COLON)) + "</td>";
		htmlContent = htmlContent + "<td class='text-right' title='" + StringUtil.escapeStr(row.csn||"")  + "'>"+ StringUtil.escapeStr(row.csn) + "</td>";
		htmlContent = htmlContent + "<td class='text-right' title='" + StringUtil.escapeStr(TimeUtil.convertToHour(row.tso, TimeUtil.Separator.COLON)) + "'>"+ StringUtil.escapeStr(TimeUtil.convertToHour(row.tso, TimeUtil.Separator.COLON)) + "</td>";
		htmlContent = htmlContent + "<td class='text-right' title='" + StringUtil.escapeStr(row.cso||"")  + "'>"+ StringUtil.escapeStr(row.cso) + "</td>";
		if($("#isTool").val()=='false'){//如果是航材库内查询
			htmlContent = htmlContent + "<td class='text-center' title='" + DicAndEnumUtil.getEnumName("materialTypeEnum",StringUtil.escapeStr(row.hCMainData?row.hCMainData.hclx:''))+ "'>"+ DicAndEnumUtil.getEnumName("materialTypeEnum",StringUtil.escapeStr(row.hCMainData?row.hCMainData.hclx:'')) + "</td>";
		}else{//工具库内查询
			htmlContent = htmlContent + "<td class='text-center' title='" + DicAndEnumUtil.getEnumName("materialToolSecondTypeEnum",StringUtil.escapeStr(row.hCMainData?row.hCMainData.hclxEj:''))+ "'>"+ DicAndEnumUtil.getEnumName("materialToolSecondTypeEnum",StringUtil.escapeStr(row.hCMainData?row.hCMainData.hclxEj:'')) + "</td>";
		}
			
		htmlContent = htmlContent + "<td class='text-left' title='" + StringUtil.escapeStr(row.hCMainData?row.hCMainData.cjjh:'') + "'>"+ StringUtil.escapeStr(row.hCMainData?row.hCMainData.cjjh:'') + "</td>";
		htmlContent = htmlContent + "<td class='text-left' title='" + StringUtil.escapeStr(row.hCMainData?(row.hCMainData.xingh||""):"") + "'>"+ StringUtil.escapeStr(row.hCMainData?(row.hCMainData.xingh||""):"") + "</td>";
		htmlContent = htmlContent + "<td class='text-left' title='" + StringUtil.escapeStr(row.hCMainData?(row.hCMainData.gg||""):"") + "'>"+ StringUtil.escapeStr(row.hCMainData?(row.hCMainData.gg||""):"") + "</td>";
		htmlContent = htmlContent + "<td class='text-center' title='" + DicAndEnumUtil.getEnumName("supervisoryLevelEnum",StringUtil.escapeStr(row.hCMainData?row.hCMainData.gljb:''))+ "'>"+ DicAndEnumUtil.getEnumName("supervisoryLevelEnum",StringUtil.escapeStr(row.hCMainData?row.hCMainData.gljb:'')) + "</td>";
		htmlContent = htmlContent + "<td class='text-left' title='" + StringUtil.escapeStr(row.hCMainData?(row.hCMainData.zzcs||""):"") + "'>"+ StringUtil.escapeStr(row.hCMainData?(row.hCMainData.zzcs||""):"") + "</td>";
		htmlContent = htmlContent + "</tr>";
	});
	$("#storeInnerList").empty();
	$("#storeInnerList").append(htmlContent);
	 refreshPermission(); 


}
function gatherSearchParam(){
var searchParams={};
var paramsMap={};
searchParams.cqid=$.trim($("#cqid_search").val()); //产权
searchParams.ckid=$.trim($("#ck_search").val());	//仓库
searchParams.keyword=$.trim($("#keyword").val());//关键字
searchParams.bjh=$.trim($("#bjh_search").val());//部件号
searchParams.pch=$.trim($("#pch_search").val());//批次号
searchParams.sn=$.trim($("#sn_search").val());//序列号
searchParams.gg=$.trim($("#gg_xh_search").val());//规格/型号
searchParams.qczt=$.trim($("#qczt_search").val());//规格/型号
searchParams.htbhCg=$.trim($("#htbh_search").val());//合同编号
paramsMap.dprtcode=$.trim($("#dprtcode_search").val());//组织机构
paramsMap.hclx=$.trim($("#wllb_search").val())//物料类别
paramsMap.gljb=$.trim($("#gljb_search").val())//管理级别	
paramsMap.userId=userId;  //当前用户id
if($("#isTool").val()=='true'){
	paramsMap.isTool=true
}else if($("#isTool").val()=='false'){
	paramsMap.isTool=false
}   
searchParams.kwh=$.trim($("#kwh_search").val())//库位号
searchParams.grn=$.trim($("#grn_search").val())//grn
searchParams.hcly=$.trim($("#hcly_search").val())//航材来源
searchParams.zt=$.trim($("#zt_search").val())//航材来源
var sjrq=$("#sjrq_search").val();
var scrq=$("#scrq_search").val();

if(sjrq){
	 var sjrqBegin = sjrq.substring(0,10)+" 00:00:00";
	 var sjrqEnd = sjrq.substring(13,23)+" 23:59:59";
	 paramsMap.rksjBegin = sjrqBegin;  
	 paramsMap.rksjEnd = sjrqEnd;     
}

if(scrq){
	 var scrqBegin = scrq.substring(0,10)+" 00:00:00";
	 var scrqEnd = scrq.substring(13,23)+" 23:59:59";
	 paramsMap.scrqBegin = scrqBegin;  
	 paramsMap.scrqEnd = scrqEnd;     
}

searchParams.zzcs=$.trim($("#zzcs_search").val()); //制造厂商

paramsMap.cjjh=$.trim($("#cjjh_search").val())//厂家件号
searchParams.paramsMap=paramsMap;    
   return searchParams;
}

function viewBulletin(ckh,id){
	var permissioncode = 'material:stock:material:inside:save';  //进入编辑页面权限按钮
	//校验数据是否已经更改
	var isExist=false;
	AjaxUtil.ajax({
		url: basePath + "/material/stock/material/validate4Exist",
		async:false,
		type:"post",
		dataType:"json",
		data:{'kcid':id},
		success:function(data){
			if(!data){
				isExist=true;			
			}
		}
	});
	
	if(isExist){
		AlertUtil.showMessage('数据已经更改,请刷新后再操作!');
		return ;
	}
	
	if (checkBtnPermissions(permissioncode)) {// 如果有编辑权限,进入编辑页面
		$("input[id$='view'],select[id$='view'],textarea[id$='view']")
		.not("#cb_view,#jz_view,#jzbz_view,#hcly_view,#cbbz_view,#cqbh_view,#hjsm_view,#scrq_view,#grn_view,#tsn_view,#tso_view,#csn_view,#cso_view,#cfyq_view,#bz_view,#qczt_view")
		.attr("disabled", "disabled");
		$("#cqbutton").removeAttr("disabled");
		inside_Modal.show({
			id : id,
			dprtcode : $("#dprtcode_search").val(),
			callback : function(data) {
				if (data)
			AlertUtil.showMessage('保存成功!');
				search();
				refreshPermission();
			}
		});
	} else {// 查看界面
		window.open(basePath +"/material/stock/material/view?kcid="+id+"&isTool="+$("#isTool").val());
	}	
}



function initStore(dprtcode){
	var htmlOption="<option value=''>显示全部</option>";
	for(var i=0;i<userStoreList.length;i++){
		var dprtcode=dprtcode;
		if(userStoreList[i].dprtcode==dprtcode){
//			if($("#isTool").val()=='false'&&userStoreList[i].cklb=='5'){//排除工具仓库
//				  continue;
//			}else if($("#isTool").val()=='true'&&userStoreList[i].cklb=='5'){
//				htmlOption+="<option value='"+userStoreList[i].id+"'>"+userStoreList[i].ckh+" "+userStoreList[i].ckmc+"</option>";
//				  break;
//			}			
			htmlOption+="<option value='"+userStoreList[i].id+"'>"+userStoreList[i].ckh+" "+userStoreList[i].ckmc+"</option>";	
		}
	}
	$("#ck_search").empty();
	$("#ck_search").append(htmlOption);	
}	
function initKw(){
	var option = "<option value=''>显示全部 All</option>";
	var ckid = $("#ck_search").val();
	$.each(userStoreList, function(index, row){
		if(row.id == ckid){
			for(var i = 0 ; i < row.storageList.length ; i++){
		 		var storage = row.storageList[i];
		 		option += '<option value="'+StringUtil.escapeStr(storage.kwh)+'">'+StringUtil.escapeStr(storage.kwh)+'</option>';
		 	}
		}
	});
	$("#kwh_search").html(option);
	$("#kwh_search").selectpicker("refresh");
		
}
function searchByDprtcode(){
	 //search();
    //刷新仓库数据
	var dprtcode=$("#dprtcode_search").val();
	initStore(dprtcode);
	//刷新来源数据
	inithcly();
}
function initBasicData(){
	var gllbOptions=getOptions(DicAndEnumUtil.getEnum("supervisoryLevelEnum"));
	var wllbOptions=getOptions(DicAndEnumUtil.getEnum("materialTypeEnum"));
	if($("#isTool").val()=='true'){//如果是工具
	    	wllbOptions=getOptions(DicAndEnumUtil.getEnum("materialToolSecondTypeEnum"));	
	    }
//	var lyOptions=getOptions(DicAndEnumUtil.getEnum("materialSrouceEnum"));
		
	$("#gljb_search").empty();
	$("#wllb_search").empty();
	$("#qczt_search").html('<option value="">显示全部</option>');
//	$("#hcly_search").empty();
	$("#gljb_search").append(gllbOptions);
	$("#wllb_search").append(wllbOptions);
	DicAndEnumUtil.registerDic("86", "qczt_search", $("#dprtcode_search").val());
//	$("#hcly_search").append(lyOptions);

	inithcly();
}

function inithcly(){
	$("#hcly_search").empty();
	$("#hcly_search").html("<option value=''>显示全部</option>");
	DicAndEnumUtil.registerDic('85',"hcly_search",$("#dprtcode_search").val());
}
function getOptions(ars){
	var html="<option value=''>显示全部</option>";	
	if(ars&&ars instanceof Array){
		 for(var i=0;i<ars.length;i++){
			 html+="<option value='"+ars[i].id+"'>"+ars[i].name+"</option>";
		 }	
	}else{
		html="";
	}
	  return html
}
	
function moreSearch(){
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		App.resizeHeight();
		$("#icon").removeClass("icon-caret-down");
		$("#icon").addClass("icon-caret-up");
	} else {
		$("#divSearch").css("display", "none");
		App.resizeHeight();
		$("#icon").removeClass("icon-caret-up");
		$("#icon").addClass("icon-caret-down");
	}
    }
function viewDetail(bjid){
	window.open(basePath+"/material/material/view?id="+bjid);	
}
function viewContract(htid){
	window.open(basePath+"/material/contract/view/"+htid);	
}
function searchreset(){
	$("#cqid_search").val("");
	$("#cqbh_search").val("");
	$("#ck_search").val("");
	$("#keyword").val("");
	$("#bjh_search").val("");
	$("#pch_search").val("");
	$("#sn_search").val("");
	$("#gg_xh_search").val("");
	$("#dprtcode_search").val(userJgdm);
	$("#wllb_search").val("");
	$("#gljb_search").val("");
	$("#kwh_search").val("");
	$("#kwh_search").selectpicker("refresh");
	$("#grn_search").val("");
	$("#hcly_search").val("");
	$("#sjrq_search").val("");
	$("#scrq_search").val("");
	$("#zzcs_search").val("");
	$("#cjjh_search").val("");
	$("#zt_search").val("");
	$("#qczt_search").val("");
	$("#htbh_search").val("");
}

function loadCq(){
	cqModal.show({
		selected:$("#cqid_search").val(),
		dprtcode:$("#dprtcode_search").val(),
		callback:function(data){
			$("#cqid_search").val(data.id);
			$("#cqbh_search").val(data.cqbh);
		}
		
	});	
}

function viewhis(kcid){
	//查看冻结履历
	frozenHistoryModal.show({
	        id:kcid       //库存id	
	})
}

function getWarningColor(time,t1,t2,t3){
	var htmlappend="";
	var lxMessage = '有效期';
	if(time<=t1){//红 色
	 htmlappend ="<td style='text-align:center;vertical-align:middle;' class='text-center' title='剩余"+lxMessage+"&lt;="+t1+"天"+"' ><i class='iconnew-lightbulbnew' style='text-vertical:middle;font-size:16px;color:" + warningColor.level1+ "' /></td>";
	}else if(t1<time&&time<=t2){//黄色
		htmlappend = "<td class='text-center' title='"
				+ t1
				+ "&lt;剩余"
				+ lxMessage
				+ "&lt;="
				+ t2
				+ "天"
				+ "' ><i class='iconnew-lightbulbnew' style='text-vertical:middle;font-size:16px;color:"
				+ warningColor.level2 + "' /></td>";
		
	}else{
		htmlappend ="<td style='text-align:center;vertical-align:middle;' class='text-center'></td>";
	}
	
	
	  return htmlappend;
	
}
function orderBy(obj){
	// 字段排序
		var orderStyle = $("#" + obj + "_order").attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
		});
		$("#" + obj + "_" + "order").removeClass("sorting");
		var orderType = "asc";
		if (orderStyle.indexOf("sorting_asc") >= 0) {
			$("#" + obj + "_" + "order").addClass("sorting_desc");
			orderType = "desc";
		} else {
			$("#" + obj + "_" + "order").addClass("sorting_asc");
			orderType = "asc";
		}
		orderStyle = $("#" + obj + "_order").attr("class");
		var currentPage = $("#pagination li[class='active']").text();
		goPage(currentPage,obj,orderStyle.split("_")[1]);
}

function exportExcel(){	
	var param=gatherSearchParam();
	window.open(basePath+"/material/stock/material/1/StockInfo.xls?json="+JSON.stringify(param));
}


function showStyle(obj){
	$(obj).addClass("bg_tr");
	$("#storeInnerList tr").not(obj).each(function(){
		$(this).removeClass("bg_tr");
	});
	
}
