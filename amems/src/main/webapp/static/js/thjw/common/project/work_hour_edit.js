
work_hour_edit = {
		id:'work_hour_edit',
		workHourData : [],
		param: {
			djid:null,//关联单据id
			parentWinId : '',
			ywlx:8,//业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
			colOptionhead : true,
			dprtcode:userJgdm//默认登录人当前机构代码
		},
		init : function(param){
			if(param){
				$.extend(this.param, param);
			}
			this.initInfo();
		},
		initInfo : function(){
			this.workHourData = [];
			$("#workHoursCount",$("#"+this.id)).html(0);
			if(this.param.djid){
				this.initDataList();
			}
		},
		/**
		 * 加载工种工时
		 */
		initDataList : function(){
			var this_ = this;
			var searchParam = {};
			searchParam.ywid = this.param.djid;
			searchParam.ywlx = this.param.ywlx;
			searchParam.dprtcode = this.param.dprtcode;
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/project2/workhour/queryAllList",
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify(searchParam),
				dataType:"json",
				success:function(data){
					if(data != null && data.length > 0){
						$.each(data, function(i, row) {
							var infos = {};
							infos.id = row.id;
							infos.jdid = row.jdid;
							infos.jdbm  = row.jdbm;
							infos.gzzid = row.gzzid;
							infos.gzzbh  = row.gzzbh;
							infos.jhgsXss = row.jhgsXss;
							infos.rw = row.rw;
							infos.bz = row.bz;
							infos.xc = row.xc;
							this_.workHourData.push(infos);
						});
						$("#workHoursCount",$("#"+this_.id)).html(data.length);
					}
				}
			});
		},
		/**
		 * 打开工种工时对话框
		 */
		openWinAdd : function(){
			var this_ = this;
			var dprtcode = this.param.dprtcode;
			Work_Hour_Win_Modal.show({
				parentWinId : this_.param.parentWinId,
				workHourData:this_.workHourData,//原值，在弹窗中默认勾选
				colOptionhead : this_.param.colOptionhead,
				dprtcode:dprtcode, //机构代码
				callback: function(data){//回调函数
					this_.workHourData = [];
					if(data != null && data.length > 0){
						this_.workHourData = data;
					}
					$("#workHoursCount",$("#"+this_.id)).html(this_.workHourData.length);
				}
			});
		},
		/**
		 * 获取工种工时
		 */
		getWorkHourList : function(){
			return this.workHourData;
		}
}



