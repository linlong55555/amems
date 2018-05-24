<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/engineering/eo_close_win.js"></script>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade double-pop-up" id="EOCloseModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false>

      <div class="modal-dialog" >
            <div class="modal-content" >
                  <div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          	<div class='pull-left'>
		                 		<div class="font-size-12" >关闭EO</div>
								<div class="font-size-9" >close</div>
							</div>
			 				<div class='pull-right' >
								<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
							</div>
							<div class='clearfix'></div>
                       </h4>
                  </div>
            <div class="modal-body" >
              	<div class="col-lg-12 col-sm-12 col-xs-12 ">
              	    <div style='border-bottom:1px solid #d5d5d5; margin-bottom:8px;padding-bottom:8px;margin-top:8px;'>
							<div class="col-lg-6 col-xs-12 col-sm-12  padding-left-0 padding-right-0 ">
								<div class="col-lg-12 col-sm-6 padding-left-8 padding-right-0" style="width:70%" id="eoclose9div">
									<ul>
										<li><input type="radio" name="eoclose" value="9" id="eoclose9"> 指定结束</li>
										<div id="approve_list">End</div>
									</ul>
								</div>
							</div>
			            	<div class="col-lg-6 col-xs-12 col-sm-12  padding-left-0 padding-right-0 " id="eoclose10div">
								<div class="col-lg-12 col-sm-6 padding-left-8 padding-right-0" style="width:70%">
									<ul>
										<li><input type="radio" name="eoclose" value="10" id="eoclose10" >正常完成</li>
										<div id="approve_not_list">Finish</div>
									</ul>
								</div>
							</div>
			            	<div class="clearfix"></div>
			        </div>
			    </div>
			</div>
		  <div class='clearfix'></div>
           <div class="modal-footer">
           	<div class="col-xs-12 padding-leftright-8" >
				<div class="input-group">
				<span class="input-group-addon modalfootertip" >
	                  <!--  <i class='glyphicon glyphicon-info-sign alert-info'></i>警告！请不要提交 -->
				</span>
                    <span class="input-group-addon modalfooterbtn">
                      <button type="button" onclick="EOCloseModal.doClose()" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
								<div class="font-size-12">确定</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
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