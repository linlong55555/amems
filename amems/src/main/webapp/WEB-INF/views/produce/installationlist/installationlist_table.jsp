<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
	
	<div id="installation_table_view">
		<div id="installation_list_main_table_top_div" class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-0 table-height">
			<table id="installation_list_main_table" class="table table-thin table-bordered table-striped table-hover table-set" style="min-width: 1400px;">
				<thead>                              
					<tr>
						<th class="fixed-column colwidth-5">
							<div class="font-size-12 line-height-18" >操作</div>
							<div class="font-size-9 line-height-18">Operation</div>
						</th>
						<th class="colwidth-5">
							<div class="font-size-12 line-height-18">标识</div>
							<div class="font-size-9 line-height-18">Mark</div>
						</th>
						<th class="colwidth-5">
							<div class="font-size-12 line-height-18">状态</div>
							<div class="font-size-9 line-height-18">Status</div>
						</th>
						<th class="colwidth-5">
							<div class="font-size-12 line-height-18">生效</div>
							<div class="font-size-9 line-height-18">Effective</div>
						</th>
						<th class="colwidth-5">
							<div class="font-size-12 line-height-18">节点</div>
							<div class="font-size-9 line-height-18">Node</div>
						</th>
						<th class="colwidth-20">
							<div class="important">
								<div class="font-size-12 line-height-18">ATA章节号</div>
								<div class="font-size-9 line-height-18">ATA</div>
							</div>
						</th>
						<th class="colwidth-15">
							<div class="important">
								<div class="font-size-12 line-height-18">件号</div>
								<div class="font-size-9 line-height-18">P/N</div>
							</div>
						</th>
						<th class="colwidth-20">
							<div class="important">
								<div class="font-size-12 line-height-18">名称</div>
								<div class="font-size-9 line-height-18">Name</div>
							</div>
						</th>
						<th class="colwidth-15">
							<div class="important">
								<div class="font-size-12 line-height-18">序列号</div>
								<div class="font-size-9 line-height-18">S/N</div>
							</div>
						</th>
						<th class="colwidth-13">
							<div class="font-size-12 line-height-18">型号</div>
							<div class="font-size-9 line-height-18">Model</div>
						</th>
						<th class="colwidth-9" name="column_zy" >
							<div class="font-size-12 line-height-18">监控类型</div>
							<div class="font-size-9 line-height-18">Monitor Type</div>
						</th>
						<th class="colwidth-7" >
							<div class="font-size-12 line-height-18">分类</div>
							<div class="font-size-9 line-height-18">Category</div>
						</th>
						<th class="colwidth-9" >
							<div class="font-size-12 line-height-18">装机件类型</div>
							<div class="font-size-9 line-height-18">Installed Type</div>
						</th>
						<th class="colwidth-10" name="column_is_bj" >
							<div class="font-size-12 line-height-18">初始化日期</div>
							<div class="font-size-9 line-height-18">Init Date</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">初始化时间</div>
							<div class="font-size-9 line-height-18">Init Time</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">初始化循环</div>
							<div class="font-size-9 line-height-18">Init Cycle</div>
						</th>
						<th class="colwidth-13">
							<div class="font-size-12 line-height-18">批次号</div>
							<div class="font-size-9 line-height-18">B/N</div>
						</th>
						<th class="colwidth-9" >
							<div class="font-size-12 line-height-18">数量</div>
							<div class="font-size-9 line-height-18">Quantity</div>
						</th>
						<th class="colwidth-7" name="column_bz" >
							<div class="font-size-12 line-height-18">单位</div>
							<div class="font-size-9 line-height-18">Unit</div>
						</th>
						<th class="colwidth-7" name="column_username" >
							<div class="font-size-12 line-height-18">证书信息</div>
							<div class="font-size-9 line-height-18">Certificate</div>
						</th>
						<th class="colwidth-9" name="column_zt" >
							<div class="font-size-12 line-height-18">出厂日期</div>
							<div class="font-size-9 line-height-18">Production Date</div>
						</th>
						<th class="colwidth-9" name="column_username" >
							<div class="font-size-12 line-height-18">CAL</div>
							<div class="font-size-9 line-height-18">CAL</div>
						</th>
						<th class="colwidth-9" name="column_username" >
							<div class="font-size-12 line-height-18">TSN</div>
							<div class="font-size-9 line-height-18">TSN</div>
						</th>
						<th class="colwidth-9" name="column_username" >
							<div class="font-size-12 line-height-18">TSO</div>
							<div class="font-size-9 line-height-18">TSO</div>
						</th>
						<th class="colwidth-9" name="column_username" >
							<div class="font-size-12 line-height-18">CSN</div>
							<div class="font-size-9 line-height-18">CSN</div>
						</th>
						<th class="colwidth-9" name="column_username" >
							<div class="font-size-12 line-height-18">CSO</div>
							<div class="font-size-9 line-height-18">CSO</div>
						</th>
						<th class="colwidth-13" name="column_username" >
							<div class="font-size-12 line-height-18">安装日期</div>
							<div class="font-size-9 line-height-18">Assembly Date</div>
						</th>
						<th class="colwidth-15" name="column_username" >
							<div class="important">
								<div class="font-size-12 line-height-18">安装记录单号</div>
								<div class="font-size-9 line-height-18">Assembly Record No.</div>
							</div>
						</th>
						<th class="colwidth-13" name="column_username" >
							<div class="font-size-12 line-height-18">拆除日期</div>
							<div class="font-size-9 line-height-18">Remove Date</div>
						</th>
						
						<th class="colwidth-15" name="column_username" >
							<div class="font-size-12 line-height-18">拆除记录单号</div>
							<div class="font-size-9 line-height-18">Remove No.</div>
						</th>
						<th class="colwidth-13" name="column_username" >
							<div class="font-size-12 line-height-18">改装记录</div>
							<div class="font-size-9 line-height-18">Record</div>
						</th>
						<th class="colwidth-13" name="column_username" >
							<div class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Note</div>
						</th>
						<th class="colwidth-13" name="column_whsj" >
							<div class="font-size-12 line-height-18">维护人</div>
							<div class="font-size-9 line-height-18">Maintainer</div>
						</th>
						<th class="colwidth-13" name="column_whsj" >
							<div class="font-size-12 line-height-18">维护时间</div>
							<div class="font-size-9 line-height-18">Maintenance Time</div>
						</th>
						<th class="colwidth-15" >
							<div class="font-size-12 line-height-18">组织机构</div>
							<div class="font-size-9 line-height-18">Organization</div>
						</th>
					</tr> 
				</thead>
				<tbody id="installation_list_main_table_list">
				</tbody>
			</table>
		</div>
		
		<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="installation_list_main_table_pagination"></div>
		<div class="clearfix"></div>
	</div>
	
<script type="text/javascript" src="${ctx}/static/js/thjw/produce/installationlist/installationlist_table.js"></script>