<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>巡检记录</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">

<input type="hidden" id="zzjgid" name="zzjgid" value=${user.jgdm} />

		<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
<script type="text/javascript">
$(document).ready(function(){
//导航
Navigation(menuCode);
});
</script>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>
		<!-- BEGIN CONTENT -->
		<div class="page-content ">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
				<div class="panel-body padding-bottom-0">
				<a href="#" onclick="add()" data-toggle="modal"
					class="btn btn-primary padding-top-1 padding-bottom-1 pull-left row-height checkPermission"
					 permissioncode="airportensure:routinginspectionrecord:main:01"
					><div class="font-size-12">新增</div>
					<div class="font-size-9">Add</div>
				</a>

					<!-- 搜索框start -->
				<div class=" pull-right padding-left-0 padding-right-0">
					<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
						<input type="text" placeholder='巡检单编号/巡检人名称' id="keyword_search" class="form-control ">
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
				
				<div class="col-xs-12 col-sm-12 col-lg-12 triangle  padding-top-10 display-none border-cccccc margin-top-10 search-height" id="divSearch">
					<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">夜航巡视</div>
							<div class="font-size-9">Night Patrol</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="isYhxs" class="form-control "  name="isYhxs">
									<option value="">显示全部</option>
									<option value="1">是</option>
									<option value="0">否</option>
							</select>
						</div>
					</div>					
					<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div></span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control " name="dprtcode" onchange="changeContent()">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					
					<div class="col-lg-2  text-right  pull-right padding-right-0" style="margin-bottom: 10px;">
						<button type="button"class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div> 
				</div>
				
					<div class="clearfix"></div>

					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-h" style="overflow-x: scroll;width: 100%;">
						<table  id="quality_check_list_table" class="table table-thin table-bordered table-set">
							<thead>
							<tr>
							<th class=" colwidth-5"><div class="font-size-12 line-height-18" >操作</div><div class="font-size-9 line-height-18" >Operation</div></th>
							<th class="sorting colwidth-10" onclick="orderBy('xjbh')" id="xjbh_order"><div class="important"><div class="font-size-12 line-height-18" >巡检单编号</div><div class="font-size-9 line-height-18" >Patrol No.</div></div></th>
							<th class="sorting colwidth-10" onclick="orderBy('xsrid')" id="xsrid_order"><div class="important"><div class="font-size-12 line-height-18" >巡视人名称</div><div class="font-size-9 line-height-18" >Patrol man</div></div></th>
							<th class="sorting colwidth-7" onclick="orderBy('xsrq')" id="xsrq_order"><div class="font-size-12 line-height-18" >巡视日期 </div><div class="font-size-9 line-height-18" >Patrol date</div></th>
							<th class="sorting colwidth-5" onclick="orderBy('kssj')" id="kssj_order"><div class="font-size-12 line-height-18" >开始时间 </div><div class="font-size-9 line-height-18" >Start time</div></th>
							<th class="sorting colwidth-5" onclick="orderBy('jssj')" id="jssj_order"><div class="font-size-12 line-height-18" >结束时间</div><div class="font-size-9 line-height-18" >End time</div></th>
							<th class="sorting colwidth-5" onclick="orderBy('is_yhxs')" id="is_yhxs_order"><div class="font-size-12 line-height-18" >夜航巡视</div><div class="font-size-9 line-height-18" >Night Patrol</div></th>
							<th class="sorting colwidth-10" onclick="orderBy('whrid')" id="whrid_order"><div class="font-size-12 line-height-18" >维护人</div><div class="font-size-9 line-height-18" >Record keeper</div></th>
							<th class="sorting colwidth-10" onclick="orderBy('whbmid')" id="whbmid_order"><div class="font-size-12 line-height-18" >维护部门</div><div class="font-size-9 line-height-18" >Record Dept</div></th>
							<th class="sorting colwidth-10" onclick="orderBy('whsj')" id="whsj_order"><div class="font-size-12 line-height-18" >维护时间</div><div class="font-size-9 line-height-18" >Record time</div></th>
							<th class="sorting colwidth-10" onclick="orderBy('dprtcode')" id="dprtcode_order"><div class="font-size-12 line-height-18" >组织机构</div><div class="font-size-9 line-height-18" >Organization</div></th>
							</tr> 
			         		 </thead>
							<tbody id="routinginspectionrecordList">
							</tbody>
							
						</table>
					</div>
					<!--  <div class="col-xs-12 text-center padding-right-0 page-height">
						<div class="col-xs-12 text-center padding-right-0 " id="pagination">
					</div>
				</div> -->
					<div class="col-xs-12 text-center padding-right-0 page-height " id="pagination">
					</div>
					
					<div class="clearfix"></div>
				</div>
			</div>

	</div>
	
	<%@ include file="/WEB-INF/views/alert.jsp"%>
	
	<!-- 	选择用户的模态框 -->
	<div class="modal fade" id="alertModalView" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 80%;height:90%;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="FixChapterBody">
			  	<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">巡检记录明细</div>
						<div class="font-size-9 ">Patrol records</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0">
		            	<div class="col-lg-12 col-xs-12">
							
							<div class="clearfix"></div>
						    
							<!-- start:table -->
							<div class="margin-top-10 " style="overflow-y:scroll;height:500px;">
								<table class="table table-bordered table-striped table-hover text-left" style="min-width:900px">
									<thead>
											<tr>
											<th colspan="3" width="30%"><div class="font-size-12 line-height-18" >检查项目</div><div class="font-size-9 line-height-18" >Inspection items</div></th>
											<th width="45%"><div class="font-size-12 line-height-18" >备注 </div><div class="font-size-9 line-height-18" >Remarks</div></th>
											<th width="15%"><div class="font-size-12 line-height-18" >文件 </div><div class="font-size-9 line-height-18" >File</div></th>
											</tr> 
							         		 </thead>
											<tbody id="detailList">
											<tr> 
											<td rowspan="5" class='text-center'>机</br>坪</br>、</br>跑</br>道</br>和</br>滑</br>行</br>道</br></td>
											<td width="11%" class='text-right' >道面表面情况</td>
											<td width="11%" class='text-left'>
												<select id="detail0101" class="form-control "  name="detail0101" disabled="disabled">
												<option value="1">无剥落物</option>
												<option value="2">有剥落物</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0101" name="detailBz0101" maxlength="300" rows="1" disabled="disabled"></textarea>
											</td>
											<td name='detailPxfj0101' id='detailPxfj0101' class='text-left'></td>
											</tr> 
											
											<tr> 
											<td class='text-right'>嵌缝料情况</td>
											<td class='text-left'>
												<select id="detail0102" class="form-control "  name="detail0102" disabled="disabled">
												<option value="1">无剥落物</option>
												<option value="2">有剥落物</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0102" name="detailBz0102" maxlength="300" rows="1" disabled="disabled"></textarea>
											</td>
											<td name='detailPxfj0102' id='detailPxfj0102' class='text-left'></td>
											</tr>
											
											<tr> 
											<td class='text-right'>标志线、标识情况</td>
											<td class='text-left'>
												<select id="detail0103" class="form-control "  name="detail0103" disabled="disabled">
												<option value="1">标志清晰</option>
												<option value="2">部分模糊</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0103" name="detailBz0103" maxlength="300" rows="1" disabled="disabled"></textarea>
											</td>
											<td name='detailPxfj0103' id='detailPxfj0103' class='text-left'></td>
											</tr>
											
											<tr> 
											<td class='text-right'>污染物情况</td>
											<td class='text-left'>
												<select id="detail0104" class="form-control "  name="detail0104" disabled="disabled">
												<option value="1">道面清洁</option>
												<option value="2">部分模糊</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0104" name="detailBz0104" maxlength="300" rows="1" disabled="disabled"></textarea>
											</td>
											<td name='detailPxfj0104' id='detailPxfj0104' class='text-left'></td>
											</tr>
											
											<tr> 
											<td class='text-right'>FOD情况</td>
											<td class='text-left'>
												<select id="detail0105" class="form-control "  name="detail0105" disabled="disabled">
												<option value="1">无</option>
												<option value="2">有</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0105" name="detailBz0105" maxlength="300" rows="1" disabled="disabled"></textarea>
											</td>
											<td name='detailPxfj0105' id='detailPxfj0105' class='text-left'></td>
											</tr >
											
											<tr> 
											<td >土质地带</td>
											<td class='text-right'>目视平整度</td>
											<td class='text-left'>
												<select id="detail0201" class="form-control "  name="detail0201" disabled="disabled">
												<option value="1">平整</option>
												<option value="2">异常</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0201" name="detailBz0201" maxlength="300" rows="1" disabled="disabled"></textarea>
											</td>
											<td name='detailPxfj0201' id='detailPxfj0201' class='text-left'></td>
											</tr> 
											
											<tr> 
											<td rowspan="2">场内障碍物</td>
											<td class='text-right'>已有障碍物标识和灯光</td>
											<td class='text-left'>
												<select id="detail0301" class="form-control "  name="detail0301" disabled="disabled">
												<option value="1">正常</option>
												<option value="2">异常</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0301" name="detailBz0301" maxlength="300" rows="1" disabled="disabled"></textarea>
											</td>
											<td name='detailPxfj0301' id='detailPxfj0301' class='text-left'></td>
											</tr> 
											
											<tr> 
											<td class='text-right'>疑似新增超高物</td>
											<td class='text-left'>
												<select id="detail0302" class="form-control "  name="detail0302" disabled="disabled">
												<option value="1">无</option>
												<option value="2">有</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0302" name="detailBz0302" maxlength="300" rows="1" disabled="disabled"></textarea>
											</td>
											<td name='detailPxfj0302' id='detailPxfj0302' class='text-left'></td>
											</tr> 
											
											<tr> 
											<td>场外障碍物</td>
											<td class='text-right'>疑似新增超高物</td>
											<td class='text-left'>
												<select id="detail0401" class="form-control "  name="detail0401" disabled="disabled">
												<option value="1">无</option>
												<option value="2">有</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0401" name="detailBz0401" maxlength="300" rows="1" disabled="disabled"></textarea>
											</td>
											<td name='detailPxfj0401' id='detailPxfj0401' class='text-left'></td>
											</tr> 
											
											<tr> 
											<td>机场围界</td>
											<td class='text-right'>目视完整度</td>
											<td class='text-left'>
												<select id="detail0501" class="form-control "  name="detail0501" disabled="disabled">
												<option value="1">完整</option>
												<option value="2">异常</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0501" name="detailBz0501" maxlength="300" rows="1" disabled="disabled"></textarea>
											</td>
											<td name='detailPxfj0501' id='detailPxfj0501' class='text-left'></td>
											</tr> 
											
											<tr> 
											<td>排水渠</td>
											<td class='text-right'>是否畅通</td>
											<td class='text-left'>
												<select id="detail0601" class="form-control "  name="detail0601" disabled="disabled">
												<option value="1">是</option>
												<option value="2">否</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0601" name="detailBz0601" maxlength="300" rows="1" disabled="disabled"></textarea>
											</td>
											<td name='detailPxfj0601' id='detailPxfj0601' class='text-left'></td>
											</tr> 
											
											<tr> 
											<td>风向带</td>
											<td class='text-right'>是否正常</td>
											<td class='text-left'>
												<select id="detail0701" class="form-control "  name="detail0701" disabled="disabled">
												<option value="1">是</option>
												<option value="2">否</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0701" name="detailBz0701" maxlength="300" rows="1" disabled="disabled"></textarea>
											</td>
											<td name='detailPxfj0701' id='detailPxfj0701' class='text-left'></td>
											</tr> 
											
											<tr> 
											<td>灯光</td>
											<td class='text-right'>目视有无损坏</td>
											<td class='text-left'>
												<select id="detail0801" class="form-control "  name="detail0801" disabled="disabled">
												<option value="1">正常</option>
												<option value="2">异常</option>
												</select>
											</td>
											<td  class='text-center'>
											<textarea class="form-control" id="detailBz0801" name="detailBz0801" maxlength="300" rows="1" disabled="disabled"></textarea>
											</td>
											<td name='detailPxfj0801' id='detailPxfj0801' class='text-left'></td>
											</tr> 
							         		 </thead>
								</table>
								<!-- <div class=" col-xs-12  text-center " style="margin-top: 0px; margin-bottom: 0px;" id="fixChapterPagination"></div> -->
							</div>
							<!-- end:table -->
		                	<div class="text-right margin-top-10 margin-bottom-10">
								
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
								
			                </div>
			     			<div class="clearfix"></div> 
						</div> 
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
</div>
	<!------选择用户 End-------->
	
	
	
	</div>
	
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
	</div>
	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
	<script type="text/javascript" src="${ctx }/static/js/thjw/airportensure/routinginspectionrecord/routinginspectionrecord_main.js"></script>
</body>
</html>