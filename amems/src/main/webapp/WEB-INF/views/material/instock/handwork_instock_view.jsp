<%@ page contentType="text/html; charset=utf-8" language="java" %>
<div class="margin-bottom-10">
	<div id="handwork_view_form">
		<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
			<div class="panel-heading margin-left-16 padding-top-3 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
				<div class="font-size-16 line-height-18">基本信息</div>
				<div class="font-size-9 ">Basic Info</div>
				<input type="hidden" id="instockId" value="${id}"/>
				<input type="hidden" id="operateType" value="${operateType}"/>
			</div>
			<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 line-height-18">申请人</div>
					<div class="font-size-9 line-height-18">Applicant</div>
				</label>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
					<input type="text" class="form-control" name="handwork_view_sqrmc" id="handwork_view_sqrmc"  readonly />
					<input type="hidden" id="handwork_view_sqrid" />
					<input type="hidden" id="handwork_view_sqbmid" />
					<span class="input-group-btn">
						<button onclick="javascript:handwork_view.selectUser1();" class="btn btn-primary">
							<i class="icon-search" ></i>
						</button>
					</span>
				</div>
			</div>
			<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 line-height-18">申请日期</div>
					<div class="font-size-9 line-height-18">Application Date</div>
				</label>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<input type="text" class="form-control date-picker" name="handwork_view_sqsj" id="handwork_view_sqsj" data-date-format="yyyy-mm-dd" readonly />
				</div>
			</div>
			<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 line-height-18">入库人</div>
					<div class="font-size-9 line-height-18">Operator</div>
				</label>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0 input-group">
					<input type="text" class="form-control" name="handwork_view_rkrmc" id="handwork_view_rkrmc"  readonly />
					<input type="hidden" id="handwork_view_rkrid" />
					<input type="hidden" id="handwork_view_rkbmid" />
					<span class="input-group-btn">
						<button onclick="javascript:handwork_view.selectUser();" class="btn btn-primary">
							<i class="icon-search" ></i>
						</button>
					</span>
				</div>
			</div>
			<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 line-height-18">入库日期</div>
					<div class="font-size-9 line-height-18">Operate date</div>
				</label>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<input type="text" class="form-control date-picker" name="handwork_view_rksj" id="handwork_view_rksj" data-date-format="yyyy-mm-dd" readonly />
				</div>
			</div>
				<div class="clearfix"></div>
			<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12">飞机注册号</div>
					<div class="font-size-9">A/C REG</div>
				</label>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<select class="form-control"  name="handwork_view_fjzch" id="handwork_view_fjzch" >
					</select>							
				</div>
			</div>
		
			<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 line-height-18">备注</div>
					<div class="font-size-9 line-height-18">Remark</div>
				</label>
				<div class="col-lg-11 col-sm-10 col-xs-8 padding-left-8 padding-right-0">
					<textarea class="form-control" id="handwork_view_bz" name="handwork_view_bz" maxlength="300" ></textarea>
				</div>
			</div>
		</div>	
	</div>
	<!-- 航材列表 start -->
	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="margin-bottom: 10px;">
		<div class="panel-heading padding-left-16 padding-top-3 padding-bottom-10 col-xs-12 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
			<div class=" pull-left margin-right-10" >
				<div class="font-size-16 line-height-16">入库列表</div>
				<div class="font-size-9 ">InStock list</div>
			</div>	
		</div>
	</div>
	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="overflow-x:auto;">
		<table class="table table-bordered table-striped table-hover table-set" style="min-width:1550px;">
			<thead>
				<tr>
					<th class="colwidth-5" id="handwork_view_operate">
						<button class="line6 " onclick="handwork_view.add();">
							<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
						</button>
					</th>
					<th class="colwidth-3" id='handwork_view_rownum' >
						<div class="font-size-12 line-height-18">序号</div>
						<div class="font-size-9 line-height-18">No.</div>
					</th>
					<th class="colwidth-15">
						<div class="font-size-12 line-height-18">件号</div>
						<div class="font-size-9 line-height-18">P/N</div>
					</th>
					<th class="colwidth-25">
						<div class="font-size-12 line-height-18">英文名称</div>
						<div class="font-size-9 line-height-18">F.Name</div>
					</th>
					<th class="colwidth-20">
						<div class="font-size-12 line-height-18">中文名称</div>
						<div class="font-size-9 line-height-18">CH.Name</div>
					</th>
					<th class="colwidth-13">
						<div class="font-size-12 line-height-18">管理级别</div>
						<div class="font-size-9 line-height-18">Management level</div>
					</th>
					<th class="colwidth-15">
						<div class="font-size-12 line-height-18">仓库</div>
						<div class="font-size-9 line-height-18">Stock</div>
					</th>
					<th class="colwidth-20">
						<div class="font-size-12 line-height-18">库位</div>
						<div class="font-size-9 line-height-18">Storage location</div>
					</th>
					<th class="colwidth-20">
						<div class="font-size-12 line-height-18">序列号/批次号</div>
						<div class="font-size-9 line-height-18">S/N</div>
					</th>
					<th class="colwidth-13">
						<div class="font-size-12 line-height-18">库存成本</div>
						<div class="font-size-9 line-height-18">Cost(RMB:YUAN)</div>
					</th>
					<th class="colwidth-7">
						<div class="font-size-12 line-height-18">数量</div>
						<div class="font-size-9 line-height-18">Num</div>
					</th>
				</tr>
			</thead>
			<tbody id="handwork_view_list">
			</tbody>
		</table>
	</div>
	<div class="clearfix"></div>
	<div class="text-right margin-top-10 margin-bottom-0">
		<button id="handwork_view_submit" style="display:none" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:handwork_view.submit()">
            <div class="font-size-12">提交</div>
			<div class="font-size-9">Submit</div>
		</button>
        <button id="handwork_view_back" class="btn btn-primary padding-top-1 padding-bottom-1" style="display:none" onclick="javascript:window.history.go(-1)">
            <div class="font-size-12">返回</div>
			<div class="font-size-9">Back</div>
		</button>
    </div>
</div>
<%@ include file="/WEB-INF/views/open_win/user.jsp"%>
<%@ include file="/WEB-INF/views/open_win/contract_cost.jsp"%>
<%@ include file="/WEB-INF/views/open_win/material_basic.jsp"%>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/instock/handwork_instock_view.js"></script>
