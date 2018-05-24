<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="margin-bottom-0 col-xs-12 padding-left-0 padding-right-0">
	<div class="pull-right padding-left-0 padding-right-0">
		 <div class="col-lg-3 col-sm-3 col-xs-12 padding-left-0 " style="width: 220px;">
			<input type="hidden" id="kcid"/>
			<div
				class="pull-left col-lg-4 col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0 ">
				<div class="font-size-12">状态</div>
				<div class="font-size-9">State</div>
			</div>
			<div class=" col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
				<select id="sqdzt" class="form-control "  name="zt">
					<option value="">显示全部All</option>
					<option value="1">申请</option>
					<option value="2">确认</option>
					<option value="3">归还</option>
					<option value="9">指定结束</option>
					<!-- <option value="10">完成</option> -->
					
				</select>
			</div>
		</div> 
		
	    <div class=" pull-left padding-left-0 padding-right-0" style="width: 220px;">
	        <span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
	        	<div class="font-size-12">组织机构</div>
				<div class="font-size-9">Organization</div>
			</span>
			<div class=" col-xs-8 col-sm-8 padding-left-5 padding-right-0">
					<select id="dprtcode1" class="form-control " name="dprtcode1">
						<c:choose>
							<c:when test="${accessDepartment!=null && fn:length(accessDepartment) > 0}">
							<c:forEach items="${accessDepartment}" var="type">
								<option value="${type.id}"
									<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}
								</option>
							</c:forEach>
							</c:when>
							<c:otherwise>
								<option value="">暂无组织机构 No Organization</option>
							</c:otherwise>
						</c:choose>
					</select>
			</div>
		</div>
		<div class="pull-left padding-left-5 padding-right-0 row-height" style="width:250px;">
			<input type="text" class="form-control " id="his_keyword_search" placeholder="记录单号/中英文/部件号/序列号/ID"/>
		</div>
        <div class="pull-right padding-left-5 padding-right-0">   
       		<button id="requisitionHistorySearch" type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="historySearch();">
				<div class="font-size-12">搜索</div>
				<div class="font-size-9">Search</div>
			</button>
			 
    	</div>    
	</div>
	 
</div>
<div class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0 table-h" >
	<table id="toolUseHistory" class="table table-thin table-bordered text-center table-set" style="min-width: 3000px;">
		<thead>
			<tr>
				 <th class="colwidth-13">
					<div class="important ">
						<div class="font-size-12 line-height-18">记录单号</div>
						<div class="font-size-9 line-height-18">Record No.</div>
					</div>
				</th>
				<th class="sorting colwidth-15" onclick="historyOrderBy('BW002.BJH',this)" name="column_BW002.BJH">
					<div class="important">
						<div class="font-size-12 line-height-18">件号</div>
						<div class="font-size-9 line-height-18">P/N</div>
					</div>
				</th>
				 
				<th class="sorting colwidth-25" onclick="historyOrderBy('D008.YWMS',this)" name="column_D008.YWMS">
					<div class="important">
						<div class="font-size-12 line-height-18">英文名称</div>
					</div>
					<div class="font-size-9 line-height-18">F.Name</div>
				</th>
				<th class="sorting colwidth-20" onclick="historyOrderBy('D008.ZWMS',this)" name="column_D008.ZWMS">
					<div class="important">
						<div class="font-size-12 line-height-18">中文名称</div>
					</div>
					<div class="font-size-9 line-height-18">CH.Name</div>
				</th>
				
				<th class="sorting colwidth-20" onclick="historyOrderBy('BW002.BJXLH',this)" name="column_BW002.BJXLH">
					<div class="important">
					<div class="font-size-12 line-height-18">序列号</div>
					<div class="font-size-9 line-height-18">S/N</div>
					</div>
				</th>
										
				<th class="colwidth-10">
					<div class="font-size-12 line-height-18">型号</div>
					<div class="font-size-9 line-height-18">Model</div>
				</th>
				<th class="colwidth-10">
					<div class="font-size-12 line-height-18">规格</div>
					<div class="font-size-9 line-height-18">Spec</div>
				</th>
				<th   class="sorting colwidth-7" onclick="historyOrderBy('ZT', this)" name="column_ZT">
					<div class="font-size-12 line-height-18">状态</div>
					<div class="font-size-9 line-height-18">State</div>
				</th>
				
				<th class="colwidth-13">
					<div class="font-size-12 line-height-18">借用人</div>
					<div class="font-size-9 line-height-18">Borrow People</div>
				</th>
				<th class="colwidth-13">
					<div class="font-size-12 line-height-18">借用时间</div>
					<div class="font-size-9 line-height-18">Borrow Time</div>
				</th>
				<th class="colwidth-13">
					<div class="font-size-12 line-height-18">借用数量</div>
					<div class="font-size-9 line-height-18">Borrow Quantity</div>
				</th>
				<th class="colwidth-30">
					<div class="font-size-12 line-height-18">借用备注</div>
					<div class="font-size-9 line-height-18">Borrow Remark</div>
				</th>
				
				<th class="colwidth-13">
					<div class="font-size-12 line-height-18">借出人</div>
					<div class="font-size-9 line-height-18">Lender</div>
				</th>
				<th class="colwidth-13">
					<div class="font-size-12 line-height-18">借出时间</div>
					<div class="font-size-9 line-height-18">Lend Time</div>
				</th>
				<th class="colwidth-30">
					<div class="font-size-12 line-height-18">借出备注</div>
					<div class="font-size-9 line-height-18">Lend Remark</div>
				</th>
				
				<th class="colwidth-13">
					<div class="font-size-12 line-height-18">归还人</div>
					<div class="font-size-9 line-height-18">Return Person</div>
				</th>
				<th class="colwidth-13">
					<div class="font-size-12 line-height-18">归还时间</div>
					<div class="font-size-9 line-height-18">Return Time</div>
				</th>
				<th class="colwidth-13">
					<div class="font-size-12 line-height-18">归还数量</div>
					<div class="font-size-9 line-height-18">Return Quantity</div>
				</th>
				<th class="colwidth-30">
					<div class="font-size-12 line-height-18">归还备注</div>
					<div class="font-size-9 line-height-18">Return Remark</div>
				</th>
				 
				<th  class="sorting colwidth-13" onclick="historyOrderBy('WHSJ', this)" name="column_WHSJ">
					<div class="font-size-12 line-height-18">制单时间</div>
					<div class="font-size-9 line-height-18">Create Time</div>
				</th>
				<th   class="sorting colwidth-15" onclick="historyOrderBy('DPRTCODE', this)" name="column_DPRTCODE">
					<div class="font-size-12 line-height-18">组织机构</div>
					<div class="font-size-9 line-height-18">Organization</div>
				</th>
			</tr>
		</thead>
		<tbody id="historyList">
		</tbody>
	</table>
</div>
<div class="col-xs-12 text-center page-height padding-left-0 padding-right-0" id="historyPagination">
</div>
  <script type="text/javascript" src="${ctx}/static/js/thjw/outfield/toolsuse/toolsuse_history.js"></script> 
