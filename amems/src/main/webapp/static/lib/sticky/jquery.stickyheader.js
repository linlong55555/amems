var Sticky = function(params){
	_Sticky.init(params.tableId);
	if($.inArray(params.tableId,_stickTables) == -1){
		_stickTables.push(params.tableId);
	}
}

var _stickTables = [];

$(window).resize($.debounce(250, function () {
	if(_stickTables.length > 0){
		$.each(_stickTables, function(tableId){
			var $t = $("#"+tableId);
			var $stickyHead  = $t.siblings('.sticky-thead');
			var $stickyCol   = $t.siblings('.sticky-col');
			var $stickyInsct = $t.siblings('.sticky-intersect');
			var $stickyWrap  = $t.parent();
			_Sticky.setWidths($t, $stickyHead, $stickyCol, $stickyInsct);
			_Sticky.repositionStickyHead($stickyWrap, $stickyHead, $stickyCol);
			_Sticky.repositionStickyCol($stickyWrap, $stickyCol, $stickyCol);
		});
	}
}));

var _Sticky = {
	init: function(tableId){
		var this_ = this;
		var $t = null,
			$thead = null,
			$col = null;
		var $stickyHead  = null,
			$stickyCol   = null,
			$stickyInsct = null,
			$stickyWrap  = null;//table父对象
		if(tableId == ""){
			return;
		}
		$t = $("#"+tableId);
		if($t.find('thead').length == 0 || $t.find('th').length == 0){
			return;
		}
		
		var $thead = $t.find('thead').clone();
		var $col   = $t.find('thead, tbody').clone();
		
		$stickyHead  = $t.siblings('.sticky-thead'),
		$stickyCol   = $t.siblings('.sticky-col'),
		$stickyInsct = $t.siblings('.sticky-intersect'),
		$stickyWrap  = $t.parent();
		
		var tableClass = $t.attr("class");
		
		if($stickyHead.length == 0){
			$stickyHead = $("<table class=\"sticky-thead "+tableClass+"\"/>");
			$stickyHead.append($thead);
			$t.after($stickyHead);
		}else{
			$stickyHead.html($thead);
		}
		
		if($t.find('.fixed-column').length > 0) {
			if($stickyCol.length == 0){
				$stickyCol = $('<table class="sticky-col '+tableClass+'" />');
				$stickyCol.empty();
				$t.after($stickyCol);
			}else{
				$stickyCol.empty();
			}
			if($stickyInsct.length == 0){
				$stickyInsct = $('<table class="sticky-intersect '+tableClass+'" />');
				$stickyInsct.empty();
				$t.after($stickyInsct);
			}else{
				$stickyInsct.empty();
			}
		}
		$stickyCol.append($col).find('thead th:not(.fixed-column)').remove().end().find('tbody td:not(.fixed-column)').remove();
		
		$stickyInsct.append($t.find('thead').clone()).find('thead th:not(.fixed-column)').remove();

		this_.setWidths($t, $stickyHead, $stickyCol, $stickyInsct);
		//
		if($stickyWrap.scrollLeft() > 0){
			$stickyCol.css({ "z-index": "50" });
			$stickyInsct.css({ "z-index": "150" });
		}else{
			$stickyCol.css({ "z-index": "-1" });
			$stickyInsct.css({ "z-index": "-1" });	
		}
		
		//2017-10-25新增 z-index值
		$stickyWrap.scroll($.throttle(250, function() {
			this_.repositionStickyHead($stickyWrap, $stickyHead, $stickyInsct);
			this_.repositionStickyCol($stickyWrap, $stickyCol, $stickyInsct);
		}));
	},
	setWidths: function ($t, $stickyHead, $stickyCol, $stickyInsct) {
		var $stickyHead_th = $stickyHead.find('th:visible');
		var $stickyCol_th_tr = $stickyCol.find('thead tr:visible');
		var $stickyCol_tb_tr = $stickyCol.find('tbody tr:visible');
		var $stickyInsct_th_tr = $stickyInsct.find('thead tr:visible');
		
		$t.find('thead th:visible').each(function (i) {
			$stickyHead_th.eq(i).width($(this).width());
		});
		
		$t.find('thead tr:visible').each(function (i) {
			$stickyCol_th_tr.eq(i).height($(this).height());
			$stickyInsct_th_tr.eq(i).height($(this).height());
		});
		if($stickyCol_tb_tr.find(".fixed-column").length > 0){
			var $t_tb_tr = $t.find('tbody tr:visible');
			for(var i = 0; i < $t_tb_tr.length; i++){
				$stickyCol_tb_tr.eq(i).height($($t_tb_tr[i]).height());
			}
		}
		$stickyHead.width($t.width());
		$stickyHead.css("min-width", $t.outerWidth());
		
		var colWidth = 0;
		var $stickyCol_td = $stickyCol.find('td:visible');
		var $stickyInsct_th = $stickyInsct.find('th:visible');
		$t.find('thead th.fixed-column:visible').each(function(i){
			$stickyCol_td.eq(i).width($(this).width());
			$stickyInsct_th.eq(i).width($(this).width());
			colWidth += $(this).outerWidth();
		});
		$stickyCol.width(colWidth);
		$stickyInsct.width(colWidth);
	},
	repositionStickyHead:function ($stickyWrap, $stickyHead, $stickyInsct) {
		if($stickyWrap.scrollTop() > 0) {
			$stickyHead.add($stickyInsct).css({
				opacity: 1,
				top:$stickyWrap.scrollTop()
			});
		} else {
			$stickyHead.add($stickyInsct).css({
				opacity: 0,
				top: 0
			});
		}
	},
	repositionStickyCol: function ($stickyWrap, $stickyCol, $stickyInsct) {
		if($stickyWrap.scrollLeft() > 0) {
			//
			$stickyCol.css({ "z-index": "50" });
			$stickyInsct.css({ "z-index": "150" });
			//2017-10-25新增 z-index值
			$stickyCol.add($stickyInsct).css({
				opacity: 1,
				left: $stickyWrap.scrollLeft()
			});
		} else {
			//
			$stickyCol.css({ "z-index": "-1" });
			$stickyInsct.css({ "z-index": "-1" });
			//2017-10-25新增 z-index值
			$stickyCol
			.css({ opacity: 0 })
			.add($stickyInsct).css({ left: 0 });
		}
	}
}

