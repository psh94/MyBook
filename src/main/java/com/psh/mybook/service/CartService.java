package com.psh.mybook.service;


import com.psh.mybook.model.cart.Cart;

import java.util.List;

public interface CartService {

    /* 장바구니 추가 */
    public int addCart(Cart cart);

    /* 장바구니 삭제 */
    public int deleteCart(int cartId);

    /* 장바구니 책 수량 수정 */
    public int modifyCount(Cart cart);

    /* 장바구니 책 목록 */
    public List<Cart> getCart(String memberId);

    /* 장바구니 책 확인 */
    public Cart checkCart(Cart cart);

    /* 장바구니 정보 리스트 */
    public List<Cart> getCartList(String memberId);
}
