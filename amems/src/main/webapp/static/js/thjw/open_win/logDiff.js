
logDiffModal = {
	version:{current:null,last:null},
	logRule:{}, 
	param: {
	},
	show : function(param){
		if(param){
			$.extend(this.param, param);
		}
		this.readData();
		this.genNav();
		this.genTabContent();
		this.loadData();
		$("#logDiffModal").modal('show');
		 
	},
	readData:function(){
		var _self = this;
		startWait();
		AjaxUtil.ajax({
		   url:basePath+"/sys/log/queryDifference",
		   type: "post",
		   dataType:"json",
		   contentType:"application/json;charset=utf-8",
		   async : false,
		   data: JSON.stringify({paramsMap:this.param }) ,
		   success:function(logRule){
			   finishWait();
			   _self.logRule = logRule;
	     } 
	   }); 
	},
	loadData:function(){
		this.loadData4Master(this.logRule.master);
		this.loadData4Slaves(this.logRule.slaves);
		if(typeof this.param.diffCallback === 'function' ){
			this.param.diffCallback({master:this.logRule.master,slaves:this.logRule.slaves});
		}
		
	},
	loadData4Master:function (table){
		var htmlContent = '';
		var _self = this;
		$.each(table.rows,function(ridx,row){
			
			if(row['DATA_VERSION']=='CURRENT'){
				_self.version['current'] = row['REC_CZLS'];
			}
			else{
				_self.version['last'] = row['REC_CZLS'];
			}
			
			htmlContent = htmlContent+'<tr>'
			htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='text-left  '>"
			+formatUndefine(row['DATA_VERSION']=='CURRENT'?'<span style="color: red">*</span>当前版本 ':'上一版本 ')+"</td>";
			var val = '';
			$.each(table.fields,function(fidx,field){
				if(field.showInDiff){
					if(field.field == "REC_XGLX"){
						val = DicAndEnumUtil.getEnumName("updateTypeEnum", row.REC_XGLX);
					}else if(field.field == "REC_HD"){
						val = StringUtil.escapeStr(row.REC_HD) +" " + DicAndEnumUtil.getEnumName("logOperationEnum", row.REC_CZSM);
					}else{
						val = formatUndefine(StringUtil.escapeStr(row[field.field]) );
						if(val === ''){
							val = '&nbsp;';
						}
					}
					htmlContent = htmlContent + "<td style=\"vertical-align: middle;\"  class='"
					+(field.dataClass||'text-left')
					+"' title='"+val+"' >"+val+"</td>";
				}
			})
			htmlContent = htmlContent+'</tr>'
		});
		$('#'+table['tableName']+' table tbody ').empty();
		$('#'+table['tableName']+' table tbody ').append(htmlContent);
	},
	loadData4Slaves:function (tables){
		if(tables!=null){
			$.each(tables,function(tabIdx,table){
				logDiffModal.loadData4Slave(table);
			 });
		}
	},
	loadData4Slave:function (table){
		
		var htmlContentCurr = '';
		var htmlContentLast = '';
		var czlsCurr = '';
		var czlsLast = '';
		var currentArr = [];
		var lastArr = [];
		$.each(table.rows,function(ridx,row){
			if(row['DATA_VERSION']=='CURRENT'){
				currentArr.push(row);
				htmlContentCurr = htmlContentCurr + logDiffModal.genarateTr(row,table.fields);
			}
			else if(row['DATA_VERSION']=='LAST'){
				lastArr.push(row);
				htmlContentLast = htmlContentLast + logDiffModal.genarateTr(row,table.fields);
			}
		});

		var currentBody = $('#'+table['tableName']+' #'+table['tableName']+'_CURRENT  tbody');
		var lastBody = $('#'+table['tableName']+' #'+table['tableName']+'_LAST  tbody');
		currentBody.empty();
		currentBody.html(htmlContentCurr);
		
		lastBody.empty();
		lastBody.html(htmlContentLast);
		
		
	 
		var currentDiv = currentBody.parent().parent();
		var lastDiv = lastBody.parent().parent();
		
		currentDiv.scroll( function() { 
			lastDiv.scrollTop($(this).scrollTop()); 
			lastDiv.scrollLeft($(this).scrollLeft()); 
		}); 
		lastDiv.scroll( function() { 
			currentDiv.scrollTop($(this).scrollTop()); 
			currentDiv.scrollLeft($(this).scrollLeft()); 
		}); 

		czlsCurr = this.version['current']||'';
		czlsLast= this.version['last']||'';
			
		currentDiv.parent().prev().find('.panel-title').html('<span style="color: red">*</span>当前版本 '+czlsCurr);
		lastDiv.parent().prev().find('.panel-title').html('上一版本 '+czlsLast);
		
		//当前版本，上一版本，补充空行
		var genRowNum = currentArr.length - lastArr.length;
		var genRowNumMod = genRowNum > 0 ?genRowNum:(0-genRowNum);
		var supplyRows = '';
		var rowTemp = {};
		$(table.fields).each(function(idx,item){
			if(item.showInDiff){
				//rowTemp[item.field] = '&nbsp;' ;
				rowTemp[item.field] = '' ;
			}
		  });
		for(i=0;i<genRowNumMod;i++){
			supplyRows = supplyRows + logDiffModal.genarateTr(rowTemp,table.fields);
		}
		
		if(genRowNum>0){
			$('#'+table['tableName']+' #'+table['tableName']+'_LAST  tbody ').append(supplyRows);
		}
		else {
			$('#'+table['tableName']+' #'+table['tableName']+'_CURRENT  tbody ').append(supplyRows);
		}
		
	},
	genarateTr:function(row,fields){
		var htmlContent = '';
		var val = '';
			htmlContent = htmlContent+'<tr>'
			$.each(fields,function(fidx,field){
				if(field.showInDiff){
					if(field.field == "REC_XGLX"){
						val = DicAndEnumUtil.getEnumName("updateTypeEnum", row.REC_XGLX);
					}else if(field.field == "REC_HD"){
						val = StringUtil.escapeStr(row.REC_HD) +" " + DicAndEnumUtil.getEnumName("logOperationEnum", row.REC_CZSM);
					}else{
						val = formatUndefine(StringUtil.escapeStr(row[field.field]) );
						if(val === ''){
							val = '&nbsp;';
						}
					}
					htmlContent += "<td style=\"vertical-align: middle;\"  class='"
						+(field.dataClass||'text-left')
						+"' title='"+val+"' >"+val+"</td>";
				}
			})
			htmlContent = htmlContent+'</tr>'
		return htmlContent;
	},
	genNav:function(){
		var navSb = '';
		if(this.logRule.master!=undefined){
			var master = this.logRule.master;
			navSb += '<li role="presentation"   class="active" >'
					+'<a  href="#'+master.tableName+'"  data-toggle="tab" style="">'+formatUndefine(master.title)+' '+formatUndefine(master.etitle)+'</a>'
					+'</li>'
		}
		
		if(this.logRule.slaves!=undefined && this.logRule.slaves.length>0){
			$.each(this.logRule.slaves,function(idx,slave){
				navSb += '<li role="presentation"  >'
					+'<a  href="#'+slave.tableName+'" data-toggle="tab" style="">'+formatUndefine(slave.title)+' '+formatUndefine(slave.etitle)+'</a>'
					+'</li>'
			});
		}
		
		$('#logDiffModal .nav-tabs').empty();
		$('#logDiffModal .nav-tabs').html(navSb);
		
	},
	genTabContent:function(){
		 
		var tabContent = '';
		if(this.logRule.master!=undefined){
			var master = this.logRule.master;
			var existLast = master.rows.length<2?false:true;
			 
			tabContent = 
			'<div class="tab-pane fade in active"  id="'+master.tableName+'">'
				+'<div class="col-xs-12 text-center padding-left-0 padding-right-0 padding-top-0" style=" overflow-x: auto;" >'
				+'<table  class="table table-thin table-bordered text-center table-set" style="overflow-x: auto;'+master.style+'">'
						+'<thead>'
						+'<tr>';
			
							tabContent = tabContent
							+'<th class="colwidth-7">'
								+'<div class="font-size-12 line-height-18">版本</div>'
								+'<div class="font-size-9 line-height-18">Rev.</div>'
							+'</th>';
							$.each(master.fields,function(idx,field){
								if(field.showInDiff){
									 
									tabContent = tabContent
									+'<th class="'+field.headClass+'">'
										+'<div class="font-size-12 line-height-18">'+formatUndefine(field.fieldName)+'</div>'
										+'<div class="font-size-9 line-height-18">'+formatUndefine(field.fieldEname)+'</div>'
									+'</th>'
								}
							});	
						tabContent = tabContent+'</tr>'
						tabContent = tabContent+'</thead>'
						+'<tbody >'	
						+'</tbody>'		 
					+'</table>'			
				+'</div>'
			+'</div>';
		}
		 
		if(this.logRule.slaves!=undefined && this.logRule.slaves!=null && this.logRule.slaves.length >0) {
			$.each(this.logRule.slaves,function(idx,slave){
				//从表分两个表展示，当前版本，上一版本数据。
				//当前版本。
				tabContent = tabContent
				+'<div class="tab-pane fade "  id="'+slave.tableName+'" >';
				
				tabContent = tabContent +'<div class="col-xs-12 panel panel-default padding-left-0 padding-right-0 padding-top-0" style="padding-left:0px;padding-right:0px;">'
				        +'<div class="panel-heading"><h3 class="panel-title"></h3></div>'
					    	+'<div class=panel-body padding-left-0 padding-right-0>'
								+'<div class=" text-center padding-left-0 padding-right-0 padding-top-0" style="overflow-x: auto;overflow-y: auto;">' 
									+'<table id="'+slave.tableName+'_CURRENT'+'"  class=" table table-thin table-bordered text-center table-set" style="overflow-x: auto;'+slave.style+'">'
										+'<thead>'
										+'<tr>';
											$.each(slave.fields,function(idx,field){
												if(field.showInDiff){
													tabContent = tabContent
													+'<th class="'+field.headClass+'">'
														+'<div class="font-size-12 line-height-18">'+formatUndefine(field.fieldName)+'</div>'
														+'<div class="font-size-9 line-height-18">'+formatUndefine(field.fieldEname)+'</div>'
													+'</th>';
												}
											});	
										tabContent = tabContent+'</tr>'
										+'</thead>'
										+'<tbody >'	
										+'</tbody>'		 
									+'</table>'		
									+'</div>'
								+'</div>'  
							+'</div>' 
						 
					//当前版本是创建，没有上一版本。
					if(existLast){
					    tabContent = tabContent +'<div class="col-xs-12 panel panel-default padding-left-0 padding-right-0 padding-top-0">'
				        +'<div class="panel-heading"><h3 class="panel-title"></h3></div>'
					    	+'<div class=panel-body>'			
								+'<div class=" text-center padding-left-0 padding-right-0 padding-top-0 " style="overflow-x: auto; overflow-y: auto;">'
									+'<table  id="'+slave.tableName+'_LAST'+'"  class=" table table-thin table-bordered text-center table-set" style="overflow-x: auto;'+slave.style+'">'
										+'<thead>'
										+'<tr>';
											$.each(slave.fields,function(idx,field){
												if(field.showInDiff){
													tabContent = tabContent
													+'<th class="'+field.headClass+'">'
														+'<div class="font-size-12 line-height-18">'+field.fieldName+'</div>'
														+'<div class="font-size-9 line-height-18">'+formatUndefine(field.fieldEname)+'</div>'
													+'</th>';
												}
											});	
										tabContent = tabContent+'</tr>'
										+'</thead>'
										+'<tbody >'	
										+'</tbody>'		 
									+'</table>'		
								+'</div>'
							+'</div>'  
						+'</div>'  
					}
					tabContent = tabContent +'</div>' //tab-pane
				;
			});
		}
		 
		var tabContentDiv = $('#logDiffModal .tab-content');
		tabContentDiv.empty();
		tabContentDiv.append(tabContent);
	}
}


