/**
 * 流程信息
 */
introduce_process_info_class = {
	id:'introduce_process_info_class',
	param: {
		status:1,			 //1保存、2已评估、3已审核、4已批准、5审核驳回、6审批驳回、
		prepared:null,		 //编写人
		preparedDate:null,	 //编写日期
		checkedOpinion:null, //审核意见
		checkedby:null,	     //审核人
		checkedDate:null,	 //审核日期
		checkedResult:null,  //审核结论
		approvedOpinion:null,//批准意见
		approvedBy:null,	 //批准人
		approvedDate:null,   //批准日期
		approvedResult:null //批准结论
	},
	loaded: false,//是否已加载
	
	data:[],
	show: function(param){
		$("#prepared").html('');
		$("#preparedDate").html('');
		$("#"+this.id+"_shenhe").val('');
		$("#checkedby").html('');
		$("#checkedDate").html('');
		$("#checkedResult").html('');
		$("#"+this.id+"_shenpi").val('');
		$("#approvedBy").html('');
		$("#approvedDate").html('');
		$("#approvedResult").html('');
		if(param){
			$.extend(this.param, param);
		}
		var status=param.status;
		
		if(status==1||status==-1){
			//编写
			//隐藏流程信息
			$("#"+this.id+"_lcxx").hide();
		}else if(status==2){
			//审核
			//隐藏批准
			$("#"+this.id+"_lcxx").show();
			
			$("#"+this.id+"_shenhed").show();
			
			$("#"+this.id+"_bianxied").show();
			if(param.checkedResult != null && param.checkedResult != ""){
				$("#"+this.id+"_shenhed_xx").show();
			}else{
				$("#"+this.id+"_shenhed_xx").hide();
			}
			if(param.approvedResult != null &&param.approvedResult != ""){
				$("#"+this.id+"_shenpid").show();
				$("#"+this.id+"_shenpi").val(param.approvedOpinion);
				$("#approvedBy").html(param.approvedBy);
				$("#approvedDate").html(param.approvedDate);
				$("#approvedResult").html(DicAndEnumUtil.getEnumName('conclusionEnum',param.approvedResult));
			}else{
				$("#"+this.id+"_shenpid").hide();
			}
			
			//只有审核可以编辑
			$("#"+this.id+"_shenhe").attr("disabled",false);
			$("#"+this.id+"_shenpi").attr("disabled",true);
			
			//编写信息
			$("#prepared").html(param.prepared);
			$("#preparedDate").html(param.preparedDate);
			
			//审核信息
			$("#"+this.id+"_shenhe").val(param.checkedOpinion);
			$("#checkedby").html(param.checkedby);
			$("#checkedDate").html(param.checkedDate);
			$("#checkedResult").html(DicAndEnumUtil.getEnumName('conclusionEnum',param.checkedResult));
		}else if(status==3){
			//审批
			$("#"+this.id+"_lcxx").show();
			//隐藏批准
			$("#"+this.id+"_shenpid").show();
			if(param.approvedResult != null &&param.approvedResult != ""){
				$("#"+this.id+"_shenpid_xx").show();
			}else{
				$("#"+this.id+"_shenpid_xx").hide();
			}
			$("#"+this.id+"_shenhed").show();
			$("#"+this.id+"_shenhed_xx").show();
			$("#"+this.id+"_bianxied").show();
			//只有批准可以编辑
			$("#"+this.id+"_shenpi").attr("disabled",false);
			$("#"+this.id+"_shenhe").attr("disabled",true);
			
			//编写信息
			$("#prepared").html(param.prepared); 
			$("#preparedDate").html(param.preparedDate);
			
			//审核信息
			$("#"+this.id+"_shenhe").val(param.checkedOpinion);
			$("#checkedby").html(param.checkedby);
			$("#checkedDate").html(param.checkedDate);
			$("#checkedResult").html(DicAndEnumUtil.getEnumName('conclusionEnum',param.checkedResult));
			
			//批准信息
			$("#"+this.id+"_shenpi").val(param.approvedOpinion);
			$("#approvedBy").html(param.approvedBy);
			$("#approvedDate").html(param.approvedDate);
			$("#approvedResult").html(DicAndEnumUtil.getEnumName('conclusionEnum',param.approvedResult));
		}else if(status==4){
			//审批
			$("#"+this.id+"_lcxx").show();
			//隐藏批准
			$("#"+this.id+"_shenpid").show();
			$("#"+this.id+"_shenhed").show();
			$("#"+this.id+"_bianxied").show();
			//只有批准可以编辑
			$("#"+this.id+"_shenpi").attr("disabled",true);
			$("#"+this.id+"_shenhe").attr("disabled",true);
			
			//编写信息
			$("#prepared").html(param.prepared);
			$("#preparedDate").html(param.preparedDate);
			
			//审核信息
			$("#"+this.id+"_shenhe").val(param.checkedOpinion);
			$("#checkedby").html(param.checkedby);
			$("#checkedDate").html(param.checkedDate);
			$("#checkedResult").html(DicAndEnumUtil.getEnumName('conclusionEnum',param.checkedResult));
			//批准信息
			$("#"+this.id+"_shenpi").val(param.approvedOpinion);
			$("#approvedBy").html(param.approvedBy);
			$("#approvedDate").html(param.approvedDate);
			$("#approvedResult").html(DicAndEnumUtil.getEnumName('conclusionEnum',param.approvedResult));
		}else if(status==5||status==6){//审核驳回。批准驳回
			$("#"+this.id+"_lcxx").show();
			//隐藏批准
			$("#"+this.id+"_shenpid").show();
			$("#"+this.id+"_shenhed").show();
			$("#"+this.id+"_shenpid_xx").show();
			$("#"+this.id+"_shenhed_xx").show();
			$("#"+this.id+"_bianxied").show();
			//只有批准可以编辑
			$("#"+this.id+"_shenpi").attr("disabled",true);
			$("#"+this.id+"_shenhe").attr("disabled",true);
			
			$("#prepared").html(param.prepared);
			$("#preparedDate").html(param.preparedDate);
			$("#"+this.id+"_shenhe").val(param.checkedOpinion);
			$("#checkedby").html(param.checkedby);
			$("#checkedDate").html(param.checkedDate);
			$("#checkedResult").html(DicAndEnumUtil.getEnumName('conclusionEnum',param.checkedResult));
			$("#"+this.id+"_shenpi").val(param.approvedOpinion);
			$("#approvedBy").html(param.approvedBy);
			$("#approvedDate").html(param.approvedDate);
			$("#approvedResult").html(DicAndEnumUtil.getEnumName('conclusionEnum',param.approvedResult));
		}
	},
	/**
	 * 获取流程信息的值
	 */
	getData:function(){
		var data={};
		data.shenhe=$("#"+this.id+"_shenhe").val(); //审核意见
		data.shenpi=$("#"+this.id+"_shenpi").val(); //批准意见
		return data;
	}
};