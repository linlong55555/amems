var pgdids=[];
var no=[];
var zt=[0,1,2,3,4,5,6,7,8,9];
var zts=["未评估","已评估","已审核","已批准","中止（关闭）","审核驳回","批准驳回","","作废","指定结束"];
var validatorForm = {};
var attachmentSingle = {};
$(document).ready(function(){
	Navigation(menuCode,"修改MEL更改单","Edit");
	//加载已选择的技术评估单
	selectChoosePgd();
	initPlaneModel();
	initMelBasis();
	$('#jx').attr("disabled",true);
	AttachmengsList.show({
		djid:$("#mcsId").val(),
		fileType:"mel"
	});//显示附件
	initInfo();
	 validatorForm =  $('#form').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	ggdbh: {
	                validators: {
	                	notEmpty: {
	                        message: '文件编号不能为空'
	                    },
	                    regexp: {
	                    	regexp: /^[^\u4e00-\u9fa5]{0,50}$/,
	                        message: '不能输入中文且长度最多不超过50个字符'
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
	            xghBb: {
	                validators: {
	                    notEmpty: {
	                        message: '修改后版本不能为空'
	                    }
	                }
	            }            
	        }
	    });
});

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
		 attachmentSingle.id = attachment.id;
		 attachmentSingle.yswjm = attachment.yswjm;
		 attachmentSingle.wbwjm = attachment.wbwjm;
		 attachmentSingle.nbwjm = attachment.nbwjm;
		 attachmentSingle.cflj = attachment.cflj;
		 attachmentSingle.wjdx = attachment.wjdx;
		 $("#wbwjmSingle").val(attachment.wbwjm);
		 var t = '';
		 t += '<a class="pull-left" style="color: blue;" href="javascript:downloadfile(\''+attachment.id+'\')">';
		 t += '<i class="icon-download-alt pull-left color-blue" style="font-size:25px" title="附件下载 download"></i>';
		 t += '</a>';
		 t += '&nbsp;<i class="icon-remove-sign cursor-pointer color-blue cursor-pointer" style="font-size:20px" title="删除 Delete" onclick=removeFile(this)></i>';
		 $("#melqdDiv").html(t);
	}
	var melChangeSheetAndBasiList = obj.melChangeSheetAndBasiList;
	if(melChangeSheetAndBasiList != null && melChangeSheetAndBasiList.length > 0){
		$.each(melChangeSheetAndBasiList,function(index,o){
			$("#xgyjDiv .basisBox[value='"+o.yjlx+"']").attr("checked",true);
			$("#xgyjDiv .basisBox[value='"+o.yjlx+"']").parent().parent().find(".xgyjValue").removeAttr("disabled");
			$("#xgyjDiv .basisBox[value='"+o.yjlx+"']").parent().parent().find(".xgyjValue").val(o.yjnr);
		});
	}
}

function removeFile(){
	$("#wbwjmSingle").val("");
	$("#melqdDiv").empty();
	attachmentSingle = {};
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

function initMelBasis(){
	$("#xgyjDiv").empty();
	var str = '';
	var data = DicAndEnumUtil.getDicItems('41',$("#dprtcode").val());
	if(data != null && data.length >0){
		$.each(data,function(i,obj){
			str += '<div class="input-group margin-bottom-10">';
			str += '<span class="input-group-addon" style="padding-left:0px;border:0px;background:none;">';
			str += '<input class="basisBox" value="'+StringUtil.escapeStr(obj.name)+'" type="checkbox" onclick="checkRow(this)"/>';
			str += StringUtil.escapeStr(obj.name)+":";
			str += '</span>';
			str += '<input type="text" class="xgyjValue form-control" />';
			str += '</div>';
		});
  	}
	$("#xgyjDiv").html(str);
	$(".xgyjValue").attr("disabled","true");
}

function checkRow(obj){
	if($(obj).is(':checked')){
		$(obj).parent().parent().find(".xgyjValue").removeAttr("disabled");
	}else{
		$(obj).parent().parent().find(".xgyjValue").attr("disabled","true");
	}
}

//保存
function save(){
	var url = basePath+"/project/mel/editSave";
	var message = "保存成功!";
	performAction(url,message);
}
//提交
function submt(){
	var url = basePath+"/project/mel/editSubmit";
	var message = "提交成功!";
	performAction(url,message);
}

	function performAction(url,message) {
		
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
	  
	  var ggdbh = $.trim($("#ggdbh").val());
	  var jx = $.trim($("#jx").val());
	  var xmh = $.trim($("#xmh").val());
	  var ssbf = $.trim($('#ssbf').val());
	  var sszj = $.trim($('#sszj').val());
	  var zy = $.trim($('#zy').val());
	  var xgqBb = $.trim($('#xgqBb').val());
	  var xghBb = $.trim($('#xghBb').val());
	  var xdqx = $.trim($('#xdqx').val());
	  var xdnr = $.trim($('#xdnr').val());
	  var xgyy = $.trim($('#xgyy').val());
	  
	  var xgbj = '';
	  $('input:checkbox[name=xgbj]:checked').each(function(){
		  xgbj += $(this).val() + ",";
	  });
	  if(xgbj != ''){
		  xgbj = xgbj.substring(0,xgbj.length-1);
	  }
	  //参数组装
	  var obj = {
			  id : $("#mcsId").val(),
			  ggdbh:ggdbh,
			  jx:jx,
			  xmh:xmh,
			  ssbf:ssbf,
			  sszj:sszj,
			  zy:zy,
			  xgqBb:xgqBb,
			  xghBb:xghBb,
			  xdqx:xdqx,
			  xgbj:xgbj,
			  xdnr:xdnr,
			  xgyy:xgyy,
			  melqdfjid : $("#melqdfjid").val(),
			  dprtcode:$("#dprtcode").val()
	  };
	  obj.technicalFileList = getPgdIdAndPgdh();
	  obj.melChangeSheetAndBasiList = getMelChangeSheetAndBasiList();
	  obj.attachments = AttachmengsList.getAttachments();
	  if(attachmentSingle != null && attachmentSingle.wjdx != null){
		  obj.attachment = attachmentSingle;
	  }
	  startWait();
	  AjaxUtil.ajax({
		  url:url,			//发送请求的地址（默认为当前页地址）
		  type:'post',										//请求方式（post或get）默认为get
		  data:JSON.stringify(obj),							//发送到服务器的数据
		  contentType:"application/json;charset=utf-8", 	//发送到服务器的数据内容编码类型
		  dataType:'json',									//预期服务器返回的数据类型
		  cache:false,	                                    //缓存（true有缓存，false无缓存）
		  async: false,
		  success:function(data) {
			  finishWait();//请求成功后调用的回调函数
			  AlertUtil.showMessage(message, '/project/mel/main?id='+data+'&pageParam='+pageParam);
		  }
	  });
	}
//获取更改单-修改依据	
function getMelChangeSheetAndBasiList(){
	var melChangeSheetAndBasiList = [];
	$('#xgyjDiv .basisBox:checked').each(function(){
		  var info = {};
		  info.yjlx = $.trim($(this).val());
		  info.yjnr =$(this).parent().parent().find(".xgyjValue").val();
		  melChangeSheetAndBasiList.push(info);
	});
	return melChangeSheetAndBasiList;
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
		obj.isXdmel=1;	//技术指令复选为1
		obj.jx=$('#jx').val();//机型
		obj.dprtcode=$('#dprtcode').val();
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
			/*htmlContent += "<td align='center'><a href=\"javascript:lineRemove('"+data.id+"')\"><i class='icon-minus cursor-pointer' ></i></a></td>";*/
			htmlContent = htmlContent + "<td><button class='line6' onclick=\"lineRemove('"+data.id+"')\" type='button'><i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i></button></td>";
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
			ordersource.id=$(this).val().split(',')[0]; 
			ordersource.pgdh=$(this).val().split(',')[1]; 
			pgdIdAndPgdh.push(ordersource);
		});
		return pgdIdAndPgdh;
	}
	function viewMainTechnicalfile(id,dprtcode){
		window.open(basePath+"/project/technicalfile/findApprovalFileUpload/"+$.trim(id)+"/"+$.trim(dprtcode)+"");
	}
	
	//上传
	var uploadObjSingle = $("#fileuploaderSingle").uploadFile({
		url:basePath+"/common/ajaxUploadFile",
		multiple:true,
		dragDrop:false,
		showStatusAfterSuccess: false,
		showDelete: false,
		
		formData: {
			"fileType":"order"
			,"wbwjmSingle" : function(){return $('#wbwjmSingle').val()}
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
			 attachmentSingle.yswjm = data.attachments[0].yswjm;
			 attachmentSingle.wbwjm = data.attachments[0].wbwjm;
			 attachmentSingle.nbwjm = data.attachments[0].nbwjm;
			 attachmentSingle.cflj = data.attachments[0].cflj;
			 attachmentSingle.wjdx = data.attachments[0].wjdx;
			 $("#wbwjmSingle").val(data.attachments[0].wbwjm);
			 $("#melqdDiv").html('');
		}
			//附件特殊字符验证（文件说明限制字符和windows系统保持一致）
			,onSubmit : function (files, xhr) {
				var wbwjm  = $.trim($('#wbwjmSingle').val());
				if(wbwjm.length>0){
					if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
		            	$('#wbwjmSingle').focus();
		            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \\ | : \" * ?");
		            	
		            	$('.ajax-file-upload-container').html("");
						uploadObjSingle.selectedFiles -= 1;
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
	
	//选择评估单
	function searchRevision(){
		
		goPage(1,"auto","desc");
	}