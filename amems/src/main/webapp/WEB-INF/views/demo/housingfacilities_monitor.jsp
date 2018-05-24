<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>工具/设备监控设置</title>
<%@ include file="/WEB-INF/views/common_new.jsp"%>
</head>
<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->

	<!-- BEGIN CONTENT -->
	<div class="page-content">
		<div class="panel panel-primary">
			<div class="panel-heading" id="NavigationBar"></div>
			<div class="panel-body padding-bottom-0">
				
				<div class="clearfix"></div>

				<div class="col-lg-12 table-h col-md-12 padding-left-0 padding-right-0" style="margin-top: 10px;overflow-x: scroll;">
					<table id="tool_main_table" class="table table-thin table-bordered table-set" style="min-width: 2040px;">
						<thead>
							<tr>
								<th class="colwidth-3" >
									<div class="font-size-12 line-height-18">序号</div>
									<div class="font-size-9 line-height-18">No.</div>
								</th>
								<th class="colwidth-20" >
									<div class="font-size-12 line-height-18">设备名称</div>
									<div class="font-size-9 line-height-18">Name</div>
								</th>
								<th class="colwidth-20" >
									<div class="font-size-12 line-height-18">设备编号</div>
									<div class="font-size-9 line-height-18">S/N</div>
								</th>
								<th class="colwidth-20" >
									<div class="font-size-12 line-height-18">适用机型</div>
									<div class="font-size-9 line-height-18">Applicability</div>
								</th>
								<th class="colwidth-20" >
									<div class="font-size-12 line-height-18">依据文件</div>
									<div class="font-size-9 line-height-18">Document Basis</div>
								</th>
								<th class="colwidth-10" >
									<div class="font-size-12 line-height-18">目前状态</div>
									<div class="font-size-9 line-height-18">Current Atate</div>
								</th>
								<th class="colwidth-15" >
									<div class="font-size-12 line-height-18">管理部门</div>
									<div class="font-size-9 line-height-18">Department</div>
								</th>
								<th class="colwidth-13" >
									<div class="font-size-12 notwrap">维护类型</div>
									<div class="font-size-9 notwrap">Maintenance Type</div>
								</th>
								<th class="colwidth-15">
									<div class="font-size-12 notwrap">维护周期(天)</div>
									<div class="font-size-9 notwrap">Maintenance Location(Day)</div>
								</th>
								<th class="colwidth-13">
									<div class="font-size-12 notwrap">上次维护日期</div>
									<div class="font-size-9 notwrap">Last Date</div>
								</th>
								<th class="colwidth-13">
									<div class="font-size-12 notwrap">下次到期日期</div>
									<div class="font-size-9 notwrap">Next Date</div>
								</th>
								<th class="colwidth-7">
									<div class="font-size-12 notwrap">剩余天数</div>
									<div class="font-size-9 notwrap">Indate</div>
								</th>
								<th class="colwidth-20" >
									<div class="font-size-12 notwrap">维护工作单号</div>
									<div class="font-size-9 notwrap">Maintenance Work No.</div>
								</th>
							</tr>
						</thead>
						<tbody id="list"></tbody>
					</table>
				</div>
				<div class="col-xs-12 text-center page-height padding-right-0 padding-left-0" id="pagination"></div>
		</div>
	</div>
</div>

<script type="text/javascript">

	var list = [];

	$(document).ready(function(){
		Navigation(menuCode,"","");//初始化导航
		initData();
		loadContentHtml();
	});
	
	function loadContentHtml(){
		var htmlContent = '';
		$.each(list,function(index,row){
	   		if (index % 2 == 0) {
				htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" >";
	  		} else {
		   		htmlContent += "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" >";
	  		}
	   		
	  	 	htmlContent += "<td class='text-center'>" + (index+1) + "</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.sbmc)+"' class='text-left' >"+StringUtil.escapeStr(row.sbmc)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.sbbh)+"' class='text-left' >"+StringUtil.escapeStr(row.sbbh)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.syjx)+"' class='text-left' >"+StringUtil.escapeStr(row.syjx)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.yjwj)+"' class='text-left' >"+StringUtil.escapeStr(row.yjwj)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.mqzt)+"' class='text-left' >"+StringUtil.escapeStr(row.mqzt)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.glbm)+"' class='text-left' >"+StringUtil.escapeStr(row.glbm)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.whlx)+"' style='text-align:center;vertical-align:middle;' >"+StringUtil.escapeStr(row.whlx)+"</td>";
	   		htmlContent += "<td style='text-align:right;vertical-align:middle;' >"+StringUtil.escapeStr(row.whzq)+"</td>";
			htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+StringUtil.escapeStr(row.scwhrq)+"</td>";
	   		htmlContent += "<td style='text-align:center;vertical-align:middle;' >"+StringUtil.escapeStr(row.xcwhrq)+"</td>";
	   		htmlContent += "<td style='text-align:right;vertical-align:middle;' >"+StringUtil.escapeStr(row.syts)+"</td>";
	   		htmlContent += "<td title='"+StringUtil.escapeStr(row.whgzdh)+"' style='text-align:center;vertical-align:middle;' >"+StringUtil.escapeStr(row.whgzdh)+"</td>";
	   		htmlContent += "</tr>";  
	    
	   });
	   $("#list").empty();
	   if(htmlContent == ''){
		   htmlContent = "<tr><td colspan=\"13\" class='text-center'>暂无数据 No data.</td></tr>";
	   }
	   $("#list").html(htmlContent);
 	}
	
	//初始化数据
	function initData(){
		var obj1 = getData("整体梯架","ASB-001","CRJ200","厂家说明书","可用","东一飞","检查","7","2017-4-18","2017-4-25","7","A00987");
		var obj2 = getData("整体梯架","ASB-001","CRJ200","厂家说明书","可用","东一飞","润滑","30","2017-4-18","2017-5-18","30","A00988");
		list.push(obj1);
		list.push(obj2);
	}
	//生成主数据
	function getData(sbmc,sbbh,syjx,yjwj,mqzt,glbm,whlx,whzq,scwhrq,xcwhrq_,syts_,whgzdh){
		var xcwhrq = TimeUtil.dateOperator(scwhrq,whzq);
		var syts = DateDiff(xcwhrq,  getNowFormatDate());
		var obj = {};
		obj.id = getUuid();
		obj.sbmc = sbmc;
		obj.sbbh = sbbh;
		obj.syjx = syjx;
		obj.yjwj = yjwj;
		obj.mqzt = mqzt;
		obj.glbm = glbm;
		obj.whlx = whlx;
		obj.whzq = whzq;
		obj.scwhrq = scwhrq;
		obj.xcwhrq = xcwhrq;
		obj.syts = syts;
		obj.whgzdh = whgzdh;
		return obj;
	}
	
	//计算天数差的函数，通用  
	function DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式  
		var aDate,  oDate1,  oDate2,  iDays;  
       	aDate  =  sDate1.split("-");  
       	oDate1  =  new  Date(aDate[0]  +  '-'  +  aDate[1]  +  '-'  +  aDate[2]);    //转换为12-18-2006格式  
       	aDate  =  sDate2.split("-");  
       	oDate2  =  new  Date(aDate[0]  +  '-'  +  aDate[1]  +  '-'  +  aDate[2]);  
       	iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24);    //把相差的毫秒数转换为天数  
       	if(oDate1.getTime() < oDate2.getTime()){
       		iDays = "-" + iDays;
       	}
       	return iDays;
 	}    
	
	function getNowFormatDate() {
	    var date = new Date();
	    var seperator1 = "-";
	    var seperator2 = ":";
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
	    return currentdate;
	} 
	
	//获取uuid
	function getUuid() {
	    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
	        var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
	        return v.toString(16);
	    });
	}
	
</script>

</body>
</html>