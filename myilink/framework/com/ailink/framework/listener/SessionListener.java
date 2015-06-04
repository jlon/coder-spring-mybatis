package com.ailink.framework.listener;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 在线人数统计
 * 
 * @author jlon
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener {
	
	private  static Set<String> onlines = new HashSet<String>();
	 
    public void sessionCreated(HttpSessionEvent event) {
    	   
           HttpSession session = event.getSession();
           onlines.add(session.getId()) ;
           
           ServletContext application = session.getServletContext();
           
           // 在application范围由一个HashSet集保存所有的session
          // HashSet sessions = (HashSet) application.getAttribute("sessions");
           
          // if (sessions == null) {
                //  sessions = new HashSet();
                  application.setAttribute("onlines", onlines.size());
          // }
           
           // 新创建的session均添加到HashSet集中
          // sessions.add(session);
           // 可以在别处从application范围中取出sessions集合
           // 然后使用onlines.size()获取当前活动的session数，即为“在线人数”
    }

    public void sessionDestroyed(HttpSessionEvent event) {
           HttpSession session = event.getSession();
           onlines.remove(session.getId());
           
           //ServletContext application = session.getServletContext();
          // HashSet sessions = (HashSet) application.getAttribute("onlines");
           // 销毁的session均从HashSet集中移除
          // sessions.remove(session);
    }
 }