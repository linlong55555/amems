
	var problemTables = {
	
		// 页面初始化
		init : function(param){
			if(param){
				$.extend(this.param, param);
			}
			$("#problemlist").empty();
			var this_ = this;
			// 加载数据
			this_.loadData();
		},	
		param : {
			data:[],
			zt:0,//发现问题通知单状态
			id:null,//发现问题通知单id
			dprtcode:userJgdm,//默认登录人当前机构代码
			callback:function(){}//回调函数
		},
		loadData : function(){
			var this_ = this;
			getDataById(this_.param.id,function(obj){
				var list = obj.details;
				this_.param.zt = obj.zt;
				this_.appendContentHtml(list);
			})
		},
		appendContentHtml : function(list){
			var htmlContent = '';
			var this_ = this;
	    	$.each(list,function(index, row) {
		    	htmlContent += "<tr>";
				htmlContent += "<td class='text-center fixed-column'>";	
				if(this_.param.zt ==0 ){
					htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='quality:noticeofdiscovery:main:02'  onClick=\"edit('"
							+ this_.param.id + "')\" title='编辑 Edit'></i>&nbsp;&nbsp";
				} 
				if(this_.param.zt ==1 ){
					htmlContent += "<i class='iconnew-end color-blue cursor-pointer checkPermission'  permissioncode='quality:noticeofdiscovery:main:04' onClick=\"endClose('"
							+ this_.param.id + "')\" title='关闭 Close'></i>&nbsp;&nbsp;";
				}
				htmlContent += "</td>";			
				htmlContent += "<td  class='text-left ' title='"+StringUtil.escapeStr(row.shwtbh)+"' ><a href='#' onClick=\"problemTables.view('"
						+ row.id + "')\">"+ StringUtil.escapeStr(formatUndefine(row.shwtbh))+ "</a></td>";		
				htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.wtms)+"'>"+ StringUtil.escapeStr(row.wtms) + "</td>";
				htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.wtdj)+ "'>" + StringUtil.escapeStr(row.wtdj)+ "</td>";
				htmlContent += "<td class='text-left' title='"+ StringUtil.escapeStr(row.wtfl)+ "'>" + StringUtil.escapeStr(row.wtfl)+ "</td>";
				htmlContent += "</tr>";
	    	});
	    	$("#problemlist").empty();
	    	$("#problemlist").html(htmlContent);
	    	refreshPermission();
	    },
	    view : function(id){
	    	window.open(basePath+"/quality/correctivemeasures/view?id="+id);
	    }
	};
	
	