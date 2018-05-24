/**
 * 上次计划
 */
last_plan_view = {
	id:'last_plan_view',
	/**
	 * 加载数据
	 * id任务id
	 */
	load : function(obj, id){
		var data = obj.monitoringLastList;
		var this_ = this;
		var last_thead = '';
		var last_tbody = '';
		var xs = 0;
		$("#"+this_.id).hide();
		if(data != null && data.length > 0 && formatUndefine(obj.fJksjid) != ''){
			$("#"+this_.id).show();
			last_thead += '<tr>';
			last_thead += '<th class="colwidth-9" >监控数据</th>';
			var tr1 = "<tr>";
			var tr2 = "<tr>";
			tr1 += "<td style='text-align:center;vertical-align:middle;'>上次计划</th>";
			tr2 += "<td style='text-align:center;vertical-align:middle;'>上次实际</th>";
			$.each(data, function(index,row){
				xs += 2;
				var jkx = StringUtil.escapeStr(MonitorUnitUtil.getMonitorName(row.jklbh));
				var scjh = this_.formatJkz(row.jklbh, row.scjhz);
				var scsj = this_.formatJkz(row.jklbh, row.scsjz);
				last_thead += "<th>"+jkx+"</th>";
				tr1 += "<td title='"+scjh+"' style='text-align:center;vertical-align:middle;'>"+scjh+"</td>";
				tr2 += "<td title='"+scsj+"' style='text-align:center;vertical-align:middle;'>"+scsj+"</td>";
			});
			last_thead += '</tr>';
			tr1 += '</tr>';
			tr2 += '</tr>';
			last_tbody = tr1 + tr2;
			$("#last_table_div", $("#" + id)).removeClass("col-xs-6").removeClass("col-xs-4").removeClass("col-xs-2").addClass("col-xs-"+xs);
			$("#last_thead", $("#" + id)).html(last_thead);
			$("#last_tbody", $("#" + id)).html(last_tbody);
		}
	},
	/**
	 * 格式化监控值
	 */
	formatJkz : function(jklbh, value){
		if(value != null && value != ''){
			value = this.convertToHour(jklbh, value) + MonitorUnitUtil.getMonitorUnit(jklbh, "");
		}
		return formatUndefine(value);
	},
	/**
	 * 分钟转小时
	 */
	convertToHour : function(jklbh, value){
		if(MonitorUnitUtil.isTime(jklbh)){
			value = TimeUtil.convertToHour(value, TimeUtil.Separator.COLON);
		}
		return value;
	},
	getThead : function(){
		var html = '';
		html += '<th class="colwidth-7" >';
		html += '<div class="font-size-12 line-height-12">监控项</div>';
		html += '<div class="font-size-9 line-height-12">Monitoring</div>';
		html += '</th>';
		html += '<th class="colwidth-13" >';
		html += '<div class="font-size-12 line-height-12">上次计划</div>';
		html += '<div class="font-size-9 line-height-12">Last Plan</div>';
		html += '</th>';
		html += '<th class="colwidth-13" >';
		html += '<div class="font-size-12 line-height-12">上次实际</div>';
		html += '<div class="font-size-9 line-height-12">Last Actual</div>';
		html += '</th>';
		return html;
	}
};