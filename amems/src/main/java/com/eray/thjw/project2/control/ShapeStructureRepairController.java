package com.eray.thjw.project2.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.project2.po.ShapeStructureRepair;
import com.eray.thjw.project2.service.ShapeStructureRepairService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("/project/shapestructurerepair")
public class ShapeStructureRepairController  extends BaseController{

	@Autowired
	private ShapeStructureRepairService shapeStructureRepairService;

	@Privilege(code = "project:shapestructurerepair:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String list(Model model) {
		return "/project2/shapeStructureRepair/sharpRepairMain";
	}

	
	@ResponseBody
	@RequestMapping(value = "queryList", method = RequestMethod.POST)
	public Map<String, Object> queryList(@RequestBody ShapeStructureRepair record, HttpServletRequest request,
			Model model) {
		String id = record.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		record.setId(null);
		PageHelper.startPage(record.getPagination());
		List<ShapeStructureRepair> recordList = shapeStructureRepairService.getList(record);
		if(((Page)recordList).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){
				if(!PageUtil.hasRecord(recordList, id)){
					ShapeStructureRepair record1=new ShapeStructureRepair();
					record1.setId(id);
					List<ShapeStructureRepair> newList = shapeStructureRepairService.getList(record1);
					if(newList != null && newList.size() == 1){
						recordList.add(0, newList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(recordList, record.getPagination());
		}else{
			List<ShapeStructureRepair> newRecordList = new ArrayList<ShapeStructureRepair>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				ShapeStructureRepair record2=new ShapeStructureRepair();
				record2.setId(id);
				newRecordList = shapeStructureRepairService.getList(record2);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, record.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, record.getPagination());
		}
	}
	
	@Privilege(code = "project:shapestructurerepair:main:01,project:shapestructurerepair:main1:01")
	@ResponseBody
	@RequestMapping("/addRecord")
	public String addRecord(@RequestBody ShapeStructureRepair record, HttpServletRequest request)
			throws BusinessException {
		try {
			if (record.getId() == null || "".equals(record.getId())) {
				String id = UUID.randomUUID().toString();
				record.setId(id);
				shapeStructureRepairService.insertRecord(record);
			} else {
				shapeStructureRepairService.updateRecordById(record);
			}
			return record.getId();
		} catch (BusinessException e) {
			throw new BusinessException("查询失败！", e);
		}
		
	}
	
	@Privilege(code = "project:shapestructurerepair:main:02,project:shapestructurerepair:main1:02")
	@ResponseBody
	@RequestMapping("/edit")
	public ShapeStructureRepair edit(@RequestBody ShapeStructureRepair record, HttpServletRequest request)
			throws Exception {
		record = shapeStructureRepairService.getRecordById(record.getId());
		return record;
	}
	
	/**
	 * 
	 * @Description 跳转到查看界面
	 * @CreateTime 2017-8-15 下午6:43:19
	 * @CreateBy 孙霁
	 * @param id
	 * @return airworthiness_view
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(Model model,@RequestParam String id)throws BusinessException {
		try {
			ShapeStructureRepair shapeStructureRepair = shapeStructureRepairService.getRecordById(id);
			model.addAttribute("shapeStructureRepair", shapeStructureRepair);
			return new ModelAndView("project2/shapeStructureRepair/structureRepair_view");
		} catch (BusinessException e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	
	@Privilege(code = "project:shapestructurerepair:main:03,project:shapestructurerepair:main1:03")
	@ResponseBody
	@RequestMapping("/deleteRecord")
	public String deleteRecord(@RequestBody ShapeStructureRepair record, HttpServletRequest request)
			throws Exception {
		shapeStructureRepairService.deleteById(record.getId());
		return "";
	}
	
	@Privilege(code = "project:shapestructurerepair:main1")
	@RequestMapping(value = "main1", method = RequestMethod.GET)
	public String list1(Model model) {
		return "/project2/shapeStructureRepair/StructureRepairMain";
	}
	
	/**
	 * 
	 * @Description 导出
	 * @CreateTime 2017-12-7 上午10:56:32
	 * @CreateBy 孙霁
	 * @param zoneStation
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "project:shapestructurerepair:main:04,project:shapestructurerepair:main1:04")
	@RequestMapping(value = "shapeStructureRepair.xls")
	public String shapeStructureRepairExcel(ShapeStructureRepair record,Model model)throws BusinessException {
		try {
			List<ShapeStructureRepair> list =shapeStructureRepairService.getList(record);
			if(list !=null && list.size() > 0){
				for (ShapeStructureRepair shapeStructureRepair : list) {
					shapeStructureRepair.setZjh(shapeStructureRepair.getZjh() != null?shapeStructureRepair.getZjh():""
						+" "+shapeStructureRepair.getFixChapter().getYwms()!=null?shapeStructureRepair.getFixChapter().getYwms():"");
				}
			}
			String wjmc;
			if(record.getSjlx() == 2){
				wjmc="sharpRepair";//外形缺损
			}else{
				wjmc="structureRepair";//结构修理
			}
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", wjmc, model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e); 
		}
	}
}
