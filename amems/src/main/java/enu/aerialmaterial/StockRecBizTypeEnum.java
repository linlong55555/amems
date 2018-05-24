package enu.aerialmaterial;

/**
 * 库存历史记录 依据类型
 * @author xu.yong
 *
 */
public enum StockRecBizTypeEnum {

	/** 出库 */
	TYPE1(1,"出库", "{0}{1}，出库单：{2}，相关单据：{3}，出库数量：{4,number, #.00}"),
	/** 入库 */
	TYPE2(2,"入库", "{0}{1}，入库单：{2}，相关单据：{3}，入库数量：{4,number, #.00}"),
	/** 移库 */
	TYPE3(3,"移库", "移库{0}：{1}，原仓库库位：{2}（{3}）{4}，移库数量：{5,number, #.00}"),
	/** 盘点*/
	TYPE4(4,"盘点", "盘点{0}：{1}，盈亏数量：{2,number, #.00}"),
	/** 检验 */
	TYPE5(5,"检验", "检验{0}：{1}"),
	/** 报废 */
	TYPE6(6,"报废", "报废{0}：{1}，报废数量：{2,number, #.00}"),
	TYPE7(7,"库存信息修改", "库存信息修改"),
	/** 收货 */
	TYPE8(8,"收货", "{0}{1}，收货单：{2}，相关单据：{3}，收货数量：{4,number, #.00}");
	
	private Integer id;
	private String name;
	//修改依据描述
	private String text;
	
	private StockRecBizTypeEnum(Integer id, String name, String text) {
		this.id = id;
		this.name = name;
		this.text = text;
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
