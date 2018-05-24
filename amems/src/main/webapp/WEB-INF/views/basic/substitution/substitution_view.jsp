<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"
	language="java"%>
<div class="modal fade in modal-new" id="alertModalShow"
	tabindex="-1" role="dialog"
	aria-labelledby="alertModalShow" aria-hidden="true"
	data-keyboard="false" data-backdrop="static">
	<div class="modal-dialog" style="width: 70%;">
		<div class="modal-content">

			<div class="modal-header modal-header-new">
				<h4 class="modal-title">
					<div class='pull-left'>
						<div class="font-size-12 ">等效替代数据详情</div>
						<div class="font-size-9">Detail</div>
					</div>
					<div class='pull-right'>
						<button type="button" class="icon-remove modal-close"
							data-dismiss="modal" aria-label="Close"></button>
					</div>
					<div class='clearfix'></div>
				</h4>
			</div>


			<div
				class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span
					class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">件号</div>
					<div class="font-size-9">P/N</div>
				</span>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<input class="form-control" name="bjh" id="bjh" type="text"
						maxlength="100" readonly="readonly" />
				</div>
			</div>

			<div
				class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span
					class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">替代件号</div>
					<div class="font-size-9">Replaced P/N</div>
				</span>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<input class="form-control" id="tdjh" name="tdjh" type="text"
						maxlength="100" readonly="readonly" />
				</div>
			</div>

			<div class="clearfix"></div>

			<div
				class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span
					class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">描述</div>
					<div class="font-size-9">Description</div>
				</span>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<textarea class="form-control" id="ms" name="ms" maxlength="300"
						readonly="readonly"></textarea>

				</div>
			</div>

			<div
				class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span
					class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">替代描述</div>
					<div class="font-size-9">Description</div>
				</span>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<textarea class="form-control" id="tdjms" name="tdjms"
						maxlength="300" readonly="readonly"></textarea>

				</div>
			</div>

			<div class="clearfix"></div>


			<div
				class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span
					class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">机型适用性</div>
					<div class="font-size-9">Applicability</div>
				</span>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<input class="form-control" id="jxbs" name="jxbs" type="text"
						readonly="readonly" />
				</div>
			</div>
			<div
				class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span
					class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">工卡适用性</div>
					<div class="font-size-9">Applicability</div>
				</span>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">

					<input class="form-control" id="gkbs" name="gkbs" type="text"
						readonly="readonly" />
				</div>
			</div>

			<div class="clearfix"></div>

			<div
				class="col-lg-4 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span
					class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">是否可逆</div>
					<div class="font-size-9">Maintainer</div>
				</span>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<input type="text" id="knxbs" class="form-control"
						readonly="readonly" />
				</div>
			</div>

			<div class="clearfix"></div>
			<div id="dxtd_modal_attachments_list_edit">
				<%@ include
					file="/WEB-INF/views/common/attachments/attachments_list_edit_common.jsp"%><!-- 加载附件信息 -->
			</div>
			<div class="clearfix"></div>
			<div class="modal-footer">
				<div class="col-xs-12 padding-leftright-8">
					<div class="input-group">
						<span class="input-group-addon modalfootertip"> </span> <span
							class="input-group-addon modalfooterbtn">
							<button type="button"
								class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
								<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
						</span>
					</div>
					<!-- /input-group -->
				</div>
			</div>
		</div>
	</div>
</div>
<!--  弹出框结束-->
<script type="text/javascript"
	src="${ctx}/static/js/thjw/basic/substitution/substitution_view.js"></script>
<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
<script type="text/javascript"
	src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script>
<!-- 控件对话框 -->
