$(function() {
	/*
	 * Navigation(menuCode); goPage(1,"auto","desc");
	 * 
	 * //验证 validation();
	 */
	Navigation(menuCode,"","");//初始化导航
	logModal.init({code : 'B_P_008'});  //日志
	initDate();
	searchRevision();
});
//通过id获取航材数据
function selectFjjxByDprtcode(dprtcode, obj){
	
	AjaxUtil.ajax({
	 	   url:basePath+"/project/planemodeldata/findAllType",
	 	   async: false,
	 	   type: "post",
	 	   dataType:"json",
	 	   data:{dprtcode:dprtcode},
	 	   success:function(data){
	 		  if(typeof(obj)=="function"){
					obj(data); 
	 		  }
	 	   }
	    }); 
}
function initPlaneModelSearch(){
 	selectFjjxByDprtcode($("#dprtcode").val(), function(data){
 	 	var option = '<option value="-" selected="true">显示全部All</option>';
 	 	if(data != null && data.length >0){
 			$.each(data,function(i,fjjx){
 				option += "<option value='"+StringUtil.escapeStr(fjjx)+"' >"+StringUtil.escapeStr(fjjx)+"</option>";
 			});
 	  	}
 	 	option += '<option value="">N/A</option>';
 	 	$("#fjjx_search").empty();
 	 	$("#fjjx_search").append(option);
	});
}
function initDate(){
	$('.date-picker').datepicker({
		autoclose : true,
		clearBtn : true
	});
}


// 跳转至指定页数:三个参数依次为:当前页码,排序字段,排序规则
function goPage(pageNumber, sortType, sequence) {
	AjaxGetDatasWithSearch(pageNumber, sortType, sequence);
}

// 查询条件
function gatherSearchParam() {
	var searchParam = {};
	searchParam.dprtcode = $("#dprtcode").val();
	searchParam.keyword = $.trim($('#keyword_search').val());
	var paramsMap = {};
	paramsMap.kcid = $("#kcid").val();
	var wbbsList = [];
	if ($("#wbbs1").attr("checked")) {
		wbbsList.push(1);
	}
	if ($("#wbbs2").attr("checked")) {
		wbbsList.push(2);
	}
	if (wbbsList.length > 0) {
		paramsMap.wbbsList = wbbsList;
	}else{
		wbbsList.push(3);
		paramsMap.wbbsList = wbbsList;
	}
	
	var xbList = [];
	if ($("#xb1").attr("checked")) {
		xbList.push(1);
	}
	if ($("#xb2").attr("checked")) {
		xbList.push(2);
	}
	if (xbList.length > 0) {
		paramsMap.xbList = xbList;
	}else{
		xbList.push(3);
		paramsMap.xbList = xbList;
	}
	searchParam.paramsMap = paramsMap;
	return searchParam;
}

// 带条件搜索的翻页
function AjaxGetDatasWithSearch(pageNumber, sortType, sequence) {

	var searchParam = gatherSearchParam();
	pagination = {
		page : pageNumber,
		sort : sortType,
		order : sequence,
		rows : 20
	};
	searchParam.pagination = pagination;
	AjaxUtil
			.ajax({
				url : basePath + "/training/faculty/query/list",
				type : "post",
				contentType : "application/json;charset=utf-8",
				dataType : "json",
				data : JSON.stringify(searchParam),
				success : function(data) {
					if (data.total > 0) {
						appendContentHtml(data.rows);
						var page_ = new Pagination({
							renderTo : "pagination",
							data : data,
							sortColumn : sortType,
							orderType : sequence,
							extParams : {},
							goPage : function(a, b, c) {
								AjaxGetDatasWithSearch(a, b, c);
							}// ,
						});
						// 标记关键字
						signByKeyword($.trim($("#keyword_search").val()),[ 2,3,6,7 ],"#faculty_list tr td");
					} else {
						$("#faculty_list").empty();
						$("#pagination").empty();
						$("#faculty_list")
								.append(
										"<tr><td class='text-center' colspan=\"11\">暂无数据 No data.</td></tr>");
					}
					new Sticky({tableId:'main_table'}); //初始化表头浮动

				}
			});
}
/* 表格拼接 */
function appendContentHtml(list) {

	var htmlContent = '';
	$.each(list,function(index, row) {
						if (index % 2 == 0) {
							htmlContent += "<tr class='cursor-pointer' bgcolor=\"#f9f9f9\" id='"
									+ row.id
									+ "' onclick='showHiddenContent(this)'>";

						} else {
							htmlContent += "<tr class='cursor-pointer' bgcolor=\"#fefefe\" id='"
									+ row.id
									+ "' onclick='showHiddenContent(this)'>";
						}

						htmlContent += "<td class='fixed-column text-center'>";
						if (row.zt == 1) {
							htmlContent += "<i class='icon-edit color-blue cursor-pointer checkPermission' "
									+ "permissioncode='training:faculty:main:update' onClick=\"faculty_open.edit('"
									+ row.id
									+ "')\" title='修改 Edit'></i>&nbsp;&nbsp;";
							htmlContent += "<i class='fa glyphicon glyphicon-trash color-blue cursor-pointer checkPermission' permissioncode='training:faculty:main:delete' onClick=\"invalid('"
									+ row.id
									+ "')\" title='作废 Invalid '></i>&nbsp;&nbsp;";
						}
						htmlContent += "</td>";
						htmlContent += "<td class='text-left' title='"
								+ StringUtil
										.escapeStr(formatUndefine(row.rybh))
								+ "'>"
								+ StringUtil
										.escapeStr(formatUndefine(row.rybh))
								+ "</td>";
						htmlContent += "<td class='text-left' title='"
								+ StringUtil.escapeStr(formatUndefine(row.xm))
								+ "'>"
								+ StringUtil.escapeStr(formatUndefine(row.xm))
								+ "</td>";
						htmlContent += "<td class='text-center' title='"
								+ (row.wbbs == 1 ? '内部' : '外部') + "'>"
								+ (row.wbbs == 1 ? '内部' : '外部') + "</td>";
						htmlContent += "<td class='text-center' title='"
								+ (row.xb == 1 ? '男' : '女') + "'>"
								+ (row.xb == 1 ? '男' : '女') + "</td>";
						htmlContent += "<td class='text-left' title='"
								+ StringUtil
										.escapeStr(formatUndefine(row.lxdh))
								+ "'>"
								+ StringUtil
										.escapeStr(formatUndefine(row.lxdh))
								+ "</td>";
						htmlContent += "<td class='text-left' title='"
								+ StringUtil
										.escapeStr(formatUndefine(row.yxdz))
								+ "'>"
								+ StringUtil
										.escapeStr(formatUndefine(row.yxdz))
								+ "</td>";
						htmlContent += "<td class='text-left' title='"
								+ StringUtil.escapeStr(formatUndefine(row.jtcy))
								+ "'>"
								+ StringUtil.escapeStr(formatUndefine(row.jtcy))
								+ "</td>";
						htmlContent += "<td class='text-left' title='"+ (row.whr ? StringUtil.escapeStr(formatUndefine(row.whr.username)): '')+ " "+ (row.whr ? StringUtil.escapeStr(formatUndefine(row.whr.realname)): '')+ "'>"
								+ (row.whr ? StringUtil.escapeStr(formatUndefine(row.whr.username)): '') + " " + (row.whr ? StringUtil.escapeStr(formatUndefine(row.whr.realname)): '') + "</td>";
						htmlContent += "<td class='text-center' title='"
								+ row.whsj + "'>" + row.whsj + "</td>";
						htmlContent += "<td class='text-left' title='"
								+ (row.jg_dprt ? StringUtil
										.escapeStr(formatUndefine(row.jg_dprt.dprtname))
										: '')
								+ "'>"
								+ (row.jg_dprt ? StringUtil
										.escapeStr(formatUndefine(row.jg_dprt.dprtname))
										: '') + "</td>";
						htmlContent += "</tr>"
					});
				if(htmlContent ==''){
					htmlContent = "<tr><td class='text-center' colspan='11'>暂无数据 No data.</td></tr>";
				}
				$("#faculty_list").empty();
				$("#faculty_list").html(htmlContent);
				refreshPermission();
}
// 附件下载
function downloadfile(id) {
	// window.open(basePath + "/common/orderDownfile/" + id);
	PreViewUtil.handle(id, PreViewUtil.Table.D_011)
}
// 字段排序
function orderBy(obj) {
	var orderStyle = $("#" + obj + "_order").attr("class");
	$("th[id$=_order]").each(
			function() { // 重置class
				$(this).removeClass("sorting_desc").removeClass("sorting_asc")
						.addClass("sorting");
			});
	$("#" + obj + "_" + "order").removeClass("sorting");
	if (orderStyle.indexOf("sorting_asc") >= 0) {
		$("#" + obj + "_" + "order").addClass("sorting_desc");
	} else {
		$("#" + obj + "_" + "order").addClass("sorting_asc");
	}
	orderStyle = $("#" + obj + "_order").attr("class");
	var currentPage = $("#pagination li[class='active']").text();
	goPage(currentPage, obj, orderStyle.split("_")[1]);
}
function appendjxfw(list) {
	var htmlContent = "";
	if (list == null) {
		return htmlContent;
	}
	$.each(list, function(index, row) {
		if (index == 0) {
			htmlContent += row.fjjx
		} else {
			htmlContent += "," + row.fjjx
		}
	});
	return htmlContent;
}
// 搜索条件显示与隐藏
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
function showHiddenContent(obj) {
	faculty_bottom.cousrseId = $(obj).attr('id');
	$("#bottom_hidden_content").find('li').eq(0).click();
 	if($(".displayContent").css("display")=='block'){
 		$(".displayContent").slideUp(100);
 	}
 	$(".displayContent").slideDown(100);
 	
 	new Sticky({tableId:'scjkyj_table'}); //初始化表头浮动
 	
 	var isBottomShown = false;
	if($(".displayContent").is(":visible")){
		isBottomShown = true;
	}
 	TableUtil.showDisplayContent();
 	if($("#hideIcon").length==0){
 		$('<div class="pull-right" id="hideIcon" style="margin-right:15px;"><img src="'+basePath+'/static/images/down.png" onclick="hideBottom()" style="width:36px;cursor:pointer;"/></div>').insertAfter($("#pagination .fenye"));
	}
 	
	if(!isBottomShown){
		TableUtil.makeTargetRowVisible($(obj), $("#faculty_list"));
	}
 	 $(obj).addClass('bg_tr').siblings().removeClass('bg_tr');
}


function hideBottom(){
	$("#hideIcon").remove();
	TableUtil.hideDisplayContent();
}

function searchRevision() {
	goPage(1, "auto", "desc");
	hideBottom();
	$("th[id$=_order]").each(function() { // 重置class
		$(this).removeClass("sorting_desc").removeClass("sorting_asc").addClass("sorting");
	});
	initPlaneModelSearch();
	 TableUtil.resetTableSorting("main_table");
}

// 搜索条件重置
function searchreset() {
	var zzjgid = $('#dprtId').val();
	$("#divSearch").find("input").each(function() {
		$(this).attr("value", "");
	});

	$("#divSearch").find("textarea").each(function() {
		$(this).val("");
	});
	$("#divSearch").find("select").each(function() {
		$(this).val("");
	});
	$("#keyword_search").val("");
	$("#dprtcode").val(zzjgid);
	 $("input:checkbox[name='wbbs']").attr("checked",true);
	 $("input:checkbox[name='xb']").attr("checked",true);
	 
	 TableUtil.resetTableSorting("main_table");
}

function add() {
	$(".modal-footer").find('.glyphicon-info-sign').css("display",'none');
	$(".modal-footer").find('.alert-info-message').attr('title','').empty();
	faculty_open.add();
}

function modalEmpty() {
	faculty_open.init('');
	$("#basic_education_list").empty();
	$("#wbbs").val('');
	$("#xm").val('');
	$("#rybh").val('');
	$("#lxdh").val('');
	$("#yxdz").val('');
	$("#jtcy").val('');
	$(":radio[value='1']").attr('checked',true);
}

function addUser(obj) {
	var jx = $(obj).val();
	if ($(obj).attr("checked")) {
		$("#use" + jx).show();

	} else {
		var techoId = $("#teco" + jx).val();
		if (techoId != "add") {
			techoIds.push(techoId);
		}
		$("#use" + jx).hide();
	}
}

function openPersonelWin(){
	faculty_open.chooseUser();
}

function saveUpdate() {
	faculty_open.saveUpdate();
}

// 获取附件上传信息
function getOrderAttachment() {
	var scwj = {};
	var responses = uploadObj.responses;
	var len = uploadObj.responses.length;
	if (len != undefined && len > 0) {
		for (var i = len - 1; i < len; i++) {
			scwj.id = $("#downFileId").val();
			scwj.biaoshi = "edit";
			scwj.yswjm = responses[i].attachments[0].yswjm;
			scwj.wbwjm = responses[i].attachments[0].wbwjm;
			scwj.nbwjm = responses[i].attachments[0].nbwjm;
			scwj.cflj = responses[i].attachments[0].cflj;
			scwj.wjdx = responses[i].attachments[0].wjdx;
		}
	}
	return scwj;
}


function submit(obj, url) {
	AjaxUtil.ajax({
		url : url,
		type : "post",
		contentType : "application/json;charset=utf-8",
		data : JSON.stringify(obj),
		dataType : "json",
		success : function(data) {
			AlertUtil.showErrorMessage("操作成功");
			$("#AddalertModal").modal("hide");
			goPage(1, "auto", "desc");
		}
	});
}

function openList() {
	var zjh = $.trim($("#wczjh").val());
	var dprtcode = $.trim($("#dprtId").val());
	FixChapterModal.show({
		selected : zjh,// 原值，在弹窗中默认勾选
		dprtcode : dprtcode,
		callback : function(data) {// 回调函数
			var wczjh = '';
			var wczjhName = '';
			if (data != null) {
				wczjh = data.zjh;
				wczjhName = data.zwms;
				wczjhName = formatUndefine(wczjh) + " "
						+ formatUndefine(wczjhName);
			}
			$("#wczjh").val(formatUndefine(wczjh));
			$("#wczjhName").val(formatUndefine(wczjhName));
		}
	});
}

function openTech() {
	var dprtcode = $.trim($("#dprtId").val());
	var techId = $.trim($("#airworId").val());
	var NotId = $.trim($("#airworthinessId").val());
	TechnicalfileModal.show({
		selected : techId,// 原值，在弹窗中默认勾选
		dprtcode : dprtcode,
		id : NotId,
		callback : function(data) {// 回调函数
			var airworId = '';
			var gljswj = '关联技术文件';
			if (data != null) {
				airworId = data.id;
				gljswj = data.jswjbh;
			}
			$("#gljswj").html(formatUndefine(gljswj));
			$("#airworId").val(formatUndefine(airworId));
		}
	});
}

function syjxshowAndhide() {
	var userId = $("#userId").val();
	var Ispg = $("input:radio[name='Ispg']:checked").val();
	if (Ispg == 1) {
		$("#jxchecked").show();
		$('#jxchecked input:checkbox').each(function() {
			$(this).attr('checked', false);
		});
		$('#jxchecked select').each(function() {
			$(this).val(userId);
			$(this).hide();
		});
	} else {
		$("#jxchecked input[name='tecoid']").each(function() {
			if ($(this).val() != "add") {
				techoIds.push($(this).val());
				$(this).val("add");
			}
		});
		$("#jxchecked").hide();
	}

}

function edit(i) {
	$(".modal-footer").find('.glyphicon-info-sign').css("display",'none');
	$(".modal-footer").find('.alert-info-message').attr('title','').empty();
}

function openddownfile() {
	var fileId = $("#downFileId").val();
	downloadfile(fileId);
}
// 删除
function invalid(sid) {
	var data = {id:sid};
	AlertUtil.showConfirmMessage("确定要删除吗？", {
		callback : function() {
			AjaxUtil.ajax({
				url : basePath + "/training/faculty/delete",
				type : "post",
				async : false,
				contentType:"application/json;charset=utf-8",
				data : JSON.stringify(data),
				dataType : "json",
				success : function(data) {
					if (data.state == "success") {
						AlertUtil.showErrorMessage("删除成功!");
						searchRevision();
					} else {
						AlertUtil.showErrorMessage(data.message);
					}
				}
			});

		}
	});
}
// 关闭
function closeAirworthiness(id, jswjbh) {
	$("#gbid").val(id);
	$("#gbbh").val(jswjbh);
	$("#alertModalClose").modal("show");
	$("#gbrq").val("");
	$("#gbyy").val("");

}


// 关闭保存
function CloseOver() {
	// 参数组装
	var obj = {};
	obj.id = $.trim($("#gbid").val()); // 主键id
	obj.gbrq = $.trim($("#gbrq").val()); // 指定结束原因
	obj.gbyy = $.trim($("#gbyy").val()); // 指定结束原因
	AjaxUtil.ajax({
		url : basePath + "/project2/airworthiness/close",
		type : 'post', // 请求方式（post或get）默认为get
		data : JSON.stringify(obj), // 发送到服务器的数据
		contentType : "application/json;charset=utf-8", // 发送到服务器的数据内容编码类型
		dataType : 'json', // 预期服务器返回的数据类型
		success : function(data) { // 请求成功后调用的回调函数
			if (data.state == "success") {
				AlertUtil.showErrorMessage("关闭成功!");
				id = obj.id;
				goPage(1, "auto", "desc");
			} else {
				AlertUtil.showErrorMessage(data.message);
			}
		}
	});
}
// 关闭查看
function shutDown(obj) {
	var jswjbh = $(obj).attr("jswjbh");
	var gbr = $(obj).attr("gbr");
	var gbrq = $(obj).attr("gbrq");
	var gbyy = $(obj).attr("gbyy");

	$("#viewjswjbh").val(jswjbh);
	$("#viewgbr").val(gbr);
	$("#viewgbrq").val(gbrq);
	$("#viewgbyy").val(gbyy);
	$("#alertModalCloseView").modal("show");

}
// 隐藏Modal时验证销毁重构
// $("#"+alertModalAdd).on("hidden.bs.modal", function() {
// $("#form").data('bootstrapValidator').destroy();
// $('#form').data('bootstrapValidator', null);
// validation();
// });

/**
 * 选择头像模态框
 */
function showChoosePhoto() {
	$("#avatar-modal").modal("show");
}
function aa() {

}
(function(factory) {
	if (typeof define === 'function' && define.amd) {
		// AMD. Register as anonymous module.
		define([ 'jquery' ], factory);
	} else if (typeof exports === 'object') {
		// Node / CommonJS
		factory(require('jquery'));
	} else {
		// Browser globals.
		factory(jQuery);
	}
})
		(function($) {

			'use strict';

			var console = window.console || {
				log : function() {
				}
			};

			function CropAvatar($element) {
				this.$container = $element;

				this.$avatarView = this.$container.find('.avatar-view');
				this.$avatar = this.$avatarView.find('img');
				this.$avatarModal = this.$container.find('#avatar-modal');
				this.$loading = this.$container.find('.loading');

				this.$avatarForm = this.$avatarModal.find('.avatar-form');
				this.$avatarUpload = this.$avatarForm.find('.avatar-upload');
				this.$avatarSrc = this.$avatarForm.find('.avatar-src');
				this.$avatarData = this.$avatarForm.find('.avatar-data');
				this.$avatarInput = this.$avatarForm.find('.avatar-input');
				this.$avatarSave = this.$avatarForm.find('.avatar-save');
				this.$avatarBtns = this.$avatarForm.find('.avatar-btns');

				this.$avatarWrapper = this.$avatarModal.find('.avatar-wrapper');
				this.$avatarPreview = this.$avatarModal.find('.avatar-preview');

				this.init();
			}

			CropAvatar.prototype = {
				constructor : CropAvatar,

				support : {
					fileList : !!$('<input type="file">').prop('files'),
					blobURLs : !!window.URL && URL.createObjectURL,
					formData : !!window.FormData
				},

				init : function() {
					this.support.datauri = this.support.fileList
							&& this.support.blobURLs;

					if (!this.support.formData) {
						this.initIframe();
					}

					this.initTooltip();
					this.initModal();
					this.addListener();
				},

				addListener : function() {
					this.$avatarView.on('click', $.proxy(this.click, this));
					this.$avatarInput.on('change', $.proxy(this.change, this));
					this.$avatarForm.on('submit', $.proxy(this.submit, this));
					this.$avatarBtns.on('click', $.proxy(this.rotate, this));
				},

				initTooltip : function() {
					this.$avatarView.tooltip({
						placement : 'bottom'
					});
				},

				initModal : function() {
					this.$avatarModal.modal({
						show : false
					});
				},

				initPreview : function() {
					var url = this.$avatar.attr('src');

					this.$avatarPreview.html('<img src="' + url + '">');
				},

				initIframe : function() {
					var target = 'upload-iframe-' + (new Date()).getTime();
					var $iframe = $('<iframe>').attr({
						name : target,
						src : ''
					});
					var _this = this;

					// Ready ifrmae
					$iframe.one('load', function() {

						// respond response
						$iframe.on('load', function() {
							var data;

							try {
								data = $(this).contents().find('body').text();
							} catch (e) {
								console.log(e.message);
							}

							if (data) {
								try {
									data = $.parseJSON(data);
								} catch (e) {
									console.log(e.message);
								}

								_this.submitDone(data);
							} else {
								_this.submitFail('Image upload failed!');
							}

							_this.submitEnd();

						});
					});

					this.$iframe = $iframe;
					this.$avatarForm.attr('target', target).after(
							$iframe.hide());
				},

				click : function() {
					this.$avatarModal.modal('show');
//					this.initPreview();
				},

				change : function() {
					var files;
					var file;

					if (this.support.datauri) {
						files = this.$avatarInput.prop('files');

						if (files.length > 0) {
							file = files[0];

							if (this.isImageFile(file)) {
								if (this.url) {
									URL.revokeObjectURL(this.url); // Revoke
									// the old
									// one
								}

								this.url = URL.createObjectURL(file);
								this.startCropper();
							}
						}
					} else {
						file = this.$avatarInput.val();

						if (this.isImageFile(file)) {
							this.syncUpload();
						}
					}
				},

				submit : function() {
					if(!this.$avatarInput.val() && this.$avatarInput.val()==""){
						return false;
					}

					if (this.support.formData) {
						this.ajaxUpload();
						this.$avatar.attr('src', this.$avatarWrapper.find(
								"img.cropper-hidden").attr('src'));
						this.$avatarModal.modal('hide');
						return false;
					}
				},

				rotate : function(e) {
					var data;

					if (this.active) {
						data = $(e.target).data();

						if (data.method) {
							this.$img.cropper(data.method, data.option);
						}
					}
				},

				isImageFile : function(file) {
					if (file.type) {
						return /^image\/\w+$/.test(file.type);
					} else {
						return /\.(jpg|jpeg|png|gif)$/.test(file);
					}
				},

				startCropper : function() {
					var _this = this;

					if (this.active) {
						this.$img.cropper('replace', this.url);
					} else {
						this.$img = $('<img src="' + this.url + '">');
						this.$avatarWrapper.empty().html(this.$img);
						this.$img.cropper({
							aspectRatio : 2/3,
							preview : this.$avatarPreview.selector,
							crop : function(e) {
								var json = [ '{"x":' + e.x, '"y":' + e.y,
										'"height":' + e.height,
										'"width":' + e.width,
										'"rotate":' + e.rotate + '}' ].join();

								_this.$avatarData.val(json);
							}
						});

						this.active = true;
					}

					this.$avatarModal.on('hidden.bs.modal', function() {
						_this.$avatarPreview.empty();
						_this.$avatarInput.val("");
						_this.stopCropper();
					});
				},

				stopCropper : function() {
					if (this.active) {
						this.$img.cropper('destroy');
						this.$img.remove();
						this.active = false;
					}
				},

				ajaxUpload : function() {
					var _this = this;
					//获取裁剪后的画板转换成二进制传输
					var dataURL = this.$img.cropper("getCroppedCanvas");
					var imgurl = dataURL.toDataURL("image/jpeg",1); 
					var blob = dataURLToBlob(imgurl);
						var url = basePath + "/training/faculty/uploadPic";
						var data = new FormData();
						//获取文件后缀
						var input = _this.$avatarInput.val();
						//构造图片名
						var fileName = new Date().getTime()+".jpg";
						data.append('myfile',blob,fileName);
						var json = _this.$avatarData.val();
						data.append('json',json);
						$.ajax(url, {
							type : 'post',
							data : data,
							dataType : 'json',
							processData : false,
							contentType : false,
							
							beforeSend : function() {
								_this.submitStart();
							},
							
							success : function(data) {
								_this.submitDone(data);
							},
							
							error : function(XMLHttpRequest, textStatus,
									errorThrown) {
								_this.submitFail(textStatus || errorThrown);
							},
							
							complete : function() {
								_this.submitEnd();
							}
						});
				},

				syncUpload : function() {
					this.$avatarSave.click();
				},

				submitStart : function() {
					this.$loading.fadeIn();
				},

				submitDone : function(data) {

					if ($.isPlainObject(data) && data.state === 200) {
						if (data.row) {
							var result = data.row;
							$("#zpdzD").val(result.zpdzD);
							$("#zpdzX").val(result.zpdzX);
							var str = result.zpdzD.replaceAll(/\\/g,"&");
							var url = basePath +"/training/faculty/preview/"+str+"/1";
							this.url = url;

							if (this.support.datauri || this.uploaded) {
								this.uploaded = false;
								this.cropDone();
							} else {
								this.uploaded = true;
								this.$avatarSrc.val(this.url);
								this.startCropper();
							}

							this.$avatarInput.val('');
						} else if (data.message) {
							this.alert(data.message);
						}
					} else {
						this.alert('Failed to response');
					}
				},

				submitFail : function(msg) {
					this.alert(msg);
				},

				submitEnd : function() {
					this.$loading.fadeOut();
				},

				cropDone : function() {
					this.$avatarForm.get(0).reset();
					this.$avatar.attr('src', this.url);
					this.stopCropper();
					this.$avatarModal.modal('hide');
				},

				alert : function(msg) {
					var $alert = [
							'<div class="alert alert-danger avatar-alert alert-dismissable">',
							'<button type="button" class="close" data-dismiss="alert">&times;</button>',
							msg, '</div>' ].join('');

					this.$avatarUpload.after($alert);
				}
			};

			$(function() {
				return new CropAvatar($('#basic_div'));
			});

		});

function customResizeHeight(bodyHeight, tableHeight){
	var table_pain = $(".table_pagination").outerHeight()||0;
	var search =  $(".searchContent").outerHeight()||0;
	var myTab = $("#myTab").outerHeight()||0;
	var bottomHeight =  bodyHeight-table_pain-search-myTab-10;
	$("#myTabContent").attr('style','height:'+bottomHeight+'px;overflow: auto;')
}

function dataURLToBlob(dataurl){
	var arr = dataurl.split(',');
	var mime = arr[0].match(/:(.*?);/)[1];
	var bstr = atob(arr[1]);
	var n = bstr.length;
	var u8arr = new Uint8Array(n);
	while(n--){
		u8arr[n] = bstr.charCodeAt(n);
	}
	return new Blob([u8arr], {type:mime});
}