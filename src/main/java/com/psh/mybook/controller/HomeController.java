package com.psh.mybook.controller;

import com.psh.mybook.annotation.Login;
import com.psh.mybook.model.member.MemberLoginParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.psh.mybook.utill.HttpResponses.RESPONSE_BAD_REQUEST;
import static com.psh.mybook.utill.HttpResponses.RESPONSE_OK;


@RestController
@Slf4j
@RequestMapping("/home")
public class HomeController {

	@GetMapping
	public ResponseEntity<Void> home(){
		return RESPONSE_OK;
	}


	@PostMapping
	public ResponseEntity<Void> homeLogin(@Login MemberLoginParam loginMember, Model model) {

		// session에 loginMember가 없을 때,
		if(loginMember == null){
			return RESPONSE_BAD_REQUEST;
		}

		// session 존재 o, loginMember도 있을 때,
		model.addAttribute("member", loginMember);
		return RESPONSE_OK;
	}


}
