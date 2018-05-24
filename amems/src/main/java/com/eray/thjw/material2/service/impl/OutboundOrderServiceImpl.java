	package com.eray.thjw.material2.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.framework.exception.SaibongException;
import com.eray.framework.saibong.SNGeneratorFactory;
import com.eray.thjw.aerialmaterial.dao.MaterialHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.StockMapper;
import com.eray.thjw.aerialmaterial.dao.StoreInnerSearchMapper;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.OutFieldStock;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.MaterialRecService;
import com.eray.thjw.aerialmaterial.service.OutFieldStockService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.dao.OutboundOrderDetailsMapper;
import com.eray.thjw.material2.dao.OutboundOrderMapper;
import com.eray.thjw.material2.dao.OutboundRelationsMapper;
import com.eray.thjw.material2.po.OutboundOrder;
import com.eray.thjw.material2.po.OutboundOrderDetails;
import com.eray.thjw.material2.po.OutboundRelations;
import com.eray.thjw.material2.po.StockHistory;
import com.eray.thjw.material2.service.OutboundOrderService;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.DistributionDepartment;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.aerialmaterial.OutStockTypeEnum;
import enu.aerialmaterial.OutfieldRecBizTypeEnum;
import enu.aerialmaterial.StockHistoryOperationEnum;
import enu.aerialmaterial.StockRecBizTypeEnum;
import enu.common.HasOrNoEnum;
import enu.material2.OutboundOrderStatusEnum;
import enu.material2.OutboundTypeEnum;
import enu.material2.StockHistorySubtypeEnum;
/**
 * 
 * @Description 出库单impl
 * @CreateTime 2018年3月15日 下午3:26:17
 * @CreateBy 林龙
 */
@Service("outboundOrderService")
public class OutboundOrderServiceImpl implements OutboundOrderService  {

	@Resource
	private OutboundOrderMapper outboundOrderMapper;
	@Resource
	private CommonService commonService;
	@Resource
	private StockSerivce stockSerivce;
	@Resource
	private MaterialHistoryMapper materialHistoryMapper;
	@Resource
	private OutboundOrderDetailsMapper outboundOrderDetailsMapper;
	@Resource
	private OutboundRelationsMapper outboundRelationsMapper;
	@Resource
	private MaterialRecService materialRecService;
	@Resource
	private StoreInnerSearchMapper storeInnerSearchMapper;
	@Resource
	private OutFieldStockService outFieldStockService;
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private StockMapper stockMapper;

	/**
	 * 
	 * @Description 保存出库单
	 * @CreateTime 2018年3月15日 下午3:21:28
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String save(OutboundOrder outboundOrder) throws BusinessException {
		String czls = UUID.randomUUID().toString();//流水号
		String uuid = UUID.randomUUID().toString();//uuid-主单id
		
		outboundOrder.setZt(OutboundOrderStatusEnum.SVAE.getId());//状态为保存
		//新增出库单
		this.insertSelective(outboundOrder,uuid,czls);
		
		//新增库存履历-新增出库单明细-新增出库关系 （出库类型!=其他）
		insertSelectiveMaterialHistory(outboundOrder,uuid);
		
		return uuid;
	}
	
	/**
	 * 
	 * @Description 新增库存履历-新增出库单明细-新增出库关系 （出库类型!=其他）
	 * @CreateTime 2018年3月15日 下午5:08:08
	 * @CreateBy 林龙
	 * @param outboundOrder.getStocklist()
	 */
	private void insertSelectiveMaterialHistory(OutboundOrder outboundOrder,String id) {
		
		for (OutboundOrderDetails outboundOrderDetails : outboundOrder.getOutboundOrderDetailslist()) {
			MaterialHistory materialHistory=new MaterialHistory(stockSerivce.queryById(outboundOrderDetails.getKcid()));//查询数据库
			
			String materialHistoryuuid = UUID.randomUUID().toString();//库存履历id
			materialHistory.setKcsl(outboundOrderDetails.getCksl());//库存履历数量为出库数量
			materialHistory.setId(materialHistoryuuid);
			 
			//新增库存履历
			materialHistoryMapper.insertSelective(materialHistory);
			
			String outboundOrderDetailsuuid = UUID.randomUUID().toString();//出库明细id
			outboundOrderDetails.setId(outboundOrderDetailsuuid);
			outboundOrderDetails.setMainid(id);
			outboundOrderDetails.setKcllid(materialHistoryuuid);
			//新增出库单明细
			outboundOrderDetailsMapper.insertSelective(outboundOrderDetails);
			
			if(outboundOrder.getShlx() != OutboundTypeEnum.OTHER.getId() && outboundOrder.getLyid() != null){//出库类型!=其他
				
				OutboundRelations outboundRelations=new OutboundRelations();
				String outboundRelationsuuid = UUID.randomUUID().toString();//出库关系表id
				outboundRelations.setId(outboundRelationsuuid);
				outboundRelations.setDprtcode(outboundOrder.getDprtcode());
				outboundRelations.setZt(HasOrNoEnum.HAS.getId());
				if(outboundOrder.getShlx()==OutboundTypeEnum.ISSUE.getId()){
					outboundRelations.setLylx(1);//来源类型为需求
				}else{
					outboundRelations.setLylx(2);//来源类型为合同
				}
				outboundRelations.setLyid(outboundOrder.getLyid());
				if(outboundOrderDetails.getParamsMap().get("mxid") !=null){
					
					outboundRelations.setLymxid(outboundOrderDetails.getParamsMap().get("mxid").toString());
				}
				outboundRelations.setCkdid(outboundOrder.getId());
				outboundRelations.setCkmxid(outboundOrderDetailsuuid);
				outboundRelations.setCksl(outboundOrderDetails.getCksl());
				outboundRelations.setDw(materialHistory.getJldw()); //计量单位
				//新增出库关系
				outboundRelationsMapper.insertSelective(outboundRelations);
			}
		}
	}

	/**
	 * 
	 * @Description 新增
	 * @CreateTime 2018年3月15日 下午3:30:57
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @param uuid
	 * @param czls
	 */
	private void insertSelective(OutboundOrder outboundOrder, String uuid,String czls) throws BusinessException{
		User user = ThreadVarUtil.getUser();//当前登陆人
		Date currentDate = commonService.getSysdate();//当前时间
		
		//当页面没有填写单编号时
		if(outboundOrder.getShdh() == null || "".equals(outboundOrder.getShdh())){ 
			String shdh=this.setDh(outboundOrder,user); //根据对象获取最新编号
			outboundOrder.setShdh(shdh);;
		}else{
			//验证验证单编号+版本 唯一
			this.validationTechnical(outboundOrder);
		}
		outboundOrder.setId(uuid);
		outboundOrder.setDprtcode(user.getJgdm());
		outboundOrder.setWhbmid(user.getBmdm());
		outboundOrder.setWhrid(user.getId());
		outboundOrder.setWhsj(currentDate);
		outboundOrder.setShbmid(user.getBmdm());
		outboundOrder.setShrid(user.getId());
		
		outboundOrderMapper.insertSelective(outboundOrder);
	}

	/**
	 * 
	 * @Description  根据单号验证唯一
	 * @CreateTime 2017年8月17日 下午5:17:37
	 * @CreateBy 林龙
	 */
	private void validationTechnical(OutboundOrder outboundOrder) throws BusinessException {
		
		int  iNum = outboundOrderMapper.getCount4Validation(outboundOrder);
		if (iNum > 0) {
			throw new BusinessException("该出库单号已存在!");
		}
	}

	/**
	 * 
	 * @param user 
	 * @Description  根据对象获取最新编号
	 * @CreateTime 2017年8月17日 下午4:32:44
	 * @CreateBy 林龙
	 * @param technical
	 * @return pgdbhNew 编号
	 */
	private String setDh(OutboundOrder outboundOrder, User user) throws BusinessException{
		OutboundOrder tec = new OutboundOrder(); //new 对象
		boolean b = true;
		String shdh = null;
		while(b){
			try {
				shdh = SNGeneratorFactory.generate(user.getJgdm(), SaiBongEnum.HC_CKD.getName(),outboundOrder); 
			} catch (SaibongException e) {
				throw new BusinessException(e);
			}
			tec.setShdh(shdh);
			tec.setDprtcode(user.getJgdm());
			//根据对象查询数量
			int i = outboundOrderMapper.queryCount(tec); 
			if(i <= 0){
				b = false;
			}
		}
		return shdh;
	}
	/**
	 * 
	 * @Description 修改出库单
	 * @CreateTime 2018年3月15日 下午3:21:53
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String update(OutboundOrder outboundOrder) throws BusinessException {
		User user = ThreadVarUtil.getUser();		//当前登陆人
		String czls = UUID.randomUUID().toString();//流水号
		
		//修改出库单
		this.updateSelective(outboundOrder,czls,user);
		
		//新增修改库存履历-新增修改出库单明细-新增修改出库关系 （出库类型!=其他）
		insertupdateSelectiveMaterialHistory(outboundOrder,user);
		
		
		return outboundOrder.getId();
	}
	
	
	
	/**
	 * 
	 * @Description 新增修改库存履历-新增修改出库单明细-新增修改出库关系 （出库类型!=其他）
	 * @CreateTime 2018年3月16日 下午5:11:22
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @param user 
	 * @param id
	 */
	private void insertupdateSelectiveMaterialHistory(OutboundOrder outboundOrder, User user) {
		List<OutboundOrderDetails> outboundOrderDetailsList = null;//相关集合,用于新增
		Map<String, String> idMap = new HashMap<String, String>();//记录页面传入相关id集合
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
		List<String> kcllidList = new ArrayList<String>();//id集合,用于批量插入日志
		//根据业务mainid获取数据库已经存在的信息
		List<OutboundOrderDetails> oldList = outboundOrderDetailsMapper.getDepartmentByYwid(outboundOrder.getId()); //查询已存在的库存履历
		
		//相关map集合,key:id,value:DistributionDepartment
		Map<String, OutboundOrderDetails> distributionDepartmentMap = new HashMap<String, OutboundOrderDetails>();
		//将数据库已存在的数据放入referenceMap
		for (OutboundOrderDetails outboundOrderDetails : oldList) {
			distributionDepartmentMap.put(outboundOrderDetails.getKcllid(), outboundOrderDetails);
		}
		if(null != outboundOrder.getOutboundOrderDetailslist() && outboundOrder.getOutboundOrderDetailslist().size() > 0){
			outboundOrderDetailsList = new ArrayList<OutboundOrderDetails>();
			for (OutboundOrderDetails outboundOrderDetails : outboundOrder.getOutboundOrderDetailslist()) {
				
				//判断相关参考文件id是否为空,是否存在于数据库,不为空且存在:修改信息,反之:新增信息
				if(null != outboundOrderDetails.getId() && null != distributionDepartmentMap.get(outboundOrderDetails.getKcllid())){
					
					idMap.put(outboundOrderDetails.getId(), outboundOrderDetails.getId());
					
					MaterialHistory materialHistory=new MaterialHistory();
					materialHistory.setId(outboundOrderDetails.getKcllid());
					materialHistory.setKcsl(outboundOrderDetails.getCksl());
					//修改库存履历
					materialHistoryMapper.updateByPrimaryKeySelective(materialHistory);
					
					//修改出库明细-不用修改
					
					//修改出库关系
					if(outboundOrder.getShlx() != OutboundTypeEnum.OTHER.getId() && outboundOrder.getLyid() != null){//出库类型!=其他
						
						OutboundRelations outboundRelations=new OutboundRelations();
						outboundRelations.setCksl(outboundOrderDetails.getCksl());
						outboundRelations.setCkmxid(outboundOrderDetails.getId());
						outboundRelations.setCkdid(outboundOrder.getId());
						//根据出库单id和出库明细id修改出库关系
						outboundRelationsMapper.updateByPrimaryKeySelective(outboundRelations);
					}
					
					
				}else{
					outboundOrderDetailsList.add(outboundOrderDetails);
				}
			}
			outboundOrder.setOutboundOrderDetailslist(outboundOrderDetailsList);
			//新增库存履历-新增出库单明细-新增出库关系 （出库类型!=其他）
			insertSelectiveMaterialHistory(outboundOrder,outboundOrder.getId());
		}
		for (OutboundOrderDetails outboundOrderDetails : oldList){
			//如果数据库相关id不在页面,那么删除
			if(null == idMap.get(outboundOrderDetails.getId())){
				columnValueList.add(outboundOrderDetails.getId());
				kcllidList.add(outboundOrderDetails.getKcllid());
			}
		}
		if(columnValueList.size() > 0){
			//批量删除
			outboundRelationsMapper.delete4Batch(columnValueList); //删除出库关系表
			
			outboundOrderDetailsMapper.delete4Batch(columnValueList); //删除出库明细
			
			materialHistoryMapper.delete4Batch(kcllidList);//删除库存履历表
		}		
	}
	/**
	 * 
	 * @Description 修改出库单
	 * @CreateTime 2018年3月16日 下午5:08:02
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @param czls
	 * @param user 
	 */
	private void updateSelective(OutboundOrder outboundOrder, String czls, User user) throws BusinessException{
		Date currentDate = commonService.getSysdate();//当前时间
		
		outboundOrder.setWhsj(currentDate);
		outboundOrderMapper.updateByPrimaryKeySelective(outboundOrder);
	}
	/**
	 * 
	 * @Description 新增提交出库单
	 * @CreateTime 2018年3月15日 下午3:22:53
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String saveSubmit(OutboundOrder outboundOrder)throws BusinessException {
		String czls = UUID.randomUUID().toString();//流水号
		String uuid = UUID.randomUUID().toString();//uuid-主单id
		
		outboundOrder.setZt(OutboundOrderStatusEnum.SUBMIT.getId());//状态为保存
		//新增出库单
		this.insertSelective(outboundOrder,uuid,czls);
		
		//新增提交库存履历-新增出库单明细-新增出库关系 （出库类型!=其他）
		insertSubimtSelectiveMaterialHistory(outboundOrder,uuid);
		
		return uuid;
	}
	
	
	
	/**
	 * 
	 * @Description 新增提交库存履历-新增出库单明细-新增出库关系 （出库类型!=其他）
	 * @CreateTime 2018年3月15日 下午5:08:08
	 * @CreateBy 林龙
	 * @param outboundOrder.getStocklist()
	 */
	private void insertSubimtSelectiveMaterialHistory(OutboundOrder outboundOrder,String id)throws BusinessException {
		User user = ThreadVarUtil.getUser();		//当前登陆人
		Date currentDate = commonService.getSysdate();//当前时间
		for (OutboundOrderDetails outboundOrderDetails : outboundOrder.getOutboundOrderDetailslist()) {
			MaterialHistory materialHistory=new MaterialHistory(stockSerivce.queryById(outboundOrderDetails.getKcid()));//查询数据库
			
			String materialHistoryuuid = UUID.randomUUID().toString();//库存履历id
			materialHistory.setKcsl(outboundOrderDetails.getCksl());//库存履历数量为出库数量
			materialHistory.setId(materialHistoryuuid);
			//新增库存履历
			materialHistoryMapper.insertSelective(materialHistory);
			
			String outboundOrderDetailsuuid = UUID.randomUUID().toString();//出库明细id
			outboundOrderDetails.setId(outboundOrderDetailsuuid);
			outboundOrderDetails.setMainid(id);
			outboundOrderDetails.setKcllid(materialHistoryuuid);
			//新增出库单明细
			outboundOrderDetailsMapper.insertSelective(outboundOrderDetails);
			
			if(outboundOrder.getShlx() != OutboundTypeEnum.OTHER.getId() && outboundOrder.getLyid() != null){//出库类型!=其他
				
				OutboundRelations outboundRelations=new OutboundRelations();
				String outboundRelationsuuid = UUID.randomUUID().toString();//出库关系表id
				outboundRelations.setId(outboundRelationsuuid);
				outboundRelations.setDprtcode(outboundOrder.getDprtcode());
				outboundRelations.setZt(HasOrNoEnum.HAS.getId());
				if(outboundOrder.getShlx()==OutboundTypeEnum.ISSUE.getId()){
					outboundRelations.setLylx(1);//来源类型为需求
				}else{
					outboundRelations.setLylx(2);//来源类型为合同
				}
				outboundRelations.setLyid(outboundOrder.getLyid());
				if(outboundOrderDetails.getParamsMap().get("mxid") != null){
					
					outboundRelations.setLymxid(outboundOrderDetails.getParamsMap().get("mxid").toString());
				}
				outboundRelations.setCkdid(outboundOrder.getId());
				outboundRelations.setCkmxid(outboundOrderDetailsuuid);
				outboundRelations.setCksl(outboundOrderDetails.getCksl());
				outboundRelations.setDw(materialHistory.getJldw()); //计量单位
				//新增出库关系
				outboundRelationsMapper.insertSelective(outboundRelations);
			}
			
			UUID czls = UUID.randomUUID();//获取随机的uuid
			//操作库存
			Stock stock=stockMapper.queryById(outboundOrderDetails.getKcid());
			//写入b_h_025  //库存履历
			StockHistory  history=new StockHistory();
			history.setId(materialHistoryuuid);
			history.setDprtcode(user.getJgdm());
			history.setCzrbmid(user.getBmdm());
			history.setCzrid(user.getId());
			history.setCzr(user.getRealname());
			history.setCzlx(StockHistoryOperationEnum.ck.getId());
			history.setCzzlx(OutboundTypeEnum.getName(outboundOrder.getShlx()));
//			history.setCzsm(StockHistorySubtypeEnum.getName(OutboundTypeEnum.getName(outboundOrder.getShlx())));
			history.setCzsm(StockHistoryOperationEnum.ck.getName());
			history.setKcid(stock.getId());
			history.setYwbh(outboundOrder.getShdh());
			history.setYwid(outboundOrder.getId());
			history.setYwmxid(outboundOrderDetails.getId());
			storeInnerSearchMapper.saveStoreHistory(history);
			
			//出库类型=发料 生成外场库存
			if(outboundOrder.getShlx() == OutboundTypeEnum.ISSUE.getId()){
				OutFieldStock outFieldStock=new OutFieldStock(stock);
				String outFieldStockId =  UUID.randomUUID().toString();
				outFieldStock.setId(outFieldStockId);
				outFieldStock.setSjly(1);
				outFieldStock.setKcsl(outboundOrderDetails.getCksl());
				//新增外场库存
				outFieldStock.setWhrid(user.getId());
				outFieldStock.setWhsj(currentDate);
				outFieldStockService.insert(outFieldStock);
//				commonRecService.write(outFieldStockId, TableEnum.B_H_003, user.getId(), UpdateTypeEnum.SAVE, outFieldStockId);

				MaterialHistory materialHistoryoutFieldStock=new MaterialHistory(stockSerivce.queryById(outboundOrderDetails.getKcid()));//查询数据库
				String outFieldStockmaterialHistoryuuid = UUID.randomUUID().toString();//库存履历id
				materialHistoryoutFieldStock.setKcsl(outboundOrderDetails.getCksl());//库存履历数量为出库数量
				materialHistoryoutFieldStock.setId(outFieldStockmaterialHistoryuuid);
				materialHistoryoutFieldStock.setKcid(outFieldStockId); //库存id为外场库存id
				//新增库存履历
				materialHistoryMapper.insertSelective(materialHistoryoutFieldStock);
				
				//新增出库单明细
				OutboundOrderDetails outboundOrderDetailsoutFieldStock=new OutboundOrderDetails();
				outboundOrderDetailsoutFieldStock.setId(outboundOrderDetailsuuid);
				outboundOrderDetailsoutFieldStock.setWckcllid(outFieldStockmaterialHistoryuuid);
				outboundOrderDetailsMapper.updateByPrimaryKeySelective(outboundOrderDetailsoutFieldStock);
			}
			

			//减去库存数，当减去库存后库存为0则删除该库存数据 -rec-验证库存
			BigDecimal kcsl =stock.getKcsl();
			BigDecimal aDouble =new BigDecimal(0.00);
			 if(kcsl.compareTo(outboundOrderDetails.getCksl())==-1){
				 throw new BusinessException("出库数量不能大于库存数量!");
			 }else if(kcsl.compareTo(outboundOrderDetails.getCksl())==0 ){
				 if(stock.getDjsl() != null){
					 if(stock.getDjsl().compareTo (aDouble)==0){
						 //当库存为0时删除
						 stockSerivce.delete(stock);
						 materialRecService.writeStockRec(stock.getId(), czls.toString(), id, outboundOrder.getShdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.DELETE,OutStockTypeEnum.TYPE6.getName(),"",outboundOrder.getShdh(),outboundOrder.getShdh(),outboundOrderDetails.getCksl());
						 
					 }
				 }else{
					 //当库存为0时删除
					 stockSerivce.delete(stock);
					 materialRecService.writeStockRec(stock.getId(), czls.toString(), id, outboundOrder.getShdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.DELETE,OutStockTypeEnum.TYPE6.getName(),"",outboundOrder.getShdh(),outboundOrder.getShdh(),outboundOrderDetails.getCksl());
				 }
			 }else{
				 //库存数减去出库数
				 BigDecimal kucuns = kcsl.subtract(outboundOrderDetails.getCksl());   	
				 stock.setKcsl(kucuns);
				 stock.setWhrid(user.getId());
				 stock.setWhsj(currentDate);
				 stockSerivce.updateByPrimaryKeySelective(stock);
				 materialRecService.writeStockRec(stock.getId(), czls.toString(), id, outboundOrder.getShdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.UPDATE,OutStockTypeEnum.TYPE6.getName(),"",outboundOrder.getShdh(),outboundOrder.getShdh(),outboundOrderDetails.getCksl());
			 }
		}
	}

	/**
	 * 
	 * @Description 修改提交出库单
	 * @CreateTime 2018年3月15日 下午3:23:43
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String updateSubmit(OutboundOrder outboundOrder)throws BusinessException {
		User user = ThreadVarUtil.getUser();		//当前登陆人
		String czls = UUID.randomUUID().toString();//流水号
		
		//修改出库单
		outboundOrder.setZt(OutboundOrderStatusEnum.SUBMIT.getId());//状态为保存
		this.updateSelective(outboundOrder,czls,user);
		
		//新增新增库存履历-新增修改出库单明细-新增修改出库关系 （出库类型!=其他）提交
		insertupdateSubSelectiveMaterialHistory(outboundOrder,user);
		
		return outboundOrder.getId();
	}
	
	/**
	 * 
	 * @Description 新增修改库存履历-新增修改出库单明细-新增修改出库关系 （出库类型!=其他）提交
	 * @CreateTime 2018年3月16日 下午5:11:22
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @param user 
	 * @param id
	 * @throws BusinessException 
	 */
	private void insertupdateSubSelectiveMaterialHistory(OutboundOrder outboundOrder, User user) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间
		List<OutboundOrderDetails> outboundOrderDetailsList = null;//相关集合,用于新增
		Map<String, String> idMap = new HashMap<String, String>();//记录页面传入相关id集合
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
		List<String> kcllidList = new ArrayList<String>();//id集合,用于批量插入日志
		//根据业务mainid获取数据库已经存在的信息
		List<OutboundOrderDetails> oldList = outboundOrderDetailsMapper.getDepartmentByYwid(outboundOrder.getId()); //查询已存在的库存履历
		
		//相关map集合,key:id,value:DistributionDepartment
		Map<String, OutboundOrderDetails> distributionDepartmentMap = new HashMap<String, OutboundOrderDetails>();
		//将数据库已存在的数据放入referenceMap
		for (OutboundOrderDetails outboundOrderDetails : oldList) {
			distributionDepartmentMap.put(outboundOrderDetails.getKcllid(), outboundOrderDetails);
		}
		if(null != outboundOrder.getOutboundOrderDetailslist() && outboundOrder.getOutboundOrderDetailslist().size() > 0){
			outboundOrderDetailsList = new ArrayList<OutboundOrderDetails>();
			for (OutboundOrderDetails outboundOrderDetails : outboundOrder.getOutboundOrderDetailslist()) {
				
				//判断相关参考文件id是否为空,是否存在于数据库,不为空且存在:修改信息,反之:新增信息
				if(null != outboundOrderDetails.getId() && null != distributionDepartmentMap.get(outboundOrderDetails.getKcllid())){
					
					idMap.put(outboundOrderDetails.getId(), outboundOrderDetails.getId());
					
					MaterialHistory materialHistory=new MaterialHistory();
					materialHistory.setId(outboundOrderDetails.getKcllid());
					materialHistory.setKcsl(outboundOrderDetails.getCksl());
					//修改库存履历
					materialHistoryMapper.updateByPrimaryKeySelective(materialHistory);
					
					//修改出库明细-不用修改
					
					//修改出库关系
					if(outboundOrder.getShlx() != OutboundTypeEnum.OTHER.getId() && outboundOrder.getLyid() != null){//出库类型!=其他
						
						OutboundRelations outboundRelations=new OutboundRelations();
						outboundRelations.setCksl(outboundOrderDetails.getCksl());
						outboundRelations.setCkmxid(outboundOrderDetails.getId());
						outboundRelations.setCkdid(outboundOrder.getId());
						//根据出库单id和出库明细id修改出库关系
						outboundRelationsMapper.updateByPrimaryKeySelective(outboundRelations);
					}
					
					UUID czls = UUID.randomUUID();//获取随机的uuid
					//操作库存
					Stock stock=stockMapper.queryById(outboundOrderDetails.getKcid());
			
					 
					//写入b_h_025  //库存履历
					StockHistory  history=new StockHistory();
					history.setId(outboundOrderDetails.getKcllid());
					history.setDprtcode(user.getJgdm());
					history.setCzrbmid(user.getBmdm());
					history.setCzrid(user.getId());
					history.setCzr(user.getRealname());
					history.setCzlx(StockHistoryOperationEnum.ck.getId());
					history.setCzzlx(OutboundTypeEnum.getName(outboundOrder.getShlx()));
//					history.setCzsm(StockHistorySubtypeEnum.getName(OutboundTypeEnum.getName(outboundOrder.getShlx())));
					history.setCzsm(StockHistoryOperationEnum.ck.getName());
					history.setKcid(stock.getId());
					history.setYwbh(outboundOrder.getShdh());
					history.setYwid(outboundOrder.getId());
					history.setYwmxid(outboundOrderDetails.getId());
					storeInnerSearchMapper.saveStoreHistory(history);
					
					//出库类型=发料 生成外场库存
					if(outboundOrder.getShlx() == OutboundTypeEnum.ISSUE.getId()){
						OutFieldStock outFieldStock=new OutFieldStock(stock);
						String outFieldStockId =  UUID.randomUUID().toString();
						outFieldStock.setId(outFieldStockId);
						outFieldStock.setSjly(1);
						outFieldStock.setKcsl(outboundOrderDetails.getCksl());
						//新增外场库存
						outFieldStock.setWhrid(user.getId());
						outFieldStock.setWhsj(currentDate);
						outFieldStockService.insert(outFieldStock);
//						commonRecService.write(outFieldStockId, TableEnum.B_H_003, user.getId(), UpdateTypeEnum.SAVE, outFieldStockId);

						MaterialHistory materialHistoryoutFieldStock=new MaterialHistory(stockSerivce.queryById(outboundOrderDetails.getKcid()));//查询数据库
						String outFieldStockmaterialHistoryuuid = UUID.randomUUID().toString();//库存履历id
						materialHistoryoutFieldStock.setKcsl(outboundOrderDetails.getCksl());//库存履历数量为出库数量
						materialHistoryoutFieldStock.setId(outFieldStockmaterialHistoryuuid);
						materialHistoryoutFieldStock.setKcid(outFieldStockId); //库存id为外场库存id
						//新增库存履历
						materialHistoryMapper.insertSelective(materialHistoryoutFieldStock);
						
						//新增出库单明细
						OutboundOrderDetails outboundOrderDetailsoutFieldStock=new OutboundOrderDetails();
						outboundOrderDetailsoutFieldStock.setId(outboundOrderDetails.getId());
						outboundOrderDetailsoutFieldStock.setWckcllid(outFieldStockmaterialHistoryuuid);
						outboundOrderDetailsMapper.updateByPrimaryKeySelective(outboundOrderDetailsoutFieldStock);
					}
					
					//减去库存数，当减去库存后库存为0则删除该库存数据 -rec-验证库存
					BigDecimal kcsl =stock.getKcsl();
					BigDecimal aDouble =new BigDecimal(0.00);
					 if(kcsl.compareTo(outboundOrderDetails.getCksl())==-1){
						 throw new BusinessException("出库数量不能大于库存数量!");
					 }else if(kcsl.compareTo(outboundOrderDetails.getCksl())==0 ){
						 if(stock.getDjsl() != null){
							 if(stock.getDjsl().compareTo (aDouble)==0){
								 //当库存为0时删除
								 stockSerivce.delete(stock);
								 materialRecService.writeStockRec(stock.getId(), czls.toString(), outboundOrder.getId(), outboundOrder.getShdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.DELETE,OutStockTypeEnum.TYPE6.getName(),"",outboundOrder.getShdh(),outboundOrder.getShdh(),outboundOrderDetails.getCksl());
								 
							 }
						 }else{
							 //当库存为0时删除
							 stockSerivce.delete(stock);
							 materialRecService.writeStockRec(stock.getId(), czls.toString(), outboundOrder.getId(), outboundOrder.getShdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.DELETE,OutStockTypeEnum.TYPE6.getName(),"",outboundOrder.getShdh(),outboundOrder.getShdh(),outboundOrderDetails.getCksl());
						 }
					 }else{
						 //库存数减去出库数
						 BigDecimal kucuns = kcsl.subtract(outboundOrderDetails.getCksl());   	
						 stock.setKcsl(kucuns);
						 stock.setWhrid(user.getId());
						 stock.setWhsj(currentDate);
						 stockSerivce.updateByPrimaryKeySelective(stock);
						 materialRecService.writeStockRec(stock.getId(), czls.toString(), outboundOrder.getId(), outboundOrder.getShdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.UPDATE,OutStockTypeEnum.TYPE6.getName(),"",outboundOrder.getShdh(),outboundOrder.getShdh(),outboundOrderDetails.getCksl());
					 }
				}else{
					outboundOrderDetailsList.add(outboundOrderDetails);
				}
			}
			outboundOrder.setOutboundOrderDetailslist(outboundOrderDetailsList);
			///新增提交库存履历-新增出库单明细-新增出库关系 （出库类型!=其他）
			insertSubimtSelectiveMaterialHistory(outboundOrder,outboundOrder.getId());
		}
		for (OutboundOrderDetails outboundOrderDetails : oldList){
			//如果数据库相关id不在页面,那么删除
			if(null == idMap.get(outboundOrderDetails.getId())){
				columnValueList.add(outboundOrderDetails.getId());
				kcllidList.add(outboundOrderDetails.getKcllid());
			}
		}
		if(columnValueList.size() > 0){
			//批量删除
			outboundRelationsMapper.delete4Batch(columnValueList); //删除出库关系表
			
			outboundOrderDetailsMapper.delete4Batch(columnValueList); //删除出库明细
			
			materialHistoryMapper.delete4Batch(kcllidList);//删除库存履历表
		}		
	}

	/**
	 * 
	 * @Description 删除出库单
	 * @CreateTime 2018年3月15日 下午3:23:43
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public void delete(OutboundOrder outboundOrder) throws BusinessException {
		
		//删除出库关系表
		outboundRelationsMapper.deleteByCkid(outboundOrder.getId());
		
		//删除出库明细表
		outboundOrderDetailsMapper.deleteByMainid(outboundOrder.getId());
		
		List<OutboundOrderDetails> outboundOrderDetailslist	=outboundOrderDetailsMapper.getDepartmentByYwid(outboundOrder.getId());
		
		for (OutboundOrderDetails outboundOrderDetails : outboundOrderDetailslist) {
			//删除库存履历表
			materialHistoryMapper.deleteByPrimaryKey(outboundOrderDetails.getKcllid());
			
		}
		//删除出库表
		outboundOrderMapper.deleteByPrimaryKey(outboundOrder.getId());
	}
	
	/**
	 * 
	 * @Description 出库列表
	 * @CreateTime 2018年3月16日 上午11:43:33
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryAllPageList(OutboundOrder outboundOrder)throws BusinessException {
		User user = ThreadVarUtil.getUser();//当前登陆人
		outboundOrder.getParamsMap().put("userId", user.getId());
		outboundOrder.getParamsMap().put("userType", user.getUserType());//验证飞机注册号权限权限
		PageHelper.startPage(outboundOrder.getPagination());
		List<OutboundOrder> list = outboundOrderMapper.queryAllPageList(outboundOrder);
		return PageUtil.pack4PageHelper(list, outboundOrder.getPagination());
	}
	
	/**
	 * 
	 * @Description 查询出库单数据
	 * @CreateTime 2018年3月16日 下午2:51:53
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public OutboundOrder getByStockoutId(OutboundOrder outboundOrder)throws BusinessException {
		return outboundOrderMapper.getByStockoutId(outboundOrder);
	}
	
	/**
	 * 
	 * @Description 撤销出库单
	 * @CreateTime 2018年3月20日 下午12:14:50
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @throws BusinessException
	 */
	@Override
	public String updaterevoke(OutboundOrder outboundOrder)throws BusinessException {
		User user = ThreadVarUtil.getUser();		//当前登陆人
		String czls = UUID.randomUUID().toString();//流水号
		
		//修改出库单
		outboundOrder.setZt(OutboundOrderStatusEnum.CANCEL.getId());//状态为保存
		this.updateSelective(outboundOrder,czls,user);
		
		//还原库存和外场库存
		updateSelectiveMaterialHistory(outboundOrder,user);
		
		return outboundOrder.getId();
	}
	/**
	 * 
	 * @Description 还原库存和外场库存
	 * @CreateTime 2018年3月20日 下午1:34:44
	 * @CreateBy 林龙
	 * @param outboundOrder
	 * @param user
	 */
	private void updateSelectiveMaterialHistory(OutboundOrder outboundOrder,User user) throws BusinessException{
		Date currentDate = commonService.getSysdate();//当前时间
		UUID czls = UUID.randomUUID();//获取随机的uuid
		for (OutboundOrderDetails outboundOrderDetails : outboundOrder.getOutboundOrderDetailslist()) {
			
			OutboundOrderDetails outboundOrderDetailsNew=outboundOrderDetailsMapper.selectByPrimaryKey(outboundOrderDetails.getId());
			if(outboundOrderDetailsNew.getWckcllid() != null){
				//根据外场库存履历查询
				MaterialHistory materialHistorywc=materialHistoryMapper.selectByPrimaryKey(outboundOrderDetailsNew.getWckcllid());
				if(materialHistorywc != null){
					OutFieldStock outFieldStock=outFieldStockService.queryByKeywc(materialHistorywc.getKcid());
					if(outFieldStock !=null ){
						
						 if(outFieldStock.getKcsl().compareTo(materialHistorywc.getKcsl())==-1){
							 throw new BusinessException("出库数量不能大于库存数量!");
						 }else if(outFieldStock.getKcsl().compareTo(materialHistorywc.getKcsl())==0  ){
							 String outFieldStockmaterialHistoryuuid = UUID.randomUUID().toString();//库存履历id
							 materialHistorywc.setKcsl(materialHistorywc.getKcsl());//库存履历数量为出库数量
							 materialHistorywc.setId(outFieldStockmaterialHistoryuuid);
							 materialHistorywc.setKcid(outFieldStock.getId()); //库存id为外场库存id
							 //新增库存履历
							 materialHistoryMapper.insertSelective(materialHistorywc);
							 
							 //当库存为0时删除
							 outFieldStockService.delete(outFieldStock.getId());
							 materialRecService.writeStockRec(outFieldStock.getId(), czls.toString(), outboundOrder.getId(), outboundOrder.getShdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.DELETE,OutStockTypeEnum.TYPE6.getName(),"",outboundOrder.getShdh(),outboundOrder.getShdh(),outboundOrderDetails.getCksl());
							 
							 
						 }else{
							 //库存数减去出库数
							 BigDecimal kucuns = outFieldStock.getKcsl().subtract(materialHistorywc.getKcsl());   	
							 outFieldStock.setKcsl(kucuns);
							 outFieldStock.setWhrid(user.getId());
							 outFieldStock.setWhsj(currentDate);
							 outFieldStockService.updateByPrimaryKeySelective(outFieldStock);
							 materialRecService.writeStockRec(outFieldStock.getId(), czls.toString(), outboundOrder.getId(), outboundOrder.getShdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.UPDATE,OutStockTypeEnum.TYPE6.getName(),"",outboundOrder.getShdh(),outboundOrder.getShdh(),outboundOrderDetails.getCksl());
					 
							String outFieldStockmaterialHistoryuuid = UUID.randomUUID().toString();//库存履历id
							materialHistorywc.setKcsl(kucuns);//库存履历数量为出库数量
							materialHistorywc.setId(outFieldStockmaterialHistoryuuid);
							materialHistorywc.setKcid(outFieldStock.getId()); //库存id为外场库存id
							//新增库存履历
							materialHistoryMapper.insertSelective(materialHistorywc);
							
						 }
					}
				}
			}
				
				//根据库存履历查询
				MaterialHistory materialHistorykc=materialHistoryMapper.selectByPrimaryKey(outboundOrderDetails.getKcllid());
				
				Stock stock=stockMapper.queryById(materialHistorykc.getKcid());
				if(stock != null){//增量
					 //库存履历数量+库存数
					 BigDecimal kucuns = stock.getKcsl().add(materialHistorykc.getKcsl());   	
					 stock.setKcsl(kucuns);
					 stock.setWhrid(user.getId());
					 stock.setWhsj(currentDate);
					 stockSerivce.updateByPrimaryKeySelective(stock);
						
					 materialRecService.writeStockRec(stock.getId(), czls.toString(), stock.getId(), outboundOrder.getShdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.UPDATE,OutStockTypeEnum.TYPE6.getName(),"",outboundOrder.getShdh(),outboundOrder.getShdh(),outboundOrderDetails.getCksl());
					 
					String materialHistoryuuid = UUID.randomUUID().toString();//库存履历id
					materialHistorykc.setKcsl(kucuns);//库存履历数量为出库数量
					materialHistorykc.setId(materialHistoryuuid);
					//新增库存履历
					materialHistoryMapper.insertSelective(materialHistorykc);
					
					//写入b_h_025  //库存履历
					StockHistory  history=new StockHistory();
					history.setId(materialHistoryuuid);
					history.setDprtcode(user.getJgdm());
					history.setCzrbmid(user.getBmdm());
					history.setCzrid(user.getId());
					history.setCzr(user.getRealname());
					history.setCzlx(StockHistoryOperationEnum.ck.getId());
					history.setCzzlx(29);
					history.setCzsm("出库-撤销");
					history.setKcid(stock.getId());
					history.setYwbh(outboundOrder.getShdh());
					history.setYwid(outboundOrder.getId());
					history.setYwmxid(outboundOrderDetails.getId());
					storeInnerSearchMapper.saveStoreHistory(history);
				}else{//新增
					stock=new Stock(materialHistorykc);
					// 验证装机清单+库存+外场不存在
					stockSerivce.getCount4ValidationBjAndXlh(stock.getId(),stock.getBjh(), stock.getSn(), stock.getDprtcode(), 11);
				
					stock.setWhrid(user.getId());
					stock.setWhsj(currentDate);
					stockSerivce.insertSelective(stock);
					materialRecService.writeStockRec(stock.getId(), czls.toString(), stock.getId(), outboundOrder.getShdh(), StockRecBizTypeEnum.TYPE1, UpdateTypeEnum.UPDATE,OutStockTypeEnum.TYPE6.getName(),"",outboundOrder.getShdh(),outboundOrder.getShdh(),outboundOrderDetails.getCksl());
					
					MaterialHistory materialHistory=new MaterialHistory(stockSerivce.queryById(stock.getId()));//查询数据库
					
					String materialHistoryuuid = UUID.randomUUID().toString();//库存履历id
					materialHistory.setKcsl(materialHistorykc.getKcsl());//库存履历数量为出库数量
					materialHistory.setId(materialHistoryuuid);
					//新增库存履历
					materialHistoryMapper.insertSelective(materialHistory);
					
					//写入b_h_025  //库存履历
					StockHistory  history=new StockHistory();
					history.setId(materialHistoryuuid);
					history.setDprtcode(user.getJgdm());
					history.setCzrbmid(user.getBmdm());
					history.setCzrid(user.getId());
					history.setCzr(user.getRealname());
					history.setCzlx(StockHistoryOperationEnum.ck.getId());
					history.setCzzlx(29);
					history.setCzsm("出库-撤销");
					history.setKcid(stock.getId());
					history.setYwbh(outboundOrder.getShdh());
					history.setYwid(outboundOrder.getId());
					history.setYwmxid(outboundOrderDetails.getId());
					storeInnerSearchMapper.saveStoreHistory(history);
				}
			
				OutboundRelations outboundRelations =new OutboundRelations();
				outboundRelations.setCkdid(outboundOrder.getId());
				outboundRelations.setZt(0); //失效
				outboundRelationsMapper.updateByPrimaryKeySelectiveCk(outboundRelations);
			
		}
	
	}
	
	
	
	
	
	
	
     
}