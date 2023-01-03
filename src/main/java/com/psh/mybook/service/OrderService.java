package com.psh.mybook.service;


import com.psh.mybook.model.Criteria;
import com.psh.mybook.model.order.Order;
import com.psh.mybook.model.order.OrderCancel;
import com.psh.mybook.model.order.OrderPageItem;

import java.util.List;

public interface OrderService {

    /* 주문 정보 */
    public List<OrderPageItem> getBooksInfo(List<OrderPageItem> orders);

    /* 주문 처리 */
    public void enrollOrder(Order order);

    /* 주문 조회 */
    public Order getOrder(String orderId);

    /* 주문 상품 리스트 */
    public List<Order> getOrderList(Criteria cri);

    /* 주문 총 갯수 */
    public int getOrderTotal(Criteria cri);

    /* 주문 취소 */
    public void orderCancle(OrderCancel dto);

}
