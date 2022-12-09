package com.psh.mybook.controller;

import com.psh.mybook.model.cart.Cart;
import com.psh.mybook.model.member.Member;
import com.psh.mybook.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.psh.mybook.utill.HttpResponses.RESPONSE_OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public int addCartPOST(Cart cart, HttpServletRequest request) {

        // 로그인 체크
        HttpSession session = request.getSession();
        Member member = (Member)session.getAttribute("member");

        // 멤버가 null 이면 5를 반환
        if(member == null) {
            return 5;
        }

        // 카트 등록 결과 반환
        return cartService.addCart(cart);
    }


    @GetMapping("/{memberId}")
    public ResponseEntity<Void> cartPage(@PathVariable("memberId") String memberId, Model model) {

        model.addAttribute("cartInfo", cartService.getCartList(memberId));
        return RESPONSE_OK;
    }

    @PostMapping("/updateCount")
    public ResponseEntity<Void> updateCart(Cart cart) {

        cartService.modifyCount(cart);

        return RESPONSE_OK;

    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteCart(Cart cart) {

        cartService.deleteCart(cart.getCartId());

        return RESPONSE_OK;

    }

}
