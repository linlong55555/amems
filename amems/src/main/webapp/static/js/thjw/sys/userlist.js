$(document).ready(function(){
	Navigation(menuCode);
	decodePageParam();
	refreshPermission();

    // 初始化导入
    importModal.init({
	    importUrl:"/sys/user/excelImport?dprtcode=" + $("#dprtcode").val(),
	    downloadUrl:"/common/templetDownfile/17",
		callback: function(data){
			decodePageParam();//开始的加载默认的内容 
			$("#ImportModal").modal("hide");
		}
	});
    changeNbzxdw($("#dprtcode").val());
});

function changeNbzxdw(dprtcode){
	AjaxUtil.ajax({
		url: basePath+"/sys/department/getChildrentList",
		type: "post",
		dataType:"json",
		data:{
			  dprtcode:dprtcode
		},
		success:function(data){
			 $("#zxdw_search").empty();			   
			   var nbzxdwhtml = "<option value=''>显示全部 All</option>";
				if(data != null && data.length >0){					
					$.each(data,function(i,department){
						nbzxdwhtml += "<option value='"+StringUtil.escapeStr(department.id)+"' >"+StringUtil.escapeStr(department.dprtname)+"</option>";
					});
				}
				$("#zxdw_search").html(nbzxdwhtml);
		}
	});	
}

/**
 * 显示导入模态框
 */
function showImportModal(){
	 importModal.show();
}


	 //带条件搜索的翻页
	 function AjaxGetDatasWithSearch(pageNumber,sortType,sequence){
		var obj ={};
		var searchParam = gatherSearchParam();
		pagination = {page:pageNumber,sort:sortType,order:sequence,rows:20};
		
		
		obj.pagination = pagination;
		obj.state=searchParam.state;
		obj.jgdm=searchParam.jgdm;
		obj.keyword=searchParam.keyword;
		obj.bmdm=$("#zxdw_search").val();
		if(id != ""){
			obj.id = id;
			id = "";
		}
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/sys/user/queryUserList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				   appendContentHtml(data.rows);
				   var page_ = new Pagination({
						renderTo : "pagination",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						extParams:{},
						goPage: function(a,b,c){
							AjaxGetDatasWithSearch(a,b,c);
						}
					});
				// 标记关键字
				   signByKeyword($("#keyword_search").val(),[2,3,4,5,6]);
			   } else {
				  $("#userlist").empty();
				  $("#pagination").empty();
				  $("#userlist").append("<tr class='text-center'><td colspan=\"12\">暂无数据 No data.</td></tr>");
			   }
			   new Sticky({tableId:'quality_check_list_table'});
	      }
	    }); 
		
	 }
	 
	/**
	 * 切换组织机构
	 */
	function changeSelectedPlane(){
		goPage(1,"auto","desc");
		
	    // 初始化导入
	    importModal.init({
		    importUrl:"/sys/user/excelImport?dprtcode=" + $("#dprtcode").val(),
		    downloadUrl:"/common/templetDownfile/17",
			callback: function(data){
				decodePageParam();//开始的加载默认的内容 
				$("#ImportModal").modal("hide");
			}
		});
	}
	 
	 //将搜索条件封装 
	 function gatherSearchParam(){
		 var searchParam = {};
		 searchParam.keyword = $.trim($("#keyword_search").val());
		 searchParam.state = $.trim($("#state").val());
		 searchParam.jgdm = $.trim($("#dprtcode").val());
		 return searchParam;
	 }
	 
	 function encodePageParam(){
		 var pageParam={};
		 var params={};
		 params.keyword=$.trim($("#keyword_search").val());
		 params.state=$.trim($("#state").val());
		 params.dprtcode=$.trim($("#dprtcode").val());
		 pageParam.params=params;
		 pageParam.pagination=pagination;
		 return Base64.encode(JSON.stringify(pageParam));
	 }
	 
	 function appendContentHtml(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   htmlContent = htmlContent + "<tr>";
			   htmlContent += "<td class='text-center fixed-column'><div>";
			   htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' permissioncode='sys:user:main:02'  onClick=\"edit('" + row.id + "')\" title='修改 Edit'></i>&nbsp;&nbsp;";
			   if(row.state==1){
				   htmlContent += "<i class='icon-lock color-blue cursor-pointer checkPermission' permissioncode='sys:user:main:03'  onClick=\"LockAndUnlock('"+ row.id + "','"+row.state+"','"+StringUtil.escapeStr(row.jgdm)+"')\" title='锁定 Lock'></i>&nbsp;&nbsp;";
			   }else{
				   htmlContent += "<i class='icon-unlock-alt color-blue cursor-pointer checkPermission' permissioncode='sys:user:main:03' onClick=\"LockAndUnlock('" + row.id + "','"+row.state+"','"+StringUtil.escapeStr(row.jgdm)+"')\" title='解锁 Unlock'></i>&nbsp;&nbsp;";
			   }
			   //登陆账号不为空时显示重置密码功能
			   if(formatUndefine(row.drzhid) != ""){
				   htmlContent += "<i class='icon-refresh color-blue cursor-pointer checkPermission' permissioncode='sys:user:main:04' onClick=\"ResetPassword('" + row.drzhid + "')\" title='重置密码 Reset password'></i>&nbsp;&nbsp;";
				   htmlContent += "<i class='icon-unlink color-blue cursor-pointer checkPermission' permissioncode='sys:user:main:05' onClick=\"UnboundAccout('" + row.id + "', this)\" title='解绑账号 Unbound account'></i>&nbsp;&nbsp;";
			   }
			   htmlContent += "</div></td>";
			   htmlContent += "<td class='text-left fixed-column' title='"+StringUtil.escapeStr(row.accountName)+"'>"+StringUtil.escapeStr(row.accountName)+"</td>"; 
			   htmlContent += "<td class='text-left fixed-column' title='"+StringUtil.escapeStr(row.username)+"'>"+StringUtil.escapeStr(row.username)+"</td>"; 
			   htmlContent += "<td class='text-left fixed-column' title='"+StringUtil.escapeStr(row.realname)+"'>"+StringUtil.escapeStr(row.realname)+"</td>";  
			   htmlContent += "<td class='text-left'>"+formatUndefine(row.phone)+"</td>";  
			   htmlContent += "<td class='text-left'>"+formatUndefine(row.cellphone)+"</td>";
			   if(row.sex==1){
				   htmlContent += "<td class='text-center'>男</td>";
			   }else{
				   htmlContent += "<td class='text-center'>女</td>";
			   }
			   htmlContent += "<td class='text-le'>"+formatUndefine(row.email)+"</td>";  
			   htmlContent += "<td class='text-center'>"+formatUndefine(row.lastvisit)+"</td>";  
			   htmlContent += "<td class='text-left'>"+formatUndefine(row.lastip)+"</td>";  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.dprt_bm?row.dprt_bm.dprtname:'')+"'>"+StringUtil.escapeStr(row.dprt_bm?row.dprt_bm.dprtname:'')+"</td>";  
			   htmlContent += "<td class='text-left' title='"+StringUtil.escapeStr(row.dprt_jg?row.dprt_jg.dprtname:'')+"'>"+StringUtil.escapeStr(row.dprt_jg?row.dprt_jg.dprtname:'')+"</td>";  
			   htmlContent += "</tr>";  
		   });
		   $("#userlist").empty();
		   $("#userlist").html(htmlContent);
		   refreshPermission();
	 }
	 
	 //查看revision对应的工卡列表
	 function goToLinkPage(obj,rid){
		 obj.stopPropagation(); //屏蔽父元素的click事件
		 window.location =basePath+"/main/work/listpage?rid="+rid;
	 }
	  
	//字段排序
	 function orderBy(obj) {
	 	var orderStyle = $("#" + obj + "_order").attr("class");
	 	$("th[id$=_order]").each(function() { //重置class
	 		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
	 	});
	 	$("#" + obj + "_" + "order").removeClass("sorting");
	 	if (orderStyle.indexOf("sorting_asc")>=0) {
	 		$("#" + obj + "_" + "order").addClass("sorting_desc");
	 	} else {
	 		$("#" + obj + "_" + "order").addClass("sorting_asc");
	 	}
	 	orderStyle = $("#" + obj + "_order").attr("class");
	 	var currentPage = $("#pagination li[class='active']").text();
	 	goPage(currentPage,obj,orderStyle.split("_")[1]);
	 }
	
	//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function goPage(pageNumber,sortType,sequence){
		AjaxGetDatasWithSearch(pageNumber,sortType,sequence);
	}
	
    //信息检索
	function searchRevision(){
		goPage(1,"auto","desc");
	}
		
	 //搜索条件重置
	 function searchreset(){
		 $("#keyword_search").val("");
		 $("#divSearch").find("input").each(function() {
			$(this).attr("value", "");
		  });
		
		  $("#divSearch").find("select").each(function(){
			 $(this).val("");
		 });
	 }
	 
	//搜索条件显示与隐藏 
	function search() {
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
		
		$('.date-picker').datepicker({
			autoclose : true
		}).next().on("click", function() {
			$(this).prev().focus();
		});
		
		$('input[name=date-range-picker]').daterangepicker().prev().on("click",function() {
			$(this).next().focus();
		});
		
		 $('#example27').multiselect({
			buttonClass : 'btn btn-default',
			buttonWidth : 'auto',
			includeSelectAllOption : true
		}); 
		 
		 //回车事件控制
		 document.onkeydown = function(event){
			e = event ? event :(window.event ? window.event : null); 
			if(e.keyCode==13){
				if($("#workOrderNum").is(":focus")){
					$("#homeSearchWorkOrder").click();      
				}
			}
		}
		 
	//编辑用户
	function edit(userId){
		window.location.href =basePath+"/sys/user/updateUser?userId="+userId+"&pageParam="+encodePageParam();
	}
	
	//锁定或解锁用户
	function LockAndUnlock(userId,state,jgdm){
		obj={
			"id":userId,
			"jgdm":jgdm,
			"state":state== 0 ? 1: 0
			};
		
		 AlertUtil.showConfirmMessage("确定要锁定/解锁用户吗？", {callback: function(){
			 AjaxUtil.ajax({
				   url:basePath+"/sys/user/updateUserState",
				   type: "post",
				   contentType:"application/json;charset=utf-8",
				   dataType:"json",
				   data:JSON.stringify(obj),
				   success:function(data){
					   AlertUtil.showErrorMessage("锁定/解锁用户成功");
					   id = userId;
					   refreshPage();
					   refreshPermission();
			      }
			    }); 
		 }});
	}
	
	//重置密码
	function ResetPassword(sid){
		AlertUtil.showConfirmMessage("确定要重置密码吗？",{callback:function(){
			startWait();	
			AjaxUtil.ajax({
				url:basePath+"/sys/account/reset/password/"+sid,
				type: "post",
				dataType: "json",
				success:function(data){
					AlertUtil.showMessage("密码重置成功");
					finishWait();
					refreshPermission();
			    }
			}); 
		}});
	}
	
	/**
	 * 解绑账号
	 * @param sid
	 * @returns
	 */
	function UnboundAccout(sid, obj){
		AlertUtil.showConfirmMessage("确定要解绑账号吗？",{callback:function(){
			startWait();	
			AjaxUtil.ajax({
				url:basePath+"/sys/user/unbound/account/"+sid,
				type: "post",
				dataType: "json",
				success:function(data){
					AlertUtil.showMessage("账号解绑成功");
					finishWait();
					id = sid;
					refreshPage();	
					refreshPermission();
			    }
			}); 
		}});
	}

	//加载新增页面
	function add(){
		window.location = basePath+"/sys/user/intoAddUser?pageParam="+encodePageParam();
	}
	
	//刷新页面
	function refreshPage(){
		goPage(pagination.page, pagination.sort, pagination.order);
	}
	
	/**
	 * 解码页面查询条件和分页 并加载数据
	 * @returns
	 */
	function decodePageParam(){
		try{
			var decodeStr = Base64.decode(pageParam);
			var pageParamJson = JSON.parse(decodeStr);
			var params = pageParamJson.params;
			$("#keyword_search").val(params.keyword);
			$("#state").val(params.state);
			$("#dprtcode").val(params.dprtcode);
			if(pageParamJson.pagination){
				goPage(pageParamJson.pagination.page, pageParamJson.pagination.sort, pageParamJson.pagination.order);
			}else{
				goPage(1,"auto","desc");
			}	
		}catch(e){
			goPage(1,"auto","desc");
		}
	}
	
	function exportExcel(){
		var keyword = $("#keyword_search").val();
		var dprtcode = $("#dprtcode").val();
		var state = $("#state").val();
		window.open(basePath+"/sys/user/UserManagement.xls?state=" + encodeURIComponent(state) + "&jgdm="
	 			+ dprtcode + "&keyword="+ encodeURIComponent(keyword));
	}
	
	//回车事件控制
	document.onkeydown = function(event) {
		e = event ? event : (window.event ? window.event : null);
		if (e.keyCode == 13) {
			$('#userMainSearch').trigger('click');
		}
	};