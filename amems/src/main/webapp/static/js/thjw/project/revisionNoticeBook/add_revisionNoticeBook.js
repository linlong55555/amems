var pgdids=[];
var no=[];
var zt=[0,1,2,3,4,5,6,7,8,9];
var zts=["未评估","已评估","已审核","已批准","中止（关闭）","审核驳回","批准驳回","","作废","指定结束"];
var validatorForm = {};
$(document).ready(function(){
	Navigation(menuCode,"新增修订通知书","Add");
	
	//机型回显
	jxhx();
	$('#xdzt').on('change', function(e) {
		$('#form').data('bootstrapValidator')  
		.updateStatus('xdzt', 'NOT_VALIDATED',null)  
		.validateField('xdzt');  
	});
	$('#xdqx').datepicker({
			 autoclose: true,
			 clearBtn:true
		}).on('hide', function(e) {
			  $('#form').data('bootstrapValidator')  
              .updateStatus('xdqx', 'NOT_VALIDATED',null)  
              .validateField('xdqx');  
	    });
	
		//提交编辑
		$('#submit').on('click',function(){
			var zt=1;
			edit(zt);
		});
		
		//保存编辑
		$('#edit').on('click',function(){
			var zt=0;
			edit(zt);
		});
		
	  $('#alertModal').on('hide.bs.modal', function () {
		window.location.href =  basePath+"/project/revisionNoticeBook/main";
	  })
		
	  $('#alertModal').on('hidden.bs.modal', function () {
	 	window.location.href =  basePath+"/project/revisionNoticeBook/main";
	  })
	   
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
	            },
	            bb: {
	                validators: {
	                    regexp: {
	                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
	                        message: '不能输入中文'
	                    }
	                }
	            }
	            
	            
	            	            
	        }
	    });
	  

	  jxgcs();
});

function jxhx(){
    var dprtcode = $.trim($("#jgdm").val());
	 var planeRegOption = '';
	 var planeDatas = acAndTypeUtil.getACTypeList({DPRTCODE:dprtcode});
	 if(planeDatas != null && planeDatas.length >0){
	  	$.each(planeDatas,function(i,planeData){
	  	    planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJJX)+"' >"+StringUtil.escapeStr(planeData.FJJX)+"</option>";
	  	});
	 }
	  /*if(planeRegOption == ''){
	  	planeRegOption = '<option value="">暂无飞机 No plane</option>';
	  }*/
	  $("#jx").append(planeRegOption);
}



    /**
     * 编辑修订通知书
     * @param zt
     */
	function edit(zt) {
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
		
	  var jx = $.trim($("#jx").val());
	  var xdrid = $.trim($("#xdrid").val());
	  var xdqx = $('#xdqx').val();
	  var xdzt = $.trim($("#xdzt").val());
	  var bz = $.trim($("#bz").val());
	  var xdnr = $.trim($("#xdnr").val());
	  var bb = $.trim($("#bb").val());
	 
	  //参数组装
	  var obj = {
			  "orderSourceList":getPgdIdAndPgdh(),
			  "ckzl":getShzlh(),
			  "jx":jx,
			  "xdrid":xdrid,
			  "xdqx":xdqx,
			  "xdzt":xdzt,
			  "xdnr":xdnr,
			  "bz":bz,
			  "zt":zt,
			  "bb":bb,
			  "orderAttachmentList":getOrderAttachment(),
			  "tzslx":1		//通知书类型1.维修方案 2.MEL 3.工单
	  };
		  
	    
	  AjaxUtil.ajax({
		  url:'addRevisionNoticeBook',					//发送请求的地址（默认为当前页地址）
		  type:'post',										//请求方式（post或get）默认为get
		  data:JSON.stringify(obj),							//发送到服务器的数据
		  contentType:"application/json;charset=utf-8", 	//发送到服务器的数据内容编码类型
		  dataType:'json',									//预期服务器返回的数据类型
		  cache:false,	                                    //缓存（true有缓存，false无缓存）
		  async: false,
		  success:function(data) {							//请求成功后调用的回调函数
			  if(obj.zt==0){
				  if(data.state=="Failure"){
					  AlertUtil.showErrorMessage(data.message);
				  }else{
					  AlertUtil.showMessage('保存成功!','/project/revisionNoticeBook/main?id='+data.id+'&pageParam='+pageParam);
				  }
				}else if(obj.zt==1){
					if(data.state=="Failure"){
						  AlertUtil.showErrorMessage(data.message);
					  }else{
						  AlertUtil.showMessage('提交成功!','/project/revisionNoticeBook/main?id='+data.id+'&pageParam='+pageParam);
					  }
					
				}else{
					if(data.state=="Failure"){
						  AlertUtil.showErrorMessage(data.message);
					  }else{
						  AlertUtil.showMessage('操作成功!','/project/revisionNoticeBook/main?id='+data.id+'&pageParam='+pageParam);
					  }
					
				}
		  }
	  });
	}
	//打开评估单列表对话框
	function openPgd() {
		var jx=$('#jx').val();
		if(jx=="" || jx==null){
			 AlertUtil.showErrorMessage("请先选择机型");
			 return
		}
		
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
		obj.isWxfa=1;	//技术指令复选为1
		obj.jx=$('#jx').val();//机型
		obj.dprtcode=$('#jgdm').val();
		obj.keyword=$.trim($('#keyword_search').val());//关键字
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
					signByKeyword($("#keyword_search").val(), [ 2, 3, 8 ],"#Pgdlist tr td");
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
			htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle' title='"+StringUtil.escapeStr(ckzl)+"'>"+StringUtil.escapeStr(ckzl)+"</td>";  
			htmlContent = htmlContent + "<td class='text-center' style='vertical-align:middle'>"+(sxrq.substring(0,10))+"</td>";
			htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'>"+username+" "+realname+"</td>";
			htmlContent = htmlContent + "<td class='text-center' style='vertical-align:middle'>"+(pgqx.substring(0,10))+"</td>";  
			htmlContent = htmlContent + "<td class='text-left' style='vertical-align:middle'>"+zts[pgzt]+"</td>";
			htmlContent = htmlContent + "</tr>";
			pgdids.push(pgdId);
			
			if($('#xdzt').val()=="" || $('#xdzt').val()==null){
				$('#xdzt').val(wjzt);
				/*$('#zhut').on('change', function(e) {
					$('#form').data('bootstrapValidator')  
					.updateStatus('zhut', 'NOT_VALIDATED',null)  
					.validateField('zhut');  
				});*/
				$('#xdzt').change();
			}
			  });
			//$("#Annunciatelist").empty();
		    $("#Annunciatelist").append(htmlContent);
		    if($("#Annunciatelist tr").length>0){
		    	$('#jx').attr("disabled",true);
		    }
	}
	//删除行
	function lineRemove(pgdid){
		pgdids.remove(pgdid);
		//alert(pgdids);
		$('#tr1_'+pgdid).remove();
		if($("#Annunciatelist tr").length<=0){
	    	$('#jx').attr("disabled",false);
	    }
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
	//获取选择后的所有评估单id
	function getPgdIdAndPgdh(){
		var pgdIdAndPgdh=[];
		$("#Annunciatelist").find("tr input[name='PgdIdAndPgdh']").each(function(){
		var ordersource={};
			ordersource.pgdid=$(this).val().split(',')[0]; 
			ordersource.pgdh=$(this).val().split(',')[1]; 
			pgdIdAndPgdh.push(ordersource);
		});
		return pgdIdAndPgdh;
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
	function viewMainTechnicalfile(id,dprtcode){
		window.open(basePath+"/project/technicalfile/findApprovalFileUpload/"+$.trim(id)+"/"+$.trim(dprtcode)+"");
	}
	//上传
	var uploadObj = $("#fileuploader").uploadFile({
		url:basePath+"/common/ajaxUploadFile",
		multiple:true,
		dragDrop:false,
		showStatusAfterSuccess: false,
		showDelete: false,
		
		formData: {
			"fileType":"order"
			,"wbwjm" : function(){return $('#wbwjm').val()}
			},//参考FileType枚举（技术文件类型）
		fileName: "myfile",
		returnType: "json",
		removeAfterUpload:true,
		showStatusAfterSuccess : false,
		showStatusAfterError: false,
		uploadStr:"<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
		uploadButtonClass: "ajax-file-upload_ext",
		onSuccess:function(files,data,xhr,pd)  //上传成功事件，data为后台返回数据
		{
			var trHtml = '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''+data.attachments[0].uuid+'\'>';
			 trHtml = trHtml+'<td><div>';
//			 trHtml = trHtml+'<i class="icon-edit color-blue cursor-pointer" onclick="#(1)" title="修改 "></i>&nbsp;&nbsp;';
			 trHtml = trHtml+'<i class="icon-trash color-blue cursor-pointer" onclick="delfile(\''+data.attachments[0].uuid+ '\')" title="Delete 删除">  ';
			 trHtml = trHtml+'</div></td>';
			 //trHtml = trHtml+'<td>'+data.attachments[0].yswjm+'</td>';
			 //trHtml = trHtml+'<td>'+data.attachments[0].nbwjm+'</td>';
			 trHtml = trHtml+"<td class='text-left' title='"+StringUtil.escapeStr(data.attachments[0].wbwjm)+"'>"+StringUtil.escapeStr(data.attachments[0].wbwjm)+'</td>';
			  
			 trHtml = trHtml+"<td class='text-left'>"+StringUtil.escapeStr(data.attachments[0].wjdxStr)+'</td>';
			 trHtml = trHtml+"<td class='text-left'>"+data.attachments[0].user.displayName+'</td>';
			 trHtml = trHtml+"<td class='text-center'>"+data.attachments[0].czsj+'</td>';
			 trHtml = trHtml+'<input type="hidden" name="relativePath" value=\''+data.attachments[0].relativePath+'\'/>';
			 
			 trHtml = trHtml+'</tr>';	 
			 $('#filelist').append(trHtml);
		}
			//附件特殊字符验证（文件说明限制字符和windows系统保持一致）
			,onSubmit : function (files, xhr) {
				var wbwjm  = $.trim($('#wbwjm').val());
				if(wbwjm.length>0){
					if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
		            	$('#wbwjm').focus();
		            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \\ | : \" * ?");
		            	
		            	$('.ajax-file-upload-container').html("");
						uploadObj.selectedFiles -= 1;
						return false;
		            } 
		            else{
		            	return true; 
		            }
				}else{
					return true;
				}
			}
			
		});
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
			$('#'+uuid).remove();
		}
	}
	//获取附件上传信息
	function getOrderAttachment(){
		var orderAttachment=[];
		  var responses = uploadObj.responses;
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
		 return orderAttachment;
	}
	function back(){
		window.location.href =basePath+"/project/revisionNoticeBook/main?pageParam="+pageParam;
	}
	
	//机型工程师的改变事件
	function jxgcs(){
		var jx=$("#jx").val();
		if(jx==null || jx==""){
			return false
		}
		//清空机型工程师
		$("#xdrid").val("");
		var user={};
		user.jgdm=$("#jgdm").val();
		user.jx=jx;
		AjaxUtil.ajax({
			//url:"saveUser",
			url:basePath+"/sys/user/queryAllByjx",
			type: "post",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(user),
			async:false,
			dataType:"json",
			success:function(data){
				finishWait();//结束Loading
				var htmlContent="";
				 $.each(data,function(index,row){
					 htmlContent+= "<option value='"+row.id+"' >"+row.displayName+"</option>";
				 });
				 $("#xdrid").empty();
				 $("#xdrid").append(htmlContent);
			}
		});
		
	}
	//选择评估单
	function searchRevision(){
		
		goPage(1,"auto","desc");
	}