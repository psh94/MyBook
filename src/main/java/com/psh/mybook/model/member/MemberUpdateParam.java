package com.psh.mybook.model.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class MemberUpdateParam {

	private String memberId;

	@NotBlank(message = "비밀번호를 입력하시오.")
	private String password;

	@Email(message = "이메일를 입력하시오.")
	private String email;

	@NotBlank(message = "주소를 입력하시오.")
	private String address;

}
