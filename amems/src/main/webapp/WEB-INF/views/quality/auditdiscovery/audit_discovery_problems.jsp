<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/auditdiscovery/audit_discovery_problems.js"></script>
<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x:auto ;">
<table id="basic_education_table" class="table table-thin table-bordered table-striped table-hover table-set">
	<thead>
		<tr>
			<th class="colwidth-7 editTable" style="vertical-align: middle; display: table-cell;" id="basic_education_th">
				<button class="line6" onclick="auditDiscoveryAddProblems.addFinding()" style="padding:0px 6px;">
			    	<i class="icon-plus cursor-pointer color-blue cursor-pointer'"></i>
		        </button>
			</th>
			<th class="">
				<div class="font-size-12 line-height-18">问题描述及依据</div>
				<div class="font-size-9 line-height-18">Audit Findings & Reference</div>
			</th>
			<th class="colwidth-10">
				<div class="font-size-12 line-height-18">问题等级</div>
				<div class="font-size-9 line-height-18">Level</div>
			</th>
			<th class="colwidth-7">
				<div class="font-size-12 line-height-18">问题分类</div>
				<div class="font-size-9 line-height-18">Category</div>
			</th>
			<th class="colwidth-7">
				<div class="font-size-12 line-height-18">问题编号</div>
				<div class="font-size-9 line-height-18">Problem No.</div>
			</th>
		</tr>
	</thead>
	<tbody id="finding_list">
   </tbody>
</table>
</div>