package com.eray.thjw.project2.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project.po.MinimumEquipmentList;
import com.eray.thjw.project.service.MelDetailService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author liub
 * @description MELController类
 */
@RequestMapping("/project2/meldetail")
@Controller("MelDetailController2")
public class MelDetailController extends BaseController {

	@Autowired
	private MelDetailService melDetailService;

	/**
	 * @author liub
	 * @description 跳转至mel界面
	 * @return mel的路径
	 */
	@Privilege(code = "project2:meldetail:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("project2/mel/mel_detail_main", responseParamMap);
	}
	
	
	/**
	 * @author liub
	 * @description 增加Mel更改单
	 * @param maintenance
	 * @return String
	 */
	@Privilege(code="project:meldetail:main:01")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@RequestBody MinimumEquipmentList minimumEquipmentList) throws BusinessException{	
		try {
			return melDetailService.save(minimumEquipmentList);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("保存失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 修改Mel更改单
	 * @param maintenance
	 * @return String
	 */
	@Privilege(code="project:meldetail:main:02")
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(@RequestBody MinimumEquipmentList minimumEquipmentList) throws BusinessException{	
		try {
			return melDetailService.edit(minimumEquipmentList);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("保存失败!",e);
		}
	}
	
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询信息
	 * @param melChangeSheet
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody MinimumEquipmentList minimumEquipmentList)throws BusinessException {
		String id = minimumEquipmentList.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		minimumEquipmentList.setId(null);
		try {
			PageHelper.startPage(minimumEquipmentList.getPagination());
			List<MinimumEquipmentList> list = melDetailService.queryAllPageList(minimumEquipmentList);
			if(((Page)list).getTotal() > 0){
				//分页查询
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						MinimumEquipmentList newRecord = new MinimumEquipmentList();
						newRecord.setId(id);
						List<MinimumEquipmentList> newRecordList = melDetailService.queryAllPageList(newRecord);
						if(newRecordList != null && newRecordList.size() == 1){
							//将记录放入结果集第一条
							list.add(0, newRecordList.get(0));
						}
					}
				}
				return PageUtil.pack4PageHelper(list, minimumEquipmentList.getPagination());
			}else{
				List<MinimumEquipmentList> newRecordList = new ArrayList<MinimumEquipmentList>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					MinimumEquipmentList newRecord = new MinimumEquipmentList();
					newRecord.setId(id);
					newRecordList = melDetailService.queryAllPageList(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						return PageUtil.pack(1, newRecordList, minimumEquipmentList.getPagination());
					}
				}
				return PageUtil.pack(0, newRecordList, minimumEquipmentList.getPagination());
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据航材id查询
	 * @param id
	 * @return MelChangeSheet
	 */
	@ResponseBody
	@RequestMapping(value = "selectById",method={RequestMethod.POST,RequestMethod.GET})
	public MinimumEquipmentList selectById(Model model,String id) throws BusinessException {
		MinimumEquipmentList list = null;
		try {
			list = melDetailService.selectById(id);
		} catch (Exception e) {
			throw new BusinessException("查询失败!",e);
		}
		return list;
	}
}



