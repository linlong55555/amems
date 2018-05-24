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
		
		$("body .form-control").attr("disabled","disabled");
		
	 });
	

 
 	 
	 
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
					 +'<td><input type="text" id="bjmc'+num+'" name="bjmc" class="col-lg-8 form-control text-left" style="width:99%" value="'+row.bjmc+'" maxlength="100" />'
					 +'<input type="hidden" id="zjqdid'+num+'" value="'+row.zjqdid+'" /><input type="hidden" id="bjid'+num+'" value="'+row.bjid+'" /></td>'
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
			   if(data.rows.length>0){
				   appendSelecedScwj(data.rows);
			   }else{
				   $("#fj").hide();
			   }
	   }
	 });
		
		
	}
	//拼接上传附件
	function appendSelecedScwj(list){
		//alert(JSON.stringify(list));
		var htmlContent="";
		 $.each(list,function(index,row){ 
			 htmlContent = htmlContent+'<tr style="cursor: pointer" bgcolor="#f9f9f9" id=\''+row.id+'\'>';
			 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.wbwjm))+"'><a href=\"javascript:downloadfile('"+row.id+"')\">"+StringUtil.escapeStr(formatUndefine(row.wbwjm))+"</a></td>";
			 htmlContent = htmlContent+"<td class='text-left'>"+StringUtil.escapeStr(formatUndefine(row.wjdxStr))+'</td>';
			 htmlContent = htmlContent+"<td class='text-left' title='"+StringUtil.escapeStr(formatUndefine(row.czr_user.displayName))+"'>"+StringUtil.escapeStr(formatUndefine(row.czr_user.displayName))+'</td>';
			 htmlContent = htmlContent+"<td class='text-center'>"+formatUndefine(row.czsj)+'</td>';
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

	
	function back(){
		 window.location =basePath+"/productionplan/planeload/main?id="+id+'&pageParam=' + pageParam;
	}