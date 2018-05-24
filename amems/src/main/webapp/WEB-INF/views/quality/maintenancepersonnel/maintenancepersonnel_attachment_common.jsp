<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>


	<div class="col-xs-12 padding-left-0 padding-right-0" id="fjdivmark" >
		 <!-- <div class=" col-lg-12 col-sm-12 col-xs-12  padding-left-0 padding-right-0 editTable" >
			<span class="col-xs-3 text-right padding-left-0 padding-right-0" id="wjsmmark">
				<div class="font-size-12 line-height-18">
					<span style="color: red"></span>文件说明
				</div>
				<div class="font-size-9 line-height-18">File Description</div>
			</span>
			 <div class="col-xs-6 padding-left-8 padding-right-0">
				<input type="text" name="wbwjm"  maxlength="90" class="form-control">
			</div>
		     <div class="col-xs-4  padding-left-0 padding-right-0 margin-bottom-10 form-group" >
				<div class="col-lg-2 col-sm-2 col-xs-12 fileuploader"  style="margin-left: 5px ;padding-left: 0"></div>
			</div> 
		</div>  -->
		
		<div class="col-xs-12 padding-left-0 padding-right-0 form-group">
		<span class="col-sm-2 col-xs-3 text-right padding-left-0 padding-right-0" id="fjlbmark">			
			<div class="font-size-12 line-height-18">
				附件列表</div>
			<div class="font-size-9 line-height-18">List</div>
		</span>
		<div class="col-sm-10 col-xs-9 padding-left-8 padding-right-8" id="fjlistmark">
			<table class="table-set table table-bordered table-striped table-hover text-center">
				<thead>
					<tr>
						<th class="editTable" style="width:30px;">
							<!-- <div class="font-size-12 line-height-18">操作</div>
							<div class="font-size-9 line-height-18">Operation</div> -->
							<!-- <div class="col-xs-12 fileuploader"  style="margin-left: 5px ;padding-left: 0"></div> -->
							<div class="col-xs-12 fileuploader"  style="margin-left: 5px ;padding-left: 0"></div>
						</th>
						<th style="width:130px;">
							<div class="font-size-12 line-height-18">文件说明</div>
							<div class="font-size-9 line-height-18">Description</div>
						</th>
						<th style="width:40px;">
							<div class="font-size-12 line-height-18">文件大小</div>
							<div class="font-size-9 line-height-18">File Size</div>
						</th>
						<th style="width:50px;">
							<div class="font-size-12 line-height-18">上传人</div>
							<div class="font-size-9 line-height-18">Uploader</div>
						</th>
						<th style="width:70px;">
							<div class="font-size-12 line-height-18">上传时间</div>
							<div class="font-size-9 line-height-18">Upload Time</div>
						</th>					
					</tr>
				</thead>
			    <tbody name="filelist">
					 <tr class="non-choice"><td colspan="4">暂无数据 No data.</td></tr>
				</tbody>
			</table>
			</div>
		</div>
		
	</div>
	<div class='clearfix'></div>
	
