<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<div id="WorkContentUtil" class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
	<table class="table table-thin table-bordered table-striped table-hover table-set" >
		<thead>
			<tr>
			   	<th class="colOptionhead" style="text-align:center;vertical-align:middle;width: 50px;" >
				   	<button class="line6 line6-btn" onclick="WorkContentUtil.add()" type="button">
						<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
				   	</button>
			    </th>
		   		<th class="colwidth-3">
			   		<div class="font-size-12 line-height-12">序号</div>
	           		<div class="font-size-9 line-height-12">No.</div>
		   		</th>
	   			<th >
			   		<div class="font-size-12 line-height-12">工作内容</div>
	           		<div class="font-size-9 line-height-12">Work Content</div>
		   		</th>
	   			<th class="colwidth-13">
		   			<div class="font-size-12 line-height-12">工作者</div>
           			<div class="font-size-9 line-height-12">Worker</div>
	   			</th>
	   			<th class="colwidth-13">
		   			<div class="font-size-12 line-height-12">检查者</div>
           			<div class="font-size-9 line-height-12">Checked By</div>
	   			</th>
			</tr>
		</thead>
		<tbody id="work_content_list">
		</tbody>
	</table>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/project/work_content_list_edit.js"></script>