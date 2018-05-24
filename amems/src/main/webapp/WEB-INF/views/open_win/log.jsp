<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/log.js"></script>
<div class="fudong_icon" id="floatIcon">
	<a href="#">
	<i class="icon-list"></i>
	</a>
</div> 
<div class="fudong"  id="floatDiv" style="display: none;">
	<div class=" panel-primary">
		<div class="panel-heading  padding-top-3">
			<div class="font-size-16 line-height-18">日志列表</div>
			<div class="font-size-9 ">Log Record</div>
		</div>
		<div class="clearfix"></div>
	 
		<div class=" pull-right padding-left-0 padding-right-0">
			<div class=" pull-left padding-left-0 padding-right-0 padding-top-5" style="width: 200px;">
					 <span
						class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0"><div
							class="font-size-12">组织机构</div>
						<div class="font-size-9">Organization</div></span>
					<div
						class=" col-xs-8 col-sm-8 padding-left-8  padding-right-0">
						<select id="log_dprtcode" class="form-control " name="log_dprtcode"
								>
								<c:choose>
									<c:when test="${accessDepartment!=null && fn:length(accessDepartment) > 0}">
									<c:forEach items="${accessDepartment}" var="type">
										<option value="${type.id}"
											<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}
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
						
			<div class=" pull-left padding-left-5 padding-right-0 padding-top-5 row-height" style="width: 150px;">
				<input placeholder="关键词" id="log_keyword" class="form-control " type="text">
			</div>
            <div class=" pull-right padding-left-5 padding-right-0 padding-top-5 margin-right-5">   
				<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="logModal.search();">
					<div class="font-size-12">搜索</div>
					<div class="font-size-9">Search</div>
             	</button>
           </div> 
		</div>
		<div id="logTableDiv" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-5" style="overflow-y: auto;">
			<table  id="log_list_table" class="table table-thin table-bordered text-center table-set" style="min-width: 300px; ">
				<thead>
					<tr id="log_list_tr">
						
					</tr>	 
				</thead>
				<tbody id="log_list_row">
				</tbody>
			</table>
		</div>

		 
	</div>
	<div class="col-xs-12 text-center padding-right-10" id="log_pagination"  style=" position: absolute; bottom: 0;">
	</div>
				
</div>