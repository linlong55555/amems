<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<div id="ReferenceUtil" class="col-xs-12 padding-left-0 padding-right-0 form-group">
	<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
		<div class="font-size-12 margin-topnew-2">参考文件</div>
		<div class="font-size-9">Reference</div>
	</label>
	<div id="reference_div" class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
		<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
			<table class="table table-thin table-bordered table-striped table-hover table-set" style='min-width:900px;margin-bottom:0px !important'>
				<thead>
					<tr>
		   				<th class="colOptionhead" style="text-align:center;vertical-align:middle;width: 50px;">
					   		<button class="line6 line6-btn" onclick="ReferenceUtil.add()"  type="button">
								<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
					   		</button>
				   		</th>
				   		<th class="colwidth-3">
						   	<div class="font-size-12 line-height-12">序号</div>
				           	<div class="font-size-9 line-height-12">No.</div>
					   	</th>
					   	<th class="colwidth-10">
						   	<div class="font-size-12 line-height-12">文件类型</div>
				           	<div class="font-size-9 line-height-12">File Type</div>
					   	</th>
					   	<th class="colwidth-15">
						   	<div class="font-size-12 line-height-12">来源</div>
				           	<div class="font-size-9 line-height-12">Issued By</div>
					   	</th>
					   	<th class="colwidth-25">
						   	<div class="font-size-12 line-height-12">文件编号</div>
				           	<div class="font-size-9 line-height-12">File No.</div>
					   	</th>
					   	<th class="colwidth-7">
						   	<div class="font-size-12 line-height-12">页码</div>
				           	<div class="font-size-9 line-height-12">Page No.</div>
					   	</th>
					   	<th class="colwidth-5">
						   	<div class="font-size-12 line-height-12">版本</div>
				           	<div class="font-size-9 line-height-12">Rev.</div>
					   	</th>
					   	<th class="colwidth-15">
						   	<div class="font-size-12 line-height-12">颁发日期</div>
				           	<div class="font-size-9 line-height-12">Issue Date</div>
					   	</th>
					   	<th >
						   	<div class="font-size-12 line-height-12">标题/描述</div>
				           	<div class="font-size-9 line-height-12">Title/Desc</div>
					   	</th>
					</tr>
				</thead>
				<tbody id="reference_list">
				</tbody>
			</table>
		</div>
	</div>
	<select style="display: none;" id="wjlxSelect" class="form-control"></select>
	<select style="display: none;" id="wjlySelect" class="form-control"></select>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/reference/reference_list_edit.js"></script>