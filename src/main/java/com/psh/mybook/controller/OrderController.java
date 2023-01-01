package com.psh.mybook.controller;

import com.psh.mybook.model.Criteria;
import com.psh.mybook.model.Page;
import com.psh.mybook.model.member.Member;
import com.psh.mybook.model.member.MemberLoginParam;
import com.psh.mybook.model.order.Order;
import com.psh.mybook.model.order.OrderCancel;
import com.psh.mybook.model.order.OrderPage;
import com.psh.mybook.service.LoginService;
import com.psh.mybook.service.MemberService;
import com.psh.mybook.service.OrderService;
import com.psh.mybook.utill.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.psh.mybook.utill.HttpResponses.RESPONSE_CONFLICT;
import static com.psh.mybook.utill.HttpResponses.RESPONSE_OK;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final LoginService loginService;


    // 책, 회원 정보를 보여준다. (주문정보 확인)
    @GetMapping
    public ResponseEntity<Void> orderPage(String memberId, OrderPage orderPage, Model model) {

        model.addAttribute("orderList", orderService.getBooksInfo(orderPage.getOrders()));
        model.addAttribute("memberInfo", memberService.getMemberInfo(memberId));

        return RESPONSE_OK;

    }

    @PostMapping("/enroll")
    public ResponseEntity<Void> orderPage(Order order, HttpServletRequest request){

        orderService.enrollOrder(order);

        MemberLoginParam member = new MemberLoginParam();
        member.setMemberId(order.getMemberId());
        member.setPassword("");
        System.out.println(member);

        HttpSession session = request.getSession();

        try {
            System.out.println(member);
            Member memberLogin = loginService.memberLogin(member);
            session.setAttribute(SessionConst.LOGIN_MEMBER, memberLogin);

        } catch (Exception e) {

            e.printStackTrace();
            return RESPONSE_CONFLICT;
        }

        return RESPONSE_OK;
    }

    /* 주문취소 */
    // 주문을 삭제하는 것이 아닌 주문 상태를 '주문취소'로 만든다.
    @PostMapping("/cancel")
    public ResponseEntity<Void> orderCancle(OrderCancel orderCancel) {

        orderService.orderCancle(orderCancel);

        return RESPONSE_OK;

    }



    /* 주문 확인 페이지 */
    @GetMapping("/list")
    public ResponseEntity<Void> orderList(Criteria cri, Model model) {

        List<Order> list = orderService.getOrderList(cri);

        if(!list.isEmpty()) {
            model.addAttribute("list", list);
            model.addAttribute("pageMaker", new Page(cri, orderService.getOrderTotal(cri)));
        } else {
            model.addAttribute("listCheck", "empty");
        }

        return RESPONSE_OK;
    }


}
