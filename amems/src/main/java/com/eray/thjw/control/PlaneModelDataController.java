package com.eray.thjw.control;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.aerialmaterial.service.HCMainDataService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.AbstractEntity;
import com.eray.thjw.po.JX_BJ;
import com.eray.thjw.po.PlaneModelData;
import com.eray.thjw.po.User;
import com.eray.thjw.service.JX_BJService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.FormulaTypeEnum;

/**
 * @author 梅志亮
 * @description  机型数据控制层
 * @develop date 2016.08.05
 */

@Controller
@RequestMapping("/project/planemodeldata")
public class PlaneModelDataController  {
	
	@Resource
	private PlaneModelDataService planeModelDataService;    
	@Resource
	private HCMainDataService hCMainDataService;
	@Resource
	private JX_BJService jX_BJService;
	/**
	 * 进入机型设置列表页面
	 * @return
	 */
	@Privilege(code="project:planemodeldata:main")
	@RequestMapping(value="main", method = RequestMethod.GET)
	public String getIndex(){
		return "project/maintenance/planemodellist";
	}
	
	/**
	 * @author 梅志亮
	 * @param menu  request  model
	 * @describe  请求机型列表数据
	 * @return list
	 */
	@SuppressWarnings("rawtypes")
	@Privilege(code="project:planemodeldata:main")
	@ResponseBody
	@RequestMapping(value="selectPlaneModelList", method = RequestMethod.POST)
	public Map<String, Object> selectPlaneModelList(@RequestBody PlaneModelData record,HttpServletRequest request,Model model)throws Exception {
		String fjjx = "";         //用户刚编辑过的记录 ID
		String dprtcode="";       
		if(record.getFjjx() != null&&record.getDprtcode()!=null){
			fjjx = record.getFjjx().toString();
			dprtcode = record.getDprtcode().toString();
		}
		//清除查询条件中的ID，保证列表查询结果集的正确性
		record.setFjjx(null);
		PageHelper.startPage(record.getPagination());
		//分页查询
		List<PlaneModelData> list = planeModelDataService.queryList(record);
		if(((Page)list).getTotal() > 0){//当总记录数大于0执行分页查询
			if(StringUtils.isNotBlank(fjjx)&&StringUtils.isNotBlank(dprtcode)){   //判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!hasRecord(list,fjjx,dprtcode)){                               //验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					PlaneModelData pm = new PlaneModelData();
					pm.setFjjx(fjjx);
					pm.setDprtcode(dprtcode);
				}
			}
			return PageUtil.pack4PageHelper(list, record.getPagination());
		}else{
			List<PlaneModelData> newRecordList = new ArrayList<PlaneModelData>(1);
			if(StringUtils.isNotBlank(fjjx)&&StringUtils.isNotBlank(dprtcode)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				PlaneModelData pm = new PlaneModelData();
				pm.setFjjx(fjjx);
				pm.setDprtcode(dprtcode);
				newRecordList = planeModelDataService.queryList(pm);
			}
			return PageUtil.pack(0, newRecordList, record.getPagination());
		}
	}
	/**
	 * 判断结果集中是否有相关记录，如果有则将该记录移动到第一条，并返回true, 否则返回false
	 * @param rowsList
	 * @param id
	 * @return
	 */
	public static <T extends AbstractEntity> boolean hasRecord(List<T> rowsList, String fjjx,String dprtcode){
		if(rowsList != null && !rowsList.isEmpty()){
			//移除刚编辑的记录
			int i = 0;
			for (i = 0; i < rowsList.size(); i++) {
				if(fjjx.equals(String.valueOf(((PlaneModelData) rowsList.get(i)).getFjjx()))&&
						dprtcode.equals(String.valueOf(((PlaneModelData) rowsList.get(i)).getDprtcode()))){
					if(i != 0){
						T t = rowsList.get(i);
						rowsList.remove(i);
						rowsList.add(0, t);
					}
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 新增机型数据初始化
	 * @return
	 */
	@Privilege(code="project:planemodeldata:main:01")
	@RequestMapping("intoPlaneData")
	public ModelAndView intoPlaneData() {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("project/maintenance/add_planemodel", model);
	}
	
	/**
	 * 验证机型是否已经存在
	 * @param record
	 * @throws Exception
	 */
	@Privilege(code="project:planemodeldata:main:01")
	@ResponseBody
	@RequestMapping("getPlaneModelConut")
	public int getCont(PlaneModelData record) throws Exception {
		User user = ThreadVarUtil.getUser();
		record.setDprtcode(user.getJgdm());
		return planeModelDataService.selectCount(record);
	}
	/**
	 * 执行机型数据增加
	 * @param record
	 * @return
	 * @throws Exception
	 */
	@Privilege(code="project:planemodeldata:main:01")
	@ResponseBody
	@RequestMapping("addPlaneModelData")
	public String addPlaneModelData(@RequestBody PlaneModelData record) throws Exception {
		String fjjx=null;
		User user = ThreadVarUtil.getUser();
		record.setDprtcode(user.getJgdm());
		try {
			fjjx=planeModelDataService.insert(record);
		} catch (RuntimeException e) {
			 throw new BusinessException("增加机型保存失败",e);
		}
		
		return fjjx;
	}
	
	/**
	 * @author 梅志亮
	 * @description 取得航材主数据的关联部件列表
	 */
	@ResponseBody
	@RequestMapping(value = "selectHcxxList", method = RequestMethod.POST)
	public Map<String, Object> selectHcxxList(@RequestBody HCMainData hcMainData, Model model) throws BusinessException {
		PageHelper.startPage(hcMainData.getPagination());
		List<HCMainData> list = hCMainDataService.selectHCMainDataList(hcMainData);
		return PageUtil.pack4PageHelper(list, hcMainData.getPagination());
	}
	
	/**
	 * 修改机型数据初始化
	 * @return
	 * @throws Exception
	 */
	@Privilege(code="project:planemodeldata:main:02")
	@RequestMapping("upPlaneData")
	public ModelAndView upPlaneData(@RequestParam("fjjx") String fjjx,@RequestParam("dprtcode") String dprtcode) throws  Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		PlaneModelData  record1=new PlaneModelData();
		record1.setFjjx(fjjx);
		record1.setDprtcode(dprtcode);
		PlaneModelData record = planeModelDataService.selectPlaneModelData(record1);
		model.put("formulaTypeEnum", FormulaTypeEnum.enumToListMap());
		model.put("record", record);
		return new ModelAndView("project/maintenance/edit_planemodel", model);
	}
	/**
	 * 执行机型部件的修改
	 * @param jX_BJ
	 * @throws Exception
	 */
	@Privilege(code="project:planemodeldata:main:02")
	@ResponseBody
	@RequestMapping(value="upPlaneBJH"  ,method = RequestMethod.POST)
	public Map<String, Object> upPlaneBJH(@RequestBody JX_BJ jX_BJ) throws  Exception {
		PageHelper.startPage(jX_BJ.getPagination());
	    List<JX_BJ> list= jX_BJService.selectByPrimaryKey(jX_BJ);
		return PageUtil.pack4PageHelper(list, jX_BJ.getPagination());
	}
	/**
	 * 执行机型设置的修改
	 * @param record
	 * @throws Exception
	 */
	@Privilege(code="project:planemodeldata:main:02")
	@ResponseBody
	@RequestMapping("updatePlaneData")
	public int updatePlaneData(@RequestBody PlaneModelData record) throws Exception {
		int count=0;
		try{
			count =planeModelDataService.updateByPrimaryKey(record);
		}catch(RuntimeException e){
			throw new BusinessException("修改机型保存失败",e);
		}
		return count;
	}
	
	/**
	 * 查看机型设置页面初始化
	 * @param fjjx
	 * @param dprtcode
	 * @throws Exception
	 */
	@RequestMapping("lookPlaneData")
	public ModelAndView lookPlaneData(@RequestParam("fjjx") String fjjx,@RequestParam("dprtcode") String dprtcode) throws  Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		PlaneModelData  record1=new PlaneModelData();
		record1.setFjjx(fjjx);
		record1.setDprtcode(dprtcode);
		PlaneModelData record = planeModelDataService.selectPlaneModelData(record1);
		model.put("formulaTypeEnum", FormulaTypeEnum.enumToListMap());
		model.put("record", record);
		return new ModelAndView("project/maintenance/view_planemodel", model);
	}
		
		
	/**
	 * @author liub
	 * @description 查询所有有效机型
	 * @param request
	 * @return List<String>
	 * @develop date 2016.09.05
	 */
	@ResponseBody
	@RequestMapping(value = "findAllType", method = RequestMethod.POST)
	public List<String> findAllType(String dprtcode)throws BusinessException {
		List<String> typeList = null;
		try {
			typeList = planeModelDataService.findAllType(dprtcode);
		} catch (Exception e) {
			throw new BusinessException("查询机型失败!",e);
		}
		return typeList;
	}
	
	/**
	 * @author meizhiliang
	 * @description 查询机型
	 * @param request
	 * @develop date 2016.09.05
	 */
	@ResponseBody
	@RequestMapping(value = "findType", method = RequestMethod.POST)
	public List<PlaneModelData> findType(HttpServletRequest request)throws BusinessException {
		List<PlaneModelData> typeList = null;
		try {
			typeList = planeModelDataService.findType();
		} catch (RuntimeException e) {
			throw new BusinessException("查询机型失败!",e);
		}
		return typeList;
	}
	
	/**
	 * @author liub
	 * @description 根据条件分页查询机型列表
	 * @param request,model,planeModelData
	 * @return Map<String, Object>
	 * @throws Exception 
	 * @develop date 2017.01.05
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody PlaneModelData planeModelData,HttpServletRequest request,Model model) throws BusinessException{
		try {
			PageHelper.startPage(planeModelData.getPagination());
			List<Map<String, Object>> list = this.planeModelDataService.queryAllPageList(planeModelData);
			return PageUtil.pack4PageHelper(list, planeModelData.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
}
