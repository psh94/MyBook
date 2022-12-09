package com.psh.mybook.model.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Member {

	private String memberId;
	
	private String password;
	
	private String name;
	
	private String email;
	
	private String address;

	// 관리자 구분 키 (0은 일반 고객, 1은 관리자)
	private int adminCk;
	
	//등록일자
	private int regDate;
	
	//회원 계정에 있는 금액
	private int money;
	
	//회원 포인트
	private int point;
}
