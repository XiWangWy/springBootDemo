package com.bless;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.util.HashSet;

/**
 * Created by wangxi on 18/7/26.
 */
@WebListener
@Slf4j
public class SessionListener implements HttpSessionListener,HttpSessionAttributeListener{
    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        log.info("--attributeAdded--");
        HttpSession session = se.getSession();
        log.info("key----:"+se.getName());
        log.info("value---:"+se.getValue());

    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
        log.info("--attributeRemoved--");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {
        log.info("--attributeReplaced--");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("--sessionCreated--");
        HttpSession session = se.getSession();
        ServletContext application = session.getServletContext();
        // 在application范围由一个HashSet集保存所有的session
        HashSet sessions = (HashSet) application.getAttribute("sessions");
        if (sessions == null) {
            sessions = new HashSet();
            application.setAttribute("sessions", sessions);
        }
        // 新创建的session均添加到HashSet集中
        sessions.add(session);
        // 可以在别处从application范围中取出sessions集合

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("--sessionDestroyed--");
    }
}
