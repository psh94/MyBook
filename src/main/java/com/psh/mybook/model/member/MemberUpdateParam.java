package com.psh.mybook.model.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class MemberUpdateParam {

	@NotBlank
	private String memberId;

	@NotBlank
	private String password;

	@NotBlank
	private String email;

	@NotBlank
	private String address;

}
