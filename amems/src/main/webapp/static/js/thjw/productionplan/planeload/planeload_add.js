var delAttachements = [];
var index=3;
$(function(){
		 Navigation(menuCode,"新增飞机载重","Add AircraftLoad");
		 $('#zxrq').datepicker({
				autoclose : true,
				clearBtn : true
			});
			    var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:$("#dprtcode").val()});
			 	var planeRegOption = '';
				if(planeDatas != null && planeDatas.length >0){
					$.each(planeDatas,function(i,planeData){
						planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
					});
				}
				$("#fjzch").html(planeRegOption); 
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
					trHtml = trHtml
							+ '<i class="icon-trash color-blue cursor-pointer"  onclick="delfile(\''
							+ data.attachments[0].uuid
							+ '\')" title="删除 Delete">  ';
					trHtml = trHtml + '</div></td>';
					trHtml = trHtml + '<td title="'+StringUtil.escapeStr(data.attachments[0].wbwjm)+'" class="text-left">'
							+ StringUtil.escapeStr(data.attachments[0].wbwjm) + '</td>';
					trHtml = trHtml + '<td class="text-left">'
							+ data.attachments[0].wjdxStr + ' </td>';
					trHtml = trHtml + '<td class="text-left" title="'+data.attachments[0].user.username + " "
					+ data.attachments[0].user.realname +'">'
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
 function add_tr(){
	 index+=1;
		 var str='<tr>'
		 +'<td class="text-center"><button class="line6" onclick="del_tr(this)"><i class="icon-minus color-blue cursor-pointer"></i></button></td>'
		 +'<td><input type="text" id="bjmc'+index+'" name="bjmc" class="col-lg-8 form-control text-left" style="width:77%" maxlength="100" />'
		 +'<input type="hidden" id="zjqdid" value=""/><input type="hidden" id="bjid" value=""/>'
		 +'<button type="button" '
		 +'class="btn btn-primary form-control"  style="width:22%" data-toggle="modal" onclick="openpart('+"'bjmc"+index+"'"+')">'
		 +'<i class="icon-search cursor-pointer"></i></button></td>'
		 +'<td><input type="text" class="form-control" maxlength="100" /></td>'
		 +'<td><input type="text" class="form-control" maxlength="9" /></td>'
		 +'</tr>'
		 $("#bjzd_list").append(str);
		 
	 }
	 function del_tr(obj){
		 $(obj).parent("td").parent("tr").remove();
	 }
	 function delfile(uuid) {
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
				$('#' + uuid).remove();
			}
		}
	 
	 function openpart(obj){
		 partModal.show({
			 	clearUser: true,
				selected : $(this).prev().prev().val(),
				callback : function(data) {
					$("#"+obj).val(formatUndefine(StringUtil.escapeStr(data.zwmc)+" "+StringUtil.escapeStr(data.ywmc)))
					$("#"+obj).next().val(formatUndefine(data.id));//装机清单id
					$("#"+obj).next().next().val(formatUndefine(data.bjid));	//部件id			
				},
				
			})
	 }
	 
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
	 
	 function save(){
		 
		 var param={};
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
		 if($("#fjzl_dw").val()==1){
			 param.fjzl_dw="KG";
		 }else{
			 param.fjzl_dw="LB"; 
		 }
		 param.zxzx_sz=$("#zxzx_sz").val();
		 if($("#zxzx_dw").val()==1){
			 param.zxzx_dw="M";
		 }else{
			 param.zxzx_dw="IN"; 
		 }
		 param.hxzx_sz=$("#hxzx_sz").val();
		 if($("#hxzx_dw").val()==1){
			 param.hxzx_dw="M";
		 }else{
			 param.hxzx_dw="IN"; 
		 }
		 param.bz=$("#bz").val();
		 param.zt=1;
		 param.dprtcode=$("#dprtcode").val();
		 param.zddwid=$("#zddwid").val();
		 param.zdrid=$("#zdrid").val();
		 param.planeLoadInfolist=info();
		//验证
		 var b=validation();
		 
		 if(b!=true&&flag!=true){
			 return false;
		 }
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
		 console.info(param.planeLoadInfolist);
		 if(b==true&&flag==true){
			 AjaxUtil.ajax({
					url : basePath + "/productionplan/planeload/add",
					type : "post",
					data : JSON.stringify(param),
					contentType : "application/json;charset=utf-8",
					dataType : "json",
					success : function(data) {
						finishWait();
						AlertUtil.showMessage('保存成功!','/productionplan/planeload/main?id=' + data+"&pageParam="+pageParam);
					}
				});
		 }
		   
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
	 
	 function back(){
		 window.location =basePath+"/productionplan/planeload/main?pageParam="+pageParam;
	 }