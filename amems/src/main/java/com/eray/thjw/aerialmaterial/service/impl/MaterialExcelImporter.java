package com.eray.thjw.aerialmaterial.service.impl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
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
import com.eray.thjw.aerialmaterial.dao.StorageMapper;
import com.eray.thjw.aerialmaterial.dao.StoreMapper;
import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.aerialmaterial.po.MaterialToPlaneModel;
import com.eray.thjw.aerialmaterial.po.MaterialToStore;
import com.eray.thjw.aerialmaterial.po.Storage;
import com.eray.thjw.aerialmaterial.po.Store;
import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.ActTypeMapper;
import com.eray.thjw.project2.po.ActType;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.FixChapterService;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.DelStatus;
import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service("materialexcelimporter")
public class MaterialExcelImporter extends AbstractExcelImporter<HCMainData> {
	@Autowired
	private HCMainDataMapper hCMainDataMapper;
	@Resource
	private MaterialToPlaneModelMapper materialToPlaneModelMapper;
	@Resource
	private MaterialToStoreMapper materialToStoreMapper;
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private ActTypeMapper actTypeMapper;
	@Resource
	private FixChapterService fixChapterService;
	@Resource
	private StorageMapper storageMapper;
	@Resource
	private StoreMapper storeMapper;

	@Override
	public void validateParam(Map<Integer, List<HCMainData>> datas) throws ExcelImportException {
		HCMainData hcMainData;
		List<HCMainData> list = new ArrayList<HCMainData>();
		// 获取所有章节号
		List<String> fixChapterList = getAllFixChapter();
		// 所有仓库号
		List<String> ckhList = getAllCkh();	
		// 所有计量单位字典值
		List<Map<String,String>> jldwDictList = SysContext.getDicItemsByDicId(1, ThreadVarUtil.getUser().getJgdm());
		//获取所有机型
		List<String> jxList=getJxByDprtcode(this.getParam("dprtcode").toString());
		List<String> jldwList = new ArrayList<String>();
		if(jldwDictList != null && !jldwDictList.isEmpty()){
			for (Map<String,String> map : jldwDictList) {
				jldwList.add(map.get("id"));
			}
		}
		
		if (datas.size() == 0) {
			addErrorMessage("部件主数据号不能为空");
		} else {
			list.addAll(datas.get(0));
			List<String> bjhList = new ArrayList<String>();
			for (int j = 0; j < list.size(); j++) {
				hcMainData = list.get(j);

				if (hcMainData.getId() == null && hcMainData.getMaterialToPlaneModelList().size() == 0) {
					addErrorMessage("第1个工作表，第"+(j+3)+"行，部件主数据不能为空");
				}
				if(hcMainData.getBjh()==null){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，部件主数据部件号不能为空");
				}
				if(!StringUtils.isBlank(hcMainData.getBjh()) &&Utils.Str.isChinese(hcMainData.getBjh())){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，部件号不能含有中文");
				}
				if(!StringUtils.isBlank(hcMainData.getCjjh()) &&Utils.Str.isChinese(hcMainData.getCjjh())){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，厂家件号不能含有中文");
				}
				if(!StringUtils.isBlank(hcMainData.getBzjh()) &&Utils.Str.isChinese(hcMainData.getBzjh())){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，标准件号不能含有中文");
				}
				if(!StringUtils.isBlank(hcMainData.getGse()) &&Utils.Str.isChinese(hcMainData.getGse())){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，Gse不能含有中文");
				}
				if(!StringUtils.isBlank(hcMainData.getGg()) &&Utils.Str.isChinese(hcMainData.getGg())){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，规格不能含有中文");
				}
				if(!StringUtils.isBlank(hcMainData.getXingh()) &&Utils.Str.isChinese(hcMainData.getXingh())){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，型号不能含有中文");
				}

				if(hcMainData.getHcjz()!=null&&hcMainData.getHcjz()!=1&&hcMainData.getHcjz()!=2&&hcMainData.getHcjz()!=3){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，航材价值请输入1或2或3或不填写");
				}
				if(hcMainData.getGljb()!=null&&hcMainData.getGljb()!=1&&hcMainData.getGljb()!=2&&hcMainData.getGljb()!=3){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，管理级别请输入1或2或3或不填写");
				}
				if(hcMainData.getSxkz()!=null&&hcMainData.getSxkz()!=0&&hcMainData.getSxkz()!=1){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，时效控制请输入0或1或不填写");
				}
				if(hcMainData.getHclx()!=null&&hcMainData.getHclx()!=0&&hcMainData.getHclx()!=1&&hcMainData.getHclx()!=2&&hcMainData.getHclx()!=4&&hcMainData.getHclx()!=5&&hcMainData.getHclx()!=6){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，部件类型请输入0或1或2或4或5或6或不填写");
				}
				if(hcMainData.getHclxEj()!=null&&hcMainData.getHclxEj()!=11&&hcMainData.getHclxEj()!=12&&hcMainData.getHclxEj()!=21&&hcMainData.getHclxEj()!=22){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，二级类型请输入11或12或21或22或不填写");
				}
				if(hcMainData.getHclx()!=null&&hcMainData.getHclxEj()!=null){
					if(hcMainData.getHclx() == 1 && hcMainData.getHclxEj() !=11 && hcMainData.getHclxEj()!=12 ){
						addErrorMessage("第1个工作表，第"+(j+3)+"行，部件类型为1航材的时候，二级类型请输入11或12或不填写");
					}
					if(hcMainData.getHclx() == 2 && hcMainData.getHclxEj() !=21 && hcMainData.getHclxEj()!=22 ){
						addErrorMessage("第1个工作表，第"+(j+3)+"行，部件类型为2工具设备的时候，二级类型请输入21或22或不填写");
					}
				}
				if(hcMainData.getIsMel()!=null&&hcMainData.getIsMel()!=0&&hcMainData.getIsMel()!=1){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，MEL请输入0或1或不填写");
				}
				if (StringUtils.isBlank(hcMainData.getYwms())) {
					addErrorMessage("第1个工作表，第"+(j+3)+"行，英文描述不能为空");
				}
				if (StringUtils.isBlank(hcMainData.getJldw())) {
					addErrorMessage("第1个工作表，第"+(j+3)+"行，计量单位不能为空");
				}
				// 章节号存在验证
				if (!StringUtils.isBlank(hcMainData.getZjh()) && !fixChapterList.contains(hcMainData.getZjh())) {
					addErrorMessage("第1个工作表，第"+(j+3)+"行，章节号不存在");
				}

				// 计量单位存在验证
				if (!StringUtils.isBlank(hcMainData.getJldw()) && !jldwList.contains(hcMainData.getJldw())) {
					addErrorMessage("第1个工作表，第"+(j+3)+"行，计量单位不存在");
				}			
				
				if(Utils.Str.getLength(hcMainData.getBjh()) > 50){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，部件号的最大长度为50");
				}
				if(Utils.Str.getLength(hcMainData.getJhly()) > 100){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，件号来源的最大长度为100");
				}
				if(Utils.Str.getLength(hcMainData.getCjjh()) > 50){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，厂家件号的最大长度为50");
				}
				if(Utils.Str.getLength(hcMainData.getZzcs()) > 100){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，制造厂商的最大长度为100");
				}
				if(Utils.Str.getLength(hcMainData.getZwms()) > 100){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，中文描述的最大长度为100");
				}
				if(Utils.Str.getLength(hcMainData.getYwms()) > 100){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，英文描述的最大长度为100");
				}
				if(Utils.Str.getLength(hcMainData.getBzjh()) > 50){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，标准件号的最大长度为50");
				}
				if(Utils.Str.getLength(hcMainData.getGse()) > 50){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，Gse最大长度为50");
				}
				if(Utils.Str.getLength(hcMainData.getGg()) > 50){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，规格的最大长度为50");
				}
				if(Utils.Str.getLength(hcMainData.getXingh()) > 50){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，型号的最大长度为50");
				}
				if(Utils.Str.getLength(hcMainData.getBz()) > 300){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，备注的最大长度为300");
				}
				if(hcMainData.getMaxKcl()!=null&&hcMainData.getMaxKcl()==MaterialExcelImporter.BIGDECIMAL_TYPE_ERROR){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，最高库存量只能是数字");
				}				
				if(hcMainData.getMaxKcl()!=null&&Utils.Str.getLength(hcMainData.getMaxKcl().toString()) > 12){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，最高库存量的最大长度为12");
				}
				if(hcMainData.getMinKcl()!=null&&hcMainData.getMinKcl()==MaterialExcelImporter.BIGDECIMAL_TYPE_ERROR){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，最低库存量只能是数字");
				}	
				if(hcMainData.getMinKcl()!=null &&Utils.Str.getLength(hcMainData.getMinKcl().toString()) > 12){
					addErrorMessage("第1个工作表，第"+(j+3)+"行，最低库存量的最大长度为12");
				}
				// 自制件验证
				if (hcMainData.getZzjbs()!=null && (hcMainData.getZzjbs()!=0&&hcMainData.getZzjbs()!=1)) {
					addErrorMessage("第1个工作表，第"+(j+3)+"行，自制件只能为0或者1");
				}
				Map<String, String> plist = new HashMap<String, String>();
				MaterialToStore materialToStore = hcMainData.getMaterialToStore();
				if (materialToStore != null) {				
					if (materialToStore.getCkh() != null) {
						if (!ckhList.contains(materialToStore.getCkh())) {
							addErrorMessage("第1个工作表，第"+(j+3)+"行，仓库号不存在");
						} else {
							Store store = storeMapper.selectByPrimaryCkh(materialToStore.getCkh(),
									ThreadVarUtil.getUser().getJgdm());
							  if(store==null){
								  addErrorMessage("第1个工作表，第"+(j+3)+"行，仓库号不存在");
							  }else{
									materialToStore.setCkh(store.getId());
									if (materialToStore.getKwh() != null) {
										List<String> kwhList = getAllKwh(materialToStore.getCkh());
										if (!kwhList.contains(materialToStore.getKwh())) {
											addErrorMessage("第1个工作表，第"+(j+3)+"行，库位号不存在");
										} else {
											materialToStore.setKwh(storageMapper.getId(store.getId(),
													ThreadVarUtil.getUser().getJgdm(), materialToStore.getKwh()));
										}
									}
							  }
						
						}
					}
				}
				List<MaterialToPlaneModel> materialToPlaneModelList = hcMainData.getMaterialToPlaneModelList();
//				  if(materialToPlaneModelList!=null&&materialToPlaneModelList.size()==0){
//					  addErrorMessage("第1个工作表，部件号"+hcMainData.getBjh()+"对应的机型在第2个工作表中没有填写");
//				  }
				for (MaterialToPlaneModel materialToPlaneModel : materialToPlaneModelList) {
					if (!"00000".equals(materialToPlaneModel.getFjjx())) {
//						PlaneModelData planeModelData = new PlaneModelData();
//						planeModelData.setFjjx(materialToPlaneModel.getFjjx());
//						planeModelData.setDprtcode(this.getParam("dprtcode").toString());
//						int count = planeModelDataMapper.selectByFjjxAndDprtcode(planeModelData);
//						if (!jxList.contains(materialToPlaneModel.getFjjx())) {
//							addErrorMessage("第2个工作表，第"+(j+3)+"行，机型不存在");
//							break;
//						}
					}
					if (plist.size() > 0) {
						for (String str : plist.keySet()) {
							if (str.equals(materialToPlaneModel.getMainid())
									&& plist.get(str).equals(materialToPlaneModel.getFjjx())) {
								addErrorMessage("第1个工作表，第"+(j+3)+"行，对应的机型在第2个工作表中重复");
								break;
							}
						}
					}
					plist.put(materialToPlaneModel.getMainid(), materialToPlaneModel.getFjjx());
				}
				if (bjhList.size() > 0) {
					for (String str : bjhList) {
						if (hcMainData.getBjh().equals(str)) {
							addErrorMessage("第1个工作表，第"+(j+3)+"行，部件主数据部件号不能重复");
							break;
						}

					}
				}
				bjhList.add(hcMainData.getBjh());
			}
		}
	}

	@Override
	public int writeToDB(Map<Integer, List<HCMainData>> datas) throws ExcelImportException {
		String czls = UUID.randomUUID().toString();
		List<HCMainData> list = new ArrayList<HCMainData>();
		list.addAll(datas.get(0));
		MaterialToStore materialToStore;
		Map<String,String> map=getIdByBjh();
		
		for (HCMainData hcMainData : list) {
			if (hcMainData.getId() != null) {
				materialToStore = hcMainData.getMaterialToStore();
				List<MaterialToPlaneModel> materialToPlaneModelList = hcMainData.getMaterialToPlaneModelList();
				if (map.containsKey(hcMainData.getBjh())) {
					materialToStore.setMainid(map.get(hcMainData.getBjh()));
					materialToStoreMapper.updateByPrimaryMainid(materialToStore);
					if (materialToPlaneModelList.size() > 0) {
						List<MaterialToPlaneModel> mlist = materialToPlaneModelMapper
								.queryByMainId(map.get(hcMainData.getBjh()));
						if (mlist.size() > 0) {
							List<String> listId = new ArrayList<String>();
							for (MaterialToPlaneModel materialToPlaneModel2 : mlist) {
								listId.add(materialToPlaneModel2.getId());
							}
							commonRecService.write("id", listId, TableEnum.D_00801, ThreadVarUtil.getUser().getId(),
									czls, LogOperationEnum.EDIT, UpdateTypeEnum.DELETE, map.get(hcMainData.getBjh()));
							materialToPlaneModelMapper.deleteByMainid(map.get(hcMainData.getBjh()));
						}					
						for(int i=0;i<materialToPlaneModelList.size();i++){
							if("00000".equals(materialToPlaneModelList.get(i).getFjjx())){
								hcMainData.setXh("00000");
								break;
							}
							if(!"00000".equals(materialToPlaneModelList.get(i).getFjjx())&&i==materialToPlaneModelList.size()-1){
								hcMainData.setXh("-");
								for (MaterialToPlaneModel materialToPlaneModel : materialToPlaneModelList) {
									materialToPlaneModel.setMainid(map.get(hcMainData.getBjh()));
									materialToPlaneModelMapper.insertSelective(materialToPlaneModel);
									commonRecService.writeByWhere(
											"b.mainid = '" + map.get(hcMainData.getBjh()) + "' and b.fjjx='"
													+ materialToPlaneModel.getFjjx() + "'",
											TableEnum.D_00801, ThreadVarUtil.getUser().getId(), czls,
											LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE, map.get(hcMainData.getBjh()));
								}
							}
						}
					}
					hCMainDataMapper.updateByBjhDprtcode(hcMainData);
					commonRecService.write(map.get(hcMainData.getBjh()), TableEnum.D_008, ThreadVarUtil.getUser().getId(),
							czls, LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE, null);
					commonRecService.writeByWhere("b.mainid = '" + map.get(hcMainData.getBjh()) + "'", TableEnum.D_00802,
							ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,
							map.get(hcMainData.getBjh()));

				} else {
					materialToStoreMapper.insertSelective(materialToStore);
					for(int i=0;i<materialToPlaneModelList.size();i++){
						if("00000".equals(materialToPlaneModelList.get(i).getFjjx())){
							hcMainData.setXh("00000");
							break;
						}
						if(!"00000".equals(materialToPlaneModelList.get(i).getFjjx())&&i==materialToPlaneModelList.size()-1){
							hcMainData.setXh("-");
							for (MaterialToPlaneModel materialToPlaneModel : materialToPlaneModelList) {
								materialToPlaneModelMapper.insertSelective(materialToPlaneModel);
								commonRecService.writeByWhere(
										"b.mainid = '" + hcMainData.getId() + "' and b.fjjx='"
												+ materialToPlaneModel.getFjjx() + "'",
										TableEnum.D_00801, ThreadVarUtil.getUser().getId(), czls,
										LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE, hcMainData.getId());
							}
						}
					}
					hCMainDataMapper.insertSelective(hcMainData);
					commonRecService.write(hcMainData.getId(), TableEnum.D_008, ThreadVarUtil.getUser().getId(), czls,
							LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE, null);
					commonRecService.writeByWhere("b.mainid = '" + hcMainData.getId() + "'", TableEnum.D_00802,
							ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,
							hcMainData.getId());
				}
			} else {
				List<MaterialToPlaneModel> materialToPlaneModelList = hcMainData.getMaterialToPlaneModelList();
				List<String> fjjxList = new ArrayList<String>();
				for (MaterialToPlaneModel materialToPlaneModel : materialToPlaneModelList) {
					fjjxList.add(materialToPlaneModel.getFjjx());
				}
				if (fjjxList.contains("00000")) {
					hcMainData.setXh("00000");
				} else {
					hcMainData.setXh("-");
					for (MaterialToPlaneModel materialToPlaneModel : materialToPlaneModelList) {
						List<MaterialToPlaneModel> mlist = materialToPlaneModelMapper
								.queryByMainId(materialToPlaneModel.getMainid());
						if (mlist.size() > 0) {
							List<String> listId = new ArrayList<String>();
							for (MaterialToPlaneModel materialToPlaneModel2 : mlist) {
								listId.add(materialToPlaneModel2.getId());
							}
							commonRecService.write("id", listId, TableEnum.D_00801, ThreadVarUtil.getUser().getId(),
									czls, LogOperationEnum.EDIT, UpdateTypeEnum.DELETE,
									materialToPlaneModel.getMainid());
							materialToPlaneModelMapper.deleteByMainid(materialToPlaneModel.getMainid());
							materialToPlaneModelMapper.insertSelective(materialToPlaneModel);
							commonRecService.writeByWhere(
									"b.mainid = '" + materialToPlaneModel.getMainid() + "' and b.fjjx='"
											+ materialToPlaneModel.getFjjx() + "'",
									TableEnum.D_00801, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT,
									UpdateTypeEnum.UPDATE, materialToPlaneModel.getMainid());
						}
					}
				}
				hcMainData.setBmid(ThreadVarUtil.getUser().getBmdm());
				hcMainData.setCjrid(ThreadVarUtil.getUser().getId());
				hcMainData.setId(materialToPlaneModelList.get(0).getMainid());
				hCMainDataMapper.updateByPrimaryKeySelective(hcMainData);
				commonRecService.write(materialToPlaneModelList.get(0).getMainid(), TableEnum.D_008,
						ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE, null);
			}
		}
		return 1;
	}

	@Override
	public Map<Integer, List<HCMainData>> convertBean(Map<Integer, List<Map<Integer, String>>> mapList)
			throws ExcelImportException {
		User user = ThreadVarUtil.getUser();
		List<HCMainData> hcMainDataList = new ArrayList<HCMainData>();
		Map<Integer, List<HCMainData>> datas = new HashMap<Integer, List<HCMainData>>();
		//把mapList数据前后空格去除
		removeSpace(mapList);
		List<Map<Integer, String>> sheetlist1 = mapList.get(0);
		List<Map<Integer, String>> sheetlist2 = mapList.get(1);
		List<String> mlist = new ArrayList<String>();
		HCMainData hcMainData;
		MaterialToStore materialToStore;
		MaterialToPlaneModel materialToPlaneModel;// 航材机型关系表
		Map<Integer, String> bean;
		if (sheetlist1.size() > 0) {
			for (int i = 0; i < sheetlist1.size(); i++) {
				bean = sheetlist1.get(i);
				hcMainData = new HCMainData();
				materialToStore = new MaterialToStore();// 航材仓库关系表
				String id = UUID.randomUUID().toString();
				hcMainData.setId(id);// id
				hcMainData.setBjh(bean.get(0));// 部件号				
				if(bean.get(0)!=null){
				mlist.add(bean.get(0));
				}else{
					break;//部件号为空时 跳出循环
				}
				hcMainData.setZwms(bean.get(1));// 中文名称
				hcMainData.setYwms(bean.get(2));// 英文名称
				hcMainData.setBzjh(bean.get(3));//标准号
				hcMainData.setGse(bean.get(4));//GSE
				Integer self=0;
				  if(bean.get(5)==null){
					  self=0;
				  }else if(StringUtils.isNumeric(bean.get(5))){
					  self=Integer.valueOf(bean.get(5));
				  }else{
					  self=-1;
				  }				
				hcMainData.setZzjbs(self);//是否自制件
				
				hcMainData.setJldw(bean.get(6));// 计量单位
				hcMainData.setJhly(bean.get(7));//件号来源
				hcMainData.setCjjh(bean.get(8));// 厂家件号
				hcMainData.setZzcs(bean.get(9));//制造厂商
				hcMainData.setZjh(bean.get(10));// 章节号
				
				hcMainData.setGg(bean.get(11));// 规格
				hcMainData.setXingh(bean.get(12));// 型号
				hcMainData.setHcjz(convertToInteger(bean.get(13), 1));// 航材价值
				hcMainData.setGljb(convertToInteger(bean.get(14), 1));// 管理级别
				hcMainData.setSxkz(convertToInteger(bean.get(15), 0));// 时效控制
				hcMainData.setHclx(convertToInteger(bean.get(16), 1));// 部件类型
				if(hcMainData.getHclx()==1){
					hcMainData.setHclxEj(convertToInteger(bean.get(17), 11));// 航材二级类型					
				}
				if(hcMainData.getHclx()==2){
					hcMainData.setHclxEj(convertToInteger(bean.get(17), 21));// 航材二级类型	
				}
				hcMainData.setIsMel(convertToInteger(bean.get(18), 0));// MEL
				hcMainData.setZt(DelStatus.TAKE_EFFECT.getId());// 状态
				if (bean.get(21) != null) {
					hcMainData.setMinKcl(convertToBigDecimal(bean.get(20)));
					// hcMainData.setMinKcl(new
					// BigDecimal(Integer.valueOf(bean.get(16))));// 最低库存量
				}
				if (bean.get(22) != null) {
					hcMainData.setMaxKcl(convertToBigDecimal(bean.get(21)));
					// hcMainData.setMaxKcl(new
					// BigDecimal(Integer.valueOf(bean.get(17))));// 最高库存量
				}
				hcMainData.setBz(bean.get(23));// 备注
				hcMainData.setDprtcode(this.getParam("dprtcode").toString());// dprtcode
				hcMainData.setCjrid(user.getId());// 创建人
				hcMainData.setBmid(user.getBmdm());// 部门id
				String mid = UUID.randomUUID().toString();
				materialToStore.setId(mid);//
				if (bean.get(19) != null) {
					materialToStore.setCkh(bean.get(19));// 建议仓库
				}
				if (bean.get(20) != null) {
					materialToStore.setKwh(bean.get(20));// 建议库位
				}
				materialToStore.setMainid(id);
				materialToStore.setWhrid(user.getId());// 维护人
				hcMainData.setMaterialToStore(materialToStore);
				List<MaterialToPlaneModel> materialToPlaneModelList = new ArrayList<MaterialToPlaneModel>();
				for (int j = 0; j < sheetlist2.size(); j++) {
					bean = sheetlist2.get(j);
					if (hcMainData.getBjh()!=null&&hcMainData.getBjh().equals(bean.get(0))) {

						materialToPlaneModel = new MaterialToPlaneModel();
						String palneModelId = UUID.randomUUID().toString();
						materialToPlaneModel.setId(palneModelId);
						materialToPlaneModel.setFjjx(bean.get(1));
						materialToPlaneModel.setWhrid(user.getId());
						materialToPlaneModel.setMainid(id);
						materialToPlaneModelList.add(materialToPlaneModel);
					}
				}
				hcMainData.setMaterialToPlaneModelList(materialToPlaneModelList);
				hcMainDataList.add(hcMainData);
				datas.put(i, hcMainDataList);
			}
			if (mlist.size() > 0) {
				int size = datas.size();
				for (int j = 0; j < sheetlist2.size(); j++) {
					bean = sheetlist2.get(j);
					for (int k = 0; k < mlist.size(); k++) {
						if (mlist.get(k)!=null&&mlist.get(k).equals(bean.get(0))) {
							break;
						}
						if (mlist.get(k)!=null&&!mlist.get(k).equals(bean.get(0)) && k == mlist.size() - 1) {
							hcMainData = new HCMainData();
							HCMainData hc = new HCMainData();
							List<MaterialToPlaneModel> materialToPlaneModelList = new ArrayList<MaterialToPlaneModel>();
							hc.setBjh(bean.get(0));
							hc.setDprtcode(this.getParam("dprtcode").toString());
							List<HCMainData> materialList = hCMainDataMapper.checkMaterial(hc);
							if (materialList.size() > 0) {
								for (int a = 0; a < materialList.size(); a++) {
									materialToPlaneModel = new MaterialToPlaneModel();
									materialToPlaneModel.setMainid(materialList.get(a).getId());
									materialToPlaneModel.setId(UUID.randomUUID().toString());
									materialToPlaneModel.setFjjx(bean.get(1));
									materialToPlaneModel.setWhrid(user.getId());
									materialToPlaneModelList.add(materialToPlaneModel);
								}
								materialList.get(0).setMaterialToPlaneModelList(materialToPlaneModelList);
								hcMainDataList.add(materialList.get(0));
								datas.put(++size, hcMainDataList);
							}
						}
					}
				}
			}
		}
		// 当部件主数据为空时部件对应机型不为空
		if (sheetlist1.size() == 0 && sheetlist2.size() > 0) {
			List<MaterialToPlaneModel> materialToPlaneModelList = new ArrayList<MaterialToPlaneModel>();
			hcMainData = new HCMainData();
			for (int j = 0; j < sheetlist2.size(); j++) {
				bean = sheetlist2.get(j);
				HCMainData hc = new HCMainData();
				hc.setBjh(bean.get(0));
				hc.setDprtcode(ThreadVarUtil.getUser().getJgdm());
				List<HCMainData> materialList = hCMainDataMapper.checkMaterial(hc);
				if (materialList.size() > 0) {
					for (int k = 0; k < materialList.size(); k++) {
						materialToPlaneModel = new MaterialToPlaneModel();
						materialToPlaneModel.setMainid(materialList.get(k).getId());
						materialToPlaneModel.setId(UUID.randomUUID().toString());
						materialToPlaneModel.setFjjx(bean.get(1));
						materialToPlaneModel.setWhrid(user.getId());
						materialToPlaneModelList.add(materialToPlaneModel);
					}
					materialList.get(0).setMaterialToPlaneModelList(materialToPlaneModelList);
					hcMainDataList.add(materialList.get(0));
				}
			}
			datas.put(0, hcMainDataList);

		}
		return datas;
	}
	
    private boolean lineIsNull(Map<Integer, String> bean) {
		boolean flg=true;
		 for(Integer i:bean.keySet()){
			 if(i==-1)continue;
			 if(bean.get(i)!=null&&!"".equals(bean.get(i))){
				 flg=false;
				 break;
			 }
		 }
		return flg;
	}

	//去除空格
	private void removeSpace(Map<Integer, List<Map<Integer, String>>> mapList) {
		List<Map<Integer, String>> list = null;
		Map<Integer, String> map = null;
		for (Integer key : mapList.keySet()) {
			list = mapList.get(key);
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					map = list.get(i);
					if (map != null) {
						for (Integer no : map.keySet()) {
							String value = map.get(no);
							if (!StringUtils.isEmpty(value)) {
								map.put(no, value.trim());
							}
						}

					}

				}
			}
		}
		
	}

	/**
	 * 判断是否是整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNum(String str) {
		try {
			new BigDecimal(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取所有章节号
	 * 
	 * @return
	 */
	private List<String> getAllFixChapter() {
		try {
			List<String> resultList = new ArrayList<String>();
			FixChapter param = new FixChapter();
			param.setDprtcode(ThreadVarUtil.getUser().getJgdm());
			List<FixChapter> fixChapters = fixChapterService.selectByDprtcode(param);
			for (FixChapter fixChapter : fixChapters) {
				resultList.add(fixChapter.getZjh());
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取所有仓库号
	 * 
	 * @return
	 */
	private List<String> getAllCkh() {
		try {
			List<String> resultList = new ArrayList<String>();
			List<Store> stores = storeMapper.findAll();
			for (Store store : stores) {
				resultList.add(store.getCkh());
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据仓库号获取所有库位号
	 * 
	 * @return
	 */
	private List<String> getAllKwh(String storeCode) {
		try {
			List<String> resultList = new ArrayList<String>();
			List<Storage> storages = storageMapper.queryStorageListByStoreId(storeCode);
			for (Storage storage : storages) {
				resultList.add(storage.getKwh());
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * 获取该组织结构下所有机型
	 * @param dprtcode
	 * @return
	 */
	private List<String> getJxByDprtcode(String dprtcode){
		List<String> resultList=new ArrayList<String>();
		List<ActType> planeDate=actTypeMapper.findByDprtcode(dprtcode);
		for (ActType planeModelData : planeDate) {
			resultList.add(planeModelData.getFjjx());
		}
		return resultList;
	}
	
	private Map<String,String> getIdByBjh(){
		Map<String,String> map=new HashMap<String,String>();
		List<HCMainData> list=hCMainDataMapper.getHcByDprtcode(this.getParam("dprtcode").toString());
		for (HCMainData hcMainData : list) {
			map.put(hcMainData.getBjh(), hcMainData.getId());
		}
		return map;
	}
	
	//校验对象所有属性纸是否为空
	private boolean validateObjectPropertyNull(Object obj){
		if(obj==null) return true;
		 Class clz=obj.getClass();
		 Field[] fields= clz.getDeclaredFields();
		 Field field=null;
		 String typeName=null;;
		 for(int i=0;i<fields.length;i++){
			 try {
				 boolean isBasic=false;
				 field=fields[i];
				 field.setAccessible(true);
				 typeName=field.getType().getName();
				 //如果是基本数据类型
				 if("byte".equals(typeName)){
					   isBasic=true;
				   if(0!=field.getByte(obj)){
					   return false;
				   }   										
				 }
				 if("short".equals(typeName)){
					  isBasic=true;
					 if(0!=field.getShort(obj)){
						 return false;
					 }	 
				 }
				 if("int".equals(typeName)){
					  isBasic=true;
					 if(0!=field.getInt(obj)){
						 return false;
					 }			 
				 }
				 if("long".equals(typeName)){
					  isBasic=true;
					 if(0!=field.getLong(obj)){
						 return false;
					 }						 
				 }
				 if("boolean".equals(typeName)){
					  isBasic=true;
					 if(false!=field.getBoolean(obj)){
						 return false;
					 }			 
				 }
				 if("double".equals(typeName)){
					  isBasic=true;
					 if(0.0!=field.getDouble(obj)){
						 return false;
					 }		 
				 }
				 if("float".equals(typeName)){
					 isBasic=true;
					 if(0.0!=field.getFloat(obj)){
						 return false;
					 }	 
				 }
				 if("char".equals(typeName)){
					 isBasic=true;
					 if('\u0000'!=field.getChar(obj)){
						 return false;
					 }
				 }
				 if(!isBasic){//引用数据类型(排除集合和数组)
					 if(!(field.get(obj) instanceof List||field.get(obj) instanceof Object[])){
						 if(null!=field.get(obj))
							  return false; 
					 }
					 
				 }
				 
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		   return true;
	}
}
