package com.eray.pbs.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eray.pbs.dao.EmpTradeConfigBaseDao;
import com.eray.pbs.dao.EmpTradeModifyLogBaseDao;
import com.eray.pbs.dao.EmployeeDao;
import com.eray.pbs.dao.EmployeePbsDao;
import com.eray.pbs.dao.LogonDao;
import com.eray.pbs.po.EmpTradeConfigBase;
import com.eray.pbs.po.EmpTradeModifyLogBase;
import com.eray.pbs.po.Employee;
import com.eray.pbs.po.EmployeePbs;
import com.eray.pbs.po.Logon;
import com.eray.util.jpa.DynamicSpecifications;
import com.eray.util.jpa.SearchFilter;

@Component
@Transactional(readOnly = true)
public class EmployeeService
{
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private EmployeePbsDao employeePbsDao;
    @Autowired
    private LogonDao logonDao; //用户登录DAO
    @Autowired
    private EmpTradeConfigBaseDao empTradeConfigBaseDao; //员工与部门之间关系
    @Autowired
    private EmpTradeModifyLogBaseDao empTradeModifyLogBaseDao; //记录修改的log记录

    private Map<String, Object> searchParams= new HashMap<String, Object>();
    private Map<String, SearchFilter> filters;
    private Specification<Employee> specEmployee;
    
    private Specification<EmployeePbs> specEmployeePbs;
    private List<EmployeePbs> employeePbsList;
    private EmployeePbs employeePbs;
    
    private PageRequest pageRequest;
    private Page<Employee> page;

    public List<Employee> findEmployee(Map<String, Object> searchParams)
    {
        return employeeDao.findAll(buildEmployeeSpecification(searchParams));
    }

    private Specification<Employee> buildEmployeeSpecification(Map<String, Object> searchParams)
    {
        filters = SearchFilter.parse(searchParams);
        specEmployee = DynamicSpecifications.bySearchFilter(filters.values(), Employee.class);
        return specEmployee;
    }

    @Transactional(readOnly = false)
    public Employee save(Employee employee,String[] para)
    {
        if(employee== null || para == null || para.length<7){
            return employee;
        }
        searchParams.clear();
        searchParams.put("EQ_eid", employee.getEmployeeId());
        filters = SearchFilter.parse(searchParams);
        specEmployeePbs = DynamicSpecifications.bySearchFilter(filters.values(), EmployeePbs.class);
        employeePbsList = employeePbsDao.findAll(specEmployeePbs);
        if (employeePbsList != null && employeePbsList.size()>0)
        {
            employeePbs=employeePbsList.get(0);
        }else{
            employeePbs = new EmployeePbs();
            employeePbs.setEid(employee.getEmployeeId());
        }
        employeePbs.setEname(para[1]);
        employeePbs.setStatus(para[4]);
        employeePbs.setDid(para[5]);
        employeePbs.setGroup(para[6]);
        if(para.length>7){
            employeePbs.setEmail(para[7]);
        }
        employeePbs = employeePbsDao.save(employeePbs);
        if (StringUtils.isNotEmpty(para[4]) && para[4].trim().equals("0")) { //0离职，3在职
        	Logon logon = logonDao.findByUserid(employeePbs.getEid());
        	if (logon != null) { //离职用户需要冻结账号
        		logon.setUserstatus("0"); //为0表示账户冻结
        		logonDao.save(logon);
        	}
        	//修改pbs_emptradeconfig 的离职状态位
        	EmpTradeConfigBase empTrade = empTradeConfigBaseDao.findByEmpId(employeePbs.getEid());
        	if (empTrade != null) {
        		empTrade.setActive("N"); //Y表示在职，N表示离职
        		empTradeConfigBaseDao.save(empTrade);
        		//完善系统log日志
        		EmpTradeModifyLogBase log = this.modifyLog(empTrade);
        		empTradeModifyLogBaseDao.save(log);
        	}
        }
        return employeeDao.save(employee);
    }

    public Employee findLast(String employeeId, String qualificationId)
    {
        if(employeeId==null ||qualificationId==null||employeeId.equals("")||qualificationId.equals("")){
            return null;
        }
        searchParams.clear();
        searchParams.put("EQ_employeeId", employeeId);
        searchParams.put("EQ_qualificationId", qualificationId);
        filters = SearchFilter.parse(searchParams);
        specEmployee = DynamicSpecifications.bySearchFilter(filters.values(),Employee.class);
        pageRequest = new PageRequest(0, 1, new Sort(Direction.DESC, "id"));
        page=employeeDao.findAll(specEmployee, pageRequest);
        if(page.getContent()!=null && page.getContent().size()>0){
            return page.getContent().get(0);
        }
        return null;
    }
    
    /**
     * 保存修改EmpTradeConfigBase的log记录日志信息
     * @param empTrade 部门与trade之间的配置关系
     * @return log记录信息
     */
    private EmpTradeModifyLogBase modifyLog(EmpTradeConfigBase empTrade) {
    	EmpTradeModifyLogBase log = new EmpTradeModifyLogBase();
    	log.setEmpId(empTrade.getEmpId());
    	log.setEmpName(empTrade.getEmpName());
    	log.setDepartment(empTrade.getDepartment());
    	log.setDeptNumber(empTrade.getDeptNumber());
    	log.setWorkCenter(empTrade.getWorkCenter());
    	log.setFactor(empTrade.getFactor());
    	log.setTeam(empTrade.getTeam());
    	log.setRoleId(empTrade.getRoleId());
    	log.setStatus(empTrade.getStatus());
    	log.setActive("Y");
    	log.setModifyType("modifyMain");
    	
    	log.setNewEmpId(empTrade.getEmpId());
    	log.setNewEmpName(empTrade.getEmpName());
    	log.setNewDepartment(empTrade.getDepartment());
    	log.setNewDeptNumber(empTrade.getDeptNumber());
    	log.setNewWorkCenter(empTrade.getWorkCenter());
    	log.setNewFactor(empTrade.getFactor());
    	log.setNewTeam(empTrade.getTeam());
    	log.setNewRoleId(empTrade.getRoleId());
    	log.setNewStatus(empTrade.getStatus());
    	log.setNewActive("N");
    	
    	log.setModifyEid("system");
    	log.setModifyEname("system");
    	
    	log.setModifyDate(new Date());
    	log.setEmpTradeId(empTrade.getId());
    	
    	return log;
    }
}
