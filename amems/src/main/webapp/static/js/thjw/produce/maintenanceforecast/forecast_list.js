	$(document).ready(function(){
		Navigation(menuCode, '', '', '', '', '', '', '');
		maintenance_forecast.init();
	});

var maintenance_forecast = {
		
		timeArr : ['2_10_FH','2_30_EH','2_20_AH'],//时间类监控项目
		enginArr : ['2_30_EH','3_30_EC'],//发动机类监控项目
		 
		mpUnit : {
			10:	"",
			11:	"D",
			12:	"",
			20:	"FH",
			30:	"FC",
		},
		
		initWebuiPopover : function(){//初始化
			var this_ = this;
			WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
				return this_.getHistoryAttachmentList(obj);
			});
			$("#work_card_main_table_top_div").scroll(function(){
				$('.attachment-view').webuiPopover('hide');
			});
		},
		getHistoryAttachmentList : function(obj){//获取历史附件列表
			var jsonData = [
			   	         {mainid : $(obj).attr('qtid'), type : '其它附件'}
			   	        ,{mainid : $(obj).attr('gkfjid'), type : '工卡附件'}
			   	        ,{mainid : $(obj).attr('gznrfjid'), type : '工作内容附件'}
			   	    ];
			return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
		},
    	
		init: function(){
			
			var _this = this;
			$('#jzrq').datepicker({
				format:'yyyy-mm-dd',
				autoclose: true,
				clearBtn:false,
				 
			}).on("changeDate", function(e) {
				
				 var currentDate = new Date(currentDateStr);
				 var limitDate = new Date(limitDateStr);
				 var date = (e.date >= limitDate) ? limitDate : e.date;
				 var date = (date <= currentDate) ? currentDate : date;
				 
				 $('#jzrq').datepicker('setDate',date ); 
			}).on("hide", function() {
				_this.changeEndDate();
				if(_this.currents && (_this.currents.length > 0 )) {
					$('#loadlistBtn').trigger('click');
				}
			});
			
			//_this.initDate();
			_this.changeDprtcode();
		},
		initDate: function(){
			var currentDate = new Date(currentDateStr);
			$('#jzrq').datepicker('setDate',currentDate ); 
		},
		/** 清除基础数据*/
		clearBaseInfo: function(){
			$("#aircraft_condition_list_table thead ").hide();
			$("#aircraft_condition_list").empty();
		},
		/** 清除监控数据*/
		clearJkInfo: function(){
			$("#production_forecast_list").empty();
		},
		/** 重新加载飞机注册号*/
		reloadAc: function(){
			var dprtcode = $.trim($("#dprtcode_search").val());
			var planeRegOption ='';
			var planeData = acAndTypeUtil.getACRegList({DPRTCODE:StringUtil.escapeStr(dprtcode),FJJX:null});
			if(planeData != null && planeData.length >0){
				$.each(planeData,function(i,planeData){
					if(dprtcode == planeData.DPRTCODE){
						planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+" "+StringUtil.escapeStr(planeData.XLH)+"</option>";
					}
				});
			}
			if(planeRegOption == ''){
				planeRegOption = '<option value="">暂无飞机 No plane</option>';
			}
			$("#fjzch").html(planeRegOption);
			
			this.changeAc();
			
		},
		/** */
		changeDprtcode: function(){
			 this.reloadAc();
		},
		/** 
		 * 修改飞机注册号
		 * 1.刷新目标值
		 * 2.刷新预计值(截止日期有值)
		 * 3.刷新目标值(截止日期有值)
		 * 
		 * */
		changeAc: function(){
			var _this = this;
			var fjzch = $('#fjzch').val();
			var jzrq = $('#jzrq').val();
			this.clearBaseInfo();
			this.clearJkInfo();
			
			if(fjzch) { 
				var jzrq = $('#jzrq').val();
				var dprtcode = $('#dprtcode_search').val();
				AjaxUtil.ajax({
					   url:basePath+"/produce/maintenance/forecast/currnetInfo",
					   type: "post",
					   contentType:"application/json;charset=utf-8",
					   dataType:"json",
					   data:JSON.stringify({
						   fjzch : fjzch,
						   dprtcode : dprtcode
					   }),
					   success:function(data){
						   var currents = data.currents ;
						   //缓存当前值，日使用量，当前日期
						   _this.currents = data.currents;
						   _this.planUsageMap = data.planUsageMap;
						   _this.currentDate = data.currentDate;
						   
						   if(currents.length > 0) {
							   //加载当前值
							   $(".searchTable").css("display","block");
							   _this.loadCol(currents);
							   _this.loadCurrents(currents);
							   
							   _this.loadOthers(currents);
							   
							   //截止日期非空，促发日期改变事件
							   $("#iconToggleTable").css("display","table-cell");
							 
							   $("#aircraft_condition_list_table thead ").show();
							   $("#iconToggleTable").css({"height":$("#aircraft_condition_list_table").outerHeight()+"px","line-height":$("#aircraft_condition_list_table").outerHeight()+"px"});
							   if(jzrq){
								   $('#loadlistBtn').trigger('click');
							   }
						   }else{
							   $(".searchTable").css("display","none");
							   $("#iconToggleTable").css("display","none"); 
						   }
						   $("#production_forecast_list").append("<tr><td class=\"text-center\"  colspan=\"20\">暂无数据 No data.</td></tr>");
						   App.resizeHeight();
					   }
				   });
			}
			else {
				 $(".searchTable").css("display","none");
				$("#production_forecast_list").append("<tr><td class=\"text-center\"  colspan=\"20\">暂无数据 No data.</td></tr>");
				 App.resizeHeight();
			}
		},
		
		showChildTR: function(param){
			var id = $(param).attr('id');			
			//行变色
			$(param).addClass('bg_tr').siblings().removeClass('bg_tr');
			// 下方div是否显示
			new Sticky({tableId:'production_forecast_list_table'});
			var isBottomShown = false;
			if($(".displayContent").is(":visible")){
				isBottomShown = true;
			}
			TableUtil.showDisplayContent();
			if(!isBottomShown){
				TableUtil.makeTargetRowVisible($(param), $("#tableId"));
			}
			//TODO 加载子表（计划预测）
			
		},
		 
		/**刷新目标值*/
		refreshGoal: function(element){
			var $ele = $(element)
			var estimate = $ele.val();
			
			//第一步，校验格式。
			if($ele.hasClass('fh')){
				//飞行时间类监控处理
				if(monitor_setting_check.isFh($ele,estimate)) {
					 //第二步，刷新目标值。
					 var idx = $ele.parent().index();
					 var current = $('.current td').eq(idx).html();
					 //处理‘小时：’格式
					 if(estimate.substr(estimate.length-1) == ':'){
						 estimate = estimate.replace(':','');
					 }
					 
					 var target = TimeUtil.operateTime(estimate, current, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD);
					 $('tr[class=target] td').eq(idx).html(target);
				}
				else {
					$ele.focus();
				}
			}
			else if($ele.hasClass('fc')){
				//飞行循环类监控处理
				if(monitor_setting_check.isFc($ele,estimate)) {
					//第二步，刷新目标值。
					 var idx = $ele.parent().index();
					 var current = $('.current td').eq(idx).html();
					 var target = parseInt(estimate)+parseInt(current);
					 $('tr[class=target] td').eq(idx).html(target);
				}
				else {
					$ele.focus();
				}
			}
		},
	 
		/** 根据飞机注册号,机构号加载列头*/
		loadCol: function(colArr){ 
			var colmap = {
			    	 '2_10_FH':{cn:'飞行时间',en:'FH'}
			    	,'3_10_FC':{cn:'飞行循环',en:'FC'}
			    	,'2_20_AH':{cn:'APU小时',en:'AH'}
			    	,'3_20_AC':{cn:'APU循环',en:'AC'}
			    	,'2_30_EH':{cn:'发小时',en:'EH'}
			    	,'3_30_EC':{cn:'发循环',en:'EC'}
			    }
			
			var wzmap = {
					'21':'1#',
					'22':'2#',
					'23':'3#',
					'24':'4#'
			};

			    //增加监控项目，位置（非发动机，位置-），以便和监控数据进行比较。
			    var $columnTr = $('#aircraft_condition_list_table tr');
				$columnTr.empty();
				var firstCol = '';
				firstCol += '<th class="fixed-column" style="width:60px;">';
				firstCol += '<div class="font-size-12 line-height-18" ></div>';
				firstCol += '<div class="font-size-9 line-height-18"></div>';
				firstCol += '</th>';
				
				$columnTr.append(firstCol);
				
				if(colArr && (colArr.length>0)){
					$.each(colArr,function(idx,item) {
						var tr = "<th >"
						+"<div class=\"font-size-12 line-height-18\">"+StringUtil.escapeStr(wzmap[item.wz]) + colmap[item.jklbh].cn+ "</div>"
						+"<div class=\"font-size-9 line-height-18\">"+colmap[item.jklbh].en+"</div></th>";
						$columnTr.append($(tr));
					});
				}
		},
		/** 根据飞机注册号,机构号加载当前值*/
		loadCurrents: function(colArr){ 
			var $tr = $("<tr class='current' ></tr>");
			var $list = $('#aircraft_condition_list');
			$list.append($tr);
			
			$tr.append("<td>当前值</td>");
			
			if(colArr) {
				$.each(colArr,function(idx,item){
					$tr.append("<td>"+item.paramsMap.val+"</td>");
				})
			}
		},
		/** 根据飞机注册号,机构号,截止日期加载预计值*/
		loadEstimateds : function(colArr){
			var _this = this;
			$('#aircraft_condition_list .estimated').remove();
			var $tr = $("<tr class='estimated' ></tr>");
			var $list = $('#aircraft_condition_list');
			$list.append($tr);
			
			$tr.append("<td style='vertical-align:middle;'>预计值</td>");
			if(colArr) {
				$.each(colArr,function(idx,item){
					var clazz = 'fc';
					//是时间类监控项目，转换成小时：分钟
					if($.inArray(item.jklbh , _this.timeArr) != -1){
						clazz = 'fh';
					}
					$tr.append("<td> <input class='"+clazz+" form-control'  onkeyup='maintenance_forecast.refreshGoal(this)' name='aircraft_fh' value="+item.paramsMap.val+"></input></td>");
				})
			}
		},
		/** 根据飞机注册号,机构号,截止日期加载目标值*/
		loadTargets : function(colArr){
			var _this = this;
			$('#aircraft_condition_list .target').remove();
			var $tr = $("<tr class='target' ></tr>");
			var $list = $('#aircraft_condition_list');
			$list.append($tr);
			$tr.append("<td class='target'>目标值</td>");
			if(colArr) {
				$.each(colArr,function(idx,item){
					//非发动机类型的监控项目，位置为'-'
					var wz = '-'
					if($.inArray(item.jklbh , _this.enginArr) != -1){
						wz = item.wz;
					}
					$tr.append("<td jklbh='"+item.jklbh+"' wz='"+wz+"'  >"+item.paramsMap.val+"</td>");
				})
			}
		},
		//进入系统时预计值，默认值为空。
		loadOthers : function( ){ 
			var _this = this;
			var endDate = $('#jzrq').val();
			 
			var planUsageMap  = _this.planUsageMap; //日使用量map
			var currents = _this.currents; //当前值
			var currentDate = _this.currentDate; //当前日期
			//缓存预备数据存在,则继续执行。
			if(planUsageMap && currents ) {
				
					//有截止日期,无截止日期
					var days = 0;//天数 
					if(endDate) {
						days = TimeUtil.dateMinus(currentDate,endDate);//天数 
					}
					
					var estimateds = []; //预计值
					var targets = []; //目标值
					var timeArr = _this.timeArr;
					
					$.each(currents,function(idx,current){
						//1.算预计值
						var estimated = jQuery.extend(true, {}, current);
						var rsyl = planUsageMap[estimated.jklbh];
						
						//是时间类监控项目，转换成小时：分钟
						if($.inArray(estimated.jklbh , timeArr) != -1){
							if(rsyl =='' || rsyl == 0) {
								rsyl = '1:0';
							}
							else{
								rsyl = TimeUtil.convertToHour(rsyl, TimeUtil.Separator.COLON);	
							}
							
							 var mi = TimeUtil.convertToMinute(rsyl);
							estimated.paramsMap.val = TimeUtil.convertToHour((mi * days), TimeUtil.Separator.COLON);;
						}
						else {
							//日期类或循环
							if(rsyl =='' || rsyl == 0) {
								rsyl = '1';
							}
							 
							estimated.paramsMap.val = (rsyl * days) ;
						}
						
						//预计值：天数 * 日使用量
						estimateds.push(estimated);
						
						//2.算目标值 ：当前值 + 预计值
						var target = jQuery.extend(true, {}, estimated);
						//是时间类监控项目，转换成小时：分钟 求和
						if($.inArray(estimated.jklbh , timeArr) != -1){
							target.paramsMap.val = TimeUtil.operateTime(target.paramsMap.val, current.paramsMap.val, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD);
						}
						else{
							target.paramsMap.val = parseInt(target.paramsMap.val) + parseInt(current.paramsMap.val);
						}
						targets.push(target);
					});
					
					_this.loadEstimateds(estimateds);
					_this.loadTargets(targets);
					
					if(!endDate) {
						this.clearEstimateds();
						this.clearTargets();
					}
					
			}
		},
		
		/** 
		 * 修改截止日期
		 * 1.刷新预计值
		 * 2.刷新目标值
		 * 3.刷新查询结果
		 * */
		changeEndDate : function( ){ 
			
			var fjzch = $('#fjzch').val();
			if(fjzch==""){
				return;
			}
			
			var _this = this;
			var endDate = $('#jzrq').val();
			var planUsageMap  = _this.planUsageMap; //日使用量map
			var currents = _this.currents; //当前值
			var currentDate = _this.currentDate; //当前日期
			//缓存预备数据存在,则继续执行。
			if(planUsageMap && currents ) {
				var days = 0;
				if(endDate){
					days = TimeUtil.dateMinus(currentDate,endDate);//天数 
				}
				var estimateds = []; //预计值
				var targets = []; //目标值
				var timeArr = _this.timeArr;
				
				$.each(currents,function(idx,current){
					//1.算预计值
					var estimated = jQuery.extend(true, {}, current);
					var rsyl = planUsageMap[estimated.jklbh];
					 
					//是时间类监控项目，转换成小时：分钟
					if($.inArray(estimated.jklbh , timeArr) != -1){
						if(rsyl =='' || rsyl == 0) {
							rsyl = '1:0';
						}
						else{
							rsyl = TimeUtil.convertToHour(rsyl, TimeUtil.Separator.COLON);	
						}
						
						 var mi = TimeUtil.convertToMinute(rsyl);
						estimated.paramsMap.val = TimeUtil.convertToHour((mi * days), TimeUtil.Separator.COLON);;
					}
					else {
						//日期类或循环
						if(rsyl =='' || rsyl == 0) {
							rsyl = '1';
						}
						 
						estimated.paramsMap.val = (rsyl * days) ;
					}
					
					
					//预计值：天数 * 日使用量
					estimateds.push(estimated);
					
					//2.算目标值 ：当前值 + 预计值
					var target = jQuery.extend(true, {}, estimated);
					//是时间类监控项目，转换成小时：分钟 求和
					if($.inArray(estimated.jklbh , timeArr) != -1){
						target.paramsMap.val = TimeUtil.operateTime(target.paramsMap.val, current.paramsMap.val, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD);
					}
					else{
						target.paramsMap.val = parseInt(target.paramsMap.val) + parseInt(current.paramsMap.val);
					}
					targets.push(target);
				});
				
				_this.loadEstimateds(estimateds);
				_this.loadTargets(targets);
				
				if(!endDate) {
					this.clearEstimateds();
					this.clearTargets();
				}
			}
		},
		/** 清除预计值*/
		clearEstimateds : function( ){ 
			$('#aircraft_condition_list_table .estimated input').val('');
		},
		/** 清除目标值 */
		clearTargets : function( ){ 
			$('#aircraft_condition_list_table .target td:not(:first)').empty();
		},
		
		/*changeEndDate : function( ){ 
			var _this = this;
			var endDate = $('#jzrq').val();
			var planUsageMap  = _this.planUsageMap; //日使用量map
			var currents = _this.currents; //当前值
			var currentDate = _this.currentDate; //当前日期
			//缓存预备数据存在,则继续执行。
			if(planUsageMap && currents ) {
				var days = 0;
				if(endDate){
					days = TimeUtil.dateMinus(currentDate,endDate);//天数 
				}
				 
				var estimateds = []; //预计值
				var targets = []; //目标值
				var timeArr = _this.timeArr;
				
				$.each(currents,function(idx,current){
					//1.算预计值
					var estimated = jQuery.extend(true, {}, current);
					var rsyl = planUsageMap[estimated.jklbh];
					var val = rsyl * days;
					 
					//是时间类监控项目，转换成小时：分钟
					if($.inArray(estimated.jklbh , timeArr) != -1){
						if(val=='' || val == 0) {
							estimated.paramsMap.val = '1:0';
						}
						else{
							estimated.paramsMap.val = TimeUtil.convertToHour(val, TimeUtil.Separator.COLON);	
						}
					}
					else {
						//日期类或循环
						if(val=='' || val == 0) {
							estimated.paramsMap.val = '1';
						}
						else{
							estimated.paramsMap.val = val;	
						}
					}
					
					//预计值：天数 * 日使用量
					estimateds.push(estimated);
					
					//2.算目标值 ：当前值 + 预计值
					var target = jQuery.extend(true, {}, estimated);
					//是时间类监控项目，转换成小时：分钟 求和
					if($.inArray(estimated.jklbh , timeArr) != -1){
						target.paramsMap.val = TimeUtil.operateTime(target.paramsMap.val, current.paramsMap.val, TimeUtil.Separator.COLON, TimeUtil.Operation.ADD);
					}
					else{
						target.paramsMap.val = parseInt(target.paramsMap.val) + parseInt(current.paramsMap.val);
					}
					targets.push(target);
				});
				
				_this.loadEstimateds(estimateds);
				_this.loadTargets(targets);
				
			}
		},*/
		 
		/** 收缩-展开行*/
		toggleTr: function(element){
			var $tr = $(element);
			if($tr.hasClass('fa-angle-down')){
				$tr.removeClass('fa-angle-down');
				$tr.addClass('fa-angle-up');
				var rowid =  $tr.attr('rowid');
				$('#production_forecast_list tr[rowid='+rowid+']').show();
			}else {
				$tr.removeClass('fa-angle-up');
				$tr.addClass('fa-angle-down');
				var rowid =  $tr.attr('rowid');
				$('#production_forecast_list tr[rowid='+rowid+']').hide()
			}
		},
		/** 连接计划值*/
		genJhz: function(plans){
			var _this = this ;
			var jhzSb = '';
			if(plans) {
				$.each(plans,function(idx,plan){
					if(StringUtil.escapeStr(plan.jhz) != ''){
						 if(MonitorUnitUtil.isCal(plan.jklbh)){
							 jhzSb += '<div>'+StringUtil.escapeStr(plan.jhz)+'</div>';
						 }else {
							 var jhz = StringUtil.escapeStr(plan.jhz);
							 //是时间类监控项目，转换成小时：分钟
						 		if(MonitorUnitUtil.isTime(plan.jklbh)){
						 		jhz = TimeUtil.convertToHour(jhz, TimeUtil.Separator.COLON);
						 		}
							 jhzSb += '<div>'+jhz+MonitorUnitUtil.getMonitorUnit(plan.jklbh)+'</div>';
						 }
					}
				})
			}
			return jhzSb;
		},
		/** 连接剩余值*/
		genSyz: function(plans){
			var _this = this ;
			var syzSb = '';
			if(plans) {
				$.each(plans,function(idx,plan){
					if(StringUtil.escapeStr(plan.paramsMap.SYZ) != ''){
						if(MonitorUnitUtil.isCal(plan.jklbh)){
							syzSb += '<div>'+StringUtil.escapeStr(plan.paramsMap.SYZ)+'D'+'</div>';
						}else {
						 	var syz = StringUtil.escapeStr(plan.paramsMap.SYZ);
						 	//是时间类监控项目，转换成小时：分钟
						 	if(MonitorUnitUtil.isTime(plan.jklbh)){
						 		syz = TimeUtil.convertToHour(syz, TimeUtil.Separator.COLON);
						 	}
							syzSb += '<div>'+syz+MonitorUnitUtil.getMonitorUnit(plan.jklbh)+'</div>';
						}
						
					}
				})
			}
			return syzSb;
		},
		/** 查看任务*/
		viewRw:function(obj) {
			$obj = $(obj);
			var lylx = $obj.attr('lylx');
			var lyid = $obj.attr('lyid');
			if(lylx =='1'){
				window.open(basePath + "/project2/maintenanceproject/view?id=" + lyid);
			}
			else if(lylx =='2'){
				//eo
				window.open(basePath + "/project2/eo/view?id=" + lyid);
			}
			else if(lylx =='3'){
				//eo
				window.open(basePath + "/project2/production/view?id=" + lyid);
			}
		},
		/**
		 * 查看工卡
		 */
		viewWorkCard : function(obj){
			$obj = $(obj);
			var gkid = $obj.attr('gkid');
			window.open(basePath+"/project2/workcard/view/" + gkid );
		},
		/** 加载监控数据*/
		loadMonitorData:function(list){
			var  _this = this;
			//来源类型：1维修项目、2EO
			var lydic = {1:'维修项目',2:'EO',3:'生产指令'};
			//维修项目类型：1一般项目、2时控项目、3时寿项目、4定检包
			var wxxmdic = {1:'一般项目',2:'时控项目',3:'时寿项目',4:'定检包'};
			var $list = $('#production_forecast_list');
			var dprtcode = $('#dprtcode_search').val();
			var gzlxdic = {};
			var gzlxArr = DicAndEnumUtil.getDict(17,dprtcode);
			if(gzlxArr && (gzlxArr.length > 0)) {
				$.each(gzlxArr,function(idx,gzlx){
					gzlxdic[gzlx.id] = gzlx.name;
				});
			}
			
			var html = '';
			$.each(list,function(idx,item){
				    
				    var childNum = (item.sublist && (item.sublist.length > 0)) ? item.sublist.length : 0;
				    childNum  = childNum + 1;
				    html += '<tr>';
				    html += '<td style="vertical-align: middle;"><span>';
				    if(childNum > 1){
				    	html += '<i class="fa fa-angle-down cursor-pointer" onclick="maintenance_forecast.toggleTr(this)" rowid="'+item.JKSJID+'" ></i>';
				    }else{
				    	html += '<i class="fa fa-angle-down"></i>';
				    }
				    html += '</span><span class="badge" style="background:#3598db;">'+childNum+'</span></td>';
					html += '<td title='+StringUtil.escapeStr(item.JHRQ)+' style="text-align:center; vertical-align:middle;"> '+StringUtil.escapeStr(item.JHRQ)+'</td>';  
					var zjh = StringUtil.escapeStr(item.ZJH);
					html += '<td title="'+zjh+'" style="vertical-align: middle;"> '+zjh +'</td>';
					html += '<td title='+lydic[StringUtil.escapeStr(item.LYLX)]+' style="vertical-align: middle;"> '+lydic[StringUtil.escapeStr(item.LYLX)]+'</td>' ;
					
					var lybn = StringUtil.escapeStr(item.LYBH)+' R'+StringUtil.escapeStr(item.BB);
					 
					html += '<td title="'+lybn +'" style="vertical-align: middle;"> '
					+ '<a href="javascript:void(0);" onclick="maintenance_forecast.viewRw(this)" lyid="'+StringUtil.escapeStr(item.LYID)+'" lylx="'+StringUtil.escapeStr(item.LYLX)+'")">'
					+lybn+'</a>'
					+'</td>';
					
					html += '<td style="vertical-align: middle;" title='+StringUtil.escapeStr(item.CKH)+'>'+StringUtil.escapeStr(item.CKH)+'</td>'  
					html += '<td style="vertical-align: middle;" title='+StringUtil.escapeStr(item.RWMS)+'>'+StringUtil.escapeStr(item.RWMS)+'</td>'  
					html += '<td style="vertical-align: middle;" title='+StringUtil.escapeStr(item.FJZCH)+'/'+StringUtil.escapeStr(item.FJXLH)+'> '+StringUtil.escapeStr(item.FJZCH)+'/'+StringUtil.escapeStr(item.FJXLH)+'</td>'  
					html += '<td style="vertical-align: middle;" title='+StringUtil.escapeStr(item.BJH)+'> '+StringUtil.escapeStr(item.BJH)+'</td>'
					html += '<td style="vertical-align: middle;" title='+StringUtil.escapeStr(item.XINGH)+'> '+StringUtil.escapeStr(item.XINGH)+'</td>' 
					html += '<td style="vertical-align: middle;" title='+StringUtil.escapeStr(item.XLH)+'> '+StringUtil.escapeStr(item.XLH)+'</td>' 
					
					html += '<td style="vertical-align: middle;" title='+StringUtil.escapeStr(gzlxdic[item.GZLX])+'> '+StringUtil.escapeStr(gzlxdic[item.GZLX]) +'</td>' //
					html += '<td style="text-align:center;vertical-align: middle;" title='+StringUtil.escapeStr(wxxmdic[item.WXXMLX])+'> '+StringUtil.escapeStr(wxxmdic[item.WXXMLX]) +'</td>'  //
					
					MonitorUnitUtil.sort(item.monitoringPlans, "jklbh");
					var jhzSb = _this.genJhz(item.monitoringPlans);
					var syzSb = _this.genSyz(item.monitoringPlans);
					
					html += '<td style="vertical-align: middle;text-align:center;" title="'+jhzSb+'"> '+jhzSb +'</td>'  
					html += '<td style="vertical-align: middle;text-align:center;" title="'+syzSb+'"> '+syzSb +'</td>'
					
					
					html += '<td style="vertical-align: middle;" title='+StringUtil.escapeStr(item.GKBH)+'> '
					+ '<a href="javascript:void(0);" onclick="maintenance_forecast.viewWorkCard(this)" gkid="'+StringUtil.escapeStr(item.GKID)+'">'
					+StringUtil.escapeStr(item.GKBH)+'</a>'
					+'</td>';
					
//					html += "<td title='"+StringUtil.escapeStr(item.GKFJ)+"' style='text-align:left;vertical-align:middle;'>";
//					html += "<a href='javascript:void(0);' onclick=\"maintenance_forecast.downloadfile('"+StringUtil.escapeStr(item.GKFJID)+"')\">"+StringUtil.escapeStr(item.GKFJ)+"</a>";
//					html += "</td>";
					
					html += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
					if((item.ATTACHCOUNT != null && item.ATTACHCOUNT > 0) 
						|| (item.KGKFJID != null && item.KGKFJID != "")
						|| (item.KGZNRFJID != null && item.KGZNRFJID != "")){
						html += '<i qtid="'+item.GKID+'" gkfjid="'+item.KGKFJID+'" gznrfjid="'+item.KGZNRFJID+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
					}
					html += "</td>";
					
					html += '</tr>';  
					
					//sublist
					var sublist = item.sublist;
					if(sublist && sublist.length >0){
						$.each(sublist,function(subidx,sub){
							 
							html += '<tr rowid="'+item.JKSJID+'" style="display:none;" >';
							html += '<td > </td>' 
							html += '<td title='+StringUtil.escapeStr(sub.JHRQ)+'  style="text-align:center; vertical-align:middle;"> '+StringUtil.escapeStr(sub.JHRQ)+'</td>'  
							html += '<td style="vertical-align: middle;"  title="'+zjh+'"> '+zjh +'</td>'
							html += '<td style="vertical-align: middle;"  title='+StringUtil.escapeStr(lydic[StringUtil.escapeStr(sub.LYLX)])+'> '+lydic[StringUtil.escapeStr(sub.LYLX)]+'</td>'  
							
							html += '<td style="vertical-align: middle;"  title="'+lybn+'"> '
							+ '<a title="'+lybn+'" href="javascript:void(0);" onclick="maintenance_forecast.viewRw(this)" lyid="'+StringUtil.escapeStr(sub.LYID)+'" lylx="'+StringUtil.escapeStr(sub.LYLX)+'")">'
							+lybn+'</a>'
							+'</td>';
							
							html += '<td style="vertical-align: middle;"  title='+StringUtil.escapeStr(sub.CKH)+'> '+StringUtil.escapeStr(sub.CKH)+'</td>'  
							
							html += '<td style="vertical-align: middle;"  title='+StringUtil.escapeStr(sub.RWMS)+'> '+StringUtil.escapeStr(sub.RWMS)+'</td>'  
							html += '<td style="vertical-align: middle;"  title='+StringUtil.escapeStr(sub.FJZCH)+'/'+StringUtil.escapeStr(sub.FJXLH)+'> '+StringUtil.escapeStr(sub.FJZCH)+'/'+StringUtil.escapeStr(sub.FJXLH)+'</td>'  
							html += '<td style="vertical-align: middle;"  title='+StringUtil.escapeStr(sub.BJH)+'> '+StringUtil.escapeStr(sub.BJH)+'</td>'
							html += '<td style="vertical-align: middle;"  title='+StringUtil.escapeStr(sub.XINGH)+'> '+StringUtil.escapeStr(sub.XINGH)+'</td>' 
							html += '<td style="vertical-align: middle;"  title='+StringUtil.escapeStr(sub.XLH)+'> '+StringUtil.escapeStr(sub.XLH)+'</td>' 
							
							html += '<td style="vertical-align: middle;"  title='+StringUtil.escapeStr(gzlxdic[sub.GZLX]) +'> '+StringUtil.escapeStr(gzlxdic[sub.GZLX]) +'</td>' //
							html += '<td style="text-align:center;vertical-align: middle;"  title='+StringUtil.escapeStr(wxxmdic[sub.WXXMLX])+'> '+StringUtil.escapeStr(wxxmdic[sub.WXXMLX]) +'</td>'  //

							MonitorUnitUtil.sort(item.monitoringPlans, "jklbh");
							var jhzSb = _this.genJhz(sub.monitoringPlans);
							var syzSb = _this.genSyz(sub.monitoringPlans);
							html += '<td  title="'+jhzSb+'" style="text-align:center; vertical-align:middle;"> '+jhzSb +'</td>'  
							html += '<td title="'+syzSb+'" style="text-align:center; vertical-align:middle;"> '+syzSb +'</td>'
							
							html += '<td style="vertical-align: middle;"  title='+StringUtil.escapeStr(sub.GKBH)+'> '
							+ '<a href="javascript:void(0);" onclick="maintenance_forecast.viewWorkCard(this)" gkid="'+StringUtil.escapeStr(item.GKID)+'">'
							+StringUtil.escapeStr(sub.GKBH)+'</a>'
							+'</td>';
							
//							html += "<td title='"+StringUtil.escapeStr(sub.GKFJ)+"' style='text-align:left;vertical-align:middle;'>";
//							html += "<a href='javascript:void(0);' onclick=\"maintenance_forecast.downloadfile('"+StringUtil.escapeStr(sub.GKFJID)+"')\">"+StringUtil.escapeStr(sub.GKFJ)+"</a>";
//							html += "</td>";
							
							html += "<td title='附件 Attachment' style='text-align:center;vertical-align:middle;'>";
							if((sub.ATTACHCOUNT != null && sub.ATTACHCOUNT > 0) 
								|| (sub.KGKFJID != null && sub.KGKFJID != "")
								|| (sub.KGZNRFJID != null && sub.KGZNRFJID != "")){
								html += '<i qtid="'+sub.GKID+'" gkfjid="'+sub.KGKFJID+'" gznrfjid="'+sub.KGZNRFJID+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
							}
							html += "</td>";
							
							html += '</tr>';  
							
							html += '</tr>';  
						})
					}
		     })
			$list.html(html);
			
			_this.initWebuiPopover();
		},
		/** 获取产生计划日期的监控项目*/
		getMonitorItem:function(item){
			var limit = {};
			var limit_jklbh = item.limit_jklbh;
			var plans = item.monitoringPlans;
			if(limit_jklbh){
				for (var i = 0 ; i < plans.length ; i++)
				{
					if(plans[i].jklbh == limit_jklbh){
						limit = plans[i];
						break;
					}
				}
			}
			return limit;
		},
		/** 查询预测计划*/
		search: function(element) {
			var _this = this;
			var fjzch = $('#fjzch').val();
			var jzrq = $('#jzrq').val();
			
			if(!jzrq){
				AlertUtil.showErrorMessage("请输入截止日期!");
				$("#production_forecast_list").empty();
				$("#production_forecast_list").append("<tr><td class=\"text-center\"  colspan=\"20\">暂无数据 No data.</td></tr>");
				return false;
			}
			var dprtcode = $('#dprtcode_search').val();
			var keyword = $.trim($('#keyword_search').val());
			var lylx = $('#lylx').val();
			
			var param = {
				   fjzch : fjzch,
				   dprtcode : dprtcode,
				   lylx : lylx
			   }
			var paramsMap = {};
			
			var targetTr = $('#aircraft_condition_list .target');
			var tds = targetTr.find('td:gt(0)');
			//向参数中追加目标值
			$.each(tds,function(idx,td){
				var  val = $(td).html();
				var  wz = $(td).attr('wz');
				var  jklbh = $(td).attr('jklbh');
				//非发动机类：监控类编号对应String
				if($(td).attr('wz') == '-') {
					paramsMap[jklbh] = $(td).html();
				}
				else {
					//发动机类:监控类编号对应集合对象(包含位置，目标值)	
					if(!(jklbh in paramsMap)) {
						paramsMap[jklbh] = {};
					}
					paramsMap[jklbh][wz] = val;
				}
			});
			paramsMap['1_10'] = jzrq;
			paramsMap['keyword'] = keyword;
			param.paramsMap = paramsMap;
		  
			AjaxUtil.ajax({
				   url:basePath+"/produce/maintenance/forecast/queryList",
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(param),
				   success:function(data){ 
					   var $list = $('#production_forecast_list');
					   $list.empty();
					   if(data.list && (data.list.length > 0)){
						   _this.loadMonitorData(data.list);	
						   signByKeyword(keyword, [5,6,7,9,10,11],"#production_forecast_list_table tbody tr td");
					   }
					   else {
						   $("#production_forecast_list").append("<tr><td class=\"text-center\"  colspan=\"20\">暂无数据 No data.</td></tr>");   
					   }
				   }
			   });
		
		},
		downloadfile : function(id){
			PreViewUtil.handle(id, PreViewUtil.Table.D_011);
		}
	}
	
	

    function showTrToggle(obj,trClass){
    	if($(obj).find("i").hasClass("fa-angle-double-down")){
    		$(obj).find("i").removeClass("fa-angle-double-down");
    		$(obj).find("i").addClass("fa-angle-double-up");
    		$("#aircraft_condition_list").find("tr").css("display","none");
    		$("#aircraft_condition_list").find("tr."+trClass).css("display","table-row");
    		$("#aircraft_condition_list_table").find("thead tr").css("display","none");
    	}else{
    		$(obj).find("i").removeClass("fa-angle-double-up");
    		$(obj).find("i").addClass("fa-angle-double-down");
    		$("#aircraft_condition_list").find("tr").css("display","table-row");
    		$("#aircraft_condition_list_table").find("thead tr").css("display","table-row");
    		
    		
    	}
    	 $("#iconToggleTable").css({"height":$("#aircraft_condition_list_table").outerHeight()+"px","line-height":$("#aircraft_condition_list_table").outerHeight()+"px"});
    	 App.resizeHeight();
    }
    
   function customResizeHeight(bodyHeight, tableHeight){
	   // 关键字高度
       var rowHeight = $('.row-height:visible').outerHeight()||0;
       var searchContentHeight=$("#forecast_list_panel .searchContent:visible").outerHeight()||0
       if(navigator.userAgent.indexOf("Chrome") > -1){
    	   bodyHeight -= 5;
       }
       $("#forecast_list_panel").find("table").parent("div").css("height",(bodyHeight-rowHeight-searchContentHeight-20)+"px");
   }
   
	function exportExcel(){
		var _this = this;
		var fjzch = $('#fjzch').val();
		var jzrq = $('#jzrq').val();
		
		if(!jzrq){
			AlertUtil.showErrorMessage("请输入截止日期!");
			$("#production_forecast_list").empty();
			$("#production_forecast_list").append("<tr><td class=\"text-center\"  colspan=\"20\">暂无数据 No data.</td></tr>");
			return false;
		}
		var dprtcode = $('#dprtcode_search').val();
		var keyword = $.trim($('#keyword_search').val());
		var lylx = $('#lylx').val();
		
		var param = {
			   fjzch : fjzch,
			   dprtcode : dprtcode,
			   lylx : lylx
		   }
		var paramsMap = {};
		
		var targetTr = $('#aircraft_condition_list .target');
		var tds = targetTr.find('td:gt(0)');
		//向参数中追加目标值
		$.each(tds,function(idx,td){
			var  val = $(td).html();
			var  wz = $(td).attr('wz');
			var  jklbh = $(td).attr('jklbh');
			//非发动机类：监控类编号对应String
			if($(td).attr('wz') == '-') {
				paramsMap[jklbh] = $(td).html();
			}
			else {
				//发动机类:监控类编号对应集合对象(包含位置，目标值)	
				if(!(jklbh in paramsMap)) {
					paramsMap[jklbh] = {};
				}
				paramsMap[jklbh][wz] = val;
			}
		});
		paramsMap['1_10'] = jzrq;
		paramsMap['keyword'] = keyword;
		param.paramsMap = paramsMap;
		
		window.open(basePath+"/produce/maintenance/forecast/forecast.xls?paramjson="+JSON.stringify(param));
		
	}