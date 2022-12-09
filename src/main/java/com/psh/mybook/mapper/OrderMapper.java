package com.psh.mybook.mapper;

import com.psh.mybook.model.Criteria;
import com.psh.mybook.model.book.Book;
import com.psh.mybook.model.cart.Cart;
import com.psh.mybook.model.member.Member;
import com.psh.mybook.model.order.Order;
import com.psh.mybook.model.order.OrderItem;
import com.psh.mybook.model.order.OrderPageItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    /* 주문 상품 정보 */
    public OrderPageItem getBooksInfo(int bookId);

    /* 주문 상품 정보(주문 처리) */
    public OrderItem getOrderInfo(int bookId);

    /* 주문 테이블 등록 */
    public int enrollOrder(Order order);

    /* 주문 아이템 테이블 등록 */
    public int enrollOrderItem(OrderItem orderItem);

    /* 주문 금액 차감 */
    public int reduceMoney(Member member);

    /* 주문 재고 차감 */
    public int reduceStock(Book book);

    /* 카트 제거(주문) */
    public int deleteOrderCart(Cart cart);



    /* 주문 상품 리스트 */
    public List<Order> getOrderList(Criteria cri);

    /* 주문 총 갯수 */
    public int getOrderTotal(Criteria cri);



    /* 주문 취소 */
    public int orderCancle(String orderId);

    /* 주문 상품 정보(주문취소) */
    public List<OrderItem> getOrderItemInfo(String orderId);

    /* 주문 정보(주문취소) */
    public Order getOrder(String orderId);
}
