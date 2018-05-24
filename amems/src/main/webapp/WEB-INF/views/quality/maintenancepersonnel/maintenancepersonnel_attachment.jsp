<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>

	<div id="attachment_attachment_div">
		<div name="personnelInfo" class='margin-top-8'>
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="pull-left text-right padding-left-0 padding-right-0">
					<div class="font-size-12 line-height-18">人员编号</div>
					<div class="font-size-9 line-height-18">Staff No.</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<span style="font-size: 16px;line-height: 30px;" name="rybh_feedback"></span>
				</div>
			</div>
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="pull-left text-right padding-left-0 padding-right-0">
					<div class="font-size-12 line-height-18">姓名</div>
					<div class="font-size-9 line-height-18">Name</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<span style="font-size: 16px;line-height: 30px;" name="xm_feedback"></span>
				</div>
			</div>
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="pull-left text-right padding-left-0 padding-right-0">
					<div class="font-size-12 line-height-18">单位/部门</div>
					<div class="font-size-9 line-height-18">Department</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<span style="font-size: 16px;line-height: 30px;" name="szdw_feedback"></span>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
		<!-- 附件 -->
		<div  class="panel panel-primary">
			<div class="panel-heading bg-panel-heading" >
				<div class="font-size-12">附件</div>
				<div class="font-size-9 ">Attachment</div>
		    </div>
	    <div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
	    <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x:auto ;">
			<table id="attachment_attachment_table" class="table table-thin table-bordered table-striped table-hover table-set text-center">
				<thead>
					<tr>
						<th class="editTable" style="vertical-align: middle;width:45px;">
							<div id="fileuploader" class="col-lg-2 col-sm-2 col-xs-12 "  style="margin-left: 5px ;padding-left: 0"></div>
						</th>
						<th class="colwidth-10">
							<div class="font-size-12 line-height-18">类型</div>
							<div class="font-size-9 line-height-18">Type</div>
						</th>
						<th class="colwidth-30">
							<div class="font-size-12 line-height-18">文件说明</div>
							<div class="font-size-9 line-height-18">Description</div>
						</th>
						<th class="colwidth-10">
							<div class="font-size-12 line-height-18">文件大小</div>
							<div class="font-size-9 line-height-18">File Size</div>
						</th>
						<th class="colwidth-10">
							<div class="font-size-12 line-height-18">上传人</div>
							<div class="font-size-9 line-height-18">Uploader</div>
						</th>
						<th class="colwidth-10">
							<div class="font-size-12 line-height-18">上传时间</div>
							<div class="font-size-9 line-height-18">Upload Time</div>
						</th>
					</tr>
				</thead>
				<tbody id="attachment_attachment_list" name="filelist">
					<tr class="non-choice"><td class="text-center" colspan="5">暂无数据 No data.</td></tr>
				</tbody>
			</table>
		</div>
		</div>
		</div>
	</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/maintenancepersonnel/maintenancepersonnel_attachment.js"></script>