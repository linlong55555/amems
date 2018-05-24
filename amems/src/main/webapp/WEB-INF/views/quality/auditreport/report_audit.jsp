<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="report_audit_alert_Modal" tabindex="-1" role="dialog"  aria-labelledby="report_audit_alert_Modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
      <div class="modal-dialog" style='width:75%;'>
            <div class="modal-content">
				<div class="modal-header modal-header-new" >
					<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-14" id="modalHeadCN">质量审核</div>
							<div class="font-size-12" id="modalHeadENG">Quality audit</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
        		</div>
            <div class="modal-body" >
              	<div class="col-xs-12 margin-top-8 ">
              	<div class="input-group-border">
              	 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">被审核单位</div>
								<div class="font-size-9 ">Audited unit</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class='input-group' style='width:100%'>
						    <input type="text" id="zjh_search" class="form-control readonly-style" ondblclick="work_card_main.openChapterWin()"  maxlength="20"  readonly/>
							<span class="input-group-btn">
								<button type="button" id="zjh_search_btn" class="btn btn-default" data-toggle="modal" onclick="work_card_main.openChapterWin()">
									<i class="icon-search cursor-pointer"></i>
								</button>
							</span>
							</div>
						</div>
				  </div>
				  <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核报告编号</div>
								<div class="font-size-9 ">Audit report no.</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" maxlength="10" />
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核日期</div>
								<div class="font-size-9 ">Date</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" maxlength="10" name='date-picker'/>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">类型</div>
								<div class="font-size-9 ">Type</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<select class='form-control'>
							<option>内部审核/Internal</option>
							<option>外部审核/External</option>
							</select>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核类别</div>
								<div class="font-size-9 ">Category</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<select class='form-control'>
							<option>计划审核/Routine</option>
							<option>非计划审核/Non-routine</option>
							</select>
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">状态</div>
								<div class="font-size-9 ">Status</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<input type="text" class="form-control" maxlength="10" />
						</div>
					</div>
					<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">分发</div>
								<div class="font-size-9 ">Distribution</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div class='input-group' style='width:100%'>
						    <input type="text"  class="form-control readonly-style"  maxlength="20"  readonly/>
							<span class="input-group-btn">
								<button type="button" class="btn btn-default" data-toggle="modal" >
									<i class="icon-search cursor-pointer"></i>
								</button>
							</span>
						</div>
						</div>
					</div>
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  padding-top-0 padding-left-0 padding-right-8  margin-bottom-10">
						<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12">审核组</div>
							<div class="font-size-9">Department</div>
						</span>
						<div  class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-8 ">
							<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x:auto ;">
							<table id="basic_education_table" class="table table-thin table-bordered table-striped table-hover table-set">
								<thead>
									<tr>
										<th class="colwidth-10 editTable" style="vertical-align: middle; display: table-cell;">
											
										</th>
										<th class="colwidth-10 editTable">
											<div class="font-size-12 line-height-18">姓名</div>
											<div class="font-size-9 line-height-18">Name</div>
										</th>
										<th >
											<div class="font-size-12 line-height-18">单位</div>
											<div class="font-size-9 line-height-18">Section</div>
										</th>
										<th >
											<div class="font-size-12 line-height-18">职务</div>
											<div class="font-size-9 line-height-18">TiTle</div>
										</th>
									</tr>
								</thead>
								<tbody id="dept_info_list">
								    <tr id="04d754d3-0222-421e-afbf-ca0d92761cfc">
										<td style="text-align: center;vertical-align:middle;">
											组长
										</td>
										<td></td>
										<td></td>
										<td></td>
	                               </tr>
									<tr>
									    <td rowspan='2'  style="text-align: center;vertical-align:middle;" id='members_td'>成员
									    <button class="line6" onclick="addMembers('members_td','dept_info_list')" style="padding:0px 6px;">
										    <i class="icon-plus cursor-pointer color-blue cursor-pointer'"></i>
									    </button>
									    </td>
										<td >
										<button class="line6" title="删除 Delete" style="padding:0px 6px;visibility:hidden;margin-right:5px;" >
												<i class="fa glyphicon glyphicon-minus color-blue cursor-pointer"></i>
											</button>
										小魏
										</td>
										<td></td>
										<td></td>
	                               </tr>
	                               <tr >
										<td >
											<button class="line6" title="删除 Delete" style="padding:0px 6px;margin-right:5px;" onclick='removeTr(this,"members_td")'>
												<i class="fa glyphicon glyphicon-minus color-blue cursor-pointer"></i>
											</button>
											甘清
										</td>
										<td></td>
										<td></td>
	                               </tr>
                               </tbody>
							</table>
						</div>
						</div>	
					</div>
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核目的</div>
								<div class="font-size-9 ">Audit purpose</div>
						</label>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<textarea class="form-control" rows="2" cols="34" maxlength="100"></textarea>
						</div>
					</div>
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核范围</div>
								<div class="font-size-9 ">Scope of audit</div>
						</label>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<textarea class="form-control"  rows="2" cols="34" maxlength="100"></textarea>
						</div>
					</div>
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核依据</div>
								<div class="font-size-9 ">Audit basis</div>
						</label>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<textarea class="form-control"  rows="2" cols="34" maxlength="100"></textarea>
						</div>
					</div>
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核概述</div>
								<div class="font-size-9 ">Overview of audit</div>
						</label>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<textarea class="form-control"  rows="2" cols="34" maxlength="100"></textarea>
						</div>
					</div>
					<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label  class="col-lg-2 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12  margin-topnew-2">审核结论</div>
								<div class="font-size-9 ">Audit conclusion</div>
						</label>
						<div class="col-lg-10 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
							<textarea class="form-control"  rows="2" cols="34" maxlength="100"></textarea>
						</div>
					</div>
				 <div class="clearfix"></div>
				</div>
				<%@ include file="/WEB-INF/views/quality/auditreport/process_Info.jsp"%>
				<div id="attachments_list_edit" >
					<%@ include file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
				</div>
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
	                    	<button id="save_btn" type="button" onclick="javascript:work_card_edit_alert_Modal.setData('');" class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
							<button id="submit_btn" type="button" onclick="javascript:work_card_edit_alert_Modal.setData('submit');"  class="operation-btn btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">提交</div>
								<div class="font-size-9">Submit</div>
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
