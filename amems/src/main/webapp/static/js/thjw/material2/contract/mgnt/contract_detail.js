/**
 * 合同详情列表
 */
var contract_main_detail = {
		id : 'contract_main_detail',
		tbodyId : 'contract_main_detail_list',
		mainid : '',
		htlx : '',
		biz : '',
		columnCount : 10,
		list : {
			10 : ['htsl_d','jfsl_d','dj_d','wlzt_d','xj_d'],
			20 : ['xlh_d','cksl_d','rksl_d','sxyy_d'],
			31 : ['htsl_d','xlh_d','jfsl_d','ghsl_d','dj_d','wlzt_d','xj_d'],
			32 : ['htsl_d','xlh_d','jfsl_d','ghsl_d','dj_d','wlzt_d','xj_d'],
			40 : ['htsl_d','cksl_d','rksl_d','dj_d','wlzt_d','xj_d'],
			50 : ['htsl_d','jfsl_d','dj_d','wlzt_d','xj_d']
		},
		col : {
			10 : 9,
			20 : 8,
			31 : 11,
			32 : 11,
			40 : 10,
			50 : 9
		},
		init : function(obj){
			this.mainid = obj.id;
			this.htlx = obj.htlx;
			this.biz = obj.biz;
			this.initViewOrHide();
			this.initInfo();
		},
		initInfo : function(){
			var this_ = this;
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/material/contractinfo/queryDetailByMainid",
				type:"post",
				data:{mainid : this_.mainid},
				dataType:"json",
				success:function(data){
					$("#htmxzj", $("#"+this_.id)).html(0);
					if(data != null && data.length > 0){
						this_.appendContentHtml(data);
					}else{
						this_.setNoData();
					}
				}
			});
		},
		appendContentHtml: function(list){
			var this_ = this;
			var htmlContent = '';
			var htmxzj = 0;
			var htlx = this_.htlx;
			var biz = StringUtil.escapeStr(this_.biz);
			$.each(list,function(index,row){
			   
				var paramsMap = row.paramsMap?row.paramsMap:{};
				var rks = paramsMap.rks;
				var cks = paramsMap.cks;
				var r_c = rks-cks;
				var c_r = cks-rks;
				var dw = StringUtil.escapeStr(row.dw);
				
				htmlContent += "<tr>";
				
				htmlContent += "<td title='"+StringUtil.escapeStr(row.bjh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bjh)+"</td>";
				
				htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.zywms)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.zywms)+"</td>";
				
				if(htlx == 20 || htlx == 31 || htlx == 32){
					htmlContent += "<td title='"+StringUtil.escapeStr(row.xlh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.xlh)+"</td>";
				}
				
				var htsl = StringUtil.escapeStr(row.sl?row.sl:1);
				
				if(htlx != 20){
					htmlContent += "<td title='"+(htsl+dw)+"' style='text-align:left;vertical-align:middle;'>"+(htsl+dw)+"</td>";
				}
				
				if(htlx == 20 || htlx == 40){
					htmlContent += "<td title='"+cks+dw+"' style='text-align:left;vertical-align:middle;'>"+cks+dw+"</td>";
				}
				if(htlx == 10 || htlx == 31 || htlx == 32 || htlx == 50){
					var jfsl = (htlx == 10?r_c:(htlx == 31?rks:(htlx == 32?cks:(htlx == 50?c_r:0))));
					htmlContent += "<td title='"+jfsl+dw+"' style='text-align:left;vertical-align:middle;'>"+jfsl+dw+"</td>";
				}
				if(htlx == 20 || htlx == 40){
					htmlContent += "<td title='"+rks+dw+"' style='text-align:left;vertical-align:middle;'>"+rks+dw+"</td>";
				}
				if(htlx == 31 || htlx == 32){
					var ghsl = (htlx == 31?cks:rks);
					htmlContent += "<td title='"+ghsl+dw+"' style='text-align:left;vertical-align:middle;'>"+ghsl+dw+"</td>";
				}
				if(htlx == 20){
					htmlContent += "<td title='"+StringUtil.escapeStr(row.sxyy)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.sxyy)+"</td>";
				}
				if(htlx != 20){
					
					var dj = formatUndefine(row.dj);
					if(dj != '' || dj === 0){
						dj = dj.toFixed(2);
					}
					
					htmlContent += "<td title='"+dj+biz+"' style='text-align:left;vertical-align:middle;'>"+dj+biz+"</td>";
					htmlContent += "<td title='"+StringUtil.escapeStr(row.wlzt)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.wlzt)+"</td>";
				}
				
				htmlContent += "<td title='"+StringUtil.escapeStr(paramsMap.cqbh)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(paramsMap.cqbh)+"</td>";
				var jhq = StringUtil.escapeStr(row.jhq);
				if(row.jhqdw == 11){
					jhq += "D";
				}
				if(row.jhqdw == 12){
					jhq += "M";
				}
				htmlContent += "<td title='"+jhq+"' style='text-align:left;vertical-align:middle;'>"+jhq+"</td>";
				/*小计*/
				if(htlx != 20){
					var xj = 0;
					xj = formatUndefine(row.sl) * formatUndefine(row.dj);
					if(xj == ''){
						xj = 0;
					}
					xj = xj.toFixed(2);
					htmxzj += Number(xj);
					htmlContent += "<td title='"+xj+biz+"' style='text-align:right;vertical-align:middle;'>"+xj+biz+"</td>";
				}
				htmlContent += "</tr>"; 
			    
			});
			$("#"+this_.tbodyId, $("#"+this_.id)).empty();
			$("#"+this_.tbodyId, $("#"+this_.id)).html(htmlContent);
			if(htlx != 20){
				htmxzj = htmxzj.toFixed(2)+biz;
				$("#htmxzj", $("#"+this_.id)).html(htmxzj);
			}
		},
		/**
		 * 设置暂无数据
		 */
		setNoData : function(){
			$("#"+this.tbodyId, $("#"+this.id)).empty();
			$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='"+this.col[this.htlx]+"' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		},
		initViewOrHide : function(){
			var this_ = this;
			this_.setViewOrHide();
			var arr = this.list[this.htlx];
			$.each(arr,function(index,id){
				$("#"+id, $("#"+this_.id)).show();
			});
			$("#contract_main_detail_tfooter_td", $("#"+this_.id)).show();
			if(this_.htlx == 20){
				$("#contract_main_detail_tfooter_td", $("#"+this_.id)).hide();
			}
			$("#contract_main_detail_tfooter_td", $("#"+this_.id)).attr("colspan", this_.col[this_.htlx]);
		},
		setViewOrHide : function(){
			$("#xlh_d", $("#"+this.id)).hide();
			$("#htsl_d", $("#"+this.id)).hide();
			$("#cksl_d", $("#"+this.id)).hide();
			$("#jfsl_d", $("#"+this.id)).hide();
			$("#rksl_d", $("#"+this.id)).hide();
			$("#ghsl_d", $("#"+this.id)).hide();
			$("#sxyy_d", $("#"+this.id)).hide();
			$("#dj_d", $("#"+this.id)).hide();
			$("#wlzt_d", $("#"+this.id)).hide();
			$("#xj_d", $("#"+this.id)).hide();
		}
}