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
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final LoginService loginService;

    @GetMapping("/{memberId}")
    public ResponseEntity<Void> orderPage(@PathVariable String memberId, OrderPage orderPage, Model model) {

        model.addAttribute("orderList", orderService.getBooksInfo(orderPage.getOrders()));
        model.addAttribute("memberInfo", memberService.getMemberInfo(memberId));

        return RESPONSE_OK;

    }

    @PostMapping("/")
    public ResponseEntity<Void> orderPage(Order order, HttpServletRequest request){

        orderService.order(order);

        MemberLoginParam member = new MemberLoginParam();
        member.setMemberId(order.getMemberId());

        HttpSession session = request.getSession();

        try {
            Member memberLogin = loginService.memberLogin(member);
            memberLogin.setPassword("");
            session.setAttribute("member", memberLogin);

        } catch (Exception e) {

            e.printStackTrace();
            return RESPONSE_CONFLICT;
        }

        return RESPONSE_OK;
    }

    /* 주문 현황 페이지 */
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

    /* 주문삭제 */
    @PostMapping("/cancel")
    public ResponseEntity<Void> orderCancle(OrderCancel orderCancel) {

        orderService.orderCancle(orderCancel);

        return RESPONSE_OK;

    }
}
