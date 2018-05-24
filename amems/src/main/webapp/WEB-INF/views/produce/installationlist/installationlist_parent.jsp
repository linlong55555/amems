<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>

<style type="text/css">
	.ztree [class^="icon-"] {
		font-family: FontAwesome;
	}
	.line {
	    border-bottom: 0px;
	}
</style>

<div class="modal fade modal-new" id="installation_parent_modal" tabindex="-1" role="dialog" aria-labelledby="installation_parent_modal" aria-hidden="true" data-backdrop='static' data-keyboard= false>
	<div class="modal-dialog" style="width:40%">
			<div class="modal-content">	
				<div class="modal-header modal-header-new" >
                	<h4 class="modal-title" >
                  	<div class='pull-left'>
	                    <div class="font-size-14" id="chinaHead">上级部件</div>
						<div class="font-size-12" id="englishHead">Parent Part</div>
				  	</div>
				  	<div class='pull-right'>
				  		<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
				  	</div>
				  	<div class='clearfix'></div>
                	</h4>
              </div>
			<div class="modal-body padding-bottom-0">
				<div class="col-xs-12 margin-top-8">
					<div class="input-group-border" style="padding-top: 8px;padding-bottom: 5px;">	
						<div class="col-sm-12 padding-leftright-8">
							<div class="input-group">
								<input class="form-control" id="installation_parent_modal_keyword" placeholder="ATA/件号/名称/序列号" type="text"> 
									<span class="input-group-btn">
									<button name="keyCodeSearch" class="btn btn-primary padding-top-1 padding-bottom-1 pull-right" type="button" onclick="installationlist_parent.refreshTree()">
										<div class="font-size-12">搜索</div>
										<div class="font-size-9">Search</div>
									</button>
								</span>
							</div>
						</div>
						<div class='clearfix'></div>
						
						<div class="zTreeDemoBackground">
							<ul id="installation_parent_modal_tree" class="ztree" style="overflow: auto;"></ul>
						</div>
					
					
						<div class='clearfix'></div>
					</div>
				</div>
			</div>
			
			<div class="clearfix"></div>
			<div class="modal-footer">
				<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
			                <i class='glyphicon glyphicon-info-sign alert-info hidden'></i><p class="alert-info-message"></p>
						</span>
						<span class="input-group-addon modalfooterbtn">
							<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="installationlist_parent.save();">
			                   	<div class="font-size-12">确认</div>
								<div class="font-size-9">Confirm</div>
							</button>
							<button type="button" onclick="installationlist_parent.clear()" class="btn btn-primary padding-top-1 padding-bottom-1">
								<div class="font-size-12">清空</div>
								<div class="font-size-9">Clear</div>
							</button>
			              	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal">
			              		<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
							</button>
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="${ctx}/static/js/thjw/produce/installationlist/installationlist_parent.js"></script>
