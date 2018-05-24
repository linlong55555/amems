package com.eray.thjw.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.control.MaterialController;
import com.eray.thjw.dao.CommonRecMapper;
import com.eray.thjw.log.po.Field;
import com.eray.thjw.log.po.Table;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.EnumUtils;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

/**
 * @author liub
 * @description 历史数据公共service,用于业务逻辑处理
 * @develop date 2016.07.25
 */
@Service
public class CommonRecServiceImpl implements CommonRecService {
	
	private static final Logger logger = LoggerFactory.getLogger(MaterialController.class);
	/**
	 * @author liub
	 * @description 历史数据Mapper
	 * @develop date 2016.08.20
	 */
	@Autowired
	private CommonRecMapper commonRecMapper;
	
	@Autowired
	private SqlSessionFactoryBean sqlSessionFactory;

	/**
	 * @author liub
	 * @description 根据条件新增历史记录
	 * @param 主键id(唯一),tableEnum(表名),username(记录操作人),typeEnum(记录-修改类型：1SAVE（新增）、2UPDATE（修改）、3DELETE（删除）)
	 * @return int
	 * @develop date 2016.08.15
	 */
	@Override
	public int write(String id, TableEnum tableEnum, String username, UpdateTypeEnum typeEnum,String recZdid) {
		logger.info("获取客户端ip值 :ip:{}", new Object[]{ThreadVarUtil.getClientIp()});
		String tableName = tableEnum.toString();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<String> columnNameList = commonRecMapper.queryColumnName(tableName);
		
		paramMap.put("id", id);
		paramMap.put("tableName", tableName);
		paramMap.put("tableNameRec", tableName+TableEnum._REC);
		paramMap.put("columnNameList", columnNameList);
		paramMap.put("recCzrid", username);
		paramMap.put("recXglx", typeEnum.getId());
		paramMap.put("clientIp", "0:0:0:0:0:0:0:1".equals(ThreadVarUtil.getClientIp())?"127.0.0.1":ThreadVarUtil.getClientIp());
		paramMap.put("recZdid", recZdid);
		
		return commonRecMapper.insert(paramMap);
	}
	
	@Override
	public int write(String id, TableEnum tableEnum, String username,String recCzls,LogOperationEnum recCzsm, UpdateTypeEnum typeEnum,String recZdid) {
		logger.info("获取客户端ip值 :ip:{}", new Object[]{ThreadVarUtil.getClientIp()});
		String tableName = tableEnum.toString();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<String> columnNameList = commonRecMapper.queryColumnName(tableName);
		
		paramMap.put("id", id);
		paramMap.put("tableName", tableName);
		paramMap.put("tableNameRec", tableName+TableEnum._REC);
		paramMap.put("columnNameList", columnNameList);
		paramMap.put("recCzrid", username);
		paramMap.put("recXglx", typeEnum.getId());
		
		paramMap.put("recCzsm", recCzsm.getId().toString());
		paramMap.put("recCzls", recCzls);
		paramMap.put("recZdid", recZdid);
		paramMap.put("clientIp", "0:0:0:0:0:0:0:1".equals(ThreadVarUtil.getClientIp())?"127.0.0.1":ThreadVarUtil.getClientIp());
		
		
		return commonRecMapper.insert(paramMap);
	}
	
	/**
	 * @author liub
	 * @description 根据条件新增历史记录
	 * @param column(条件字段名),columnValueList(条件字段对应的值),tableEnum(表名),username(记录操作人),typeEnum(记录-修改类型：1SAVE（新增）、2UPDATE（修改）、3DELETE（删除）)
	 * @return int
	 * @develop date 2016.08.15
	 */
	/*@Override
	public int write(String column , List<String> columnValueList, TableEnum tableEnum, String username, UpdateTypeEnum typeEnum,String recZdid) {
		logger.info("获取客户端ip值 :ip:{}", new Object[]{ThreadVarUtil.getClientIp()});
		String tableName = tableEnum.toString();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<String> columnNameList = commonRecMapper.queryColumnName(tableName);
		
		paramMap.put("column", column);
		paramMap.put("columnValueList", columnValueList);
		paramMap.put("tableName", tableName);
		paramMap.put("tableNameRec", tableName+TableEnum._REC);
		paramMap.put("columnNameList", columnNameList);
		paramMap.put("recCzrid", username);
		paramMap.put("recZdid", recZdid);
		paramMap.put("recXglx", typeEnum.getId());
		paramMap.put("clientIp", "0:0:0:0:0:0:0:1".equals(ThreadVarUtil.getClientIp())?"127.0.0.1":ThreadVarUtil.getClientIp());

		return commonRecMapper.insertList(paramMap);
	}*/
 
	@Override
	public int write(String column , List<String> columnValueList, TableEnum tableEnum, String username,String recCzls,LogOperationEnum recCzsm, UpdateTypeEnum typeEnum,String recZdid) {
		 
		// 间隔500插入
		if(columnValueList.size() >= 500){
			List<String> temp = new ArrayList<String>();
			int count = 0;
			for (int i = 0; i < columnValueList.size(); i++) {
				temp.add(columnValueList.get(i));
				if(temp.size() >= 500 || i == columnValueList.size() - 1){
					count += writeAtInterval(column, temp, tableEnum, username, recCzls, recCzsm, typeEnum, recZdid);
					temp.clear();
				}
			}
			return count;
		}else{
			return writeAtInterval(column, columnValueList, tableEnum, username, recCzls, recCzsm, typeEnum, recZdid);
		}
		
	}
	
	/**
	 * 间隔写入Rec
	 * @param column
	 * @param columnValueList
	 * @param tableEnum
	 * @param username
	 * @param recCzls
	 * @param recCzsm
	 * @param typeEnum
	 * @param recZdid
	 * @return
	 */
	private int writeAtInterval(String column , List<String> columnValueList, TableEnum tableEnum, String username,String recCzls,LogOperationEnum recCzsm, UpdateTypeEnum typeEnum,String recZdid){
		String tableName = tableEnum.toString();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<String> columnNameList = commonRecMapper.queryColumnName(tableName);
		
		paramMap.put("column", column);
		paramMap.put("columnValueList", columnValueList);
		paramMap.put("tableName", tableName);
		paramMap.put("tableNameRec", tableName+TableEnum._REC);
		paramMap.put("columnNameList", columnNameList);
		paramMap.put("recCzrid", username);
		paramMap.put("recXglx", typeEnum.getId());
		
		paramMap.put("recZdid", recZdid);
		paramMap.put("recCzsm", recCzsm.getId().toString());
		paramMap.put("recCzls", recCzls);
		paramMap.put("clientIp", "0:0:0:0:0:0:0:1".equals(ThreadVarUtil.getClientIp())?"127.0.0.1":ThreadVarUtil.getClientIp());

		return commonRecMapper.insertList(paramMap);
	}
	
	@Override
	public int writeByWhere(String conditions, TableEnum tableEnum, String username,String recCzls,LogOperationEnum recCzsm, UpdateTypeEnum typeEnum,String recZdid) {
		 
		String tableName = tableEnum.toString();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<String> columnNameList = commonRecMapper.queryColumnName(tableName);
		
		paramMap.put("conditions", conditions);
		paramMap.put("tableName", tableName);
		paramMap.put("tableNameRec", tableName+TableEnum._REC);
		paramMap.put("columnNameList", columnNameList);
		paramMap.put("recCzrid", username);
		paramMap.put("recXglx", typeEnum.getId());
		
		paramMap.put("recCzsm", recCzsm.getId().toString());
		paramMap.put("recCzls", recCzls);
		paramMap.put("recZdid", recZdid);
		paramMap.put("clientIp", "0:0:0:0:0:0:0:1".equals(ThreadVarUtil.getClientIp())?"127.0.0.1":ThreadVarUtil.getClientIp());

		return commonRecMapper.insertListByConditions(paramMap);
	}

	@Override
	public int queryCount4Log(BaseEntity baseEntity) throws Exception {
		String countSqlId = baseEntity.getParamsMap().get("countSqlId").toString();
		SqlSession session = sqlSessionFactory.getObject().openSession();
		int count  = session.selectOne(countSqlId, baseEntity);
		session.close();
		return count;
	}

	@Override
	public Map<String, Object> queryList4Log(BaseEntity baseEntity) throws Exception {
		List<Map<String, Object>> list = null;
		String listSqlId = baseEntity.getParamsMap().get("listSqlId").toString();
		SqlSession session = sqlSessionFactory.getObject().openSession();
		PageHelper.startPage(baseEntity.getPagination());
		list  = session.selectList(listSqlId, baseEntity);
		session.close();
		return PageUtil.pack4PageHelper(list, baseEntity.getPagination());
	}

	@Override
	public List<Map<String, Object>> queryDiff4Log(Table baseEntity) throws Exception {
		List<Map<String, Object>> list = null;
		String diffSqlId = baseEntity.getParamsMap().get("diffSqlId").toString();
		SqlSession session = sqlSessionFactory.getObject().openSession();
		list  = session.selectList(diffSqlId, baseEntity);
		session.close();
		
		//处理枚举
		String name = null;
		if (list!=null && !list.isEmpty()) {
			List<Field> fields = baseEntity.getFields4Enum();
			for (Field field : fields) {
				for (Map<String, Object> map : list) {
					name = EnumUtils.getNameById(field.getEnumClass(), map.get(field.getField()));
					map.put(field.getField(), name);
				}
			}
		}
		
		return list;
	}
	
	
}
