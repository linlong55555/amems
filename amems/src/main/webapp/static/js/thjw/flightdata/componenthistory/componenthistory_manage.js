	
	$(document).ready(function(){
		
		Navigation(menuCode);
		changeOrganization();
		 goPage(1,"","");//开始的加载默认的内容 
		 
	});
	
	//改变组织机构时改变飞机注册号
	function changeOrganization(){
		
		var planeDatas = acAndTypeUtil.getACRegList({DPRTCODE:$("#dprtcode").val()});
		
		$("#fjzch").empty();
	 	var planeRegOption = '<option value="" >全部</option>';
		if(planeDatas != null && planeDatas.length >0){
			$.each(planeDatas,function(i,planeData){
				planeRegOption += "<option value='"+StringUtil.escapeStr(planeData.FJZCH)+"' >"+StringUtil.escapeStr(planeData.FJZCH)+"</option>";
			});
		}
		 
		$("#fjzch").html(planeRegOption); 
		 
		 goPage(1,"","");
		 $('#fjzch').on('change',function(){
			 goPage(1,"","");
		 });
	}
	
	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sortField){
		 var param = {};
		 param.keyword = $.trim($("#keyword").val());
		 
		 var fjzch = $.trim($("#fjzch").val());
		 if(fjzch != ''){
			 param.fjzch = fjzch;
		 }
		 
		 var dprtcode = $.trim($("#dprtcode").val());
		 if(dprtcode != ''){
			 param.dprtcode = dprtcode;
		 }
		 
		 var zt = $.trim($("#zt").val());
		 if(zt != ''){
			 param.zt = zt;
		 }
		 
		 var wz = $.trim($("#wz").val());
		 if(wz != ''){
			 param.wz = wz;
		 }
		 
		 param['sort'] = sortField;						//排序列字段名
		 param['order'] = sortType;					//排序方式，可以是 'asc' 或者 'desc'，默认值是 'asc'
		 param['page'] = pageNumber;						//页数
		 param["rows"] = 20;		 
		 param['userType'] = userType;
		 param['userId'] = userId;
		 
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/flightdata/componenthistory/queryList",
		   type: "post",
		   data:param,
		   async: false,
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				   appendContentHtml(data.rows);
				   var page_ = new Pagination({
						renderTo : "pagination",
						data: data,
						sortColumn : sortField,
						orderType : sortType,
						extParams:{},
						goPage: function(a,b,c){
							AjaxGetDatasWithSearch(a,c,b);
						}//,
						//controller: this_
					});
			   } else {
				  $("#list").empty();
				  $("#pagination").empty();
				  $("#list").append("<tr><td colspan=\"12\">暂无数据 No data.</td></tr>");
			   }
			   new Sticky({
					tableId : 'componenthistory'
				});
			   refreshPermission();
	      }
	    }); 
		
	 }
	 
	 function appendContentHtml(list){
		   var htmlContent = ''; 
		   $.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent = htmlContent + "<tr   bgcolor=\"#f9f9f9\">";
			   } else {
				   htmlContent = htmlContent + "<tr   bgcolor=\"#fefefe\">";
			   }
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.JH) )  +"'>"+formatUndefine(StringUtil.escapeStr(row.JH))+"</td>";  
			 
			   //件号，序列号非空，层级大于1可以查看详情（调整后：拆下件不显示子部件）
			   if (row.JH !=undefined && row.JH != '' 
				   && row.XLH !=undefined && row.XLH != '' ) {
				   
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" title='"+formatUndefine(StringUtil.escapeStr(row.XLH) )  +"' class='text-left'>" 
				   +"<a href='javascript:void(0);' onClick=\"detail(this)\"" 
				   + " jh='"+StringUtil.escapeStr(row.JH)+"'"
				   + " xlh='"+StringUtil.escapeStr(row.XLH )+"'"
				   + " dprtcode='"+row.DPRTCODE+"'"
				   +">"
			   		+formatUndefine(StringUtil.escapeStr(row.XLH) )+"</a></td>";
				   
			   }
			   else{
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.XLH))+"'>" +formatUndefine(StringUtil.escapeStr(row.XLH))+"</td>";
			   }
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.YWMS))+"' >"+formatUndefine(StringUtil.escapeStr(row.YWMS)) +"</td>";
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.ZWMS))  +"' >"+ formatUndefine(StringUtil.escapeStr(row.ZWMS)) +"</td>";  
			   
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.CJJH))+"'>"+formatUndefine(StringUtil.escapeStr(row.CJJH) )+"</td>";
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.ZJH))+' '+ formatUndefine(StringUtil.escapeStr(row.ZJZWMS) )+"'>"+formatUndefine(StringUtil.escapeStr(row.ZJH) )
			   +' '+ formatUndefine(StringUtil.escapeStr(row.ZJZWMS) )		   
			   +"</td>"; 
			 
			   
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.FJZCH))+"'>"+formatUndefine(StringUtil.escapeStr(row.FJZCH) )+"</td>";
			   
			   //件号，序列号非空，层级大于1可以查看详情（父级的）
			   if (row.FJDJH !=undefined && row.FJDJH != '' 
				   && row.FJDXLH !=undefined && row.FJDXLH != '' 
				   && row.CJ != undefined && row.CJ>1 ) {
				   
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" title='"+formatUndefine(StringUtil.escapeStr(row.FJDMC) )  +"' class='text-left'>"
				   +"<a href='javascript:void(0);' onClick=\"detail(this)\"" 
				   + " jh='"+StringUtil.escapeStr(row.FJDJH)+"'"
				   + " xlh='"+StringUtil.escapeStr(row.FJDXLH) +"'"
				   + " dprtcode='"+row.DPRTCODE+"'"
				   +">"
				   +formatUndefine(StringUtil.escapeStr(row.FJDMC))
				   +"</a></td>";
				   
			   }
			   else{
				   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left'></td>";
			   }
			   
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.ZT) )  +"'>"+formatUndefine(row.ZT)+"</td>";  
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-right' title='"+formatUndefine(StringUtil.escapeStr(row.CJ) )  +"'>"+formatUndefine(row.CJ)+"</td>";  
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left' title='"+formatUndefine(DicAndEnumUtil.getEnumName('partsPositionEnum',row.WZ)) +"'>"+formatUndefine(DicAndEnumUtil.getEnumName('partsPositionEnum',row.WZ))+"</td>";  
			   htmlContent = htmlContent + "<td style=\"vertical-align: middle;\" class='text-left' title='"+formatUndefine(StringUtil.escapeStr(row.DPRTNAME) )  +"'>"+formatUndefine(StringUtil.escapeStr(row.DPRTNAME) )+"</td>"; 
			   htmlContent = htmlContent + "</tr>";  
			    
		   });
		   $("#list").empty();
		   $("#list").html(htmlContent);
		  
		   var keyword = $.trim($("#keyword").val());
		   signByKeyword(keyword, [1,2,3,5,7])
		 
	 }
	 
	 //查看revision对应的工卡列表
	 function goToLinkPage(obj,rid){
		 obj.stopPropagation(); //屏蔽父元素的click事件
		 window.location =basePath+"/main/work/listpage?rid="+rid;
	 }
	  
	//字段排序
	function orderBy(sortField) {
		var sortColum = $("#" + sortField + "_order");
		var orderStyle = sortColum.attr("class");
		$("th[id$=_order]").each(function() { //重置class
			$(this).removeClass().addClass("sorting");
		});
		sortColum.removeClass("sorting");
		if (orderStyle == "sorting_asc") {
			sortColum.addClass("sorting_desc");
		} else {
			sortColum.addClass("sorting_asc");
		}
		orderStyle = $("#" + sortField + "_order").attr("class");
		var currentPage = $("#pagination li[class='active']").text();
		currentPage = currentPage==''?1:currentPage;
		//sortField = 'b1.jh'
		goPage(currentPage,orderStyle.split("_")[1],sortField);
	}
	
    /**
     * 跳转至指定页数:三个参数依次为:当前页码,排序规则 排序字段,
     * @param pageNumber
     * @param sortType
     * @param sortField
     */ 
	function goPage(pageNumber,sortType,sortField){
		AjaxGetDatasWithSearch(pageNumber,sortType,sortField);
	}	
		
	 //搜索条件重置
	 function searchreset(){
		 var userJgdm = $('#user_jgdm').val();
		  $("#divSearch").find("#zt").each(function(){
			 $(this).val("");
		 })
		 $("#divSearch").find("#wz").each(function(){
			 $(this).val("");
		 })
		 $("#divSearch").find("#fjzch").each(function(){
			 $(this).val("");
		 })
		 
		 $("#keyword").val("");
		  
		 $('#dprtcode').val(userJgdm);
		 $('#dprtcode').trigger('change');
		  
	 }
	 
	 function search(){
			goPage(1,"","");
	 }
	 
	 //查看工单详细信息
	 /*function detail(jh,xlh,dprtcode){
		 window.open(basePath+"/flightdata/componenthistory/view?jh="+encodeURIComponent($.trim(jh))+'&xlh='+encodeURIComponent($.trim(xlh))+'&dprtcode='+encodeURIComponent(dprtcode));
	 }*/
	 
	 function detail(obj){
		 var jh = $(obj).attr('jh');
		 var xlh = $(obj).attr('xlh');
		 var dprtcode = $(obj).attr('dprtcode');
		 window.open(basePath+"/flightdata/componenthistory/view?jh="+encodeURIComponent(jh)+'&xlh='+encodeURIComponent(xlh)+'&dprtcode='+encodeURIComponent(dprtcode));
	 }
		 
		 
	 
	 function more() {
			if ($("#divSearch").css("display") == "none") {
				$("#divSearch").css("display", "block");
				$("#icon").removeClass("icon-caret-down");
				$("#icon").addClass("icon-caret-up");
			} else {
				$("#divSearch").css("display", "none");
				$("#icon").removeClass("icon-caret-up");
				$("#icon").addClass("icon-caret-down");
			}
		}
		 