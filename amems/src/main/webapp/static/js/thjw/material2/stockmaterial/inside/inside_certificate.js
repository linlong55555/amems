
	$(function(){
		
		$("#stock_certificate_modal").on("shown.bs.modal", function() {
			AlertUtil.hideModalFooterMessage();
		});

	});
	
	var CertificateUtil = function(param_){
			
		var modalId = "stock_certificate_modal";
		// 证书数据
		var data = [];
		// 当前证书数据
		var current = {};
		
		var attachmentsObj = {};
		
		var param = {
			parentId : "",
			dprtcode : "",
			tbody : null
		};
		
		(function(){
			if(param_){
				$.extend(param, param_);
			}
			if(param.parentId){
				init();
			}
		})();
		
		// 初始化
		function init(){
			$("#certificate_modal_zjlx").empty();
			DicAndEnumUtil.registerDic("51", "certificate_modal_zjlx", param.dprtcode);
			
			if(param.ope_type == 3){
				jQueryName("common_certificate_addTh").hide();
			}else{
				jQueryName("common_certificate_addTh").show();
			}
			
			// 初始化日期控件
			$('.date-picker').datepicker({
				autoclose : true,
				clearBtn : true
			});
			
			addNoDataRow ();
			
			$("#common_certificate_addBtn").off("click").on("click", function(){
				modify();
			});
			
		}
		
		// 通过name获取jquery对象
		function jQueryName(name){
			return $("#"+param.parentId+" [name='"+name+"']");
		}
		
		// 通过class获取jquery对象
		function jQueryClass(clazz){
			return $("#"+param.parentId+" ."+clazz);
		}
		
		// 加载部件证书列表
		function load(obj){
			$("#common_certificate_saveBtn").off("click").on("click", function(){
				
				save();
			});
			if(obj.data){
				if(obj.data.length > 0){
					param.tbody.empty();
					$(obj.data).each(function(i, cer){
						addRow(cer);
					});
				}else{
					addNoDataRow(obj.tbody);
				}
				addDataList(obj.data);
			}else if(obj.bjh){
				if(obj.xlh){
					obj.pch = "-";
				}else{
					obj.xlh = "-";
					obj.pch = obj.pch || "-";
				}
				startWait();
				AjaxUtil.ajax({
					url: basePath+"/aerialmaterial/certificate/list",
					type: "post",
					contentType:"application/json;charset=utf-8",
					dataType:"json",
					data:JSON.stringify({
						jh : obj.bjh,
						xlh : obj.xlh,
						pch : obj.pch,
						dprtcode : obj.dprtcode
					}),
					success:function(data){
						finishWait();
						if(data.length > 0){
							param.tbody.empty();
							$(data).each(function(i, cer){
								addRow(cer);
							});
						}else{
							addNoDataRow(obj.tbody);
						}
						addDataList(data);
				    }
				}); 
			}
		}
		
		// 保存证书
		function save(){
			
			var obj = {
				rowid : $("#certificate_modal_rowid").val(),
				zjlx : $("#certificate_modal_zjlx").val(),
				zsbh : $("#certificate_modal_zsbh").val(),
				zscfwz : $("#certificate_modal_zscfwz").val(),
				qsrq : $("#certificate_modal_qsrq").val(),
				attachments : attachmentsObj.getAttachments(),
			};
			addRow(obj);
			addData(obj);
			
			$("#"+modalId).modal("hide");
		}
		
		// 添加数据
		function addData(obj){
			var insert = true;
			for(var i = 0; i < data.length; i++){
				if(data[i].rowid == obj.rowid){
					data[i] = obj;
					insert = false;
				}
			}
			if(insert){
				data.push(obj);
			}
		}
		
		// 查找数据
		function findData(rowid){
			var one = {};
			$(data).each(function(i, row){
				if(row.rowid == rowid){
					one = row;
				}
			});
			return one;
		}
		
		// 添加数据集合
		function addDataList(list){
			data = [];
			$(list).each(function(i, obj){
				obj.qsrq = (obj.qsrq || "").substr(0, 10);
				obj.whsj = (obj.whsj || "").substr(0, 10);
				addData(obj);
			});
		}
		
		// 新增行
		function addRow(obj){
			if(!obj.rowid || jQueryName(obj.rowid).length == 0){
				obj.rowid = Math.uuid().toLowerCase();
				var html = "";
				html += "<tr name='"+obj.rowid+"'>";
				if(param.ope_type != 3){
					html += "<td class='text-center'>";
					html += "<button class='line6 line6-btn' type='button' name='editBtn'><i class='icon-edit cursor-pointer color-blue cursor-pointer' title='修改 Edit'></i></button>";
					html += "<button class='line6 line6-btn margin-left-5' type='button' name='deleteBtn'><i class='icon-minus cursor-pointer color-blue cursor-pointer' title='删除 Delete'></i></button>";
					html += "</td>";
				}
				html += "<td name='zjlx'>"+StringUtil.escapeStr(obj.zjlx)+"</td>";
				html += "<td name='zsbh'>"+StringUtil.escapeStr(obj.zsbh)+"</td>";
				html += "<td name='zscfwz'>"+StringUtil.escapeStr(obj.zscfwz)+"</td>";
				html += "<td name='qsrq' class='text-center'>"+(obj.qsrq||"").substr(0, 10)+"</td>";
				html += "<td name='fj' class='text-center'>";
				if(obj.attachments != null && obj.attachments.length > 0) {
					html += '<i fjid="'+obj.rowid+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer"  style="font-size:15px"></i>';
				}
				html += "</td>";
				html += "</tr>";
				param.tbody.find(".noData").remove();
				param.tbody.append(html);
				jQueryName(obj.rowid).find("[name='editBtn']").on("click", function(){
					modify(obj.rowid);
				});
				jQueryName(obj.rowid).find("[name='deleteBtn']").on("click", function(){
					deleteRow(obj.rowid);
				});
			}else{
				var $row = jQueryName(obj.rowid);
				$row.find("[name='zjlx']").text(StringUtil.escapeStr(obj.zjlx));
				$row.find("[name='zsbh']").text(StringUtil.escapeStr(obj.zsbh));
				$row.find("[name='zscfwz']").text(StringUtil.escapeStr(obj.zscfwz));
				$row.find("[name='qsrq']").text((obj.qsrq||"").substr(0, 10));
				if(obj.attachments != null && obj.attachments.length > 0) {
					$row.find("[name='fj']").html('<i fjid="'+obj.rowid+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer"  style="font-size:15px"></i>');
				}
				
			}
			$("#"+modalId+" .date-picker").datepicker("update");
			// 初始化附件消息框
			initWebuiPopover();
		}
		
		// 初始化附件消息框
		function initWebuiPopover(){
			if(!param.container){
				param.container = 'body';
			}
			WebuiPopoverUtil.initWebuiPopover('attachment-view',param.container,function(obj){
				console.info();
				return getHistoryAttachmentList(obj);
			});
			$("#maintenance_item_table_top_div").scroll(function(){
				$('.attachment-view').webuiPopover('hide');
			});
		}
		
		// 获取历史附件列表
		function getHistoryAttachmentList(obj){
			console.info(obj);
			return appendContentHtml(findData($(obj).attr('fjid')).attachments);
		}
		
		// 拼接html
		function appendContentHtml(list){
			var this_ = this;
			var htmlContent = '';
			var attachments = [];
			var delList = [];
			$.each(list, function(i, obj){
				if(obj.operate != "DEL"){
					attachments.push(obj);
				}else{
					delList.push(obj);
				}
			});
			for(var i = 0; i < attachments.length; i++){
				var isdelete = false;
				for(var j = 0; j < delList.length; j++){
					if(attachments[i].id == delList[j].id){
						isdelete = true;
					}
				}
				if(isdelete){
					attachments.splice(i, 1);
				}
			}
			$.each(attachments,function(index,row){
			   
				var fj = StringUtil.escapeStr(row.wbwjm);
				if(StringUtil.escapeStr(row.hzm) != ''){
					fj += "."+StringUtil.escapeStr(row.hzm);
				}
				htmlContent = htmlContent + "<tr>";
				htmlContent += "<td title='附件' class='text-center' style='padding-left:0px;'>附件</td>";
				htmlContent += '<td title="'+fj+'" class="text-left">';
				if(row.id){
					htmlContent += "<a href='javascript:void(0);' onclick=downloadfile('"+row.id+"') >"+fj+"</a>";
				}else{
					htmlContent += fj;
				}
				htmlContent += '</td>';
				htmlContent += "</tr>"; 
			    
			});
			$("#history_list", $("#history_attach_alert_Modal")).empty();
			$("#history_list", $("#history_attach_alert_Modal")).html(htmlContent);
			
			return $("#history_attach_alert_Modal").html();
		}
		
		// 删除行
		function deleteRow(rowid){
			var $row = jQueryName(rowid);
			$row.remove();
			if(param.tbody.find("tr").length == 0){
				addNoDataRow();
			}
			for (var i = 0; i < data.length; i++) {
	            if (data[i].rowid == rowid) {
	            	data.splice(i, 1);
	            }
	        }
		}
		
		// 新增无数据行
		function addNoDataRow(){
			if(param.ope_type != 3){
				param.tbody.html("<tr class='noData'><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>");
			}else{
				param.tbody.html("<tr class='noData'><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>");
			}
		}
		
		// 新增/修改证书
		function modify(rowid){
			var obj = findData(rowid);
			$("#"+modalId).modal("show");
			obj = obj || {};
			if(obj.zjlx){
				$("#certificate_modal_zjlx").val(obj.zjlx);
			}else{
				$("#certificate_modal_zjlx").find("option:first").attr("selected", 'selected'); 
			}
			$("#certificate_modal_rowid").val(obj.rowid||"");
			$("#certificate_modal_zsbh").val(obj.zsbh||"");
			$("#certificate_modal_zscfwz").val(obj.zscfwz||"");
			$("#certificate_modal_qsrq").val((obj.qsrq||"").substr(0, 10));
			initAttachment(obj.attachments);
		}
		
		// 初始化附件
		function initAttachment(attachments){
			attachments = attachments || [];
			attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
			$.each(attachments, function(i, obj){
				if(!obj.uuid){
					obj.uuid = Math.uuid().toLowerCase();
				}
			});
			attachmentsObj.show({
				attachHead : true,
				attachDatas:attachments,
				fileType:"certificate",
		 		colOptionhead : true,
				fileHead : true,
			});
		}
		
		// 获取证书数据
		function getData(){
			$.each(data, function(i, obj){
				delete obj.rowid;
				obj.attachments = attachmentsUtil.getAttachmentsComponents().removeDeleteAttachment(obj.attachments);
			});
			return data;
		}
		
		return {
			load : load,
			getData : getData
		};
		
	};
	
	