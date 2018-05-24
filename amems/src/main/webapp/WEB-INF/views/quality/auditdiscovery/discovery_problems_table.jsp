<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<div  class="panel panel-primary" style='margin-bottom:0px;'>
	<div class="panel-heading bg-panel-heading" >
		<div class="font-size-12 ">审核发现问题清单</div>
		<div class="font-size-9">Audit discovery list</div>
	</div>
	<div class="panel-body padding-left-0 padding-right-0">
		<div class="col-lg-12 col-md-12 padding-leftright-8" style="overflow-x: auto;">
		<table class="table table-thin table-bordered table-striped table-hover table-set">
		<thead>
			<tr>
				<th class="colwidth-5 editTable" style="vertical-align: middle; display: table-cell;">
					<div class="font-size-12 line-height-18">状态</div>
					<div class="font-size-9 line-height-18">Status</div>
				</th>
				<th class="colwidth-10">
					<div class="font-size-12 line-height-18">问题编号</div>
					<div class="font-size-9 line-height-18">Problem No.</div>
				</th>
				<th class="">
					<div class="font-size-12 line-height-18">问题描述及依据</div>
					<div class="font-size-9 line-height-18">Audit Findings & Reference</div>
				</th>
				<th class="colwidth-10">
					<div class="font-size-12 line-height-18">问题等级</div>
					<div class="font-size-9 line-height-18">Level</div>
				</th>
				<th class="colwidth-10">
					<div class="font-size-12 line-height-18">问题分类</div>
					<div class="font-size-9 line-height-18">Category</div>
				</th>
				
			</tr>
		</thead>
		<tbody id="problemlist">
	    </tbody>
	    </table>
		</div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/auditdiscovery/discovery_problems_table.js"></script>