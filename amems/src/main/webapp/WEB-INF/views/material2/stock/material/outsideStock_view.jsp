<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>库存详情</title>
	<%@ include file="/WEB-INF/views/common_new.jsp"%>
	<%@ include file="/WEB-INF/views/material2/stockmaterial/inside/frozen_history.jsp"%>
	<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
</head>
<body class="page-header-fixed">
<input type="hidden" id="dprtId" value="${user.jgdm}" />
<input type="hidden" id="userId" name="userId" value="${user.id}" />
<input type="hidden" id="userType" value="${user.userType}" />
<input type="hidden" id="kcid" value="${id}" />
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
		<!-- BEGIN CONTENT -->
<div class="page-content">
	<div class="panel panel-primary">
		<div class="panel-heading  ">
			<div id="NavigationBar"></div>
		</div>
		<div class=" panel-body padding-bottom-0" id="stock_detail">
			<div class="col-lg-12 col-sm-12 col-xs-12 col-md-12 padding-left-0 padding-right-0">
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">部件号</div>
							<div class="font-size-9 ">PN</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="bjh_view" class="form-control"  type="text" disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">名称</div>
							<div class="font-size-9 ">Name</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="ckmc_view" class="form-control"  type="text"disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">批次号</div>
							<div class="font-size-9 ">Batch No.</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="pch_view" class="form-control"   type="text" disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">序列号</div>
							<div class="font-size-9 ">SN</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="sn_view" class="form-control"  type="text" disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">仓库</div>
							<div class="font-size-9 ">Warehouse</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="ck_view" class="form-control"  type="text" disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">库位</div>
							<div class="font-size-9 ">Library</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="kw_view" class="form-control"  type="text" disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">库存数量</div>
							<div class="font-size-9 ">Quantity</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<div id="" class="input-group" style="width: 100%;">
							    <input id="kcsldw_view"  class="form-control" type="text" disabled="disabled">
						   </div>
						</div>
				    </div>
				    
				     <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" style='height:34px;'>
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">冻结数量</div>
							<div class="font-size-9 ">Quantity</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<div id="djno" class="input-group" style="width: 100%;">
							      <input id="djsl_view"  class="form-control" type="text" disabled="disabled">
						   </div>
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">库存成本</div>
							<div class="font-size-9 ">Cost</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<div id="" class="input-group" style="width: 100%;">
							    <input id="kccb_view" ondblclick="" class="form-control" type="text" disabled="disabled">
								<span class="input-group-btn">
									<select class='form-control' id="biz_view" style='width:50px;border-left:0px;' disabled="disabled">
									</select>
								</span>
						   </div>
						</div>
				    </div>
				    
				     <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">库存价值</div>
							<div class="font-size-9 ">Price</div>
						</label>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
							<div id="" class="input-group" style="width: 100%;">
							    <input id="jz_view"  class="form-control fxyz" type="text"  disabled="disabled">
								<span class="input-group-btn">
									<select class='form-control' id="jzbz_view" style='width:50px;border-left:0px;'  disabled="disabled">
									</select>
								</span>
						   </div>
						</div>
				    </div>
				    
				     <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">产权</div>
							<div class="font-size-9 ">Right</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="cqbh_view" class="form-control" type="text"  disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">货架寿命</div>
							<div class="font-size-9 ">Life</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="hjsm_view" class="form-control" data-date-format="yyyy-mm-dd"   placeholder="" type="text" name='date-picker' disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">来源</div>
							<div class="font-size-9 ">Source</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="hcly_view" class="form-control" type="text"  disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">GRN</div>
							<div class="font-size-9 ">GRN</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="grn_view" class="form-control" type="text" disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">TSN</div>
							<div class="font-size-9 ">TSN</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="tsn_view" class="form-control"  placeholder="" type="text" disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">TSO</div>
							<div class="font-size-9 ">TSO</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="tso_view" class="form-control"  placeholder="" type="text" disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">CSN</div>
							<div class="font-size-9 ">CSN</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="csn_view" class="form-control"  placeholder="" type="text" disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">CSO</div>
							<div class="font-size-9 ">CSO</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="cso_view" class="form-control"  placeholder="" type="text"  disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">生产日期</div>
							<div class="font-size-9 ">Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="scrq_view" class="form-control"  data-date-format="yyyy-mm-dd" type="text" name='date-picker' disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">状态</div>
							<div class="font-size-9 ">Status</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="zt_view" class="form-control"  type="text" disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">存放要求</div>
							<div class="font-size-9 ">Requirements</div>
						</span>
						<div class="col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
							<textarea class='form-control' style='height:55px;' id='cfyq_view' disabled="disabled"></textarea>
						</div>
				    </div>
				    
				    <div class="col-md-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">备注</div>
							<div class="font-size-9 ">Remark</div>
						</span>
						<div class="col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
							<textarea class='form-control' style='height:55px;' id='bz_view' disabled="disabled"></textarea>
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">物料类别</div>
							<div class="font-size-9 ">Type</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="wllb_view" class="form-control"  type="text" disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">厂家件号</div>
							<div class="font-size-9 ">CJ No.</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="cjjh_view" class="form-control"  placeholder="" type="text" disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">规格</div>
							<div class="font-size-9 ">Specifications</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="gg_view" class="form-control"  placeholder="" type="text" disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">型号</div>
							<div class="font-size-9 ">Model</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="xh_view" class="form-control"   type="text"  disabled="disabled">
						</div>
				    </div>				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">管理级别</div>
							<div class="font-size-9 ">Level</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="gljb_view" class="form-control"   type="text"  disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">制造厂商</div>
							<div class="font-size-9 ">Manufacturer</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="zzcs_view" class="form-control"  placeholder="" type="text"  disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">最大库存数</div>
							<div class="font-size-9 ">Maximum</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="maxkcs_view" class="form-control"  placeholder="" type="text"  disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">最小库存数</div>
							<div class="font-size-9 ">Minimum </div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="minkcs_view" class="form-control"  type="text"  disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">上架时间</div>
							<div class="font-size-9 ">Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="sjsj_view" class="form-control"  data-date-format="yyyy-mm-dd"  type="text"  disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">上架人</div>
							<div class="font-size-9 ">Shelf</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="sjr_view" class="form-control"   type="text" disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">最近修改时间</div>
							<div class="font-size-9 ">Date</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="update_view" class="form-control"  data-date-format="yyyy-mm-dd"  type="text" disabled="disabled">
						</div>
				    </div>
				    
				    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
						<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12  margin-topnew-2">最近修改人</div>
							<div class="font-size-9 ">Modifier</div>
						</span>
						<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
							<input id="update_person_view" class="form-control"   type="text" disabled="disabled">
						</div>
				    </div>
				    <div class='clearfix'></div>
			</div>
			<div class='clearfix'></div>
			<!-- 附加信息 -->
		            <div class="panel panel-primary">
						<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
							<a data-toggle="collapse" data-parent="" href="#certificate_info" class="collapsed">
								<div class="pull-left">
									<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
								</div>
								<div class="pull-left">
									<div class="font-size-12">证书信息</div>
									<div class="font-size-9 ">Certificate info</div>
								</div>
								<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
								<div class="clearfix"></div>
							</a>
						</div>
						<div id="certificate_info" class="panel-collapse collapse">
							<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
							    <div id="" class="col-lg-12 col-md-12 padding-leftright-8" style="overflow-x: auto;">
								    <table id="" class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important;'>
									<thead>                              
										<tr>
											<th class='colwidth-9'>
												<div class="font-size-12 line-height-18" >证书类型</div>
												<div class="font-size-9 line-height-18">Type</div>
											</th>
											<th class='colwidth-9' onclick="" name="">
												<div class="font-size-12 line-height-18">证书编号</div>
												<div class="font-size-9 line-height-18">Certificate No.</div>
											</th>
											<th class='colwidth-20' onclick="" name="">
													<div class="font-size-12 line-height-18">存放位置</div>
													<div class="font-size-9 line-height-18">Position</div>
											</th>
											<th class='colwidth-9' onclick="" name="">
													<div class="font-size-12 line-height-18">签署日期</div>
													<div class="font-size-9 line-height-18">Date</div>
											</th>
											<th class='colwidth-9' onclick="" name="">
													<div class="font-size-12 line-height-18">附件</div>
													<div class="font-size-9 line-height-18">Attachment</div>
											</th>
											
										</tr> 
									</thead>
								<tbody id="store_inner_certificate_list"></tbody>


								</table>
								</div>
						
							</div>
						</div>
					</div>
					<!--借用归还履历  -->
					  <div class="panel panel-primary" id="borrowInfo">
						<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
							<a data-toggle="collapse" id='' data-parent="" href="#borrow_history" class="collapsed">
								<div class="pull-left">
									<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
								</div>
								<div class="pull-left">
									<div class="font-size-12">借用归还履历</div>
									<div class="font-size-9 ">borrow resume</div>
								</div>
								<div class="pull-left">
									<div class="font-size-12"><a href="javascript:more()" id="more">更多</a></div>
								</div>
								<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
								<div class="clearfix"></div>
							</a>
						</div>
						<div id="borrow_history" class="panel-collapse collapse" >
							<div class="panel-body padding-left-0 padding-right-0" >
							    	<div id="" class="col-lg-12 col-md-12 padding-leftright-8" style="overflow-x: auto;">
								    <table id="" class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important;'>
									<thead>                              
										<tr>
											<th class='colwidth-9'>
												<div class="font-size-12 line-height-18" >类型</div>
												<div class="font-size-9 line-height-18">Type</div>
											</th>
											<th class='colwidth-9' onclick="" name="">
												<div class="font-size-12 line-height-18">时间</div>
												<div class="font-size-9 line-height-18">Time</div>
											</th>
											<th class='colwidth-20' onclick="" name="">
													<div class="font-size-12 line-height-18">人员</div>
													<div class="font-size-9 line-height-18">Person</div>
											</th>
											<th class='colwidth-20' onclick="" name="">
													<div class="font-size-12 line-height-18">数量</div>
													<div class="font-size-9 line-height-18">Quantity</div>
											</th>
											<th class='colwidth-20' onclick="" name="">
													<div class="font-size-12 line-height-18">是否长期借用</div>
													<div class="font-size-9 line-height-18">borrow</div>
											</th>
											<th class='colwidth-20' onclick="" name="">
													<div class="font-size-12 line-height-18">借用备注</div>
													<div class="font-size-9 line-height-18">borrow remark</div>
											</th>
											
										</tr> 
									</thead>
									<tbody id='borrowTbody'>							
									</tbody>
								</table>
								</div>
						
							</div>
						</div>
					</div>
					
					<div class="panel panel-primary">
						<div class="panel-heading bg-panel-heading padding-top-3 padding-bottom-1 collapse-style">
							<a data-toggle="collapse" id='' data-parent="" href="#resume_info" class="collapsed">
								<div class="pull-left">
									<input type='checkbox' class='selectCheckbox' style='margin-top:6px;margin-right:8px;'/>
								</div>
								<div class="pull-left">
									<div class="font-size-12">库存履历</div>
									<div class="font-size-9 ">Stock resume</div>
								</div>
								<i class="fa fa-chevron-left pull-right margin-top-5" data-icon-hide="fa fa-chevron-down" data-icon-show="fa fa-chevron-left"></i>
								<div class="clearfix"></div>
							</a>
						</div>
						<div id="resume_info" class="panel-collapse collapse" >
							<div class="panel-body padding-left-0 padding-right-0" >
							    	<div id="" class="col-lg-12 col-md-12 padding-leftright-8" style="overflow-x: auto;">
								    <table id="" class="table table-thin table-bordered table-striped table-hover table-set" style='margin-bottom:0px !important;'>
									<thead>                              
										<tr>
											<th class='colwidth-9'>
												<div class="font-size-12 line-height-18" >操作时间</div>
												<div class="font-size-9 line-height-18">Date</div>
											</th>
											<th class='colwidth-9' onclick="" name="">
												<div class="font-size-12 line-height-18">操作人</div>
												<div class="font-size-9 line-height-18">Operator</div>
											</th>
											<th class='colwidth-20' onclick="" name="">
													<div class="font-size-12 line-height-18">操作说明</div>
													<div class="font-size-9 line-height-18">Description</div>
											</th>
											
										</tr> 
									</thead>
									<tbody id='hisTbody'>							
									</tbody>
								</table>
								</div>
						
							</div>
						</div>
					</div>
		   </div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/js/thjw/material2/stock/material/outsideStock_view.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/Math.uuid.js"></script>
		<%@ include file="/WEB-INF/views/open_win/history_attach_win.jsp"%><!------附件对话框 -------->
	<script type="text/javascript" src="${ctx}/static/js/thjw/common/webuiPopoverUtil.js"></script><!-- 控件对话框 -->
</body>
</html>