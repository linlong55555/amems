<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
	var id = "${param.id}";
	var pageParam = '${param.pageParam}';
</script>
<link rel="stylesheet" href="${ctx}/static/lib/cropper/cropper.css"> 
<script type="text/javascript" src="${ctx}/static/lib/cropper/cropper.js"></script>

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
					refreshMaintenancePersonnel();//调用主列表页查询
				}
			}
		});
	});
</script>

</head>
<body class="page-header-fixed">

	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<div class="page-content table-tab-type">
		<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>
			
			<div class="panel-body padding-bottom-0 ">
			    <div class='row-height margin-top-0' >
				<!-- <input type="hidden" id="adjustHeight" value="-8" /> -->
				<button type="button" onclick="goToAddPage()"
					class="btn btn-primary padding-top-1 padding-bottom-1 padding-right-5 checkPermission"
					permissioncode="quality:maintenancepersonnel:add">
					<div class="font-size-12">新增档案</div>
					<div class="font-size-9">Add</div>
				</button>
				
				 <button type="button" onclick="showImportModal()"
					class="btn btn-primary padding-top-1 padding-bottom-1 padding-right-5 checkPermission"
					permissioncode="quality:maintenancepersonnel:import">
					<div class="font-size-12">导入</div>
					<div class="font-size-9">Import</div>
				</button>
				 <button type="button" onclick="exportExcelList()"
					class="btn btn-primary padding-top-1 padding-bottom-1 padding-right-5 checkPermission"
					permissioncode="quality:maintenancepersonnel:export">
					<div class="font-size-12">导出</div>
					<div class="font-size-9">Export</div>
				</button>
				<!--
				<button type="button" onclick="goToAddPage()"
					class="btn btn-primary padding-top-1 padding-bottom-1 padding-right-5">
					<div class="font-size-12">导出</div>
					<div class="font-size-9">Export</div>
				</button> -->
				
				<div class="pull-right padding-left-0 padding-right-0 divSearch">	
					<div class="pull-left" style='margin-right:20px;'>
						<div class="pull-left text-right padding-left-0 padding-right-0 " style='margin-right:8px;'>
							<div class="font-size-12">归属</div>
							<div class="font-size-9">Attribution</div>
						</div>
						<div class="pull-left text-right padding-left-0 padding-right-0">
							<label class="padding-left-5 label-input" style="font-weight:normal;">
							  <input type="checkbox" name="wbbs_search" value="1" checked="checked" onclick="refreshMaintenancePersonnel()"> 内部
							</label>
							<label class="padding-left-5 label-input" style="font-weight:normal;">
							  <input type="checkbox" name="wbbs_search" value="2" checked="checked" onclick="refreshMaintenancePersonnel()"> 外部
							</label>
						</div>
					</div>
					
					<div class="pull-left" style='margin-right:20px;'>
						<div class="pull-left text-right padding-left-0 padding-right-0" style='margin-right:8px;'>
							<div class="font-size-12">状态</div>
							<div class="font-size-9">Status</div>
						</div>
						<div class="pull-left text-right padding-left-0 padding-right-0 " style="vertical-align: bottom;">
							<label class="padding-left-5 label-input" style=";font-weight:normal;">
							  <input type="checkbox" name="zzzt_search" value="1" checked="checked" onclick="refreshMaintenancePersonnel()"> 在职
							</label>
							<label class="padding-left-5 label-input" style="font-weight:normal;">
							  <input type="checkbox" name="zzzt_search" value="0" checked="checked" onclick="refreshMaintenancePersonnel()"> 离职
							</label>
						</div>
					</div>
					
					<!-- 搜索框start -->
					<div class=" pull-right padding-left-0 padding-right-0">
						<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
							<input type="text" placeholder='编号/姓名/单位部门/联系电话/电子邮箱/邮编/政治面貌' id="keyword_search" class="form-control ">
						</div>
	                    <div class=" pull-right padding-left-5 padding-right-0 ">   
							<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="refreshMaintenancePersonnel()">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
	                   		</button>
	                        <button type="button" class="btn btn-primary dropdown-toggle padding-top-1 padding-bottom-1 resizeHeight" onclick="search();">
								<div class="pull-left text-center"><div class="font-size-12">更多</div><div class="font-size-9">More</div></div>
								<div class="pull-left padding-top-5">
								 &nbsp;<i class="font-size-15 icon-caret-down" id="icon"></i>
								</div>
					   		</button>
	                    </div> 
					</div>
					<!-- 搜索框end -->
				<div class='clearfix'></div>
				</div>
				
				
				<div class="col-xs-12 col-lg-12 col-sm-12 triangle  padding-top-10  margin-top-10 display-none border-cccccc searchContent divSearch" id="divSearch">
					
					<div class="col-xs-12 col-sm-6 padding-left-0 padding-right-0 form-group">
						<div class="col-xs-4 col-sm-3  text-right padding-left-0 padding-right-0">
							
							<div class="font-size-12">工龄</div>
							<div class="font-size-9">Marriage</div>
						</div>
						<div class="col-xs-8 col-sm-9 padding-left-8 padding-right-0">
							<label class="label-input" style="font-weight:normal;">
								<input type="checkbox" name="gl_search" value="1" checked="checked"> 1年以下
							</label>
							<label class="label-input" style="font-weight:normal;">
								<input type="checkbox" name="gl_search" value="2" checked="checked"> 1年-5年
							</label>
							<label class="label-input" style="font-weight:normal;">
								<input type="checkbox" name="gl_search" value="3" checked="checked"> 5年以上
							</label>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 padding-left-0 padding-right-0 form-group">
						<div class="col-xs-4 col-sm-3  text-right padding-left-0 padding-right-0">
							
							<div class="font-size-12">性别</div>
							<div class="font-size-9">Marriage</div>
						</div>
						<div class="col-xs-8 col-sm-9 padding-left-8 padding-right-0">
							<label class="label-input" style="font-weight:normal;">
								<input type="checkbox" name="xb_search" value="1" checked="checked">&nbsp;男&nbsp;
							</label>
							<label class="label-input" style="font-weight:normal;">
								<input type="checkbox" name="xb_search" value="2" checked="checked">&nbsp;女&nbsp;
							</label>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 padding-left-0 padding-right-0 form-group">
						<div class="col-xs-4 col-sm-3  text-right padding-left-0 padding-right-0">
							
							<div class="font-size-12">司龄</div>
							<div class="font-size-9">Marriage</div>
						</div>
						<div class="col-xs-8 col-sm-9 padding-left-8 padding-right-0">
							<label class="label-input" style="font-weight:normal;">
								<input type="checkbox" name="sl_search" value="1" checked="checked"> 1年以下
							</label>
							<label class="label-input" style="font-weight:normal;">
								<input type="checkbox" name="sl_search" value="2" checked="checked"> 1年-5年
							</label>
							<label class="label-input" style="font-weight:normal;">
								<input type="checkbox" name="sl_search" value="3" checked="checked"> 5年以上
							</label>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 padding-left-0 padding-right-0 form-group">
						<div class="col-xs-4 col-sm-3  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">婚姻</div>
							<div class="font-size-9">Marriage</div>
						</div>
						<div class="col-xs-8 col-sm-9 padding-left-8 padding-right-0">
							<label class="label-input" style="font-weight:normal;">
								<input type="checkbox" name="jh_search" value="0" checked="checked">&nbsp;未婚&nbsp;
							</label>
							<label class="label-input" style="font-weight:normal;">
								<input type="checkbox" name="jh_search" value="1" checked="checked">&nbsp;已婚&nbsp;
							</label>
						</div>
					</div>
					
					<div class="col-xs-12 col-sm-6 padding-left-0 padding-right-0 form-group">
						<span class="col-xs-4 col-sm-3  text-right padding-left-0 padding-right-0">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="col-xs-8 col-sm-9 padding-left-8 padding-right-0">
							<select id="dprtcode_search" class="form-control " onchange="changeOrganization()">
							   <c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}"
									<c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-lg-2  text-right padding-right-0 pull-right" style="margin-bottom: 10px;">
						<button type="button"
							class="btn btn-primary padding-top-1 padding-bottom-1"
							onclick="searchreset();">
							<div class="font-size-12">重置</div>
							<div class="font-size-9">Reset</div>
						</button>
					</div>
				</div>
				<div class='clearfix'></div>
				</div>
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-10 table-tab-type_table" style="overflow-x:auto">
					<table class="table table-thin table-bordered table-set"  id="maintenancepersonnel_table">
						<thead>
							<tr>
								<th class="colwidth-5 fixed-column">
									<div class="font-size-12 line-height-18">操作</div>
									<div class="font-size-9 line-height-18">Operation</div>
								</th>
								<th class="colwidth-10 sorting fixed-column" onclick="orderBy('rybh')" id="rybh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">人员编号</div>
										<div class="font-size-9 line-height-18">Staff No.</div>
									</div>
								</th>
								<th class="colwidth-10 sorting fixed-column" onclick="orderBy('xm')" id="xm_order">
									<div class="important">
										<div class="font-size-12 line-height-18">姓名</div>
										<div class="font-size-9 line-height-18">Name</div>
									</div>
								</th>
								<th class="colwidth-5 sorting" onclick="orderBy('zzzt')" id="zzzt_order">
									<div class="font-size-12 line-height-18">状态</div>
									<div class="font-size-9 line-height-18">Status</div>
								</th>
								<th class="colwidth-5 sorting" onclick="orderBy('wbbs')" id="wbbs_order">
									<div class="font-size-12 line-height-18">归属</div>
									<div class="font-size-9 line-height-18">Ascription</div>
								</th>
								<!-- <th class="colwidth-7 sorting" onclick="orderBy('cxfz')" id="cxfz_order">
									<div class="font-size-12 line-height-18">诚信分值</div>
									<div class="font-size-9 line-height-18">Reputation</div>
								</th> -->
								<th class="colwidth-10 sorting" onclick="orderBy('szdw')" id="szdw_order">
									<div class="important">
										<div class="font-size-12 line-height-18">单位/部门</div>
										<div class="font-size-9 line-height-18">Department</div>
									</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('dgmc')" id="dgmc_order">
									<div class="font-size-12 line-height-18">岗位</div>
									<div class="font-size-9 line-height-18">Post</div>
								</th>
								<th class="colwidth-5 sorting" onclick="orderBy('xb')" id="xb_order">
									<div class="font-size-12 line-height-18">性别</div>
									<div class="font-size-9 line-height-18">Gender</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('sr')" id="sr_order">
									<div class="font-size-12 line-height-18">出生日期</div>
									<div class="font-size-9 line-height-18">Birthdate</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('cjgzrq')" id="cjgzrq_order">
									<div class="font-size-12 line-height-18">参加工作日期</div>
									<div class="font-size-9 line-height-18">Work Date</div>
								</th>
								<th class="colwidth-12 sorting" onclick="orderBy('rzrq')" id="rzrq_order">
									<div class="font-size-12 line-height-18">入职日期</div>
									<div class="font-size-9 line-height-18">Employment Date</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('zzmm')" id="zzmm_order">
									<div class="important">
										<div class="font-size-12 line-height-18">政治面貌</div>
										<div class="font-size-9 line-height-18">Political Status</div>
									</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('yzbm')" id="yzbm_order">
									<div class="important">
										<div class="font-size-12 line-height-18">邮编</div>
										<div class="font-size-9 line-height-18">Postal Code</div>
									</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('lxdh')" id="lxdh_order">
									<div class="important">
										<div class="font-size-12 line-height-18">联系电话</div>
										<div class="font-size-9 line-height-18">Telephone</div>
									</div>
								</th>
								<th class="colwidth-15 sorting" onclick="orderBy('yxdz')" id="yxdz_order">
									<div class="important">
										<div class="font-size-12 line-height-18">电子邮箱</div>
										<div class="font-size-9 line-height-18">Email</div>
									</div>
								</th>
								<th class="colwidth-10 sorting" onclick="orderBy('is_jh')" id="is_jh_order">
									<div class="font-size-12 line-height-18">婚姻状况</div>
									<div class="font-size-9 line-height-18">Marital Status</div>
								</th>
								<!-- <th class="colwidth-15 sorting" onclick="orderBy('dabh')" id="dabh_order">
									<div class="font-size-12 line-height-18">档案编号</div>
									<div class="font-size-9 line-height-18">File No.</div>
								</th> -->
								<th class="colwidth-13">
									<div class="font-size-12 line-height-18">维护人</div>
									<div class="font-size-9 line-height-18">Editor</div>
								</th>
								<th class="colwidth-15">
									<div class="font-size-12 line-height-18">维护时间</div>
									<div class="font-size-9 line-height-18">Edit Time</div>
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
				<input type="hidden" id="current_id" value=""/>
				<input type="hidden" id="personnelId" value=""/>
				<input type="hidden" id="type" value="3"/>
				<div id="detail_div" class="col-xs-12 widget-body padding-left-0 padding-right-0 padding-top-10 editTable margin-bottom-10" style="display: none;">
                	<ul class="nav nav-tabs tabNew-style ">
                    	<li class="active"><a href="#archive_basic" data-toggle="tab" aria-expanded="true">基本信息 General Info</a></li>
                    	<li class=""><a href="#archive_post" data-toggle="tab" aria-expanded="false">岗位信息 Position</a></li>
                    	<li class=""><a href="#archive_license" data-toggle="tab" aria-expanded="false">执照信息 License</a></li>
                    	<li class=""><a href="#archive_maintenance" data-toggle="tab" aria-expanded="false">维修等级 Maintenance</a></li>
                    	<li class=""><a href="#archive_train" data-toggle="tab" aria-expanded="false">培训记录 Training</a></li>
                    	<li class=""><a href="#archive_award" data-toggle="tab" aria-expanded="false">个人奖惩 Performance</a></li>
                    	<li class=""><a href="#archive_reputation" data-toggle="tab" aria-expanded="false">诚信记录 Reputation</a></li>
                      	<li class=""><a href="#archive_attachment" data-toggle="tab" aria-expanded="false" onclick="loadBasicAttachments()">附件 Attachment</a></li>
                      	<li class=""><a href="" data-toggle="tab" aria-expanded="false" onclick="exportExcel()">导出 Export</a></li>
                    </ul>
                    <div class="tab-content tab-second-height overflow-auto" >
                    	<!----------------------------------- 基本信息begin ---------------------------------->
                    	<div class="tab-pane fade active in " id="archive_basic">
                    		<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_basic.jsp"%>
                      	</div>
                      	<!----------------------------------- 基本信息end ---------------------------------->
                      	
                      	<!----------------------------------- 岗位信息begin ---------------------------------->
                      	<div class="tab-pane fade " id="archive_post">
                      		<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_post.jsp"%>
                      	</div>
                      	<!----------------------------------- 岗位信息end ---------------------------------->
                      	
                      	<!----------------------------------- 执照信息begin ---------------------------------->
	                    <div class="tab-pane fade " id="archive_license">
	                    	<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_license.jsp"%>
	                    </div>
	                    <!----------------------------------- 执照信息end ---------------------------------->
	                    
	                    <!----------------------------------- 维修等级begin ---------------------------------->
	                    <div class="tab-pane fade" id="archive_maintenance">
	                    	<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_maintenance.jsp"%>
	                    </div>
	                    <!----------------------------------- 维修等级end ---------------------------------->
	                    
	                    <!----------------------------------- 培训记录begin ---------------------------------->
	                    <div class="tab-pane fade " id="archive_train">
	                    	<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_train.jsp"%>
	                    </div>
	                    <!----------------------------------- 培训记录end ---------------------------------->
	                    
	                    <!----------------------------------- 个人奖惩begin ---------------------------------->
	                    <div class="tab-pane fade " id="archive_award">
	                    	<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_award.jsp"%>
	                    </div>
	                    <!----------------------------------- 个人奖惩end ---------------------------------->
	                    
	                    <!----------------------------------- 诚信记录begin ---------------------------------->
	                    <div class="tab-pane fade " id="archive_reputation">
	                    	<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_reputation.jsp"%>
	                    </div>
	                    <!----------------------------------- 诚信记录end ---------------------------------->
                      	
                      	<!----------------------------------- 附件begin ---------------------------------->
                      	<div class="tab-pane fade " id="archive_attachment">
                      		<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_attachment.jsp"%>
                      	</div>
                      	<!----------------------------------- 附件end ---------------------------------->
                  </div>
              </div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
    <%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
    <link href="${ctx}/static//js/tool/jquery-upload-file-master/css/uploadfile.css" rel="stylesheet">
	<script src="${ctx}/static//js/tool/jquery-upload-file-master/jquery.uploadfile.min.js"></script>	
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/maintenancepersonnel/maintenancepersonnel_main.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/maintenancepersonnel/maintenancepersonnel_obj.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/thjw/quality/maintenancepersonnel/maintenancepersonnel_attachment_common.js"></script>
    <link rel="stylesheet" href="${ctx}/static/js/thjw/quality/maintenancepersonnel/maintenancepersonnel.css"> 
    <%@ include file="/WEB-INF/views/open_win/import.jsp"%>
   	<script src="${ctx}/static/js/thjw/common/preview.js"></script>
   	<script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script>
</body>
</html>