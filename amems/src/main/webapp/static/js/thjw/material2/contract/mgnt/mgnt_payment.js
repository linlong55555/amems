/**
 * 收付款列表
 */
var mgnt_payment_main = {
		id : 'mgnt_payment_main',
		tbodyId : 'mgnt_payment_main_tbody',
		colOptionheadClass : "colOptionhead",
		colOptionhead : true,
		mainid : '',
		biz : '',
		param: {
			mainid : '',
			biz: '',
			zt : 0,
			isView : false,
			callback:function(){}
		},
		init : function(param){
			var this_ = this;
			if(param){
				$.extend(this.param, param);
			}
			this_.loadPayment(param.mainid, param.zt, param.biz);
		},
		loadPayment : function(htid, zt, biz){
			this.mainid = htid;
			this.biz = biz;
			var flag = this.initViewOrHide(zt);
			this.initInfo();
//			if(flag){
//			}else{
//				this.setNoData();
//			}
		},
		initViewOrHide : function(zt){
			var flag = true;
			//显示或隐藏操作列
			if((zt == 2 || zt == 4 || zt == 12) && !this.param.isView){
				this.colOptionhead = true;
				$("."+this.colOptionheadClass, $("#"+this.id)).show();
				$("#contract_main_payment_tfooter_td", $("#"+this.id)).attr("colspan", 7);
			}else{
				this.colOptionhead = false;
				$("."+this.colOptionheadClass, $("#"+this.id)).hide();
				$("#contract_main_payment_tfooter_td", $("#"+this.id)).attr("colspan", 6);
				flag = false;
			}
			return flag;
		},
		initInfo : function(){
			var this_ = this;
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/material/contractpayment/selectByMainid",
				type:"post",
				data:{mainid : this_.mainid},
				dataType:"json",
				success:function(data){
					$("#contract_main_payment_tfooter_td", $("#"+this_.id)).show();
					if(data != null && data.length > 0){
						this_.appendContentHtml(data);
						this_.setTotal();
					}else{
						this_.setNoData();
					}
					this_.callback();
				}
			});
		},
		appendContentHtml: function(list){
			var this_ = this;
			var htmlContent = '';
			var biz = StringUtil.escapeStr(this_.biz);
			$.each(list,function(index,row){
			   
				var paramsMap = row.paramsMap?row.paramsMap:{};
				
				htmlContent += "<tr>";
				
				if(this_.colOptionhead){
					htmlContent += "<td class='fixed-column' style='text-align:center;vertical-align:middle;' >";
					htmlContent += "<i class='spacing icon-edit color-blue cursor-pointer checkPermission' permissioncode='material:contract:mgnt:08' onClick="+this_.id+".openWinEdit('"+ row.id + "') title='修改 Edit'></i>";
					htmlContent += "<i class='spacing icon-trash color-blue cursor-pointer checkPermission' permissioncode='material:contract:mgnt:09' onClick="+this_.id+".del('"+ row.id + "') title='删除 Delete'></i>";  
					htmlContent += "</td>"; 
				}
				
				htmlContent += "<td style='text-align:center;vertical-align:middle;'>";
				htmlContent += 	  "<span name='orderNumber'>"+(index+1)+"</span>";
				htmlContent += "<input type='hidden' name='lx' value='"+row.lx+"'/>";
				htmlContent += "<input type='hidden' name='je' value='"+row.je+"'/>";
				htmlContent += "</td>";
				
				htmlContent += "<td style='text-align:center;vertical-align:middle;'>"+(row.lx==1?"收":"付")+"</td>";
				
				var je = formatUndefine(row.je);
				if(je != ''){
					je = je.toFixed(2);
				}
				
				htmlContent += "<td title='"+je+biz+"' style='text-align:left;vertical-align:middle;'>"+je+biz+"</td>";
				htmlContent += "<td title='"+StringUtil.escapeStr(row.sfkfs)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.sfkfs)+"</td>";
				htmlContent += "<td title='"+StringUtil.escapeStr(row.fphm)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.fphm)+"</td>";
				htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.bz)+"</td>";
				htmlContent += "</tr>"; 
			    
			});
			$("#"+this_.tbodyId, $("#"+this_.id)).empty();
			$("#"+this_.tbodyId, $("#"+this_.id)).html(htmlContent);
			refreshPermission();
		},
		/**
		 * 设置暂无数据
		 */
		setNoData : function(){
			$("#"+this.tbodyId, $("#"+this.id)).empty();
			if(this.colOptionhead){
				$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='7' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
			}else{
				$("#"+this.tbodyId, $("#"+this.id)).append("<tr><td colspan='6' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
			}
			$("#contract_main_payment_tfooter_td", $("#"+this.id)).hide();
		},
		openWinAdd : function(){//打开新增收付款弹出框
			var this_ = this;
			this_.selectHtById(this_.mainid, function(htObj){
				if(htObj.zt == 2 || htObj.zt == 4 || htObj.zt == 12){
					var obj = {};
					obj.id = '';
					obj.lx = 2;
					obj.htmxid = '';
					if(htObj.htlx == 32 || htObj.htlx == 50){
						obj.lx = 1;
					}
					payment_open_alert.show({
				 		modalHeadCN : '新增收付款',
						modalHeadENG : 'Add',
						htObj:htObj,
						viewObj:obj,
						dprtcode : htObj.dprtcode,//默认登录人当前机构代码
						callback : function(data) {//回调函数
							if (data != null) {
								var message = '保存成功!';
								var url = basePath+"/material/contractpayment/save";
								this_.performAction(url, data, message, true);
								$("#"+this_.id+" .modal-body").prop('scrollTop','0');
							}
						}
					});
				}else{
					AlertUtil.showErrorMessage("该合同已更新，请刷新后再进行操作!");
				}
			});
		},
		openWinEdit : function(id){//打开修改合同付款弹出框
			var this_ = this;
			this_.selectHtById(this_.mainid, function(htObj){
				if(htObj.zt == 2 || htObj.zt == 4 || htObj.zt == 12){
					this_.selectById(id, function(obj){
						obj.htmxName = obj.paramsMap.htmxName;
						payment_open_alert.show({
					 		modalHeadCN : '编辑收付款',
							modalHeadENG : 'Edit',
							htObj:htObj,
							viewObj:obj,
							dprtcode : htObj.dprtcode,//默认登录人当前机构代码
							callback : function(data) {//回调函数
								if (data != null) {
									data.id = id;
									var message = '保存成功!';
									var url = basePath+"/material/contractpayment/update";
									this_.performAction(url, data, message, true);
									$("#"+this_.id+" .modal-body").prop('scrollTop','0');
								}
							}
						});
					});
				}else{
					AlertUtil.showErrorMessage("该合同已更新，请刷新后再进行操作!");
				}
			});
		},
		performAction : function(url, param, message, isScrollTop){//执行编辑合同
			var this_ = this;
			startWait($("#"+this_.id));
			// 提交数据
			AjaxUtil.ajax({
				url:url,
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify(param),
				dataType:"json",
				modal:$("#"+this_.id),
				success:function(data){
					finishWait($("#"+this_.id));
					AlertUtil.showMessage(message);
					payment_open_alert.close();
					this_.initInfo();
				}
			});
		},
		/**
		 * 删除合同付款
		 */
		del : function(id){
			var this_ = this;
			AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
				
				 AjaxUtil.ajax({
					 type:"post",
					 url:basePath+"/material/contractpayment/delete",
					 dataType:"json",
					 data:{'id':id},
					 success:function(data) {
						AlertUtil.showMessage('删除成功!');
						this_.initInfo();
					 }
				 });
				 
			}});
		},
		selectById : function(id,obj){//通过id获取数据
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/material/contractpayment/selectById",
				type:"post",
				data:{id:id},
				dataType:"json",
				success:function(data){
					if(data != null){
						if(typeof(obj)=="function"){
							obj(data); 
						}
					}
				}
			});
		},
		selectHtById : function(id,obj){//通过id获取数据
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/material/contract/selectById",
				type:"post",
				data:{id:id},
				dataType:"json",
				success:function(data){
					if(data != null){
						if(typeof(obj)=="function"){
							obj(data); 
						}
					}
				}
			});
		},
		setTotal : function(){
			var data = this.getSFKXX();
			var yjfje = this.getJfje(data.skje, data.fkje);
			$("#htfkzj", $("#"+this.id)).html(yjfje);
		},
		getSFKXX : function(){
			var this_ = this;
			var skje = 0;
			var fkje = 0;
			var param = {skje : '', fkje : ''};
			var len = $("#"+this_.tbodyId,$("#"+this_.id)).find("tr").length;
			if (len == 1){
				if($("#"+this_.tbodyId,$("#"+this_.id)).find("td").length ==1){
					return param;
				}
			}
			if (len > 0){
				$("#"+this.tbodyId,$("#"+this.id)).find("tr").each(function(){
					var tr_this = this;
					var lx = $(tr_this).find("input[name='lx']").val();
					var je =$(tr_this).find("input[name='je']").val();
					if(lx == 1){
						skje += Number(je);
					}else{
						fkje += Number(je);
					}
				});
			}
			param.skje = skje;
			param.fkje = fkje;
			return param;
		},
		getJfje : function(skje, fkje){
			var biz = StringUtil.escapeStr(this.biz);
			var yjfje = '';
			if(skje == '' && fkje == ''){
				yjfje = "-";
			}
			if(skje != null && skje != ''){
				yjfje += "收：" + skje.toFixed(2) + biz + " ";
			}
			if(fkje != null && fkje != ''){
				yjfje += "付：" + fkje.toFixed(2) + biz + " ";
			}
			return yjfje;
		},
		callback : function(){
			if(this.param.callback && typeof(this.param.callback) == "function"){
				this.param.callback();
			}
		}
}