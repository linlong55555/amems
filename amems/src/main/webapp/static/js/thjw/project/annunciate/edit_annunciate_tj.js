var zt=[0,1,2,3,4,5,6,7,8,9];
var zts=["未评估","已评估","已审核","已批准","中止（关闭）","审核驳回","批准驳回","","作废","指定结束"];
var pgdids=[];
var oldScwjList=[];
$(function() {
//加载已选择的技术评估单
selectChoosePgd();

//加载已上传的附件
selectChooseFj();
//时间控件
$('#tgqx').datepicker({
	 autoclose: true,
	 clearBtn:true
}).on('hide', function(e) {
	  $('#form').data('bootstrapValidator')  
    .updateStatus('tgqx', 'NOT_VALIDATED',null)  
    .validateField('tgqx');  
});
//时间控件
$('#sxrq').datepicker({
	autoclose: true,
	clearBtn:true
}).on('hide', function(e) {
	$('#form').data('bootstrapValidator')  
	.updateStatus('sxrq', 'NOT_VALIDATED',null)  
	.validateField('sxrq');  
});

 validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            //valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
                       
           /* zhut: {
                validators: {
                	notEmpty: {
                        message: '主题不能为空'
                    }
                }
            },*/
            userids: {
                validators: {
                    notEmpty: {
                        message: '圈阅人员不能为空'
                    }
                }
            },
            tgqx: {
                validators: {
                	notEmpty: {
                        message: '通告期限不能为空'
                    }
                }
            }
            	            
        }
    });
 
//生成多选下拉框动
 $('#userids').multiselect({
 	buttonClass: 'btn btn-default',
     buttonWidth: 'auto',
     numberDisplayed:20,
     includeSelectAllOption: true,
     onChange:function(element,checked){
     }
 });

});
//打开评估单列表对话框
function openPgd() {
	$("#keyword_search").val("");
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
	var zlid=$('#zlid').val();
	var keyword=$.trim($('#keyword_search').val());
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
	obj.isJstg=1;	//技术通告复选为1
	obj.keyword=keyword;
	obj.dprtcode=$('#dprtcode').val();
	//var searchParam = gatherSearchParam();
	obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
	
	startWait();
	 AjaxUtil.ajax({
	   url:basePath+"/project/technicalfile/selectPgdList",
	   type: "post",
	   contentType:"application/json;charset=utf-8",
	   dataType:"json",
	   data:JSON.stringify(obj),
	   success:function(data){
	    finishWait();
		   if(data.total >0){
			   appendContentHtml(data.rows);
			   var page_ = new Pagination({
					renderTo : "pagination",
					data: data,
					sortColumn : sortType,
					orderType : sequence,
					extParams:{},
					goPage: function(a,b,c){
						AjaxGetDatasWithSearch(a,b,c);
					}//,
					//controller: this_
				});
			// 标记关键字
				signByKeyword($("#keyword_search").val(), [ 2, 3, 8 ]);
		   } else {
			  $("#Pgdlist").empty();
			  $("#pagination").empty();
			  $("#Pgdlist").append("<tr><td colspan=\"12\">暂无数据 No data.</td></tr>");
		   }
     }
   }); 
	
}
function chooesRow2(obj){
	chooesRow($(obj));
}

function chooesRow1(objradio){
	var obj = $(objradio).find("input[type='checkbox']");
	chooesRow(obj);
}

//行选
function chooesRow(obj){
	var flag = obj.is(":checked");
	if(flag){
		obj.removeAttr("checked");
	}else{
		obj.attr("checked",true);
	}
	
}
function appendContentHtml(list){
	   var htmlContent = '';
	   $.each(list,function(index,row){
		   if (index % 2 == 0) {
			   htmlContent = htmlContent + "<tr style=\"cursor:pointer\"  bgcolor=\"#f9f9f9\" onclick='chooesRow1(this)' >";
		   } else {
			   htmlContent = htmlContent + "<tr style=\"cursor:pointer\"  bgcolor=\"#fefefe\" onclick='chooesRow1(this)' >";
		   }
		   htmlContent = htmlContent + "<td><input type=\"checkbox\" name='pgd' onclick='chooesRow2(this)'><input type='hidden' value="+formatUndefine(row.id)+">" +
																		   		"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.pgdh))+"'>" +
																		   		"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.shzltgh))+"'>" +
																		   		"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.wjzt))+"'>" +
																		   		"<input type='hidden' value='"+StringUtil.escapeStr(formatUndefine(row.ly))+"'>" +
																		   		"<input type='hidden' value='"+formatUndefine(row.sxrq)+"'>" +
																		   		"<input type='hidden' value='"+formatUndefine(row.pgqx)+"'>" +
																		   		"<input type='hidden' value="+formatUndefine(row.zt)+">"+
																					"<input type='hidden' value="+StringUtil.escapeStr(row.pgr_user.username)+">"+
																					"<input type='hidden' value="+StringUtil.escapeStr(row.pgr_user.realname)+"></td>";
		   htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.shzltgh))+"'>"+StringUtil.escapeStr(formatUndefine(row.shzltgh))+"</td>";  
			htmlContent = htmlContent + "<td class='text-center'>"+StringUtil.escapeStr(formatUndefine(row.pgdh))+"</td>";  
			htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.ly))+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.jx))+"'>"+StringUtil.escapeStr(formatUndefine(row.jx))+"</td>";  
			htmlContent = htmlContent + "<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.fl))+"</td>";  
			htmlContent = htmlContent + "<td class='text-right'>"+StringUtil.escapeStr(formatUndefine(row.bb))+"</td>";  
			htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.wjzt)+"' class='text-left'>"+StringUtil.escapeStr(row.wjzt)+"</td>";  
			htmlContent = htmlContent + "<td class='text-center'>"+(formatUndefine(row.sxrq).substring(0,10))+"</td>"; 
			htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(row.pgr_user.displayName)+"</td>";  
			htmlContent = htmlContent + "<td class='text-left'>"+(formatUndefine(row.pgqx).substring(0,10))+"</td>";  
			htmlContent = htmlContent + "<td class='text-left'>"+formatUndefine(zts[row.zt])+"</td></tr>";  
		    
	   });
	   //$("#Pgdlist").empty();
	   $("#Pgdlist").html(htmlContent);
	 
}
//保存评估单
function appendPgd(){
	var dprtcode=$("#jgdm").val();
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
		htmlContent = htmlContent + "<td class='text-center' style='vertical-align:middle'><input type='hidden' name='PgdIdAndPgdh' value="+pgdId+","+StringUtil.escapeStr(pgdh)+"><input type='hidden' name='Ckzl' value='"+StringUtil.escapeStr(ckzl)+"'><a href=\"javascript:viewMainTechnicalfile('"+pgdId+"','"+dprtcode+"')\">"+StringUtil.escapeStr(pgdh)+"</a></td>";
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'>"+StringUtil.escapeStr(ly)+"</td>";  
		htmlContent = htmlContent + "<td class='text-left' title='"+ckzl+"' style='vertical-align:middle'>"+StringUtil.escapeStr(ckzl)+"</td>";  
		htmlContent = htmlContent + "<td class='text-center' style='vertical-align:middle'>"+(sxrq.substring(0,10))+"</td>";
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'>"+StringUtil.escapeStr(username)+" "+StringUtil.escapeStr(realname)+"</td>";
		htmlContent = htmlContent + "<td class='text-center' style='vertical-align:middle'>"+(pgqx.substring(0,10))+"</td>";  
		htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'>"+zts[pgzt]+"</td>";
		htmlContent = htmlContent + "</tr>";
		pgdids.push(pgdId);
		
		if($('#zhut').val()=="" || $('#zhut').val()==null){
			$('#zhut').val(wjzt);
		}
		  });
		//$("#Annunciatelist").empty();
	    $("#Annunciatelist").append(htmlContent);
	    if($("#Annunciatelist tr").length>0){
	    	$('#jx').attr("disabled",true);
	    }
}
function viewMainTechnicalfile(id,dprtcode){
	window.open(basePath+"/project/technicalfile/findApprovalFileUpload/"+$.trim(id)+"/"+$.trim(dprtcode)+"");
}
//删除行
function lineRemove(pgdid){
	pgdids.remove(pgdid);
	//alert(pgdid);
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
//加载所有用户
function selectUserMain(){
	var id=$('#annunciatezlbh').val();
	//查询所有用户id和名称
		AjaxUtil.ajax({
			url:basePath+"/project/annunciate/selectUserToSend",
			type:"post",
			dataType:"json",
			data:{
				'id' : id
			},
			success:function(data){
				//alert(JSON.stringify(data));
					  appendUser(data);
				
			}
		});
	
}
//拼接用户名称内容
function appendUser(dataList){
	//未选的用户
	var param = dataList.userListNo; //页面数据
	var htmlContent = "";
	$.each(param,function(index,data){
		htmlContent += "<option value="+data.id+","+data.bmdm+">"+data.realname+","+data.username+"</option>"; 
	});
	$("#oListboxFrom").empty();
	$("#oListboxFrom").html(htmlContent);
	//已选的用户
	var param = dataList.userListYes; //页面数据
	var htmlContent = "";
	$.each(param,function(index,data){
		htmlContent += "<option value="+data.username+","+data.bmdm+">"+data.realname+","+data.username+"</option>"; 
	});
	$("#oListboxTo").empty();
	$("#oListboxTo").html(htmlContent);
}
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
//获取选择的角色id
function getUsersIdName(){
	var obj = document.getElementById('oListboxTo');
	var options = obj.options;
	var usersId = new Array();
	for(var i=0,len=options.length;i<len;i++){
	    var opt = options[i];
	    usersId.push(opt.value);
	}
	return usersId;
}
//保存
function save(){
	//评估单id
	var orderSourceList=getPgdIdAndPgdh();
	var len = $("#Annunciatelist").find("tr").length;
	if(len<=0){
		 AlertUtil.showErrorMessage("请先选择评估单");
		return false;
	}
	 $('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	  }
	
	
	//基本信息
	var id=$.trim($("#annunciateId").val());
	var jstgh=$.trim($("#annunciatezlbh").val());
	var zhut=$.trim($("#zhut").val());
	var nr=$.trim($("#nr").val());
	var sendList = [];
	var userids = $('#userids').val();
	//alert(userids);
	if(userids != null){
		for(var i = 0 ; i < userids.length ; i++){
			if('multiselect-all' != userids[i]){
				var send={};
				send.jsrid=userids[i].split(',')[0];
				send.jsbmid=userids[i].split(',')[0];
				sendList.push(send);
			}
		}
	}
	var zt=0;
	var tgqx=$('#tgqx').val();
	var sxrq=$('#sxrq').val();
	var obj={
			"id":id,
			"jstgh":jstgh,
			"orderSourceList":orderSourceList,
			"zhut":zhut,
			"nr":nr,
			"zt":zt,
			"ckzl":getShzlh(),
			"sendList":sendList,//用户ID
			"tgqx":tgqx,
			"sxrq":sxrq,
			"dprtcode":$('#dprtcode').val(),
			"bb":$('#bb').val()
	};
	//alert(JSON.stringify(obj));
	saveMainAnnunciate(obj);
}
//获取所有的适航指令号并拼接
function getShzlh(){
	var ckzl="";
	$("#Annunciatelist").find("tr input[name='Ckzl']").each(function(){
		//alert($(this).val());
		//alert(1);
		ckzl+=$(this).val()+",";
	});
	//alert(ckzl);
	ckzl=ckzl.substring(0,ckzl.length-1);
	//alert(ckzl);
	
	return ckzl;
}
//提交
function save1(){
	//评估单id
	var orderSourceList=getPgdIdAndPgdh();
	//alert(orderSourceList);
	var len = $("#Annunciatelist").find("tr").length;
	if(len<=0){
		 AlertUtil.showErrorMessage("请先选择评估单");
		return false;
	}
	 $('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	  }
	
	
	//基本信息
	var id=$.trim($("#annunciateId").val());
	var jstgh=$.trim($("#annunciatezlbh").val());
	var zhut=$.trim($("#zhut").val());
	var nr=$.trim($("#nr").val());
	var sendList = [];
	var userids = $('#userids').val();
	//alert(userids);
	if(userids != null){
		for(var i = 0 ; i < userids.length ; i++){
			if('multiselect-all' != userids[i]){
				var send={};
				send.jsrid=userids[i].split(',')[0];
				send.jsbmid=userids[i].split(',')[0];
				sendList.push(send);
			}
		}
	}
	var tgqx=$('#tgqx').val();
	var sxrq=$('#sxrq').val();
	var obj={
			"id":id,
			"jstgh":jstgh,
			"orderSourceList":orderSourceList,
			"zhut":zhut,
			"nr":nr,
			"ckzl":getShzlh(),
			"sendList":sendList,//用户ID
			"tgqx":tgqx,
			"sxrq":sxrq,
			"dprtcode":$('#dprtcode').val(),
			"bb":$('#bb').val()
			
	};
	//alert(JSON.stringify(obj));
	saveMainAnnunciate(obj);
}
//提交后台
function saveMainAnnunciate(obj){
	if(obj.sendList<=0){
		AlertUtil.showErrorMessage("请选择需要圈阅的人员");
		return false
	}
	var len = $("#Annunciatelist").find("tr").length;
	if(len<=0){
		 AlertUtil.showErrorMessage("请先选择评估单");
		return false;
	}
	 $('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		  AlertUtil.showErrorMessage("请根据提示输入正确的信息");
		return false;
	  }
	// 提交数据
	AjaxUtil.ajax({
		url:basePath+"/project/annunciate/editAnnunciate",
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType:"json",
		success:function(data){
				AlertUtil.showMessage('提交成功!','/project/annunciate/main?id='+data+'&pageParam='+pageParam);
		}
	});
}
//加载已选择的关联评估单
function selectChoosePgd(){
	var id=$('#annunciateId').val();
	//alert(id);
	AjaxUtil.ajax({
		url:basePath + "/project/annunciate/selectChoosePgd",
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
		htmlContent = htmlContent + "<td><button class='line6' onclick=\"lineRemove('"+data.id+"')\" type='button'><i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i></button></td>";
		htmlContent += "<td class='text-center' style='vertical-align:middle'>" +
				"<input type='hidden' name='PgdIdAndPgdh' value="+data.id+","+StringUtil.escapeStr(data.pgdh)+"><input type='hidden' name='Ckzl' value='"+StringUtil.escapeStr(data.shzltgh)+"'>"+
				"<a href=\"javascript:viewMainTechnicalfile('"+data.id+"','"+data.dprtcode+"')\">"
				+data.pgdh+"</a></td>";
		htmlContent = htmlContent +"<td class='text-left' style='vertical-align:middle'>"+StringUtil.escapeStr(data.ly)+"</td>";
		htmlContent = htmlContent +"<td class='text-left' title='"+StringUtil.escapeStr(data.shzltgh)+"' style='vertical-align:middle'>"+StringUtil.escapeStr(data.shzltgh)+"</td>";
		htmlContent = htmlContent +"<td class='text-center' style='vertical-align:middle'>"+indexOfTime(data.sxrq)+"</td>";
		htmlContent = htmlContent +"<td class='text-left' style='vertical-align:middle'>"+StringUtil.escapeStr(data.pgr_user.displayName)+"</td>";
		htmlContent = htmlContent +"<td class='text-center' style='vertical-align:middle'>"+indexOfTime(data.pgqx)+"</td>";
		htmlContent = htmlContent +"<td class='text-left' style='vertical-align:middle'>"+zts[data.zt]+"</td>";
		htmlContent = htmlContent + "</tr>";
	});
	
	$("#Annunciatelist").empty();
	$("#Annunciatelist").html(htmlContent);
}
//异步删除评估单
function deleteOrderSource(pgdId){
	pgdids.remove(pgdId);
	var id=$('#annunciatezlbh').val();
	AjaxUtil.ajax({
		url:basePath + "/project/annunciate/deleteOrderSource",
		type:"post",
		data:{
			'pgdid' : pgdId,
			'zlbh' : id,
		},
		dataType:"json",
		success:function(data){
			if(data.state=="success"){
				$('#tr1_'+pgdId).remove();
			}
			
		}
	});
	
}

//同意
function agreedMain(){
	obj={
			'id':$('#annunciateId').val(),
			'shyj':$('#shyj').val(),
			'zt':2
	}
	submitMainAnnunciatesh(obj);
}
//驳回
function rejectedMain(){
	obj={
			'id':$('#annunciateId').val(),
			'shyj':$('#shyj').val(),
			'zt':5
	}
	submitMainAnnunciatesh(obj);
}
//提交
function submitMainAnnunciatesh(obj){
	//alert(JSON.stringify(obj));
	// 提交数据
	AjaxUtil.ajax({
		url:basePath + "/project/annunciate/submitshenheMainAnnunciate",
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType:"json",
		success:function(data){
			if(obj.zt==3){
				AlertUtil.showMessage('审核成功!','/project/annunciate/main');
			}else if(obj.zt==6){
				AlertUtil.showMessage('驳回成功!','/project/annunciate/main');
			}else{
				AlertUtil.showMessage('操作成功!','/project/annunciate/main');
				
			}
			
		}
	});

}

//同意
function agreedMainAnnunciate(){
	obj={
			'id':$('#annunciateId').val(),
			'pfyj':$('#pfyj').val(),
			'zt':3
	}
	submitMainAnnunciate(obj);
}
//驳回
function rejectedMainAnnunciate(){
	obj={
			'id':$('#annunciateId').val(),
			'pfyj':$('#pfyj').val(),
			'zt':6
	}
	submitMainAnnunciate(obj);
}
//中止
function suspendMainAnnunciate(){
	obj={
			'id':$('#annunciateId').val(),
			'pfyj':$('#pfyj').val(),
			'zt':4
	}
	submitMainAnnunciate(obj);
}
//提交
function submitMainAnnunciate(obj){
	//alert(JSON.stringify(obj));
	// 提交数据
	AjaxUtil.ajax({
		url:basePath + "/project/annunciate/submitpifuMainAnnunciate",
		type: "post",
		contentType:"application/json;charset=utf-8",
		data:JSON.stringify(obj),
		dataType:"json",
		success:function(data){
			if(obj.zt==3){
				AlertUtil.showMessage('批准成功!','/project/annunciate/main?id='+data+'&pageParam='+pageParam);
			}else if(obj.zt==6){
				AlertUtil.showMessage('驳回成功!','/project/annunciate/main?id='+data+'&pageParam='+pageParam);
			}else if(obj.zt==5){
				AlertUtil.showMessage('中止成功!','/project/annunciate/main?id='+data+'&pageParam='+pageParam);
			}else{
				AlertUtil.showMessage('操作成功!','/project/annunciate/main?id='+data+'&pageParam='+pageParam);
			}
		}
	});

}
//选择评估单
function searchRevision(){
	goPage(1,"auto","desc");
}
//加载已上传的附件
function selectChooseFj(){
	var mainid=$('#annunciateId').val();
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
		 /*htmlContent = htmlContent+'<td><div>';
		 htmlContent = htmlContent+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+row.id+ '\')" title="删除附件">  ';
		 htmlContent = htmlContent+'</div></td>';*/
		/* htmlContent = htmlContent+'<td>'+row.yswjm+'</td>';
		 htmlContent = htmlContent+'<td>'+row.nbwjm+'</td>';*/
		 htmlContent = htmlContent+"<td class='text-left'><a href=\"javascript:downloadfile('"+row.id+"')\">"+StringUtil.escapeStr(formatUndefine(row.wbwjm))+"</td>";
		 htmlContent = htmlContent+"<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.wjdxStr))+'</td>';
		 htmlContent = htmlContent+"<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.czr_user.displayName))+'</td>';
		 htmlContent = htmlContent+"<td class='text-center'>"+formatUndefine(row.czsj)+'</td>';
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

/*//上传
var fileid=1;
var uploadObj = $("#fileuploader").uploadFile({
	url:basePath+"/common/ajaxUploadFile",
	multiple:false,
	dragDrop:false,
	showStatusAfterSuccess: false,
	showDelete: false,
	
	maxFileCount:5,
	formData: {
		"fileType":"order"
		,"wbwjm" : function(){return $('#wbwjm').val();}
		},//参考FileType枚举（技术文件类型）
	fileName: "myfile",
	returnType: "json",
	removeAfterUpload:true,
	uploadStr:"上传",
	statusBarWidth:150,
	dragdropWidth:150,
	onSuccess:function(files,data,xhr,pd)  //上传成功事件，data为后台返回数据
	{
		var trHtml = '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''+data.attachments[0].uuid+'\'>';
		 trHtml = trHtml+'<td><div>';
//		 trHtml = trHtml+'<i class="icon-edit color-blue cursor-pointer" onclick="#(1)" title="修改 "></i>&nbsp;&nbsp;';
		 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+data.attachments[0].uuid+ '\')" title="删除附件">  ';
		 trHtml = trHtml+'</div></td>';
		 trHtml = trHtml+'<td>'+data.attachments[0].wbwjm+'</td>';
		 trHtml = trHtml+'<td>'+data.attachments[0].wjdx+'KB</td>';
		 trHtml = trHtml+'<td>'+data.attachments[0].user.username+' '+data.attachments[0].user.realname+'</td>';
		 trHtml = trHtml+'<td>'+data.attachments[0].czsj+'</td>';
		 
		 trHtml = trHtml+'<input type="hidden" name="fjid" value=\''+fileid+'\'/>';
		 trHtml = trHtml+'<input type="hidden" name="nbwjm" value=\''+data.attachments[0].nbwjm+'\'/>';
		 trHtml = trHtml+'<input type="hidden" name="yswjm" value=\''+data.attachments[0].yswjm+'\'/>';
		 trHtml = trHtml+'<input type="hidden" name="wjdx" value=\''+data.attachments[0].wjdx+'\'/>';
		 trHtml = trHtml+'<input type="hidden" name="wbwjm1" value=\''+data.attachments[0].wbwjm+'\'/>';
		 trHtml = trHtml+'<input type="hidden" name="cflj" value=\''+data.attachments[0].cflj+'\'/>';
		 
		 trHtml = trHtml+'</tr>';	 
		 $('#filelist').append(trHtml);
		 //fileid++;
	}
}); */
/**
* 删除附件
* @param newFileName
*/
function delfile(uuid) {
	var  responses = uploadObj.responses;
	var len = uploadObj.responses.length;
	var fileArray = [];
	var waitDelArray = [];
	if(len > 0 ) {
		for(var i =0;i<len;i++){
			if(responses[i].attachments[0].uuid != uuid){
				fileArray.push(responses[i]);
			}
		}
		uploadObj.responses = fileArray;
		uploadObj.selectedFiles -= 1;
		
	}
	$('#'+uuid).remove();
}
//附件下载
function downloadfile(id){
	//window.open(basePath + "/common/orderDownfile/" + id);
	PreViewUtil.handle(id, PreViewUtil.Table.D_011)
}
//获取附件上传信息
function getOrderAttachment(){
	var orderAttachment=[];
	/*var responses = uploadObj.responses;
	  var len = uploadObj.responses.length;
	  if(len != undefined && len>0){
		  for(var i =0;i<len;i++){
			  orderAttachment.push({
						'yswjm':responses[i].attachments[0].yswjm
						,'wbwjm':responses[i].attachments[0].wbwjm
						,'nbwjm':responses[i].attachments[0].nbwjm
						,'cflj':responses[i].attachments[0].cflj
						,'wjdx':responses[i].attachments[0].wjdx
					});
			}
	  }
	 return orderAttachment;*/
	$("#filelist").find("tr").each(function(){
		  var infos ={};
			var index=$(this).index(); //当前行
			var id =$("input[name='fjid']").eq(index).val();
			var nbwjm =$("input[name='nbwjm']").eq(index).val();
			var yswjm =$("input[name='yswjm']").eq(index).val(); 
			var wbwjm =$("input[name='wbwjm1']").eq(index).val(); 
			var cflj =$("input[name='cflj']").eq(index).val();
			var wjdx =$("input[name='wjdx']").eq(index).val();
			infos.id=id;
			infos.nbwjm=nbwjm;
			infos.yswjm=yswjm;
			infos.wbwjm=wbwjm;
			infos.cflj=cflj;
			infos.wjdx=wjdx;
			orderAttachment.push(infos);
		});
	  
	 return orderAttachment;
}
function back(){
	window.location.href =basePath+"/project/annunciate/main?pageParam="+pageParam;
}
