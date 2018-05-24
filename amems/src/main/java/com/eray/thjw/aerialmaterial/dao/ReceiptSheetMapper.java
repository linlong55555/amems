package com.eray.thjw.aerialmaterial.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.BorrowApply;
import com.eray.thjw.aerialmaterial.po.Contract;
import com.eray.thjw.aerialmaterial.po.Expatriate;
import com.eray.thjw.aerialmaterial.po.ExpatriateDetail;
import com.eray.thjw.aerialmaterial.po.GodownEntry;
import com.eray.thjw.aerialmaterial.po.GodownEntryDetail;
import com.eray.thjw.aerialmaterial.po.Inspection;
import com.eray.thjw.aerialmaterial.po.ReceiptSheet;
import com.eray.thjw.po.BaseEntity;

public interface ReceiptSheetMapper {
    int deleteByPrimaryKey(String id);

    int insert(ReceiptSheet record);

    int insertSelective(ReceiptSheet record);

    ReceiptSheet selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReceiptSheet record);

    int updateByPrimaryKey(ReceiptSheet record);
    
    /**
     * 收货分页查询
     * @param record
     * @return
     */
    List<ReceiptSheet> queryPage(ReceiptSheet record);
    
    /**
     * 采购部件分页查询
     * @param record
     * @return
     */
    List<Map<String, Object>> queryPurchasePage(BaseEntity baseEntity);
    
    /**
     * 送修部件分页查询
     * @param record
     * @return
     */
    List<Map<String, Object>> queryRepairPage(BaseEntity baseEntity);
    
    /**
     * 借入申请部件分页查询
     * @param record
     * @return
     */
    List<Map<String, Object>> queryBorrowPage(BaseEntity baseEntity);
    
    /**
     * 根据id查询收货数据
     * @param id
     * @return
     */
    ReceiptSheet queryById(String id);
    
    /**
     * 更新合同到货数量
     * @param paramMap
     * @return
     */
    int updateContractDhsl(Map<String, Object> paramMap);
    
    /**
     * 更新合同到货数量状态
     * @param paramMap
     * @return
     */
    int updateContractDhslZt(Map<String, Object> paramMap);
    
    /**
     * 检查合同到货数量是否超出
     * @param paramMap
     * @return
     */
    Contract checkContractDhsl(Map<String, Object> paramMap);
    
    /**
     * 根据序列号找到外派清单数据
     * @param paramMap
     * @return
     */
    Expatriate findExpatriateByXlh(Map<String, Object> paramMap);
    
   /**
    * 根据件号找到外派清单数据集合
    * @param paramMap
    * @return
    */
   List<Expatriate> findExpatriateByJh(Map<String, Object> paramMap);
   
   /**
    * 根据收货单找到外派清单数据集合
    * @param paramMap
    * @return
    */
   List<ExpatriateDetail> findExpatriateDetailByDjid(Map<String, Object> paramMap);
    
    /**
     * 更新外派清单归还数量
     * @param paramMap
     * @return
     */
    int updateExpatriateGhsl(Map<String, Object> paramMap);
    
    /**
     * 新增外派清单详细
     * @param paramMap
     * @return
     */
    int insertExpatriateDetail(Map<String, Object> paramMap);
    
    /**
     * 更新借入申请数量
     * @param paramMap
     * @return
     */
    int updateBorrowApplySl(Map<String, Object> paramMap);
    
    /**
     * 检查借入申请到货数量是否超出
     * @param paramMap
     * @return
     */
    BorrowApply checkBorrowApplySl(Map<String, Object> paramMap);
    
    /**
     * 新增外派清单
     * @param paramMap
     * @return
     */
    int insertExpatriate(Map<String, Object> paramMap);
    
    /**
     * 删除外派清单
     * @param paramMap
     * @return
     */
    int deleteExpatriate(Map<String, Object> paramMap);
    
    /**
     * 删除外派清单详细
     * @param paramMap
     * @return
     */
    int deleteExpatriateDetail(Map<String, Object> paramMap);
    
    /**
     * 找到外派清单详细数据
     * @param paramMap
     * @return
     */
    ExpatriateDetail findExpatriateDetail(Map<String, Object> paramMap);
    
    /**
     * 撤销质检单
     * @param ins
     * @return
     */
    int revokeInspection(Inspection ins);
    
    /**
     * 撤销入库单
     * @param gd
     * @return
     */
    int revokeGodownEntry(GodownEntry gd);
    
    /**
     * 撤销入库单详细
     * @param gdDetail
     * @return
     */
    int revokeGodownEntryDetail(GodownEntryDetail gdDetail);
    
    /**
     * 找到不完整的收货单-库存数据
     * @param record
     * @return
     */
    int findIncompleteStockCount(ReceiptSheet record);
    
    /**
     * 找到已经入库的数量
     * @param record
     * @return
     */
    int findInstockCount(ReceiptSheet record);

    int updateContractRksl(Map<String, Object> paramMap);

	void updateBorrowApplyrkSl(Map<String, Object> paramMap);
}