<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="margin-bottom-0 col-xs-12 padding-left-0 padding-right-0">
	<div class="pull-right padding-left-0 padding-right-0">
		<div class="pull-left padding-left-0 padding-right-0 row-height" style="width:250px;">
			<input type="text" class="form-control " id="his_keyword_search" placeholder="领用申请单号"/>
		</div>
        <div class="pull-right padding-left-5 padding-right-0">   
       		<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="historySearch();">
				<div class="font-size-12">搜索</div>
				<div class="font-size-9">Search</div>
			</button>
			<button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" id="btn1" onclick="his_more_params();">
				<div class="pull-left text-center">
					<div class="font-size-12">更多</div>
					<div class="font-size-9">More</div>
				</div>
				<div class="pull-left padding-top-5">
				 &nbsp;<i class="font-size-15 icon-caret-down" id="icon1"></i>
				</div>
	   		</button>
    	</div>    
	</div>
	<div class="col-xs-12 triangle  padding-top-10 margin-bottom-0 margin-top-10 display-none border-cccccc search-height" id="divSearch1">
		<div class="col-lg-12  padding-left-0 padding-right-0">
		
			<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0">
				<div
					class="pull-left col-lg-4 col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0 ">
					<div class="font-size-12">状态</div>
					<div class="font-size-9">State</div></div>
					
				<div class="form-group col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
					<select id="sqdzt" class="form-control "  name="zt">
						<option value="">显示全部All</option>
						<option value="1">保存</option>
						<option value="2">提交</option>
						<option value="9">指定结束</option>
						<option value="10">完成</option>
					</select>
				</div>
			</div>
			
			<div class="col-lg-3 col-sm-6 col-xs-12 margin-bottom-10 padding-left-0 padding-right-0">
				<div class="pull-left col-lg-4 col-xs-4 col-sm-4  text-right padding-left-0 padding-right-0">
				<div class="font-size-12">飞机注册号</div>
				<div class="font-size-9">A/C REG</div></div>
				<div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
					<select class='form-control' id='fjzch' name="fjzch" >
						
					</select>
				</div>
			</div>
			
			<div class="col-lg-3 col-sm-6 col-xs-12  padding-left-0  margin-bottom-10  padding-right-0">
				<span class="pull-left col-lg-4 col-xs-4 col-sm-4  text-right padding-left-0 padding-right-0">
				<div class="font-size-12">组织机构</div>
					<div class="font-size-9">Organization</div></span>
				<div class="col-lg-8 col-xs-8 col-sm-8 padding-left-8 padding-right-0">
					<select id="dprtcode1" class="form-control " name="dprtcode" >
						<c:forEach items="${accessDepartment}" var="type">
							<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="col-lg-3 col-sm-6 col-xs-12 pull-right text-right padding-right-0" style="margin-bottom: 10px;">
				<button type="button"
					class="btn btn-primary padding-top-1 padding-bottom-1"
					onclick="historySearchreset();">
					<div class="font-size-12">重置</div>
					<div class="font-size-9">Reset</div>
				</button>
			</div> 
		</div>
	</div>
</div>
<div class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0 table-h" >
	<table id="llls" class="table table-thin table-bordered table-set" style="min-width: 1350px;">
		<thead>
			<tr>
				<th class="fixed-column colwidth-5">
					<div class="font-size-12 line-height-18">操作</div>
					<div class="font-size-9 line-height-18">Operation</div>
				</th>
				<th class="colwidth-3">
					<div class="font-size-12 line-height-18">序号</div>
					<div class="font-size-9 line-height-18">No.</div>
				</th>
				<th class="colwidth-10 sorting" name="column_lysqdh" onclick="historyOrderBy('lysqdh', this)">
					<div class="important">
						<div class="font-size-12 line-height-18">领用申请单号</div>
						<div class="font-size-9 line-height-18">Requisition No.</div>
					</div>
				</th>
				<th class="sorting colwidth-15" name="column_fjzch" onclick="historyOrderBy('fjzch', this)">
					<div class="font-size-12 line-height-18">飞机注册号</div>
					<div class="font-size-9 line-height-18">A/C REG</div>
				</th>
				
				<th class="sorting colwidth-13" name="column_sqrid" onclick="historyOrderBy('sqrid', this)">
					<div class="font-size-12 line-height-18">申请人</div>
					<div class="font-size-9 line-height-18">Applicant</div>
				</th>
				<th class="sorting colwidth-9" name="column_sqrq" onclick="historyOrderBy('sqrq', this)">
					<div class="font-size-12 line-height-18">申请日期</div>
					<div class="font-size-9 line-height-18">Application Date</div>
				</th>
				<th class="colwidth-30">
					<div class="font-size-12 line-height-18">领用原因</div>
					<div class="font-size-9 line-height-18">Cause</div>
				</th>
				<th class="sorting colwidth-7" name="column_zt" onclick="historyOrderBy('zt', this)">
					<div class="font-size-12 line-height-18">状态</div>
					<div class="font-size-9 line-height-18">State</div>
				</th>
				<th class="sorting colwidth-13" name="column_zdrid" onclick="historyOrderBy('zdrid', this)">
					<div class="font-size-12 line-height-18">制单人</div>
					<div class="font-size-9 line-height-18">Creator</div>
				</th>
				<th class="sorting colwidth-13" name="column_zdsj" onclick="historyOrderBy('zdsj', this)">
					<div class="font-size-12 line-height-18">制单时间</div>
					<div class="font-size-9 line-height-18">Create Time</div>
				</th>
				<th class="sorting colwidth-13" name="column_dprtcode" onclick="historyOrderBy('dprtcode', this)">
					<div class="font-size-12 line-height-18">组织机构</div>
					<div class="font-size-9 line-height-18">Organization</div>
				</th>
			</tr>
		</thead>
		<tbody id="historyList">
		</tbody>
	</table>
</div>
<div class="col-xs-12 text-center page-height" id="historyPagination">
</div>

<!-- start指定结束提示框 -->
<div class="modal fade" id="closeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:550px!important;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertModalShutDownBody">
				<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">指定结束</div>
						<div class="font-size-9 ">Assign end</div>
					</div>
					<div class="panel-body padding-top-10 padding-bottom-0" >
						<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">领料申请单号</div>
								<div class="font-size-9 line-height-18">Order No.</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
								<input type="text" class="form-control " id="lysqdh" name="lysqdh" disabled="disabled"/>
								<input type="hidden" class="form-control " id="lysqid" name="lysqid"/>
							</div>
						</div>
						<div id="zdjsrDiv" class="display-none col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">指定结束人</div>
								<div class="font-size-9 line-height-18">Operator</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
								<input class="form-control" id="zdjsr" name="zdjsr" disabled="disabled"/>
							</div>
						</div>
						<div id="zdjssjDiv" class="display-none col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">结束时间</div>
								<div class="font-size-9 line-height-18">Operate Time</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
								<input class="form-control" id="zdjssj" name="zdjssj" disabled="disabled"/>
							</div>
						</div>
						<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
							<label class="col-lg-2 col-sm-2 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18"><span style="color: red" id="showjsyy">*</span>结束原因</div>
								<div class="font-size-9 line-height-18">End Cause</div>
							</label>
							<div class="col-lg-10 col-sm-10 col-xs-10 padding-left-8 padding-right-0">
								<textarea class="form-control" style="height:100px" id="zdjsyy" name="zdjsyy" placeholder='长度最大为150' maxlength="150"></textarea>
							</div>
						</div>
				     	<div class="clearfix"></div>
				     	<div class="text-right margin-top-10 padding-buttom-10 ">
							<button id="shutdownConfirmBtn" type="button" class="display-none btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" onclick="sbShutDown()">
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
	                   		</button>
                    			<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
							</button>
                   		</div><br/>
					 </div> 
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
</div>
<!-- end指定结束提示框 -->

<script type="text/javascript" src="${ctx}/static/js/thjw/material/requisition/requisition_history.js"></script>
