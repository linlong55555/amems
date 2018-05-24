// Copyright (C) 2012 WHTY
package com.eray.pbs.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.selector.ContextSelector;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.classic.util.ContextSelectorStaticBinder;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * Convenience class that features simple methods for custom Logback
 * configuration.
 *
 * <p>
 * Only needed for non-default Logback initialization with a custom config
 * location. By default, Logback will simply read its configuration from a
 * "logback.xml" or "logback_test.xml" file in the root of the class path.
 *
 * <p>
 * For web environments, the analogous LogbackWebConfigurer class can be found
 * in the web package, reading in its configuration from context-params in
 * web.xml. In a J2EE web application, Logback is usually set up via
 * LogbackConfigListener or LogbackConfigServlet, delegating to
 * LogbackWebConfigurer underneath.
 *
 * @since 27-set-2007 11.42.07
 * @see LogbackWebConfigurer
 * @see LogbackConfigListener
 * @see LogbackConfigServlet
 */
public class LogbackConfigurer {
    /**
     * Construtor default
     */
    protected LogbackConfigurer() {
    }

    /**
     * Initialize logback from the given file.
     *
     * @param location
     *            the location of the config file: either a "classpath:"
     *            location (e.g. "classpath:logback.xml"), an absolute file URL
     *            (e.g. "file:C:/logback.xml), or a plain absolute path in the
     *            file system (e.g. "C:/logback.xml")
     * @throws java.io.FileNotFoundException
     *             if the location specifies an invalid file path
     * @throws JoranException
     *             when the cofigurarion fails
     */
    public static void initLogging(String location)
        throws FileNotFoundException, JoranException {
        String resolvedLocation =
                SystemPropertyUtils.resolvePlaceholders(location);
        URL url = ResourceUtils.getURL(resolvedLocation);
        ContextSelector selector =
                ContextSelectorStaticBinder.getSingleton().getContextSelector();
        LoggerContext loggerContext = selector.getLoggerContext();
        loggerContext.reset();
        ContextInitializer ci = new ContextInitializer(loggerContext);
        ci.configureByResource(url);
    }

    /**
     * Shut down logback.
     * <p>
     * This isn't strictly necessary, but recommended for shutting down logback
     * in a scenario where the host VM stays alive (for example, when shutting
     * down an application in a J2EE environment).
     */
    public static void shutdownLogging() {
        ContextSelector selector =
                ContextSelectorStaticBinder.getSingleton().getContextSelector();
        LoggerContext loggerContext = selector.getLoggerContext();
        String loggerContextName = loggerContext.getName();
        LoggerContext context = selector.detachLoggerContext(loggerContextName);
        context.stop();
    }

    /**
     * Set the specified system property to the current working directory.
     * <p>
     * This can be used e.g. for test environments, for applications that
     * leverage LogbackWebConfigurer's "webAppRootKey" support in a web
     * environment.
     *
     * @param key
     *            system property key to use, as expected in Logback
     *            configuration (for example: "demo.root", used as
     *            "${demo.root}/WEB-INF/demo.log")
     * @see LogbackWebConfigurer
     */
    public static void setWorkingDirSystemProperty(String key) {
        System.setProperty(key, new File("").getAbsolutePath());
    }
}
