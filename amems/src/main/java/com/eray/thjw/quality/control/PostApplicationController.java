package com.eray.thjw.quality.control;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.DeptInfo;
import com.eray.thjw.po.User;
import com.eray.thjw.quality.po.PostApplication;
import com.eray.thjw.quality.po.PostApplicationSQJX;
import com.eray.thjw.quality.service.PostApplicationService;
import com.eray.thjw.service.DeptInfoService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.produce.WorkorderTypeEnum;
import enu.quality.PostStatusEnum;

/**
 * 
 * @Description 授权
 * @CreateTime 2017年11月9日 下午3:22:08
 * @CreateBy 林龙
 */
@Controller
@RequestMapping("/quality/post/application")
public class PostApplicationController extends BaseController{
	
	@Resource
	private PostApplicationService postApplicationService;
	
	@Resource
	private DeptInfoService deptInfoService;

	/**
	 * @Description 授权列表跳转
	 * @CreateTime 2017年8月14日 上午10:24:08
	 * @CreateBy 林龙
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="quality:post:application:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(Map<String, Object> model)throws BusinessException {
	 	model = new HashMap<String, Object>();
		try {
			model.put("postStatusEnum", PostStatusEnum.enumToListMap()); //岗位状态枚举
			return new ModelAndView("quality/postapplication/postapplication_main",model);
		} catch (Exception e) {
			throw new BusinessException("授权列表跳转失败!",e);
		}
	}

	/**
	 * @Description 授权列表分页数据 
	 * @CreateTime 2017年8月14日 上午9:39:57
	 * @CreateBy 林龙
	 * @param technical 授权
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody PostApplication postApplication,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			resultMap = postApplicationService.queryAllPageList(postApplication);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("授权列表加载失败!",e);
		}
	}
	
	
	/**
	 * @Description 新增保存授权
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param technical 授权
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:post:application:main:01")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@RequestBody PostApplication postApplication) throws BusinessException{
		try {
			postApplication.setZt(PostStatusEnum.SAVE.getId());//状态为保存
			return postApplicationService.save(postApplication);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存授权失败!",e);
		}
	}
	
	/**
	 * @Description 新增提交授权
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param technical 授权
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:post:application:main:01")
	@ResponseBody
	@RequestMapping(value = "saveSubmit", method = RequestMethod.POST)
	public String saveSubmit(@RequestBody PostApplication postApplication) throws BusinessException{
		try {
			postApplication.setZt(PostStatusEnum.Submit.getId());//状态为提交
			return postApplicationService.save(postApplication);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("提交授权失败!",e);
		}
	}
	
	/**
	 * @Description 根据授权id查询授权信息
	 * @CreateTime 2017年8月15日 下午8:15:37
	 * @CreateBy 林龙
	 * @param postApplication 授权
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getByPostApplicationId", method = RequestMethod.POST)
	public PostApplication getByPostApplicationId(@RequestBody PostApplication postApplication)throws BusinessException {
		try {
			return postApplicationService.getInfoById(postApplication);
		} catch (Exception e) {
			throw new BusinessException("查询授权失败!",e);
		}
	}
	
	/**
	 * @Description 修改保存授权
	 * @CreateTime 2017年9月27日 下午4:34:50
	 * @CreateBy 林龙
	 * @param postApplication 授权
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:post:application:main:02")
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@RequestBody PostApplication postApplication) throws BusinessException{
		try {
			return postApplicationService.update(postApplication);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("修改授权失败!",e);
		}
	}
	
	/**
	 * @Description 修改提交授权
	 * @CreateTime 2017年9月27日 下午4:36:53
	 * @CreateBy 林龙
	 * @param postApplication
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:post:application:main:02")
	@ResponseBody
	@RequestMapping(value = "updateSubmit", method = RequestMethod.POST)
	public String updateSubmit(@RequestBody PostApplication postApplication) throws BusinessException{
		try {
			postApplication.setZt(PostStatusEnum.Submit.getId());//状态为提交
			return postApplicationService.update(postApplication);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("提交授权失败!",e);
		}
	}
	
	
	/**
	 * @Description 修改审核人授权
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param technical 授权
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:post:application:main:04")
	@ResponseBody
	@RequestMapping(value = "updateShr", method = RequestMethod.POST)
	public String updateShr(@RequestBody PostApplication postApplication) throws BusinessException{
		try {
			return postApplicationService.updateShr(postApplication);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("修改审核人授权失败!",e);
		}
	}
	
	/**
	 * @Description 删除授权
	 * @CreateTime 2017年9月28日 下午5:05:44
	 * @CreateBy 林龙
	 * @param postApplication
	 * @throws BusinessException
	 */
	@Privilege(code="quality:post:application:main:03")
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody PostApplication postApplication) throws BusinessException{
		try {
			postApplicationService.delete(postApplication);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除授权失败!",e);
		}
	}
	
	/**
	 * @Description 指定结束授权
	 * @CreateTime 2017年9月29日 上午10:07:06
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	@Privilege(code="quality:post:application:main:05")
	@ResponseBody
	@RequestMapping(value = "gConfirm", method = RequestMethod.POST)
	public void gConfirm(@RequestBody PostApplication postApplication) throws BusinessException{
		try {
			postApplicationService.updategConfirm(postApplication);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("指定结束授权失败!",e);
		}
	}

	
	/**
	 * 
	 * @Description 查看当前页面
	 * @CreateTime 2017年11月14日 下午4:17:26
	 * @CreateBy 林龙
	 * @param id
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("find/{id}")
	public ModelAndView find(@PathVariable("id") String id, HttpServletRequest request,HttpServletResponse resp,Model model) throws Exception {
		try {
			model.addAttribute("viewid", id);
			return new ModelAndView("/quality/postapplication/postapplication_view");
		} catch (RuntimeException e) {
			throw new BusinessException("授权查看跳转失败!",e);
		}
	}
	
	/**
	 * @Description 获取岗位授权-申请机型
	 * @CreateTime 2017年8月15日 下午8:15:37
	 * @CreateBy 刘兵
	 * @param postApplication 授权
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "querySQJXByMainid", method = RequestMethod.POST)
	public List<PostApplicationSQJX> querySQJXByMainid(String mainid)throws BusinessException {
		try {
			return postApplicationService.querySQJXByMainid(mainid);
		} catch (Exception e) {
			throw new BusinessException("查询授权失败!",e);
		}
	}
	
	/**
	 * @Description 打印
	 * @CreateTime 2018年1月31日 下午12:35:57
	 * @CreateBy 韩武
	 * @param post
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "print")
	public String export(PostApplication post, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			model.addAttribute("id", post.getId());
			DeptInfo DeptInfo = deptInfoService.selectById(post.getDprtcode());
			String imagePath = "zx.jpg";
			String wcImage = "zx.jpg";
			String type = "135";
			if(null != DeptInfo){
				if("145".equals(DeptInfo.getDeptType())){
					imagePath = "hx.jpg";
					wcImage = "hxwc.jpg";
					type = "145";
				}
			}
			String path = request.getSession().getServletContext().getRealPath("/static/images/report");
			String subreport_dir = request.getSession().getServletContext().getRealPath("/WEB-INF/views/reports/common");
			model.addAttribute("images_path", path.concat(File.separator).concat(imagePath));
			model.addAttribute("wcImage", path.concat(File.separator).concat(wcImage));
			model.addAttribute("subreport_dir", subreport_dir);
			model.addAttribute("type", type);
			return outReport("pdf", "common", "postapplication", model);
		} catch (Exception e) {
			throw new BusinessException("预览或导出失败", e);
		}

	}
}
