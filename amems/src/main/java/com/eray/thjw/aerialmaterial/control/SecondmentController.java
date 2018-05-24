package com.eray.thjw.aerialmaterial.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.Secondment;
import com.eray.thjw.aerialmaterial.service.SecondmentService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.aerialmaterial.SecondmentTypeEnum;
/**
 * 借调对象
 * @author xu.yong
 *
 */
@Controller
@RequestMapping("/aerialmaterial/secondment")
public class SecondmentController extends BaseController {
	
	@Resource
	private SecondmentService secondmentService;
	@Resource
	private DepartmentService departmentService;
	@Resource
	private CommonRecService commonRecService;                   //REC表服务
		
	@RequestMapping(value="/list/type", method=RequestMethod.POST)
	public @ResponseBody List<Secondment> queryByType(@RequestBody Secondment secondment){
		return this.secondmentService.queryByType(secondment);
	}
	
	/**
	 * @author 梅志亮  借调对象页面
	 * @time 2016-10-31
	 */
	@Privilege(code = "aerialmaterial:secondment:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String getIndex(Model model) {
		model.addAttribute("secondmentTypeEnum", SecondmentTypeEnum.enumToListMap());
		return "material/secondment/secondmentlist";
	}
	
	/**
	 * @author 梅志亮  借调对象列表
	 * @throws BusinessException 
	 * @time 2016-11-17
	 */
	@SuppressWarnings("rawtypes")
	@Privilege(code = "aerialmaterial:secondment:main")
	@ResponseBody
	@RequestMapping(value = "querySecondmentList", method = RequestMethod.POST)
	public Map<String, Object> queryQualityCheckList(@RequestBody Secondment secondment, HttpServletRequest request,Model model) throws BusinessException {
	
		String id = secondment.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		secondment.setId(null);
		
		PageHelper.startPage(secondment.getPagination());
		List<Secondment> list = secondmentService.selectSecondmentList(secondment);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					Secondment secondment1 = new Secondment();
					secondment1.setId(id);
					List<Secondment> newRecordList = secondmentService.selectSecondmentList(secondment1);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, secondment.getPagination());
		}else{
			List<Secondment> newRecordList = new ArrayList<Secondment>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				Secondment secondment1 = new Secondment();
				secondment1.setId(id);
				newRecordList = secondmentService.selectSecondmentList(secondment1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, secondment.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, secondment.getPagination());
		}
	}
	
	
	/**
	 * 新增借调对象初始化
	 * @param req
	 * @param resp
	 * @return
	 */
	@Privilege(code = "aerialmaterial:secondment:main:01")
	@RequestMapping("intoSeconded")
	public ModelAndView intoFixChapter(HttpServletRequest req,
			HttpServletResponse resp) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("departments", departmentService.queryOrg());
		
		return new ModelAndView("material/secondment/add_secondment", model);
	}
	/**
	 * 判断借调对象编号是否重复
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
	@RequestMapping("override")
	public int override(HttpServletRequest req,
			HttpServletResponse resp,String jddxbh) {
		int count=secondmentService.selectCount(jddxbh);
		return count;
	}
	/**
	 * 获取该组织机构所有的对象编号
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
	@RequestMapping("filterAerocade")
	public Map<String, Object> filterAerocade(HttpServletRequest req,
			HttpServletResponse resp,String dprtcode) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<String> list= new ArrayList<String>();
		List<Secondment> secondmentList=secondmentService.queryByDprtcode(dprtcode);
		for (Secondment secondment : secondmentList) {
			if(null!=secondment.getJddxbh()&&!"".equals(secondment.getJddxbh())){
				list.add(secondment.getJddxbh());
			}
		}
		resultMap.put("jddxbhList", list);
		return resultMap;
	}
	
	/**
	 * @author meizhiliang
	 * @description 新增借调对象
	 * @develop date 2016.08.24
	 * @throws BusinessException
	 */
	@Privilege(code = "aerialmaterial:secondment:main:01")
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@RequestBody Secondment secondment) throws BusinessException {
		int count=0;
		User user = ThreadVarUtil.getUser();
		UUID uuid = UUID.randomUUID();
		String id=uuid.toString();
		secondment.setId(id);
		secondment.setWhdwid(user.getBmdm());
		secondment.setWhrid(user.getId());
		if(secondment.getJddxlx()==1){
		}else{
			secondment.setDprtcode(user.getJgdm());
		}
		try {
			count = secondmentService.insertSelective(secondment);
		} catch (RuntimeException e) {
			throw new BusinessException("新增借调对象失败", e);
		}
		if(count == 0){
			throw new BusinessException("新增借调对象失败");
		}
		return id;
	}
	/**
     * @author 梅志亮  修改借调对象初始化
	 * @time 2016-10-31 
	 */
	@Privilege(code = "aerialmaterial:secondment:main:02")
	@RequestMapping(value="intoEdit",method=RequestMethod.GET)
	public ModelAndView  intoEdit(String id){
		Map<String, Object> model =new HashMap<String, Object>();
		model.put("secondmentTypeEnum", SecondmentTypeEnum.enumToListMap());
		model.put("secondment",secondmentService.selectById(id));
		model.put("departments", departmentService.queryOrg());
		return new ModelAndView("material/secondment/edit_secondment",model);
	}
	
	/**
	 * @author meizhiliang
	 * @description 执行借调对象修改
	 * @develop date 2016.08.24
	 * @throws BusinessException
	 */
	@Privilege(code = "aerialmaterial:secondment:main:02")
	@ResponseBody
	@RequestMapping(value = "Edit", method = RequestMethod.POST)
	public String Edit(@RequestBody Secondment secondment) throws BusinessException {
		int count=0;
		User user = ThreadVarUtil.getUser();
		secondment.setWhrid(user.getId());
		if(secondment.getJddxlx()==1){
		}else{
			secondment.setDprtcode(user.getJgdm());
		}
		try {
			count = secondmentService.updateByPrimaryKeySelective(secondment);
		} catch (RuntimeException e) {
			throw new BusinessException("修改借调对象失败",e);
		}
		if(count == 0){
			throw new BusinessException("修改借调对象失败");
		}
		return secondment.getId();
	}
	/**
	 * @author meizhiliang
	 * @description 执行逻辑删除修改
	 * @develop date 2016.08.24
	 * @throws BusinessException
	 */
	@Privilege(code = "aerialmaterial:secondment:main:03")
	@ResponseBody
	@RequestMapping(value = "Delete", method = RequestMethod.POST)
	public int Delete(String id) throws BusinessException {
		int count=0;
		Secondment secondment =new Secondment();
		secondment.setId(id);
		secondment.setZt(0);
		try {
			count = secondmentService.updateByPrimaryKeySelective(secondment);
		} catch (RuntimeException e) {
			throw new BusinessException("删除借调对象失败",e);
		}
		return count;
	}
	
	/**
	 * 查询所有的借调对象
	 * @param secondment
	 * @return
	 */
	@RequestMapping(value="/list/all", method=RequestMethod.POST)
	public @ResponseBody List<Secondment> queryAll(@RequestBody Secondment secondment){
		return this.secondmentService.queryAll(secondment);
	}
}
