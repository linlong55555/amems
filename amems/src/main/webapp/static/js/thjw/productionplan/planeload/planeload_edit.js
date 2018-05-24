var delAttachements = [];
var index=3;
var deleteScwjId=[];
var deletebs01801Id=[];
$(function(){
		 Navigation(menuCode,"修改飞机载重","Edit AircraftLoad");
		 $('#zxrq').datepicker({
				autoclose : true,
				clearBtn : true
			});
		 		var fjzch=$("#planeloadFjzch").val();
			    var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:$("#dprtcode").val()});
			 	var planeRegOption = '';
				if(planeDatas != null && planeDatas.length >0){
					$.each(planeDatas,function(i,planeData){
						if(fjzch==planeData.FJZCH){
							planeRegOption += "<option selected='true' value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
						}else{
							planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
						}
					});
				}
				$("#fjzch").html(planeRegOption); 
				
		//回显部件震动
		bjzd();
		//回显上传文件
		selectChooseFj();
	 });
	

 var uploadObj = $("#fileuploader")
	.uploadFile(
			{
				url : basePath + "/common/ajaxUploadFile",
				multiple : true,
				dragDrop : false,
				showStatusAfterSuccess : false,
				showDelete : false,
				maxFileCount : 10,
				formData : {
					"fileType" : "maintenanceRecord",
					"wbwjm" : function() {
						return $('#wbwjm').val()
					}
				},
				fileName : "myfile",
				returnType : "json",
				removeAfterUpload : true,				
				showStatusAfterError: false,
				uploadStr : "<div class=\"font-size-12\">上传</div><div class=\"font-size-9\">Upload</div>",
				uploadButtonClass : "ajax-file-upload_ext",
				onSuccess : function(files, data, xhr, pd) // 上传成功事件，data为后台返回数据
				{
					var trHtml = '<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''
							+ data.attachments[0].uuid + '\'>';
					trHtml = trHtml + '<td><div>';
					trHtml = trHtml+ "<i class='icon-trash color-blue cursor-pointer' onclick=\"delfile('"+data.attachments[0].uuid+"' ,'"+1+"' )\" title='Delete 删除'>";
					
					trHtml = trHtml + '</div></td>';
					trHtml = trHtml + '<td title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'" class="text-left">'
							+ StringUtil.escapeStr(data.attachments[0].wbwjm) + '</td>';
					trHtml = trHtml + '<td class="text-left">'
							+ data.attachments[0].wjdxStr + ' </td>';
					trHtml = trHtml + '<td class="text-left" title="'+data.attachments[0].user.username + " "
					+ data.attachments[0].user.realname +'" >'
							+ data.attachments[0].user.username + " "
							+ data.attachments[0].user.realname + '</td>';
					trHtml = trHtml + '<td>' + data.attachments[0].czsj
							+ '</td>';
					trHtml = trHtml
							+ '<input type="hidden" name="relativePath" value=\''
							+ data.attachments[0].relativePath + '\'/>';

					trHtml = trHtml + '</tr>';
					$('#filelist').append(trHtml);
				}
				,onSubmit : function (files, xhr) {
					var wbwjm  = $.trim($('#wbwjm').val());
					if(wbwjm.length>0){
						if(/[<>\/\\|:"?*]/gi.test(wbwjm)){
			            	$('#wbwjm').focus();
			            	AlertUtil.showErrorMessage("文件说明不能包含如下特殊字符 < > / \ | : \" * ?");
			            	
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
 
 function validation(){
	 
	 $("#fjzch").css("border-color","");
	 $("#zxrq").css("border-color","");
	 $("#zxyzd_x1").css("border-color","");
	 $("#zxyzd_x2").css("border-color","");
	 $("#zxyzd_x3").css("border-color","");
	 $("#zxyzd_y1").css("border-color","");
	 $("#zxyzd_y2").css("border-color","");
	 $("#zxyzd_y3").css("border-color","");
	 $("#zxyzd_z1").css("border-color","");
	 $("#zxyzd_z2").css("border-color","");
	 $("#zxyzd_z3").css("border-color","");
	 $("#fjzl_sz").css("border-color","");
	 $("#zxzx_sz").css("border-color","");
	 $("#hxzx_sz").css("border-color","");
	 
	 $("input[name='bjmc']").each(function(i){			 
		 $(this).parent().next().next().children("input:first-child").css("border-color","");
      });
	 
	 var foal=true;
	 var re =new RegExp("^\\d+(\\.\\d+)?$");
	 if($("#fjzch").val()==null || $("#fjzch").val()==""){
		 AlertUtil.showErrorMessage('飞机注册号不能为空!');
		 return false;
	 }
	 
	 if($("#zxrq").val()==null || $("#zxrq").val()==""){
		 AlertUtil.showErrorMessage('日期不能为空!');
		 $("#zxrq").css("border-color","#a94442");
		 return false;
	 }
	 var zxyzd=['#zxyzd_x1','#zxyzd_x2','#zxyzd_x3',
	            '#zxyzd_y1','#zxyzd_y2','#zxyzd_y3',
	            '#zxyzd_z1','#zxyzd_z2','#zxyzd_z3','#wjzd'];
	 for(var i=0;i<zxyzd.length;i++){
		 var str=$(zxyzd[i]).val();
		 if($(zxyzd[i]).val()!=null && $(zxyzd[i]).val()!=""){
			 if(!re.test(str)){
				 AlertUtil.showErrorMessage('主旋翼震动只能输入数字或小数点');
				 $(zxyzd[i]).css("border-color","#a94442");
				 return false;
			 }
		 }
	 }
	 
	 var sz=['#fjzl_sz','#zxzx_sz','#hxzx_sz'];
	 var szName=['飞机总量','纵向重心','横向重心'];
	 for(var i=0;i<sz.length;i++){
		 var str=$(sz[i]).val();
		 if($(sz[i]).val()!=null && $(sz[i]).val()!=""){
			 if(!re.test(str)){
				 AlertUtil.showErrorMessage(szName[i]+'只能输入数字或小数点');
				 $(sz[i]).css("border-color","#a94442");
				 return false;
			 }
		 }
	 }
	 $("input[name='bjmc']").each(function(i){ 			 
		 zdz=$(this).parent().next().next().children("input:first-child").val();
		 if(zdz!=null && zdz!=""){
			 if(!re.test(zdz)){
				 AlertUtil.showErrorMessage('震动值只能输入数字或小数点');
				 $(this).parent().next().next().children("input:first-child").css("border-color","#a94442");
				 foal=false;
				 return false;
			 }
		 }
		 
		 
      });
	 return foal;
 }
 
 function add_tr(){
	 index+=1;
		 var str='<tr>'
		 +'<td class="text-center"><button class="line6" onclick="del_tr(this)"><i class="icon-minus color-blue cursor-pointer"></i></button></td>'
		 +'<td><input type="text" id="bjmc'+index+'" name="bjmc" class="col-lg-8 form-control text-left" style="width:77%" maxlength="100" />'
		 +'<input type="hidden" id="zjqdid'+index+'" value=""/><input type="hidden" id="bjid'+index+'" value=""/>'
		 +'<button type="button" '
		 +'class="btn btn-primary form-control"  style="width:22%" data-toggle="modal" onclick="openpart('+"'bjmc"+index+"'"+')">'
		 +'<i class="icon-search cursor-pointer"></i></button></td>'
		 +'<td><input type="text" class="form-control" maxlength="100" /></td>'
		 +'<td><input type="text" class="form-control" maxlength="9" /><input type="hidden" id="bs018Id'+index+'" class="form-control" value="1" /></td>'
		 +'</tr>'
		 $("#bjzd_list").append(str);
		 
	 }
	 function del_tr(obj){
		 var id=$(obj).parent("td").parent("tr").find("td").eq(3).find("input").eq(1).val();
		 if(id!="1"){
			 deletebs01801Id.push(id);
		 }
		 $(obj).parent("td").parent("tr").remove();
		
	 }
	 function delfile(uuid,id) {
			var responses = uploadObj.responses;
			var len = uploadObj.responses.length;
			var fileArray = [];
			var waitDelArray = [];
			if (len > 0) {
				for (var i = 0; i < len; i++) {
					if (responses[i].attachments[0].uuid != uuid) {
						fileArray.push(responses[i]);
					}
				}
				uploadObj.responses = fileArray;
				uploadObj.selectedFiles -= 1;
			}
			$('#' + uuid).remove();
			if(id!=1){
				deleteScwjId.push(id);
			}
			
		}
	 
	 function openpart(obj){
		 partModal.show({
			 	clearUser: true,
				selected : $(this).prev().prev().val(),
				callback : function(data) {
					$("#"+obj).val(formatUndefine(data.jh))
					$("#"+obj).next().val(formatUndefine(data.rwid));//装机清单id
					$("#"+obj).next().next().val(formatUndefine(data.id));	//部件id			
				},
				
			})
	 }
	 
	 var flag=true;
	 function info(){
		 flag=true;
		 var params=[];
		 $("input[name='bjmc']").each(function(i){			 
			 var obj={};
			 var bjmc=$(this).val();
			 obj.bjmc=bjmc;
			 obj.zjqdid=$(this).next().val();
			 obj.bjid=$(this).next().next().val();
			 var sxm=$(this).parent().next().children("input:first-child").val();
			 obj.sxm=sxm;
			 var zdz=$(this).parent().next().next().children("input:first-child").val();
			 obj.zdz=zdz;
			 obj.id=$(this).parent().next().next().children("input:first-child").next().val();
			 obj.dprtcode=$("#dprtcode").val();
			 obj.fjzch=$("#fjzch").val();
			 obj.zt=1;
			 obj.zxrq=$("#zxrq").val();
			 obj.zddwid=$("#zddwid").val();
			 obj.zdrid=$("#zdrid").val();
			 if(bjmc!=''&&(sxm==''&&zdz=='')){
				 AlertUtil.showErrorMessage('部件震动部件名不能为空且属性名或震动值不能为空!'); 
				 flag=false;				
			 }
			 if(bjmc!=''&&(sxm!=''||zdz!='')){
				 params.push(obj);
			 }
	      });	 
		 return params;
	 }
	 
	 function deletefile(uuid) {
			var key = $('#' + uuid).attr('key');
			if (key == undefined || key == null || key == '') {
				var responses = uploadObj.responses;
				var len = uploadObj.responses.length;
				var fileArray = [];
				var waitDelArray = [];
				if (len > 0) {
					for (var i = 0; i < len; i++) {
						if (responses[i].attachments[0].uuid != uuid) {
							fileArray.push(responses[i]);
						}
					}
					uploadObj.responses = fileArray;
					uploadObj.selectedFiles -= 1;
				}
				$('#' + uuid).remove();

			} else {
				$('#' + uuid).remove();
				delAttachements.push({
					id : key
				});
			}
		}
	 
	 function downloadfile(id) {
			PreViewUtil.handle(id, PreViewUtil.Table.D_011);

		}
		//回显部件震动
	function bjzd(){
		AjaxUtil.ajax({
			async: false,
			url : basePath + "/productionplan/planeload/queryListBymainid",
			type:"post",
			data:{mainid:$("#planeloadId").val()},
			dataType:"json",
			success:function(data){
				 initTableCol(data);
			}
		});
	}
	//拼接部件震动信息
	function  initTableCol(list){
		$.each(list,function(i,row){
			var num=1+i;
				index=num
			if(i<=2){
				$("#bjmc"+num).val(row.bjmc);
				$("#zjqdid"+num).val(row.zjqdid);
				$("#bjid"+num).val(row.bjid);
				$("#sxm"+num).val(row.sxm);
				$("#zdz"+num).val(row.zdz);
				$("#bs018Id"+num).val(row.id);
			}else{
				 var str='<tr>'
					 +'<td class="text-center"><button class="line6" onclick="del_tr(this)"><i class="icon-minus color-blue cursor-pointer"></i></button></td>'
					 +'<td><input type="text" id="bjmc'+num+'" name="bjmc" class="col-lg-8 form-control text-left" style="width:77%" value="'+row.bjmc+'" maxlength="100" />'
					 +'<input type="hidden" id="zjqdid'+num+'" value="'+row.zjqdid+'" /><input type="hidden" id="bjid'+num+'" value="'+row.bjid+'" />'
					 +'<button type="button" '
					 +'class="btn btn-primary form-control"  style="width:22%" data-toggle="modal" onclick="openpart('+"'bjmc"+num+"'"+')">'
					 +'<i class="icon-search cursor-pointer"></i></button></td>'
					 +'<td><input type="text" id="sxm'+num+'" class="form-control" value="'+row.sxm+'" maxlength="100" /></td>'
					 +'<td><input type="text" id="zdz'+num+'" class="form-control" value="'+row.zdz+'" maxlength="10" /><input type="hidden" id="bs018Id'+num+'" class="form-control" value="'+row.id+'" /></td>'
					 +'</tr>'
					 $("#bjzd_list").append(str);
			}
		});
	}
	//加载已上传的附件
	function selectChooseFj(){
		var mainid=$('#planeloadId').val();
		AjaxUtil.ajax({
		   url:basePath+"/productionplan/planeload/selectedScwjList",
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
		//alert(JSON.stringify(list));
		var htmlContent="";
		 $.each(list,function(index,row){ 
			 htmlContent = htmlContent+'<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''+row.id+'\'>';
			 htmlContent = htmlContent+'<td><div>';
//		 trHtml = trHtml+'<i class="icon-edit color-blue cursor-pointer" onclick="#(1)" title="修改 "></i>&nbsp;&nbsp;';
			 htmlContent = htmlContent+"<i class='icon-trash color-blue cursor-pointer' onclick=\"delfile('"+row.id+"' ,'"+row.id+"' )\" title='Delete 删除'>";
			 htmlContent = htmlContent+'</div></td>';
			/* htmlContent = htmlContent+'<td>'+row.yswjm+'</td>';
			 htmlContent = htmlContent+'<td>'+row.nbwjm+'</td>';*/
			 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.wbwjm))+"'><a href=\"javascript:downloadfile('"+row.id+"')\">"+StringUtil.escapeStr(formatUndefine(row.wbwjm))+"</a></td>";
			 htmlContent = htmlContent+"<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.wjdxStr))+'</td>';
			 htmlContent = htmlContent+"<td class='text-left'title='"+StringUtil.escapeStr(formatUndefine(row.czr_user.displayName))+"'>"+StringUtil.escapeStr(formatUndefine(row.czr_user.displayName))+'</td>';
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
	//修改提交
	function update(){
		
			 var param={};
			 param.id=$("#planeloadId").val();
			 param.fjzch=$("#fjzch").val();
			 param.zxrq=$("#zxrq").val();
			 param.ms=$("#ms").val();
			 param.zxyzd_x1=$("#zxyzd_x1").val();
			 param.zxyzd_x2=$("#zxyzd_x2").val();
			 param.zxyzd_x3=$("#zxyzd_x3").val();
			 param.zxyzd_y1=$("#zxyzd_y1").val();
			 param.zxyzd_y2=$("#zxyzd_y2").val();
			 param.zxyzd_y3=$("#zxyzd_y3").val();
			 param.zxyzd_z1=$("#zxyzd_z1").val();
			 param.zxyzd_z2=$("#zxyzd_z2").val();
			 param.zxyzd_z3=$("#zxyzd_z3").val();
			 param.wjzd=$("#wjzd").val();
			 param.gx=$("#gx").val();
			 param.fjzl_sz=$("#fjzl_sz").val();
			 param.fjzl_dw=$("#fjzl_dw").val();
			 param.zxzx_sz=$("#zxzx_sz").val();
			 param.zxzx_dw=$("#zxzx_dw").val();
			 param.hxzx_sz=$("#hxzx_sz").val();
			 param.hxzx_dw=$("#hxzx_dw").val();
			 param.bz=$("#bz").val();
			 param.zt=1;
			 param.dprtcode=$("#planeloadDprtcode").val();
			 param.zddwid=$("#zddwid").val();
			 //param.zdrid=$("#zdrid").val();
			 param.planeLoadInfolist=info();
			
			 param.planeLoadInfoIds=deletebs01801Id;
			 param.scwjIds=deleteScwjId;
			 var attachments = [];
				var responses = uploadObj.responses;
				var len = uploadObj.responses.length;
				if (len != undefined && len > 0) {
					for (var i = 0; i < len; i++) {
						attachments.push({
							'yswjm' : responses[i].attachments[0].yswjm,
							'wbwjm' : responses[i].attachments[0].wbwjm,
							'nbwjm' : responses[i].attachments[0].nbwjm,
							'wjdx' : responses[i].attachments[0].wjdx,
							'cflj' : responses[i].attachments[0].cflj,
							'id' : responses[i].attachments[0].id,
							'operate' : responses[i].attachments[0].operate

						});
					}
				}
				var dellen = delAttachements.length;
				if (dellen != undefined && dellen > 0) {
					for (var i = 0; i < dellen; i++) {
						attachments.push({
							'id' : delAttachements[i].id,
							'operate' : 'DEL'

						});
					}
				}
			 param.attachments = attachments;
			//验证
			 var b=validation();
			if(b==true&&flag==true){
			 AjaxUtil.ajax({
					url : basePath + "/productionplan/planeload/edit",
					type : "post",
					data : JSON.stringify(param),
					contentType : "application/json;charset=utf-8",
					dataType : "json",
					success : function(data) {
						finishWait();
						AlertUtil.showMessage('修改成功!','/productionplan/planeload/main?id=' + data
								+ '&pageParam=' + pageParam);
					}
				});
			 }
			   
	}
	
	function back(){
		 window.location =basePath+"/productionplan/planeload/main?id="+id+'&pageParam=' + pageParam;
	}