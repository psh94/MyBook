package com.psh.mybook.service;

import com.psh.mybook.mapper.BookMapper;
import com.psh.mybook.mapper.ImageMapper;
import com.psh.mybook.model.book.Book;
import com.psh.mybook.model.book.BookEnrollParam;
import com.psh.mybook.model.book.BookInfo;
import com.psh.mybook.model.book.BookUpdateParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final BookMapper bookMapper;

    private final ImageMapper imageMapper;

    // Transactional을 한 이유? 사진 한 장이 아닌 여러장을 받을 수 있기 때문에
    @Transactional
    @Override
    public void bookEnroll(BookEnrollParam bookEnrollParam) {
        bookMapper.bookEnroll(bookEnrollParam);

        /* 이미지 등록 로직 */
        if(bookEnrollParam.getImageList() == null || bookEnrollParam.getImageList().size() <= 0) {
            return; // 여기서 종료
        }

        bookEnrollParam.getImageList().forEach(attach ->{

            attach.setBookId(bookEnrollParam.getBookId());
            imageMapper.imageEnroll(attach);

        });
    }

    @Override
    public boolean isExistBookIsbn(String isbn) {
        return bookMapper.isExistBookIsbn(isbn);
    }

    @Override
    public BookInfo bookGet(int bookId) {

            BookInfo bookInfo = bookMapper.bookGet(bookId);
            bookInfo.setImageList(imageMapper.getAttachInfo(bookId));

            return bookInfo;
    }

    @Transactional
    @Override
    public void bookUpdate(BookUpdateParam param) {

        if(param.getImageList() !=null && param.getImageList().size()>0){

            // 이미지 삭제
            imageMapper.deleteImageAll(param.getBookId());

            // 이미지 추가
            param.getImageList().forEach(attach -> {

                attach.setBookId(param.getBookId());
                imageMapper.imageEnroll(attach);
            });
        }

        bookMapper.bookUpdate(param);
    }

    @Override
    public void bookDelete(Book book) {
        bookMapper.bookDelete(book);
    }

    @Override
    public Book getBookNameById(int bookId) {
        return bookMapper.getBookNameById(bookId);
    }
}
