<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<!-- 课程列表start -->
<div class="tab-pane fade" id="bottom_course_div">
	<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0">
		<div style="overflow-x: auto;" class="tab-second-height">
			<!-- start:table -->
			<table id="courseTable" class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:510px">
				<thead>
					<tr>
						<th class="colwidth-3 ">
							<div class="font-size-12 line-height-18">操作</div>
							<div class="font-size-9 line-height-18">Operation</div>
						</th>
						<th class="colwidth-10 ">
							<div class="font-size-12 line-height-18">课程代码</div>
							<div class="font-size-9 line-height-18">Course No.</div>
						</th>
						<th class="colwidth-10 ">									
							<div class="font-size-12 line-height-18">课程名称</div>
							<div class="font-size-9 line-height-18">Course Name</div>
						</th>
						<th class="colwidth-13">
							<div class="font-size-12 line-height-18">飞机机型</div>
							<div class="font-size-9 line-height-18">A/C Type</div>
						</th>
						<th class="colwidth-5 ">									
							<div class="font-size-12 line-height-18">是否复训</div>
							<div class="font-size-9 line-height-18">Whether</div>
						</th>
						<th class="colwidth-15 ">									
							<div class="font-size-12 line-height-18">授权期限</div>
							<div class="font-size-9 line-height-18">Limit</div>
						</th>
					</tr>
				</thead>
				<tbody id="bottom_course_list"><tr><td class="text-center" colspan="5">暂无数据 No data.</td></tr></tbody>
			</table>
			<!-- end:table -->
		</div>
		<div class="clearfix"></div>
	</div>
</div>
<!-- 课程列表end -->
<!-- 附件开start-->
<div class="tab-pane fade" id="attachments_bottom_list_edit">
	<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
</div>
<!-- 附件end -->
<!-- 授课记录start -->
<div class="tab-pane fade" id="bottom_courseInfo_div">
	<div class="col-lg-12 col-xs-12 padding-left-0 padding-right-0">
		<div style="overflow-x: auto;" class="tab-second-height">
			<!-- start:table -->
			<table id="courseTable" class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:510px">
				<thead>
					<tr>
						<th class="colwidth-3 ">
							<div class="font-size-12 line-height-18">课程代码</div>
							<div class="font-size-9 line-height-18">Course No.</div>
						</th>
						<th class="colwidth-5 ">									
							<div class="font-size-12 line-height-18">课程名称</div>
							<div class="font-size-9 line-height-18">Course Name</div>
						</th>
						<th class="colwidth-5 ">									
							<div class="font-size-12 line-height-18">机型</div>
							<div class="font-size-9 line-height-18">A/C Type</div>
						</th>
						<th class="colwidth-3 ">									
							<div class="font-size-12 line-height-18">是否复训</div>
							<div class="font-size-9 line-height-18">Whether</div>
						</th>
						<th class="colwidth-5 ">									
							<div class="font-size-12 line-height-18">授课日期</div>
							<div class="font-size-9 line-height-18">Date</div>
						</th>
						<th class="colwidth-3 ">									
							<div class="font-size-12 line-height-18">状态</div>
							<div class="font-size-9 line-height-18">Status</div>
						</th>
						<th class="colwidth-3 ">									
							<div class="font-size-12 line-height-18">计划课时</div>
							<div class="font-size-9 line-height-18">Plan Of Hours</div>
						</th>
						<th class="colwidth-3 ">									
							<div class="font-size-12 line-height-18">实际课时</div>
							<div class="font-size-9 line-height-18">Actual Hours</div>
						</th>
						<th class="colwidth-3 ">									
							<div class="font-size-12 line-height-18">计划参训人数</div>
							<div class="font-size-9 line-height-18">Plan QTY</div>
						</th>
						<th class="colwidth-3 ">									
							<div class="font-size-12 line-height-18">实际参训人数</div>
							<div class="font-size-9 line-height-18">Actual QTY</div>
						</th>
					</tr>
				</thead>
				<tbody id="bottom_courseInfo_list"><tr><td class="text-center" colspan="10">暂无数据 No data.</td></tr></tbody>
			</table>
			<!-- end:table -->
		</div>
		<div class="clearfix"></div>
	</div>
</div>
<!-- 授课记录end -->