package com.eray.pbs.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eray.pbs.server.DbServer;
import com.eray.pbs.thread.CheckPbsLogThread;
import com.eray.pbs.util.Module;
import com.eray.util.framework.ThreadPoolManager;

public class CheckPbsLogModule implements Module<Object>{

	private Logger logger = LoggerFactory.getLogger(CheckPbsLogModule.class);
	
	@Override
	public void init(DbServer<Object> server) throws Exception {
		logger.info("{} is starting", this.getClass().getSimpleName());
        ThreadPoolManager.getThreadPoolExecutor().execute(new CheckPbsLogThread());
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
