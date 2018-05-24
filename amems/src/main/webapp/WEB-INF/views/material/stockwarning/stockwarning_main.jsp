<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTENT -->
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
			<div class='searchContent margin-top-0 row-height'>
					<div
			class="col-xs-12 padding-left-0 padding-right-0 form-group" id="test11">
								<!--------搜索框start-------->
			<div class=" pull-left  padding-left-0 padding-right-0 margin-right-10 ">				
					<button type="button"  onclick="stockWaringOutExcel();"
						class="btn btn-primary padding-top-1 padding-bottom-1 pull-left padding-left-10">
						<div class="font-size-12">导出</div>
						<div class="font-size-9">Export</div>
					</button>
				</div>
		<div class=" pull-right padding-left-0 padding-right-0" >
				
				<label class=" pull-left  padding-left-0 padding-right-0" style="padding:2px 4px;border: 1px solid #ffc0c0;background-color:#ffd6d6; font-weight:normal;margin-right:10px;">
						<span class="pull-left   text-right padding-left-0 padding-right-0">
							<div class="font-size-12">最低库存预警</div>
							<div class="font-size-9">Minimum</div>
						</span>
						<div class="pull-left padding-left-8 padding-right-0 ">
						 <input type="checkbox" name="gg" id="zuidi"  onclick="onclick1()"> 
						</div>
				</label>
				
				<label class=" pull-left  padding-left-0 padding-right-0" style="padding:2px 4px;border: 1px solid #efef44;background-color: #ffffd2; font-weight:normal; margin-right:10px;">
						<span class="pull-left   text-right padding-left-0 padding-right-0">
							<div class="font-size-12">最高库存预警 </div>
							<div class="font-size-9">Maximum</div>
						</span>
						<div class="pull-left padding-left-8 padding-right-0 ">
				 			<input type="checkbox" name="gg" id="zuigao" onclick="changeSelectedPlane2()">
						</div>
				</label>	

			<div class=" pull-left  padding-left-0 padding-right-0  ">
						<span class="pull-left   text-right padding-left-0 padding-right-0">
							<div class="font-size-12">航材类型</div>
							<div class="font-size-9">Airmaterial Type</div>
						</span>
						<div class="pull-left padding-left-8 padding-right-0 "  style="width: 200px; margin-right:10px;">
							<select id="hclx" name="hclx" class="form-control " onchange="changeSelectedPlane()">
								<option value="" selected="selected">显示全部All</option>
								<c:forEach items="${materialTypeEnum}" var="item">
								  	<option value="${item.id}" >${item.name}</option>
								</c:forEach>
							</select>
						</div>
				</div>
				
				<div class=" pull-left padding-left-0">
						<span class="pull-left  text-right padding-left-0 padding-right-0 ">
							<div class="font-size-12">组织机构</div>
							<div class="font-size-9">Organization</div>
						</span>
						<div class="pull-left padding-left-8 padding-right-0 "  style="width: 180px; margin-right:10px;">
							<select id="dprtcode" name="dprtcode" class="form-control " onchange="changeSelectedPlane()">
								<c:forEach items="${accessDepartment}" var="type">
									<option value="${type.id}" <c:if test="${user.jgdm eq type.id }">selected='selected'</c:if>>${erayFns:escapeStr(type.dprtname)}</option>
								</c:forEach>
							</select>
						</div>
				</div>
					
					<div class=" pull-left padding-left-0 padding-right-0" style="width:200px;" >
						<input placeholder="件号/中英文名称/厂家件号" id="keyword_search" class="form-control " type="text">
					</div>
                    <div class=" pull-right padding-left-5 padding-right-0 ">   
						<button type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
							<div class="font-size-12">搜索</div>
							<div class="font-size-9">Search</div>
                   		</button>
                    </div> 
				</div>
				<!------------搜索框end------->
					
					</div>
	
			
				<div class="clearfix"></div>
				</div>

				<div  class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height"  style="overflow-x:scroll">
					<table id="sqzz" class="table table-thin table-bordered table-set " id="borrow_return_outstock_table" style="min-width:1000px!important;">
						<thead>
							<tr>
								<th class=" colwidth-5" style="vertical-align: middle;">
									<div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class=" sorting colwidth-15"  name="column_bjh"  c onclick="orderBy('bjh', this)" >
									<div class="important">
									<div class="font-size-12 line-height-18">件号</div>
									<div class="font-size-9 line-height-18">P/N</div>
									</div>
								</th>
								<th class="sorting colwidth-20"   name="column_ywms" onclick="orderBy('ywms', this)" >
									<div class="important">
									<div class="font-size-12 line-height-18">英文名称</div>
									<div class="font-size-9 line-height-18">F.Name</div>
									</div>
								</th>
								<th  class="sorting colwidth-15" name="column_zwms" onclick="orderBy('zwms', this)">
									<div class="important">
									<div class="font-size-12 line-height-18">中文名称</div>
									<div class="font-size-9 line-height-18">CH.Name</div>
									</div>
								</th>
								<th   class="sorting colwidth-7" name="column_min_kcl" onclick="orderBy('min_kcl', this)">
										<div class="font-size-12 line-height-18">最低库存量</div>
										<div class="font-size-9 line-height-18">Min Stock</div>
								</th>
								<th   class="sorting colwidth-7" name="column_max_kcl" onclick="orderBy('max_kcl', this)">
										<div class="font-size-12 line-height-18">最高库存量</div>
										<div class="font-size-9 line-height-18">Max Stock</div>
								</th>
								<th   class="sorting colwidth-7" name="column_kcsl" onclick="orderBy('kcsl', this)">
										<div class="font-size-12 line-height-18">库存数量</div>
										<div class="font-size-9 line-height-18">Stock Num</div>
								</th>
										<th  class="sorting colwidth-5" name="column_jldw" onclick="orderBy('jldw', this)">
									<div class="font-size-12 line-height-18">单位</div>
									<div class="font-size-9 line-height-18">Unit</div>
								</th>
								<th  class="sorting colwidth-15" name="column_cjjh" onclick="orderBy('cjjh', this)">
									<div class="important">
									<div class="font-size-12 line-height-18">厂家件号</div>
									<div class="font-size-9 line-height-18">MP/N</div>
									</div>
								</th>
								<th  class="sorting colwidth-9" name="column_gljb" onclick="orderBy('gljb', this)">
									<div class="font-size-12 line-height-18">管理级别</div>
									<div class="font-size-9 line-height-18">Level</div>
								</th>
								<th   class="colwidth-9">
										<div class="font-size-12 line-height-18">航材类别</div>
										<div class="font-size-9 line-height-18">Airmaterial Type</div>
								</th>
								<th  class="colwidth-15">
									<div class="font-size-12 line-height-18">组织机构</div>
									<div class="font-size-9 line-height-18">Organization</div>
								</th>
							</tr>
						</thead>
						<tbody id="list">
						</tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="${ctx}/static/js/thjw/material/stockwarning/stockwarning_main.js"></script>
</body>
</html>