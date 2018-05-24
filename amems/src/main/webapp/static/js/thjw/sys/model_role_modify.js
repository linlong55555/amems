$(function() {
	Navigation(menuCode,"机型角色修改","Model Update");
	validatorForm =  $('#form').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	roleName: {
	                validators: {
	                    notEmpty: {
	                        message: '机型角色名称不为空'
	                    },
	                    stringLength: {
	                        max: 30,
	                        message: '长度最多不超过30个字符'
	                    }
	                }
	            },
	            remark: {
	                validators: {
	                  stringLength: {
                        max: 65,
                        message: '长度最多不超过65个字符'
                      }
	                }
	            }
	        }
	    });
	 appendContentHtml();
});

	//返回
	function go(){
		 window.location = basePath+"/sys/role/main?t=" + new Date().getTime()+"#ModelRoleList";
	}
	
	function modify(){
		$('#form').data('bootstrapValidator').validate();
		if(!$('#form').data('bootstrapValidator').isValid()){
			return false;
	    }
		startWait();//开始Loading
		var workContentParam = [];
		$("#list").find("tr").each(function(){
			
			var index=$(this).index(); //当前行
			var DPRTCODE =$("input[name='DPRTCODE']").eq(index).val(); //当前行,飞机机型
		
			if($("input[name='fjjx']").eq(index).attr("checked")){
				var fjjx =$("input[name='fjjx']").eq(index).val(); //当前行,飞机机型
				var notCheckLength = $("input[name='checkox"+index+"']:not(:checked)").length;
				var checked = $("input[name='checkox"+index+"']:checked");
				var checkedLength = checked.length;
				if(notCheckLength == 0 || checkedLength == 0){
					var infos ={};
					infos.fjjx  = fjjx;
	                infos.dprtcode  = DPRTCODE;
	               	workContentParam.push(infos);
				}else{
					checked.each(function(){
						var infos ={};
	                    infos.fjjx  = fjjx;
	                    infos.dprtcode  = DPRTCODE;
	                    infos.fjzch  = $(this).val();
	                    workContentParam.push(infos);
		            });
				}
			}
	});
	var fixedCheckItem = {
		'id' : $('#id').val(),// 机型角色id
		'roleName' : $('#roleName').val(),// 机型角色名称
		'roleRemark' : $('#roleRemark').val()// 机型角色备注
	};
	fixedCheckItem.modelToRoleList = workContentParam;
		// 提交数据
		AjaxUtil.ajax({
			url:basePath+"/sys/role/modifymodelRole",
			contentType:"application/json;charset=utf-8",
			type:"post",
			data:JSON.stringify(fixedCheckItem),
			dataType:"json",
			success:function(data){
				
				if (data.state == "Success") {
					finishWait();//结束Loading
					$("#alertModal").modal("show");
					$('#alertModal').on('hidden.bs.modal', function (e) {
						window.location = basePath+"/sys/role/main?t=" + new Date().getTime()+"#ModelRoleList";
					});
					$("#alertModalBody").html("修改成功!");
				} else {
					finishWait();//结束Loading
					AlertUtil.showErrorMessage(data.text);
				}
			}
		});
	}

	function appendContentHtml(){
		AjaxUtil.ajax({
			url:basePath+"/sys/role/init/modelrole/modify",
			type:"post",
			dataType : 'json',
			data:{roleId: $('#id').val()},
			success:function(data){
				if(data && data.planeModelList && data.planeModelList.length > 0){
					var htmlContent = '';
					$.each(data.planeModelList,	function(index, row){
						var jxChecked = false;
						var jxAllChecked = false;
						if(data.rolePlaneModelList && data.rolePlaneModelList.length > 0){
							$.each(data.rolePlaneModelList, function(index1, row1){
								if(row1.dprtcode == row.dprtcode && StringUtil.escapeStr(row1.fjjx) == StringUtil.escapeStr(row.fjjx) ){
									jxChecked = true;
									if(row1.lx === 1){
										jxAllChecked = true;
									}
									return false;
								}
							});
						}
					   htmlContent += "<tr >";
					   htmlContent += "<td class='text-left'><input  name='DPRTCODE'  type='hidden' value='"+formatUndefine(row.dprtcode)+"'/>"+formatUndefine(row.dprtname)+"</td>";  
					   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.fjjx)+"'> <label style=' font-weight:normal'><input "+(jxChecked?"checked":"")+" name='fjjx' onclick='onclick1(this,\""+index+"\")'  type='checkbox' value='"+StringUtil.escapeStr(row.fjjx)+"'/>"+StringUtil.escapeStr(StringUtil.subString(row.fjjx,16))+"</lable></td>";  
					   var htmlContent1 = '';
					   $.each(row.planeDataList,function(index1,row1){
						   if(jxAllChecked){
							   htmlContent1+="  <label style=' font-weight:normal' title='"+StringUtil.escapeStr(row1.fjzch)+"'><input  name='checkox"+index+"' checked type='checkbox' onclick='onclick2(\""+StringUtil.escapeStr(row.fjjx)+"\",\""+index+"\")' value='"+StringUtil.escapeStr(row1.fjzch)+"'/>"+StringUtil.escapeStr(row1.fjzch)+"</lable>  &nbsp;&nbsp;";
						   }else{
							   var fjzchChecked = false;
							   if(data.rolePlaneModelList && data.rolePlaneModelList.length > 0){
									$.each(data.rolePlaneModelList,function(index2, row2){
										if(row2.dprtcode == row.dprtcode && StringUtil.escapeStr(row2.fjjx) == row.fjjx && StringUtil.escapeStr(row2.fjzch) == StringUtil.escapeStr(row1.fjzch)){
											fjzchChecked = true;
											return false;
										}
									});
								}
							   
							   htmlContent1+="  <label style=' font-weight:normal' title='"+StringUtil.escapeStr(row1.fjzch)+"'><input  name='checkox"+index+"' "+(fjzchChecked?"checked":"")+" type='checkbox' onclick='onclick2(\""+StringUtil.escapeStr(row.fjjx)+"\",\""+index+"\")' value='"+StringUtil.escapeStr(row1.fjzch)+"'/>"+StringUtil.escapeStr(row1.fjzch)+"</lable>  &nbsp;&nbsp;";
						   }
					   });
					   htmlContent += "<td class='text-left'>"+htmlContent1+"</td>";  
					   htmlContent += "</tr>";  
				   });
				   $("#list").empty();
				   $("#list").html(htmlContent);
				   TableUtil.addTitle("#list tr td"); //加载td title
				}
				refreshPermission();
			}
		});
	}

	function onclick1(obj,index){
		 if($(obj).is(':checked')) {
			 $("[name ='checkox"+index+"']:checkbox").attr("checked", true);
		}else{
			$("[name ='checkox"+index+"']:checkbox").attr("checked", false);
		}
	}

	function onclick2(FJJX,index){
		var chks=$("[name ='checkox"+index+"']:checked");
		var len = chks.length;
		if(len==0){
			$("[name ='fjjx']:checkbox:eq("+index+")").attr("checked", false);
		}
		
		if($("[name ='checkox"+index+"").is(':checked')) {
			$("[name ='fjjx']:checkbox:eq("+index+")").attr("checked", true);
		}
	}
