$(function(){
	/**
	 * 弹出窗验证销毁
	 */
	
	$("#AddalertModal").on("hidden.bs.modal", function() {
						$("#form_main").data('bootstrapValidator').destroy();
						$('#form_main').data('bootstrapValidator', null);
					});
	
	$("#AddalertModal").on("shown.bs.modal", function() {
		validation();
	});
	
	

});

var faculty_open = {
		id:'',
		modelId: '#AddalertModal',
		courseTableList: '#basic_education_list',
		add: function(){
			var this_ = this;
			this_.initForm();
			$("#modalName").html("新增");
			$("#modalEname").html("Add");
			 AlertUtil.hideModalFooterMessage();    	   //初始化错误信息 
			$(".noteditable").attr("disabled",false);				//标签不可编辑
			$(".colse-div").addClass("readonly-style");  			//去掉文本框为白的的样式
			$(".required").show();	
			this_.show();
		},
		edit: function(id){
			var this_ = this;
			this_.initForm();
			this_.id = id;
			$(".noteditable").attr("disabled",true);				//标签不可编辑
			$(".colse-div").removeClass("readonly-style");  			//去掉文本框为白的的样式
			$(".required").hide();				
			
			$("#modalName").html("修改");
			$("#modalEname").html("Update");
			var obj = {id:id};
			startWait();
			AjaxUtil.ajax({
			   url: basePath+'/training/faculty/queryInfo',
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   data:JSON.stringify(obj),
			   dataType:"json",
			   success:function(param){
				   	var data = param.row;
					$("#xm").val(data.xm);
					//$("input[name='wbbs'][value='"+data.wbbs+"']").attr('checked',true);
					$("#rybh").val(data.rybh);
					$("#lxdh").val(data.lxdh);
					$("#yxdz").val(data.yxdz);
					$("#wxryid").val(data.wxryid);
					$("#wxrydaid").val(data.wxrydaid);
					$("#jtcy").val(data.jtcy);
					$("#zpdzD").val(data.zpdzD);
					$("#zpdzX").val(data.zpdzX);
					if(data.zpdzD!=null && data.zpdzD!=''){
						var str = data.zpdzD.replaceAll(/\\/g,"&");
						var url = basePath +"/training/faculty/preview/"+str+"/1";
						$(".avatar-view img").attr('src',url);
					}
					$("#AddalertModal input[name='wbbs'][value='"+data.wbbs+"']").attr('checked',true);
					$("#wxrydaid").val(data.wxrydaid);
					$("#AddalertModal input[name='xb'][value='"+data.xb+"']").attr('checked',true);
					var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
					attachmentsObj.show({
						djid: data.id,
						fileType:"card"
					});//显示附件
				   var courseList = data.courseList;
				   this_.addCourseList(courseList);
				   finishWait();
		      }
		    });
			 AlertUtil.hideModalFooterMessage();    	   //初始化错误信息 
			this_.show();
		},
		show: function(){
			var this_ = this;
			$(this_.modelId).modal('show');
			this_.initAttach();
		},
		close: function(){
			var this_ = this;
			$(this_.modelId).modal('hide');
			this_.initForm();
		},
		saveUpdate: function(){
			var this_ = this;
			var obj = {};
			var isValid = true;
			//表单验证
			var bv = $("#form_main").data('bootstrapValidator');
			bv.validate();
			if(!bv.isValid()){
				AlertUtil.showModalFooterMessage("请根据提示输入正确的信息!");
				return;
			}
			
			
			var len = $("#basic_education_list").find("tr").length;
			
			if (len == 1) {
				if($("#basic_education_list").find("td").length ==1){
					AlertUtil.showModalFooterMessage("请增加授权课程!");
					return;
				}
			}
			if (len > 0){
				$("#basic_education_list").find("tr").each(function(){
					var tr_this = this;
					var index=$(this).index(); //当前行
					var startDate =$("#basic_education_list input[name='startDate']").eq(index).val();
			 		 if(startDate==''){
			 			$(tr_this).find("#basic_education_list input[name='startDate']").focus();
			 			$(tr_this).find("#basic_education_list input[name='startDate']").addClass("border-color-red");
			 			AlertUtil.showModalFooterMessage('请输入课程授权开始日期!');
			 			isValid = false; 
						return false;
			 	     }
				});
				if(!isValid){
					return;
				}
			}
			//表单数据获取
			obj.id = this.id;
			obj.xm = $("#xm").val();
			obj.wbbs = $("#AddalertModal input[name='wbbs']:checked").val();
			obj.rybh = $("#rybh").val();
			obj.lxdh = $("#lxdh").val();
			obj.yxdz = $("#yxdz").val();
			obj.jtcy = $("#jtcy").val();
			obj.xb = $("#AddalertModal input[name='xb']:checked").val();
			obj.zpdzD = $("#zpdzD").val();
			obj.zpdzX = $("#zpdzX").val();
			obj.wxryid = $("#wxryid").val();
			obj.wxrydaid = $("#wxrydaid").val();
			//获取附件id
			var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
			var attachments = attachmentsObj.getAttachments();
			obj.attachments = attachments;
			var courseList = [];
			//课程数据获取
			$(this_.courseTableList).find('tr').each(function(){
				var param = {};
				var objTr = $(this);
				if(objTr.attr('data-kcid')){
					var paramsMap = {};
					param.kcid = objTr.attr('data-kcid');
					paramsMap.sqksrq = objTr.find("[name='startDate']").val();
					paramsMap.sqjsrq =objTr.find("[name='endDate']").val();
					param.zt = objTr.find("input[name='isFxWin']").val();
					param.id = objTr.attr('data-id');
					param.paramsMap = paramsMap;
					courseList.push(param);
				}
			});
			obj.courseList = courseList;
			
			startWait();
			AjaxUtil.ajax({
			   url: basePath+'/training/faculty/saveorupdate',
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   data:JSON.stringify(obj),
			   dataType:"json",
			   success:function(data){
				   finishWait();
				   if(data.state == 'success'){
					   AlertUtil.showMessage('保存成功!');
					   //刷新列表
					   this_.close();
					   searchRevision();
					   hideBottom();
				   }else{
					   
				   }
		      }
		    });
		},
		//选择用户，根据所选用户返回用户信息，并反填
		chooseUser: function(){
			var param = {};
			
			param.wbbs=$("#AddalertModal input[name='wbbs']:checked").val();
			param.selected=$("#wxrydaid").val();
			param.callback = function(data){
				if(data.xm==undefined){
					if($("#AddalertModal input[name='wbbs']:checked").val()==2){
						$("#xm").attr("ondblclick","");
						$("#xm").attr("readonly",false);
						$("#rybh").attr("readonly",false);
						$(".colse-div").removeClass("readonly-style");  			//去掉文本框为白的的样式
						$("#wxryid").val('');
						$("#wxrydaid").val('');
						$("#xm").val('');
						$("#rybh").val('');
					}
				}
				
				$("#xm").val(data.xm);
				$("#rybh").val(data.rybh);
				$("#szdw").val(data.szdw);
				$("#lxdh").val(data.lxdh);
				$("#yxdz").val(data.yxdz);
				$("#jtcy").val(data.jtcy);
				$("#wxryid").val(data.wxryid);
				$("#wxrydaid").val(data.id);
				$("#AddalertModal input[name='wbbs'][value='"+data.wbbs+"']").attr('checked','true');
				
				if(data.wbbs==2){
					$("#xm").attr("readonly","readonly");
					$("#rybh").attr("readonly","readonly");
					$(".colse-div").addClass("readonly-style");  			//去掉文本框为白的的样式
					$("#xm").attr("ondblclick","openPersonelWin();");
				}
				
				$("#AddalertModal input[name='xb'][value='"+data.xb+"']").attr('checked','true');
				$('#form_main').data('bootstrapValidator').updateStatus('xm', 'NOT_VALIDATED',null);
				$('#form_main').data('bootstrapValidator').updateStatus('rybh', 'NOT_VALIDATED',null);
			},
			faculty_user.show(param);
			$("#userModal").modal("show");
		},
		//修改时调用，与addCourseList实现的功能相同，添加课程信息，只因为传参不一样所以有两个方法
		addCourse: function(list){
			var this_ = this;
			var html = "";
			if($(this_.courseTableList).find("tr").eq(0).find("td").length > 1){
				this_.reCourse(true);
			}else{
				this_.reCourse(false);
			}
			if(list && list.length>0){
				for(var i=0;i<list.length;i++){
					var data = list[i];
					var remove = "<td style='text-align: center;vertical-align:middle;'><button title='作废 Invalid' class='line6 line6-btn' onclick='faculty_open.remove(this)'><i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i></button></td>";
					var kcbh = "<td style='vertical-align:middle;'>"+StringUtil.escapeStr(data.kcbh)+"</td>";
					var kcmc = "<td style='vertical-align:middle;'>"+StringUtil.escapeStr(data.kcmc)+"</td>";
					var fjjx = "<td style='vertical-align:middle;'>"+StringUtil.escapeStr(data.fjjx)+"</td>";
					var isfx = "<td class='text-center' style='vertical-align:middle;'>"+(data.isFx == 1?"是":"否")+"<input type='hidden' name='isFxWin' value='"+data.isFx+"'/></td>";
					var date = "<td><div class='input-group input-group-new'><div  class='input-group-addon'><input type='text' onchange='faculty_open.changeDqrq(this)' class='form-control date-picker' data-date-format='yyyy-mm-dd' name='startDate'   /></div>" +
							"<div class='input-group-addon'><input type='text'   class='form-control date-picker' data-date-format='yyyy-mm-dd' name='endDate' /></div></div></td>"
					var total = remove+kcbh+kcmc+fjjx+isfx+date;
					html += "<tr data-id='' data-kcid="+data.id+">"+total+"</tr>";
				}
				$(this_.courseTableList).append(html);
			}
			initDate();
		},
		changeDqrq : function(obj){
			$(obj).removeClass("border-color-red");
			if($(obj).val() == null || $(obj).val() == ''){
				$(obj).addClass("border-color-red");
			}
		},
		//修改时调用，与addCourse实现的功能相同，添加课程信息，只因为传参不一样所以有两个方法
		addCourseList: function(list){
			var this_ = this;
			var html = "";
			this_.reCourse(false);
			if(list && list.length>0){
				for(var i=0;i<list.length;i++){
					var data = list[i];
					if(data.sqksrq==null || data.sqksrq ==''){
						data.sqksrq= '';
					}
					if(data.sqjsrq == null || data.sqjsrq ==''){
						data.sqjsrq = '';
					}
					var remove = "<td style='text-align: center;vertical-align:middle;'><button title='作废 Invalid' class='line6 line6-btn' onclick='faculty_open.remove(this)'><i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i></button></td>";
					var kcbh = "<td style='vertical-align:middle;'>"+data.paramsMap.kcbh+"</td>";
					var kcmc = "<td style='vertical-align:middle;'>"+data.paramsMap.kcmc+"</td>";
					var fjjx = "<td style='vertical-align:middle;'>"+StringUtil.escapeStr(data.paramsMap.fjjx)+"</td>";
					var isfx = "<td class='text-center' style='vertical-align:middle;'>"+(data.paramsMap.isfx == 1?"是":"否")+"<input type='hidden' name='isFxWin' value='"+data.paramsMap.isfx+"'/></td>";
					var date = "<td><div class='input-group input-group-new'><div class='input-group-addon'><input type='text' class='form-control date-picker' data-date-format='yyyy-mm-dd' name='startDate' onchange='faculty_open.changeDqrq(this)'   value='"+(data.sqksrq).substr(0,10)+"'/></div>" +
							"<div class='input-group-addon'><input type='text' class='form-control date-picker' data-date-format='yyyy-mm-dd' name='endDate'  value='"+(data.sqjsrq).substr(0,10)+"'/></div></div></td>"
					var total = remove+kcbh+kcmc+fjjx+isfx+date;
					html += "<tr data-kcid="+data.kcid+" data-id="+data.id+">"+total+"</tr>";
				}
				$(this_.courseTableList).append(html);
			}
			initDate();
		},
		/**
		 * 删除一行
		 */
		remove : function(obj){
			var this_=this;
			$(obj).parent().parent().remove();
			var len = $("#basic_education_list").find("tr").length;
			if (len >= 1){
			
			}else{
				$(this_.courseTableList).empty();
				var html = "<tr class='non-choice'><td class='text-center' colspan='6'>暂无数据 No data.</td></tr>";
				$(this_.courseTableList).append(html);
			}
		},
		//判断是否要重新格式化课程列表
		reCourse: function(bl){
			if(bl){
				return false
			}
			var this_ = this;
			var istrue = true;
			$(this.courseTableList).find('tr').each(function(){
				if($(this).attr('data-id')){
					istrue =false;
				};
			});
			if(istrue){
				$(this_.courseTableList).empty();
			}
			return istrue;
		},
		//课程列表格式化
		initCourse: function(){
			$(this.courseTableList).empty();
			var html = "<tr class='non-choice'><td class='text-center' colspan='6'>暂无数据 No data.</td></tr>";
			$(this.courseTableList).append(html);
		},
		//格式化授课管理表单
		initForm: function(){
			$("#wxry_win_Btn").show();
			$("#xm").val('');
			$("#wbbs").val('');
			$("#rybh").val('');
			$("#szdw").val('');
			$("#lxdh").val('');
			$("#yxdz").val('');
			$("#xb").val('');
			$('#jtcy').val('');
			$("#wxryid").val('');
			$("#zpdzD").val('');
			$("#zpdzX").val('');
			$("#wxrydaid").val('');
			$(".avatar-view img").attr('src',basePath+'/static/images/maintenanceTest.png')
			this.id = '';
			this.initCourse();
			$("#AddalertModal input[name='wbbs'][value='1']").attr('checked',true);
			$("#AddalertModal input[name='xb'][value='1']").attr('checked',true);
			onchlickradio();
		},
		//格式化附件信息
		initAttach: function(){
			var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_list_edit');
			attachmentsObj.show({
				djid:'',
				fileType:"card"
			});//显示附件
		}
}

//课程选择相关方法
var faculty_open_course = {
		modalId: '#course-list-modal',
		tableBodyId: '#faculty_open_courseList',
		paramList:[],
		returnList:[],
		idList: [],
		show: function(){
			$(this.modalId).modal('show');
		},
		close: function(){
			$(this.modalId).modal('hide');
		},
		searchRevision: function(){
			this.goPage(1,"auto","desc");
		},
		goPage: function(pageNumber,sortType,sequence){
			this.ajaxGetDatasWithSearch(pageNumber,sortType,sequence)
		},
		ajaxGetDatasWithSearch: function(pageNumber,sortType,sequence){
			var this_ = this;
			this_.paramList=[];
			//查询条件初始化
			var searchParam = this_.gatherSearchParam();
			searchParam.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
			var url = basePath+"/training/course/queryAllPageList";
			startWait();
			AjaxUtil.ajax({
				 url:url,
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   dataType:"json",
			   data:JSON.stringify(searchParam),
			   success:function(data){
				   finishWait();
				   if(data.total >0){
					   this_.paramList = data.rows;
					   this_.appendContentHtml(data.rows);
					   new Pagination({
							renderTo : "coursePagination",
							data: data,
							sortColumn : sortType,
							orderType : sequence,
							goPage:function(pageNumber,sortType,sequence){
								this_.ajaxGetDatasWithSearch(pageNumber,sortType,sequence);
							}
						}); 
					   // 标记关键字
					   signByKeyword($("#faculty_open_course_keyword_search").val(),[2,3,4,6,8,9,13,14,15],"#faculty_open_courseList tr td");
				   } else {
					  $(this_.tableBodyId).empty();
					  $("#coursePagination").empty();
					  $(this_.tableBodyId).append("<tr><td colspan=\"18\" class='text-center'>暂无数据 No data.</td></tr>");
				   }
				   new Sticky({tableId:'course_main_table'});
		      }
		    });
		},
		gatherSearchParam:function(){
			var searchParam = {};
			var paramsMap = {};
			searchParam.dprtcode = $("#dprtcode").val();
			 var keyword = $.trim($("#faculty_open_course_keyword_search").val());
			 var fjjx = $.trim($("#fjjx_search").val());
			 if(fjjx != "-"){
				 paramsMap.qjx = "qjx";
				 searchParam.fjjx = fjjx;
			 }
			//排除已选择的列表
			var idList = faculty_open_course.idList;
			if(idList.length>0){
				paramsMap.idList = idList;
				searchParam.paramsMap = paramsMap;
			}
			searchParam.keyword = keyword;
			 var isFxList = [];
			 $('input[name="isFx_search"]').each(function(i){
				 if(this.checked){
					 isFxList.push($(this).val());
				 }
				});
			 if(isFxList.length == 0){
				 isFxList.push(2);
			 }
			 paramsMap.isFxList = isFxList;
			 searchParam.paramsMap = paramsMap;
			 return searchParam;			
		},
		appendContentHtml: function(list){
			   var this_ = this;
			   var htmlContent = '';
			   $.each(list,function(index,row){
				   if (index % 2 == 0) {
					   htmlContent += "<tr id='"+row.id+"' index='"+index+"' onclick='faculty_open_course.chooseCourse("+index+",this)' dprtcode='"+row.dprtcode+"' ddd='2'  kcbh='"+StringUtil.escapeStr(row.kcbh)+"' style=\"cursor:pointer\" bgcolor=\"#f9f9f9\">";
				   } else {
					   htmlContent += "<tr id='"+row.id+"' index='"+index+"' onclick='faculty_open_course.chooseCourse("+index+",this)' dprtcode='"+row.dprtcode+"' ddd='2' kcbh='"+StringUtil.escapeStr(row.kcbh)+"' style=\"cursor:pointer\" bgcolor=\"#fefefe\">";
				   }
				   
				   htmlContent += "<td class='fixed-column text-center' ><input type='checkbox' index='"+index+"' name='checkrow' index='"+index+"' ddd='1' onclick=\"SelectUtil.checkRow(this,'selectAllId','faculty_open_courseList')\" /></td>";
				   
				   htmlContent += "<td title='"+StringUtil.escapeStr(row.kclb)+"' class='text-left'>"+StringUtil.escapeStr(row.kclb)+"</td>"; 
				   htmlContent += "<td title='"+StringUtil.escapeStr(row.kcbh)+"' class='text-left'>";
				   htmlContent += StringUtil.escapeStr(row.kcbh);
				   htmlContent += "</td>";
				   htmlContent += "<td title='"+StringUtil.escapeStr(row.kcmc)+"' class='text-left'>"+StringUtil.escapeStr(row.kcmc)+"</td>";
				   htmlContent += "<td title='"+StringUtil.escapeStr(row.fjjx)+"' class='text-left'>"+StringUtil.escapeStr(row.fjjx)+"</td>";
				   htmlContent += "<td title='"+StringUtil.escapeStr(row.pxmb)+"' class='text-left'>"+StringUtil.escapeStr(row.pxmb)+"</td>";
				   htmlContent += "<td title='"+StringUtil.escapeStr(row.ks)+"' class='text-right'>"+StringUtil.escapeStr(row.ks)+"</td>";
				   htmlContent += "<td title='"+StringUtil.escapeStr(row.pxxs)+"' class='text-left'>"+StringUtil.escapeStr(row.pxxs)+"</td>";
				   htmlContent += "<td title='"+StringUtil.escapeStr(row.ksxs)+"' class='text-left'>"+StringUtil.escapeStr(row.ksxs)+"</td>";
				   htmlContent += "<td class='text-center'>"+(row.isFx == 1?"是":"否")+"</td>";
				   var fxjg = formatUndefine(row.zqz) + DicAndEnumUtil.getEnumName('cycleEnum',row.zqdw);
				   htmlContent += "<td title='"+fxjg+"' class='text-left'>"+fxjg+"</td>";
				   htmlContent += "<td title='"+StringUtil.escapeStr(row.fxks)+"' class='text-right'>"+StringUtil.escapeStr(row.fxks)+"</td>";
				   htmlContent += "<td title='"+StringUtil.escapeStr(row.fxpxxs)+"' class='text-left'>"+StringUtil.escapeStr(row.fxpxxs)+"</td>";
				   htmlContent += "<td title='"+StringUtil.escapeStr(row.fxksxs)+"' class='text-left'>"+StringUtil.escapeStr(row.fxksxs)+"</td>";
				   htmlContent += "<td title='"+StringUtil.escapeStr(row.bz)+"' class='text-left'>"+StringUtil.escapeStr(row.bz)+"</td>";
				   if(undefined != row.zdr){
					   htmlContent += "<td title='"+StringUtil.escapeStr(row.zdr.displayName)+"' class='text-left'>"+StringUtil.escapeStr(row.zdr.displayName)+"</td>";
				   }
				   else{
					   htmlContent += "<td class='text-left'>"+"</td>";
				   }
				   htmlContent += "<td class='text-center'>"+StringUtil.escapeStr(row.whsj)+"</td>";
				   htmlContent += "<td class='text-left'>"+StringUtil.escapeStr(AccessDepartmentUtil.getDpartName(row.dprtcode))+"</td>";
				   htmlContent += "</tr>";  
				    
			   });
			   $(this_.tableBodyId).empty();
			   $(this_.tableBodyId).html(htmlContent);
			   SelectUtil.selectNode('selectAllId',"faculty_open_courseList");
			   refreshPermission();
		},
		performSelectAll:function(){

            $(this.modalId).find("input[type='checkbox']").each(function() {  
                this.checked = true;  
            });  
		},
		performSelectAllClear: function(){
			$(this.modalId).find("input[type='checkbox']").each(function() {  
                this.checked = false;  
            });  
		},
		save: function(){
			var this_ = this;
			this_.returnList = [];
			$(this.modalId).find("tr input:checked").each(function(i){
				var index = $(this).attr("index");	
				this_.returnList.push(this_.paramList[index]);
			});
			faculty_open.addCourse(this_.returnList);
			this_.close();
		},
		//选择课程
		chooseCourse: function(index,this_){
			
			var obj = $(this_).parent().find("input[type='checkbox']:eq("+index+")");
			
			if(obj.is(':checked')){
				obj.attr('checked',false);
			}else{
				obj.attr('checked',true);
			}
		}
}

//添加课程
function addCourse(){
	faculty_open_course.show();
	faculty_open_course.idList = [];
	$(faculty_open.courseTableList).find('tr').each(function(){
		if($(this).attr('data-kcid')){
			faculty_open_course.idList.push($(this).attr('data-kcid'));
		};
	});
	faculty_open_course.goPage(1,"auto","desc");
}


/**
 * 表单信息验证
 */
function validation(){
	$("#form_main").bootstrapValidator({
		 message: '数据不合法',
	        feedbackIcons: {
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	xm: {
	                validators: {
	                	notEmpty: {
	                        message: '员工姓名不能为空'
	                    }
	                }
	            },
	            rybh: {
	                validators: {
	                    notEmpty: {
	                        message: '编号不能为空'
	                    }
	                }
	            },
	            startDate: {
	                validators: {
	                    notEmpty: {
	                        message: '开始时间不能为空'
	                    }
	                }
	            },
	            lxdh:{
	            	validators:{
	            		callback: {
	                        message: '联系电话输入不合法',
	                        callback: function(value, validator) {
	                        	var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
	                            var isMob=/^1[34578]\d{9}$/;
	                            if(!value||isMob.test(value)||isPhone.test(value)){
	                                return true;
	                            }
	                            else{
	                                return false;
	                            }
	                        }
	                    }
	            	}
	            },
	            yxdz:{
	            	validators:{
	            		regexp:{
	            			regexp: /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/,
	            			message:'邮箱不正确'
	            		}
	            	}            	
	            }
	        }

	});
}

//绑定内外部选择按钮，显示与隐藏搜索按钮
function onchlickradio(){
	var value= $("#AddalertModal input[name='wbbs']:checked").val();
	if(value=='1'){
		//$("#wxry_win_Btn").show();
		$("#xm").attr("ondblclick","openPersonelWin();");
		$(".colse-div").addClass("readonly-style");  			//去掉文本框为白的的样式
		$("#xm").attr("readonly",true);
		$("#rybh").attr("readonly",true);
		$("#wxryid").val('');
		$("#wxrydaid").val('');
		$("#xm").val('');
		$("#rybh").val('');
	}else{
		$("#xm").attr("ondblclick","");
		$("#xm").attr("readonly",false);
		$("#rybh").attr("readonly",false);
		$(".colse-div").removeClass("readonly-style");  			//去掉文本框为白的的样式
		$("#wxryid").val('');
		$("#wxrydaid").val('');
		$("#xm").val('');
		$("#rybh").val('');
	}
}

function resetStartDate(obj){
	var data = $(obj);
	if(data.val()){
		$(obj).removeClass("border-color-red");
	}else{
		$(obj).addClass("border-color-red");
	}
}
function onchangexm(){
	$("#wxrydaid").val('');
}