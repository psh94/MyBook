package com.psh.mybook.model.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class Order {

    /*
    Order : OrderPage에서 주문하기를 눌렀을 때 DB로 넘어가는 객체
    OrderItem : Order에 들어가는 주문 아이템 하나

    OrderPage : 상품 상세 페이지나 장바구니에서 주문하기를 눌렀을 때 OrderPage view를 구성하는 객체
    OrderPageItem : OrderPage를 구성하는 아이템 하나
     */


    /* 주문 번호 */
    private String orderId;

    /* 배송 받는이 */
    private String takerName;

    /* 주문 회원 아이디 */
    private String memberId;

    /* 주소 */
    private String address;

    /* 주문 상태 */
    private String orderState;

    /* 사용 포인트 */
    private int usePoint;

    /* 주문 상품 */
    private List<OrderItem> orderItems;

    /*------------- 직접 작성 x -----------------*/

    /* 배송비 */
    private int deliveryCost;

    /* 주문 날짜 */
    private Date orderDate;



    // DB에는 올라가지 않는 데이터들
    /*판매가(모든 상품 비용) */
    private int orderSalePrice;

    /* 적립 포인트 */
    private int orderSavePoint;

    /* 최종 판매 비용 */
    private int orderFinalSalePrice;


    // 주문 작업에 필요한 데이터를 세팅해주는 메서드
    public void getOrderPriceInfo() {
        for(OrderItem orderItem : orderItems) {
            // 각 book 마다 비용과 포인트를 더해준다.
            orderSalePrice += orderItem.getTotalPrice();
            orderSavePoint += orderItem.getTotalSavePoint();
        }

        /* 배송비용 */
        // 총 주문 금액이 3만원을 넘을 시 배송비 0원
        if(orderSalePrice >= 30000) {
            deliveryCost = 0;
        } else {
            deliveryCost = 3000;
        }
        /* 최종 비용 = 상품 비용 + 배송비 - 사용 포인트 */
        orderFinalSalePrice = orderSalePrice + deliveryCost - usePoint;
    }
}

