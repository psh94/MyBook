package com.psh.mybook.interceptor;

import com.psh.mybook.model.member.Member;
import com.psh.mybook.utill.SessionConst;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CartInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        Member member = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);

        if(member == null){
            response.sendRedirect("/main");
            return false;
        } else {
            return true;
        }
    }
}
