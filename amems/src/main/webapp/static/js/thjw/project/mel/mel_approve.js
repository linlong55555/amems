var pgdids=[];
var no=[];
var zt=[0,1,2,3,4,5,6,7,8,9];
var zts=["未评估","已评估","已审核","已批准","中止（关闭）","审核驳回","批准驳回","","作废","指定结束"];
var attachmentSingle = {};
$(document).ready(function(){
	Navigation(menuCode,"批准MEL更改单","Approve");
	//加载已选择的技术评估单
	selectChoosePgd();
	initPlaneModel();
	$('#jx').attr("disabled",true);
	AttachmengsList.show({
		djid:$("#mcsId").val(),
		attachHead : true,
		fileHead : false,
		colOptionhead : false,
		fileType:"mel"
	});//显示附件
	initInfo();
});

//设置只读/失效/隐藏
function setView(){
	$("#ggdbh").attr("readonly","readonly");
	$("#xmh").attr("readonly","readonly");
	$("#ssbf").attr("readonly","readonly");
	$("#sszj").attr("readonly","readonly");
	$("#zy").attr("readonly","readonly");
	$("#xgqBb").attr("readonly","readonly");
	$("#xghBb").attr("readonly","readonly");
	$("#xdqx").attr("readonly","readonly");
	$("#xdnr").attr("readonly","readonly");
	$("#xgyy").attr("readonly","readonly");
	$("#jx").attr("disabled","true");
	$("input:checkbox[name='xgbj']").attr("disabled","true");
}

function initInfo(){
	AjaxUtil.ajax({
		async: false,
		url:basePath+"/project/mel/selectById",
		type:"post",
		data:{id:$("#mcsId").val()},
		dataType:"json",
		success:function(data){
			if(data != null){
				loadData(data);
			}
			setView();
		}
	});
}

function loadData(obj){
	$("#mcsId").val(formatUndefine(obj.id));
	$("#ggdbh").val(formatUndefine(obj.ggdbh));
	$("#jx").val(formatUndefine(obj.jx));
	$("#xmh").val(formatUndefine(obj.xmh));
	$("#ssbf").val(formatUndefine(obj.ssbf));
	$("#sszj").val(formatUndefine(obj.sszj));
	$("#zy").val(formatUndefine(obj.zy));
	$("#xgqBb").val(formatUndefine(obj.xgqBb));
	$("#xghBb").val(formatUndefine(obj.xghBb));
	$("#xdnr").val(formatUndefine(obj.xdnr));
	$("#xgyy").val(formatUndefine(obj.xgyy));
	$("#xdqx").val(formatUndefine(obj.xdqx));
	$("#melqdfjid").val(formatUndefine(obj.melqdfjid));
	
	$("#shyj").val(formatUndefine(obj.shyj));
	$("#shrname").text(formatUndefine(obj.shr?obj.shr.displayName:''));
	$("#shsj").text(formatUndefine(obj.shsj));
	
	$("#pfyj").val(formatUndefine(obj.pfyj));
	$("#pyrname").text(formatUndefine(obj.pyr?obj.pyr.displayName:''));
	$("#pysj").text(formatUndefine(obj.pfsj));
	
	if(obj.xgbj != null){
		var arr = obj.xgbj.split(",");
		$.each(arr,function(index,v){
			$("input:checkbox[name='xgbj'][value='"+v+"']").attr("checked",true);
		});
		
	}
	var attachment = obj.attachment;
	if(attachment != null){
		 var t = '';
		 t += '<a class="pull-left" style="color: blue;" href="javascript:downloadfile(\''+attachment.id+'\')">'+attachment.wbwjm+'</a>';
		 $("#melqdDiv").html(t);
	}
	var melChangeSheetAndBasiList = obj.melChangeSheetAndBasiList;
	if(melChangeSheetAndBasiList != null && melChangeSheetAndBasiList.length > 0){
		var str = '';
		$.each(melChangeSheetAndBasiList,function(index,o){
			str += '<div>';
			str += '<span style="margin-right:5px;" >';
			str += StringUtil.escapeStr(o.yjlx)+":";
			str += '</span>';
			str += '<span style="margin-left:5px;" >';
			str += StringUtil.escapeStr(o.yjnr);
			str += '</span>';
			str += '</div>';
		});
		$("#xgyjDiv").html(str);
	}
}

function downloadfile(id){
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
}

function initPlaneModel(){
	var data = acAndTypeUtil.getACTypeList({DPRTCODE:$("#dprtcode").val()});
 	var option = '<option value="">please choose</option>';
 	if(data.length >0){
		$.each(data,function(i,obj){
			option += "<option value='"+StringUtil.escapeStr(obj.FJJX)+"' >"+StringUtil.escapeStr(obj.FJJX)+"</option>";
		});
  	 }
 	$("#jx").append(option);
}

//审核通过
function agreedAudit(){
	var pfyj = $.trim($("#pfyj").val());
	var id = $.trim($("#mcsId").val());
	
	var fixedCheckItem = {
			id : id,
			pfyj : pfyj
		};
	
	AlertUtil.showConfirmMessage("您确定要批准通过吗？", {callback: function(){
	
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/project/mel/agreedApprove",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(fixedCheckItem),
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage('批准通过!','/project/mel/main?id='+id+'&pageParam='+pageParam);
			}
		});
	
	}});
}
//审核驳回
function rejectedAudit(){
	var pfyj = $.trim($("#pfyj").val());
	var id = $.trim($("#mcsId").val());
	if(shyj == "" || shyj == null){
		AlertUtil.showErrorMessage("批准意见不能为空!");
		return false;
	}
	var fixedCheckItem = {
			id : id,
			pfyj : pfyj
		};
	AlertUtil.showConfirmMessage("您确定要批准驳回吗？", {callback: function(){
	
		startWait();
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/project/mel/rejectedApprove",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(fixedCheckItem),
			dataType:"json",
			success:function(data){
				finishWait();
				AlertUtil.showMessage('批准驳回!','/project/mel/main?id='+id+'&pageParam='+pageParam);
			}
		});
	
	}});
}

	//加载已选择的关联评估单
	function selectChoosePgd(){
		var id=$('#mcsId').val();
		//alert(id);
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
			htmlContent += "<td class='text-center' style='vertical-align:middle'>" +
					"<input type='hidden' name='PgdIdAndPgdh' value="+data.id+","+StringUtil.escapeStr(data.pgdh)+"><input type='hidden' name='Ckzl' value='"+StringUtil.escapeStr(data.shzltgh)+"'>"+
					"<a href=\"javascript:viewMainTechnicalfile('"+data.id+"','"+data.dprtcode+"')\">"
					+data.pgdh+"</a></td>";
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
	
	
	function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent = htmlContent + "<tr style=\"cursor:pointer\"  bgcolor=\"#f9f9f9\" onclick='chooesRow1(this)' >";
			   } else {
				   htmlContent = htmlContent + "<tr style=\"cursor:pointer\"  bgcolor=\"#fefefe\" onclick='chooesRow1(this)' >";
			   }
			      
			   htmlContent = htmlContent + "<td><input type=\"checkbox\" name='pgd' onclick='chooesRow2(this)' ><input type='hidden' value="+formatUndefine(row.id)+">" +
																			   		"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.pgdh))+"'>" +
																			   		"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.shzltgh))+"'>" +
																			   		"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.wjzt))+"'>" +
																			   		"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.ly))+"'>" +
																			   		"<input type='hidden' value='"+formatUndefine(row.sxrq)+"'>" +
																			   		"<input type='hidden' value='"+formatUndefine(row.pgqx)+"'>" +
																			   		"<input type='hidden' value="+formatUndefine(row.zt)+">"+
																						"<input type='hidden' value="+row.pgr_user.username+">"+
																						"<input type='hidden' value="+row.pgr_user.realname+"></td>";
			   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.shzltgh))+"'>"+StringUtil.escapeStr(formatUndefine(row.shzltgh))+"</td>";  
				htmlContent = htmlContent + "<td class='text-center'>"+StringUtil.escapeStr(formatUndefine(row.pgdh))+"</td>";  
				htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.ly))+"</td>";  
				htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jx))+"'>"+StringUtil.escapeStr(formatUndefine(row.jx))+"</td>";  
				htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.fl))+"</td>";  
				htmlContent = htmlContent + "<td class='text-right'>"+StringUtil.escapeStr(formatUndefine(row.bb))+"</td>";  
				htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(formatUndefine(row.wjzt))+"' class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.wjzt))+"</td>";  
				htmlContent = htmlContent + "<td class='text-center'>"+(formatUndefine(row.sxrq).substring(0,10))+"</td>"; 
				htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(row.pgr_user.displayName)+"</td>";  
				htmlContent = htmlContent + "<td class='text-left'>"+(formatUndefine(row.pgqx).substring(0,10))+"</td>";  
				htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(zts[row.zt])+"</td></tr>";   
			    
		   });
		   //$("#Pgdlist").empty();
		   $("#Pgdlist").html(htmlContent);
		 
	}
	function viewMainTechnicalfile(id,dprtcode){
		window.open(basePath+"/project/technicalfile/findApprovalFileUpload/"+$.trim(id)+"/"+$.trim(dprtcode)+"");
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
	
