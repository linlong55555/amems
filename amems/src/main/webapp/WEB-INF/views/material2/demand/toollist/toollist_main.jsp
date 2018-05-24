<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>工具需求清单</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	
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
						$('#toollistMainSearch').trigger('click'); //调用主列表页查询
					}
				}
			});
		});
	</script>	
	<script>
	var id = "${param.id}";
	var pageParam = '${param.pageParam}';
	</script>
</head>
<body class="page-header-fixed">
<input type="hidden" id="dprtId" value="${user.jgdm}" />
<input type="hidden" id="userId" name="userId" value="${user.id}" />
<input type="hidden" id="userType" value="${user.userType}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
		<!-- BEGIN CONTENT -->
<div class="page-content">
	<div class="panel panel-primary">
		<div class="panel-heading  ">
			<div id="NavigationBar"></div>
		</div>
		<div class=" panel-body padding-bottom-0" >
			<div class='searchContent margin-top-0 row-height' >
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<div class="btn-group">
						    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" style='height:34px;' onclick='processModal()'>
						    	<div class="font-size-12" style=''>批量修改状态</div>
								<div class="font-size-9 " style='margin-top:0px;'>Batch Edit</div>
                            </button>
						    <button type="button" class="btn btn-primary dropdown-toggle dropdown-toggle-new" data-toggle="dropdown" >
						       <i class="font-size-15 icon-caret-down" id="icon"></i>
						    </button>
						    <ul class="dropdown-menu text-left dropdown-toggle-menu-new" style=''>
								<li><a href="JavaScript:analysisView()" >需求统计分析</a></li>
								<li><a href="javaScript:editContract()">编制合同</a></li>
								<li><a href="JavaScript:changeConfirm()">变更确认</a></li>
								<li><a href="JavaScript:exportExcel()">导出</a></li>
							</ul>
						</div> 
                    </div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-3 col-md-3 col-sm-3 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">飞机注册号</div>
							<div class="font-size-9">A/C Reg</div>
						</span>
						<div class="col-lg-9 col-md-9 col-sm-9 col-xs-11 padding-left-8 padding-right-0" >
							<select id="fjzch_search" class="form-control " onchange="" >
							<option value="" selected="selected">显示全部All</option>
							</select>
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
					    <div class='text-right'>
						<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
								<input id="bzzt_search1" checked="checked" style="vertical-align:text-bottom;" type="checkbox">&nbsp;待处理&nbsp;&nbsp;
						</label>
						<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
								<input id="bzzt_search2" checked="checked" style="vertical-align:text-bottom;" type="checkbox">&nbsp;处理中&nbsp;&nbsp;
						</label>
						<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
								<input id="bzzt_search3" style="vertical-align:text-bottom;" type="checkbox">&nbsp;完成&nbsp;&nbsp;
						</label>
						<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
								<input id="qrbs_search" checked="checked" style="vertical-align:text-bottom;" type="checkbox">&nbsp;变更&nbsp;&nbsp;
						</label>
						</div>
					</div>
					<!-- 搜索框start -->
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group pull-right" style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='需求单编号/需求原因/故障备注'  class="form-control" id="keyword_search" >
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button id="toollistMainSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="materiallistMain.reload();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                    </div>
		                    <div class="input-group-addon btn-searchnew-more">
			                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1"  id="btn" onclick="moreSearch();">
								<div class='input-group input-group-search'>
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
					<!-- 搜索框end -->
				  <div class='clearfix'></div>
			
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">需求类别</div>
							<div class="font-size-9">Type</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select class='form-control' id="xqlb_search">
							</select>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">部件</div>
							<div class="font-size-9">PN</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' id='bj_search' placeholder='部件编号/名称' class='form-control'/>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">提报人</div>
							<div class="font-size-9">Journalist</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' id='tbr_search' placeholder='编号/姓名' class='form-control'/>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id="dprtcode_search"  name="dprtcode" onchange="dprtChange()">
								<c:forEach items="${accessDepartment}" var="type" >
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">计划使用日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" readonly id="jhsyrq_search" />
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">序列号</div>
							<div class="font-size-9">SN</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type='text' id='xlh_search' class='form-control'/>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">影响放行</div>
							<div class="font-size-9">Release</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id="isYxfx_search">
								<option value="" selected="selected">显示全部All</option>
								<option value = "0" >是</option>
								<option value = "1" >否</option>
							</select>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">非计划停场</div>
							<div class="font-size-9">Unplanned parking</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id="isFjhtc_search">
								<option value="" selected="selected">显示全部All</option>
								<option value = "0" >是</option>
								<option value = "1" >否</option>
							</select>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">紧急</div>
							<div class="font-size-9">Urgent</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select class='form-control' id="jjcd_search" >
								<option value="" selected="selected">显示全部All</option>
								<option value = "1" >一般</option>
								<option value = "9" >紧急</option>
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
				</div>
				<div class='clearfix'></div>
				</div>
		
		   <!-- 需求追踪 -->
		   <div  class='table_pagination'>
		   <div id="work_card_main_table_top_div" class="modal-body col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set" style="overflow-x: auto;">
					<table id="meteriallist_table" class="table table-thin table-bordered table-striped table-hover table-set" style="">
						<thead>                              
							<tr>
								<th class="viewCol fixed-column colwidth-7 selectAllImg">
									<a href="#" onclick="selectAllBtn()" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
									<a href="#" class="margin-left-5" onclick="clearSelectAllBtn()" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
								</th>
								<th class='colwidth-5 sorting' onclick="orderBy('xqbs')" id="xqbs_order">
									<div class="font-size-12 line-height-18" >变更</div>
									<div class="font-size-9 line-height-18">Change</div>
								</th>
								<th class='colwidth-10 sorting' onclick="orderBy('qrbs')" id="qrbs_order">
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">Status</div>
								</th>
								<th class='colwidth-7 sorting' onclick="orderBy('xqlb')" id="xqlb_order">
										<div class="font-size-12 line-height-18">需求类别</div>
										<div class="font-size-9 line-height-18">Category</div>
								</th>
								<th class='colwidth-7 sorting' onclick="orderBy('fjzch')" id="fjzch_order">
									<div class="font-size-12 line-height-18">飞机注册号</div>
									<div class="font-size-9 line-height-18">A/C Reg</div>
								</th>
								<th class='colwidth-10 sorting'  onclick="orderBy('bjh')" id="bjh_order" >
									    <div class="font-size-12 line-height-18">部件号</div>
										<div class="font-size-9 line-height-18">PN</div>
							    </th>
								<th class='colwidth-13 sorting' onclick="orderBy('bjmc')" id="bjmc_order" >
									<div class="font-size-12 line-height-18">部件名称</div>
									<div class="font-size-9 line-height-18">PN name</div>
								</th>
								<th class='colwidth-9 sorting' onclick="orderBy('jhsyrq')" id="jhsyrq_order">
										<div class="font-size-12 line-height-18">计划使用日期</div>
										<div class="font-size-9 line-height-18">Date</div>
							   </th>
							   <th class='colwidth-9 sorting' onclick="orderBy('xqsl')" id="xqsl_order">
									<div class="font-size-12 line-height-18">需求数量</div>
									<div class="font-size-9 line-height-18">QTY</div>
							   </th>
							   <th class='colwidth-9 sorting' onclick="orderBy('kcsl')" id="kcsl_order">
									<div class="font-size-12 line-height-18">库存可用数量</div>
									<div class="font-size-9 line-height-18">QTY</div>
							   </th>
							   <th class='colwidth-20 sorting' onclick="orderBy('bzbz')" id="bzbz_order">
								    <div class="important">
										<div class="font-size-12 line-height-18">保障备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
								    </div>
							   </th>
							    <th class='colwidth-7 sorting' onclick="orderBy('xlh')" id="xlh_order">
									<div class="font-size-12 line-height-18">序列号</div>
									<div class="font-size-9 line-height-18">SN</div>
							   </th>
							   <th class='colwidth-20 sorting' onclick="orderBy('gmjy')" id="gmjy_order">
									<div class="font-size-12 line-height-18">购买建议</div>
									<div class="font-size-9 line-height-18">Purchase advice</div>
							   </th>
							   <th class='colwidth-12 sorting' onclick="orderBy('thj')" id="thj_order">
									<div class="font-size-12 line-height-18">替换件</div>
									<div class="font-size-9 line-height-18">Replacement parts</div>
							   </th>
							   <th class='colwidth-5 sorting' onclick="orderBy('jjcd')" id="jjcd_order">
									<div class="font-size-12 line-height-18">紧急</div>
									<div class="font-size-9 line-height-18">Urgent</div>
							   </th>
							   <th class='colwidth-7 sorting' onclick="orderBy('is_yxfx')" id="is_yxfx_order">
									<div class="font-size-12 line-height-18">影响放行</div>
									<div class="font-size-9 line-height-18">Release</div>
							   </th>
							   <th class='colwidth-10 sorting' onclick="orderBy('is_fjhtc')" id="is_fjhtc_order">
									<div class="font-size-12 line-height-18">非计划停场</div>
									<div class="font-size-9 line-height-18">Unplanned parking</div>
							   </th>
							   <th class='colwidth-15 sorting' onclick="orderBy('bh')" id="bh_order">
								   <div class="important">
										<div class="font-size-12 line-height-18">需求单编号</div>
										<div class="font-size-9 line-height-18">Demand No.</div>
								   </div>
							   </th>
							   <th class='colwidth-15 sorting' onclick="orderBy('xqyy')" id="xqyy_order">
								    <div class="important">
										<div class="font-size-12 line-height-18">需求原因/故障描述</div>
										<div class="font-size-9 line-height-18">Retention of demand cause</div>
								    </div>
							   </th>
							    <th class='colwidth-15 sorting' onclick="orderBy('lybh')" id="lybh_order">
									<div class="font-size-12 line-height-18">需求来源</div>
									<div class="font-size-9 line-height-18">Demand Source</div>
							   </th>
							   <th class='colwidth-10 sorting' onclick="orderBy('sqrid')" id="sqrid_order">
									<div class="font-size-12 line-height-18">提报人</div>
									<div class="font-size-9 line-height-18">Journalist</div>
							   </th>
								
							</tr> 
						</thead>
						<tbody id="materiallist_list"></tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="materiallist_pagination"></div>
				</div>
				<div id='bottom_hidden_content' class='displayContent col-xs-12  clearfix padding-top-10 padding-left-0 padding-right-0 padding-top-0' style='display:none;'>
				 <div id="hideIcon" class="pull-right" style='height:1px;padding-right:8px;margin-top:1px;'>
						<img src="${ctx}/static/images/down.png" onclick='hideBottom()' style="width:33px;cursor:pointer;">
					</div>
				<ul id="myChildTab" class="nav nav-tabs tabNew-style" style="padding-top:0px !important;">
			    <li class="active" >
				<a id='tool' href='#toolTab' data-toggle="tab"  >
					<div class="font-size-12 line-height-12">需求详情</div>
                    <div class="font-size-9 line-height-9">Details of demand</div>
                 </a>
				</li>
				<li class=""  >
				<a href='#feedbackTool' data-toggle="tab"  >
					<div class="font-size-12 line-height-12">流程信息</div>
                    <div class="font-size-9 line-height-9">Process info</div>
                  </a>
				</li>
				</ul>
				<div id="plan_feedback_replace_tab" class="tab-content" style='padding:0px;'>
					<div id="toolTab" class="tab-pane fade active in " style="" >
						<%@ include file="/WEB-INF/views/material2/demand/materiallist/materiallist_view.jsp" %> <!-- 需求详情 --> 
					</div>
					<div id="feedbackTool" class="tab-pane fade">
						<%@ include file="/WEB-INF/views/material2/demand/materiallist/process_info.jsp" %> <!-- 流程信息 -->
					</div>
					
				</div>
				</div>
		   </div>
		</div>
	</div>
<%@ include file="/WEB-INF/views/material2/demand/materiallist/process_modal.jsp" %><!-- 批量处理操作 -->
<%@ include file="/WEB-INF/views/material2/demand/materiallist/analysis_open.jsp" %><!-- 需求统计分析 -->
<%@ include file="/WEB-INF/views/open_win/contract_type.jsp"%><!-- 合同类型弹出框 -->
<%@ include file="/WEB-INF/views/material2/contract/mgnt/mgnt_add.jsp"%><!-- 新增合同弹出框 -->
<script type="text/javascript" src="${ctx }/static/js/thjw/material2/demand/toollist/toollist_main.js"></script>

</body>
</html>