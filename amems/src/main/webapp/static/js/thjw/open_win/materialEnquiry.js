MaterialEnquiryModal = {
	alertId : 'MaterialEnquiryModal',
	enquiryListId : 'enquiryList',
	param: {
		mainid:null,
		enquiryType:null,
		materialSl : 1,
		callback:function(){}
	},
	data:[],
	show : function(param){
		$("#"+this.alertId).modal("show");
		if(param){
			$.extend(this.param, param);
		}
		if(this.param.mainid){
			this.initshoworhide();
			this.loadEnquiry(this.param.mainid);
		}
	},
	initshoworhide : function(){
		if(this.param.enquiryType == 1){
			$("#enquiryHead tr th:eq(2)", $("#"+this.alertId)).show();
			$("#enquiryHead tr th:eq(3)", $("#"+this.alertId)).hide();
			$("#enquiryHead tr th:eq(4)", $("#"+this.alertId)).hide();
			$("#enquiryHead tr th:eq(5)", $("#"+this.alertId)).hide();
		}else{
			$("#enquiryHead tr th:eq(2)", $("#"+this.alertId)).hide();
			$("#enquiryHead tr th:eq(3)", $("#"+this.alertId)).show();
			$("#enquiryHead tr th:eq(4)", $("#"+this.alertId)).show();
			$("#enquiryHead tr th:eq(5)", $("#"+this.alertId)).show();
		}
	},
	loadEnquiry: function(mainid){
		var this_ = this;
		AjaxUtil.ajax({
			url:basePath + "/aerialmaterial/enquiry/queryEnquiryListByMainId",
			type:"post",
			async: false,
			data:{
				mainid:mainid
			},
			dataType:"json",
			success:function(data){
				if(data.length >0){
					this_.appendContentHtml(data);
			    }
			}
		});
	},
	appendContentHtml: function(list){
		
		var this_ = this;
		var htmlContent = '';
		$.each(list,function(index,row){
			if (index % 2 == 0) {
				htmlContent += "<tr bgcolor=\"#f9f9f9\" >";
			} else {
				htmlContent += "<tr bgcolor=\"#fefefe\" >";
			}
			
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(index+1)+"</td>";  
			htmlContent += "<td title='"+row.gysmc+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.gysmc)+"</td>";  
			
			var zj = '';
			if(this_.param.enquiryType == 1){
				htmlContent += "<td class='text-right'>"+formatUndefine(row.bjClf).toFixed(2)+"</td>"; 
				if(formatUndefine(row.bjClf) != ''){
					zj = formatUndefine(row.bjClf) * this_.param.materialSl;
				}
			}else{
				htmlContent += "<td style='text-align:right;vertical-align:middle;'>"+formatUndefine(row.bjClf).toFixed(2)+"</td>";  
				htmlContent += "<td style='text-align:right;vertical-align:middle;'>"+formatUndefine(row.bjGsf).toFixed(2)+"</td>";  
				htmlContent += "<td style='text-align:right;vertical-align:middle;'>"+formatUndefine(row.bjQtf).toFixed(2)+"</td>";  
				zj = formatUndefine(row.bjClf) + formatUndefine(row.bjGsf) + formatUndefine(row.bjQtf);
			}
			if(zj == ''){
				zj = 0;
			}
			zj = zj.toFixed(2);
			htmlContent += "<td style='text-align:right;vertical-align:middle;'>"+zj+"</td>";
			htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+indexOfTime(row.yjdhrq)+"</td>";  
			htmlContent += "<td title='"+StringUtil.escapeStr(row.xjr?row.xjr.displayName:'')+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xjr?row.xjr.displayName:'')+"</td>";  
			htmlContent += "</tr>";  
		});
		$("#"+this_.enquiryListId, $("#"+this_.alertId)).empty();
		$("#"+this_.enquiryListId, $("#"+this_.alertId)).html(htmlContent);
	}
}