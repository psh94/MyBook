package com.psh.mybook.service;


import com.psh.mybook.mapper.*;
import com.psh.mybook.model.Criteria;
import com.psh.mybook.model.book.AttachImage;
import com.psh.mybook.model.book.Book;
import com.psh.mybook.model.member.Member;
import com.psh.mybook.model.order.Order;
import com.psh.mybook.model.order.OrderCancel;
import com.psh.mybook.model.order.OrderItem;
import com.psh.mybook.model.order.OrderPageItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderMapper orderMapper;

    private final ImageMapper imageMapper;

    private final MemberMapper memberMapper;

    private final CartMapper cartMapper;

    private final BookMapper bookMapper;

    @Override
    public List<OrderPageItem> getBooksInfo(List<OrderPageItem> orders) {

        //상품 결과 리스트
        List<OrderPageItem> result = new ArrayList<OrderPageItem>();

        // orders로 받아온 상품들을 불러와 initSaleTotal 한 이후, result에 담는다.
        for(OrderPageItem ord : orders) {

            OrderPageItem booksInfo = orderMapper.getBooksInfo(ord.getBookId());

            // orders로 받아온 값들 세팅
            booksInfo.setBookCount(ord.getBookCount());

            booksInfo.initSaleTotal();

            // 이미지 세팅
            List<AttachImage> imageList = imageMapper.getAttachList(booksInfo.getBookId());

            booksInfo.setImageList(imageList);

            // result에 넣기
            result.add(booksInfo);
        }

        return result;
    }

    @Override
    @Transactional
    public void order(Order order) {


        /* 회원 정보 가져오기 */
        Member member = memberMapper.getMemberInfo(order.getMemberId());

        /* 주문 정보 */
        //
        List<OrderItem> orderItemList = new ArrayList<>();

        for(OrderItem orderItem : order.getOrders()) {

            OrderItem orderItemInfo = orderMapper.getOrderInfo(orderItem.getBookId());

            // 수량 셋팅
            orderItem.setBookCount(orderItemInfo.getBookCount());

            // 기본 정보 셋팅
            orderItem.initSaleTotal();

            //List 객체 추가
            orderItemList.add(orderItemInfo);
        }
        /* Order 셋팅 */
        order.setOrders(orderItemList);
        order.getOrderPriceInfo();

        /*DB 주문,주문상품(,배송정보) 넣기*/

        /* orderId만들기 및 OrderDTO객체 orderId에 저장 */
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("_yyyyMMddmm");
        String orderId = member.getMemberId() + format.format(date);
        order.setOrderId(orderId);

        /* db넣기 */
        orderMapper.enrollOrder(order);		//vam_order 등록
        for(OrderItem orderItem : order.getOrders()) {		//vam_orderItem 등록
            orderItem.setOrderId(orderId);
            orderMapper.enrollOrderItem(orderItem);
        }

        /* 비용 포인트 변동 적용 */

        /* 비용 차감 & 변동 돈(money) Member객체 적용 */
        int calMoney = member.getMoney();
        calMoney -= order.getOrderFinalSalePrice();
        member.setMoney(calMoney);

        /* 포인트 차감, 포인트 증가 & 변동 포인트(point) Member객체 적용 */
        int calPoint = member.getPoint();
        calPoint = calPoint - order.getUsePoint() + order.getOrderSavePoint();	// 기존 포인트 - 사용 포인트 + 획득 포인트
        member.setPoint(calPoint);

        /* 변동 돈, 포인트 DB 적용 */
        orderMapper.reduceMoney(member);

        /* 재고 변동 적용 */
        for(OrderItem orderItem : order.getOrders()) {
            /* 변동 재고 값 구하기 */
            Book book = bookMapper.getBooksInfo(orderItem.getBookId());
            book.setQuantity(book.getQuantity() - orderItem.getBookCount());
            /* 변동 값 DB 적용 */
            orderMapper.reduceStock(book);
        }
    }

    /* 주문 상품 리스트 */
    @Override
    public List<Order> getOrderList(Criteria cri) {
        return orderMapper.getOrderList(cri);
    }

    /* 주문 총 갯수 */
    @Override
    public int getOrderTotal(Criteria cri) {
        return orderMapper.getOrderTotal(cri);
    }

    /* 주문취소 */
    @Override
    @Transactional
    public void orderCancle(OrderCancel orderCancel) {
        /* 주문, 주문상품 객체 */
        /*회원*/
        Member member = memberMapper.getMemberInfo(orderCancel.getMemberId());
        /*주문상품*/
        List<OrderItem> orderItemList = orderMapper.getOrderItemInfo(orderCancel.getOrderId());
        for(OrderItem orderItem : orderItemList) {
            orderItem.initSaleTotal();
        }
        /* 주문 */
        Order orderWarpper = orderMapper.getOrder(orderCancel.getOrderId());
        orderWarpper.setOrders(orderItemList);

        orderWarpper.getOrderPriceInfo();

        /* 주문상품 취소 DB */
        orderMapper.orderCancle(orderCancel.getOrderId());

        /* 돈, 포인트, 재고 변환 */
        /* 돈 */
        int calMoney = member.getMoney();
        calMoney += orderWarpper.getOrderFinalSalePrice();
        member.setMoney(calMoney);

        /* 포인트 */
        int calPoint = member.getPoint();
        calPoint = calPoint + orderWarpper.getUsePoint() - orderWarpper.getOrderSavePoint();
        member.setPoint(calPoint);

        /* DB적용 */
        orderMapper.reduceMoney(member);

        /* 재고 */
        for(OrderItem orderItem : orderWarpper.getOrders()) {
            Book book = bookMapper.getBooksInfo(orderItem.getBookId());
            book.setQuantity(book.getQuantity() + orderItem.getBookCount());
            orderMapper.reduceStock(book);
        }

    }
}
