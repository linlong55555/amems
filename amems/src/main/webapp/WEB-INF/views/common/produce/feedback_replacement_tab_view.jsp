<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/produce/feedback_replacement_tab_view.js"></script><!--当前界面js  -->
<div class="modal fade in modal-new" id="feedback_replacement_tab_view" tabindex="-1" role="dialog"  aria-labelledby="feedback_replacement_tab_view" aria-hidden="true" data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style="width: 70%;" >
		<div class="modal-content">
			<div class="modal-header modal-header-new" >
               	<h4 class="modal-title" >
           			<div class='pull-left'>
               			<div class="font-size-12 ">详情</div>
						<div class="font-size-9">Detail</div>
				 	</div>
					<div class='pull-right' >
					  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class='clearfix'></div>
				</h4>
          	</div>
           	<div class="modal-body" style='padding-top:0px;'>
             	<div  style='margin-top:8px;margin-bottom:8px;'>
					<ul id="myChildTab" class="nav nav-tabs tabNew-style" style="padding-top:0px !important;">
						<li id='feedback_win_tab' class="child_li_div"  >
						<a href='#feedbackWinTab' data-toggle="tab"  >
							<div class="font-size-12 line-height-12">完工反馈</div>
		                    <div class="font-size-9 line-height-9">Feedback</div>
		                  </a>
						</li>
						<li id='replacement_win_tab' class="child_li_div" >
						<a href='#replacementWinTab' data-toggle="tab"  >
							<div class="font-size-12 line-height-12">拆换件记录</div>
		                    <div class="font-size-9 line-height-9">Replacement</div>
		                </a>
						</li>
					</ul>
					<div class="tab-content" style='padding:0px;'>
						<div id="feedbackWinTab" class="child_li_div tab-pane fade">
							<%@ include file="/WEB-INF/views/produce/taskhistory/taskhistory_feedback.jsp" %> <!-- 完工反馈 -->
						</div>
						<div id="replacementWinTab" class="child_li_div tab-pane fade" style='padding-bottom:8px;'>
							<%@ include file="/WEB-INF/views/produce/taskhistory/taskhistory_record.jsp" %> <!-- 拆换件记录 -->
						</div>
					</div>	         		
				</div>
               <div class="clearfix"></div>              
          </div>
          <div class="clearfix"></div>  
          <div class="modal-footer">
          		<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
						</span>
	                   	<span class="input-group-addon modalfooterbtn">
	                    	<button onclick="feedback_replacement_tab_view.close();" type="button" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
					    	</button>
	                   	</span>
              		</div><!-- /input-group -->
				</div>
			</div>
		</div>
	</div>
</div>
<!--  弹出框结束-->