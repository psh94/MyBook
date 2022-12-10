package com.psh.mybook.service;


import com.psh.mybook.mapper.CartMapper;
import com.psh.mybook.mapper.ImageMapper;
import com.psh.mybook.model.cart.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith({MockitoExtension.class})
public class CartServiceTests {

    @Mock
    CartMapper cartMapper;

    @Mock
    ImageMapper imageMapper;

    @InjectMocks
    CartServiceImpl cartService;

    @InjectMocks
    ImageServiceImpl imageService;

    Cart cart;

    @BeforeEach
    void setUp(){
        Cart cart = new Cart();
        cart.setBookId(2);
        cart.setBookCount(3);
        cart.setMemberId("test3");
        cart.setBookDiscount(20);

    }


    @Test
    @DisplayName("장바구니 추가")
    public void addCartTest() {

        int result = cartService.addCart(cart);

        System.out.println("result : " + result);

    }


    /* 카트 삭제 */

    @Test
    @DisplayName("장바구니 제거")
    public void deleteCartTest() {
        int cartId = 5;

        cartService.deleteCart(cartId);

    }

    /* 카트 수량 수정 */

    @Test
    @DisplayName("장바구니 수량 수정")
    public void modifyCartTest() {
        int cartId = 3;
        int count = 5;

        cart = new Cart();
        cart.setCartId(cartId);
        cart.setBookCount(count);

        cartService.modifyCount(cart);

    }


    /* 카트 목록 */

    @Test
    @DisplayName("장바구니 목록 조회")
    public void getCartTest() {
        String memberId = "test3";


        List<Cart> list = cartService.getCart(memberId);
        for(Cart cart : list) {
            System.out.println(cart);
            cart.initSaleTotal();
            System.out.println("init cart : " + cart);
        }
    }


    /* 카트 확인 */

    @Test
    @DisplayName("장바구니 조회")
    public void checkCartTest() {

        String memberId = "test2";
        int bookId = 2;

        cart = new Cart();
        cart.setMemberId(memberId);
        cart.setBookId(bookId);

        Cart resutlCart = cartService.checkCart(cart);
        System.out.println("결과 : " + resutlCart);

    }


}
