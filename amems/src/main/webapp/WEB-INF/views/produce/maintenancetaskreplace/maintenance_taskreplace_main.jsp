<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>任务替代</title>
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">;
var pageParam = '${param.pageParam}';
var userJgdm='${user.jgdm}';
</script>

<script type="text/javascript">
	$(document).ready(function(){
		//回车事件控制
		$(this).keydown(function(event) {
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
				if(formatUndefine(winId) != ""){
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
				}else{
					initDatasByConditions();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
	<div class="clearfix"></div>
	<div class="page-content table-table-type" >
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body" id="task_replace_main">
			<input id='adjustHeight' type='hidden' value='15'>
				<div class='margin-top-0 searchContent row-height'>					
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">机型</div>
							<div class="font-size-9 ">A/C Type</div>
						</span>
						<div id="jxdiv" class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
							<select id="jx" style="width:120px;"  class="form-control " onchange="initDatas()">
							</select>
						</div>
					</div>
				  <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
					<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
						<input type='radio' name='group_type' value='1' style='vertical-align:text-bottom' onchange="initDatas()" checked="checked"/>&nbsp;按ATA章节&nbsp;&nbsp;
					</label>
					<label class='padding-left-5' style='margin-top:6px;font-weight:normal;'>
						<input type='radio' name='group_type' value='2' style='vertical-align:text-bottom;' onchange="initDatas()"/>&nbsp;按维修项目大类&nbsp;&nbsp;
					</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					
						<label class='padding-left-5' style='margin-top:6px;font-weight:normal;' onchange="initDatas()">
							<input type='checkbox' name='wxxmlx' value='4' style='vertical-align:text-bottom' checked="checked"/>&nbsp;定检包&nbsp;&nbsp;
						</label>
						<label class='padding-left-5' style='margin-top:6px;font-weight:normal;' onchange="initDatas()">
							<input type='checkbox' name='wxxmlx' value='1' style='vertical-align:text-bottom;' checked="checked"/>&nbsp;一般项目&nbsp;&nbsp;
						</label>
						<label class='padding-left-5' style='margin-top:6px;font-weight:normal;' onchange="initDatas()">
							<input type='checkbox' name='wxxmlx' value='2' style='vertical-align:text-bottom;' checked="checked"/>&nbsp;时控项目&nbsp;&nbsp;
						</label>
						<label class='padding-left-5' style='margin-top:6px;font-weight:normal;' onchange="initDatas()">
							<input type='checkbox' name='wxxmlx' value='3' style='vertical-align:text-bottom;' checked="checked"/>&nbsp;时寿项目&nbsp;&nbsp;
				 		</label>					
				</div>
				<div class="col-md-3 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group" style='padding-left:15px;'>
					<div class="col-xs-12 input-group input-group-search">
						<input type="text" placeholder='任务号/参考号/任务描述'  class="form-control" id="keyword_search">
	                    <div class="input-group-addon btn-searchnew" >
	                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="initDatasByConditions();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
	                  		</button>
	                    </div>
	                    <div class="input-group-addon btn-searchnew-more">
		                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  id="btn" onclick="getMore();">
								<div class='input-group'>
									<div class="input-group-addon">
										<div class="font-size-12">更多</div>
										<div class="font-size-9 margin-top-5" >More</div>
									</div>
									<div class="input-group-addon">
									 	&nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
									</div>
								</div>
				   			</button>
	                	</div>
				      </div>
				   </div>	
				   <!-- 更多的搜索框 -->						
				   <div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-bottom-10  display-none border-cccccc" id="divSearch">
		
					<div class="  pull-left col-lg-3 col-md-6 col-sm-6 col-xs-12  padding-right-0 margin-top-0 form-group">
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div id="jxdiv" class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-right-0">
						    <select id="dprtcode" class="form-control " onchange="initDatas(this)">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}"
										<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
									
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="clearfix"></div>
			<div class='table_pagination' style='margin-bottom:8px;'>
				<div id="maintenance_item_table_top_div" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set" c-height="40%"  style="overflow-x: auto;">
					<table  id="scjkyj_table" class="table table-thin table-bordered table-striped table-hover table-set">
						<thead>
							<tr>
								<th class="colwidth-15 downward" id="fjzch_order" onclick="toggleAll()" name="ope_td">
										<div class="important">
										<div class="font-size-12 line-height-18">任务号</div>
										<div class="font-size-9 line-height-18">Task No.</div>
										</div>
								</th>
								<th class="colwidth-6"  id="fjjx_order">
										<div class="font-size-12 line-height-18">版本</div>
										<div class="font-size-9 line-height-18">Rev .</div>
								</th>
								<th class="colwidth-15 important"  id="xlh_order">
										<div class="font-size-12 line-height-18">参考号</div>
										<div class="font-size-9 line-height-18">Ref No.</div>
								</th>
								<th class="colwidth-15 important"  id="bzm_order">
									<div class="font-size-12 line-height-18">任务描述</div>
									<div class="font-size-9 line-height-18">Task Description</div>
								</th>
								<th class="colwidth-15 "  id="jdms_order">
									<div class="font-size-12 line-height-18">适用性</div>
									<div class="font-size-9 line-height-18">Applicability</div>
								</th>
								<th class="colwidth-8 ">
									<div class="font-size-12 line-height-18">维修项目类型</div>
									<div class="font-size-9 line-height-18">Classfiy</div>
								</th>
									
								<th class="colwidth-15">
										<div class="font-size-12 line-height-18">ATA章节号</div>
										<div class="font-size-9 line-height-18">ATA</div>
								</th>									
									
								<th class="colwidth-10" >
										<div class="font-size-12 line-height-18">工卡号</div>
										<div class="font-size-9 line-height-18">Work Card No.</div>
								</th>
								<th class="colwidth-10" >
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization.</div>
								</th>						
								</tr>
							</tr>
						</thead>
						<tbody id="scjkyj_list">
						</tbody>
					</table>
				</div>	
				<div class='clearfix'></div>
			</div>	
			<div  id='bottom_hidden_content' class='displayContent' style='display:none;'>
				    <div id="hideIcon" class="pull-right" style='height:1px;padding-right:8px;'>
						<img src="${ctx}/static/images/down.png" onclick='hideBottom()'  style="width:33px;cursor:pointer;">
					</div>
				    <div  class="pull-right" style='height:1px;padding-right:8px;'>
				          <input type="button" class="btn btn-primary "  value="保存"  onclick='save()'>
					</div>
				    <ul id="myChildTab" class="nav nav-tabs tabNew-style bottom_hidden_Ul" style='padding-top:0px !important;'>
                      	<li class="active" ><a id="personTab" href="#Dropdown" data-toggle="tab" aria-expanded="false" style='border-top:0px;'>任务替代关系</a></li>
                    </ul>
					 <div class="tab-content" style='padding:0px;'>
		                <div class="tab-pane fade active in" id="Dropdown">
		                <div class="panel-body" >
                    	<div id="qdeo_div">
                    	<div class='padding-bottom-8 row-height margin-top-10'>
							<div class="col-lg-4 col-sm-4 col-xs-12  padding-left-0 padding-right-0 form-group">
								<label  class="col-lg-3 col-sm-3 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">取代EO</div>
								</label>
								<div class="col-lg-9 col-sm-9 col-xs-9 padding-leftright-8">
									<div class="input-group">
										<input id="eeobh" class="form-control readonly-style" readonly="" type="text" ondblclick="openEOWin()">
										<input id="eeoid" type="hidden">
										<input id="wxxmid" type="hidden">
										<input id="jx_con" type="hidden">
										<input id="wxfaid" type="hidden">
										<input id="rwh" type="hidden">
					                    <span class="input-group-addon btn btn-default" onclick="openEOWin()" >
					                    	<i class="icon-search cursor-pointer"></i>
					                    </span>
				                	</div>
								</div>
							</div>
							<div class="col-lg-8 col-sm-8 col-xs-12  padding-left-0 padding-right-0 form-group" id="eo_title_div" >
								<label class="col-lg-1 col-sm-1 col-xs-1 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">EO标题</div>
									<div class="font-size-9">Title</div>
								</label>
								<div class="col-lg-11 col-sm-11 col-xs-11 padding-leftright-8" id="">
								<div class="margin-top-10">
									<a href='javascript:void(0);' onclick='viewEO(this)' id="eo_title"></a>
							    </div>
							</div>
						</div>
						 <div class='clearfix'></div>
                    </div>
                   
                    
   						<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0">
							<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">取代维修项目</div>
							</label>
							<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8 tab-table-content" style="overflow-x:auto;">
								<!-- start:table -->
								<table class="table table-bordered table-striped table-thin table-hover text-center table-set" style="min-width: 500px;">
									<thead>
								   		<tr>
								   			<th style="width: 30px;">
												<div class="text-center">
													<button class="line6 line6-btn" onclick="openProjectWinAdd()">
														<i class="icon-plus cursor-pointer color-blue cursor-pointer" ></i>
													</button>
												</div>
											</th>
											<th style="width: 30px;">
												<div class="font-size-12 line-height-12">序号</div>
												<div class="font-size-9 line-height-12">No.</div>
											</th>
											<th class="colwidth-5">
												<div class="font-size-12 line-height-12">版本</div>
												<div class="font-size-9 line-height-12">Rev.</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-12">任务号</div>
												<div class="font-size-9 line-height-12">Task No.</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-12">参考号</div>
												<div class="font-size-9 line-height-12">Ref No.</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-12">适用性</div>
												<div class="font-size-9 line-height-12">Applicability</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-12">参考文件</div>
												<div class="font-size-9 line-height-12">Ref Document</div>
											</th>
											<th class="colwidth-10">
												<div class="font-size-12 line-height-12">工时</div>
												<div class="font-size-9 line-height-12">MHRs</div>
											</th>
								 		 </tr>
									</thead>
									<tbody id="maintenance_project_list">
									</tbody>
								</table>
								<!-- end:table -->
							</div>
							<div class="clearfix"></div>
						</div>
              		</div>
              	</div>
              </div>
            </div>
         </div>
		</div>
	</div>
</div>	
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/maintenancetaskreplace/maintenance_taskreplace_main.js"></script><!--当前js  -->
<%@ include file="/WEB-INF/views/open_win/eo_win.jsp"%><!-------eo -------->
<%@ include file="/WEB-INF/views/open_win/maintenance_project.jsp"%><!-------维修项目对话框 -------->

</body>
</html>