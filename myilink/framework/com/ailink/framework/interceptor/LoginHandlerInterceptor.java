package com.ailink.framework.interceptor;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ailink.framework.core.cookie.CookieTool;
import com.ailink.framework.utils.ApplicationContextHelper;
import com.ailink.framework.utils.Const;
import com.jlong.test.model.User;

/**
 *过滤所有的URL.进行登陆检查
 * @author jlon
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {

	/*@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		String path = request.getServletPath();

		if (path.matches(Const.NO_INTERCEPTOR_PATH)) {
			return true;
		} else if(path.contains("ailink")
				||path.contains("forget_password")||
				path.contains("reset_password")
				||path.contains("change_password")
				){
			return true;
		}else{

			HttpSession session = request.getSession();
			User user1 = (User) session.getAttribute(Const.SESSION_USER);
            
			//
			if (user1 != null) {

				// 已经登录，判断cookie是否存储了他的账户密码
				Cookie cokLoginName = CookieTool.getCookieByName(request,
						"loginName");
				Cookie cokLoginPwd = CookieTool.getCookieByName(request,
						"loginPwd");

				if (cokLoginName != null && cokLoginPwd != null
						&& cokLoginName.getValue() != null 
						&& !cokLoginName.getValue().equals("")
						&& cokLoginPwd.getValue() != null 
						&& !cokLoginPwd.getValue().equals("")) {

					String loginName = cokLoginName.getValue();
					String loginPwd = cokLoginPwd.getValue();
					
                    User user2 =new User();
                    user2.setLoginname(loginName);
                    user2.setPassword(loginPwd);
                    
					// 检查到客户端保存了用户的密码，进行该账户的验证(这一步的目的是：当用户的密码改变的时候同时更新cookie)
					UserMapper service = (UserMapper) ApplicationContextHelper.getBean("userMapper");
					
					User user = service.getUserInfo(user2);

					if (user == null) {
						CookieTool.addCookie(response, "loginName", null, 0); // 清除Cookie
						CookieTool.addCookie(response, "loginPwd", null, 0); // 清除Cookie
						try {
							// 跳转到登陆
							//response.sendRedirect(request.getContextPath()
									//+ "/login.html");
							request.getRequestDispatcher("/login")
							.forward(request, response);
							return false;

						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						return true;
					}
				}
			}else{
				// 跳转到登陆
				//response.sendRedirect(request.getContextPath()
						//+ "/login.html");
				request.getRequestDispatcher("/login")
				.forward(request, response);
				
				return false;
			}
		}
		return true;
	}*/
}