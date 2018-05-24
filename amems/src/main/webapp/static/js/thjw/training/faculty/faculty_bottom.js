var faculty_bottom = {
		cousrseId:'',
		init: function(){
			$("#bottom_hidden_content").find('ul li').removeClass("active");
			$("#bottom_course_div").removeClass("active in");
			$("#attachments_bottom_list_edit").removeClass("active in");
			$("#bottom_courseInfo_div").removeClass("active in");
		},
		//tab切换
		choose: function(obj,index){
			var this_ = this;
			this_.init();
			$(obj).addClass("active");
			//选择授权课程
			if(index == 0){
				courseList.search();
				$("#bottom_course_div").addClass('active in');
				//选择授课记录
			}else if(index == 1){
				courseInfoList.search();
				$("#bottom_courseInfo_div").addClass('active in');
			}else{
				//附件查看
				$("#attachments_bottom_list_edit").addClass('active in');
				var attachmentsObj = attachmentsUtil.getAttachmentsComponents('attachments_bottom_list_edit');
				attachmentsObj.show({
					djid: this_.cousrseId,
					fileHead:false,
					colOptionhead:false,
					left_column:false,
					multiple:false,
					attachHead:false,
					fileType:"card"
				});//显示附件
			}
		},
		//课程删除
		remove:function(obj){
			var this_ = this;
			var sid = $(obj).parent().parent().attr('data-id');
			var data = {id:sid,mainid:this_.cousrseId};
			AlertUtil.showConfirmMessage("确定要删除吗？", {
				callback : function() {
					AjaxUtil.ajax({
						url : basePath + "/training/faculty/deleteCourseInfo",
						type : "post",
						async : false,
						contentType:"application/json;charset=utf-8",
						data : JSON.stringify(data),
						dataType : "json",
						success : function(data) {
							if (data.state == "success") {
								AlertUtil.showErrorMessage("删除成功");
								$("#myTab").find('li').eq(0).click();
							} else {
								AlertUtil.showErrorMessage(data.message);
							}
						}
					});

				}
			});
		}

}

//授权课程列表
var courseList = {
		id:'',
		courseTableList:'#bottom_course_list',
		add: function(){
			
		},
		getList: function(pageNumber, sortType, sequence){
			var this_ = this;
			var searchParam = gatherSearchParam();
	/*		pagination = {
				page : pageNumber,
				sort : sortType,
				order : sequence,
				rows :20
			};*/
			//searchParam.pagination = pagination;
			searchParam.mainid = faculty_bottom.cousrseId;
			startWait();
			AjaxUtil.ajax({
			   url: basePath+'/training/faculty/queryCourseList',
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   data:JSON.stringify(searchParam),
			   dataType:"json",
			   success:function(data){
				    finishWait();
				    /*if (data.total > 0) {
						var page_ = new Pagination({
							renderTo : "bottom_pagination",
							data : data,
							sortColumn : sortType,
							orderType : sequence,
							extParams : {},
							goPage : function(a, b, c) {
								this_.getList(a, b, c);
							}// ,
						});
					}*/
				    if (data.length > 0) {
    					 this_.appendContentHtml(data);
    				}else{
    					$(this_.courseTableList).empty();
    					$(this_.courseTableList).append("<tr class='text-center'><td colspan='6'>暂无数据 No data.</td></tr>");
    				}
				    
//					this_.appendContentHtml(data);
		      }
		    });
		},
		search: function(){
			this.goPage(1,'sort','desc');
		},
		goPage: function(pageNumber, sortType, sequence){
			this.getList(pageNumber, sortType, sequence);
		},
		appendContentHtml: function(list){
			var this_ = this;
			var html = "";
//			if(list && list.length>0){
				for(var i=0;i<list.length;i++){
					var data = list[i];
					if(data.sqksrq==null || data.sqksrq ==''){
						data.sqksrq= '';
					}
					if(data.sqjsrq == null || data.sqjsrq ==''){
						data.sqjsrq = '';
					}
					var remove = "<td style='vertical-align:middle;'><button title='作废 Invalid' class='line6 line6-btn' onclick='faculty_bottom.remove(this)'><i class='icon-minus cursor-pointer color-blue cursor-pointer' ></i></button></td>";
					var kcbh = "<td style='vertical-align:middle;'>"+data.paramsMap.kcbh+"</td>";
					var kcmc = "<td style='vertical-align:middle;'>"+data.paramsMap.kcmc+"</td>";
					var fjjx = "<td style='vertical-align:middle;'>"+StringUtil.escapeStr(data.paramsMap.fjjx)+"</td>";
					var isfx = "<td class='text-center' style='vertical-align:middle;'>"+(data.paramsMap.isfx == 1?"是":"否")+"<input type='hidden' name='isFxWin' value='"+data.paramsMap.isfx+"'/></td>";
					var date = "<td ><div class='input-group input-group-new'><div class='input-group-addon'><input type='text' class='form-control'  readonly name='startDate'   value='"+(data.sqksrq).substr(0,10)+"'/></div>" +
							"<div class='input-group-addon'><input type='text' class='form-control' readonly name='endDate'  value='"+(data.sqjsrq).substr(0,10)+"'/></div></div></td>";
					var total = remove+kcbh+kcmc+fjjx+isfx+date;
					html += "<tr data-id="+data.id+">"+total+"</tr>";
				}
//			}else{
//				html = "<tr class='text-center'><td colspan='6'>暂无数据 No data.</td></tr>";
//			}
			$(this_.courseTableList).empty();
			$(this_.courseTableList).append(html);
			initDate();
			refreshPermission();
	}
}

//授课记录列表
var courseInfoList = {
		courseTableList:"#bottom_courseInfo_list",
		search: function(){
			this.goPage(1,'sort','desc');
		},
		goPage: function(pageNumber, sortType, sequence){
			this.getList(pageNumber, sortType, sequence);
		},
		getList: function(pageNumber, sortType, sequence){
			var this_ = this;
			var searchParam = gatherSearchParam();
	/*		pagination = {
				page : pageNumber,
				sort : sortType,
				order : sequence,
				rows : 20
			};
			searchParam.pagination = pagination;*/
			searchParam.jsid = faculty_bottom.cousrseId;
			console.info(searchParam);
			startWait();
			AjaxUtil.ajax({
			   url: basePath+'/training/faculty/queryCourseInfoList',
			   type: "post",
			   contentType:"application/json;charset=utf-8",
			   data:JSON.stringify(searchParam),
			   dataType:"json",
			   success:function(data){
				    finishWait();
					if (data.length > 0) {
	/*					var page_ = new Pagination({
							renderTo : "bottom_pagination",
							data : data,
							sortColumn : sortType,
							orderType : sequence,
							extParams : {},
							goPage : function(a, b, c) {
								this_.getList(a, b, c);
							}// ,
						});*/
					 this_.appendHtml(data);
				}else{
					$("#bottom_courseInfo_list").empty();
					$("#bottom_courseInfo_list").append("<tr class='text-center'><td colspan='10'>暂无数据 No data.</td></tr>");
				}
		      }
		    });
		},
		appendHtml:function(list){
			var this_ = this;
			var html = "";
//			if(list!=null && list.length>0){
				for(var i=0;i<list.length;i++){
					var data = list[i];
					var kcbh = "<td>"+StringUtil.escapeStr(data.course.kcbh)+"</td>";//课程代码
					var kcmc = "<td>"+StringUtil.escapeStr(data.course.kcmc)+"</td>";//课程名称
					var fjjx = "<td>"+StringUtil.escapeStr(data.course.fjjx)+"</td>";//飞机机型
					var isfx = '';//是否复训
					var zt = '';//状态
					if(data.fxbs=='1'){
						 isfx = "<td>是</td>";
					}else{
						isfx = "<td>否</td>";
					}
					var skri = '';//授课日期
					//根据状态获取授课日期
					var sjks='';
					var sjcr='';
					if(data.zt == '10'){
						var sjsj='';
						if(data.sjJsrq==''){
							 sjsj = (data.sjKsrq?data.sjKsrq.substr(0,10):'')+(data.sj_kssj?(' '+data.sjKssj):'');
						}else{
							sjsj = (data.sjKsrq?data.sjKsrq.substr(0,10):'')+(data.sj_kssj?(' '+data.sjKssj):'')+"~"+(data.sjJsrq?data.sjJsrq.substr(0,10):'')+(data.sjJssj?(' '+data.sjJssj):'');
						}
						skri = "<td title='"+sjsj+"'>"+sjsj+"</td>";
						zt = "<td>完成</td>";
						sjks=data.sjks?data.sjks:0;
						sjcr=data.scrs?data.scrs:0;
					}else{
						var sj ='';
						if(data.jhJsrq==''){
							 sj = (data.jhKsrq?data.jhKsrq.substr(0,10):'')+(data.jhKssj?(' '+data.jhKssj):'');
						}else{
							 sj = (data.jhKsrq?data.jhKsrq.substr(0,10):'')+(data.jhKssj?(' '+data.jhKssj):'')+"~"+(data.jhJsrq?data.jhJsrq.substr(0,10):'')+(data.jhJssj?(' '+data.jhJssj):'');
						}
						skri = "<td title='"+sj+"'>"+sj+"</td>";
						zt = "<td>计划</td>";
						sjks='';
						sjcr='';
					}
					var jhke = "<td>"+(data.ks?data.ks:0)+"</td>";//计划课时
					var sjke = "<td>"+sjks+"</td>";//实际课时
					var jhrs = "<td>"+(data.ycrs?data.ycrs:0)+"</td>";//计划参训人数
					var sjrs = "<td>"+sjcr+"</td>";//实际参训人数
					html +="<tr>"+(kcbh+kcmc+fjjx+isfx+skri+zt+jhke+sjke+jhrs+sjrs)+"</tr>";
				}
//			}else{
//				html = "<tr class='text-center'><td colspan='10'>暂无数据 No data.</td></tr>";
//			}
			$(this_.courseTableList).empty();
			$(this_.courseTableList).append(html);
			initDate();
		},
		
}
