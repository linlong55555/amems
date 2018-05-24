<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<div id="ReferenceUtilView" class="col-xs-12 padding-left-0 padding-right-0 form-group">
	<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
		<div class="font-size-12 margin-topnew-2">参考文件</div>
		<div class="font-size-9">Reference</div>
	</label>
	<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
		<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 " style="overflow-x: auto;">
			<table class="table table-thin table-bordered table-striped table-hover table-set " style='margin-bottom:0px !important'>
				<thead>
					<tr>
		   				<th class="colOptionheadview" style="text-align:center;vertical-align:middle;width: 50px;">
					   		<button class="line6 line6-btn" onclick="ReferenceUtilView.add()"  type="button">
								<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
					   		</button>
				   		</th>
				   		<th class="colwidth-3">
						   	<div class="font-size-12 line-height-12">序号</div>
				           	<div class="font-size-9 line-height-12">No.</div>
					   	</th>
					     <th class="colwidth-10">
					     	<div class="font-size-12 line-height-18">类型</div>
							<div class="font-size-9 line-height-18">Type</div>
						</th>
					    <th class="colwidth-10">
					        <div class="font-size-12 line-height-18">来源</div>
						    <div class="font-size-9 line-height-18">Issued By</div>
					    </th>
						<th class="colwidth-13">
							<div class="font-size-12 line-height-18">文件编号</div>
							<div class="font-size-9 line-height-18">File No.</div>
						</th>
						<th class="colwidth-20">
							<div class="font-size-12 line-height-18">标题/描述</div>
							<div class="font-size-9 line-height-18">Title/Description</div>
						</th>
						<th class="colwidth-13">
							<div class="font-size-12 line-height-18">颁发日期</div>
							<div class="font-size-9 line-height-18">Issue Date</div>
						</th>
						<th class="colwidth-13">
							<div class="font-size-12 line-height-18">生效日期</div>
							<div class="font-size-9 line-height-18">Effect Date</div>
						</th>
						<th class="colwidth-13">
							<div class="font-size-12 line-height-18">到期日期</div>
						    <div class="font-size-9 line-height-18">Due Date</div>
						</th>
					</tr>
				</thead>
				<tbody id="reference_list_view">
					
				</tbody>
			</table>
		</div>
	</div>
	<select style="display: none;" id="wjlxSelectview" class="form-control"></select>
	<select style="display: none;" id="wjlySelectview" class="form-control"></select>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/assessment/reference_list_view.js"></script><!--参考文件的js  -->