
	var measures_record = {
			
		// 页面初始化
		init : function(){			
			var this_ = this;
			// 加载数据
			this_.loadData();			
		},
		
		param : {
			id : null,
			dprtcode:userJgdm,
			modal:null,
			callback:function(){}//回调函数
		},
		
		// 显示弹窗
		show : function(param, isReload){
			if(param){
				$.extend(this.param, param);
			}
			this.init();
		},		
		
		// 加载数据
		loadData : function(){
			var id = this.param.id;
			var dprtcode = this.param.dprtcode;
			var data ={};
			data.mainid = id;
			data.dprtcode = dprtcode;
			var this_ = this;
			AjaxUtil.ajax({
				url : basePath + "/quality/processrecord/list",
				type : "post",
				data : JSON.stringify(data),
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				async : false,
				success : function(data) {
					if(data.length>0){
						this_.appendContentHtml(data);
					}else{
						var str = this_.param.modal?("#"+this_.param.modal+" #processRecord_list"):"#processRecord_list";
						$(str).empty();
						$(str).append("<tr class='text-center'><td colspan=\"3\">暂无数据 No data.</td></tr>");
					}
				}
			});
		},
		appendContentHtml : function(list){
			var htmlContent = '';
			var this_ = this;
	    	$.each(list,function(index, row) {
		    	htmlContent += "<tr>";		
				htmlContent += "<td  class='text-left ' title='"+StringUtil.escapeStr(row.czrmc)+"' >"+ StringUtil.escapeStr(formatUndefine(row.czrmc))+ "</a></td>";		
				htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.czsj)+"'>"+ StringUtil.escapeStr(row.czsj) + "</td>";
				htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.czsm)+ "'>" + StringUtil.escapeStr(row.czsm)+ "</td>";
				htmlContent += "</tr>";
	    	});
	    	var str = this_.param.modal?("#"+this_.param.modal+" #processRecord_list"):"#processRecord_list";
	    	$(str).empty();
	    	$(str).html(htmlContent);
	    },
	};
	
	