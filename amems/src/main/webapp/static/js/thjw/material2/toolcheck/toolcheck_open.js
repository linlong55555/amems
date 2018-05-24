toolcheck_open_modal = {
	id : "toolcheck_open_modal",
	deleteUploadId : "",
	uploadObjSingle :[],
	uploadObjjl : {},
	planeDatas:[],
	param: {
		parentWinId : '',
		selected:null,
		clearZjh: true,
		callback:function(){},
		dprtcode:userJgdm//默认登录人当前机构代码
	},
	/**
	 * 字段验证
	 */ 
	validation : function(){
		validatorForm = $('#toolcheckForm').bootstrapValidator({
		       message: '数据不合法',
		       feedbackIcons: {
		          invalid:    'glyphicon glyphicon-remove',
		          validating: 'glyphicon glyphicon-refresh'
		       },
		       fields: {
		    	   zbjxlh: {
			            validators: {
			            	notEmpty: {message: '编号不能为空'},
						    regexp: {
				                   regexp: /^[\x00-\xff]*$/,
				                   message: '编号不能输入中文!' 	
				            }
			            }
			      }, 
			      bjxlh: {
			    	  validators: {
			    		  notEmpty: {message: '编号不能为空'},
			    		  regexp: {
			    			  regexp: /^[\x00-\xff]*$/,
			    			  message: '编号不能输入中文!' 	
			    		  }
			    	  }
			      }, 
				  bldh: {
					  validators: {
						  regexp: {
		                        regexp: /^[\x00-\xff]*$/,
		                        message: '保留单编号不能输入中文!' 	
		                  }
					  }
				  }
		       }
		   });
	},
	/**
	 * 清空数据
	 */
	EmptiedData : function(){
		$("#toolcheck_open_modal_list").html("");
		$(".noteditable").attr("disabled",false);				  //标签不可编辑
		$(".required").show();   			    			      //隐藏必填标识*
		$(".colse").addClass("readonly-style");  				  //去掉文本框为白的的样式
		
		$(".bldh").attr("disabled",false);						  //保留单号不可编辑
		
		$("#id").val(""); 									  	  //清空id
		$("#"+this.id+" input:text").val(""); 					  //清空所有文本框数据
		$("#"+this.id+" textarea").val("");	  					  //清空所有多行文本框数据
		$("[name='jyZqdw']").val("11"); 								  //清空id
		$("#bs").val("2"); 
		$("#toolcheck_open_modal_data_list").show();
//		this.getAttachments(null,true,true);	 				  //初始化附件
		
	},
	//初始化附件
	getAttachments: function(id,fileHead,colOptionhead){
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		attachmentsObj.show({
			djid:id,
			fileHead : fileHead,
			colOptionhead : colOptionhead,
			fileType:"reservation"
		});//显示附件
	},
	initDic : function(){
		$("#jlfsSelect").empty();
		DicAndEnumUtil.registerDic('80','jlfsSelect',this.param.dprtcode);
	},
	addRow : function(){
		var this_ = this;
		var jlfsOption = $("#jlfsSelect").html();
		var jldj = $("#jldj").val();
		var rowid = Math.uuid().toLowerCase();
		var html = "";
		html += "<div name='zbjs' class='input-group-border padding-left-0' style='margin-top:15px;border:1px dashed #d5d5d5;'>";
		html += '<input type="hidden" value="0" name="bjbs"/>';
		html += '<input type="hidden"  name="zid" value="'+rowid+'"/>';
		html += "<div style='width:80px;background:white;height:20px;margin-top:-20px;margin-left:20px;text-align:center'>";
		html += "<button class='line6 line6-btn' onclick='toolcheck_open_modal.removeRow(this)'  type='button'><i class='icon-minus cursor-pointer color-blue cursor-pointer'></i></button>";
		html += "</div>";
		
		html += "<div class='col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-top-8 padding-left-0 padding-right-0 form-group'>";
		html += "<label class='col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0'>";
		html += "<div class='font-size-12 margin-topnew-2'><label class='required' style='color: red'>*</label>编号</div>";
		html += '<div class="font-size-9 ">No.</div></label>';
		html += '<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">';
		html += '<input type="text"  name="bjxlh" class="form-control"  maxlength="50"  />';
		html += '</div></div>';
		
		html += '<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-top-8 padding-left-0 padding-right-0 form-group">';
		html += '<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">';
		html += '<div class="font-size-12 margin-topnew-2">中文名称</div>';
		html += '<div class="font-size-9 ">Chinese name</div></label>';
		html += '<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">';
		html += '<input type="text"   name="zwms"  class="form-control"  />';
		html += '</div></div>';
		
		html += '<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-top-8 padding-left-0 padding-right-0 form-group">';
		html += '<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">';
		html += '<div class="font-size-12 margin-topnew-2">英文名称</div>';
		html += '<div class="font-size-9 ">English name</div></label>';
		html += '<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">';
		html += '<input type="text"   name="ywms" class="form-control"  />';
		html += '</div></div>';
		
		html += '<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-top-8 padding-left-0 padding-right-0 form-group">';
		html += '<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">';
		html += '<div class="font-size-12 margin-topnew-2">型号</div>';
		html += '<div class="font-size-9 ">Model</div></label>';
		html += '<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">';
		html += '<input type="text"   name="xh" class="form-control"  />';
		html += '</div></div>';
		
		html += '<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-top-0 padding-left-0 padding-right-0 form-group">';
		html += '<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">';
		html += '<div class="font-size-12 margin-topnew-2">规格</div>';
		html += '<div class="font-size-9 ">Specifications</div></label>';
		html += '<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">';
		html += '<input type="text"   name="gg" class="form-control"  />';
		html += '</div></div>';
		
		html += '<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">';
		html += '<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">';
		html += '<div class="font-size-12 margin-topnew-2"><label class="required" style="color: red">*</label>周期</div>';
		html += '<div class="font-size-9 ">Cycle</div></label>';
		html += '<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 padding-right-0">';
		html += '<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-0 padding-right-0">';
		html += '<input type="text" class="form-control" name="jyZq" onkeyup="clearNoNum(this)" maxlength="3"  /></div>';
		html += '<div class="col-lg-3 col-sm-3 col-xs-3 padding-left-2 padding-right-0">';
		html += '<select  name="jyZqdw" class="form-control" onchange="toolcheck_open_modal.changeNextDate(this)">';
		html += '<option value="11" >天</option>';
		html += '<option value="12" >月</option></select></div>';
		html += '</div></div>';
		
		html += '<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-top-0 padding-left-0 padding-right-0 form-group">';
		html += '<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">';
		html += '<div class="font-size-12 margin-topnew-2"><label class="required" style="color: red">*</label>校验日期</div>';
		html += '<div class="font-size-9 ">Date</div></label>';
		html += '<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">';
		html += '<input type="text"    name="jyScrq" class="form-control date-picker"  onchange="toolcheck_open_modal.changeNextDate(this)"/>';
		html += '</div></div>';
		
		html += '<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-top-0 padding-left-0 padding-right-0 form-group">';
		html += '<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">';
		html += '<div class="font-size-12 margin-topnew-2"><label class="required" style="color: red">*</label>下次校验日期</div>';
		html += '<div class="font-size-9 ">Date</div></label>';
		html += '<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">';
		html += '<input type="text"    name="jyXcrq" class="form-control date-picker"  />';
		html += '</div></div>';
		
		html += '<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-top-0 padding-left-0 padding-right-0 form-group">';
		html += '<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">';
		html += '<div class="font-size-12 margin-topnew-2">计量方式</div>';
		html += '<div class="font-size-9 ">Cycle</div></label>';
		html += '<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">';
		html += '<select class="form-control noteditable"  name="jlfs"  >'+jlfsOption+'</select>';
		html += '</div></div>';
		
		html += '<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-top-0 padding-left-0 padding-right-0 form-group">';
		html += '<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">';
		html += '<div class="font-size-12 margin-topnew-2">计量等级</div>';
		html += '<div class="font-size-9 ">Grade</div></label>';
		html += '<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 ">';
		html += '<input type="text"   name="jldj"  class="form-control" value="'+jldj+'" />';
		html += '</div></div>';
		
		html += '<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">';
		html += '<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">';
		html += '<div class="font-size-12 margin-topnew-2">备注</div>';
		html += '<div class="font-size-9 ">Remark</div></span>';
		html += '<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">';
		html += '<textarea style="height: 75px;" class="form-control"  name="bz" maxlength="300"></textarea>';
		html += '</div></div>';
		
		html += "<div class='clearfix'></div></div>";
		
		$("#"+this_.id+"_list").append(html);
		
		$('.date-picker').datepicker({
			format:'yyyy-mm-dd',
			autoclose : true
		}).next().on("click", function() {
			$(this).prev().focus();
		});
		
		
	},
	//改变周期或最新检验日期时加载下次校验日期
	changeNextDate : function(obj){
		
		$("#toolcheck_open_modal_list>div[name='zbjs']").each(function(){
			var index=$(this).index(); //当前行

			var jyZq =$("input[name='jyZq']").eq(index).val();
			var jyZqdw =$("select[name='jyZqdw']").eq(index).val();
			var jyScrq =$("input[name='jyScrq']").eq(index).val();
			var jyXcrq =$("input[name='jyXcrq']").eq(index);
			if(jyZqdw == 11){
				jyXcrq.val(TimeUtil.dateOperator(jyScrq,jyZq));
			}else{
				jyXcrq.val(TimeUtil.dateOperator4Month(jyScrq,jyZq));
			}
		});
	
		$("input[name='jyXcrq']").datepicker("update");
		$("input[name='jyXcrq']").datepicker("update");
	},
	/**
	 * 删除一行
	 */
	removeRow : function(obj){
		$(obj).parent().parent().remove();
	},
	/**
	 * 删除一行
	 */
	remove : function(obj){
		$("#bs").val("1"); 
		$("#toolcheck_open_modal_data_list").hide();
		
	},
	filses : function(rid){
		$("#FilesDownModal_"+rid).modal("show");
	},
	setData : function(){
		$("#FilesDownModal").modal("hide");
	},
	filseDown : function(){
		$("#FilesDownModal").modal("show");
	},
	setData : function(){
		$("#FilesDownModal").modal("hide");
	},
	//初始化附件
	getAttachments: function(id,fileHead,colOptionhead){
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
		attachmentsObj.show({
			djid:id,
			fileHead : fileHead,
			colOptionhead : colOptionhead,
			fileType:"reservation"
		});//显示附件
	},
	//选择工具设备
	selecttoolequipment : function(){
		var bjid = $.trim($("#bjid").val());
		var dprtcode = $("#dprtcode").val();
		selecttoolequipmentModal.show({
			selected:bjid,//原值，在弹窗中默认勾选
			dprtcode:dprtcode,
			parentWinId : "toolcheck_open_modal",
			callback: function(data){//回调函数
				if(data != null){
					$("#bjid").val(formatUndefine(data.sn));
					$("#bjxlh").val(formatUndefine(data.sn));
					$("#bjh").val(formatUndefine(data.bjh));
					$("#bjmc").val(formatUndefine(data.ywms)+" "+formatUndefine(data.zwms));
					
					if(formatUndefine(data.ckh) != ''){
						$("#wz").val(formatUndefine(data.ckh)+" "+formatUndefine(data.ckmc)+" "+formatUndefine(data.kwh));
					}else{
						$("#wz").val("不在库");
					}
					
					
					$("#rksj").val(formatUndefine(data.rksj).substring(0,10));
					$("#pch").val(formatUndefine(data.pch));
					if(StringUtil.escapeStr(data.kcsl) != ''){
						$("#kcsl").val(StringUtil.escapeStr(data.kcsl)+" "+StringUtil.escapeStr(data.jldw));
					}else{
						
					}
					
					$("#zbjxlh").val(formatUndefine(data.sn));
					$("#zzwms").val(formatUndefine(data.zwms));
					$("#zywms").val(formatUndefine(data.ywms));
					$("#zxh").val(formatUndefine(data.xh));
					$("#zgg").val(formatUndefine(data.gg));
				}
				
				$('#toolcheckForm').data('bootstrapValidator')  
	 		      .updateStatus("zbjxlh", 'NOT_VALIDATED',null)  
	 		      .validateField("zbjxlh");
				
			}
		});
	},
	changeWjbh : function(obj){
		this.onkeyup4Code(obj);
	},
	onkeyup4Code : function(obj){
		obj.value = obj.value.replaceAll("−","-");
		var reg = new RegExp("^[a-zA-Z 0-9-_\x21-\x7e]{1,50}$");
		
	     obj.value = validate(obj.value);
	     function validate(_value){
	    	 if(_value.length == 0){
	    		 return "";
	    	 }
	    	 if(!reg.test(_value)){
	    		return validate(_value.substr(0, _value.length-1));
	    	 }
	    	 return _value;
	    }
	},
	//改变周期或最新检验日期时加载下次校验日期
	loadNextDate : function(){
			var jyZq = $("#zjyZq").val();
			var jyScrq = $("#zjyScrq").val();
			var zzjyZqdw = $("#zjyZqdw").val();
			if(zzjyZqdw == 11){
				$("#zjyXcrq").val(TimeUtil.dateOperator(jyScrq,jyZq));
			}else{
				$("#zjyXcrq").val(TimeUtil.dateOperator4Month(jyScrq,jyZq));
			}
			$("#zjyScrq").datepicker("update");
			$("#zjyXcrq").datepicker("update");
	}
};
$('.date-picker').datepicker({
	format:'yyyy-mm-dd',
	autoclose : true
}).next().on("click", function() {
	$(this).prev().focus();
});
$('#sqrq').datepicker({
	 autoclose: true,
	 clearBtn:true
}).on('hide', function(e) {
	  $('#toolcheckForm').data('bootstrapValidator')  
.updateStatus('sqrq', 'NOT_VALIDATED',null)  
.validateField('sqrq');  
});

//只能输入数字
function clearNoNum(obj){
	//先把非数字的都替换掉，除了数字
    obj.value = obj.value.replace(/[^\d]/g,"");
    if(obj.value.length >= 1 && obj.value.substring(0,1) == 0){
 		obj.value = 1;
 	 }
    toolcheck_open_modal.loadNextDate();
    toolcheck_open_modal.changeNextDate();
}
