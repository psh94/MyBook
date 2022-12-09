package com.psh.mybook.model.cart;

import com.psh.mybook.model.book.AttachImage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Cart {

    private int cartId;

    private String memberId;

    private int bookId;

    private int bookCount;

    // book
    private String bookName;

    private int bookPrice;

    private double bookDiscount;

    // 추가
    private int salePrice;

    private int totalPrice;

    private int point;

    private int totalPoint;

    private List<AttachImage> imageList;

    public void initSaleTotal(){
        this.salePrice = (int) (this.bookPrice * (1-this.bookDiscount));
        this.totalPrice = this.salePrice * this.bookCount;
        this.point = (int)(Math.floor(this.salePrice*0.05));
        this.totalPoint =this.point * this.bookCount;
    }
}
