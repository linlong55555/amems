
	$(function(){
		$("#flb_work_wcgz_table_div").scroll(function(){
			$('.attachment-view').webuiPopover('hide');
		});
	});
	
	var flb_work = {
		
		workTbodyName : "flb_work_table_list",
		
		iceTbodyName : "flb_anti_ice_table_list",
			
		param : {
			legs:[]
		},
			
		// 初始化
		init : function(param){
			if(param){
				$.extend(this.param, param);
			}
			this.initLeg();
		},
		
		// 初始化航段
		initLeg : function(){
			var this_ = this;
			var legs = [];
			var before = {
				hd : 1,
				hdms : "航前",
				finishedWorks : []
			};
			var station = {
				hd : 3,
				hdms : "过站",
				finishedWorks : []
			};
			var after = {
				hd : 99,
				hdms : "航后",
				finishedWorks : []
			};
			var stop = {
				hd : 101,
				hdms : "停场",
				finishedWorks : []
			};
			legs.push(before);
			legs.push(station);
			legs.push(after);
			legs.push(stop);
			this_.param.legs = legs;
		},
		
		// 新增航段
		addLeg : function(hd){
			var this_ = this;
			var legs = this_.param.legs;
			var leg = {
				hd : hd,
				finishedWorks : []
			};
			legs.push(leg);
		},
		
		// 删除航段
		deleteLeg : function(hd){
			var this_ = this;
			var legs = this_.param.legs;
			for (var i = 0; i < legs.length; i++) {
	            if (legs[i].hd == hd) {
	            	legs.splice(i, 1);
	            }
	        }
		},
		
		// 新增/修改数据
		mergeWork : function(work){
			var this_ = this;
			var works = this_.getLeg(work.hd).finishedWorks;
			var insert = true;
			for(var i = 0; i < works.length; i++){
				if(works[i].rowid == work.rowid){
					works[i] = work;
					insert = false;
				}
			}
			if(insert){
				works.push(work);
			}
		},
		
		// 删除完成工作
		deleteWorkData : function(hd, rowid){
			var this_ = this;
			var works = this_.getLeg(hd).finishedWorks;
			for(var i = 0; i < works.length; i++){
				if(works[i].rowid == rowid){
					works.splice(i, 1);
				}
			}
		},
		
		// 获取完成工作
		getWork : function(rowid){
			var this_ = this;
			var legs = this_.param.legs;
			var result = {};
			for (var i = 0; i < legs.length; i++) {
				var leg = legs[i];
				for (var j = 0; j < leg.finishedWorks.length; j++) {
		            var work = leg.finishedWorks[j];
		            if(work.rowid == rowid){
		            	result = work;
		            }
		        }
	        }
			return result;
		},
		
		// 获取航段-数据
		getLeg : function(hd){
			var this_ = this;
			var legs = this_.param.legs;
			var leg = {};
			for (var i = 0; i < legs.length; i++) {
	            if (legs[i].hd == hd) {
	            	leg = legs[i];
	            }
	        }
			return leg;
		},
		
		// 获取当前航段-jQuery对象
		getHd : function($obj){
			var div = $obj.parents("div[hd]");
			return div.attr("hd");
		},
		
		// 获取航段描述
		getHdms : function($obj){
			var div = $obj.parents("div[hd]");
			return div.attr("hdms");
		},
		
		// 获取所属div
		getContainer : function(hd){
			return $("#work_hd"+hd);
		},
			
		// 打开拆换记录弹窗
		openReplaceWin : function(obj, rowid, type){
			var this_ = this;
			type = type || "1";
			var hd = this_.getHd($(obj));
			var parentId = $(obj).parents("tr[parent]").attr("parent");
			var data = {};
			if(rowid){
				data = this_.getSubData(parentId, rowid)
			}else{
				var work = this_.getWork(parentId);
				data.cxBz = work.gdlx == 9 ? work.gzbg : work.gdbt;
			}
			var work = this_.getWork(parentId);
			open_win_installationlist_replace.show({
				data:data,//原值，在弹窗中默认勾选
				jx:flb_detail.getACType(),
				ope_type : type,
				defaultCzrq : work.wcrq,
				fjzch:flb_detail.getFjzch(),
				fdjsl:flb_detail.getEngCount(),
				dprtcode:flb_detail.getDprtcode(), //机构代码
				callback: function(data){//回调函数
					this_.mergeSubRow(hd, parentId, data);
					this_.mergeSubData(hd, parentId, data);
//					var record = flb_detail.record;
//					if(data.cxj && data.cxj.cj == 1){
//						var cxj = data.cxj;
//						record.refreshPreflightData(hd, cxj.wz, "", "", "off", data.cxSj, {});
//					}
//					if(data.zsj && data.zsj.cj == 1){
//						var zsj = data.zsj;
//						record.refreshPreflightData(hd, zsj.wz, zsj.bjh, zsj.xlh, "on", data.zsSj, data.zsj);
//					}
				}
			});
		},
		
		// 加载工单数据
		loadWOData : function(gdid, hd ,parentId){
			var this_ = this;
			startWait();
			AjaxUtil.ajax({
				url: basePath+"/produce/workorder/selectWOById",
				type: "post",
				dataType:"json",
				data:{gdid:gdid},
				success:function(data){
					finishWait();
					if(data.woIORecordList && data.woIORecordList.length > 0){
						$(data.woIORecordList || []).each(function(i, record){
							
							record.gdczjlid = record.id;
							record.sgbs = 0;
							delete record.id;
							
							// 添加子表
							var subRow = this_.getContainer(hd).find("tr[parent='"+parentId+"'] ");
							var $i = this_.getContainer(hd).find("tr[name='"+parentId+"'] i[name='collapse_icon']");
							if(subRow.length == 0){
								this_.addSubTable(hd, parentId);
								$i.removeClass("fa-angle-down").addClass("fa-angle-up");
							}
							
							// 分钟转小时
							if(record.zsj){
								var zsj = record.zsj;
								zsj.tsn = TimeUtil.convertToHour(zsj.tsn, TimeUtil.Separator.COLON);
								zsj.tso = TimeUtil.convertToHour(zsj.tso, TimeUtil.Separator.COLON);
								if(zsj.initDatas && zsj.initDatas.length > 0){
									$.each(zsj.initDatas, function(){
										var init = this;
										if(MonitorUnitUtil.isTime(init.jklbh)){
											init.csz = TimeUtil.convertToHour(init.csz, TimeUtil.Separator.COLON);
										}
									});
								}
							}
							
							// 添加子行数据
							this_.mergeSubRow(hd, parentId, record);
							this_.mergeSubData(hd, parentId, record);
						});
					}
			    }
			}); 
		},
		
		// 显示完成工作
		showFinishedWork : function(count){
			$("[hd='5'],[hd='7']").addClass("hidden");
			if(count > 2){
				$("[hd='5']").removeClass("hidden");
			}
			if(count > 3){
				$("[hd='7']").removeClass("hidden");
			}
		},
		
		// 选择工单
		chooseWorkOrder : function(target){
			var this_ = this;
			var hd = this_.getHd($(target));
			
			var existList = [];
			var legs = this_.param.legs;
			for (var i = 0; i < legs.length; i++) {
				var leg = legs[i];
				for (var j = 0; j < leg.finishedWorks.length; j++) {
		            var work = leg.finishedWorks[j];
		            existList.push(work.gdid);
		        }
	        }
			
			flb_workorder.show({
				existList : existList,//需要过滤的id集合
				dprtcode : flb_detail.getDprtcode(),
				fjzch : flb_detail.getFjzch(),
				hd : hd,
				hdms : this_.getHdms($(target)),
				callback : function(data){//回调函数
					if(data != null){
						$(data).each(function(){
							this_.mergeWorkRow(this);
							this_.mergeWork(this);
							this_.loadWOData(this.gdid, hd , this.rowid);
						});
					}
				}
			});
		},
		
		// 手工输入
		manual : function(target){
			var this_ = this;
			flb_manual.show({
				dprtcode : flb_detail.getDprtcode(),
				fjzch : flb_detail.getFjzch(),
				hd : this_.getHd($(target)),
				hdms : this_.getHdms($(target)),
				data : {},
				callback : function(data){//回调函数
					if(data != null){
						this_.mergeWorkRow(data);
						this_.mergeWork(data);
					}
				}
			});
		},
		
		// 修改完成工作
		edit : function(target, rowid){
			var this_ = this;
			var work = this_.getWork(rowid);
			flb_manual.show({
				dprtcode : flb_detail.getDprtcode(),
				fjzch : flb_detail.getFjzch(),
				hd : this_.getHd($(target)),
				hdms : this_.getHdms($(target)),
				data : work,
				callback : function(data){//回调函数
					if(data != null){
						this_.mergeWorkRow(data);
						this_.mergeWork(data);
					}
				}
			});
		},
		
		// 删除完成工作
		deleteWork : function(target, rowid){
			var this_ = this;
			var hd = this_.getHd($(target));
			AlertUtil.showConfirmMessage("是否确认删除完成工作？", {callback : function(){
				
				this_.deleteWorkData(hd, rowid);
				
				$container = this_.getContainer(hd);
				$container.find("tr[name='"+rowid+"']").remove();
				$container.find("tr[parent='"+rowid+"']").remove();
				var $tbody = $container.find("tbody[name='"+this_.workTbodyName+"']");
				var $trs = $tbody.find("tr");
				if($trs.length == 0){
					$tbody.html('<tr class="no-data"><td class="text-center" colspan="14">暂无数据 No data.</td></tr>');
				}
			}});
		},
		
		// 新增/修改完成工作
		mergeWorkRow : function(data){
			var this_ = this;
			var hd = data.hd;
			
			var gdlx = data.gdlx||(data.workorder?data.workorder.gdlx:"9");
			var gdlxContent = "";
			if(gdlx == 1){
				gdlxContent = DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum', data.paramsMap.wxxmlx);
			}else{
				gdlxContent = DicAndEnumUtil.getEnumName('workorderTypeEnum', gdlx);
			}
			
			var gzzList = '';
			$.each(data.workers || [], function(i, row){
				gzzList += formatUndefine(row.gzz) + ",";
			});
			if(gzzList != ''){
				gzzList = gzzList.substring(0,gzzList.length - 1);
			}
			
			if(!data.rowid){
				data.rowid = Math.uuid().toLowerCase();
				var flbZt = flb_detail.getStatus() || "1";
				var gdContent = "";
				if(data.sgbs == 1 && flbZt == "1"){
					gdContent = StringUtil.escapeStr(data.gdbh);
				}else{
					gdContent = '<a href="javascript:void(0);" onclick="flb_work.viewWorkOrder(\''+data.gdid+'\')">'+StringUtil.escapeStr(data.gdbh)+'</a>';
				}
				
				var html = ['<tr ondblclick="flb_work.edit(this,\''+data.rowid+'\')" name="'+data.rowid+'">',
				            	'<td class="text-center">',
				            		'<button class="line6 line6-btn notView" onclick="flb_work.toggleSubTable(\''+hd+'\',\''+data.rowid+'\')">',
				            			'<i class="fa fa-angle-down color-blue cursor-pointer" name="collapse_icon" title="收缩/展开"></i>',
		            				'</button>&nbsp;',
						            '<button class="line6 line6-btn notView" onclick="flb_work.edit(this,\''+data.rowid+'\')">',
						            	'<i class="icon-edit cursor-pointer color-blue" title="修改 Edit"></i>',
						            '</button>&nbsp;',
						            '<button class="line6 line6-btn notView" onclick="flb_work.deleteWork(this,\''+data.rowid+'\')">',
						            	'<i class="icon-minus cursor-pointer color-blue" title="删除 Delete"></i>',
						            '</button>&nbsp;',
					            '</td>',
					            '<td name="gdlx" class="text-center" title="'+gdlxContent+'" >'+gdlxContent+'</td>',
					            '<td name="gdbh" class="text-center" title="'+StringUtil.escapeStr(data.gdbh)+'" >',
					            	gdContent,
					            '</td>',
					            '<td name="rwxx" title="'+StringUtil.escapeStr(data.rwxx)+'" >'+StringUtil.escapeStr(data.rwxx)+'</td>',
					            '<td name="gzbg" title="'+StringUtil.escapeStr(data.gzbg)+'" >'+StringUtil.escapeStr(data.gzbg)+'</td>',
					            '<td name="gzxx" title="'+StringUtil.escapeStr(data.gzxx||(data.workorder?data.workorder.gzxx:""))+'" >'+StringUtil.escapeStr(data.gzxx||(data.workorder?data.workorder.gzxx:""))+'</td>',
					            '<td name="clcs" title="'+StringUtil.escapeStr(data.clcs||(data.workorder?data.workorder.clcs:""))+'" >'+StringUtil.escapeStr(data.clcs||(data.workorder?data.workorder.clcs:""))+'</td>',
					            '<td name="sjGs" title="'+(data.sjgs ? data.sjgs + "时" : "")+'" class="text-center">'+(data.sjgs ? data.sjgs + "时" : "")+'</td>',
					            '<td name="sjZd" title="'+StringUtil.escapeStr(data.sjZd)+'" >'+StringUtil.escapeStr(data.sjZd)+'</td>',
					            '<td name="sjGzz" title="'+StringUtil.escapeStr(gzzList)+'" >'+StringUtil.escapeStr(gzzList)+'</td>',
					            '<td name="sjJcz" title="'+StringUtil.escapeStr(data.zrr)+'" >'+StringUtil.escapeStr(data.zrr)+'</td>',
					            '<td name="wcrq" title="'+(data.wcrq||"").substr(0, 10)+'" class="text-center">'+(data.wcrq||"").substr(0, 10)+'</td>',
					            '<td name="wcsj" title="'+(data.wcrq||"").substr(11, 5)+'" class="text-center">'+(data.wcrq||"").substr(11, 5)+'</td>',
					            '<td name="wgfj" class="text-center">',
					            	data.attachments != null && data.attachments.length > 0 ? ('<i wgfjid="'+data.rowid+'"  class="attachment-view  glyphicon glyphicon glyphicon-list color-blue cursor-pointer " style="font-size:15px" ></i>') : '',
					            '</td>',
				            '</tr>'].join("");
				var $tbody = this_.getContainer(hd).find("tbody[name='"+this_.workTbodyName+"']");
				$tbody.children(".no-data").remove();
				$tbody.append(html);
			}else{
				var $tr = this_.getContainer(hd).find("tr[name='"+data.rowid+"']");
				$tr.find("td[name='gdlx']").text(gdlxContent);
				$tr.find("td[name='gdlx']").attr("title", gdlxContent);
				$tr.find("td[name='rwxx']").text(StringUtil.escapeStr(data.rwxx));
				$tr.find("td[name='rwxx']").attr("title",StringUtil.escapeStr(data.rwxx));
				$tr.find("td[name='gzbg']").text(StringUtil.escapeStr(data.gzbg));
				$tr.find("td[name='gzbg']").attr("title",StringUtil.escapeStr(data.gzbg));
				$tr.find("td[name='gzxx']").text(StringUtil.escapeStr(data.gzxx));
				$tr.find("td[name='gzxx']").attr("title",StringUtil.escapeStr(data.gzxx));
				$tr.find("td[name='clcs']").text(StringUtil.escapeStr(data.clcs));
				$tr.find("td[name='clcs']").attr("title",StringUtil.escapeStr(data.clcs));
				$tr.find("td[name='sjGzz']").text(StringUtil.escapeStr(gzzList));
				$tr.find("td[name='sjGzz']").attr("title",StringUtil.escapeStr(gzzList));
				$tr.find("td[name='sjJcz']").text(StringUtil.escapeStr(data.zrr));
				$tr.find("td[name='sjJcz']").attr("title",StringUtil.escapeStr(data.zrr));
				$tr.find("td[name='wcrq']").text((data.wcrq||"").substr(0, 10));
				$tr.find("td[name='wcrq']").attr("title",(data.wcrq||"").substr(0, 10));
				$tr.find("td[name='wcsj']").text((data.wcrq||"").substr(11, 5));
				$tr.find("td[name='wcsj']").attr("title",(data.wcrq||"").substr(11, 5));
				$tr.find("td[name='sjZd']").text(StringUtil.escapeStr(data.sjZd));
				$tr.find("td[name='sjZd']").attr("title",StringUtil.escapeStr(data.sjZd));
				$tr.find("td[name='sjGs']").text(StringUtil.escapeStr(data.sjgs));
				$tr.find("td[name='sjGs']").attr("title",StringUtil.escapeStr(data.sjgs));
				$tr.find("td[name='wgfj']").html(data.attachments != null && data.attachments.length > 0 ? ('<i wgfjid="'+data.rowid+'"  class="attachment-view  glyphicon glyphicon glyphicon-list color-blue cursor-pointer " style="font-size:15px" ></i>') : '');
			}
			this_.initWebuiPopover();
		},
		initWebuiPopover : function(){//初始化
			var this_ = this;
			var webuiPopoverParent = this_.param.webuiPopoverParent || "#open_win_flightlogbook_detail_modal";
			WebuiPopoverUtil.initWebuiPopover('attachment-view',webuiPopoverParent,function(obj){
				return this_.getHistoryAttachmentList(obj);
			});
		},
		getHistoryAttachmentList : function(obj){//获取历史附件列表
			var this_ = this;
			var work = this_.getWork($(obj).attr('wgfjid'));
			return this_.appendContentHtml(work.attachments);
		},
		// 拼接html
		appendContentHtml : function(list){
			var this_ = this;
			var htmlContent = '';
			var attachments = attachmentsUtil.getAttachmentsComponents().removeDeleteAttachment(list);
			$.each(attachments,function(index,row){
			   
				var fj = StringUtil.escapeStr(row.wbwjm);
				if(StringUtil.escapeStr(row.hzm) != ''){
					fj += "."+StringUtil.escapeStr(row.hzm);
				}
				htmlContent = htmlContent + "<tr>";
				htmlContent += "<td title='附件' class='text-center' style='padding-left:0px;'>附件</td>";
				htmlContent += '<td title="'+fj+'" class="text-left">';
				if(row.id){
					htmlContent += "<a href='javascript:void(0);' onclick=flb_work.downloadfile('"+row.id+"') >"+fj+"</a>";
				}else{
					htmlContent += fj;
				}
				htmlContent += '</td>';
				htmlContent += "</tr>"; 
			    
			});
			$("#history_list", $("#history_attach_alert_Modal")).empty();
			$("#history_list", $("#history_attach_alert_Modal")).html(htmlContent);
			
			return $("#history_attach_alert_Modal").html();
		},
		// 下载文件
		downloadfile : function(id){
			PreViewUtil.handle(id, PreViewUtil.Table.D_011);
		},
		// 显示子table
		toggleSubTable : function (hd, rowid){
			var this_ = this;
			var subRow = this_.getContainer(hd).find("tr[parent='"+rowid+"'] ");
			var $i = this_.getContainer(hd).find("tr[name='"+rowid+"'] i[name='collapse_icon']");
			if(subRow.length >= 1){
				if($i.hasClass("fa-angle-up")){
					$i.removeClass("fa-angle-up").addClass("fa-angle-down");
					subRow.hide();
				}else{
					$i.removeClass("fa-angle-down").addClass("fa-angle-up");
					subRow.show();
				}
			}else {
				this.addSubTable(hd, rowid);
				$i.removeClass("fa-angle-down").addClass("fa-angle-up");
			}
		},
		
		// 新增子table
		addSubTable : function(hd, rowid){
			var this_ = this;
			var previousRow = this_.getContainer(hd).find("tr[name='"+rowid+"']");
			var html = ['<tr parent="'+rowid+'">',
				            '<td style="vertical-align:middle;" class="text-center">拆装记录</td>',
				            '<td colspan="13">',
					            '<div class="col-xs-12 padding-left-0 padding-right-0"style="overflow-x: auto;">',
						            '<table class="table table-thin table-bordered text-center table-set" style="margin-bottom: 1px !important;">',
							            '<thead>',
								            '<tr>',
									            '<th class="colwidth-7" style="vertical-align:middle;">',
										            '<button type="button" onclick="flb_work.openReplaceWin(this)" class="line6 line6-btn notView">',
										           		'<i class="icon-plus cursor-pointer color-blue"></i>',
										            '</button>',
									            '</th>',
									            '<th class="colwidth-15">',
										            '<div class="font-size-12 line-height-18">拆下件号</div>',
										            '<div class="font-size-9 line-height-18">P/N</div>',
									            '</th>',
									            '<th class="colwidth-15">',
										            '<div class="font-size-12 line-height-18">拆下序号</div>',
										            '<div class="font-size-9 line-height-18">S/N</div>',
									            '</th>',
									            '<th class="colwidth-20">',
										            '<div class="font-size-12 line-height-18">名称</div>',
										            '<div class="font-size-9 line-height-18">Name</div>',
									            '</th>',
									            '<th class="colwidth-13">',
										            '<div class="font-size-12 line-height-18">拆下时间</div>',
										            '<div class="font-size-9 line-height-18">Time</div>',
									            '</th>',
									            '<th class="colwidth-15">',
										            '<div class="font-size-12 line-height-18">装上件号</div>',
										            '<div class="font-size-9 line-height-18">P/N</div>',
									            '</th>',
									            '<th class="colwidth-15">',
										            '<div class="font-size-12 line-height-18">装上序号</div>',
										            '<div class="font-size-9 line-height-18">S/N</div>',
									            '</th>',
									            '<th class="colwidth-20">',
										            '<div class="font-size-12 line-height-18">名称</div>',
										            '<div class="font-size-9 line-height-18">Name</div>',
									            '</th>',
									            '<th class="colwidth-13">',
										            '<div class="font-size-12 line-height-18">装上时间</div>',
										            '<div class="font-size-9 line-height-18">Time</div>',
									            '</th>',
									            '<th class="colwidth-7">',
										            '<div class="font-size-12 line-height-18">拆装位置</div>',
										            '<div class="font-size-9 line-height-18">Time</div>',
									            '</th>',
									            '<th class="colwidth-15">',
										            '<div class="font-size-12 line-height-18">拆装原因</div>',
										            '<div class="font-size-9 line-height-18">Reason</div>',
									            '</th>',
									            '<th class="colwidth-30">',
										            '<div class="font-size-12 line-height-18">装上件初始化信息</div>',
										            '<div class="font-size-9 line-height-18">Reason</div>',
									            '</th>',
								            '</tr>',
							            '</thead>',
							            '<tbody>',
							            	'<tr class="no-data"><td class="text-center" colspan="12">暂无数据 No data.</td></tr>',
							            '</tbody>',
						            '</table>',
					            '</div>',
				            '</td>',
			            '</tr>'].join("");
			previousRow.after(html);
		},
		
		// 新增/修改子table
		mergeSubRow : function(hd, rowid, data){
			var this_ = this;
			var $tbody = this_.getContainer(hd).find("tr[parent='"+rowid+"'] tbody");
			if(!data.rowid){
				data.rowid = Math.uuid().toLowerCase();
				var html = ['<tr name="'+data.rowid+'">',
				            	'<td class="text-center" style="vertical-align:middle;">',
						            data.sgbs == 1 ? '<button class="line6 line6-btn notView" onclick="flb_work.openReplaceWin(this, \''+data.rowid+'\')", \'2\'>' : '',
					            		data.sgbs == 1 ? '<i class="icon-edit cursor-pointer color-blue" title="修改 Edit"></i>' : '',
		            				data.sgbs == 1 ? '</button>&nbsp;' : '',
            						data.sgbs == 0 ? '<button class="line6 line6-btn" onclick="flb_work.openReplaceWin(this, \''+data.rowid+'\', \'3\')">' : '',
        								data.sgbs == 0 ? '<i class="icon-eye-open cursor-pointer color-blue" title="查看 View"></i>' : '',
									data.sgbs == 0 ? '</button>&nbsp;' : '',
						            '<button class="line6 line6-btn notView" onclick="flb_work.deleteSubRow(this, \''+data.rowid+'\')">',
						            	'<i class="icon-minus cursor-pointer color-blue" title="删除 Delete"></i>',
						            '</button>&nbsp;',
					            '</td>',
					            '<td name="cxbjh" class="text-left" title="'+StringUtil.escapeStr(data.cxj ? data.cxj.bjh : "")+'" >'+StringUtil.escapeStr(data.cxj ? data.cxj.bjh : "")+'</td>',
					            '<td name="cxxlh" class="text-left" title="'+StringUtil.escapeStr(data.cxj ? data.cxj.xlh : "")+'" >'+StringUtil.escapeStr(data.cxj ? data.cxj.xlh : "")+'</td>',
					            '<td name="cxmc" class="text-left" title="'+StringUtil.escapeStr(data.cxj ? ((data.cxj.ywmc||"") + " " + (data.cxj.zwmc||"")) : "")+'" >'+StringUtil.escapeStr(data.cxj ? ((data.cxj.ywmc||"") + " " + (data.cxj.zwmc||"")) : "")+'</td>',
					            '<td name="cxsj" class="text-center" title="'+(data.cxSj||"").substr(0, 16)+'" >'+(data.cxSj||"").substr(0, 16)+'</td>',
					            '<td name="zsbjh" class="text-left" title="'+StringUtil.escapeStr(data.zsj ? data.zsj.bjh : "")+'" >'+StringUtil.escapeStr(data.zsj ? data.zsj.bjh : "")+'</td>',
					            '<td name="zsxlh" class="text-left" title="'+StringUtil.escapeStr(data.zsj ? data.zsj.xlh : "")+'" >'+StringUtil.escapeStr(data.zsj ? data.zsj.xlh : "")+'</td>',
					            '<td name="zsmc" class="text-left" title="'+StringUtil.escapeStr(data.zsj ? ((data.zsj.ywmc||"") + " " + (data.zsj.zwmc||"")) : "")+'">'+StringUtil.escapeStr(data.zsj ? ((data.zsj.ywmc||"") + " " + (data.zsj.zwmc||"")) : "")+'</td>',
					            '<td name="zssj" class="text-center" title="'+(data.zsSj||"").substr(0, 16)+'">'+(data.zsSj||"").substr(0, 16)+'</td>',
					            '<td name="czwz" class="text-center" title="'+DicAndEnumUtil.getEnumName('installedPositionEnum', (data.cxj ? data.cxj.wz : (data.zsj ? data.zsj.wz : "11")))+'" >'+DicAndEnumUtil.getEnumName('installedPositionEnum', (data.cxj ? data.cxj.wz : (data.zsj ? data.zsj.wz : "11")))+'</td>',
					            '<td name="czyy" class="text-left" title="'+StringUtil.escapeStr(data.cxBz || data.zsBz || "")+'" >'+StringUtil.escapeStr(data.cxBz || data.zsBz || "")+'</td>',
					            '<td name="init" class="text-left" title="'+this_.getInitValueByJklbh(data.zsj ? data.zsj.initDatas : [])+'" >'+this_.getInitValueByJklbh(data.zsj ? data.zsj.initDatas : [])+'</td>',
				            '</tr>'].join("");
				$tbody.find(".no-data").remove();
				$tbody.append(html);
			}else{
				var $tr = this_.getContainer(hd).find("tr[name='"+data.rowid+"']");
				$tr.find("td[name='cxbjh']").text(StringUtil.escapeStr(data.cxj ? data.cxj.bjh : ""));
				$tr.find("td[name='cxbjh']").attr("title",StringUtil.escapeStr(data.cxj ? data.cxj.bjh : ""));
				$tr.find("td[name='cxxlh']").text(StringUtil.escapeStr(data.cxj ? data.cxj.xlh : ""));
				$tr.find("td[name='cxxlh']").attr("title",StringUtil.escapeStr(data.cxj ? data.cxj.xlh : ""));
				$tr.find("td[name='cxmc']").text(StringUtil.escapeStr(data.cxj ? ((data.cxj.ywmc||"") + " " + (data.cxj.zwmc||"")) : ""));
				$tr.find("td[name='cxmc']").attr("title",StringUtil.escapeStr(data.cxj ? ((data.cxj.ywmc||"") + " " + (data.cxj.zwmc||"")) : ""));
				$tr.find("td[name='zsbjh']").text(StringUtil.escapeStr(data.zsj ? data.zsj.bjh : ""));
				$tr.find("td[name='zsbjh']").attr("title",StringUtil.escapeStr(data.zsj ? data.zsj.bjh : ""));
				$tr.find("td[name='zsxlh']").text(StringUtil.escapeStr(data.zsj ? data.zsj.xlh : ""));
				$tr.find("td[name='zsxlh']").attr("title",StringUtil.escapeStr(data.zsj ? data.zsj.xlh : ""));
				$tr.find("td[name='zsmc']").text(StringUtil.escapeStr(data.zsj ? ((data.zsj.ywmc||"") + " " + (data.zsj.zwmc||"")) : ""));
				$tr.find("td[name='zsmc']").attr("title",StringUtil.escapeStr(data.zsj ? ((data.zsj.ywmc||"") + " " + (data.zsj.zwmc||"")) : ""));
				$tr.find("td[name='cxsj']").text((data.cxSj||"").substr(0, 16));
				$tr.find("td[name='cxsj']").attr("title",(data.cxSj||"").substr(0, 16));
				$tr.find("td[name='zssj']").text((data.zsSj||"").substr(0, 16));
				$tr.find("td[name='zssj']").attr("title",(data.zsSj||"").substr(0, 16));
				$tr.find("td[name='czwz']").text(DicAndEnumUtil.getEnumName('installedPositionEnum', (data.cxj ? data.cxj.wz : (data.zsj ? data.zsj.wz : "11"))));
				$tr.find("td[name='czwz']").attr("title",DicAndEnumUtil.getEnumName('installedPositionEnum', (data.cxj ? data.cxj.wz : (data.zsj ? data.zsj.wz : "11"))));
				$tr.find("td[name='czyy']").text(StringUtil.escapeStr(data.cxBz || data.zsBz || ""));
				$tr.find("td[name='czyy']").attr("title",StringUtil.escapeStr(data.cxBz || data.zsBz || ""));
				$tr.find("td[name='init']").text(this_.getInitValueByJklbh(data.zsj ? data.zsj.initDatas : []));
				$tr.find("td[name='init']").attr("title",this_.getInitValueByJklbh(data.zsj ? data.zsj.initDatas : []));
			}
		},
		
		// 删除字表行
		deleteSubRow : function(obj, rowid){
			var this_ = this;
			AlertUtil.showConfirmMessage("是否确认删除拆装记录？", {callback : function(){
				
				var hd = this_.getHd($(obj));
				var parentId = $(obj).parents("tr[parent]").attr("parent");
				this_.deleteSubData(hd, parentId, rowid);
				
				var $container = this_.getContainer(hd);
				$container.find("tr[name='"+rowid+"']").remove();
				var $tbody = $container.find("tr[parent='"+parentId+"'] tbody");
				var $trs = $tbody.find("tr");
				if($trs.length == 0){
					$tbody.html('<tr class="no-data"><td class="text-center" colspan="12">暂无数据 No data.</td></tr>');
				}
			}});
		},
		
		// 新增/修改子表数据
		mergeSubData : function(hd, rowid, data){
			var this_ = this;
			var disassemblies = this_.getWork(rowid).disassemblies || [];
			var insert = true;
			for(var i = 0; i < disassemblies.length; i++){
				if(disassemblies[i].rowid == data.rowid){
					disassemblies[i] = data;
					insert = false;
				}
			}
			if(insert){
				disassemblies.push(data);
			}
			this_.getWork(rowid).disassemblies = disassemblies;
		},
		
		// 删除字表数据
		deleteSubData : function(hd, parentId, rowid){
			var this_ = this;
			var disassemblies = this_.getWork(parentId).disassemblies || [];
			for(var i = 0; i < disassemblies.length; i++){
				if (disassemblies[i].rowid == rowid) {
					disassemblies.splice(i, 1);
	            }
			}
		},
		
		// 获取子表数据
		getSubData : function(parentId, rowid){
			var this_ = this;
			var result = {};
			if(parentId && rowid){
				var disassemblies = this_.getWork(parentId).disassemblies || [];
				for (var i = 0; i < disassemblies.length; i++) {
					if(disassemblies[i].rowid == rowid){
		            	result = disassemblies[i];
		            }
		        }
			}
			return result;
		},
		
		// 根据监控类编号获取初始化值
		getInitValueByJklbh : function(initDatas){
			var result = "";
			$(initDatas||[]).each(function(i, obj){
				if(obj.csz){
					if(MonitorUnitUtil.isCal(obj.jklbh)){
						result += obj.csz + " ";
					}else{
						result += obj.csz  + MonitorUnitUtil.getMonitorUnit(obj.jklbh) + " ";
					}
				}
			});
			return result;
		},
		
		// 新增防冰液行
		addAntiIceRow : function(obj){
			var this_ = this;
			var $tbody = $(obj).parents("div[hd]").find("tbody[name='"+this_.iceTbodyName+"']");
			$tbody.find(".no-data").remove();
			$tbody.append(['<tr>',
				               '<td class="text-center notView">',
					               '<button class="line6 line6-btn" onclick="flb_work.deleteAntiIceRow(this)">',
					               		'<i class="icon-minus cursor-pointer color-blue"></i>',
					               '</button>',
				               '</td>',
				               '<td style="text-align: left;vertical-align:middle;">',
				               		'<input class="form-control" type="text" maxlength="100" name="fbylx">',
				               '</td>',
				               '<td style="text-align: left;vertical-align:middle;">',
				               		'<input class="form-control" type="text" maxlength="100" name="fbdm">',
				               '</td>',
				               '<td style="text-align: left;vertical-align:middle;">',
				               		'<input class="form-control" type="text" name="kssj">',
				               '</td>',
				               '<td style="text-align: left;vertical-align:middle;">',
				               		'<input class="form-control" type="text" name="bcsj">',
				               '</td>',
			               '</tr>'].join(""));
			return $tbody.find("tr:last");
		},
		
		// 获取数据
		getData : function(){
			var this_ = this;
			var legs = this.param.legs;
			var flag = true;
			for(var i = 0; i < legs.length; i++){
				var leg = legs[i];
				
				var $container = $("div[hd='"+leg.hd+"']");
				leg.hdms = $container.attr("hdms");
				leg.hz = $.trim($container.find("input[name='work_hz']").val());
				leg.jcrid = $.trim($container.find("input[name='work_jcrid']").val());
				leg.jcr = $.trim($container.find("input[name='work_jcr']").val());
				for(var j = 0; j < leg.finishedWorks.length; j++){
					leg.finishedWorks[j].xc = j + 1;
					leg.finishedWorks[j].disassemblies = leg.finishedWorks[j].disassemblies || [];
				}
				var antiIces = [];
				$container.find("tbody[name='flb_anti_ice_table_list']>tr:not(.no-data)").each(function(i, tr){
					var $tr = $(tr);
					var obj = {};
					obj.xc = i + 1;
					obj.id = $.trim($tr.attr("name"));
					obj.fbylx = $.trim($tr.find("input[name='fbylx']").val());
					obj.fbdm = $.trim($tr.find("input[name='fbdm']").val());
					obj.kssj = $.trim($tr.find("input[name='kssj']").val());
					obj.bcsj = $.trim($tr.find("input[name='bcsj']").val());
					//检验开始时间长度
					var rwmsCount = this_.calRwmsCount(obj.kssj);
					if(rwmsCount > 20){
						AlertUtil.showModalFooterMessage("防冰液记录的开始时间的最大长度为20!");
						flag = false;
						return false;
					}
					//检验保持时间长度
					var rwmsCount = this_.calRwmsCount(obj.bcsj);
					if(rwmsCount > 20){
						AlertUtil.showModalFooterMessage("防冰液记录的保持时间的最大长度为20!");
						flag = false;
						return false;
					}
					antiIces.push(obj);
				});
				leg.antiIces = antiIces;
			}
			if(flag){
				return legs;
			}else{
				AlertUtil.showModalFooterMessage(message.substring(0,message.length-1));
				return false;
			}			
		},
		
		// 删除防冰液行
		deleteAntiIceRow : function(obj){
			var $tr = $(obj).parent().parent();
			var $tbody = $tr.parent();
			$tr.remove();
			if($tbody.find("tr:not(.no-data)").length == 0){
				$tbody.append('<tr class="no-data"><td class="text-center" colspan="5">暂无数据 No data.</td></tr>');
			}
		},
		
		// 查看工单
		viewWorkOrder : function(id){
			window.open(basePath + "/produce/workorder/woView?gdid=" + id);
		},
		
		// 打开放行人弹窗
		openFxrWin : function(obj){
			var this_ = this;
			var $container = $(obj).parents("div[hd]");
			Personel_Tree_Multi_Modal.show({
				checkUserList:[{id:$container.find("[name='work_jcrid']").val(),displayName:$container.find("[name='work_jcr']").val()}],//原值，在弹窗中回显
				dprtcode:flb_detail.getDprtcode(),
				multi:false,
				callback: function(data){//回调函数
					var user = $.isArray(data)?data[0]:{id:'',displayName:''};
					$container.find("[name='work_jcrid']").val(user.id);
					$container.find("[name='work_jcr']").val(user.displayName);
					this_.disableUser($container);
				}
			});
			AlertUtil.hideModalFooterMessage();
		},
		
		// 禁止输入用户
		disableUser : function($container){
			var user = $container.find("[name='work_jcrid']").val();
			if(user){
				$container.find("[name='work_jcr']").attr("readonly","readonly").addClass("readonly-style");
			}else{
				$container.find("[name='work_jcr']").removeAttr("readonly").removeClass("readonly-style");
			}
		},
		
		// 填充数据
		fillData : function(data){
			
			var this_ = this;
			data = data || [];
			if(data.length == 0){
				this_.initLeg();
			}else{
				this_.param.legs = data;
			}
			$("div[hd]").each(function(){
				var $container = $(this);
				var hd = $container.attr("hd");
				var obj = this_.getLeg(hd);
				$container.find("input[name='work_hz']").val(obj.hz);
				$container.find("input[name='work_jcrid']").val(obj.jcrid);
				$container.find("input[name='work_jcr']").val(obj.jcr);
				this_.disableUser($container);
				
				var $tbody_work = $container.find("tbody[name='"+this_.workTbodyName+"']");
				$tbody_work.html('<tr class="no-data"><td class="text-center" colspan="14">暂无数据 No data.</td></tr>');
				$(obj.finishedWorks || []).each(function(j, work){
					work.hd = obj.hd;
					this_.mergeWorkRow(work);
					
					this_.addSubTable(obj.hd, work.rowid);
					if(work.disassemblies && work.disassemblies.length > 0){
						var $i = $container.find("tr[name='"+work.rowid+"'] i[name='collapse_icon']");
						$i.removeClass("fa-angle-down").addClass("fa-angle-up");
						$(work.disassemblies).each(function(k, rec){
							if(rec.zsj){
								rec.zsj.chucrq = (rec.zsj.chucrq || "").substr(0, 10);
								rec.zsj.tsn = rec.zsj.tsn ? TimeUtil.convertToHour(rec.zsj.tsn, TimeUtil.Separator.COLON) : "";
								rec.zsj.tso = rec.zsj.tso ? TimeUtil.convertToHour(rec.zsj.tso, TimeUtil.Separator.COLON) : "";
								$(rec.zsj.initDatas || []).each(function(){
									var init = this;
									if(MonitorUnitUtil.isTime(init.jklbh)){
										init.csz = init.csz ? TimeUtil.convertToHour(init.csz, TimeUtil.Separator.COLON) : "";
									}
								});
							}
							this_.mergeSubRow(obj.hd, work.rowid, rec);
						});
					}else{
						var subRow = $container.find("tr[parent='"+work.rowid+"']");
						subRow.hide();
					}
				});
				
				var $tbody_ice = $container.find("tbody[name='"+this_.iceTbodyName+"']");
				$tbody_ice.html('<tr class="no-data"><td class="text-center" colspan="5">暂无数据 No data.</td></tr>');
				$(obj.antiIces || []).each(function(j, ice){
					$icetr = this_.addAntiIceRow($tbody_ice.find("tr:first"));
					$icetr.attr("name", ice.id);
					$icetr.find("input[name='fbylx']").val(ice.fbylx);
					$icetr.find("input[name='fbdm']").val(ice.fbdm);
					$icetr.find("input[name='kssj']").val(ice.kssj);
					$icetr.find("input[name='bcsj']").val(ice.bcsj);
				});
				
				if(obj.hd){
					$("[hd='"+hd+"']").removeClass("hidden");
				}else{
					$("[hd='"+hd+"']").addClass("hidden");
				}
			});
		},
		calRwmsCount:function(obj){
			var count = obj.replace(/[^\x00-\xff]/g,"012").length;							
			return count;
		},
	};
	
	