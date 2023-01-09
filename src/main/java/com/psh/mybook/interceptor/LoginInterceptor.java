package com.psh.mybook.interceptor;

import com.psh.mybook.annotation.LoginRequired;
import com.psh.mybook.utill.SessionConst;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

public class LoginInterceptor implements HandlerInterceptor {


	//로그인이 제대로 되지 않았을 경우 다시 /login으로 돌아가는 interceptor
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		// 인터셉터를 @LoginRequired로 적용
		if(!(handler instanceof HandlerMethod)){
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		LoginRequired loginRequired = handlerMethod.getMethodAnnotation(LoginRequired.class);

		if(Objects.isNull(loginRequired)){
			return true;
		}

		// 인터셉터 적용
		// redirectURL : 특정한 작업 이후 원하는 페이지로 이동하거나, 해당 작업 후 후속 작업이 필요할때 사용
		System.out.println("LoginInterceptor preHandle 작동");

		String requestURI = request.getRequestURI();

		HttpSession session = request.getSession();

		if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER)==null){
			response.sendRedirect("/login?redirectURL=" + requestURI);
			return false;
		}

		return true;

	}




}
