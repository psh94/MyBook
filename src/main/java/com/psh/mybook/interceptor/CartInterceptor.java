package com.psh.mybook.interceptor;

import com.psh.mybook.model.member.Member;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CartInterceptor implements HandlerInterceptor {

    //클라이언트가 로그인을 하였는지 확인하는 interceptor
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        Member member = (Member) session.getAttribute("member");

        if(member == null){
            response.sendRedirect("/member/login");
            return false;
        }
        return true;
    }
}
