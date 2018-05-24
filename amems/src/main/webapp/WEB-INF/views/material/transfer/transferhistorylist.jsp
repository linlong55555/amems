<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript"
	src="${ctx}/static/js/thjw/material/transfer/transferhistorylist.js">
</script>
<div  class="tab-pane fade in active" style="padding-left:15px;padding-right:15px;" id="#transferHistory" >
	<div class=" col-xs-12 padding-left-0 padding-right-0 ">
	   <div class=" pull-right padding-left-0 padding-right-0">
			<input type="hidden" id="zzjgid" name="zzjgid" value="${user.jgdm}" />
			<div class=" pull-left padding-left-0 padding-right-0 row-height" style="width: 250px;">
				<input type="text" class="form-control " id="keyword_search1" placeholder="移库单号/件号/批次号/序列号"/>
			</div>
           	<div class="pull-right padding-left-5 padding-right-0">   
           		<button id="hctransferHistory" type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="searchRevision1();">
					<div class="font-size-12">搜索</div>
					<div class="font-size-9">Search</div>
				</button>
				<button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" id="btn" onclick="search1();">
					<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
					<div class="pull-left padding-top-5">
					  &nbsp;<i class="font-size-15 icon-caret-down" id="icon1"></i>
					</div>
   				</button>
            </div>
		</div>
		<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10  margin-top-10  display-none border-cccccc search-height" id="divSearch1">
		     <div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 ">
				<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
				<div class="font-size-12">移库日期</div>
					<div class="font-size-9">Transfer Date</div></span>
				<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
				  <input type="text" class="form-control " name="date-range-picker" id="ykrq_search" readonly />
				</div>
			</div>
			<div class="col-lg-3 col-sm-6 col-xs-12   padding-left-0 padding-right-0 margin-bottom-10  ">
				<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12">组织机构</div>
					<div class="font-size-9">Organization</div>
				</span>
				<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
					<select id="dprtcode1" class="form-control " name="dprtcode1" onchange="changeSelectedPlane()">
						<c:forEach items="${accessDepartment}" var="type">
							<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}</option>
						</c:forEach>
					</select>
				</div>
			</div>
	
			<div class="col-lg-2 pull-right text-right padding-right-0" style="margin-bottom: 10px;">
				<button type="button"class="btn btn-primary padding-top-1 padding-bottom-1"
					onclick="searchreset1();">
					<div class="font-size-12">重置</div>
					<div class="font-size-9">Reset</div>
				</button>
			</div> 
		</div>
	</div> 
	<div class="col-xs-12  padding-left-0 padding-right-0 padding-top-0 table-h" style="border:1px red solid;overflow-x: scroll;"> 
		<table id="transferhistory_table" class="table-set table table-thin table-bordered table-striped table-hover" style="min-width: 2800px !important">
			<thead>
				<tr>
					<th class="fixed-column colwidth-5">
						<div class="font-size-12 line-height-18">操作</div>
						<div class="font-size-9 line-height-18">Operation</div>
					</th>
					<th class="sorting fixed-column colwidth-13" onclick="orderBy1('ykdh',this)" name="column_ykdh">
						<div class="important">
						<div class="font-size-12 line-height-18">移库单号</div>
						</div>
						<div class="font-size-9 line-height-18">Transfer No.</div>
					</th>
					<th class="sorting colwidth-10" onclick="orderBy1('bjh',this)" name="column_bjh">
						<div class="important">
						<div class="font-size-12 line-height-18">件号</div>
						<div class="font-size-9 line-height-18">P/N</div>
						</div>
					</th>
					<th class="sorting colwidth-13" onclick="orderBy1('ywms',this)" name="column_ywms">
						<div class="font-size-12 line-height-18">英文名称</div>
						<div class="font-size-9 line-height-18">F.Name</div>
					</th>
					<th class="sorting colwidth-13" onclick="orderBy1('zwms',this)" name="column_zwms">
						<div class="font-size-12 line-height-18">中文名称</div>
						<div class="font-size-9 line-height-18">CH.Name</div>
					</th>
					<th class="colwidth-13">
						<div class="important">
						<div class="font-size-12 line-height-18">序列号</div>
						<div class="font-size-9 line-height-18">S/N</div>
						</div>
					</th>
					<th class="colwidth-13">
						<div class="important">
						<div class="font-size-12 line-height-18">批次号</div>
						<div class="font-size-9 line-height-18">B/N</div>
						</div>
					</th>
					<th class="sorting colwidth-10" onclick="orderBy1('ys_Cklb',this)" name="column_ys_Cklb">
						<div class="font-size-12 line-height-18">原始仓库类别</div>
						<div class="font-size-9 line-height-18">O-Type</div>
					</th>
					<th class="sorting colwidth-7" onclick="orderBy1('ys_Ckh',this)" name="column_ys_Ckh">
						<div class="font-size-12 line-height-18">原始仓库号</div>
						<div class="font-size-9 line-height-18">O-Store</div>
					</th>
					<th class="sorting colwidth-10" onclick="orderBy1('ys_Ckmc',this)" name="column_ys_Ckmc">
						<div class="font-size-12 line-height-18">原始仓库名称</div>
						<div class="font-size-9 line-height-18">O-Name</div>
					</th>
					<th class="sorting colwidth-7" onclick="orderBy1('ys_Kwh',this)" name="column_ys_Kwh">
						<div class="font-size-12 line-height-18">原始库位号</div>
						<div class="font-size-9 line-height-18">O-Storage</div>
					</th>
					<th class="sorting colwidth-10" onclick="orderBy1('mb_Cklb',this)" name="column_mb_Cklb">
						<div class="font-size-12 line-height-18">目标仓库类别</div>
						<div class="font-size-9 line-height-18">T-Type</div>
					</th>
					<th class="sorting colwidth-7" onclick="orderBy1('mb_Ckh',this)" name="column_mb_Ckh">
						<div class="font-size-12 line-height-18">目标仓库号</div>
						<div class="font-size-9 line-height-18">T-Store</div>
					</th>
					<th class="sorting colwidth-10" onclick="orderBy1('mb_Ckmc',this)" name="column_mb_Ckmc">
						<div class="font-size-12 line-height-18">目标仓库名称</div>
						<div class="font-size-9 line-height-18">T-Name</div>
					</th>
					<th class="sorting colwidth-7" onclick="orderBy1('mb_Kwh',this)" name="column_mb_Kwh">
						<div class="font-size-12 line-height-18">目标库位号</div>
						<div class="font-size-9 line-height-18">T-Storage</div>
					</th>
					<th class="sorting colwidth-5" onclick="orderBy1('mb_Sl',this)" name="column_mb_Sl">
						<div class="font-size-12 line-height-18">目标数量</div>
						<div class="font-size-9 line-height-18">T-Num</div>
					</th>
					<th class="sorting colwidth-5" onclick="orderBy1('zt',this)" name="column_zt">
						<div class="font-size-12 line-height-18">状态</div>
						<div class="font-size-9 line-height-18">State</div>
					</th>
					<th class="sorting colwidth-10" onclick="orderBy1('ykrid',this)" name="column_ykrid">
						<div class="font-size-12 line-height-18">移库人</div>
						<div class="font-size-9 line-height-18">Transferer</div>
					</th>
					<th class="sorting colwidth-7" onclick="orderBy1('ykrq',this)" name="column_ykrq">
						<div class="font-size-12 line-height-18">移库日期</div>
						<div class="font-size-9 line-height-18">Transfer Date</div>
					</th>
					<th class="sorting colwidth-30" onclick="orderBy1('ykyy',this)" name="column_ykyy">
						<div class="font-size-12 line-height-18">移库原因</div>
						<div class="font-size-9 line-height-18">Transfer Reason</div>
					</th>
					<th class="sorting colwidth-10" onclick="orderBy1('zdrid',this)" name="column_zdrid">
						<div class="font-size-12 line-height-18">制单人</div>
						<div class="font-size-9 line-height-18">Creator</div>
					</th>
					<th class="sorting colwidth-13" onclick="orderBy1('zdsj',this)" name="column_zdsj">
						<div class="font-size-12 line-height-18">制单时间</div>
						<div class="font-size-9 line-height-18">Create Time</div>
					</th>
					<th class="sorting colwidth-13" onclick="orderBy1('dprtcode',this)" name="column_dprtcode">
						<div class="font-size-12 line-height-18">组织机构</div>
						<div class="font-size-9 line-height-18">Organization</div>
					</th>
				</tr>
			</thead>
			<tbody id="list1">
			</tbody>
		</table>
	</div>
	<div class=" col-xs-12 page-height text-center"  id="pagination1">
   </div>
</div>

