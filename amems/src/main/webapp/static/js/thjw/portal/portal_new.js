//获取页面的路径
var ctxUrl=$("#ctxInput").val();

/*定义面板*/
var  PanelBorad=function(options){
	var     this_= this ;
	    this_.id = "" ;
	    this_.title = "" ;
	    this_.icon = "icon-hand-right" ;
	    this_.buttons = ['refresh', 'more', 'close'] ;
	    this_.renderTo = "portal" ;
	    this_.beforePanel = null ;
	    this_.bodyHeight = "250px" ;
	    this_.moreUrl = "";
	    this_.removeCallback = null;
	    this_.panelClass = "panel-primary"
	    this_.badgeNum = null ;
	    this_.divClass = null;
	    this_.refreshTime = null;
	    this_.load = null;
	    this_.currentTime=null;
	 var configs = $.extend( this_, options );
	 
	 //外层div
	 
	 var $div = $("<div name='panel-div' class='"+configs.panelDivClass+"'></div>");
	 
	 //设置panel的样式
	 
	 var $divPanel = $("<div class='panel "+configs.panelClass+"' ></div>");
	 
	 //刷新时间
	 
	
		 
     //按钮组的显示
		 
	 var buttonsHtml="";
	 
	 //panel右边按钮组的设置
	 
	 for(var i=0;i<configs.buttons.length;i++){
			if(configs.buttons[i]=="refresh"){
				buttonsHtml += "<i title='刷新 Refresh' class='icon-refresh cursor-pointer margin-left-10'/>";
			}else if(configs.buttons[i]=="more"){
				buttonsHtml += "<i title='更多 More' class='icon-external-link cursor-pointer margin-left-10'/>";
			}else{
				buttonsHtml += "<i title='隐藏面板 Hide' class='icon-trash cursor-pointer margin-left-10'/>";
			}
		}
	 
	//panel-heading 设置 
	 
	 var $divHead = $("<div class='panel-heading'><h3 class=\"panel-title\">" +
				"<i class='"+configs.icon+" cursor-pointer margin-right-10'/>" +
				configs.title + "<span class=\"badge margin-left-10\">"+configs.badgeNum+"</span>"+
				"<div class='pull-right'>刷新时间：<span id='"+this_.id+"_time'></span>" +
				buttonsHtml+
				"</div>" +
		        "</h3></div>");
	 
	 //panel-body 设置 
	 
	 var $panelBody= $("<div class='panel-body padding-top-0 padding-left-0 padding-right-0 padding-bottom-0' style='height:"+configs.bodyHeight+";overflow:auto;'></div>");
	 var $BodyContent="";
	 
	 //show 方法
	 
	 function show(){
		 
		 //填充 divPanel
		 
		
		 $divPanel.append($divHead).append($panelBody);
		 
		 //填充 div
		 
		 $div.append($divPanel);
		 
		 if(configs.beforePanel){
			 
				configs.beforePanel.getDiv().after($div);
			 
			}else{
				$("#"+configs.renderTo).prepend($div);
			}
		 
		 //显示时间
		 this_.getCurentTime();
		 
		  
		  //删除小图标触发的事件
		 
		  $divHead.find(".icon-trash").on("click", this_.destory);
		  
		  //刷新小图标触发的事件
		  
		  $divHead.find(".icon-refresh").on("click", function(){
			  
			  
			  configs.load();
			  
			  //获取最新刷新时间
			  
			  this_.getCurentTime();
			  
			  //刷新后返回到滚动条的最上方
			  $panelBody.prop('scrollTop','0')
			  
			  
			});
		  
		  $divHead.find(".icon-external-link").on("click", function(){
				if(typeof configs.moreUrl=='string'){
					
					//更多按钮的处理方法
					
					window.open(configs.moreUrl);
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
		  
		  //加载数据
		  
		  configs.load();
	 }
	 
	 //删除panel的方法
	 
	 this_.destory=function(flag){
		 $div.remove();
		 this_.removeCallback(flag);
		 this_ = null;
	 }
	 
	 //执行show() 方法
	 
	 this_.show = function(){
			show();
	 };
	 
	 //获取div的方法
	 
	 this_.getDiv = function(){
			return $div;
	 }
	 
	 //开启遮罩层效果
	 
	 this_.blockUI = function (){
		 
		//开启刷新效果
		  Refresh.blockUI({target: $div, iconOnly: true,url:ctxUrl});
	 }
	 
	 //关闭遮罩层效果
	 
	 this_.unblockUI= function(){
		 
		//关闭刷新效果
		  Refresh.unblockUI($div);
		 
	 }
	 //获取而当前时间
	 this_.getCurentTime = function(){
		   
		    TimeUtil.getCurrentTime(function(time){
		     var timeStr=time.toString();
			 var now = new Date(Date.parse(timeStr.replace(/-/g,"/"))); 
			 //获取小时
			 var hours = now.getHours()<10?("0"+now.getHours()):now.getHours();
			 //获取分钟
			 
			 var minutes = now.getMinutes()<10?("0"+now.getMinutes()):now.getMinutes();
			 
			 //获取秒
			 
			 var seconds = now.getSeconds()<10?("0"+now.getSeconds()):now.getSeconds();
			 
			 clock= hours + ':' + minutes + ':' + seconds; 
			 //返回时间
			 $("#"+this_.id+"_time").text(clock);
		    
		 });
		
	 }
	 //返回主体部分
	 
	 
	 this_.getDataContainer = function(){
			return $panelBody;
		}
	 
	//设置badge数据
	 
	 this_.setNumber = function(num){
			$(".badge", $divHead).html(num);
		}
	 //执行show()方法
	 
	 show();
	 
	 //返回  this 对象
	 return this_;
}

var panelData = [
             	{id:"block5", permissioncode:"index:05", title:"我的培训通知", moreUrl:basePath+"/training/trainingnotice/manage", singleUrl: basePath+"/training/trainingnotice/find/"},
             	{id:"block6", permissioncode:"index:06", title:"我的提订单（未完成）", moreUrl:basePath+"/aerialmaterial/reserve/manage", singleUrl: basePath+"/aerialmaterial/reserve/view?id="},
             	{id:"block7", permissioncode:"index:07", title:"MCC 135工单预警（完工未关闭）", moreUrl:basePath+"/produce/workorder/main", singleUrl: basePath+"/produce/workorder/woView?gdid="},
             	{id:"block8", permissioncode:"index:08", title:"MCC 145工单预警（完工未关闭）", moreUrl:basePath+"/produce/workorder145/main", singleUrl: basePath+"/produce/workorder145/woView?gdid="},
             	{id:"block9", permissioncode:"index:09", title:"待办事项", moreUrl:basePath+"/project2/todo/main", singleUrl: [basePath+"/project2/airworthiness/view?id=",basePath+"/project2/assessment/view?id="]},
             	{id:"block10", permissioncode:"index:10", title:"传阅", moreUrl:basePath+"/project2/bulletin/circulation/main", singleUrl: [basePath+"/project2/bulletin/view?id=",basePath+"/project2/eo/view?id="]},
             	//新增部分
             	{id:"block11", permissioncode:"index:11", title:"待办工作", moreUrl:basePath+"/project2/todo/main", singleUrl: [basePath+"/project2/airworthiness/view?id=",basePath+"/project2/assessment/view?id="]},
             	{id:"block12", permissioncode:"index:12", title:"飞机监控(135)", moreUrl:basePath+"/aircraftinfo/status/main", singleUrl: [basePath+"/produce/reservation/fault/monitor",basePath+"/produce/reservation/item/monitor",basePath+"/produce/reservation/defect/monitor",basePath+"produce/maintenanceplan/main",basePath+"/produce/maintenance/monitoring/main"]},
             	{id:"block13", permissioncode:"index:13", title:"飞机监控(145)", moreUrl:basePath+"/project2/bulletin/circulation/main", singleUrl: [basePath+"/produce/reservation/fault/monitor",basePath+"/produce/reservation/item/monitor",basePath+"/produce/reservation/defect/monitor",basePath+"/produce/maintenanceplan145/main"]},
             	/*{id:"block13", permissioncode:"index:13", title:"培训通知", moreUrl:basePath+"/project2/bulletin/circulation/main", singleUrl: [basePath+"/project2/bulletin/view?id=",basePath+"/project2/eo/view?id="]},
             	{id:"block14", permissioncode:"index:14", title:"传阅通知", moreUrl:basePath+"/project2/bulletin/circulation/main", singleUrl: [basePath+"/project2/bulletin/view?id=",basePath+"/project2/eo/view?id="]},*/
             	{id:"block14", permissioncode:"index:14", title:"个人执照/课程到期提醒", moreUrl:basePath+"/project2/bulletin/circulation/main", singleUrl: [basePath+"/project2/bulletin/view?id=",basePath+"/project2/eo/view?id="]},
             	{id:"block15", permissioncode:"index:15", title:"执照到期提醒", moreUrl:basePath+"/project2/bulletin/circulation/main", singleUrl: [basePath+"/project2/bulletin/view?id=",basePath+"/project2/eo/view?id="]},
             	{id:"block16", permissioncode:"index:16", title:"工具/设备校验提醒", moreUrl:basePath+"/quality/toolcheck/main", singleUrl: [basePath+"/quality/toolcheck/find/"]},
             	{id:"block17", permissioncode:"index:17", title:"人员培训提醒", moreUrl:basePath+"/project2/bulletin/circulation/main", singleUrl: [basePath+"/project2/bulletin/view?id=",basePath+"/project2/eo/view?id="]},
             	{id:"block18", permissioncode:"index:18", title:"质量审核通知", moreUrl:basePath+"/quality/auditnoticequery/main", singleUrl: [basePath+"/project2/bulletin/view?id=",basePath+"/project2/eo/view?id="]}
             	
             ]

//页面打卡时加载初始化事件

$(document).ready(function(){
	init();
});

//全局变量

var delPanel = [];//删除panel

var jcarousel = null;//快捷功能旋转木马

var shortcut = [];

var message = [];//消息

var msgData = [];//留言

var treeObj = null;//菜单树

var training =[];//培训

var trainingLoaded = false;//培训是否加载了
var messageLoaded = false;//消息是否加载了

//初始化事件


function init(){
	
var this_ = this;
	
	//左右切换
	
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

//存在的收藏信息

function exsistsShortcut(id){
	for(var i = 0; i< shortcut.length; i++){
		if(shortcut[i].cdid == id){
			return true;
		}
	}
	return false;
}

//展示Panel的方法

function showPanel(){
	var currentPanel = null;
	
//待办工作
	
if($.inArray(panelData[6].id, delPanel) == -1 && checkBtnPermissions(panelData[6].permissioncode)){ 
	if(!panelData[6].panel){
		var panel = new PanelBorad({
			id: panelData[6].id,
			title: panelData[6].title,
			beforePanel: currentPanel,
			moreUrl: panelData[6].moreUrl,
			buttons:['refresh', 'more'],
			refreshTime:"",
			panelDivClass:"col-lg-12 col-sm-12 col-xs-12 padding-leftright-3",
			tableHead: "",
			removeCallback: function(flag){
				panelData[6].panel = null;
				if(flag !== true){
					delPanel.push(panelData[6].id);
					//往后台存入删除的面板
					removePanel(panelData[6].id);
				}
			},
			load: function(){
				var this_ = this;
				var tbodyHtml = "<table class='table table-thin table-bordered table-striped table-hover table-set'><thead>";
				   tbodyHtml += "<tr><th class='colwidth-8'><div class='font-size-12 line-height-18'>工作类型</div><div class='font-size-9 line-height-18'>Type</div></th>"; 
				   tbodyHtml += "<th class='colwidth-35'><div class='font-size-12 line-height-18'>说明</div><div class='font-size-9 line-height-18'>Desc</div></th>";
				   tbodyHtml += "<th class='colwidth-8'><div class='font-size-12 line-height-18'>办理期限</div><div class='font-size-9 line-height-18'>Date</div></th>"; 
				   tbodyHtml += "<th class='colwidth-13'><div class='font-size-12 line-height-18'>处理</div><div class='font-size-9 line-height-18'>Handle</div></th></tr></thead>"; 
				   this_.blockUI();
					AjaxUtil.ajax({
					url:basePath+"/sys/custom/block/block11",
					type: "get",
					cache:false,
					dataType:"json",
					success:function(data){
						
						tbodyHtml += "<tbody>";
						if(data && data.rows){
							if(data.rows.length > 0){
								$.each(data.rows, function(index, row){
									tbodyHtml += "<tr>";
									var lx = DicAndEnumUtil.getEnumName('todoEnum',row.DBGZLX);
								    tbodyHtml += "<td title='"+lx+"'>"+lx+"</td>"; 
								    tbodyHtml += "<td title='"+StringUtil.escapeStr(row.SM)+"'>"+StringUtil.escapeStr(row.SM)+"</td>";
								    tbodyHtml += "<td title='"+StringUtil.escapeStr(row.BLQX==null?"":row.BLQX.substring(0,10))+"' class='text-center'>"+StringUtil.escapeStr(row.BLQX==null?"":row.BLQX.substring(0,10))+"</td>";
								    tbodyHtml += "<td>"
								    var chlidren = row.DBSTR;
								    
								  //评估单-有办理记录
								  if(chlidren && row.DBGZLX == '9' && row.JD == 1){
									var str = chlidren.split(",");
									var arr = str[0].split("#_#");
									//未处理
									//arr[1] 业务编号 YWBH
									//arr[0] 业务ID YWID
									//arr[2] 业务版本 YWBB
									if(arr[1]) {
										if(str.length>1){
											tbodyHtml +=this_.formatToggle(arr,row);
											
									    	tbodyHtml += "<i class='icon-caret-down showHideIcon cursor-pointer' id='"+index+"icon'  indexNum='"+index+"' style='margin-right:10px;'></i>";
										}else{
											tbodyHtml +=this_.formatToggle(arr,row);
										}
										
									}else{
										 tbodyHtml += "<a href='javascript:void(0);' dbgzType='0'" ;
										 tbodyHtml += this_.formatAttr(row,arr[0])
										 tbodyHtml += " title='办理' style='margin-right:8px;'>办理</a>";
										
									}
								  }else{
									  
									  
									  if(chlidren){
										  var str = chlidren.split(",");
											var arr = str[0].split("#_#");
											if(str.length>1){
												tbodyHtml +=this_.formatToggle(arr,row);
										    	tbodyHtml += "<i class='icon-caret-down showHideIcon cursor-pointer' id='"+index+"icon'  indexNum='"+index+"' style='margin-right:10px;'></i>";
											}else{
												tbodyHtml +=this_.formatToggle(arr,row);
										    	tbodyHtml += "</a>&nbsp;";
											}
									  }
									  
									  tbodyHtml += "<a href='javascript:void(0);' dbgzType='0'" ;
									  tbodyHtml += this_.formatAttr(row,"");
									  tbodyHtml += " title='办理' style='margin-right:8px;'>办理</a>" ;
									
								  }
								 
									tbodyHtml += "<a href='javascript:void(0);' dbgzType='1' ";
									tbodyHtml += "lybh='"+row.LYBH+"' " ;
									tbodyHtml += "fjjx='"+row.FJJX+"' " ;
									tbodyHtml += "dbgzlx='"+row.DBGZLX+"' ";
									tbodyHtml += "todoId='"+ row.ID +"' title='反馈'>反馈</a>";
									 if(chlidren){
										 var str = chlidren.split(",");
										 tbodyHtml +="<div id='"+index+"content' style='display:none;'>";
										 for(var i=1;i<str.length;i++){
											 var arr = str[i].split("#_#");
											 tbodyHtml +=this_.formatToggle(arr,row);
											 tbodyHtml +="<br/>";
									    }
									    tbodyHtml +="</div>";
									 }
									
						            tbodyHtml += "</td>";
									tbodyHtml += "</tr>";
								})
							}else{
								tbodyHtml += "<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>";
							}
							tbodyHtml += "</tbody></table>";
								
							this_.getDataContainer().html(tbodyHtml);
							
							//取消遮罩层效果
							
							this_.unblockUI();
							
							//设置badge
							
							this_.setNumber(data.rows.length == 0 ?'':data.rows.length);
							
							//表格中链接的点击事件
							
							$("a", this_.getDataContainer()).on("click", this_.view);
							$("i.showHideIcon", this_.getDataContainer()).on("click", this_.showHideIcon);
						}else{
							this_.setNumber('');
							tbodyHtml += "<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>";
							tbodyHtml += "</tbody></table>";
							this_.getDataContainer().html(tbodyHtml);
							
							//取消遮罩层效果
							this_.unblockUI();
						}
				    }
				}); 
			},
			formatAttr:function(row,YWID){
				tbodyHtml ="";
				tbodyHtml += "lybh ='"+StringUtil.escapeStr(row.LYBH)+"'" ;
			    tbodyHtml += "fjjx ='"+StringUtil.escapeStr(row.FJJX)+"'" ;
			    tbodyHtml += "dbgzlx ='"+StringUtil.escapeStr(row.DBGZLX)+"'" ;
			    tbodyHtml += "todoId ='"+StringUtil.escapeStr(row.ID)+"'";
			    tbodyHtml += "lyid  ='"+StringUtil.escapeStr(row.LYID)+"'";
			    tbodyHtml += "ywid  ='"+StringUtil.escapeStr(YWID)+"'";
			    tbodyHtml += "dbywid  ='"+StringUtil.escapeStr(row.DBYWID)+"'";
			    tbodyHtml += "jd  ='"+StringUtil.escapeStr(row.JD)+"'";
			    return tbodyHtml;
			},
			formatToggle:function(arr,row){
				var this_=this;
				var tbodyHtml = "";
				tbodyHtml += "<a href='javascript:void(0)' dbgzType='2'";
		    	tbodyHtml += this_.formatAttr(row,arr[0]);
		    	tbodyHtml += "title='"+arr[1]+"R"+arr[2]+"'>";
		    	tbodyHtml += arr[1];
		    	if(StringUtil.escapeStr(arr[2]) != ''){
		    		tbodyHtml += " R"+arr[2];
		    	}
		    	tbodyHtml += "</a>&nbsp;";
		    	return tbodyHtml;
			},
			
			view: function(e){
				var dbgzType=$(e.target).attr("dbgzType");
				var lybh =$(e.target).attr("lybh");
				var fjjx =$(e.target).attr("fjjx");
				var dbgzlx =$(e.target).attr("dbgzlx");//待办类型
				var todoId =$(e.target).attr("todoId");
				var lyid =$(e.target).attr("lyid");
				var ywid =$(e.target).attr("ywid");
				var dbywid =$(e.target).attr("dbywid");
				var jd =$(e.target).attr("jd");
				lybh = Base64.encode(encodeURIComponent(lybh));
				fjjx = Base64.encode(encodeURIComponent(fjjx));
				if(dbgzType==0){//办理
					handleCfg.toHandle({todoId:todoId,dbgzlx:dbgzlx,lyid:lyid,fjjx:fjjx,id:dbywid,jd:jd});
				}else if(dbgzType==1){//反馈
					handleCfg.feedback({dbgzlx:dbgzlx,lybh:lybh,fjjx:fjjx,todoId:todoId});
				}else if(dbgzType==2){
					//办理单个链接
					handleCfg.toViewTodo({todoId:todoId,dbgzlx:dbgzlx,lyid:lyid,fjjx:fjjx,id:ywid,jd:jd});
				}else{
					window.open(panelData[6].singleUrl + $(e.target).attr("bizId"));
				}
			},
			showHideIcon:function(e){
				var lybh =$(e.target).attr("indexNum");
				if($("#"+lybh+"content").css("display")=="block"){
					$("#"+lybh+"content").css("display","none");
				}else{
					$("#"+lybh+"content").css("display","block");
				}
			}
		});
	panelData[6].panel = panel;
	}
	currentPanel = panelData[6].panel;
}else{
	if(panelData[6].panel){
		panelData[6].panel.destory(true);
	}
}	
	
//飞机监控(135)

if($.inArray(panelData[7].id, delPanel) == -1 && checkBtnPermissions(panelData[7].permissioncode)){ 
	if(!panelData[7].panel){
var panel = new PanelBorad({
	id: panelData[7].id,
	title: panelData[7].title,
	beforePanel: currentPanel,
	moreUrl: panelData[7].moreUrl+"?dprtcode="+userJgdm+"&yjbs=1",
	bodyHeight:"330px",
	refreshTime:"2018-02-28 17:20:15",
	panelDivClass:"col-lg-12 col-sm-12 col-xs-12 padding-leftright-3",
	
	//删除时调用的方法
	removeCallback: function(flag){
		panelData[7].panel = null;
		if(flag !== true){
			delPanel.push(panelData[7].id);
			//往后台存入删除的面板
			removePanel(panelData[7].id);
		}
	},
	//加载事件
	load: function(){
		var this_ = this;
		/*this_.blockUI();*/
		var tbodyHtml ="";
		tbodyHtml += '<div class="jcarouselnew-wrapper col-xs-12" >';
		tbodyHtml += '<div id="shortcutDivNew" class="jcarouselnew" data-jcarousel="true">';
		tbodyHtml += '<ul style="height:330px;">';
		this_.blockUI();
		AjaxUtil.ajax({
			url:basePath+"/sys/custom/block/block12",
			type: "get",
			cache:false,
			dataType:"json",
			success:function(data){
				if(data && data.rows){
					if(data.rows.length > 0){
						$.each(data.rows, function(index, row){
							
							var title1 = "剩余有效期&lt;="+row.YJTS_JB1+"天";
							var title2 = row.YJTS_JB1+"&lt;剩余有效期&lt;="+row.YJTS_JB2+"天";
							
							tbodyHtml += '<li style="height:330px;">';
							tbodyHtml += '<div class="monitoring_content">';
							tbodyHtml += '<div class="monitoring_title">';
							tbodyHtml += '<div class="pull-left monitoring_title_div">';
							if(row.GJDJZYXQ_WARN==2 || row.SHZYXQ_WARN==2 || row.DTZZYXQ_WARN==2 ){
								tbodyHtml += '<i class="fa fa-exclamation-triangle monitoring_title_icon" style="color:red;" title="'+title1+'"></i>';
							}else if((row.GJDJZYXQ_WARN !=2 && row.SHZYXQ_WARN !=2 && row.DTZZYXQ_WARN!=2)&&(row.GJDJZYXQ_WARN==1 || row.SHZYXQ_WARN==1 || row.DTZZYXQ_WARN==1 )){
								tbodyHtml += '<i class="fa fa-exclamation-triangle monitoring_title_icon" title="'+title2+'"></i>';
							}else{
								tbodyHtml += '';
								/*tbodyHtml += '<i class="fa fa-exclamation-triangle monitoring_title_icon" style="visibility:hidden;"></i>';*/
							}
							tbodyHtml += '</div>';
							tbodyHtml += '<div class="pull-left" style="margin-left:8px;">';
							tbodyHtml += '<p class="margin-bottom-0 font-size-16" ><a href="javascript:void(0)" bltype="5" fjzch="'+StringUtil.escapeStr(row.FJZCH)+'" title="'+StringUtil.escapeStr(row.FJZCH)+'">'+StringUtil.escapeStr(row.FJZCH)+'</a></p>';
							tbodyHtml += '<p class="margin-bottom-0 font-size-12" title="'+StringUtil.escapeStr(row.XLH)+'">'+StringUtil.escapeStr(row.XLH)+'</p>';
							tbodyHtml += '</div>';
							tbodyHtml += '<div class="pull-right" style="padding-left:10px;padding-right:5px;">';
							
							// 适航状态：0适航、1表示不适航
							
							if(StringUtil.escapeStr(row.SHZT) == "1"){
								tbodyHtml += '<i class="iconnew-flightTop" style="font-size:32px;color:red;" title="不适航"></i><label style="font-weight:bold;font-size:18px;vertical-align:4px;padding-left:5px;">不适航</label>';	
							}else{
								tbodyHtml += '<i class="icon-fighter-jet" style="font-size:30px;color:green;" title="适航"></i><label style="font-weight:bold;font-size:18px;vertical-align:4px;padding-left:5px;">适航</label>';
							}
							
							tbodyHtml += '</div>';
							tbodyHtml += '<div class="clearfix"></div>';
							tbodyHtml += '</div>';
							tbodyHtml += '<div>'
							tbodyHtml += '<div class="col-xs-6 monitoring_header_left">';
							tbodyHtml += StringUtil.escapeStr(row.FXXS)==''?"0 FH":StringUtil.escapeStr(row.FXXS);
							tbodyHtml += '</div>';
							tbodyHtml += '<div class="col-xs-6 monitoring_header_right">';
							tbodyHtml += StringUtil.escapeStr(row.FXXH)==''?"0 FC":StringUtil.escapeStr(row.FXXH);
							tbodyHtml += '</div>';
							tbodyHtml += '<div class="clearfix"></div>';
							tbodyHtml += '</div>';
							tbodyHtml += '<div  class="monitoring_body">';
							tbodyHtml += '<p class="margin-bottom-0" >';
							tbodyHtml += '<label style="padding-left:5px;">国籍登记证有效期：</label>';
							if(StringUtil.escapeStr(row.GJDJZYXQ_SY)===''){
								tbodyHtml += '<label class="pull-right" style="padding-right:20px;">&nbsp;&nbsp;<i class="fa fa-exclamation-triangle" style="visibility:hidden;"></i></label>';
							}else{
								tbodyHtml += '<label class="pull-right" style="padding-right:20px;">'+StringUtil.escapeStr(row.GJDJZYXQ_SY)+' D&nbsp;';
								if(row.GJDJZYXQ_WARN==1){
									tbodyHtml += '<i class="fa fa-exclamation-triangle" style="color:#FF9900;" title="'+title2+'"></i>';	
								}else if(row.GJDJZYXQ_WARN==2){
									tbodyHtml += '<i class="fa fa-exclamation-triangle" style="color:red;" title="'+title1+'"></i>';	
								}else{
									tbodyHtml += '<i class="fa fa-exclamation-triangle" style="visibility:hidden;"></i>';	
								}
								
								tbodyHtml += '</label>';
							}
							
							tbodyHtml += '</p>';
							tbodyHtml += '<p class="margin-bottom-0" >';
							tbodyHtml += '<label style="padding-left:5px;">适航证有效期：</label>';
							if(StringUtil.escapeStr(row.SHZYXQ_SY)===''){
								tbodyHtml += '<label class="pull-right" style="padding-right:20px;"></label>&nbsp;&nbsp;<i class="fa fa-exclamation-triangle" style="visibility:hidden;"></i>';	
							}else{
								tbodyHtml += '<label class="pull-right" style="padding-right:20px;">'+StringUtil.escapeStr(row.SHZYXQ_SY)+' D&nbsp;';
								if(row.SHZYXQ_WARN==1){
									tbodyHtml += '<i class="fa fa-exclamation-triangle" style="color:#FF9900;" title="'+title2+'"></i>';	
								}else if(row.SHZYXQ_WARN==2){
									tbodyHtml += '<i class="fa fa-exclamation-triangle" style="color:red;" title="'+title1+'"></i>';	
								}else{
									tbodyHtml += '<i class="fa fa-exclamation-triangle" style="visibility:hidden;"></i>';	
								}
								tbodyHtml += '</label>';
							}
							tbodyHtml += '</p>';
							tbodyHtml += '<p class="margin-bottom-0">';
							tbodyHtml += '<label style="padding-left:5px;">无线电台执照有效期：</label>';
							if(StringUtil.escapeStr(row.DTZZYXQ_SY)===''){
								tbodyHtml += '<label class="pull-right" style="padding-right:20px;"></label>&nbsp;&nbsp;<i class="fa fa-exclamation-triangle" style="visibility:hidden;"></i>';
							}else{
								tbodyHtml += '<label class="pull-right" style="padding-right:20px;">'+StringUtil.escapeStr(row.DTZZYXQ_SY)+' D&nbsp;';
								if(row.DTZZYXQ_WARN==1){
									tbodyHtml += '<i class="fa fa-exclamation-triangle" style="color:#FF9900;" title="'+title2+'"></i>';	
								}else if(row.DTZZYXQ_WARN==2){
									tbodyHtml += '<i class="fa fa-exclamation-triangle" style="color:red;" title="'+title1+'"></i>';	
								}else if(row.DTZZYXQ_WARN){
									tbodyHtml += '<i class="fa fa-exclamation-triangle" style="visibility:hidden;"></i>';	
								}
								tbodyHtml += '</label>';
							}
							
							tbodyHtml += '</p>';
							tbodyHtml += '</div>';
							tbodyHtml += '<div style="border-left:1px solid #d5d5d5;border-right:1px  solid #d5d5d5;height:133px;overflow:auto;">';
							tbodyHtml += '<table class="table" style="margin-bottom:0px !important;">';
							tbodyHtml += '<tbody>';
							
							tbodyHtml += this_.formatLastData(row);
							
							tbodyHtml +='</tbody> ';
							tbodyHtml +='</table>';
							tbodyHtml +='</div>';
							tbodyHtml +='<div style="border:1px solid #d5d5d5;border-bottom:0px;">';
						
							tbodyHtml +='<div class="col-xs-4 monitoring_ckwxjh">';
							tbodyHtml +='<a href="javascript:void(0)" bltype="4" fjzch="'+StringUtil.escapeStr(row.FJZCH)+'" title="查看维修监控">查看维修监控</a>';
							tbodyHtml +='</div>';
							tbodyHtml +='<div class="col-xs-8 monitoring_zzjxdgz" style="">';
							tbodyHtml +='正在进行的工作：';
							if(row.GD_NUM == 0){
								tbodyHtml +='<span style="padding-left:5px;padding-right:5px;">'+StringUtil.escapeStr(row.GD_NUM)+'</span>个';
							}else{
								tbodyHtml +='<a href="javascript:void(0)" style="padding-left:5px;padding-right:5px; " bltype="3" fjzch="'+StringUtil.escapeStr(row.FJZCH)+'" title="'+StringUtil.escapeStr(row.GD_NUM)+'">'+StringUtil.escapeStr(row.GD_NUM)+'</a>个';
							}
							
							tbodyHtml +='</div>';
							tbodyHtml +='<div class="clearfix"></div>';
							tbodyHtml +='</div>';
							tbodyHtml +='<div>';
							tbodyHtml +='<div class="col-xs-4 monitoring_footer_left">';
							
							if(row.GZBL_NUM==0){
								tbodyHtml +='<label >故障保留</label><label style="font-weight:normal;">N/A</label>';	
							}else{
								tbodyHtml +='<label >故障保留</label><a href="javascript:void(0)"><span class="badge margin-left-10 monitoring-badge" bltype="0" fjzch="'+StringUtil.escapeStr(row.FJZCH)+'" title="'+StringUtil.escapeStr(row.GZBL_NUM)+'">'+StringUtil.escapeStr(row.GZBL_NUM)+'</span></a>';
							}
							tbodyHtml +='</div>';
							tbodyHtml +='<div class="col-xs-4 monitoring_footer_left">';
							
							if(row.XMBL_NUM==0){
								tbodyHtml +='<label >项目保留</label><label style="font-weight:normal;">N/A</label>';	
							}else{
								tbodyHtml +='<label >项目保留</label><a href="javascript:void(0)"><span class="badge margin-left-10 monitoring-badge" bltype="1" fjzch="'+StringUtil.escapeStr(row.FJZCH)+'" title="'+StringUtil.escapeStr(row.XMBL_NUM)+'" >'+StringUtil.escapeStr(row.XMBL_NUM)+'</span></a>';
							}
							
							tbodyHtml +='</div> '; 
							tbodyHtml +='<div class="col-xs-4 monitoring_footer_right" style="">';
							
							if(row.QXBL_NUM==0){
								tbodyHtml +='<label >缺陷保留</label><label style="font-weight:normal;">N/A</label>';	
							}else{
								tbodyHtml +='<label >缺陷保留</label><a href="javascript:void(0)">';
								tbodyHtml +='<span class="badge margin-left-10 monitoring-badge" bltype="2"' ;
								tbodyHtml +='fjzch="'+StringUtil.escapeStr(row.FJZCH)+'"' ;
								tbodyHtml +='title="'+StringUtil.escapeStr(row.QXBL_NUM)+'">'
								tbodyHtml +=StringUtil.escapeStr(row.QXBL_NUM)+'</span></a>';
							}
							tbodyHtml +='</div>';
							tbodyHtml +='</div>';
							tbodyHtml +='</div>';
							tbodyHtml += '</li>';
						})
					}else{
						/*tbodyHtml += "<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>"*/
					}
					tbodyHtml += '</ul>';
					tbodyHtml +='<i class="icon-chevron-left jcarouselnew-control-prev inactive" data-jcarouselcontrol="true" style="top:160px;"></i>';
					tbodyHtml +='<i class="icon-chevron-right jcarouselnew-control-next" data-jcarouselcontrol="true"  style="top:160px;"></i>'; 
					tbodyHtml +='</div>';
					tbodyHtml +='</div>';
					this_.getDataContainer().html(tbodyHtml);
					this_.unblockUI();
					jcarouselNew = $('#shortcutDivNew').jcarousel();
					$('.jcarouselnew-control-prev').on('jcarouselcontrol:active', function() {
				        $(this).removeClass('inactive');
				    }) .on('jcarouselcontrol:inactive', function() {
				        $(this).addClass('inactive');
				    }).jcarouselControl({
				        target: '-=1'
				    });
					$('.jcarouselnew-control-next').on('jcarouselcontrol:active', function() {
				        $(this).removeClass('inactive');
				    }).on('jcarouselcontrol:inactive', function() {
				        $(this).addClass('inactive');
				    }).jcarouselControl({
				        target: '+=1'
				    });
					this_.setNumber(data.rows.length == 0 ?'':data.rows.length);
					
					$("a", this_.getDataContainer()).on("click", this_.view);
				}else{
					this_.setNumber('');
					tbodyHtml += "<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>";
					tbodyHtml += "</tbody>";
					tbodyHtml += "</table>";
					this_.getDataContainer().html(tbodyHtml);
					this_.unblockUI();
				}
				}
			})
		
		
	},
	formatLastData : function(row){
		var this_ = this;
		var jksj = row.JKSJ;
		var tbodyHtml = "";
		if(jksj != null && jksj != '' && jksj.length > 0){
			$.each(jksj,function(index, j){
				tbodyHtml += '<tr>';
				tbodyHtml += '<td class="colwidth-5" title="'+DicAndEnumUtil.getEnumName('installedPositionEnum',j.WZ)+'">'+DicAndEnumUtil.getEnumName('installedPositionEnum',j.WZ)+'</td>';
				tbodyHtml += '<td class="colwidth-15">';
				tbodyHtml += '<p class="margin-bottom-0" title="P/N:'+j.BJH+'">P/N:'+j.BJH+'</p>';
				tbodyHtml += '<p class="margin-bottom-0" title="S/N:'+j.XLH+'">S/N:'+j.XLH+'</p>';
				tbodyHtml += '</td>';
				tbodyHtml += '<td class="colwidth-8">';
				var jkz = j.JKZ;
				if(jkz != null && jkz != '' && jkz.length > 0){
					$.each(jkz,function(index, z){
						tbodyHtml += '<p class="margin-bottom-0 text-right" title="'+z+'">'+z+'</p>';
					});
				}
				tbodyHtml += '</td>';
				tbodyHtml += '</tr>';
			
			});
		}
		return tbodyHtml;
	},
	//查看链接的事件
	view: function(e){
		var bltype = $(e.target).attr("bltype");
		var fjzch = $(e.target).attr("fjzch");
		fjzch = Base64.encode(encodeURIComponent(fjzch));
		if(bltype == "0"){
			//故障保留
			window.open(basePath+"/produce/reservation/fault/monitor?dprtcode="+userJgdm+"&fjzch="+fjzch);
		}else if(bltype == "1"){
			//项目保留
			window.open(basePath+"/produce/reservation/item/monitor?dprtcode="+userJgdm+"&fjzch="+fjzch);
		}else if(bltype == "2"){
			//缺陷保留
			window.open(basePath+"/produce/reservation/defect/monitor?dprtcode="+userJgdm+"&fjzch="+fjzch);
		}else if(bltype == "3"){
			//正在进行的工作
			window.open(basePath+"/produce/maintenanceplan/main?dprtcode="+userJgdm+"&fjzch="+fjzch);
			
		}else if(bltype == "4"){
			//查看维修监控
			window.open(basePath+"/produce/maintenance/monitoring/main?dprtcode="+userJgdm+"&fjzch="+fjzch);
		}else if(bltype == "5"){
			//飞机注册号
			window.open(basePath+"/aircraftinfo/status/main?dprtcode="+userJgdm+"&fjzch="+fjzch);
		}else{
	
			console.info("暂时没有连接！")
		}
		
	}
})
panelData[7].panel = panel;
	}
	currentPanel = panelData[7].panel;
}else{
	if(panelData[7].panel){
		panelData[7].panel.destory(true);
	}
}
	
//飞机监控(145)
	
if($.inArray(panelData[8].id, delPanel) == -1 && checkBtnPermissions(panelData[8].permissioncode)){ 
	if(!panelData[8].panel){
var panel = new PanelBorad({
	id: panelData[8].id,
	title: panelData[8].title,
	beforePanel: currentPanel,
	moreUrl: panelData[8].moreUrl+"?dprtcode="+userJgdm+"&yjbs=1",
	buttons:['refresh','close'],
	bodyHeight:"110px",
	refreshTime:"2018-02-28 17:20:15",
	panelDivClass:"col-lg-12 col-sm-12 col-xs-12 padding-leftright-3",
	
	//删除时调用的方法
	removeCallback: function(flag){
		panelData[8].panel = null;
		if(flag !== true){
			delPanel.push(panelData[8].id);
			//往后台存入删除的面板
			removePanel(panelData[8].id);
		}
	},
	//加载事件
	load: function(){
		var this_ = this;
		
		var tbodyHtml ="";
		tbodyHtml += '<div class="jcarouselnew-wrapper col-xs-12" >';
		tbodyHtml += '<div id="shortcutDivNew145" class="jcarouselnew" data-jcarousel="true">';
		tbodyHtml += '<ul>';
		this_.blockUI();
		AjaxUtil.ajax({
			url:basePath+"/sys/custom/block/block13",
			type: "get",
			cache:false,
			dataType:"json",
			success:function(data){
				if(data && data.rows){
					if(data.rows.length > 0){
						$.each(data.rows, function(index, row){
							
							tbodyHtml += '<li style="height:110px;">';
							tbodyHtml += '<div class="monitoring_content">';
							tbodyHtml += '<div class="monitoring_title">';
							tbodyHtml += '<div class="pull-left" style="margin-left:10px;height:45px;">';
							tbodyHtml += '<p class="margin-bottom-0 font-size-16" ><a href="javascript:void(0)" bltype="5" fjzch="'+StringUtil.escapeStr(row.FJZCH)+'" title="'+StringUtil.escapeStr(row.FJZCH)+'">'+StringUtil.escapeStr(row.FJZCH)+'</a></p>';
							tbodyHtml += '<p class="margin-bottom-0 font-size-12" title="'+StringUtil.escapeStr(row.XLH)+'">'+StringUtil.escapeStr(row.XLH)+'</p>';
							tbodyHtml += '</div>';
							tbodyHtml += '<div class="clearfix"></div>';
							tbodyHtml += '</div>';
							tbodyHtml +='<div style="border:1px solid #d5d5d5;border-bottom:0px;">';
							
							tbodyHtml +='<div class="col-xs-8 monitoring_zzjxdgz" style="">';
							tbodyHtml +='正在进行的工作：';
							if( row.GD_NUM==0 ){
								tbodyHtml +='<span style="padding-left:5px;padding-right:5px;" >0</span>个';	
							}else{
								tbodyHtml +='<a href="javascript:void(0);" style="padding-left:5px;padding-right:5px;" fjzch="'+StringUtil.escapeStr(row.FJZCH)+'" bltype="3" title="'+StringUtil.escapeStr(row.GD_NUM)+'">'+StringUtil.escapeStr(row.GD_NUM)+'</a>个';
							}
							
							tbodyHtml +='</div>';
							tbodyHtml +='<div class="clearfix"></div>';
							tbodyHtml +='</div>';
							tbodyHtml +='<div>';
							tbodyHtml +='<div class="col-xs-4 monitoring_footer_left">';
							if(row.GZBL_NUM==0){
								tbodyHtml +='<label >故障保留</label><label style="font-weight:normal;">N/A</label>';	
							}else{
								tbodyHtml +='<label >故障保留</label><a href="javascript:void(0);" ><span class="badge margin-left-10 monitoring-badge" fjzch="'+StringUtil.escapeStr(row.FJZCH)+'" bltype="0" title="'+StringUtil.escapeStr(row.GZBL_NUM)+'" >'+StringUtil.escapeStr(row.GZBL_NUM)+'</span></a>';
							}
							tbodyHtml +='</div>';
							tbodyHtml +='<div class="col-xs-4 monitoring_footer_left">';
							if(row.XMBL_NUM==0){
								tbodyHtml +='<label >项目保留</label><label style="font-weight:normal;">N/A</label>';	
							}else{
								tbodyHtml +='<label >项目保留</label><a href="javascript:void(0);" ><span class="badge margin-left-10 monitoring-badge" bltype="1" fjzch="'+StringUtil.escapeStr(row.FJZCH)+'" title="'+StringUtil.escapeStr(row.XMBL_NUM)+'" >'+StringUtil.escapeStr(row.XMBL_NUM)+'</span></a>';
							}
							tbodyHtml +='</div> '; 
							tbodyHtml +='<div class="col-xs-4 monitoring_footer_right" style="">';
							if(row.QXBL_NUM==0){
								tbodyHtml +='<label >缺陷保留</label><label style="font-weight:normal;">N/A</label>';	
							}else{
								tbodyHtml +='<label >缺陷保留</label><a href="javascript:void(0);" ><span class="badge margin-left-10 monitoring-badge" bltype="2" fjzch="'+StringUtil.escapeStr(row.FJZCH)+'" title="'+StringUtil.escapeStr(row.QXBL_NUM)+'" >'+StringUtil.escapeStr(row.QXBL_NUM)+'</span></a>';
							}
							
							tbodyHtml +='</div>';
							tbodyHtml +='</div>';
							tbodyHtml +='</div>';
							tbodyHtml += '</li>';
						})
					}else{
						tbodyHtml += "<div>暂无数据 No data.</div>"
					}
					tbodyHtml += '</ul>';
					tbodyHtml +='<i class="icon-chevron-left jcarouselnew-control-prev inactive" data-jcarouselcontrol="true"  style="top:60px;"></i>';
					tbodyHtml +='<i class="icon-chevron-right jcarouselnew-control-next" data-jcarouselcontrol="true"  style="top:60px;"></i>'; 
					tbodyHtml +='</div>';
					tbodyHtml +='</div>';
					this_.getDataContainer().html(tbodyHtml);
					this_.unblockUI();
					jcarouselNew = $('#shortcutDivNew145').jcarousel();
					$('.jcarouselnew-control-prev').on('jcarouselcontrol:active', function() {
				        $(this).removeClass('inactive');
				    }) .on('jcarouselcontrol:inactive', function() {
				        $(this).addClass('inactive');
				    }).jcarouselControl({
				        target: '-=1'
				    });
					$('.jcarouselnew-control-next').on('jcarouselcontrol:active', function() {
				        $(this).removeClass('inactive');
				    }).on('jcarouselcontrol:inactive', function() {
				        $(this).addClass('inactive');
				    }).jcarouselControl({
				        target: '+=1'
				    });
					
					this_.setNumber(data.rows.length == 0 ?'':data.rows.length);
					$("a", this_.getDataContainer()).on("click", this_.view);
				}else{
					this_.setNumber('');
					tbodyHtml += "<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>";
					this_.getDataContainer().html(tbodyHtml);
					this_.unblockUI();
				}
				}
			})
		/*this_.unblockUI();*/
		
		
	},
	//查看链接的事件
	view: function(e){
		var bltype = $(e.target).attr("bltype");
		var fjzch = $(e.target).attr("fjzch");
		fjzch = Base64.encode(encodeURIComponent(fjzch));
		if(bltype == "0"){
			//故障保留
			window.open(basePath+"/produce/reservation/fault/monitor?dprtcode="+userJgdm+"&fjzch="+fjzch);
		}else if(bltype == "1"){
			//项目保留
			window.open(basePath+"/produce/reservation/item/monitor?dprtcode="+userJgdm+"&fjzch="+fjzch);
		}else if(bltype == "2"){
			//缺陷保留
			window.open(basePath+"/produce/reservation/defect/monitor?dprtcode="+userJgdm+"&fjzch="+fjzch);
		}else if(bltype == "3"){
			//正在进行的工作
			window.open(basePath+"/produce/maintenanceplan145/main?dprtcode="+userJgdm+"&fjzch="+fjzch);
			
		}else if(bltype == "5"){
			//飞机注册号
			window.open(basePath+"/aircraftinfo/status/main?dprtcode="+userJgdm+"&fjzch="+fjzch);
			
		}else{
			console.info("暂时没有连接！")
		}
		
	}
	
})
panelData[8].panel = panel;
}
currentPanel = panelData[8].panel;
}else{
	if(panelData[8].panel){
		panelData[8].panel.destory(true);
	}
}
	
//我的培训通知
if($.inArray(panelData[0].id, delPanel) == -1 && checkBtnPermissions(panelData[0].permissioncode)){ 
	
	if(!panelData[0].panel){
		var panel = new PanelBorad({
			id: panelData[0].id,
			title: panelData[0].title,
			beforePanel: currentPanel,
			moreUrl: panelData[0].moreUrl,
			refreshTime:"2018-02-28 17:20:15",
			panelDivClass:"col-lg-6 col-sm-6 col-xs-12 padding-leftright-3",
			
			//删除时调用的方法
			removeCallback: function(flag){
				panelData[0].panel = null;
				if(flag !== true){
					delPanel.push(panelData[0].id);
					//往后台存入删除的面板
					removePanel(panelData[0].id);
				}
			},
			//加载事件
			load: function(){
				var this_ = this;
				var tbodyHtml = "<table class='table table-thin table-bordered table-striped table-hover table-set'><thead>";
				   tbodyHtml += "<tr><th class='colwidth-6'><div class='font-size-12 line-height-18'>通知类型</div><div class='font-size-9 line-height-18'>Notice Type</div></th>";
				   tbodyHtml +=	"<th class='colwidth-20'><div class='font-size-12 line-height-18'>培训时间</div><div class='font-size-9 line-height-18'>Train Time</div></th>"; 
				   tbodyHtml +=	"<th class='colwidth-13'><div class='font-size-12 line-height-18'>培训地点</div><div class='font-size-9 line-height-18'>Train Place</div></th>"; 
				   tbodyHtml +=	"<th class='colwidth-8'><div class='font-size-12 line-height-18'>课程名称</div><div class='font-size-9 line-height-18'>Course Name</div></th>"; 
				   tbodyHtml +=	"<th class='colwidth-8'><div class='font-size-12 line-height-18'>培训形式</div><div class='font-size-9 line-height-18'>Train Model</div></th>"; 
				   tbodyHtml += "</tr></thead>";
				   this_.blockUI();   
				AjaxUtil.ajax({
					url:basePath+"/sys/custom/block/block5",
					type: "get",
					cache:false,
					dataType:"json",
					success:function(data){
						
						tbodyHtml += "<tbody>";
							if(data && data.rows && data.rows.length > 0){
								training = data.rows;
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
									tbodyHtml += "<td title='"+StringUtil.escapeStr(row.CKCMC)+"'><a href='javascript:void(0);' bizId=\""+row.ID+"\">"+StringUtil.escapeStr(row.CKCMC)+"</a></td>";
									tbodyHtml += "<td align='left' title='"+StringUtil.escapeStr(row.PXXS)+"'>"+StringUtil.escapeStr(row.PXXS)+"</td>";
									tbodyHtml += "</tr>";
								});
								tbodyHtml += "</tbody></table>";
								this_.getDataContainer().html("");
								this_.getDataContainer().append(tbodyHtml);
								this_.unblockUI();
								this_.setNumber(data.rows.length == 0 ?'':data.rows.length);
								$("a", this_.getDataContainer()).on("click", function(e){
									this_.view(e);
								});
								
							}else{
								training = [];
								this_.setNumber('');
								tbodyHtml += "<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>";
								tbodyHtml += "</tbody></table>";
								this_.getDataContainer().html("");
								this_.getDataContainer().html(tbodyHtml);
								this_.unblockUI();
							}
						if(!trainingLoaded){
							trainingLoaded = true;
							//弹出强制提示
							initPopMsg();
						}
				    }
				}); 
			},
			//查看链接的事件
			view: function(e){
				var dbgzlx = $(e.target).attr("dbgzlx");
				window.open(panelData[0].singleUrl + $(e.target).attr("bizId"));
			}
		})
		panelData[0].panel = panel;
	}
	currentPanel = panelData[0].panel;
}else{
	if(panelData[0].panel){
		panelData[0].panel.destory(true);
	}
}
	
//传阅
if($.inArray(panelData[5].id, delPanel) == -1 && checkBtnPermissions(panelData[5].permissioncode)){ 
	if(!panelData[5].panel){
		var panel = new PanelBorad({
			id: panelData[5].id,
			title: panelData[5].title,
			beforePanel: currentPanel,
			moreUrl: panelData[5].moreUrl,
			refreshTime:"2018-02-28 17:20:15",
			panelDivClass:"col-lg-6 col-sm-6 col-xs-12 padding-leftright-3",
			tableHead: "" ,
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
				var tbodyHtml = "<table class='table table-thin table-bordered table-striped table-hover table-set'><thead>";
					tbodyHtml += "<tr>";
					tbodyHtml += "<th class='colwidth-8'><div class='font-size-12 line-height-18'>类型</div><div class='font-size-9 line-height-18'>Type</div></th>";
					tbodyHtml += "<th class='colwidth-12'><div class='font-size-12 line-height-18'>编号</div><div class='font-size-9 line-height-18'>No.</div></th>";
					tbodyHtml += "<th class='colwidth-13'><div class='font-size-12 line-height-18'>机型</div><div class='font-size-9 line-height-18'>A/C Type</div></th>";
					tbodyHtml += "<th class='colwidth-15'><div class='font-size-12 line-height-18'>主题</div><div class='font-size-9 line-height-18'>Subject</div></th>";
					tbodyHtml += "<th class='colwidth-12'><div class='font-size-12 line-height-18'>传阅期限</div><div class='font-size-9 line-height-18'>Bulletin Limit</div></th>";
					tbodyHtml += "</tr>";
					tbodyHtml += "</thead>";
					tbodyHtml += "</tbody>";
					this_.blockUI();
					AjaxUtil.ajax({
					url:basePath+"/sys/custom/block/block10",
					type: "get",
					cache:false,
					dataType:"json",
					success:function(data){
						if(data && data.rows){
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
								tbodyHtml += "<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>"
							}
							tbodyHtml += "</tbody>";
							tbodyHtml += "</table>";
							this_.getDataContainer().html(tbodyHtml);
							this_.unblockUI();
							this_.setNumber(data.rows.length == 0 ?'':data.rows.length);
							
							$("a", this_.getDataContainer()).on("click", this_.view);
						}else{
							this_.setNumber('');
							tbodyHtml += "<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>";
							tbodyHtml += "</tbody>";
							tbodyHtml += "</table>";
							this_.getDataContainer().html(tbodyHtml);
							this_.unblockUI();
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
	
	
//待办工作

if($.inArray(panelData[4].id, delPanel) == -1 && checkBtnPermissions(panelData[4].permissioncode)){ 
	if(!panelData[4].panel){
		var panel = new PanelBorad({
			id: panelData[4].id,
			title: panelData[4].title,
			beforePanel: currentPanel,
			moreUrl: panelData[4].moreUrl,
			refreshTime:"2018-02-28 17:20:15",
			panelDivClass:"col-lg-6 col-sm-6 col-xs-12 padding-leftright-3",
			
			//删除时调用的方法
			removeCallback: function(flag){
				panelData[4].panel = null;
				if(flag !== true){
					delPanel.push(panelData[4].id);
					//往后台存入删除的面板
					removePanel(panelData[4].id);
				}
			},
			//加载事件
			load: function(){
				var this_ = this;
				var tbodyHtml = "<table class='table table-thin table-bordered table-striped table-hover table-set'><thead>";
				   tbodyHtml += "<tr><th class='colwidth-8'><div class='font-size-12 line-height-18'>来源编号</div><div class='font-size-9 line-height-18'>Source No.</div></th>"; 
				   tbodyHtml += "<th class='colwidth-3'><div class='font-size-12 line-height-18'>版本</div><div class='font-size-9 line-height-18'>Rev.</div></th>";
				   tbodyHtml += "<th class='colwidth-13'><div class='font-size-12 line-height-18'>飞机机型</div><div class='font-size-9 line-height-18'>A/C Type</div></th>"; 
				   tbodyHtml += "<th class='colwidth-9'><div class='font-size-12 line-height-18'>工作类型</div><div class='font-size-9 line-height-18'>Work Type</div></th>"; 
				   tbodyHtml += "<th class='colwidth-10'><div class='font-size-12 line-height-18'>说明</div><div class='font-size-9 line-height-18'>Description</div></th>"; 
				   tbodyHtml += "<th class='colwidth-5'><div class='font-size-12 line-height-18'>办理期限</div><div class='font-size-9 line-height-18'>Deal Date</div></th></tr></thead>";
				   this_.blockUI();
					AjaxUtil.ajax({
					url:basePath+"/sys/custom/block/block9",
					type: "get",
					cache:false,
					dataType:"json",
					success:function(data){
						
						tbodyHtml += "<tbody>";
						if(data && data.rows){
							if(data.rows.length > 0){
								$.each(data.rows, function(index, row){
									tbodyHtml += "<tr>";
									tbodyHtml += "<td title='"+row.LYBH+"'><a href='javascript:void(0);'  dbgzlx=\""+row.DBGZLX+"\"  bizId=\""+row.LYID+"\" >"+row.LYBH+"</a></td>";
									tbodyHtml += "<td align='center' title='"+(row.BB==null?"":"R"+StringUtil.escapeStr(row.BB))+"'>"+(row.BB==null?"":"R"+StringUtil.escapeStr(row.BB))+"</td>";
									tbodyHtml += "<td title='"+StringUtil.escapeStr(row.FJJX)+"'>"+StringUtil.escapeStr(row.FJJX)+"</td>";
									var lx = DicAndEnumUtil.getEnumName('todoEnum',row.DBGZLX);
								    tbodyHtml += "<td title='"+lx+"'>"+lx+"</td>";  
									tbodyHtml += "<td title='"+StringUtil.escapeStr(row.SM)+"'>"+StringUtil.escapeStr(row.SM)+"</td>";
									tbodyHtml += "<td title='"+StringUtil.escapeStr(row.BLQX==null?"":row.BLQX.substring(0,10))+"'>"+StringUtil.escapeStr(row.BLQX==null?"":row.BLQX.substring(0,10))+"</td>";
									tbodyHtml += "</tr>";
								})
							}else{
								tbodyHtml += "<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>";
							}
							tbodyHtml += "</tbody></table>";
							this_.getDataContainer().empty();	
							this_.getDataContainer().append(tbodyHtml);
							
							//取消遮罩层效果
							
							this_.unblockUI();
							
							//设置badge
							
							this_.setNumber(data.rows.length == 0 ?'':data.rows.length);
							
							//表格中链接的点击事件
							
							$("a", this_.getDataContainer()).on("click", this_.view);
						}else{
							this_.setNumber('');
							tbodyHtml += "<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>";
							tbodyHtml += "</tbody></table>";
							this_.getDataContainer().html("");
							this_.getDataContainer().html(tbodyHtml);
							
							//取消遮罩层效果
							this_.unblockUI();
						}
				    }
				}); 
			},
			//查看链接的事件
			view: function(e){
				var dbgzlx = $(e.target).attr("dbgzlx");
				if(dbgzlx =='9'){
					window.open(panelData[4].singleUrl[0] + $(e.target).attr("bizId"));
				}else{
					window.open(panelData[4].singleUrl[1] + $(e.target).attr("bizId"));
				}
				
			}
		})
		panelData[4].panel = panel;
	}
	currentPanel = panelData[4].panel;
}else{
	if(panelData[4].panel){
		panelData[4].panel.destory(true);
	}
}
	
	
//我的提订单（未完成）
if($.inArray(panelData[1].id, delPanel) == -1 && checkBtnPermissions(panelData[1].permissioncode)){ 
	if(!panelData[1].panel){
		var panel = new PanelBorad({
			id: panelData[1].id,
			title: panelData[1].title,
			beforePanel: currentPanel,
			moreUrl: panelData[1].moreUrl,
			refreshTime:"2018-02-28 17:20:15",
			panelDivClass:"col-lg-6 col-sm-6 col-xs-12 padding-leftright-3",
			tableHead: "",
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
				var tbodyHtml = "<table class='table table-thin table-bordered table-striped table-hover table-set'><thead>";
					tbodyHtml += "<tr>";
					tbodyHtml += "<th class='colwidth-10'><div class='font-size-12 line-height-18'>提订单号</div><div class='font-size-9 line-height-18'>Order No.</div></th>";
					tbodyHtml += "<th class='colwidth-15'><div class='font-size-12 line-height-18'>提订名称</div><div class='font-size-9 line-height-18'>Order Name</div></th>";
					tbodyHtml += "<th class='colwidth-7'><div class='font-size-12 line-height-18'>进度</div><div class='font-size-9 line-height-18'>Progress</div></th>";
					tbodyHtml += "<th class='colwidth-15'><div class='font-size-12 line-height-18'>件号</div><div class='font-size-9 line-height-18'>P/N</div></th>";
					tbodyHtml += "<th class='colwidth-20'><div class='font-size-12 line-height-18'>航材名称</div><div class='font-size-9 line-height-18'>Material Name</div></th>";
					tbodyHtml += "<th class='colwidth-7'><div class='font-size-12 line-height-18'>提定数量</div><div class='font-size-9 line-height-18'>QTY</div></th>";
					tbodyHtml += "<th class='colwidth-7'><div class='font-size-12 line-height-18'>审核数量</div><div class='font-size-9 line-height-18'>QTY</div></th>";
					tbodyHtml += "</tr>";
					tbodyHtml += "</thead>";
					tbodyHtml += "</tbody>";
					this_.blockUI();
				AjaxUtil.ajax({
					url:basePath+"/sys/custom/block/block6",
					type: "get",
					cache:false,
					dataType:"json",
					success:function(data){
						if(data && data.rows){
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
								tbodyHtml += "<tr><td class='text-center' colspan=\"7\">暂无数据 No data.</td></tr>";
							}
							tbodyHtml += "</tbody>";
							tbodyHtml += "</table>";
							this_.getDataContainer().html(tbodyHtml);
							this_.unblockUI();
							this_.setNumber(data.rows.length == 0 ?'':data.rows.length);
							$("a", this_.getDataContainer()).on("click", this_.view);
						}else{
							this_.setNumber('');
							tbodyHtml += "<tr><td class='text-center' colspan=\"7\">暂无数据 No data.</td></tr>";
							tbodyHtml += "</tbody>";
							tbodyHtml += "</table>";
							this_.getDataContainer().html(tbodyHtml);
							this_.unblockUI();
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
	
	
	
//个人执照/课程到期提醒

if($.inArray(panelData[9].id, delPanel) == -1 && checkBtnPermissions(panelData[9].permissioncode)){ 
	if(!panelData[9].panel){
		var panel = new PanelBorad({
			id: panelData[9].id,
			title: panelData[9].title,
			beforePanel: currentPanel,
			buttons :['refresh',  'close'],
			refreshTime:"2018-02-28 17:20:15",
			panelDivClass:"col-lg-6 col-sm-6 col-xs-12 padding-leftright-3",
			tableHead: "",
			removeCallback: function(flag){
				panelData[9].panel = null;
				if(flag !== true){
					delPanel.push(panelData[9].id);
					//往后台存入删除的面板
					removePanel(panelData[9].id);
				}
			},
			load: function(){
				var xxlx={
						1:'授权',
						21:'维修执照',
						22:'机型执照',
						23:'课程'
				};
				var this_ = this;
				
				var tbodyHtml = "<table class='table table-thin table-bordered table-striped table-hover table-set'><thead>";
					tbodyHtml += '<tr>'
					tbodyHtml += '<th class="colwidth-6">'
					tbodyHtml += '<div class="font-size-12 line-height-18">类型</div>'
					tbodyHtml += '<div class="font-size-9 line-height-18">Type</div>'
					tbodyHtml += '</th>'
					tbodyHtml += '<th class="colwidth-20">'
					tbodyHtml += '<div class="font-size-12 line-height-18">描述</div>'
					tbodyHtml += '<div class="font-size-9 line-height-18">Instruction</div>'
					tbodyHtml += '</th>'
					tbodyHtml += '<th class="colwidth-8">'
					tbodyHtml += '<div class="font-size-12 line-height-18">有效期限</div>'
					tbodyHtml += '<div class="font-size-9 line-height-18">Expiration date</div>'
					tbodyHtml += '</th>'
					tbodyHtml += '</tr>'
					tbodyHtml += '</thead>'
					tbodyHtml += '<tbody>'
					
					this_.blockUI();
					AjaxUtil.ajax({
						url:basePath+"/sys/custom/block/block14",
						type: "get",
						cache:false,
						dataType:"json",
						success:function(data){
							if(data && data.rows){
								if(data.rows.length > 0){
									$.each(data.rows, function(index, row){
										
										tbodyHtml += "<tr>";
										
										tbodyHtml += "<td title='"+StringUtil.escapeStr(xxlx[row.XXLX])+"'>"+StringUtil.escapeStr(xxlx[row.XXLX])+"</td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.MS)+"'>"+StringUtil.escapeStr(row.MS)+"</td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.DQRQ)+"'>"+StringUtil.escapeStr(row.DQRQ)+"</td>";
										tbodyHtml += "</tr>";
									})
								}else{
									tbodyHtml += "<tr><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>"
								}
								tbodyHtml += '</tbody>'
								tbodyHtml += '</table>'
								this_.getDataContainer().html(tbodyHtml);
								this_.unblockUI();
								this_.setNumber(data.rows.length == 0 ?'':data.rows.length);
							}else{
								this_.setNumber('');
								tbodyHtml += "<tr><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>";
								this_.getDataContainer().html(tbodyHtml);
								this_.unblockUI();
							}
							}
						}) 
			}
		});
	panelData[9].panel = panel;
	}
	currentPanel = panelData[9].panel;
}else{
	if(panelData[9].panel){
		panelData[9].panel.destory(true);
	}
}
			
//执照到期提醒


if($.inArray(panelData[10].id, delPanel) == -1 && checkBtnPermissions(panelData[10].permissioncode)){ 
	if(!panelData[10].panel){
		var panel = new PanelBorad({
			id: panelData[10].id,
			title: panelData[10].title,
			beforePanel: currentPanel,
			buttons:['refresh', 'close'] ,
			refreshTime:"2018-02-28 17:20:15",
			panelDivClass:"col-lg-6 col-sm-6 col-xs-12 padding-leftright-3",
			tableHead: "",
			removeCallback: function(flag){
				panelData[10].panel = null;
				if(flag !== true){
					delPanel.push(panelData[10].id);
					//往后台存入删除的面板
					removePanel(panelData[10].id);
				}
			},
			load: function(){
				var xxlx={
						1:'授权',
						21:'维修执照',
						22:'机型执照',
						23:'课程'
				};
				var this_ = this;
				
				var tbodyHtml = "<table class='table table-thin table-bordered table-striped table-hover table-set'><thead>";
					tbodyHtml += '<tr>'
					tbodyHtml += '<th class="colwidth-6">'
					tbodyHtml += '<div class="font-size-12 line-height-18">类型</div>'
					tbodyHtml += '<div class="font-size-9 line-height-18">Type</div>'
					tbodyHtml += '</th>'
					tbodyHtml += '<th class="colwidth-13">'
					tbodyHtml += '<div class="font-size-12 line-height-18">人员</div>'
					tbodyHtml += '<div class="font-size-9 line-height-18">Personnel</div>'
					tbodyHtml += '</th>'
					tbodyHtml += '<th class="colwidth-13">'
					tbodyHtml += '<div class="font-size-12 line-height-18">机型</div>'
					tbodyHtml += '<div class="font-size-9 line-height-18">Model</div>'
					tbodyHtml += '</th>'
					tbodyHtml += '<th class="colwidth-13">'
					tbodyHtml += '<div class="font-size-12 line-height-18">专业</div>'
					tbodyHtml += '<div class="font-size-9 line-height-18">Major</div>'
					tbodyHtml += '</th>'
					tbodyHtml += '<th class="colwidth-13">'
					tbodyHtml += '<div class="font-size-12 line-height-18">执照号</div>'
					tbodyHtml += '<div class="font-size-9 line-height-18">Licence NO</div>'
					tbodyHtml += '</th>'
					tbodyHtml += '<th class="colwidth-13">'
					tbodyHtml += '<div class="font-size-12 line-height-18">有效期</div>'
					tbodyHtml += '<div class="font-size-9 line-height-18">Validity</div>'
					tbodyHtml += '</th>'
					tbodyHtml += '</tr>'
					tbodyHtml += '</thead>'
					tbodyHtml += '<tbody>'
					
					this_.blockUI();
					AjaxUtil.ajax({
						url:basePath+"/sys/custom/block/block15",
						type: "get",
						cache:false,
						dataType:"json",
						success:function(data){
							if(data && data.rows){
								if(data.rows.length > 0){
									$.each(data.rows, function(index, row){
										
										tbodyHtml += "<tr>";
										
										tbodyHtml += "<td title='"+StringUtil.escapeStr(xxlx[row.XXLX])+"'>"+StringUtil.escapeStr(xxlx[row.XXLX])+"</td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.RY)+"'>"+StringUtil.escapeStr(row.RY)+"</td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.FJJX)+"'>"+StringUtil.escapeStr(row.FJJX)+"</td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.ZY)+"'>"+StringUtil.escapeStr(row.ZY)+"</td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.ZJBH)+"'>"+StringUtil.escapeStr(row.ZJBH)+"</td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.DQRQ)+"'>"+StringUtil.escapeStr(row.DQRQ)+"</td>";
										tbodyHtml += "</tr>";
									})
								}else{
									tbodyHtml += "<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>"
								}
								tbodyHtml += '</tbody>'
								tbodyHtml += '</table>'
								this_.getDataContainer().html(tbodyHtml);
								this_.unblockUI();
								this_.setNumber(data.rows.length == 0 ?'':data.rows.length);
							}else{
								this_.setNumber('');
								tbodyHtml += "<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>";
								this_.getDataContainer().html(tbodyHtml);
								this_.unblockUI();
							}
							}
						})
			}
		});
	panelData[10].panel = panel;
	}
	currentPanel = panelData[10].panel;
}else{
	if(panelData[10].panel){
		panelData[10].panel.destory(true);
	}
}	
					
//工具/设备校验提醒

if($.inArray(panelData[11].id, delPanel) == -1 && checkBtnPermissions(panelData[11].permissioncode)){ 
	if(!panelData[11].panel){
		var panel = new PanelBorad({
			id: panelData[11].id,
			title: panelData[11].title,
			beforePanel: currentPanel,
			moreUrl: panelData[11].moreUrl+"?dprtcode="+userJgdm+"&yjbs=1",
			refreshTime:"2018-02-28 17:20:15",
			panelDivClass:"col-lg-6 col-sm-6 col-xs-12 padding-leftright-3",
			tableHead: "",
			removeCallback: function(flag){
				panelData[11].panel = null;
				if(flag !== true){
					delPanel.push(panelData[11].id);
					//往后台存入删除的面板
					removePanel(panelData[11].id);
				}
			},
			load: function(){
				var this_ = this;
				
				var tbodyHtml = "<table class='table table-thin table-bordered table-striped table-hover table-set'><thead>";
					tbodyHtml += '<tr>'
					tbodyHtml += '<th class="colwidth-6">'
					tbodyHtml += '<div class="font-size-12 line-height-18">编号</div>'
					tbodyHtml += '<div class="font-size-9 line-height-18">Type</div>'
					tbodyHtml += '</th>'
					tbodyHtml += '<th class="colwidth-20">'
					tbodyHtml += '<div class="font-size-12 line-height-18">工具/设备名称</div>'
					tbodyHtml += '<div class="font-size-9 line-height-18">Name</div>'
					tbodyHtml += '</th>'
					tbodyHtml += '<th class="colwidth-13">'
					tbodyHtml += '<div class="font-size-12 line-height-18">校验日期</div>'
					tbodyHtml += '<div class="font-size-9 line-height-18">Date</div>'
					tbodyHtml += '</th>'
					tbodyHtml += '</tr>'
					tbodyHtml += '</thead>'
					tbodyHtml += '<tbody>'
					
					this_.blockUI();
					AjaxUtil.ajax({
						url:basePath+"/sys/custom/block/block16",
						type: "get",
						cache:false,
						dataType:"json",
						success:function(data){
							if(data && data.rows){
								if(data.rows.length > 0){
									$.each(data.rows, function(index, row){
										
										tbodyHtml += "<tr>";
										
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.GJBH)+"'><a href='javascript:void(0);'   gjsbid='"+row.ID+"'>"+StringUtil.escapeStr(row.GJBH)+"</a></td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.GJMC)+"'>"+StringUtil.escapeStr(row.GJMC)+"</td>";
										tbodyHtml += "<td class='text-center' title='"+indexOfTime(row.JYRQ)+"'>"+indexOfTime(row.JYRQ)+"</td>";
										tbodyHtml += "</tr>";
									})
								}else{
									tbodyHtml += "<tr><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>"
								}
								tbodyHtml += '</tbody>'
								tbodyHtml += '</table>'
								this_.getDataContainer().html(tbodyHtml);
								this_.unblockUI();
								this_.setNumber(data.rows.length == 0 ?'':data.rows.length);
								$("a", this_.getDataContainer()).on("click", this_.view);
							}else{
								this_.setNumber('');
								tbodyHtml += "<tr><td class='text-center' colspan=\"3\">暂无数据 No data.</td></tr>";
								this_.getDataContainer().html(tbodyHtml);
								this_.unblockUI();
							}
							}
						})
			},
			view: function(e){
				window.open(panelData[11].singleUrl + $(e.target).attr("gjsbid"));
			}
		});
		panelData[11].panel = panel;
	}
	currentPanel = panelData[11].panel;
}else{
	if(panelData[11].panel){
		panelData[11].panel.destory(true);
	}
}
							
//人员培训提醒

if($.inArray(panelData[12].id, delPanel) == -1 && checkBtnPermissions(panelData[12].permissioncode)){ 
	if(!panelData[12].panel){
		var panel = new PanelBorad({
			id: panelData[12].id,
			title: panelData[12].title,
			beforePanel: currentPanel,
			moreUrl: panelData[12].moreUrl,
			buttons:['refresh', 'close'],
			refreshTime:"2018-02-28 17:20:15",
			panelDivClass:"col-lg-6 col-sm-6 col-xs-12 padding-leftright-3",
			tableHead: "",
			removeCallback: function(flag){
				panelData[12].panel = null;
				if(flag !== true){
					delPanel.push(panelData[12].id);
					//往后台存入删除的面板
					removePanel(panelData[12].id);
				}
			},
			load: function(){
				var this_ = this;
				
				var tbodyHtml = "<table class='table table-thin table-bordered table-striped table-hover table-set'><thead>";
					tbodyHtml += '<tr>'
					tbodyHtml += '<th class="colwidth-6">'
					tbodyHtml += '<div class="font-size-12 line-height-18">人员</div>'
					tbodyHtml += '<div class="font-size-9 line-height-18">Personnel</div>'
					tbodyHtml += '</th>'
					tbodyHtml += '<th class="colwidth-13">'
					tbodyHtml += '<div class="font-size-12 line-height-18">课程编号</div>'
					tbodyHtml += '<div class="font-size-9 line-height-18">Course NO</div>'
					tbodyHtml += '</th>'
					tbodyHtml += '<th class="colwidth-13">'
					tbodyHtml += '<div class="font-size-12 line-height-18">课程名称</div>'
					tbodyHtml += '<div class="font-size-9 line-height-18">Name</div>'
					tbodyHtml += '</th>'
					tbodyHtml += '<th class="colwidth-8">'
					tbodyHtml += '<div class="font-size-12 line-height-18">A/C</div>'
					tbodyHtml += '<div class="font-size-9 line-height-18">A/C</div>'
					tbodyHtml += '</th>'
					tbodyHtml += '<th class="colwidth-8">'
					tbodyHtml += '<div class="font-size-12 line-height-18">下次培训日期</div>'
					tbodyHtml += '<div class="font-size-9 line-height-18">Date</div>'
					tbodyHtml += '</th>'
					tbodyHtml += '</tr>'
					tbodyHtml += '</thead>'
					tbodyHtml += '<tbody>'
						
					this_.blockUI();
					AjaxUtil.ajax({
						url:basePath+"/sys/custom/block/block17",
						type: "get",
						cache:false,
						dataType:"json",
						success:function(data){
							if(data && data.rows){
								if(data.rows.length > 0){
									$.each(data.rows, function(index, row){
										
										tbodyHtml += "<tr>";
										
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.RY)+"'>"+StringUtil.escapeStr(row.RY)+"</td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.KCBH)+"'>"+StringUtil.escapeStr(row.KCBH)+"</td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.KCMC)+"'>"+StringUtil.escapeStr(row.KCMC)+"</td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.FJJX)+"'>"+StringUtil.escapeStr(row.FJJX)+"</td>";
										tbodyHtml += "<td title='"+StringUtil.escapeStr(row.DQRQ)+"'>"+StringUtil.escapeStr(row.DQRQ)+"</td>";
										tbodyHtml += "</tr>";
									})
								}else{
									tbodyHtml += "<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>"
								}
								tbodyHtml += '</tbody>'
								tbodyHtml += '</table>'
								this_.getDataContainer().html(tbodyHtml);
								this_.unblockUI();
								this_.setNumber(data.rows.length == 0 ?'':data.rows.length);
							}else{
								this_.setNumber('');
								tbodyHtml += "<tr><td class='text-center' colspan=\"5\">暂无数据 No data.</td></tr>";
								this_.getDataContainer().html(tbodyHtml);
								this_.unblockUI();
							}
							
							}
						})
			}
		});
	panelData[12].panel = panel;
	}
	currentPanel = panelData[12].panel;
}else{
	if(panelData[12].panel){
		panelData[12].panel.destory(true);
	}
}
									
//MCC135工单预警（完工未关闭）
if($.inArray(panelData[2].id, delPanel) == -1 && checkBtnPermissions(panelData[2].permissioncode)){ 
	if(!panelData[2].panel){
		var panel = new PanelBorad({
			id: panelData[2].id,
			title: panelData[2].title,
			beforePanel: currentPanel,
			moreUrl: panelData[2].moreUrl+"?dprtcode="+userJgdm+"&gdzt=7&wgbs=1",
			refreshTime:"2018-02-28 17:20:15",
			panelDivClass:"col-lg-6 col-sm-6 col-xs-12 padding-leftright-3",
			tableHead: "",
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
				var tbodyHtml = "<table class='table table-thin table-bordered table-striped table-hover table-set'><thead>";
					tbodyHtml += "<tr>";
					tbodyHtml += "<th class='colwidth-13'><div class='font-size-12 line-height-18'>任务单号</div><div class='font-size-9 line-height-18'>Task No</div></th>";
					tbodyHtml += "<th class='colwidth-10'><div class='font-size-12 line-height-18'>任务类型</div><div class='font-size-9 line-height-18'>Task Type</div></th>";
					tbodyHtml += "<th class='colwidth-15'><div class='font-size-12 line-height-18'>任务信息</div><div class='font-size-9 line-height-18'>Task Information</div></th>";
					tbodyHtml += "<th class='colwidth-12'><div class='font-size-12 line-height-18'>飞机注册号</div><div class='font-size-9 line-height-18'>A/C REG</div></th>";
					tbodyHtml += "<th class='colwidth-7'><div class='font-size-12 line-height-18'>部件号</div><div class='font-size-9 line-height-18'>P/N</div></th>";
					tbodyHtml += "<th class='colwidth-7'><div class='font-size-12 line-height-18'>序列号</div><div class='font-size-9 line-height-18'>S/N</div></th>";
					tbodyHtml += "</tr>";
					tbodyHtml += "<tbody>";
					this_.blockUI();
				AjaxUtil.ajax({
					url:basePath+"/sys/custom/block/block7",
					type: "get",
					cache:false,
					dataType:"json",
					success:function(data){
						if(data && data.rows){
							if(data.rows.length > 0){
								$.each(data.rows, function(index, row){
									tbodyHtml += "<tr>";
									tbodyHtml += "<td align='center' title='"+row.GDBH+"'><a href='javascript:void(0);' bizId=\""+row.ID+"\" >"+row.GDBH+"</a></td>";
								    tbodyHtml += "<td title='"+DicAndEnumUtil.getEnumName('workorderTypeEnum',row.GDLX)+"'>"+DicAndEnumUtil.getEnumName('workorderTypeEnum',row.GDLX)+"</td>";  
									tbodyHtml += "<td title='"+StringUtil.escapeStr(row.GDBT)+"'>"+StringUtil.escapeStr(row.GDBT)+"</td>";
									tbodyHtml += "<td title='"+StringUtil.escapeStr(row.FJZCH)+"'>"+StringUtil.escapeStr(row.FJZCH)+"</td>";
									tbodyHtml += "<td title='"+StringUtil.escapeStr(row.BJH)+"'>"+StringUtil.escapeStr(row.BJH)+"</td>";
									tbodyHtml += "<td title='"+StringUtil.escapeStr(row.XLH)+"'>"+StringUtil.escapeStr(row.XLH)+"</td>";
									tbodyHtml += "</tr>";
								})
							}else{
								tbodyHtml  += "<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>";
							}
							tbodyHtml  +="</tbody>";
							tbodyHtml  +="</table>";
							this_.getDataContainer().html(tbodyHtml);
							this_.unblockUI();
							this_.setNumber(data.rows.length == 0 ?'':data.rows.length);
							
							$("a", this_.getDataContainer()).on("click", this_.view);
						}else{
							this_.setNumber('');
							tbodyHtml  += "<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>";
							tbodyHtml  += "</tbody>";
							tbodyHtml  += "</table>";
							this_.getDataContainer().html(tbodyHtml);
							this_.unblockUI();
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
		var panel = new PanelBorad({
			id: panelData[3].id,
			title: panelData[3].title,
			beforePanel: currentPanel,
			moreUrl: panelData[3].moreUrl+"?dprtcode="+userJgdm+"&gdzt=7&wgbs=1",
			refreshTime:"2018-02-28 17:20:15",
			panelDivClass:"col-lg-6 col-sm-6 col-xs-12 padding-leftright-3",
			tableHead: "",
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
				var tbodyHtml = "<table class='table table-thin table-bordered table-striped table-hover table-set'><thead>";
					tbodyHtml += "<tr>";
					tbodyHtml += "<th class='colwidth-12'><div class='font-size-12 line-height-18'>任务单号</div><div class='font-size-9 line-height-18'>Task No</div></th>";
					tbodyHtml += "<th class='colwidth-10'><div class='font-size-12 line-height-18'>任务类型</div><div class='font-size-9 line-height-18'>Task Type</div></th>";
					tbodyHtml += "<th class='colwidth-15'><div class='font-size-12 line-height-18'>任务信息</div><div class='font-size-9 line-height-18'>Task Information</div></th>";
					tbodyHtml += "<th class='colwidth-12'><div class='font-size-12 line-height-18'>飞机注册号</div><div class='font-size-9 line-height-18'>A/C REG</div></th>";
					tbodyHtml += "<th class='colwidth-7'><div class='font-size-12 line-height-18'>部件号</div><div class='font-size-9 line-height-18'>P/N</div></th>";
					tbodyHtml += "<th class='colwidth-7'><div class='font-size-12 line-height-18'>序列号</div><div class='font-size-9 line-height-18'>S/N</div></th>";
					tbodyHtml += "</tr>";
					tbodyHtml += "</thead>";
					tbodyHtml += "<tbody>";
					this_.blockUI();
				AjaxUtil.ajax({
					url:basePath+"/sys/custom/block/block8",
					type: "get",
					cache:false,
					dataType:"json",
					success:function(data){
						if(data && data.rows){
							if(data.rows.length > 0){
								$.each(data.rows, function(index, row){
									tbodyHtml += "<tr>";
									tbodyHtml += "<td align='center' title='"+row.GDBH+"'><a href='javascript:void(0);' bizId=\""+row.ID+"\" >"+row.GDBH+"</a></td>";
								    tbodyHtml += "<td title='"+DicAndEnumUtil.getEnumName('workorderTypeEnum',row.GDLX)+"'>"+DicAndEnumUtil.getEnumName('workorderTypeEnum',row.GDLX)+"</td>";  
									tbodyHtml += "<td title='"+StringUtil.escapeStr(row.GDBT)+"'>"+StringUtil.escapeStr(row.GDBT)+"</td>";
									tbodyHtml += "<td title='"+StringUtil.escapeStr(row.FJZCH)+"'>"+StringUtil.escapeStr(row.FJZCH)+"</td>";
									tbodyHtml += "<td title='"+StringUtil.escapeStr(row.BJH)+"'>"+StringUtil.escapeStr(row.BJH)+"</td>";
									tbodyHtml += "<td title='"+StringUtil.escapeStr(row.XLH)+"'>"+StringUtil.escapeStr(row.XLH)+"</td>";
									tbodyHtml += "</tr>";
								})
							}else{
								tbodyHtml += "<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>";
							}
							
							tbodyHtml  += "</tbody>";
							tbodyHtml  += "</table>";
							this_.getDataContainer().html(tbodyHtml);
							this_.unblockUI();
							this_.setNumber(data.rows.length == 0 ?'':data.rows.length);
							
							$("a", this_.getDataContainer()).on("click", this_.view);
						}else{
							this_.setNumber('');
							tbodyHtml += "<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>";
							tbodyHtml  += "</tbody>";
							tbodyHtml  += "</table>";
							
							this_.getDataContainer().html(tbodyHtml);
							this_.unblockUI();
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

//质量审核通知

if($.inArray(panelData[13].id, delPanel) == -1 && checkBtnPermissions(panelData[13].permissioncode)){ 
	if(!panelData[13].panel){
		var panel = new PanelBorad({
			id: panelData[13].id,
			title: panelData[13].title,
			beforePanel: currentPanel,
			moreUrl: panelData[13].moreUrl+"?dprtcode="+userJgdm+"&gdzt=7&wgbs=1",
			refreshTime:"2018-02-28 17:20:15",
			panelDivClass:"col-lg-6 col-sm-6 col-xs-12 padding-leftright-3",
			tableHead: "",
			removeCallback: function(flag){
				panelData[13].panel = null;
				if(flag !== true){
					delPanel.push(panelData[13].id);
					//往后台存入删除的面板
					removePanel(panelData[13].id);
				}
			},
			load: function(){
				var this_ = this;
				var tbodyHtml = "<table class='table table-thin table-bordered table-striped table-hover table-set'><thead>";
					tbodyHtml += "<tr>";
					tbodyHtml += "<th class='colwidth-12'><div class='font-size-12 line-height-18'>审核日期</div><div class='font-size-9 line-height-18'>Task No</div></th>";
					tbodyHtml += "<th class='colwidth-10'><div class='font-size-12 line-height-18'>检查内容</div><div class='font-size-9 line-height-18'>Task Type</div></th>";
					tbodyHtml += "<th class='colwidth-15'><div class='font-size-12 line-height-18'>审核类别</div><div class='font-size-9 line-height-18'>Task Information</div></th>";
					tbodyHtml += "<th class='colwidth-12'><div class='font-size-12 line-height-18'>检查单号</div><div class='font-size-9 line-height-18'>A/C REG</div></th>";
					tbodyHtml += "</tr>";
					tbodyHtml += "</thead>";
					tbodyHtml += "<tbody>";
					this_.blockUI();
				AjaxUtil.ajax({
					url:basePath+"/sys/custom/block/block18",
					type: "get",
					cache:false,
					dataType:"json",
					success:function(data){
						if(data && data.rows){
							if(data.rows.length > 0){
								$.each(data.rows, function(index, row){
									var shlb=StringUtil.escapeStr(DicAndEnumUtil.getEnumName('auditnoticeTyepEnum',row.SHLB));
									tbodyHtml += "<tr>";
									tbodyHtml += "<td title='"+indexOfTime(row.JH_SHRQ)+"'>"+indexOfTime(row.JH_SHRQ)+"</td>";
									tbodyHtml += "<td title='"+StringUtil.escapeStr(row.JCNR)+"'>"+StringUtil.escapeStr(row.JCNR)+"</td>";
									tbodyHtml += "<td title='"+shlb+"'>"+shlb+"</td>";
									tbodyHtml += "<td title='"+StringUtil.escapeStr(row.JCDBH)+"'><a href='javascript:void(0);' rowId='"+row.ID+"'>"+StringUtil.escapeStr(row.JCDBH)+"</a></td>";
									tbodyHtml += "</tr>";
								})
							}else{
								tbodyHtml += "<tr><td class='text-center' colspan=\"4\">暂无数据 No data.</td></tr>";
							}
							
							tbodyHtml  += "</tbody>";
							tbodyHtml  += "</table>";
							this_.getDataContainer().html(tbodyHtml);
							this_.unblockUI();
							this_.setNumber(data.rows.length == 0 ?'':data.rows.length);
							
							$("a", this_.getDataContainer()).on("click", this_.view);
						}else{
							this_.setNumber('');
							tbodyHtml += "<tr><td class='text-center' colspan=\"6\">暂无数据 No data.</td></tr>";
							tbodyHtml  += "</tbody>";
							tbodyHtml  += "</table>";
							
							this_.getDataContainer().html(tbodyHtml);
							this_.unblockUI();
						}
				    }
				}); 
			},
			view: function(e){
				var rowId=$(e.target).attr("rowId");
				window.open(basePath+"/quality/auditnotice/view?id="+rowId);
			}
		});
		panelData[13].panel = panel;
	}
	currentPanel = panelData[13].panel;
}else{
	if(panelData[13].panel){
		panelData[13].panel.destory(true);
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
		width:"814px",
		height:window.innerHeight/1.5,
//		content : "<div><img style='width:100%;height:100%' src='"+basePath+"/static/images/process_model/lct.png'/></div>"// 这里可以直接写字符串，也可以 是一个函数，该函数返回一个字符串；
		content:generateFlowChartHtml()
	});
}

//图片锚点的写法

function getArea(){
	
    var html="";
	//上传适航性资料 
	if(checkMenuPermissions("project2:airworthiness:main")){
		html += '<area shape="rect" coords="47,40,149,78" href="'+basePath+'/project2/airworthiness/main" target="_blank" title="上传适航性资料"/>';
	}else{
		html += '<div class="ltcBg" style="position:absolute;left:47px;top:40px;width:102px;border-radius:5px;height:38px;"></div>';
	}
		
	
	
	//技术评估
	if(checkMenuPermissions("project2:assessment:main")){
		html += '<area shape="rect" coords="159,145,262,183" href="'+basePath+'/project2/assessment/main" target="_blank" title="技术评估" />';
		
	}else{
		html += '<div class="ltcBg" style="position:absolute;;left:159px;top:145px;width:102px;border-radius:5px;height:38px;"></div>';
		
	}
	// 机型维护
	
	if(checkMenuPermissions("project2:actype:main")){
		html += '<area shape="rect" coords="531,40,635,78" href="'+basePath+'/project2/actype/main" target="_blank" title="机型维护" />';
	}else{
		html += '<div class="ltcBg" style="position:absolute;;left:531px;top:40px;width:102px;border-radius:5px;height:38px;"></div>';
	}
	
	
	// 飞机注册
	if(checkMenuPermissions("aircraftinfo:main")){
		html += '<area shape="rect" coords="529,146,633,183" href="'+basePath+'/aircraftinfo/main" target="_blank" title="飞机注册"/>';
		
	}else{
		html += '<div class="ltcBg" style="position:absolute;left:529px;top:146px;width:102px;border-radius:5px;height:38px;"></div>';	
	}
	// FLB维护
	if(checkMenuPermissions("produce:flb:main")){
			html += '<area shape="rect" coords="628,240,733,278" href="'+basePath+'/produce/flb/main" target="_blank" title="FLB维护"/>';
	}else if(!checkMenuPermissions("produce:flb:main")&&checkMenuPermissions("produce:flb2:main")){
		    html += '<area shape="rect" coords="628,240,733,278" href="'+basePath+'/produce/flb2/main" target="_blank" title="FLB维护"/>';
	}else{
		html += '<div class="ltcBg" style="position:absolute;left:628px;top:240px;width:102px;border-radius:5px;height:38px;"></div>';	
	}
	
	
	// 装机清单维护
	if(checkMenuPermissions("aircraftinfo:installationlist:main")){
		html += '<area shape="rect" coords="530,311,633,348" href="'+basePath+'/aircraftinfo/installationlist/main" target="_blank" title="装机清单维护"/>';
	}else{
		html += '<div class="ltcBg" style="position:absolute;left:530px;top:311px;width:102px;border-radius:5px;height:38px;"></div>';
	}
	
	// 工卡创建
	if(checkMenuPermissions("project2:workcard:main")){
		html += '<area shape="rect" coords="353,239,456,278" href="'+basePath+'/project2/workcard/main" target="_blank" title="工卡创建"/>';
	}else{
		html += '<div class="ltcBg" style="position:absolute;left:353px;top:239px;width:102px;border-radius:5px;height:38px;"></div>';
	}
	
	// 下达工程指令
	if(checkMenuPermissions("project2:eo:main")){
		html += '<area shape="rect" coords="340,372,445,410" href="'+basePath+'/project2/eo/main" target="_blank" title="下达工程指令"/>';
	}else{
		html += '<div class="ltcBg" style="position:absolute;left:340px;top:372px;width:102px;border-radius:5px;height:38px;"></div>';
	}
	
	// 维修计划初始化
	if(checkMenuPermissions("produce:maintenance:initialization:main")){
		html += '<area shape="rect" coords="342,575,445,614" href="'+basePath+'/produce/maintenance/initialization/main" target="_blank" title="维修计划初始化"/>';
	}else{
		html += '<div class="ltcBg" style="position:absolute;left:342px;top:575px;width:102px;border-radius:5px;height:38px;"></div>';
	}
	
	// 飞机维修监控
	if(checkMenuPermissions("produce:maintenance:monitoring:main")){
		html += '<area shape="rect" coords="511,575,615,614" href="'+basePath+'/produce/maintenance/monitoring/main" target="_blank" title="飞机维修监控"/>';
	}else{
		html += '<div class="ltcBg" style="position:absolute;left:511px;top:575px;width:102px;border-radius:5px;height:38px;"></div>';
	}
	
	// 工包工单创建
	if(checkMenuPermissions("produce:workpackage:main")){
		html += '<area shape="rect" coords="510,646,615,685" href="'+basePath+'/produce/workpackage/main" target="_blank" title="工包工单创建"/>';
	}else{
		html += '<div class="ltcBg" style="position:absolute;left:510px;top:646px;width:102px;border-radius:5px;height:38px;"></div>';
	}
	
	// 生产确认维修方案
	if(checkMenuPermissions("project2:maintenanceproject:effect:main")){
		html += '<area shape="rect" coords="159,462,263,499" href="'+basePath+'/project2/maintenanceproject/effect/main" target="_blank" title="生产确认维修方案"/>';
	}else{
		html += '<div class="ltcBg" style="position:absolute;left:159px;top:462px;width:102px;border-radius:5px;height:38px;"></div>';
	}
	
	// 维修方案改版
	if(checkMenuPermissions("project2:maintenanceproject:main")){
		html += '<area shape="rect" coords="160,371,263,410" href="'+basePath+'/project2/maintenanceproject/main" target="_blank" title="维修方案改版"/>';
	}else{
		html += '<div class="ltcBg" style="position:absolute;left:160px;top:371px;width:102px;border-radius:5px;height:38px;"></div>';
	}
	
	// 维修提示
	if(checkMenuPermissions("project2:bulletin:main")){
		html += '<area shape="rect" coords="13,372,115,392" href="'+basePath+'/project2/bulletin/main" target="_blank" title="维修提示" />';
	}else{
		html += '<div class="ltcBg" style="position:absolute;left:13px;top:372px;width:102px;border-radius:5px;height:20px;"></div>';
	}
	
	// 技术指令
	if(checkMenuPermissions("project2:instruction:main")){
		html += '<area shape="rect" coords="13,391,116,408" href="'+basePath+'/project2/instruction/main" target="_blank" title="技术指令" />';
	}else{
		html += '<div class="ltcBg" style="position:absolute;left:13px;top:391px;width:102px;border-radius:5px;height:17px;"></div>';
	}
	
	// MEL变更
	if(checkMenuPermissions("project2:mel:main")){
		html += '<area shape="rect" coords="13,407,116,426" href="'+basePath+'/project2/mel/main" target="_blank" title="MEL变更" />';
	}else{
		html += '<div class="ltcBg"  style="position:absolute;left:13px;top:407px;width:102px;border-radius:5px;height:19px;"></div>';
	}
	
	return html;
}
function generateFlowChartHtml(){
	
var html='';
    html += '<div style="position:relative;">'
	html += '<img src="'+basePath+'/static/images/process_model/lct.png"  style="position:absolute;top:0px;left:0px;width:776px;margin:0 auto;" usemap="#Map" border="0" onload="getCoords1()" id="flowChartImg1"/>';
	html += '<map name="Map" id="Map">';
	html += getArea();
	html += '</map>';
	html += '</div>';
	
    return html;
}
function getCoords1(x1,y1,x2,y2){
	var orgWidth = 776;
	var orgHeight = 703;
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

var timer = window.setInterval("refreshPage()",1000*60*5);//60秒钟刷新一次
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