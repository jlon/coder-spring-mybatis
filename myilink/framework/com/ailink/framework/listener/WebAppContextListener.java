package com.ailink.framework.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ailink.framework.utils.Const;

/**
 * 
 * @author jlon
 *
 */
@WebListener
public class WebAppContextListener implements ServletContextListener {
	
	
	public void contextInitialized(ServletContextEvent event) {
		Const.WEB_APP_CONTEXT = WebApplicationContextUtils
				.getWebApplicationContext(event.getServletContext());
		
		ServletContext sc = event.getServletContext();
		sc.setAttribute("ctx", getContextPath(sc));
	}

	public void contextDestroyed(ServletContextEvent event) {
		ServletContext sc = event.getServletContext();
        sc.removeAttribute("ctx");
	}

	private String getContextPath(ServletContext sc) {
		return sc.getContextPath();
	}
}
