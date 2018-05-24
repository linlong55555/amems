	
	$(document).ready(function(){
		 
		 $('#form').bootstrapValidator({
		        message: '数据不合法',
		        feedbackIcons: {
		            //valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		                       
		        	zdjsyy: {
		                validators: {
		                    notEmpty: {
		                        message: '指定结束原因不能为空'
		                    }
					        , 
					        stringLength: {
		                        max: 160,
		                        message: '指定结束原因最大长度不能超过160个字符'
		                    }
		        
		                }
		            }
		        }
		    });
		 
		 $('#submit').on('click',function(){
			  $('#form').data('bootstrapValidator').validate();
			  if(!$('#form').data('bootstrapValidator').isValid()){
				return false;
			  }
			   
	    	  //参数组装
			  var obj = {};
			  obj.id = $.trim($("#id").val());				//主键id
		      obj.zdjsyy =  $.trim($("#zdjsyy").val());		//指定结束原因
	    	  AjaxUtil.ajax({
				  url:basePath+'/productionplan/plantask/end',						//发送请求的地址（默认为当前页地址）
				  type:'post',										//请求方式（post或get）默认为get
				  data:obj,							//发送到服务器的数据
				  dataType:'json',									//预期服务器返回的数据类型
				  cache:false,										//缓存（true有缓存，false无缓存）
				  async: false,
				  success:function(data) {							//请求成功后调用的回调函数
					  $('#alertModalBody').html("成功");
				      $('#alertModal').modal('show');
					 
				  },
				  error:function(data) {								 
					  $('#alertModalBody').html(data.responseText);
				      $('#alertModal').modal('show');
				  }
			  }); 
	    	 
	      })
	      
		 
	      $('#alertModal').on('hide.bs.modal', function () {
			window.location.href =  basePath+"/productionplan/plantask/manage";
		})
			
		$('#alertModal').on('hidden.bs.modal', function () {
			window.location.href =  basePath+"/productionplan/plantask/manage";
		})
		 
	});
 
		 