package com.psh.mybook.model.order;

import com.psh.mybook.model.book.AttachImage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrderPageItem { // 상품을 담을 클래스

    /* View에서 오는 값*/
    private int bookId;

    private int bookCount;

    /* DB에서 오는 값 */
    private String bookName;

    private int bookPrice;

    private double bookDiscount;

    /* 직접 만들어 낼 값 */
    private int salePrice;

    private int totalPrice;

    private int point;

    private int totalPoint;

    /* 상품 이미지 */
    private List<AttachImage> imageList;

    public void initSaleTotal() {
        this.salePrice = (int) (this.bookPrice * (1-this.bookDiscount));
        this.totalPrice = this.salePrice*this.bookCount;
        this.point = (int)(Math.floor(this.salePrice*0.05));
        this.totalPoint =this.point * this.bookCount;
    }
}
