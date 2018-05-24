<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>人员排班</title>
</script>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
<script type="text/javascript">
var id = "${param.id}";
var pageParam = '${param.pageParam}';
</script>

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
					searchSchedule();//调用主列表页查询
				}
			}
		});
	});
</script>
	
</head>
<body class="page-header-fixed">
	<div class="clearfix"></div>
	<div class="page-content table-table-type">
	<input type="hidden" id="fazhi" value="${threshold}" />
	<input type="hidden" id="zzjgid" value="${user.jgdm}" />
		<div class="panel panel-primary">
			<!--导航开始  -->
			<div class="panel-heading" id="NavigationBar"></div>
			<!--导航结束  -->
			<div class="panel-body">
			  <div  class='searchContent  row-height' >
				<div class='margin-top-0'>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12   padding-left-0 padding-right-0  form-group">
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">排班日期</div>
							<div class="font-size-9">Date</div>
						</span>
						<div  class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
							<input type="text" class="form-control " name="date-range-picker" readonly="readonly" id="scheduleDate_search" />
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">基地</div>
							<div class="font-size-9">Station</div>
						</span>
						<div  class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
							<select id="jd" name="jd" class="form-control" onchange="searchSchedule();">
							</select>
						</div>
					</div>
					<div class="col-lg-3 col-md-6 col-sm-6 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" >
						<span  class="col-lg-3 col-md-3 col-sm-3 col-xs-2 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">组织机构</div>
								<div class="font-size-9">Organization</div>
						</span>
						<div  class="col-lg-9 col-md-9 col-sm-9 col-xs-10 padding-left-8 padding-right-0">
							<select id="dprtcode" class="form-control " onchange="onchangeDprtcode();">								
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${type.dprtname}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-lg-3 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-0 form-group" style='padding-left:15px;'>
						<div class=" col-xs-12 input-group input-group-search" >
						    <input type="text" placeholder='机械师/电子师/机械员/电子员'  id="keyword_search" class="form-control">
	                           <div class="input-group-addon btn-searchnew " style="padding-right: 0px !important;">
			                    <button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1" onclick="searchSchedule();" style='margin-right:0px !important;'>
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
		                  		</button>
		                	</div>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class='table_pagination'>
				<!-- 表格 -->
				<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height" c-height="55%" id="tableId" style="overflow-x: auto;">
						<table  id="crewScheduling_check_list_table" class="table table-thin table-bordered table-striped table-hover table-set">
							<thead>
								<tr>
									<th class="fixed-column colwidth-7" >
										<div class="font-size-12 line-height-18">操作</div>
										<div class="font-size-9 line-height-18">Operation</div>
									</th>
									<th class="fixed-column colwidth-9" >
										<div class="font-size-12 line-height-18">日期</div>
										<div class="font-size-9 line-height-18">Date</div>
									</th>
									<th class="colwidth-13" >
										<div class="important">
											<div class="font-size-12 line-height-18">机械师</div>
											<div class="font-size-9 line-height-18">Mechanical Engineer</div>
										</div>
									</th>
									<th class="colwidth-13">
										<div class="important">
											<div class="font-size-12 line-height-18">电子师</div>
											<div class="font-size-9 line-height-18">Electronic Engineer</div>
										</div>
									</th>
									<th class="colwidth-13">
										<div class="important">
											<div class="font-size-12 line-height-18">机械员</div>
											<div class="font-size-9 line-height-18">Mechanical Member</div>
										</div>
									</th>
									<th class="colwidth-13">
										<div class="important">
											<div class="font-size-12 line-height-18">电子员</div>
											<div class="font-size-9 line-height-18">Electronic Member</div>
										</div>
									</th>
									<th class="colwidth-13">
										<div class="important">
											<div class="font-size-12 line-height-18">机械师(备)</div>
											<div class="font-size-9 line-height-18">Mechanical Engineer</div>
										</div>
									</th>
									<th class="colwidth-13">
										<div class="important">
											<div class="font-size-12 line-height-18">电子师(备)</div>
											<div class="font-size-9 line-height-18">Electronic Engineer</div>
										</div>
									</th>
									<th class="colwidth-13">
										<div class="important">
											<div class="font-size-12 line-height-18">机械员(备)</div>
											<div class="font-size-9 line-height-18">Mechanical Member</div>
										</div>
									</th>
									<th class="colwidth-13">
										<div class="important">
											<div class="font-size-12 line-height-18">电子员(备)</div>
											<div class="font-size-9 line-height-18">Electronic Member</div>
										</div>
									</th>
									<th class="colwidth-13">
										<div class="important">
											<div class="font-size-12 line-height-18">MCC调度</div>
											<div class="font-size-9 line-height-18">MCC Dispatch</div>
										</div>
									</th>
									<th class="colwidth-13">
										<div class="important">
											<div class="font-size-12 line-height-18">维护人</div>
											<div class="font-size-9 line-height-18">Maintainer</div>
										</div>
									</th>
									<th class="colwidth-13">
										<div class="font-size-12 line-height-18">维护时间</div>
										<div class="font-size-9 line-height-18">Maintenance time</div>
									</th>
									<th class="colwidth-15">
										<div class="font-size-12 line-height-18">组织机构</div>
										<div class="font-size-9 line-height-18">Organization</div>
									</th>
								</tr>
							</thead>
							<tbody id="list">
							</tbody>
					</table>
				</div>	
				<div class='clearfix'></div>
			</div>	
		</div>
	</div>
</div>	

<!-------修改模态框 start-------->
<input type="hidden"  name="id"  id="id">
<input type="hidden"  name="userid"  id="userid" value="${sessionScope.user.id}" >
<div class="modal fade modal-new" id="xlhExistModal" tabindex="-1" role="dialog" aria-labelledby="xlhExistModal" aria-hidden="true" data-backdrop="static" >
	<div class="modal-dialog" style='width:80%;'>
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
				<h4 class="modal-title" >
					<div class='pull-left'>
						<div class="font-size-14">人员排班</div>
						<div class="font-size-12">Crew Scheduling</div> 
					</div>
					<div class='pull-right' >
				  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					</div>
			  		<div class='clearfix'></div>
				</h4>
			</div>
		 	<div class="modal-body" id="AssignEndModalBody">
 		 		<div class="col-xs-12 margin-top-8 padding-left-10 padding-right-8">
					<div class="panel panel-default padding-right-0">
				        <div class="panel-heading bg-panel-heading">
				        	<div class="font-size-12 ">基础信息</div>
							<div class="font-size-9">Audit Info</div>
					    </div>
						<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
							<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
		       					
		       					<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">排班日期</div>
											<div class="font-size-9 ">Date</div>
									</label>
									<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
										<input type="text"  id="pbrq"  class=" form-control padding-left-3 padding-right-3 "  readonly="readonly"/>
									</div>
								</div>
		       					<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">上次维护信息</div>
											<div class="font-size-9 ">Info</div>
									</label>
									<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
										<input type="text"  id="oldInfo"  class=" form-control padding-left-3 padding-right-3 "  readonly="readonly"/>
									</div>
								</div>
		       					<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">组织机构</div>
											<div class="font-size-9 ">Organization</div>
									</label>
									<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
										<input type="hidden"  name="dprtcodeid"  id="dprtcodeid">
										<input type="text"  id="dprtcodename"  class=" form-control padding-left-3 padding-right-3 "  readonly="readonly"/>
									</div>
								</div>
		       					<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" >
									<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
											<div class="font-size-12 margin-topnew-2">基地</div>
											<div class="font-size-9 ">Station</div>
									</label>
									<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 label-input-div">
										<input type="hidden" id="base">
										<input type="text"  id="basename"  class=" form-control padding-left-3 padding-right-3 "  readonly="readonly"/>
									</div>
								</div>
		   						 <div class="clearfix"></div>
							</div>
						</div>
				   	</div>
					<div class="panel panel-default padding-right-0">
				        <div class="panel-heading bg-panel-heading">
				        	<div class="font-size-12 ">人员信息</div>
							<div class="font-size-9">Audit Info</div>
					    </div>
						<div class="panel-body padding-bottom-0 padding-left-0 padding-right-0  padding-top-0">	 	
							<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
		       					<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">机械师</div>
										<div class="font-size-9 ">Mechanical Eng</div>
									</label>
									<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class="input-group" style="width: 100%">
											<input type="hidden"  name="jxsid"  id="jxsid">
											<input type="text"  name="jxs" class="form-control noteditable readonly-style" id="jxs" readonly="readonly" ondblclick="openUser('jxs')">
											<span class="required input-group-btn" id="wxrybtn" >
												<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="openUser('jxs')">
													<i class="icon-search cursor-pointer" ></i>
												</button>
											</span>
					                	</div>
									</div>
								</div>
		       					<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">机械师(备)</div>
										<div class="font-size-9 ">Mechanical Eng(Spare)</div>
									</label>
									<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class="input-group" style="width: 100%">
											<input type="hidden"  name="jxbyid"  id="jxbyid">
											<input type="text"  name="jxby" class="form-control noteditable readonly-style" id="jxby" readonly="readonly" ondblclick="openUser('jxby')">
											<span class="required input-group-btn" id="wxrybtn" >
												<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="openUser('jxby')">
													<i class="icon-search cursor-pointer" ></i>
												</button>
											</span>
					                	</div>
									</div>
								</div>
		       					<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">机械员</div>
										<div class="font-size-9 ">Mechanical Mem</div>
									</label>
									<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class="input-group" style="width: 100%">
											<input type="hidden"  name="jxyid"  id="jxyid">
											<input type="text"  name="jxy" id="jxy" class="form-control noteditable readonly-style"  readonly="readonly" ondblclick="openUser('jxy')">
											<span class="required input-group-btn" id="wxrybtn" >
												<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="openUser('jxy')">
													<i class="icon-search cursor-pointer" ></i>
												</button>
											</span>
					                	</div>
									</div>
								</div>
		       					<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">机械员(备)</div>
										<div class="font-size-9 ">Mechanical Me(Spare)</div>
									</label>
									<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class="input-group" style="width: 100%">
											<input type="hidden"  name="jxybyid"  id="jxybyid">
											<input type="text"  name="jxyby" id="jxyby" class="form-control noteditable readonly-style"  readonly="readonly" ondblclick="openUser('jxyby')">
											<span class="required input-group-btn" id="wxrybtn" >
												<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="openUser('jxyby')">
													<i class="icon-search cursor-pointer" ></i>
												</button>
											</span>
					                	</div>
									</div>
								</div>
		       					<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">电子师</div>
										<div class="font-size-9 ">Electronic Eng</div>
									</label>
									<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class="input-group" style="width: 100%">
											<input type="hidden"  name="dzsid"  id="dzsid">
											<input type="text"  name="dzs" id="dzs" class="form-control noteditable readonly-style"  readonly="readonly" ondblclick="openUser('dzs')">
											<span class="required input-group-btn" id="wxrybtn" >
												<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="openUser('dzs')">
													<i class="icon-search cursor-pointer" ></i>
												</button>
											</span>
					                	</div>
									</div>
								</div>
		       					<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">电子师(备)</div>
										<div class="font-size-9 ">Electronic Eng(Spare)</div>
									</label>
									<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class="input-group" style="width: 100%">
											<input type="hidden"  name="dzbyid"  id="dzbyid">
											<input type="text"  name="dzby" id="dzby" class="form-control noteditable readonly-style"  readonly="readonly" ondblclick="openUser('dzby')">
											<span class="required input-group-btn" id="wxrybtn" >
												<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="openUser('dzby')">
													<i class="icon-search cursor-pointer" ></i>
												</button>
											</span>
					                	</div>
									</div>
								</div>
		       					<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">电子员</div>
										<div class="font-size-9 ">Electronic Mem</div>
									</label>
									<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class="input-group" style="width: 100%">
											<input type="hidden"  name="dzyid"  id="dzyid">
											<input type="text"  name="dzy" id="dzy" class="form-control noteditable readonly-style"  readonly="readonly" ondblclick="openUser('dzy')">
											<span class="required input-group-btn" id="wxrybtn" >
												<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="openUser('dzy')">
													<i class="icon-search cursor-pointer" ></i>
												</button>
											</span>
					                	</div>
									</div>
								</div>
		       					<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
									<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
										<div class="font-size-12 margin-topnew-2">电子员(备)</div>
										<div class="font-size-9 ">Electronic Mem(Spare)</div>
									</label>
									<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
										<div class="input-group" style="width: 100%">
											<input type="hidden"  name="dzybyid"  id="dzybyid">
											<input type="text"  name="dzyby" id="dzyby" class="form-control noteditable readonly-style"  readonly="readonly" ondblclick="openUser('dzyby')">
										<span class="required input-group-btn" id="wxrybtn" >
											<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="openUser('dzyby')">
												<i class="icon-search cursor-pointer" ></i>
											</button>
										</span>
				                	</div>
								</div>
							</div>
	       					<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 margin-topnew-2">MCC调度</div>
									<div class="font-size-9 ">MCC Scheduling</div>
								</label>
								<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
									<div class="input-group" style="width: 100%">
										<input type="hidden"  name="mccddid"  id="mccddid">
										<input type="text"  name="mccdd" id="mccdd" class="form-control noteditable readonly-style"  readonly="readonly" ondblclick="openUser('mccdd')">
										<span class="required input-group-btn" id="wxrybtn" >
											<button type="button" class="btn btn-default form-control"  style='height:34px;' data-toggle="modal" onclick="openUser('mccdd')">
												<i class="icon-search cursor-pointer" ></i>
										</button>
									</span>
			                	</div>
							</div>
						</div>
       					<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-3 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 margin-topnew-2">状态</div>
								<div class="font-size-9 ">Status</div>
							</label>
							<div class="col-lg-9 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
								<label style=" font-weight:normal" class="pull-left"><input type="radio" name="zt" id="zt" value='1' checked="checked">有效</label>										
								<label style=" font-weight:normal" class="pull-left padding-left-10"><input type="radio" name="zt"  value='0' id="zt" >无效</label>
							</div>
						</div>
  						<div class="clearfix"></div>
					</div>
				</div>
		   	</div>
		   	</div>
		   	</div>
            <div class="clearfix"></div>              
   			<div class="modal-footer ">
				<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="pull-left modalfootertip" >
			                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
						<span class="input-group-addon modalfooterbtn">
							<button id="baocun" type="button" onclick="saveCrewScheduleData();" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
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
<!-------修改模态框  End-------->
<!--日志STATR -->	
<%@ include file="/WEB-INF/views/open_win/log.jsp"%>
<%@ include file="/WEB-INF/views/open_win/logDiff.jsp"%> 
<!--日志END  -->
<script type="text/javascript" src="${ctx }/static/js/thjw/produce/schedule/crewSchedule.js"></script><!--当前页面js  -->
<%@ include file="/WEB-INF/views/open_win/users_tree_multi.jsp"%><!-------用户对话框 -------->
</body>
</html>
