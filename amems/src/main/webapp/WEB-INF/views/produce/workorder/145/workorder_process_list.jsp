<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
	<div id="WorkordeProcessUtil" class="panel panel-primary" style='margin-bottom:0px;'>
    	<!-- panel-heading -->
		<div class="panel-heading bg-panel-heading" >
			<div class="font-size-12 ">工序列表 </div>
			<div class="font-size-9">Process list</div>
		</div>
		<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
			<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
	    <label class="col-lg-1  col-md-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
			<div class="font-size-12 margin-topnew-2">工序列表 </div>
			<div class="font-size-9">Process list</div>
		</label>
		<div class="col-lg-11 col-md-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
			<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
						<table  class="table table-thin table-bordered table-striped table-hover table-set text-center" >
							<thead>
								<tr>
								   <th width="50" class="colOptionhead" >
									   <button class="line6 line6-btn" onclick="WorkordeProcessUtil.add()"  type="button">
											<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
								   		</button>
								   </th>
								   <th class="colwidth-8">
									   	<div class="font-size-12 line-height-12">序号</div>
							           	<div class="font-size-9 line-height-12">No.</div>
								   	</th>
								   <th >
									   <div class="font-size-12 line-height-12">工作内容</div>
							           <div class="font-size-9 line-height-12">Content</div>
								   </th>
								   <th class="colwidth-30">
									   <div class="font-size-12 line-height-12">工种</div>
							           <div class="font-size-9 line-height-12">Workcenter</div>
								   </th>
								   <th class="colwidth-10">
									   <div class="font-size-12 line-height-12">工时</div>
							           <div class="font-size-9 line-height-12">Hours</div>
								   </th>
								</tr>
							</thead>
							<tbody id="process_list" >
								
							</tbody>
					</table>
				</div>
				</div>
				</div>
			<div class='clearfix'></div>
		</div>
	</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/workorder/145/workorder_process_list.js"></script>