<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>

	<div id="basic_div">
	  <form id="basic_form">
	  	<div class="margin-top-10 padding-left-0 padding-right-0">
	  		<div class="col-lg-2 col-sm-2 col-xs-12">
				<div id="zpButt" class="avatar-view" title="更换照片" onclick="showChoosePhoto()">
			      <img src="${ctx}/static/images/maintenanceTest.png" alt="照片">
			    </div>
			    <input class="form-control" id="zpdzD" type="hidden"/>
				<input class="form-control" id="zpdzX" type="hidden"/>
	  		</div>
	  		<div class="col-lg-10 col-sm-10 col-xs-12 padding-left-0 padding-right-0">
      		    <!--归属  -->
      		    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">归属</div>
						<div class="font-size-9 ">Ascraption</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" style='height:34px;'>
							<label class="margin-right-5 label-input" style='font-weight:normal;'>
							<input type="radio" name="wbbs" value="1" checked="checked" onchange="onchlickradio()">&nbsp;内
						</label>
						<label class="margin-right-5 label-input" style='font-weight:normal;'>
							<input type="radio" name="wbbs" value="2" onchange="onchlickradio()">&nbsp;外
						</label>
					</div>
				</div>
	  		    <!-- 编号  -->
	  		    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2"><span style="color: red" id="rybhmark">*</span>编号</div>
						<div class="font-size-9">Staff No.</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input type="text" class="form-control" id="rybh" name="rybh" maxlength="15" />
					</div>
				</div>
	  		    
	  			<!-- <div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
					<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18"><span style="color: red" id="rybhmark">*</span>编号</div>
						<div class="font-size-9 line-height-18">Staff No.</div>
					</label>
					<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
						<input type="text" class="form-control" id="rybh" name="rybh" maxlength="15"></input>
					</div>
				</div> -->
				
				 <!-- 姓名  -->
	  		    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2"><span style="color: red" id="xmmark">*</span>姓名</div>
						<div class="font-size-9 ">Name</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" id="xm_div">
						<div class="input-group">
							<input type="text" class="form-control readonly-style colse-div noteditable" ondblclick='chooseWxry();' id="xm" name="xm" maxlength="100" readonly="readonly" ></input>
							<input class="form-control" id="wxryid" type="hidden" />
							<span class="required input-group-btn">
								<button class="btn btn-default form-control" type="button" onclick="chooseWxry()" id="xmButt">
									<i class="icon-search"></i>
								</button>
							</span>
						</div>
					</div>
				</div>
				<!-- <div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
					<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18"><span style="color: red" id="xmmark">*</span>姓名</div>
						<div class="font-size-9 line-height-18">Name</div>
					</label>
					<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" id="xm_div">
						<div class="input-group">
							<input type="text" class="form-control" id="xm" name="xm" maxlength="100"></input>
							<input class="form-control" id="wxryid" type="hidden" />
							<span class="input-group-btn">
								<button class="btn btn-primary form-control" type="button" onclick="chooseWxry()">
									<i class="icon-search"></i>
								</button>
							</span>
						</div>
					</div>
				</div> -->
				  <!-- 单位/部门  -->
	  		    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">单位/部门</div>
						<div class="font-size-9">Department</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input type="text" class="form-control" id="szdw" name="szdw" maxlength="100" />
					</div>
				</div>
				 <div class="clearfix"></div>
				<!-- <div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
					<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18">单位/部门</div>
						<div class="font-size-9 line-height-18">Department</div>
					</label>
					<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
						<input type="text" class="form-control" id="szdw" name="szdw" maxlength="100"></input>
					</div>
				</div> -->
				  <!-- 性别  -->
	  		    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">性别</div>
						<div class="font-size-9 ">Gender</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8" style='height:34px;'>
							<label class="margin-right-5 label-input" style='font-weight:normal;'>
							<input type="radio" name="xb" value="1" checked="checked">&nbsp;男
						</label>
						<label class="margin-right-5 label-input" style='font-weight:normal;'>
							<input type="radio" name="xb" value="2">&nbsp;女
						</label>
					</div>
				</div>
	
				<!-- <div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
					<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18">性别</div>
						<div class="font-size-9 line-height-18">Gender</div>
					</label>
					<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
						<label class="checkbox-inline">
							<input type="radio" name="xb" value="1" checked="checked">&nbsp;男
						</label>
						<label class="checkbox-inline">
							<input type="radio" name="xb" value="2">&nbsp;女
						</label>
					</div>
				</div> -->
				 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">出生日期</div>
						<div class="font-size-9">Birthday</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input class="form-control date-picker" id="sr" name="sr" data-date-format="yyyy-mm-dd" maxlength="10" type="text" />
					</div>
				</div>
				
				
				<!-- <div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
					<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18">出生日期</div>
						<div class="font-size-9 line-height-18">Birthday</div>
					</label>
					<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
						<input class="form-control date-picker" id="sr" name="sr" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
					</div>
				</div> -->
				<!-- 籍贯 -->
				 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">国籍</div>
						<div class="font-size-9 ">Nationality</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input type="text" class="form-control" id="jg" name="jg" maxlength="100" />
					</div>
				</div>
				<!-- <div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
					<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18">籍贯</div>
						<div class="font-size-9 line-height-18">Native Place</div>
					</label>
					<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
						<input type="text" class="form-control" id="jg" name="jg" maxlength="100"></input>
					</div>
				</div> -->
				
				<!-- 政治面貌 -->
				 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">政治面貌</div>
						<div class="font-size-9 ">Political </div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<select id="zzmm" class="form-control"></select>
					</div>
				</div>
				<div class="clearfix"></div>
				<!-- <div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
					<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18">政治面貌</div>
						<div class="font-size-9 line-height-18">Political Status</div>
					</label>
					<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
						<select id="zzmm" class="form-control"></select>
					</div>
				</div> -->
				<!-- 学历 -->
				 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">学历</div>
						<div class="font-size-9 ">Education</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<select id="xl" class="form-control"></select>
					</div>
				</div>
				<!-- <div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
					<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18">学历</div>
						<div class="font-size-9 line-height-18">Education</div>
					</label>
					<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
						<select id="xl" class="form-control"></select>
					</div>
				</div> -->
					<!-- 身份证号 -->
				 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">身份证号</div>
						<div class="font-size-9 ">ID Number</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input type="text" class="form-control" id="sfz" name="sfz" maxlength="50" />
					</div>
				</div>
				<!-- <div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
					<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18">身份证号</div>
						<div class="font-size-9 line-height-18">ID Number</div>
					</label>
					<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
						<input type="text" class="form-control" id="sfz" name="sfz" maxlength="50"></input>
					</div>
				</div> -->
				
					<!-- 邮政编码 -->
				 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">邮政编码</div>
						<div class="font-size-9 ">Postalcode</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input type="text" class="form-control" id="yzbm" name="yzbm" maxlength="20" />
					</div>
				</div>
				<!-- <div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
					<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18">邮政编码</div>
						<div class="font-size-9 line-height-18">Postalcode</div>
					</label>
					<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
						<input type="text" class="form-control" id="yzbm" name="yzbm" maxlength="20"></input>
					</div>
				</div> -->
				
					<!-- 联系电话 -->
				 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">联系电话</div>
						<div class="font-size-9 ">Telephone</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input type="text" class="form-control" id="lxdh" name="lxdh" maxlength="20"/>
					</div>
				</div>
				<div class="clearfix"></div>
				<!-- <div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
					<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18">联系电话</div>
						<div class="font-size-9 line-height-18">Telephone</div>
					</label>
					<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
						<input type="text" class="form-control" id="lxdh" name="lxdh" maxlength="20"></input>
					</div>
				</div> -->
					<!-- 邮箱 -->
				 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">邮箱</div>
						<div class="font-size-9 ">Email</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<input type="text" class="form-control" id="yxdz" name="yxdz" maxlength="30" />
					</div>
				</div>
				<!-- <div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
					<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18">邮箱</div>
						<div class="font-size-9 line-height-18">Email</div>
					</label>
					<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
						<input type="text" class="form-control" id="yxdz" name="yxdz" maxlength="30"></input>
					</div>
				</div> -->
			
				<!-- 地址 -->
				 <div class="col-lg-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-2 col-xs-1 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">地址</div>
						<div class="font-size-9 ">Address</div>
					</span>
					<div class="col-lg-10  col-xs-11 padding-leftright-8">
						<input type="text" class="form-control" id="dz" name="dz" maxlength="100" />
					</div>
				</div>
				<!-- <div class="col-lg-9 col-sm-9 col-xs-9  padding-left-0 padding-right-0 margin-bottom-10 form-group">
					<label class="col-lg-1 col-sm-1 col-xs-1 text-right padding-left-0 padding-right-0" style="width: 11%">
						<div class="font-size-12 line-height-18">地址</div>
						<div class="font-size-9 line-height-18">Address</div>
					</label>
					<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0" style="width: 89%">
						<input type="text" class="form-control" id="dz" name="dz" maxlength="100"></input>
					</div>
				</div> -->
					<!-- 婚姻状况 -->
				 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">婚姻状况</div>
						<div class="font-size-9 ">Marital Status</div>
					</span>
					<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
						<label class="margin-right-5 label-input" style='font-weight:normal;'>
							<input type="radio" name="is_jh" value="1" checked="checked">&nbsp;已婚
						</label>
						<label class="margin-right-5 label-input" style='font-weight:normal;'>
							<input type="radio" name="is_jh" value="0">&nbsp;未婚
						</label>
					</div>
				</div>
				<!-- <div class=" col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
					<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18">婚姻状况</div>
						<div class="font-size-9 line-height-18">Marital Status</div>
					</label>
					<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
						<label class="checkbox-inline">
							<input type="radio" name="is_jh" value="1" checked="checked">&nbsp;已婚
						</label>
						<label class="checkbox-inline">
							<input type="radio" name="is_jh" value="0">&nbsp;未婚
						</label>
					</div>
				</div> -->
				<div class="clearfix"></div>
				
					<!-- 家庭成员 -->
				 <div class="col-lg-12 col-sm-12 col-xs-12 padding-left-0 padding-right-0 form-group">
					<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 margin-topnew-2">家庭成员</div>
						<div class="font-size-9 ">Family Member</div>
					</span>
					<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9 padding-leftright-8">
						<input type="text" class="form-control" id="jtcy" name="jtcy" maxlength="100" />
					</div>
				</div>
				<!-- <div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
					<label class="col-lg-1 col-sm-1 col-xs-1 text-right padding-left-0 padding-right-0">
						<div class="font-size-12 line-height-18">家庭成员</div>
						<div class="font-size-9 line-height-18">Family Member</div>
					</label>
					<div class="col-lg-11 col-sm-11 col-xs-11 padding-left-8 padding-right-0">
						<input type="text" class="form-control" id="jtcy" name="jtcy" maxlength="100"></input>
					</div>
				</div> -->
				<div class="clearfix"></div>
	  		</div>
	  	</div>
		
		<div class="col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0  margin-bottom-10 form-group margin-top-10">
			<span class="col-lg-1 col-md-1 col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
				<div class="font-size-12 line-height-18">备注</div>
				<div class="font-size-9 line-height-18">Remark</div>
			</span>
			<div class="col-lg-11 col-md-11 col-sm-10 col-xs-9  padding-left-8 padding-right-0">
				<textarea rows="3" name="bz" class="form-control" id="bz" maxlength="300"></textarea>
			</div>
		</div>
		
		<div class="clearfix"></div>
		<!-- 参加工作情况 -->
		<div  class="panel panel-primary">
			<div class="panel-heading bg-panel-heading" >
				<div class="font-size-12">参加工作情况</div>
				<div class="font-size-9 ">Join-in Information</div>
		    </div>
	    <div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
		
		<div class="col-lg-12 padding-left-0 padding-right-0" id="cjgzqk">
		     
	     	<!-- 参加工作日期 -->
			 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
				
					<div class="font-size-12 margin-topnew-2"><span style="color: red" id="cjgzrqmark"></span>参加工作日期</div>
					<div class="font-size-9 ">Work Date</div>
				</span>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<input class="form-control date-picker" id="cjgzrq" name="cjgzrq" data-date-format="yyyy-mm-dd" maxlength="10" type="text" />
				</div>
			</div>
		<!-- 	<div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 margin-bottom-10 form-group">
				<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 line-height-18"><span style="color: red" id="cjgzrqmark">*</span>参加工作日期</div>
					<div class="font-size-9 line-height-18">Work Date</div>
				</label>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<input class="form-control date-picker" id="cjgzrq" name="cjgzrq" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
				</div>
			</div> -->
			<!-- 入职日期 -->
			 <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
				
					<div class="font-size-12 margin-topnew-2"><span style="color: red" id="rzrqmark"></span>入职日期</div>
					<div class="font-size-9 ">Employment Date</div>
				</span>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<input class="form-control date-picker" id="rzrq" name="rzrq" data-date-format="yyyy-mm-dd" maxlength="10" type="text" />
				</div>
			</div>
			<!-- <div class="col-lg-3 col-sm-3 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<label class="col-lg-4 col-sm-4 col-xs-4 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 line-height-18"><span style="color: red" id="rzrqmark">*</span>入职日期</div>
					<div class="font-size-9 line-height-18">Employment Date</div>
				</label>
				<div class="col-lg-8 col-sm-8 col-xs-8 padding-left-8 padding-right-0">
					<input class="form-control date-picker" id="rzrq" name="rzrq" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
				</div>
			</div> -->
			
			
			<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<span class="col-lg-4 col-md-1 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">入职前信息</div>
					<div class="font-size-9">Pre Entry Info</div>
				</span>
				<div class="col-lg-8 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0">
					<input class="form-control" id="rzqxx" name="rzqxx" type="text" maxlength="100">
				</div>
			</div>
		</div>
		
		<div class="col-lg-12 padding-left-0 padding-right-0">
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group">
				<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3  text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">在职状态</div>
					<div class="font-size-9 margin-topnew-2">Status</div>
				</span>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
					<label id="radioChange1" class="margin-right-5 label-input" style='font-weight:normal;' onclick="toggleLzrq('1')">
						<input type="radio" name="zzzt" value="1" checked="checked">&nbsp;在职
					</label>
					<label id="radioChange2" class="margin-right-5 label-input" style='font-weight:normal;' onclick="toggleLzrq('0')">
						<input type="radio" name="zzzt" value="0">&nbsp;离职
					</label>
				</div>
			</div>
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12  padding-left-0 padding-right-0 margin-bottom-10 form-group" style="display: none;" id="lzrqDiv">
				<span class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2"><span style="color: red" id="lzrqmark">*</span>离职日期</div>
					<div class="font-size-9 ">Leave Date</div>
				</span>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-left-8 padding-right-0">
					<input class="form-control date-picker" id="lzrq" name="lzrq" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
				</div>
			</div>
		</div>
		</div>
		</div>
	  </form>
		<div class="clearfix"></div>
		
		
		<!-- 教育经历 -->
		<div  class="panel panel-primary">
			<div class="panel-heading bg-panel-heading" >
				<div class="font-size-12">教育经历</div>
				<div class="font-size-9 ">Education Experience</div>
		    </div>
	    <div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
		<!-- <div class="panel-heading margin-left-16 padding-top-1 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
			<div class="col-lg-4  padding-left-0 padding-right-0 ">
				<div class="font-size-16 line-height-18">教育经历</div>
				<div class="font-size-9 ">Education Experience</div>
			</div>
			<div class="clearfix"></div>
	    </div> -->
	    
	    <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x:auto ;">
			<table id="basic_education_table" class="table table-thin table-bordered table-striped table-hover table-set">
				<thead>
					<tr>
						<th class="colwidth-7 editTable" style="vertical-align: middle;">
							<button class="line6" onclick="addBasicEducation()" style="padding:0px 6px;">
						    	<i class="icon-plus cursor-pointer color-blue cursor-pointer'"></i>
					        </button>
						</th>
						<th class="colwidth-13">
							<div class="font-size-12 line-height-18">日期</div>
							<div class="font-size-9 line-height-18">Date</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">毕业院校</div>
							<div class="font-size-9 line-height-18">Graduation Colleges</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">专业</div>
							<div class="font-size-9 line-height-18">Profession</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">学制</div>
							<div class="font-size-9 line-height-18">Length Of Schooling</div>
						</th>
						<th class="colwidth-15 downward cursor-pointer" onclick="vieworhideFj('jyjl')" name="th_jyjl">
							<div class="font-size-12 line-height-18">附件</div>
							<div class="font-size-9 line-height-18">Attachment</div>
						</th>
					</tr>
				</thead>
				<tbody id="basic_education_list">
					<tr class="non-choice"><td class="text-center" colspan="5">暂无数据 No data.</td></tr>
				</tbody>
			</table>
		</div>
		</div>
		</div>
		
		<div class="clearfix"></div>
		<!-- 外语水平 -->
		<div  class="panel panel-primary">
			<div class="panel-heading bg-panel-heading" >
				<div class="font-size-12">外语水平</div>
				<div class="font-size-9 ">Foreign Language</div>
		    </div>
	    <div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
		<!-- <div class="panel-heading margin-left-16 padding-top-1 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
			<div class="col-lg-4  padding-left-0 padding-right-0 padding-top-5">
				<div class="font-size-16 line-height-18">外语水平</div>
				<div class="font-size-9 ">Foreign Language</div>
			</div>
			<div class="clearfix"></div>
	    </div> -->
	    
	    <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x:auto ;">
			<table id="basic_foreign_language_table" class="table table-thin table-bordered table-striped table-hover table-set">
				<thead>
					<tr>
						<th class="colwidth-7 editTable" style="vertical-align: middle;">
							<button class="line6" onclick="addBasicLanguage()" style="padding:0px 6px;">
						    	<i class="icon-plus cursor-pointer color-blue cursor-pointer'"></i>
					        </button>
						</th>
						<th class="colwidth-30">
							<div class="font-size-12 line-height-18">语种</div>
							<div class="font-size-9 line-height-18">Languages</div>
						</th>
						<th class="colwidth-30">
							<div class="font-size-12 line-height-18">水平</div>
							<div class="font-size-9 line-height-18">Level</div>
						</th>
						<th class="colwidth-30 downward cursor-pointer" onclick="vieworhideFj('wysp')" name="th_wysp">
							<div class="font-size-12 line-height-18">附件</div>
							<div class="font-size-9 line-height-18">Attachment</div>
						</th>
					</tr>
				</thead>
				<tbody id="basic_foreign_language_list">
					<tr class="non-choice"><td class="text-center" colspan="3">暂无数据 No data.</td></tr>
				</tbody>
			</table>
		</div>
		</div>
		</div>
		<div class="clearfix"></div>
		<!-- 聘任职称 -->
	    <div  class="panel panel-primary">
			<div class="panel-heading bg-panel-heading" >
				<div class="font-size-12">聘任职称</div>
				<div class="font-size-9 ">Tech. Title Record</div>
		    </div>
	    <div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
		<!-- <div class="panel-heading margin-left-16 padding-top-1 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
			<div class="col-lg-4  padding-left-0 padding-right-0 padding-top-5">
				<div class="font-size-16 line-height-18">聘任职称</div>
				<div class="font-size-9 ">Tech. Title Record</div>
			</div>
			<div class="clearfix"></div>
	    </div> -->
	    
	    <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x:auto ;">
			<table id="basic_title_table" class="table table-thin table-bordered table-striped table-hover table-set">
				<thead>
					<tr>
						<th class="colwidth-7 editTable" style="vertical-align: middle;">
							<button class="line6" onclick="addBasicTitle()" style="padding:0px 6px;">
						    	<i class="icon-plus cursor-pointer color-blue cursor-pointer'"></i>
					        </button>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">职称</div>
							<div class="font-size-9 line-height-18">Title</div>
						</th>
						<th class="colwidth-30">
							<div class="font-size-12 line-height-18">授权单位</div>
							<div class="font-size-9 line-height-18">Authorized Organization</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">聘任日期</div>
							<div class="font-size-9 line-height-18">Appointment Date</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">聘任期限</div>
							<div class="font-size-9 line-height-18">Appointment Period</div>
						</th>
						<th class="colwidth-20">
							<div class="font-size-12 line-height-18">备注</div>
							<div class="font-size-9 line-height-18">Remark</div>
						</th>
						<th class="colwidth-15 downward cursor-pointer" onclick="vieworhideFj('przc')" name="th_przc">
							<div class="font-size-12 line-height-18">附件</div>
							<div class="font-size-9 line-height-18">Attachment</div>
						</th>
					</tr>
				</thead>
				<tbody id="basic_title_list">
					<tr class="non-choice"><td class="text-center" colspan="6">暂无数据 No data.</td></tr>
				</tbody>
			</table>
		</div>
		</div>
		</div>
		
		<div class="clearfix"></div>
		
		<!-- 工作履历 -->
		<div  class="panel panel-primary">
			<div class="panel-heading bg-panel-heading" >
				<div class="font-size-12">工作履历</div>
				<div class="font-size-9 ">Duty & Responsibility</div>
		    </div>
	    <div class="panel-body padding-leftright-8" style='padding-bottom:0px;'>
		<!-- <div class="panel-heading margin-left-16 padding-top-1 margin-bottom-10 " style="border-bottom: 1px solid #ccc;">
			<div class="col-lg-4  padding-left-0 padding-right-0 padding-top-10">
				<div class="font-size-16 line-height-18">工作履历</div>
				<div class="font-size-9 ">Duty & Responsibility</div>
			</div>
			<div class="clearfix"></div>
	    </div> -->
	    
	    <div class="col-lg-12 col-md-12 padding-left-0 padding-right-0" style="overflow-x:auto ;">
			<table id="basic_work_experience_table" class="table table-thin table-bordered table-striped table-hover table-set">
				<thead>
					<tr>
						<th class="colwidth-7 editTable" style="vertical-align: middle;">
							<button class="line6" onclick="addBasicWorkExperience()" style="padding:0px 6px;">
						    	<i class="icon-plus cursor-pointer color-blue cursor-pointer'"></i>
					        </button>
						</th>
						<th class="colwidth-20">
							<div class="font-size-12 line-height-18">日期</div>
							<div class="font-size-9 line-height-18">Date</div>
						</th>
						<th class="colwidth-30">
							<div class="font-size-12 line-height-18">工作单位/部门</div>
							<div class="font-size-9 line-height-18">Company</div>
						</th>
						<th class="colwidth-15">
							<div class="font-size-12 line-height-18">职务</div>
							<div class="font-size-9 line-height-18">Job</div>
						</th>
						<th class="colwidth-30">
							<div class="font-size-12 line-height-18">工作内容</div>
							<div class="font-size-9 line-height-18">Work Content</div>
						</th>
						<th class="colwidth-15 downward cursor-pointer" onclick="vieworhideFj('gzll')" name="th_gzll">
							<div class="font-size-12 line-height-18">附件</div>
							<div class="font-size-9 line-height-18">Attachment</div>
						</th>
					</tr>
				</thead>
				<tbody id="basic_work_experience_list">
					<tr class="non-choice"><td class="text-center" colspan="5">暂无数据 No data.</td></tr>
				</tbody>
			</table>
		</div>
		</div>
		</div>
		
		
		<!-- 教育经历模态框Start -->
		<div class="modal modal-new" id="basic_education_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false>
			<div class="modal-dialog modal-lg" >
				<div class="modal-content">
					<div class="modal-header modal-header-new">
						<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-12" >教育经历</div>
							<div class="font-size-9" >Education</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
					</div>
					<div class="modal-body">
					   	<div class="col-xs-12 margin-top-8 ">
              		     <div class="input-group-border">
						
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color: red">*</span>开始日期</div>
									<div class="font-size-9 line-height-18">Start Date</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control date-picker" id="basic_education_modal_ksrq" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group" id="rwdh_div">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">结束日期</div>
									<div class="font-size-9 line-height-18">End Date</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control date-picker" id="basic_education_modal_jsrq" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
								</div>
							</div>
						
						
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color: red">*</span>毕业院校</div>
									<div class="font-size-9 line-height-18">Graduation Colleges</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="basic_education_modal_byxx" type="text" maxlength="100">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color: red">*</span>专业</div>
									<div class="font-size-9 line-height-18">Profession</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="basic_education_modal_jyzy" type="text" maxlength="100">
								</div>
							</div>
					
						
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">学制</div>
									<div class="font-size-9 line-height-18">Length Of Schooling</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="basic_education_modal_xz" type="text" maxlength="15">
								</div>
							</div>
						
						
						<!----------------------------------- 附件begin ---------------------------------->
						<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_attachment_common.jsp"%>
						<!----------------------------------- 附件begin ---------------------------------->
						</div>
						</div>
					</div>
						<div class="modal-footer">
			           	<div class="col-xs-12 padding-leftright-8" >
							<div class="input-group">
								<span class="input-group-addon modalfootertip" >
									<!-- <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p> -->
								</span>
			                    <span class="input-group-addon modalfooterbtn">
				                   <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmBasicEducation()">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
			                    </span>
			               	</div>
						</div>
					</div>
					<!-- <div class="modal-footer" style="margin-right:20px">
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmBasicEducation()">
							<div class="font-size-12">确定</div>
							<div class="font-size-9">Confirm</div>
						</button>
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
						</button>
					</div> -->
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>
		<!-- 教育经历模态框End -->
		
		
		<!-- 外语水平模态框Start -->
		<div class="modal fade modal-new" id="basic_language_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false>
			<div class="modal-dialog modal-lg" >
				<div class="modal-content">
				    <div class="modal-header modal-header-new">
						<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-12" >外语水平</div>
							<div class="font-size-9" >Foreign Language Level</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
					</div>
					<div class="modal-body">
					    <div class="col-xs-12 margin-top-8 ">
              		    <div class="input-group-border">
						
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color: red">*</span>语种</div>
									<div class="font-size-9 line-height-18">Languages</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="basic_language_modal_yz" type="text" maxlength="15">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color: red">*</span>水平</div>
									<div class="font-size-9 line-height-18">Level</div>
								</span>
								<div class="col-sm-8 col-xs-9  padding-leftright-8">
									<select id="basic_language_modal_wysp" class="form-control"></select>
								</div>
							</div>
						
						<!----------------------------------- 附件begin ---------------------------------->
						<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_attachment_common.jsp"%>
						<!----------------------------------- 附件begin ---------------------------------->
					</div>
					</div>
					</div>
						<div class="modal-footer">
			           	<div class="col-xs-12 padding-leftright-8" >
							<div class="input-group">
								<span class="input-group-addon modalfootertip" >
									<!-- <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p> -->
								</span>
			                    <span class="input-group-addon modalfooterbtn">
				                   <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmBasicLanguage()">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
			                    </span>
			               	</div>
						</div>
					</div>
					<!-- <div class="modal-footer" style="margin-right:20px">
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmBasicLanguage()">
							<div class="font-size-12">确定</div>
							<div class="font-size-9">Confirm</div>
						</button>
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
						</button>
					</div> -->
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>
		<!-- 外语水平模态框End -->
		
		<!-- 聘任职称模态框Start -->
		<div class="modal modal-new" id="basic_title_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false>
			<div class="modal-dialog modal-lg" >
				<div class="modal-content">
					<div class="modal-header modal-header-new">
						<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-12" >聘任职称</div>
							<div class="font-size-9" >Appointment Of Professional Titles</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
					</div>
					<!-- <div class="modal-header padding-bottom-5">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<div class="font-size-16 line-height-18">聘任职称</div>
						<div class="font-size-9 ">Appointment Of Professional Titles</div>
					</div> -->
					<div class="modal-body">
						<div class="col-xs-12 margin-top-8 ">
              		    <div class="input-group-border">
						
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color: red">*</span>职称</div>
									<div class="font-size-9 line-height-18">Title</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="basic_title_modal_zc" type="text" maxlength="100">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color: red">*</span>授权单位</div>
									<div class="font-size-9 line-height-18">Authorized Organization</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="basic_title_modal_sqdw" type="text" maxlength="100">
								</div>
							</div>
						
						
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color: red">*</span>聘任日期</div>
									<div class="font-size-9 line-height-18">Appointment Date</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control date-picker" id="basic_title_modal_prrq" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">聘任期限</div>
									<div class="font-size-9 line-height-18">Appointment Period</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control date-picker" id="basic_title_modal_prqx" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
								</div>
							</div>
				
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">备注</div>
								<div class="font-size-9 line-height-18">Remark</div>
							</span>
							<div class="col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class="form-control" id="basic_title_modal_bz"  style='height:55px;' maxlength="300"></textarea>
							</div>
						</div>
						<!----------------------------------- 附件begin ---------------------------------->
						<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_attachment_common.jsp"%>
						<!----------------------------------- 附件begin ---------------------------------->
					</div>
					</div>
					<div class='clearfix'></div>
					</div>
						<div class="modal-footer">
			           	<div class="col-xs-12 padding-leftright-8" >
							<div class="input-group">
								<span class="input-group-addon modalfootertip" >
									<i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p>
								</span>
			                    <span class="input-group-addon modalfooterbtn">
				                    <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmBasicTitle()">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
			                    </span>
			               	</div>
						</div>
					</div>
					<!-- <div class="modal-footer" >
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmBasicTitle()">
							<div class="font-size-12">确定</div>
							<div class="font-size-9">Confirm</div>
						</button>
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
						</button>
					</div> -->
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>
		<!-- 聘任职称模态框End -->
		
		
		<!-- 工作履历模态框Start -->
		<div class="modal modal-new" id="basic_work_experience_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop='static' data-keyboard= false>
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
				<div class="modal-header modal-header-new">
						<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-12" >工作履历</div>
							<div class="font-size-9" >Work Experience</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
					</div>
					<div class="modal-body">
					   <div class="col-xs-12 margin-top-8 ">
              		     <div class="input-group-border">
						
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color: red">*</span>开始日期</div>
									<div class="font-size-9 line-height-18">Begin Date</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control date-picker" id="basic_work_experience_modal_ksrq" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">结束日期</div>
									<div class="font-size-9 line-height-18">End Date</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control date-picker" id="basic_work_experience_modal_jsrq" data-date-format="yyyy-mm-dd" maxlength="10" type="text">
								</div>
							</div>
						
						
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 "><span style="color: red">*</span>工作单位/部门</div>
									<div class="font-size-9 line-height-18">Company</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="basic_work_experience_modal_szdw" type="text" maxlength="100">
								</div>
							</div>
							<div class="col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
								<span class="col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
									<div class="font-size-12 ">职务</div>
									<div class="font-size-9 line-height-18">Job</div>
								</span>
								<div class="col-sm-8 col-xs-9 padding-leftright-8">
									<input class="form-control" id="basic_work_experience_modal_zw" type="text" maxlength="100">
								</div>
							</div>
						<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
							<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0">
								<div class="font-size-12">工作内容</div>
								<div class="font-size-9 line-height-18">Work Content</div>
							</span>
							<div class="col-sm-10 col-xs-9 padding-leftright-8">
								<textarea class="form-control" id="basic_work_experience_modal_gznr" rows="5" style='height:55px;' maxlength="1000"></textarea>
							</div>
						</div>
						<!----------------------------------- 附件begin ---------------------------------->
						<%@ include file="/WEB-INF/views/quality/maintenancepersonnel/maintenancepersonnel_attachment_common.jsp"%>
						<!----------------------------------- 附件begin ---------------------------------->
					</div>
					</div>
					<div class='clearfix'></div>
					</div>
					<div class="modal-footer">
			           	<div class="col-xs-12 padding-leftright-8" >
							<div class="input-group">
								<span class="input-group-addon modalfootertip" >
									<!-- <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p> -->
								</span>
			                    <span class="input-group-addon modalfooterbtn">
				                   <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmBasicWorkExperience()">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
									<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
										<div class="font-size-12">关闭</div>
										<div class="font-size-9">Close</div>
									</button>
			                    </span>
			               	</div>
						</div>
					</div>
					<!-- <div class="modal-footer" style="margin-right:20px">
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" onclick="confirmBasicWorkExperience()">
							<div class="font-size-12">确定</div>
							<div class="font-size-9">Confirm</div>
						</button>
						<button type="button" class="btn btn-primary padding-top-1 padding-bottom-1" data-dismiss="modal" >
							<div class="font-size-12">关闭</div>
							<div class="font-size-9">Close</div>
						</button>
					</div> -->
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>
		<!-- 工作履历模态框End -->
		
		<!-- 选择头像模态框Start -->
		<div class="modal fade modal-new" id="avatar-modal" tabindex="-1" role="dialog" aria-labelledby="avatar-modal-label" aria-hidden="true" data-backdrop='static' data-keyboard= false>
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
				  <form class="avatar-form" action="crop.php" enctype="multipart/form-data" method="post">
				  <div class="modal-header modal-header-new">
						<h4 class="modal-title" >
                       	<div class='pull-left'>
                      		<div class="font-size-12" >选择头像</div>
							<div class="font-size-9" >Choose Photo</div>
				  		</div>
				  		<div class='pull-right' >
						  	<button type="button" class="icon-remove modal-close" data-dismiss="modal" aria-label="Close"></button>
						</div>
				  		<div class='clearfix'></div>
                	</h4>
					</div>
					<!-- <div class="modal-header padding-bottom-5">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<div class="font-size-16 line-height-18">选择头像</div>
						<div class="font-size-9 ">Choose Photo</div>
					</div> -->
					<div class="modal-body">
		              <div class="avatar-body">
		
		                <!-- Upload image and data -->
		                <div class="avatar-upload">
		                  <input type="hidden" class="avatar-src" name="avatar_src">
		                  <input type="hidden" class="avatar-data" name="avatar_data">
			              <input type="file" class="sr-only avatar-input" id="avatarInput" name="avatar_file" accept="image/jpeg,image/jpg,image/png">
			              <button type="button" class="btn btn-primary padding-top-1 padding-bottom-1 margin-top-8" onclick="$('#avatarInput').click();">
							<div class="font-size-12">&nbsp;&nbsp;选择&nbsp;&nbsp;</div>
							<div class="font-size-9">Choose</div>
						  </button>
			       		 <!--  <label for="avatarInput">Local upload</label>
		                  <input type="file" class="avatar-input" id="avatarInput" name="avatar_file"> -->
		                </div>
		
		                <!-- Crop and preview -->
		                <div class="row">
		                  <div class="col-md-9">
		                    <div class="avatar-wrapper"></div>
		                  </div>
		                  <div class="col-md-3">
		                    <div class="avatar-preview preview-lg"></div>
		                    <div class="avatar-preview preview-md"></div>
		                    <div class="avatar-preview preview-sm"></div>
		                  </div>
		                </div>
		
		                <div class="row avatar-btns">
		                  <div class="col-md-9">
		                    <div class="btn-group">
		                      <button type="button" class="btn btn-primary" data-method="rotate" data-option="-90" title="Rotate -90 degrees">左旋转</button>
		                      <button type="button" class="btn btn-primary" data-method="rotate" data-option="-15">向左15度</button>
		                      <button type="button" class="btn btn-primary" data-method="rotate" data-option="-30">向左30度</button>
		                      <button type="button" class="btn btn-primary" data-method="rotate" data-option="-45">向左45度</button>
		                    </div>
		                    <div class="btn-group">
		                      <button type="button" class="btn btn-primary" data-method="rotate" data-option="90" title="Rotate 90 degrees">右旋转</button>
		                      <button type="button" class="btn btn-primary" data-method="rotate" data-option="15">向右15度</button>
		                      <button type="button" class="btn btn-primary" data-method="rotate" data-option="30">向右30度</button>
		                      <button type="button" class="btn btn-primary" data-method="rotate" data-option="45">向右45度</button>
		                    </div>
		                  </div>
		                  <!-- <div class="col-md-3">
		                    <button type="submit" class="btn btn-primary btn-block avatar-save">
								确定
							</button>
		                  </div> -->
		                </div>
		               
		              </div>
		              <div class='clearfix'></div>
		            </div>
		            <div class="modal-footer">
			           	<div class="col-xs-12 padding-leftright-8" >
							<div class="input-group">
								<span class="input-group-addon modalfootertip" >
									<!-- <i class='glyphicon glyphicon-info-sign alert-info'></i><p class="alert-info-message"></p> -->
								</span>
			                    <span class="input-group-addon modalfooterbtn">
				                    <button type="submit" class="btn btn-primary padding-top-1 padding-bottom-1">
										<div class="font-size-12">确定</div>
										<div class="font-size-9">Confirm</div>
									</button>
			                    </span>
			               	</div>
						</div>
					</div>
		          </form>
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>
		<!-- 选择头像模态框End -->
	</div>
<script type="text/javascript" src="${ctx}/static/js/thjw/quality/maintenancepersonnel/maintenancepersonnel_basic.js"></script>