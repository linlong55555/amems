package com.eray.thjw.system.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.po.User;
import com.eray.thjw.system.dao.DictItemMapper;
import com.eray.thjw.system.dao.DictMapper;
import com.eray.thjw.system.po.Dict;
import com.eray.thjw.system.po.DictItem;
import com.eray.thjw.system.service.DictItemService;
import com.eray.thjw.util.ThreadVarUtil;

@Service
public class DictItemServiceImpl implements DictItemService {
    @Resource
    private DictItemMapper dictItemMapper;
    @Resource
    private DictMapper dictMapper;

	@Override
	public List<DictItem> getDictItemList(DictItem dictItem) {
		return dictItemMapper.getDictItemList(dictItem);
	}

	@Override
	public String insertSelective(DictItem dictItem) {
		String massge="";
		User user=ThreadVarUtil.getUser();
		String id=UUID.randomUUID().toString();
		dictItem.setId(id);
		dictItem.setWhrid(user.getId());
		Dict dict = dictMapper.selectDict(dictItem.getLxid(),dictItem.getDprtcode());
		if(dict!=null){          //判断是否存在字典项                             
			dictItem.setId(id);
			int num=dictItemMapper.getDictItemCount(dictItem);
			if(num==0){
				dictItemMapper.insertSelective(dictItem);
				massge="success";
			}else{
				massge="数据已存在！";
			}
		}else{
			copyDictAndDictItem(dictItem);       //复制字典项和字典明细
			int num=dictItemMapper.getDictItemCount(dictItem);
			if(num==0){
				dictItemMapper.insertSelective(dictItem);
				massge="success";
			}else{
				massge="数据已存在！";
			}
		}
		return massge;
	}
	/**
	 * 复制字典项和明细
	 * @author Meizhiliang
	 */
	private void copyDictAndDictItem(DictItem dictItem){
		dictMapper.insertSelective(dictItem);
		dictItemMapper.insertDictItem(dictItem);
	}
	
	/**
	 * 
	 * @Description 
	 * @CreateTime 2017年8月17日 下午10:07:24
	 * @CreateBy Meizhiliang
	 * @UpdateBy 李高升
	 * @param dictItem
	 * @return
	 */
	@Override
	public String updateByPrimaryKeySelective(DictItem dictItem) {
		String massge="";
		User user=ThreadVarUtil.getUser();
		dictItem.setWhrid(user.getId());
		Dict dict = dictMapper.selectDict(dictItem.getLxid(),dictItem.getDprtcode());
		if(dict!=null){          //判断是否存在字典项                             
			int num=dictItemMapper.getDictItemCount(dictItem);
			if(num==0){
				dictItemMapper.updateByPrimaryKeySelective(dictItem);;
				massge="success";
			}else{
				massge="数据已存在！";
			}
		}else{
			copyDictAndDictItem(dictItem);       //复制字典项和字典明细
			int num=dictItemMapper.getDictItemCount(dictItem);			
			if(num==1){
				dictItemMapper.updateByPrimaryKeySelective(dictItem);;
				massge="success";
			}else{
				massge="数据已存在！";
			}
		}
		return massge;
	}

	@Override
	public String deleteByDictItem(DictItem dictItem) {
		String massge="";
		Dict dict = dictMapper.selectDict(dictItem.getLxid(),dictItem.getDprtcode());
		if(dict!=null){          //判断是否存在字典项                             
			dictItemMapper.delete(dictItem);
			massge="success";
		}else{
			copyDictAndDictItem(dictItem);       //复制字典项和字典明细
			dictItemMapper.delete(dictItem);
			massge="success";
		}
		return massge;
	}

	@Override
	public String doSynch(Integer lxid, String dprtcode) {
		dictMapper.deleteByPrimaryKey(lxid, dprtcode);
		dictItemMapper.deleteByKey(lxid, dprtcode);
		String massge="success";
		return massge;
	}
}
