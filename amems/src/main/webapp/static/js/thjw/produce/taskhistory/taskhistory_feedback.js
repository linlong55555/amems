var taskhistoryFeedbackMain={
		id : 'taskhistoryFeedbackMain',
		AjaxGetDatasWithSearch:function(gdid, id){
			var _this = this;
			if(id != null && id != '' && typeof id != undefined){
				_this.id = id;
			}
			 AjaxUtil.ajax({
				 url:basePath+"/produce/taskhistory/selectGdBygdid",
				   type: "post",
				   dataType:"json",
				   data:{
					   'gdid':gdid
				   },
				   success:function(data){
			 			if(data.workOrder){
			 			_this.appendSpanHtml(data.workOrder);
			 		}else{
			 			$("#taskhistoryFeedbackMain", $("#"+_this.id)).find("span").html("");
			 			//显示附件
						var attachmentsObj = attachmentsUtil.getAttachmentsComponents(this_.id);
						attachmentsObj.show({
							djid:'',
							fileHead : false,
							attachHead:false,
							colOptionhead : false,
							fileType:"card"
						});
					}
			 	}
			 });
		},
		// 表单拼接
		appendSpanHtml:function(row){
			var this_ = this;
			var bs = row.paramsMap?row.paramsMap.sjWwbs:'2';
			$("#feedback_jhZxdw", $("#"+this_.id)).val("");
			if(bs == 1){
				$("#feedback_jhZxdw", $("#"+this_.id)).val("外委  "+(row.paramsMap.sjZxdw?row.paramsMap.sjZxdw:''));
			}else if(bs == 0){
				$("#feedback_jhZxdw", $("#"+this_.id)).val("内部  "+(row.paramsMap.sjZxdw?row.paramsMap.sjZxdw:''));
			}
			$("#feedback_sjJsrq", $("#"+this_.id)).val(row.sjJsrq?row.sjJsrq.substring(0,10):'');
			$("#feedback_sjGzz", $("#"+this_.id)).val(row.sjGzz?row.sjGzz:'');
			$("#feedback_sjJcz", $("#"+this_.id)).val(row.sjJcz?row.sjJcz:'');
			$("#feedback_sjGs", $("#"+this_.id)).val(row.sjGs?row.sjGs:'');
			$("#feedback_sjZd", $("#"+this_.id)).val(row.sjZd?row.sjZd:'');
			$("#feedback_gzxx", $("#"+this_.id)).val(row.gzxx?row.gzxx:'');
			$("#feedback_clcs", $("#"+this_.id)).val(row.clcs?row.clcs:'');
			
			//显示附件
			var attachmentsObj = attachmentsUtil.getAttachmentsComponents(this_.id);
			attachmentsObj.show({
				djid:row.id,
				fileHead : false,
				colOptionhead : false,
				attachHead:false,
				fileType:"card"
			});
			
		 }
}