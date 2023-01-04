package com.psh.mybook.service;

import com.psh.mybook.mapper.MemberMapper;
import com.psh.mybook.model.member.Member;
import com.psh.mybook.model.member.MemberJoinParam;
import com.psh.mybook.model.member.MemberLoginParam;
import com.psh.mybook.model.member.MemberUpdateParam;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class MemberServiceTests{

    @Mock
    MemberMapper memberMapper;

    @InjectMocks
    MemberServiceImpl memberService;

    @InjectMocks
    LoginServiceImpl loginService;

    BCryptPasswordEncoder passwordEncoder;

    Member member;

    MemberJoinParam param;


    @BeforeEach
    void setUp(){
        member = new Member();
        member.setMemberId("test");
        member.setPassword("test");
        member.setName("test");
        member.setEmail("test@test.com");
        member.setAddress("test");
    }


    // 회원가입 성공
    @Test
    @DisplayName("회원가입")
    public void joinMemberTest(){

        MemberJoinParam param = new MemberJoinParam();
        param.setMemberId("test2");
        param.setPassword("test2");
        param.setName("test2");
        param.setEmail("test2@test.com");
        param.setAddress("test2");

        memberService.memberJoin(param);

        verify(memberMapper).memberJoin(any(MemberJoinParam.class));

    }

    @Test
    @DisplayName("아이디 중복 체크")
    public void memberIdCheckTestWhenSuccess(){

        when(memberMapper.isExistMemberId("test2")).thenReturn(false);

        assertDoesNotThrow(()->{
            memberService.isExistMemberId("test2");
        });

        verify(memberMapper).isExistMemberId("test2");

    }


    @Test
    @DisplayName("회원 업데이트")
    public void UpdateMemberTest(){
        MemberUpdateParam memberUpdateParam = new MemberUpdateParam();
        //memberId가 test인 경우
        memberUpdateParam.setMemberId(member.getMemberId());
        memberUpdateParam.setPassword(member.getPassword());
        memberUpdateParam.setEmail(member.getEmail());
        memberUpdateParam.setAddress("seoul");

        memberService.memberUpdate(memberUpdateParam);
        verify(memberMapper).memberUpdate(memberUpdateParam);

        Assertions.assertThat("seoul").isEqualTo(memberUpdateParam.getAddress());
    }

    @Test
    @DisplayName("로그인")
    public void loginTest(){

        MemberLoginParam loginParam = new MemberLoginParam();
        loginParam.setMemberId("test");
        loginParam.setPassword("test");

        assertThat(loginParam.getMemberId()).isEqualTo(member.getMemberId());
        assertThat(loginParam.getPassword()).isEqualTo(member.getPassword());

        loginService.memberLogin(loginParam);

        verify(memberMapper).memberLogin(loginParam);

    }


    @Test
    @DisplayName("멤버 등록 삭제")
    public void DeleteMemberTest(){

        memberService.memberDelete(member);

        verify(memberMapper).memberDelete(member);





    }



}