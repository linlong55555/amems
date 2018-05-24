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
import com.eray.thjw.aerialmaterial.po.QualityCheck;
import com.eray.thjw.aerialmaterial.service.QualityCheckService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.service.UserService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import enu.MaterialTypeEnum;
import enu.SupervisoryLevelEnum;
import enu.aerialmaterial.MaterialCheckResultEnum;
import enu.aerialmaterial.MaterialSrouceEnum;
 
@Controller
@RequestMapping("/aerialmaterial/qualityCheck/")
public class QualityCheckController extends BaseController {
	
	@Resource
	private QualityCheckService qualityCheckService;
	@Resource
	private UserService userService;
	@Resource
	private AttachmentService attachmentService;
	
	/**
	 * @author 梅志亮  航材检验单列表
	 * @time 2016-10-31
	 */
	@Privilege(code = "aerialmaterial:qualityCheck:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView getIndex() {
		return new ModelAndView("material/qualitycheck/qualitychecklist",initData());
	}
	/**
	 * @author 梅志亮  航材检验单列表
	 * @throws BusinessException 
	 * @time 2016-10-31
	 */
	@SuppressWarnings("rawtypes")
	@Privilege(code = "aerialmaterial:qualityCheck:main")
	@ResponseBody
	@RequestMapping(value = "queryQualityCheckList", method = RequestMethod.POST)
	public Map<String, Object> queryQualityCheckList( @RequestBody QualityCheck qualitycheck, HttpServletRequest request, Model model) throws BusinessException {
		String id = qualitycheck.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		qualitycheck.setId(null);
		PageHelper.startPage(qualitycheck.getPagination());
		List<QualityCheck> list = qualityCheckService.selectQualityCheckList(qualitycheck);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					QualityCheck qualitycheck1 = new QualityCheck();
					qualitycheck1.setId(id);
					List<QualityCheck> newRecordList = qualityCheckService.selectQualityCheckList(qualitycheck1);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, qualitycheck.getPagination());
		}else{
			List<QualityCheck> newRecordList = new ArrayList<QualityCheck>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				QualityCheck qualitycheck1 = new QualityCheck();
				qualitycheck1.setId(id);
				newRecordList = qualityCheckService.selectQualityCheckList(qualitycheck1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, qualitycheck.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, qualitycheck.getPagination());
		}
	}
	/**
     * @author 梅志亮  航材检验初始化
	 * @time 2016-10-31 
	 */
	@Privilege(code = "aerialmaterial:qualityCheck:main:01")
	@RequestMapping(value="Checking",method=RequestMethod.GET)
	public ModelAndView  Checking(String id){
		Map<String, Object> model = initData();
		model.put("queryQuality",qualityCheckService.selectByPrimaryKey(id));
		return new ModelAndView("material/qualitycheck/edit_qualitycheck",model);
	}
	/**
     * @author 梅志亮  加载航材检验的附件
	 * @time 2016-10-31 
	 */
	@Privilege(code = "aerialmaterial:qualityCheck:main:01")
	@ResponseBody
	@RequestMapping(value = "LoadAttachment", method = RequestMethod.POST)
	public List<Attachment> LoadAttachment(String id) throws BusinessException {
		Attachment att=new Attachment();
		att.setMainid(id);
		List <Attachment> list=null;
		try {
			list = attachmentService.queryListAttachments(att);
		} catch (Exception e) {
			throw new BusinessException("航材检验附件查询失败",e);
		}
 		return list;
	}
	
	
	@Privilege(code = "aerialmaterial:qualityCheck:main:01")
	@ResponseBody
	@RequestMapping(value = "Edit", method = RequestMethod.POST)
	public int Edit(@RequestBody QualityCheck qualitycheck) throws BusinessException {
		try {
			return qualityCheckService.updateByPrimaryKeySelective(qualitycheck);
		} catch (Exception e) {
			throw new BusinessException("航材检验失败",e);
		}
	}  
	/**
     * @author 梅志亮  查看航材检验单
	 * @time 2016-10-31 
	 */
	@RequestMapping(value="Looked",method=RequestMethod.GET)
	public ModelAndView  Looked(String id){
		Map<String, Object> model = initData();
		model.put("queryQuality",qualityCheckService.selectByPrimaryKey(id));
		Attachment at =new Attachment();
		at.setMainid(id);
		model.put("attachment",attachmentService.queryListAttachments(at));
		return new ModelAndView("material/qualitycheck/view_qualitycheck",model);
	}
	/**
	 * 预览导出PDF
	 * @param id,dprtcode,keyword
	 * @throws BusinessException
	 */
	@RequestMapping(value = "qualityCheckOutPDF")
	public String export(String id,String dprtcode,Model model) throws BusinessException {
		try {
			model.addAttribute("id", id);
			return outReport("pdf", dprtcode, "QualityCheckAvailableCard", model);
		} catch (Exception e) {
			throw new BusinessException("预览或导出失败",e);
		}
	}
	/**
	 * 请求跳转页面页面绑定参数
	 * @return model
	 */
	private Map<String, Object>  initData(){
		Map<String, Object> model =new HashMap<String, Object>();
		model.put("materialTypeEnum", MaterialTypeEnum.enumToListMap());
		model.put("supervisoryLevelEnum",SupervisoryLevelEnum.enumToListMap());
		model.put("materialSrouceEnum",MaterialSrouceEnum.enumToListMap());
		model.put("materialCheckResultEnum",MaterialCheckResultEnum.enumToListMap());
		return model;
	}
}
