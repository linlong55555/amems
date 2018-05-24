package enu.material2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 库存履历主信息-操作子类型
 * @CreateTime 2018年3月19日 上午10:17:30
 * @CreateBy 韩武
 */
public enum MaterialHistorySubtypeEnum {
	
	STOCK_ZS_PURCHASE(1, "装上"),
	STOCK_CX_PURCHASE(2, "拆下"),
	STOCK_RECEIPT_PURCHASE(11, "收货-采购"),
	STOCK_RECEIPT_REPAIR(12, "收货-修理"),
	STOCK_RECEIPT_RENT_IN(13, "收货-租进"),
	STOCK_RECEIPT_RENT_OUT(14, "收货-租出"),
	STOCK_RECEIPT_EXCHANGE(15, "收货-交换"),
	STOCK_RECEIPT_RETURN_MATERIAL(16, "收货-退料"),
	STOCK_RECEIPT_OTHER(17, "收货-其他"),
	STOCK_RECEIPT_ON_SHELF(18, "收货-入库上架"),
	STOCK_RECEIPT_CANCEL(19, "收货-撤销"),
	STOCK_OUT_REPAIR(21, "出库-修理"),
	STOCK_OUT_RENT_IN(22, "出库-租进"),
	STOCK_OUT_RENT_OUT(23, "出库-租出"),
	STOCK_OUT_EXCHANGE(24, "出库-交换"),
	STOCK_OUT_SELL(25, "出库-外销"),
	STOCK_OUT_ISSUE(26, "出库-发料"),
	STOCK_OUT_PURCHASE(27, "出库-采购"),
	STOCK_OUT_OTHER(28, "出库-其他"),
	STOCK_OUT_CANCEL(29, "出库-撤销"),
	STOCK_TRANSFERN(30, "移库"),
	STOCK_TRANSFER_IN(31, "移库-移入"),
	STOCK_TRANSFER_OUT(32, "移库-移出"),
	STOCK_COUNT_INVENTORY_LOSSES(41, "盘点-盘亏"),
	STOCK_COUNT_INVENTORY_PROFIT(42, "盘点-盘盈"),
	STOCK_DESTROY_OFF_SHELF(51, "销毁-下架"),
	STOCK_DESTROY_CANCEL(59, "销毁-撤销"),
	;
	
	
	private Integer id;
    private String name;
    
    private MaterialHistorySubtypeEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (MaterialHistorySubtypeEnum c : MaterialHistorySubtypeEnum.values()) {
            if (c.getId().intValue() == id.intValue()) {
                return c.name;
            }
        }
        return "";
    }
    
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	 /**
     * 枚举转listmap
     * @return
     */
    public static List<Map<String, Object>> enumToListMap() {
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	MaterialHistorySubtypeEnum[] enums = MaterialHistorySubtypeEnum.values();
    	
    	for (MaterialHistorySubtypeEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	 Collections.sort(list, new Comparator<Map<String, Object>>() {
    	        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
    	            return o1.get("name").toString().compareTo(o2.get("name").toString()) ;
    	        }
    	    });
    	
    	return list;
	}
    
    /**
     * @Description 根据收货类型枚举找到库存履历主数据枚举  ReceiptTypeEnum--->StockHistorySubtypeEnum
     * @CreateTime 2018年3月19日 上午10:38:04
     * @CreateBy 韩武
     * @param parentId
     * @return
     */
    public static Integer getIdByReceiptType(Integer shlx, Integer lymxlx) {
    	if(lymxlx == null){
    		return STOCK_RECEIPT_OTHER.getId();
    	}
    	if(ReceiptTypeEnum.PURCHASE.getId().equals(shlx)){
    		return STOCK_RECEIPT_PURCHASE.getId();
    	}else if(ReceiptTypeEnum.REPAIR.getId().equals(shlx)){
    		return STOCK_RECEIPT_REPAIR.getId();
    	}else if(ReceiptTypeEnum.RENT_IN.getId().equals(shlx)){
    		return STOCK_RECEIPT_RENT_IN.getId();
    	}else if(ReceiptTypeEnum.RENT_OUT.getId().equals(shlx)){
    		return STOCK_RECEIPT_RENT_OUT.getId();
    	}else if(ReceiptTypeEnum.EXCHANGE.getId().equals(shlx)){
    		return STOCK_RECEIPT_EXCHANGE.getId();
    	}else if(ReceiptTypeEnum.RETURN_MATERIAL.getId().equals(shlx)){
    		return STOCK_RECEIPT_RETURN_MATERIAL.getId();
    	}else if(ReceiptTypeEnum.OTHER.getId().equals(shlx)){
    		return STOCK_RECEIPT_OTHER.getId();
    	}
		return null;
	}
     
}
