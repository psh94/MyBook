package com.psh.mybook.model.order;

import com.psh.mybook.model.book.AttachImage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderPageItem {

    //OrderPageItem은 상품에 대한 정보를 담는다.

    /* View에서 오는 값*/
    @NotNull
    private int bookId;

    @NotNull
    private int bookCount;

    /* DB에서 오는 값 */
    private String bookName;

    private int bookPrice;

    private double bookDiscount;

    /* initSaleTotal()로 직접 만들어 낼 값 */
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
