package com.psh.mybook.service;


import com.psh.mybook.model.book.Book;
import com.psh.mybook.model.book.BookEnrollParam;
import com.psh.mybook.model.book.BookInfo;
import com.psh.mybook.model.book.BookUpdateParam;

public interface BookService {

    /* 상품 등록 */
    public void bookEnroll(BookEnrollParam bookEnrollParam);

    /* 책 등록 중복 검사 */
    public boolean isExistBookIsbn(String isbn);

    /* 상품 조회 */
    public BookInfo bookGet(int bookId);

    /* 책 업데이트 */
    public void bookUpdate(BookUpdateParam bookUpdateParam);

    /* 책 삭제 */
    public void bookDelete(Book book);

    /* 상품 id 이름 */
    public Book getBookNameById(int bookId);

}
