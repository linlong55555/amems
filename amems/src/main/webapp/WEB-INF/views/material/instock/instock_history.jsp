<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<!-- 入库历史 -->
<div class="margin-bottom-0 col-xs-12 padding-left-0 padding-right-0">
	<div class="pull-right padding-left-0 padding-right-0">
		<div class="pull-left padding-left-0 padding-right-0" style="width:250px;">
			<input type="text" class="form-control row-height" id="instock_history_keyword_search" placeholder="入库单号"/>
		</div>
        <div class="pull-right padding-left-5 padding-right-0">   
            <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="instock_history.load();">
				<div class="font-size-12">搜索</div>
				<div class="font-size-9">Search</div>
			</button>
			<button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" onclick="instock_history.more();">
				<div class="pull-left text-center">
					<div class="font-size-12">更多</div>
					<div class="font-size-9">More</div>
				</div>
				<div class="pull-left padding-top-5">
				 &nbsp;<i class="font-size-15 icon-caret-down" id="instock_history_icon"></i>
				</div>
	   		</button>
        </div>    
	</div>
	<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10 margin-bottom-10 margin-top-10 display-none border-cccccc search-height" id="instock_history_divSearch">
		<div class="col-xs-12 col-lg-12 col-sm-12  padding-left-0 padding-right-0">
			<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
				<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12">入库类型</div>
					<div class="font-size-9">Type</div>
				</label>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<select id="instock_history_rklx" class="form-control " name="instock_history_rklx" >
						<option value="">显示全部All</option>
					</select>
				</div>
			</div>
			<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
				<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12">入库日期</div>
					<div class="font-size-9">Instock Date</div>
				</label>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<input type="text" class="date-range-picker form-control" id="instock_history_between_date" readonly />
				</div>
			</div>
			<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
				<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12">状态</div>
					<div class="font-size-9">State</div>
				</label>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<select id="instock_history_zt" class="form-control " name="instock_history_zt" >
						<option value="">显示全部All</option>
					</select>
				</div>
			</div>
			<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 line-height-18">组织机构</div>
					<div class="font-size-9 line-height-18">Organization</div>
				</label>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<select id="instock_history_dprtcode" class="form-control " name="instock_history_dprtcode" >
						<c:forEach items="${accessDepartment}" var="type">
							<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="col-lg-3 col-sm-6 col-xs-12 pull-right text-right padding-right-0" style="margin-bottom: 10px;">
				<button type="button"
					class="btn btn-primary padding-top-1 padding-bottom-1"
					onclick="instock_history.searchReset();">
					<div class="font-size-12">重置</div>
					<div class="font-size-9">Reset</div>
				</button>
			</div> 
		</div>

	</div>
</div>
<div id="instock_history_table_div" class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0 table-h"  style="overflow-x:auto;">
	<table class="table table-thin table-bordered table-striped table-hover table-set" id="instock_history_table" style="min-width:1750px !important;">
		<thead>
			<tr>
				<th class="fixed-column colwidth-7">
					<div class="font-size-12 line-height-18">操作</div>
					<div class="font-size-9 line-height-18">Operation</div>
				</th>
				<th class="colwidth-3">
					<div class="font-size-12 line-height-18">序号</div>
					<div class="font-size-9 line-height-18">No.</div>
				</th>
				<th class="colwidth-7" class="sorting" name="instock_history_column_rklx" onclick="instock_history.orderBy('rklx', this)">
					<div class="font-size-12 line-height-18">入库类型</div>
					<div class="font-size-9 line-height-18">Type</div>
				</th>
				<th class="sorting colwidth-10" name="instock_history_column_rkdh" onclick="instock_history.orderBy('rkdh', this)">
					<div class="important"><div class="font-size-12 line-height-18">入库单号</div></div>
					<div class="font-size-9 line-height-18">InStock No.</div>
				</th>
				<th class="sorting colwidth-7" name="instock_history_column_zt" onclick="instock_history.orderBy('zt', this)">
					<div class="font-size-12 line-height-18">状态</div>
					<div class="font-size-9 line-height-18">State</div>
				</th>
				<th class="sorting colwidth-13" name="instock_history_column_jddxms" onclick="instock_history.orderBy('jddxms', this)">
					<div class="font-size-12 line-height-18">借调对象</div>
					<div class="font-size-9 line-height-18">Seconded obj</div>
				</th>
				<th class="sorting colwidth-13" name="instock_history_column_jdfzr" onclick="instock_history.orderBy('jdfzr', this)">
					<div class="font-size-12 line-height-18">借调负责人</div>
					<div class="font-size-9 line-height-18">Seconded operator</div>
				</th>
				<th class="sorting colwidth-15" name="instock_history_column_fjzch" onclick="instock_history.orderBy('fjzch', this)">
					<div class="font-size-12 line-height-18">飞机注册号</div>
					<div class="font-size-9 line-height-18">A/C REG</div>
				</th>
				<th class="sorting colwidth-13" name="instock_history_column_sqrid" onclick="instock_history.orderBy('sqrid', this)">
					<div class="font-size-12 line-height-18">申请人</div>
					<div class="font-size-9 line-height-18">Applicant</div>
				</th>
				<th class="sorting colwidth-13" name="instock_history_column_sqsj2" onclick="instock_history.orderBy('sqsj2', this)">
					<div class="font-size-12 line-height-18">申请时间</div>
					<div class="font-size-9 line-height-18">Application Date</div>
				</th>
				<th class="sorting colwidth-13" name="instock_history_column_rukid" onclick="instock_history.orderBy('rukid', this)">
					<div class="font-size-12 line-height-18">入库人</div>
					<div class="font-size-9 line-height-18">Operator</div>
				</th>
				<th class="sorting colwidth-9" name="instock_history_column_rksj" onclick="instock_history.orderBy('rksj', this)">
					<div class="font-size-12 line-height-18">入库日期</div>
					<div class="font-size-9 line-height-18">Operate date</div>
				</th>
				<th class="colwidth-30">
					<div class="font-size-12 line-height-18">备注</div>
					<div class="font-size-9 line-height-18">Remark</div>
				</th>
				<th class="colwidth-13">
					<div class="font-size-12 line-height-18">组织机构</div>
					<div class="font-size-9 line-height-18">Organization</div>
				</th>
			</tr>
		</thead>
		<tbody id="instock_history_list">
		</tbody>
	</table>
</div>
<div class=" col-xs-12  text-center page-height" id="instock_history_pagination">
</div>

<script type="text/javascript" src="${ctx}/static/js/thjw/material/instock/instock_history.js"></script>