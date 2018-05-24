<%@ page contentType="text/html; charset=utf-8" language="java"  errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>维修人员培训跟踪</title>
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
						searchRevision();//调用主列表页查询
					}
				}
			});
		});
	</script>
	
</head>
<body class="page-header-fixed">
<input type="hidden" id="zzjgid" name="zzjgid" value=${user.jgdm} />
<input type="hidden" id="userId" name="userId" value=${user.id} />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
<script type="text/javascript">
	$(document).ready(function(){
	//导航
	Navigation(menuCode);
	});
	var id = "${param.id}";
	var pageParam = '${param.pageParam}';
</script>
		<!-- BEGIN CONTENT -->
		<div class="page-content">
			<div class="panel panel-primary">
				<div class="panel-heading" id="NavigationBar"></div>
				<div class="panel-body padding-bottom-0">
				<div class='row-height searchContent margin-top-0'>
				<a href="#" onclick="exportExcel()" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="training:tracking:main:01">
					<div class="font-size-12">导出</div>
					<div class="font-size-9">Export</div>
				</a> 
				<div class=" pull-right padding-left-0 padding-right-0" >
		
					<div class="pull-left form-group">
						<span class="pull-left  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">人员</div>
							<div class="font-size-9">Personel</div>
						</span>
						<div class=" padding-left-8 padding-right-0 pull-left" style="width: 150px; margin-right:5px;">
							<input class="form-control " type="text" id="ryKeyword" placeholder='人员编号/人员名称 '>
						</div>
					</div>
					<div class="pull-left form-group">
						<span class="pull-left  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">课程</div>
							<div class="font-size-9">Course</div>
						</span>
						<div class=" padding-left-8 padding-right-0 pull-left" style="width: 150px; margin-right:5px;">
							<input class="form-control " type="text" id="kcKeyword" placeholder='课程编号/课程名称' >
						</div>
					</div>
					<!-- <div class=" pull-left padding-left-0 padding-right-0 form-group" style="width:250px;" >
						<input placeholder='人员编号/人员名称/课程编号/课程名称/培训形式/考核形式/培训讲师/培训机构' id="keyword_search" class="form-control " type="text">
					</div> -->
					
                    <div class=" pull-right padding-left-5 padding-right-0 form-group" >  
                    <div class="col-xs-12 input-group input-group-search text-right">
								<input placeholder='培训形式/考核形式/培训讲师/培训机构' id="keyword_search" class="form-control " type="text" style="width:250px;">
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision();" style='margin-left:5px;'>
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                         <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" id="btn" onclick="search();">
							<div class="pull-left text-center">
								<div class="font-size-12">更多</div>
								<div class="font-size-9">More</div>
							</div>
							<div class="pull-left padding-top-5"> &nbsp;
							<i class="font-size-15 icon-caret-down" id="icon"></i>
							</div>
				   		</button>
				   		</div>
                    </div> 
				</div>
				
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 display-none border-cccccc margin-bottom-10" id="divSearch">
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">上次培训日期</div>
							<div class="font-size-9">Last Training Date</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="sjKsrq" readonly />
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">下次培训日期</div>
							<div class="font-size-9">Next Training Date</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="xcpxrq" readonly />
						</div>
					</div>
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">是否已培训</div>
							<div class="font-size-9">Yes/No Training</div>
						</span>
						<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
							<select class="form-control" id="is_ypx">
								<option value="">显示全部</option>
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
						</div>
					</div>
					
					<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
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
					
					
					<div class="col-lg-3  text-right  pull-right padding-right-0" style="margin-bottom: 10px;">
						<button type="button"class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div> 
				</div>
				<div class="clearfix"></div>
				</div>
					<div class="clearfix"></div>
					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" style="overflow-x: auto;width: 100%;">
						<table id="quality_check_list_table" class="table table-thin table-bordered table-set table-hover" style="min-width: 2600px !important">
							<thead>
							<tr>
							<th  class="fixed-column colwidth-3" >
								<div class="font-size-12 line-height-18">标识</div>
								<div class="font-size-9 line-height-18">Mark</div>
							</th>
							<th class="sorting fixed-column colwidth-8"  onclick="orderBy('rybh')" id="rybh_order">
								<div class="font-size-12 line-height-18" >人员编号</div>
								<div class="font-size-9 line-height-18" >Personnel No.</div>
							</th>
							<th class="sorting colwidth-10 fixed-column" onclick="orderBy('MXM')" id="MXM_order">
								<div class="font-size-12 line-height-18" >人员名称</div>
								<div class="font-size-9 line-height-18" >Name</div>
							</th>
							<th class="sorting colwidth-13" onclick="orderBy('CKCBH')" id="CKCBH_order">
								<div class="font-size-12 line-height-18" >课程编号</div>
								<div class="font-size-9 line-height-18" >Course No.</div>
							</th>
							<th class="sorting colwidth-13" onclick="orderBy('CKCMC')" id="CKCMC_order">
								<div class="font-size-12 line-height-18" >课程名称</div>
								<div class="font-size-9 line-height-18" >Course Name</div>
							</th>
							<th class="sorting colwidth-10" onclick="orderBy('fjjx')" id="fjjx_order">
								<div class="font-size-12 line-height-18" >飞机机型</div>
								<div class="font-size-9 line-height-18" >A/C Type</div>
							</th>
							<th class="sorting colwidth-10" onclick="orderBy('is_ypx')" id="is_ypx_order">
								<div class="font-size-12 line-height-18" >是否已培训</div>
								<div class="font-size-9 line-height-18" >Yes/No Training</div>
							</th>
							<th class="sorting colwidth-15"  onclick="orderBy('sjgw')" id="sjgw_order" >
									<div class="font-size-12 line-height-18" >涉及岗位</div>
									<div class="font-size-9 line-height-18" >Related Posts</div>
							</th>
							<th class="sorting colwidth-10" onclick="orderBy('sj_ksrq')" id="sj_ksrq_order">
									<div class="font-size-12 line-height-18" >上次培训日期</div>
									<div class="font-size-9 line-height-18" >Date</div>
							</th>
							<th class="sorting colwidth-7" onclick="orderBy('CZQZ')" id="CZQZ_order">
									<div class="font-size-12 line-height-18" >复训周期</div>
									<div class="font-size-9 line-height-18" >CYC</div>
							</th>
							<th class="sorting colwidth-7" onclick="orderBy('FXQX')" id="FXQX_order">
									<div class="font-size-12 line-height-18" >复训期限</div>
									<div class="font-size-9 line-height-18" >CYC</div>
							</th>
							<th class="sorting colwidth-10" onclick="orderBy('xcpxrq')" id="xcpxrq_order">
									<div class="font-size-12 line-height-18" >计划下次培训日期</div>
									<div class="font-size-9 line-height-18" >Next Training Date</div>
							</th>
							<th class="sorting colwidth-7" onclick="orderBy('sy')" id="sy_order">
									<div class="font-size-12 line-height-18" >剩余天数</div>
									<div class="font-size-9 line-height-18" >Remain</div>
							</th>
							<th class="sorting colwidth-10" onclick="orderBy('PXJH')" id="PXJH_order">
									<div class="font-size-12 line-height-18" >培训计划</div>
									<div class="font-size-9 line-height-18" >Training Plan</div>
							</th>
							<th class="sorting colwidth-10" onclick="orderBy('pxlb')" id="pxlb_order">
									<div class="font-size-12 line-height-18" >培训类别</div>
									<div class="font-size-9 line-height-18" >Training Category</div>
							</th>
							<th class="sorting colwidth-5" onclick="orderBy('sjks')" id="sjks_order">
									<div class="font-size-12 line-height-18" >课时</div>
									<div class="font-size-9 line-height-18" >HRS</div>
							</th>
							<th class="sorting colwidth-10" onclick="orderBy('pxxs')" id="pxxs_order">
								<div class="important">
									<div class="font-size-12 line-height-18" >培训形式</div>
									<div class="font-size-9 line-height-18" >Training Form</div>
								</div>
							</th>
							<th class="sorting colwidth-10" onclick="orderBy('ksxs')" id="ksxs_order">
								<div class="important">
									<div class="font-size-12 line-height-18" >考核形式</div>
									<div class="font-size-9 line-height-18" >Examination Form</div>
								</div>
							</th>
							<th class="sorting colwidth-10" onclick="orderBy('jsxm')" id="jsxm_order">
								<div class="important">
									<div class="font-size-12 line-height-18" >培训讲师</div>
									<div class="font-size-9 line-height-18" >Lecturer</div>
								</div>
							</th>
							<th class="sorting colwidth-12" onclick="orderBy('pxgh')" id="pxgh_order">
								<div class="important">
									<div class="font-size-12 line-height-18" >培训机构</div>
									<div class="font-size-9 line-height-18" >Training Institution</div>
								</div>
							</th>
							<th class="sorting colwidth-12" onclick="orderBy('cql')" id="cql_order">
									<div class="font-size-12 line-height-18" >考勤率(%)</div>
									<div class="font-size-9 line-height-18" >Attendance Rate(%)</div>
							</th>
							<th class="sorting colwidth-12" onclick="orderBy('cj')" id="cj_order">
									<div class="font-size-12 line-height-18" >考试成绩</div>
									<div class="font-size-9 line-height-18" >Examination Results</div>
							</th>
							<th class="sorting colwidth-7" onclick="orderBy('zs')" id="zs_order">
									<div class="font-size-12 line-height-18" >证书</div>
									<div class="font-size-9 line-height-18" >Certificate</div>
							</th>
							<th class="sorting colwidth-7" onclick="orderBy('khjg')" id="khjg_order">
									<div class="font-size-12 line-height-18" >考核结果</div>
									<div class="font-size-9 line-height-18" >Result</div>
							</th>
							 <th class="sorting colwidth-10" onclick="orderBy('dprtcode')" id="dprtcode_order">
									<div class="font-size-12 line-height-18" >组织机构</div>
									<div class="font-size-9 line-height-18" >Organization</div>
							</th> 
							</tr> 
			         		 </thead>
							<tbody id="planPersonlist">
							</tbody>
						</table>
					</div>
					<div class="col-xs-12 text-center padding-right-0 page-height " id="pagination">
					</div>
					
					<div class="clearfix"></div>
				</div>
			</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/training/tracking/tracking_main.js"></script>
	
</body>
</html>