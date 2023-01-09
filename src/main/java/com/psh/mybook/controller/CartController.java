package com.psh.mybook.controller;

import com.psh.mybook.annotation.LoginRequired;
import com.psh.mybook.model.cart.Cart;
import com.psh.mybook.model.member.Member;
import com.psh.mybook.service.CartService;
import com.psh.mybook.utill.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.psh.mybook.utill.HttpResponses.RESPONSE_OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    // ---------------- 장바구니 생성 --------------------------------
    @PostMapping
    @LoginRequired
    public int addCart(Cart cart, HttpServletRequest request) {

        // 로그인 체크
        HttpSession session = request.getSession();
        Member member = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);

        /*
        0 : 등록 실패
        1 : 등록 성공
        2 : 이미 데이터 존재
        3 : 비 로그인 상태
        
        0,1,2는 서비스에서 처리, 3은 컨트롤러에서 처리
         */
        
        if(member == null) {
            return 3;
        }

        // 카트 등록 결과 반환
        return cartService.addCart(cart);
    }

    //--------------- 장바구니 조회 ---------------------------------
    @GetMapping("/{cartId}")
    @LoginRequired
    public ResponseEntity<Void> cartPage(@PathVariable String cartId, @Login Member member, Model model) {
        
        List<Cart> cart = cartService.getCart(member.getMemberId());
        model.addAttribute("cart", cart);
        return RESPONSE_OK;
    }

    //--------------- 장바구니 책 수량 수정 ---------------------------------
    @PutMapping("/{cartId}")
    @LoginRequired
    public ResponseEntity<Void> updateCart(@PathVariable String cartId, Cart cart) {

        cartService.modifyCount(cart);

        return RESPONSE_OK;
    }
    
    //--------------- 장바구니 삭제 ---------------------------------
    @DeleteMapping("/{cartId}")
    @LoginRequired
    public ResponseEntity<Void> deleteCart(@PathVariable String cartId, Cart cart) {

        cartService.deleteCart(cart.getCartId());

        return RESPONSE_OK;

    }

}
