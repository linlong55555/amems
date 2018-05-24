<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title></title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
var id = "${param.id}";
var pageParam = '${param.pageParam}';

//来源ID(评估单ID)
var todo_lyid = "${param.todo_lyid}";
var todo_fjjx = "${param.todo_fjjx}";
var todo_ywid = "${param.todo_ywid}";
var todo_dprtcode = "${param.todo_dprtcode}";
var todo_jd = "${param.todo_jd}";

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
					searchRevision();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">
<input type="hidden" id="zzjgid" name="zzjgid" value="${user.jgdm}" />
<input type="hidden" id="userId" name="userId" value="${user.id}" />
<input type="hidden" id="zdbmid" name="zdbmid" value="${user.bmdm}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
		<!-- BEGIN CONTENT -->
		<div class="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
				<!-- <input type="hidden" id="adjustHeight" value="10"> -->
				<div class="panel-body padding-bottom-0">
				<div class='margin-top-0 searchContent row-height'>
				<div class=" col-lg-3 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group">
				<a href="javascript:add();"  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission"
				permissioncode='project2:bulletin:01'>
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a>
				<a id="batchReview" type="button" onclick="batchApproveWin(false);"  permissioncode='project2:bulletin:05'
					class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left checkPermission">
					<div class="font-size-12">批量审核</div>
					<div class="font-size-9">Review</div>
				</a>
				<a id="batchApprove" type="button" onclick="batchApproveWin(true);"  permissioncode='project2:bulletin:06'
					class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left checkPermission">
					<div class="font-size-12">批量批准</div>
					<div class="font-size-9">Approve</div>
				</a>
				<a href="javascript:exportExcel();"  class="btn btn-primary padding-top-1 margin-left-10 padding-bottom-1 pull-left checkPermission" permissioncode='project2:bulletin:09' >
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
				</a>
				</div>
				<div class="col-lg-2 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-4 col-md-4 col-sm-4 col-xs-1 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">机型</div>
							<div class="font-size-9 ">A/C Type</div>
						</span>
						<div id="jxdiv" class="col-lg-8 col-md-8 col-sm-8 col-xs-11 padding-left-8 padding-right-0">
							
						</div>
					</div>
					<div class="col-lg-2 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span class="col-lg-4 col-md-4 col-sm-4 col-xs-1  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">状态</div>
							<div class="font-size-9">Status</div>
						</span>
						<div class="col-lg-8 col-md-8 col-sm-8 col-xs-11 padding-left-8 padding-right-0 label-input-div" >
							<select id="zt_search" class="form-control "  name="zt_search">
									<option value="">显示全部 All</option>
									<c:forEach items="${bulletinStatusEnum}" var="type">
										<c:if test="${type.id != 9 && type.id != 10}">
											<option value="${type.id}">${type.name}</option>
										</c:if>
									</c:forEach>
							</select>
						</div>
				</div>
				<div class="col-lg-2 col-md-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
					<span class="col-lg-4 col-md-4 col-sm-4 col-xs-1  text-right padding-left-0 padding-right-0">
						<div class="font-size-12">版本</div>
						<div class="font-size-9">Rev.</div>
					</span>
					<div class="col-lg-8 col-md-8 col-sm-8 col-xs-11 padding-left-8 padding-right-0 label-input-div" >
						<select id="bb_search" class="form-control "  name="bb_search">
							<option value="current">当前</option>
							<option value="history">历史</option>
						</select>
					</div>
				</div>
					<!-- 搜索框start -->
				<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>
					<div class="col-xs-12 input-group input-group-search">
					<input type="text" placeholder='维护提示编号/主题'  class="form-control" id="keyword_search" >
                    <div class="input-group-addon btn-searchnew" >
                    	<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();" style='margin-right:0px !important;'>
						<div class="font-size-12">搜索</div>
						<div class="font-size-9">Search</div>
                  		</button>
                    </div>
                    <div class="input-group-addon btn-searchnew-more">
	                    <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1  resizeHeight"  id="btn" onclick="search();">
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
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					<div class="col-lg-5 col-sm-0 col-xs-0   padding-left-0 padding-right-0 margin-bottom-8 "></div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12   padding-left-0 padding-right-0 margin-bottom-8 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">维护提示期限</div>
							<div class="font-size-9">Bulletin Limit</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="tgqx_search" readonly />
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12   padding-left-0 padding-right-0 margin-bottom-8 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="zzjg" class="form-control " name="zzjg">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>										
					<div class="col-lg-1  text-right  pull-right padding-right-0" style="margin-bottom: 10px;">
						<button type="button"class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>					 
				</div>
				<div class="clearfix"></div>
				</div>				
					<div class="clearfix"></div>
					<div class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-h table-set" style="overflow-x: auto;" id="bulletin_list_table_div">
						<table id="bulletin_list_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 1700px !important">
							<thead>
							<tr>
							<th  class="fixed-column selectAllImg" id="checkAll" style='text-align:center;vertical-align:middle;width:60px;'>
								<a href="#" onclick="performSelectAll()" ><img src="${ctx}/static/assets/img/d1.png" alt="全选" /></a>
								<a href="#" class="margin-left-5" onclick="performSelectClear()" ><img src="${ctx}/static/assets/img/d2.png" alt="不全选" /></a>
							</th>
							<th class="fixed-column colwidth-7"  ><div class="font-size-12 line-height-18" >操作</div><div class="font-size-9 line-height-18" >Operation</div></th>
							<th class="fixed-column colwidth-5"  ><div class="font-size-12 line-height-18" >标识</div><div class="font-size-9 line-height-18" >Mark</div></th>
							<th class="fixed-column sorting  colwidth-10" onclick="orderBy('jstgh')" id="jstgh_order"><div class="important"><div class="font-size-12 line-height-18" >维护提示编号</div><div class="font-size-9 line-height-18" >TB No.</div></div></th>
							<th class="fixed-column sorting  colwidth-5" onclick="orderBy('bb')" id="bb_order"><div class="font-size-12 line-height-18" >版本</div><div class="font-size-9 line-height-18" >Rev.</div></th>
							<th class="sorting  colwidth-10" onclick="orderBy('jx')" id="jx_order"><div class="font-size-12 line-height-18" >机型</div><div class="font-size-9 line-height-18" >A/C Type</div></th>
							<th class="sorting colwidth-9" onclick="orderBy('tgqx')" id="tgqx_order"><div class="font-size-12 line-height-18" >维护提示期限 </div><div class="font-size-9 line-height-18" >Bulletin Limit</div></th>
							<th class="sorting colwidth-5"  onclick="orderBy('syts')" id="syts_order"><div class="font-size-12 line-height-18" >剩余(天) </div><div class="font-size-9 line-height-18" >Remain</div></th>
							<th class="sorting colwidth-30" onclick="orderBy('zhut')" id="zhut_order"><div class="important"><div class="font-size-12 line-height-18" >主题 </div><div class="font-size-9 line-height-18" >Subject</div></div></th>
							<th class="downward colwidth-13" onclick="vieworhidePgd()" name="pgd" ><div class="font-size-12 line-height-18" >技术评估单</div><div class="font-size-9 line-height-18" >Evaluation</div></th>
							<th class="colwidth-10" ><div class="font-size-12 line-height-18" >圈阅情况</div><div class="font-size-9 line-height-18" >Redlining Case</div></th>							
							<th class="sorting colwidth-9" onclick="orderBy('sxrq')" id="sxrq_order"><div class="font-size-12 line-height-18" >生效日期</div><div class="font-size-9 line-height-18" >Effect Date</div></th>
							<th class="colwidth-9" ><div class="font-size-12 line-height-18" >附件</div><div class="font-size-9 line-height-18" >Attachment</div></th>
							<th class="sorting colwidth-5" onclick="orderBy('zt')" id="zt_order"><div class="font-size-12 line-height-18" >状态</div><div class="font-size-9 line-height-18" >Status</div></th>
							<th class="sorting colwidth-13" onclick="orderBy('zdrid')" id="zdrid_order"><div class="font-size-12 line-height-18" >维护人</div><div class="font-size-9 line-height-18" >Maintainer</div></th>
							<th class="sorting colwidth-13" onclick="orderBy('zdsj')" id="zdsj_order"><div class="font-size-12 line-height-18" >维护时间</div><div class="font-size-9 line-height-18" >Maintenance Time</div></th>
							<th class="sorting colwidth-13" onclick="orderBy('dprtcode')" id="dprtcode_order"><div class="font-size-12 line-height-18" >组织机构 </div><div class="font-size-9 line-height-18" >Organization</div></th>
							</tr> 
			         		 </thead>
							<tbody id="bulletinlist">
							</tbody>
							
						</table>
					</div>
					<div class="col-xs-12 text-center padding-left-0 padding-right-0 page-height " id="pagination_list">
					</div>
					
					<div class="clearfix"></div>
				</div>
			</div>

	</div>
	
	<!-------添加模态框 start-------->
	<div class="modal fade modal-new" id="addModal" tabindex="-1" role="dialog" data-backdrop="static"
		aria-labelledby="myModalLabel" aria- hidden="true"  >
		<div class="modal-dialog" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                       <div class="font-size-14 "><span id="titleName"></span></div>
							<div class="font-size-12"><span id="titleEname"></span></div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
				<div class="modal-body " >
					<form id="form">
						<div class="col-xs-12 margin-top-8">
							<div class="input-group-border">
										<input type="hidden" id="id" />	
										<input type="hidden" id="fBbid" />	
										<input type="hidden" id="zt" />
										<input type="hidden" id="dprt" />					
									<div class="col-lg-3 col-md-4  col-sm-6 col-xs-12  padding-left-0 padding-right-8 margin-bottom-0 form-group">
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">维护提示编号</div>
											<div class="font-size-9">TB No.</div>
										</span>
											<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-10 padding-right-0">
												<input type="text" class="form-control "maxlength="50" placeholder="不填写则自动生成"  id="jxtgbh" name="jxtgbh"  />
											</div>
									</div>	
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-8  form-group">
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12"><span style="color: red" id="bbmark">*</span>版本</div>
											<div class="font-size-9">Rev.</div>
										</span>
										<div id="bbViewHistoryDiv" class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-10 padding-right-0">
											<div class="input-group input-group-new">
												<input type="text" class="form-control" maxlength="9" onkeyup="clearNoNumTwo(this)" id="current_bb" name="current_bb"  />
												<span name="lastBbSpan" class="input-group-addon">
						                     		← <a id="previous_bb" href="javascript:void(0);" onclick="viewPrevious()"></a>
						                     		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						                     		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						                     		  <input type="hidden" id="previous_id" />
						                     	</span>
						                     	<span name="lastBbSpan" class='input-group-btn' title="历史列表  List">
													<i class="attachment-view2 glyphicon glyphicon glyphicon-list color-blue cursor-pointer" style="font-size:15px;float: left;text-decoration:none;position:relative; margin-left: 10px;"></i>
												</span>
										    </div>
										    
										</div>
									    <div id="bbNoViewHistoryDiv" class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-10 padding-right-0">
											<input class="form-control" id="bb" name="bb" type="text" maxlength="9" onkeyup='clearNoNumTwo(this)' />
										</div>
									</div>
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-8  form-group">
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12"><span style="color: red"  id="jxmark">*</span>机型</div>
											<div class="font-size-9">A/C Type</div>
										</span>
											<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-10 padding-right-0">
												<select class='form-control' id='fjjx' name='fjjx'>
												</select>
											</div>
									</div>
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-8   form-group">
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12"><span style="color: red" id="bfrqmark">*</span>颁发日期</div>
											<div class="font-size-9">Issue Date</div>
										</span>
											<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-10 padding-right-0">
												<input type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" id="bfrq" name="bfrq"  />
											</div>
									</div>
									<div class="clearfix"></div>																		
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-8   form-group">
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">生效日期</div>
											<div class="font-size-9">Effect Date</div>
										</span>
											<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-10 padding-right-0">
												<input type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" id="sxrq" name="sxrq"  />
											</div>
									</div>
									<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-8  form-group">
										<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">维护提示期限</div>
											<div class="font-size-9">Bulletin Limit</div>
										</span>
											<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-10 padding-right-0">
												<input type="text" class="form-control date-picker" maxlength="10" data-date-format="yyyy-mm-dd" id="tgqx" name="tgqx"  />
											</div>
									</div>									
									<div class="clearfix"></div>
									<div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-8  form-group">
										<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right  padding-right-0"><div class="font-size-12 "><span style="color: red" id="zhutmark">*</span>主题</div>
											<div class="font-size-9 ">Subject</div>
										</span>
									 	<div class="col-lg-11 col-md-11  col-sm-10 col-xs-9 padding-left-8 padding-right-0">
												<textarea class="form-control" id="zhut" name="zhut" placeholder=''   maxlength="300" style="height:55px"></textarea>
										</div>
									</div>
									<div class="clearfix"></div>
									<%@ include file="/WEB-INF/views/open_win/evaluationList.jsp"%><!-- -评估单列表 -->
									 <div class="clearfix"></div>
									<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0  form-group">
										<label class="col-lg-2 col-md-2 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">涉及</div>
											<div class="font-size-9">Related To</div>
										</label>
											<div class="col-lg-10 col-md-10 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
											<c:forEach items="${compnentTypeEnum}" var="type">											
												<label style="font-weight:normal" class="margin-right-5 label-input"><input type="radio" name="sj"  value='${type.id}' checked="checked">&nbsp;${type.name}</label>																						
											</c:forEach>
											</div>
									</div>									
									<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12  padding-left-0 padding-right-0   form-group">
										<span class="col-lg-2 col-md-2 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">有效性</div>
											<div class="font-size-9">Effectivity</div>
										</span>
											<div class="col-lg-10 col-md-10 col-sm-8 col-xs-9 padding-left-8 padding-right-8">
												<input type="text" class="form-control "maxlength="300" placeholder=""  id="yxx" name="yxx"  />
											</div>
									</div>
									<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0   form-group">
										<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">来源说明</div>
											<div class="font-size-9">Desc</div>
										</span>
											<div class="col-lg-11  col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
												<input type="text" class="form-control "maxlength="300" placeholder=""  id="lysm" name="lysm"  />
											</div>
									</div>
									<div class="clearfix"></div>
									<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
										<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0" >
											<div class="font-size-12 ">参考资料</div>
											<div class="font-size-9 ">References</div>
										</span>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
												<textarea class="form-control" id="ckzl" name="ckzl" placeholder=''   maxlength="1000" style="height:55px"></textarea>
										</div>
									</div>
									<div class="clearfix"></div>
									<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
										<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0" >
											<div class="font-size-12 ">背景</div>
											<div class="font-size-9 ">Background</div>
										</span>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
												<textarea class="form-control" id="bj" name="bj" placeholder=''   maxlength="1000" style="height:55px"></textarea>
										</div>
									</div>
									<div class="clearfix"></div>
									<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
										<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0" >
											<div class="font-size-12 ">描述</div>
											<div class="font-size-9 ">Description</div>
										</span>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
												<textarea class="form-control" id="ms" name="ms" placeholder=''   maxlength="1000" style="height:55px"></textarea>
										</div>
									</div>
									<div class="clearfix"></div>
									<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
										<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0" >
											<div class="font-size-12 ">措施</div>
											<div class="font-size-9 ">Action</div>
										</span>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
												<textarea class="form-control" id="cs" name="cs" placeholder=''   maxlength="1000" style="height:55px"></textarea>
										</div>
									</div>
									<div class="clearfix"></div>
									<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0    form-group">
										<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12">维护提示类别</div>
											<div class="font-size-9">TB Category</div>
										</span>
											<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8 label-input-div">
												<label  class="margin-right-5 label-input"><input type="radio" name="tglb"  value='1' checked="checked">&nbsp;信息类</label>										
												<label  class="label-input"><input type="radio" name="tglb"  value='2'  >&nbsp;标准类</label>
											</div>
									</div>
									<div class="clearfix"></div>
									<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
										<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-8" >
											<div class="font-size-12 ">改版原因</div>
											<div class="font-size-9 ">Reason</div>
										</span>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
												<textarea class="form-control " id="gbyy" name="gbyy" placeholder=''   maxlength="1000" style="height:55px"></textarea>
										</div>
									</div>
									<div class="clearfix"></div>
									<div 
										class="col-lg-12 col-md-12 col-sm-12 col-xs-12  padding-top-0 padding-left-0 padding-right-8  form-group">
										<label
											class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12"><span style="color: red" id="ffmark">*</span>分发</div>
											<div class="font-size-9">Distribution</div>
										</label>
										<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0 pull-left input-group">
											<div id="jj_div_edit" class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-0 padding-right-8">
												<div class="input-group" style="min-width:25%;">
													<div ondblclick="openzrdw()" id="ff" class='form-control base-color readonly-style' style='border-right:1px solid #d5d5d5;border-radius:0px;min-height:34px;height:auto;padding-left:3px;padding-right:3px;'>
													</div> 
								                    <div id="wxrybtn" class="input-group-addon btn btn-default" style='padding-left:0px;padding-right:0px;width:38px;' onclick="openzrdw()"><i class='icon-search'></i></div>
							                	</div>
											</div>	
											<input type="hidden" value="" name="ffid" class="form-control " placeholder='' maxlength=""
													id="ffid">
										</div>									
									</div>
									<div class="clearfix"></div>
									</div>
									<div class="panel panel-primary" id="fjxxpanel">
										<div class="panel-heading bg-panel-heading">
											   <div class="font-size-12 "><input type="checkbox"  name="is_fjxx"  />&nbsp;&nbsp;附加信息</div>
											  <div class="font-size-9">Additional Information</div>
										</div>
										<div class="panel-body" id="fjxxdiv" style="display: none;">
											<span>涉及部件禁装和部件送修时，请填写以下内容；如无关，则不需保留此项内容。</span>
											<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 padding-top-8  ">
													<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-8 padding-right-0" >
														<div class="input-group input-group-new">
													<span class="input-group-addon" ><input type="checkbox"  name="is_wcfjpc" />&nbsp;&nbsp;已完成现有机翼的普装检查&nbsp;&nbsp;EO/MAO	</span>					
													<input type="text" class="form-control" maxlength="300" placeholder=""  id="eo_mao" name="eo_mao"  />
													</div>
													</div>
											</div>											
											<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  margin-bottom-0 form-group">						
													<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
															<div class="input-group input-group-new">														
															<span class="input-group-addon padding-left-35"><input type="radio"  name="is_fj_syxbj" value="0" />&nbsp;&nbsp;无受影响部件</span>																										
															<label></label>
														</div>
													</div>
											</div>
											<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  margin-bottom-0 ">						
													<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
															<div class="input-group input-group-new">														
															<span class="input-group-addon padding-left-35"><input type="radio" name="is_fj_syxbj"  value="1" />&nbsp;&nbsp;有受影响部件</span>																							
															<input type="text" class="form-control" maxlength="300" placeholder="" id="syxbj_fj"  name="syxbj_fj"   />
														</div>
													</div>
											</div>
											<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 padding-top-8  form-group">
													<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-8 padding-right-0" >
														<div class="input-group input-group-new">
													<span class="input-group-addon" ><input type="checkbox"  name="is_bjzjhs" />&nbsp;&nbsp;根据部件装机清单,核实我公司&nbsp;&nbsp;EO/MAO	</span>					
													<label></label>
													</div>
													</div>
											</div>										
											<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  margin-bottom-0 form-group">						
													<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
															<div class="input-group input-group-new">														
															<span class="input-group-addon padding-left-35"><input type="radio" name="is_bj_syxbj" value="0" />&nbsp;&nbsp;无受影响部件</span>																												
															<label></label>
														</div>
													</div>
											</div>
											<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  margin-bottom-0 ">						
													<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" >
															<div class="input-group input-group-new">														
															<span class="input-group-addon padding-left-35"><input type="radio" value="1"  name="is_bj_syxbj" />&nbsp;&nbsp;有受影响部件</span>																												
															<input type="text" class="form-control" maxlength="300" placeholder="" id="syxbj_bj" value="" name="syxbj_bj"   />
														</div>
													</div>
											</div>
											<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 padding-top-8  form-group">
													<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 padding-left-8 padding-right-0" >
														<div class="input-group input-group-new">
													<span class="input-group-addon" ><input type="checkbox" name="is_wpc" />&nbsp;&nbsp;未普查,请PPC核实&nbsp;&nbsp;EO/MAO	</span>					
													<label></label>
													</div>
													</div>
											</div>
										</div>
									</div>
									<div id="attachments_list_edit" style="display:none">							
										<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
									</div>
									<div class="clearfix"></div>																										
										<%@ include file="/WEB-INF/views/open_win/introduce_process_info.jsp" %> <!--流程信息 -->	
								</div>
									<div class="clearfix"></div>
									</form>
								</div>
									<div class="modal-footer ">
										<div class="col-xs-12 padding-leftright-8" >
											<div class="input-group">
												<span class="input-group-addon modalfootertip" >
									                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
												</span>
												<span class="input-group-addon modalfooterbtn">
													<button type="button" id="save" onclick="saveData()"
														class="btn btn-primary padding-top-1 padding-bottom-1">
														<div class="font-size-12">保存</div>
														<div class="font-size-9">Save</div>
													</button>
													<button type="button" id="submit" onclick="submitData()"
														class="btn btn-primary padding-top-1 padding-bottom-1">
														<div class="font-size-12">提交</div>
														<div class="font-size-9">Submit</div>
													</button>
													<button type="button" id="audited" onclick="auditedData('3')"
														class="btn btn-primary padding-top-1 padding-bottom-1">
														<div class="font-size-12">审核通过</div>
														<div class="font-size-9">Audited</div>
													</button>
													<button type="button" id="reject" onclick="auditedData('5')"
														class="btn btn-primary padding-top-1 padding-bottom-1">
														<div class="font-size-12">审核驳回</div>
														<div class="font-size-9">Audit Reject</div>
													</button>
													<button type="button" id="approve" onclick="approvedData('4')"
														class="btn btn-primary padding-top-1 padding-bottom-1">
														<div class="font-size-12">批准通过</div>
														<div class="font-size-9">Approved</div>
													</button>
													<button type="button" id="rejected" onclick="approvedData('6')"
														class="btn btn-primary padding-top-1 padding-bottom-1 ">
														<div class="font-size-12">批准驳回</div>
														<div class="font-size-9">Rejected</div>
													</button>
													<button type="button" id="revision" onclick="revisionData()"
														class="btn btn-primary padding-top-1 padding-bottom-1 ">
														<div class="font-size-12">改版</div>
														<div class="font-size-9">Revision</div>
													</button>
													<button type="button" data-dismiss="modal"
														class="btn btn-primary padding-top-1 padding-bottom-1 ">
														<div class="font-size-12">关闭</div>
														<div class="font-size-9">Close</div>
													</button>
												</span>
											</div>
									</div>
								</div>
				</div>
			</div>
	</div>
	<!-------添加模态框end-------->
	
	
	
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<script type="text/javascript" src="${ctx }/static/js/thjw/project2/bulletin/bulletin_main.js"></script>
	<%@ include file="/WEB-INF/views/open_win/batchApprovel.jsp"%><!-------批量审批对话框 -------->
	<%@ include file="/WEB-INF/views/open_win/selectEvaluation.jsp"%><!-- -选择评估单列表 -->
	<%@ include file="/WEB-INF/views/open_win/department.jsp"%><!-- 选择部门 -->
	<%@ include file="/WEB-INF/views/project2/bulletin/viewMarkupStatus.jsp"%><!-- 圈阅情况 -->
	<%@ include file="/WEB-INF/views/project2/bulletin/history_view_win.jsp"%><!-- 历史版本 -->
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
</body>
</html>