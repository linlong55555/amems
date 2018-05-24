$(function(){
	Navigation(menuCode, '查看审核项目单', 'View', 'SC-1-2 ', '孙霁', '2017-09-25', '孙霁', '2017-09-25');//加载导航栏
	var audititemId = $("#audititemId").val();
	selectById(audititemId);
	shcyappend(audititemId);
	$('.page-content input').attr('disabled',true);
	$('.page-content textarea').attr('disabled',true);
	$('.page-content select').attr('disabled',true); 
});

function selectById(id){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/quality/audititemlist/selectById",
		type:"post",
		data:{
			 'id' : id
		 },
		dataType:"json",
		success:function(data){
			if(data != null){
				viewObj(data.audititem);
			}
		}
	});
}
//加载数据
function shcyappend(ywid){
	if(ywid != null && ywid != ""){
		AjaxUtil.ajax({
			async: false,
			url:basePath+"/quality/auditMembers/selectByYwid",
			type:"post",
			data:{
				'ywid' : ywid
			},
			dataType:"json",
			success:function(data){
				if(data != null && data.rows.length > 0 ){
					$.each(data.rows,function(index,row){
						if(row.js == 1){
							shcyTdAppend('组长',row);
						}else{
							shcyTdAppend('成员',row);
						}
					});
					
				}else{
					$("#dept_info_list").empty();
					$("#dept_info_list").append("<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>");
				}
			}
		});
	}
}

function shcyTdAppend(str,row){
	var trInfo='';
		trInfo +='<tr>';
		trInfo += "<td class='text-center' >"+str+"</td>";
		trInfo += "<td class='text-left' >"+StringUtil.escapeStr(row.cybh)+" "+StringUtil.escapeStr(row.cymc)+"</td>";
		trInfo += "<td class='text-left' >"+StringUtil.escapeStr(row.bmmc)+"</td>";
		trInfo += "<td class='text-left' >"+StringUtil.escapeStr(row.zw)+"</td>";
		trInfo +='</tr>';
		$("#dept_info_list").append(trInfo);
}

function viewObj(obj){
	var this_ = this;
	$("#shxmdbh").val(StringUtil.escapeStr(obj.shxmdbh));
	$("#shxmdbh").attr("disabled",true);
	$("#sjShrq").val(StringUtil.escapeStr(obj.sjShrq?obj.sjShrq.substring(0,10):''));
	$("input:radio[name='lx'][value="+obj.lx+"]").attr('checked', 'checked');
	$("input:radio[name='shlb'][value="+obj.shlb+"]").attr('checked', 'checked');
	$("#shdxmc").val(StringUtil.escapeStr(obj.shdxbh)+" "+StringUtil.escapeStr(obj.shdxmc));
	$("#qrr").val(StringUtil.escapeStr(obj.qrrbh)+" "+StringUtil.escapeStr(obj.qrrmc));
	$("#qrsj").val(StringUtil.escapeStr(obj.qrsj));
	$("#zt").val(StringUtil.escapeStr(DicAndEnumUtil.getEnumName('auditItemStatusEnum',obj.zt)));
	
	//显示附件
	var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
	attachmentsObj.show({
		djid:obj.id,
		fileHead : true,
		colOptionhead : false,
		fileType:"card"
	});
}


//附件下载
function downfile(id){
	PreViewUtil.handle(id, PreViewUtil.Table.D_011)
}