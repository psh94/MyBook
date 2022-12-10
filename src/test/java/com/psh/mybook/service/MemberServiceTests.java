package com.psh.mybook.service;

import com.psh.mybook.mapper.MemberMapper;
import com.psh.mybook.model.member.Member;
import com.psh.mybook.model.member.MemberJoinParam;
import com.psh.mybook.model.member.MemberLoginParam;
import com.psh.mybook.model.member.MemberUpdateParam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
class MemberServiceTests{

    @Mock
    MemberMapper memberMapper;

    @InjectMocks
    MemberServiceImpl memberService;

    @InjectMocks
    LoginServiceImpl loginService;



    Member member;

    MemberJoinParam param;


    @BeforeEach
    void setUp(){
        member = new Member();
        member.setMemberId("test");
        member.setPassword("abcd");
        member.setName("kim");
        member.setEmail("test@test.com");
        member.setAddress("city");
    }


    // 회원가입 성공
    @Test
    @DisplayName("회원가입 성공")
    public void joinTestWhenSuccess(){

        MemberJoinParam param = new MemberJoinParam();
        param.setMemberId("test1212");
        param.setPassword("abcd");
        param.setName("lee");
        param.setEmail("test2@test.com");
        param.setAddress("city2");

        assertThat(param.getMemberId()).isNotEqualTo(member.getMemberId());

        memberService.memberJoin(param);

        verify(memberMapper).memberJoin(param);

    }

    @Test
    @DisplayName("회원가입 실패 - 아이디 중복")
    public void joinTestWhenFail(){

        MemberJoinParam param = new MemberJoinParam();
        param.setMemberId(member.getMemberId());
        param.setPassword("abcd");
        param.setName("lee");
        param.setEmail("test2@test.com");
        param.setAddress("city2");

        assertThat(param.getMemberId()).isEqualTo(member.getMemberId());

    }


    @Test
    @DisplayName("회원 업데이트 성공")
    public void UpdateMemberTest(){
        MemberUpdateParam memberUpdateParam = new MemberUpdateParam();
        //memberId가 test인 경우
        memberUpdateParam.setMemberId("test");
        memberUpdateParam.setPassword("abcd");
        memberUpdateParam.setEmail("abcd@test.com");
        memberUpdateParam.setAddress("seoul");

        memberService.memberUpdate(memberUpdateParam);
    }

    @Test
    @DisplayName("로그인 성공")
    public void loginTest(){

        MemberLoginParam loginParam = new MemberLoginParam();
        loginParam.setMemberId("test");
        loginParam.setPassword("abcd");

        assertThat(loginParam.getMemberId()).isEqualTo(member.getMemberId());
        assertThat(loginParam.getPassword()).isEqualTo(member.getPassword());

        loginService.memberLogin(loginParam);

        verify(memberMapper).memberLogin(loginParam);

    }


    @Test
    @DisplayName("책 등록 삭제")
    public void DeleteMemberTest(){

        memberService.memberDelete(member);
    }



}