package com.eray.thjw.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.eray.thjw.aerialmaterial.dao.StoreMapper;
import com.eray.thjw.aerialmaterial.po.Store;
import com.eray.thjw.dao.PlaneModelDataMapper;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.SessionUtil;
import com.eray.thjw.util.ThreadVarUtil;

import enu.ThreadVar;

/**
 * @Description 接口嵌入页面拦截器
 * @CreateTime 2017年12月25日 上午9:36:49
 * @CreateBy 韩武
 */
public class EmbeddedInterceptor extends HandlerInterceptorAdapter {
	
	@Resource
	private PlaneModelDataMapper planeModelDataMapper;
	
	@Resource
	private StoreMapper storeMapper;
	
	@Resource
	private DepartmentService departmentService;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {  
         
	}  
	  
	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView view) throws Exception {  
        String contextPath = request.getContextPath(); 
        if (view != null) {  
            request.setAttribute("ctx", contextPath);  
        }  
    }  
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {  
    	
    	User user = SessionUtil.getUserFromSession(request);
    	
    	// 初始化session
		if (null == user) {
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			initAdminMap(resultMap);
			
			SessionUtil.initSession(request, resultMap);
		}
		ThreadVarUtil.setUser(user);
		ThreadVarUtil.set(ThreadVar.CLIENT_IP.name(), user != null ? user.getLastip() : "");
		return super.preHandle(request, response, handler);
    }  
    
    
    private void initAdminMap(Map<String, Object> returnMap){
		
		BaseEntity entity = new BaseEntity();
		entity.getParamsMap().put("usertype", "admin");
		List<Map<String,Object>> aircraftDatas = planeModelDataMapper.selectAircraftData(entity );
		List<Map<String,Object>> aircraftDatas135145 = planeModelDataMapper.selectAircraft135145Data(entity );
		
		List<Department> department = departmentService.queryOrg();//获取组织结构集合
		List<Store> storeList = this.storeMapper.selectAll();
		
		returnMap.put("department", department);
		returnMap.put("islegal", "true");
		returnMap.put("userStoreList", storeList);
		//存储授权机型和飞机注册号
		returnMap.put("userACRegList", aircraftDatas);
		returnMap.put("userACReg135145List", aircraftDatas135145);
	}
    
}
