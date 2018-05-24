<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
	 	<div class="col-lg-12 padding-left-0 padding-right-0">
		<div class="col-lg-12  ibox-title"  style='padding:0px;height:25px;background:#ececec;'>
			<label class="pull-left" style='height:25px;line-height:25px;padding-left:10px;'>
		         工单管理/Work Order
			</label>
		</div>
		<div  class="col-lg-12 col-md-12 padding-left-0 padding-right-0 " style="overflow-x: auto;min-height:20px;max-height:200px;">
        <table class="table table-thin table-bordered table-hover table-set" style="min-width:950px"  >
			<thead>
				<tr>
					<th class="colwidth-3" >
						<div class="font-size-12 line-height-18">序号</div>
						<div class="font-size-9 line-height-18">No.</div>
					</th>
					<th class="colwidth-10">
				     	<div class="font-size-12 line-height-18">工单类型</div>
					 	<div class="font-size-9 line-height-18">W/O Type</div>
					</th>
					<th class="colwidth-10">
		     		 	<div class="font-size-12 line-height-18">工单编号</div>
					 	<div class="font-size-9 line-height-18">W/O No.</div>
					</th>
					<th th_class="cont-exp7" td_class="workOrderDisplayFile" table_id=""  class="cont-exp7 downward colwidth-13" onclick="CollapseOrExpandUtil.collapseOrExpandAll(this)">
				    	<div class="font-size-12 line-height-18">关联评估单号</div>
						<div class="font-size-9 line-height-18">Assessment NO.</div>
					</th>	
					<th class="colwidth-7"">
					   	<div class="font-size-12 line-height-18">执行分类</div>
						<div class="font-size-9 line-height-18">Category</div>
					</th>	
					<th class="colwidth-7" onclick="orderBy('fjzch')" id="fjzch_order">
					    <div class="font-size-12 line-height-18">飞机注册号</div>
						<div class="font-size-9 line-height-18">A/C REG</div>
					</th>	
					<th class="colwidth-10">
					    <div class="font-size-12 line-height-18">部件件号</div>
						<div class="font-size-9 line-height-18">P/N</div>
					</th>	
					<th class="colwidth-10">
					    <div class="font-size-12 line-height-18">部件序列号</div>
						<div class="font-size-9 line-height-18">S/N</div>
					</th>	
					<th class="colwidth-5">
					    <div class="font-size-12 line-height-18">专业</div>
						<div class="font-size-9 line-height-18">Skill</div>
					</th>	
					<th class="colwidth-13">
					    <div class="font-size-12 line-height-18">ATA章节号</div>
						<div class="font-size-9 line-height-18">ATA</div>
					</th>	
					<th class="colwidth-7">
					    <div class="font-size-12 line-height-18">基地</div>
						<div class="font-size-9 line-height-18">Station</div>
					</th>	
					<th class="colwidth-7">
					    <div class="font-size-12 line-height-18">工单状态</div>
						<div class="font-size-9 line-height-18">State</div>
					</th>	
					<th class="colwidth-10">
					    <div class="font-size-12 line-height-18">工作组</div>
						<div class="font-size-9 line-height-18">WorkGroup</div>
					</th>
					<th class="colwidth-13">
					    <div class="font-size-12 line-height-18">制单人</div>
						<div class="font-size-9 line-height-18">Creator</div>
					</th>	
					<th class="colwidth-13">
					    <div class="font-size-12 line-height-18">制单时间</div>
						<div class="font-size-9 line-height-18">Create Time</div>
					</th>
					<th class="colwidth-10">
					    <div class="font-size-12 line-height-18">组织机构</div>
						<div class="font-size-9 line-height-18">Organization</div>
					</th>
				</tr>
			</thead>
			<tbody id="work_order_list">
			</tbody>
		</table>
    </div>		 
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/project/engineeringcenter/engineering_center_workorder.js"></script>
