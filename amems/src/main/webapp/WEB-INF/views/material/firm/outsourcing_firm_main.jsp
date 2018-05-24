<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>供应商主数据</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<%@ include file="/WEB-INF/views/open_win/import.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		//回车事件控制
		$(this).keydown(function(event) {
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				var winId = $(".modal:visible").eq($(".modal:visible").length-1).attr("id"); //子查询窗口
				if(formatUndefine(winId) != ""){
					$("#"+winId+" button[name='keyCodeSearch']").trigger('click'); //调用当前窗口的查询
				}else{
					searchRevision();//调用主列表页查询
				}
			}
		});
	});
</script>
 
</head>
<body class="page-header-fixed">
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="" />
	<input type="hidden" id="gyslb" value="${gyslb}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			     <div  class='searchContent row-height' >
				<div class="col-xs-3 col-md-3 padding-left-0 form-group">
					<a href="javascript:void(0);" class="btn btn-primary padding-top-1 padding-bottom-1 margin-right-10 pull-left checkPermission" permissioncode="aerialmaterial:outsourcingfirm:main:01" onclick="openWinAdd()">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</a> 
					<button type="button"
						class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission" permissioncode="aerialmaterial:outsourcingfirm:main:04"
						onclick="showImportModal()">
						<div class="font-size-12">导入</div>
						<div class="font-size-9">Import</div>
					</button>
					<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 checkPermission" permissioncode='aerialmaterial:outsourcingfirm:main:export' onclick="exportExcel()">
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
					</button>
				</div>
			
				<!-- 搜索框start -->
				<div class=" pull-right padding-left-0 padding-right-0 form-group">
					<div class=" pull-left padding-left-0 padding-right-0" style="width: 450px;">
						<input type="text" placeholder='供应商编号/供应商名称/供应商简称/批准号/地址/授权范围/证书说明/备注/维护人' id="keyword_search" class="form-control ">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                        <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" id="btn" onclick="search();">
							<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
							<div class="pull-left padding-top-5">
							 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
							</div>
				   		</button>
                    </div> 
				</div>
				<!-- 搜索框end -->
				
				<div class="col-lg-12 col-sm-12 col-xs-12 triangle  padding-top-10 margin-bottom-10 display-none border-cccccc margin-top-0" id="divSearch">
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">授权开始日期</div>
							<div class="font-size-9">Start Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="sqksrq_search" readonly />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">授权截止日期</div>
							<div class="font-size-9">End Date</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="sqjsrq_search" readonly />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">维护日期</div>
							<div class="font-size-9">Operate Time</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<input type="text" class="form-control" name="date-range-picker" id="whrq_search" readonly />
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-10 ">
						<span class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-8 padding-left-8 padding-right-0">
							<select id="dprtcode_search" name="dprtcode_search" class="form-control ">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="clearfix"></div>
					
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				
				<div class="clearfix"></div>
                </div>
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" style="overflow-x: scroll;">
					<table id="supplier_main_table" class="table table-thin table-bordered table-set" style="min-width: 1400px;">
						<thead>                              
							<tr>
								<th class="fixed-column colwidth-7">
									<div class="font-size-12 line-height-18" >操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('gysbm')" id="gysbm_order">
									<div class="important">
										<div class="font-size-12 line-height-18">供应商编号</div>
										<div class="font-size-9 line-height-18">Supplier No.</div>
									</div>
								</th>
								<th class="colwidth-20 sorting" onclick="orderBy('gysmc')" id="gysmc_order">
									<div class="important">
										<div class="font-size-12 line-height-18">供应商名称</div>
										<div class="font-size-9 line-height-18">Supplier Name</div>
									</div>
								</th>
							     <th class="colwidth-20 sorting" onclick="orderBy('gysjc')" id="gysjc_order">
									<div class="important">
										<div class="font-size-12 line-height-18">供应商简称</div>
										<div class="font-size-9 line-height-18">Supplier Abbreviation</div>
									</div>
								</th>
								<th class="colwidth-7 sorting" onclick="orderBy('pzh')" id="pzh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">批准号</div>
										<div class="font-size-9 line-height-18">APR No.</div>
									</div>
								</th>
								<th class="colwidth-30 sorting" onclick="orderBy('dz')" id="dz_order">
									<div class="important">
										<div class="font-size-12 line-height-18">地址</div>
										<div class="font-size-9 line-height-18">Address</div>
									</div>
								</th>
								<th class="colwidth-20 sorting" onclick="orderBy('sqfw')" id="sqfw_order">
									<div class="important">
										<div class="font-size-12 line-height-18">授权范围</div>
										<div class="font-size-9 line-height-18">Scope</div>
									</div>
								</th>
								<th class="colwidth-30 sorting" onclick="orderBy('zssm')" id="zssm_order">
									<div class="important">
										<div class="font-size-12 line-height-18">证书说明</div>
										<div class="font-size-9 line-height-18">Document Remark</div>
									</div>
								</th>
								<th class="colwidth-9 sorting" onclick="orderBy('sqkssj')" id="sqkssj_order">
									<div class="font-size-12 line-height-18">授权开始日期</div>
									<div class="font-size-9 line-height-18">Start Date</div>
								</th>
								<th class="colwidth-9 sorting" onclick="orderBy('sqjssj')" id="sqjssj_order">
									<div class="font-size-12 line-height-18">授权截止日期</div>
									<div class="font-size-9 line-height-18">End Date</div>
								</th>
								<th class="colwidth-9 sorting" onclick="orderBy('SYTS')" id="SYTS_order">
									<div class="font-size-12 line-height-18">剩余天数</div>
									<div class="font-size-9 line-height-18">Remain(Day)</div>
								</th>
								<th class="colwidth-30 sorting" onclick="orderBy('bz')" id="bz_order">
									<div class="important">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('username')" id="username_order">
									<div class="important">
										<div class="font-size-12 line-height-18">维护人</div>
										<div class="font-size-9 line-height-18">Maintainer</div>
									</div>
								</th>
								<th class="colwidth-13 sorting" onclick="orderBy('cjsj')" id="cjsj_order">
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Maintenance Time</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('dprtcode')" id="dprtcode_order">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="list"></tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>

<!-------供应商详情对话框 Start-------->
	
<div class="modal fade modal-new" id="alertForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false>
	<div class="modal-dialog" style="width:65%;">
		<div class="modal-content">
		 	<div class="modal-header modal-header-new" >
				<h4 class="modal-title" >
                      	<div class='pull-left'>
                     		<div class="font-size-14" id="modalHeadCN">新增</div>
						<div class="font-size-12" id="modalHeadENG">Add</div>
			  		</div>
			  		<div class='pull-right' >
					  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					</div>
			  		<div class='clearfix'></div>
               	</h4>
       		</div>
       		
			<div class="modal-body padding-bottom-0" id="alertBody">
			<div class="col-xs-12 margin-top-8">
		               <div class="input-group-border">
		               	<form id="form">
		               	    <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red" id="gysbmmark">*</span>供应商编号</div>
										<div class="font-size-9 line-height-18">Supplier No.</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
										<input type="text" class="form-control " id="gysbm" name="gysbm" maxlength="50" />
										<input type="hidden" class="form-control " id="id" />
									</div>
								</div>
							
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18"><span style="color: red" id="gysmcmark">*</span>供应商名称</div>
										<div class="font-size-9 line-height-18">Supplier Name</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
										<input type="text" class="form-control " id="gysmc" name="gysmc" maxlength="100" />
									</div>
								</div>
													
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">供应商简称</div>
										<div class="font-size-9 line-height-18">Supplier Abbre</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
										<input class="form-control" id="gysjc" name="gysjc" type="text" />
									</div>
								</div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">批准号</div>
										<div class="font-size-9 line-height-18">APR No.</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
										<input type="text" class="form-control " id="pzh" name="pzh" maxlength="100" />
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">授权开始日期</div>
										<div class="font-size-9 line-height-18">Start Date</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
										<input class="form-control date-picker" id="sqkssj" name="sqkssj" data-date-format="yyyy-mm-dd" type="text" />
									</div>
								</div>
							
								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">授权截止日期</div>
										<div class="font-size-9 line-height-18">End Date</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
										<input class="form-control date-picker" id="sqjssj" name="sqjssj" data-date-format="yyyy-mm-dd" type="text" />
									</div>
								</div>

								<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
									<label  class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 line-height-18">本企业代码</div>
										<div class="font-size-9 line-height-18">Code</div>
									</label>
									<div class="col-lg-8 col-sm-8 col-xs-8 padding-leftright-8">
										<input type="text" class="form-control" id="bqydm" name="bqydm" maxlength="100" >
									</div>
								</div>
								<div class="clearfix"></div>

							 	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
										<div class="font-size-12 line-height-18">授权范围</div>
										<div class="font-size-9 line-height-18">Scope</div>
									</label>
									<div class="col-lg-11 col-sm-10 col-xs-8 padding-leftright-8">
										<textarea class="form-control" id="sqfw" name="sqfw" maxlength="1000" ></textarea>
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								 	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
										<div class="font-size-12 line-height-18">证书说明</div>
										<div class="font-size-9 line-height-18">Document</div>
									</label>
									<div class="col-lg-11 col-sm-10 col-xs-8 padding-leftright-8">
										<textarea class="form-control" id="zssm" name="zssm" maxlength="1000" ></textarea>
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
										<div class="font-size-12 line-height-18">地址</div>
										<div class="font-size-9 line-height-18">Scope</div>
									</label>
									<div class="col-lg-11 col-sm-10 col-xs-8 padding-leftright-8">
										<textarea class="form-control" id="dz" name="dz" maxlength="1000" ></textarea>
									</div>
								</div>
								
								<div class="clearfix"></div>
								
								<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0 ">
										<div class="font-size-12 line-height-18">备注</div>
										<div class="font-size-9 line-height-18">Remark</div>
									</label>
									<div class="col-lg-11 col-sm-10 col-xs-8 padding-leftright-8">
										<textarea class="form-control" id="bz" name="bz" maxlength="300" ></textarea>
									</div>
								</div>
								<div class="clearfix"></div>
		               	</form>
		               </div>
		               <div class='clearfix'></div>
			           <%@ include file="/WEB-INF/views/material/firm/contact_person_list_edit.jsp"%><!-- 加载联系人信息 -->
			           
			           <div id="attachments_list_edit" style="display:none">
			           	<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
					   </div><!-- 加载附件信息 -->
					   
		             </div>
		        <div class="clearfix"></div>
			</div>
			<div class="modal-footer">
	           	<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
							<i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
	                    <span class="input-group-addon modalfooterbtn">
	                    	<button id="supplierSave" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="javascript:save();">
			                   	<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
			              	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
			              		<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
	                    </span>
	               	</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-------供应商详情对话框 End-------->
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<script type="text/javascript" src="${ctx}/static/js/thjw/material/firm/outsourcing_firm_main.js"></script>
</body>
</html>