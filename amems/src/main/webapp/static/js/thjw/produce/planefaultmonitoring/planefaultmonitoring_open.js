Planefaultmonitoring_Open_Modal = {
	id : "Planefaultmonitoring_Open_Modal",
	deleteUploadId : "",
	uploadObjSingle :[],
	uploadObjjl : {},
	/**
	 * 清空数据
	 */
	EmptiedData : function(){
		$("#djid").val();
		$("#"+this.id+"_fjzch").attr("disabled",false);//飞机注册号可编辑
		$("#"+this.id+" input:text").val(""); //清空文本框数据
		$("#"+this.id+" textarea").val("");   //清空多行文本数据
		$("#"+this.id+" #common_zjh_value").val("");  //清空ata章节号
		$("#"+this.id+" #common_zjh_display").val(""); //清空ata章节号
		$("#djid").val("");
		var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list');
		attachmentsObj.show({
			djid:null,
			fileHead : true,
			colOptionhead : true,
			fileType:"monitoring"
		});//初始化附件
	},
	/**
	 * 字段验证
	 */
	validation : function(){
		validatorForm = $('#monitoringForm').bootstrapValidator({
		       message: '数据不合法',
		       feedbackIcons: {
		          invalid:    'glyphicon glyphicon-remove',
		          validating: 'glyphicon glyphicon-refresh'
		       },
		       fields: {
		    	  fjzch: {
		            validators: {
		            	notEmpty: {message: '飞机注册号不能为空'}
		            }
			      },
			      common_zjh_display: {
			            validators: {
			            	notEmpty: {message: '章节号不能为空'}
			            }
				      }
		       }
		   });
	},
	//历史版本
	initWebuiPopover : function(){
		var this_ = this;
		WebuiPopoverUtil.initWebuiPopover('attachment-view2',"#"+this_.id,function(obj){
			return this_.getHistoryBbList(obj);
		});
	},
	//附件下载
	downfile : function(id){
		PreViewUtil.handle(id, PreViewUtil.Table.D_011);
	}
};

$(document).ready(function(){
});
