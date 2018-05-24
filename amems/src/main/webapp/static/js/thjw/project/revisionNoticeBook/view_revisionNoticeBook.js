var zt=[0,1,2,3,4,5,6,7,8,9];
var zts=["未评估","已评估","已审核","已批准","中止（关闭）","审核驳回","批准驳回","","作废","指定结束"];
var pgdids=[];
var oldScwjList=[];
$(document).ready(function(){
	
	//加载已选择的技术评估单
	selectChoosePgd();	
	
	//加载已上传的附件
	selectChooseFj();
	
	//加载完成说明
	FinishExplanation();
	
		$('#xdqx').datepicker({
			 autoclose: true,
			 clearBtn:true
		}).on('hide', function(e) {
			  $('#form').data('bootstrapValidator')  
		     .updateStatus('xdqx', 'NOT_VALIDATED',null)  
		     .validateField('xdqx');  
		   });

		var zt = $('#zt').val();
		if(zt!=''){
			$('#zt').val(DicAndEnumUtil.getEnumName('revisionNoticeBookZtEnum',zt));
		}
		 
		//提交编辑
		$('#submit').on('click',function(){
			edit(1);
		});
		
		//保存编辑
		$('#edit').on('click',function(){
			edit(0);
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
	            jx: {
	                validators: {
	                    notEmpty: {
	                        message: '机型不能为空'
	                    }
	                }
	            },
	            ckzl: {
	                validators: {
	                    notEmpty: {
	                        message: '通知书类型不能为空'
	                    }
	                }
	            },
	            xdzt: {
	                validators: {
	                    notEmpty: {
	                        message: '修订主题不能为空'
	                    }
	                }
	            },	
	            xdqx: {
	                validators: {
	                    notEmpty: {
	                        message: '修订期限不能为空'
	                    }
	                }
	            },	
	            xdrid: {
	                validators: {
	                    notEmpty: {
	                        message: '修订人不能为空'
	                    }
	                }
	            }
	            
	            
	            	            
	        }
	    });
		
		
		
		//初始化日志功能
		if(	$("#jg").val()=="1"){
		logModal.init({code:'B_G_005',id:$('#revisionNoticeBookId').val()});	
		}
});

	//加载已选择的关联评估单
	function selectChoosePgd(){
		var id=$('#revisionNoticeBookId').val();
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
			/*htmlContent += "<td align='center'>--</td>";*/
			htmlContent += "<td class='text-center' style='vertical-align:middle'>" +
					"<input type='hidden' name='PgdIdAndPgdh' value="+data.id+","+StringUtil.escapeStr(data.pgdh)+"><input type='hidden' name='Ckzl' value="+StringUtil.escapeStr(data.shzltgh)+">"+
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
	//审核同意
	function agreedMain(){
	var	obj={
				'id':$('#revisionNoticeBookId').val(),
				'shyj':$('#shyj').val(),
				'zt':2
		};
	AlertUtil.showConfirmMessage("您确定要审核通过吗？",{callback: function(){
		submitshenheMainRevisionNoticeBook(obj);
	}});
	
	}
	//审核驳回
	function rejectedMain(){
		var shyj=$('#shyj').val();
		if(shyj=="" || shyj==null){
			AlertUtil.showErrorMessage("审核意见不能为空!");
			return false;
		}
		var	obj={
				'id':$('#revisionNoticeBookId').val(),
				'shyj':shyj,
				'zt':5
		};
		AlertUtil.showConfirmMessage("您确定要审核驳回吗？",{callback: function(){
			submitshenheMainRevisionNoticeBook(obj);
		}});

	}
	//批准同意
	function agreedMainInstruction(){
		var	obj={
				'id':$('#revisionNoticeBookId').val(),
				'pfyj':$('#pfyj').val(),
				'zt':3,
				'tzslx':$("#tzslx").val()
		};
		AlertUtil.showConfirmMessage("您确定要批准通过吗？",{callback: function(){
			submitpifuMainRevisionNoticeBook(obj);
		}});
		
	}
	
	//批准驳回
	function rejectedMainInstruction(){
		var pfyj=$('#pfyj').val();
		if(pfyj=="" || pfyj==null){
			AlertUtil.showErrorMessage("批准意见不能为空!");
			return false;
		}
		var	obj={
				'id':$('#revisionNoticeBookId').val(),
				'pfyj':pfyj,
				'zt':6
		};
		AlertUtil.showConfirmMessage("您确定要批准驳回吗？",{callback: function(){
			submitpifuMainRevisionNoticeBook(obj);
		}});
		
	}
	
	//批准中止
	function suspendMainInstruction(){
		var pfyj=$('#pfyj').val();
		if(pfyj=="" || pfyj==null){
			AlertUtil.showErrorMessage("批准意见不能为空!");
			return false;
		}
		var	obj={
				'id':$('#revisionNoticeBookId').val(),
				'pfyj':$('#pfyj').val(),
				'zt':4
		};
		AlertUtil.showConfirmMessage("您确定要中止(关闭)吗？",{callback: function(){
			submitpifuMainRevisionNoticeBook(obj);
		}});
		
	}
	
	//审核提交
	function submitshenheMainRevisionNoticeBook(obj){
		//alert(JSON.stringify(obj));
		// 提交数据
		AjaxUtil.ajax({
			url:basePath + "/project/revisionNoticeBook/submitshenheMainRevisionNoticeBook",
			type: "post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(obj),
			dataType:"json",
			success:function(data){
				var tzslx=$("#tzslx").val();
				if(obj.zt==2){
					if(tzslx==1){
						AlertUtil.showMessage('审核成功!','/project/revisionNoticeBook/main?id='+obj.id+'&pageParam='+pageParam);
					}else if(tzslx==2){
						AlertUtil.showMessage('审核成功!','/project/revisionNoticeBook/mel_main?id='+obj.id+'&pageParam='+pageParam);
					}else{
						AlertUtil.showMessage('审核成功!','/project/revisionNoticeBook/card_main?id='+obj.id+'&pageParam='+pageParam);
					}
				}else if(obj.zt==5){
					if(tzslx==1){
						AlertUtil.showMessage('批准成功!','/project/revisionNoticeBook/main?id='+obj.id+'&pageParam='+pageParam);
					}else if(tzslx==2){
						AlertUtil.showMessage('批准成功!','/project/revisionNoticeBook/mel_main?id='+obj.id+'&pageParam='+pageParam);
					}else{
						AlertUtil.showMessage('批准成功!','/project/revisionNoticeBook/card_main?id='+obj.id+'&pageParam='+pageParam);
					}
				}else{
					if(tzslx==1){
						AlertUtil.showMessage('操作成功!','/project/revisionNoticeBook/main?id='+obj.id+'&pageParam='+pageParam);
					}else if(tzslx==2){
						AlertUtil.showMessage('操作成功!','/project/revisionNoticeBook/mel_main?id='+obj.id+'&pageParam='+pageParam);
					}else{
						AlertUtil.showMessage('操作成功!','/project/revisionNoticeBook/card_main?id='+obj.id+'&pageParam='+pageParam);
					}
					
				}
				
			}
		});

	}
	//批复提交
	function submitpifuMainRevisionNoticeBook(obj){
		//alert(JSON.stringify(obj));
		// 提交数据
		AjaxUtil.ajax({
			url:basePath + "/project/revisionNoticeBook/submitpifuMainRevisionNoticeBook",
			type: "post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(obj),
			dataType:"json",
			success:function(data){
				if(obj.zt==3){
					AlertUtil.showMessage('批准成功!','/project/revisionNoticeBook/main?id='+obj.id+'&pageParam='+pageParam);
				}else if(obj.zt==6){
					AlertUtil.showMessage('驳回成功!','/project/revisionNoticeBook/main?id='+obj.id+'&pageParam='+pageParam);
				}else if(obj.zt==4){
					AlertUtil.showMessage('中止成功!','/project/revisionNoticeBook/main?id='+obj.id+'&pageParam='+pageParam);
				}else{
					AlertUtil.showMessage('操作成功!','/project/revisionNoticeBook/main?id='+obj.id+'&pageParam='+pageParam);
					
				}
				
			}
		});
 
	}
	//完成说明
	function FinishExplanation(){
		var wcrq=$('#wcrq').val();
		//alert(wcrq);
		if(wcrq!=""){
			$('#wc').fadeIn();
		}
	}
	
	function viewMainTechnicalfile(id,dprtcode){
		window.open(basePath+"/project/technicalfile/findApprovalFileUpload/"+$.trim(id)+"/"+$.trim(dprtcode)+"");
	}
	
	//加载已上传的附件
	function selectChooseFj(){
		var mainid=$('#revisionNoticeBookId').val();
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
			 htmlContent = htmlContent+"<td class='text-left'  title='"+StringUtil.escapeStr(row.wbwjm)+"'><a href=\"javascript:downloadfile('"+row.id+"')\">"+StringUtil.escapeStr(formatUndefine(row.wbwjm))+"</td>";
			 htmlContent = htmlContent+"<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.wjdxStr))+'</td>';
			 htmlContent = htmlContent+"<td class='text-left'>"+formatUndefine(row.czr_user.displayName)+'</td>';
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
	
	//附件下载
	function downloadfile(id){
		PreViewUtil.handle(id, PreViewUtil.Table.D_011)
		//window.open(basePath + "/common/orderDownfile/" + id);
	}
	//接收状态
	function updateJszt(obj){
		AjaxUtil.ajax({
			   url:basePath+"/project/revisionNoticeBook/updateJszt",
			   type: "post",
			   dataType:"json",
			   contentType:"application/json;charset=utf-8",
			   data:JSON.stringify(obj),
			   success:function(data){
				   
		   }
		 });
	}
	function back(){
		var tzslx=$("#tzslx").val();
		 if(tzslx==1){
			 window.location.href =basePath+"/project/revisionNoticeBook/main?pageParam="+pageParam;
		  }else if(tzslx==2){
			  window.location.href =basePath+"/project/revisionNoticeBook/mel_main?pageParam="+pageParam;
		  }else{
			  window.location.href =basePath+"/project/revisionNoticeBook/card_main?pageParam="+pageParam;
		  }
		
	}
	
	
	
	
	
	