

	var FlbRecord = function(param_){
			
		var tableName = "flb_record_table";
		
		var tbodyName = "flb_record_table_list";
		
		var notCalcColumnList = ['hbh','fxrwlx','qfz','zlz','kcsj','qfsj','ldsj','tcsj','jz','rydw'];
		
		var calcPreColumnList = ['fxsj','fxxh','f1sj','f1xh','f2sj','f2xh','f3sj','f3xh','f4sj','f4xh','apusj','apuxh'];
		
		var param = {
			parentId : "",
			ope_type : 1,//1:新增,2:修改,3:查看
			win_type : 'detail',//页面类型
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
		
		// 初始化
		function init(){
			
			// 选择列高亮
			initHover();
			
			// 初始化控件
			initWidget();
			
			// 初始化数据字典和枚举
			initDicAndEnum();
			
			// 绑定事件
			bindEvent()
		}
		
		// 绑定事件
		function bindEvent(){
			
			/*
			 * 点击事件
			 */
			jQueryName("flb_record_table_add_btn").off("click").on("click", function(){
				// 新增行
				addRow();
			});
			jQueryName("flb_record_table_del_btn").off("click").on("click", function(){
				// 删除行
				deleteRow();
			});
			jQueryName("jz_btn").off("click").on("click", function(){
				// 打开机长弹窗
				openJzWin(this);
			});
			
			/*
			 * 双击事件
			 */
			jQueryName("jzstr").off("dblclick").on("dblclick", function(){
				// 打开机长弹窗
				openJzWin(this);
			});
			
			/*
			 * 失焦事件
			 */
			
			getNotCalcColumn().off("blur").on("blur", function(){
				// 计算飞行数据
				calc($(this).parent().attr("name"));
			});
			
			jQueryClass("time-masked").find("input").off("blur").on("blur", function(){
				// 验证日期时间
				validateDateTime($(this));
			});
			
			jQueryName(tableName)
				.find("td[name='kcsj']>input").on("blur", function(){
				// 加载飞行前数据
				loadPreflightData();
			});
			
			jQueryName(tableName)
				.find("td[name='kcsj']>input,td[name='qfsj']>input,td[name='ldsj']>input,td[name='tcsj']>input").on("blur", function(){
					var fxrq = $.trim($("#common_fxrq").val());
					var $tr = $(this).parent().parent("tr");
					var	kcsj = buildFlightDate(fxrq, $tr.find("td[name='kcsj']>input").val());
					var qfsj = buildFlightDate(fxrq, $tr.find("td[name='qfsj']>input").val(), kcsj);
					var ldsj = buildFlightDate(fxrq, $tr.find("td[name='ldsj']>input").val(), qfsj||kcsj);
					var tcsj = buildFlightDate(fxrq, $tr.find("td[name='tcsj']>input").val(), ldsj||qfsj||kcsj);
					var sysj = (new Date(tcsj.replace(/-/g, "/")).getTime()-new Date(kcsj.replace(/-/g, "/")).getTime())/(60*1000);				
					var fxsj =(new Date(ldsj.replace(/-/g, "/")).getTime()-new Date(qfsj.replace(/-/g, "/")).getTime())/(60*1000);	
					$tr.find("td[name='sysj']>input").val(TimeUtil.convertToHour(sysj, TimeUtil.Separator.COLON));
					$tr.find("td[name='fxsj']>input").val(TimeUtil.convertToHour(fxsj, TimeUtil.Separator.COLON));
					calc("sysj");
					calc("fxsj");
					if(fxsj){
						autoFillEH($tr, TimeUtil.convertToHour(fxsj, TimeUtil.Separator.COLON));
					}
			});
			
			jQueryName(tableName)
				.find("td[name='fxsj']>input").on("blur", function(){
				var $this = $(this);
				var $tr = $this.parent().parent("tr");
				// 根据飞行时间自动填写发动机时间
				autoFillEH($tr, $this.val());
			});
			
			jQueryName(tableName)
				.find("td[name='fxxh']>input").on("blur", function(){
				var $this = $(this);
				var $tr = $this.parent().parent("tr");
				// 根据飞行循环自动填写发动机循环
				autoFillEC($tr, $this.val());
			});
			
			/*
			 * 键盘键入事件
			 */
			jQueryClass("validate-time").find("input").off("keyup").on("keyup", function(){
				// 回退时间
				validateTime($(this));
			});
			jQueryClass("validate-cycle").find("input").off("keyup").on("keyup", function(){
				// 回退循环
				validateCycle($(this));
			});
			jQueryClass("validate-decimal").find("input").off("keyup").on("keyup", function(){
				// 回退小数
				validateDecimal($(this));
			});
			jQueryClass("validate-eng").find("input").off("keyup").on("keyup", function(){
				// 回退英文
				validateEng($(this));
			});
		}
		
		// 根据飞行时间自动填写发动机时间
		function autoFillEH($tr, val){
			$tr.find("td[name='f1sj']>input").val(val);
			$tr.find("td[name='f2sj']>input").val(val);
			$tr.find("td[name='f3sj']>input").val(val);
			$tr.find("td[name='f4sj']>input").val(val);
			calc("f1sj");
			calc("f2sj");
			calc("f3sj");
			calc("f4sj");
		}
		
		// 根据飞行循环自动填写发动机循环
		function autoFillEC($tr, val){
			$tr.find("td[name='f1xh']>input").val(val);
			$tr.find("td[name='f2xh']>input").val(val);
			$tr.find("td[name='f3xh']>input").val(val);
			$tr.find("td[name='f4xh']>input").val(val);
			calc("f1xh");
			calc("f2xh");
			calc("f3xh");
			calc("f4xh");
		}
		
		// 初始化控件
		function initWidget(){
			// 初始化输入框内容限制控件
			jQueryClass("time-masked").find("input").mask("99:99");
		}
		
		// 初始化数据字典和枚举
		function initDicAndEnum(){
			
			var $fxrwlx = jQueryName("fxrwlx").find("select");
			
			$fxrwlx.empty();
			$fxrwlx.append("<option value=''></option>");
			DicAndEnumUtil.registerDic("53", param.parentId+" [name='fxrwlx']>select", param.dprtcode);
			$fxrwlx.val("载客");
			
			var $rydw = jQueryName("rydw").find("select");
			$rydw.empty();
			$rydw.append("<option value=''></option>");
			DicAndEnumUtil.registerDic("74", param.parentId+" [name='rydw']>select", param.dprtcode);
			$rydw.find("option:first").attr("selected", 'selected'); 
		}
		
		// 验证日期时间
		function validateDateTime($obj){
			var value = $obj.val();
			if(!/^(([01][0-9])|(2[0-3]))\:[0-5][0-9]$/.test(value)){
				$obj.val("");
			}
		}
		
		// 获取不参与计算的列
		function getNotCalcColumn(){
			var condition = "td:not(";
			$(notCalcColumnList).each(function(){
				condition += "[name='"+this+"'],";
			});
			condition = condition.substring(0, condition.length - 1);
			condition += ")>input";
			
			return jQueryName(tableName).find(condition);
		}
		
		// 打开机长弹窗
		function openJzWin(obj){
			var $tr = $(obj).parents("tr[hc]");
			Personel_Tree_Multi_Modal.show({
				checkUserList:[{id:$tr.find("[name='jzid']").val(),displayName:$tr.find("[name='jzstr']").val()}],//原值，在弹窗中回显
				dprtcode:flb_detail.getDprtcode(),
				multi:false,
				callback: function(data){//回调函数
					var user = $.isArray(data)?data[0]:{id:'',displayName:''};
					$tr.find("[name='jzid']").val(user.id);
					$tr.find("[name='jzstr']").val(user.displayName);
					disableUser($tr);
				}
			});
			AlertUtil.hideModalFooterMessage();
		}
		
		// 禁止输入用户
		function disableUser($tr){
			var user = $tr.find("[name='jzid']").val();
			if(user){
				$tr.find("[name='jzstr']").attr("readonly","readonly").addClass("readonly-style");
			}else{
				$tr.find("[name='jzstr']").removeAttr("readonly").removeClass("readonly-style");
			}
		}
		
		// 回退时间
		function validateTime($obj){
			backspace($obj, /^(0|[1-9]\d*)(\:[0-5]?[0-9]?)?$/, true);
		}
		
		// 回退循环
		function validateCycle($obj){
			backspace($obj, /^(0|[1-9]\d*)$/);
		}
		
		// 回退小数
		function validateDecimal($obj){
			backspace($obj, /^(0|[1-9]\d*)(\.[0-9]?[0-9]?)?$/);
		}
		
		// 回退英文
		function validateEng($obj){
			validateEng($obj, /^[\x00-\xff]*$/);
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
		}
		
		// 计算所有累计值
		function calcAll(visible){
			getNotCalcColumn().each(function(){
				calc($(this).parent().attr("name"), visible);
			});
			
		}
		
		// 计算单个累计值
		function calc(columnName, visible){
			// 计算航次
			var $trs = jQueryName(tbodyName).find("tr" + (visible ? ":visible" : ""));
			
			// 计算有飞行前数据的列
			if($.inArray(columnName, calcPreColumnList) != -1){
				$trs.each(function(i, tr){
					var $tr = $(tr);
					// 当前选中的td
					var td = $tr.find("td[name='"+columnName+"']");
					// 当前行的飞行前数据
					var pre = $.trim(td.find("small[name='pre']").text());
					// 当前行的输入框值
					var val = td.find("input").val()||"0";
					// 当前行的序列号
					var xlh = td.find("small[name='xlh']").text();
					
					// 下一行的对应td
					var nextTd = $tr.next().find("td[name='"+columnName+"']");
					if(nextTd.length > 0 && pre){
						// 下一行的序列号
						var nextXlh = nextTd.find("small[name='xlh']").text();
						
						// 件号和序列号没有发生改变
						if(xlh == nextXlh){
							// 刷新下一行的飞行前数据
							nextTd.find("small[name='pre']").text(add(pre, val, columnName));
						}
					}
					if($trs.length == i + 1){
						// 计算累计
						var sumInput = jQueryName(tableName).find("tfoot tr th[name='"+columnName+"'] input");
						sumInput.val(add(pre, val, columnName));
						var sumSpan = jQueryName(tableName).find("tfoot tr th[name='"+columnName+"'] span");
						sumSpan.text(add(pre, val, columnName));
					}
				});
			}
			// 计算累计
			else{
				var total = "0";
				$trs.each(function(i, tr){
					var $tr = $(tr);
					// 当前选中的td
					var td = $tr.find("td[name='"+columnName+"']");
					// 当前行的输入框值
					var val = td.find("input").val()||"0";
					total = add(total, val, columnName);
					
					if(columnName == "rycyl" || columnName == "ryjyl"){
						// 计算飞行前总油量
						calcOilBefore($tr);
					}
					if(columnName == "rysyyl"){
						// 计算油量消耗
						calcOilCost($tr);
					}
				});
				
				var sumSpan = jQueryName(tableName).find("tfoot tr th[name='"+columnName+"'] span");
				sumSpan.text(total);
			}
		}
		
		// 计算飞行前总油量
		function calcOilBefore($tr){
			var rycyl = $tr.find("td[name='rycyl']>input").val() || "0";
			var ryjyl = $tr.find("td[name='ryjyl']>input").val() || "0";
			var total = add(rycyl, ryjyl, "");
			$tr.find("td[name='ryzyl']>span").text(total);
			calcOilCost($tr);
		}
		
		// 计算油量消耗
		function calcOilCost($tr){
			var ryzyl = $tr.find("td[name='ryzyl']>span").text();
			var rysyyl = $tr.find("td[name='rysyyl']>input").val();
			var cost = subtract(ryzyl, rysyyl, "");
			$tr.find("td[name='ryxhyl']>span").text(cost);
			
			var total = "0";
			jQueryName(tbodyName).find("tr:visible>td[name='ryxhyl']>span").each(function(){
				var val = $(this).text();
				total = add(total, val, "");
			});
			jQueryName(tableName).find("tfoot>tr>th[name='ryxhyl']>span").text(total);
		}
		
		// 加法
		function add(param1, param2, columnName){
			// 时间计算
			if(columnName.indexOf("sj") != -1){
				return TimeUtil.operateTime(param1, param2, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD);
			} 
			// 循环计算
			else {
				var param1_float = parseFloat(param1);
				var param2_float = parseFloat(param2);
				param1_float = isNaN(param1_float) ? 0 : param1_float;
				param2_float = isNaN(param2_float) ? 0 : param2_float;
				return parseFloat((param1_float + param2_float).toFixed(2));
			}
		}
		
		// 减法
		function subtract(param1, param2, columnName){
			// 时间计算
			if(columnName.indexOf("sj") != -1){
				return TimeUtil.operateTime(param1, param2, TimeUtil.Separator.COLON, TimeUtil.Operation.SUBTRACT);
			} 
			// 循环计算
			else {
				var param1_float = parseFloat(param1);
				var param2_float = parseFloat(param2);
				param1_float = isNaN(param1_float) ? 0 : param1_float;
				param2_float = isNaN(param2_float) ? 0 : param2_float;
				return parseFloat((param1_float - param2_float).toFixed(2));
			}
		}
			
		// 选择列高亮
		function initHover (){
			jQueryName(tableName).find("td, th").hover(
				function(){
					var columeName = $(this).attr("name");
					jQueryName(tableName).find("td[name='"+columeName+"'],th[name='"+columeName+"'],th[children*='"+columeName+"']").addClass("warning");
				},
				function(){
					jQueryName(tableName).find("td,th").removeClass("warning");
				}
		    );
		}
		
		// 添加行
		function addRow(){
			var $trs = jQueryName(tbodyName).find("tr:visible");
			if($trs.length >= 4){
				// 最多4个航次
				AlertUtil.showErrorMessage("最多只能添加4个航次！");
			}else {
				$trs.last().next().show();
			}
			flb_work.addLeg($trs.length * 2 + 1);
			flb_work.showFinishedWork($trs.length + 1);
		}
		
		// 删除行
		function deleteRow(){
			var $trs = jQueryName(tbodyName).find("tr:visible");
			
			// 验证航次是否添加完成工作？
			
			if($trs.length == 2){
				// 最少2个航次
				AlertUtil.showErrorMessage("最少保留2个航次！");
			}else {
				$trs.last().hide();
			}
			flb_work.deleteLeg($trs.length * 2 - 1);
			flb_work.showFinishedWork($trs.length - 1);
		}
		
		// 显示发动机
		function showEngine(){
			var count = 2;
			if(param.win_type == "detail"){
				count = flb_detail.getEngCount();
			}else if(param.win_type == "main"){
				count = flb.getEngCount();
			}
			jQueryName(tableName).find("th[name='fdj2'],th[name='fdj3'],th[name='fdj4']").hide();
			jQueryName(tableName).find("[name='f2sj'],[name='f2xh'],[name='f2hytjl']").hide();
			jQueryName(tableName).find("[name='f3sj'],[name='f3xh'],[name='f3hytjl']").hide();
			jQueryName(tableName).find("[name='f4sj'],[name='f4xh'],[name='f4hytjl']").hide();
			if(count >= 2){
				jQueryName(tableName).find("th[name='fdj2'],[name='f2sj'],[name='f2xh'],[name='f2hytjl']").show();
			}
			if(count >= 3){
				jQueryName(tableName).find("th[name='fdj3'],[name='f3sj'],[name='f3xh'],[name='f3hytjl']").show();
			}
			if(count >= 4){
				jQueryName(tableName).find("th[name='fdj4'],[name='f4sj'],[name='f4xh'],[name='f4hytjl']").show();
			}
		}
		
		// 验证数据
		function validateData(){
			
			var result = true;
			var $trs = jQueryName(tbodyName).find("tr:visible");
			var fxrq = $.trim($("#common_fxrq").val());
			var count = 0;
			var lastKcsj = "";
			$trs.each(function(){
				var $tr = $(this);
				if(hasValue($tr)){
					var hcms = $tr.attr("hcms");
					var hbh = $.trim($tr.find("td[name='hbh']>input").val());
					var kcsj = $.trim($tr.find("td[name='kcsj']>input").val());
					kcsj = buildFlightDate(fxrq, kcsj);
					var qfsj = buildFlightDate(fxrq, $.trim($tr.find("td[name='qfsj']>input").val()), kcsj);
					var ldsj = buildFlightDate(fxrq, $.trim($tr.find("td[name='ldsj']>input").val()), qfsj||kcsj);
					var tcsj = buildFlightDate(fxrq, $.trim($tr.find("td[name='tcsj']>input").val()), ldsj||qfsj||kcsj);
					
					// 如果填写了APU的任意一个信息，则其他信息都可不用填写
					var apusj = $.trim($tr.find("td[name='apusj']>input").val());
					var apuxh = $.trim($tr.find("td[name='apuxh']>input").val());
					var apuhytjl = $.trim($tr.find("td[name='apuhytjl']>input").val());
					if(apusj || apuxh || apuhytjl){
						return true;
					}
					if(!fxrq){
						AlertUtil.showModalFooterMessage("请输入飞行日期！");
						result = false;
						return false;
					}
					if(!hbh){
						AlertUtil.showModalFooterMessage("请输入航班号！");
						result = false;
						return false;
					}
					if(!kcsj){
						AlertUtil.showModalFooterMessage("请输入开车时间！");
						result = false;
						return false;
					}
					if(qfsj && kcsj && !TimeUtil.compareDate(qfsj, kcsj)){
						AlertUtil.showModalFooterMessage(hcms + "的起飞时间必须大于开车时间！");
						result = false;
						return false;
					}
					if(qfsj && ldsj && !TimeUtil.compareDate(ldsj, qfsj)){
						AlertUtil.showModalFooterMessage(hcms + "的落地时间必须大于起飞时间！");
						result = false;
						return false;
					}
					if(ldsj && tcsj && TimeUtil.compareDate(ldsj, tcsj)){
						AlertUtil.showModalFooterMessage(hcms + "的停车时间必须大于等于落地时间！");
						result = false;
						return false;
					}
					if(lastKcsj && !TimeUtil.compareDate(kcsj, lastKcsj)){
						AlertUtil.showModalFooterMessage(hcms + "的开车时间输入不正确！");
						result = false;
						return false;
					}
					lastKcsj = tcsj||ldsj||qfsj||kcsj;					
					count++;
				}
			});
			
			if(result && !validateFlightData($trs)){
				AlertUtil.showModalFooterMessage("请依次添加飞行数据信息！");
				result = false;
			}
			
			/*if(result && count == 0){
				AlertUtil.showModalFooterMessage("请至少添加一个航次的飞行数据信息！");
				result = false;
			}*/
			return result;
			
			
			// 验证飞行前数据
			function validateFlightData($rows){
				var result = true;
				$rows.each(function(i, row){
					row = $(row);
					if(!hasValue(row) && hasValue(row.next())){
						result = false;
						return false;
					}
				});
				return result;
			}
			
			// 判断tr中input是否只是有一个值
			function hasValue($tr){
				var result = false;
				$tr.find("input").each(function(){
					if($(this).val() != ""){
						result = true;
						return false;
					}
				});
				return result;
			}
		}
		
		// 获取飞行数据
		function getData(){
			if(!validateData()){
				return null;
			}
			var flightDatas = [];
			var fxrq = $.trim($("#common_fxrq").val());
			var count = flb_detail.getEngCount();
			jQueryName(tbodyName).find("tr:visible").each(function(){
				var $tr = $(this);
				var obj = {};
				obj.id = $.trim($tr.attr("name"));
				obj.hc = $.trim($tr.attr("hc"));
				obj.hcms = $.trim($tr.attr("hcms"));
				obj.hbh = $.trim($tr.find("td[name='hbh']>input").val());
				obj.fxrwlx = $.trim($tr.find("td[name='fxrwlx']>select").val());
				obj.qfz = $.trim($tr.find("td[name='qfz']>input").val());
				obj.zlz = $.trim($tr.find("td[name='zlz']>input").val());
				obj.kcsj = buildFlightDate(fxrq, $.trim($tr.find("td[name='kcsj']>input").val()) || "00:00");
				obj.qfsj = buildFlightDate(fxrq, $.trim($tr.find("td[name='qfsj']>input").val()), obj.kcsj);
				obj.ldsj = buildFlightDate(fxrq, $.trim($tr.find("td[name='ldsj']>input").val()), obj.qfsj||obj.kcsj);
				obj.tcsj = buildFlightDate(fxrq, $.trim($tr.find("td[name='tcsj']>input").val()), obj.ldsj||obj.qfsj||obj.kcsj);
				obj.sysjFz = $.trim($tr.find("td[name='sysj']>input").val());
				obj.fxsjFz = $.trim($tr.find("td[name='fxsj']>input").val());
				obj.fxxh = $.trim($tr.find("td[name='fxxh']>input").val());
				obj.lxqlcs = $.trim($tr.find("td[name='lxqlcs']>input").val());
				obj.f1SjFz = $.trim($tr.find("td[name='f1sj']>input").val());
				obj.f1Xh = $.trim($tr.find("td[name='f1xh']>input").val());
				obj.f1Hytjl = $.trim($tr.find("td[name='f1hytjl']>input").val());
				if(count >= 2){
					obj.f2SjFz = $.trim($tr.find("td[name='f2sj']>input").val());
					obj.f2Xh = $.trim($tr.find("td[name='f2xh']>input").val());
					obj.f2Hytjl = $.trim($tr.find("td[name='f2hytjl']>input").val());
				}
				if(count >= 3){
					obj.f3SjFz = $.trim($tr.find("td[name='f3sj']>input").val());
					obj.f3Xh = $.trim($tr.find("td[name='f3xh']>input").val());
					obj.f3Hytjl = $.trim($tr.find("td[name='f3hytjl']>input").val());
				}
				if(count >= 4){
					obj.f4SjFz = $.trim($tr.find("td[name='f4sj']>input").val());
					obj.f4Xh = $.trim($tr.find("td[name='f4xh']>input").val());
					obj.f4Hytjl = $.trim($tr.find("td[name='f4hytjl']>input").val());
				}
				obj.apuSjFz = $.trim($tr.find("td[name='apusj']>input").val());
				obj.apuXh = $.trim($tr.find("td[name='apuxh']>input").val());
				obj.apuHytjl = $.trim($tr.find("td[name='apuhytjl']>input").val());
				obj.idgtksj = $.trim($tr.find("td[name='idgtksj']>input").val());
				obj.yyy = $.trim($tr.find("td[name='yyy']>input").val());
				obj.ryCyl = $.trim($tr.find("td[name='rycyl']>input").val());
				obj.ryJyl = $.trim($tr.find("td[name='ryjyl']>input").val());
				obj.rySyyl = $.trim($tr.find("td[name='rysyyl']>input").val());
				obj.rydw = $.trim($tr.find("td[name='rydw']>select").val());
				obj.jz = $.trim($tr.find("input[name='jzstr']").val());
				obj.jzid = $.trim($tr.find("input[name='jzid']").val());
				
				
				/* 时间转换 小时-->分钟 */
				obj.sysjFz = obj.sysjFz ? TimeUtil.convertToMinute(obj.sysjFz) : "";
				obj.fxsjFz = obj.fxsjFz ? TimeUtil.convertToMinute(obj.fxsjFz) : "";
				obj.f1SjFz = obj.f1SjFz ? TimeUtil.convertToMinute(obj.f1SjFz) : "";
				obj.f2SjFz = obj.f2SjFz ? TimeUtil.convertToMinute(obj.f2SjFz) : "";
				obj.f3SjFz = obj.f3SjFz ? TimeUtil.convertToMinute(obj.f3SjFz) : "";
				obj.f4SjFz = obj.f4SjFz ? TimeUtil.convertToMinute(obj.f4SjFz) : "";
				obj.apuSjFz = obj.apuSjFz ? TimeUtil.convertToMinute(obj.apuSjFz) : "";
				
				if(obj.hbh || obj.apuSjFz || obj.apuXh || obj.apuHytjl){
					flightDatas.push(obj);
				}
			});
			return flightDatas;
			
		}
		
		// 生成飞行时间
		function buildFlightDate(fxrq, time, kcsj){
			var result = "";
			if(time){
				if(time.indexOf(":") == -1){
					time += ":00";
				}
				result = fxrq + " " + time + ":00";
			}
			if(kcsj && result && TimeUtil.compareDate(kcsj, result)){
				result = addDate(fxrq, 1) + " " + time + ":00";
			}
			return result;
		}
		
		
		// 日期后推
		function addDate(date, days) {
            var date = new Date(date);
            date.setDate(date.getDate() + days);
            var month = date.getMonth() + 1;
            if(month.length < 2){
            	month = "0" + month;
            }
            var day = date.getDate();
            if(day.length < 2){
            	day = "0" + day;
            }
            return date.getFullYear() + '-' + month + '-' + day;
        }
		
		// 加载飞行前数据
		function loadPreflightData(){
			
			var kcsjData = getKcsjData();
			if(kcsjData.length > 0){
				startWait();
				AjaxUtil.ajax({
					url: basePath+"/produce/flb/loadPreflightData",
					type: "post",
					contentType:"application/json;charset=utf-8",
					dataType:"json",
					data:JSON.stringify({
						fjzch : flb_detail.getFjzch(),
						dprtcode : flb_detail.getDprtcode(),
						flightSheetVoyageList : kcsjData
					}),
					success:function(data){
						finishWait();
						$(data.flightSheetVoyageList || []).each(function(i, voyage){
							$tr = jQueryName(tbodyName).find("tr[hc='"+voyage.hc+"']");
							fillPreflightData($tr, voyage.preflightData);
						});
				    }
				}); 
			}
			
			// 获取开车时间数据
			function getKcsjData(){
				var flightDatas = [];
				var fxrq = $.trim($("#common_fxrq").val());
				if(fxrq){
					jQueryName(tbodyName).find("tr:visible").each(function(){
						var $tr = $(this);
						var obj = {};
						obj.hc = $.trim($tr.attr("hc"));
						obj.kcsj = buildFlightDate(fxrq, $.trim($tr.find("td[name='kcsj']>input").val())) || fxrq;
						if(obj.kcsj){
							flightDatas.push(obj);
						}
					});
				}
				return flightDatas;
			}
		}
		
		// 刷新飞行前数据
		function refreshPreflightData(hd, wz, bjh, xlh, type, jssj, data){
			jQueryName(tbodyName).find("tr").each(function(){
				var $tr = $(this);
				var fxrq = $.trim($("#common_fxrq").val());
				var	kcsj = buildFlightDate(fxrq, $tr.find("td[name='kcsj']>input").val());
				var hc = $tr.attr("hc");
				if(TimeUtil.compareDate(jssj, kcsj)){
					return true;
				}
				var fjdxh, fjdsj, apuxh, apusj;
				var inits = data.initDatas || [];
				$(inits).each(function(i, init){
					if(init.jklbh == "2_30_EH"){
						fjdsj = init.csz;
					} else if (init.jklbh == "2_20_AH"){
						apusj = init.csz;
					} else if (init.jklbh == "3_30_EC"){
						fjdxh = init.csz;
					} else if (init.jklbh == "3_20_AC"){
						apuxh = init.csz;
					}
				});
				if(wz == "21"){
					setJhAndXlh($tr, 'f1sj', bjh, xlh, fjdsj, type, hc);
					setJhAndXlh($tr, 'f1xh', '', '', fjdxh, type, hc);
				} else if (wz == "22"){
					setJhAndXlh($tr, 'f2sj', bjh, xlh, fjdsj, type, hc);
					setJhAndXlh($tr, 'f2xh', '', '', fjdxh, type, hc);
				} else if (wz == "23"){
					setJhAndXlh($tr, 'f3sj', bjh, xlh, fjdsj, type, hc);
					setJhAndXlh($tr, 'f3xh', '', '', fjdxh, type, hc);
				} else if (wz == "24"){
					setJhAndXlh($tr, 'f4sj', bjh, xlh, fjdsj, type, hc);
					setJhAndXlh($tr, 'f4xh', '', '', fjdxh, type, hc);
				} else if (wz == "31"){
					setJhAndXlh($tr, 'apusj', bjh, xlh, apusj, type, hc);
					setJhAndXlh($tr, 'apuxh', '', '', apuxh, type, hc);
				}
			});
		}
		
		// 刷新序列号
		function setJhAndXlh(row, column, jh, xlh, pre, type, hc){
			var td = row.find("td[name='"+column+"']");
			var xlh = xlh||"&nbsp;";
			if(type == "on"){
				pre = pre||"0";
			}else{
				pre = pre||"&nbsp;";
			}
			td.find("small[name='xlh']").html(xlh);
			td.find("small[name='pre']").html(pre);
			if(hc == 8){
				calc(column);
			}
		}
		
		// 填充飞行前数据
		function fillPreflightData($tr, data){
			$tr.find("[name='xlh']").html("&nbsp;");
			$tr.find("[name='pre']").html("&nbsp;");
			$(data).each(function(i, pre){
				var sj = "0";
				var xlh = formatUndefine(pre.xlh)||"&nbsp;";
				var xh = pre.xh||"0";
				if(pre.sj != null){
					sj = TimeUtil.convertToHour(pre.sj, TimeUtil.Separator.COLON);
				}
				if(pre.wz == "11"){
					$tr.find("td[name='fxsj']>[name='pre']").html(sj);
					$tr.find("td[name='fxxh']>[name='pre']").html(xh);
				}else if(pre.wz == "21"){
					$tr.find("td[name='f1sj']>[name='xlh']").html(xlh);
					$tr.find("td[name='f1xh']>[name='xlh']").html(xlh);
					$tr.find("td[name='f1sj']>[name='pre']").html(sj);
					$tr.find("td[name='f1xh']>[name='pre']").html(xh);
				}else if(pre.wz == "22"){
					$tr.find("td[name='f2sj']>[name='xlh']").html(xlh);
					$tr.find("td[name='f2xh']>[name='xlh']").html(xlh);
					$tr.find("td[name='f2sj']>[name='pre']").html(sj);
					$tr.find("td[name='f2xh']>[name='pre']").html(xh);
				}else if(pre.wz == "23"){
					$tr.find("td[name='f3sj']>[name='xlh']").html(xlh);
					$tr.find("td[name='f3xh']>[name='xlh']").html(xlh);
					$tr.find("td[name='f3sj']>[name='pre']").html(sj);
					$tr.find("td[name='f3xh']>[name='pre']").html(xh);
				}else if(pre.wz == "24"){
					$tr.find("td[name='f4sj']>[name='xlh']").html(xlh);
					$tr.find("td[name='f4xh']>[name='xlh']").html(xlh);
					$tr.find("td[name='f4sj']>[name='pre']").html(sj);
					$tr.find("td[name='f4xh']>[name='pre']").html(xh);
				}else if(pre.wz == "31"){
					$tr.find("td[name='apusj']>[name='xlh']").html(xlh);
					$tr.find("td[name='apuxh']>[name='xlh']").html(xlh);
					$tr.find("td[name='apusj']>[name='pre']").html(sj);
					$tr.find("td[name='apuxh']>[name='pre']").html(xh);
				}
			});
			calc("fxsj");
			calc("fxxh");
			calc("f1sj");
			calc("f1xh");
			calc("f2sj");
			calc("f2xh");
			calc("f3sj");
			calc("f3xh");
			calc("f4sj");
			calc("f4xh");
			calc("apusj");
			calc("apuxh");
		}
		
		// 填充数据
		function fillData(data){
			data = data || [];
			initDicAndEnum();
			
			var $tbody = jQueryName(tbodyName);
			$tbody.find("input").val("");
			$tbody.find("tr").attr("name","");
			$tbody.find("[name='xlh']").html("&nbsp;");
			$tbody.find("[name='pre']").html("&nbsp;");
			$tbody.find("tr[hc='2'],tr[hc='4']").show();
			$tbody.find("tr[hc='6'],tr[hc='8']").hide();
			$(data).each(function(i, obj){
				
				/* 时间转换 分钟-->小时 */
				obj.sysjFz = obj.sysjFz ? TimeUtil.convertToHour(obj.sysjFz, TimeUtil.Separator.COLON) : "";
				obj.fxsjFz = obj.fxsjFz ? TimeUtil.convertToHour(obj.fxsjFz, TimeUtil.Separator.COLON) : "";
				obj.f1SjFz = obj.f1SjFz ? TimeUtil.convertToHour(obj.f1SjFz, TimeUtil.Separator.COLON) : "";
				obj.f2SjFz = obj.f2SjFz ? TimeUtil.convertToHour(obj.f2SjFz, TimeUtil.Separator.COLON) : "";
				obj.f3SjFz = obj.f3SjFz ? TimeUtil.convertToHour(obj.f3SjFz, TimeUtil.Separator.COLON) : "";
				obj.f4SjFz = obj.f4SjFz ? TimeUtil.convertToHour(obj.f4SjFz, TimeUtil.Separator.COLON) : "";
				obj.apuSjFz = obj.apuSjFz ? TimeUtil.convertToHour(obj.apuSjFz, TimeUtil.Separator.COLON) : "";
				
				var $tr = jQueryName(tbodyName).find("tr[hc='"+obj.hc+"']");
				$tr.attr("name", obj.id);
				$tr.find("td[name='hbh']>input").val(obj.hbh);
				$tr.find("td[name='fxrwlx']>select,td[name='fxrwlx']>input").val(obj.fxrwlx);
				$tr.find("td[name='rydw']>select,td[name='rydw']>input").val(obj.rydw);
				$tr.find("td[name='qfz']>input").val(obj.qfz);
				$tr.find("td[name='zlz']>input").val(obj.zlz);
				$tr.find("td[name='kcsj']>input").val(buildFlightHour(obj.kcsj));
				$tr.find("td[name='qfsj']>input").val(buildFlightHour(obj.qfsj));
				$tr.find("td[name='ldsj']>input").val(buildFlightHour(obj.ldsj));
				$tr.find("td[name='tcsj']>input").val(buildFlightHour(obj.tcsj));
				$tr.find("td[name='sysj']>input").val(obj.sysjFz);
				$tr.find("td[name='fxsj']>input").val(obj.fxsjFz);
				$tr.find("td[name='fxxh']>input").val(obj.fxxh);
				$tr.find("td[name='lxqlcs']>input").val(obj.lxqlcs);
				$tr.find("td[name='f1sj']>input").val(obj.f1SjFz);
				$tr.find("td[name='f1xh']>input").val(obj.f1Xh);
				$tr.find("td[name='f1hytjl']>input").val(obj.f1Hytjl);
				$tr.find("td[name='f2sj']>input").val(obj.f2SjFz);
				$tr.find("td[name='f2xh']>input").val(obj.f2Xh);
				$tr.find("td[name='f2hytjl']>input").val(obj.f2Hytjl);
				$tr.find("td[name='f3sj']>input").val(obj.f3SjFz);
				$tr.find("td[name='f3xh']>input").val(obj.f3Xh);
				$tr.find("td[name='f3hytjl']>input").val(obj.f3Hytjl);
				$tr.find("td[name='f4sj']>input").val(obj.f4SjFz);
				$tr.find("td[name='f4xh']>input").val(obj.f4Xh);
				$tr.find("td[name='f4hytjl']>input").val(obj.f4Hytjl);
				$tr.find("td[name='apusj']>input").val(obj.apuSjFz);
				$tr.find("td[name='apuxh']>input").val(obj.apuXh);
				$tr.find("td[name='apuhytjl']>input").val(obj.apuHytjl);
				$tr.find("td[name='idgtksj']>input").val(obj.idgtksj);
				$tr.find("td[name='yyy']>input").val(obj.yyy);
				$tr.find("td[name='rycyl']>input").val(obj.ryCyl);
				$tr.find("td[name='ryjyl']>input").val(obj.ryJyl);
				$tr.find("td[name='rysyyl']>input").val(obj.rySyyl);
				$tr.find("input[name='jzstr']").val(obj.jz);
				$tr.find("input[name='jzid']").val(obj.jzid);
				
				fillPreflightData($tr, obj.preflightData);
				
				disableUser($tr);
				if(obj.id){
					$tr.show();
				}
			});
			
			// 显示发动机
			showEngine();
			// 计算累计值
			calcAll(false);
			
			if(param.callback && typeof(param.callback) == "function"){
				param.callback({});
			}
			
			
			// 生成飞行时间
			function buildFlightHour(str){
				return (str||"").substr(11, 5);
			}
		}
		
		return {
			showEngine : showEngine,
			getData : getData,
			fillData : fillData,
			loadPreflightData : loadPreflightData,
			refreshPreflightData : refreshPreflightData
		}
		
	};
	
	