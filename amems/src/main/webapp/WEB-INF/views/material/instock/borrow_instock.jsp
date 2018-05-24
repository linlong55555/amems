<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<!-- 借入入库 -->
<div class="margin-bottom-0 col-xs-12 padding-left-0 padding-right-0">
	<div class="col-xs-2 col-md-3 padding-left-0 checkPermission" permissioncode='aerialmaterial:instock:main:02'>
		<a href="javascript:void(0);" onclick="borrow.goHandwork();" class="btn btn-primary padding-top-1 padding-bottom-1 pull-left checkPermission" permissioncode="aerialmaterial:requisition:main:01">
			<div class="font-size-12">手工借入</div>
			<div class="font-size-9">Manual Borrow</div>
		</a>
	</div>
	<div class="pull-right padding-left-0 padding-right-0">
		<div class="pull-left padding-left-0 padding-right-0" style="width:250px;">
			<input type="text" class="form-control row-height" id="borrow_keyword_search" placeholder="借入申请单号"/>
		</div>
        <div class="pull-right padding-left-5 padding-right-0">   
            <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="borrow.load();">
				<div class="font-size-12">搜索</div>
				<div class="font-size-9">Search</div>
			</button>
			<button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" onclick="borrow.more();">
				<div class="pull-left text-center">
					<div class="font-size-12">更多</div>
					<div class="font-size-9">More</div>
				</div>
				<div class="pull-left padding-top-5">
				 &nbsp;<i class="font-size-15 icon-caret-down" id="borrow_icon"></i>
				</div>
	   		</button>
        </div>    
	</div>
	<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10 margin-top-10 display-none border-cccccc search-height" id="borrow_divSearch">
		<div class="col-xs-12 col-lg-12 col-sm-12  padding-left-0 padding-right-0">
			<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 margin-bottom-10">
				<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12">借调对象类型</div>
					<div class="font-size-9">Type</div>
				</label>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<select id="borrow_jddxlx" class="form-control " name="borrow_jddxlx" >
						<option value="">显示全部All</option>
					</select>
				</div>
			</div>
			<div class="col-xs-12 col-sm-6 col-lg-3 padding-left-0 padding-right-0 margin-bottom-10">
				<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12">借调对象</div>
					<div class="font-size-9">Seconded obj</div>
				</label>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<select id="borrow_jddx" class="form-control " name="borrow_jddx" >
						<option value="">显示全部All</option>
					</select>
				</div>
			</div>
			<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 line-height-18">组织机构</div>
					<div class="font-size-9 line-height-18">Organization</div>
				</label>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<select id="borrow_dprtcode" fsfs="${user.jgdm}" class="form-control " name="borrow_dprtcode" >
						<c:forEach items="${accessDepartment}" var="type">
							<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="col-lg-3 col-sm-6 col-xs-12 pull-right text-right padding-right-0" style="margin-bottom: 10px;">
				<button type="button"
					class="btn btn-primary padding-top-1 padding-bottom-1"
					onclick="borrow.searchReset();">
					<div class="font-size-12">重置</div>
					<div class="font-size-9">Reset</div>
				</button>
			</div> 
		</div>

	</div>
</div>
<div  class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0 table-h"  style="overflow-x:auto;">
	<table id="jrrk" class="table table-thin table-bordered table-hover table-set" style="min-width:1250px">
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
				<th class="sorting colwidth-10" name="column_sqdh" onclick="borrow.orderBy('sqdh', this)">
					<div class="important"><div class="font-size-12 line-height-18">借入申请单号</div></div>
					<div class="font-size-9 line-height-18">Borrowing No.</div>
				</th>
				<th class="sorting colwidth-13" name="column_jddxid" onclick="borrow.orderBy('jddxid', this)">
					<div class="font-size-12 line-height-18">借调对象</div>
					<div class="font-size-9 line-height-18">Seconded obj</div>
				</th>
				<th  class="sorting colwidth-15" name="column_fjzch" onclick="borrow.orderBy('fjzch', this)">
					<div class="font-size-12 line-height-18">飞机注册号</div>
					<div class="font-size-9 line-height-18">A/C REG</div>
				</th>
				<th class="sorting colwidth-13" name="column_sqrid" onclick="borrow.orderBy('sqrid', this)">
					<div class="font-size-12 line-height-18">申请人</div>
					<div class="font-size-9 line-height-18">Applicant</div>
				</th>
				<th class="sorting colwidth-13" name="column_sqsj" onclick="borrow.orderBy('sqsj', this)">
					<div class="font-size-12 line-height-18">申请日期</div>
					<div class="font-size-9 line-height-18">Application Date</div>
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
		<tbody id="borrow_list">
		</tbody>
	</table>
</div>
<div class=" col-xs-12  text-center page-height" id="borrow_pagination">  
</div>

<script type="text/javascript" src="${ctx}/static/js/thjw/material/instock/borrow_instock.js"></script>