package com.eray.thjw.aerialmaterial.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.StorageMapper;
import com.eray.thjw.aerialmaterial.dao.StoreMapper;
import com.eray.thjw.aerialmaterial.po.Storage;
import com.eray.thjw.aerialmaterial.po.Store;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.MaintenanceClass;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service("storageexcelimporter")
public class StorageExcelImporter extends AbstractExcelImporter<Storage> {
	@Autowired
	private StorageMapper storageMapper;
	@Autowired
	private StoreMapper storeMapper;
	@Resource
	private CommonRecService commonRecService;

	@Override
	public void validateParam(Map<Integer, List<Storage>> datas) throws ExcelImportException {
		Storage storage;
		List<Storage> list = new ArrayList<Storage>();
		if (datas.size() == 0) {
			addErrorMessage("模板内容不能为空");
		} else {
			//校验库位号在文本的唯一性
	    	  validatUnique(datas.get(0));
			list.addAll(datas.get(0));
			for (int j = 0; j < list.size(); j++) {
				storage = list.get(j);
				if (StringUtils.isEmpty(storage.getKwh())) {
					addErrorMessage("第1个工作表，第"+(j+3)+"行库位号不能为空");
				}
				if(!StringUtils.isBlank(storage.getKwh()) &&Utils.Str.isChinese(storage.getKwh())){
					addErrorMessage("第1个工作表，第"+(j+3)+"行库位号不能含有中文");
				}
				if(Utils.Str.getLength(storage.getKwh()) > 50){
					addErrorMessage("第1个工作表，第"+(j+3)+"行库位号的最大长度为50");
				}
				if(Utils.Str.getLength(storage.getBz()) > 300){
					addErrorMessage("第1个工作表，第"+(j+3)+"行备注的最大长度为300");
				}
			}
		}
	}

	@Override
	public int writeToDB(Map<Integer, List<Storage>> datas) throws ExcelImportException {
		List<Storage> list = new ArrayList<Storage>();
		list.addAll(datas.get(0));
		String czls = UUID.randomUUID().toString();
	
		String id = list.get(0).getMainid();
		
		for (Storage storage : list) {
			String checkId=storageMapper.selectByMainidAndKwh(storage);
			if(checkId!=null){
				storage.setId(checkId);
				storageMapper.updateByPrimaryKeySelective(storage);
				commonRecService.writeByWhere(
						"b.mainid = '" + id + "' and b.kwh='"
								+storage.getKwh()  + "'",
						TableEnum.D_00901, ThreadVarUtil.getUser().getId(), czls,
						LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE, id);
			}
			else{
				storageMapper.insertSelective(storage);
				commonRecService.writeByWhere(
						"b.mainid = '" + id + "' and b.kwh='"
								+storage.getKwh()  + "'",
						TableEnum.D_00901, ThreadVarUtil.getUser().getId(), czls,
						LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE, id);
			}
		}	
		User user = ThreadVarUtil.getUser();
		String id1 = this.getParam("id").toString();
		Store store=new Store();
		store.setBmid(user.getBmdm());
		store.setCjrid(user.getId());
		store.setId(id1);
		storeMapper.updateByPrimaryKeySelective(store);
		commonRecService.write(id, TableEnum.D_009, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT,
				UpdateTypeEnum.UPDATE,null);
		return 1;
	}

	@Override
	public Map<Integer, List<Storage>> convertBean(Map<Integer, List<Map<Integer, String>>> mapList)
			throws ExcelImportException {
		User user = ThreadVarUtil.getUser();
		List<Storage> list = new ArrayList<Storage>();
		Map<Integer, List<Storage>> datas = new HashMap<Integer, List<Storage>>();
		String id1 = this.getParam("id").toString();
		Store store= storeMapper.selectByPrimaryKey(id1);
		String dprtcode = store.getDprtcode();
		String ckh =store.getCkh() ;
		Integer cklb = store.getCklb();
		Storage storage;
		List<Map<Integer, String>> resultList = mapList.get(0);
		Map<Integer, String> bean;
		for (int i = 0; i < resultList.size(); i++) {
			bean = resultList.get(i);
			//if (bean.get(0) != null) {
				storage = new Storage();
				String id = UUID.randomUUID().toString();
				storage.setMainid(id1);
				storage.setId(id);
				storage.setCkh(ckh);
				storage.setKwh(bean.get(0));
				storage.setBz(bean.get(1));
				storage.setZt(1);
				storage.setCjrid(user.getId());
				storage.setBmid(user.getBmdm());
				storage.setDprtcode(dprtcode);
				storage.setCklb(cklb);
				list.add(storage);
				datas.put(i, list);
			//}
		}
		return datas;
	}
	   //校验库位号唯一性
		private void validatUnique(List<Storage> datas) {
		Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
		List<Integer> li = null;
		String kwh = "";
		for (int i = 0; i < datas.size(); i++) {
			kwh = datas.get(i).getKwh();
			if (map.containsKey(kwh)) {
				li = map.get(kwh);
				li.add(i + 3);
			} else {
				li = new ArrayList<Integer>();
				li.add(i + 3);
			}
			map.put(kwh, li);
		}

		StringBuilder builder = new StringBuilder("");
		for (String key : map.keySet()) {
			li = map.get(key);
			if (li.size() == 1)
				continue;
			for (Integer num : li) {
				builder.append(num).append(",");
			}
			addErrorMessage("第1个工作表，第"
					       + builder.toString().substring(0,
							builder.toString().length() - 1) + "行，库位号重复");
			builder.delete(0, builder.length());
		}
	}
}