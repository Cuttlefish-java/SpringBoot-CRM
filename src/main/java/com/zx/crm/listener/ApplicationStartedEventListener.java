package com.zx.crm.listener;

import com.zx.crm.settings.service.DicValueService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationStartedEventListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("数据字典开始加载到redis");
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext());
        DicValueService dicValueService = context.getBean(DicValueService.class);
        dicValueService.setDicValueTORedis();
        System.out.println("数据字典加载到redis结束");
        System.out.println("possibility -----------start");
        dicValueService.addPossibility();
        System.out.println("possibility ------------stop");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /*WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext());
        DicValueService dicValueService = context.getBean(DicValueService.class);
        System.out.println("dicValueService"+dicValueService);
        dicValueService.flushDicToRedis();*/
    }
}
