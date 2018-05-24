<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
 <div class="panel panel-primary" id="PublicationUtil">
	<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
		<a data-toggle="collapse" data-parent="#accordion" href="#pulicationCollapsed" class="collapsed">
		<div class="pull-left">
		<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
		</div>
		<div class="pull-left">
		<div class="font-size-12">受影响的出版物 </div>
		<div class="font-size-9 ">Pulication Affected</div>
		</div>
		<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
		<div class="clearfix"></div>
		</a>
	</div>
	<div id="pulicationCollapsed" class="panel-collapse collapse" >
		<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
		    <div class='col-xs-12 padding-left-0 padding-right-0'>
		    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">是否影响出版物</div>
					<div class="font-size-9">Pulications Affected</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<label class='margin-right-5 label-input' ><input  checked="checked"  type="radio" maxlength="8" name="is_yxcbw_public" value="1" onchange="showPulAffected(1)">&nbsp;是&nbsp;&nbsp;</label>
					<label class='label-input' ><input   type="radio" maxlength="8"  name="is_yxcbw_public" value="0"  onchange="showPulAffected(0)">&nbsp;否</label>
				</div>
			</div>
		    </div>
			<div class="col-xs-12 padding-left-0 padding-right-0 form-group" id="pulAffectedList">
			    <label class="col-lg-1  col-md-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">受影响的出版物</div>
					<div class="font-size-9">Pulication Affected</div>
				</label>
				<div class="col-lg-11 col-md-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
					<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
								<table  class="table table-thin table-bordered table-striped table-hover table-set text-center" >
									<thead>
										<tr>
										   <th width="50" class="colOptionhead" id="syxcbwcolOptionhead">
											   <button class="line6 line6-btn" onclick="PublicationUtil.add()"  type="button">
													<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
										   		</button>
										   </th>
										   <th class="colwidth-3">
											   	<div class="font-size-12 line-height-12">序号</div>
									           	<div class="font-size-9 line-height-12">No.</div>
										   	</th>
										   <th>
											   <div class="font-size-12 line-height-12">类型</div>
									           <div class="font-size-9 line-height-12">Type</div>
										   </th>
										   <!-- class='sorting' -->
										   <th >
											   <div class="font-size-12 line-height-12">文件号 </div>
									           <div class="font-size-9 line-height-12">Doc No.</div>
										   </th>
										   <th >
											   <div class="font-size-12 line-height-12">说明</div>
									           <div class="font-size-9 line-height-12">Description</div>
										   </th>
										</tr>
									</thead>
									<tbody  id="syxcbw_list">
										
									</tbody>
							</table>
						</div>
				  </div>
				</div>
			<div class='clearfix'></div>
			
		</div>
	</div>
	</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/engineering/publication.js"></script>
<script type="text/javascript">
	function showPulAffected(flag){
		PublicationUtil.rowCount = 0;
		if(flag==1){
			$("#pulAffectedList").css("display", "inline");
			$("#syxcbw_list").append("<tr><td colspan='5' style='text-align:center;vertical-align:middle;' >暂无数据 No data.</td></tr>");
		}else{
			$("#pulAffectedList").css("display", "none");
			$("#syxcbw_list").empty();
		}
	}
</script>
