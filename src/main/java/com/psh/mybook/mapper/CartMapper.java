package com.psh.mybook.mapper;

import com.psh.mybook.model.cart.Cart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {

    /* 장바구니 추가 */
    // 등록에 실패 했을 경우 Exception을 던지지 않고 0을 반환할 수 있도록 하자.
    public int addCart(Cart cart) throws Exception;

    /* 장바구니 삭제 */
    public int deleteCart(int cartId);

    /* 장바구니 책 수량 수정 */
    public int modifyCount(Cart cart);

    /* 장바구니 책 목록 */
    public List<Cart> getCart(String memberId);

    /* 장바구니 책 확인 */
    public Cart checkCart(Cart cart);


}
