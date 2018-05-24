<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
	
	<style type="text/css">
		.ztree [class^="icon-"] {
			font-family: FontAwesome;
		}
		.line {
		    border-bottom: 0px;
		}
	</style>
	
	<div id="installation_tree_view" style="display: none;">
		
		<!-- 装机清单树start -->
		<div id="installation_tree_view_left" class="col-lg-3 col-xs-12 padding-left-0 padding-right-0">
			
			<div class="panel panel-primary">
			
				<div class="panel-heading">
					<div class="font-size-12 line-height-12">装机清单</div>
					<div class="font-size-9 line-height-9">Installation List</div>
				</div>
				<div class="panel-body padding-left-0 padding-right-0">
				
					<!-- <div class="col-lg-9 col-lg-offset-3 padding-leftright-8">
						<div class="input-group">
							<input type="text" class="form-control" id="installation_tree_search" placeholder="ATA/件号/名称/序列号/安装记录单号"/>
							<span class="input-group-btn">
								<button class="btn btn-primary padding-top-1 padding-bottom-1" type="button">
									<div class="font-size-12">搜索</div>
									<div class="font-size-9">Search</div>
								</button>
							</span>
						</div>
					</div>
					<div class="clearfix"></div>
					
					<hr style="margin-top: 5px;margin-bottom: 0px;"> -->
					
					<div class="zTreeDemoBackground margin-right-10">
						<ul id="installation_tree" class="ztree" style="height: 430px; overflow: auto;"></ul>
					</div>
				</div>
	   		</div>
		</div>
		<!-- 装机清单树end -->
		
		<!-- 装机清单详情start -->
		<div id="installation_tree_view_right" class="col-lg-9 col-xs-12 padding-left-5 padding-right-0">
			<div class="panel panel-primary">
			
				<div class="panel-heading">
					<div class="font-size-12 line-height-12">部件信息</div>
					<div class="font-size-9 line-height-9">Component Info</div>
				</div>
				<div class="panel-body" id="installation_tree_view_detail">
					<%@ include file="/WEB-INF/views/produce/installationlist/installationlist_common.jsp"%>
					<div class="col-lg-12 padding-right-0 padding-left-0 padding-bottom-8">
						<div class="pull-right">
							<button id="installation_tree_del_btn" type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="installationlist_tree.del()">
								<div class="font-size-12">删除</div>
								<div class="font-size-9">Delete</div>
							</button>
							<button id="installation_tree_save_btn" type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="installationlist_tree.save()">
								<div class="font-size-12">保存</div>
								<div class="font-size-9">Save</div>
							</button>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
	   		</div>
		</div>
		<!-- 装机清单详情end -->
		<div class="clearfix"></div>
	</div>
	
	<script type="text/javascript" src="${ctx}/static/js/thjw/produce/installationlist/installationlist_tree.js"></script>