<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade in modal-new" id="AddalertModal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">
   <div class="modal-dialog" style='width:50%;'>
       <div class="modal-content">
           <div class="modal-header modal-header-new" >
                <h4 class="modal-title" >
                   <div class='pull-left'>
                  	  <div class="font-size-14 " id="AddalertModal_modalName"></div>
					  <div class="font-size-12" id="AddalertModal_modalEname"></div>
					  <input type="hidden"  id="mark">
				   </div>
				   <div class='pull-right' >
					  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
				   </div>
				   <div class='clearfix'></div>
                 </h4>
            </div>
            <div class="modal-body" >
              	<div class="col-xs-12  ">
              	<div class="input-group-border">
              	<form id="form">
                    <div class="col-xs-12 padding-left-0 padding-right-0 form-group ">
						<label class="col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2"><span style="color: red">*</span>机型</div>
							<div class="font-size-9">A/C Type</div>
						</label>
						<div class="col-xs-9 padding-leftright-8">
							<input class="form-control" id="fjjx" name="fjjx" type="text" maxlength="50">
						</div>
					</div>
					
                    <div class="col-xs-12 padding-left-0 padding-right-0 form-group">
						<label class="col-xs-2 text-right padding-left-0 padding-right-0">
							<div class="font-size-12 margin-topnew-2">描述</div>
							<div class="font-size-9">Desc</div>
						</label>
						<div class="col-xs-9 padding-leftright-8">
							<textarea class="form-control" style="height:55px"  name="bz" id="bz" maxlength="300"></textarea>
						</div>
					</div>
					
					<div class="clearfix"></div>
				</form>
				</div>
				</div>
                <div class="clearfix"></div>              
           </div>
  			<div class="modal-footer ">
				<div class="col-xs-12 padding-leftright-8" >
					<div class="input-group">
						<span class="input-group-addon modalfootertip" >
			                <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
						</span>
						<span class="input-group-addon modalfooterbtn">
						<button type="button" id="save" onclick="AddalertModal.save();" class="btn btn-primary padding-top-1 padding-bottom-1">
							<div class="font-size-12">保存</div>
							<div class="font-size-9">Save</div>
						</button>
						   <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
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
<script type="text/javascript" src="${ctx}/static/js/thjw/project2/actype/actype_open.js"></script><!--当前页面js  -->
