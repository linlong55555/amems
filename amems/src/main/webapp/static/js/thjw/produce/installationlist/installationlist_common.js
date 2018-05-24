
	var InstallationlistCommon = function(param_){
		
		var certificateUtil = {};
			
		var param = {
			parentId : "",
			ope_type : 1,//1:新增,2:修改,3:查看
			isEdit : true,
			cer_width : "col-lg-11 col-md-11 col-md-11 col-sm-10",	// 证书宽度
			init_width_des : "col-lg-4 col-sm-4 col-xs-4",
			init_width_con : "col-lg-8 col-sm-8 col-xs-8",
			positionList : [],//飞机站位
			maintenanceItem : [],//维修项目
			data : {},//数据
			dprtcode:userJgdm,//默认登录人当前机构代码
			callback:function(){}//回调函数
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
			// 初始化宽度
			initWidth();
			// 初始化数据字典和枚举
			initDicAndEnum();
			// 初始化验证
			initValidation();
			// 初始化证书
			initCertificate();
			// 添加事件
			bindEvent();
			// 加载数据
			loadData();
		}
		
		// 通过name获取jquery对象
		function jQueryName(name){
			return $("#"+param.parentId+" [name='"+name+"']");
		}
		
		// 通过属性获取jquery对象
		function jQueryAttr(attr, value){
			return $("#"+param.parentId+" ["+attr+"='"+value+"']");
		}
		
		// 通过class获取jquery对象
		function jQueryClass(clazz){
			return $("#"+param.parentId+" ."+clazz);
		}
		
		// 初始化宽度
		function initWidth(){
			jQueryName("certificate_table_div").addClass(param.cer_width);
			jQueryClass("initDes").addClass(param.init_width_des);
			jQueryClass("initCon").addClass(param.init_width_con);
		}
		
		// 初始化输入框是否可编辑
		function initEditalbe(data){
			jQueryName("common_bjh").removeAttr("disabled");
			jQueryName("common_pch").removeAttr("disabled");
			jQueryName("common_xlh").removeAttr("disabled");
			jQueryName("common_wz").removeAttr("disabled");
			jQueryName("common_zjh_btn").removeClass("hidden");
			jQueryName("common_fjd_btn").removeClass("hidden");
			jQueryName("common_fjzw_btn").removeClass("hidden");
			jQueryName("common_azr_btn").removeClass("hidden");			
			jQueryName("common_fjd_display").removeAttr("disabled").addClass("readonly-style");
			
			if(data.id){
				if(data.id == '0'){
					jQueryName("common_zjh_btn").addClass("hidden");
					jQueryName("common_fjd_btn").addClass("hidden");
					jQueryName("common_fjzw_btn").addClass("hidden");
					jQueryName("common_azr_btn").addClass("hidden");					
				}
				
				if(data.wz != "11" && data.cj == 1){
					jQueryName("common_wz").attr("disabled","disabled");
					jQueryName("common_fjd_btn").addClass("hidden");
					jQueryName("common_fjd_display").attr("disabled","disabled").removeClass("readonly-style");
				}
				if(data.effective){
					jQueryName("common_bjh").attr("disabled","disabled");
					jQueryName("common_pch").attr("disabled","disabled");
					jQueryName("common_xlh").attr("disabled","disabled");
					jQueryName("common_wz").attr("disabled","disabled");
				}
			}
			
			if(param.ope_type == 3){
				jQueryName("common_bjh").attr("disabled","disabled");
				jQueryName("common_pch").attr("disabled","disabled");
				jQueryName("common_xlh").attr("disabled","disabled");
				jQueryName("common_wz").attr("disabled","disabled");
				jQueryName("common_fjd_btn").attr("disabled","disabled");
				jQueryName("common_fjd_display").attr("disabled","disabled");
			}
		}
		
		// 添加事件
		function bindEvent(){
			/*
			 * 单击事件
			 */
			jQueryName("common_zjh_btn").off("click").on("click", function(){
				// 打开章节号弹窗
				openChapterWin();
			});
			jQueryName("common_fjzw_btn").off("click").on("click", function(){
				// 打开飞机站位弹窗
				openStationWin();
			});
			jQueryName("common_fjd_btn").off("click").on("click", function(){
				// 打开上级部件弹窗
				openParentWin();
			});
			jQueryName("common_azr_btn").off("click").on("click", function(){
				// 打开安装人弹窗
				openInstallerWin();
			});
			jQueryName("common_ccr_btn").off("click").on("click", function(){
				// 打开拆除人弹窗
				openRemoverWin();
			});
			jQueryName("common_sfcx").off("click").on("click", function(){
				// 切换显示拆下信息输入框
				switchRemovedInput();
			});
			
			
			/*
			 * 双击事件
			 */
			jQueryName("common_zjh_display").off("dblclick").on("dblclick", function(){
				// 打开章节号弹窗
				openChapterWin();
			});
//			jQueryName("common_fjzw").off("dblclick").on("dblclick", function(){
//				// 打开飞机站位弹窗
//				openStationWin();
//			});
			jQueryName("common_fjd_display").off("dblclick").on("dblclick", function(){
				// 打开上级部件弹窗
				openParentWin();
			});
			jQueryName("common_azr_display").off("dblclick").on("dblclick", function(){
				// 打开安装人弹窗
				openInstallerWin();
			});
			jQueryName("common_ccr_display").off("dblclick").on("dblclick", function(){
				// 打开拆除人弹窗
				openRemoverWin();
			});
			
			/*
			 * 失焦事件
			 */
			jQueryName("common_bjh").off("blur").on("blur", function(){
				// 加载航材数据
				loadMaterialData();
				// 加载部件历史数据
				loadRecordData();
				// 加载维修项目
				loadMaintenanceItem();
				// 加载证书
				loadCertificate();
			});
			jQueryName("common_xlh").off("blur").on("blur", function(){
				// 加载部件历史数据
				loadRecordData();
				// 切换初始化值输入框是否显示
				switchInitInput();
				// 限制装机数量
				limitZjsl();
				// 重新验证初始化日期
				jQueryName('installation_form').data('bootstrapValidator')  
			     	.updateStatus("common_cal", 'NOT_VALIDATED',null);
				// 加载证书
				loadCertificate();
				// 加载维修项目
				loadMaintenanceItem();
			});
			jQueryName("common_pch").off("blur").on("blur", function(){
				// 加载证书
				loadCertificate();
			});
			
			/*
			 * 改变事件
			 */
			jQueryName("common_wz").off("change").on("change", function(){
				// 切换初始化值输入框是否显示
				switchInitInput();
			});
			jQueryName("common_chucrq").off("change").on("change", function(){
				// 计算CAL
				computeCal();
			});
			
			
			/*
			 * 键盘键入事件
			 */
			jQueryName("common_ccr_display").off("keyup").on("keyup", function(){
				if(jQueryName("common_ccr_display").val()){
					jQueryName("common_ccr_value").val("-");
				}
				reValidate("common_ccr_value");
			});
			jQueryClass("validate-time").off("keyup").on("keyup", function(){
				// 回退时间
				validateTime($(this));
			});
			jQueryClass("validate-cycle").off("keyup").on("keyup", function(){
				// 回退循环
				validateCycle($(this));
			});
		}
		
		// 初始化数据字典和枚举
		function initDicAndEnum(){
			
			buildWz();
			jQueryName("common_llklx").empty();
			jQueryName("common_jldw").empty();
			jQueryName("common_zjjlx").empty();
			DicAndEnumUtil.registerEnum("careercardtypeenum", param.parentId+" [name='common_llklx']");
			DicAndEnumUtil.registerDic("1", param.parentId+" [name='common_jldw']",installationlist.getDprtcode());
			
			DicAndEnumUtil.registerDic("52", param.parentId+" [name='common_zjjlx']",installationlist.getDprtcode());
			jQueryName("common_zjjlx").val("其他");
		}
		
		// 生成位置
		function buildWz(defaultWz){
			jQueryName("common_wz").empty();
			
			
			jQueryName("common_wz").append("<option value='11'>机身</option>");
			
			for(var i = 0; i < installationlist.getEngCount(); i++){
				jQueryName("common_wz").append("<option value='2"+(i+1)+"'>"+(i+1)+"#发</option>");
			}
			jQueryName("common_wz").append("<option value='31'>APU</option>");
			
			var wz = jQueryName("common_fjd_wz").val();
			if(wz){
				jQueryName("common_wz").find("option[value!='"+wz+"']").remove();
			}
			if(defaultWz){
				jQueryName("common_wz").val(defaultWz);
			}
		}
		
		// 打开章节号弹窗
		function openChapterWin(){
			FixChapterModal.show({
				selected:$.trim(jQueryName("common_zjh_value").val()),
				dprtcode:installationlist.getDprtcode(), //机构代码
				callback: function(data){//回调函数
					if(data != null && data.zjh != null){
						jQueryName("common_zjh_value").val(data.zjh);
						jQueryName("common_zjh_display").val(data.displayName);
					}else{
						jQueryName("common_zjh_value").val("");
						jQueryName("common_zjh_display").val("");
					}
/*					jQueryName('installation_form').data('bootstrapValidator')  
					     .updateStatus('common_zjh_value', 'NOT_VALIDATED',null)
					     .validateField('common_zjh_value');  */
				}
			});
		}
		
		// 加载航材数据
		function loadMaterialData(){
			AjaxUtil.ajax({
				url:basePath+"/material/material/selectByBjhAndDprcode",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify({
					bjh : jQueryName("common_bjh").val(),
					dprtcode : installationlist.getDprtcode()
				}),
				success:function(data){
					if(data.hCMainData){
						row = data.hCMainData;
						jQueryName('common_zwmc').val(row.zwms);
						jQueryName('common_ywmc').val(row.ywms);
						jQueryName('common_cjjh').val(row.cjjh);
						jQueryName('common_xh').val(row.xingh);
						jQueryName('common_jldw').val(row.jldw);
						
						jQueryName('common_zjh_display').val(row.fixChapter ? row.fixChapter.displayName : "");
						jQueryName('common_zjh_value').val(row.zjh);
					}
					
					finishWait();
					
			    }
			}); 
		}
		
		// 加载部件历史数据
		function loadRecordData(){
			var bjh = jQueryName("common_bjh").val();
			var xlh = jQueryName("common_xlh").val();
			if(bjh && xlh){
				AjaxUtil.ajax({
					url:basePath+"/aerialmaterial/component/findByJhAndXlh",
					type: "post",
					contentType:"application/json;charset=utf-8",
					dataType:"json",
					data:JSON.stringify({
						jh : bjh,
						xlh : xlh,
						dprtcode : installationlist.getDprtcode()
					}),
					success:function(data){
						if(data){
							jQueryName('common_llklx').val(data.llklx);
							jQueryName('common_llkbh').val(data.llkbh);
							jQueryName('common_bjgzjl').val(data.bjgzjl);
						}
				    }
				});
			}
		}
		
		// 打开飞机站位弹窗
		function openStationWin(){
			PositionModal.show({
//				selected : param.positionList,//原值，在弹窗中默认勾选
				dprtcode : installationlist.getDprtcode(),
				jx : installationlist.getACType(),
				callback : function(data){//回调函数
					var str = '';
					param.positionList = [];
					if(data != null && data.length > 0){
						$.each(data,function(index,row){
							param.positionList.push(row);
							str += formatUndefine(row.sz) + ",";
						});
					}
					if(str != ''){
						str = str.substring(0,str.length - 1);
					}
					jQueryName("common_fjzw").val(str);
				}
			});
		}
		
		// 打开上级部件弹窗
		function openParentWin(){
			installationlist_parent.show({
				selected : jQueryName("common_fjd_value").val(),//原值，在弹窗中默认勾选
				dprtcode : param.dprtcode,
				fjzch : installationlist.getFjzch(),
				wz : jQueryName("common_wz").val(),
				id : jQueryName("common_id").val(),
				effect : false,
				callback : function(data){//回调函数
					if(data != null){
						setParent(data);
						buildWz(jQueryName("common_wz").val());
						reValidate("common_fjd_value");
					}
				}
			});
		}
		
		// 设置父节点
		function setParent(data){
			jQueryName("common_fjd_value").val(data.id||"");
			jQueryName("common_fjd_display").val(data.bjh||"");
			jQueryName("common_fjd_cj").val(data.cj||"");
			jQueryName("common_fjd_wz").val(data.wz||"");
		}
		
		// 打开安装人弹窗
		function openInstallerWin(){
			Personel_Tree_Multi_Modal.show({
				checkUserList:[{id:jQueryName("common_azr_value").val(),displayName:jQueryName("common_azr_display").val()}],//原值，在弹窗中回显
				dprtcode:installationlist.getDprtcode(),
				multi:false,
				callback: function(data){//回调函数
					var user = $.isArray(data)?data[0]:{id:'',displayName:''};
					jQueryName("common_azr_value").val(user.id);
					jQueryName("common_azr_display").val(user.displayName);
					limitUserInput();
				}
			});
			AlertUtil.hideModalFooterMessage();
		}
		
		// 打开拆除人弹窗
		function openRemoverWin(){
			
			Personel_Tree_Multi_Modal.show({
				checkUserList:[{id:jQueryName("common_ccr_value").val(),displayName:jQueryName("common_ccr_display").val()}],//原值，在弹窗中回显
				dprtcode:installationlist.getDprtcode(),
				multi:false,
				callback: function(data){//回调函数
					var user = $.isArray(data)?data[0]:{id:'',displayName:''};
					jQueryName("common_ccr_value").val(user.id);
					jQueryName("common_ccr_display").val(user.displayName);
					limitUserInput();
					reValidate("common_ccr_value");
				}
			});
			AlertUtil.hideModalFooterMessage();
		}
		
		// 切换初始化值输入框是否显示
		function switchInitInput(){
			var xlh = jQueryName("common_xlh").val();
			var wz = jQueryName("common_wz").val();
			if(xlh){
				jQueryName("common_init_div").show("500");
				jQueryName("common_init_eh").hide();
				jQueryName("common_init_ec").hide();
				jQueryName("common_init_apuh").hide();
				jQueryName("common_init_apuc").hide();
				if(wz == 21 || wz == 22 || wz == 23 || wz == 24){
					jQueryName("common_init_eh").show();
					jQueryName("common_init_ec").show();
				}
				if(wz == 31){
					jQueryName("common_init_apuh").show();
					jQueryName("common_init_apuc").show();
				}
			}else{
				jQueryName("common_init_div").hide("500");
			}
		}
		
		// 限制装机数量
		function limitZjsl(){
			var xlh = jQueryName("common_xlh").val();
			if(xlh){
				jQueryName("common_zjsl").val("1");
				reValidate("common_zjsl");
				jQueryName('installation_form').data('bootstrapValidator')  
			    	.updateStatus("common_zjsl", 'NOT_VALIDATED',null); 
				jQueryName("common_zjsl").attr("disabled","disabled");
			}else{
				jQueryName("common_zjsl").removeAttr("disabled");
			}
			
			if(param.ope_type == 3){
				jQueryName("common_zjsl").attr("disabled","disabled");
			}
		}
		
		// 限制选择用户输入框
		function limitUserInput(){
			var azr = jQueryName("common_azr_value").val();
			if(azr && azr != "-"){
				jQueryName("common_azr_display").addClass("readonly-style").attr("disabled","disabled");
			}else{
				jQueryName("common_azr_display").removeClass("readonly-style").removeAttr("disabled");
			}
			
			var ccr = jQueryName("common_ccr_value").val();
			if(ccr && ccr != "-"){
				jQueryName("common_ccr_display").addClass("readonly-style").attr("disabled","disabled");
			}else{
				jQueryName("common_ccr_display").removeClass("readonly-style").removeAttr("disabled");
			}
		}
		
		// 切换显示拆下信息输入框
		function switchRemovedInput(){
			if(jQueryName("common_sfcx").is(":checked")){
				jQueryName("common_cxxx_div").show();
			}else{
				jQueryName("common_cxxx_div").hide();
			}
		}
		
		// 计算CAL
		function computeCal(){
			jQueryName("common_ccsj").text(dateMinus(jQueryName("common_chucrq").val()));
		}
		
		// 计算日期相减天数
		function dateMinus(sDate){
		  if(sDate){
			  var sdate = new Date(sDate.replace(/-/g, "/"));
			　　var now = new Date();
				now.setHours(0);
				now.setMinutes(0);
				now.setSeconds(0);
				now.setMilliseconds(0);
			　　var days = now.getTime() - sdate.getTime();
			　　var day = parseInt(days / (1000 * 60 * 60 * 24));
			　　return day;
		  }
		　　return "";
		}
		
		// 加载维修项目
		function loadMaintenanceItem(){
			var xlh = jQueryName("common_xlh").val();
			if(xlh){
				startWait();
				AjaxUtil.ajax({
					url: basePath+"/project2/maintenanceproject/applylist",
					type: "post",
					contentType:"application/json;charset=utf-8",
					dataType:"json",
					data:JSON.stringify({
						jx : installationlist.getACType(),
						fjzch : installationlist.getFjzch(),
						bjh : jQueryName("common_bjh").val(),
						dprtcode : installationlist.getDprtcode(),
						id : param.ope_type == 3 ? jQueryName("common_id").val() : ""
					}),
					success:function(data){
						finishWait();
						appendMaintenanceItemHtml(data);
						param.maintenanceItem = data;
				    }
				}); 
			}else{
				appendMaintenanceItemHtml([]);
				param.maintenanceItem = [];
			}
		}
		
		// 拼接维修项目
		function appendMaintenanceItemHtml(list){
			var $tbody = jQueryName("common_maintenance_list");
			
			var html = "";
			var skbs = 0;
			var ssbs = 0;
			if(list.length > 0){
				$(list).each(function(i, obj){
					if(obj.wxxmlx == 2){
						skbs = 1;
					}
					if(obj.wxxmlx == 3){
						ssbs = 1;
					}
					var monitorCount = obj.projectMonitors.length;
					$(obj.projectMonitors).each(function(i2, monitor){
						
						var scz = (monitor.scz||"") + convertUnit(monitor.jklbh, monitor.scz, monitor.dwScz);
						var zqz = (monitor.zqz||"") + convertUnit(monitor.jklbh, monitor.zqz, monitor.dwZqz);
						var rc = "-" + monitor.ydzQ + "/+" + monitor.ydzH + convertUnit(monitor.jklbh, monitor.ydzQ, monitor.ydzHdw);
						 	
						html += "<tr>";
						if(i2 == 0){
							html += "<td rowspan='"+monitorCount+"' style='text-align:center;vertical-align: middle;'>";
							html += "<a href='javascript:void(0);' onclick='viewMaintenanceItem(\""+obj.id+"\")'>"+StringUtil.escapeStr(obj.rwh)+"</a>";
							html += "</td>";
							html += "<td rowspan='"+monitorCount+"' style='text-align:center;vertical-align: middle;'>";
							html += "R" + obj.bb;
							html += "</td>";
							html += "<td rowspan='"+monitorCount+"' style='text-align:left;vertical-align: middle;' title='"+StringUtil.escapeStr(obj.rwms)+"'>";
							html += StringUtil.escapeStr(obj.rwms);
							html += "</td>";
							html += "<td style='text-align:center;border-left: none;border-right: none;border-bottom: none;'>"+MonitorUnitUtil.getMonitorName(monitor.jklbh)+"</td>";
							html += "<td style='text-align:center;border-left: none;border-right: none;border-bottom: none;'>"+scz+"</td>";
							html += "<td style='text-align:center;border-left: none;border-right: none;border-bottom: none;'>"+zqz+"</td>";
							html += "<td style='text-align:center;border-left: none;border-right: none;border-bottom: none;'>"+rc+"</td>";
						}else{
							html += "<td style='text-align:center;border: none;'>"+MonitorUnitUtil.getMonitorName(monitor.jklbh)+"</td>";
							html += "<td style='text-align:center;border: none;'>"+scz+"</td>";
							html += "<td style='text-align:center;border: none;'>"+zqz+"</td>";
							html += "<td style='text-align:center;border: none;'>"+rc+"</td>";
						}
						
						html += "</tr>";
					});
				});
			}else{
				html += "<tr><td class='text-center' colspan=\"7\">暂无数据 No data.</td></tr>";
			}
			
			jQueryName("common_skbs").val(skbs);
			jQueryName("common_ssbs").val(ssbs);
			$tbody.html(html);
			
			
			// 单位转换
			function convertUnit(jklbh, value, unit){
				if(value != null && value != "" && value != undefined){
					return MonitorUnitUtil.getMonitorUnit(jklbh, unit);
				}else{
					return "";
				}
			}
		}
		
		// 加载证书
		function loadCertificate(){
			certificateUtil.load({
				bjh : jQueryName("common_bjh").val(),
				xlh : jQueryName("common_xlh").val(),
				pch : jQueryName("common_pch").val(),
				dprtcode : installationlist.getDprtcode(),
			});
		}
		
		// 回退时间
		function validateTime($obj){
			backspace($obj, /^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/, true);
		}
		
		// 回退循环
		function validateCycle($obj){
			backspace($obj, /^(0|[1-9]\d*)$/);
		}
		
		// 回退
		function backspace(obj, reg, replace){
			var value = obj.val();
			if(replace){
				value = value.replace(/：/g, ":");
			}
			while(!(reg.test(value)) && value.length > 0){
				value = value.substr(0, value.length-1);
		    }
			obj.val(value);
			
			// 重新验证
			reValidate(obj);
		}
		
		// 初始化验证
		function initValidation(){
			jQueryName('installation_form').bootstrapValidator({
		        message: '数据不合法',
		        excluded: [':disabled'],
		        feedbackIcons: {
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		        	common_bjh: {
		                validators: {
		                    notEmpty: {
		                        message: '件号不能为空'
		                    },
		                    regexp: {
		                        regexp: /^[\x00-\xff]*$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		        	common_ywmc: {
		                validators: {
		                    notEmpty: {
		                        message: '英文名称不能为空'
		                    }
		                }
		            },
		            common_xlh: {
		                validators: {
		                	regexp: {
		                        regexp: /^[\x00-\xff]*$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            common_pch: {
		                validators: {
		                	regexp: {
		                        regexp: /^[\x00-\xff]*$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            common_cjjh: {
		                validators: {
		                	regexp: {
		                        regexp: /^[\x00-\xff]*$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            common_xh: {
		                validators: {
		                	regexp: {
		                        regexp: /^[\x00-\xff]*$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
/*		            common_zjh_value: {
		                validators: {
		                    notEmpty: {
		                        message: 'ATA章节号不能为空'
		                    }
		                }
		            }, */
		            common_zjsl: {
		                validators: {
		                    notEmpty: {
		                        message: '装机数量不能为空'
		                    },
		                    regexp: {
		                        regexp: /^([1-9]|([1-9]\d+))$/,
		                        message: '只能输入大于1的正整数'
		                    }
		                }
		            },
		            common_jldw: {
		                validators: {
		                    notEmpty: {
		                        message: '单位不能为空'
		                    }
		                }
		            },
		            common_fjzw: {
		                validators: {
		                	regexp: {
		                        regexp: /^[\x00-\xff]*$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            common_wz: {
		                validators: {
		                    notEmpty: {
		                        message: '分类不能为空'
		                    }
		                }
		            },
		            common_fjd_value: {
		                validators: {
		                    notEmpty: {
		                        message: '上级部件不能为空'
		                    }
		                }
		            },
		            common_llkbh: {
		                validators: {
		                	regexp: {
		                        regexp: /^[\x00-\xff]*$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            common_cal: {
		            	validators: {
		                	callback: {
		                        message: '初始化日期不能为空',
		                        callback: function(value, validator) {
		                        	if(!jQueryName('common_xlh').val()){
		                        		return true;
		                        	}
		                        	if(value){
		                        		return true;
		                        	}
		                        	return false;
		                        }
		                    }
		                }
		            },
		            common_fh: {
		                validators: {
		                	regexp: {
		                        regexp: /^(0|[1-9]\d*)(\:[0-5]?[0-9])?$/,
		                        message: '时间格式不正确'
		                    }
		                }
		            },
		            common_fc: {
		                validators: {
		                	regexp: {
		                        regexp: /^(0|[1-9]\d*)$/,
		                        message: '循环格式不正确'
		                    }
		                }
		            },
		            common_eh: {
		                validators: {
		                	regexp: {
		                        regexp: /^(0|[1-9]\d*)(\:[0-5]?[0-9])?$/,
		                        message: '时间格式不正确'
		                    }
		                }
		            },
		            common_ec: {
		                validators: {
		                	regexp: {
		                        regexp: /^(0|[1-9]\d*)$/,
		                        message: '循环格式不正确'
		                    }
		                }
		            },
		            common_apuh: {
		                validators: {
		                	regexp: {
		                        regexp: /^(0|[1-9]\d*)(\:[0-5]?[0-9])?$/,
		                        message: '时间格式不正确'
		                    }
		                }
		            },
		            common_apuc: {
		                validators: {
		                	regexp: {
		                        regexp: /^(0|[1-9]\d*)$/,
		                        message: '循环格式不正确'
		                    }
		                }
		            },
		            common_tsn: {
		                validators: {
		                	regexp: {
		                        regexp: /^(0|[1-9]\d*)(\:[0-5]?[0-9])?$/,
		                        message: '时间格式不正确'
		                    }
		                }
		            },
		            common_tso: {
		                validators: {
		                	regexp: {
		                        regexp: /^(0|[1-9]\d*)(\:[0-5]?[0-9])?$/,
		                        message: '时间格式不正确'
		                    }
		                }
		            },
		            common_csn: {
		                validators: {
		                	regexp: {
		                        regexp: /^(0|[1-9]\d*)$/,
		                        message: '循环格式不正确'
		                    }
		                }
		            },
		            common_cso: {
		                validators: {
		                	regexp: {
		                        regexp: /^(0|[1-9]\d*)$/,
		                        message: '循环格式不正确'
		                    }
		                }
		            },
		            common_azjldh: {
		                validators: {
		                	regexp: {
		                        regexp: /^[\x00-\xff]*$/,
		                        message: '不能输入中文'
		                    }
		                }
		            },
		            common_azrq: {
		            	validators: {
		                    notEmpty: {
		                        message: '安装日期不能为空'
		                    }
		                }
		            },
		            common_ccjldh: {
		                validators: {
		                	regexp: {
		                        regexp: /^[\x00-\xff]*$/,
		                        message: '不能输入中文'
		                    },
		                    callback: {
		                        message: '拆下记录单号不能为空',
		                        callback: function(value, validator) {
		                        	if(jQueryName("common_sfcx").is(":checked") && !value){
		                        		return false;
		                        	}
		                        	return true;
		                        }
		                    }
		                }
		            },
		            common_cxrq: {
		                validators: {
		                    callback: {
		                        message: '拆下日期不能为空',
		                        callback: function(value, validator) {
		                        	if(jQueryName("common_sfcx").is(":checked") && !value){
		                        		return false;
		                        	}
		                        	return true;
		                        }
		                    }
		                }
		            },
		            common_ccr_value: {
		                validators: {
		                    callback: {
		                        message: '拆下人不能为空',
		                        callback: function(value, validator) {
		                        	if(jQueryName("common_sfcx").is(":checked") && !value){
		                        		return false;
		                        	}
		                        	return true;
		                        }
		                    }
		                }
		            },
		        }
		    });
			
			
			jQueryName('common_cal').datepicker({
				 autoclose: true,
				 clearBtn:true
			}).on('hide', function(e) {
				reValidate("common_cal");
		    });
			
			jQueryName('common_azrq').datepicker({
				 autoclose: true,
				 clearBtn:true
			}).on('hide', function(e) {
				reValidate("common_azrq");
		    });
			
			jQueryName('common_cxrq').datepicker({
				 autoclose: true,
				 clearBtn:true
			}).on('hide', function(e) {
				reValidate("common_cxrq");
		    });
		}
		
		// 重新验证
		function reValidate(name){
			jQueryName('installation_form').data('bootstrapValidator')  
		     .updateStatus(name, 'NOT_VALIDATED',null)
		     .validateField(name); 
		}
		
		// 验证数据
		function validateData(){
			AlertUtil.hideModalFooterMessage();
			jQueryName('installation_form').data('bootstrapValidator').resetForm(false);
			jQueryName('installation_form').data('bootstrapValidator').validate();
			if(!jQueryName('installation_form').data('bootstrapValidator').isValid()){
				AlertUtil.showModalFooterMessage("请根据提示输入正确的信息！");
				return false;
			}
//			if(param.positionList && param.positionList.length > 8){
//				AlertUtil.showModalFooterMessage("最多选择8个飞机站位！");
//				return false;
//			}
			
			if(!validateTime(jQueryName("common_azsj").val())){
				AlertUtil.showModalFooterMessage("安装日期时间不正确！");
				return false;
			}
			if(!validateTime(jQueryName("common_cxsj").val())){
				AlertUtil.showModalFooterMessage("拆下日期时间不正确！");
				return false;
			}
			
			var azrq = jQueryName("common_azrq").val();
			var cxrq = jQueryName("common_sfcx").is(":checked") ? jQueryName("common_cxrq").val() : "";
			if(azrq && cxrq){
				var azsj = jQueryName("common_azsj").val() || "00:00";
				var cxsj = jQueryName("common_cxsj").val() || "00:00";
				if(!TimeUtil.compareDate(cxrq + " " + cxsj, azrq + " " + azsj)){
					if($("#installation_modify_modal").is(":hidden")){
						AlertUtil.showMessage("拆下日期不能小于等于安装日期！");
					}else{
						AlertUtil.showModalFooterMessage("拆下日期不能小于等于安装日期！");
					}				
					return false;
				}
			}
			
			return true;
			
			function validateTime(str){
				if(str){
					var hour = parseInt(str.split(":")[0]);
					var minute = parseInt(str.split(":")[1]);
					if(hour > 23 || minute > 60){
						return false;
					}
				}
				return true;
			}
		}
		
		// 弱验证
		function weakValidate(obj){
			// 验证维修项目中如果有发动机监控或者APU监控，那么需要提醒该部件的分类需要是发动机或者APU
			var maintenanceItem = param.maintenanceItem;
			var apuFlag = false;
			var engFlag = false;
			var wz = jQueryName('common_wz').val();
			if(maintenanceItem.length > 0){
				$(maintenanceItem).each(function(i, obj){
					$(obj.projectMonitors).each(function(i2, monitor){
						if((monitor.jklbh == "2_20_AH" || monitor.jklbh == "3_20_AC") &&
								wz != "31"){
							apuFlag = true;
						}
						 
						if((monitor.jklbh == "2_30_EH" || monitor.jklbh == "3_30_EC") &&
								wz != "21" && wz != "22" && wz != "23" && wz != "24"){
							engFlag = true;
						}
					});
				});
				if(apuFlag){
					obj.weakMsg = "由于适用维修项目包含APU的监控，部件的分类为"+DicAndEnumUtil.getEnumName('installedPositionEnum', (wz))+"，是否确定执行？";
				}else if(engFlag){
					obj.weakMsg = "由于适用维修项目包含发动机的监控，部件的分类应为"+DicAndEnumUtil.getEnumName('installedPositionEnum', (wz))+"，是否确定执行？";
				}
			}
		}
		
		// 获取表单
		function getForm(){
			return jQueryName('installation_form');
		}
		
		// 获取装机清单数据
		function getInstallation(){
			if(!validateData()){
				return null;
			}
			var obj = {};
			weakValidate(obj);
			var paramsMap = {};
			var initDatas = [];
			obj.dprtcode = $.trim(installationlist.getDprtcode());
			obj.jx = $.trim(installationlist.getACType());
			obj.fjzch = $.trim(installationlist.getFjzch());
			obj.id = $.trim(jQueryName("common_id").val());
			obj.bjh = $.trim(jQueryName("common_bjh").val());
			obj.ywmc = $.trim(jQueryName("common_ywmc").val());
			obj.zwmc = $.trim(jQueryName("common_zwmc").val());
			obj.xlh = $.trim(jQueryName("common_xlh").val());
			obj.pch = $.trim(jQueryName("common_pch").val());
			obj.cjjh = $.trim(jQueryName("common_cjjh").val());
			obj.zjh = $.trim(jQueryName("common_zjh_value").val());
			obj.xh = $.trim(jQueryName("common_xh").val());
			obj.zjsl = $.trim(jQueryName("common_zjsl").val());
			obj.jldw = $.trim(jQueryName("common_jldw").val());
			obj.wz = $.trim(jQueryName("common_wz").val());
			obj.fjdid = $.trim(jQueryName("common_fjd_value").val());
			obj.cj = parseInt(jQueryName("common_fjd_cj").val()) + 1;
			obj.llklx = $.trim(jQueryName("common_llklx").val());
			obj.llkbh = $.trim(jQueryName("common_llkbh").val());
			obj.bjgzjl = $.trim(jQueryName("common_bjgzjl").val());
			obj.zjjlx = $.trim(jQueryName("common_zjjlx").val());
			obj.skbs = $.trim(jQueryName("common_skbs").val());
			obj.ssbs = $.trim(jQueryName("common_ssbs").val());
			if(obj.xlh){
				initDatas.push(generateInitData("common_cal"));
				initDatas.push(generateInitData("common_fh"));
				initDatas.push(generateInitData("common_fc"));
				if(obj.wz == 21 || obj.wz == 22 || obj.wz == 23 || obj.wz == 24){
					initDatas.push(generateInitData("common_eh"));
					initDatas.push(generateInitData("common_ec"));
				}
				if(obj.wz == 31){
					initDatas.push(generateInitData("common_apuh"));
					initDatas.push(generateInitData("common_apuc"));
				}
			}
			
			obj.chucrq = $.trim(jQueryName("common_chucrq").val());
			obj.tsn = $.trim(jQueryName("common_tsn").val());
			obj.tso = $.trim(jQueryName("common_tso").val());
			obj.csn = $.trim(jQueryName("common_csn").val());
			obj.cso = $.trim(jQueryName("common_cso").val());
			obj.azjldh = $.trim(jQueryName("common_azjldh").val());
			paramsMap.azrq = $.trim(jQueryName("common_azrq").val());
			paramsMap.azsj = $.trim(jQueryName("common_azsj").val());
			obj.azrid = $.trim(jQueryName("common_azr_value").val());
			obj.azr = $.trim(jQueryName("common_azr_display").val());
			if(jQueryName("common_sfcx").is(":checked")){
				obj.zjzt = 2;
				obj.ccjldh = $.trim(jQueryName("common_ccjldh").val());
				paramsMap.ccrq = $.trim(jQueryName("common_cxrq").val());
				paramsMap.ccsj = $.trim(jQueryName("common_cxsj").val());
				obj.ccrid = $.trim(jQueryName("common_ccr_value").val());
				if(obj.ccrid == "-"){
					obj.ccrid = "";
				}
				obj.ccr = $.trim(jQueryName("common_ccr_display").val());
			}else{
				obj.ccjldh = "";
				paramsMap.ccrq = "";
				paramsMap.ccsj = "";
				obj.ccrid = "";
				obj.ccr = "";
				obj.zjzt = 1;
			}
			
			obj.bz = $.trim(jQueryName("common_bz").val());
			
//			obj.fjzw = $(param.positionList).map(function(){
//				return this.sz;
//			}).get().join(",");
			obj.fjzw = $.trim(jQueryName("common_fjzw").val());
			obj.paramsMap = paramsMap;
			obj.initDatas = initDatas;
			obj.certificates = certificateUtil.getData();
			return obj;
			
			// 生成初始化数据
			function generateInitData(name){
				var row = jQueryName(name);
				return {
					jklbh : $.trim(row.attr("jklbh")),
					jkflbh : $.trim(row.attr("jkflbh")),
					csz : $.trim(row.val()),
				};
			}
		}
		
		// 初始化证书
		function initCertificate(){
			certificateUtil = new CertificateUtil({
				parentId : param.parentId,
				dprtcode : installationlist.getDprtcode(),
				tbody : jQueryName("installationlist_certificate_list"),
				container : param.container,
				ope_type : param.ope_type
			});
		}
		
		// 加载数据
		function loadData(id){
			var id = id || param.dataId;
			if(id){
				if(id == "0"){
					
					fillData({
						id : "0",
						bjh : installationlist.getFjzch(),
						xlh : installationlist.getMSN()
					});
					
				}else if(id != "011" && id != "021" && id != "022" && id != "023" && id != "024" && id != "031"){
					startWait();
					
					var url = basePath+"/aircraftinfo/installationlist/detail";
					if(param.data_type == "effect"){
						url = basePath+"/aircraftinfo/installationlist/effectdetail";
					}
					AjaxUtil.ajax({
						url: url,
						type: "post",
						contentType:"application/json;charset=utf-8",
						dataType:"json",
						data:JSON.stringify({
							id : id,
						}),
						success:function(data){
							finishWait();
							fillData(data);
					    }
					}); 
				}
			}else{
				fillData({});
			}
		}
		
		/**
		 * 填充数据
		 * @param data
		 */
		function fillData(obj){
			
			if(param.ope_type == 3){
				$("#fjzch").val(obj.fjzch);
			}
			jQueryName("common_id").val(obj.id);
			jQueryName("common_bjh").val(obj.bjh);
			jQueryName("common_ywmc").val(obj.ywmc);
			jQueryName("common_zwmc").val(obj.zwmc);
			jQueryName("common_xlh").val(obj.xlh);
			jQueryName("common_pch").val(obj.pch);
			jQueryName("common_cjjh").val(obj.cjjh);
			jQueryName("common_zjh_value").val(obj.zjh);
			jQueryName("common_zjh_display").val(obj.fixChapter ? obj.fixChapter.displayName : "");
			jQueryName("common_xh").val(obj.xh);
			jQueryName("common_zjsl").val(obj.zjsl);
			jQueryName("common_jldw").val(obj.jldw);
			jQueryName("common_zjjlx").val(obj.zjjlx || "其他");
			if(obj.fjdid == 0){
				obj.parent = {
					id : "0",
					bjh : "N/A",
					cj : "0",
					wz : obj.wz
				}
			}
			jQueryName("common_fjd_value").val(obj.parent ? obj.parent.id : "");
			jQueryName("common_fjd_display").val(obj.parent ? obj.parent.bjh : "");
			jQueryName("common_fjd_cj").val(obj.parent ? obj.parent.cj : "");
			jQueryName("common_fjd_wz").val(obj.parent ? obj.parent.wz : "");
			buildWz();
			jQueryName("common_wz").val(obj.wz);
			jQueryName("common_llklx").val(obj.llklx);
			jQueryName("common_llkbh").val(obj.llkbh);
			jQueryName("common_bjgzjl").val(obj.bjgzjl);
			jQueryName("common_ssbs").val(obj.ssbs);
			jQueryName("common_skbs").val(obj.skbs);
			
			
			jQueryName("common_cal").val("");
			jQueryName("common_fh").val("");
			jQueryName("common_fc").val("");
			jQueryName("common_eh").val("");
			jQueryName("common_ec").val("");
			jQueryName("common_apuh").val("");
			jQueryName("common_apuc").val("");
			jQueryName("common_init_div").find("input[jklbh]").val("");
			if(obj.initDatas){
				$.each(obj.initDatas, function(i, init){
					jQueryAttr("jklbh", init.jklbh).val(init.csz||init.zssyy);
				});
			}
			
			jQueryName("common_chucrq").val((obj.chucrq||"").substr(0, 10));
			jQueryName("common_tsn").val(obj.tsn);
			jQueryName("common_tso").val(obj.tso);
			jQueryName("common_csn").val(obj.csn);
			jQueryName("common_cso").val(obj.cso);
			jQueryName("common_azjldh").val(obj.azjldh);
			jQueryName("common_azrq").val(obj.azsj ? obj.azsj.split(" ")[0] : "");
			jQueryName("common_azsj").val(obj.azsj ? obj.azsj.split(" ")[1].substr(0, 5) : "");
			jQueryName("common_azr_value").val(obj.azrid);
			jQueryName("common_azr_display").val(obj.azr);
			
			if(obj.zjzt == 2){
				jQueryName("common_sfcx").attr("checked","checked");
			}else{
				jQueryName("common_sfcx").removeAttr("checked");
			}
			
			jQueryName("common_ccjldh").val(obj.ccjldh);
			jQueryName("common_cxrq").val(obj.ccsj ? obj.ccsj.split(" ")[0] : "");
			jQueryName("common_cxsj").val(obj.ccsj ? obj.ccsj.split(" ")[1].substr(0, 5) : "");
			jQueryName("common_ccr_display").val(obj.ccr);
			if(obj.ccr){
				if(obj.ccrid){
					jQueryName("common_ccr_value").val(obj.ccrid);
				}else{
					jQueryName("common_ccr_value").val("-");
				}
			}
			
			jQueryName("common_bz").val(obj.bz);
			
//			param.positionList = obj.stations;
//			var str = "";
//			if(obj.stations != null && obj.stations.length > 0){
//				$.each(obj.stations,function(row){
//					str += formatUndefine(row.sz) + ",";
//				});
//			}
//			if(str != ''){
//				str = str.substring(0,str.length - 1);
//			}
			jQueryName("common_fjzw").val(obj.fjzw);
			
			// 刷新时间控件
			$("#"+param.parentId+" .date-picker").datepicker("update");
			
			// 新增下拉框默认第一个选中
			if(!obj.id){
				jQueryName("common_jldw").find("option:first").attr("selected", 'selected'); 
				jQueryName("common_wz").find("option:first").attr("selected", 'selected'); 
				jQueryName("common_llklx").find("option:first").attr("selected", 'selected'); 
			}
			
			// 初始化输入框是否可编辑
			initEditalbe(obj);
			// 加载维修项目
			loadMaintenanceItem();
			// 加载证书
			loadCertificate();
			// 切换初始化值输入框是否显示
			switchInitInput();
			// 限制装机数量
			limitZjsl();
			// 限制选择用户输入框
			limitUserInput();
			// 计算CAL
			computeCal();
			// 切换显示拆下信息输入框
			switchRemovedInput();
			
			if(param.callback && typeof(param.callback) == "function"){
				param.callback({});
			}
		}
		
		return {
			getInstallation : getInstallation,
			getForm : getForm,
			loadData : loadData,
			jQueryName : jQueryName,
			setParent : setParent,
			buildWz : buildWz
		};
	};
	
	// 维修项目查看
	function viewMaintenanceItem(id){
		window.open(basePath + "/project2/maintenanceproject/view?id=" + id);
	}
	
	