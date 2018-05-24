<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class=" pull-right padding-left-0 padding-right-0">

	<div class=" pull-left padding-left-0 padding-right-0" style="width: 300px;">
		<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
			<div class="font-size-12">出库日期</div>
			<div class="font-size-9">Out Stock Date</div>
		</span>
		<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
			<input type="text" class="form-control " name="date-range-picker" id="outDate" readonly />
		</div>
	</div>

	<div class=" pull-left padding-left-5 padding-right-0" style="width: 250px;">
		<span class="pull-left col-lg-4 col-xs-4 col-sm-4  text-right padding-left-0 padding-right-0">
		<div class="font-size-12">组织机构</div>
		<div class="font-size-9">Organization</div></span>
		<div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
		<select id="dprtcodeOut" class="form-control " name="dprtcode" >
			<c:forEach items="${accessDepartment}" var="type">
				<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
				</c:forEach>
			</select>
		</div>
	</div>

	<div class=" pull-right padding-left-0 padding-right-0">
		<div class=" pull-left padding-left-5 padding-right-0" style="width: 250px;">
		<input class="form-control " id="out_keyword_search" placeholder="出库单号/部件号/中英文/序列号/GRN" type="text">
	</div>
	<div class=" pull-right padding-left-5 padding-right-0 "> 
		<button id="OutStockList" type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchOut();">
				<div class="font-size-12">搜索</div>
				<div class="font-size-9">Search</div>
			</button>
		</div>
	</div>
 
</div>
<div class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0 table-h">
	<table id="outstock" class="table table-thin table-bordered table-set">
		<thead>
			<tr>
				<!-- --出入库单号，人，时间，件号，英，中文，数量，机构 -->
				<th class="sorting colwidth-10" onclick="orderByOut('T2.CKDH',this)" name="column_T2.CKDH">
					<div class="important">
						<div class="font-size-12 line-height-18">出库单号</div>
					</div>
					<div class="font-size-9 line-height-18">OutStock No.</div>
				</th>
				<th class="sorting colwidth-15" onclick="orderByOut('T1.BJH',this)" name="column_T1.BJH">
					<div class="important">
						<div class="font-size-12 line-height-18">件号</div>
					</div>
					<div class="font-size-9 line-height-18">P/N</div>
				</th>
				<th class="sorting colwidth-25" onclick="orderByOut('T1.YWMS',this)" name="column_T1.YWMS">
					<div class="important">
						<div class="font-size-12 line-height-18">英文名称</div>
					</div>
					<div class="font-size-9 line-height-18">F.Name</div>
				</th>
				<th class="sorting colwidth-20" onclick="orderByOut('T1.ZWMS',this)" name="column_T1.ZWMS">
					<div class="important">
						<div class="font-size-12 line-height-18">中文名称</div>
					</div>
					<div class="font-size-9 line-height-18">CH.Name</div>
				</th>
				<th class="colwidth-13">
					<div class="font-size-12 line-height-18">航材管理级别</div>
					<div class="font-size-9 line-height-18">Aircraft Level</div>
				</th>
					
				<th class="sorting colwidth-20" onclick="orderByOut('T1.SN',this)" name="column_T1.SN">
					<div class="important">
					<div class="font-size-12 line-height-18">序列号</div></div>
					<div class="font-size-9 line-height-18">S/N</div>
					</div>
				</th>
				<th class="sorting colwidth-20" onclick="orderByOut('T1.PCH',this)" name="column_T1.PCH">
					<div class="important">
					<div class="font-size-12 line-height-18">批次号</div></div>
					<div class="font-size-9 line-height-18">B/N</div>
					</div>
				</th>
				<th class="sorting colwidth-20" onclick="orderByOut('T1.GRN',this)" name="column_T1.GRN">
					<div class="important">
					<div class="font-size-12 line-height-18">GRN</div></div>
					<div class="font-size-9 line-height-18">GRN</div>
					</div>
				</th>
				
				<th class="sorting colwidth-7" >
				<div class="font-size-12 line-height-18">数量</div>
				<div class="font-size-9 line-height-18">Num</div>
				</th>

				 <th  class="sorting colwidth-13" onclick="orderByOut('T2.CKSJ',this)" name="column_T2.CKSJ">
					<div class="font-size-12 line-height-18">出库日期</div>
					<div class="font-size-9 line-height-18">Out Stock Date</div>
				</th>
				<th  class="sorting colwidth-15" onclick="orderByOut('T2.DPRTCODE',this)" name="column_T2.DPRTCODE">
					<div class="font-size-12 line-height-18">组织机构</div>
					<div class="font-size-9 line-height-18">Organization</div>
				</th>
			</tr>
		</thead>
		<tbody id="list">
		</tbody>
	</table>
</div>
<div class="col-xs-12 text-center page-height" id="PaginationOut">
</div>
  <script type="text/javascript" src="${ctx}/static/js/thjw/material/stock/stock_olist.js"></script> 
