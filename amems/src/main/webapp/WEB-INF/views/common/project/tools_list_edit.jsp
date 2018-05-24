<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<div id="Tools_list_edit" class="panel panel-primary" >
	<div class="panel-heading bg-panel-heading" >
		<div class="font-size-12 ">工具设备</div>
		<div class="font-size-9">Tools</div>
	</div>
	<div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
		<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" style="display: none;" id="is_tsgjsb_public_div">
			<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
				<div class="font-size-12 margin-topnew-2">特殊工具和设备</div>
				<div class="font-size-9">Special tools and equipment</div>
			</span>
			<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
				<label class='margin-right-5 label-input' ><input  checked="checked"   type="radio" maxlength="8" name="is_tsgjsb_public" value="1" >&nbsp;是&nbsp;&nbsp;</label>
				<label class='label-input' ><input   type="radio" maxlength="8"  name="is_tsgjsb_public" value="0" >&nbsp;否</label>
			</div>
		</div>
		<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 " >
			<span id="left_column" class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-8">
						<div class="font-size-12 margin-topnew-2">工具设备</div>
						<div class="font-size-9">Tools</div>
			</span>
			<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-0 padding-right-0" style="overflow-x: auto;">
			<table class="table table-thin table-bordered table-striped table-hover table-set" style="min-width:1100px">
				<thead>
					<tr>
			   			<th class="colOptionhead" style="text-align:center;vertical-align:middle;width: 50px;">
						   	<button class="line6 line6-btn" onclick="Tools_list_edit.openWinAdd()"  type="button">
								<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
						   	</button>
					    </th>
					    <th class="colwidth-3">
					   		<div class="font-size-12 line-height-12">序号</div>
			           		<div class="font-size-9 line-height-12">No.</div>
				   		</th>
				   		<th class="colwidth-7">
					   		<div class="font-size-12 line-height-12">类型</div>
			           		<div class="font-size-9 line-height-12">Type</div>
				   		</th>
				   		<th class="colwidth-10">
					   		<div class="font-size-12 line-height-12">件号</div>
			           		<div class="font-size-9 line-height-12">P/N</div>
				   		</th>
				   		<th class="colwidth-9">
					   		<div class="font-size-12 line-height-12">型号</div>
			           		<div class="font-size-9 line-height-12">Model</div>
				   		</th>
			   			<th class="colwidth-15">
					   		<div class="font-size-12 line-height-12">名称</div>
			           		<div class="font-size-9 line-height-12">Desc</div>
				   		</th>
				   		<th class="colwidth-13">
					   		<div class="font-size-12 line-height-12">器材代号/IPC No.</div>
			           		<div class="font-size-9 line-height-12">Part No./IPC No.</div>
				   		</th>
			   			<th class="colwidth-5">
				   			<div class="font-size-12 line-height-12">需求数量</div>
		           			<div class="font-size-9 line-height-12">QTY</div>
			   			</th>
			   			<th class="colwidth-5">
				   			<div class="font-size-12 line-height-12">单位</div>
		           			<div class="font-size-9 line-height-12">Unit</div>
			   			</th>
			   			<th class="colwidth-7">
				   			<div class="font-size-12 line-height-12">件号来源</div>
		           			<div class="font-size-9 line-height-12">Source</div>
			   			</th>
			   			<th class="colwidth-7">
				   			<div class="font-size-12 line-height-12">必需性</div>
		           			<div class="font-size-9 line-height-12">Necessity</div>
			   			</th>
			   			<th class="colwidth-13">
				   			<div class="font-size-12 line-height-12">备注</div>
		           			<div class="font-size-9 line-height-12">Remark</div>
			   			</th>
			   			<th class="colwidth-20">
				   			<div class="font-size-12 line-height-12">互换信息</div>
		           			<div class="font-size-9 line-height-12">Exchange Info</div>
			   			</th>
					</tr>
				</thead>
				<tbody id="tools_list">
				</tbody>
			</table>
		</div>
		</div>

		<div class='clearfix'></div>

	</div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/project/tools_list_edit.js"></script>