<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
	<div class="panel panel-primary">
    	<!-- panel-heading -->
		<div class="panel-heading bg-panel-heading" >
			<div class="font-size-12 ">工作内容</div>
			<div class="font-size-9">Job Description</div>
		</div>
		<div class="panel-body padding-left-0 padding-right-0" style='padding-bottom:0px;'>
			<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 padding-left-0 padding-right-0 form-group">
				<label class="col-lg-4 col-md-3 col-sm-4 col-xs-3 text-right padding-left-0 padding-right-0">
					<div class="font-size-12 margin-topnew-2">
					<input type="radio"  checked="checked"/>
					工作内容附件</div>
					<div class="font-size-9">Attachment</div>
				</label>
				<div class="col-lg-8 col-md-9 col-sm-8 col-xs-9 padding-leftright-8">
					<div class="input-group">
					<input type="text" class="form-control">
                     <span class="input-group-addon">上传</span>
                	</div> 
				</div>
			</div>
			<div class="clearfix"></div>
			
			<div class="col-lg-12 col-md-12 padding-left-0 padding-right-0 padding-leftright-8" style="overflow-x: auto;">
					<table  class="table table-thin table-bordered table-striped table-hover table-set text-center" >
						<thead>
							<tr>
							     <th width="50">
									   <button class="line6 line6-btn" onclick="openSelectEvaluation()" type="button">
										<i class="icon-plus cursor-pointer color-blue cursor-pointer"></i>
									   </button>
								   </th>
							   <th width="50">
									  <div class="font-size-12 line-height-12">序号</div>
						           <div class="font-size-9 line-height-12">Item</div>
								   </th>
							   <th>
								   <div class="font-size-12 line-height-12">工作内容 </div>
						           <div class="font-size-9 line-height-12">Job Description</div>
							   </th>
							   <!-- class='sorting' -->
							   <th width='80'>
								   <div class="font-size-12 line-height-12">工作者</div>
						           <div class="font-size-9 line-height-12">Mech.</div>
							   </th>
							    
							   <th width='80'>
								   <div class="font-size-12 line-height-12">检查者</div>
						           <div class="font-size-9 line-height-12">Insp.</div>
							   </th>
							</tr>
						</thead>
						<tbody >
							<tr style='height:100px;'>
							 <td style="text-align:center;vertical-align:middle;">
								<button class="line6 line6-btn" type="button">
									<i class="icon-minus cursor-pointer color-blue cursor-pointer"></i>
								</button>
							</td>
							<td>1</td>					
							<td>111</td>
							<td>111</td>
							<td>111</td>
							</tr>
							<tr style='height:100px;'>
							<td style="text-align:center;vertical-align:middle;">
								<button class="line6 line6-btn" type="button">
									<i class="icon-minus cursor-pointer color-blue cursor-pointer"></i>
								</button>
							</td>
							<td>2</td>	
							<td>111</td>
							<td >111</td>
							<td>111</td>
							</tr>
						</tbody>
				</table>
			</div>
			<div class='clearfix'></div>
		</div>
	</div>
					
<!--  弹出框结束-->
<script type="text/javascript">

	$(function(){
		
	})

</script>
