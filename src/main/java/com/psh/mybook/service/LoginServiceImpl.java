package com.psh.mybook.service;


import com.psh.mybook.mapper.MemberMapper;
import com.psh.mybook.model.member.Member;
import com.psh.mybook.model.member.MemberLoginParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final MemberMapper memberMapper;
    private final HttpSession session;

    @Override
    public Member memberLogin(MemberLoginParam param) {
        if (param.getMemberId() == null || param.getPassword() == null) {
            return null;
        }
        return memberMapper.memberLogin(param);
    }

}

