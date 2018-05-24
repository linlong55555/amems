<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<div class="panel-body" id="">
<div class="col-lg-12 col-sm-12 col-xs-12 col-md-12 padding-left-0 padding-right-0">
	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
		<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
			<div class="font-size-12 margin-topnew-2">需求编号</div>
			<div class="font-size-9">Demand No.</div>
		</span>
		<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
			<input class="form-control" id="bh" name="" type="text" readonly/>
		</div>
	</div>
	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
		<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
			<div class="font-size-12 margin-topnew-2">状态</div>
			<div class="font-size-9">Status</div>
		</span>
		<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
			<input class="form-control" id="zt" name="" type="text" readonly/>
		</div>
	</div>
	<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:36px;'>
	    <div class='pull-right'>
	    <label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
				<input id="jjcd" disabled="disabled" style="vertical-align:text-bottom;" type="checkbox">&nbsp;紧急&nbsp;&nbsp;
		</label>
		<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
				<input id="isYxfx" disabled="disabled" style="vertical-align:text-bottom;" type="checkbox">&nbsp;影响放行&nbsp;&nbsp;
		</label>
		<label class="padding-left-5" style="margin-top:6px;font-weight:normal;">
				<input id="isFjhtc" disabled="disabled" style="vertical-align:text-bottom;" type="checkbox">&nbsp;非计划停场&nbsp;&nbsp;
		</label>
	    </div>
	</div>
	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
		<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
			<div class="font-size-12 margin-topnew-2">需求类别</div>
			<div class="font-size-9">Demand type</div>
		</span>
		<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
			<input class="form-control" id="xqlb" name="" type="text" readonly/>
		</div>
	</div>
	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
		<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
			<div class="font-size-12 margin-topnew-2">计划使用日期</div>
			<div class="font-size-9">Date</div>
		</span>
		<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
			<input class="form-control" id="jhsyrq" name="" type="text" readonly/>
		</div>
	</div>
	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
		<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
			<div class="font-size-12 margin-topnew-2">上级件号</div>
			<div class="font-size-9">Superior P/N</div>
		</span>
		<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
			<input class="form-control" id="sjbjh" name="" type="text" readonly/>
		</div>
	</div>
	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
		<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
			<div class="font-size-12 margin-topnew-2">上级件名称</div>
			<div class="font-size-9">Name</div>
		</span>
		<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
			<input class="form-control" id="sjbjmc" name="" type="text" readonly/>
		</div>
	</div>
	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
		<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
			<div class="font-size-12 margin-topnew-2">A/C注册号</div>
			<div class="font-size-9">A/C Register</div>
		</span>
		<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
			<input class="form-control" id="fjzch" name="" type="text" readonly/>
		</div>
	</div>
	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
		<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
			<div class="font-size-12 margin-topnew-2">A/C S/N</div>
			<div class="font-size-9">A/C S/N</div>
		</span>
		<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
			<input class="form-control" id="xlh" name="" type="text" readonly/>
		</div>
	</div>
	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
		<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
			<div class="font-size-12 margin-topnew-2">A/C FH</div>
			<div class="font-size-9">A/C FH</div>
		</span>
		<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
			<input class="form-control" id="fxsj" name="" type="text" readonly/>
		</div>
	</div>
	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
		<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
			<div class="font-size-12 margin-topnew-2">A/C FC</div>
			<div class="font-size-9">A/C FC</div>
		</span>
		<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
			<input class="form-control" id="fxxh" name="" type="text" readonly/>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
		<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
			<div class="font-size-12 margin-topnew-2">需求原因/故障描述</div>
			<div class="font-size-9">Retention Of Demand Cause</div>
		</span>
		<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
			<textarea style="height: 75px;" class="form-control" id="xqyy" name="" maxlength="1000" readonly></textarea>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
		<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
			<div class="font-size-12 margin-topnew-2">购买建议/其他说明</div>
			<div class="font-size-9">Other Instructions For Purchasing Advice</div>
		</span>
		<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
			<textarea style="height: 75px;" class="form-control" id="gmjy" name="" maxlength="1000" readonly></textarea>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 padding-left-0 padding-right-0 form-group">
		<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
			<div class="font-size-12 margin-topnew-2">物料信息</div>
			<div class="font-size-9">Material info</div>
		</span>
		<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
			<div id="" class="col-lg-12 col-md-12 padding-left-0 margin-top-0 padding-right-0  table-set" style="overflow-x: auto;">
		    <table id="" class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important;'>
			<thead>                              
				<tr>
					<th class='colwidth-5'>
						<div class="font-size-12 line-height-18" >物料类别</div>
						<div class="font-size-9 line-height-18">Type</div>
					</th>
					<th class='colwidth-7' onclick="" name="">
						<div class="font-size-12 line-height-18">件号</div>
						<div class="font-size-9 line-height-18">PN</div>
					</th>
					<th class='colwidth-10' onclick="" name="">
							<div class="font-size-12 line-height-18">名称/描述</div>
							<div class="font-size-9 line-height-18">Name/Desc</div>
					</th>
					<th class='colwidth-5' onclick="" name="" >
						<div class="font-size-12 line-height-18">型号</div>
						<div class="font-size-9 line-height-18">Model</div>
					</th>
					<th class='colwidth-5' onclick="" name="" >
							<div class="font-size-12 line-height-18">规格</div>
							<div class="font-size-9 line-height-18">Specifications</div>
					</th>
					<th class='colwidth-7' onclick="" name="" >
						<div class="font-size-12 line-height-18">序列号</div>
						<div class="font-size-9 line-height-18">SN</div>
					</th>
					<th class='colwidth-5' onclick="" name="" >
							<div class="font-size-12 line-height-18">需求数量</div>
							<div class="font-size-9 line-height-18">QTY</div>
					</th>
					<th class='colwidth-7' onclick="" name="" >
						<div class="font-size-12 line-height-18">件号来源</div>
						<div class="font-size-9 line-height-18">Original</div>
					</th>
					<th class='colwidth-10' onclick="" name="" >
						<div class="font-size-12 line-height-18">ATA章节号</div>
						<div class="font-size-9 line-height-18">ATA</div>
					</th>
					<th class='colwidth-7' onclick="" name="" >
						<div class="font-size-12 line-height-18">标准件号</div>
						<div class="font-size-9 line-height-18">Normal PN</div>
					</th>
					<th class='colwidth-7'>
						<div class="font-size-12 line-height-18">替换件</div>
						<div class="font-size-9 line-height-18">Replace PN</div>
					</th>
				</tr> 
			</thead>
			<tbody id="detail_list">
			</tbody>
		</table>
		</div>
		</div>
	</div>
	
	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
		<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
			<div class="font-size-12 margin-topnew-2">提交人</div>
			<div class="font-size-9">Submitter</div>
		</span>
		<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
			<input class="form-control" id="sqr" name="" type="text" readonly/>
		</div>
	</div>
	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
		<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
			<div class="font-size-12 margin-topnew-2">提交时间</div>
			<div class="font-size-9">Time</div>
		</span>
		<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
			<input class="form-control" id="sqsj" name="" type="text" readonly/>
		</div>
	</div>
	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
		<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
			<div class="font-size-12 margin-topnew-2">批准人</div>
			<div class="font-size-9">Approver</div>
		</span>
		<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
			<input class="form-control" id="jhr" name="" type="text" readonly/>
		</div>
	</div>
	<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
		<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
			<div class="font-size-12 margin-topnew-2">批准时间</div>
			<div class="font-size-9">Time</div>
		</span>
		<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
			<input class="form-control" id="jhsj" name="" type="text" readonly/>
		</div>
	</div>
</div>
</div>