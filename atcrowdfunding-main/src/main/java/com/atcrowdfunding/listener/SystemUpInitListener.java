package com.atcrowdfunding.listener;

import com.atcrowdfunding.util.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 监听application对象创建的监听器
 *
 * @auther:shisheng
 * @creat 2020-05-11-18:09
 */
public class SystemUpInitListener implements ServletContextListener {

    Logger log = LoggerFactory.getLogger(SystemUpInitListener.class);

    //当application创建时执行的方法
    @Override
    public void contextInitialized(ServletContextEvent se) {
        ServletContext application = se.getServletContext();
        String contextPath = application.getContextPath();
        log.debug("当前应用上下文路径：{}", contextPath);
        application.setAttribute(Const.PATH, contextPath);

    }

    //当application销毁时执行的方法
    @Override
    public void contextDestroyed(ServletContextEvent se) {
        log.debug("当前application对象被销毁");
    }
}
