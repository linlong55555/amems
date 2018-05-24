package com.eray.pbs.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.stereotype.Service;

/**
 * 缓存接口
 * 
 * @author Hao.Z
 * @date 2014-03-18 13:52:32
 */
@Service
public class CacheService
{
    private boolean isInit=false;
    private ConcurrentLinkedQueue<Map<String, Object>> organizationQueue;
    private ConcurrentLinkedQueue<Map<String, Object>> personnelQueue;
    private ConcurrentLinkedQueue<Map<String, Object>> projectQueue;
    private ConcurrentLinkedQueue<Map<String, Object>> workQueue;

    /**
     * 系统初始化时候调用，加载常用数据
     */
    public void initCache()
    {
        organizationQueue = new ConcurrentLinkedQueue<Map<String, Object>>();
        personnelQueue = new ConcurrentLinkedQueue<Map<String, Object>>();
        projectQueue = new ConcurrentLinkedQueue<Map<String, Object>>();
        workQueue = new ConcurrentLinkedQueue<Map<String, Object>>();
        isInit=true;
    }

    public Map<String, Object> pullOrganization()
    {
        if(organizationQueue.size()>0){
            return organizationQueue.poll();
        }else{
            return null;
        }
    }

    public Map<String, Object> pullPersonnel()
    {
        if(personnelQueue.size()>0){
            return personnelQueue.poll();
        }else{
            return null;
        }
    }

    public Map<String, Object> pullProject()
    {
        if(projectQueue.size()>0){
            return projectQueue.poll();
        }else{
            return null;
        }
    }

    public Map<String, Object> pullWork()
    {
        if(workQueue.size()>0){
            return workQueue.poll();
        }else{
            return null;
        }
    }

    public void pushOrganizationQueue(Map<String, Object> map)
    {
        organizationQueue.add(map);
    }

    public void pushPersonnelQueue(Map<String, Object> map)
    {
        personnelQueue.add(map);
    }

    public void pushProjectQueue(Map<String, Object> map)
    {
        projectQueue.add(map);
    }
    
    public void pushWorkQueue(Map<String, Object> map)
    {
        workQueue.add(map);
    }

    public boolean isInit()
    {
        return isInit;
    }
}
