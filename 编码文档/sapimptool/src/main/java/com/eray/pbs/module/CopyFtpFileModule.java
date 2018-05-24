package com.eray.pbs.module;

import com.eray.pbs.server.DbServer;
import com.eray.pbs.thread.CopyFtpFileThread;
import com.eray.pbs.thread.CopyFtpFileWorkThread;
import com.eray.pbs.util.Module;
import com.eray.util.framework.ThreadPoolManager;

public class CopyFtpFileModule implements Module<Object>
{

	@Override
    public void init(DbServer<Object> server) throws Exception
    {
    }

	@Override
    public void start() throws Exception
    {
		ThreadPoolManager.getThreadPoolExecutor().execute(new CopyFtpFileThread());
		ThreadPoolManager.getThreadPoolExecutor().execute(new CopyFtpFileWorkThread());
    }

	@Override
    public void stop()
    {
    }

	@Override
    public void destroy()
    {
    }

}
