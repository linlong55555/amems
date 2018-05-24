<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<script type="text/javascript" src="${ctx}/static/js/thjw/material/stock/stock_edit.js"></script>
<div class="modal fade padding-top-0" id="stockEditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width: 1200px;">
		<div class="modal-content" >
			<div class="modal-body  padding-bottom-0" id="stockEditModalBody" >
			  	<div class="panel panel-primary">
					 <div class="panel-heading  padding-top-3 padding-bottom-1">
						<div class="font-size-16 line-height-18">库存信息修改</div>
						<div class="font-size-9 ">Stock Edit</div>
					</div>
					<div class="panel-body padding-top-10 padding-bottom-0">
	            		<!-- <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0"> -->
						<div id="form">
						<input type="hidden" name="id" id="id"  >
						<input type="hidden" name="kcly" id="kcly"  >
						<input type="hidden" name="bjid" id="bjid"  >
						
					    <div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12 line-height-18">部件号</div>
									<div class="font-size-9 line-height-18">P/N</div></label>
							<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
							    <input type="text"  id="bjh" class="form-control" value=" "  disabled="disabled"/>
							</div>
						</div>
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									    <div class="font-size-12 line-height-18">中文名称</div>
										<div class="font-size-9 line-height-18">CH.Name</div></label>
								<div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
								    <input type="text" id="zwms" class="form-control" value=" "  disabled="disabled"/>
								</div>
						</div>
				   		<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							    <div class="font-size-12 line-height-18">英文名称</div>
								<div class="font-size-9 line-height-18">F.Name</div></label>
							   <div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
								 <input type="text" id="ywms" class="form-control"  value=" " disabled="disabled"/>
							   </div>
				    	</div>
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10">
								<label class="col-lg-4 col-sm-4 col-xs-4  text-right padding-left-0 padding-right-0">
							    <div class="font-size-12 line-height-18">管理级别</div>
								<div class="font-size-9 line-height-18">level</div></label>
								<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									   <input type="text" id="gljb" class="form-control"  value=" " disabled="disabled"/>
							   </div>
					    </div>
					    <div class="clearfix"></div>
					    
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">航材类型</div>
									<div class="font-size-9 line-height-18">type</div>
								</label>
								<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								     <input type="text" id="hclx" class="form-control"  value=" " disabled="disabled"/>
								</div>
						</div>
							
						<div class=" col-lg-3 col-sm-6 col-xs-12  padding-left-0 padding-right-0">
									<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								    <div class="font-size-12 line-height-18">数量</div>
									<div class="font-size-9 line-height-18">Num</div></label>
								   <div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
										<input type="text"  class="form-control"  id="kcsl" value=" " disabled="disabled"/>
								 </div>
					    </div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">仓库名称</div>
									<div class="font-size-9 line-height-18"> Store Name</div>
								</label>
								<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" id="ckmc" value=" " class="form-control " readonly />
								</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">库位号</div>
									<div class="font-size-9 line-height-18">location</div>
								</label>
								<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" id="kwh" value=" " class="form-control " readonly />
								</div>
						</div>
						<div class="clearfix"></div>
						
						
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">许可证号</div>
									<div class="font-size-9 line-height-18">Licence</div>
								</label>
								<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" value=" " class="form-control " id="xkzh" name="xkzh" maxlength="50"   />
								</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">适航证号</div>
									<div class="font-size-9 line-height-18">Certificate</div>
								</label>
								<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" value=" " class="form-control " id="shzh" name="shzh" maxlength="50"   />
								</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 line-height-18">适航证位置</div>
									<div class="font-size-9 line-height-18">position</div>
								</label>
								<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
									<input type="text" value=" " id="shzwz"  name="shzwz" maxlength="30" class="form-control "    />
								</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">货架寿命</div>
								<div class="font-size-9 line-height-18">Shelf-Life</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" id="hjsm"  name="hjsm" class='form-control date-picker'   />
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">索赔期限</div>
								<div class="font-size-9 line-height-18">Claim period</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" class="form-control date-picker" name="spqx" id="spqx"  />
							</div>
						</div>
						 
						 <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">生产日期</div>
								<div class="font-size-9 line-height-18">date</div></label>
								<div class=" col-xs-8 padding-left-8 padding-right-0">
									<input class="form-control date-picker" name="scrq" d type="text"  id="scrq" maxlength="10" />
								</div>
						</div>
						 <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">制造厂商</div>
								<div class="font-size-9 line-height-18">Manufacturer</div></label>
								<div class=" col-xs-8 padding-left-8 padding-right-0">
									<input class="form-control " name="zzcs"  type="text"  id="zzcs" maxlength="10"/>
								</div>
						</div>
						
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">TSN</div>
								<div class="font-size-9 line-height-18">TSN</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" value=" " id="tsn"  name="tsn" maxlength="50" class="form-control " />
							</div>
						</div>
						<div class="clearfix"></div>
						
						
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">TSO</div>
								<div class="font-size-9 line-height-18">TSO</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" value=" " id="tso"  name="tso"  maxlength="50" class="form-control "  />
							</div>
						</div>
						 
						 <div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4  text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">航材来源</div>
								<div class="font-size-9 line-height-18">Source</div>
							</label>
							<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								 <select id="hcly" class="form-control"  >
							     </select>
							</div>
						</div> 
					    <div class=" col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							    <div class="font-size-12 line-height-18">序列号</div>
								<div class="font-size-9 line-height-18">S/N</div>
								</label>
							   <div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
									<input type="text"  class="form-control" id="sn" name="sn" value=" "  />
							 </div>
					    </div>
					    <div class=" col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 ">
								<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
							    <div class="font-size-12 line-height-18">批次号 </div>
								<div class="font-size-9 line-height-18">B/N</div>
								</label>
							   <div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
									<input type="text"  class="form-control" id="pch" name="pch" value=" "  />
							 </div>
					    </div>
					    <div class="clearfix"></div>
					    <div class="col-xs-12 col-sm-6 col-lg-3  padding-left-0 padding-right-0 margin-bottom-0 ">
							<label class="pull-left col-xs-4 col-sm-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">库存成本</div>
								<div class="font-size-9">Cost(RMB:YUAN)</div>
							</label>
							  <div class="col-lg-8 col-sm-8 col-xs-8  padding-left-8 padding-right-0 form-group">
									<!-- <input type="text" id="kccb" class="form-control"  value=" "  /> -->
								<div class='input-group'>
									 <input type='text' class='form-control' id='kccb'  name='kccb' onkeyup='onkeyup4Num(this);'/>
									 <span class='input-group-btn' >
										 <button class='btn btn-primary'  onclick="showContractCost();" >
										     <i class='icon-list' ></i>
										 </button>
									 </span>
								 </div>
							 </div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">GRN</div>
								<div class="font-size-9 line-height-18">GRN</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" value=" " id="grn"  name="grn"  maxlength="50" class="form-control "  />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">CSN</div>
								<div class="font-size-9 line-height-18">CSN</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" value=" " id="csn"  name="csn"  maxlength="50" class="form-control "  />
							</div>
						</div>
						<div class="col-lg-3 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">CSO</div>
								<div class="font-size-9 line-height-18">CSO</div>
							</label>
							<div class=" col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
								<input type="text" value=" " id="cso"  name="cso"  maxlength="50" class="form-control "  />
							</div>
						</div>
					    <div class="clearfix"></div>
					    
						 <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
							<label class="col-lg-1 col-sm-2 col-xs-4 text-right padding-left-0 padding-right-0">
								<div class="font-size-12 line-height-18">存放要求</div>
								<div class="font-size-9 line-height-18">Storage Must</div>
							</label>
							<div class="col-lg-11 col-sm-10 col-xs-8  padding-left-8 padding-right-0 form-group">
								<textarea class="form-control" id="cfyq" name="cfyq" maxlength="150" placeholder="最大长度不超过150"> </textarea>
							</div>
						</div>
				
						<div class="clearfix"></div>
						
						</form>
						
						<div class="text-right" style="margin-top: 0px;margin-bottom: 10px;">
		                    	<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1"  onclick="stockEditComp.save()">
		                    	<div class="font-size-12">修改</div>
								<div class="font-size-9">Edit</div>
								</button>
								
								<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1"  data-dismiss="modal">
		                    	<div class="font-size-12">关闭</div>
								<div class="font-size-9">Close</div>
								</button>
		                 </div>
						 <div class="clearfix"></div>
					    
					 	  
					<!-- </div> -->
					</div>
					<div class="clearfix"></div>
					 
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/open_win/contract_cost.jsp"%>
