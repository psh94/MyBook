package com.psh.mybook.model.book;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class BookEnrollParam {

    private int bookId;

    @NotNull
    private String bookName;

    @NotNull
    private String author;

    @NotNull
    private String isbn;

    @NotNull
    private int bookPrice;

    @NotNull
    private int bookCount;

    private double bookDiscount;

    private Date regDate;

    private Date updateDate;

    private List<AttachImage> imageList;
}
