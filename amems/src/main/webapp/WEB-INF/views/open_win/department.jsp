<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<style>
.ztree * {
    padding: 0;
    margin: 0;
    line-height:20px;
    margin-top:5px;
    font-size: 14px;
    font-family: Verdana, Arial, Helvetica, AppleGothic, sans-serif;
}

.ztree ul li{
list-style-type:none;
font-size:14px;
}
.ztree li {
    padding: 0;
    margin: 0;
    list-style: none;
    line-height: 25px;
    text-align: left;
    white-space: nowrap;
    outline: 0;
}
.ztree li a.curSelectedNode {
    padding-top: 0px;
    background-color: #FFE6B0;
    color: black;
    height: 18px;
    line-height: 16px;
    border: 1px #FFB951 solid;
    opacity: 0.8;
    margin-top: 5px;
}
.ztree li a {
    padding: 1px 3px 0 0;
    margin: 0;
    cursor: pointer;
    height: 17px;
    color: #333;
    background-color: transparent;
    text-decoration: none;
    vertical-align: top;
    display: inline-block;
    margin-top: 5px;
}
</style>
<script type="text/javascript" src="${ctx}/static/js/thjw/open_win/department.js"></script>
<div class="modal fade in modal-new" id="departmentModal"  role="dialog" data-backdrop="static" data-keyboard="false"
			aria-labelledby="departmentModal" aria-hidden="true">
			<div class="modal-dialog" style="width:500px!important;">
				<div class="modal-content">
						<div class="modal-header modal-header-new" >
                       <h4 class="modal-title" >
                          <div class='pull-left'>
	                       <div class="font-size-12 ">部门列表</div>
							<div class="font-size-9">Department List</div>
						  </div>
						  <div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" onclick="departmentModal.closemodal()" aria-label="Close"></button>
						  </div>
						  <div class='clearfix'></div>
                       </h4>
                  </div>
					<div class="modal-body" >
              			<div class="col-xs-12 margin-top-8 " style="padding-left:0px;padding-right:0px">
             		 		<div class="panel panel-primary">
										<div class="panel-body padding-left-0 padding-top-0 padding-right-0" >						
									<!-- start:table -->
									<div class="margin-top-0 col-lg-12 col-sm-12 col-xs-12">
										<div id="tab_trade" class="tab-pane active col-xs-12" style="overflow: auto;">
											<div class="zTreeDemoBackground">
												<ul id="treeDemo" class="ztree" ></ul>
											</div>
										</div>
									</div>
 <!-- 					<div
										class="col-lg-6 col-sm-6 col-xs-6 padding-left-0 margin-bottom-8 padding-right-0">
										
											
											<div class="col-lg-3 col-sm-3 col-xs-3 padding-left-0 margin-bottom-8 padding-right-0">
						                    
						                    <div class="font-size-12">已选择部门</div>
											<div class="font-size-9">Department</div>
						                 
						                    </div>
						                    
						                   <input class="form-control" id="um_selectDprtname" type="text"  /> 
											<div class="col-lg-9 col-sm-9 col-xs-9 padding-left-0 margin-bottom-8 padding-right-0">
											<ul id="bmlist">
											
											</ul>
						                	</div>
									</div>  -->
									</div>
										</div>
									</div>
									<div class="clearfix"></div>
								</div>
									<!-- end:table -->
								<div class="modal-footer">
									<div class="col-xs-12 padding-leftright-8" >
										<div class="input-group">
											<span class="input-group-addon modalfootertip" >
									                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
											</span>
											<span class="input-group-addon modalfooterbtn">
												<button type="button" onclick="departmentModal.setData()" 
													class="btn btn-primary padding-top-1 padding-bottom-1">
													<div class="font-size-12">确定</div>
													<div class="font-size-9">Confirm</div>
												</button>
												<button type="button" onclick="departmentModal.clearData()" id="departmentModal_clear"
													class="btn btn-primary padding-top-1 padding-bottom-1">
													<div class="font-size-12">清空</div>
													<div class="font-size-9">Clear</div>
												</button>
												<button type="button"
													class="btn btn-primary padding-top-1 padding-bottom-1"
													onclick="departmentModal.closemodal()">
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