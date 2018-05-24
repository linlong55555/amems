<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="margin-bottom-0 col-xs-12 padding-left-0 padding-right-0">
	<div class="pull-right padding-left-0 padding-right-0">
		<div class="pull-left padding-left-0 padding-right-0" style="width:250px;">
			<input type="text" class="form-control row-height" id="purchase_keyword_search" placeholder="合同号/件号/中英文/厂家件号"/>
		</div>
        <div class="pull-right padding-left-5 padding-right-0">   
            <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="purchase.load();">
				<div class="font-size-12">搜索</div>
				<div class="font-size-9">Search</div>
			</button>
			<button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" onclick="purchase.more();">
				<div class="pull-left text-center">
					<div class="font-size-12">更多</div>
					<div class="font-size-9">More</div>
				</div>
				<div class="pull-left padding-top-5">
				 &nbsp;<i class="font-size-15 icon-caret-down" id="purchase_icon"></i>
				</div>
	   		</button>
        </div>    
	</div>
	<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-top-10 display-none border-cccccc search-height" id="purchase_divSearch">
		<div class="col-xs-12 col-lg-12 col-sm-12  padding-left-0 padding-right-0">
			<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
				<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12">到货日期</div>
					<div class="font-size-9">Arrival Date</div>
				</label>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<input type="text" class="date-range-picker form-control" id="purchase_between_date" readonly />
				</div>
			</div>
			<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 line-height-18">组织机构</div>
					<div class="font-size-9 line-height-18">Organization</div>
				</label>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<select id="purchase_dprtcode" class="form-control " name="dprtcode" >
						<c:forEach items="${accessDepartment}" var="type">
							<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="col-lg-3 col-sm-6 col-xs-12 pull-right text-right padding-right-0" style="margin-bottom: 10px;">
				<button type="button"
					class="btn btn-primary padding-top-1 padding-bottom-1"
					onclick="purchase.searchReset();">
					<div class="font-size-12">重置</div>
					<div class="font-size-9">Reset</div>
				</button>
			</div> 
		</div>

	</div>
</div>
<div  class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0 table-h"  style="overflow-x:scroll;">
	<table id="cgrk" class="table table-thin table-bordered table-hover table-set" style="min-width:1700px">
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
				<th class="sorting colwidth-10" name="column_hth" onclick="purchase.orderBy('hth', this)">
					<div class="important"><div class="font-size-12 line-height-18">合同号</div></div>
					<div class="font-size-9 line-height-18">Order No.</div>
				</th>
				<th class="sorting colwidth-15" name="column_bjh" onclick="purchase.orderBy('bjh', this)">
					<div class="important"><div class="font-size-12 line-height-18">件号</div></div>
					<div class="font-size-9 line-height-18">P/N</div>
				</th>
				<th class=" sorting colwidth-25" name="column_ywms" onclick="purchase.orderBy('ywms', this)">
					<div class="important"><div class="font-size-12 line-height-18">英文名称</div></div>
					<div class="font-size-9 line-height-18">F.Name</div>
				</th>
				<th class=" sorting colwidth-20" name="column_zwms" onclick="purchase.orderBy('zwms', this)">
					<div class="important"><div class="font-size-12 line-height-18">中文名称</div></div>
					<div class="font-size-9 line-height-18">CH.Name</div>
				</th>
				<th class="sorting colwidth-15" name="column_cjjh" onclick="purchase.orderBy('cjjh', this)">
					<div class="important"><div class="font-size-12 line-height-18">厂家件号</div></div>
					<div class="font-size-9 line-height-18">MP/N</div>
				</th>
				<th class="sorting colwidth-10" name="column_hclx" onclick="purchase.orderBy('hclx', this)">
					<div class="font-size-12 line-height-18">航材类型</div>
					<div class="font-size-9 line-height-18">Airmaterial type</div>
				</th>
				<th class="sorting colwidth-13" name="column_gljb" onclick="purchase.orderBy('gljb', this)">
					<div class="font-size-12 line-height-18">管理级别</div>
					<div class="font-size-9 line-height-18">Management level</div>
				</th>
				<th class="sorting colwidth-9" name="column_sl" onclick="purchase.orderBy('sl', this)">
					<div class="font-size-12 line-height-18">到货数量</div>
					<div class="font-size-9 line-height-18">Arrival Num</div>
				</th>
				<th class="sorting colwidth-7" name="column_jldw" onclick="purchase.orderBy('jldw', this)">
					<div class="font-size-12 line-height-18">单位</div>
					<div class="font-size-9 line-height-18">Unit</div>
				</th>
				<th class="sorting colwidth-9" name="column_sqsj2" onclick="purchase.orderBy('sqsj2', this)">
					<div class="font-size-12 line-height-18">到货日期</div>
					<div class="font-size-9 line-height-18">Arrival date</div>
				</th>
				<th class="colwidth-15">
					<div class="font-size-12 line-height-18">组织机构</div>
					<div class="font-size-9 line-height-18">Organization</div>
				</th>
			</tr>
		</thead>
		<tbody id="purchase_list">
		</tbody>
	</table>
</div>
<div class=" col-xs-12  text-center page-height" id="purchase_pagination">
</div>

<script type="text/javascript" src="${ctx}/static/js/thjw/material/instock/purchase_instock.js"></script>