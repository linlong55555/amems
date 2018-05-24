
//待办工作类型：
//1维护提示、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
//、21故障保留、22缺陷保留、41岗位授权、42质检、43质量内审计划、44内审问题整改、61航材上架、62工具上架、63航材销毁下架、64工具销毁下架、65报废'
//待办处理配置
var handleCfg = {
		1 : {dbgzlx:"1", permissioncode:"index:01", title:"维护提示", 
				url : basePath + "/project2/bulletin/edit",
				handleUrl:basePath+"/project2/bulletin/main?", 
				viewUrl: basePath+"/project2/bulletin/view?id=",
				toHandleOrView: function(param, type){
     				param.data = JSON.stringify({id:param.id});
     				param.contentType = "application/json;charset=utf-8";
     				handleCfg.performAction(param, type, this.url, this.handleUrl, this.viewUrl);
	     		}
			},
     	2 : {dbgzlx:"2",permissioncode:"index:02",title:"技术指令", 
     			url : basePath + "/project2/instruction/edit",
     			handleUrl:basePath+"/project2/instruction/main?", 
     			viewUrl: basePath+"/project2/instruction/view?id=",
	     		toHandleOrView: function(param, type){
     				param.data = JSON.stringify({id:param.id});
     				param.contentType = "application/json;charset=utf-8";
     				handleCfg.performAction(param, type, this.url, this.handleUrl, this.viewUrl);
	     		}
     		},
     	3 : {dbgzlx:"3", permissioncode:"index:03", title:"维修方案", 
	     		url:basePath+"/project2/maintenancescheme/detail",
	     		handleUrl:{
	 				1 : basePath+"/project2/maintenanceproject/main?",
	 				2 : basePath+"/project2/maintenanceproject/audit/main?",
	 				3 : basePath+"/project2/maintenanceproject/approval/main?",
	 				5 : basePath+"/project2/maintenanceproject/main?",
	 				11 : basePath+"/project2/maintenanceproject/main?",
	 				12 : basePath+"/project2/maintenanceproject/effect/main?"
	 			},
	     		viewUrl: basePath+"/project2/maintenancescheme/view?id=",
	     		toHandleOrView: function(param, type){
	     			var handleUrl = this.handleUrl[param.jd];
	     			var viewUrl = this.viewUrl;
     				if(param.id){
     					handleCfg.getByUrl(this.url, JSON.stringify({id:param.id}), "application/json;charset=utf-8", function(obj){
             				if(obj != null) {
             					//状态：-1初始化、1保存、2提交、3已审核、4已批准、5审核驳回、6审批驳回、7生效、8失效
             					//状态跟节点相同或审核驳回、审批驳回进入编辑页，否则刷新页面。
             					if(param.jd == obj.zt || (param.jd == 5 && (obj.zt == 5 || obj.zt == 6)) || (param.jd == 11 && obj.zt == 4) || (param.jd == 12 && obj.zt == 7)){
             						window.open(handleUrl+'todo_fjjx='+param.fjjx+'&todo_ywid='+param.id+'&todo_dprtcode='+obj.dprtcode+'&todo_zt='+obj.zt);
                 				}else{
                 					if(type == 1){
        	         					AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
                 					}else{
                 						window.open(viewUrl+param.id);
                 					}
                 				}
         					}else{
         						AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
         					}
             			});
     				}else{
     					window.open(handleUrl+'todo_lyid='+param.lyid+'&todo_fjjx='+param.fjjx);
     				}
	     		
	     		},
	     		/** 查看处理情况*/
	     		toView: function(param) {
	     			var _this = this;
	     			AjaxUtil.ajax({
	     				url:basePath+"/project2/maintenancescheme/detail",
	     				type: "post",
	     				async: false,
	     				contentType:"application/json;charset=utf-8",
	     				data:JSON.stringify({id:param.id}),
	     				dataType:"json",
	     				success:function(data) {
	     					if((data != null) ) {
	     						//生效状态-查看，其他都进办理界面。
	     						if(data.zt =='10') {
	     							window.open(_this.viewUrl+param.id);
	     						} 
	     						else {
	     							//其他状态-继续办理
//	     							_this.toHandle(param);
		     					}
	     					}
	     				}
	     			});
	     		}
     		},
     	4 : {dbgzlx:"4", permissioncode:"index:04", title:"非例行工单", 
	     		url:basePath+"/produce/workorder/selectWOById",
	     		handleUrl:basePath+"/produce/workorder/main?", 
	     		viewUrl: basePath+"/produce/workorder/woView?gdid=",
	     		toHandleOrView: function(param, type){
	 				if(param.id){
	 					var handleUrl = this.handleUrl;
	 	     			var viewUrl = this.viewUrl;
	 	     			var editArr = [1,2,7];
	 					handleCfg.getByUrl(this.url, {gdid:param.id}, "", function(obj){
	         				if(obj != null) {
	         					//状态：1保存、2提交、7下发
	         					if(param.jd == 1 && $.inArray(obj.zt, editArr) != -1){
	         						window.open(handleUrl+'todo_ywid='+param.id+'&todo_dprtcode='+obj.dprtcode);
	             				}else{
	             					if(type == 1){
	    	         					AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
	             					}else{
	             						window.open(viewUrl+param.id);
	             					}
	             				}
	     					}else{
	     						AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
	     					}
	         				});
	 				}else{
	 					window.open(this.handleUrl+'todo_lyid='+param.lyid+'&todo_fjjx='+param.fjjx);
	 				}
     			}
     		},
     	6 : {dbgzlx:"6", permissioncode:"index:05", title:"工程指令EO", 
     			url : basePath+"/project2/eo/selectById",
     			handleUrl:basePath+"/project2/eo/main?", 
     			viewUrl: basePath+"/project2/eo/view?id=",
     			toHandleOrView: function(param, type){
     				param.data = {eoId:param.id,viewFlag:"1"};
     				handleCfg.performAction(param, type, this.url, this.handleUrl, this.viewUrl);
	     		}
     		},
     	7 : {dbgzlx:"7", permissioncode:"index:07", title:"MEL", 
     			url:basePath+"/project2/mel/selectById",
     			handleUrl:basePath+"/project2/mel/main?", 
     			viewUrl: basePath+"/project2/mel/view?id=",
     			toHandleOrView: function(param, type){
     				param.data = {id:param.id};
     				handleCfg.performAction(param, type, this.url, this.handleUrl, this.viewUrl);
	     		}
     		},
     	8 : {dbgzlx:"8", permissioncode:"index:08", title:"工卡", 
     			url : basePath+"/project2/workcard/selectById",
     			handleUrl:basePath+"/project2/workcard/main?", 
     			viewUrl: basePath+"/project2/workcard/view/",
     			toHandleOrView: function(param, type){
     				param.data = {id:param.id};
     				handleCfg.performAction(param, type, this.url, this.handleUrl, this.viewUrl);
	     		}
     		},
     	9 : {
     			dbgzlx:"9", 
     			permissioncode:"index:06", 
     			title:"技术评估单", 
     			url : basePath+"/project2/assessment/getByTechnicalId",
     			handleUrl:{
     				1 : basePath+"/project2/assessment/main?",
     				2 : basePath+"/project2/assessment/audit/main?",
     				3 : basePath+"/project2/assessment/approval/main?",
     				5 : basePath+"/project2/assessment/main?",
     				6 : basePath+"/project2/assessment/main?"
     			},
     			viewUrl: basePath+"/project2/assessment/view?id=",
     			toHandleOrView: function(param, type){
     				param.data = JSON.stringify({id:param.id});
     				param.contentType = "application/json;charset=utf-8";
     				handleCfg.performAction(param, type, this.url, this.handleUrl[param.jd], this.viewUrl);
	     		}
     		},
 		10 : {dbgzlx:"10", permissioncode:"index:10", title:"生产指令", 
 			url : basePath+"/project2/todo/selectById",
 			handleUrl:basePath+"/project2/production/main?",
 			viewUrl: basePath+"/project2/production/view?id=",
 			toHandleOrView: function(param, type){
 				if(param.id){
 					var handleUrl = this.handleUrl;
 					handleCfg.getByUrl(this.url, {id:param.todoId}, "", function(obj){
 						if(obj != null) {
         					if(obj.zt == 1){
         						window.open(handleUrl+'todo_fjjx='+param.fjjx+'&todo_ywid='+param.id+'&todo_dprtcode='+obj.dprtcode+'&todo_jd='+param.jd);
         					}else{
	         					AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
             				}
     					}else{
     						AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
     					}
         			});
 				}
 			}
 		},
     	21 : {dbgzlx:"21", permissioncode:"index:21", title:"故障保留", 
     		url:basePath + "/produce/reservation/fault/getByFailureKeepId",
 			handleUrl:basePath+"/produce/reservation/fault/main?", 
 			viewUrl: basePath+"/produce/reservation/fault/find/",
 			toHandleOrView: function(param, type){
 				param.data = JSON.stringify({id:param.id});
 				param.contentType = "application/json;charset=utf-8";
 				param.jd = param.jd == 3?2:param.jd;
 				handleCfg.performAction(param, type, this.url, this.handleUrl, this.viewUrl);
     		}
 		},
 		22 : {dbgzlx:"22", permissioncode:"index:22", title:"缺陷保留", 
 			url:basePath + "/produce/reservation/defect/getByDefectKeepId",
 			handleUrl:basePath+"/produce/reservation/defect/main?", 
 			viewUrl: basePath+"/produce/reservation/defect/find/",
 			toHandleOrView: function(param, type){
 				param.data = JSON.stringify({id:param.id});
 				param.contentType = "application/json;charset=utf-8";
 				param.jd = param.jd == 3?2:param.jd;
 				handleCfg.performAction(param, type, this.url, this.handleUrl, this.viewUrl);
 			}
     	},
     	41 : {dbgzlx:"41", permissioncode:"index:41", title:"岗位授权", 
 			url:basePath + "/quality/post/application/getByPostApplicationId",
 			handleUrl:{
 				2 : basePath+"/quality/post/review/main?",
 				3 : basePath+"/quality/post/evaluation/main?",
 				5 : basePath+"/quality/post/application/main?"
 			},
 			viewUrl: basePath+"/quality/post/application/find/",
 			toHandleOrView: function(param, type){
 				param.data = JSON.stringify({id:param.id});
 				param.contentType = "application/json;charset=utf-8";
 				handleCfg.performAction(param, type, this.url, this.handleUrl[param.jd], this.viewUrl);
 			}
     	},
     	42 : {dbgzlx:"42", permissioncode:"index:42", title:"质检", 
 			url: basePath+"/material/inspection/getByInspectionId",
 			handleUrl:basePath+"/material/inspection/main?", 
 			viewUrl: basePath+"/material/inspection/find/",
 			toHandleOrView: function(param, type){
 				if(param.id){
 					var handleUrl = this.handleUrl;
 	     			var viewUrl = this.viewUrl;
 					handleCfg.getByUrl(this.url, JSON.stringify({id:param.id}), "application/json;charset=utf-8", function(obj){
         				if(obj != null) {
         					if(param.jd == 33 && (obj.zt == 1 || obj.zt == -1)){
         						window.open(handleUrl+'todo_ywid='+param.id+'&todo_jd='+param.jd);
         	 				}else{
	         					AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
             				}
     					}else{
     						AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
     					}
         			});
 				}
 			}
     	},
     	43 : {dbgzlx:"43", permissioncode:"index:43", title:"质量内审计划", 
 			url : basePath+"/quality/annualplan/getInfoById",
 			handleUrl:{
 				1 : basePath+"/quality/annualplan/main?",
 				2 : basePath+"/quality/annualplan/audit?",
 				3 : basePath+"/quality/annualplan/approval?",
 				5 : basePath+"/quality/annualplan/main?",
 				6 : basePath+"/quality/annualplan/main?"
 			},
 			viewUrl: basePath+"/quality/annualplan/main?id=",
 			toHandleOrView: function(param, type){
 				if(param.id){
 					var handleUrl = this.handleUrl[param.jd];
 					handleCfg.getByUrl(this.url, {id:param.id}, "", function(obj){
 	     				if(obj != null) {
 	     					//状态跟节点相同或审核驳回、审批驳回进入编辑页，否则刷新页面。
 	     					if((param.jd == 2 && 1 == obj.zt) ||(param.jd == 3 && 2 == obj.zt) || (param.jd == 1 && (obj.zt == 5 || obj.zt == 6))){
 	     						window.open(handleUrl+'todo_nf='+obj.nf+'&todo_ywid='+param.id+'&todo_dprtcode='+obj.dprtcode+'&todo_jd='+param.jd);
 	         				}else{
	         					AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
 	         				}
 	 					}else{
 	 						AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
 	 					}
 	     			});
 				}else{
 					AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
 				}
     		}
 		},
 		44 : {dbgzlx:"44", permissioncode:"index:44", title:"内审问题整改", 
 			url : basePath+"/project2/todo/selectById",
 			handleUrl:{
 				21 : basePath+"/quality/correctivemeasures/main?",
 				22 : basePath+"/quality/correctivemeasures/main?",
 				23 : basePath+"/quality/rectifymeasuresfollow/main?",
 				24 : basePath+"/quality/rectifymeasuresfollow/main?"
 			},
 			viewUrl: basePath+"/quality/correctivemeasures/view?id=",
 			toHandleOrView: function(param, type){
 				if(param.id){
 					var handleUrl = this.handleUrl[param.jd];
 	     			var editArr = [21,22,23,24];
 					handleCfg.getByUrl(this.url, {id:param.todoId}, "", function(obj){
 						if(obj != null) {
         					if(obj.zt == 1 && $.inArray(param.jd*1, editArr) != -1){
         						window.open(handleUrl+'todo_ywid='+param.id+'&todo_jd='+param.jd);
         					}else{
	         					AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
             				}
     					}else{
     						AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
     					}
         			});
 				}
 			}
 		},
     	61 : {dbgzlx:"61", permissioncode:"index:61", title:"航材上架", 
 			url: basePath+"/project2/todo/selectById",
 			handleUrl:basePath+"/material/outin/materialstockin?", 
 			toHandleOrView: function(param, type){
 				if(param.id){
 					var handleUrl = this.handleUrl;
 					handleCfg.getByUrl(this.url, {id:param.todoId}, "", function(obj){
 						if(obj != null) {
         					if(obj.zt == 1 && param.jd == 31){
         						window.open(handleUrl+'todo_ywid='+param.id+'&todo_dprtcode='+obj.dprtcode);
         					}else{
	         					AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
             				}
     					}else{
     						AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
     					}
         			});
 				}
 			}
     	},
     	62 : {dbgzlx:"62", permissioncode:"index:62", title:"工具上架", 
 			url: basePath+"/project2/todo/selectById",
 			handleUrl:basePath+"/material/outin/toolstockin?", 
 			toHandleOrView: function(param, type){
 				if(param.id){
 					var handleUrl = this.handleUrl;
 					handleCfg.getByUrl(this.url, {id:param.todoId}, "", function(obj){
 						if(obj != null) {
         					if(obj.zt == 1 && param.jd == 31){
         						window.open(handleUrl+'todo_ywid='+param.id+'&todo_dprtcode='+obj.dprtcode);
         					}else{
	         					AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
             				}
     					}else{
     						AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
     					}
         			});
 				}
 			}
     	},
     	63 : {dbgzlx:"63", permissioncode:"index:63", title:"航材销毁下架", 
 			url: basePath+"/project2/todo/selectById",
 			handleUrl:basePath+"/material/destroy/airmaterial/main?", 
 			toHandleOrView: function(param, type){
 				if(param.id){
 					var handleUrl = this.handleUrl;
 					handleCfg.getByUrl(this.url, {id:param.todoId}, "", function(obj){
 						if(obj != null) {
         					if(obj.zt == 1 && param.jd == 32){
         						window.open(handleUrl+'todo_ywid='+param.id);
         					}else{
	         					AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
             				}
     					}else{
     						AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
     					}
         			});
 				}
 			}
     	},
     	64 : {dbgzlx:"64", permissioncode:"index:64", title:"工具销毁下架", 
 			url: basePath+"/project2/todo/selectById",
 			handleUrl:basePath+"/material/destroy/tool/main?", 
 			toHandleOrView: function(param, type){
 				if(param.id){
 					var handleUrl = this.handleUrl;
 					handleCfg.getByUrl(this.url, {id:param.todoId}, "", function(obj){
 						if(obj != null) {
         					if(obj.zt == 1 && param.jd == 32){
         						window.open(handleUrl+'todo_ywid='+param.id);
         					}else{
	         					AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
             				}
     					}else{
     						AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
     					}
         			});
 				}
 			}
     	},
     	65 : {dbgzlx:"65", permissioncode:"index:65", title:"报废", 
 			url : basePath + "/material/scrapped/apply/getRecord",
 			handleUrl:{
 				2 : basePath+"/material/scrapped/audit/main?",
 				5 : basePath+"/material/scrapped/apply/main?"
 			}, 
 			viewUrl: basePath+"/material/scrapped/apply/view?id=",
 			toHandleOrView: function(param, type){
 				param.data = JSON.stringify({id:param.id, paramsMap : {"option" : "edit" }});
 				param.contentType = "application/json;charset=utf-8";
 				param.jd = param.jd == 3?2:param.jd;
 				handleCfg.performAction(param, type, this.url, this.handleUrl[param.jd], this.viewUrl);
 			}
     	},
 		/**
 		 * param 参数
 		 * type 类型：1，办理；2，查看
 		 * url 获取数据路径
 		 * handleUrl 办理路径
 		 * viewUrl 查看路径
 		 */
 		performAction : function(param, type, url, handleUrl, viewUrl){//执行处理情况
 			if(param.id){
 				this.getByUrl(url, param.data, param.contentType, function(obj){
     				if(obj != null) {
     					//状态：-1初始化、1保存、2提交、3已审核、4已批准、5审核驳回、6审批驳回、7生效、8失效
     					//状态跟节点相同或审核驳回、审批驳回进入编辑页，否则刷新页面。
     					if(param.jd == obj.zt || (param.jd == 5 && (obj.zt == 5 || obj.zt == 6)) || obj.zt == -1){
     						window.open(handleUrl+'todo_fjjx='+param.fjjx+'&todo_ywid='+param.id+'&todo_dprtcode='+obj.dprtcode+'&todo_jd='+param.jd);
         				}else{
         					if(type == 1){
	         					AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
         					}else{
         						window.open(viewUrl+param.id);
         					}
         				}
 					}else{
 						AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
 					}
     			});
			}else{
				window.open(handleUrl+'todo_lyid='+param.lyid+'&todo_fjjx='+param.fjjx);
			}
 		},
 		getByUrl : function(url, param, contentType, obj){//获取数据
 			AjaxUtil.ajax({
 				async : false,
 				url : url,
 				type : "post",
 				contentType : contentType?contentType:"application/x-www-form-urlencoded; charset=UTF-8",
 				data : param,
 				dataType : "json",
 				success : function(data){
					if(typeof(obj)=="function"){
						obj(data); 
					}
 				}
 			});
 		},
 		/** 反馈 */
 		feedback : function(param){
			handleCfg.getByUrl(basePath+"/project2/todo/selectById", {id:param.todoId}, "", function(obj){
				if(null != obj && obj.zt == 1){
					window.open(basePath+"/project2/todo/main?lybh="+param.lybh+"&fjjx="+param.fjjx+"&dbgzlx="+param.dbgzlx+"&todoId="+param.todoId);
				}else{
 					AlertUtil.showErrorMessage("该数据已更新，请刷新后再进行操作!");
 				}
			});
 		},
 		/** 办理 */
 		toHandle : function(param){
 			this.toHandleOrView(param, 1);
 		},
 		/** 查看办理结果 */
 		toViewTodo : function(param){
 			this.toHandleOrView(param, 2);
 		},
 		toHandleOrView : function(param, type){//处理待办或查看结果
 			var dbgzlx = param.dbgzlx;
 			var cfg = handleCfg[dbgzlx];
 			if(cfg) {
 				cfg.toHandleOrView(param, type);
 			}
 		},
 		/** 查看来源 */
 		viewSource : function(param){
 			if(param.id && handleCfg[param.dbgzlx]){
 				var cfg = handleCfg[param.dbgzlx];
 				var viewUrl = basePath+"/project2/assessment/view?id=";//查看技术评估单
 				if(param.id == param.dbywid){
 					viewUrl = cfg.viewUrl;//待办工作查看来源详情
 				}else{
 					if(param.dbgzlx =='9') {//查看适航性资料
     					viewUrl = basePath+"/project2/airworthiness/view?id=";
 					}
 				}
     			window.open(viewUrl+param.id);
 			}
 		}
}
 
