$(function(){
	Navigation(menuCode, '', '', 'gc_001001', '孙霁', '2017-08-02', '孙霁', '2017-08-02');
	$('.date-picker').datepicker({
		autoclose : true,
		clearBtn : true
	});
	var dprtcode = $("#dprtcode").val();
	//验证
	validation();
	//初始化日志功能
	//logModal.init({code:'B_G2_000'});
	//飞机注册号
	initfjzch(dprtcode);
	//加载列表
	taskhistoryMain.reload();
	
});
//章节号弹窗
function openList(){
	var zjh = $.trim($("#wczjh").val());
	var dprtcode = $.trim($("#dprtcode").val());
	FixChapterModal.show({
		selected:zjh,//原值，在弹窗中默认勾选
		dprtcode:dprtcode,
		callback: function(data){//回调函数
			var wczjh = '';
			if(data != null){
				wczjh = data.zjh;
			}
			$("#wczjh").val(formatUndefine(wczjh));
		}
	});
}
// 组织机构改变事件
function dprtChange(){
	initfjzch($("#dprtcode").val());
	taskhistoryMain.reload();
}
//飞机搜索框
function initfjzch(dprtcode){
	var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:dprtcode});
	var planeRegOption = "";
	if(planeDatas != null && planeDatas.length >0){
		$.each(planeDatas,function(i,planeData){
			planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+" "+StringUtil.escapeStr(planeData.XLH)+"</option>";
		});
		planeRegOption += "<option value='-1' >N/A</option>";
	}else{
		planeRegOption += "<option value='' >"+"暂无飞机"+"</option>";
	}
	$("#fjzch").html(planeRegOption); 
}
//验证
function validation(){
 $('#form').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	jswjly: {
	                validators: {
	                	notEmpty: {
	                        message: '来源不能为空'
	                    }
	                }
	            },
	           jswjlx: {
	                validators: {
	                    notEmpty: {
	                        message: '文件类型不能为空'
	                    }
	                }
	            },
	           jswjwjbh: {
	                validators: {
	                    notEmpty: {
	                        message: '文件编号不能为空'
	                    },
	                    regexp: {
	                    	  regexp: /^[\x00-\xff]*$/,
	 	                      message: '文件编号不能输入中文及中文符号'
		                }
	                }
	            },
	           jswjbb: {
	                validators: {
	                    notEmpty: {
	                        message: '版本不能为空'
	                    },
	                    regexp: {
	                    	  regexp: /^[\x00-\xff]*$/,
	 	                      message: '版本不能输入中文及中文符号'
		                }
	                }
	            }
	        }
	    });
}

var taskhistoryMain={
		gatherSearchParam:function(){
			var searchParam={};
			 searchParam.dprtcode=$("#dprtcode").val();
			 searchParam.gdlx=$("#gdlx").val();
			 searchParam.zjh=$("#zjh").val();
			 searchParam.fjzch=$("#fjzch").val();
			var paramsMap = {};
			 paramsMap.rwh=$("#rwh").val();
			 searchParam.keyword=$.trim($('#keyword_search').val());
			 searchParam.paramsMap = paramsMap;
			 var ztList = [];
			 $("input[name='zt_search']:checked").each(function() {
				 ztList.push(this.value);
			 });			
			 searchParam.paramsMap.ztList = ztList;
			 return searchParam;
		},
		goPage:function(pageNumber,sortType,sequence){
			this.AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
		},
		AjaxGetDatasWithSearch:function(pageNumber,sortType,sequence){
			 var _this = this;
			 pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
			 var searchParam=_this.gatherSearchParam();
			 if(searchParam.paramsMap.ztList.length == 0){
				 $("#taskhistory_list").empty();
				 $("#taskhistory_list").append("<tr><td class='text-center' colspan=\"22\">暂无数据 No data.</td></tr>");
				 return false;
			 }
			 searchParam.pagination = pagination;
			 startWait();
			 AjaxUtil.ajax({
				 url:basePath+"/produce/taskhistory/queryAll",
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(searchParam),
				   success:function(data){
					   finishWait();
			 			if(data.rows.length > 0){
			 			_this.appendContentHtml(data.rows);
			 			//分页
			 			var page_ = new Pagination({
							renderTo : "taskhistory_pagination", 
							data: data,
							sortColumn : sortType,
							orderType : sequence,
							extParams:{},
							goPage: function(a,b,c){
								_this.AjaxGetDatasWithSearch(a,b,c);
							}
						});
			 		// 标记关键字
						   signByKeyword($("#keyword_search").val(),[4,6,10,16]);
			 		
			 		}else{
			 			$("#taskhistory_list").empty();
						$("#taskhistory_pagination").empty();
						$("#taskhistory_list").append("<tr><td class='text-center' colspan=\"22\">暂无数据 No data.</td></tr>");
					}
			 			new Sticky({tableId:'quality_check_list_table'});
			 			hideBottom();
			 		}
			 });
		},
		showUp: function(){
			var isBottomShown = false;
			if($(".displayContent").is(":visible")){
				isBottomShown = true;
			}
			if(isBottomShown){
				$("<div class='pull-right' id='hideIcon' style='margin-right:15px;'><img src='"+basePath+"/static/images/down.png' onclick='hideBottom()' style='width:36px;cursor:pointer;'></div>").insertAfter($(".fenye", $("#taskhistory_pagination")));
			}
		},
		reload:function(){
			TableUtil.resetTableSorting("quality_check_list_table")
			hideBottom();
			this.goPage(1,"auto","desc");
		},
		// 表格拼接
		appendContentHtml:function(list){
			var _this = this;
			var htmlContent = '';
			
			$.each(list,function(index,row){
				htmlContent += "<tr gdid='"+row.paramsMap.gdid+"' gdbh ='"+row.gdbh+"' wgbs ='"+row.wgbs+"' onclick='taskhistoryMain.showChildTR(this)'>";
				 htmlContent += "<td style='text-align:center;vertical-align:middle;' class='fixed-column '>"+(index+1)+"</td>"; 
				    htmlContent += "<td style='text-align:center;vertical-align:middle;' class='fixed-column'>"+StringUtil.escapeStr(row.sjJsrq?row.sjJsrq.substring(0,10):'')+"</td>"; 
				    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.zjh)+" "+StringUtil.escapeStr(row.paramsMap.zjhywms)+"'>"+StringUtil.escapeStr(row.zjh)+" "+StringUtil.escapeStr(row.paramsMap.zjhywms)+"</td>";
				    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.gdbh)+"'><a href=\"javascript:viewWorkOrder('"+row.paramsMap.gdid+"')\">"+StringUtil.escapeStr(row.gdbh)+"</a></td>";
				    htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+StringUtil.escapeStr(row.fjzch)+"'><a href=\"javascript:viewFjzch('"+row.fjzch+"','"+row.dprtcode+"')\">"+StringUtil.escapeStr(row.fjzch)+"</a></td>";
				    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.gdbt)+"'>"+StringUtil.escapeStr(row.gdbt)+"</td>";
				    /*工单状态*/
					if(row.zt==9){
						htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum', row.zt))+"'><a href=\"javascript:taskhistoryMain.openWin4ViewZDClose('"+ row.paramsMap.gdid+ "')\" > "+formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum', row.zt))+"</a></td>";
					}else if(row.zt==10){
						htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum', row.zt))+"'><a href=\"javascript:taskhistoryMain.openWin4ViewWGClose('"+ row.paramsMap.gdid + "')\" > "+formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum', row.zt))+"</a></td>";
					}else{
						htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum', row.zt))+"'>"+formatUndefine(DicAndEnumUtil.getEnumName('workorderStatusEnum', row.zt))+"</td>";
					}
				    /*htmlContent += "<td class='text-left' title='"+DicAndEnumUtil.getEnumName('workorderStatusEnum',row.zt)+"'>"+DicAndEnumUtil.getEnumName('workorderStatusEnum',row.zt)+"</td>";*/
				    htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.paramsMap.gbbh)+"'><a href=\"javascript:viewWorkPackage('"+StringUtil.escapeStr(row.paramsMap.gbid)+"')\">"+StringUtil.escapeStr(row.paramsMap.gbbh)+"</td>";
				    if(row.gdlx == 1){
				    	htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum',row.paramsMap.gdzlx)+"'>"+DicAndEnumUtil.getEnumName('maintenanceProjectTypeEnum',row.paramsMap.gdzlx)+"</td>"; 
				    }else{
				    	htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+DicAndEnumUtil.getEnumName('workorderTypeEnum',row.gdlx)+"'>"+DicAndEnumUtil.getEnumName('workorderTypeEnum',row.gdlx)+"</td>"; 
				    }
				    if(row.paramsMap.rwbb){
				    	htmlContent += "<td style='text-align:left;vertical-align:middle;' title='"+StringUtil.escapeStr(row.paramsMap.rwh)+" R"+StringUtil.escapeStr(row.paramsMap.rwbb)+"'><a href=\"javascript:viewWxxm('"+StringUtil.escapeStr(row.paramsMap.rwid)+"','"+StringUtil.escapeStr(row.gdlx)+"')\">"+StringUtil.escapeStr(row.paramsMap.rwh)+" R"+StringUtil.escapeStr(row.paramsMap.rwbb)+"</a></td>"; 
				    }else{
				    	htmlContent += "<td style='text-align:left;vertical-align:middle;'></td>"; 
				    }
				    
				    htmlContent += "<td style='text-align:left;vertical-align:middle;' class='text-left' title='"+StringUtil.escapeStr(row.paramsMap.ckh)+"'>"+StringUtil.escapeStr(row.paramsMap.ckh)+"</td>"; 
				    htmlContent += "<td style='text-align:left;vertical-align:middle;' class='text-left' title='"+StringUtil.escapeStr(row.sjGzz)+"'>"+StringUtil.escapeStr(row.sjGzz)+"</td>"; 
				    htmlContent += "<td style='text-align:left;vertical-align:middle;' class='text-left' title='"+StringUtil.escapeStr(row.gzxx)+"'>"+StringUtil.escapeStr(row.gzxx)+"</td>"; 
				    htmlContent += "<td style='text-align:left;vertical-align:middle;' class='text-left' title='"+StringUtil.escapeStr(row.clcs)+"'>"+StringUtil.escapeStr(row.clcs)+"</td>"; 
				    htmlContent +=	"<td title='工单附件 Attachment' style='text-align:center;vertical-align:middle;'>";
				    if(row.paramsMap && (row.paramsMap.gdfjcount && row.paramsMap.gdfjcount > 0)){
				    	htmlContent += '<i gdid="'+row.paramsMap.gdid+'" class="attachment-view glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i>';
				    }
				    htmlContent +=	"</td>";
				    htmlContent += _this.formatLastfjjlb(row.paramsMap.fxjlbxx,row.zt);
				   /* htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.paramsMap.fxjlbxx)+"'>"+StringUtil.escapeStr(row.paramsMap.fxjlbxx)+"</td>"; 
				    htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.text)+"'>"+StringUtil.escapeStr(row.text)+"</td>"; 
				    htmlContent += "<td class='text-left' title='飞行记录本附件'>飞行记录本附件</td>"; */
				    htmlContent += _this.formatLastData(row.paramsMap.jhsjsj, row.zt);
				    htmlContent += "<td style='text-align:right;vertical-align:middle;' title='"+StringUtil.escapeStr(row.paramsMap.jhgs)+"'>"+StringUtil.escapeStr(row.paramsMap.jhgs)+"</td>"; 
				    
				    if(row.zt == 9){
				    	htmlContent += "<td></td>";
					}else{
						htmlContent += "<td style='text-align:right;vertical-align:middle;' title='"+StringUtil.escapeStr(row.paramsMap.sjgs)+"'>"+StringUtil.escapeStr(row.paramsMap.sjgs)+"</td>"; 
					}
				    
				    htmlContent += "<td style='text-align:center;vertical-align:middle;' title='"+StringUtil.escapeStr(row.paramsMap.dprtname)+"'>"+StringUtil.escapeStr(row.paramsMap.dprtname)+"</td>"; 
				    htmlContent += "</tr>" ;
		   $("#taskhistory_list").empty();
		   $("#taskhistory_list").html(htmlContent);
		   refreshPermission(); 
		   initWebuiPopover();
		   initWebuiPopoverFxjlb();
		 });
		},
		formatLastData : function(data, zt){
			var this_ = this;
			var str = "";
			if(data == null || data == ""){
				str += "<td></td>";
				str += "<td></td>";
				return str;
			}
			var list = data.split(",");
			var scjh = '';
			var scsj = '';
			$.each(list,function(index,row){
				var tdArr = row.split("#_#");
				scjh += this_.formatJkz(tdArr[0],tdArr[1]);
				scsj += this_.formatJkz(tdArr[0],tdArr[2]);
			});
			str += "<td title='"+scjh.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+scjh+"</td>";
			if(zt == 9){
				str += "<td></td>";
			}else{
				str += "<td title='"+scsj.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+scsj+"</td>";
			}
			return str;
		},
		/**
		 * 格式化监控值
		 */
		formatJkz : function(jklbh, value){
			if(value != null && value != ''){
				value = this.convertToHour(jklbh, value) + MonitorUnitUtil.getMonitorUnit(jklbh, "")+"</br>";
			}else{
				value = " "+"</br>";
			}
			return value;
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
		formatLastfjjlb : function(data,zt){
			var str = "";
			if(data == null || data == ""){
				str += "<td></td>";
				str += "<td></td>";
				//str += "<td></td>";
				return str;
			}
			var list = data.split(",");
			var fjjlb = '';
			var fjjlbTitle = '';
			var jlbym = '';
			//var jlbfj = '';
			$.each(list,function(index,row){
				var tdArr = row.split("#_#");
				fjjlb += "<a href=\"javascript:fjjlbView('"+tdArr[0]+"')\">"+tdArr[1]+"</a></br>";
				fjjlbTitle += tdArr[1]+"\n";
				jlbym += tdArr[2]+"</br>";
				//jlbfj +='<i fxjlbid="'+tdArr[0]+'" class="attachment-view-fxjlb glyphicon glyphicon glyphicon-list color-blue cursor-pointer "  style="font-size:15px"></i></br>';
			});
			str += "<td title='"+fjjlbTitle.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+fjjlb+"</td>";
			if(zt == 9){
				str += "<td  style='text-align:center;vertical-align:middle;'></td>";
			}else{
				str += "<td title='"+jlbym.replaceAll("</br>","\n")+"' style='text-align:center;vertical-align:middle;'>"+jlbym+"</td>";
			}
			//str += "<td title='飞行记录本附件' style='text-align:center;vertical-align:middle;'>"+jlbfj+"</td>";
			return str;
		},
		appendjxfw:function(list){
			 var htmlContent="";
			 if(list == null){
				 return htmlContent;
			 }
			 $.each(list,function(index,row){
				 if(index==0){
					 htmlContent += StringUtil.escapeStr(formatUndefine(row.fjjx));
				 }else{
					 htmlContent +=","+StringUtil.escapeStr(formatUndefine(row.fjjx));
				 }
			 });
			 return htmlContent;
		 },
		addTitle:function(){
			$("#maintenance_item_list tr td").each(function(){
				if(!$(this).attr("title")){
					$(this).attr("title", $(this).text());
				}
			});
		},
		//搜索条件显示与隐藏
		search:function(){
		
		},
		appendOrder:function(list){
			var htmlContent='';
			if(list != null && list !=''){
				$.each(list,function(index,row){
					if(index == 0){
						htmlContent += DicAndEnumUtil.getEnumName('projectBusinessEnum',row.zlxl);
					}else{
						htmlContent +="/"+DicAndEnumUtil.getEnumName('projectBusinessEnum',row.zlxl);
					}
				});
			}
			return htmlContent;
		},
		showChildTR:function(obj){
			var this_=this;
			//行变色
			$(obj).addClass('bg_tr').siblings().removeClass('bg_tr');
			// 下方div是否显示
			new Sticky({tableId:'quality_check_list_table'});
			var isBottomShown = false;
			if($(".displayContent").is(":visible")){
				isBottomShown = true;
			}
			TableUtil.showDisplayContent();
			if($("#hideIcon").length == 0){
				$('<div class="pull-right" id="hideIcon" style="margin-right:15px;"><img src="'+basePath+'/static/images/down.png" onclick="hideBottom()" style="width:36px;cursor:pointer;"/></div>').insertAfter($("#taskhistory_pagination .fenye"));
			}
			if(!isBottomShown){
				TableUtil.makeTargetRowVisible($(obj), $("#tableId"));
			}
			var ztContentHtml="";
			
			var gdid=$(obj).attr("gdid");
			var gdbh=$(obj).attr("gdbh");
			var wgbs=$(obj).attr("wgbs");
			if(wgbs == 1){
				taskhistoryFeedbackMain.AjaxGetDatasWithSearch(gdid);
			}else{
				taskhistoryFeedbackMain.appendSpanHtml({});
			}
			//加载完工反馈和拆换件记录
			taskhistoryRecordMain.AjaxGetDatasWithSearch(gdid,gdbh);
			
		},
		/**查看工单关闭(指定)弹框*/
		openWin4ViewZDClose : function(id){
			var this_ = this;
			this_.selectById(id,function(obj){
				Workorder135ZDCloseWin.show({
					viewObj:obj,
					isShowReplacementRecord:true,
					colOptionhead : false,				
				});	
			});
		},
		/**查看工单关闭(正常完成)弹框*/
		openWin4ViewWGClose : function(id){
			var this_ = this;
			this_.selectById(id,function(obj){
				Workorder135WGCloseWin.show({
					viewObj:obj,
					isShowReplacementRecord:true,
					colOptionhead:false,
				});	
			});
		},
		/**根据ID查找数据*/
		selectById : function(id,obj){//通过id获取数据
			AjaxUtil.ajax({
				async: false,
				url:basePath+"/produce/workorder/selectWOById",
				type:"post",
				data:{gdid:id},
				dataType:"json",
				success:function(data){
					if(data != null){
						if(typeof(obj)=="function"){
							obj(data); 
						}
					}
				}
			});
		}
};
function orderBy(obj){
	// 字段排序
		var orderStyle = $("#" + obj + "_order").attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
		});
		$("#" + obj + "_" + "order").removeClass("sorting");
		var orderType = "asc";
		if (orderStyle.indexOf("sorting_asc") >= 0) {
			$("#" + obj + "_" + "order").addClass("sorting_desc");
			orderType = "desc";
		} else {
			$("#" + obj + "_" + "order").addClass("sorting_asc");
			orderType = "asc";
		}
		orderStyle = $("#" + obj + "_order").attr("class");
		var currentPage = $("#pagination li[class='active']").text();
		taskhistoryMain.goPage(currentPage,obj,orderStyle.split("_")[1]);
}
//搜索条件显示与隐藏 
function search() {
	
	if ($("#divSearch").css("display") == "none") {
		$("#divSearch").css("display", "block");
		$("#icon").removeClass("icon-caret-down");
		$("#icon").addClass("icon-caret-up");
	} else {
		$("#divSearch").css("display", "none");
		$("#icon").removeClass("icon-caret-up");
		$("#icon").addClass("icon-caret-down");
	}
}
function hideBottom(){
	$("#hideIcon").remove();
	TableUtil.hideDisplayContent();
}

function encodePageParam(){
	 var pageParam={};
	 var params={};
	 params.keyword=$.trim($("#keyword_search").val());
	 params.zt=$.trim($("#zt").val());
	 params.zzjg=$.trim($("#dprtcode").val());
	 pageParam.params=params;
	 pageParam.pagination=pagination;
	 return Base64.encode(JSON.stringify(pageParam));
}
/**
 * 解码页面查询条件和分页 并加载数据
 * @returns
 */
function decodePageParam(){
	try{
		var decodeStr = Base64.decode(pageParam);
		var pageParamJson = JSON.parse(decodeStr);
		var params = pageParamJson.params;
		$("#keyword_search").val(params.keyword);
		$("#zt").val(params.zt);
		$("#dprtcode").val(params.dprtcode);
		$("#tgqx").val(params.tgqx);
		if(pageParamJson.pagination){
			taskhistoryMain.goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
		}else{
			taskhistoryMain.goPage(1,"auto","desc");
		}
	}catch(e){
		taskhistoryMain.goPage(1,"auto","desc");
	}
}

function scfjBox(obj){
	var checked = $(obj).is(":checked");
	if(checked){
		if(!uploadObj.wbwjm){
			$("#fileuploaderSingle").show();
		}else{
			$("#scwjWbwjm").show();
		}
	}else{
			$("#fileuploaderSingle").hide();
			$("#scwjWbwjm").hide();
	}
	
	
}

function openList(){
	var zjh = $.trim($("#zjh").val());
	var dprtcode = $.trim($("#dprtId").val());
	var parentWinId = "AddalertModal";
	FixChapterModal.show({
		selected:zjh,//原值，在弹窗中默认勾选
		dprtcode:dprtcode,
		parentWinId:parentWinId,
		callback: function(data){//回调函数
			var zjh = '';
			if(data != null){
				zjh = data.zjh;
			}
			$("#zjh").val(formatUndefine(zjh));
		}
	});
}

function syjxshowAndhide(){
	var userId=$("#userId").val();
	var Ispg=$("input:radio[name='Ispg']:checked").val();
	if(Ispg==1){
		$("#jxchecked").show();
		$("#pgqxDiv").show();
		/*$('#jxchecked input:checkbox').each(function () {
	        $(this).attr('checked',false);
		});
		$('#jxchecked select').each(function () {
			 $(this).val(userId);
	         $(this).hide();
		});*/
	}else{
		$("#jswjpgqx").val("");
		$("#pgqxDiv").hide();
		
		$("#jxchecked input[name='tecoid']").each(function () {
			if($(this).val()!="add"){
				techoIds.push($(this).val());
				$(this).val("add");
			}
		});
		$("#jxchecked").hide();
	}
	
}
//下载
function downloadfile(id){
	PreViewUtil.handle(id, PreViewUtil.Table.D_011);
}
//搜索条件重置
function searchreset(){
	 var zzjgid=$('#dprtId').val();
	 var zt = document.getElementsByName("zt_search");
		for (var i = 0; i < zt.length; i++) {
			if(zt[i].value == '10'){
				zt[i].checked=true;
			}else{
				zt[i].checked=false;
			}
		}
	
	$("#rwh").val("");
	 $("#divSearch").find("textarea").each(function(){
		 $(this).val("");
	 });
	 $("#divSearch").find("select").each(function(){
			$(this).val("");
		});
	 $("#keyword_search").val("");
	 $("#dprtcode").val(zzjgid);
	// checked();
	 $("#gdlx").val("");
	 $("#zjh").val("");
	//飞机注册号
	 initfjzch(zzjgid);
	 $("#xpgbs1").attr("checked","checked");
	 $("#xpgbs2").attr("checked","checked");
//	 multiSelectReset(zzjgid);
}

/**
 * 点击表头展开表格
 */
function vieworhideOutputAll(){
	var obj = $("#bottom_hidden_content th[name='output_return']");
	var flag = obj.hasClass("downward");
	if(flag){
		obj.removeClass("downward").addClass("upward");
	}else{
		obj.removeClass("upward").addClass("downward");
	}
	 for(var i=0;i<OutputAllIds.length;i++){
		 if(flag){				
			 $("#"+OutputAllIds[i]+'content1').fadeIn(500);
			 $("#"+OutputAllIds[i]+'icon1').removeClass("icon-caret-down");
			 $("#"+OutputAllIds[i]+'icon1').addClass("icon-caret-up");
		 }else{
			 $("#"+OutputAllIds[i]+'content1').hide();
			 $("#"+OutputAllIds[i]+'icon1').removeClass("icon-caret-up");
			 $("#"+OutputAllIds[i]+'icon1').addClass("icon-caret-down");
		 }
	 }
	 new Sticky({tableId:'OrderTable'}); //初始化表头浮动
}
function vieworhideOutput(i){
	 var flag = $("#"+OutputAllIds[i]+'content1').is(":hidden");
	 if(flag){
		 $("#"+OutputAllIds[i]+'content1').fadeIn(500);
		 $("#"+OutputAllIds[i]+'icon1').removeClass("icon-caret-down");
		 $("#"+OutputAllIds[i]+'icon1').addClass("icon-caret-up");
	 }else{
		 $("#"+OutputAllIds[i]+'content1').hide();
		 $("#"+OutputAllIds[i]+'icon1').removeClass("icon-caret-up");
		 $("#"+OutputAllIds[i]+'icon1').addClass("icon-caret-down");
	 }
	 new Sticky({tableId:'OrderTable'}); //初始化表头浮动
}

function customResizeHeight(bodyHeight, tableHeight){
	$(".displayContent .tab-content .tab-pane:visible .panel-body:first").css("overflow","auto");
	$(".displayContent .tab-content .tab-pane:visible .panel-body").not(":first").removeAttr("style");
	$("#Dropdown .panel-body").eq(1).addClass("padding-top-0 padding-bottom-0");
	var panelBodyH=parseInt($(".displayContent .panel-body:visible").css("height"));
	$("#profile .panel-body").find("table").parent().css("max-height",(panelBodyH-12)+"px")
}

function initWebuiPopover(){//初始化
	WebuiPopoverUtil.initWebuiPopover('attachment-view','body',function(obj){
		return getHistoryAttachmentList(obj);
	});
	$("#structrucRepair_table").scroll(function(){
		$('.attachment-view').webuiPopover('hide');
	});
}
function getHistoryAttachmentList(obj){//获取历史附件列表
	var jsonData = [
         {mainid : $(obj).attr('gdid'), type : '工单附件'}
    ];
	return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
}
function initWebuiPopoverFxjlb(){//初始化
	WebuiPopoverUtil.initWebuiPopover('attachment-view-fxjlb','body',function(obj){
		return getHistoryAttachmentListFxjlb(obj);
	});
	$("#structrucRepair_table").scroll(function(){
		$('.attachment-view-fxjlb').webuiPopover('hide');
	});
}
function getHistoryAttachmentListFxjlb(obj){//获取历史附件列表
	var jsonData = [
	                {mainid : $(obj).attr('fxjlbid'), type : '飞行记录本附件'}
	                ];
	return history_attach_alert_Modal.getHistoryAttachmentList(jsonData);
}
function viewFjzch(fjzch,dprtcode){
	window.open(basePath+"/aircraftinfo/view?fjzch="+fjzch+"&dprtcode="+dprtcode);
}
//跳转到工单查看
function viewWorkOrder(gdid){
	window.open( basePath+"/produce/workorder/woView?gdid="+gdid);
}
//跳转到工包查看
function viewWorkPackage(gbid){
	window.open(basePath+"/produce/workpackage/view?id="+gbid);

}
//跳转到维修项目
function viewWxxm(id,gdlx){
	if(gdlx == 1){
		window.open(basePath+"/project2/maintenanceproject/view?id="+id);
	}else if(gdlx == 2){
		window.open(basePath+"/project2/eo/view?id="+id);
	}else if(gdlx == 3){
		window.open(basePath+"/project2/workcard/view/"+id);
	}
}
//跳转打飞行记录本查看页面
function fjjlbView(id){
	window.open(basePath+"/produce/flb/view?id="+id);
}
//导出
function exportExcel(){
	var searchParam = taskhistoryMain.gatherSearchParam();
	 if(searchParam.paramsMap.ztList.length == 0){
		 searchParam.paramsMap.ztList = [0];
	 }
	window.open(basePath+"/produce/taskhistory/taskhistory.xls?paramjson="+JSON.stringify(searchParam));
	
}