<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>技术文件上传</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
</head>
<body class="page-header-fixed">
	<!-- <input type="hidden" id="adjustHeight" value="150"> -->
	<input type="hidden" id="userId" value="${user.id}" />
	<input type="hidden" id="userType" value="${user.userType}" />
	<input type="hidden" id="zzjgid" value=${user.jgdm } />
	<input type="hidden" id="monitorType" value="${monitorType}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content">
		<!-- from start -->
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">

				<!-- 搜索框start -->
				<div class=" pull-right padding-left-0 padding-right-0 row-height">
					<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
						<input type="text" placeholder='评估单号/文件编号/文件主题/备注/制单人' id="keyword_search" class="form-control ">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                        <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" id="btn" onclick="search();">
							<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
							<div class="pull-left padding-top-5">
							 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
							</div>
				   		</button>
                    </div> 
				</div>
				<!-- 搜索框end -->

				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 display-none border-cccccc margin-top-10 search-height" id="divSearch">

					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">机型</div>
							<div class="font-size-9">Model</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select class="form-control " id="jx">
								<option value="">显示全部</option>
								<%-- <c:forEach items="${typeList}" var="type">
									<option value="${type}">${type}</option>
								</c:forEach> --%>
							</select>
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">分类</div>
							<div class="font-size-9">Category</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="fl" class="form-control " name="fl">
								<option value="">显示全部</option>
							</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">来源</div>
							<div class="font-size-9">Source</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="ly" class="form-control " name="ly">
								<option value="">显示全部</option>
							</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">评估状态</div>
							<div class="font-size-9">State</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="zt" class="form-control " name="zt">
								<option value="">显示全部</option>
								<option value="0">未评估</option>
								<option value="1">已评估</option>
								<option value="2">已审核</option>
								<option value="3">已批准</option>
								<option value="4">中止（关闭）</option>
								<option value="5">审核驳回</option>
								<option value="6">批准驳回</option>
								<option value="9">指定结束</option>
							</select>
						</div>
					</div>
					
					<div class="clearfix"></div>

					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">机型工程师</div>
							<div class="font-size-9">Engineer</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="jxgcs" class="form-control " name="jxgcs">
								<option value="">显示全部</option>
								<c:forEach items="${userToRole}" var="type">
									<option value="${type.id}">${erayFns:escapeStr(type.displayName)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div id="wjlxdiv" class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">文件类型</div>
							<div class="font-size-9">File Type</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="wjlx" class="form-control " name="wjlx">
								<option value="">显示全部</option>
							</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select id="zzjg" class="form-control " name="zzjg" onchange="changeContent()">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}"
										<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="col-sm-3 text-right  pull-right padding-right-0"
						style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				<div class="clearfix"></div>

				<div class="col-lg-12 col-md-12 table-h padding-left-0 padding-right-0" id="quality_check_list_table_div" style="margin-top: 10px;overflow-x: auto;">
					<table id="quality_check_list_table" class="table table-thin table-bordered table-set" style="min-width: 2800px;">
						<thead>
							<tr>
								<th class="viewCol fixed-column colwidth-7">
									<div class="font-size-12 line-height-18 " >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="viewCol sorting fixed-column colwidth-13" onclick="orderBy('GBWHSJ')" id="GBWHSJ_order">
									<div class="font-size-12 line-height-18">质量关闭</div>
									<div class="font-size-9 line-height-18">Quality Close</div>
								</th>
								<th class="sorting fixed-column colwidth-13" onclick="orderBy('pgdh')" id="pgdh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">评估单号</div>
										<div class="font-size-9 line-height-18">Assessment NO.</div>
									</div>
								</th>
								<th class="sorting colwidth-10" onclick="orderBy('ly')" id="ly_order">
									<div class="font-size-12 line-height-18">来源</div>
									<div class="font-size-9 line-height-18">Source</div>
								</th>
								<th class="sorting colwidth-5" onclick="orderBy('jx')" id="jx_order">
									<div class="font-size-12 line-height-18">机型</div>
									<div class="font-size-9 line-height-18">Model</div>
								</th>
								<th class="sorting colwidth-7" onclick="orderBy('fl')" id="fl_order">
									<div class="font-size-12 line-height-18">分类</div>
									<div class="font-size-9 line-height-18">Category</div>
								</th>
								<th class="sorting colwidth-7" onclick="orderBy('wjlx')" id="wjlx_order">
									<div class="font-size-12 line-height-18">文件类型</div>
									<div class="font-size-9 line-height-18">File Type</div>
								</th>
								<th class="sorting colwidth-15" onclick="orderBy('shzltgh')" id="shzltgh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">文件编号</div>
										<div class="font-size-9 line-height-18">File No.</div>
									</div>
								</th>
								<th class="sorting colwidth-25" onclick="orderBy('wjzt')" id="wjzt_order">
									<div class="important">
										<div class="font-size-12 line-height-18">文件主题</div>
										<div class="font-size-9 line-height-18">Subject</div>
									</div>
								</th>
								<th class="sorting  colwidth-5" onclick="orderBy('bb')" id="bb_order">
									<div class="font-size-12 line-height-18">版本</div>
									<div class="font-size-9 line-height-18">Revision</div>
								</th>
								<th class="sorting  colwidth-10" onclick="orderBy('sxrq')" id="sxrq_order">
									<div class="font-size-12 line-height-18">生效日期</div>
									<div class="font-size-9 line-height-18">Effective Date</div>
								</th>
								<th class="sorting  colwidth-10" onclick="orderBy('pgqx')" id="pgqx_order">
									<div class="font-size-12 line-height-18">评估期限</div>
									<div class="font-size-9 line-height-18">Assess period</div>
								</th>
								<th class="sorting  colwidth-5" onclick="orderBy('syts')" id="syts_order">
									<div class="font-size-12 line-height-18">剩余天数</div>
									<div class="font-size-9 line-height-18">Days</div>
								</th>
								<th class="sorting  colwidth-13" onclick="orderBy('pgrid')" id="pgrid_order">
									<div class="font-size-12 line-height-18">机型工程师</div>
									<div class="font-size-9 line-height-18">Engineer</div>
								</th>
								<th class="sorting  colwidth-7" onclick="orderBy('zt')" id="zt_order">
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">State</div>
								</th>
								<th th_class="cont-exp2" td_class="affectedDisplayFile" table_id="quality_check_list_table"  class="cont-exp2 downward colwidth-20" onclick="CollapseOrExpandUtil.collapseOrExpandAll(this)">
									<div class="font-size-12 line-height-18">评估结果</div>
									<div class="font-size-9 line-height-18">Result</div>
								</th>
								<th class="colwidth-30">
									<div class="font-size-12 line-height-18">原文件</div>
									<div class="font-size-9 line-height-18">File</div>
								</th>
								<th class="sorting  colwidth-30" onclick="orderBy('bz')" id="bz_order">
									<div class="important">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</div>
								</th>
								<th th_class="cont-exp2" td_class="affectedDisplayFile" table_id="quality_check_list_table"  class="cont-exp2 downward colwidth-30" onclick="CollapseOrExpandUtil.collapseOrExpandAll(this)">
									<div class="font-size-12 line-height-18">执行对象</div>
									<div class="font-size-9 line-height-18"> Affected Object</div>
								</th>
								<th class="sorting  colwidth-13" onclick="orderBy('zdrid')" id="zdrid_order">
									<div class="important">
										<div class="font-size-12 line-height-18">制单人</div>
										<div class="font-size-9 line-height-18">Creator</div>
									</div>
								</th>
								<th class="sorting  colwidth-13" onclick="orderBy('zdsj')" id="zdsj_order">
									<div class="font-size-12 line-height-18">制单时间</div>
									<div class="font-size-9 line-height-18">Create Time</div>
								</th>
								<th class="sorting  colwidth-13" onclick="orderBy('dprtcode')" id="dprtcode_order">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="list">
						</tbody>

					</table>
				</div>
					<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>

				<div class="clearfix"></div>
				
				
		<!-- 点击后显示的内容 -->
		 <div class="panel panel-primary"  style='display:none;' id='jstreeMain'>
             <div class="panel-body" style='padding:0px;' >	
<!-- 				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="border:1px solid red; margin-top: 10px; padding-bottom:1px;"> -->
                 <div class="col-lg-4 col-sm-3 col-xs-12 pull-left border-r" style='padding:0px;'>
                    <div class="ibox " style='padding:0px;' id='iboxdiv'>
                      <div class="ibox-title" style='padding:0px;height:25px;background:#ececec;'>
	                     <label class=" pull-left" style='height:25px;line-height:25px;padding-left:10px;'>
					           	下达指令/Give Order
						 </label>
                       </div> 
                       <div class="ibox-content" style='padding-left:10px;' > 
                         <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" id='ibox-contentdiv' style="overflow: auto;max-height:200px;min-height:20px;">
                           	 <div id="using_json"></div>
                         </div> 
                       </div>
                    </div>
                 </div>     
                     
				<div class="col-lg-8 col-sm-9 col-xs-12 pull-right" id='jstreeContent' style='padding:0px;'>
			      <div class='displayFile' style='display:none;padding:0px;'>
			      		 <%@ include file="/WEB-INF/views/project/engineeringcenter/engineering_center_technicalbulletin.jsp"%>
			        </div>
			        <div class='displayFile' style='display:none;'>
			        	<%@ include file="/WEB-INF/views/project/engineeringcenter/engineering_center_technicalorder.jsp"%> 
			        </div>
			        <div class='displayFile' style='display:none;'>
			        	<%@ include file="/WEB-INF/views/project/engineeringcenter/engineering_center_amendmentnotice.jsp"%> 
			        </div>
			        <div class='displayFile' style='display:none;'>
			           <%@ include file="/WEB-INF/views/project/engineeringcenter/engineering_center_engineeringorder.jsp"%>
			            <%-- <div class='displayFileChild' style='display:none;'>
			       	    <%@ include file="/WEB-INF/views/project/engineeringcenter/engineering_center_engineeringorder.jsp"%>
			       	    </div>
			       	    <div class='displayFileChild' style='display:none;'>
			       	    <%@ include file="/WEB-INF/views/project/engineeringcenter/engineering_center_workorder.jsp"%>
			       	    </div> --%>
			        </div> 
			        <div class='displayFile' style='display:none;'>
			        	<%@ include file="/WEB-INF/views/project/engineeringcenter/engineering_center_workorder.jsp"%>
			        </div>
               </div>
				<div class="clearfix"></div>
			</div> 
		</div>
		</div>
	</div>
</div>

<!-- 质量关闭对话框 begin -->
<div class="modal fade" id="QualityCloseModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:400px!important;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="QualityCloseModalBody">
			  	<div class="panel panel-primary ">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">质量关闭</div>
						<div class="font-size-9 ">Quality Close</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
						<div class="col-lg-12 col-xs-12 col-sm-12  padding-left-0 margin-top-10 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-lg-3 col-xs-3 col-sm-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12" id="chinaHead">关闭单号</div>
								<div class="font-size-9" id="englishHead">Close No.</div>
							</span>
							<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
								<input type="text" class="form-control " id="egbdh" name="ejsdh" disabled="disabled"/>
							</div>
						</div>
						<div id="vgbr" class="col-lg-12 col-xs-12 col-sm-12  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-lg-3 col-xs-3 col-sm-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">关闭人</div>
								<div class="font-size-9">Closer</div>
							</span>
							<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
								<input type="text" class="form-control " id="ezlgbr" name="ezlgbr" disabled="disabled"/>
							</div>
						</div> 
						<div id="vgbsj" class="col-lg-12 col-xs-12 col-sm-12  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-lg-3 col-xs-3 col-sm-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">关闭时间</div>
								<div class="font-size-9">Close Time</div>
							</span>
							<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
								<input type="text" class="form-control " id="ezlgbrq" name="ezlgbrq" disabled="disabled"/>
							</div>
						</div> 
		            	<div class="col-lg-12 col-xs-12 col-sm-12  padding-left-0 padding-right-0 margin-bottom-10 ">
							<span class="pull-left col-lg-3 col-xs-3 col-sm-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12"><span id="vgbyy" style="color: red">*</span>关闭原因</div>
								<div class="font-size-9">Close Cause</div>
							</span>
							<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-8 padding-right-0">
								<textarea class="form-control" id="ezlgbyy" name="ezlgbyy" placeholder='长度最大为150'   maxlength="150"></textarea>
							</div>
						</div>
						
				     	<div class="clearfix"></div>
				     	
				     	<div class="text-right margin-top-0 padding-buttom-0 margin-buttom-0 ">
							<button id="ConfirmBtn" type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-0" onclick="QualityCloseModal.setData()">
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
	                   		</button>&nbsp;
                   			<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-0" data-dismiss="modal">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
                   		</div>
                   		<br/>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 质量关闭对话框 end -->

<%@ include file="/WEB-INF/views/open_win/AssignEnd.jsp"%><!-- 指定结束对话框 -->
<script src="${ctx}/static/js/thjw/common/preview.js"></script>
<script type="text/javascript" src="${ctx }/static/js/thjw/project/engineeringcenter/engineering_center_main.js"></script>
</body>
</html>