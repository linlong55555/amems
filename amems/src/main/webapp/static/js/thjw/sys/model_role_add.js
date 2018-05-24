$(function() {
	//菜单
	Navigation(menuCode,"机型角色新增","Model Add");
	
	validatorForm =  $('#form').bootstrapValidator({
        message: '数据不合法',
        feedbackIcons: {
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	roleCode: {
                validators: {
                	notEmpty: {
                        message: '机型角色代码不能为空'
                    },
                    regexp: {
                        regexp: /^[^\u4e00-\u9fa5]{0,}$/,
                        message: '不包含中文'
                    },
                    stringLength: {
                        max: 30,
                        message: '长度最多不超过30个字符'
                    }
                }
            },
                       
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
	 
	//加载数据
	 appendContentHtml();
});

	//返回
	function go(){
		 window.location = basePath+"/sys/role/main?t=" + new Date().getTime()+"#ModelRoleList";
	}

	function save() {
		$('#form').data('bootstrapValidator').validate();
		if (!$('#form').data('bootstrapValidator').isValid()) {
			return false;
		}
		
		startWait();//开始Loading
		//验证机型Code是否重复
		if(checkUpdMt1($('#roleCode').val(),$('#dprtcode').val())){
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
				'roleCode' : $('#roleCode').val(),// 机型角色代码
				'roleName' : $('#roleName').val(),// 机型角色名称
				'roleRemark' : $('#roleRemark').val(),// 机型角色备注
				'dprtId' : $('#dprtcode').val(),// 机型角色机构代码
			};
		
			fixedCheckItem.modelToRoleList = workContentParam;
		
			// 提交数据
			AjaxUtil.ajax({
				url:basePath+"/sys/role/addmodelRole",
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
						$("#alertModalBody").html("添加成功!");
					} else {
						finishWait();//结束Loading
						AlertUtil.showErrorMessage(data.text);
					}
				}
				
			});
		}
	}

	//检查机型角色code是否重复
	function checkUpdMt1(roleCode,dprtcode){
		var flag = false;
		AjaxUtil.ajax({
			url:basePath+"/sys/role/ModelcheckUpdMt",
			type:"post",
			async: false,
			data:{
				'modelRoleCode' : roleCode,
				'dprtId' : dprtcode
			},
			dataType:"json",
			success:function(data){
				
				if (data.state == "success") {
					flag = true;
				} else {
					finishWait();//结束Loading
					AlertUtil.showErrorMessage(data.message);
				}
			}
		});
		return flag;
	}

	function appendContentHtml(){
		AjaxUtil.ajax({
			url:basePath+"/sys/role/init/modelrole/add",
			type:"post",
			dataType : 'json',
			success:function(data){
				if(data && data.planeModelList && data.planeModelList.length > 0){
					var htmlContent = '';
					$.each(data.planeModelList,	function(index, row){
					   htmlContent += "<tr >";
					   htmlContent += "<td class='text-left'><input  name='DPRTCODE'  type='hidden' value='"+formatUndefine(row.dprtcode)+"' />"+StringUtil.escapeStr(row.dprtname)+"</td>"; 
					   
					   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.fjjx)+"'> <label style=' font-weight:normal'><input name='fjjx' onclick='onclick1(this,\""+index+"\")'  type='checkbox' value='"+StringUtil.escapeStr(row.fjjx)+"'/>"+StringUtil.escapeStr(StringUtil.subString(row.fjjx,16))+"</lable></td>";  
					   var htmlContent1 = '';
					   $.each(row.planeDataList,function(index1,row1){
						   htmlContent1+="  <label style=' font-weight:normal' title='"+StringUtil.escapeStr(row1.fjzch)+"'><input  name='checkox"+index+"' type='checkbox' onclick='onclick2(\""+StringUtil.escapeStr(row.fjjx)+"\",\""+index+"\")' value='"+StringUtil.escapeStr(row1.fjzch)+"'/>"+StringUtil.escapeStr(row1.fjzch)+"</lable>  &nbsp;&nbsp;";
					   });
					   htmlContent += "<td class='text-left' name='kk'>"+htmlContent1+"</td>";  
					   htmlContent += "</tr>";  
				   });
				   $("#list").empty();
				   $("#list").html(htmlContent);
				   TableUtil.addTitle("#list tr td"); //加载td title
				}
				new Sticky({tableId:'modelid'}); //初始化表头浮动
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
