var gljb=["","无","批次号管理","序列号管理"];
var hclb=["其他","航材","设备","工具","危险品","低值易耗品"];
var deleteScwjId=[];
var stockIds=[];
$(document).ready(function(){
	//加载已选数据
	selectDestructionDetail();
	//加载上传附件
	selectChooseFj();
	
});

function selectDestructionDetail(){
	var id=$("#destructionId").val();
	AjaxUtil.ajax({
		url:basePath + "/aerialmaterial/destruction/queryChoStock",
		type:"post",
		async: false,
		data:{
			'id' : id
		},
		dataType:"json",
		success:function(data){
			//拼接内容
			appendSelectChooseStock(data.rows);
		}
	});
	
}

function appendSelectChooseStock(list){
	var htmlContent="";
	 $.each(list,function(index,row){ 
		 htmlContent = htmlContent+'<tr style="cursor: pointer"  id=\''+row.id+'\'>';
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.bjh))+"'>"+StringUtil.escapeStr(formatUndefine(row.bjh))+"</td>";
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.ywms))+"'>"+StringUtil.escapeStr(formatUndefine(row.ywms))+'</td>';
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.zwms))+"'>"+StringUtil.escapeStr(formatUndefine(row.zwms))+'</td>';
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.hcMainData.cjjh))+"'>"+StringUtil.escapeStr(formatUndefine(row.hcMainData.cjjh))+'</td>';
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(hclb[row.hcMainData.hclx]))+"'>"+StringUtil.escapeStr(formatUndefine(hclb[row.hcMainData.hclx]))+'</td>';
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(gljb[row.hcMainData.gljb]))+"'>"+StringUtil.escapeStr(formatUndefine(gljb[row.hcMainData.gljb]))+'</td>';
		 htmlContent += "<td title='"+StringUtil.escapeStr(row.sn)+"' align='left'>"+StringUtil.escapeStr(row.sn)+"</td>"; 
		 htmlContent += "<td title='"+StringUtil.escapeStr(row.pch)+"' align='left'>"+StringUtil.escapeStr(row.pch)+"</td>"; 
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.shzh))+"'>"+StringUtil.escapeStr(formatUndefine(row.shzh))+'</td>';
		 htmlContent = htmlContent+"<td class='text-right' title='"+StringUtil.escapeStr(formatUndefine(row.kcsl))+"'>"+StringUtil.escapeStr(formatUndefine(row.kcsl))+'</td>';
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jldw))+"'>"+StringUtil.escapeStr(formatUndefine(row.jldw))+'</td>';
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.ckmc))+"'>"+StringUtil.escapeStr(formatUndefine(row.ckmc))+'</td>';
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.kwh))+"'>"+StringUtil.escapeStr(formatUndefine(row.kwh))+'</td>';
		 htmlContent = htmlContent+'</tr>';
	 });
	 $('#detailTBody').append(htmlContent);
}

//加载已上传的附件
function selectChooseFj(){
	var mainid=$('#destructionId').val();
	AjaxUtil.ajax({
	   url:basePath+"/aerialmaterial/destruction/selectedScwjList",
	   type: "post",
	   dataType:"json",
	   data:{
		   'mainid' : mainid
	   },
	   success:function(data){
		   appendSelecedScwj(data.rows);
   }
 });
	
	
}
//拼接上传附件
function appendSelecedScwj(list){
	var htmlContent="";
	 $.each(list,function(index,row){ 
		 htmlContent = htmlContent+'<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''+row.id+'\'>';
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.wbwjm))+"."+StringUtil.escapeStr(row.hzm)+"'><a href=\"javascript:downloadfile('"+row.id+"')\">"+StringUtil.escapeStr(formatUndefine(row.wbwjm))+"."+StringUtil.escapeStr(row.hzm)+"</a></td>";
		 htmlContent = htmlContent+"<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.wjdxStr))+'</td>';
		 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.czr_user.displayName))+"'>"+StringUtil.escapeStr(formatUndefine(row.czr_user.displayName))+'</td>';
		 htmlContent = htmlContent+"<td class='text-center'>"+formatUndefine(row.czsj)+'</td>';
		 	 htmlContent = htmlContent+'<input type="hidden" name="fjid" value=\''+row.id+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="nbwjm" value=\''+StringUtil.escapeStr(row.nbwjm)+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="yswjm" value=\''+StringUtil.escapeStr(row.yswjm)+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="wjdx" value=\''+StringUtil.escapeStr(row.wjdx)+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="wbwjm1" value=\''+StringUtil.escapeStr(row.wbwjm)+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="cflj" value=\''+row.cflj+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="hzm" value=\''+row.hzm+'\'/>';
		 
		 htmlContent = htmlContent+'</tr>';
	 });
	 if(htmlContent==''){
		 htmlContent='<tr><td colspan=\"4\">暂无数据 No data.</td></tr>';
	 }
	 $('#filelist').append(htmlContent);
	
}
