
	var flb_detail = {
			
		id : "open_win_flightlogbook_detail_modal",
		
		param: {
			parentId : "",
			chinaHead : "新增",
			englishHead : "Add", //已经选择的
			ope_type : 1,//1:新增,2:修改,3:改版
			isEdit : true,
			jx : '',
			wxfabh : '',
			editData : {},
			dprtcode:userJgdm,//默认登录人当前机构代码
			callback:function(){}//回调函数
		},
		
			
		// 显示页面
		show : function(id){
			$("#"+this.id).modal("show");
			AlertUtil.hideModalFooterMessage(this.id);
			this.init(id);
		},
		
		// 页面初始化
		init : function(id){
			
			var this_ = this;
			// 初始化飞机注册号
			flb.initAcReg("common_fjzch",this_.param.dprtcode);
			
			this_.record = new FlbRecord({
				parentId : "open_win_flightlogbook_detail_modal",
				ope_type : 1,
				win_type : "detail",
				dprtcode:this_.getDprtcode(),
			});
			
			this_.loadAttachment(id);
			
			$('#common_fxrq').datepicker({
				 autoclose: true,
				 clearBtn:true
			}).on('hide', function(e) {
				// 加载故障保留单
				this_.loadFaultReservation();
				// 加载飞行前数据
				this_.record.loadPreflightData();
		    });
			
			// 初始化完成工作证
			flb_work.init({
				fjzch : this_.getFjzch(),
				dprtcode : this_.getDprtcode()
			});
			
			this_.loadData(id);
		},
		
		// 获取发动机数量
		getEngCount : function(){
			return parseInt($("#common_fjzch option:selected").attr("fdjsl"));
		},
		
		// 获取组织机构
		getDprtcode : function(){
			return this.param.dprtcode;
		},
		
		// 获取飞机注册号
		getFjzch : function(){
			return $.trim($("#common_fjzch").val());
		},
		
		//获取飞行日期
		getFxrq : function(){
			return $.trim($("#common_fxrq").val());
		},
		
		//获取飞行记录本状态
		getStatus : function(){
			return $.trim($("#common_zt").val());
		},
		
		// 获取机型
		getACType : function(){
			return $.trim($("#common_fjzch option:selected").attr("jx"));
		},
		
		// 加载附件
		loadAttachment : function(id){
			var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_flb_edit');
			attachmentsObj.show({
				attachHead : true,
				djid:formatUndefine(id),
				fileType:"flb",
		 		colOptionhead : true,
				fileHead : true,
				left_column : false,
			});//显示附件
		},
		
		// 加载故障保留单
		loadFaultReservation : function(){
			
			var this_ = this;
			var $tbody = $("#flb_fault_reservation_table_list");
			if(!this_.getFxrq()){
				$tbody.empty();
				$tbody.append("<tr><td colspan=\"7\">暂无数据 No data.</td></tr>");
				return;
			}
			AjaxUtil.ajax({
				url:basePath+"/produce/reservation/fault/queryListByFLB",
				type:"post",
				data:JSON.stringify({
					fjzch : this_.getFjzch(),
					fxrq : this_.getFxrq(),
					dprtcode : this_.getDprtcode()
				}),
				dataType:"json",
				contentType: "application/json;charset=utf-8",
				success:function(data){
					$tbody.empty();
					if(data.length >0){
						   var htmlContent = '';
						   $.each(data,function(index,row){
							   htmlContent += ["<tr>",
							                   "<td style='text-align:center'><a href='javascript:void(0);' onclick='flb_detail.viewFaultReservation(\""+row.id+"\")' title='"+StringUtil.escapeStr(row.bldh)+"' >"+StringUtil.escapeStr(row.bldh)+"</a></td>",
							                   "<td title='"+DicAndEnumUtil.getEnumName('failureKeepTypeEnum', row.bllx)+"'>"+DicAndEnumUtil.getEnumName('failureKeepTypeEnum', row.bllx)+"</td>",
							                   "<td>"+this_.showPJInfo(row.scblqxList, 'scblqx'+row.id)+"</td>",
							                   "<td title='"+(row.jcrq||"").substr(0, 10)+"'>"+(row.jcrq||"").substr(0, 10)+"</td>",
							                   "<td style='text-align:left' title='"+StringUtil.escapeStr(row.blyy)+"' >"+StringUtil.escapeStr(row.blyy)+"</td>",
							                   "<td style='text-align:left' title='"+StringUtil.escapeStr(row.zcBlyy)+"' >"+StringUtil.escapeStr(row.zcBlyy)+"</td>",
							                   "<td style='text-align:left' title='"+StringUtil.escapeStr(row.gzms)+"'>"+StringUtil.escapeStr(row.gzms)+"</td>",		
							                   "</tr>"
							                   ].join("");
						   });
						   $tbody.html(htmlContent);
				   } else {
					   $tbody.append("<tr><td colspan=\"7\">暂无数据 No data.</td></tr>");
				   }
				}
			});
		},
		
		//拼接数据
		showPJInfo : function(list, id) {
			var html = "";
			if (list != null) {
				for (var i = 0; i < list.length; i++) {
					var showContent =  StringUtil.escapeStr(list[i]);
					
					if(showContent.length == 0 && i != list.length-1){
						showContent = "<br>";
					}
					else if(showContent.length > 18){
						showContent = showContent.substring(0,18)+"...";
					}
					
					if (i == 1) {
						html = html + "<div id='"+ id + "'>";
					}
					if (i == 0) {
						html += "<i href='javascript:void(0);' title='"+ showContent + "'>" + showContent + "</i>";
					}
					if (i != 0) {
						html += "<i href='javascript:void(0);' title='" + showContent + "' >" + showContent + "</i>";
						if(showContent != "<br>"){
							html += "<br>";
						}
					}
					if (i != 0 && i == list.length - 1) {
						html += "</div>";
					}
				}
			}
			
			return html;
		},
		
		// 查看故障保留单
		viewFaultReservation : function(id){
			window.open(basePath + "/produce/reservation/fault/find/" + id);
		},
		
		// 更改飞机
		changePlane : function (){
			this.loadData("0");
			this.loadFaultReservation();
		},
		
		// 验证数据
		validateData : function(){
			var this_ = this;
			
			var fjzch = $.trim($("#common_fjzch").val());
			var fxrq = $.trim($("#common_fxrq").val());
			var jlbym = $.trim($("#common_jlbym").val());
			if(!fjzch){
				AlertUtil.showModalFooterMessage("请输入飞机注册号！");
				return false;
			}
			if(!fxrq){
				AlertUtil.showModalFooterMessage("请输入飞行日期！");
				return false;
			}
			if(!jlbym){
				AlertUtil.showModalFooterMessage("请输入记录本页码！");
				return false;
			}
			return true;
		},
		
		// 获取数据
		getData : function(){
			var this_ = this;
			var obj = {};
			if(!this_.validateData()){
				obj.error = true;
			}
			
			obj.id = $.trim($("#common_id").val());
			obj.fjzch = $.trim($("#common_fjzch").val());
			obj.fxrq = $.trim($("#common_fxrq").val());
			obj.jlbym = $.trim($("#common_jlbym").val());
			obj.dprtcode = this_.getDprtcode();
			
			/* 飞行数据  */
			obj.flightSheetVoyageList = this_.record.getData();
			if(obj.flightSheetVoyageList == null){
				obj.error = true;
			}
			if(obj.flightSheetVoyageList && obj.flightSheetVoyageList.length > 0){
				obj.kcsj = obj.flightSheetVoyageList[0].kcsj;
			}else{
				//当没有飞行数据时，飞行记录本开车时间为飞行日期
				obj.kcsj = obj.fxrq+" 00:00:00";
			}
			
			/* 完成工作  */
			obj.legs = flb_work.getData();
			if(!obj.legs){
				obj.error = true;
			}
			/* 附件  */
			obj.attachments = attachmentsUtil.getAttachmentsComponents('attachments_flb_edit').getAttachments();
			
			return obj;
		},
		
		// 删除飞行记录本
		del : function(){
			var this_ = this;
			var id = $.trim($("#common_id").val());
			AlertUtil.showConfirmMessage("是否确定删除？", {callback: function(){
				startWait();
				AjaxUtil.ajax({
					url:basePath+"/produce/flb/delete",
					contentType:"application/json;charset=utf-8",
					type:"post",
					data:JSON.stringify({
						id : id
					}),
					dataType:"json",
					success:function(data){
						finishWait();
						AlertUtil.showMessage("删除成功！");
						$("#"+this_.id).modal("hide");
						flb.reload();
					}
				});
			}});
		},
		
		// 保存飞行记录本
		save : function(){
			
			var this_ = this;
			var obj = this_.getData();
			if(obj){
				if(obj.error){
					return false;
				}else if(obj.weakMsg){
					AlertUtil.showConfirmMessage(obj.weakMsg, {callback: function(){
						delete obj.weakMsg;
						doSave();
					}});
				}else{
					doSave();
				}
			}
			
			function doSave(){
				startWait($("#"+this_.id));
				AjaxUtil.ajax({
					url:basePath+"/produce/flb/save",
					contentType:"application/json;charset=utf-8",
					type:"post",
					data:JSON.stringify(obj),
					modal : $("#"+this_.id),
					dataType:"json",
					success:function(data){
						finishWait($("#"+this_.id));
						AlertUtil.showMessage("保存成功！");
						$("#"+this_.id).modal("hide");
						flb.reload();
					}
				});
			}
		},
		// 提交飞行记录本
		submit : function(){
			
			var this_ = this;
			var obj = this_.getData();
			if(obj){
				if(obj.error){
					return false;
				}else if(obj.weakMsg){
					AlertUtil.showConfirmMessage(obj.weakMsg, {callback: function(){
						delete obj.weakMsg;
						doSubmit();
					}});
				}else{
					doSubmit();
				}
			}
			
			function doSubmit(){
				startWait($("#"+this_.id));
				AjaxUtil.ajax({
					url:basePath+"/produce/flb/submit",
					contentType:"application/json;charset=utf-8",
					type:"post",
					data:JSON.stringify(obj),
					modal : $("#"+this_.id),
					dataType:"json",
					success:function(data){
						finishWait($("#"+this_.id));
						AlertUtil.showMessage("提交成功！");
						$("#"+this_.id).modal("hide");
						flb.reload();
					}
				});
			}
		},
		revision:function(){
			var this_ = this;
			var obj = this_.getData();
			if(obj){
				if(obj.error){
					return false;
				}else if(obj.weakMsg){
					AlertUtil.showConfirmMessage(obj.weakMsg, {callback: function(){
						delete obj.weakMsg;
						doRevise();
					}});
				}else{
					doRevise();
				}
			}
			
			function doRevise(){
				startWait($("#"+this_.id));
				AjaxUtil.ajax({
					url:basePath+"/produce/flb/revise",
					contentType:"application/json;charset=utf-8",
					type:"post",
					data:JSON.stringify(obj),
					modal : $("#"+this_.id),
					dataType:"json",
					success:function(data){
						finishWait($("#"+this_.id));
						AlertUtil.showMessage("修订成功！");
						$("#"+this_.id).modal("hide");
						flb.reload();
					}
				});
			}
		},
		//作废飞行记录本
		discard:function(){
			var this_ = this;
			var id = $.trim($("#common_id").val());
			AlertUtil.showConfirmMessage("确定要作废该记录?", {callback: function(){
				startWait($("#"+this_.id));
				AjaxUtil.ajax({
					url:basePath+"/produce/flb/discard/"+id,
					contentType:"application/json;charset=utf-8",
					type:"post",
					modal : $("#"+this_.id),
					dataType:"json",
					success:function(data){
						finishWait($("#"+this_.id));
						AlertUtil.showMessage("作废成功！");
						$("#"+this_.id).modal("hide");
						flb.reload();
					}
				});
			}});
			
		},
		// 加载数据
		loadData : function(id){
			var this_ = this;
			if(id == "0"){ // 切换飞机
				var data = {};
				data.fjzch = $.trim($("#common_fjzch").val());
				data.fxrq = $.trim($("#common_fxrq").val());
				data.jlbym = $.trim($("#common_jlbym").val());
				this_.fillData(data);
				this_.record.loadPreflightData();
			}else if(id){// 修改
				startWait();
				AjaxUtil.ajax({
					url: basePath+"/produce/flb/detail",
					type: "post",
					contentType:"application/json;charset=utf-8",
					dataType:"json",
					data:JSON.stringify({
						id : id,
					}),
					success:function(data){
						finishWait();
						this_.fillData(data);
				    }
				}); 
			}else{ // 新增
				var data = {};
				data.fjzch = $.trim($("#fjzch").val());
				this_.fillData(data);
				this_.record.loadPreflightData();
			}
		},
		
		// 填充数据
		fillData : function(data){
			var this_ = this;
			
			$("#common_id").val(data.id);
			$("#common_zt").val(data.zt);
			if(data.fjzch==''||data.fjzch == null){
				$("#common_fjzch option:first").prop("selected", 'selected');  
			}else{
				$("#common_fjzch").val(data.fjzch);
			}			
			$("#common_fxrq").val((data.fxrq || "").substr(0, 10));
			$("#common_fxrq").datepicker("update");
			$("#common_jlbym").val(data.jlbym);
			$("#common_fxjlbh").text(data.fxjlbh);
			
			if(data.zt == 2 || data.zt == 12){
				$("#common_fjzch").attr("disabled","disabled");
				$("#common_fxrq").attr("disabled","disabled");
			}else{
				$("#common_fjzch").removeAttr("disabled");
				$("#common_fxrq").removeAttr("disabled");
			}
			
			if(data.fxjlbh){
				$("#common_fxjlbh_div").show();
			}else{
				$("#common_fxjlbh_div").hide();
			}
			
			// 填充航次数据
			this_.record.fillData(data.flightSheetVoyageList);
			
			// 填充航段数据
			flb_work.fillData(data.legs);
			
			// 加载故障保留单
			this_.loadFaultReservation();
			
			// 加载页码
			this_.loadPage(data);
			
			// 判断按钮显示
			this_.showButton(data);
		},
		
		// 判断按钮显示
		showButton : function(data){
			//根据状态显示按钮
			flb.hiddenBtn();
			if(!data.zt){
				$(".btn-save").show();
				$(".btn-submit").show();
			}else if(data.zt == 1){
				$(".btn-save").show();
				$(".btn-submit").show();
				$(".btn-delete").show();
			}else if(data.zt == 2 || data.zt == 12){
				$(".btn-revision").show();
				$(".btn-scrap").show();
			}
		},
		
		// 加载页码
		loadPage : function(data){
			var this_ = this;
			$("#detail_last_page").off("click");
			$("#detail_next_page").off("click");
			if(data.lastPage){
				$("#detail_last_page").on("click", function(){
					this_.loadData(data.lastPage);
				});
			}
			if(data.nextPage){
				$("#detail_next_page").on("click", function(){
					this_.loadData(data.nextPage);
				});
			}
			if(data.id){
				$("#detail_close_page").show();
			}else{
				$("#detail_close_page").hide();
			}
		},
		
	};
	
	