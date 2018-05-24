package com.eray.thjw.aerialmaterial.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.HCMainDataMapper;
import com.eray.thjw.aerialmaterial.dao.MaterialToPlaneModelMapper;
import com.eray.thjw.aerialmaterial.dao.MaterialToStoreMapper;
import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.aerialmaterial.po.MaterialToPlaneModel;
import com.eray.thjw.aerialmaterial.po.MaterialToStore;
import com.eray.thjw.aerialmaterial.service.HCMainDataService;
import com.eray.thjw.dao.SubstitutionMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.MountLoadingList;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.Substitution;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.DelStatus;
import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

/**
 * @author liub
 * @description 航材service,用于业务逻辑处理
 * @develop date 2016.09.09
 */
@Service
public class HCMainServiceImpl implements HCMainDataService {
	
    @Resource
    private HCMainDataMapper hCMainDataMapper;
    /**
	 * @author liub
	 * @description 航材机型关系Mapper
	 * @develop date 2016.09.19
	 */
    @Resource
    private MaterialToPlaneModelMapper materialToPlaneModelMapper;
    
    /**
	 * @author liub
	 * @description 航材仓库关系Mapper
	 * @develop date 2016.09.19
	 */
    @Resource
    private MaterialToStoreMapper materialToStoreMapper;
    
    /**
  	 * @author liub
  	 * @description 等效替代件号Mapper
  	 */
	@Autowired
	private SubstitutionMapper substitutionMapper;
    
    /**
  	 * @author liub
  	 * @description 附件service
  	 */
	@Resource
	private AttachmentService attachmentService;
    
    /**
	 * @author liub
	 * @description 历史数据公共service
	 * @develop date 2016.09.26
	 */
	@Autowired
	private CommonRecService commonRecService;
	
    /**
	 * @author liub
	 * @description 增加航材
	 * @param hcMainData
	 * @develop date 2016.09.18
	 * @throws BusinessException 
	 */
	@Override
	public String add(HCMainData hcMainData) throws BusinessException{
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		hcMainData.setDprtcode(user.getJgdm());
		checkExist(hcMainData);//检查是否存在
		//新增航材信息
		hcMainData.setZt(DelStatus.TAKE_EFFECT.getId());
		hcMainData.setCjrid(user.getId());
		hcMainData.setBmid(user.getBmdm());
		//查询是否有作废数据
		HCMainData material = hCMainDataMapper.selectByBjh(hcMainData);
		String id="";
		if(material != null ){
			id = material.getId();
			hcMainData.setId(id);
			hCMainDataMapper.updateByPrimaryKeySelective(hcMainData);
			
			//新增或删除航材机型关系表
			List<String> jxList = hcMainData.getJxList();
			List<MaterialToPlaneModel> materialToPlaneModelList = materialToPlaneModelMapper.queryByMainId(hcMainData.getId());
			List<String> oldJxList = new ArrayList<String>();
			for(MaterialToPlaneModel materialToPlaneModel : materialToPlaneModelList){
				oldJxList.add(materialToPlaneModel.getFjjx());
				if(!jxList.contains(materialToPlaneModel.getFjjx())){
					commonRecService.write(materialToPlaneModel.getId(), TableEnum.D_00801, user.getId(), czls ,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, hcMainData.getId());//保存历史记录信息
					materialToPlaneModelMapper.deleteByPrimaryKey(materialToPlaneModel.getId());
				}
			}
			List<String> columnValueList = new ArrayList<String>();
			for (String jx : jxList) {
				if(!oldJxList.contains(jx)){
					UUID jxuuid = UUID.randomUUID();//获取随机的uuid
					String jxId = jxuuid.toString();
					MaterialToPlaneModel materialToPlaneModel = new MaterialToPlaneModel();
					materialToPlaneModel.setId(jxId);
					materialToPlaneModel.setMainid(hcMainData.getId());
					materialToPlaneModel.setFjjx(jx);
					materialToPlaneModel.setWhrid(user.getId());
					columnValueList.add(jxId);
					materialToPlaneModelMapper.insertSelective(materialToPlaneModel);
				}
			}
			if(columnValueList.size() > 0){
				commonRecService.write("id", columnValueList, TableEnum.D_00801, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, hcMainData.getId());//保存航材对应机型历史记录信息
			}
			
			//判断维护基本信息或是维护生产信息
			if(null != hcMainData.getIsProduction() && "true".equals(hcMainData.getIsProduction())){
				HCMainData obj = hCMainDataMapper.checkUptById(hcMainData.getId());
				//判断其它地方是否引用部件
				if(null != obj && (obj.getGljb().intValue() != hcMainData.getGljb().intValue() || obj.getHclx().intValue() != hcMainData.getHclx().intValue())){
					throw new BusinessException("其它地方在使用，管理级别和航材类型不能修改!");
				}
				//新增或修改航材仓库关系表
				MaterialToStore materialToStore = hcMainData.getMaterialToStore();
				if(null != materialToStore.getId() && !"".equals(materialToStore.getId())){
					materialToStore.setWhrid(user.getId());
					materialToStoreMapper.updateByPrimaryKeySelective(materialToStore);
					commonRecService.write(materialToStore.getId(), TableEnum.D_00802, user.getId(), czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.UPDATE, hcMainData.getId());//保存航材对应仓库历史记录信息
				}else{
					if(null != materialToStore.getCkh() && !"".equals(materialToStore.getCkh())){
						UUID ckuuid = UUID.randomUUID();//获取随机的uuid
						String msId = ckuuid.toString();
						materialToStore.setId(msId);
						materialToStore.setMainid(hcMainData.getId());
						materialToStore.setWhrid(user.getId());
						materialToStoreMapper.insertSelective(materialToStore);
						commonRecService.write(msId, TableEnum.D_00802, user.getId(), czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, hcMainData.getId());//保存航材对应仓库历史记录信息
					}
				}
			}
			hCMainDataMapper.updateByPrimaryKeySelective(hcMainData);
			commonRecService.write(hcMainData.getId(), TableEnum.D_008, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.UPDATE, hcMainData.getId());//保存航材历史记录信息
			attachmentService.eidtAttachment(hcMainData.getAttachments(), hcMainData.getId(), czls, hcMainData.getId(), hcMainData.getDprtcode(), LogOperationEnum.EDIT);
			
		}else{
			id = UUID.randomUUID().toString();//获取随机的uuid
			hcMainData.setId(id);
			hCMainDataMapper.insertSelective(hcMainData);
			commonRecService.write(id, TableEnum.D_008, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, hcMainData.getId());//保存航材历史记录信息
			attachmentService.eidtAttachment(hcMainData.getAttachments(), hcMainData.getId(), czls, hcMainData.getId(), hcMainData.getDprtcode(), LogOperationEnum.SAVE_WO);
			//新增航材机型关系表
			if(null != hcMainData.getJxList()){
				List<String> columnValueList = new ArrayList<String>();
				for (String jx : hcMainData.getJxList()) {
					UUID uuid2 = UUID.randomUUID();//获取随机的uuid
					String jxId = uuid2.toString();
					MaterialToPlaneModel materialToPlaneModel = new MaterialToPlaneModel();
					materialToPlaneModel.setId(jxId);
					materialToPlaneModel.setMainid(id);
					materialToPlaneModel.setFjjx(jx);
					materialToPlaneModel.setWhrid(user.getId());
					columnValueList.add(jxId);
					materialToPlaneModelMapper.insertSelective(materialToPlaneModel);
				}
				if(columnValueList.size() > 0){
					commonRecService.write("id", columnValueList, TableEnum.D_00801, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, hcMainData.getId());//保存航材对应机型历史记录信息
				}
			}
			MaterialToStore materialToStore = hcMainData.getMaterialToStore();
			if(null != materialToStore.getCkh() && !"".equals(materialToStore.getCkh())){
				//新增航材仓库关系表
				UUID ckuuid = UUID.randomUUID();//获取随机的uuid
				String mtId = ckuuid.toString();
				materialToStore.setId(mtId);
				materialToStore.setMainid(id);
				materialToStore.setWhrid(user.getId());
				materialToStoreMapper.insertSelective(materialToStore);
				commonRecService.write(mtId, TableEnum.D_00802, user.getId(), czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, hcMainData.getId());//保存航材对应仓库历史记录信息
			}
		}
		return id;
	}
	
	/**
	 * @author liub
	 * @description 修改航材
	 * @param hcMainData
	 * @develop date 2016.09.19
	 * @throws BusinessException 
	 */
	@Override
	public void edit(HCMainData hcMainData) throws BusinessException{
		String czls = UUID.randomUUID().toString();//操作流水
		//修改航材信息
		User user = ThreadVarUtil.getUser();
		hcMainData.setCjrid(user.getId());
		hcMainData.setBmid(user.getBmdm());
		
		//新增或删除航材机型关系表
		List<String> jxList = hcMainData.getJxList();
		List<MaterialToPlaneModel> materialToPlaneModelList = materialToPlaneModelMapper.queryByMainId(hcMainData.getId());
		List<String> oldJxList = new ArrayList<String>();
		for(MaterialToPlaneModel materialToPlaneModel : materialToPlaneModelList){
			oldJxList.add(materialToPlaneModel.getFjjx());
			if(!jxList.contains(materialToPlaneModel.getFjjx())){
				commonRecService.write(materialToPlaneModel.getId(), TableEnum.D_00801, user.getId(), czls ,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, hcMainData.getId());//保存历史记录信息
				materialToPlaneModelMapper.deleteByPrimaryKey(materialToPlaneModel.getId());
			}
		}
		List<String> columnValueList = new ArrayList<String>();
		for (String jx : jxList) {
			if(!oldJxList.contains(jx)){
				UUID jxuuid = UUID.randomUUID();//获取随机的uuid
				String jxId = jxuuid.toString();
				MaterialToPlaneModel materialToPlaneModel = new MaterialToPlaneModel();
				materialToPlaneModel.setId(jxId);
				materialToPlaneModel.setMainid(hcMainData.getId());
				materialToPlaneModel.setFjjx(jx);
				materialToPlaneModel.setWhrid(user.getId());
				columnValueList.add(jxId);
				materialToPlaneModelMapper.insertSelective(materialToPlaneModel);
			}
		}
		if(columnValueList.size() > 0){
			commonRecService.write("id", columnValueList, TableEnum.D_00801, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, hcMainData.getId());//保存航材对应机型历史记录信息
		}
		
		//判断维护基本信息或是维护生产信息
		if(null != hcMainData.getIsProduction() && "true".equals(hcMainData.getIsProduction())){
			HCMainData obj = hCMainDataMapper.checkUptById(hcMainData.getId());
			//判断其它地方是否引用部件
			if(null != obj && (obj.getGljb().intValue() != hcMainData.getGljb().intValue() || obj.getHclx().intValue() != hcMainData.getHclx().intValue())){
				throw new BusinessException("其它地方在使用，管理级别和航材类型不能修改!");
			}
			//新增或修改航材仓库关系表
			MaterialToStore materialToStore = hcMainData.getMaterialToStore();
			if(null != materialToStore.getId() && !"".equals(materialToStore.getId())){
				materialToStore.setWhrid(user.getId());
				materialToStoreMapper.updateByPrimaryKeySelective(materialToStore);
				commonRecService.write(materialToStore.getId(), TableEnum.D_00802, user.getId(), czls ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, hcMainData.getId());//保存航材对应仓库历史记录信息
			}else{
				if(null != materialToStore.getCkh() && !"".equals(materialToStore.getCkh())){
					UUID ckuuid = UUID.randomUUID();//获取随机的uuid
					String msId = ckuuid.toString();
					materialToStore.setId(msId);
					materialToStore.setMainid(hcMainData.getId());
					materialToStore.setWhrid(user.getId());
					materialToStoreMapper.insertSelective(materialToStore);
					commonRecService.write(msId, TableEnum.D_00802, user.getId(), czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, hcMainData.getId());//保存航材对应仓库历史记录信息
				}
			}
		}
		hCMainDataMapper.updateByPrimaryKeySelective(hcMainData);
		commonRecService.write(hcMainData.getId(), TableEnum.D_008, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, hcMainData.getId());//保存航材历史记录信息
		attachmentService.eidtAttachment(hcMainData.getAttachments(), hcMainData.getId(), czls, hcMainData.getId(), hcMainData.getDprtcode(), LogOperationEnum.EDIT);
	}
	
	/**
	 * @author liub
	 * @description  作废
	 * @param id
	 * @return
	 */
	@Override
	public void cancel(String id) throws BusinessException{
		HCMainData obj = hCMainDataMapper.checkUptById(id);
		if(null != obj){
			throw new BusinessException("该部件已被使用，不能作废!");
		}
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		HCMainData hcMainData = new HCMainData();
		hcMainData.setId(id);
		hcMainData.setZt(DelStatus.LOSE_EFFECT.getId());
		hcMainData.setCjrid(user.getId());
		hcMainData.setBmid(user.getBmdm());
		hCMainDataMapper.cancel(hcMainData);
		commonRecService.write(id, TableEnum.D_008, user.getId(), czls ,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, hcMainData.getId());//保存航材历史记录信息
	}
	
	/**
	 * @author liub
	 * @description 根据主键id查询航材信息
	 * @param id
	 * @return HCMainData
	 * @develop date 2016.09.19
	 */
	@Override
	public HCMainData selectById(String id){
		return hCMainDataMapper.selectById(id);
	}
	
	/**
	 * @author liub
	 * @description 检查航材类型是否可以修改(如果存在则不能修改)
	 * @param id
	 * @return HCMainData
	 */
	@Override
	public HCMainData checkUptById(String id){
		return hCMainDataMapper.checkUptById(id);
	}

	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询航材信息(弹窗需要的数据)
	 * @param hcMainData
	 * @return List<HCMainData>
	 * @develop date 2016.10.11
	 */
	@Override
	public List<HCMainData> queryWinAllPageList(HCMainData hcMainData){
		List<HCMainData> hcMainDataList = hCMainDataMapper.queryWinAllPageList(hcMainData);
		setMaterialModel(hcMainDataList);
		return hcMainDataList;
	}
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询航材信息
	 * @param hcMainData
	 * @return List<HCMainData>
	 * @develop date 2016.09.12
	 */
	@Override
	public List<HCMainData> queryAllPageList(HCMainData hcMainData){
		List<HCMainData> hcMainDataList = hCMainDataMapper.queryAllPageList(hcMainData);
		setMaterialModel(hcMainDataList);//设置机型
		setSubstitution(hcMainDataList);//设置等效替代部件
		return hcMainDataList;
	}

	/**
	 * @author liub
	 * @description  根据航材id查询航材仓库关系
	 * @param mainId
	 * @return MaterialToStore
	 * @develop date 2016.07.25
	 */
	@Override
	public MaterialToStore getMaterialStoreByMainId(String mainId){
		return materialToStoreMapper.getMaterialStoreByMainId(mainId);
	}
	
	/**
	 * @author liub
	 * @description  根据盘点id、关键字查询航材信息
	 * @param pdid,keyword
	 * @return List<HCMainData>
	 * @develop date 2016.11.24
	 */
	public List<HCMainData> queryMaterialListByPdid(String pdid,String keyword){
		return hCMainDataMapper.queryMaterialListByPdid(pdid,keyword);
	}
	
	/**
	 * @author liub
	 * @description 检查航材是否存在
	 * @param hcMainData
	 * @return
	 * @develop date 2016.09.14
	 */
	public void checkExist(HCMainData hcMainData) throws BusinessException{
		String message = "";
		HCMainData checkParam = new HCMainData();
		checkParam.setBjh(hcMainData.getBjh());
		checkParam.setDprtcode(hcMainData.getDprtcode());
		List<HCMainData> materialList = hCMainDataMapper.checkMaterial(checkParam);
		for (HCMainData material : materialList) {
			if(null != hcMainData.getId() && hcMainData.getId().equals(material.getId())){
				continue;
			}
			if(null != hcMainData.getBjh() && hcMainData.getBjh().equals(material.getBjh())){
				message += "部件号["+hcMainData.getBjh()+"]已存在!";
			}
		}
		if(!"".equals(message)){
			throw new BusinessException(message);
		}
	}
	
	/**
	 * @author liub
	 * @description 设置获取航材适用机型,并赋值
	 * @param hcMainDataList
	 */
	private void setMaterialModel(List<HCMainData> hcMainDataList){
		List<String> mainIdList = new ArrayList<String>();
		for (HCMainData hc : hcMainDataList) {
			if(null == hc.getXh() || "".equals(hc.getXh()) || "-".equals(hc.getXh())){
				mainIdList.add(hc.getId());
			}
		}
		if(0 != mainIdList.size()){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mainIdList", mainIdList);
			List<MaterialToPlaneModel> mToPlaneModelList = materialToPlaneModelMapper.queryByMainIdList(map);
			for (HCMainData hc : hcMainDataList) {
				if(null == hc.getXh() || "".equals(hc.getXh()) || "-".equals(hc.getXh())){
					StringBuffer xh = new StringBuffer();
					for (MaterialToPlaneModel materialToPlaneModel : mToPlaneModelList) {
						if(hc.getId().equals(materialToPlaneModel.getMainid())){
							xh.append(materialToPlaneModel.getFjjx()).append(",");
						}
					}
					if(xh.length() > 0){
						xh.deleteCharAt(xh.length() - 1);
					}
					hc.setXh(xh.toString());
				}
			}
		}
	}
	
	/**
	 * @author liub
	 * @description 设置等效替代部件
	 * @param hcMainDataList
	 */
	private void setSubstitution(List<HCMainData> hcMainDataList){
		if(hcMainDataList == null || hcMainDataList.size() == 0){
			return;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();//参数
		List<String> jhList = new ArrayList<String>();
		for (HCMainData hc : hcMainDataList) {
			jhList.add(hc.getBjh());
		}
		paramMap.put("dprtcode", hcMainDataList.get(0).getDprtcode());
		paramMap.put("jhList", jhList);
		//获取等效替代部件集合
		List<Substitution> substitutionList = substitutionMapper.queryByBjhList(paramMap);
		if(substitutionList.size()>0){
			for (HCMainData hc : hcMainDataList) {
				List<Substitution> resultList = new ArrayList<Substitution>();
				for (Substitution substitution : substitutionList) {
					if (hc.getBjh().equals(substitution.getBjh()) || (hc.getBjh().equals(substitution.getTdjh()) && substitution.getKnxbs() == 2)) {
						resultList.add(substitution);
					}
				}
				hc.setSubstitutionList(resultList);
			}
		}
	}

	@Override
	public Map<String, Object> selectHCList(HCMainData hcmaindata){
		//将多种航材类型的String拆分存到集合当中
		if(hcmaindata.getHclxs()!=null && !"".equals(hcmaindata.getHclxs())){
			List <String> list =new ArrayList<String>();
	        String d[] = hcmaindata.getHclxs().split(",");
	        for (int i = 0; i < d.length; i++) {
	        	list.add(d[i]);
	        }
	        hcmaindata.setHclxlist(list);
		}
		//查询的航材不包含已经选择过的航材
		if(null!=hcmaindata.getHcids()&&hcmaindata.getHcids().isEmpty()){
			hcmaindata.setHcids(null);
		}
		PageHelper.startPage(hcmaindata.getPagination());
		List<HCMainData> list = hCMainDataMapper.selectHCList(hcmaindata);
		return PageUtil.pack4PageHelper(list, hcmaindata.getPagination());
	}

	@Override
	public List<HCMainData> selectHCMainDataList(HCMainData hcmaindata) {
		return hCMainDataMapper.selectHCMainDataList(hcmaindata);
	}

	@Override
	public List<HCMainData> selectMaterialCostList(HCMainData record) {
		List<HCMainData> hcMainDataList = hCMainDataMapper.selectMaterialCostList(record);
		setMaterialModel(hcMainDataList);
		return hcMainDataList;
	}

	/**
	 * 根据件号和组织机构查询
	 */
	@Override
	public HCMainData selectByBjhAndDprt(HCMainData data) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bjh", data.getBjh());
		paramMap.put("dprtcode", data.getDprtcode());
		return hCMainDataMapper.selectByBjhAndDprt(paramMap);
	}

	@Override
	public List<HCMainData> queryAllPageLists(HCMainData hCMainData) {
		return hCMainDataMapper.queryAllPageLists(hCMainData);
	}

	/**
	 * 修改装机清单时，同步航材主数据
	 */
	@Override
	public int updateByLoadingList(LoadingList ll, String czls) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bjh", ll.getJh());
		paramMap.put("dprtcode", ll.getDprtcode());
		HCMainData hc = hCMainDataMapper.selectByBjhAndDprt(paramMap);
		int count = 0;
		if(hc != null){
			hc.setBjh(ll.getJh());
			hc.setDprtcode(ll.getDprtcode());
			hc.setZwms(ll.getZwmc());
			hc.setYwms(ll.getYwmc());
			hc.setCjjh(ll.getCjjh());
			hc.setZjh(ll.getZjh());
			hc.setCjrid(ThreadVarUtil.getUser().getId());
			if(StringUtils.isNotBlank(ll.getXlh())){
				hc.setGljb(3);
			}else if(StringUtils.isNotBlank(ll.getPch())){
				hc.setGljb(2);
			}else{
				hc.setGljb(1);
			}
			count =  hCMainDataMapper.updateByLoadingList(hc);
			commonRecService.write(hc.getId(), TableEnum.D_008,
					ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,hc.getId());
		}
		return count;
	}

	/**
	 * 修改飞行记录单装上件时，同步航材主数据
	 */
	@Override
	public int updateByLoadingList(MountLoadingList ll, String czls) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bjh", ll.getJh());
		paramMap.put("dprtcode", ll.getDprtcode());
		HCMainData hc = hCMainDataMapper.selectByBjhAndDprt(paramMap);
		int count = 0;
		if(hc != null){
			hc.setBjh(ll.getJh());
			hc.setDprtcode(ll.getDprtcode());
			hc.setZwms(ll.getZwmc());
			hc.setYwms(ll.getYwmc());
			hc.setCjjh(ll.getCjjh());
			hc.setZjh(ll.getZjh());
			hc.setCjrid(ThreadVarUtil.getUser().getId());
			if(StringUtils.isNotBlank(ll.getXlh())){
				hc.setGljb(3);
			}else if(StringUtils.isNotBlank(ll.getPch())){
				hc.setGljb(2);
			}else{
				hc.setGljb(1);
			}
			count =  hCMainDataMapper.updateByLoadingList(hc);
			commonRecService.write(hc.getId(), TableEnum.D_008,
					ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,hc.getId());
		}
		return count;
	}
	
	/**
	 * @author liub
	 * @description 根据部件号、机构代码查询耗材
	 * @param hcmaindata
	 * @return List<HCMainData>
	 */
	@Override
	public List<HCMainData> selectBjhAndDprt(HCMainData hcmaindata){
		return hCMainDataMapper.selectBjhAndDprt(hcmaindata);
	}
	/**
	 * @author liubing
	 * @description  根据条件查询航材信息(弹窗)含库存数量
	 * @param hcmaindata
	 * @return List<HCMainData>
	 * @develop date 2017.08.09
	 */
	@Override
	public List<HCMainData> selectWinList(HCMainData hcmaindata){
		return hCMainDataMapper.selectWinList(hcmaindata);
	}
	/**
	 * @Description 获取部件数据
	 * @CreateBy 岳彬彬
	 * @CreateTime 2017年8月24日 上午10:50:04
	 * @param hcmaindata
	 */
	@Override
	public List<HCMainData> selectPartList(HCMainData hcmaindata) {
		
		return hCMainDataMapper.selectPartList(hcmaindata);
	}

	/**
	 * @Description 根据组织机构获取部件前十条数据
	 * @CreateTime 2017年8月31日 下午1:44:53
	 * @CreateBy 韩武
	 * @param dprtcode
	 * @return
	 */
	@Override
	public List<HCMainData> queryLimitTen(HCMainData hcmaindata) {
		return hCMainDataMapper.queryLimitTen(hcmaindata);
	}
	/**
	 * 
	 * @Description 根据bjh 和 dprtcode 查询数据
	 * @CreateTime 2017-9-27 上午11:38:42
	 * @CreateBy 孙霁
	 * @param hcmaindata
	 * @return
	 */
	@Override
	public HCMainData selectByBjhAndDprcode(HCMainData hcmaindata) {
		return hCMainDataMapper.selectByBjhAndDprcode(hcmaindata);
	}

	/**
	 * 
	 * @Description 根据条件查询航材证书列表
	 * @CreateTime 2017-11-9 下午3:54:45
	 * @CreateBy 孙霁
	 * @param hcmaindata
	 * @return
	 */
	@Override
	public Map<String, Object> queryAllCertificate(HCMainData hcmaindata) {
		PageHelper.startPage(hcmaindata.getPagination());
		List<HCMainData> list = hCMainDataMapper.queryAllCertificate(hcmaindata);
		return PageUtil.pack4PageHelper(list, hcmaindata.getPagination());
	}

	/**
	 * 
	 * @Description 根据部件号 和序列号和组织机构查询数据
	 * @CreateTime 2017-11-10 下午2:28:44
	 * @CreateBy 孙霁
	 * @param hCMainData
	 * @return HCMainData
	 */
	@Override
	public HCMainData selectByBjhAndXlh(HCMainData hCMainData) throws BusinessException  {
		HCMainData obj= hCMainDataMapper.selectByBjhAndXlh(hCMainData);
		return obj;
	}
	
	/**
	 * 
	 * @Description 部件主数据导出
	 * @CreateTime 2017年12月4日 下午5:34:00
	 * @CreateBy 岳彬彬
	 * @param hcMainData
	 * @param whrq
	 * @return
	 */
	@Override
	public List<HCMainData> doHcExportList(HCMainData hcMainData,String whrq) {
		hcMainData.getPagination().setOrder("desc");
		hcMainData.getPagination().setSort("auto");
		hcMainData.getPagination().setRows(100000);
		if(null != hcMainData.getKeyword() && !"".equals(hcMainData.getKeyword())){
			String keyword = hcMainData.getKeyword();
			try {
				keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if(keyword.contains("'")){
				keyword=keyword.replace("'", "'|| chr(39) ||'");
			}
			hcMainData.setKeyword(keyword);
		}
		if(null != whrq && !"".equals(whrq)){
			hcMainData.getParamsMap().put("whrqBegin", whrq.substring(0, 10).concat(" 00:00:00"));
			hcMainData.getParamsMap().put("whrqEnd", whrq.substring(13, 23).concat(" 23:59:59"));		
		}
		PageHelper.startPage(hcMainData.getPagination());
		List<HCMainData> hcMainDataList = hCMainDataMapper.queryAllPageList(hcMainData);
		List<HCMainData> temp  = new ArrayList<HCMainData>();
		for (int i = 0; i < hcMainDataList.size(); i++) {
			temp.add(hcMainDataList.get(i));
			if(temp.size() >= 300 || i == hcMainDataList.size() - 1){				
				setMaterialModel(temp);//设置机型
				setSubstitution(temp);//设置等效替代部件
				temp.clear();
			}
		}
		for (HCMainData hc : hcMainDataList) {
			List<Substitution> subList = hc.getSubstitutionList();
			StringBuffer sbf = new StringBuffer("");
			if(subList != null && subList.size()>0){
				for (Substitution substitution : subList) {
					if(substitution.getKnxbs() == 2 && !hc.getBjh().equals(substitution.getBjh())){
						sbf.append(substitution.getBjh()).append(",");
					}else{
						sbf.append(substitution.getTdjh()).append(",");
					}
				}
				if(!"".equals(sbf.toString())){
					hc.getParamsMap().put("substitution", sbf.substring(0, sbf.length()-1));
				}			
			}
		}
		return hcMainDataList;
	}
	
}
