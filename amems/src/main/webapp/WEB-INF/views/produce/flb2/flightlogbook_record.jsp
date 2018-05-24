<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<style type="text/css">
	small {
		line-height: 18px;
	}
	.border-all {
    	border: 1px solid #ddd !important;
	}
	.border-left {
    	border-left: 1px solid #ddd !important;
	}
	.border-right {
	    border-right: 1px solid #ddd !important;
	}
	.border-bottom {
	    border-bottom: 1px solid #ddd !important;
	}
	.border-top {
	    border-top: 1px solid #ddd !important;
	}
</style>


<div class='padding-top-0 padding-left-0 padding-right-0'>
	<div class="bottom-hidden-table-content col-lg-12 padding-leftright-8" name="flb_detail_content">
		<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x: auto;">
			<table name="flb_record_table" class="table table-thin">
				<thead class="nav-tabs">
					<tr>
						<th class="border-all" colspan="2" rowspan="2" style="min-width: 130px;vertical-align:middle;">
							<div class="pull-left padding-left-10 margin-right-10">
								<div class="font-size-12 line-height-12">飞行数据</div>
								<div class="font-size-9 line-height-12">Flight Data</div>
							</div>
							<div class="pull-left">
								<button title="新增航次" name="flb_record_table_add_btn" class="line6 line6-btn margin-top-5 btn-default" type="button">
									<i class="icon-plus cursor-pointer color-blue cursor-pointer" style="line-height: 24px;"></i>
							    </button>
								<button title="删除航次" name="flb_record_table_del_btn" class="line6 line6-btn margin-top-5 btn-default" type="button">
									<i class="icon-minus cursor-pointer color-blue cursor-pointer" style="line-height: 24px;"></i>
								</button>
							</div>
						</th>
						<th class="border-all" style="min-width: 80px;vertical-align:middle;" rowspan="2" name="hbh">
							<div class="font-size-12 line-height-12">航班号</div>
							<div class="font-size-9 line-height-12">FLT. No.</div>
						</th>
						<th class="border-all" style="min-width: 80px;vertical-align:middle;" rowspan="2" name="fxrwlx">
							<div class="font-size-12 line-height-12">任务类型</div>
							<div class="font-size-9 line-height-12">Task Type</div>
						</th>
						<th class="border-all" colspan="2" children="qfz,zlz">
							<div class="font-size-12 line-height-12">航段</div>
							<div class="font-size-9 line-height-12">Leg</div>
						</th>
						<th class="border-all" colspan="6" children="kcsj,qfsj,ldsj,tcsj,sysj,fxsj">
							<div class="font-size-12 line-height-12">飞行时间</div>
							<div class="font-size-9 line-height-12">Flight Hour</div>
						</th>
						<th class="border-all" style="min-width: 80px;vertical-align:middle;" rowspan="2" name="fxxh">
							<div class="font-size-12 line-height-12">FC</div>
							<div class="font-size-9 line-height-12">FC</div>
						</th>
						<th class="border-all" style="min-width: 80px;vertical-align:middle;" rowspan="2" name="lxqlcs">
							<div class="font-size-12 line-height-12">连续起落次数</div>
							<div class="font-size-9 line-height-12">Touch GO</div>
						</th>
						<th class="border-all" colspan="3" children="f1sj,f1xh,f1hytjl" name="fdj1">
							<div class="font-size-12 line-height-12">1#发动机</div>
							<div class="font-size-9 line-height-12">ENG No.1</div>
						</th>
						<th class="border-all" colspan="3" children="f2sj,f2xh,f2hytjl" name="fdj2">
							<div class="font-size-12 line-height-12">2#发动机</div>
							<div class="font-size-9 line-height-12">ENG No.2</div>
						</th>
						<th class="border-all" colspan="3" children="f3sj,f3xh,f3hytjl" name="fdj3">
							<div class="font-size-12 line-height-12">3#发动机</div>
							<div class="font-size-9 line-height-12">ENG No.3</div>
						</th>
						<th class="border-all" colspan="3" children="f4sj,f4xh,f4hytjl" name="fdj4">
							<div class="font-size-12 line-height-12">4#发动机</div>
							<div class="font-size-9 line-height-12">ENG No.4</div>
						</th>
						<th class="border-all" colspan="3" children="apusj,apuxh,apuhytjl">
							<div class="font-size-12 line-height-12">APU</div>
							<div class="font-size-9 line-height-12">APU</div>
						</th>
						<th class="border-left border-top border-bottom" style="min-width: 80px;vertical-align:middle;" rowspan="2" name="idgtksj">
							<div class="font-size-12 line-height-12">IDG脱开时间</div>
							<div class="font-size-9 line-height-12">Search Time</div>
						</th>
						<th class="border-right border-top border-bottom" style="min-width: 80px;vertical-align:middle;" rowspan="2" name="yyy">
							<div class="font-size-12 line-height-12">液压油</div>
							<div class="font-size-9 line-height-12">HYD</div>
						</th>
						<th class="border-all" colspan="6" children="rycyl,ryjyl,ryzyl,rysyyl,ryxhyl,rydw">
							<div class="font-size-12 line-height-12">燃油油量</div>
							<div class="font-size-9 line-height-12">FUEL QTY</div>
						</th>
						<th class="border-all" style="min-width: 150px;vertical-align:middle;" rowspan="2" name="jz">
							<div class="font-size-12 line-height-12">机长</div>
							<div class="font-size-9 line-height-12">Captain</div>
						</th>
					</tr>
					<tr>
						<th style="min-width: 80px;" name="qfz">
							<div class="font-size-12 line-height-12">起飞站</div>
							<div class="font-size-9 line-height-12">DEP</div>
						</th>
						<th style="min-width: 80px;" class="border-right" name="zlz">
							<div class="font-size-12 line-height-12">着陆站</div>
							<div class="font-size-9 line-height-12">DES</div>
						</th>
						<th style="min-width: 80px;" name="kcsj">
							<div class="font-size-12 line-height-12">开车</div>
							<div class="font-size-9 line-height-12">Start</div>
						</th>
						<th style="min-width: 80px;" name="qfsj">
							<div class="font-size-12 line-height-12">起飞</div>
							<div class="font-size-9 line-height-12">T.O</div>
						</th>
						<th style="min-width: 80px;" name="ldsj">
							<div class="font-size-12 line-height-12">落地</div>
							<div class="font-size-9 line-height-12">Land</div>
						</th>
						<th style="min-width: 80px;" name="tcsj">
							<div class="font-size-12 line-height-12">停车</div>
							<div class="font-size-9 line-height-12">Stop</div>
						</th>
						<th style="min-width: 80px;" name="sysj">
							<div class="font-size-12 line-height-12">使用</div>
							<div class="font-size-9 line-height-12">Block</div>
						</th>
						<th style="min-width: 80px;" name="fxsj">
							<div class="font-size-12 line-height-12">FH</div>
							<div class="font-size-9 line-height-12">FLTHR</div>
						</th>
						<th style="min-width: 80px;" name="f1sj">
							<div class="font-size-12 line-height-12">EH</div>
							<div class="font-size-9 line-height-12">EH</div>
						</th>
						<th style="min-width: 80px;" name="f1xh">
							<div class="font-size-12 line-height-12">EC</div>
							<div class="font-size-9 line-height-12">EC</div>
						</th>
						<th style="min-width: 120px;" class="border-right" name="f1hytjl">
							<div class="font-size-12 line-height-12">滑油添加量（夸脱）</div>
							<div class="font-size-9 line-height-12">Oil(QT)</div>
						</th>
						<th style="min-width: 80px;" name="f2sj">
							<div class="font-size-12 line-height-12">EH</div>
							<div class="font-size-9 line-height-12">EH</div>
						</th>
						<th style="min-width: 80px;" name="f2xh">
							<div class="font-size-12 line-height-12">EC</div>
							<div class="font-size-9 line-height-12">EC</div>
						</th>
						<th style="min-width: 120px;" class="border-right" name="f2hytjl">
							<div class="font-size-12 line-height-12">滑油添加量（夸脱）</div>
							<div class="font-size-9 line-height-12">Oil(QT)</div>
						</th>
						<th style="min-width: 80px;" name="f3sj">
							<div class="font-size-12 line-height-12">EH</div>
							<div class="font-size-9 line-height-12">EH</div>
						</th>
						<th style="min-width: 80px;" name="f3xh">
							<div class="font-size-12 line-height-12">EC</div>
							<div class="font-size-9 line-height-12">EC</div>
						</th>
						<th style="min-width: 120px;" class="border-right" name="f3hytjl">
							<div class="font-size-12 line-height-12">滑油添加量（夸脱）</div>
							<div class="font-size-9 line-height-12">Oil(QT)</div>
						</th>
						<th style="min-width: 80px;" name="f4sj">
							<div class="font-size-12 line-height-12">EH</div>
							<div class="font-size-9 line-height-12">EH</div>
						</th>
						<th style="min-width: 80px;" name="f4xh">
							<div class="font-size-12 line-height-12">EC</div>
							<div class="font-size-9 line-height-12">EC</div>
						</th>
						<th style="min-width: 120px;" class="border-right" name="f4hytjl">
							<div class="font-size-12 line-height-12">滑油添加量（夸脱）</div>
							<div class="font-size-9 line-height-12">Oil(QT)</div>
						</th>
						<th style="min-width: 80px;" name="apusj">
							<div class="font-size-12 line-height-12">APU.H</div>
							<div class="font-size-9 line-height-12">APU.H</div>
						</th>
						<th style="min-width: 80px;" name="apuxh">
							<div class="font-size-12 line-height-12">APU.C</div>
							<div class="font-size-9 line-height-12">APU.C</div>
						</th>
						<th style="min-width: 120px;" class="border-right" name="apuhytjl">
							<div class="font-size-12 line-height-12">滑油添加量（夸脱）</div>
							<div class="font-size-9 line-height-12">Oil(QT)</div>
						</th>
						<th style="min-width: 80px;" name="rycyl">
							<div class="font-size-12 line-height-12">飞行前存油量</div>
							<div class="font-size-9 line-height-12">Fuel Remaining</div>
						</th>
						<th style="min-width: 80px;" name="ryjyl">
							<div class="font-size-12 line-height-12">加油量</div>
							<div class="font-size-9 line-height-12">Fuel Add</div>
						</th>
						<th style="min-width: 80px;" name="ryzyl">
							<div class="font-size-12 line-height-12">飞行前总油量</div>
							<div class="font-size-9 line-height-12">Fuel Total</div>
						</th>
						<th style="min-width: 100px;" name="rysyyl">
							<div class="font-size-12 line-height-12">飞行后剩余油量</div>
							<div class="font-size-9 line-height-12">AFT. LDG</div>
						</th>
						<th style="min-width: 80px;" name="ryxhyl">
							<div class="font-size-12 line-height-12">消耗油量</div>
							<div class="font-size-9 line-height-12">CONS QTY</div>
						</th>
						<th style="min-width: 80px;" name="rydw">
							<div class="font-size-12 line-height-12">燃油单位</div>
							<div class="font-size-9 line-height-12">Unit</div>
						</th>
					</tr>
				</thead>
				<tbody name="flb_record_table_list">
					<tr id="hc1" hcms="航次一" hc="2" class="border-all">
						<td colspan="2" style="min-width: 80px;" class="text-center border-all">
							<small class="text-warning" style="display: block;">序列号S/N</small>
							<small class="text-muted" style="display: block;">飞行前数据</small>
							<div>航次一</div>
						</td>
						<td name="hbh" class="">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="16" type="text">
						</td>
						<td name="fxrwlx" class="">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<select class="form-control" type="text"></select>
						</td>
						<td name="qfz" class="">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="100" type="text">
						</td>
						<td name="zlz" class="">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="100" type="text">
						</td>
						<td name="kcsj" class="time-masked">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="5" type="text">
						</td>
						<td name="qfsj" class="time-masked">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="5" type="text">
						</td>
						<td name="ldsj" class="time-masked">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="5" type="text">
						</td>
						<td name="tcsj" class="time-masked">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="5" type="text">
						</td>
						<td name="sysj" class="validate-time">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="fxsj" class="validate-time">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="fxxh" class="validate-cycle">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="lxqlcs" class="validate-cycle">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f1sj" class="validate-time" style="white-space:nowrap;max-width: 80px;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f1xh" class="validate-cycle" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f1hytjl" class="validate-decimal" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f2sj" class="validate-time" style="white-space:nowrap;max-width: 80px;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f2xh" class="validate-cycle" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f2hytjl" class="validate-decimal" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f3sj" class="validate-time" style="white-space:nowrap;max-width: 80px;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f3xh" class="validate-cycle" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f3hytjl" class="validate-decimal" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f4sj" class="validate-time" style="white-space:nowrap;max-width: 80px;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f4xh" class="validate-cycle" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f4hytjl" class="validate-decimal" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="apusj" class="validate-time" style="white-space:nowrap;max-width: 80px;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="apuxh" class="validate-cycle" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="apuhytjl" class="validate-decimal" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="idgtksj" class="validate-time">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="yyy" class="validate-decimal">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="rycyl" class="validate-decimal">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="ryjyl" class="validate-decimal">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="ryzyl" class="text-center" style="line-height: 3">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<span>0</span>
						</td>
						<td name="rysyyl" class="validate-decimal">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="ryxhyl" class="text-center" style="line-height: 3">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<span>0</span>
						</td>
						<td name="rydw" class="">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<select class="form-control" type="text"></select>
						</td>
						<td name="jz">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control" name="jzid" type="hidden">
							<div class="input-group">
								<input name="jzstr" class="form-control" type="text" maxlength="100">
					            <span class="input-group-addon btn btn-default" name="jz_btn">
					            	<i class="icon-search cursor-pointer"></i>
					            </span>
				          	</div>
						</td>
					</tr>
					<tr id="hc2" hcms="航次二" hc="4" class="border-all">
						<td colspan="2" style="min-width: 80px;" class="text-center border-all">
							<small class="text-warning" style="display: block;">序列号S/N</small>
							<small class="text-muted" style="display: block;">飞行前数据</small>
							<div>航次二</div>
						</td>
						<td name="hbh" class="">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="16" type="text">
						</td>
						<td name="fxrwlx" class="">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<select class="form-control" type="text"></select>
						</td>
						<td name="qfz" class="">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="100" type="text">
						</td>
						<td name="zlz" class="">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="100" type="text">
						</td>
						<td name="kcsj" class="time-masked">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="5" type="text">
						</td>
						<td name="qfsj" class="time-masked">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="5" type="text">
						</td>
						<td name="ldsj" class="time-masked">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="5" type="text">
						</td>
						<td name="tcsj" class="time-masked">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="5" type="text">
						</td>
						<td name="sysj" class="validate-time">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="fxsj" class="validate-time">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="fxxh" class="validate-cycle">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="lxqlcs" class="validate-cycle">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f1sj" class="validate-time" style="white-space:nowrap;max-width: 80px;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f1xh" class="validate-cycle" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f1hytjl" class="validate-decimal" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f2sj" class="validate-time" style="white-space:nowrap;max-width: 80px;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f2xh" class="validate-cycle" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f2hytjl" class="validate-decimal" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f3sj" class="validate-time" style="white-space:nowrap;max-width: 80px;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f3xh" class="validate-cycle" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f3hytjl" class="validate-decimal" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f4sj" class="validate-time" style="white-space:nowrap;max-width: 80px;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f4xh" class="validate-cycle" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f4hytjl" class="validate-decimal" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="apusj" class="validate-time" style="white-space:nowrap;max-width: 80px;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="apuxh" class="validate-cycle" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="apuhytjl" class="validate-decimal" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="idgtksj" class="validate-time">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="yyy" class="validate-decimal">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="rycyl" class="validate-decimal">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="ryjyl" class="validate-decimal">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="ryzyl" class="text-center" style="line-height: 3">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<span>0</span>
						</td>
						<td name="rysyyl" class="validate-decimal">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="ryxhyl" class="text-center" style="line-height: 3">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<span>0</span>
						</td>
						<td name="rydw" class="">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<select class="form-control" type="text"></select>
						</td>
						<td name="jz">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control" name="jzid" type="hidden">
							<div class="input-group">
								<input name="jzstr" class="form-control" type="text" maxlength="100">
					            <span class="input-group-addon btn btn-default" name="jz_btn">
					            	<i class="icon-search cursor-pointer"></i>
					            </span>
				          	</div>
						</td>
					</tr>
					<tr id="hc3" hcms="航次三" hc="6" class="border-all" style="display: none;">
						<td colspan="2" style="min-width: 80px;" class="text-center border-all">
							<small class="text-warning" style="display: block;">序列号S/N</small>
							<small class="text-muted" style="display: block;">飞行前数据</small>
							<div>航次三</div>
						</td>
						<td name="hbh" class="">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="16" type="text">
						</td>
						<td name="fxrwlx" class="">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<select class="form-control" type="text"></select>
						</td>
						<td name="qfz" class="">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="100" type="text">
						</td>
						<td name="zlz" class="">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="100" type="text">
						</td>
						<td name="kcsj" class="time-masked">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="5" type="text">
						</td>
						<td name="qfsj" class="time-masked">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="5" type="text">
						</td>
						<td name="ldsj" class="time-masked">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="5" type="text">
						</td>
						<td name="tcsj" class="time-masked">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="5" type="text">
						</td>
						<td name="sysj" class="validate-time">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="fxsj" class="validate-time">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="fxxh" class="validate-cycle">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="lxqlcs" class="validate-cycle">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f1sj" class="validate-time" style="white-space:nowrap;max-width: 80px;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f1xh" class="validate-cycle" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f1hytjl" class="validate-decimal" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f2sj" class="validate-time" style="white-space:nowrap;max-width: 80px;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f2xh" class="validate-cycle" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f2hytjl" class="validate-decimal" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f3sj" class="validate-time" style="white-space:nowrap;max-width: 80px;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f3xh" class="validate-cycle" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f3hytjl" class="validate-decimal" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f4sj" class="validate-time" style="white-space:nowrap;max-width: 80px;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f4xh" class="validate-cycle" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f4hytjl" class="validate-decimal" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="apusj" class="validate-time" style="white-space:nowrap;max-width: 80px;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="apuxh" class="validate-cycle" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="apuhytjl" class="validate-decimal" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="idgtksj" class="validate-time">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="yyy" class="validate-decimal">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="rycyl" class="validate-decimal">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="ryjyl" class="validate-decimal">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="ryzyl" class="text-center" style="line-height: 3">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<span>0</span>
						</td>
						<td name="rysyyl" class="validate-decimal">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="ryxhyl" class="text-center" style="line-height: 3">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<span>0</span>
						</td>
						<td name="rydw" class="">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<select class="form-control" type="text"></select>
						</td>
						<td name="jz">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control" name="jzid" type="hidden">
							<div class="input-group">
								<input name="jzstr" class="form-control" type="text" maxlength="100">
					            <span class="input-group-addon btn btn-default" name="jz_btn">
					            	<i class="icon-search cursor-pointer"></i>
					            </span>
				          	</div>
						</td>
					</tr>
					<tr id="hc4" hcms="航次四" hc="8" class="border-all" style="display: none;">
						<td colspan="2" style="min-width: 80px;" class="text-center border-all">
							<small class="text-warning" style="display: block;">序列号S/N</small>
							<small class="text-muted" style="display: block;">飞行前数据</small>
							<div>航次四</div>
						</td>
						<td name="hbh" class="">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="16" type="text">
						</td>
						<td name="fxrwlx" class="">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<select class="form-control" type="text"></select>
						</td>
						<td name="qfz" class="">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="100" type="text">
						</td>
						<td name="zlz" class="">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="100" type="text">
						</td>
						<td name="kcsj" class="time-masked">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="5" type="text">
						</td>
						<td name="qfsj" class="time-masked">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="5" type="text">
						</td>
						<td name="ldsj" class="time-masked">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="5" type="text">
						</td>
						<td name="tcsj" class="time-masked">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="5" type="text">
						</td>
						<td name="sysj" class="validate-time">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="fxsj" class="validate-time">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="fxxh" class="validate-cycle">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="lxqlcs" class="validate-cycle">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f1sj" class="validate-time" style="white-space:nowrap;max-width: 80px;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f1xh" class="validate-cycle" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f1hytjl" class="validate-decimal" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f2sj" class="validate-time" style="white-space:nowrap;max-width: 80px;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f2xh" class="validate-cycle" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f2hytjl" class="validate-decimal" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f3sj" class="validate-time" style="white-space:nowrap;max-width: 80px;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f3xh" class="validate-cycle" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f3hytjl" class="validate-decimal" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f4sj" class="validate-time" style="white-space:nowrap;max-width: 80px;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f4xh" class="validate-cycle" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="f4hytjl" class="validate-decimal" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="apusj" class="validate-time" style="white-space:nowrap;max-width: 80px;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="apuxh" class="validate-cycle" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;visibility: hidden;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="apuhytjl" class="validate-decimal" style="white-space:nowrap;">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="idgtksj" class="validate-time">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="yyy" class="validate-decimal">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="rycyl" class="validate-decimal">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="ryjyl" class="validate-decimal">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="ryzyl" class="text-center" style="line-height: 3">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<span>0</span>
						</td>
						<td name="rysyyl" class="validate-decimal">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control input-sm" maxlength="10" type="text">
						</td>
						<td name="ryxhyl" class="text-center" style="line-height: 3">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<span>0</span>
						</td>
						<td name="rydw" class="">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<select class="form-control" type="text"></select>
						</td>
						<td name="jz">
							<small name="xlh" class="text-warning" style="display: block;">&nbsp;</small>
							<small name="pre" class="text-muted" style="display: block;">&nbsp;</small>
							<input class="form-control" name="jzid" type="hidden">
							<div class="input-group">
								<input name="jzstr" class="form-control" type="text" maxlength="100">
					            <span class="input-group-addon btn btn-default" name="jz_btn">
					            	<i class="icon-search cursor-pointer"></i>
					            </span>
				          	</div>
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr class="border-bottom border-all">
						<th colspan="2" style="padding-top: 5px;padding-bottom:5px;" class="border-all">
							<div class="text-center" style="font-weight:normal;">累计总数 Total</div>
						</th>
						<th style="padding-top: 5px;padding-bottom:5px;">
							<span></span>
						</th>
						<th style="padding-top: 5px;padding-bottom:5px;" class="">
							<span></span>
						</th>
						<th style="padding-top: 5px;padding-bottom:5px;" class="">
							<span></span>
						</th>
						<th style="padding-top: 5px;padding-bottom:5px;" class="">
							<span></span>
						</th>
						<th style="padding-top: 5px;padding-bottom:5px;" class="">
							<span></span>
						</th>
						<th style="padding-top: 5px;padding-bottom:5px;" class="">
							<span></span>
						</th>
						<th style="padding-top: 5px;padding-bottom:5px;">
							<span></span>
						</th>
						<th style="padding-top: 5px;padding-bottom:5px;"></th>
						<th name="sysj" style="padding-top: 5px;padding-bottom:5px;" class="text-left">
							<span>0</span>
						</th>
						<th name="fxsj" style="padding-top: 5px;padding-bottom:5px;" class="text-left">
							<span>0</span>
						</th>
						<th name="fxxh" style="padding-top: 5px;padding-bottom:5px;" class="text-left">
							<span>0</span>
						</th>
						<th name="lxqlcs" style="padding-top: 5px;padding-bottom:5px;" class="text-left">
							<span>0</span>
						</th>
						<th name="f1sj" style="padding-top: 5px;padding-bottom:5px;" class="text-left">
							<span>0</span>
						</th>
						<th name="f1xh" style="padding-top: 5px;padding-bottom:5px;" class="text-left">
							<span>0</span>
						</th>
						<th name="f1hytjl" style="padding-top: 5px;padding-bottom:5px;" class="text-left">
							<span>0</span>
						</th>
						<th name="f2sj" style="padding-top: 5px;padding-bottom:5px;" class="text-left">
							<span>0</span>
						</th>
						<th name="f2xh" style="padding-top: 5px;padding-bottom:5px;" class="text-left">
							<span>0</span>
						</th>
						<th name="f2hytjl" style="padding-top: 5px;padding-bottom:5px;" class="text-left">
							<span>0</span>
						</th>
						<th name="f3sj" style="padding-top: 5px;padding-bottom:5px;" class="text-left">
							<span>0</span>
						</th>
						<th name="f3xh" style="padding-top: 5px;padding-bottom:5px;" class="text-left">
							<span>0</span>
						</th>
						<th name="f3hytjl" style="padding-top: 5px;padding-bottom:5px;" class="text-left">
							<span>0</span>
						</th>
						<th name="f4sj" style="padding-top: 5px;padding-bottom:5px;" class="text-left">
							<span>0</span>
						</th>
						<th name="f4xh" style="padding-top: 5px;padding-bottom:5px;" class="text-left">
							<span>0</span>
						</th>
						<th name="f4hytjl" style="padding-top: 5px;padding-bottom:5px;" class="text-left">
							<span>0</span>
						</th>
						<th name="apusj" style="padding-top: 5px;padding-bottom:5px;" class="text-left">
							<span>0</span>
						</th>
						<th name="apuxh" style="padding-top: 5px;padding-bottom:5px;" class="text-left">
							<span>0</span>
						</th>
						<th name="apuhytjl" style="padding-top: 5px;padding-bottom:5px;" class="text-left">
							<span>0</span>
						</th>
						<th style="padding-top: 5px;padding-bottom:5px;"></th>
						<th name="yyy" style="padding-top: 5px;padding-bottom:5px;" class="text-left">
							<span>0</span>
						</th>
						<th style="padding-top: 5px;padding-bottom:5px;"></th>
						<th name="ryjyl" style="padding-top: 5px;padding-bottom:5px;" class="text-left">
							<span>0</span>
						</th>
						<th style="padding-top: 5px;padding-bottom:5px;">
							<span></span>
						</th>
						<th style="padding-top: 5px;padding-bottom:5px;">
							<span></span>
						</th>
						<th name="ryxhyl" style="padding-top: 5px;padding-bottom:5px;" class="text-center">
							<span>0</span>
						</th>
						<th style="padding-top: 5px;padding-bottom:5px;">
							<span></span>
						</th>
						<th style="padding-top: 5px;padding-bottom:5px;"></th>
					</tr>
				</tfoot>
			</table>
		</div>
	</div>
	<div class='clearfix'></div>
</div>

<script type="text/javascript" src="${ctx}/static/js/thjw/produce/flb2/flightlogbook_record.js"></script>
