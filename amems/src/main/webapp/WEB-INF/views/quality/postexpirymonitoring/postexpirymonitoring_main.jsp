<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>岗位到期监控</title>
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
					postexpirymonitoringMain.reload();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body >
	<div class="page-content" >
		<div class="panel panel-primary"  id='panel_content'>
			<div class="panel-heading" id="NavigationBar"></div>
            <div class="panel-body padding-bottom-0">
             <!-- 搜索框 -->
			    <div  class='searchContent margin-top-0 row-height' >
				    <!-- 上传按钮  -->
					<div class=" col-lg-1 col-md-12 col-sm-12 col-xs-12   padding-left-0 padding-right-0 form-group  ">
						<a href="javascript:postexpirymonitoringMain.exportExcel();" permissioncode='quality:post:monitor:main:01'  class="btn btn-primary padding-top-1 padding-bottom-1 pull-left  checkPermission">
							<div class="font-size-12">导出</div>
							<div class="font-size-9">Export</div>
						</a> 
					</div>
					
				  <div class=" col-lg-2 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
						<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
						</span>
						<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div text-right" >
							<label style='margin-top:6px;font-weight:normal;' >
								<input id="yxq_search" name='yxq_search' type='checkbox' style='vertical-align:text-bottom' checked />&nbsp;存在有效期
							</label>
				 		</div>
				  </div> 
					<div class="col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
							<span class="col-lg-3 col-md-3 col-sm-4 col-xs-2  text-right padding-left-0 padding-right-0">
								<div class="font-size-12">授权到期</div>
								<div class="font-size-9">Expiration</div>
							</span>
							<div class="col-lg-9 col-md-9 col-sm-8 col-xs-10 padding-left-8 padding-right-0 label-input-div" >
								<input type='text' class='form-control' id="pgrq" name="date-range-picker" />
							</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-12 col-xs-12   padding-left-0 padding-right-0  form-group ">
							<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div>
							</span>
							<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
								<select id="dprtcode" class="form-control " onchange="postexpirymonitoringMain.reload();">
									<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
									</c:forEach>
								</select>
							</div>
					</div>
					<!-- 关键字搜索 -->
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group " style='padding-left:15px;'>
						<div class="col-xs-12 input-group input-group-search">
							<input type="text" placeholder='人员编号/姓名/岗位编号/岗位名称'  class="form-control" id="keyword_search" >
		                    <div class="input-group-addon btn-searchnew" >
		                    	<button type="button" onclick="postexpirymonitoringMain.reload();" class=" btn btn-primary padding-top-1 padding-bottom-1"   style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                    </div>
						</div>
					</div>
					<div class='clearfix'></div>
				</div>
				<!-- 搜索框End -->
				<div class='table_pagination'>
				<!-- 表格 -->
				<div class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0 table-height table-set" c-height="45%" id="tableId" style="overflow-x: auto;width: 100%;">
						<table  id="quality_check_list_table" class="table table-thin table-bordered table-set table-hover table-striped">
							<thead>
								<tr>
									<th class="colwidth-5" >
										<div class="font-size-12 line-height-18 ">标识</div>
										<div class="font-size-9 line-height-18">Mark</div>
									</th>
									<th  class="colwidth-10 sorting" onclick="orderBy('rybh')" id="rybh_order">
										<div class="important">
											<div class="font-size-12 line-height-18">人员编号</div>
											<div class="font-size-9 line-height-18">Staff No.</div>
										</div>
									</th>
									<th class="colwidth-7 sorting" onclick="orderBy('xm')" id="xm_order">
										<div class="important">
											<div class="font-size-12 line-height-18">姓名</div>
											<div class="font-size-9 line-height-18">Name</div>
										</div>
									</th>
									<th class="colwidth-9 sorting" onclick="orderBy('fjjx')" id="fjjx_order">
										<div class="font-size-12 line-height-18">机型</div>
										<div class="font-size-9 line-height-18">A/C Type</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('dgbh')" id="dgbh_order">
										<div class="important">
											<div class="font-size-12 line-height-18">授权岗位</div>
											<div class="font-size-9 line-height-18">Post</div>
										</div>
									</th>
									<th class="colwidth-9 sorting" onclick="orderBy('sqsj')" id="sqsj_order">
										<div class="font-size-12 line-height-18">申请日期</div>
										<div class="font-size-9 line-height-18">Date</div>
									</th>
									<th class="colwidth-9 sorting" onclick="orderBy('pgsj')" id="pgsj_order">
										<div class="font-size-12 line-height-18">授权日期</div>
										<div class="font-size-9 line-height-18">Date</div>
									</th>
									<th class="colwidth-15 sorting" onclick="orderBy('ksrq')" id="ksrq_order">
										<div class="font-size-12 line-height-18">有效期</div>
										<div class="font-size-9 line-height-18">Validity period</div>
									</th>
									<th class="colwidth-7 sorting" onclick="orderBy('syts')" id="syts_order">
										<div class="font-size-12 line-height-18">剩余(天)</div>
										<div class="font-size-9 line-height-18">Remain(Day)</div>
									</th>
									<th class="colwidth-9 sorting" onclick="orderBy('wbbs')" id="wbbs_order">
										<div class="font-size-12 line-height-18">内/外</div>
										<div class="font-size-9 line-height-18">Inside/Outside</div>
									</th>
									<th class="colwidth-9 sorting" onclick="orderBy('whrid')" id="whrid_order">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
									</th>
									<th class="colwidth-13 sorting" onclick="orderBy('whsj')" id="whsj_order">
										<div class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance Time</div>
									</th>
									<th class="colwidth-10 sorting" onclick="orderBy('jg_dprtname')" id="jg_dprtname_order">
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>
								</tr>
							</thead>
							<tbody id="list" ></tbody>
					</table>
				</div>	
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination" ></div>
				<div class='clearfix'></div>
				</div>	
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/postexpirymonitoring/postexpirymonitoring_main.js"></script><!--当前界面js  -->
</body>
</html>