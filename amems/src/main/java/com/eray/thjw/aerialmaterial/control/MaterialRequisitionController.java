package com.eray.thjw.aerialmaterial.control;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.aerialmaterial.po.MaterialRequisition;
import com.eray.thjw.aerialmaterial.service.MaterialRequisitionService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.MaterialTypeEnum;
import enu.aerialmaterial.RequisitionStatusEnum;



/**
 * 航材领用控制层
 * @author xu.yong
 */
@Controller
@RequestMapping("/aerialmaterial/requisition")
public class MaterialRequisitionController extends BaseController {
	
	@Resource
	private MaterialRequisitionService materialRequisitionService;
	
	@Resource
	private StockSerivce stockSerivce;
	
	/**
	 * 进入领用主界面
	 * @param model
	 * @return
	 */
	@Privilege(code="aerialmaterial:requisition:main")
	@RequestMapping(value="/main", method = RequestMethod.GET)
	public String main(Model model, HttpServletRequest request){
		model.addAttribute("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		return "material/requisition/requisition_main";
	}
	
	/**
	 * 判断用户是否有保存状态的领用申请单
	 */
	@RequestMapping(value="/allowEdit", method = RequestMethod.GET)
	public @ResponseBody Boolean allowEdit(){
		return this.materialRequisitionService.hasSaved();
	}
	
	/**
	 * 进入编辑界面
	 */
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(Model model, HttpServletRequest request){
		model.addAttribute("operateType", "edit");
		return "material/requisition/requisition_view";
	}
	
	/**
	 * 领用操作<br/>
	 * 领用主界面点击“领用”
	 */
	@RequestMapping(value="/requisition/{id}")
	public @ResponseBody String requisition(@PathVariable String id){
		this.materialRequisitionService.addMaterial(id);
		return "success";
	}
	
	/**
	 * 查询用户保存状态的领用申请单
	 */
	@RequestMapping(value="view/saved", method = RequestMethod.GET)
	public @ResponseBody MaterialRequisition query(){
		return this.materialRequisitionService.query();
	}
	
	/**
	 * 进入查看界面
	 */
	@RequestMapping(value="/view/{id}" , method = RequestMethod.GET)
	public String view(@PathVariable String id, Model model){
		model.addAttribute("operateType", "view");
		model.addAttribute("id", id);
		return "material/requisition/requisition_view";
	}
	
	/**
	 * 查询领用申请单
	 */
	@RequestMapping(value="/view/id/{id}", method = RequestMethod.GET)
	public @ResponseBody MaterialRequisition queryById(@PathVariable String id){
		return this.materialRequisitionService.queryById(id);
	}
	
	/**
	 * 保存领用申请单
	 * @param materialRequisition
	 * @throws ParseException 
	 */
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public @ResponseBody void save(@RequestBody Map map) throws ParseException{
		MaterialRequisition materialRequisition = new MaterialRequisition();
		materialRequisition.setId((String)map.get("id"));
		materialRequisition.setFjzch((String)map.get("fjzch"));
		materialRequisition.setSqrq(DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, (String)map.get("sqrq")));
		materialRequisition.setSqrid((String)map.get("sqrid"));
		materialRequisition.setSpbmid((String)map.get("spbmid"));
		materialRequisition.setLyyy((String)map.get("lyyy"));
		
		List<Map> modifyList = (List<Map>)map.get("modifyList");
		
		List<String> idList = (List<String>)map.get("idList");
		
		this.materialRequisitionService.save(materialRequisition, modifyList, idList);
	}
	
	/**
	 * 提交申请单
	 * @param id
	 * @throws ParseException 
	 */
	@RequestMapping(value="/submit", method=RequestMethod.POST)
	public @ResponseBody void submit(@RequestBody Map map) throws ParseException{
		MaterialRequisition materialRequisition = new MaterialRequisition();
		materialRequisition.setId((String)map.get("id"));
		materialRequisition.setFjzch((String)map.get("fjzch"));
		materialRequisition.setSqrq(DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, (String)map.get("sqrq")));
		materialRequisition.setSqrid((String)map.get("sqrid"));
		materialRequisition.setSpbmid((String)map.get("spbmid"));
		materialRequisition.setLyyy((String)map.get("lyyy"));
		//修改清单
		List<Map> modifyList = (List<Map>)map.get("modifyList");
		//删除清单
		List<String> idList = (List<String>)map.get("idList");
		
		//保存和提交分开处理，事务独立
		//保存页面修改
		this.materialRequisitionService.save(materialRequisition, modifyList, idList);
		//提交申请单
		this.materialRequisitionService.saveSubmit(materialRequisition.getId());
		
	}
	
	
	/**
	 * 分页查询领用申请单
	 * @param materialRequisition
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public Map<String, Object> list(@RequestBody MaterialRequisition materialRequisition,HttpServletRequest request,Model model) throws BusinessException{
		
//		User user = ThreadVarUtil.getUser();
//		materialRequisition.setZdrid(user.getId());
		String id = materialRequisition.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		materialRequisition.setId(null);
		
		PageHelper.startPage(materialRequisition.getPagination());
		List<MaterialRequisition> list = this.materialRequisitionService.queryPage(materialRequisition);
		if(((Page)list).getTotal() > 0){
			
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					MaterialRequisition materialRequisition1 = new MaterialRequisition();
					materialRequisition1.setId(id);
					List<MaterialRequisition> newRecordList = this.materialRequisitionService.queryPage(materialRequisition1);
					if(newRecordList != null && newRecordList.size() == 1){
						//将记录放入结果集第一条
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, materialRequisition.getPagination());
		}else{
			List<MaterialRequisition> newRecordList = new ArrayList<MaterialRequisition>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				MaterialRequisition materialRequisition1 = new MaterialRequisition();
				materialRequisition1.setId(id);
				newRecordList = this.materialRequisitionService.queryPage(materialRequisition1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, materialRequisition.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, materialRequisition.getPagination());
		}
		
	}
	
	/**
	 * 作废
	 * @param id
	 */
	@RequestMapping(value="/cancel/{id}", method=RequestMethod.POST)
	public @ResponseBody void cancel(@PathVariable String id){
		this.materialRequisitionService.updateState(id, RequisitionStatusEnum.INVALID.getId());
	}
	
	/**
	 * 指定结束
	 * @param id
	 */
	@RequestMapping(value="/end", method=RequestMethod.POST)
	public @ResponseBody void end(@RequestParam String id, @RequestParam String zdjsyy){
		this.materialRequisitionService.saveClose(id, zdjsyy);
	}
	
}
