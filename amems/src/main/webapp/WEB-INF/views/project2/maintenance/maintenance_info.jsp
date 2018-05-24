<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java" %>
 <!-- 基本信息(版本) -->
     <div class="panel panel-primary left_second_content">
		    <!-- panel-heading -->
			<div class="panel-heading">
				<div class="font-size-12 line-height-12">基本信息<span name="info_rev_bracket"></span></div>
				<div class="font-size-9 line-height-9">Basic Info(Rev.)</div>
			</div>
			<div class="panel-body padding-left-0 padding-right-0 left_second_body padding-top-0"  >
			    <table class='table' style='border-bottom:1px solid #d5d5d5;margin-top:0px !important'>
			    <tbody>
			    <tr class="hidden viewType">
				    <th width='80px' style="font-weight: normal;padding-left:8px;">
				    <div class="font-size-12 line-height-12">机型</div>
					<div class="font-size-9 line-height-12">A/C Type</div>
				    </th>
				    <td style="vertical-align:middle;" id="info_jx">
				    </td>
			    </tr>
			    <tr class="hidden viewType">
				    <th style="font-weight: normal;padding-left:8px;">
				    <div class="font-size-12 line-height-12">版本</div>
					<div class="font-size-9 line-height-12">Version</div>
				    </th>
				    <td style="vertical-align:middle;" name="info_rev">
				    </td>
			    </tr>
			    <tr>
				    <th width='80px' style="font-weight: normal;padding-left:8px;">
				    <div class="font-size-12 line-height-12">编号</div>
					<div class="font-size-9 line-height-12">No.</div>
				    </th>
				    <td style="vertical-align:middle;" name="info_wxfabh">
				    </td>
			    </tr>
			     <tr>
				    <th style="font-weight: normal;padding-left:8px;">
				    <div class="font-size-12 line-height-12">描述</div>
					<div class="font-size-9 line-height-12">Description</div>
				    </th>
				    <td style="vertical-align:middle;" id="info_zwms">
				    </td>
			    </tr>
			     <tr>
				    <th style="font-weight: normal;padding-left:8px;">
				    <div class="font-size-12 line-height-12">状态</div>
					<div class="font-size-9 line-height-12">Status</div>
				    </th>
				    <td style="vertical-align:middle;" id="info_zt">
				    </td>
			    </tr>
			    <tr>
				    <th style="font-weight: normal;padding-left:8px;">
				    <div>
				    <div class="font-size-12 line-height-12">改版依据</div>
					<div class="font-size-9 line-height-12">Based On</div>
					</div>
				    </th>  
				    <td style="vertical-align:middle;word-wrap:break-word;word-break:break-all;" id="info_gbyj">
				    </td>
			    </tr>
			    <tr>
				    <th style="font-weight: normal;padding-left:8px;">
				    <div class="font-size-12 line-height-12">技术评估单</div>
					<div class="font-size-9 line-height-12">Assessment</div>
				    </th>
				    <td style="vertical-align:middle;" id="info_pgd">
				    </td>
			    </tr>
			     <tr>
				    <th style="font-weight: normal;padding-left:8px;">
				    <div class="font-size-12 line-height-12">计划生效</div>
					<div class="font-size-9 line-height-12">Date</div>
				    </th>
				    <td style="vertical-align:middle;" id="info_jhSxrq">
				    </td>
			    </tr>
			     <tr>
				    <th style="font-weight: normal;padding-left:8px;">
				    <div class="font-size-12 line-height-12">实际生效</div>
					<div class="font-size-9 line-height-12">Date</div>
				    </th>
				    <td style="vertical-align:middle;" id="info_sjSxrq">
				    </td>
			    </tr>
			     <tr>
				    <th style="font-weight: normal;padding-left:8px;">
				    <div class="font-size-12 line-height-12">编制人</div>
					<div class="font-size-9 line-height-12">Creator</div>
				    </th>
				    <td style="vertical-align:middle;" id="info_zdr">
				    </td>
			    </tr>
			     <tr>
				    <th style="font-weight: normal;padding-left:8px;">
				    <div class="font-size-12 line-height-12">编制时间</div>
					<div class="font-size-9 line-height-12">Time</div>
				    </th>
				    <td style="vertical-align:middle;" id="info_zdsj">
				    </td>
			    </tr>
			     <tr>
				    <th style="font-weight: normal;padding-left:8px;">
				    <div class="font-size-12 line-height-12">审核人</div>
					<div class="font-size-9 line-height-12">Reviewer</div>
				    </th>
				    <td style="vertical-align:middle;" id="info_shr">
				    </td>
			    </tr>
			     <tr>
				    <th style="font-weight: normal;padding-left:8px;">
				    <div class="font-size-12 line-height-12">审核时间</div>
					<div class="font-size-9 line-height-12">Time</div>
				    </th>
				    <td style="vertical-align:middle;" id="info_shrq">
				    </td>
			    </tr>
			     <tr>
				    <th style="font-weight: normal;padding-left:8px;">
				    <div class="font-size-12 line-height-12">审核意见</div>
					<div class="font-size-9 line-height-12">Opinion</div>
				    </th>
				    <td style="vertical-align:middle;" id="info_shyj">
				    </td>
			    </tr>
			    <tr>
				    <th style="font-weight: normal;padding-left:8px;">
				    <div class="font-size-12 line-height-12">批准人</div>
					<div class="font-size-9 line-height-12">Approver</div>
				    </th>
				    <td style="vertical-align:middle;" id="info_pzr">
				    </td>
			    </tr>
			     <tr>
				    <th style="font-weight: normal;padding-left:8px;">
				    <div class="font-size-12 line-height-12">批准时间</div>
					<div class="font-size-9 line-height-12">Time</div>
				    </th>
				    <td style="vertical-align:middle;" id="info_pzrq">
				    </td>
			    </tr>
			    <tr>
				    <th style="font-weight: normal;padding-left:8px;">
				    <div class="font-size-12 line-height-12">批准意见</div>
					<div class="font-size-9 line-height-12">Opinion</div>
				    </th>
				    <td style="vertical-align:middle;" id="info_pzyj">
				    </td>
			    </tr>
			    <tr>
				    <th style="font-weight: normal;padding-left:8px;">
				    <div class="font-size-12 line-height-12">确认人</div>
					<div class="font-size-9 line-height-12">Confirmer</div>
				    </th>
				    <td style="vertical-align:middle;" id="info_sxr">
				    </td>
			    </tr>
			    <tr>
				    <th style="font-weight: normal;padding-left:8px;">
				    <div class="font-size-12 line-height-12">确认时间</div>
					<div class="font-size-9 line-height-12">Time</div>
				    </th>
				    <td style="vertical-align:middle;" id="info_sxrq">
				    </td>
			    </tr>
			    <tr>
				    <th style="font-weight: normal;padding-left:8px;">
				    <div class="font-size-12 line-height-12">备注</div>
					<div class="font-size-9 line-height-12">Note</div>
				    </th>
				    <td style="vertical-align:middle;" id="info_bz">
				    </td>
			    </tr>
			    </tbody>
			    </table>
			</div>
    </div>
    <script src="${ctx}/static/js/thjw/project2/maintenance/maintenance_info.js"></script>