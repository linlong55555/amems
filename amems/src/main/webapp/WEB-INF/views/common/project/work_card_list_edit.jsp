<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<div id="WorkCardUtil" class="col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group">
	<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
		<div class="font-size-12 margin-topnew-2">相关工卡</div>
		<div class="font-size-9">Work Card</div>
	</label>
	<div id="work_card_div" class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8">
		<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
			<table class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important'>
				<thead>
					<tr>
				   		<th class="colwidth-3 colOptionhead" style="text-align:center;vertical-align:middle;">
						   	<button class="line6 line6-btn" onclick="WorkCardUtil.openWinAdd()"  type="button">
								<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
						   	</button>
					    </th>
				   		<th class="colwidth-3">
						   	<div class="font-size-12 line-height-12">序号</div>
				           	<div class="font-size-9 line-height-12">No.</div>
					   	</th>
					   	<th class="colwidth-15">
						   	<div class="font-size-12 line-height-12">工卡编号</div>
				           	<div class="font-size-9 line-height-12">Work Card No.</div>
					   	</th>
					   	<th class="colwidth-30">
						   	<div class="font-size-12 line-height-12">工卡标题</div>
				           	<div class="font-size-9 line-height-12">Title</div>
					   	</th>
					   	<th class="colwidth-10">
						   	<div class="font-size-12 line-height-12">维修项目</div>
				           	<div class="font-size-9 line-height-12">Project</div>
					   	</th>
					   	<th class="colwidth-13">
						   	<div class="font-size-12 line-height-12">ATA章节号</div>
				           	<div class="font-size-9 line-height-12">ATA</div>
					   	</th>
					   	<th class="colwidth-9">
						   	<div class="font-size-12 line-height-12">工作类别</div>
				           	<div class="font-size-9 line-height-12">Category</div>
					   	</th>
					   	<th class="colwidth-15">
						   	<div class="font-size-12 line-height-12">参考工时</div>
				           	<div class="font-size-9 line-height-12">MHRs</div>
					   	</th>
					   	<th class="colwidth-7">
						   	<div class="font-size-12 line-height-12">专业</div>
				           	<div class="font-size-9 line-height-12">Skill</div>
					   	</th>
					</tr>
				</thead>
				<tbody id="work_card_list">
				</tbody>
			</table>
		</div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/project/work_card_list_edit.js"></script>