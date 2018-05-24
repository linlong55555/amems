evaluation_modal = {
		id:"evaluation_modal",
		data:[],
		evaluationList:{},
		existsIdList:[],
		param: {
			parentId:null,
			isShowed:true,
			zlid:null,
			userId:userId,
			zlxl:null,
			dprtcode:null,
			jx:null,
			changeCss:false,
			isShowAll:true,
		},
		init:function(param){
			this.evaluationList={};
			this.existsIdList=[];
			this.param.zlid=null;
			this.param.isShowed=true;
			this.param.isShowAll=true;
			if(param){
				$.extend(this.param, param);
			}
			
			$("#"+this.id+"_list").empty();
			if(this.param.changeCss){
				$("#"+this.id+"_divCss").removeClass().addClass("col-lg-11 col-md-11 col-md-11 col-sm-10 col-xs-9 padding-left-8 padding-right-0");
			}
			if(this.param.isShowed){
				$("#evaluation_modal_addEvaluation").show();
			}
			if(this.param.zlid==null||this.param.zlid==''){
				$("#"+this.id+"_list").append("<tr id='evaluation_modal_tr'><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
			}else{
				this.load();
			}
			//处理待办,如果todo_lyid存在，就预先插入该评估单。
			var _this = this;
			if(todo_lyid) {
				var id = todo_lyid;
				todo_lyid = null;
				data = open_win_evaluation_modal.getOne({
					id:id
					,callback:function(data){
						if(data.total >0){
							_this.appendHtml(data.rows);
						}  
					}});
			}
		},
		changeParam:function(param){
			this.evaluationList={};
			this.existsIdList=[];
			if(param){
				$.extend(this.param, param);
			}
			$("#"+this.id+"_list").empty();
			$("#"+this.id+"_list").append("<tr id='evaluation_modal_tr'><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
			this.evaluationList={};
		},
		openSelectEvaluation:function(){
			var this_ = this;
			open_win_evaluation_modal.show({
				parentid:this_.param.parentId,
				userId:this_.param.userId,
				zlxl:this_.param.zlxl,
				jx:this_.param.jx,
				existsIdList:this_.existsIdList,
				multi:true,
				dprtcode:this_.param.dprtcode,
				callback : function(data) {// 回调函数
					this_.appendHtml(data);
				}
			});
		},
		getData:function(){
			var evaluations=[];
			for(key in this.evaluationList){
				evaluations.push(this.evaluationList[key]);
			}
			return evaluations;
		},
		appendHtml:function(list){
			var htmlContent="";			
			var this_ = this;
			if(this_.param.isShowed){
				$("#evaluation_modal_addEvaluation").show();
			}else{
				$("#evaluation_modal_addEvaluation").hide();
			}
			if(list!=null){
				$.each(list,function(index,row){					
					this_.evaluationList[row.id]=row;
					this_.existsIdList.push(row.id);
					htmlContent +="<tr id='"+row.id+"'>";
					if(this_.param.isShowed){						
						htmlContent +="<td class='text-center'><button class='line6 line6-btn' onclick='"+this_.id+".deleteEvaluation(\""+ row.id+ "\");'  type='button'><i class='icon-minus cursor-pointer color-blue cursor-pointer'></i></button></td>";									
					}
					htmlContent += "<td title='"+StringUtil.escapeStr(row.pgdh)+"' style='text-align:left;vertical-align:middle;'><a href='javascript:void(0);' onclick='"+this_.id+".toViewPgd(\""+ row.id +"\")'>"+StringUtil.escapeStr(row.pgdh)+"</a></td>"; 
				    htmlContent += "<td title='R"+StringUtil.escapeStr(row.bb)+"' style='text-align:center;vertical-align:middle;'>R"+StringUtil.escapeStr(row.bb)+"</td>";
				    htmlContent += "<td title='"+StringUtil.escapeStr(row.airworthiness==null?"":row.airworthiness.jswjlx)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.airworthiness==null?"":row.airworthiness.jswjlx)+"</td>";
				    htmlContent += "<td title='"+StringUtil.escapeStr(row.airworthiness.jswjbh) +" "+StringUtil.escapeStr(row.airworthiness==null?"":row.airworthiness.bb==null?"":"R"+row.airworthiness.bb)+"' style='text-align:left;vertical-align:middle;'><a href='#' onclick='"+this_.id+".ToAirworthiness(\""+ row.jswjid+ "\")'>"+StringUtil.escapeStr(row.airworthiness==null?"":row.airworthiness.jswjbh)+" "+StringUtil.escapeStr(row.airworthiness==null?"":row.airworthiness.bb==null?"":"R"+row.airworthiness.bb)+"</a></td>";
				    htmlContent += "<td title='"+StringUtil.escapeStr(row.pgdzt)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.pgdzt)+"</td>";
				    htmlContent += "<td title='"+StringUtil.escapeStr(row.airworthiness.scfj==null?"":row.airworthiness.scfj.wbwjm+"."+row.airworthiness.scfj.hzm)+"' style='text-align:left;vertical-align:middle;'><a href='javascript:void(0);' onclick='"+this_.id+".downloadfile(\""+(row.airworthiness.scfj ==null?"":StringUtil.escapeStr(row.airworthiness.scfj.id))+"\")' >"+StringUtil.escapeStr(row.airworthiness.scfj==null?"":row.airworthiness.scfj.wbwjm+"."+row.airworthiness.scfj.hzm)+"</a></td>";
				    htmlContent += "<td title='"+StringUtil.escapeStr(row.airworthiness==null?"":row.airworthiness.bfrq==null?"":row.airworthiness.bfrq.substring(0,10))+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.airworthiness==null?"":row.airworthiness.bfrq==null?"":row.airworthiness.bfrq.substring(0,10))+"</td>";
				    htmlContent += "<td title='"+StringUtil.escapeStr(row.airworthiness==null?"":row.airworthiness.sxrq==null?"":row.airworthiness.sxrq.substring(0,10))+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.airworthiness==null?"":row.airworthiness.sxrq==null?"":row.airworthiness.sxrq.substring(0,10))+"</td>";
				   
				    htmlContent += "<td title='"+StringUtil.escapeStr(row.airworthiness==null?"":row.airworthiness.dqrq==null?"":row.airworthiness.dqrq.substring(0,10))+"' style='text-align:center;vertical-align:middle;'>"+StringUtil.escapeStr(row.airworthiness==null?"":row.airworthiness.dqrq==null?"":row.airworthiness.dqrq.substring(0,10))+"</td>";
				    htmlContent += "<td title='"+StringUtil.escapeStr(row.pgr==null?"":row.pgr.username)+' '+ StringUtil.escapeStr(row.pgr==null?"":row.pgr.realname)+"' style='text-align:left;vertical-align:middle;'>"+StringUtil.escapeStr(row.pgr==null?"":row.pgr.username)+' '+ StringUtil.escapeStr(row.pgr==null?"":row.pgr.realname)+"</td>";	   
				   	htmlContent += "</tr>";
			   });
			   $("#evaluation_modal_tr").remove();
			   $("#evaluation_modal_list").append(htmlContent);
			}
		},
		deleteEvaluation:function(id){
			$("#evaluation_modal_table #"+id).remove();
			var table = document.getElementById(this.id+"_table");
			var row = table.rows.length;
			if(row == 1){
				$("#"+this.id+"_list").append("<tr id='evaluation_modal_tr'><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
			}
			var existcode=this.existsIdList;
			if(existcode.length>0){
				var index = existcode.indexOf(id);
				if (index > -1) {
					existcode.splice(index, 1);
				}
			}
			this.existsIdList=existcode;
			delete(this.evaluationList[id]);
			
		},
		load : 	function(pageNumber, sortColumn, orderType){		
			var obj ={};
			obj.paramsMap={"zlid":this.param.zlid};
			obj.pagination = {page:pageNumber, sort:sortColumn, order:orderType, rows:10000};
			startWait();
			var this_ = this;
			AjaxUtil.ajax({
				url: basePath+"/project2/assessment/getTechnical",
				type: "post",
				contentType:"application/json;charset=utf-8",
				dataType:"json",
				async:false,
				data:JSON.stringify(obj),
				success:function(data){
					if(data.total >0){
						this_.data = data.rows;												
							this_.appendHtml(data.rows);						
					} else {
						if(this_.param.isShowAll){
							$("#"+this_.id+"_list").empty();
							if(this_.param.isShowed){
								$("#evaluation_modal_addEvaluation").show();
								$("#"+this_.id+"_list").append("<tr id='evaluation_modal_tr'><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
							}else{
								$("#evaluation_modal_addEvaluation").hide();
								$("#"+this_.id+"_list").append("<tr id='evaluation_modal_tr'><td class='text-center' colspan=\"10\">暂无数据 No data.</td></tr>");
							}
						}else{
							document.getElementById(this_.id+"_div").style.display="none";
						}					
					}
					finishWait();
			    }
			}); 
		},
		ToAirworthiness:function(id){
			window.open(basePath + "/project2/airworthiness/view?id="+ id );
		},
		downloadfile : function(id){
			PreViewUtil.handle(id, PreViewUtil.Table.D_011);
		},
		toViewPgd:function(id){
			window.open(basePath+"/project2/assessment/view?id="+id);
		}
		
};