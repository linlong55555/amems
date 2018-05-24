<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body>
<input type="hidden" value="${user.jgdm}" id="dprtcode">
<input type="hidden"  value="${viewid}" id="viewid">
<div class="page-content">
	<div class="panel panel-primary">
		<div class="panel-heading" id="NavigationBar"></div>
		<div class="panel-body">
			<div class="panel-heading padding-left-16 padding-top-3  col-xs-12  margin-bottom-10" style="border-bottom: 1px solid #ccc;">
				<div class=" pull-left margin-right-10" >
					<div class="font-size-16 line-height-18">基本信息</div>
					<div class="font-size-9 ">Basic  info</div>
				</div>	
			</div>	
			<div class="col-xs-12 margin-top-8 padding-left-0  padding-right-0" >
				 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">出库单号</div>
						<div class="font-size-9 ">Outbound  No</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
						<input type="text"   class="form-control " id="ckdh" readonly="readonly">
					</div>
				</div>
				 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">出库类型</div>
						<div class="font-size-9 ">Outbound  Type</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
						<input type="text"   class="form-control " id="cklx" readonly="readonly">
					</div>
				</div>
				 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">申请人</div>
						<div class="font-size-9 ">Applicant</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
						<input type="text"  class="form-control " id="sqr" readonly="readonly">
					</div>
				</div>
				 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">申请日期</div>
						<div class="font-size-9 ">Applicant Date</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
						<input type="text"  class="form-control " id="sqsj" readonly="readonly">
					</div>
				</div>
				 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">飞机注册号</div>
						<div class="font-size-9 ">A/C REG</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
						<input type="text"  class="form-control " id="fjzch" readonly="readonly">
					</div>
				</div>
				 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">借调对象</div>
						<div class="font-size-9 ">Seconded Obj</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
						<input type="text"  class="form-control " id="jddxms" readonly="readonly">
					</div>
				</div>
				 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">借调对象负责人</div>
						<div class="font-size-9 ">Operator</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
						<input type="text"  class="form-control " id="jdfzr" readonly="readonly">
					</div>
				</div>
				 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">标识</div>
						<div class="font-size-9 ">Identify</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
						<input type="text"  class="form-control " id="sgbs" readonly="readonly">
					</div>
				</div>
				<div class="clearfix"></div>
				 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">状态</div>
						<div class="font-size-9 ">Status</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
						<input type="text"  class="form-control " id="zt" readonly="readonly">
					</div>
				</div>
				 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">出库人</div>
						<div class="font-size-9 "> Operator</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
						<input type="text"  class="form-control " id="ckr" readonly="readonly">
					</div>
				</div>
				 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">出库日期</div>
						<div class="font-size-9 ">Outstock Date</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
						<input type="text"  class="form-control " id="cksj" readonly="readonly">
					</div>
				</div>
				 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">制单人</div>
						<div class="font-size-9 ">Creator</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
						<input type="text"  class="form-control " id="zdri" readonly="readonly">
					</div>
				</div>
				<div class="clearfix"></div>
				 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">制单时间</div>
						<div class="font-size-9 ">Create Time</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8 span-input-div">
						<input type="text"  class="form-control " id="zdsj" readonly="readonly">
					</div>
				</div>
				<div class="col-xs-12 col-sm-12  padding-left-0 padding-right-0 form-group">
					<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 ">备注</div>
							<div class="font-size-9 ">Note</div>
					</span>
					<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
						<textarea class="noteditable form-control padding-left-3 padding-right-3" id="bz"   
							style="height:55px" maxlength="300" readonly></textarea>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="panel-heading padding-left-16 padding-top-3  col-xs-12  margin-bottom-10" style="border-bottom: 1px solid #ccc;">
				<div class=" pull-left margin-right-10" >
					<div class="font-size-16 line-height-18">航材信息</div>
					<div class="font-size-9 ">Aircraft info</div>
				</div>	
			</div>	
			<div  class="col-xs-12 text-center  margin-top-10 margin-bottom-5 padding-left-0 padding-right-0 padding-top-0"  style="overflow-x:scroll">
				<table class="table table-thin table-bordered table-set" style="min-width:1100px">
					<thead>
						<tr>
							<th class="colwidth-5">
								<div class="font-size-12 line-height-18">序号</div>
								<div class="font-size-9 line-height-18">No.</div>
							</th>
							<th class="colwidth-15">
								<div class="font-size-12 line-height-18">部件号</div>
								<div class="font-size-9 line-height-18">P/N</div>
							</th>
							<th class="colwidth-15">
								<div class="font-size-12 line-height-18">英文名称</div>
								<div class="font-size-9 line-height-18">F.Name</div>
							</th>
							<th class="colwidth-15">
								<div class="font-size-12 line-height-18">中文名称</div>
								<div class="font-size-9 line-height-18">CH.Name</div>
							</th>
							<th class="colwidth-15">
								<div class="font-size-12 line-height-18">厂家件号</div>
								<div class="font-size-9 line-height-18">MP/N</div>
							</th>
							<th class="colwidth-15">
								<div class="font-size-12 line-height-18">航材类型</div>
								<div class="font-size-9 line-height-18">Airmaterial type</div>
							</th>
							<th class="colwidth-15">
								<div class="font-size-12 line-height-18">序列号</div>
								<div class="font-size-9 line-height-18">S/N</div>
							</th>
							<th class="colwidth-15">
								<div class="font-size-12 line-height-18">批次号</div>
								<div class="font-size-9 line-height-18">P/N</div>
							</th>
							<th class="colwidth-15">
								<div class="font-size-12 line-height-18">适航证号</div>
								<div class="font-size-9 line-height-18">Certificate</div>
							</th>
							<th class="colwidth-15">
								<div class="font-size-12 line-height-18">货架寿命</div>
								<div class="font-size-9 line-height-18">Shelf-Life</div>
							</th>
							<th  class="colwidth-15">
								<div class="font-size-12 line-height-18">出库数量</div>
								<div class="font-size-9 line-height-18">Outstock Num</div>
							</th>
							<th  class="colwidth-15">
								<div class="font-size-12 line-height-18">已退数量</div>
								<div class="font-size-9 line-height-18">Returned Num</div>
							</th>
							<th class="colwidth-15">
								<div class="font-size-12 line-height-18">单位</div>
								<div class="font-size-9 line-height-18">Unit</div>
							</th>
							<th class="colwidth-15">
								<div class="font-size-12 line-height-18">仓库</div>
								<div class="font-size-9 line-height-18">Store</div>
							</th>
							<th class="colwidth-15">
								<div class="font-size-12 line-height-18">库位</div>
								<div class="font-size-9 line-height-18">Storage location</div>
							</th>
						</tr>
					</thead>
					<tbody id="historyaviationlist">
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/outstock/outboundhistory_view.js"></script>
</body>
</html>