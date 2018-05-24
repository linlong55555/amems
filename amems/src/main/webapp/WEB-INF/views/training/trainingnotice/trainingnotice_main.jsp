<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Home</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%><style type="text/css">
.bootstrap-tagsinput {
  width: 100% !important;
}
</style>
<script>
var id = "${param.id}";
var pageParam = '${param.pageParam}';
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
					searchFjgzRecord();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body >
<input type="hidden" id="zddwid" value="${user.bmdm}" />
<input type="hidden" id="gbrid" value="${sessionScope.user.id}" />
<input type="hidden" id="username" value="${sessionScope.user.username}" />
<input type="hidden" id="realname" value="${sessionScope.user.realname}" />
		<div class="page-content ">
			<div class="panel panel-primary" >
				<div class="panel-heading  "> 
				<div id="NavigationBar"></div>
				</div>
				<div class="panel-body  padding-bottom-0">
				
				<div class="searchContent row-height">
					<div class="margin-top-0">
						<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group"></div>
						<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group ">
							<span class="col-lg-4 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">培训开始日期</div>
								<div class="font-size-9 ">Training Date</div>
							</span>
							<div id="fjzch_search_sel" class="col-lg-8 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
								<input type="text" class="form-control "  name="date-range-picker" id="pxDate_search" readonly />
							</div>
						</div>
						<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group">
							<span class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 ">组织机构</div>
								<div class="font-size-9 ">Organization</div>
							</span>
							<div class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
								<select id="dprtcode" class="form-control ">
									<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}"
											<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}
										</option>
									</c:forEach>
								</select>							
							</div>
						</div>
						<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style="padding-left:15px;">
							<div class="col-xs-12 input-group input-group-search">
								<input type="text" placeholder='课程代码/课程名称/培训地点/讲师' id="keyword" class="form-control ">
								<div class="input-group-addon btn-searchnew">
									<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchFjgzRecord();">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
				
				<div class="clearfix"></div>

				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 table-height" style="margin-top: 10px;overflow-x: scroll" >
					<table class="table table-thin table-bordered   table-set" id="fjgz_record_sheet_table">
						<thead>
							<tr>
								<th class="colwidth-10 "  >
										<div class="font-size-12 line-height-18">通知类型</div>
										<div class="font-size-9 line-height-18">Notification Type</div>
								</th>
								<th class="colwidth-15 sorting"  onclick="orderBy('kcbh')" id="kcbh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">课程代码</div>
										<div class="font-size-9 line-height-18">Course Code</div>
									</div>
								</th>
								<th class="colwidth-15 sorting"  onclick="orderBy('kcmc')" id="kcmc_order">
									<div class="important">
										<div class="font-size-12 line-height-18">课程名称</div>
										<div class="font-size-9 line-height-18">Course Name</div>
									</div>
								</th>
								<th class="colwidth-25 sorting"  onclick="orderBy('jh_ksrq')" id="jh_ksrq_order">
										<div class="font-size-12 line-height-18">培训时间</div>
										<div class="font-size-9 line-height-18">Training Time</div>
								</th>
								<th class="colwidth-15 sorting"  onclick="orderBy('kcdd')" id="kcdd_order">
									<div class="important">
										<div class="font-size-12 line-height-18">培训地点</div>
										<div class="font-size-9 line-height-18">Training Location</div>
									</div>
								</th>
								<th class="colwidth-15 sorting"  onclick="orderBy('pxlb')" id="pxlb_order">
										<div class="font-size-12 line-height-18">培训类别</div>
										<div class="font-size-9 line-height-18">Type</div>
								</th>
								<th class="colwidth-15 sorting"  onclick="orderBy('pxjg')" id="pxjg_order">
										<div class="font-size-12 line-height-18">培训机构</div>
										<div class="font-size-9 line-height-18">Training Institutions</div>
								</th>
								<th class="colwidth-15 sorting"  onclick="orderBy('zrr')" id="zrr_order">
										<div class="font-size-12 line-height-18">主办</div>
										<div class="font-size-9 line-height-18">Host</div>
								</th>
								<th class="colwidth-15 sorting"  onclick="orderBy('pxdx')" id="pxdx_order">
										<div class="font-size-12 line-height-18">培训对象</div>
										<div class="font-size-9 line-height-18">Training Target</div>
								</th>
								<th class="colwidth-15 sorting"  onclick="orderBy('jsxm')" id="jsxm_order">
									<div class="important">
										<div class="font-size-12 line-height-18">讲师</div>
										<div class="font-size-9 line-height-18">Lecturer</div>
									</div>
								</th>
								<th class="colwidth-15 sorting"  onclick="orderBy('fxbs')" id="fxbs_order">
										<div class="font-size-12 line-height-18">初/复训标识</div>
										<div class="font-size-9 line-height-18">Whether</div>
								</th>
								<th class="colwidth-15 sorting"  onclick="orderBy('pxxs')" id="pxxs_order">
										<div class="font-size-12 line-height-18">培训形式</div>
										<div class="font-size-9 line-height-18">Form</div>
								</th>
								<th class="colwidth-15 sorting"  onclick="orderBy('ksxs')" id="ksxs_order">
										<div class="font-size-12 line-height-18">考核形式</div>
										<div class="font-size-9 line-height-18">form</div>
								</th>
								<th class="colwidth-10 sorting"  onclick="orderBy('ks')" id="ks_order">
										<div class="font-size-12 line-height-18">课时</div>
										<div class="font-size-9 line-height-18">Class</div>
								</th>
								<th class="colwidth-10 "  >
										<div class="font-size-12 line-height-18">课件</div>
										<div class="font-size-9 line-height-18">Courseware</div>
								</th>
								<th class="colwidth-15 " >
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th> 
							</tr>
						</thead>
						<tbody id="fjgzjk_list">

						</tbody>
					</table>
				</div>
				<div class=" col-xs-12  text-center page-height padding-left-0 padding-right-0 "  id="fjgzjk_pagination"></div>
				<div class="clearfix"></div>
			</div>
			<div class="clearfix"></div>
			</div>
</div>

<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<%@ include file="/WEB-INF/views/open_win/attachments_list_edit.jsp"%><!-------附件对话框 -------->
<script type="text/javascript" src="${ctx}/static/js/thjw/training/trainingnotice/trainingnotice_main.js"></script>
</body>
</html>