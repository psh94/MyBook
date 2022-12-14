package com.psh.mybook.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
    //페이징의 기준

    /* 현재 페이지 번호 */
    private int pageNum;

    /* 페이지 표시 개수 */
    private int amount;

    /* 페이지 skip */
    private int skip;
    
    /* 검색어 키워드 */
    private String keyword;

    /* 상품 번호(댓글 기능에서 사용) */
    private int bookId;

    /* Criteria 기본 생성자 (기본 세팅) */
    public Criteria(){
        this(1,10);
    }

    /* Criteria 생성자 */
    public Criteria(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
        this.skip = (pageNum -1) * amount;
    }

    /*
     Setter를를 호출한다는 은 값을 변경한다는 의미이므로
     skip의 값을 변경 해주어야 한다.
     */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
        this.skip = (pageNum - 1) * this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        this.skip = (this.pageNum - 1) * amount;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

}
