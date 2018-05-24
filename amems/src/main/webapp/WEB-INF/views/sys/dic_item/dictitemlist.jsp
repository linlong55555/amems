<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
     <div  class="col-xs-12 padding-left-0 padding-right-0" >
			<div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0">
				<div class="col-lg-12 pull-right padding-left-0 padding-right-0">
					<button id='addbutton' type="button" onclick="add()"  
						class=" col-lg-1 btn btn-primary padding-top-1 padding-bottom-1 pull-left row-height checkPermission"
						permissioncode="sys:dict:main:01">
						<div class="font-size-12">新增</div>
						<div class="font-size-9">Add</div>
					</button>
					<button type="button" id='sysbutton' style="margin-left: 10px;" onclick="restoreDefault()"  
						class=" col-lg-0 btn btn-primary padding-top-1 padding-bottom-1 pull-left row-height checkPermission"
						permissioncode="sys:dict:main:04">
						<div class="font-size-12">恢复默认 </div>
						<div class="font-size-9">Restore Default</div>
					</button>
					<div class="col-lg-2 col-sm-6 col-xs-6 padding-left-10 padding-right-0 pull-left" style="margin-top: 12px;">
					    <label name="lxmc" id="lxmc" ></label>
					</div>
                   <div class=" pull-right padding-left-0 padding-right-0">
						<div class=" pull-left padding-left-0 padding-right-0" style="width: 250px;">
							<input type="text" placeholder='值/描述' id="dickeyword_search" class="form-control ">
						</div>
	                    <div class=" pull-right padding-left-5 padding-right-0 ">   
							<button id="sjzdSearch" type="button" class=" btn btn-primary padding-top-1 padding-bottom-1 " onclick="searchRevision();">
								<div class="font-size-12">搜索</div>
								<div class="font-size-9">Search</div>
	                   		</button>
	                    </div> 
					</div>
				 </div> 
			</div>
			<div  class="col-lg-12 col-md-12 padding-left-0 padding-right-0 margin-top-10 table-h" style="overflow-x: auto;">
			<table class="table table-thin table-bordered table-hover table-set"  >
				<thead>
		   		<tr>
					<th  class=" colwidth-5">
						<div class="font-size-12 line-height-18">操作</div>
						<div class="font-size-9 line-height-18">Operation</div>
					</th>
					<th  class=" colwidth-7 sorting" onclick="orderBy('sz')" id="sz_order">
						  <div class="important">
					     <div class="font-size-12 line-height-18">值</div>
						 <div class="font-size-9 line-height-18">Value</div>
						</div>
					</th>
					<th  class=" colwidth-15 sorting" onclick="orderBy('mc')" id="mc_order">
						 <div class="important">
					     <div class="font-size-12 line-height-18">描述</div>
						 <div class="font-size-9 line-height-18">Description</div>
						</div>
					</th>
					<th  class=" colwidth-5 sorting" onclick="orderBy('xc')" id="xc_order">
					     <div class="font-size-12 line-height-18">项次</div>
						 <div class="font-size-9 line-height-18">Item No.</div>
						</div>
					</th>
					<th class=" colwidth-9 sorting" onclick="orderBy('whrid')" id="whrid_order">
						<div class="font-size-12 line-height-18">维护人</div>
						<div class="font-size-9 line-height-18">Maintainer</div>
					</th>
					<th class=" colwidth-10 sorting" onclick="orderBy('whsj')" id="whsj_order">
						<div class="font-size-12 line-height-18">维护时间</div>
						<div class="font-size-9 line-height-18">Maintenance Time</div>
					</th>
	 		 </tr>
			</thead>
			<tbody id="dicList">
			
			</tbody>
			</table>
    </div>	
</div>
