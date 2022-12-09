package com.psh.mybook.service;


import com.psh.mybook.model.member.Member;
import com.psh.mybook.model.member.MemberLoginParam;

public interface LoginService {

    /* 로그인 */
    public Member memberLogin(MemberLoginParam param);
}
