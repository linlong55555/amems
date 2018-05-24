<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
	
	<div id="Contact_person_list_edit" style="display:none">	
	   <div class="panel panel-primary">
		<div id="personHead" class="panel-heading bg-panel-heading" >
		    <div class="font-size-12 ">联系人</div>
			<div class="font-size-9">Contact Person</div>
		</div>
		
	   	<div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
	   		<small class="text-muted">双击可修改联系人</small>
		   	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0" style="overflow-x:auto;">
				<table class="table table-thin table-bordered table-striped table-hover text-center table-set" style="min-width:900px" >
					<thead>
						<tr>
							<th id="colOptionhead" class="colwidth-5">
								<div class="text-center">
									<button class="line6 " onclick="Contact_person_list_edit.openContactPersonWinAdd()">
										<i class="icon-plus cursor-pointer color-blue cursor-pointer" ></i>
									</button>
								</div>
							</th>
							<th class="colwidth-3">
								<div class="font-size-12 line-height-18">序号</div>
								<div class="font-size-9 line-height-18">No.</div>
							</th>
							<th class="colwidth-13"  >
								<div class="font-size-12 line-height-18">联系人</div>
								<div class="font-size-9 line-height-18">Contact Person</div>
							</th>
							<th class=" colwidth-15"  >
								<div class="font-size-12 line-height-18">职位</div>
								<div class="font-size-9 line-height-18">Position</div>
							</th>
							<th class=" colwidth-10"  >
								<div class="font-size-12 line-height-18">手机</div>
								<div class="font-size-9 line-height-18">Phone</div>
							</th>
							<th class=" colwidth-10"  >
								<div class="font-size-12 line-height-18">座机</div>
								<div class="font-size-9 line-height-18">Tel</div>
							</th>
							<th class=" colwidth-10"  >
								<div class="font-size-12 line-height-18">传真</div>
								<div class="font-size-9 line-height-18">Fax</div>
							</th>
							<th class=" colwidth-15"  >
								<div class="font-size-12 line-height-18">邮箱</div>
								<div class="font-size-9 line-height-18">E-mail</div>
							</th>
							<th class=" colwidth-10"  >
								<div class="font-size-12 line-height-18">QQ</div>
								<div class="font-size-9 line-height-18">QQ</div>
							</th>
							<th class=" colwidth-10"  >
								<div class="font-size-12 line-height-18">微信</div>
								<div class="font-size-9 line-height-18">Wechat</div>
							</th>
							<th class=" colwidth-15"  >
								<div class="font-size-12 line-height-18">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</th>
						</tr>
					</thead>
				    <tbody id="Contact_person_list_edit_Tbody">
						 
					</tbody>
				</table>
			</div>
		</div>
	</div>	
	</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/firm/contact_person_list_edit.js"></script>
<%@ include file="/WEB-INF/views/material/firm/contact_person_win.jsp"%><!-- 加载联系人信息 -->
