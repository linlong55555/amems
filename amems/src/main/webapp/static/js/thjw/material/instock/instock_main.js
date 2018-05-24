$(document).ready(function() {

	Navigation(menuCode);
	
	if(tabId == 'purchase'){
		$("#tab_purchase").tab('show');
		loadPurchase();
	}else if(tabId == 'borrow'){
		$("#tab_borrow").tab('show');
		loadBorrow();
	}else if(tabId == 'repair'){
		$("#tab_repair").tab('show');
		loadRepair();
	}else if(tabId == 'lendReturn'){
		$("#tab_lendReturn").tab('show');
		loadLendReturn();
	}else if(tabId == 'handwork'){
		$("#tab_handwork").tab('show');
	}else if(tabId == 'history'){
		$("#tab_history").tab('show');
		loadInstockHistory();
	}else{
		id = "";
		pageParam = "";
		loadPurchase();// 加载采购数据
	}
	
	// 初始化
	$('.date-picker').datepicker({
		autoclose : true
	}).next().on("click", function() {
		$(this).prev().focus();
	});

});


var purchaseFlag = false;
function loadPurchase(){
	if(!purchaseFlag){
		purchase.init();
		purchaseFlag = true;
	}
}


var lendReturnFlag = false;

/**
 * 加载借出归还数据
 * @returns
 */
function loadLendReturn(){
	if(!lendReturnFlag){
		lend_return.init();
		lendReturnFlag = true;
	}
}

var repairFlag = false;

/**
 * 加载借出归还数据
 * @returns
 */
function loadRepair(){
	if(!repairFlag){
		repair.init();
		repairFlag = true;
	}
}

var borrowFlag = false;

/**
 * 加载借入数据
 * @returns
 */
function loadBorrow(){
	if(!borrowFlag){
		borrow.init();
		borrowFlag = true;
	}
}

var instockHisFlag = false;

/**
 * 加载入库历史数据
 * @returns
 */
function loadInstockHistory(){
	if(!instockHisFlag){
		instock_history.init();
		instockHisFlag = true;
	}
}