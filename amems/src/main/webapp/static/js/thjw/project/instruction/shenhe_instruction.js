var zt=[0,1,2,3,4,5,6,7,8,9];
var zts=["未评估","已评估","已审核","已批准","中止（关闭）","审核驳回","批准驳回","","作废","指定结束"];
var pgdids=[];
var no=[];
var instructionContentIds=[];
var oldScwjList=[];
$(function() {
//加载已选择的技术评估单
selectChoosePgd();

//回显工作内容
appendInstructionContent();

//加载已上传的附件
selectChooseFj();

$('#fcrq').datepicker({
	 autoclose: true,
	 clearBtn:true
}).on('hide', function(e) {
	  $('#form').data('bootstrapValidator')  
   .updateStatus('fcrq', 'NOT_VALIDATED',null)  
   .validateField('fcrq');  
});

if($("#Annunciatelist tr").length>0){
	$('#jx').attr("disabled",true);
}

validatorForm =  $('#form').bootstrapValidator({
    message: '数据不合法',
    feedbackIcons: {
        //valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
                   
        zhut: {
            validators: {
            	notEmpty: {
                    message: '主题不能为空'
                }
            }
        },
        jx: {
            validators: {
                notEmpty: {
                    message: '机型不能为空'
                }
            }
        },
        jsrid: {
            validators: {
            	notEmpty: {
                    message: '接收人不能为空'
                }
            }
        },
        nr: {
            validators: {
            	notEmpty: {
                    message: '内容不能为空'
                }
            }
        }
        	            
    }
});
obtainDrptment();
});
//打开评估单列表对话框
function openPgd() {
	 goPage(1,"auto","desc");
	 $("#alertModalPgd").modal("show");
}
//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
function goPage(pageNumber,sortType,sequence){
	AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
}
//带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
	var obj ={};
	var zlid=$('zlid').val();
	if (zlid != null && zlid != "") {
		obj.zlid = zlid;					//指令id
	   	}
	var pgdsId="";
	for(var i=0;i<pgdids.length;i++){
   		//alert(pgdids[i]);
   		pgdsId+= pgdids[i]+",";
   	}
	pgdsId=pgdsId.substring(0,pgdsId.length-1);
	obj.pgdsId=pgdsId;//已选择的评估单id
	obj.isJszl=1;	//技术通告复选为2
	//var searchParam = gatherSearchParam();
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	
	startWait();
	 AjaxUtil.ajax({
	   url:basePath+"/project/instruction/selectPgdList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(obj),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   appendContentHtml(data.rows);
			   appendPaginationHtml(data,sortType,sequence);
		   } else {
			  $("#Pgdlist").empty();
			  $("#pagination").empty();
			  $("#Pgdlist").append("<tr><td colspan=\"11\">暂无数据 No data.</td></tr>");
		   }
     }
   }); 
	
}
function appendContentHtml(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr style=\"cursor:pointer\"  bgcolor=\"#f9f9f9\" onclick='chooesRow1(this)' >";
		   } else {
			   htmlContent = htmlContent + "<tr style=\"cursor:pointer\"  bgcolor=\"#fefefe\" onclick='chooesRow1(this)' >";
		   }
		   htmlContent = htmlContent + "<td><input type=\"checkbox\" name='pgd'><input type='hidden' value="+formatUndefine(row.id)+">" +
																		   		"<input type='hidden' value="+StringUtil.escapeStr(formatUndefine(row.pgdh))+">" +
																		   		"<input type='hidden' value="+StringUtil.escapeStr(formatUndefine(row.shzltgh))+">" +
																		   		"<input type='hidden' value="+StringUtil.escapeStr(formatUndefine(row.wjzt))+">" +
																		   		"<input type='hidden' value="+StringUtil.escapeStr(formatUndefine(row.ly))+">" +
																		   		"<input type='hidden' value="+formatUndefine(row.sxrq)+">" +
																		   		"<input type='hidden' value="+formatUndefine(row.pgqx)+">" +
																		   		"<input type='hidden' value="+formatUndefine(row.zt)+">"+
																					"<input type='hidden' value="+row.pgr_user.username+">"+
																					"<input type='hidden' value="+row.pgr_user.realname+"></td>";
						htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(formatUndefine(row.shzltgh))+"</td>";  
						htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(formatUndefine(row.pgdh))+"</td>";  
						htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(formatUndefine(row.ly))+"</td>";  
						htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(formatUndefine(row.jx))+"</td>";  
						htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(formatUndefine(row.fl))+"</td>";  
						htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(formatUndefine(row.bb))+"</td>";  
						htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(formatUndefine(row.wjzt))+"</td>";  
						htmlContent = htmlContent + "<td>"+(formatUndefine(row.sxrq).substring(0,10))+"</td>"; 
						htmlContent = htmlContent + "<td>"+formatUndefine(row.pgr_user.displayName)+"</td>";  
						htmlContent = htmlContent + "<td>"+(formatUndefine(row.pgqx).substring(0,10))+"</td>";  
						htmlContent = htmlContent + "<td>"+formatUndefine(zts[row.zt])+"</td>"; 
						htmlContent = htmlContent + "</tr>"; 
		    
	   });
	   //$("#Pgdlist").empty();
	   $("#Pgdlist").html(htmlContent);
	 
}
function appendPaginationHtml(data,sortType,sequence){
	   var viewpagecount = 10;//每页显示12个分页便签
	   var param = data.rows; //页面数据
	   var total = data.total;//总数据量
	   var size =  data.pageable.rows;//每页显示的条目数
	   var pageCount = total % size==0? parseInt(total/size):parseInt(total/size)+1;//总的页数 
	   var currentPage = 1; //记录当前页码 
	   var subpagination ="";
	   
	   for (var i=0;i<pageCount;i++){
		   if (data.pageable.page == (i+1)) {
		  	 currentPage = i + 1;
		  	 break;
		   }
	   }
	   
	   var startpage = currentPage - (viewpagecount % 2 == 0 ? viewpagecount / 2 - 1: viewpagecount / 2);//起始页
	   var endpage = currentPage + viewpagecount / 2; //结束页 
	   if (startpage < 1) {
			startpage = 1;
			if (pageCount >= viewpagecount){
				endpage = viewpagecount;
			}else{
				endpage = pageCount;
			}
		}
	   
	   if (endpage > pageCount) {
			endpage = pageCount;
			if ((endpage - viewpagecount) > 0){
				startpage = endpage - viewpagecount + 1;
			}else{
				startpage = 1;
			}
		}
	   var paginationContent = "";
	   if (currentPage==1) { //当前为第一页时,不能向前翻页
		   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&lt;&lt;</a></li>";
		   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&lt;</a></li>";
	   } else {
		   paginationContent = paginationContent 
        + "<li onclick=\"goPage(1,'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&lt;&lt;</a></li>";
		   paginationContent = paginationContent 
        + "<li onclick=\"goPage("+(currentPage-1)+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&lt;</a></li>";
	   }
	   
	   for (var index = startpage;index <= endpage;index++){
		   if (index==currentPage){
			   paginationContent = paginationContent + "<li class=\"active\"><a href=\"javascript:void(0)\">"+index+"</a></li>";
		   }else {
			   paginationContent = paginationContent + "<li onclick=\"goPage("+index+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">"+index+"</a></li>";
		   }
	   }
	   if (currentPage ==pageCount){
		   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&gt;</a></li>";
		   paginationContent = paginationContent + "<li class=\"disabled\"><a href=\"javascript:void(0)\">&gt;&gt;</a></li>";
	   }else {
		   paginationContent = paginationContent + "<li onclick=\"goPage("+(currentPage+1)+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&gt;</a></li>";
		   paginationContent = paginationContent + "<li onclick=\"goPage("+pageCount+",'"+sortType+"','"+sequence+"')\"><a href=\"javascript:void(0)\">&gt;&gt;</a></li>";
	   }
	   $("#pagination").empty();
	   $("#pagination").html(paginationContent);
}
//保存评估单
function appendPgd(){
	
	var htmlContent = ""; 
	$('input[name=pgd]:checked').each(function(){   
		var pgdId=$(this).next().val();
		var pgdh=$(this).next().next().val();
		var ckzl=$(this).next().next().next().val();
		var wjzt=$(this).next().next().next().next().val();
		var ly=$(this).next().next().next().next().next().val();
		var sxrq=$(this).next().next().next().next().next().next().val();
		var pgqx=$(this).next().next().next().next().next().next().next().val();
		var pgzt=$(this).next().next().next().next().next().next().next().next().val();
		var username=$(this).next().next().next().next().next().next().next().next().next().val();
		var realname=$(this).next().next().next().next().next().next().next().next().next().next().val();
		//alert(wjzt);
		htmlContent = htmlContent + "<tr   style=\"cursor:pointer\" bgcolor=\"#fefefe\" id='tr1_"+pgdId+"'>";
		htmlContent = htmlContent + "<td><button class='line6' onclick=\"lineRemove('"+pgdId+"')\" type='button'><i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i></button></td>";
		htmlContent = htmlContent + "<td><input type='hidden' name='PgdIdAndPgdh' value="+pgdId+","+StringUtil.escapeStr(pgdh)+"><input type='hidden' name='Ckzl' value="+StringUtil.escapeStr(ckzl)+">"+StringUtil.escapeStr(pgdh)+"</td>";
		htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(ly)+"</td>";  
		htmlContent = htmlContent + "<td>"+StringUtil.escapeStr(ckzl)+"</td>";  
		htmlContent = htmlContent + "<td>"+(sxrq.substring(0,10))+"</td>";
		htmlContent = htmlContent + "<td>"+username+" "+realname+"</td>";
		htmlContent = htmlContent + "<td>"+(pgqx.substring(0,10))+"</td>";  
		htmlContent = htmlContent + "<td>"+zts[pgzt]+"</td>";
		htmlContent = htmlContent + "</tr>";
		pgdids.push(pgdId);
		
		if($('#zhut').val()=="" || $('#zhut').val()==null){
			$('#zhut').val(wjzt);
		}
		  });
		//$("#Annunciatelist").empty();
	    $("#Annunciatelist").append(htmlContent);
}
//删除行
function lineRemove(pgdid){
	pgdids.remove(pgdid);
	//alert(pgdids);
	$('#tr1_'+pgdid).remove();
}
//扩展数组方法：查找指定元素的下标  
Array.prototype.indexOf = function(val) {  
for (var i = 0; i < this.length; i++) {  
    if (this[i] == val) return i;  
}  
return -1;  
};  

//扩展数组方法:删除指定元素  
Array.prototype.remove = function(val) {  
var index = this.indexOf(val);  
while(index>-1){  
    this.splice(index, 1);  
    index = this.indexOf(val);  
}  
}; 
/*//将搜索条件封装 
function gatherSearchParam(){
	 var searchParam = {};
	 searchParam.rid = $.trim($("#rid_search").val());
	 searchParam.aircrafttailnumber = $.trim($("#aircrafttailnumber_search").val());
	 searchParam.msn = $.trim($("#msn_search").val());
	 searchParam.aircrafttype = $.trim($("#aircrafttype_search").val());
	 searchParam.description = $.trim($("#description_search").val());
	 searchParam.plannedstartdate = $.trim($("#plannedstartdate_search").val());
	 searchParam.plannedfinishdate = $.trim($("#plannedfinishdate_search").val());
	 searchParam.actualstartdate = $.trim($("#actualstartdate_search").val());
	 return searchParam;
}*/

//获取选择后的所有评估单id
function getPgdIdAndPgdh(){
	var pgdIdAndPgdh=[];
	$("#Annunciatelist").find("tr input[name='PgdIdAndPgdh']").each(function(){
	var ordersource={};
		//alert($(this).val());
		//alert("2");
		ordersource.pgdid=$(this).val().split(',')[0]; 
		ordersource.pgdh=$(this).val().split(',')[1]; 
		pgdIdAndPgdh.push(ordersource);
	});
	return pgdIdAndPgdh;
}
//加载已选择的关联评估单
function selectChoosePgd(){
	var id=$('#instructionId').val();
	AjaxUtil.ajax({
		url:basePath + "/project/technicalfile/selectChoosePgd",
		type:"post",
		async: false,
		data:{
			'id' : id
		},
		dataType:"json",
		success:function(data){
			//拼接内容
			appendSelectChoosePgd(data);
		}
	});
}
//拼接已选择的评估单
function appendSelectChoosePgd(dataList){
	var param = dataList.tchnicalFileList; //页面数据
	var htmlContent = "";
	$.each(param,function(index,data){
		pgdids.push(data.id); 
		//alert(data.id);
		htmlContent += "<tr bgcolor=\"#f9f9f9\" id='tr1_"+data.id+"'>";
		/*htmlContent += "<td align='center'><a href=\"javascript:lineRemove('"+data.id+"')\"><i class='icon-minus cursor-pointer' ></i></a></td>";*/
		/*htmlContent = htmlContent + "<td><button class='line6' onclick=\"lineRemove('"+data.id+"')\" type='button'><i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i></button></td>";*/
		htmlContent += "<td class='text-center' style='vertical-align:middle'>" +
				"<input type='hidden' name='PgdIdAndPgdh' value="+data.id+","+StringUtil.escapeStr(data.pgdh)+"><input type='hidden' name='Ckzl' value="+StringUtil.escapeStr(data.shzltgh)+">"+
				"<a href=\"javascript:viewMainTechnicalfile('"+data.id+"','"+data.dprtcode+"')\">"
				+data.pgdh+"</td></a>";
		htmlContent = htmlContent +"<td class='text-left' style='vertical-align:middle'>"+StringUtil.escapeStr(data.ly)+"</td>";
		htmlContent = htmlContent +"<td class='text-left' style='vertical-align:middle' title='"+StringUtil.escapeStr(data.shzltgh)+"'>"+StringUtil.escapeStr(data.shzltgh)+"</td>";
		htmlContent = htmlContent +"<td class='text-center' style='vertical-align:middle'>"+indexOfTime(data.sxrq)+"</td>";
		htmlContent = htmlContent +"<td class='text-left' style='vertical-align:middle'>"+data.pgr_user.displayName+"</td>";
		htmlContent = htmlContent +"<td class='text-center' style='vertical-align:middle'>"+indexOfTime(data.pgqx)+"</td>";
		htmlContent = htmlContent +"<td class='text-left' style='vertical-align:middle'>"+zts[data.zt]+"</td>";
		htmlContent = htmlContent + "</tr>";
	});
	
	$("#Annunciatelist").empty();
	$("#Annunciatelist").html(htmlContent);
}
function viewMainTechnicalfile(id,dprtcode){
	window.open(basePath+"/project/technicalfile/findApprovalFileUpload/"+$.trim(id)+"/"+$.trim(dprtcode)+"");
}
//异步删除评估单
function deleteOrderSource(pgdId){
	alert(pgdId);
	pgdids.remove(pgdId);
	var id=$('#instructionzlbh').val();
	AjaxUtil.ajax({
		url:basePath + "/project/instruction/deleteOrderSource",
		type:"post",
		data:{
			'pgdid' : pgdId,
			'zlbh' : id,
		},
		dataType:"json",
		success:function(data){
			if(data.state=="success"){
				//alert(data.massage);
				$('#tr1_'+pgdId).remove();
			}
			
		}
	});
	
}
//根据选择的接收人回显相应的接收人部门
function obtainDrptment(){
	var id=$('#jsrid').val();
	
	if(id=="" || id==null){
		$('#jsrbmid').val("");
		return;
	}
	AjaxUtil.ajax({
		url:basePath+"/project/instruction/obtainDrptment",
		type: "post",
		data:{
			"id" : id
		},
		dataType:"json",
		success:function(data){
			if(data.user.department==null || data.user.department==""){
				$('#jsrbmid').val("");
			}else{
				$('#jsrbmid').val(data.user.department.dprtname);
			}
		}
	});
	
}
//回显工作内容
function appendInstructionContent(){
	var id=$('#instructionId').val();
	AjaxUtil.ajax({
		url:basePath + "/project/instruction/appendInstructionContent",
		type:"post",
		async: false,
		data:{
			'id' : id
		},
		dataType:"json",
		success:function(data){
			
			//拼接内容
			appendContent(data);
		}
	});
}
//拼接内容
function appendContent(dataList){
	var param = dataList.instructionContentList; //页面数据
	var htmlContent = "";
	$.each(param,function(index,data){
		instructionContentIds.push(data.id);
		//alert(data.id);
		no++;
		htmlContent =htmlContent +"<tr id='gznr"+no+"'>"
		/*htmlContent =htmlContent +"<td><a href=\"javascript:deleteGznr("+no+")\"><i class='icon-minus cursor-pointer' ></i></a></td>"*/
		htmlContent =htmlContent +"<td><textarea class='col-lg-12 col-sm-12 col-xs-12' style='height:40px' name='gznr' disabled='disabled' >"+StringUtil.escapeStr(data.gznr)+"</textarea></td>"
		htmlContent =htmlContent +"<td ><input type='hidden' name='gznrId' id='gznrId' value="+data.id+"></td>"
		htmlContent =htmlContent +"<td></td>"
		htmlContent =htmlContent +"</tr>"
	});
	
	$("#GznrList").empty();
	$("#GznrList").html(htmlContent);
}
//添加一行工作内容
function appendGznr(){
	var htmlContent ="";
	no++;
	htmlContent =htmlContent +"<tr id='gznr"+no+"'>"
	/*htmlContent =htmlContent +"<td><a href=\"javascript:deleteGznr("+no+")\"><i class='icon-minus cursor-pointer' ></i></a></td>"*/
	htmlContent =htmlContent +"<td><textarea class='col-lg-12' style='height:40px' name='gznr' ></textarea></td>"
	htmlContent =htmlContent +"<td ><input type='hidden' name='gznrId' id='gznrId' value=1 ></td>"
	htmlContent =htmlContent +"<td></td>"
	htmlContent =htmlContent +"</tr>"
	$("#GznrList").append(htmlContent);
}
//删除工作内容
function deleteGznr(no){
	$('#gznr'+no).remove();
}
//获取工作内容列表
function getinstructionContent(){
	var instructionContentList=[];
	$("#GznrList").find("tr").each(function(){
		var index=$(this).index(); //当前行
		var instructionContent={};
		instructionContent.gznr=$("textarea[name='gznr']").eq(index).val();
		instructionContent.id=$("input[name='gznrId']").eq(index).val();
		instructionContentList.push(instructionContent);
	});
	return instructionContentList;
	}

//同意
function agreedMainInstruction(){
	obj={
			'id':$('#instructionId').val(),
			'shyj':$('#shyj').val(),
			'zt':2
	}
	AlertUtil.showConfirmMessage("您确定要审核通过吗？",{callback: function(){
		submitMainInstruction(obj);
	}});
	
}
//驳回
function rejectedMainInstruction(){
	obj={
			'id':$('#instructionId').val(),
			'shyj':$('#shyj').val(),
			'zt':5
	}
	if(obj.shyj=="" || obj.shyj==null){
		AlertUtil.showErrorMessage("审核意见不能为空");
		return;
	}
	AlertUtil.showConfirmMessage("您确定要审核驳回吗？",{callback: function(){
		submitMainInstruction(obj);
	}});
	
}
//提交
function submitMainInstruction(obj){
	// 提交数据
	AjaxUtil.ajax({
		url:basePath + "/project/instruction/submitshenheMainInstruction",
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType:"json",
		success:function(data){
			if(obj.zt==2){
				AlertUtil.showMessage('审核成功!','/project/instruction/main?id='+data+'&pageParam='+pageParam);
			}else if(obj.zt==5){
				AlertUtil.showMessage('驳回成功!','/project/instruction/main?id='+data+'&pageParam='+pageParam);
			}else{
				AlertUtil.showMessage('操作成功!','/project/instruction/main?id='+data+'&pageParam='+pageParam);
			}
		}
	});

}
//加载已上传的附件
function selectChooseFj(){
	var mainid=$('#instructionId').val();
	AjaxUtil.ajax({
	   url:basePath+"/project/annunciate/selectedScwjList",
	   type: "post",
	   dataType:"json",
	   data:{
		   'mainid' : mainid
	   },
	   success:function(data){
		   //oldScwjList=data.rows;
		   appendSelecedScwj(data.rows);
   }
 });
	
	
}
//拼接上传附件
function appendSelecedScwj(list){
	//alert(JSON.stringify(list));
	var htmlContent="";
	 $.each(list,function(index,row){ 
		 oldScwjList.push(row.id);
		 htmlContent = htmlContent+'<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''+row.id+'\'>';
		/* htmlContent = htmlContent+'<td><div>';
		 htmlContent = htmlContent+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+row.id+ '\')" title="删除附件">  ';
		 htmlContent = htmlContent+'</div></td>';*/
		/* htmlContent = htmlContent+'<td>'+row.yswjm+'</td>';
		 htmlContent = htmlContent+'<td>'+row.nbwjm+'</td>';*/
		 htmlContent = htmlContent+"<td  class='text-left'> <a href=\"javascript:downloadfile('"+row.id+"')\">"+formatUndefine(row.wbwjm)+"</td>";
		 htmlContent = htmlContent+"<td  class='text-left'>"+formatUndefine(row.wjdxStr)+'</td>';
		 htmlContent = htmlContent+"<td  class='text-left'>"+formatUndefine(row.czr_user.displayName)+'</td>';
		 htmlContent = htmlContent+"<td  class='text-center'>"+formatUndefine(row.czsj)+'</td>';
		/* htmlContent = htmlContent+'<td>'+row.cflj+'</td>';*/
		 	 htmlContent = htmlContent+'<input type="hidden" name="fjid" value=\''+row.id+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="nbwjm" value=\''+StringUtil.escapeStr(row.nbwjm)+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="yswjm" value=\''+StringUtil.escapeStr(row.yswjm)+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="wjdx" value=\''+StringUtil.escapeStr(row.wjdx)+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="wbwjm1" value=\''+StringUtil.escapeStr(row.wbwjm)+'\'/>';
			 htmlContent = htmlContent+'<input type="hidden" name="cflj" value=\''+row.cflj+'\'/>';
		 
		 htmlContent = htmlContent+'</tr>';
	 });
	 $('#filelist').append(htmlContent);
	
}
//附件下载
function downloadfile(id){
	//window.open(basePath + "/common/orderDownfile/" + id);
	PreViewUtil.handle(id, PreViewUtil.Table.D_011)
}
function back(){
	window.location.href =basePath+"/project/instruction/main?pageParam="+pageParam;
}