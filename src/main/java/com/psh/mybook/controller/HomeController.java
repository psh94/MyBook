package com.psh.mybook.controller;

import com.psh.mybook.annotation.Login;
import com.psh.mybook.model.member.MemberLoginParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class HomeController {

	@GetMapping("/")
	public String home(){
		return "home";
	}


	@PostMapping("/")
	public String homeLogin(@Login MemberLoginParam loginMember, Model model) {

		// session에 loginMember가 없을 때,
		if(loginMember == null){
			return "home";
		}

		// session 존재 o, loginMember도 있을 때,
		model.addAttribute("member", loginMember);
		return "loginHome";
	}


}
