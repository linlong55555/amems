var choseAllIds = []; //技术评估单
var choseLywjIds = []; //技术评估单,来源文件

/**列表信息*/
var eo_main={
		id:"engineer_mainpage",
		lastSelect:null,
		lastColor:null,
		pagination : {},
		tableDivId : "engineering_main_table_div",
		eo_status :{
			"1":{name:"保存"},
			"2":{name:"提交"},
			"3":{name:"已审核"},
			"4":{name:"已批准"},
			"5":{name:"审核驳回"},
			"6":{name:"批准驳回"},
			"7":{name:"生效"},
			"9":{name:"指定结束"},
			"10":{name:"完成"}
		},
		eo_sylbs :{
			"1":{name:"飞机"},
			"2":{name:"发动机"},
			"3":{name:"APU"},
			"99":{name:"部件"},
		},
		initInfo:function(){
			//机型
			this.initPlaneModel(userJgdm);
			//列表信息
			this.reload();
			/*this.authHeight("engineer_mainpage","displayContent");*/
			App.resizeHeight();
			$(window).resize(function(){
				App.resizeHeight();
			});
			/*$(window).resize(function(){
				this.authHeight("engineer_mainpage","displayContent");	
			});*/
		},
		/**EO状态*/
		getEOStatusName : function(status){
			var obj = this.eo_status[status] || {};
			return StringUtil.escapeStr(obj.name);
		},
		/**适用类别*/
		getEOSylbName : function(sylb){
			var obj = this.eo_sylbs[sylb] || {};
			return StringUtil.escapeStr(obj.name);
		},
		/**机型*/
		initPlaneModel : function(dprtcode){
			var this_ = this;
			var data = acAndTypeUtil.getACTypeList({DPRTCODE:dprtcode});
			var option = '<option value="" selected="selected">显示全部All</option>';
			if(data.length >0){
				$.each(data,function(i,obj){
					option += "<option value='"+StringUtil.escapeStr(obj.FJJX)+"' >"+StringUtil.escapeStr(obj.FJJX)+"</option>";
				});
			}
			$("#jx_search").empty();
			$("#jx_search").append(option);
		},
		refreshPage : function(){//刷新页面
			this.goPage(this.pagination.page, this.pagination.sort, this.pagination.order);
		},
		goPage:function(pageNumber,sortType,sequence){
			this.hideBottom();
			this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
		},
		AjaxGetDatasWithSearch:function(pageNumber,sortType,sequence){
			var _this = this;
			var searchParam ={};
			_this.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
			searchParam.pagination = _this.pagination;
			if (id != "") {
				searchParam.id = id;
				id = "";
			}
			$.extend(searchParam, _this.getParams());
			
			startWait();
			AjaxUtil.ajax({
				url:basePath+"/project2/eo/queryAllPageList",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				data:JSON.stringify(searchParam),
				success:function(data){
					finishWait();
					if(data.rows.length > 0){
						_this.appendContentHtml(data.rows);
						//分页
						new Pagination({
							renderTo : "engineering_pagination",
							data: data,
							sortColumn : sortType,
							orderType : sequence,
							extParams : {},
							goPage : function(a, b, c) {
								_this.goPage(a, b, c);
							}
						});
						// 标记关键字
						signByKeyword($("#keyword_search").val(),[2,8],"#engineering_list tr td");
					}else{
						$("#engineering_list").empty();
						$("#engineering_pagination").empty();
						$("#engineering_list").append("<tr><td class='text-center' colspan=\"14\">暂无数据 No data.</td></tr>");
					}	
					
					//翻动列表,表头浮动
					new Sticky({
						tableId : 'eo_main_table'
					});
				}
			});
		},
		reload:function(){
			this.goPage(1,"auto","desc");
		},
		// 表格拼接
		appendContentHtml:function(list){
			choseAllIds = [];
			choseLywjIds = [];
			var _this = this;
			var htmlContent = '';

			$.each(list,function(index,row){
				choseAllIds.push(row.id);
				choseLywjIds.push(row.id);
				if (index % 2 == 0) {
					htmlContent +="<tr class='cursor-pointer' id='"+row.id+"' onclick=eo_main.showChildTR('"+row.id+"','"+eo_main.getEOSylbName(row.sylb)+"','"+row.zt+"') bgcolor=\"#f9f9f9\">";
				} else {
					htmlContent +="<tr class='cursor-pointer' id='"+row.id+"' onclick=eo_main.showChildTR('"+row.id+"','"+eo_main.getEOSylbName(row.sylb)+"','"+row.zt+"') bgcolor=\"#fefefe\">";
				}
				htmlContent += "<td class='text-center fixed-column'>" ;
				htmlContent += "<i class='icon-print color-blue cursor-pointer'  onClick=eo_main.print('"+ row.id + "',event) title='打印 Print'></i>&nbsp;&nbsp;"
				/*if(row.zt == "1" || row.zt == "5" || row.zt == "6" ){
					htmlContent += "<a href=javascript:eo_main.openWinSubmit('"+ row.id + "','"+ row.dprtcode + "'); class='pull-left' style='margin-right:5px;' >提交</a>";
				}*/
				if(row.zt == "1" || row.zt == "5" || row.zt == "6" ){ //状态为,保存、审核驳回、审批驳回,可编辑
					htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='project2:eo:main:02'  onClick=eo_main.openWinEdit('"+ row.id + "') title='修改 Edit'></i>&nbsp;&nbsp;";
					htmlContent += "<i class='icon-trash color-blue cursor-pointer checkPermission' permissioncode='project2:eo:main:08'  onClick=eo_main.del('"+ row.id + "') title='删除 Delete'></i>&nbsp;&nbsp;";
				}
				if((row.zt == "9" || row.zt == "10") && (row.bBbid == "" || row.bBbid == null)){//状态为,指定结束、正常且最新标识=2且后版本为空,可改版
					htmlContent += "<i class='icon-pencil color-blue cursor-pointer checkPermission' permissioncode='project2:eo:main:03' onClick=eo_main.openWinRevision('"+ row.id + "') title='改版 Revision'></i>&nbsp;&nbsp;";
				}
				if(row.zt == "2" ){//状态为,提交,可审核
					htmlContent += "<i class='icon-foursquare color-blue cursor-pointer checkPermission' permissioncode='project2:eo:main:04' onClick=eo_main.openWinAudit('"+ row.id + "') title='审核 Audit'></i>&nbsp;&nbsp;";
				}
				if(row.zt == "3" ){//状态为,已审核,可批准
					htmlContent += "<i class='icon-check color-blue cursor-pointer checkPermission' permissioncode='project2:eo:main:05' onClick=eo_main.openWinApprove('"+ row.id + "') title='批准 Approved'></i>&nbsp;&nbsp;";
				}
				if(row.zt == "2" || row.zt == "3" || row.zt == "4" || row.zt == "7" ){//状态为,提交、已审核、已批准、生效,可关闭
					htmlContent += "<i class='icon-off color-blue cursor-pointer checkPermission' permissioncode='project2:eo:main:06'  onClick=eo_main.openWinClose('"+ row.id + "') title='关闭 Close'></i>&nbsp;&nbsp;";
				}
				if(row.showYellow){//EO适用性表中，有>=1条，未确认的状态数据就要，黄色标记显示
					htmlContent += 	"<i class='icon-circle' style='font-size:18px;color:#ff9900;line-height:25px;vertical-align:middle;'></i><div class='clearfix'></div>";
				}
				htmlContent += 	"</td>";
				htmlContent += "<td   title='"+row.eobh+"'>" +
									"<a href=javascript:eo_main.openWinView('"+ row.id + "',1); class='pull-left' style='margin-right:5px;' >"+StringUtil.escapeStr(row.eobh)+"</a>"+
							    "</td>";
				htmlContent += "<td style='text-align:center;'  title='R"+StringUtil.escapeStr(row.bb)+"'>R"+StringUtil.escapeStr(row.bb)+"</td>";
				htmlContent += "<td   title='"+StringUtil.escapeStr(row.jx)+"'>"+StringUtil.escapeStr(row.jx)+"</td>";
				htmlContent += "<td class='text-left'>"+ eo_main.getPgds(row.isList, row.id) + "</td>";
				htmlContent += "<td class='text-left'>"+ eo_main.getLywjs(row.lywjList, row.id) + "</td>";
				htmlContent += "<td class='text-center'>"+"<a href='#' onClick=\"eo_main.toCirculation('"+ row.id + "')\" >"+row.yy+"/"+row.zh + "</a></td>";
				htmlContent += "<td   title='"+row.eozt+"'>"+StringUtil.escapeStr(row.eozt)+"</td>";
				
				if((row.zt == "9" || row.zt == "10")){
					htmlContent += "<td title='"+eo_main.getEOStatusName(row.zt)+"'>" +
							        "<a href='javascript:void(0);' " +
												" zt='"+row.zt+"' gbrid='"+row.gbr.id+"' eobh='"+StringUtil.escapeStr(row.eobh)+"' " +
												" gbrq='"+StringUtil.escapeStr(row.gbrq)+"'  gbyy='"+StringUtil.escapeStr(row.gbyy)+"' " +
										    	" onclick=\"eo_main.shutDown(this)\">"+eo_main.getEOStatusName(row.zt) +
									"</a>"+
						         "</td>"; 
				}else{
					htmlContent += "<td title='"+eo_main.getEOStatusName(row.zt)+"'>"+eo_main.getEOStatusName(row.zt)+"</td>"; 
				}
				
				htmlContent += "<td   title='"+eo_main.getEOSylbName(row.sylb)+"'>"+eo_main.getEOSylbName(row.sylb)+"</td>";
				htmlContent += "<td title='附件 Attachment' style='text-align:center;'>";
				if((row.paramsMap.attachCount != null && row.paramsMap.attachCount > 0) || (row.gznrfjid != null && row.gznrfjid != "")){
					htmlContent += '<i qtid="'+row.id+'" gkfjid="'+row.gkfjid+'" gznrfjid="'+row.gznrfjid+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
				}
				htmlContent += "</td>";
				htmlContent += "<td   title='"+StringUtil.escapeStr(row.zdr.displayName)+"'>"+StringUtil.escapeStr(row.zdr.displayName)+"</td>";
				htmlContent += "<td   title='"+StringUtil.escapeStr(row.whsj)+"'>"+StringUtil.escapeStr(row.whsj)+"</td>";
				htmlContent += "<td   title='"+AccessDepartmentUtil.getDpartName(row.dprtcode)+"'>"+AccessDepartmentUtil.getDpartName(row.dprtcode)+"</td>";
				htmlContent +="</tr>";	
			});

			$("#engineering_list").html(htmlContent);
			_this.initWebuiPopover();
			refreshPermission();
		},
		addTitle:function(){
			$("#maintenance_item_list tr td").each(function(){
				if(!$(this).attr("title")){
					$(this).attr("title", $(this).text());
				}
			});
		},
		authHeight:function(id,displaynonecontent){
			//头部高度
			var headerHeight = $('.header').outerHeight();
			var headerDownHeight = $('.header-down').outerHeight();
			//window高度
			var windowHeight = $(window).height();
			//尾部的高度
			var footerHeight = $('.footer').outerHeight()||0;
			//分页的高度
			var paginationHeight = $('.page-height:visible').outerHeight()||0;
			//panelheader的高度
			var panelHeadingHeight = $('.panel-heading').outerHeight();
			//调整高度
			var adjustHeight = $("#adjustHeight").val()||0;
			//搜索框的高度
			var searchContent=$(".searchContent").outerHeight()||0;
			//body的高度
			var bodyHeight = windowHeight - headerHeight - footerHeight - panelHeadingHeight- headerDownHeight ;
			//表格的高度
			var tableHeight = bodyHeight - searchContent - paginationHeight  - 30 - adjustHeight;
			if(navigator.userAgent.indexOf("Chrome") > -1){
				tableHeight -= 10;
			}
			//隐藏的div
			if($("."+displaynonecontent).css("display")=='block'){
				$(".table-height").attr('style', 'height:' + tableHeight*0.3+ 'px !important;overflow-x: auto;');
				var table_height=$(".table-height").outerHeight()||0;
				var bottom_height=tableHeight-table_height;
				$("."+displaynonecontent).css("height",bottom_height+"px");
				var bottom_panel_heading=$("."+displaynonecontent+" .panel-heading").eq(0).outerHeight()||0;
				var botttom_panel_body=bottom_height-bottom_panel_heading-5;
				$("."+displaynonecontent+" .panel-body").attr('style', 'height:' + botttom_panel_body+ 'px !important;overflow-x: auto;');

			}else{
				if($(".table-height").length!=0){
					$(".table-height").attr('style', 'height:' + tableHeight+ 'px !important;overflow-x: auto;');
				}
			}
		},
		showChildTR:function(eoId,sylb,eozt){
			var this_= $("#"+eoId);
			
			if(this.lastSelect!=null){
				this.lastSelect.attr("bgcolor",this.lastColor);
			}
			
			var isBottomShown = false;
			if($(".displayContent").is(":visible")){
				isBottomShown = true;
			}
			
			$(".displayContent").css("display","block");
			
			this.lastColor=this_.attr("bgcolor");
			this_.attr("bgcolor","#eaeaea");
			new Sticky({tableId:'eo_main_table'}); //初始化表头浮动
			
			
			this.lastSelect=this_;
			if($("#hideIcon").length==0){
				$('<div class="pull-right" id="hideIcon" style="margin-right:15px;"><img src="'+basePath+'/static/images/down.png" onclick="eo_main.hideBottom()" style="width:36px;cursor:pointer;"/></div>').insertAfter($("#engineering_pagination .fenye"));
			}

			this.authHeight("engineer_mainpage","displayContent");
			App.resizeHeight();
			
			/**加载EO执行对象数据*/
			$("#topic_list").html("");
			$("#zxlb_list").html("");
			//if(eozt == "7"){ //EO状态=7生效,才加载
				this.loadEOExecutionObjs(eoId,sylb,eozt);
			//}
			
			if(!isBottomShown){
				TableUtil.makeTargetRowVisible(this_, $("#engineering_main_table_div"));
			}
		},
		/**加载EO执行对象数据*/
		loadEOExecutionObjs : function(eoId,sylb,eozt){
			var this_ = this;
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/project2/eo/getEOExecutionObjs",
				type:"post",
				data:{'eoId':eoId},
				dataType:"json",
				success:function(data){
					this_.appentExeObjDatas(data,eoId,eozt);
					this_.appentExeListDatas(data,sylb);
				}
			});
		},
		appentExeObjDatas : function(data,eoId,eozt){
			/*var data = 
			{
					"exeObjs": [
					            {
					            	"zxdxid":"123456",
					            	"showYellow":true,
					            	"exeName": "部件/序列号",
					            	"woNums": 9,
					            	"qrZt" : 0,
					            	"woList": [
					            	           {
					            	        	   "gdbh": "WO-EO-B-0001-001",
					            	        	   "zt": "关闭"
					            	           },
					            	           {
					            	        	   "gdbh": "WO-EO-B-0001-002",
					            	        	   "zt": "关闭"
					            	           }
					            	           ]
					            }
					            ]
			};*/
			var ztContentHtml="";
			$.each(data.exeObjs,function(index,row){
				if(row.showYellow){
						ztContentHtml +="<tr><td><div class='pull-left'>";
						ztContentHtml +="<i class=' icon-circle font-size-12 margin-left-8 color-blue'></i>&nbsp;<span>"+row.exeName+"</span>&nbsp;<span class='badge'>"+row.woNums+"</span></div>";
						ztContentHtml +="<div class=' pull-right margin-right-8'>";
						ztContentHtml +="<i class='icon-circle' style='font-size:25px;color:#ff9900;vertical-align:middle;'></i>";

				}else{
					ztContentHtml +="<tr><td><div class='pull-left'>";
					ztContentHtml +="<i class='icon-circle font-size-12 margin-left-8 color-blue'></i>&nbsp;<span>"+row.exeName+"</span>&nbsp;<span class='badge'>"+row.woNums+"</span></div>";
					ztContentHtml +="<div class=' pull-right margin-right-8'>";
				}

				var closeBtnHtml ="<button type='button' class='btn btn-primary padding-top-1 padding-bottom-1' style='padding-left:5px;padding-right:5px;margin-left:5px;' " +
								           "onClick=eo_main.openEOZxdxClose('"+ row.zxdxid + "','"+ eoId + "')>关闭</button>";
				var closeIHtml ="<i class='font-size-12 padding-top-1 padding-bottom-1' style='padding-left:5px;padding-right:5px;margin-left:5px;'>已关闭</i>";
				
				if(row.qrZt=="0"){
					ztContentHtml +="<button type='button' class='btn btn-primary padding-top-1 padding-bottom-1' style='padding-left:5px;padding-right:5px;margin-left:5px;' " +
							"onClick=eo_main.openEOZxdxConfirm('"+ row.zxdxid + "','"+ eoId + "')>确认</button>";	
				}
				
				if(row.gbZt=="9" || row.gbZt=="10"){
					ztContentHtml += closeIHtml;
				}else{
					if(eozt == "7"){//b_g2_01001表 关闭状态 ！= 9、10  并且   b_g2_010.状态=生效
						ztContentHtml += closeBtnHtml;
					}
				}
				
				ztContentHtml +="</div></td></tr>";
				if(null != row.woList && row.woList.length>0){
					$.each(row.woList,function(index,wo){
						ztContentHtml +="<tr class='topics-child_tr'>";
						ztContentHtml +="<td>"+wo.gdbh+" "+wo.gdzt+"</td>";
						ztContentHtml +="</tr>";
					});
				}
			});
			$("#topic_list").html("");
			$("#topic_list").html(ztContentHtml);
		},
		appentExeListDatas : function(data,syx){
			/*var data = 
			{
					"exeList": [
					         {
					        	 "syx": "部件",
					        	 "fjzch": "工单飞机注册号",
					        	 "jh": "ll",
					        	 "xlh": "ll",
					        	 "bjName": "部件中英文",
					        	 "wobh": "WO000000001",
					        	 "wobt": "我为祖国点赞",
					        	 "wozt": "关闭",
					        	 "jhzxList": "集合",
					        	 "sjzxList": "集合",
					        	 "woid": "ll"
					         }
					         ]
			};*/

			var zxlbContentHtml="";
			if(null != data && null != data.exeList && data.exeList.length>0){
				$.each(data.exeList,function(index,row){
					zxlbContentHtml +="<tr class='cursor-pointer' >";
					zxlbContentHtml += "<td  title='"+syx+"' style='text-align:left;vertical-align:middle;' >"+syx+"</td>";
					zxlbContentHtml += "<td title='"+row.fjzch+"' style='text-align:left;vertical-align:middle;' >"+row.fjzch+"</td>";
					zxlbContentHtml += "<td  title='"+row.jh+"' style='text-align:left;vertical-align:middle;' >"+row.jh+"</td>";
					zxlbContentHtml += "<td title='"+row.xlh+"' style='text-align:left;vertical-align:middle;' >"+row.xlh+"</td>";
					if(StringUtil.escapeStr(row.bjName)){
						zxlbContentHtml += "<td  title='"+row.bjName+"' style='text-align:left;vertical-align:middle;' >"+row.bjName+"</td>";
					}else{
						zxlbContentHtml += "<td  title='"+row.bjName+"' style='text-align:left;vertical-align:middle;' >"+row.bjName+"</td>";
					}
					zxlbContentHtml += "<td   title='"+row.wobh+"' style='text-align:left;vertical-align:middle;' ><a href=javascript:eo_main.openWinView('"+ row.woid + "',2);>"+row.wobh+"</a></td>";
					zxlbContentHtml += "<td  title='"+row.wobt+"' style='text-align:left;vertical-align:middle;' >"+row.wobt+"</td>";
					zxlbContentHtml += "<td style='text-align:center;' >"+eo_main.getJhzx(row.jhzxList, row.woid)+"</td>";
					zxlbContentHtml += "<td style='text-align:center;' >"+eo_main.getSjzx(row.sjzxList, row.woid)+"</td>";
					zxlbContentHtml += "<td   title='"+row.wozt+"' style='text-align:left;vertical-align:middle;' >"+row.wozt+"</td>";
					zxlbContentHtml += "<td   title='"+row.wobh+"' style='text-align:left;vertical-align:middle;' ><a href=javascript:eo_main.openWinView('"+ row.woid + "',2);>EO执行反馈单</a></td>";
					zxlbContentHtml +="</tr>";
				});
			}else{
				zxlbContentHtml="<tr><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>";
			}
			$("#zxlb_list").html(zxlbContentHtml);
		},
		//拼接计划执行
		getJhzx : function(list, id) {
			var html = "";
			if (list != null) {
				for (var i = 0; i < list.length; i++) {
					if(list[i] == null){continue;}
					
					var jklbh = StringUtil.escapeStr(list[i].jklbh); //监控类型
					var unit = MonitorUnitUtil.getMonitorUnit(jklbh); //单位
					var val = StringUtil.escapeStr(list[i].jhValue); //实际值
					
					if(val == "NA"){
						continue;
					}
					
					if(MonitorUnitUtil.isTime(jklbh)){
						val = TimeUtil.convertToHour(val, TimeUtil.Separator.COLON);
					}
					
					var showContent = (val)+" "+unit;
					if(showContent.length > 18){
						showContent = showContent.substring(0,18)+"...";
					}
					
					if (i == 1) {
						html = html + "<div id='jhzx"+ id + "'>";
					}
					if (i == 0) {
						html += "<i href='javascript:void(0);' title='"+ showContent + "'>" + showContent + "</i>";
					}
					if (i != 0) {
						html += "<i href='javascript:void(0);' title='" + showContent + "' >" + showContent + "</i>";
						html += "<br>";
					}
					if (i != 0 && i == list.length - 1) {
						html += "</div>";
					}
				}
			}
			
			return html;
		},
		//拼接实际执行
		getSjzx : function(list, id) {
			var html = "";
			if (list != null) {
				for (var i = 0; i < list.length; i++) {
					if(list[i] == null){continue;}
					
					var jklbh = StringUtil.escapeStr(list[i].jklbh); //监控类型
					var unit = MonitorUnitUtil.getMonitorUnit(jklbh); //单位
					var val = StringUtil.escapeStr(list[i].sjValue); //实际值
					
					if(val == "NA"){
						continue;
					}
					
					if(MonitorUnitUtil.isTime(jklbh)){
						val = TimeUtil.convertToHour(val, TimeUtil.Separator.COLON);
					}
					
					var showContent = val+" "+unit;
					if(showContent.length > 18){
						showContent = showContent.substring(0,18)+"...";
					}
					
					if (i == 1) {
						html = html + "<div id='sjzx"+ id + "'>";
					}
					if (i == 0) {
						html += "<i href='javascript:void(0);' title='"+ showContent + "'>" + showContent + "</i>";
					}
					if (i != 0) {
						html += "<i href='javascript:void(0);' title='" + showContent + "' >" + showContent + "</i>";
						html += "<br>";
					}
					if (i != 0 && i == list.length - 1) {
						html += "</div>";
					}
				}
			}
			
			return html;
		},
		/**字段排序*/
		orderBy : function(obj){
			var orderStyle = $("#" + obj + "_order").attr("class");
			$("th[id$=_order]").each(
					function() { // 重置class
						$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
					});
			$("#" + obj + "_" + "order").removeClass("sorting");
			if (orderStyle.indexOf("sorting_asc") >= 0) {
				$("#" + obj + "_" + "order").addClass("sorting_desc");
			} else {
				$("#" + obj + "_" + "order").addClass("sorting_asc");
			}
			orderStyle = $("#" + obj + "_order").attr("class");
			var currentPage = $("#engineering_pagination li[class='active']").text();
			this.goPage(currentPage, obj, orderStyle.split("_")[1]);
		},
		/**搜索条件显示与隐藏*/
		search : function(){
			if ($("#divSearch").css("display") == "none") {
				$("#divSearch").css("display", "block");
				/*this.authHeight("engineer_mainpage","displayContent");*/
				App.resizeHeight();
				$("#icon").removeClass("icon-caret-down");
				$("#icon").addClass("icon-caret-up");
			} else {
				$("#divSearch").css("display", "none");
				/*this.authHeight("engineer_mainpage","displayContent");*/
				App.resizeHeight();
				$("#icon").removeClass("icon-caret-up");
				$("#icon").addClass("icon-caret-down");
			}
		},
		/**隐藏底部列表*/
		hideBottom : function(){
			if(this.lastSelect!=null){
				this.lastSelect.attr("bgcolor",eo_main.lastColor);
				this.lastSelect=null;
				this.lastColor=null;
			}
			$(".displayContent").css("display","none");
			$("#hideIcon").remove();
			/*this.authHeight("engineer_mainpage","displayContent");	*/
			App.resizeHeight();
		},
		/**搜索条件重置*/
		searchreset : function(){
			//机型
			$("#jx_search option:first").prop("selected", 'selected');  

			//保存状态
			$("#zt_search option:first").prop("selected", 'selected');  

			//关键搜索框
			$("#keyword_search").val("");

			//编制日期
			$("#bzrq_search").val("");

			//组织机构
			$("#dprtcode_search").val(userJgdm);
		},
		/**将搜索条件封装*/
		getParams : function(){
			var searchParam = {};
			
			var paramsMap = {};
			
			var keyword = $.trim($("#keyword_search", $("#"+this.id)).val());
			var jx = $.trim($("#jx_search", $("#"+this.id)).val());
			var zt = $.trim($("#zt_search", $("#"+this.id)).val());
			
			var tgqx = $.trim($("#bzrq_search", $("#"+this.id)).val());
			var tgqxBegin='';
			var tgqxEnd='';
			if (null != tgqx && "" != tgqx) {
				tgqxBegin = tgqx.substring(0, 10) + " 00:00:00";
				tgqxEnd = tgqx.substring(13, 23) + " 23:59:59";
			}
			paramsMap.tgqxBegin = tgqxBegin;
			paramsMap.tgqxEnd = tgqxEnd;
			paramsMap.keyword = keyword;
			paramsMap.userId = userId;
			paramsMap.userType = userType;
			if(jx != null && jx != ''){
				searchParam.jx = jx;
			}
			if(zt != null && zt != ''){
				searchParam.zt = zt;
			}
			searchParam.dprtcode = $.trim($("#dprtcode_search", $("#"+this.id)).val());
			searchParam.paramsMap = paramsMap;
			return searchParam;
		},
		/**开始搜索*/
		doSearch : function(){
			this.hideBottom();
			this.resetThIco(); //重置列头排序图标
			this.goPage(this.pagination.page, this.pagination.sort, this.pagination.order);
		},
		resetThIco : function(){
			TableUtil.resetTableSorting(this.tableDivId); //重置排序图标
			var pgd = $("th[name='pgd']");
			if(pgd.hasClass("upward")) {
				pgd.removeClass("upward").addClass("downward");
			}
			var lywj = $("th[name='lywj']");
			if(lywj.hasClass("upward")) {
				lywj.removeClass("upward").addClass("downward");
			}
		},
		/**后台数据请求*/
		performAction : function(url,param,message,divId){
			var this_ = this;
			startWait($("#"+divId));
			// 提交数据
			AjaxUtil.ajax({
				url:url,
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify(param),
				dataType:"json",
				modal:$("#"+divId),
				success:function(data){
					finishWait($("#"+divId));
					
					id = data;		
					pageParam=this_.encodePageParam();
					AlertUtil.showMessage(message, this_.decodePageParam());
					
					eo_add_alert_Modal.close();
					$("#AssignEndModal").modal("hide");
				}
			});
		},
		/**打开新增EO弹出框*/
		openWinAdd : function(){
			var this_ = this;
			var obj = {};
			var eoSub = {};
			obj.eoSub = eoSub;
			obj.id = "0";
			obj.syxszList = null; //适用性列表
			obj.sylb = null;
			eo_add_alert_Modal.show({
				modalHeadCN : '新增',
				modalHeadENG : 'Add',
				editParam:1,//新增
				viewObj:obj,
				dprtcode:userJgdm,//机构代码
				callback : function(data) {//回调函数
					if (data != null) {
						var divId = "EOAddModal";
						var message = '保存成功!';
						if(data.paramsMap != null && data.paramsMap.operationType != null && data.paramsMap.operationType != ''){
							if(data.paramsMap.operationType == 2){
								message = '提交成功!';
								divId = "alertConfirmModal";
							}
						}
						var url = basePath+"/project2/eo/save";
						this_.performAction(url,data,message,divId);
					}
				}
			});
		},
		/**打开修改EO弹出框*/
		openWinEdit : function(id){//打开修改工卡弹出框
			var this_ = this;
			this_.selectById(id,function(obj){
				if(!(obj.zt == "1" || obj.zt == "5" || obj.zt == "6")){
					AlertUtil.showMessage('该EO已更新，请刷新后再进行操作!');
					this_.refreshPage();
					return;
				}
				eo_add_alert_Modal.show({
			 		modalHeadCN : '编辑',
					modalHeadENG : 'Edit',
					editParam:2,//编辑
					viewObj:obj,
					dprtcode:obj.dprtcode,//机构代码
					callback : function(data) {//回调函数
						if (data != null) {
							data.id = id;
							var divId = "EOAddModal";
							var message = '保存成功!';
							if(data.paramsMap != null && data.paramsMap.operationType != null && data.paramsMap.operationType != ''){
								if(data.paramsMap.operationType == 2){
									message = '提交成功!';
									divId = "alertConfirmModal";
								}
							}
							var url = basePath+"/project2/eo/update";
							this_.performAction(url,data,message,divId);
						}
					}
				});
			});
		},
		/**打开改版EO弹出框*/
		openWinRevision : function(id){
			var this_ = this;
			this_.selectById(id,function(obj){
				if(!((obj.zt == "9" || obj.zt == "10") && obj.zxbs == "2" && (obj.bBbid == "" || obj.bBbid == null))){
					AlertUtil.showMessage('该EO已更新，请刷新后再进行操作!');
					this_.refreshPage();
					return;
				}
				eo_add_alert_Modal.show({
			 		modalHeadCN : '改版',
					modalHeadENG : 'Revision',
					editParam:5,//改版
					viewObj:obj,
					dprtcode:obj.dprtcode,//机构代码
					callback : function(data) {//回调函数
						if (data != null) {
							data.id = id;
							var divId = "EOAddModal";
							var message = '保存成功!';
							if(data.paramsMap != null && data.paramsMap.operationType != null && data.paramsMap.operationType != ''){
								if(data.paramsMap.operationType == 2){
									message = '提交成功!';
									divId = "alertConfirmModal";
								}
							}
							var url = basePath+"/project2/eo/doRevision";
							this_.performAction(url,data,message,divId);
						}
					}
				});
			});
		},
		/**打开审核弹出框*/
		openWinAudit : function(id){
			var this_ = this;
			this_.selectById(id,function(obj){
				if(!(obj.zt == "2")){
					AlertUtil.showMessage('该EO已更新，请刷新后再进行操作!');
					this_.refreshPage();
					return;
				}
				eo_add_alert_Modal.show({
			 		modalHeadCN : '审核',
					modalHeadENG : 'Audit',
					editParam:3,//审核
					viewObj:obj,
					dprtcode:obj.dprtcode,//机构代码
					callback : function(data,message) {//回调函数
						if (data != null) {
							data.id = id;
							var url = basePath+"/project2/eo/doAudit";
							this_.performAction(url,data,message,"alertConfirmModal");
						}
					}
				});
			});
		},
		/**打开审核弹出框*/
		openWinApprove : function(id){
			var this_ = this;
			this_.selectById(id,function(obj){
				if(!(obj.zt == "3")){
					AlertUtil.showMessage('该EO已更新，请刷新后再进行操作!');
					this_.refreshPage();
					return;
				}
				eo_add_alert_Modal.show({
			 		modalHeadCN : '批准',
					modalHeadENG : 'Approve',
					editParam:4,//批准
					viewObj:obj,
					dprtcode:obj.dprtcode,//机构代码
					callback : function(data,message) {//回调函数
						if (data != null) {
							data.id = id;
							var url = basePath+"/project2/eo/doApprove";
							this_.performAction(url,data,message,"alertConfirmModal");
						}
					}
				});
			});
		},
		/**
		 * 打开查看弹出框
		 * 		1:EO
		 *      2:WO
		 * */
		openWinView : function(id,pageSwitch){
			if(pageSwitch == 1){
				window.open( basePath+"/project2/eo/view?id="+id);
			}
			else if(pageSwitch == 2){
				window.open( basePath+"/produce/workorder/woView?gdid="+id);
			}
		},
		/**根据ID查找数据*/
		selectById : function(id,obj){//通过id获取数据
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/project2/eo/selectById",
				type:"post",
				data:{eoId:id,viewFlag:"0"},
				dataType:"json",
				success:function(data){
					if(data != null){
						if(typeof(obj)=="function"){
							obj(data); 
						}
					}
				}
			});
		},
		/**打开提交弹出框*/
		openWinSubmit : function(id,dprtcode_){
			var this_ = this;

			var message = "提交成功!";
			var tip = "您确定要提交吗？";
			
			AlertUtil.showConfirmMessage(tip, {callback: function(){
				var data = {};
				data.id = id;
				var url = basePath+"/project2/eo/doSubmit";
				this_.doAlertAction(url,data,message);
			}});
		},
		doAlertAction : function(url,param,message){
			var this_ = this;
			startWait();
			AjaxUtil.ajax({
				url:url,
				contentType:"application/json;charset=utf-8",
				type:"post",
				data:JSON.stringify(param),
				dataType:"json",
				success:function(data){
					finishWait();
					AlertUtil.showMessage(message);
					this_.refreshPage();
				}
			});
		},
		/**打开关闭弹出框*/
		openWinClose : function(id){
			var this_ = this;
			this_.selectById(id,function(obj){
				if(!(obj.zt == "2" || obj.zt == "3" || obj.zt == "4" || obj.zt == "7")){
					AlertUtil.showMessage('该EO已更新，请刷新后再进行操作!');
					this_.refreshPage();
					return;
				}
				AssignEndModal.show({
					zt:obj.zt,
					edit:true,//是否可编辑,可编辑：关闭操作;不可编辑：查看关闭详情
					chinaHead:'EO编号',//单号中文名称
					englishHead:'EO No.',//单号英文名称
					jsdh:obj.eobh,//关闭单号
			 		jsyy:"",//指定结束原因
			 		attachemtShow:true,//是否需要上传附件
					callback: function(data){//回调函数
						AlertUtil.showConfirmMessage("您确定要关闭吗？", {callback: function(){
							var message = '关闭成功!';
							data.id = id;
							var url = basePath+"/project2/eo/doClose";
							this_.performAction(url,data,message);
						}});
					}
				});
			});
		},
		/**
		 * 删除EO
		 */
		del : function(id){
			var this_ = this;
			this_.selectById(id,function(obj){
				if(!(obj.zt == "1" || obj.zt == "5" || obj.zt == "6")){
					AlertUtil.showMessage('该EO已更新，请刷新后再进行操作!');
					this_.refreshPage();
					return;
				}
				AlertUtil.showConfirmMessage("确定要删除吗？", {callback: function(){
					AjaxUtil.ajax({
						type:"post",
						url:basePath+"/project2/eo/doDelete",
						dataType:"json",
						data:{'eoId':id},
						success:function(data) {
							pageParam= this_.encodePageParam();
							AlertUtil.showMessage('删除成功!', this_.decodePageParam());
							this_.refreshPage();
						}
					});
				}});
			});
		},
		// 拼接评估单
		getPgds : function(list, id) {
			var html = "";
			if (list != null) {
				for (var i = 0; i < list.length; i++) {
					if(list[i] == null){continue;}
					
					var showContent = (StringUtil.escapeStr(list[i].pgdh))+" R"+StringUtil.escapeStr(list[i].bb);
					if(showContent.length > 18){
						showContent = showContent.substring(0,18)+"...";
					}
					
					if (i == 1) {
						html = html + "　<i class='icon-caret-down' id='pgd" + id
								+ "icon' onclick=eo_main.switchAll('" + id + "')></i><div id='pgd"
								+ id + "' style='display:none'>";
					}
					if (i == 0) {
						html += "<a href='javascript:void(0);' title='"
								+ StringUtil.escapeStr(list[i].pgdh)
								+ "' onclick=\"eo_main.toViewPgd('" + list[i].pgdid
								+ "')\" >" + showContent
								+ "</a>";
					}
					if (i != 0) {
						html += "<a href='javascript:void(0);' title='"
								+ StringUtil.escapeStr(list[i].pgdh)
								+ "' onclick=\"eo_main.toViewPgd('" + list[i].pgdid
								+ "')\" >" + showContent
								+ "</a>";
						html += "<br>";

					}
					if (i != 0 && i == list.length - 1) {
						html += "</div>";
					}
				}
			}
			return html;
		},
		// 单行展开评估单
		switchPgd : function(id) {
			new Sticky({
				tableId : 'eo_main_table'
			});
			if ($("#pgd" + id).is(":hidden")) {
				$("#pgd" + id).fadeIn(500);
				$("#pgd" + id + 'icon').removeClass().addClass("icon-caret-up cursor-pointer");
			} else {
				$("#pgd" + id).hide();
				$("#pgd" + id + 'icon').removeClass().addClass("icon-caret-down cursor-pointer");
			}
		},
		// 列展开评估单
		vieworhidePgd : function() {
			new Sticky({
				tableId : 'eo_main_table'
			});
			var obj = $("th[name='pgd']");
			var flag = obj.hasClass("downward");
			if (flag) {
				obj.removeClass("downward").addClass("upward");
			} else {
				obj.removeClass("upward").addClass("downward");
			}
			for (var i = 0; i < choseAllIds.length; i++) {
				if (flag) {
					$("#pgd" + choseAllIds[i]).fadeIn(500);
					$("#pgd" + choseAllIds[i] + 'icon').removeClass().addClass("icon-caret-up cursor-pointer");
				} else {
					$("#pgd" + choseAllIds[i]).hide();
					$("#pgd" + choseAllIds[i] + 'icon').removeClass().addClass("icon-caret-down cursor-pointer");
				}
			}
		},
		//评估单链接地址
		toViewPgd : function(id){
			window.open(basePath+"/project2/assessment/view?id="+id);
		},
		// 拼接来源文件
		getLywjs : function(list, id) {
			var html = "";
			if (list != null) {
				for (var i = 0; i < list.length; i++) {
					
					var showContent = "";
					
					if(list[i] != null)
					{
						showContent = (StringUtil.escapeStr(list[i].jswjbh))+" R"+StringUtil.escapeStr(list[i].bb);
					}
					
					if(showContent.length > 18){
						showContent = showContent.substring(0,18)+"...";
					}
					
					if (i == 1) {
						html = html + "<div id='lywj" + id + "' style='display:none'>";
					}
					if (i >= 0) {
						html += "<a href='javascript:void(0);' title='";
							    if(showContent.length > 0){
							    	html +=  (StringUtil.escapeStr(list[i].jswjbh))+" R"+StringUtil.escapeStr(list[i].bb);
							    	html +=  "' onclick=\"eo_main.toViewLywj('" + list[i].id + "')\" >" + showContent;
							    }else{
							    	html +=  "' >" + showContent;
							    }
						html +=  "</a>";
						html += "<br>";
					}
					if (i != 0 && i == list.length - 1) {
						html += "</div>";
					}
				}
			}
			return html;
		},
		// 单行展开来源文件
		switchLywj : function(id) {
			new Sticky({
				tableId : 'eo_main_table'
			});
			if ($("#lywj" + id).is(":hidden")) {
				$("#lywj" + id).fadeIn(500);
				/*$("#lywj" + id + 'icon').removeClass().addClass("icon-caret-up cursor-pointer");*/
			} else {
				$("#lywj" + id).hide();
				/*$("#lywj" + id + 'icon').removeClass().addClass("icon-caret-down cursor-pointer");*/
			}
		},
		// 列展开来源文件
		vieworhideLywj : function() {
			new Sticky({
				tableId : 'eo_main_table'
			});
			var obj = $("th[name='pgd']");
			var flag = obj.hasClass("downward");
			/*if (flag) {
				obj.removeClass("downward").addClass("upward");
			} else {
				obj.removeClass("upward").addClass("downward");
			}*/
			for (var i = 0; i < choseLywjIds.length; i++) {
				if (!flag) {
					$("#lywj" + choseLywjIds[i]).fadeIn(500);
					/*$("#lywj" + choseLywjIds[i] + 'icon').removeClass().addClass("icon-caret-up cursor-pointer");*/
				} else {
					$("#lywj" + choseLywjIds[i]).hide();
					/*$("#lywj" + choseLywjIds[i] + 'icon').removeClass().addClass("icon-caret-down cursor-pointer");*/
				}
			}
		},
		//文件来源链接地址
		toViewLywj : function(id){
			window.open(basePath+"/project2/airworthiness/view?id="+id);
		},
		viewAll : function(){
			var this_ = this;
			this_.vieworhidePgd();
			this_.vieworhideLywj();
		},
		switchAll : function(id){
			var this_ = this;
			this_.switchPgd(id);
			this_.switchLywj(id);
		},
		/**
		 * 机构代码改变时执行
		 */
		changeDprt : function(){
			var this_ = this;
			var dprtcode = $.trim($("#dprtcode_search", $("#"+this.id)).val());
			this_.dprtcode = dprtcode;
			this_.initPlaneModel(dprtcode);
			this_.reload();
		},
		/***
		 * 控制元素是否可编辑
		 * @param elementId 元素ID
		 */
		setRedonly : function(elementId){
			if($("#"+elementId).css("visibility")=='hidden'){
				$("#"+elementId).val("");
				$("#"+elementId).css("visibility","visible");
			}else{
				$("#"+elementId).val("");
				$("#"+elementId).css("visibility","hidden");
			}
		},
		initWebuiPopover : function(){//初始化
			var this_ = this;
			WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
				return this_.getHistoryAttachmentList(obj);
			});
			$("#engineering_main_table_div").scroll(function(){
				$('.attachment-view').webuiPopover('hide');
			});
		},
		getHistoryAttachmentList : function(obj){//获取历史附件列表
			var jsonData = [
		         {mainid : $(obj).attr('qtid'), type : '其它附件'}
		        ,{mainid : $(obj).attr('gznrfjid'), type : '工作内容附件'}
		    ];
			return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
		},
		//关闭
		shutDown : function(e){
			var eobh=$(e).attr("eobh"); //EO编号
			var gbrid=$(e).attr("gbrid");//关闭人
			var gbyy=$(e).attr("gbyy"); //关闭原因
			var gbrq=$(e).attr("gbrq"); //关闭日期
			var zt=$(e).attr("zt"); //状态
		 	AssignEndModal.show({
		 		chinaHead:'EO编号',//单号中文名称
				englishHead:'EO No.',//单号英文名称
		 		edit:false,//是否可编辑,true可编辑：指定结束操作;false不可编辑：查看结束详情
		 		jsdh:eobh,//EO单号
		 		jsr:gbrid,//指定结束人
		 		jsyy:gbyy,//指定结束原因
		 		jssj:gbrq,//指定结束时间
		 		zt:zt,//状态
		 		callback: function(data){//回调函数
		 		}
		 	});
		},
		//关闭EO执行对象:b_g2_01001表 关闭状态 != 9、10  并且   b_g2_010.状态=生效
		openEOZxdxClose : function(id,eoId){
			var this_ = this;
			
			//第一层:校验eo状态=7
			this_.selectById(eoId,function(obj){
				if(obj.zt != "7"){
					AlertUtil.showMessage('该EO已更新，请刷新后再进行操作!');
					this_.refreshPage();
					return;
				}
				
				//第二层:校验b_g2_01001表 关闭状态 != 9、10
				this_.selectZxdxById(id,function(obj){
					if(obj.gbzt == "9" || obj.gbzt == "10"){
						AlertUtil.showMessage('该执行对象已更新，请刷新后再进行操作!');
						this_.refreshPage();
						return;
					}
					AssignEndModal.show({
						edit:true,//是否可编辑,可编辑：关闭操作;不可编辑：查看关闭详情
						chinaHead:'执行对象',//单号中文名称
						englishHead:'ExeObj',//单号英文名称
						jsdh:obj.bh+"/"+obj.xlh,//关闭单号
				 		jsyy:"",//指定结束原因
						callback: function(data){//回调函数
							AlertUtil.showConfirmMessage("您确定要关闭吗？", {callback: function(){
								var message = '关闭成功!';
								
								var zxdxObj = {};
								zxdxObj.id = id;
								zxdxObj.mainid = eoId;
								zxdxObj.gbyy = data.gbyy;
								zxdxObj.gbzt = data.zt;
								
								AjaxUtil.ajax({
									type:"post",
									contentType:"application/json;charset=utf-8",
									url:basePath+"/project2/eo/doZxdxClose",
									dataType:"json",
									data:JSON.stringify(zxdxObj),
									success:function(data) {
										AlertUtil.showMessage(message);
										eo_add_alert_Modal.close();
										$("#AssignEndModal").modal("hide");
										$("#"+eoId).click();
									}
								});
								
							}});
						}
					});
				});
				
			});
		},
		//确认EO执行对象
		openEOZxdxConfirm : function(id,eoId){
			var this_ = this;
			this_.selectZxdxById(id,function(obj){
				if(obj.qrzt == "1"){
					AlertUtil.showMessage('该执行对象已更新，请刷新后再进行操作!');
					this_.refreshPage();
					return;
				}
				AlertUtil.showConfirmMessage("是否要完成确认？", {callback: function(){
					AjaxUtil.ajax({
						type:"post",
						url:basePath+"/project2/eo/doZxdxConfirm",
						dataType:"json",
						data:{'zxdxId':id},
						success:function(data) {
							AlertUtil.showMessage('确认成功!');
							$("#"+eoId).click();
						}
					});
				}});
			});
		},
		/**根据ID查找数据*/
		selectZxdxById : function(id,obj){//通过id获取数据
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/project2/eo/selectZxdxById",
				type:"post",
				data:{zxdxId:id},
				dataType:"json",
				success:function(data){
					if(data != null){
						if(typeof(obj)=="function"){
							obj(data); 
						}
					}
				}
			});
		},
		 encodePageParam : function() {
			var this_ = this;
			var pageParam = {};
			pageParam.pagination = this_.pagination;
			return Base64.encode(JSON.stringify(pageParam));
		},
		 decodePageParam : function() {
			 var this_ = this;
			try {
				var decodeStr = Base64.decode(pageParam);
				var pageParamJson = JSON.parse(decodeStr);
				
				var params = pageParamJson.params;
				if (pageParamJson.pagination) {
					this_.goPage(pageParamJson.pagination.page,
							pageParamJson.pagination.sort,
							pageParamJson.pagination.order);
				} else {
					this_.goPage(1, "auto", "desc");
				}
			} catch (e) {
				this_.goPage(1, "auto", "desc");
			}
		},
		/**
		 * 圈阅情况
		 * @param id
		 */
		toCirculation : function(id){
			var param = {};
			param.id = id;
			AjaxUtil.ajax({
				url : basePath + "/project2/bulletin/viewMark",
				type : "post",
				async : false,
				contentType : "application/json;charset=utf-8",
				data : JSON.stringify(param),
				dataType : "json",
				success : function(data) {
					if(data.length>0){
						var htmlContent='';
						$.each(data,function(index,row){
							htmlContent += "<tr>";
							htmlContent += "<td >"+StringUtil.escapeStr(row.department?row.department.dprtname:"")+"</td>";
							htmlContent += "<td class='text-center'>"+(row.isJs==1?"已阅":row.isJs==0?"未阅":"")+"</td>";
							htmlContent += "<td class='text-center'>"+(row.isJs==1?formatUndefine(row.whsj):"")+"</td>";
							htmlContent += "</tr>";
						});
						$("#viewMarkupStatus_List").empty();
						$("#viewMarkupStatus_List").html(htmlContent);
					}else{
						$("#viewMarkupStatus_List").empty();
						$("#viewMarkupStatus_List").append("<tr><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>");
					}
				}
			});
			$("#viewMarkupStatus_modal").modal("show");
		},
		print:function(id,e){
			if(e.stopPropagation) { //W3C阻止冒泡方法  
		        e.stopPropagation();  
		    } else {  
		        e.cancelBubble = true; //IE阻止冒泡方法  
		    }
			var url=basePath+"/project2/eo/eo?id=" + encodeURIComponent(id);
			window.open(basePath+'/static/lib/pdf.js/web/viewer.html?file='+encodeURIComponent(url),
					'PDF','width:50%;height:50%;top:100;left:100;');	
		}
};


function exportEoList(){
	var serachParams=eo_main.getParams();
	window.open(basePath+"/project2/eo/EOChapter.xls?params="+JSON.stringify(serachParams));
}
