package com.psh.mybook.model.book;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class Book {

    private int bookId;

    private String bookName;

    private String author;

    private String isbn;

    private int price;

    private int quantity;

    private double discount;

    private Date regDate;

    private Date updateDate;

    private List<AttachImage> imageList;
}
