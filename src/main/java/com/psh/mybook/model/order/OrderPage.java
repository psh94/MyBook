package com.psh.mybook.model.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderPage {
    // OrderPageItem 객체를 요소로 가지는 List 타입의 변수를 갖는 클래스

    @NotNull
    private List<OrderPageItem> orders;



}
