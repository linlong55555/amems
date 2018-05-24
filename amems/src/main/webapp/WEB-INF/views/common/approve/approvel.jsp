<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
	
<div id="ApprovelUtil" style="display:none">	
	
	<div class="panel panel-primary">
	
		<div class="panel-heading bg-panel-heading">
			<div class="font-size-12 ">意见</div>
			<div class="font-size-9">Opinion</div>
		</div>
	
		<div class="panel-body padding-left-0 padding-right-0" >						
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  form-group">
			<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0" >
				<div class="font-size-12 ">审核意见</div>
				<div class="font-size-9 ">Review Opinion</div>
			</span>
			<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-top-0 padding-right-8">
					<textarea class="form-control" id="shyj" name="shyj" placeholder='' disabled="disabled"  maxlength="300" style="height:55px"></textarea>
			</div>
		</div>
		<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 "  >
			<div class="font-size-12 line-height-18"></div>
			<div class="font-size-9 line-height-18"></div></label>
			
			<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-top-0 margin-bottom-8">
			　<label style="margin-right: 50px"></label>　
			<div class="pull-left padding-right-10" style="margin-right:200px">
			<div class="font-size-12 line-height-18">审核人　<label style=" font-weight:normal" id="shrname"></label></div>
			<div class="font-size-9 line-height-12">Reviewer</div>
			</div>
			<div class="pull-left">
			<div class="font-size-12 line-height-18">审核时间 　<label style=" font-weight:normal" id="shsj"></label></div>
			<div class="font-size-9 line-height-12">Review Time </div>
			</div>
		</div>
		<div class="clearfix"></div>						
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-8 ">
			<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0" >
				<div class="font-size-12 ">批准意见</div>
				<div class="font-size-9 ">Approval Opinion</div>
			</span>
			<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-top-0 padding-right-8">
					<textarea class="form-control" id="pzyj" name="pzyj" placeholder='' disabled="disabled"  maxlength="300" style="height:55px"></textarea>
			</div>
		</div>
		<div class="clearfix"></div>
		<label class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0 padding-top-0"  >
			</label>
			<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-top-0 margin-bottom-0">
			　
			<div class="pull-left padding-right-10" style="margin-right:200px">
			<div class="font-size-12 line-height-18">批准人　<label style=" font-weight:normal" id="pzrname"></label></div>
			<div class="font-size-9 line-height-12">Appr. By</div>
			</div>
			<div class="pull-left ">
			<div class="font-size-12 line-height-18">批准时间　<label style=" font-weight:normal" id="pzsj"></label></div>
			<div class="font-size-9 line-height-12">Approved Time</div>
			</div>
		</div>
		</div>
		</div>	
	</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/common/approve/approvel.js"></script>