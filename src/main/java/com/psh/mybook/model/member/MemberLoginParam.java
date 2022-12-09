package com.psh.mybook.model.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class MemberLoginParam {

	@NotBlank(message = "아이디를 입력하시오.")
	private String memberId;

	@NotBlank(message = "비밀번호를 입력하시오.")
	private String password;

}
