<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div id="StoppingUtil" class="panel panel-primary" style='margin-bottom:0px;'>
	<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
		<a data-toggle="collapse" data-parent="#accordion" href="#manpowerCollapsed" class="collapsed">
		<div class="pull-left">
		<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
		</div>
		<div class="pull-left">
		<div class="font-size-12">工时/停场时间</div>
		<div class="font-size-9 ">Manpower/Elapsed Hours</div>
		</div>
		<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
		<div class="clearfix"></div>
		</a>
	</div>
	<div id="manpowerCollapsed" class="panel-collapse collapse" >
		<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
			<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
	    <label class="col-lg-1  col-md-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
			<div class="font-size-12 margin-topnew-2">工时/停场时间 </div>
			<div class="font-size-9">Manpower/Elapsed Hrs</div>
		</label>
		<div class="col-lg-11 col-md-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
			<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
						<table  class="table table-thin table-bordered table-striped table-hover table-set text-center" >
							<thead>
								<tr>
								   <th width="50" class="colOptionhead" >
									   <button class="line6 line6-btn" onclick="StoppingUtil.add()"  type="button">
											<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
								   		</button>
								   </th>
								   <th class="colwidth-3">
									   	<div class="font-size-12 line-height-12">序号</div>
							           	<div class="font-size-9 line-height-12">No.</div>
								   	</th>
								    <th>
									   <div class="font-size-12 line-height-12">任务</div>
							           <div class="font-size-9 line-height-12">Task</div>
								   </th>
								   <th>
									   <div class="font-size-12 line-height-12">组别</div>
							           <div class="font-size-9 line-height-12">Group</div>
								   </th>
								   <!-- class='sorting' -->
								   <th >
									   <div class="font-size-12 line-height-12">人数  </div>
							           <div class="font-size-9 line-height-12">People</div>
								   </th>
								   <th >
									   <div class="font-size-12 line-height-12">总工时</div>
							           <div class="font-size-9 line-height-12">Total Manpower</div>
								   </th>
								   
								   <th>
									   <div class="font-size-12 line-height-12">总停场时间</div>
							           <div class="font-size-9 line-height-12">Total Elapsed Hours</div>
								   </th>
								   <!-- class='sorting' -->
								   <th >
									   <div class="font-size-12 line-height-12">备注</div>
							           <div class="font-size-9 line-height-12">Note</div>
								   </th>
								     
								</tr>
							</thead>
							<tbody id="gstcsj_list" >
								
							</tbody>
					</table>
				</div>
				</div>
				</div>
			<div class='clearfix'></div>
			
		</div>
	</div>
	</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/engineering/stopping.js"></script>