
$(document).ready(function(){
	Navigation(menuCode,"","");//初始化导航
	initData();
	
	
});

function initData(){
	 var kcid=$("#kcid").val();
		AjaxUtil.ajax({
			url: basePath +"/aerialmaterial/outfieldstock/selectById",
			 type:"post",
			 async: false,
			 data:{
				 'id' : kcid
			 },
			 dataType:"json",
			 success:function(data){
				 finishWait();
				 setData(data.outFieldStock);
			 }
		 });
}
function initBizOpen(dprtcode){
	  var dicts=DicAndEnumUtil.getDict(3,dprtcode);
		var htmlOption="";
		for(var i=0;i<dicts.length;i++){
		 htmlOption+="<option value='"+dicts[i].id+"'>"+dicts[i].name+"</option>";	
			
		}
		return htmlOption;
	
}
function setData(obj){
	$("#bjh_view").val(obj.bjh || '');
	$("#ckmc_view").val((obj.ywms || '') && (obj.zwms || ''));
	$("#pch_view").val(obj.pch || '');
	$("#sn_view").val(obj.sn || '');
	$("#ck_view").val('外场');
	$("#kw_view").val('外场');
	$("#kcsldw_view").val(obj.kcsl || '');
	$("#djsl_view").val(obj.djsl || '0');  
	$("#kccb_view").val(obj.kccb || '');
	$("#biz_view").val(obj.biz || '');
	$("#jz_view").val(obj.jz || '');
	$("#jzbz_view").val(obj.jzbz || '');
	$("#cqbh_view").val(obj.cqbh || '');
	$("#cqbh_view").val(obj.paramsMap?obj.paramsMap.cqbh:'');//物料类别
	$("#hjsm_view").val(obj.hjsm?obj.hjsm.substring(0,10):'');
	$("#hcly_view").val(obj.hcly);//来源
	
	$("#grn_view").val(obj.grn || '');
	$("#tsn_view").val(obj.tsn || '');
	$("#tso_view").val(obj.tso || '');
	$("#csn_view").val(obj.csn || '');
	$("#cso_view").val(obj.cso || '');
	$("#scrq_view").val(obj.scrq?obj.scrq.substring(0,10):'');
	
	$("#zt_view").val(DicAndEnumUtil.getEnumName('stockStatusEnum',obj.zt));//状态
	$("#cfyq_view").val(obj.cfyq || '');
	$("#bz_view").val(obj.bz || '');
	$("#wllb_view").val(obj.paramsMap?DicAndEnumUtil.getEnumName('materialTypeEnum',obj.paramsMap.hclx):'');//物料类别
	$("#cjjh_view").val(obj.cjjh || '');
	$("#gg_view").val(obj.gg || '');
	$("#xh_view").val(obj.xh || '');
	$("#gljb_view").val(DicAndEnumUtil.getEnumName('supervisoryLevelEnum',obj.paramsMap?obj.paramsMap.gljb:''));//管理级别
	$("#maxkcs_view").val(obj.paramsMap?obj.paramsMap.max_kcl:'');
	$("#minkcs_view").val(obj.paramsMap?obj.paramsMap.min_kcl:'');
	
	$("#sjr_view").val(obj.sjr?obj.sjr.displayName:'');
	$("#sjsj_view").val(obj.rksj || '');
	$("#update_person_view").val(obj.xgr?obj.xgr.displayName:'');
	$("#update_view").val(obj.whsj || '');
	
	var hbHtml = initBizOpen(obj.dprtcode);
	$("#biz_view").empty();
	$("#biz_view").append(hbHtml);
	$("#jzbz_view").empty();
	$("#jzbz_view").append(hbHtml);
	
	zsObj = {};
	zsObj.jh = obj.bjh || '-';
	zsObj.xlh = obj.sn || '-';
	zsObj.pch = obj.pch || '-';
	if(obj.sn){
		zsObj.pch = '-';	
	}
	zsObj.zt = 1;
	zsObj.dprtcode = obj.dprtcode;
	//加载证书
	loadingZs(zsObj);
	//加载借用归还
	loadingJygh(obj.id,obj.paramsMap?obj.paramsMap.hclx:'');
	//库存履历
	loadingKcll(obj.id);
}
function loadingZs(obj){
		startWait();
		AjaxUtil.ajax({
			url: basePath+"/aerialmaterial/certificate/selectByParams",
			type: "post",
			contentType:"application/json;charset=utf-8",
			dataType:"json",
			data:JSON.stringify(obj),
			success:function(data){
				finishWait();
				if(data != null  && data.length > 0 ){
					setCertificateInfo(returndata);
				}else{
					$("#store_inner_certificate_list").empty();
					$("#store_inner_certificate_list").append('<tr class="noData"><td class="text-center" colspan="5">暂无数据 No data.</td></tr>');
				}
			}
		})
}

	function setCertificateInfo(list){
		var htmlappend="";
		$.each(list,function(index,row){
			htmlappend += "<tr>";
			htmlappend += "<style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.zjlx)+"</td>";
			htmlappend += "<td style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.zsbh)+"</td>";
			htmlappend += "<td style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.zscfwz)+"</td>";
			htmlappend += "<td style='text-align:center;vertical-align:middle;' >"+row.qsrq?row.qsrq.substr(0, 10):''+"</td>";
			/*附件*/
			if(row.paramsMap != null && row.paramsMap.attachcount != null && row.paramsMap.attachcount > 0){
				htmlappend += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
				htmlappend += '<i fjid="'+row.id+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
				htmlappend += "</td>";
			}else{
				htmlappend += "<td style='text-align:center;vertical-align:middle;' ></td>";
			}
			htmlappend += "</td>";
			htmlappend += "</tr>";	
			initWebuiPopover();
			$("#store_inner_certificate_list").empty();
			$("#store_inner_certificate_list").append(htmlappend);
		});
		
	}
	//附件列表
	function initWebuiPopover(){//初始化
		WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
			return getHistoryAttachmentList(obj);
		});
		$("#certificate_info").scroll(function(){
			$('.attachment-view').webuiPopover('hide');
		});
	}
	function getHistoryAttachmentList(obj){//获取历史附件列表
		var jsonData = [
	         {mainid : $(obj).attr('fjid'), type : '国籍登记证号'}
	    ];
		return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
	}
//加载借用归还
function loadingJygh(kcid,hclx){
	//如果物料类型是工具,展示借用/归还履历
	 if(hclx == 2){
		 $("#borrowInfo").show();
		 startWait();
			AjaxUtil.ajax({
				url: basePath+"/material/stock/material/queryAllJygh",
				type: "post",
				dataType:"json",
				data:{
					"id":kcid
				},
				success:function(data){
					finishWait();
					if(data != null && data.length > 0){
						appendJygh(data);
					}else{
						$("#borrowTbody").empty();
						$("#borrowTbody").html("<tr><td class='text-center' colspan='6'>暂无数据 No data.</td></tr>");
					}
				}
			});
			$("#borrow_history").show();
	 }else{
		 $("#borrowInfo").hide();		 
	 }
}
//拼接借用归还列表
function appendJygh(list){
	var htmlBorrowHistory="";
	$.each(list,function(index,row){
		htmlBorrowHistory += "<tr>";
		htmlBorrowHistory += "<td style='text-align:right;vertical-align:middle;'>"+row.jhlx=='10'?'借用':'归还'+"</td>";
		htmlBorrowHistory += "<td style='text-align:right;vertical-align:middle;'>"+row.jy_sj?row.jy_sj.substring(0,10):''+"</td>";
		htmlBorrowHistory += "<td style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.jy_zrrmc)+"</td>";
		htmlBorrowHistory += "<td style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.jy_sl)+"</td>";
		htmlBorrowHistory += "<td style='text-align:right;vertical-align:middle;'>"+row.cqjybs=='0'?'否':'是'+"</td>";
		htmlBorrowHistory += "<td style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.jy_bz)+"</td>";
		htmlBorrowHistory += "</tr>";
	});
	$("#borrowTbody").empty();
	$("#borrowTbody").html(htmlBorrowHistory);
}
//库存履历
function loadingKcll(kcid){
	startWait();
	AjaxUtil.ajax({
		url: basePath+"/material/stock/material/queryAllKcll",
		type: "post",
		dataType:"json",
		data:{
			"id":kcid
		},
		success:function(data){
			finishWait();
			if(data != null && data.length > 0){
				appendKcll(data);
			}else{
				$("#hisTbody").empty();
				$("#hisTbody").html("<tr><td class='text-center' colspan='3'>暂无数据 No data.</td></tr>");
			}
		}
	});
}
//拼接库存履历
function appendKcll(list){
	var htmlappend="";
	$.each(list,function(index,row){
		htmlappend += "<tr>";
		htmlappend += "<td style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.czsj)+"</td>";
		htmlappend += "<td style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.zdr.username)+" "+StringUtil.escapeStr(row.zdr.realname)+"</td>";
		htmlappend += "<td style='text-align:right;vertical-align:middle;'>"+StringUtil.escapeStr(row.czsm)+"</td>";
		htmlappend += "</tr>";	
	});
	$("#hisTbody").empty();
	$("#hisTbody").html(htmlappend);
}

