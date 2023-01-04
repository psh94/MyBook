package com.psh.mybook.controller;

import com.psh.mybook.annotation.LoginRequired;
import com.psh.mybook.model.member.Member;
import com.psh.mybook.model.member.MemberJoinParam;
import com.psh.mybook.model.member.MemberLoginParam;
import com.psh.mybook.model.member.MemberUpdateParam;
import com.psh.mybook.service.LoginService;
import com.psh.mybook.service.MemberService;
import com.psh.mybook.utill.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.psh.mybook.utill.HttpResponses.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

	private final MemberService memberService;

	private final LoginService loginService;

	//회원가입
	@PostMapping("/join")
	public ResponseEntity<Void> join(@Valid MemberJoinParam param, BindingResult bindingResult) throws Exception{

		if(bindingResult.hasErrors()){
			return RESPONSE_BAD_REQUEST;
		}

		memberService.memberJoin(param);
		return RESPONSE_CREATED;

	}

	@PostMapping("/join/exists")
	public ResponseEntity<Void> memberChkId(String memberId){

		if(!memberService.isExistMemberId(memberId)){
			return RESPONSE_OK;
		} else {
			return RESPONSE_CONFLICT;
		}


	}

	/* 로그인 */
	@PostMapping("/login")
	public ResponseEntity<Void> login(@Valid MemberLoginParam param, BindingResult bindingResult, HttpServletRequest request) throws Exception {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		Member loginMember = loginService.memberLogin(param);

		HttpSession session = request.getSession();


		//--------로그인 실패 시----------
		//바인딩 에러
		if (bindingResult.hasErrors()) {
			return RESPONSE_BAD_REQUEST;
		}


		//--------암호화 된 비밀번호 해석--------
		if (passwordEncoder.matches(param.getPassword(), loginMember.getPassword())) {

			//loginMember를 찾을 수 없을 때
			if (loginMember == null) {
				bindingResult.reject("loginFail", "로그인에 실패했습니다. 아이디 또는 비밀번호를 확인해 주세요.");
				return RESPONSE_CONFLICT;
			}

			//--------- 로그인 성공 시,--------------
			session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
			return RESPONSE_OK;
		}
		return RESPONSE_CONFLICT;


	}

	// session을 제거해서 로그아웃
	@GetMapping("/logout")
	@LoginRequired
	public ResponseEntity<Void> logout(HttpServletRequest request){

		HttpSession session = request.getSession();

		session.invalidate();

		return RESPONSE_OK;

	}

	@GetMapping("/my-account")
	@LoginRequired
	public ResponseEntity<Void> getMember(String memberId){
		memberService.getMemberInfo(memberId);
		return RESPONSE_OK;
	}


	@PutMapping("/my-account")
	@LoginRequired
	public ResponseEntity<Void> memberUpdate(@Valid MemberUpdateParam param, BindingResult bindingResult){

		if(bindingResult.hasErrors()){
			return RESPONSE_BAD_REQUEST;
		}

		memberService.memberUpdate(param);
		return RESPONSE_OK;

	}

	@DeleteMapping("/my-account")
	@LoginRequired
	public ResponseEntity<Void> memberDelete(Member member){

		if(member !=null) {
			memberService.memberDelete(member);
			return RESPONSE_OK;
		}

		return RESPONSE_BAD_REQUEST;
	}




}
