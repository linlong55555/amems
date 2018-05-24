<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="margin-bottom-0 col-xs-12 padding-left-0 padding-right-0">
	<!-- 搜索框start -->
							<div class=" pull-right padding-left-0 padding-right-0">
							
							<div class="pull-left ">
									<span class="pull-left  text-right padding-left-0 padding-right-0">
									<div class="font-size-12">组织机构</div>
										<div class="font-size-9">Organization</div></span>
									<div class=" padding-left-8 padding-right-0 pull-left" style="width: 180px; margin-right:5px;">
										<select id="destzzjg" class="form-control " name="destzzjg" onchange="searchRevisionDestruction();">
											<c:forEach items="${accessDepartment}" var="type">
												<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
											</c:forEach>
										</select>
									</div>
							</div>
							
							
							
								<div class=" pull-left padding-left-0 padding-right-0 row-height" style="width: 250px;">
									<input type="text" placeholder='销毁单号/备注/销毁人/制单人' id="keyword_search_destruction" class="form-control ">
								</div>
			                    <div class=" pull-right padding-left-5 padding-right-0 ">   
									<button id="requisitionHistorySearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevisionDestruction();">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
			                   		</button>
			                    </div> 
							</div>
	
</div>
<div class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0 table-h" >
	<table id="llls" class="table table-thin table-bordered table-set" style="min-width: 1500px;">
		<thead>
			<tr>
				<th class="fixed-column colwidth-5">
					<div class="font-size-12 line-height-18">操作</div>
					<div class="font-size-9 line-height-18">Operation</div>
				</th>
				<th class="colwidth-10 fixed-column sorting" id="xhdh_destructionOrder" onclick="destructionOrderBy('xhdh')">
					<div class="important">
					<div class="font-size-12 line-height-18">销毁单号</div>
					<div class="font-size-9 line-height-18">Destroy No.</div>
					</div>
				</th>
				<th class="colwidth-10 sorting" id="xhrq_destructionOrder" onclick="destructionOrderBy('xhrq')">
					
						<div class="font-size-12 line-height-18">销毁日期</div>
						<div class="font-size-9 line-height-18">Destroy Date</div>
				</th>
				<th class="sorting colwidth-15" id="xhrid_destructionOrder" onclick="destructionOrderBy('xhrid')">
					<div class="important">
					<div class="font-size-12 line-height-18">销毁人</div>
					<div class="font-size-9 line-height-18">Destroy Person</div>
					</div>
				</th>
				
				<th class="sorting colwidth-25" id="bz_destructionOrder" onclick="destructionOrderBy('bz')">
					<div class="important">
					<div class="font-size-12 line-height-18">备注</div>
					<div class="font-size-9 line-height-18">Remark</div>
					</div>
				</th>
				<th class="sorting colwidth-5" id="zt_destructionOrder" onclick="destructionOrderBy('zt')">
					<div class="font-size-12 line-height-18">状态</div>
					<div class="font-size-9 line-height-18">State</div>
				</th>
				<th class="sorting colwidth-10" id="zdrid_destructionOrder" onclick="destructionOrderBy('zdrid')">
					<div class="important">
					<div class="font-size-12 line-height-18">制单人</div>
					<div class="font-size-9 line-height-18">Creator</div>
					</div>
				</th>
				<th class="sorting colwidth-10" id="zdsj_destructionOrder" onclick="destructionOrderBy('zdsj')">
					<div class="font-size-15 line-height-18">制单时间</div>
					<div class="font-size-9 line-height-18">Create Time</div>
				</th>
				<th class="sorting colwidth-10" id="zdbmid_destructionOrder" onclick="destructionOrderBy('zdbmid')">
					<div class="font-size-12 line-height-18">制单部门</div>
					<div class="font-size-9 line-height-18">Department</div>
				</th>
				<th class="sorting colwidth-10" id="dprtcode_destructionOrder" onclick="destructionOrderBy('dprtcode')">
					<div class="font-size-12 line-height-18">组织机构</div>
					<div class="font-size-9 line-height-18">Organization</div>
				</th>
			</tr>
		</thead>
		<tbody id="destructionList">
		</tbody>
	</table>
</div>
<div class="col-xs-12 text-center page-height" id="historyPagination">
</div>

<script type="text/javascript" src="${ctx}/static/js/thjw/material/destruction/destruction_list.js"></script>
