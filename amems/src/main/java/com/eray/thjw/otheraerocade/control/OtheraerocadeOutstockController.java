package com.eray.thjw.otheraerocade.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.Secondment;
import com.eray.thjw.aerialmaterial.service.InstockService;
import com.eray.thjw.aerialmaterial.service.SecondmentService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.otheraerocade.service.OtherAerocadeBorrowReturnOutstockService;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;

import enu.MaterialTypeEnum;
import enu.SupervisoryLevelEnum;

/**
 * 
 * 外飞行队借入归还出库
 * @author xu.yong
 *
 */
@Controller
@RequestMapping("/otheraerocade")
public class OtheraerocadeOutstockController extends BaseController {
	
	@Resource
	private OtherAerocadeBorrowReturnOutstockService otherAerocadeBorrowReturnOutstockService;
	
	@Resource
	private InstockService instockService;
	
	@Resource
	private DepartmentService departmentService;
	@Resource
	private SecondmentService secondmentService;
	
	/**
	 * 跳转到外飞行队借入归还出库单界面
	 * @return
	 */
	@Privilege(code="otheraerocade:outstock:main")
	@RequestMapping("/outstock/main")
	public ModelAndView main(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		List<Secondment> newRecordList= secondmentService.queryOtherOrg(ThreadVarUtil.getUser().getJgdm());
		model.put("newRecordList", newRecordList);
		return new ModelAndView("material/otheraerocade/borrow_return_outstock_main", model);
	}
	
	/**
	 * 外飞行队借入归还出库单列表
	 * @param entity
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/outstock/list", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> borrowReturnOutstockList(BaseEntity entity) throws BusinessException{
		Map<String, Object> map = this.otherAerocadeBorrowReturnOutstockService.queryBorrowReturnOutstock(entity);
		List<Department> departments = departmentService.queryOrg();
		PageUtil.addExt(map, "departments", departments);
		return map;
	}
	
	/**
	 * 外飞行队借入归还出库单航材清单
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/outstock/detail/list/{id}", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> borrowReturnOutstockDetailList(@PathVariable String id){
		Map<String, Object> map = PageUtil.pack(this.otherAerocadeBorrowReturnOutstockService.queryBorrowReturnOutstockDetail(id));
		PageUtil.addDict(map, "gljb", SupervisoryLevelEnum.enumToListMap());
		PageUtil.addDict(map, "hclx", MaterialTypeEnum.enumToListMap());
		return map;
	}
	
//	@Privilege(code="otheraerocade:outstock:main:01")
	@RequestMapping(value = "/outstock/instock/{id}")
	public String goInstock(@PathVariable String id, Model model){
		//查询
		model.addAttribute("outstock", this.otherAerocadeBorrowReturnOutstockService.queryBorrowReturnOutstockById(id));
		List<Department> departments = departmentService.queryOrg();
		model.addAttribute("departments", departments);
		model.addAttribute("operateType", "edit");
		return "material/otheraerocade/lend_return_instock_view";
	}
	
	@RequestMapping(value = "/outstock/instock/detail/{id}")
	public @ResponseBody Map<String, Object> queryBorrowReturnOutstockDetail(@PathVariable String id){
		return PageUtil.pack(this.otherAerocadeBorrowReturnOutstockService.queryBorrowReturnOutstockDetail(id));
	}
	
	
	
	
	/**
	 * 根据外飞行队出库航材进行借出航材的分配 
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/outstock/instock/distribution")
	public @ResponseBody Map<String, Object> distribution(@RequestBody Map<String, Object> map) throws BusinessException{
		return PageUtil.pack(this.otherAerocadeBorrowReturnOutstockService.getExpatriateByBJ(map));
	}
	
	/**
	 * 保存采购入库
	 * @param id
	 * @throws BusinessException 
	 */
	@RequestMapping(value="/instock/submit")
	public @ResponseBody void instockSubmit(@RequestBody Map<String, Object> map) throws BusinessException{
		this.instockService.saveLendByOutstock(map);
	}
	
}
