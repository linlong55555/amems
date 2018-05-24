$(document).ready(function(){
		Navigation(menuCode,"","");//初始化导航
		
		 //收缩效果
		$('#borrow_history').on('show.bs.collapse', function () {
			$('#borrow_history').siblings("div").find("input.selectCheckbox").prop("checked",true);
		})
		$('#borrow_history').on('hide.bs.collapse', function () {
			$('#borrow_history').siblings("div").find("input.selectCheckbox").prop("checked",false);
		})
		
		$('#certificate_info').on('show.bs.collapse', function () {
			$('#certificate_info').siblings("div").find("input.selectCheckbox").prop("checked",true);
		})
		$('#certificate_info').on('hide.bs.collapse', function () {
			$('#certificate_info').siblings("div").find("input.selectCheckbox").prop("checked",false);
		})
		
		
		$('#resume_info').on('show.bs.collapse', function () {
			$('#resume_info').siblings("div").find("input.selectCheckbox").prop("checked",true);
		})
		$('#resume_info').on('hide.bs.collapse', function () {
			$('#resume_info').siblings("div").find("input.selectCheckbox").prop("checked",false);
		})	
		
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",
				function() {
					$(this).next().focus();
		});
		$("input[name='date-picker']").datepicker({
			autoclose : true,
			clearBtn : true
		});
		
	
		//初始化管理级别,物料类别,来源基础数据
		initBasicDataOpen();
		//初始化当前用户有权限的仓库
		initStoreOpen();
		//初始化币种
		initBizOpen();		
		initData();
	})
	
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
function initBasicDataOpen(){
	var gllbOptions=getOptionsOpen(DicAndEnumUtil.getEnum("supervisoryLevelEnum"));
	var wllbOptions=getOptionsOpen(DicAndEnumUtil.getEnum("materialTypeEnum"));
	  if($("#isTool").val()=='true'){//如果是工具
	    	wllbOptions=getOptionsOpen(DicAndEnumUtil.getEnum("materialToolSecondTypeEnum"));	
	    }
//	var lyOptions=getOptionsOpen(DicAndEnumUtil.getEnum("materialSrouceEnum"));
	$("#gljb_view").empty();
	$("#wllb_view").empty();
//	$("#hcly_view").empty();
	$("#gljb_view").append(gllbOptions);
	$("#wllb_view").append(wllbOptions);
//	$("#hcly_view").append(lyOptions);
	inithclywww();
}
function inithclywww(){
	$("#hcly_view").empty();
//	$("#hcly_view").html("<option value=''>显示全部</option>");
	DicAndEnumUtil.registerDic('85',"hcly_view",$("#dprtcode_search").val());
}
function initStoreOpen(){
	var htmlOption="<option value=''></option>";
	for(var i=0;i<userStoreList.length;i++){
		var dprtcode=currentUser.jgdm;
		if(userStoreList[i].dprtcode==dprtcode){
			htmlOption+="<option value='"+userStoreList[i].id+"'>"+userStoreList[i].ckh+" "+userStoreList[i].ckmc+"</option>";	
		}
	}	
	$("#ck_view").empty();
	$("#ck_view").append(htmlOption);
}
function initBizOpen(){
	  var dicts=DicAndEnumUtil.getDict(3,$("#dprtId").val());
		var htmlOption="<option value=''></option>";
		for(var i=0;i<dicts.length;i++){
		 htmlOption+="<option value='"+dicts[i].id+"'>"+dicts[i].name+"</option>";	
			
		}
		$("#cbbz_view").empty();
		$("#cbbz_view").append(htmlOption);
		
		$("#jzbz_view").empty();
		$("#jzbz_view").append(htmlOption);
	
}
function getOptionsOpen(ars){
	var html="<option value=''></option>";
	if(ars&&ars instanceof Array){
		 for(var i=0;i<ars.length;i++){
			 html+="<option value='"+ars[i].id+"'>"+ars[i].name+"</option>";
		 }	
	}else{
		html="";
	}
	  return html
}
function initData(){
	  var kcid=$("#kcid").val();
		AjaxUtil.ajax({
			url: basePath + "/material/stock/material/queryStockById",
			type:"post",
			dataType:"json",
			data:{'kcid':kcid},
			success:function(datas){
				 setData(datas);
			}
		})
	}
function setData(datas){
	var data=datas['stock']; //库存数据
	var history=datas['stockHistory']; //库存履历
	var borrowRecords=datas['borrowRecords']; //工具借用归还履历
    if(data){
     	if(data.materialBatchInfo){
    		$("#cb_view").val(data.materialBatchInfo.cb);
    		$("#jz_view").val(data.materialBatchInfo.cb);
    		$("#cbbz_view").val(data.materialBatchInfo.cbbz);
    		$("#jzbz_view").val(data.materialBatchInfo.jzbz);
    	}else{
    		$("#cbbz_view").find("option:eq(0)").attr("selected","selected");
    		$("#jzbz_view").find("option:eq(0)").attr("selected","selected");
    	}
	    $("#bz_view").val(data.bz);
		$("#kcid_view").val(data.id);
		$("#bjh_view").val(data.bjh||"");
		$("#ckmc_view").val((data.hCMainData?data.hCMainData.ywms:"")+" "+(data.hCMainData?(data.hCMainData.zwms?data.hCMainData.zwms:''):""));
		$("#pch_view").val(data.pch||"");
		$("#sn_view").val(data.sn||"");
		$("#ck_view").val(data.ckid);
		$("#cklb_view").val(data.cklb);
		$("#ckh_view").val(data.ckh);
		$("#kw_view").val(data.kwh||"");
		$("#kwid_view").val(data.kwid);
		$("#kcsl_view").val(data.kcsl||0);
		$("#dw_view").val(data.hCMainData?(data.hCMainData.jldw||''):'');
		$("#kcsldw_view").val((data.kcsl||0)+" "+(data.hCMainData?(data.hCMainData.jldw||''):''));
		$("#djsl_view").val(data.djsl||"");
		if(data.djsl){
			$("#djsl_link").html(data.djsl+" "+(data.hCMainData?(data.hCMainData.jldw||''):''));
		}else{
			$("#djsl_link").remove();
			$("#djno").append("<input type='text' id='djsl_link' disabled='disabled' class='form-control' value='"+0+" "+(data.hCMainData?(data.hCMainData.jldw||''):'')+"'/>");
		}		
		$("#cqbh_view").val(data.propertyRight?data.propertyRight.cqbh:"");
		$("#cqid_view").val(data.cqid);
		$("#hjsm_view").val(data.hjsm?data.hjsm.substring(0, 10):"");
		$("#hcly_view").val(data.hcly||"");
		$("#grn_view").val(data.grn||"");
		$("#tsn_view").val(TimeUtil.convertToHour(data.tsn, TimeUtil.Separator.COLON)||"");
		$("#tso_view").val(TimeUtil.convertToHour(data.tso, TimeUtil.Separator.COLON)||"");
		$("#csn_view").val(data.csn||"");
		$("#cso_view").val(data.cso||"");
		$("#scrq_view").val(data.scrq?data.scrq.substring(0, 10):"");
		$("#zt_view").val(DicAndEnumUtil.getEnumName("stockStatusEnum",StringUtil.escapeStr(data.zt)));
		$("#cfyq_view").val(data.cfyq||"");
		if($("#isTool").val()=='false'){//航材物料类别
			$("#wllb_view").val(data.hCMainData?data.hCMainData.hclx:"");					
		}else{//工具物料类别
			$("#wllb_view").val(data.hCMainData?data.hCMainData.hclxEj:"");
		}
		$("#cjjh_view").val(data.hCMainData?data.hCMainData.cjjh:"");
		$("#gg_view").val(data.hCMainData?data.hCMainData.gg:"");
		$("#xh_view").val(data.hCMainData?data.hCMainData.xingh:"");
		$("#gljb_view").val(data.hCMainData?data.hCMainData.gljb:"");
		$("#zzcs_view").val(data.hCMainData?(data.hCMainData.zzcs||""):"");
		$("#maxkcs_view").val(data.hCMainData?data.hCMainData.maxKcl:"");
		$("#minkcs_view").val(data.hCMainData?data.hCMainData.minKcl:"");
		$("#sjsj_view").val(data.rksj||"");
		$("#sjr_view").val((data.rkr_user?data.rkr_user.realname:"")+" "+(data.rkr_user?data.rkr_user.username:""));
		$("#update_view").val(data.whsj||"");
		$("#update_person_view").val((data.zdr?data.zdr.realname:"")+" "+(data.zdr?data.zdr.username:""));			    	
    }

	
	var htmlHistory="";
	$("#libraryTbody").empty();
	if(history.length==0){
		htmlHistory="<tr><td class='text-center' colspan='3'>暂无数据 No data.</td></tr>";
	}else{
		for(var i=0;i<history.length;i++){
			htmlHistory+="<tr><td>"+history[i].czsj+"</td><td>"+history[i].zdr.realname+"</td><td>"+history[i].czsm+"</td></tr>"		
		}		
	}
	$("#hisTbody").empty();
	$("#hisTbody").append(htmlHistory);
	
	//如果物料类型是工具,展示借用/归还履历
	 if(data.hCMainData&&data.hCMainData.hclx=='2'){
		 $("#borrowInfo").css("display","block");
			var htmlBorrowHistory="";
			$("#borrowTbody").empty();
			if(borrowRecords.length==0){
				htmlBorrowHistory="<tr><td class='text-center' colspan='6'>暂无数据 No data.</td></tr>";
			}else{
				for(var i=0;i<borrowRecords.length;i++){
					htmlBorrowHistory+="<tr><td>"+(borrowRecords[i].jhlx=='10'?'借用':'归还')+"</td>" +
							               "<td>"+(borrowRecords[i].jy_sj?borrowRecords[i].jy_sj.substring(0,10):'')+"</td>" +
							               "<td>"+borrowRecords[i].jy_zrrmc+"</td>"+
							               "<td>"+borrowRecords[i].jy_sl+"</td>"+
							               "<td>"+(borrowRecords[i].cqjybs=='0'?'否':'是')+"</td>"+
							               "<td>"+borrowRecords[i].jy_bz+"</td></tr>"
				}		
			}
			$("#borrowTbody").empty();
			$("#borrowTbody").append(htmlBorrowHistory);
	 }else{
		 $("#borrowInfo").css("display","none");		 
	 }
	 
     //加载证书信息
	 loadCertificateInformation(data);
	
}

function showFrozenHistory(){
	//查看冻结履历
	frozenHistoryModal.show({
	        id:$("#kcid").val()       //库存id	
	})	
}

function loadCertificateInformation(data){
	 var certificateUtil = new CertificateUtil({
			parentId : "stock_detail",
			dprtcode : data.dprtcode,
			tbody : $("#store_inner_certificate_list"),
			container : "#stock_detail",
			ope_type : 3
		});
		
		var obj={
			bjh:data.bjh,	
			pch:data.pch,	
			xlh:data.sn,
			dprtcode:data.dprtcode
		};

		certificateUtil.load(obj);	
		
}

function more(e){
	borrowHistoryModal.show({
		id:$("#kcid_view").val()		
	})
	 e = e || window.event;  
	 if(e.stopPropagation) { //W3C阻止冒泡方法  
	        e.stopPropagation();  
	    } else {  
	        e.cancelBubble = true; //IE阻止冒泡方法  
	    } 
	 

}