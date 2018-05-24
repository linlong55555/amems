package com.eray.pbs.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eray.pbs.server.DbServer;
import com.eray.pbs.thread.WorkSpentBackSAPThread;
import com.eray.pbs.util.Module;
import com.eray.util.framework.ThreadPoolManager;

/**
 * 产生spent hrs工时 2015.7.14
 * @author ganqing
 *
 */
public class WorkSpentBackSAPModule implements Module<Object> {

	private static final Logger logger = LoggerFactory.getLogger(WorkSpentBackSAPModule.class);
	@Override
	public void init(DbServer<Object> server) throws Exception {
		logger.info("{} is starting", this.getClass().getSimpleName());
		ThreadPoolManager.getThreadPoolExecutor().execute(new WorkSpentBackSAPThread());
		logger.info("{} is started", this.getClass().getSimpleName());
	}

	@Override
	public void start() throws Exception {
		
	}

	@Override
	public void stop() {
		
	}

	@Override
	public void destroy() {
		
	}

}
