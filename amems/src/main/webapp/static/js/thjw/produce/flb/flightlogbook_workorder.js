
	
	/**
	 * 弹出窗验证销毁
	 */
	$(function(){
		$("#open_win_flightlogbook_workorder_modal").on("hidden.bs.modal", function() {
			AlertUtil.hideModalFooterMessage();
		});
	});
	
	var flb_workorder = {
			
		id : "open_win_flightlogbook_workorder_modal",
		
		allTbody : "flightlogbook_workorder_modal_tbody_n",
		
		selectTbody : "flightlogbook_workorder_modal_tbody_y",
		
		param: {
			selected:[],
			existList:[],
			callback:function(){},
			fjzch:"",
			hd:"",
			dprtcode:userJgdm//默认登录人当前机构代码
		},
		
		// 页面初始化
		show : function(param){
			$("#"+this.id).modal("show");
			if(param){
				$.extend(this.param, param);
			}
			$("#workorder_modal_zt").val("7");
			$("#workorder_modal_gb").val("");
			$("#workorder_modal_gd").val("");
			this.load();
		},
		
		
		// 加载工单工包数据
		load : function(){
			var this_ = this;
			startWait();
			AjaxUtil.ajax({
			   url:basePath+"/produce/workpackage/query4FLB",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify({
				   fjzch : this_.param.fjzch,
				   dprtcode : this_.param.dprtcode,
				   paramsMap : {
					   gb : $.trim($("#workorder_modal_gb").val()),
					   gd : $.trim($("#workorder_modal_gd").val()),
					   zt : $.trim($("#workorder_modal_zt").val()),
				   }
			   }),
			   success:function(data){
				   finishWait();
				   this_.setData(data);
				   this_.buildHtmlContent(data);
			   }
		   }); 
		},
		
		// 设置数据
		setData : function(data){
			var this_ = this;
			var all = [];
			$(data).each(function(){
				var gb = this;
				$(this.workOrders).each(function(){
					this.hd = this_.param.hd;
					this.hdms = this_.param.hdms;
					this.gbid = gb.id;
					this.gbbh = gb.gbbh;
					$(this.workers || []).each(function(){
						delete this.id;
					});
					all.push(this);
				});
			});
			this.param.all = all;
		},
		
		// 拼接工包html
		buildHtmlContent : function(data){
			var this_ = this;
			var html = "";
			var existList = this_.param.existList;
			var hasData = false;
			if(data.length > 0){
				$(data).each(function(i, pack){
					html += ['<tr bgcolor="#f9f9f9" name="'+pack.id+'" onclick="flb_workorder.rowonclick(event, this)" class="parentNode">',
					         '<td style="text-align:center;vertical-align:middle;">',
					         	'<input type="checkbox">',
					         '</td>',
					         '<td colspan="10">',
						         '<span><i class="fa fa-angle-up cursor-pointer" onclick="flb_workorder.collapse(\''+pack.id+'\')"></i></span>',
						         '<span class="badge margin-left-8" style="background:#3598db;" name="count">'+pack.workOrders.length+'</span>',
						         '<span class="margin-left-8">'+StringUtil.escapeStr(pack.gbbh + " " + pack.gbmc)+'</span>',
					         '</td>',
					         '</tr>'].join("");
					$(pack.workOrders).each(function(i2, order){
						if($.inArray(order.id, existList) == -1){
							hasData = true;
							var gdlx = "";
							if(order.gdlx == 1){
								gdlx = DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum', order.paramsMap.wxxmlx);
							}else{
								gdlx = DicAndEnumUtil.getEnumName('workorderTypeEnum', order.gdlx);
							}
							
							var gzzList = '';
							$.each(order.workers || [], function(i, row){
								gzzList += formatUndefine(row.gzz) + ",";
							});
							if(gzzList != ''){
								gzzList = gzzList.substring(0,gzzList.length - 1);
							}
							
							html += ['<tr parent="'+pack.id+'" name="'+order.id+'" onclick="flb_workorder.rowonclick(event, this)">',
							         '<td style="text-align:center;vertical-align:middle;">',
							         	'<input type="checkbox">',
							         '</td>',
							         '<td style="text-align:center;vertical-align:middle;">'+gdlx+'</td>',
							         '<td style="text-align:left;vertical-align:middle;">',
							         	'<a href="javascript:void(0);" onclick="flb_workorder.viewWorkOrder(\''+order.id+'\')">'+StringUtil.escapeStr(order.gdbh)+'</a>',
							         '</td>',
							         '<td style="text-align:left;vertical-align:middle;">'+StringUtil.escapeStr(order.gdbt)+'</td>',
							         '<td style="text-align:left;vertical-align:middle;">'+StringUtil.escapeStr(order.gzxx)+'</td>',
							         '<td style="text-align:left;vertical-align:middle;">'+StringUtil.escapeStr(order.clcs)+'</td>',
							         '<td style="text-align:center;vertical-align:middle;">'+(order.sjJsrq||"").substr(0, 10)+'</td>',
							         '<td style="text-align:center;vertical-align:middle;">'+(order.sjJsrq||"").substr(11, 5)+'</td>',
							         '<td style="text-align:center;vertical-align:middle;">'+StringUtil.escapeStr(order.sjGs)+'</td>',
							         '<td style="text-align:left;vertical-align:middle;">'+StringUtil.escapeStr(gzzList)+'</td>',
							         '<td style="text-align:left;vertical-align:middle;">'+StringUtil.escapeStr(order.sjJcz)+'</td>',
							         '</tr>'].join("");
						}
					});
				});
			}
			if(hasData){
				$("#"+this_.allTbody).html(html);
			}else{
				$("#"+this_.allTbody).html("<tr class='no-data'><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
			}
			$("#"+this_.selectTbody).html("<tr class='no-data'><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
			this_.refreshPackage();
			TableUtil.addTitle("#"+this_.allTbody+" tr td");
		},
		
		// 行点击
		rowonclick : function(event, tr){
			
			var checked = $(tr).find("input:checkbox").is(":checked");
			// 选中行
			if ($(event.target).attr("type") != "checkbox" &&
					!$(event.target).hasClass("fa")) {
				checked = !checked;
				$(tr).find("input:checkbox").attr("checked",checked);
			}
			
			// 选中子节点
			if($(tr).hasClass("parentNode")){
				$("#"+this.allTbody+" tr[parent='"+$(tr).attr("name")+"']:not(.remove) input:checkbox").attr("checked",checked);
			}
		},
		
		// 收缩
		collapse : function(parentName){
			var $i = $("#"+this.allTbody+" tr[name='"+parentName+"'] i");
			if($i.hasClass("fa-angle-up")){
				$i.removeClass("fa-angle-up").addClass("fa-angle-down");
				$("tr[parent='"+parentName+"']").hide("500").addClass("collapse");
			}else{
				$i.removeClass("fa-angle-down").addClass("fa-angle-up");
				$("tr[parent='"+parentName+"']").show("500").removeClass("collapse");
			}
		},
		
		// 选中
		select : function(){
			
			var this_ = this;
			$("#"+this_.allTbody+" input:checkbox:checked:not(.remove)").each(function(){
				
				var $tr = $(this).parents("#"+this_.allTbody+" tr");
				$tr.find("input:checkbox").attr("checked",false);
				// 移除暂无数据
				$("#"+this_.selectTbody+" .no-data").remove();
				// 已选择添加数据
				if(!$tr.hasClass("parentNode")){
					$("#"+this_.selectTbody).append($tr.prop("outerHTML"));
				}
				// 待选择删除行
				$tr.hide().addClass("remove");
			});
			
			if($("#"+this_.allTbody+" tr:visible").length == 0){
				$("#"+this_.allTbody).append("<tr class='no-data'><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
			}
			this_.refreshPackage();
		},
		
		// 移除
		remove : function(){
			
			var this_ = this;
			$("#"+this_.selectTbody+" input:checkbox:checked").each(function(){
				
				var $tr = $(this).parents("#"+this_.selectTbody+" tr");
				// 移除暂无数据
				$("#"+this_.allTbody+" .no-data").remove();
				// 待选择添加数据
				$("#"+this_.allTbody+" tr[name='"+$tr.attr("name")+"']").show().removeClass("remove");
				// 已选择删除行
				$tr.remove();
			});
			
			if($("#"+this_.selectTbody+" tr:visible").length == 0){
				$("#"+this_.selectTbody).append("<tr class='no-data'><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
			}
			this_.refreshPackage();
		},
		
		// 刷新工包的数量
		refreshPackage : function (){
			
			var this_ = this;
			$("#"+this_.allTbody+" tr.parentNode").each(function(){
				var $tr = $(this);
				var children = $("#"+this_.allTbody+" tr[parent='"+$tr.attr("name")+"']:not(.remove)");
				$tr.find("[name='count']").text(children.length);
				if(children.length > 0){
					$tr.show();
				}else{
					$tr.hide();
				}
			});
			
			$("#select_count").text($("#"+this_.selectTbody+" tr:not(.no-data)").length);
		},
		
		// 选中全部
		selectAll :function(){
			$("#"+this.allTbody+" tr:not(.remove) :checkbox").attr("checked", true);
		},
		
		// 取消全部
		cancelAll : function(){
			$("#"+this.allTbody+" tr:not(.remove) :checkbox").removeAttr("checked");
		},
		
		// 保存
		save: function(){
			var data = this.getData();
			if(data.length == 0){
				AlertUtil.showModalFooterMessage("请至少选择一个工单！", this.id);
				return false;
			}
			if(this.param.callback && typeof(this.param.callback) == "function"){
				this.param.callback(data);
			}
			$("#"+this.id).modal("hide");
		},
		
		// 获取数据
		getData : function(){
			var this_ = this;
			var data = [];
			$("#"+this_.allTbody+" input:checkbox:checked:not(.remove)").each(function(){
				var id = $(this).parents("#"+this_.allTbody+" tr").attr("name");
				$(this_.param.all).each(function(){
					if(id == this.id){
						var obj = {};
						obj.hd = this_.param.hd;
						obj.hdms = this_.param.hdms;
						obj.gdid = this.id;
						obj.gdlx = this.gdlx;
						obj.gdbh = this.gdbh;
						obj.rwxx = this.gdbt;
						obj.gzxx = this.gzxx;
						obj.clcs = this.clcs;
						obj.sjgs = this.sjGs;
						obj.sjZd = this.sjZd;
						obj.gzz = this.sjGzz;
						obj.zrr = this.sjJcz;
						obj.zrrid = this.sjJczid;
						obj.wcrq = this.sjJsrq;
						obj.sgbs = 0;
						obj.gbid = this.gbid;
						obj.gbbh = this.gbbh;
						obj.hsgs = this.hsgs;
						obj.workers = this.workers;
						obj.attachments = this.attachmentList;
						obj.paramsMap = this.paramsMap;
						data.push(obj);
					}
				});
			});
			return data;
		},
		
		// 查看工单
		viewWorkOrder : function(id){
			window.open(basePath + "/produce/workorder/woView?gdid=" + id);
		},
		
	};
	
	