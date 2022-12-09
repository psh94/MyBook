package com.psh.mybook.interceptor;

import com.psh.mybook.model.member.Member;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminInterceptor implements HandlerInterceptor {

	// 사용자가 admin 계정인지 확인하는 interceptor
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession();

		Member loginedMember = (Member)session.getAttribute("member");

		if(loginedMember == null || loginedMember.getAdminCk() == 0) {	// 관리자 계정 아닌 경우

			response.sendRedirect("/");	// home 페이지로 리다이렉트

			return false;

		}

		return true;	// 관리자 계정 로그인 경우

	}

}
