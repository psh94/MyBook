package com.psh.mybook.service;


import com.psh.mybook.model.member.Member;
import com.psh.mybook.model.member.MemberJoinParam;
import com.psh.mybook.model.member.MemberUpdateParam;

public interface MemberService {

    /* 회원가입 */
    public void memberJoin(MemberJoinParam param);

    /* 아이디 중복 검사 */
    public boolean isExistMemberId(String memberId);

    /* 회원 정보 수정 */
    public void memberUpdate(MemberUpdateParam param);

    /* 회원 탈퇴 */
    public void memberDelete(Member member);

    /* 주문자 주소 정보 */
    public Member getMemberInfo(String memberId);
}
