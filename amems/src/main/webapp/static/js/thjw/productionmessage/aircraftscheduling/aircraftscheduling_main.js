	
	 $(function(){
		 Navigation(menuCode);
		 $('input.date-range-picker').daterangepicker().prev().on("click", function() {
				$(this).next().focus();
		 });
		 $('#aircraftschedulingDate').on('apply.daterangepicker',function(ev, picker) {
			 refreshSchedulingTable();
		});
		 $(".cancelBtn").hide();
		setDefaultDateRangePickerValue();
		//初始化日志功能
		logModal.init({code:'B_S_010'});
	 });
	 
	/**
	 * 显示飞机排班维护模态框
	 * @param plane
	 * @param date
	 */
	function showUpdateModal(btn, date){
		var plane = $(btn).attr("fjzch");
		$("#fjzch_updateModal").text(plane);
		$("#rq_updateModal").text(date);
		$("#updateModal").modal("show");
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/productionmessage/aircraftscheduling/loadPlaneScheduling",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify({
			    pbrq : $("#rq_updateModal").html(),
			    dprtcode : $("#dprtcode").val(),
				crewScheduleObject : {
					dxid : $("#fjzch_updateModal").text()
				}
		   }),
		   success:function(data){
		     finishWait();
		     $("#updateModalBody").empty();
		     if(data.length > 0){
		    	 for(var i = 0; i < data.length; i++){
		    		 if(i==0){
		    			 buildSchedulingRow(data[i].sbsj ,data[i].xbsj, false);
		    		 }else{
		    			 buildSchedulingRow(data[i].sbsj ,data[i].xbsj, true);
		    		 }
		    	 }
		    	 calcTotal();
		    	 $("#state_updateModal").val(2);
		     }else{
		    	 buildSchedulingRow(null,null,false);
		    	 $("#state_updateModal").val(1);
		     }
	      }
	    }); 
	}
	
	/**
	 * 删除飞机排班时间
	 * @param btn
	 */
	function deleteRow(btn){
		$(btn).parent().parent().remove();
	}
	
	/**
	 * 构造排班时间row
	 * @param sbsj
	 * @param xbsj
	 */
	function buildSchedulingRow(sbsj, xbsj, canDelete){
		var sbsjXs = sbsj ? sbsj.substr(11,2) : 0;
		var xbsjXs = xbsj ? xbsj.substr(11,2) : 23;
		canDelete = canDelete == null ? true : canDelete;
		$("#updateModalBody").append([
      				'<div class="row"  style="padding-bottom: 5px;">',
      				'<div class="col-lg-1 padding-right-0">',
      				canDelete?'	<button class="btn btn-default btn-xs" type="button" onclick="deleteRow(this)" title="删除飞机排班">':'',
      				canDelete?'		<i class="icon-minus cursor-pointer color-default cursor-pointer"></i>':'',
      				canDelete?'	</button>':'',
      				'</div>',
      				'<div class="col-lg-4 padding-right-0">',
      				'	<select class="form-control input-sm" name="beginTime" onchange="calcTotal(this)">',
      				'		<option value="0"'+(parseInt(sbsjXs)==0?' selected="selected"':'')+'>0</option>',
      				'		<option value="1"'+(parseInt(sbsjXs)==1?' selected="selected"':'')+'>1</option>',
      				'		<option value="2"'+(parseInt(sbsjXs)==2?' selected="selected"':'')+'>2</option>',
      				'		<option value="3"'+(parseInt(sbsjXs)==3?' selected="selected"':'')+'>3</option>',
      				'		<option value="4"'+(parseInt(sbsjXs)==4?' selected="selected"':'')+'>4</option>',
      				'		<option value="5"'+(parseInt(sbsjXs)==5?' selected="selected"':'')+'>5</option>',
      				'		<option value="6"'+(parseInt(sbsjXs)==6?' selected="selected"':'')+'>6</option>',
      				'		<option value="7"'+(parseInt(sbsjXs)==7?' selected="selected"':'')+'>7</option>',
      				'		<option value="8"'+(parseInt(sbsjXs)==8?' selected="selected"':'')+'>8</option>',
      				'		<option value="9"'+(parseInt(sbsjXs)==9?' selected="selected"':'')+'>9</option>',
      				'		<option value="10"'+(parseInt(sbsjXs)==10?' selected="selected"':'')+'>10</option>',
      				'		<option value="11"'+(parseInt(sbsjXs)==11?' selected="selected"':'')+'>11</option>',
      				'		<option value="12"'+(parseInt(sbsjXs)==12?' selected="selected"':'')+'>12</option>',
      				'		<option value="13"'+(parseInt(sbsjXs)==13?' selected="selected"':'')+'>13</option>',
      				'		<option value="14"'+(parseInt(sbsjXs)==14?' selected="selected"':'')+'>14</option>',
      				'		<option value="15"'+(parseInt(sbsjXs)==15?' selected="selected"':'')+'>15</option>',
      				'		<option value="16"'+(parseInt(sbsjXs)==16?' selected="selected"':'')+'>16</option>',
      				'		<option value="17"'+(parseInt(sbsjXs)==17?' selected="selected"':'')+'>17</option>',
      				'		<option value="18"'+(parseInt(sbsjXs)==18?' selected="selected"':'')+'>18</option>',
      				'		<option value="19"'+(parseInt(sbsjXs)==19?' selected="selected"':'')+'>19</option>',
      				'		<option value="20"'+(parseInt(sbsjXs)==20?' selected="selected"':'')+'>20</option>',
      				'		<option value="21"'+(parseInt(sbsjXs)==21?' selected="selected"':'')+'>21</option>',
      				'		<option value="22"'+(parseInt(sbsjXs)==22?' selected="selected"':'')+'>22</option>',
      				'		<option value="23"'+(parseInt(sbsjXs)==23?' selected="selected"':'')+'>23</option>',
      				'	</select>',
      				'</div>',
      				'<div class="col-lg-4 padding-right-0">',
      				'	<select class="form-control input-sm" name="endTime" onchange="calcTotal(this)">',
      				'		<option value="0"'+(parseInt(xbsjXs)==0?' selected="selected"':'')+'>0</option>',
      				'		<option value="1"'+(parseInt(xbsjXs)==1?' selected="selected"':'')+'>1</option>',
      				'		<option value="2"'+(parseInt(xbsjXs)==2?' selected="selected"':'')+'>2</option>',
      				'		<option value="3"'+(parseInt(xbsjXs)==3?' selected="selected"':'')+'>3</option>',
      				'		<option value="4"'+(parseInt(xbsjXs)==4?' selected="selected"':'')+'>4</option>',
      				'		<option value="5"'+(parseInt(xbsjXs)==5?' selected="selected"':'')+'>5</option>',
      				'		<option value="6"'+(parseInt(xbsjXs)==6?' selected="selected"':'')+'>6</option>',
      				'		<option value="7"'+(parseInt(xbsjXs)==7?' selected="selected"':'')+'>7</option>',
      				'		<option value="8"'+(parseInt(xbsjXs)==8?' selected="selected"':'')+'>8</option>',
      				'		<option value="9"'+(parseInt(xbsjXs)==9?' selected="selected"':'')+'>9</option>',
      				'		<option value="10"'+(parseInt(xbsjXs)==10?' selected="selected"':'')+'>10</option>',
      				'		<option value="11"'+(parseInt(xbsjXs)==11?' selected="selected"':'')+'>11</option>',
      				'		<option value="12"'+(parseInt(xbsjXs)==12?' selected="selected"':'')+'>12</option>',
      				'		<option value="13"'+(parseInt(xbsjXs)==13?' selected="selected"':'')+'>13</option>',
      				'		<option value="14"'+(parseInt(xbsjXs)==14?' selected="selected"':'')+'>14</option>',
      				'		<option value="15"'+(parseInt(xbsjXs)==15?' selected="selected"':'')+'>15</option>',
      				'		<option value="16"'+(parseInt(xbsjXs)==16?' selected="selected"':'')+'>16</option>',
      				'		<option value="17"'+(parseInt(xbsjXs)==17?' selected="selected"':'')+'>17</option>',
      				'		<option value="18"'+(parseInt(xbsjXs)==18?' selected="selected"':'')+'>18</option>',
      				'		<option value="19"'+(parseInt(xbsjXs)==19?' selected="selected"':'')+'>19</option>',
      				'		<option value="20"'+(parseInt(xbsjXs)==20?' selected="selected"':'')+'>20</option>',
      				'		<option value="21"'+(parseInt(xbsjXs)==21?' selected="selected"':'')+'>21</option>',
      				'		<option value="22"'+(parseInt(xbsjXs)==22?' selected="selected"':'')+'>22</option>',
      				'		<option value="23"'+(parseInt(xbsjXs)==23?' selected="selected"':'')+'>23</option>',
      				'	</select>',
      				'</div>',
      				'<div class="col-lg-3 padding-right-0">',
          			'	<div class="font-size-16" style="line-height: 30px;" name="totalTime">0:00~23:59（24h）</div>',
          			'</div>',
      				'</div>'].join(''));
	}
	
	/**
	 * 计算总计
	 */
	function calcTotal(select){
		if(select){
			var row = $(select).parent().parent();
			var beginTime = row.find("select[name='beginTime']").val();
			var endTime = row.find("select[name='endTime']").val();
			var total = parseInt(endTime) - parseInt(beginTime) + 1;
			row.find("div[name='totalTime']").html(beginTime+":00~"+endTime+":59（"+total+"h）");
		}else{
			$("#updateModalBody>div.row").each(function(){
				var row = $(this);
				var beginTime = row.find("select[name='beginTime']").val();
				var endTime = row.find("select[name='endTime']").val();
				var total = parseInt(endTime) - parseInt(beginTime) + 1;
				row.find("div[name='totalTime']").html(beginTime+":00~"+endTime+":59（"+total+"h）");
			});
		}
	}
	
	/**
	 * 显示飞机视图模态框
	 * @param plane
	 * @param date
	 */
	function showPlaneViewModal(btn){
		var plane = $(btn).attr("fjzch");
		$("#planeViewModal").modal("show");
		$("#outerDiv").height($(window).height()-300);
		startWait();
		// 排班时间范围
		var schedulingDate = $.trim($("#aircraftschedulingDate").val());
		// 时间范围对应的日期数组
		var beginDate = schedulingDate.substring(0,10);
		var endDate = schedulingDate.substring(13,23);
		var timeArr = getTimeArr(beginDate, endDate);
		AjaxUtil.ajax({
		   url:basePath+"/productionmessage/aircraftscheduling/queryPlaneSchedulingInPlaneView",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify({
				fjzch : plane,
			    pbrqBegin : beginDate,
			    pbrqEnd : endDate,
			    dprtcode : $("#dprtcode").val()
		   }),
		   success:function(data){
		     finishWait();
		     $("#planeViewBody").empty();
		     var schedules = data.schedules
		     for(var i = 0; i < timeArr.length; i++){
		    	 // 获取指定日期的排班数据
		    	 var dayList = getDayList(schedules, timeArr[i]);
		    	 if(dayList.length == 0){
		    		 $("#planeViewBody").append([
				    		 '<tr>',
							 '	<td>'+timeArr[i]+'</td>',
							 '	<td colspan="24">',
							 '		<div style="height: 30px;vertical-align: middle;">休息</div>',
							 '	</td>',
							 '	<td></td>',
							 '	<td></td>',
							 '</tr>'].join(""));
		    	 }else{
		    		 var list = [];
		    		 for(var k =0; k < dayList.length; k++){
		    			 list = list.concat(getscheduleArr(dayList[k]));
		    		 }
		    		 // 去除重复时间
		    		 list = removeRepeat(list);
		    		 if(list.length == 24){
		    			 $("#planeViewBody").append([
					    		 '<tr>',
								 '	<td>'+timeArr[i]+'</td>',
								 '	<td colspan="24">',
								 '		<div style="height: 30px;vertical-align: middle;">全天出勤</div>',
								 '	</td>',
								 '	<td title="'+dayList[0].whr.displayName+'">'+substring0To12(dayList[0].whr.displayName)+'</td>',
								 '	<td>'+dayList[0].whsj+'</td>',
								 '</tr>'].join(""));
		    		 }else{
		    			 $("#planeViewBody").append([
								'<tr>',
								'<td>'+timeArr[i]+'</td>',
								'<td colspan="24">',
								'	<div class="progress">',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(0, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(1, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(2, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(3, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(4, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(5, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(6, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(7, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(8, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(9, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(10, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(11, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(12, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(13, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(14, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(15, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(16, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(17, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(18, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(19, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(20, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(21, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1666%;'+($.inArray(22, list) == -1?"background-color: white;":"")+'"></div>',
								'	  <div class="progress-bar" style="width: 4.1682%;'+($.inArray(23, list) == -1?"background-color: white;":"")+'"></div>',
								'	</div>',
								'</td>',
								'<td title="'+dayList[0].whr.displayName+'">'+substring0To12(dayList[0].whr.displayName)+'</td>',
								'<td>'+dayList[0].whsj+'</td>',
								'</tr>'].join(""));
		    		 }
		    	 }
	    	}
	      }
	    });
		
		/**
		 * 获取指定日期的排班数据
		 */
		function getDayList(schedules, targetDay){
			var dayList = [];
	    	for(var j = 0; j < schedules.length; j++){
	    		 var pbrq = schedules[j].pbrq;
	    		 if(pbrq.length > 10){
		    			pbrq = pbrq.substr(0,10);
		    	 }
	    		 if(pbrq == targetDay){
	    			 dayList.push(schedules[j]);
	    		 }
	    	}
	    	return dayList;
		}
		
		/**
		 * 获取上班时间~下班时间之间的小时集合
		 */
		function getscheduleArr(schedule){
			var result = [];
			var sbsjXs = parseInt(schedule.sbsj.substr(11,2));
			var xbsjXs = parseInt(schedule.xbsj.substr(11,2));
			for(var i = sbsjXs; i <= xbsjXs; i++){
				result.push(i);
			}
			return result;
		}
		
		/**
		 * 数组去重
		 */
		function removeRepeat(arr){
			arr.sort();
			var re=[arr[0]];
			for(var i = 1; i < arr.length; i++)
			{
				if( arr[i] !== re[re.length-1])
				{
					re.push(arr[i]);
				}
			}
			return re;
		}
	}
	
	/**
	 * 设置默认的飞机排班时间
	 */
	function setDefaultDateRangePickerValue(){
		TimeUtil.getCurrentDate(function(date) {
			var startDate = date; 
			var endDate = addDays(addMonths(date, 1), -1);
			$("#aircraftschedulingDate").data('daterangepicker').setStartDate(startDate);
			$("#aircraftschedulingDate").data('daterangepicker').setEndDate(endDate);
			$("#aircraftschedulingDate").val(startDate+" ~ "+endDate);
			refreshBase();
		});
		
		function addMonths(date, months){ 
			var d=new Date(Date.parse(date.replace(
					/-/g, "/"))); 
			d.setMonth(d.getMonth()+months); 
			var month=d.getMonth()+1; 
			var day = d.getDate(); 
			if(month<10){ 
			month = "0"+month; 
			} 
			if(day<10){ 
			day = "0"+day; 
			} 
			var val = d.getFullYear()+"-"+month+"-"+day; 
			return val; 
		}
		
		function addDays(date, days){ 
			var d=new Date(date); 
			d.setDate(d.getDate()+days); 
			var month=d.getMonth()+1; 
			var day = d.getDate(); 
			if(month<10){ 
			month = "0"+month; 
			} 
			if(day<10){ 
			day = "0"+day; 
			} 
			var val = d.getFullYear()+"-"+month+"-"+day; 
			return val; 
		}
	}
	
	/**
	 * 刷新基地
	 */
	function refreshBase(){
		AjaxUtil.ajax({
			   url:basePath+"/productionmessage/aircraftscheduling/queryBase",
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify({
				   dprtcode : $("#dprtcode").val()
			   }),
			   success:function(data){
				   $("#jd_search").empty();
				   if(data && data.length > 0){
					   $("#jd_search").append('<option value="">显示全部All</option>');
					   for(var i = 0; i < data.length; i++){
						   $("#jd_search").append('<option value="'+data[i].id+'">'+StringUtil.escapeStr(data[i].jdms)+'</option>');
					   }
				   }else{
					   $("#jd_search").append('<option value="">暂无基地</option>');
				   }
				   refreshSchedulingTable();
		      }
		    }); 
	}
	
	/**
	 * 刷新飞机排班表
	 */
	function refreshSchedulingTable(){
		// 排班时间范围
		var schedulingDate = $.trim($("#aircraftschedulingDate").val());
		// 时间范围对应的日期数组
		var beginDate = schedulingDate.substring(0,10);
		var endDate = schedulingDate.substring(13,23);
		var timeArr = getTimeArr(beginDate, endDate);
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/productionmessage/aircraftscheduling/queryPlaneScheduling",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify({
			   dprtcode : $("#dprtcode").val(),
			   jd : $("#jd_search").val(),
			   paramsMap : {
				   pbrqBegin : beginDate,
				   pbrqEnd : endDate
			   }
		   }),
		   success:function(data){
		     finishWait();
		     $("#schedulingTable").empty();
		     // 拼接thead
		     assembleThead();
		     // 拼接tbody
		     assembleTbody(data);
			 
	      }
	    }); 
		
		/**
		 * 拼接tbody
		 */
		function assembleTbody(data){
			
			
			var html = '<tbody>';
			if(data.length >0){
			    for(var i = 0; i < data.length; i++){
			    	var workingArr = [];
			    	for(var k = 0; k < data[i].schedules.length; k++){
			    		var pbrq = (data[i].schedules)[k].pbrq;
			    		if(pbrq.length > 10){
			    			pbrq = pbrq.substr(0,10);
			    		}
			    		workingArr.push(pbrq);
			    	}
			    	html += "<tr>";
			    	html += "<td style='text-align:left'><a href='javascript:void(0);' fjzch='"+StringUtil.escapeStr(data[i].fjzch)+"' onclick='showPlaneViewModal(this)'>"+StringUtil.escapeStr(data[i].fjzch)+"</a></td>";
			    	for(var j = 0; j < timeArr.length; j++){
			    		if($.inArray(timeArr[j], workingArr) == -1){
			    			html += "<td style=\"cursor: pointer;\" fjzch='"+StringUtil.escapeStr(data[i].fjzch)+"' onclick=\"showUpdateModal(this,'"+timeArr[j]+"')\"></td>";
			    		}else{
			    			html += "<td style=\"cursor: pointer;\" fjzch='"+StringUtil.escapeStr(data[i].fjzch)+"' onclick=\"showUpdateModal(this,'"+timeArr[j]+"')\"><i class=\"icon-ok\"></i></td>";
			    		}
			    	}
			    	html += "</tr>";
			    }
			} else {
				    html += "<tr><td colspan=\""+(timeArr.length+1)+"\">暂无数据 No data.</td></tr>";
			}
			html += "</tbody>";
			$("#schedulingTable").append(html);
		}
		
		/**
		 * 拼接thead
		 */
		function assembleThead(){
			 $("#schedulingTable").append([
   		  	 		'<thead>',
   		  	 		'<tr>',
   		  	 		'<th style="width: 100px;padding: 0px;">',
   		  	 		'	<div class="out">',
   		  	 		'		<b>日期</b>',
   		  	 		'		<em>飞机</em>',
   		  	 		'	</div>',
   		  	 		'</th>',
   		  	 		assembleTh(),
   		  	 		'</tr>',
   		  	 		'</thead>'
   		  	 	  ].join(""));
		}
		
		
		/**
		 * 拼接td
		 */
		function assembleTh(){
			var res = [];
			for(var i = 0; i < timeArr.length; i++){
				res.push('<th style="min-width: 80px;vertical-align:middle;">'+timeArr[i]+'</th>');
			}
			return res;
		}
	}
	
	/**
	 * 获取2个日期之间的所有日期
	 * @param start
	 * @param end
	 * @returns {Array}
	 */
	function getTimeArr(start,end){
		var startTime = getDate(start);
		var endTime = getDate(end);
		var resultArr = [];
		while((endTime.getTime()-startTime.getTime())>=0){
		  var year = startTime.getFullYear();
		  var month = (startTime.getMonth()+1).toString();
		  month = month.length==1?"0"+month:month;
		  var day = startTime.getDate().toString().length==1?"0"+startTime.getDate():startTime.getDate();
		  resultArr.push(year+"-"+month+"-"+day);
		  startTime.setDate(startTime.getDate()+1);
		}
		return resultArr;
	}
	
	function getDate(datestr){
	  var temp = datestr.split("-");
	  var date = new Date(temp[0],temp[1]-1,temp[2]);
	  return date;
	}
	
	/**
	 * 保存排班
	 */
	function save(){
		var param = [];
		var timeArr = [];
		var pbsjValidate = false;
		$("#updateModalBody>div.row").each(function(){
			var row = $(this);
			var pbrq = $("#rq_updateModal").html();
			var beginTime = row.find("select[name='beginTime']").val();
			var endTime = row.find("select[name='endTime']").val();
			if(parseInt(beginTime) > parseInt(endTime)){
				pbsjValidate = true;
				return false;
			}
			param.push({
				sbsjXs : beginTime,
				xbsjXs : endTime,
				jd : $("#jd_search").val(),
				pbrq : pbrq,
				dprtcode : $("#dprtcode").val(),
				crewScheduleObject : {
					dxid : $("#fjzch_updateModal").text()
				},
				operation : $("#state_updateModal").val()
				
			});
			for(var i = parseInt(beginTime); i <= parseInt(endTime);i++){
				timeArr.push(i);
			}
		});
		if(pbsjValidate){
			AlertUtil.showErrorMessage("开始时间不能大于结束时间！");
			return false;
		}
		
		if(isRepeat(timeArr)){
			AlertUtil.showErrorMessage("包含重复的飞机排班时间！");
			return false;
		}
		
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/productionmessage/aircraftscheduling/savePlaneScheduling",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(param),
		   success:function(data){
		     finishWait();
		     AlertUtil.showMessage("保存飞机排班数据成功!");
		     refreshSchedulingTable();
		     $("#updateModal").modal("hide");
	      }
	    }); 
		
		function isRepeat(arr){
			var result = false;
			var nary=arr.sort();  
			for(var i=0;i<nary.length;i++){  
				if (nary[i]==nary[i+1]){  
					result = true;
					break;
				}  
			}
			return result;
		}
	}
	
	/**
	 * 取消排班
	 */
	function cancel(){
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/productionmessage/aircraftscheduling/cancelPlaneScheduling",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify({
			    pbrq : $("#rq_updateModal").html(),
				crewScheduleObject : {
					dxid : $("#fjzch_updateModal").text()
				}
		   }),
		   success:function(data){
		     finishWait();
		     AlertUtil.showMessage("取消飞机排班数据成功!");
		     refreshSchedulingTable();
		     $("#updateModal").modal("hide");
	      }
	    }); 
	}
	
	function substring0To12(str){
		if(str!="" && str!=null ){
			if(str.length>=12){
				return str.substring(0,12)+"...";
			}
			return str;
		}
		return str;
	}