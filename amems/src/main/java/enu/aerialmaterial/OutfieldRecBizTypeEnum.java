package enu.aerialmaterial;

/**
 * 外场库存历史记录 依据类型
 * @author xu.yong
 *
 */
public enum OutfieldRecBizTypeEnum {

	/** 出库 */
	TYPE1(1,"出库","{0}{1}，出库单：{2}，相关单据：{3}，出库数量：{4,number, #.00}"),
	/** 入库 */
	TYPE2(2,"入库","{0}{1}，入库单：{2}，相关单据：{3}，入库数量：{4,number, #.00}"),
	/** 报废 */
	TYPE3(3,"报废","报废{0}：{1}，报废数量：{2,number, #.00}"),
	/** 拆解	{0}:"撤销"/"" {1}:飞行记录单号	 {2}:任务类型（中文）    {3}:任务编号   {4}:工单号   {5}:拆解数量 */
	TYPE4(4,"拆解","拆解{0}：{1}，{2}：{3}，工单：{4}，拆解数量 ：{5,number, #.00}"),
	/** 装上	{0}:"撤销"/"" {1}:飞行记录单号	 {2}:任务类型（中文）    {3}:任务编号   {4}:工单号   {5}:装上数量 */
	TYPE5(5,"装上","装上{0}：{1}，{2}：{3}，工单：{4}，装上数量 ：{5,number, #.00}"),
	TYPE7(7,"库存信息修改", "库存信息修改"),
	TYPE8(8,"销毁", "销毁"),
	/** 收货 */
	TYPE9(9,"收货", "{0}{1}，收货单：{2}，相关单据：{3}，收货数量：{4,number, #.00}");
	
	private Integer id;
	private String name;
	private String text;
	
	private OutfieldRecBizTypeEnum(Integer id, String name, String text) {
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
