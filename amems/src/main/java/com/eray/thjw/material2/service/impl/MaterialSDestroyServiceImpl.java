package com.eray.thjw.material2.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.MaterialHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.StockFreezeHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.StockMapper;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.po.StockFreezeHistory;
import com.eray.thjw.aerialmaterial.service.MaterialRecService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.dao.DestroyMapper;
import com.eray.thjw.material2.dao.ScrappedApplyMapper;
import com.eray.thjw.material2.dao.ScrappedInfoMapper;
import com.eray.thjw.material2.dao.StockHistoryMapper;
import com.eray.thjw.material2.po.Destroy;
import com.eray.thjw.material2.po.ScrappedApply;
import com.eray.thjw.material2.po.ScrappedInfo;
import com.eray.thjw.material2.po.StockHistory;
import com.eray.thjw.material2.service.MaterialSDestroyService;
import com.eray.thjw.po.User;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.UpdateTypeEnum;
import enu.aerialmaterial.StockRecBizTypeEnum;
import enu.material2.DestroyStatusEnum;
import enu.material2.StockHistorySubtypeEnum;
import enu.material2.StockHistoryTypeEnum;

/**
 * @Description 销毁service实现类
 * @CreateTime 2018年3月21日 上午9:52:03
 * @CreateBy 韩武
 */
@Service("materialSDestroyService")
public class MaterialSDestroyServiceImpl implements MaterialSDestroyService {

	@Resource
	private DestroyMapper destroyMapper;
	@Resource
	private ScrappedInfoMapper scrappedInfoMapper;
	@Resource
	private StockMapper stockMapper;
	@Resource
	private MaterialRecService materialRecService;
	@Resource
	private ScrappedApplyMapper scrappedApplyMapper;
	@Resource
	private MaterialHistoryMapper materialHistoryMapper;
	@Resource
	private StockHistoryMapper stockHistoryMapper;
	@Resource
	private StockFreezeHistoryMapper stockFreezeHistoryMapper;
	@Resource
	private StockSerivce stockSerivce;

	@Override
	public Map<String, Object> getToDestroyList(Destroy record) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = record.getId();
		record.setId("");
		try {
			PageHelper.startPage(record.getPagination());
			List<Destroy> recordList = destroyMapper.getAllPageList(record);
			if (((Page) recordList).getTotal() > 0) {
				// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if (StringUtils.isNotBlank(id)) {
					// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					if (!PageUtil.hasRecord(recordList, id)) {
						// 在查询条件中增加ID
						Destroy newRecord = new Destroy();
						newRecord.setId(id);
						List<Destroy> newRecordList = destroyMapper.getAllPageList(newRecord);
						if (newRecordList != null && newRecordList.size() == 1) {
							recordList.add(0, newRecordList.get(0));
						}
					}
				}
				resultMap = PageUtil.pack4PageHelper(recordList, record.getPagination());
				return resultMap;

			} else {
				List<Destroy> newRecordList = new ArrayList<Destroy>();
				if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					// 在查询条件中增加ID
					Destroy newRecord = new Destroy();
					newRecord.setId(id);
					newRecordList = destroyMapper.getAllPageList(newRecord);
					if (newRecordList != null && newRecordList.size() == 1) {
						resultMap = PageUtil.pack(1, newRecordList, record.getPagination());
						return resultMap;
					}
				}
				resultMap = PageUtil.pack(0, newRecordList, record.getPagination());
				return resultMap;
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败", e);
		}
	}

	/**
	 * 
	 * @Description 销毁
	 * @CreateTime 2018年3月27日 下午5:30:49
	 * @CreateBy 岳彬彬
	 * @param idList
	 */
	@Override
	public void update4DestroyRecrod(List<String> idList) throws BusinessException {
		if (null != idList && idList.size() > 0) {
			// 验证不存在与b_h2_021 销毁（销毁状态=1）的数据，报废明细id关联
			doValidation4Destroy(idList);
			//处理逻辑
			doValidationBfsl(idList);
		} else {
			throw new BusinessException("该选中数据中后再进行报废操作!");
		}

	}

	/**
	 * 
	 * @Description 验证不存在与b_h2_021 销毁（销毁状态=1）的数据，报废明细id关联
	 * @CreateTime 2018年3月27日 下午5:33:52
	 * @CreateBy 岳彬彬
	 * @param idList
	 * @throws BusinessException
	 */
	private void doValidation4Destroy(List<String> idList) throws BusinessException {
		int count = destroyMapper.getCount4ValidDestroy(idList);
		if (count > 0) {
			throw new BusinessException("该选中数据中存在已经报废的数据，请刷新页面后重新操作!");
		}
	}

	/**
	 * 
	 * @Description   处理逻辑
	 * @CreateTime 2018年3月27日 下午6:07:33
	 * @CreateBy 岳彬彬
	 * @param idList
	 * @throws BusinessException
	 */
	private void doValidationBfsl(List<String> idList) throws BusinessException {
		// 为确保数据正确，不被同时操作，采用单条循环查询操作数据
		ScrappedInfo info;// 报废明细
		Stock stock;// 库存
		ScrappedApply record;// 报废单
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();//当前用户
		for (String id : idList) {
			info = scrappedInfoMapper.selectByPrimaryKey(id);
			stock = stockMapper.selectByPrimaryKey(info.getKcid());
			if (stock.getKcsl().compareTo(info.getKcsl()) == -1) {// 库存数量小于报废数量
				throw new BusinessException(stock.getBjh() + "报废数量大于库存数量!");
			}
			record = scrappedApplyMapper.selectByPrimaryKey(info.getMainid());
			if (stock.getKcsl().compareTo(info.getKcsl()) == 0) {// 库存数量等于报废数量
				doStockAndHistory(info,stock,record,czls,user);//处理表数据
				// 删除库存
				stockMapper.delete(stock);
			}
			if (stock.getKcsl().compareTo(info.getKcsl()) == 1) {// 库存数量大于报废数量
				doStockAndHistory(info,stock,record,czls,user);//处理表数据
				// 扣除库存
				Stock newStock = new Stock();
				newStock.setId(stock.getId());
				newStock.setKcsl(stock.getKcsl().subtract(info.getKcsl()));
				newStock.setWhrid(user.getId());
				stockMapper.update4Destroy(newStock);
			}
		}
	}
	/**
	 * 
	 * @Description 处理冻结履历、库存履历信息、库存履历主信息、报废明细、销毁
	 * @CreateTime 2018年3月28日 下午4:05:20
	 * @CreateBy 岳彬彬
	 * @param info
	 * @param stock
	 * @param record
	 * @param czls
	 * @param user
	 */
	private void doStockAndHistory(ScrappedInfo info,Stock stock,ScrappedApply record,String czls,User user){
		//删除B_H_023冻结履历
		stockFreezeHistoryMapper.deleteYwid(info.getId());;//删除冻结履历数据
		// 记录b_h_001_rec
		materialRecService.writeStockRec(stock.getId(), czls, info.getMainid(), record.getBfdh(),
				StockRecBizTypeEnum.TYPE6, UpdateTypeEnum.UPDATE, "", record.getBfdh(), info.getKcsl());

		// 写入b_h_017部件 库存履历信息
		MaterialHistory materialHistory = new MaterialHistory(stock);// 履历表实体
		materialHistory.setKcsl(info.getKcsl());// 库存数量为报废数量
		String materialHistoryuuid = UUID.randomUUID().toString();// 库存履历id
		materialHistory.setId(materialHistoryuuid);
		materialHistoryMapper.insertSelective(materialHistory);

		// 写入b_h_025 库存履历主信息
		StockHistory stockHistory = new StockHistory();
		stockHistory.setId(materialHistory.getId());
		stockHistory.setDprtcode(materialHistory.getDprtcode());
		stockHistory.setCzsj(new Date());
		stockHistory.setCzrbmid(user.getBmdm());
		stockHistory.setCzrid(user.getId());
		stockHistory.setCzr(user.getDisplayName());
		stockHistory.setCzlx(StockHistoryTypeEnum.STOCK_DESTROY.getId());
		stockHistory.setCzzlx(StockHistorySubtypeEnum.STOCK_DESTROY_OFF_SHELF.getId());
		stockHistory.setCzsm("销毁-下架");
		stockHistory.setKcid(materialHistory.getKcid());
		stockHistory.setYwid(record.getId());
		stockHistory.setYwbh(record.getBfdh());
		stockHistory.setYwmxid(info.getId());
		stockHistoryMapper.insertSelective(stockHistory);
		
		//更新b_h2_00901报废明细库存履历id
		ScrappedInfo newInfo = new ScrappedInfo();
		newInfo.setId(info.getId());
		newInfo.setKcllid(materialHistoryuuid);
		scrappedInfoMapper.updateByPrimaryKeySelective(newInfo);
		//新增或更新b_h2_021销毁表数据
		Destroy destroy = destroyMapper.selectByBfmxid(info.getId());//查询该数据是否已存在与销毁表中
		if(null == destroy){
			destroy = new Destroy();
			destroy.setId(UUID.randomUUID().toString());
			destroy.setWhrid(user.getId());
			destroy.setWhbmid(user.getBmdm());
			destroy.setWhsj(new Date());
			destroy.setDprtcode(record.getId());
			destroy.setXhrid(user.getId());
			destroy.setXhbmid(user.getBmdm());
			destroy.setXhsj(new Date());
			destroy.setZt(DestroyStatusEnum.Destroy.getId());
			destroy.setBfmxid(info.getId());
			destroyMapper.insertSelective(destroy);
		}else{
			destroy.setWhrid(user.getId());
			destroy.setWhbmid(user.getBmdm());
			destroy.setWhsj(new Date());
			destroy.setXhrid(user.getId());
			destroy.setXhbmid(user.getBmdm());
			destroy.setXhsj(new Date());
			destroy.setZt(DestroyStatusEnum.Destroy.getId());
			destroy.setBfmxid(info.getId());
			destroyMapper.updateByPrimaryKeySelective(destroy);
		}
		
	}
	/**
	 * 
	 * @Description 已销毁列表
	 * @CreateTime 2018年3月28日 下午2:07:57
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> getDestroyList(Destroy record) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = record.getId();
		record.setId("");
		try {
			PageHelper.startPage(record.getPagination());
			List<Destroy> recordList = destroyMapper.getAllDestroyList(record);
			if (((Page) recordList).getTotal() > 0) {
				// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if (StringUtils.isNotBlank(id)) {
					// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					if (!PageUtil.hasRecord(recordList, id)) {
						// 在查询条件中增加ID
						Destroy newRecord = new Destroy();
						newRecord.setId(id);
						List<Destroy> newRecordList = destroyMapper.getAllDestroyList(newRecord);
						if (newRecordList != null && newRecordList.size() == 1) {
							recordList.add(0, newRecordList.get(0));
						}
					}
				}
				resultMap = PageUtil.pack4PageHelper(recordList, record.getPagination());
				return resultMap;

			} else {
				List<Destroy> newRecordList = new ArrayList<Destroy>();
				if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					// 在查询条件中增加ID
					Destroy newRecord = new Destroy();
					newRecord.setId(id);
					newRecordList = destroyMapper.getAllDestroyList(newRecord);
					if (newRecordList != null && newRecordList.size() == 1) {
						resultMap = PageUtil.pack(1, newRecordList, record.getPagination());
						return resultMap;
					}
				}
				resultMap = PageUtil.pack(0, newRecordList, record.getPagination());
				return resultMap;
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败", e);
		}
	}
	/**
	 * 
	 * @Description 撤销
	 * @CreateTime 2018年3月28日 下午3:12:19
	 * @CreateBy 岳彬彬
	 * @param list  报废明细id集合
	 * @throws BusinessException
	 */
	@Override
	public void update4RevokeRecrod(List<String> list) throws BusinessException {
		// 验证至少选择一条数据
		if (null != list && list.size() > 0) {
			// 验证不存在与b_h2_021 销毁（销毁状态=11）的数据，报废明细id关联
			doValidation4Revoke(list);
			//撤销操作
			doDetail(list);
		} else {
			throw new BusinessException("该选中数据中后再进行报废操作!");
		}
	}
	/**
	 * 
	 * @Description 验证不存在与b_h2_021 销毁（销毁状态=11）的数据，报废明细id关联
	 * @CreateTime 2018年3月28日 下午3:15:47
	 * @CreateBy 岳彬彬
	 * @param list
	 * @throws BusinessException
	 */
	private void doValidation4Revoke(List<String> list) throws BusinessException{
		int count = destroyMapper.getCount4ValidRevoke(list);
		if (count > 0) {
			throw new BusinessException("该选中数据中存在已经撤销的数据，请刷新页面后重新操作!");
		}
	}
	/**
	 * 
	 * @Description 撤销操作
	 * @CreateTime 2018年3月28日 下午3:19:40
	 * @CreateBy 岳彬彬
	 * @param list
	 * @throws BusinessException
	 */
	private void doDetail(List<String> list)throws BusinessException{
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		ScrappedInfo info;//报废明细
		ScrappedApply record ;//报废主表
		StockFreezeHistory stockFreeze;//冻结履历
		StockHistory stockHistory;//b_h_025 库存履历主信息
		MaterialHistory  materialHistory;//部件履历数据
		MaterialHistory newMaterialHistory;//新增部件履历数据
		Destroy destroy;//销毁实体
		for (String id : list) {
			info = scrappedInfoMapper.selectByPrimaryKey(id);
			record = scrappedApplyMapper.selectByPrimaryKey(info.getMainid());
			//根据b_h2_021.报废明细id修改b_h2_021状态=11撤销
			destroy = new Destroy();
			destroy.setWhrid(user.getId());
			destroy.setWhbmid(user.getBmdm());
			destroy.setWhsj(new Date());
			destroy.setZt(DestroyStatusEnum.Revoke.getId());
			destroy.setBfmxid(id);
			destroyMapper.update4Revoke(destroy);
			
			//还原冻结履历
			stockFreeze = new StockFreezeHistory();
			String stockFreezeId = UUID.randomUUID().toString();
			stockFreeze.setId(stockFreezeId);
			stockFreeze.setKcid(info.getKcid());
			stockFreeze.setDjsl(info.getKcsl());
			stockFreeze.setKclx(1);
			stockFreeze.setYwlx(9);
			stockFreeze.setYwid(info.getId());
			stockFreeze.setYwbh(record.getBfdh());
			stockFreeze.setCzsj(new Date());
			stockFreezeHistoryMapper.insertSelective(stockFreeze);
			
			//还原库存信息 --根据b_h2_00901.库存履历id查找库存履历表来还原
			materialHistory = materialHistoryMapper.selectById(info.getKcllid());//获取部件履历数据
			Stock stock = stockMapper.selectByPrimaryKey(materialHistory.getKcid());
			if(null == stock){//库存不存在
				stock = new Stock(materialHistory);
				stock.setWhrid(user.getId());
				// 验证装机清单+库存+外场不存在
				stockSerivce.getCount4ValidationBjAndXlh(stock.getId(),stock.getBjh(), stock.getSn(), stock.getDprtcode(), 12);
				stockMapper.insertSelective(stock);//如果不存在则按照履历表的数据来新增库存，库存id与履历表库存id相同
			}else{//如果库存id存在与库存表则只累加库存数量
				Stock newStock = new Stock();
				newStock.setKcsl(stock.getKcsl().add(materialHistory.getKcsl()));
				newStock.setId(stock.getId());
				newStock.setWhrid(user.getId());
				stockMapper.update4Destroy(newStock);
			}
			
			//记录b_h_001_rec
			materialRecService.writeStockRec(stock.getId(), czls, info.getMainid(), record.getBfdh(),
					StockRecBizTypeEnum.TYPE6, UpdateTypeEnum.UPDATE, "", record.getBfdh(), info.getKcsl());
			
			//写入b_h_017 部件库存履历
			newMaterialHistory = new MaterialHistory(stock);// 履历表实体
			newMaterialHistory.setKcsl(info.getKcsl());// 库存数量为报废数量
			String materialHistoryuuid = UUID.randomUUID().toString();// 库存履历id
			newMaterialHistory.setId(materialHistoryuuid);
			materialHistoryMapper.insertSelective(newMaterialHistory);
			
			//更新b_h2_00901报废明细库存履历id
			ScrappedInfo newInfo = new ScrappedInfo();
			newInfo.setId(info.getId());
			newInfo.setKcllid(materialHistoryuuid);
			scrappedInfoMapper.updateByPrimaryKeySelective(newInfo);
			
			//写入b_h_025 库存履历主信息
			stockHistory = new StockHistory();
			stockHistory.setId(materialHistoryuuid);
			stockHistory.setDprtcode(materialHistory.getDprtcode());
			stockHistory.setCzsj(new Date());
			stockHistory.setCzrbmid(user.getBmdm());
			stockHistory.setCzrid(user.getId());
			stockHistory.setCzr(user.getDisplayName());
			stockHistory.setCzlx(StockHistoryTypeEnum.STOCK_DESTROY.getId());
			stockHistory.setCzzlx(StockHistorySubtypeEnum.STOCK_DESTROY_CANCEL.getId());
			stockHistory.setCzsm("销毁-撤销");
			stockHistory.setKcid(materialHistory.getKcid());
			stockHistory.setYwid(record.getId());
			stockHistory.setYwbh(record.getBfdh());
			stockHistory.setYwmxid(info.getId());
			stockHistoryMapper.insertSelective(stockHistory);
		}
		
	}
}