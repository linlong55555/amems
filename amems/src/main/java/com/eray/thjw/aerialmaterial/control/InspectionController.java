package com.eray.thjw.aerialmaterial.control;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.Inspection;
import com.eray.thjw.aerialmaterial.service.InspectionService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.WorkCard;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.MaterialTypeEnum;
import enu.SupervisoryLevelEnum;
import enu.aerialmaterial.MaterialCheckResultEnum;
import enu.aerialmaterial.MaterialSrouceEnum;

/**
 * 
 * @Description 检验单
 * @CreateTime 2017年12月4日 上午10:03:26
 * @UpdateBy 林龙
 */
@Controller
@RequestMapping("/aerialmaterial/inspection")
public class InspectionController extends BaseController {
	
	@Resource
	private InspectionService inspectionService;
	
	@Privilege(code = "aerialmaterial:inspection:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("materialCheckResultEnum",MaterialCheckResultEnum.enumToListMap());
		return new ModelAndView("material/inspection/inspection_main", model);
	}
	
	@ResponseBody
	@RequestMapping(value = "queryInspectionList", method = RequestMethod.POST)
	public Map<String, Object> queryQualityCheckList( @RequestBody Inspection record, HttpServletRequest request, Model model) throws BusinessException {
		String id = record.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		record.setId(null);
		PageHelper.startPage(record.getPagination());
		List<Inspection> list = inspectionService.selectInspectionList(record);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					Inspection inspection1 = new Inspection();
					inspection1.setId(id);
					List<Inspection> newRecordList = inspectionService.selectInspectionList(inspection1);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, record.getPagination());
		}else{
			List<Inspection> newRecordList = new ArrayList<Inspection>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				Inspection inspection2 = new Inspection();
				inspection2.setId(id);
				newRecordList = inspectionService.selectInspectionList(inspection2);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, record.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, record.getPagination());
		}
	}
	
	@Privilege(code = "aerialmaterial:inspection:main:01")
	@RequestMapping(value="Checking",method=RequestMethod.GET)
	public ModelAndView  Checking(String id){
		Map<String, Object> model = initData();
		model.put("queryQuality",inspectionService.selectByPrimaryKey(id));
		return new ModelAndView("material/inspection/inspection_edit",model);
	}
	
	@ResponseBody
	@RequestMapping(value = "selectById",method={RequestMethod.POST,RequestMethod.GET})
	public Inspection selectById(String id){
		return inspectionService.getById(id);
	}
	
	private Map<String, Object>  initData(){
		Map<String, Object> model =new HashMap<String, Object>();
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		model.put("supervisoryLevelEnum",SupervisoryLevelEnum.enumToListMap());
		model.put("materialSrouceEnum",MaterialSrouceEnum.enumToListMap());
		model.put("materialCheckResultEnum",MaterialCheckResultEnum.enumToListMap());
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public int Edit(@RequestBody Inspection record) throws Exception {		
			return inspectionService.updateByPrimaryKeySelective(record);
	}  
	
	@RequestMapping(value="Looked",method=RequestMethod.GET)
	public ModelAndView  Looked(String id){
		Map<String, Object> model = initData();
		model.put("queryQuality",inspectionService.selectByPrimaryKey(id));
		return new ModelAndView("material/inspection/inspection_view",model);
	}
}
