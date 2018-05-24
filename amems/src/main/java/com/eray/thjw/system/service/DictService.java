package com.eray.thjw.system.service;

import java.util.List;
import com.eray.thjw.system.po.Dict;

public interface DictService {
	/**
	 * 根据组织机构查询字典项
	 * @param newdic
	 * @return List<NewDic>
	 * @author Meizhiliang
	 */
	public List<Dict> getNewDicListByDprtcode(Dict  newdic);   
	
}
