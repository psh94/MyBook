package com.psh.mybook.service;


import com.psh.mybook.model.Criteria;
import com.psh.mybook.model.book.Book;
import com.psh.mybook.model.book.BookEnrollParam;
import com.psh.mybook.model.book.BookUpdateParam;

import java.util.List;

public interface BookService {

    /* 상품 등록 */
    public void enrollBook(BookEnrollParam bookEnrollParam);

    /* 책 등록 중복 검사 */
    public boolean isExistBookId(int bookId);

    /* 상품 조회 */
    public Book getBookInfo(int bookId);

    /* 책 업데이트 */
    public void bookUpdate(BookUpdateParam bookUpdateParam);

    /* 책 삭제 */
    public void bookDelete(Book book);

    /* 상품 id 이름 */
    public Book getBookNameById(int bookId);

    /* 책 목록 검색 */
    public List<Book> getBookList(Criteria cri);

    /* 책 개수 검색 */
    public int getBookTotal(Criteria cri);

}
