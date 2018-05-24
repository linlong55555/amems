package com.eray.pbs.util;

import com.eray.pbs.server.DbServer;

/**
 *
 * @version 1.0
 * @since 2012-6-5 18:11:01
 */
public interface Module<T> {

    /**
     * 初始化模块
     *
     * @param server
     *            模块所服务的系统
     * @throws Exception 
     */
    void init(DbServer<T> server) throws Exception;

    /**
     * 启动基本模块
     */
    void start() throws Exception;

    /**
     * 停止模块
     */
    void stop();

    /**
     * 销毁模块
     */
    void destroy();
}
