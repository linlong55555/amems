  $(document).ready(function(){
	  Navigation(menuCode,"新增机型","Add");

	  $(".panel-heading").not(":first").click(function(){         //隐藏和显示
			$(this).next().slideToggle("fast");
		});
	  
	 validatorForm =  $('#form').bootstrapValidator({
	        message: '数据不合法',
	        feedbackIcons: {
	            //valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	fjjx: {
	                validators: {
	                	notEmpty: {
	                        message: '飞机机型不能为空'
	                    },
	                    regexp: {
	                        regexp: /^[^\u4e00-\u9fa5]{0,50}$/,
	                        message: '不包含中文且长度不超过50个字符'
	                    }
	                }
	            },
	        }
	    });
	});

 function del_tr(obj) {                                            //删除部件号行
    $(obj).parent().parent().remove();     
 }
 function addTr(){
	   $("#zwsj").remove();
	   var htmlContent = '';
	   htmlContent = htmlContent + "<tr  name='one_line' bgcolor=\"#f9f9f9\">";
	   htmlContent = htmlContent + "<td style='vertical-align:middle'><button class='line6' onclick='del_tr(this)'><i class='fa glyphicon glyphicon-minus color-blue cursor-pointer'></i></button></td>" ;
	   htmlContent = htmlContent + "<td style='vertical-align:middle' ><div class='input-group'><input maxlength='50'class='form-control' type='text' style='ime-mode:disabled' name='bjh'/><span class='input-group-btn'><button onclick='openHcxxList(this)' class='btn btn-primary'><i class='icon-search'></i></button></span></div></td>";  
	   htmlContent = htmlContent + "<td style='vertical-align:middle'><input maxlength='300'class='form-control' type='text' style='ime-mode:disabled' name='ywms'/></td>"; 
	   htmlContent = htmlContent + "<td style='vertical-align:middle' ><input maxlength='100'class='form-control' type='text'  name='zwms'/></td>";  
	   htmlContent = htmlContent + "<td style='vertical-align:middle'><input  maxlength='300' placeholder='最大长度不超过300' class='form-control' type='text' name='bz'/></td>";  
	   htmlContent = htmlContent + "</tr>";  
	   $("#CKlist").append(htmlContent);
 }
function setValue(value){
	if(value==null||value==0||value==""){
		return '1';
	}else{
		return value;
	}
}  
  
function savePlaneModelData() {
	$("#addtr input[name='bjh']").each(function(){
		var input = $(this);
		input.css("border-color","");
	});
	
	$('#form').data('bootstrapValidator').validate();
	  if(!$('#form').data('bootstrapValidator').isValid()){
		return false;
	}
	  
    var bjhs=[];												//	判断部件号是否为空
	var len = $("#CKlist").find("tr").length;  
	if (len > 0){
		var flag=true;
		$("#CKlist").find("tr").each(function(){
			var index=$(this).index(); //当前行
			var jh =$("input[name='bjh']").eq(index).val();
			if(jh==null||jh==""){
				//AlertUtil.showMessage("关联部件号不能为空！");
				flag=false;
				return false;
			}else{
				bjhs.push(jh);
				flag=true;
			}
		});
//		if(!flag){
//			return;
//		}
	}
	var newbjhs=bjhs.sort();                                  //判断部件号是否重复
    for(var i=0;i<bjhs.length;i++) {
		if(newbjhs[i]==newbjhs[i+1]) {
			AlertUtil.showMessage("关联部件号不可重复！");
			$("#addtr input[name='bjh']").each(function(){       //如果重复则改变器样式
				var input = $(this);
				if(input.val()==bjhs[i]){
					input.css("border-color","#a94442");
				}
			});
			return false;
		}
	}
	 // 保存飞机型号
	 var fjjx = $.trim($("#fjjx").val());

	 var obj={};
	 obj.fjjx=$('#fjjx').val(),                  
	 obj.rJsfxsj=setValue($('#rJsfxsj').val()),                 
	 obj.rSsdsj=setValue($('#rSsdsj').val()),                        
	 obj.rJcsj=setValue($('#rJcsj').val()),                    
	 obj.rQljxh=setValue($('#rQljxh').val()),                           
	 obj.rJcxh= setValue($('#rJcxh').val()),                      
	 obj.rWdgxh=setValue($('#rWdgxh').val()),                        
	 obj.rSsdxh=setValue($('#rSsdxh').val()),                    
	 obj.rN1= setValue($('#rN1').val()),                           
	 obj.rN2= setValue($('#rN2').val()),            
	 obj.rN3=setValue($('#rN3').val()),     
	 obj.rN4=setValue($('#rN4').val()),                           
	 obj.rN5=setValue($('#rN5').val()),            
	 obj.rN6=setValue($('#rN6').val()),      
	 obj.rTsjk1=setValue($('#rTsjk1').val()),                           
	 obj.rTsjk2= setValue($('#rTsjk2').val()),  
	 obj.isTsqk= $("input:radio[name='isTsqk']:checked").val(),
	 obj.gsDjjh= $('#gsDjjh').val(),  
	 
	 obj. jX_BJList= getPlaneBJParmes();
	 AjaxUtil.ajax({
			type : 'post',
			cache : false,
			url : basePath+'/project/planemodeldata/getPlaneModelConut',                                                             //验证飞机机型是否重复
			//contentType:"application/json;charset=utf-8",
			dataType : 'json',
			data:{fjjx: fjjx},
			success : function(data) {
				if (data != 1) {                                                                                //机型不存在则可以执行增加操作
					AjaxUtil.ajax({
						type : 'post',
						url : basePath+'/project/planemodeldata/addPlaneModelData',                                                           
						contentType:"application/json;charset=utf-8",
						data:JSON.stringify(obj),
						dataType : 'json',
						success : function(data) {
							if (data!=null) {
								AlertUtil.showMessage("保存成功",'/project/planemodeldata/main?fjjx='+encodeURIComponent(data)+'&pageParam='+pageParam);
							} else {
								AlertUtil.showErrorMessage(data.text);
							}
						}
					});
				} else {
					AlertUtil.showErrorMessage("该机型数据已经存在");
				}
			}
		});
	}

	//获取机型部件表的参数
	function getPlaneBJParmes(){
		var PlaneBJParmes = [];
		var len = $("#CKlist").find("tr").length;
		if (len == 1) {
			if($("#CKlist").find("td").length ==1){
				return PlaneBJParmes;
			};
		}
		if (len > 0){
			$("#CKlist").find("tr").each(function(){
				var infos ={};
				var index=$(this).index(); //当前行
				
				var jh =$("input[name='bjh']").eq(index).val();
				var zwmc =$("input[name='zwms']").eq(index).val(); 
				var ywmc =$("input[name='ywms']").eq(index).val(); 
				var bz =$("input[name='bz']").eq(index).val(); 
				
				infos.fjjx=$('#fjjx').val(),  
     			infos.jh = jh;
				infos.zwmc = zwmc;
				infos.ywmc = ywmc;
				infos.bz  = bz;
				PlaneBJParmes.push(infos);
			});
		}
		return PlaneBJParmes;
	}
	

	//信息部件信息
	function searchRevision(){
		goPage2(1,"auto","desc");
	}

	var thisObj = null;
	function openHcxxList(obj) {
		 goPage2(1,"auto","desc");
		 $("#alertModalHcxx").modal("show");
		 thisObj = obj;
	}
	//跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则 
	function goPage2(pageNumber,sortType,sequence){
		AjaxGetDatasWithSearch2(pageNumber,sortType,sequence);
	}
	//带条件搜索的翻页
	function AjaxGetDatasWithSearch2(pageNumber,sortType,sequence){
	    var obj ={};
		obj.keyword = $.trim($("#keyword_search").val());
		obj.dprtcode= userJgdm;
		obj.pagination = {page:pageNumber,sort:sortType,order:sequence,rows:10};
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/project/planemodeldata/selectHcxxList",
		   type: "post",
		   contentType:"application/json;charset=utf-8",
		   dataType:"json",
		   data:JSON.stringify(obj),
		   success:function(data){
		    finishWait();
			   if(data.total >0){
				   appendContentHtml2(data.rows);
				   new Pagination({
						renderTo : "pagination3",
						data: data,
						sortColumn : sortType,
						orderType : sequence,
						goPage:function(a,b,c){
							AjaxGetDatasWithSearch2(a,c,b);
						}
					});
				   // 标记关键字
				   signByKeyword($("#keyword_search").val(),[2,3,4]);
			   } else {
				  $("#Hcxxlist").empty();
				  $("#pagination3").empty();
				  $("#Hcxxlist").append("<tr class='text-center'><td colspan=\"4\">暂无数据 No data.</td></tr>");
			   }
	   },
	 }); 
		
	}
	function chooesRow2(obj){
		chooesRow($(obj));
	}
	function chooesRow1(objradio){
		var obj = $(objradio).find("input[type='radio']");
		chooesRow(obj);
	}
	//行选
	function chooesRow(obj){
		var flag = obj.is(":checked");
		if(flag){
			obj.removeAttr("checked");
		}else{
			obj.attr("checked",true);
		}
	}
	function appendContentHtml2(list){
		   var htmlContent = '';
		   $.each(list,function(index,row){
			   if (index % 2 == 0) {
				   htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#f9f9f9\" onclick='chooesRow1(this)'>";
			   } else {
				   htmlContent = htmlContent + "<tr style=\"cursor:pointer\" bgcolor=\"#fefefe\" onclick='chooesRow1(this)'>";
			   }
			      
			   htmlContent = htmlContent + "<td class='text-center'><input type=\"radio\" onclick='chooesRow2(this)' name='hcxx'>" +
											"<input type='hidden' name='bjh' value="+StringUtil.escapeStr(row.bjh)+">" +
											"<input type='hidden' name='zwms' value='"+StringUtil.escapeStr(row.zwms)+"'>" +
									   		"<input type='hidden' name='ywms' value='"+StringUtil.escapeStr(row.ywms)+"'>" +
									   		"</td>";
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.bjh)+"'>"+StringUtil.escapeStr(row.bjh)+"</td>";  
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.ywms)+"'>"+StringUtil.escapeStr(row.ywms)+"</td>"; 
			   htmlContent = htmlContent + "<td title='"+StringUtil.escapeStr(row.zwms)+"'>"+StringUtil.escapeStr(row.zwms)+"</td>";  
			   htmlContent = htmlContent + "</tr>";  
		   });
		   $("#Hcxxlist").html(htmlContent);
		 
	}
	
	
	function back(){
		window.location.href =basePath+"/project/planemodeldata/main?pageParam="+pageParam;
	}
	
	
	
	//保存航材信息
	function appendHcxx(){
		$('input[name=hcxx]:checked').each(function(){  
			var bjh=$(this).next().val();
			var zwms=$(this).next().next().val();
			var ywms=$(this).next().next().next().val();
			$(thisObj).parent().prev().val(bjh);
			var $tr = $(thisObj).parent().parent().parent().parent();
			$tr.find("input[name='zwms']").val(zwms);
			$tr.find("input[name='ywms']").val(ywms);
		 });
	}
	
	//扩展数组方法：查找指定元素的下标  
	Array.prototype.indexOf = function(val) {  
	for (var i = 0; i < this.length; i++) {  
	    if (this[i] == val) return i;  
	}  
	return -1;  
	};  

	//扩展数组方法:删除指定元素  
	Array.prototype.remove = function(val) {  
	var index = this.indexOf(val);  
	while(index>-1){  
	    this.splice(index, 1);  
	    index = this.indexOf(val);  
	}  
	};
	
	//只能输入正整数
	function clearNoNum(obj){
     obj.value=obj.value.replace(/\D/g,'');
     obj.value=obj.value.replace(/\D/g,'');
     /*if(obj.value>32767){
    	 obj.value='';
     }*/
	}
