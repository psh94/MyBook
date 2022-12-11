package com.psh.mybook.model.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class OrderCancel {

    @NotNull
    private String memberId;

    private String orderId;

    private String keyword;

    private int amount;

    private int pageNum;
}
