package com.eray.pbs.thread;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eray.pbs.po.StageDescription;
import com.eray.pbs.server.DbServer;
import com.eray.pbs.service.StageDecriptionService;
import com.eray.pbs.util.StageMap;
import com.eray.util.framework.SpringContextHolder;

public class DbServerThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(DbServerThread.class);
    
    private StageDecriptionService stageDecriptionService;
    private StageMap stageMap;
    private StageDescription stageDescription;
    
    @Override
    public void run() {
        logger.info("pbs DBServer is starting ...");
        DbServer<Object> server=SpringContextHolder.getBean("dbServer");
        
        stageMap=StageMap.getStageMap();
        stageDecriptionService = SpringContextHolder.getBean("stageDecriptionService");
        Iterable<StageDescription> stageDiscriptionIt=stageDecriptionService.findAll();
        Iterator<StageDescription> it = stageDiscriptionIt.iterator();
        while(it.hasNext()){
        	stageDescription = it.next();
        	stageMap.putStage(stageDescription.getStage(),stageDescription.getStageDescription());
        }
        server.start();
    }

}
