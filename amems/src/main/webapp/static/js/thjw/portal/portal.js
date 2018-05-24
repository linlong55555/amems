
var Panel = function(params){
	var me = this;
	me.id = "";
	me.title = "";
//	me.icon = "icon-bookmark-empty";
	me.icon = "icon-hand-right";
	me.button = ['refresh', 'more', 'close'];
	me.renderTo = "portal";
	me.beforePanel = null;//渲染到哪个面板之后
	me.height = "250px";
	me.moreUrl = "";
	me.removeCallback = null;
	me.panelClass = "panel-primary";
	me.tableHead = "<tr></tr>";
	me.load = function(){};
	
	$.extend(me, params);
	
	var $div = $("<div name='panel-div' class='col-lg-6 col-sm-6 col-xs-6' style='padding-left:2px;padding-right:2px'></div>");
	var $divPanel = $("<div class='panel "+me.panelClass+"' style='height:"+me.height+";'></div>");
	var $divHead = $("<div class='panel-heading'><h3 class=\"panel-title\">" +
			"<i class='"+me.icon+" cursor-pointer margin-right-10'/>" +
			me.title + "<span class=\"badge margin-left-10\"></span>"+
			"<div class='pull-right'>" +
			"<i title='刷新 Refresh' class='icon-refresh cursor-pointer margin-left-10'/>" +
			"<i title='更多 More' class='icon-external-link cursor-pointer margin-left-10'/>" +
			"<i title='隐藏面板 Hide' class='icon-trash cursor-pointer margin-left-10'/>" +
			"</div>" +
	"</h3></div>");
	
//	var $divUl = $("<ul class='list-group padding-left-10 padding-right-10' style='height:"+me.height+";overflow:auto;'>");
	
	var $panelTable = $("<table class=\"table table-thin table-bordered table-striped table-hover table-set\" ></table>");
	var $tableHead = $("<thead></thead>");
	var $tableBody = $("<tbody></tbody>");
	
	function show(){
		$tableHead.html(me.tableHead);
		$panelTable.append($tableHead).append($tableBody);
		$divPanel.append($divHead).append($("<div class='panel-body padding-top-0 padding-left-0 padding-right-0 padding-bottom-0' style='height:213px;overflow:auto;'></div>").append($panelTable));
		$div.append($divPanel);
		if(me.beforePanel){
			me.beforePanel.getDiv().after($div);
		}else{
			$("#"+me.renderTo).prepend($div);
		}
		$divHead.find(".icon-trash").on("click", me.destory);
		$divHead.find(".icon-refresh").on("click", function(){
			me.load();
		});
		$divHead.find(".icon-external-link").on("click", function(){
			if(typeof me.moreUrl=='string'){
				window.open(me.moreUrl);
			}else{
				if($(this).is(":empty")){
					$(this).append('<ul class="dropdialoghover1 " style="border-radius:5px"><li><a data-toggle="modal" title="维修方案" href='+basePath+'/project/revisionNoticeBook/main>'
							+'修订维修方案'
						+'</a></li><li><a data-toggle="modal" title="工卡" href='+basePath+'/project/revisionNoticeBook/card_main >修订工卡'
						+'</a></li><li><a data-toggle="modal" title="MEL" href='+basePath+'/project/revisionNoticeBook/mel_main> 修订MEL'
						+'</a></li></ul>')
				}else{
					$(this).empty();
				}
			}
		});			
		
		me.load();
	};
	
	me.destory = function(flag){
		$div.remove();
		me.removeCallback(flag);
		me = null;
	}
	
	me.show = function(){
		show();
	};
	
	me.getDiv = function(){
		return $div;
	}
	
	me.getDataContainer = function(){
		return $tableBody;
	}
	
	me.setNumber = function(num){
		$(".badge", $divHead).html(num);
	}
	
	show();
	return me;
}

var panelData = [
	{id:"block5", permissioncode:"index:05", title:"我的培训通知", moreUrl:basePath+"/training/trainingnotice/manage", singleUrl: basePath+"/training/trainingnotice/find/"},
	{id:"block6", permissioncode:"index:06", title:"我的提订单（未完成）", moreUrl:basePath+"/aerialmaterial/reserve/manage", singleUrl: basePath+"/aerialmaterial/reserve/view?id="},
	{id:"block7", permissioncode:"index:07", title:"MCC 135工单预警（完工未关闭）", moreUrl:basePath+"/produce/workorder/main", singleUrl: basePath+"/produce/workorder/woView?gdid="},
	{id:"block8", permissioncode:"index:08", title:"MCC 145工单预警（完工未关闭）", moreUrl:basePath+"/produce/workorder145/main", singleUrl: basePath+"/produce/workorder145/woView?gdid="},
	{id:"block9", permissioncode:"index:09", title:"待办工作", moreUrl:basePath+"/project2/todo/main", singleUrl: [basePath+"/project2/airworthiness/view?id=",basePath+"/project2/assessment/view?id="]},
	{id:"block10", permissioncode:"index:10", title:"传阅", moreUrl:basePath+"/project2/bulletin/circulation/main", singleUrl: [basePath+"/project2/bulletin/view?id=",basePath+"/project2/eo/view?id="]}
]

var delPanel = []

var jcarousel = null;//快捷功能旋转木马

var shortcut = [];

var message = [];//消息

var msgData = [];//留言

var treeObj = null;//菜单树

var training =[];//培训

var trainingLoaded = false;//培训是否加载了
var messageLoaded = false;//消息是否加载了

$(document).ready(function(){
	init();
});

/**
 * 初始化页面，加载用户客制化信息
 * @returns
 */
function init(){
	var this_ = this;
	
	jcarousel = $('#shortcutDiv').jcarousel();
	
	$('.jcarousel-control-prev').on('jcarouselcontrol:active', function() {
	         $(this).removeClass('inactive');
	     }) .on('jcarouselcontrol:inactive', function() {
	         $(this).addClass('inactive');
	     }).jcarouselControl({
	         target: '-=1'
	     });
	
	$('.jcarousel-control-next').on('jcarouselcontrol:active', function() {
	         $(this).removeClass('inactive');
	     }).on('jcarouselcontrol:inactive', function() {
	         $(this).addClass('inactive');
	     }).jcarouselControl({
	         target: '+=1'
	     });
	
	startWait();
	AjaxUtil.ajax({
		url:basePath+"/sys/custom/block/list",
		type: "get",
		cache:false,
		dataType:"json",
		success:function(data){
			//加载用户不显示的block
			if(data && data.delPanel){
				$.each(data.delPanel, function(index, row){
					delPanel.push(row.kbm);
				})
				showPanel();
			}
			//加载用户快捷方法
			var shortHtml = "<ul class='top-height'>";
			if(data && data.shortcut){
				shortcut = data.shortcut;
				$.each(data.shortcut, function(index, row){
					shortHtml += "<li>";
					shortHtml += "<div style='position: relative;'>";
					shortHtml += "<a href=\""+(row.menu?(basePath+"/"+row.menu.path):"javascript:void(0);")+"\" class=\"btn btn-primary padding-top-1 padding-bottom-1\">";
					shortHtml += "<div class=\"font-size-12\">"+row.zwms+"</div>";
					shortHtml += "<div class=\"font-size-9\">"+row.ywms+"</div>";
					shortHtml += "</a>";
//					shortHtml += "<i class='icon-remove-circle close_x' style='color:red;' onclick='removeShortcut(\""+row.id+"\", 1, event);'></i>"
					shortHtml += "</div>";
					shortHtml += "</li>";
				})
			}
			shortHtml += '</ul>';
			jcarousel.html(shortHtml);
		    jcarousel.jcarousel('reload');
		    loadMenutree();
		    
		    //加载消息
		    if(!messageLoaded && data && data.message){
		    	message = data.message;
		    	messageLoaded = true;
		    	//弹出强制提示
				initPopMsg();
			}else{
				message = [];
			}
		    initMessage();
		    
			//初始化个性化定制内容
			$("#customBtn").on("click", function(){
				var html = "";
				$.each(panelData, function(index, row){
					if(checkBtnPermissions(row.permissioncode)){
						html += '<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0 form-group">';
						html += '<label class="col-lg-6 col-sm-6 col-xs-6 text-right padding-left-0 padding-right-0">';
						html += 	'<div class="font-size-12">'+row.title+'</div>';
						html += '</label>';
						html += '<div class="col-lg-6 col-sm-6 col-xs-6 padding-left-8 padding-right-0">';
						html += 	'<input panelid="'+row.id+'" type="checkbox" '+ (($.inArray(row.id, delPanel)==-1)?'checked':'') +' data-on-text="show" data-off-text="hide" data-on-color="success" data-off-color="warning" />';
						html += '</div>';
						html += '</div>';
					}
				})
				$("#customDiv").html(html);
				$("#customModal").modal("show");
				$("input:checkbox", $("#customModal")).bootstrapSwitch();
			});
			
			if(data && data.msg){
				msgData = data.msg;
			}
			initMsg();
			finishWait();
			
	    }
	});
	
	initFlowChart();
}

function exsistsShortcut(id){
	for(var i = 0; i< shortcut.length; i++){
		if(shortcut[i].cdid == id){
			return true;
		}
	}
	return false;
}


function showPanel(){
	var currentPanel = null;
	
	
	
	//待办工作
	if($.inArray(panelData[4].id, delPanel) == -1 && checkBtnPermissions(panelData[4].permissioncode)){ 
		if(!panelData[4].panel){
			var panel = new Panel({
				id: panelData[4].id,
				title: panelData[4].title,
				beforePanel: currentPanel,
				moreUrl: panelData[4].moreUrl,
				tableHead: "<tr><th class='colwidth-8'><div class='font-size-12 line-height-18'>来源编号</div><div class='font-size-9 line-height-18'>Source No.</div></th>" +
						"<th class='colwidth-3'><div class='font-size-12 line-height-18'>版本</div><div class='font-size-9 line-height-18'>Rev.</div></th>" +
						"<th class='colwidth-13'><div class='font-size-12 line-height-18'>飞机机型</div><div class='font-size-9 line-height-18'>A/C Type</div></th>" +
						"<th class='colwidth-9'><div class='font-size-12 line-height-18'>工作类型</div><div class='font-size-9 line-height-18'>Work Type</div></th>" +
						"<th class='colwidth-10'><div class='font-size-12 line-height-18'>说明</div><div class='font-size-9 line-height-18'>Description</div></th>" +
						"<th class='colwidth-5'><div class='font-size-12 line-height-18'>办理期限</div><div class='font-size-9 line-height-18'>Deal Date</div></th></tr>",
				removeCallback: function(flag){
					panelData[4].panel = null;
					if(flag !== true){
						delPanel.push(panelData[4].id);
						//往后台存入删除的面板
						removePanel(panelData[4].id);
					}
				},
				load: function(){
					var this_ = this;
					//this.getDataContainer().empty();
					AjaxUtil.ajax({
						url:basePath+"/sys/custom/block/block9",
						type: "get",
						cache:false,
						dataType:"json",
						success:function(data){
							if(data && data.rows){
								var tbodyHtml = "";
								if(data.rows.length > 0){
									$.each(data.rows, function(index, row){
										
										tbodyHtml += "<tr>";
										
//										var gddlxForOrder = formatUndefine(row.GDLX)=='1'?'4':row.GDLX;
										
										tbodyHtml += "<td title='"+row.LYBH+"'><a href='javascript:void(0);'  dbgzlx=\""+row.DBGZLX+"\"  bizId=\""+row.LYID+"\" >"+row.LYBH+"</a></td>";
										tbodyHtml += "<td align='center' title='"+(row.BB==null?"":"R"+StringUtil.escapeStr(row.BB))+"'>"+(row.BB==null?"":"R"+StringUtil.escapeStr(row.BB))+"</td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.FJJX)+"'>"+StringUtil.escapeStr(row.FJJX)+"</td>";
										var lx = DicAndEnumUtil.getEnumName('sendOrderEnum',row.DBGZLX);
									    tbodyHtml += "<td title='"+lx+"'>"+lx+"</td>";  
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.SM)+"'>"+StringUtil.escapeStr(row.SM)+"</td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.BLQX==null?"":row.BLQX.substring(0,10))+"'>"+StringUtil.escapeStr(row.BLQX==null?"":row.BLQX.substring(0,10))+"</td>";
										tbodyHtml += "</tr>";
									})
								}else{
									tbodyHtml = "<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>"
								}
								this_.getDataContainer().html(tbodyHtml);
								this_.setNumber(data.rows.length == 0 ?'':data.rows.length);
								
								$("a", this_.getDataContainer()).on("click", this_.view);
							}else{
								this_.setNumber('');
								var tbodyHtml = "<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>";
								this_.getDataContainer().html(tbodyHtml);
							}
					    }
					}); 
				},
				view: function(e){
					var dbgzlx = $(e.target).attr("dbgzlx");
					if(dbgzlx =='9'){
						window.open(panelData[4].singleUrl[0] + $(e.target).attr("bizId"));
					}else{
						window.open(panelData[4].singleUrl[1] + $(e.target).attr("bizId"));
					}
					
				}
			});
			panelData[4].panel = panel;
		}
		currentPanel = panelData[4].panel;
	}else{
		if(panelData[4].panel){
			panelData[4].panel.destory(true);
		}
	}
	
	//我的培训通知
	if($.inArray(panelData[0].id, delPanel) == -1 && checkBtnPermissions(panelData[0].permissioncode)){ 
		if(!panelData[0].panel){
			var panel = new Panel({
				id: panelData[0].id,
				title: panelData[0].title,
				beforePanel: currentPanel,
				moreUrl: panelData[0].moreUrl,
				tableHead: "<tr><th class='colwidth-6'><div class='font-size-12 line-height-18'>通知类型</div><div class='font-size-9 line-height-18'>Notice Type</div></th><th class='colwidth-20'><div class='font-size-12 line-height-18'>培训时间</div><div class='font-size-9 line-height-18'>Train Time</div></th><th class='colwidth-13'><div class='font-size-12 line-height-18'>培训地点</div><div class='font-size-9 line-height-18'>Train Place</div></th><th class='colwidth-8'><div class='font-size-12 line-height-18'>课程名称</div><div class='font-size-9 line-height-18'>Course Name</div></th><th class='colwidth-8'><div class='font-size-12 line-height-18'>培训形式</div><div class='font-size-9 line-height-18'>Train Model</div></th></tr>",
				removeCallback: function(flag){
					panelData[0].panel = null;
					if(flag !== true){
						delPanel.push(panelData[0].id);
						//往后台存入删除的面板
						removePanel(panelData[0].id);
					}
				},
				load: function(){
					var this_ = this;
					//this.getDataContainer().empty();
					AjaxUtil.ajax({
						url:basePath+"/sys/custom/block/block5",
						type: "get",
						cache:false,
						dataType:"json",
						success:function(data){
							if(data && data.rows && data.rows.length > 0){
								training = data.rows;
								var tbodyHtml = "";
								var state = [];
								state[1] = "参训";
								state[2] = "讲师";
								$.each(data.rows, function(index, row){
									tbodyHtml += "<tr>";
									tbodyHtml += "<td align='center' title='"+StringUtil.escapeStr(state[row.TZLX])+"'>"+StringUtil.escapeStr(state[row.TZLX])+"</td>";
									if(row.JH_JSRQ!=null&&row.JH_JSRQ!=''){
										tbodyHtml += "<td align='center' title='"+(row.JH_KSRQ.substring(0,10)+" "+StringUtil.escapeStr(row.JH_KSSJ))+"~"+(row.JH_JSRQ.substring(0,10)+" "+StringUtil.escapeStr(row.JH_JSSJ))+"' >"+(row.JH_KSRQ.substring(0,10)+" "+StringUtil.escapeStr(row.JH_KSSJ))+"~"+(row.JH_JSRQ.substring(0,10)+" "+StringUtil.escapeStr(row.JH_JSSJ))+"</td>";
									}else{
										tbodyHtml += "<td align='center' title='"+(row.JH_KSRQ.substring(0,10)+" "+StringUtil.escapeStr(row.JH_KSSJ))+"' >"+(row.JH_KSRQ.substring(0,10)+" "+StringUtil.escapeStr(row.JH_KSSJ))+"</td>";
									}								
									tbodyHtml += "<td title='"+StringUtil.escapeStr(row.KCDD)+"'>"+StringUtil.escapeStr(row.KCDD)+"</td>";
									tbodyHtml += "<td title='"+StringUtil.escapeStr(row.CKCMC)+"'><a href='javascript:void(0);' bizId=\""+row.PXJHID+"\">"+StringUtil.escapeStr(row.CKCMC)+"</a></td>";
									tbodyHtml += "<td align='left' title='"+StringUtil.escapeStr(row.PXXS)+"'>"+StringUtil.escapeStr(row.PXXS)+"</td>";
									tbodyHtml += "</tr>";
								});
								this_.getDataContainer().html(tbodyHtml);
								this_.setNumber(data.rows.length == 0 ?'':data.rows.length);
								$("a", this_.getDataContainer()).on("click", function(e){
									this_.view(e);
								});
							}else{
								training = [];
								this_.setNumber('');
								var tbodyHtml = "<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>";
								this_.getDataContainer().html(tbodyHtml);
							}
							if(!trainingLoaded){
								trainingLoaded = true;
								//弹出强制提示
								initPopMsg();
							}
					    }
					}); 
				},
				view: function(e){
					var this_ = this;
					setTimeout(function(){this_.load()},5000);
					window.open(panelData[0].singleUrl + $(e.target).attr("bizId"));
				}
			});
			panelData[0].panel = panel;
		}
		currentPanel = panelData[0].panel;
	}else{
		training = [];
		trainingLoaded = true;
		if(panelData[0].panel){
			panelData[0].panel.destory(true);
		}
	}
	//MCC135工单预警（完工未关闭）
	if($.inArray(panelData[2].id, delPanel) == -1 && checkBtnPermissions(panelData[2].permissioncode)){ 
		if(!panelData[2].panel){
			var panel = new Panel({
				id: panelData[2].id,
				title: panelData[2].title,
				beforePanel: currentPanel,
				moreUrl: panelData[2].moreUrl,
				tableHead: "<tr><th class='colwidth-13'><div class='font-size-12 line-height-18'>任务单号</div><div class='font-size-9 line-height-18'>Task No</div></th><th class='colwidth-10'><div class='font-size-12 line-height-18'>任务类型</div><div class='font-size-9 line-height-18'>Task Type</div></th><th class='colwidth-15'><div class='font-size-12 line-height-18'>任务信息</div><div class='font-size-9 line-height-18'>Task Information</div></th><th class='colwidth-12'><div class='font-size-12 line-height-18'>飞机注册号</div><div class='font-size-9 line-height-18'>A/C REG</div></th><th class='colwidth-7'><div class='font-size-12 line-height-18'>部件号</div><div class='font-size-9 line-height-18'>P/N</div></th><th class='colwidth-7'><div class='font-size-12 line-height-18'>序列号</div><div class='font-size-9 line-height-18'>S/N</div></th></tr>",
				removeCallback: function(flag){
					panelData[2].panel = null;
					if(flag !== true){
						delPanel.push(panelData[2].id);
						//往后台存入删除的面板
						removePanel(panelData[2].id);
					}
				},
				load: function(){
					var this_ = this;
					//this.getDataContainer().empty();
					AjaxUtil.ajax({
						url:basePath+"/sys/custom/block/block7",
						type: "get",
						cache:false,
						dataType:"json",
						success:function(data){
							if(data && data.rows){
								var tbodyHtml = "";
								if(data.rows.length > 0){
									$.each(data.rows, function(index, row){
										tbodyHtml += "<tr>";
										
//										var gddlxForOrder = formatUndefine(row.GDLX)=='1'?'4':row.GDLX;
										
										tbodyHtml += "<td align='center' title='"+row.GDBH+"'><a href='javascript:void(0);' bizId=\""+row.ID+"\" >"+row.GDBH+"</a></td>";
									    tbodyHtml += "<td title='"+DicAndEnumUtil.getEnumName('workorderTypeEnum',row.GDLX)+"'>"+DicAndEnumUtil.getEnumName('workorderTypeEnum',row.GDLX)+"</td>";  
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.GDBT)+"'>"+StringUtil.escapeStr(row.GDBT)+"</td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.FJZCH)+"'>"+StringUtil.escapeStr(row.FJZCH)+"</td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.BJH)+"'>"+StringUtil.escapeStr(row.BJH)+"</td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.XLH)+"'>"+StringUtil.escapeStr(row.XLH)+"</td>";
										tbodyHtml += "</tr>";
									})
								}else{
									tbodyHtml = "<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>"
								}
								this_.getDataContainer().html(tbodyHtml);
								this_.setNumber(data.rows.length == 0 ?'':data.rows.length);
								
								$("a", this_.getDataContainer()).on("click", this_.view);
							}else{
								this_.setNumber('');
								var tbodyHtml = "<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>";
								this_.getDataContainer().html(tbodyHtml);
							}
					    }
					}); 
				},
				view: function(e){
					window.open(panelData[2].singleUrl + $(e.target).attr("bizId"));
				}
			});
			panelData[2].panel = panel;
		}
		currentPanel = panelData[2].panel;
	}else{
		if(panelData[2].panel){
			panelData[2].panel.destory(true);
		}
	}
	
	//MCC145工单预警（完工未关闭）
	if($.inArray(panelData[3].id, delPanel) == -1 && checkBtnPermissions(panelData[3].permissioncode)){ 
		if(!panelData[3].panel){
			var panel = new Panel({
				id: panelData[3].id,
				title: panelData[3].title,
				beforePanel: currentPanel,
				moreUrl: panelData[3].moreUrl,
				tableHead: "<tr><th class='colwidth-12'><div class='font-size-12 line-height-18'>任务单号</div><div class='font-size-9 line-height-18'>Task No</div></th><th class='colwidth-10'><div class='font-size-12 line-height-18'>任务类型</div><div class='font-size-9 line-height-18'>Task Type</div></th><th class='colwidth-15'><div class='font-size-12 line-height-18'>任务信息</div><div class='font-size-9 line-height-18'>Task Information</div></th><th class='colwidth-12'><div class='font-size-12 line-height-18'>飞机注册号</div><div class='font-size-9 line-height-18'>A/C REG</div></th><th class='colwidth-7'><div class='font-size-12 line-height-18'>部件号</div><div class='font-size-9 line-height-18'>P/N</div></th><th class='colwidth-7'><div class='font-size-12 line-height-18'>序列号</div><div class='font-size-9 line-height-18'>S/N</div></th></tr>",
				removeCallback: function(flag){
					panelData[3].panel = null;
					if(flag !== true){
						delPanel.push(panelData[3].id);
						//往后台存入删除的面板
						removePanel(panelData[3].id);
					}
				},
				load: function(){
					var this_ = this;
					//this.getDataContainer().empty();
					AjaxUtil.ajax({
						url:basePath+"/sys/custom/block/block8",
						type: "get",
						cache:false,
						dataType:"json",
						success:function(data){
							if(data && data.rows){
								var tbodyHtml = "";
								if(data.rows.length > 0){
									$.each(data.rows, function(index, row){
										tbodyHtml += "<tr>";
										
//										var gddlxForOrder = formatUndefine(row.GDLX)=='1'?'4':row.GDLX;
										
										tbodyHtml += "<td align='center' title='"+row.GDBH+"'><a href='javascript:void(0);' bizId=\""+row.ID+"\" >"+row.GDBH+"</a></td>";
									    tbodyHtml += "<td title='"+DicAndEnumUtil.getEnumName('workorderTypeEnum',row.GDLX)+"'>"+DicAndEnumUtil.getEnumName('workorderTypeEnum',row.GDLX)+"</td>";  
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.GDBT)+"'>"+StringUtil.escapeStr(row.GDBT)+"</td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.FJZCH)+"'>"+StringUtil.escapeStr(row.FJZCH)+"</td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.BJH)+"'>"+StringUtil.escapeStr(row.BJH)+"</td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.XLH)+"'>"+StringUtil.escapeStr(row.XLH)+"</td>";
										tbodyHtml += "</tr>";
									})
								}else{
									tbodyHtml = "<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>"
								}
								this_.getDataContainer().html(tbodyHtml);
								this_.setNumber(data.rows.length == 0 ?'':data.rows.length);
								
								$("a", this_.getDataContainer()).on("click", this_.view);
							}else{
								this_.setNumber('');
								var tbodyHtml = "<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>";
								this_.getDataContainer().html(tbodyHtml);
							}
					    }
					}); 
				},
				view: function(e){
					window.open(panelData[3].singleUrl + $(e.target).attr("bizId"));
				}
			});
			panelData[3].panel = panel;
		}
		currentPanel = panelData[3].panel;
	}else{
		if(panelData[3].panel){
			panelData[3].panel.destory(true);
		}
	}
	
	//我的提订单（未完成）
	if($.inArray(panelData[1].id, delPanel) == -1 && checkBtnPermissions(panelData[1].permissioncode)){ 
		if(!panelData[1].panel){
			var panel = new Panel({
				id: panelData[1].id,
				title: panelData[1].title,
				beforePanel: currentPanel,
				moreUrl: panelData[1].moreUrl,
				tableHead: "<tr><th class='colwidth-10'><div class='font-size-12 line-height-18'>提订单号</div><div class='font-size-9 line-height-18'>Order No.</div></th><th class='colwidth-15'><div class='font-size-12 line-height-18'>提订名称</div><div class='font-size-9 line-height-18'>Order Name</div></th><th class='colwidth-7'><div class='font-size-12 line-height-18'>进度</div><div class='font-size-9 line-height-18'>Progress</div></th><th class='colwidth-15'><div class='font-size-12 line-height-18'>件号</div><div class='font-size-9 line-height-18'>P/N</div></th><th class='colwidth-20'><div class='font-size-12 line-height-18'>航材名称</div><div class='font-size-9 line-height-18'>Material Name</div></th><th class='colwidth-7'><div class='font-size-12 line-height-18'>提定数量</div><div class='font-size-9 line-height-18'>QTY</div></th><th class='colwidth-7'><div class='font-size-12 line-height-18'>审核数量</div><div class='font-size-9 line-height-18'>QTY</div></th></tr>",
				removeCallback: function(flag){
					panelData[1].panel = null;
					if(flag !== true){
						delPanel.push(panelData[1].id);
						//往后台存入删除的面板
						removePanel(panelData[1].id);
					}
				},
				load: function(){
					var this_ = this;
					//this.getDataContainer().empty();
					AjaxUtil.ajax({
						url:basePath+"/sys/custom/block/block6",
						type: "get",
						cache:false,
						dataType:"json",
						success:function(data){
							if(data && data.rows){
								var tbodyHtml = "";
								if(data.rows.length > 0){
									$.each(data.rows, function(index, row){
										tbodyHtml += "<tr>";
										tbodyHtml += "<td align='center' title='"+row.SQDH+"'><a href='javascript:void(0);' bizId=\""+row.MAINID+"\" >"+row.SQDH+"</a></td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.TDMC)+"'>"+StringUtil.escapeStr(row.TDMC)+"</td>";
										tbodyHtml += "<td title='"+row.JDZT+"'>"+row.JDZT+"</td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.BJH)+"'>"+StringUtil.escapeStr(row.BJH)+"</td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.YWMS)+" "+StringUtil.escapeStr(row.ZWMS)+"'>"+StringUtil.escapeStr(row.YWMS)+" "+StringUtil.escapeStr(row.ZWMS)+"</td>";
										tbodyHtml += "<td align='right' title='"+row.SQSL+"'>"+row.SQSL+"</td>";
										tbodyHtml += "<td align='right' title='"+formatUndefine(row.SL)+"'>"+formatUndefine(row.SL)+"</td>";
										tbodyHtml += "</tr>";
									})
								}else{
									tbodyHtml = "<tr><td class='text-center' colspan=\"7\">暂无数据 No data.</td></tr>"
								}
								this_.getDataContainer().html(tbodyHtml);
								this_.setNumber(data.rows.length == 0 ?'':data.rows.length);
								$("a", this_.getDataContainer()).on("click", this_.view);
							}else{
								this_.setNumber('');
								var tbodyHtml = "<tr><td class='text-center' colspan=\"7\">暂无数据 No data.</td></tr>";
								this_.getDataContainer().html(tbodyHtml);
							}
					    }
					}); 
				},
				view: function(e){
					window.open(panelData[1].singleUrl + $(e.target).attr("bizId"));
				}
			});
			panelData[1].panel = panel;
		}
		currentPanel = panelData[1].panel;
	}else{
		if(panelData[1].panel){
			panelData[1].panel.destory(true);
		}
	}	
	
	//传阅
	if($.inArray(panelData[5].id, delPanel) == -1 && checkBtnPermissions(panelData[5].permissioncode)){ 
		if(!panelData[5].panel){
			var panel = new Panel({
				id: panelData[5].id,
				title: panelData[5].title,
				beforePanel: currentPanel,
				moreUrl: panelData[5].moreUrl,
				tableHead: "<tr><th class='colwidth-8'><div class='font-size-12 line-height-18'>类型</div><div class='font-size-9 line-height-18'>Type</div></th>" +
						"<th class='colwidth-12'><div class='font-size-12 line-height-18'>编号</div><div class='font-size-9 line-height-18'>No.</div></th>" +
						"<th class='colwidth-13'><div class='font-size-12 line-height-18'>机型</div><div class='font-size-9 line-height-18'>A/C Type</div></th>" +
						"<th class='colwidth-15'><div class='font-size-12 line-height-18'>主题</div><div class='font-size-9 line-height-18'>Subject</div></th>" +
						"<th class='colwidth-12'><div class='font-size-12 line-height-18'>传阅期限</div><div class='font-size-9 line-height-18'>Bulletin Limit</div></th>" ,
				removeCallback: function(flag){
					panelData[5].panel = null;
					if(flag !== true){
						delPanel.push(panelData[5].id);
						//往后台存入删除的面板
						removePanel(panelData[5].id);
					}
				},
				load: function(){
					var this_ = this;
					AjaxUtil.ajax({
						url:basePath+"/sys/custom/block/block10",
						type: "get",
						cache:false,
						dataType:"json",
						success:function(data){
							if(data && data.rows){
								var tbodyHtml = "";
								if(data.rows.length > 0){
									$.each(data.rows, function(index, row){
										tbodyHtml += "<tr>";
										tbodyHtml += "<td title='"+DicAndEnumUtil.getEnumName("projectBusinessEnum",StringUtil.escapeStr(row.YWLX))+"'>"+DicAndEnumUtil.getEnumName("projectBusinessEnum",StringUtil.escapeStr(row.YWLX))+"</td>";
										tbodyHtml += "<td title='"+row.JSTGH+" "+(row.BB==null?"":"R"+StringUtil.escapeStr(row.BB))+"'><a href='javascript:void(0);'  ywlx=\""+row.YWLX+"\" zt=\""+row.ZT+"\"   id=\""+row.ID+"\" >"+row.JSTGH+" "+(row.BB==null?"":"R"+StringUtil.escapeStr(row.BB))+"</a></td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.JX)+"'>"+StringUtil.escapeStr(row.JX)+"</td>";
									    tbodyHtml += "<td title='"+StringUtil.escapeStr(row.ZHUT)+"'>"+StringUtil.escapeStr(row.ZHUT)+"</td>";  
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.TGQX == null ? "": row.TGQX.substring(0, 10)) +"'>"+StringUtil.escapeStr(row.TGQX == null ? "": row.TGQX.substring(0, 10)) +"</td>";
										tbodyHtml += "</tr>";
									})
								}else{
									tbodyHtml = "<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>"
								}
								this_.getDataContainer().html(tbodyHtml);
								this_.setNumber(data.rows.length == 0 ?'':data.rows.length);
								
								$("a", this_.getDataContainer()).on("click", this_.view);
							}else{
								this_.setNumber('');
								var tbodyHtml = "<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>";
								this_.getDataContainer().html(tbodyHtml);
							}
					    }
					}); 
				},
				view: function(e){
					var ywlx = $(e.target).attr("ywlx");
					if(ywlx =='1'){
						window.open(panelData[5].singleUrl[0] + $(e.target).attr("id")+ "&zt=" + $(e.target).attr("zt"));
					}
					if(ywlx =='6'){
						window.open(panelData[5].singleUrl[1] + $(e.target).attr("id"));
					}
					
				}
			});
			panelData[5].panel = panel;
		}
		currentPanel = panelData[5].panel;
	}else{
		if(panelData[5].panel){
			panelData[5].panel.destory(true);
		}
	}
		
}

function removePanel(panelId){
	//往后台存入删除的面板;
	AjaxUtil.ajax({
		url:basePath+"/sys/custom/block/add",
		type: "post",
		dataType:"json",
		data:{panelId: panelId},
		success:function(data){
	    }
	}); 
}
/**
 * 保存客制化Bolock
 * @returns
 */
function saveCustom(){
	var this_ = this;
	$.each($("input:checkbox", $("#customDiv")), function(){
		var $this = $(this);
		if($this.is(":checked")){
			var _index = $.inArray($this.attr("panelid"), delPanel);
			if(_index >= 0){
				delPanel.splice(_index, 1);
			}
		}else{
			var _index = $.inArray($this.attr("panelid"), delPanel);
			if(_index == -1){
				delPanel.push($this.attr("panelid"));
			}
		}
		
	});
	//往后台存入删除的面板;
	AjaxUtil.ajax({
		url:basePath+"/sys/custom/block/adds",
		type: "post",
		dataType:"json",
		data:{panelIds: delPanel},
		success:function(data){
	    }
	}); 
	showPanel();
}

/**
 * 添加快捷功能，打开modal
 * @returns
 */
function addShortcut(){
	$("#shortcutModal").modal("show");
}


function removeShortcut(id, type, e){
	$(e.target).parent().parent().remove();
	jcarousel.jcarousel('reload');
	AjaxUtil.ajax({
		url:basePath+"/sys/custom/shortcut/remove",
		type: "post",
		dataType:"json",
		data:{id:id,type:type},
		success:function(data){
	    }
	}); 
}

/**
 * 初始化消息显示
 * @returns
 */
function initMessage(){
	
	//轮播
	var html = "";
	$.each(message, function(index, row){
		html += "<li>";
		html += "<a  onclick=\"viewMessage('"+index+"');\" href='javascript:void(0);' title='"+StringUtil.title(row.bt,30)+"'>"+StringUtil.subString(row.bt,30)+"</a>"+"<span>["+formatDate(row.fbsj)+"]</span>";
		html += "</li>";
	});
	$("#messageUl").html(html);
	
	$("#messageDiv").BreakingNews({
		background		:"#FFF",
		title			:"<i id=\"messageBth\" title='通知公告 Notice' class=\"icon-volume-up font-size-24\" style=\"padding:5px;height:40px;border-radius:5px; display: block; line-height: 32px;\"></i>",
		titlecolor		:"#FFF",
		titlebgcolor	:"#428bca",
		linkcolor		:"#333",
		linkhovercolor		:"#428bca",
		fonttextsize		:16,
		isbold			:false,
		border			:"solid 0px #099",
		width			:"100%",
		timer			:3000,
		autoplay		:true,
		effect			:"slide",
	});
    
	var title =  "<div>通知公告"  + "<span class=\"badge margin-left-10\">"+message.length+"</span>"+
//	"<em onclick='moreMessage();' class='pull-right icon-external-link cursor-pointer'/>" +
	"</div>" ;
	$('#messageBth').webuiPopover({
		placement:'auto',
		container: document.body,
		style:'',
		padding: false,
		title : title,// 设置 弹出框 的标题
		trigger: 'click',
		type:'html',
		width:500,
		height:'auto',
		backdrop:false,
		content : generateMessageHtml()// 这里可以直接写字符串，也可以 是一个函数，该函数返回一个字符串；
	});
}

function moreMessage(){
	//TODO 设置通知公告 更多 链接
	window.open(basePath+"/");
}

function generateMessageHtml(){
	var html = "<ul class='bd list-group margin-bottom-0'>";
	$.each(message, function(index, row){
		html += "<li class='list-group-item'>";
		if(row.jjd == 9){
			html += "<i class='icon-volume-up margin-right-5' style='color:red;'></i>";
		}else{
			html += "<i class='icon-volume-up margin-right-10'></i>";
		}
		html += "<span class='margin-right-10'><a onclick=\"viewMessage('"+index+"');\" href='javascript:void(0);' title='"+StringUtil.escapeStr(StringUtil.title(row.bt,30))+"'>"+StringUtil.escapeStr(StringUtil.subString(row.bt,30))+"</a>["+formatDate(row.fbsj)+"]</span>";
		html += "</li>";
	});
	html += "</ul>"
	return html;
}

/**
 * 查看新闻
 * @returns
 */
function viewMessage(index){
	var _data = message[index];
	$("#MESSAGE_BT").val(_data.bt);
	$("#MESSAGE_FBSJ").val(formatUndefine(_data.fbsj).substr(0,10));
	
	var jjd = "普通";
	if(_data.jjd == 9){
		jjd = "紧急";
	}
	$("#MESSAGE_JJD").val(jjd);
	$("#MESSAGE_YXQ_KS").val(formatUndefine(_data.yxqKs).substr(0,10));
	$("#MESSAGE_YXQ_JS").val(formatUndefine(_data.yxqJs).substr(0,10));
	$("#MESSAGE_NR").val(_data.nr);
	$("#messageModal").modal("show");
}


function formatDate(date, length){
	if(date){
		try{
			if(length){
				return date.substr(5,length)
			}else{
				return date.substr(5,5)
			}
		}catch(e){return "";}
	}
	return "";
}

function initFlowChart(){
	$('#flowChartBtn').webuiPopover({
		placement:'auto',
		title : "",// 设置 弹出框 的标题
		trigger: 'click',
		width:window.innerWidth/1.5,
		height:window.innerHeight/1.5,
//		content : "<div><img style='width:100%;height:100%' src='"+basePath+"/static/images/process_model/lct.png'/></div>"// 这里可以直接写字符串，也可以 是一个函数，该函数返回一个字符串；
		content:generateFlowChartHtml()
	});
}

function generateFlowChartHtml(){
	var html = '<div id="carousel-example-generic" style="width:100%;height:100%" class="carousel slide" data-ride="carousel">';
	html += '<ol class="carousel-indicators">';
	html += '<li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>';
	html += '<li data-target="#carousel-example-generic" data-slide-to="1"></li>';
	html += '<li data-target="#carousel-example-generic" data-slide-to="2"></li>';
	html += '</ol>';

	html += '<div class="carousel-inner" role="listbox">';
	html += '<div class="item active">';
	html += '<img onload="getCoords1()" id="flowChartImg1" style="width:1221px;margin: 0px auto;" src="'+basePath+'/static/images/process_model/lct.png" alt="..." usemap="#Map" border="0">';
	html += '<map name="Map" id="Map">';
	html += '<area shape="rect" coords="16,65,107,101" href="'+basePath+'/project/technicalfile/mainupload" />';
	html += '<area shape="rect" coords="134,67,223,100" href="'+basePath+'/project/technicalfile/mainassess" />';
	html += '<area shape="rect" coords="249,68,339,100" href="'+basePath+'/project/technicalfile/mainaudit" />';
	html += '<area shape="rect" coords="363,68,453,99" href="'+basePath+'/project/technicalfile/mainapproval" />';
	html += '<area shape="rect" coords="16,147,106,182" href="'+basePath+'/project/annunciate/main" />';
	html += '<area shape="rect" coords="133,147,225,181" href="'+basePath+'/project/instruction/main" />';
	html += '<area shape="rect" coords="248,147,338,181" href="'+basePath+'/project/revisionNoticeBook/main" />';
	html += '<area shape="rect" coords="362,148,451,181" href="'+basePath+'/project/engineering/main" />';
	html += '<area shape="rect" coords="478,147,569,182" href="'+basePath+'/project/workorder/main" />';
	html += '<area shape="rect" coords="247,216,339,251" href="'+basePath+'/project/maintenance/main" />';
	html += '<area shape="rect" coords="362,215,453,251" href="'+basePath+'/project/workorder/main" />';
	html += '<area shape="rect" coords="247,285,338,319" href="'+basePath+'/project/fixedcheckitem/main" />';
	html += '<area shape="rect" coords="478,293,568,328" href="'+basePath+'/productionplan/scheduledcheckitem/main" />';
	html += '<area shape="rect" coords="248,349,339,385" href="'+basePath+'/project/maintenance/main" />';
	html += '<area shape="rect" coords="248,417,338,452" href="'+basePath+'/project/confirmationofrevision/main" />';
	html += '<area shape="rect" coords="704,66,795,101" href="'+basePath+'/project/confirmationofrevision/main"/>';
	html += '<area shape="rect" coords="704,135,794,170" href="'+basePath+'/productionplan/loadingList/main" />';
	html += '<area shape="rect" coords="703,199,795,235" href="'+basePath+'/productionplan/loadingList/main" />';
	html += '<area shape="rect" coords="703,267,795,303" href="'+basePath+'/productionplan/scheduledcheckitem/main" />';
	html += '<area shape="rect" coords="703,334,795,370" href="'+basePath+'/project/workorder/main" />';
	html += '<area shape="rect" coords="703,399,795,435" href="'+basePath+'/productionplan/plantask/planpanel" />';
	html += '<area shape="rect" coords="995,66,1085,101" href="'+basePath+'/aerialmaterial/reserve/manage" />';
	html += '<area shape="rect" coords="994,134,1086,170" href="'+basePath+'/aerialmaterial/reserve/approve" />';
	html += '<area shape="rect" coords="994,199,1087,235" href="'+basePath+'/aerialmaterial/contract/main" />';
	html += '<area shape="rect" coords="994,267,1087,303" href="" />';
	html += '<area shape="rect" coords="994,334,1086,370" href="'+basePath+'/aerialmaterial/contract/main" />';
	html += '<area shape="rect" coords="994,399,1086,436" href="'+basePath+'/aerialmaterial/qualityCheck/main" />';
	html += '<area shape="rect" coords="994,457,1086,492" href="'+basePath+'/aerialmaterial/instock/main" />';
	html += '<area shape="rect" coords="1115,134,1206,170" href="'+basePath+'/aerialmaterial/enquiry/main" />';
	html += '</map>';
	html += '</div>';
	html += '</div>';

	return html;
}
function getCoords1(x1,y1,x2,y2){
	var orgWidth = 1221;
	var orgHeight = 496;
	var width= $("#flowChartImg1").width();
	var height = $("#flowChartImg1").height();
	var xratio = width/orgWidth;
	var yratio = height/orgHeight;
	$("#Map area").each(function(index, row){
		var coords = $(this).attr("coords");
		var coordsArray = coords.split(",");
		$(this).attr("coords", xratio*coordsArray[0] +","+ yratio*coordsArray[1] +","+ xratio*coordsArray[2] +","+ yratio*coordsArray[3]) ;
	})
	
}

/**
 * 初始化航材留言
 * 
 * @returns
 */
function initMsg(){
	$("#msgCount").text(msgData.length);
	var title =  "<div>留言"  + "<span class=\"badge margin-left-10\">"+msgData.length+"</span>"+
	"<em onclick='refreshMsg();' class='pull-right icon-refresh cursor-pointer'/></div>" ;
	$('#msgBtn').webuiPopover('destroy').webuiPopover({
		placement:'auto',
		container: document.body,
		style:'',
		padding: false,
		title : title,// 设置 弹出框 的标题
		trigger: 'click',
		type:'html',
		width:500,
		height:'auto',
		backdrop:false,
		content : generateMsgHtml()// 这里可以直接写字符串，也可以 是一个函数，该函数返回一个字符串；
	});
}

function refreshMsg(){
	AjaxUtil.ajax({
		url:basePath+"/sys/custom/msg/list",
		type: "get",
		dataType:"json",
		success:function(data){
			if(data && data.msg){
				msgData = data.msg;
			}else{
				msgData = [];
			}
			initMsg();
			$('#msgBtn').webuiPopover("show");
		}
	});
}

function generateMsgHtml(){
	var html = "<ul class='bd list-group margin-bottom-0'>";
	$.each(msgData, function(index, row){
		html += "<li class='list-group-item'>";
		html += "<span class='margin-right-10'>"+StringUtil.escapeStr(row.LYU)+"</span>";
		html += "<span class='margin-right-10'>"+StringUtil.escapeStr(row.LYR)+"</span>";
		html += "<span class='margin-right-10'>"+formatDate(row.LYSJ,11)+"</span>";
		html += "<span class='margin-right-10'>"+formatUndefine(row.LXM)+"</span>";
		if(row.LX == 1 || row.LX == 2){
			html += "<span class='margin-right-10'><a href='javascript:void(0);' onclick=\"viewMsg('"+row.LX+"','"+row.DJID+"');\" >"+formatUndefine(row.SQDH)+"</a></span>";
		}else{
			html += "<span class='margin-right-10'><a href='javascript:void(0);' onclick=\"viewMsg('"+row.LX+"','"+row.DJID+"');\" >"+formatUndefine(row.HTH)+"</a></span>";
		}
		html += "<span title='"+StringUtil.escapeStr(StringUtil.title(row.TDMC,15))+"' class='margin-right-10'>"+StringUtil.escapeStr(StringUtil.subString(row.TDMC,15))+"</span>";
		html += "<span class='margin-right-10'><br/></span>";
		html += "<span title='"+StringUtil.escapeStr(StringUtil.title(row.LYNR,35))+"' class='margin-right-10'>"+StringUtil.escapeStr(StringUtil.subString(row.LYNR,35))+"</span>";
		html += "</li>";
	});
	html += "</ul>"
	return html;
}
/**
 * 查看留言
 * @param lx 类型
 * @param djid 单据ID
 * @returns
 */
function viewMsg(lx, djid){
	setTimeout(refreshMsg, 5000);
	if(lx == "1"){
		//提订单
		window.open(basePath+"/aerialmaterial/reserve/view?id="+djid);
	}else if(lx == "2"){
		//送修单
		window.open(basePath+"/aerialmaterial/repair/view?id="+djid);
	}else{//合同（采购、送修合同）
		window.open(basePath+"/aerialmaterial/contract/view?id="+djid);
	}
}

//加载菜单树
function loadMenutree(){
	var data = [];
	if(userMenuListJson){
		$.each(userMenuListJson, function(index, row){
			var menu_ = {};
			
			menu_.id = row.id;
			menu_.name = row.menuName +"/"+ row.menuFname;
			menu_.pId = row.parentId;
			if(row.menuType == 1){
				menu_.nocheck = true;
			}else if(row.menuType == 2 && exsistsShortcut(row.id)){
				menu_.checked = true;
			}
			menu_.zwms = row.menuName;
			menu_.ywms = row.menuFname;
			menu_.path = row.path;
			var children = [];
			if(row.children){
				$.each(row.children, function(index1, row1){
					var cnode = {};
					cnode.id = row1.id;
					cnode.name = row1.menuName +"/"+ row1.menuFname;
					cnode.pId = row1.parentId;
					if(row1.menuType == 1){
						cnode.nocheck = true;
						if(row1.children){
							var children1 = [];
							$.each(row1.children, function(index2, row2){
								var cnode1 = {};
								cnode1.id = row2.id;
								cnode1.name = row2.menuName +"/"+ row2.menuFname;
								cnode1.pId = row2.parentId;
								if(row2.menuType == 1){
									cnode1.nocheck = true;
								}else if(row2.menuType == 2 && exsistsShortcut(row2.id)){
									cnode1.checked = true;
								}
								cnode1.zwms = row2.menuName;
								cnode1.ywms = row2.menuFname;
								cnode1.path = row2.path;
								cnode1.open = true;
								children1.push(cnode1);
							})
							cnode.children = children1;
							cnode.open = true;
						}
						
					}else if(row1.menuType == 2 && exsistsShortcut(row1.id)){
						cnode.checked = true;
					}
					cnode.zwms = row1.menuName;
					cnode.ywms = row1.menuFname;
					cnode.path = row1.path;
					cnode.open = true;
					children.push(cnode);
				})
			}
			menu_.children = children;
			menu_.open = true;
			data.push(menu_);
		});
	}
	var setting = {
		view: {showIcon: false},
		callback:{onClick: function(){}},
		check: {enable: true, autoCheckTrigger: true},
		data: {
			simpleData: {enable: true}
		}
	};
	treeObj = $.fn.zTree.init($("#menuTree"), setting, data);
}

/**
 * 保存快捷菜单
 * @returns
 */
function saveShortcut(){
	var nodes = treeObj.getCheckedNodes(true);
	
	var cdids = [];
	
	var shortHtml = "";
	$.each(nodes, function(index, row){
		shortHtml += "<li>";
		shortHtml += "<div style='position: relative;'>";
		shortHtml += "<a href=\""+(basePath+"/"+row.path)+"\" class=\"btn btn-primary padding-top-1 padding-bottom-1\">";
		shortHtml += "<div class=\"font-size-12\">"+row.zwms+"</div>";
		shortHtml += "<div class=\"font-size-9\">"+row.ywms+"</div>";
		shortHtml += "</a>";
//		shortHtml += "<i class='icon-remove-circle close_x' onclick='removeShortcut(\""+row.id+"\", 2, event);'></i>"
		shortHtml += "</div>";
		shortHtml += "</li>";
		cdids.push(row.id);
	})
	jcarousel.find("ul").html(shortHtml);
    jcarousel.jcarousel('reload');
	//页面上增加
//	var zwms = $("#zwms").val();
//	var ywms = $("#ywms").val();
//	var cdid = $("#cdid").val();
//	var $selected = $("#cdid").find(":selected");
//	var path = $selected.attr("menuUrl");
//	$("#cdid").trigger("change");
	
	//保存
	AjaxUtil.ajax({
		url:basePath+"/sys/custom/shortcut/adds",
		type: "post",
		dataType:"json",
		data:{cdids: cdids},
		success:function(data){
		}
	});
}

/**
 * 弹出提示
 * @returns
 */
function initPopMsg(){
	if(!(messageLoaded && (checkBtnPermissions(panelData[4].permissioncode)?trainingLoaded:true))){
		return ;
	}
	
	var messageHtml = "";
	$.each(message, function(index, row){
		if(row.jjd == 9){
			messageHtml += "<li class='list-group-item'>";
			messageHtml += "<a onclick=\"viewMessage('"+index+"');\"  href='javascript:void(0);' title='"+StringUtil.title(row.bt, 30)+"'>"+StringUtil.subString(row.bt, 30)+"</a>["+formatDate(row.fbsj)+"]";
			messageHtml += "</li>";
		}
	}) 
	if(messageHtml == ""){
		$("#popMessage").hide();
	}
	
	var popTrainHtml = "";
	$.each(training, function(index, row){
		if(row.IS_TX == 1){
			if(row.TQQDW == "11"){//日
				if(row.SY <= row.TQQ){
					popTrainHtml += generateTrainingLIHtml(row);
				}
			}else if(row.TQQDW == "12"){//月
				if(row.SY/30 < row.TQQ){
					popTrainHtml += generateTrainingLIHtml(row);
				}
			}else if(row.TQQDW == "13"){//年
				if(row.SY/365 < row.TQQ){
					popTrainHtml += generateTrainingLIHtml(row);
				}
			}
		}
	});
	if(popTrainHtml == ""){
		$("#popTrain").hide();
	}
	
	if(messageHtml == "" && popTrainHtml == ""){
		return;
	}
	
	$("#popMessageUl").html(messageHtml);
	$("#popTrainTbody").html(popTrainHtml);
	$("#popModal").modal("show");
}

/**
 * 生成培训计划LI标签HTML
 */
function generateTrainingLIHtml(row){
	var pxxs = ["","课堂","自学","OJT"];
	var tbodyHtml = "<tr>";
	if(row.jszt == 0){
		tbodyHtml += "<em class='icon-'/>";
	}
	tbodyHtml += "<td align='center'><a href='javascript:void(0);' onclick=\"viewTraining('"+row.MAINID+"', this);\" >"+row.KCSJ+"</a></td>";
	tbodyHtml += "<td title='"+StringUtil.escapeStr(row.PXDD)+"'>"+StringUtil.escapeStr(row.PXDD)+"</td>";
	tbodyHtml += "<td title='"+StringUtil.escapeStr(row.KCMC)+"'>"+StringUtil.escapeStr(row.KCMC)+"</td>";
	tbodyHtml += "<td align='center'>"+pxxs[row.PXXS]+"</td>";
	tbodyHtml += "</tr>";
	return tbodyHtml;
}

function viewTraining(id, obj){
	try{
		setTimeout(function(){panelData[4].panel.load()},3000);
	}catch(e){};
	$(obj).parent().parent().remove();
	window.open(panelData[4].singleUrl + id);
}

var timer = window.setInterval("refreshPage()",1000*60);//60秒钟刷新一次
function refreshPage(){
	refreshMessage();//刷新通知公告和留言
	$("div[name='panel-div'] .icon-refresh").each(function(){
		$(this).click();
	});
}

function refreshMessage(){
	AjaxUtil.ajax({
		url:basePath+"/sys/custom/message/list",
		type: "get",
		dataType:"json",
		success:function(data){
			if(data && data.message){
				message = data.message;
			}else{
				message = [];
			}
			initMessage();//刷新通知公告
			if(data && data.msg){
				msgData = data.msg;
			}else{
				msgData = [];
			}
			initMsg();//刷新留言
		}
	});
}
