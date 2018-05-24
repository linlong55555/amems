<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
	
	<input type="hidden" id="dprtId" value="${user.jgdm}" />
	<input type="hidden" id="userId" value="${user.id}" />
	<div class="panel-heading padding-left-16 padding-top-3 padding-bottom-10 col-xs-12 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
		<div class=" pull-left margin-right-10" >
			<div class="font-size-16 line-height-18">留言</div>
			<div class="font-size-9 ">Message</div>
		</div>	
	</div>
				
	<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-bottom-10">
				
		<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
		 
		 	<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"  >
				<div class="font-size-12 line-height-18">提醒人</div>
				<div class="font-size-9 line-height-18">Remind people</div>
			</label>
			<div class="col-lg-5 col-sm-8 col-xs-7 padding-left-8 padding-right-0 pull-left">
				<textarea class="form-control pull-left" id="majsrName" readonly style="height:33px;"></textarea>
			</div>
	        <div class="padding-right-0 pull-left">
				<button class="btn btn-primary form-control" style="width:39px;height:33px;" data-toggle="modal" onclick="MessageFormUtil.openUserModalWin()">
					<i class="icon-search cursor-pointer"></i>
				</button>
			</div>
		</div>
		
 		<div class="clearfix"></div>
 		
 		<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 margin-top-10">
 
		 	<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0"  >
				<div class="font-size-12 line-height-18">留言内容</div>
				<div class="font-size-9 line-height-18">Message</div>
			</label>
			<div class="col-lg-11 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
				<textarea class="form-control" id="manbwjm" maxlength="300" ></textarea>
			</div>
		
		</div>
		
 	</div>			

<script type="text/javascript" src="${ctx}/static/js/thjw/common/message/message_list_add.js"></script>
<%@ include file="/WEB-INF/views/open_win/user_multi.jsp"%><!-------用户对话框 -------->