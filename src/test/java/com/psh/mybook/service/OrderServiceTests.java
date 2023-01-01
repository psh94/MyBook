package com.psh.mybook.service;


import com.psh.mybook.mapper.OrderMapper;
import com.psh.mybook.model.book.Book;
import com.psh.mybook.model.cart.Cart;
import com.psh.mybook.model.member.Member;
import com.psh.mybook.model.order.Order;
import com.psh.mybook.model.order.OrderItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith({MockitoExtension.class})
public class OrderServiceTests {

    @Mock
    OrderMapper orderMapper;

    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    @DisplayName("상품 정보")
    public void getOrderInfoTest() {

        OrderItem orderInfo = orderMapper.getOrderInfo(3);

        System.out.println("result : " + orderInfo);
    }

    @Test
    @DisplayName("member_order 테이블 등록")
    void enrollOrderTest() {

        Order ord = new Order();
        List<OrderItem> orders = new ArrayList();

        OrderItem order1 = new OrderItem();

        order1.setBookId(3);
        order1.setBookCount(5);
        order1.setBookPrice(70000);
        order1.setBookDiscount(0.1);
        order1.initSaleTotal();



        ord.setOrderItems(orders);

        ord.setOrderId("2021_test1");
        ord.setTakerName("test");
        ord.setMemberId("test2");
        ord.setAddress("test");
        ord.setOrderState("배송중비");
        ord.getOrderPriceInfo();
        ord.setUsePoint(1000);

        orderMapper.enrollOrder(ord);

    }

    @Test
    @DisplayName("itemOrder 테이블 등록")
    public void enrollOrderItemTest() {

        OrderItem oid = new OrderItem();

        oid.setOrderId("2021_test1");
        oid.setBookId(3);
        oid.setBookCount(1);
        oid.setBookPrice(70000);
        oid.setBookDiscount(0.1);

        oid.initSaleTotal();

        orderMapper.enrollOrderItem(oid);

    }

    @Test
    @DisplayName("돈, 포인트 정보 변경")
    public void deductMoneyTest() {

        Member member = new Member();

        member.setMemberId("test2");
        member.setMoney(500000);
        member.setPoint(10000);

        orderMapper.reduceMoney(member);
    }

    @Test
    @DisplayName("상품 재고 변경")
    public void deductStockTest() {
        Book book = new Book();

        book.setBookId(3);
        book.setBookCount(77);

        orderMapper.reduceStock(book);
    }

    @Test
    @DisplayName("장바구니 제거(주문 후 처리)")
    public void deleteOrderCart() {
        String memberId = "test2";
        int bookId = 3201;

        Cart dto = new Cart();
        dto.setMemberId(memberId);
        dto.setBookId(bookId);

        orderMapper.deleteOrderCart(dto);

    }

}
