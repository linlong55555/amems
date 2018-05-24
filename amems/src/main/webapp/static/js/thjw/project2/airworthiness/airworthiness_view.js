$(function(){
	Navigation(menuCode, '查看适航性资料', 'View', 'GC-3-2 ', '朱超', '2017-08-5', '孙霁', '2017-08-18');//加载导航栏
	$('input[name=date-range-picker]').daterangepicker().prev().on("click",
			function() {
				$(this).next().focus();
	});
	$('.page-content input').attr('disabled',true);
	$('.page-content textarea').attr('disabled',true);
	$('.page-content select').attr('disabled',true); 
	
	var Ispg=$("input:radio[name='Ispg']:checked").val();
	if(Ispg == 0){
		$("#jswjpgqxDiv").hide();
	}
	var zt = $("#zt").val();
	var ztText = DicAndEnumUtil.getEnumName('airworthinessEnum',zt);
	$("#zt").val(ztText);
});

function addUser(obj){
	var jx=$(obj).val();
	if($(obj).attr("checked")){
		$("#use"+jx).css("visibility","visible");
		updateTeorList(null,jx,null,null,2);
		
	}else{
		updateTeorList(null,jx,null,null,4);
		var techoId=$("#teco"+jx).val();
		if(techoId !="add"){
			techoIds.push(techoId);
		}
		$("#use"+jx).css("visibility","hidden");
	}
}
//下载
function openAttachmentWinEdit(){
	
}

function syjxshowAndhide(){
	var userId=$("#userId").val();
	var Ispg=$("input:radio[name='Ispg']:checked").val();
	if(Ispg==1){
		$("#jxchecked").show();
	}else{
		$("#jxchecked input[name='tecoid']").each(function () {
			if($(this).val()!="add"){
				techoIds.push($(this).val());
				$(this).val("add");
			}
		});
		$("#jxchecked").hide();
	}
	
}

function edit(i){
	$("#modalName").html("适航性资料修改");
	$("#modalEname").html("Edit");
	validation();
	modalEmpty();
	
	var airworthiness=objData[i];
	var Userchecked = 'edit';
	//保存按钮隐藏
	if(airworthiness.zt == 0){
		$("#baocunButton").show();
	}else{
		$("#baocunButton").hide();
		Userchecked = 'disabled';
	}
	$("#jswjlx").val(formatUndefine(airworthiness.jswjlx));
	$("#jswjly").val(formatUndefine(airworthiness.jswjly));
	$("#jswjwjbh").val(formatUndefine(airworthiness.jswjbh));
	$("#airworthinessId").val(formatUndefine(airworthiness.id));
	$("#jswjbb").val(formatUndefine(airworthiness.bb));
	$("#jswjsxrq").val(airworthiness.sxrq?formatUndefine(airworthiness.sxrq.substring(0,10)):'');
	$("#jswjpgqx").val(airworthiness.pgqx?formatUndefine(airworthiness.pgqx.substring(0,10)):'');
	$("#jswjdqrq").val(airworthiness.dqrq?formatUndefine(airworthiness.dqrq.substring(0,10)):'');
	$("#jswjbfrq").val(airworthiness.bfrq?formatUndefine(airworthiness.bfrq.substring(0,10)):'');
	$("#xzah").val(formatUndefine(airworthiness.xzah));
	$("#sm").val(formatUndefine(airworthiness.scfj?airworthiness.scfj.wbwjm:''));
	$("#downFileId").val(airworthiness.scfj?airworthiness.scfj.id:'');
	$("#uploadFile").show();
	$("#zhut").val(formatUndefine(airworthiness.jswjzt));
	$("#airworthinessDprtcode").val(formatUndefine(airworthiness.dprtcode));
	if(airworthiness.zj){
		$("#ataywmc").val(formatUndefine(airworthiness.zj.ywms));
		$("#atazwmc").val(formatUndefine(airworthiness.zj.zwms));
		$("#wczjh").val(formatUndefine(airworthiness.ata));
	}
	if(airworthiness.paramsMap.gljswjbh !=null && airworthiness.paramsMap.gljswjbh !="" ){
		$("#glshxzlBh").val(formatUndefine(airworthiness.paramsMap.gljswjbh));
		$("#gljspgdzhut").val(formatUndefine(airworthiness.paramsMap.gljswjzt));
	}
	$("#glshxzlid").val(formatUndefine(airworthiness.gljswjid));
	$("#bz").val(formatUndefine(airworthiness.bz));
	
	queryFjjx(airworthiness.dprtcode);
	
	if(airworthiness.technicalfileOrderList != null ){
		var list=airworthiness.technicalfileOrderList;
		$.each(list,function(index,row){
			updateTeorList(row.id,row.fjjx,row.pgrid,row.dprtmentId,3);
			var this_=$("input:checkbox[value='"+row.fjjx+"']");
			this_.attr('checked',true);
			this_.attr(Userchecked, Userchecked);
			$("#use"+row.fjjx).css("visibility","visible");
			$("#use"+row.fjjx).find("input").eq(0).val(row.pgr_user?row.pgr_user.displayName:'');
			$("#use"+row.fjjx).find("input").eq(1).val(row.pgrid);
		});
	}else{
		$("#jxchecked").hide();
	}
	
		$("input:radio[name='Ispg'][value='"+airworthiness.xpgbs+"']").attr('checked',true);
	
	
	if(airworthiness.zt != 0){
		$('#jswjlx').attr("disabled",true); 
		$('#jswjly').attr("disabled",true); 
		$('#jswjwjbh').attr("disabled",true); 
		$('#jswjbb').attr("disabled",true); 
		if(airworthiness.xpgbs==1){
			$("input:radio[name='Ispg']").attr("disabled",true); 
		}
	}
	
	
	$("#AddalertModal").modal("show");
	
}

function openddownfile(fileId){
	PreViewUtil.handle(fileId, PreViewUtil.Table.D_011);
}


//拼接飞机机型
function appendFjjx(list,dprtcode){
	var htmlContent='';
	var userDprt=$("#dprtId").val();
	var dprtment=$("#dprtment").val();
	var userDisplayName=$("#userDisplayName").val();
	var userId=$("#userId").val();
	if(userDprt != dprtcode){
		userDisplayName='';
		userId='';
	}
	$.each(list,function(index,row){
		var technicalfileOrder={
				id: null,
				fjjx : row,
				pgrid : userId,
				dprtmentId:dprtment,
				zt: 0,
		};
		
		//添加机型到技术指令list
		teorList.push(technicalfileOrder);
		
		htmlContent += "<div class='col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group'>";
		htmlContent += "<label class='col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0'>";
		if(index==0){
			htmlContent +="<div class='font-size-12 margin-topnew-2'>评估范围</div>";	
			htmlContent +="<div class='font-size-9'>Applicability</div>";	
		}else{
			htmlContent +="<div class='font-size-12 margin-topnew-2'>　　　　</div>";	
			htmlContent +="<div class='font-size-9'>　　　　</div>";	
		}
		htmlContent +="</label>";
		htmlContent +="<div class='col-md-9 col-sm-8 col-xs-9 padding-leftright-8'>";		
		htmlContent +="<div class='col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0'>";		
		htmlContent +="<div class='input-group' hieght='35px'>";		
		htmlContent +="<label class='input-group-addon input-group-none'  >";		
		htmlContent +="<input onchange='addUser(this)' type='checkbox' value='"+row+"' />&nbsp;";		
		htmlContent +=row+" &nbsp;";			
		htmlContent +="</label>";			
		htmlContent +="<div class='input-group' id='use"+row+"' style='visibility:hidden;'>";			
		htmlContent +="<input type='text'  class='form-control' value='"+userDisplayName+"' disabled='disabled'/>";			
		htmlContent +="<input type='hidden'  value='"+userId+"' class='form-control' />";			
		htmlContent +="<span class='input-group-btn'>";			
		htmlContent +="<button type='button' class='btn btn-default form-control' style='height:34px;' data-toggle='modal' onclick='openUserWin(this)'>";			
		htmlContent +="<i class='icon-search cursor-pointer'></i>";			
		htmlContent +="</button>";			
		htmlContent +="</span>";			
		htmlContent +="</div>";			
		htmlContent +="<input type='hidden' id='teco"+row+"' name='tecoid' value='add'>";			
		htmlContent +="</div></div></div></div>";
		
	});
	$("#jxchecked").empty();
	$("#jxchecked").html(htmlContent);
}


