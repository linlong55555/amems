<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>航空维修管理系统</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<link href="${ctx}/static/lib/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/lib/bootstrap-switch/js/bootstrap-switch.min.js"></script>
<link href="${ctx}/static/lib/jCarousel/jcarousel.ajax.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/lib/jCarousel/jcarousel.new.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/lib/jCarousel/jquery.jcarousel.min.js"></script>
<link href="${ctx}/static/lib/webui-popover/jquery.webui-popover.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/lib/webui-popover/jquery.webui-popover.min.js"></script>
<%-- <script type="text/javascript" src="${ctx}/static/lib/superSlide/jquery.SuperSlide.2.1.1.js"></script> --%>
<link href="${ctx}/static/lib/BreakingNews/BreakingNews.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/lib/BreakingNews/BreakingNews.js"></script>
<script src="${ctx}/static/assets/plugins/jquery.blockui.min.js"	type="text/javascript"></script>
<script src="${ctx}/static/js/refresh.js"	type="text/javascript"></script>
<style>
.padding-leftright-3{
padding-left:3px;
padding-right:3px;
}
.padding-leftright-7{
padding-left:7px;
padding-right:7px;
}
.dropdialoghover1{
border:1px solid #e0e0e0;
background:#fff;
padding-top:5px;
padding-bottom:5px;
position: absolute;
width: 110px;
padding-left: 10px;
}
.dropdialoghover1 li{list-style: none; font-size:12px}
.dropdialoghover1 li a{text-decoration: none;list-style: none; color:#333; }
.dropdialoghover1 li a:hover{text-decoration: none;list-style: none; color:#3598db; }
.dropdialoghover1 i{padding-right:3px;}
.ltcBg{
background:#000000;
filter:alpha(opacity=20);   
      -moz-opacity:0.2;   
      -khtml-opacity: 0.2;   
      opacity: 0.2;  
cursor:not-allowed;
}
</style>
</head>
<body class="page-header-fixed">
<input type="hidden" id="ctxInput" value="${ctx}" />
	<div class="clearfix"></div>
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" style="display:none">
			</div>
			<div class="panel-body padding-leftright-7" >
				<div class="col-xs-12 margin-bottom-10 padding-leftright-3" >
				   <div class=" tog_bg">
					<table class="table table-bordered">
						<tr width="100%">
							<td class="top-height" width="630px">
								<div class="jcarousel-wrapper" style="width:578px;">
					                <div id="shortcutDiv" class="jcarousel">
					                    <div class="loading">Loading...</div>
					                </div>
					                <span class="jcarousel-control-add"><i title="快捷功能 Shortcut" onclick="addShortcut();" class="icon-edit"></i></span>
					                <i class="icon-chevron-left jcarousel-control-prev"></i>
					                <i class="icon-chevron-right jcarousel-control-next"></i>
					            </div>
							</td>
							<td bordercolor="#ffffff">
								<div class="BreakingNewsController easing" id="messageDiv">
									<div class="bn-title cursor-pointer"></div>
									<ul id="messageUl">
									</ul>
									<div class="bn-arrows"><span class="bn-arrows-left"></span><span class="bn-arrows-right"></span></div>	
								</div>
							</td>
							<td width="140px">
								<div class="message_group">
									<a id="msgBtn" title="留言 Message"  href="#"><i class="icon-comment-alt pull-left font-size-20"></i><span id="msgCount" class="badge"  style="position: absolute; background:#fff ! important; color:#666; padding: 0px 3px ! important;">0</span></a>
									<a id="flowChartBtn" href="#"><i  title="流程图 Flow Chart" class="icon-sitemap pull-left font-size-20"></i></a>
									<a id="customBtn" title="定制 Customization" href="#"><i class="icon-cog pull-left font-size-20"></i></a>
								</div>
							</td>
						</tr>
					</table>
					</div>	
				</div>
				<div id="portal" class='col-lg-12 padding-left-0 padding-right-0' >
				</div>
				
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	
	<!-- 客制化模块显示 start -->
	
	<div class="modal modal-new" id="customModal" tabindex="-1" role="dialog"  aria-labelledby="customModal" aria-hidden="true" data-keyboard="false" data-backdrop="static">
      <div class="modal-dialog" style='width:45%'>
            <div class="modal-content">
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
		                      <div class="font-size-12">个性定制</div>
							  <div class="font-size-9">Customization</div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
            <div class="modal-body" id="alertModalUserBody">
	            <div class="col-xs-12 margin-top-8 ">
		            <div class="input-group-border">
		        		<div id="customDiv">
		        		</div>
		        		<div class="clearfix"></div>
					</div> 
				</div>
				<div class="clearfix"></div>
            </div>
             <div class="modal-footer">
           		<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
	                    <span class="input-group-addon modalfooterbtn">
	                     <button type="button" onclick="saveCustom();"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							data-dismiss="modal">
							<div class="font-size-12">保存</div>
							<div class="font-size-9">Save</div>
						</button>
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
						</button>
	                    </span>
	               	</div><!-- /input-group -->
				</div>
           
				<div class="clearfix"></div> 
				
			</div>
            </div>
            </div>
            </div>
            
	
	<!-- <div class="modal fade" id="customModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
   
				  	<div class="panel panel-primary margin-top-10">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">个性定制</div>
							<div class="font-size-9 ">Customization</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
			            	<div class="col-lg-12 col-xs-12 padding-top-10">
			            		<div id="customDiv">
			            		</div>
							</div> 
		                	<div class="text-right margin-top-10 margin-bottom-10">
								<button type="button" onclick="saveCustom();"
									class="btn btn-primary padding-top-1 padding-bottom-1"
									data-dismiss="modal">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
								</button>
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>									
			                </div>
			     			<div class="clearfix"></div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
	</div> -->
	<!-- 客制化模块显示 end -->
	
	<!-- 快捷菜单定义 start -->
	<div class="modal fade" id="shortcutModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
				  	<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">快捷功能</div>
							<div class="font-size-9 ">Shortcut</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
			            	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 padding-top-10">
			            		<div id="tab_trade" class="tab-pane active col-xs-5" style="padding:10px;">
									<div class="zTreeDemoBackground">
										<ul id="menuTree" class="ztree" style=" width:600px; height:400px; overflow-y:scroll; "></ul>
									</div>
								</div>
<!-- 			            		<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group"> -->
<!-- 									<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"> -->
<!-- 										<div class="font-size-12 line-height-18">菜单</div> -->
<!-- 										<div class="font-size-9 line-height-18">Menu</div> -->
<!-- 									</label> -->
<!-- 									<div class="col-lg-10 col-sm-10 col-xs-8 padding-left-8 padding-right-0"> -->
<!-- 										<select type="text" class="form-control" name="cdid" id="cdid" ></select> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 			            		<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group"> -->
<!-- 									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"> -->
<!-- 										<div class="font-size-12 line-height-18">中文描述</div> -->
<!-- 										<div class="font-size-9 line-height-18">CH.Name</div> -->
<!-- 									</label> -->
<!-- 									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0"> -->
<!-- 										<input type="text" class="form-control" name="zwms" id="zwms" /> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 								<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group"> -->
<!-- 									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0"> -->
<!-- 										<div class="font-size-12 line-height-18">英文描述</div> -->
<!-- 										<div class="font-size-9 line-height-18">F.Name</div> -->
<!-- 									</label> -->
<!-- 									<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0"> -->
<!-- 										<input type="text" class="form-control" name="ywms" id="ywms" /> -->
<!-- 									</div> -->
<!-- 								</div> -->
							</div> 
		                	<div class="text-right margin-top-10 margin-bottom-10">
								<button type="button" onclick="saveShortcut();"
									class="btn btn-primary padding-top-1 padding-bottom-1"
									data-dismiss="modal">
									<div class="font-size-12">保存</div>
									<div class="font-size-9">Save</div>
								</button>
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>									
			                </div>
			     			<div class="clearfix"></div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 快捷菜单定义 end -->
	<!-- 弹出提示 start -->
	<div class="modal fade" id="popModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false>
		<div class="modal-dialog">
			<div class="modal-content">
<!-- 				<div class="modal-header padding-top-8 padding-bottom-8"  style='border-bottom:none;'>
					<div class="modal-title font-size-16 line-height-18">提醒</div>
					<div class="modal-title font-size-9 ">Remind</div>
				</div> -->
				<div class="modal-body padding-bottom-0 ">
			    	 <div class="modal-header padding-top-0 padding-bottom-8 padding-left-0" style="border-bottom:none;">
					   <div class="modal-title font-size-16 line-height-18">提醒</div>
					   <div class="modal-title font-size-9 ">Remind</div>
				      </div>
				  	<div id="popMessage"  class="panel panel-primary ">
						<div class="panel-heading  padding-top-0 padding-bottom-1">
							<div class="font-size-16 line-height-18">重要通知公告</div>
							<div class="font-size-9 ">Important Notice</div>
						</div>
<!-- 						<div class="panel-body padding-top-0 padding-bottom-0"> -->
							<ul id="popMessageUl" class='list-group padding-left-10 padding-right-10' style='height:200px;overflow:auto;'>
			            	</ul>
<!-- 						</div> -->
					</div>
				  	<div id="popTrain" class="panel panel-primary" style='height:250px;overflow:auto;'>
						<div class="panel-heading">
							<div class="font-size-14 line-height-18">培训计划</div>
							<div class="font-size-9 ">Training Plan</div>
						</div>
						<table class="table table-thin table-bordered table-striped table-hover table-set" >
							<thead>
								<tr><th class="colwidth-13">培训时间</th><th>培训地点</th><th>课程名称</th><th  class="colwidth-7">培训形式</th></tr>
							</thead>
							<tbody id="popTrainTbody">
							</tbody>
						</table>
					</div>
				</div>
				<div class="modal-footer padding-top-8 padding-bottom-8">
			        <div class="text-right">
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
						</button>									
	                </div>
			    </div>
			</div>
		</div>
	</div>
	<!-- 弹出提示 end -->
	
	<!-- 查看通知公告 start -->
	<div class="modal fade" id="messageModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-body padding-bottom-0" id="alertModalUserBody">
				  	<div class="panel panel-primary">
						<div class="panel-heading  padding-top-3 padding-bottom-1">
							<div class="font-size-16 line-height-18">通知公告</div>
							<div class="font-size-9 ">Notice</div>
						</div>
						<div class="panel-body padding-top-0 padding-bottom-0">
			            	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 padding-top-10">
			            		<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
									<label class="col-lg-1 col-sm-1 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">标题</div>
										<div class="font-size-9 line-height-18">Title</div>
									</label>
									<div class="col-lg-11 col-sm-11 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control" name="MESSAGE_BT" id="MESSAGE_BT" readonly/>
									</div>
								</div>
								<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
									<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">发布日期</div>
										<div class="font-size-9 line-height-18">Release date</div>
									</label>
									<div class="col-lg-10 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control" name="MESSAGE_FBSJ" id="MESSAGE_FBSJ" readonly/>
									</div>
								</div>
								<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
									<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">紧急程度</div>
										<div class="font-size-9 line-height-18">Emergency</div>
									</label>
									<div class="col-lg-10 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control" name="MESSAGE_JJD" id="MESSAGE_JJD" readonly/>
									</div>
								</div>
								<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
									<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">有效期-开始</div>
										<div class="font-size-9 line-height-18">Exp.</div>
									</label>
									<div class="col-lg-10 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control" name="MESSAGE_YXQ_KS" id="MESSAGE_YXQ_KS" readonly/>
									</div>
								</div>
								<div class="col-lg-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
									<label class="col-lg-2 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">有效期-结束</div>
										<div class="font-size-9 line-height-18">Exp.</div>
									</label>
									<div class="col-lg-10 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
										<input type="text" class="form-control" name="MESSAGE_YXQ_JS" id="MESSAGE_YXQ_JS" readonly/>
									</div>
								</div>
			            		<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
									<label class="col-lg-1 col-sm-1 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">内容</div>
										<div class="font-size-9 line-height-18">Content</div>
									</label>
									<div class="col-lg-11 col-sm-11 col-xs-8 padding-left-8 padding-right-0">
										<textarea class="form-control" name="MESSAGE_NR" id="MESSAGE_NR" readonly></textarea>
									</div>
								</div>
								
							</div> 
		                	<div class="text-right margin-top-10 margin-bottom-10">
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>									
			                </div> 
			     			<div class="clearfix"></div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 查看通知公告 end -->
	<%-- <script type="text/javascript" src="${ctx}/static/js/thjw/portal/portal.js"></script> --%>
	 
		<script type="text/javascript" src="${ctx}/static/js/thjw/portal/portal_new.js"></script>  
		<script type="text/javascript" src="${ctx}/static/js/thjw/project2/todo/todo_option.js"></script>
	
</body>
</html>