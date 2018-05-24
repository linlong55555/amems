package com.eray.thjw.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.FirmMapper;
import com.eray.thjw.aerialmaterial.po.Firm;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.system.dao.ContactPersonMapper;
import com.eray.thjw.system.po.ContactPerson;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service("contactpersonexcelimporter")
public class ContactPersonExcelImporter extends AbstractExcelImporter<ContactPerson>{
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
    private FirmMapper firmMapper;
	
	@Resource
    private ContactPersonMapper contactPersonMapper;
	

	/**
	 * 参数验证
	 * @throws ExcelImportException 
	 */
	@Override
	public void validateParam(Map<Integer, List<ContactPerson>> datasMap) throws ExcelImportException {
		
		// 循环工作表
		for (Integer sheetIndex : datasMap.keySet()) {
			// 工作表对应的的装机清单数据
			List<ContactPerson> datas = datasMap.get(sheetIndex);
			// 该sheet数据为空，则直接读取下个sheet
			if(datas.isEmpty()){
				continue;
			}
			// sheetName
			String sheetName = String.valueOf(datas.get(0).getParamsMap().get("sheetName"));
			ContactPerson person;
			if(sheetName.equals("联系人")){
				for (int i = 0; i < datas.size(); i++) {
					person = datas.get(i);
					
					// 非空验证
					if(StringUtils.isBlank(person.getLxr())){
						addErrorMessage("联系人，第"+(i+3)+"行，联系人不能为空");
					}
					
					// 非中文验证
					String gysbh = String.valueOf(person.getParamsMap().get("gysbh"));
					if(!StringUtils.isBlank(gysbh) && Utils.Str.isChinese(gysbh)){
						addErrorMessage("联系人，第"+(i+3)+"行，供应商编号不能含有中文");
					}
					if(!StringUtils.isBlank(person.getWx()) && Utils.Str.isChinese(person.getWx())){
						addErrorMessage("联系人，第"+(i+3)+"行，微信不能含有中文");
					}
					//类型验证
					String gyslb = String.valueOf(person.getParamsMap().get("gyslb"));
					if(!StringUtils.isBlank(gyslb) && !gyslb.equals("1") && !gyslb.equals("2") ){
						addErrorMessage("联系人，第"+(i+3)+"行，类型填写范围为1和2或者不填写");
					}
					
					// 手机号验证
					if(!StringUtils.isBlank(person.getSj()) && !Utils.Str.isMobileNumber(person.getSj())){
						addErrorMessage("联系人，第"+(i+3)+"行，手机号格式不正确");
					}
					
					// 固定电话验证
					if(!StringUtils.isBlank(person.getZj()) && Utils.Str.isChinese(person.getZj())){
						addErrorMessage("联系人，第"+(i+3)+"行，座机不能输入中文");
					}
					
					// 传真号验证
					if(!StringUtils.isBlank(person.getCz()) && Utils.Str.isChinese(person.getCz())){
						addErrorMessage("联系人，第"+(i+3)+"行，传真号不能输入中文");
					}
					
					// 邮箱验证
					if(!StringUtils.isBlank(person.getYxdz()) && !Utils.Str.isEmail(person.getYxdz())){
						addErrorMessage("联系人，第"+(i+3)+"行，邮箱地址格式不正确");
					}
					
					// QQ验证
					if(!StringUtils.isBlank(person.getQq()) && !Utils.Str.isQQNumber(person.getQq())){
						addErrorMessage("联系人，第"+(i+3)+"行，QQ号格式不正确");
					}
					
					// 长度验证
					if(Utils.Str.getLength(gysbh) > 50){
						addErrorMessage("联系人，第"+(i+3)+"行，供应商编号的最大长度为50");
					}
					if(Utils.Str.getLength(person.getGysmc()) > 100){
						addErrorMessage("联系人，第"+(i+3)+"行，供应商名称的最大长度为100");
					}
					if(Utils.Str.getLength(person.getLxr()) > 100){
						addErrorMessage("联系人，第"+(i+3)+"行，联系人的最大长度为100");
					}
					if(Utils.Str.getLength(person.getZw()) > 100){
						addErrorMessage("联系人，第"+(i+3)+"行，职位的最大长度为100");
					}
					if(Utils.Str.getLength(person.getWx()) > 50){
						addErrorMessage("联系人，第"+(i+3)+"行，微信的最大长度为50");
					}
					if(Utils.Str.getLength(person.getBz()) > 300){
						addErrorMessage("联系人，第"+(i+3)+"行，备注的最大长度为300");
					}
					if(Utils.Str.getLength(person.getZj()) > 20){
						addErrorMessage("联系人，第"+(i+3)+"行，座机的最大长度为20");
					}
					if(Utils.Str.getLength(person.getCz()) > 20){
						addErrorMessage("联系人，第"+(i+3)+"行，传真号的最大长度为20");
					}
					if(Utils.Str.getLength(person.getYxdz()) > 100){
						addErrorMessage("联系人，第"+(i+3)+"行，邮箱地址的最大长度为100");
					}
				}
			}
		}
	}
	
	@Override
	public int writeToDB(Map<Integer, List<ContactPerson>> datasMap) {
		// 批量插入装机清单数据
		int count = 0;
		// 汇总所有sheet的装机清单数据
		List<ContactPerson> datas = new ArrayList<ContactPerson>();
		for (Integer sheetIndex : datasMap.keySet()) {
			datas = datasMap.get(sheetIndex);
			// 该sheet数据为空，则直接读取下个sheet
			if(datas.isEmpty()){
				continue;
			}
			// sheetName
			String sheetName = String.valueOf(datas.get(0).getParamsMap().get("sheetName"));
			if(sheetName.equals("联系人")){
				// 获取所有联系人
				Map<String, ContactPerson> persons = getAllContactPersons();
				// 写入日志
				String czls = UUID.randomUUID().toString();
				List<ContactPerson> insertList = new ArrayList<ContactPerson>();
				List<ContactPerson> updateList = new ArrayList<ContactPerson>();
				for (ContactPerson person : datas) {
					if(StringUtils.isNotBlank(person.getSj()) 
							&& persons.containsKey(person.getSj()+person.getLxr())){
						person.setId(persons.get(person.getSj()+person.getLxr()).getId());
						updateList.add(person);
					}else{
						person.setId(UUID.randomUUID().toString());
						insertList.add(person);
					}
				}
				// 批量新增联系人
				count += batchInsert(insertList);
				// 批量修改联系人
				count += batchUpdate(updateList);
				if(!insertList.isEmpty()){
					commonRecService.write("id", getIds(insertList), TableEnum.D_016, ThreadVarUtil.getUser().getId(), czls,
							LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE, "");
				}
				
				if(!updateList.isEmpty()){
					commonRecService.write("id", getIds(updateList), TableEnum.D_016, ThreadVarUtil.getUser().getId(), czls,
							LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE, "");
				}
			}
		}
		return count;
	}
	
	/**
	 * 获取对象id集合
	 * @param list
	 * @return
	 */
	private List<String> getIds(List<ContactPerson> list){
		List<String> resultList = new ArrayList<String>();
		for (ContactPerson person : list) {
			resultList.add(person.getId());
		}
		return resultList;
	}
	
	/**
	 * 批量新增联系人
	 * @param insertList
	 * @return
	 */
	private int batchInsert(List<ContactPerson> insertList){
		int count = 0;
		List<ContactPerson> temp = new ArrayList<ContactPerson>();
		for (int i = 0; i < insertList.size(); i++) {
			temp.add(insertList.get(i));
			if(temp.size() >= 500 || i == insertList.size() - 1){
				count += contactPersonMapper.batchInsert(temp);
				temp.clear();
			}
		}
		return count;
	}
	
	/**
	 * 批量修改联系人
	 * @param updateList
	 * @return
	 */
	private int batchUpdate(List<ContactPerson> updateList){
		int count = 0;
		List<ContactPerson> temp = new ArrayList<ContactPerson>();
		for (int i = 0; i < updateList.size(); i++) {
			temp.add(updateList.get(i));
			if(temp.size() >= 500 || i == updateList.size() - 1){
				count += contactPersonMapper.batchUpdate(temp);
				temp.clear();
			}
		}
		return count;
	}
	
	/**
	 * 获取所有联系人
	 * @return
	 */
	private Map<String, ContactPerson> getAllContactPersons(){
		Map<String, ContactPerson> resultMap = new HashMap<String, ContactPerson>();
		List<ContactPerson> persons= contactPersonMapper.queryByDprtcode(ThreadVarUtil.getUser().getJgdm());
		for (ContactPerson contactPerson : persons) {
			if(StringUtils.isNotBlank(contactPerson.getSj())){
				resultMap.put(contactPerson.getSj()+contactPerson.getLxr(), contactPerson);
			}
		}
		return resultMap;
	}

	/**
	 * 转化为对应实体类
	 * @throws ExcelImportException 
	 */
	@Override
	public Map<Integer, List<ContactPerson>> convertBean(Map<Integer, List<Map<Integer, String>>> totalMapList) throws ExcelImportException {
		// 结果集
		Map<Integer, List<ContactPerson>> resultMap = new TreeMap<Integer, List<ContactPerson>>();
		// 所有航材供应商
		Map<String, Firm> firmMap = getAllFirm("1");
		//外委供应商
		Map<String, Firm> wwfirmMap = getAllFirm("2");
		User user = ThreadVarUtil.getUser();
		// 循环sheet
		for (Integer sheetIndex : totalMapList.keySet()) {
			// sheet对应装机清单数据
			List<Map<Integer, String>> mapList = totalMapList.get(sheetIndex);
			if(mapList.isEmpty()){
				continue;
			}
			// sheetName
			String sheetName = mapList.get(0).get(-1);
			List<ContactPerson> list = new ArrayList<ContactPerson>();
			if(sheetName.equals("联系人")){
				ContactPerson person;
				Map<Integer, String> bean;
				list.clear();
				for (int i = 0; i < mapList.size(); i++) {
					bean = mapList.get(i);
					person = new ContactPerson();
					/*
					 * 读取excel值
					 */
					person.getParamsMap().put("gysbh", (bean.get(0)));	// 供应商编号
					person.setGysmc(bean.get(1));	// 章节号
					person.setLxr(bean.get(2));	// 联系人
					person.setZw(bean.get(4));	// 职位
					person.setSj(bean.get(5));	// 手机号
					person.setZj(bean.get(6));	// 座机号
					person.setCz(bean.get(7));	// 传真号
					person.setYxdz(bean.get(8));	// 邮箱
					person.setQq(bean.get(9));	// QQ号
					person.setWx(bean.get(10));	// 微信号
					person.setBz(bean.get(11));	// 备注
					person.setWhrid(user.getId());	// 维护人id
					person.setDprtcode(user.getJgdm());	// 组织机构
					person.setZt(1);	// 状态
					person.getParamsMap().put("sheetName", (bean.get(-1)));
					String gyslb ="1";
					if(null != bean.get(3) && !"".equals(bean.get(4))){
						  gyslb = bean.get(3);	//供应商类别
					}
					person.getParamsMap().put("gyslb", gyslb);
					/*
					 * 带出供应商数据
					 */
					if(!"1".equals(gyslb)){
						if(wwfirmMap.containsKey(bean.get(0))){
							Firm firm = wwfirmMap.get(bean.get(0));
							person.setCsid(firm.getId());
							person.setGysmc(firm.getGysmc());
						}else{
							person.getParamsMap().remove("gysbh");
						}
					}else{
						if(firmMap.containsKey(bean.get(0))){
							Firm firm = firmMap.get(bean.get(0));
							person.setCsid(firm.getId());
							person.setGysmc(firm.getGysmc());
						}else{
							person.getParamsMap().remove("gysbh");
						}
					}
					
					list.add(person);
				}
			}
			resultMap.put(sheetIndex, list);
		}
		return resultMap;
	}
	
	/**
	 * 获取所有供应商
	 * @return
	 */
	private Map<String, Firm> getAllFirm(String gyslb){
		Map<String, Firm> resultMap = new HashMap<String, Firm>();
		Firm param = new Firm();
		param.setDprtcode(ThreadVarUtil.getUser().getJgdm());
		param.setGyslb(gyslb);
		List<Firm> firms= firmMapper.queryAllFirmList(param);
		for (Firm firm : firms) {
			resultMap.put(firm.getGysbm(), firm);
		}
		return resultMap;
	}

}
