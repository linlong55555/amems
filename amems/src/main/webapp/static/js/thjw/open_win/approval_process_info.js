/**
 * 流程信息
 */
approval_process_info = {
	id:'introduce_process_info_class',
	param: {
		status:1,			 //1保存、2已评估、3已审核、4已批准、5审核驳回、6审批驳回、
		prepared:null,		 //编写人
		preparedDate:null,	 //编写日期
		approvedOpinion:null,//批准意见
		approvedBy:null,	 //批准人
		approvedDate:null,   //批准日期
	},
	loaded: false,//是否已加载
	data:[],
	show: function(param){
		$("#prepared").html('');
		$("#preparedDate").html('');
		$("#"+this.id+"_shenpi").val('');
		$("#approvedBy").html('');
		$("#approvedDate").val('');
		if(param){
			$.extend(this.param, param);
		}
		var status=param.status;
		
		if(status==1){
			//编写
			//隐藏流程信息
			$("#"+this.id+"_lcxx").hide();
		}else if(status==2){
			
			//审批
			$("#"+this.id+"_lcxx").show();
			//隐藏批准
			$("#"+this.id+"_shenpid").show();
			$("#"+this.id+"_bianxied").show();
			//只有批准可以编辑
			$("#"+this.id+"_shenpi").attr("disabled",false);
			$("#approvedDate").attr("disabled",false);
			//编写信息
			$("#prepared").html(param.prepared); 
			$("#preparedDate").html(param.preparedDate);
			
			//批准信息
			$("#"+this.id+"_shenpi").val(param.approvedOpinion);
			$("#approvedBy").html(param.approvedBy);
			if(param.approvedDate==null||param.approvedDate==""){
				TimeUtil.getCurrentDate("#approvedDate");                          //取得当前时间
			}else{
				$("#approvedDate").val(param.approvedDate);
			}
		}else if(status==4){
			//已审批
			$("#"+this.id+"_lcxx").show();
			//隐藏批准
			$("#"+this.id+"_shenpid").show();
			$("#"+this.id+"_bianxied").show();
			//只有批准可以编辑
			$("#"+this.id+"_shenpi").attr("disabled",true);
			$("#approvedDate").attr("disabled",true);
			
			//编写信息
			$("#prepared").html(param.prepared);
			$("#preparedDate").html(param.preparedDate);
			
			//批准信息
			$("#"+this.id+"_shenpi").val(param.approvedOpinion);
			$("#approvedBy").html(param.approvedBy);
			$("#approvedDate").val(param.approvedDate);
		}else if(status==5||status==6){//审核驳回。批准驳回
			$("#"+this.id+"_lcxx").show();
			//隐藏批准
			$("#"+this.id+"_shenpid").show();
			$("#"+this.id+"_shenpid_xx").show();
			$("#"+this.id+"_bianxied").show();
			//只有批准可以编辑
			$("#"+this.id+"_shenpi").attr("disabled",true);
			$("#approvedDate").attr("disabled",true);
			
			$("#prepared").html(param.prepared);
			$("#preparedDate").html(param.preparedDate);
			$("#"+this.id+"_shenpi").val(param.approvedOpinion);
			$("#approvedBy").html(param.approvedBy);
			$("#approvedDate").val(param.approvedDate);
		}
	},
	/**
	 * 获取流程信息的值
	 */
	getData:function(){
		var data={};
		data.shenpi=$("#"+this.id+"_shenpi").val(); //批准意见
		data.approvedDate=$("#approvedDate").val(); //批准日期
		return data;
	}
};