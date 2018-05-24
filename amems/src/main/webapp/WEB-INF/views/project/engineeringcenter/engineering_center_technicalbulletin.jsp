<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>

<div class="col-lg-12 padding-left-0 padding-right-0">
	<div class="col-lg-12  ibox-title"  style='padding:0px;height:25px;background:#ececec;'>
		<label class="pull-left" style='height:25px;line-height:25px;padding-left:10px;'>
	         维护提示/Technical Bulletin
		</label>
	</div>
	<div  class="col-lg-12 col-md-12 padding-left-0 padding-right-0 " style="overflow-x: auto;min-height:20px;max-height:200px;">
		<table class="table table-thin table-bordered table-hover table-set" style="min-width:950px"  >
				<thead>
					<tr>
						<th class="colwidth-3" >
							<div class="font-size-12 line-height-18">序号</div>
							<div class="font-size-9 line-height-18">No.</div>
						</th>
						<th class='colwidth-10'>
						    <div class="font-size-12 line-height-18 notwrapnew ">维护提示编号</div>
							<div class="font-size-9 line-height-18 notwrapnew ">T/B No.</div>
						</th>
						<th th_class="cont-exp3" td_class="bulletinDisplayFile" table_id="" class='cont-exp3 downward colwidth-13' onclick="CollapseOrExpandUtil.collapseOrExpandAll(this)">
						    <div class="font-size-12 line-height-18 notwrapnew">关联评估单号</div>
							<div class="font-size-9 line-height-18 notwrapnew">Assessment NO.</div>
						</th>
						<th  class="colwidth-20" >
						    <div class="font-size-12 line-height-18 notwrapnew">参考资料 </div>
							<div class="font-size-9 line-height-18 notwrapnew">Reference Material</div>
						</th>
						<th  class="colwidth-9">
							<div class="font-size-12 line-height-18 notwrapnew">圈阅情况 </div>
							<div class="font-size-9 line-height-18 notwrapnew">Markup Status</div>
						</th>
						<th  class="colwidth-30" >
							<div class="font-size-12 line-height-18 notwrapnew">主题 </div>
							<div class="font-size-9 line-height-18 notwrapnew">Subject</div>
						</th>
						<th class="colwidth-7">
							<div class="font-size-12 line-height-18 notwrapnew">状态 </div>
							<div class="font-size-9 line-height-18 notwrapnew">State</div>
						</th>
						<th class="colwidth-9">
							<div class="font-size-12 line-height-18 notwrapnew">通告期限  </div>
							<div class="font-size-9 line-height-18 notwrapnew">Bulletin Period</div>
						</th>
						<th class="colwidth-13">
							<div class="font-size-12 line-height-18 notwrapnew">制单人 </div>
							<div class="font-size-9 line-height-18 notwrapnew">Creator</div>
						</th>
						<th class="colwidth-13">
							<div class="font-size-12 line-height-18 notwrapnew">制单时间 </div>
							<div class="font-size-9 line-height-18 notwrapnew">Create Time</div>
						</th>
						<th class="colwidth-13">
							<div class="font-size-12 line-height-18 notwrapnew">组织机构 </div>
							<div class="font-size-9 line-height-18 notwrapnew">Organization</div>
						</th>
					</tr>
				</thead>
				<tbody id="thchnical_bulletin_list">
				</tbody>
		</table>
    </div>		 
</div>
<!-- 圈阅情况对话框 start -->
<div class="modal fade" id="alertModalSend" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:650px!important;">
		<div class="modal-content">
			<div class="modal-body padding-bottom-0" id="alertModalStoreBody">
				<div class="panel panel-primary">
					<div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">查看圈阅情况</div>
						<div class="font-size-9 ">View Markup Status</div>
					</div>
					<div class="panel-body padding-top-0 padding-bottom-0" >
		            	<div class="col-lg-12 col-xs-12">
		            	
			            	<table class="table table-thin table-bordered table-striped table-hover text-center margin-top-20 table-set">
								<thead>
									<tr>
										<th class="colwidth-13"><div class="font-size-12 line-height-18" >姓名</div><div class="font-size-9 line-height-18" >Name</div></th>
										<th class="colwidth-7"><div class="font-size-12 line-height-18" >状态</div><div class="font-size-9 line-height-18" >State</div></th>
										<th class="colwidth-15"><div class="font-size-12 line-height-18" >接收部门</div><div class="font-size-9 line-height-18" >Department</div></th>
										<th class="colwidth-13"><div class="font-size-12 line-height-18" >接收时间</div><div class="font-size-9 line-height-18" >Receive Time</div></th>
									</tr> 
			         		 	</thead>
								<tbody id="SendList"></tbody>
							</table>
							<div class="col-xs-12 text-center padding-right-0 page-height " id="send_pagination">
							</div>
							<div class="clearfix"></div>
				   			<div class="modal-footer" style="border-top: medium none ! important;">
		                    	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-bottom-10" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
		                   	</div>
					 	</div>
					</div> 
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 圈阅情况对话框 end -->

<script type="text/javascript" src="${ctx}/static/js/thjw/project/engineeringcenter/engineering_center_technicalbulletin.js"></script>
