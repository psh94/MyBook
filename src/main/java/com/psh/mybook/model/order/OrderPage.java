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

    @NotNull
    private List<OrderPageItem> orders;



}
