<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>

	<div id="license_div" >
		<!-- 维修执照 -->
		<div  class="col-xs-12 padding-left-0 padding-right-0 form-group">
			<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
				<div class="font-size-12">维修执照</div>
				<div class="font-size-9 ">License-holding</div>
			</label>
		    <div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" style="overflow-x:auto ;">
				<table id="maintenance_license_table" class="table table-thin table-bordered table-striped table-hover table-set">
					<thead>
						<tr>
							<th class="colwidth-10">
								<div class="font-size-12 line-height-18">颁发单位</div>
								<div class="font-size-9 line-height-18">Issuing Unit</div>
							</th>
							<th class="colwidth-10">
								<div class="font-size-12 line-height-18">专业</div>
								<div class="font-size-9 line-height-18">Profession</div>
							</th>
							<th class="colwidth-10">
								<div class="font-size-12 line-height-18">执照号</div>
								<div class="font-size-9 line-height-18">License No.</div>
							</th>
							<th class="colwidth-10">
								<div class="font-size-12 line-height-18">颁发日期</div>
								<div class="font-size-9 line-height-18">Date Of Issue</div>
							</th>
							<th class="colwidth-15">
								<div class="font-size-12 line-height-18">有效期</div>
								<div class="font-size-9 line-height-18">Period Of Validity</div>
							</th>
							<th class="colwidth-8" >
								<div class="font-size-12 line-height-18">附件</div>
								<div class="font-size-9 line-height-18">Attachment</div>
							</th>
						</tr>
					</thead>
					<tbody id="maintenance_license_list">
						<tr class="non-choice"><td class="text-center" colspan="6">暂无数据 No data.</td></tr>
					</tbody>
				</table>
			</div>
		
		</div>
		<!--  机型执照-->
		<div class="clearfix"></div>
		
		<div  class="col-xs-12 padding-left-0 padding-right-0 form-group">
			<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
				<div class="font-size-12">机型信息</div>
				<div class="font-size-9 ">A/C Type Info</div>
	    	</label>
		    <div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8" style="overflow-x:auto ;">
				<table id="type_license_table" class="table table-thin table-bordered table-striped table-hover table-set">
					<thead>
						<tr>
							<th class="colwidth-10">
								<div class="font-size-12 line-height-18">颁发单位</div>
								<div class="font-size-9 line-height-18">Issuing Unit</div>
							</th>
							<th class="colwidth-10">
								<div class="font-size-12 line-height-18">机型</div>
								<div class="font-size-9 line-height-18">Type</div>
							</th>
							<th class="colwidth-10">
								<div class="font-size-12 line-height-18">专业</div>
								<div class="font-size-9 line-height-18">Profession</div>
							</th>
							<th class="colwidth-8">
								<div class="font-size-12 line-height-18">维护级别</div>
								<div class="font-size-9 line-height-18">Level</div>
							</th>
							<th class="colwidth-10">
								<div class="font-size-12 line-height-18">颁发日期</div>
								<div class="font-size-9 line-height-18">Date Of Issue</div>
							</th>
							<th class="colwidth-15">
								<div class="font-size-12 line-height-18">有效期</div>
								<div class="font-size-9 line-height-18">Period Of Validity</div>
							</th>
							<th class="colwidth-8" >
								<div class="font-size-12 line-height-18">附件</div>
								<div class="font-size-9 line-height-18">Attachment</div>
							</th>
						</tr>
					</thead>
					<tbody id="type_license_list">
						<tr class="non-choice"><td class="text-center" colspan="7">暂无数据 No data.</td></tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/postapplication/maintenancepersonnel_license.js"></script>