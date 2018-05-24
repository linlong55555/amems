

var MonitorUnitUtil = {
		
		sortObj : {
			"1_10" : 1,
			"2_10_FH" : 2,
			"2_20_AH" : 4,
			"2_30_EH" : 6,
			"3_10_FC" :3,
			"3_20_AC" : 5,
			"3_30_EC" : 7
		},
		
		unitObj : {
			"1_10" : {
				name : "日历",
				code : "CAL",
				value : "10",
				unit : "",
				subitem : [{
					name : "日",
					code : "D",
					value : "11",
					unit : "D",
				},{
					name : "月",
					code : "M",
					value : "12",
					unit : "M",
				}]
			},
			
			"2_10_FH" : {
				name : "飞行时间",
				code : "FH",
				value : "20",
				unit : "FH",
			},
			
			"2_20_AH" : {
				name : "APU时间",
				code : "APU.H",
				value : "20",
				unit : "APUH",
			},
			
			"2_30_EH" : {
				name : "发动机时间",
				code : "ENG.H",
				value : "20",
				unit : "EH",
			},
			
			"3_10_FC" :{
				name : "飞行循环",
				code : "FC",
				value : "30",
				unit : "FC",
			},
			
			"3_20_AC" : {
				name : "APU循环",
				code : "APU.C",
				value : "30",
				unit : "APUC",
			},
			
			"3_30_EC" : {
				name : "发动机循环",
				code : "ENG.C",
				value : "30",
				unit : "EC",
			}
		},
		
		// 通过监控类编号获取监控类对象
		getMonitorObjByValue : function(jklbh, value){
			var obj = this.unitObj[jklbh] || {};
			if(obj.subitem && value){
				$.each(obj.subitem, function(){
					if(this.value == value){
						obj = this;
						return false;
					}
				});
			}
			return obj;
		},
		
		// 通过监控类编号获取监控类对象
		getMonitorObjByCode : function(jklbh, code){
			var obj = this.unitObj[jklbh] || {};
			if(obj.subitem && code){
				$.each(obj.subitem, function(){
					if(this.code == code){
						obj = this;
						return false;
					}
				});
			}
			return obj;
		},
		
		// 通过监控类编号获取监控类名称
		getMonitorName : function(jklbh, value){
			return this.getMonitorObjByValue(jklbh, value).name || "";
		},
		
		// 通过监控类编号获取监控单编码
		getMonitorCode : function(jklbh, value){
			return this.getMonitorObjByValue(jklbh, value).code || "";
		},
		
		// 通过监控类编号获取监控单位值(英文)
		getMonitorValue : function(jklbh, code){
			return this.getMonitorObjByCode(jklbh, code).value || "";
		},
		
		// 通过监控类编号获取监控单位值(中文)
		getMonitorUnit : function(jklbh, value){
			return this.getMonitorObjByValue(jklbh, value).unit || "";
		},
		
		// 是否日历
		isCal : function(jklbh){
			return jklbh == "1_10";
		},
		
		// 是否时间格式
		isTime : function(jklbh){
			return jklbh == "2_20_AH" 
					|| jklbh == "2_10_FH"
					|| jklbh == "2_30_EH";
		},
		
		// 是否循环格式
		isLoop : function(jklbh){
			return jklbh == "3_10_FC" 
				|| jklbh == "3_20_AC"
				|| jklbh == "3_30_EC";
		},
		
		// 分钟转换为小时
		switchMinuteToHour : function(obj){
			if(this.isTime(obj.jklbh)){
				obj.scz = TimeUtil.convertToHour(obj.scz, TimeUtil.Separator.COLON);
				obj.zqz = TimeUtil.convertToHour(obj.zqz, TimeUtil.Separator.COLON);
				obj.ydzQ = TimeUtil.convertToHour(obj.ydzQ, TimeUtil.Separator.COLON);
				obj.ydzH = TimeUtil.convertToHour(obj.ydzH, TimeUtil.Separator.COLON);
			}
		},
		
		/**
		 * 排序
		 * dataList 数据集合
		 * field 排序字段名称
		 */
		sort : function(dataList, field){
			if(dataList != null && dataList.length > 0 && field != null){
				dataList.sort(this.compare(field));
			}
			return dataList;
		},
		/**
		 * 排序比较
		 */
		compare : function(property){
			var this_ = this;
			return function(a,b){
				var value1 = this_.getPxhByJklbh(a[property]);
				var value2 = this_.getPxhByJklbh(b[property]);
		        return value1 - value2;
		    }
		},
		

		/**
		 * 排序,根据字符串集合
		 * dataList 数据集合
		 */
		sortByStrList : function(dataList){
			if(dataList != null && dataList.length > 0){
				dataList.sort(this.compareStr());
			}
			return dataList;
		},
		/**
		 * 排序比较
		 */
		compareStr : function(){
			var this_ = this;
			return function(a,b){
				var aArr = a.split("#_#");
				var bArr = b.split("#_#");
				var value1 = this_.getPxhByJklbh(aArr[0]);
				var value2 = this_.getPxhByJklbh(bArr[0]);
		        return value1 - value2;
		    }
		},
		
		/**
		 * 通过监控类编号获取排序号
		 */
		getPxhByJklbh : function(jklbh){
			var pxh = this.sortObj[jklbh];
			return pxh;
		}
};

