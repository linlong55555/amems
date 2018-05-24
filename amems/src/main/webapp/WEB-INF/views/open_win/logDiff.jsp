<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/logDiff.js"></script>
<div class="modal fade modal-new padding-top-0" id="logDiffModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 1200px;">
		<div class="modal-content" >
		    <div class="modal-header modal-header-new">
				<h4 class="modal-title">
					<div class="pull-left">
						<div class="font-size-12 line-height-18">日志明细</div>
						<div class="font-size-9 ">Log Detail</div>
					</div>
					<div class="pull-right">
					<button class="icon-remove modal-close" type="button" data-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class="clearfix"></div>
				</h4>
			</div>
			<div class="modal-body padding-top-8 padding-bottom-8" id="logDiffModalBody" >
			  	<!-- <div class="panel panel-primary"> -->
					<!-- <div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">日志明细</div>
						<div class="font-size-9 ">Log Detail</div>
					</div> -->
					<!-- <div class="panel-body padding-top-10 padding-bottom-10"> -->
						<ul class="nav nav-tabs tabNew-style" role="tablist" style='border-radius:0px;padding-top:0px !important;'>
						</ul>
						<div class="tab-content " style='border-radius:0px'>
						</div>  
					<!-- </div> -->
					<!-- <div class="clearfix"></div>
					<div class="text-right margin-top-5 margin-bottom-5">
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
									<div class="font-size-12">关闭</div>
									<div class="font-size-9">Close</div>
								</button>
	                </div> -->
				<!-- </div> -->
				<div class='clearfix'></div>
			</div>
			<div class="modal-footer">
				<div class="col-xs-12 padding-leftright-8">
				   <button class="btn btn-primary padding-top-1 padding-bottom-1" type="button" data-dismiss="modal">
					<div class="font-size-12">关闭</div>
					<div class="font-size-9">Close</div>
					</button>
				</div>
			</div>
		</div>
	</div>
</div>
