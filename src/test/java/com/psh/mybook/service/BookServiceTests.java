package com.psh.mybook.service;

import com.psh.mybook.mapper.BookMapper;
import com.psh.mybook.mapper.ImageMapper;
import com.psh.mybook.model.book.AttachImage;
import com.psh.mybook.model.book.Book;
import com.psh.mybook.model.book.BookEnrollParam;
import com.psh.mybook.model.book.BookUpdateParam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
public class BookServiceTests {

    @Mock
    BookMapper bookMapper;

    @Mock
    ImageMapper imageMapper;

    @InjectMocks
    BookServiceImpl bookService;

    @InjectMocks
    ImageServiceImpl imageService;

    BookEnrollParam book;

    List<AttachImage> imageList;


    @BeforeEach
    void setUp(){
        book = new BookEnrollParam();
        book.setBookId(3);
        book.setBookName("연금술사");
        book.setAuthor("코엘료 파울로");
        book.setIsbn("9788982814471");
        book.setPrice(10800);
        book.setQuantity(500);

        List<AttachImage> imageList = new ArrayList<AttachImage>();

        AttachImage image1 = new AttachImage();
        AttachImage image2 = new AttachImage();

        image1.setFileName("test Image 1");
        image1.setUploadPath("test image 1");
        image1.setUuid("test1111");

        image2.setFileName("test Image 2");
        image2.setUploadPath("test image 2");
        image2.setUuid("test2222");

        imageList.add(image1);
        imageList.add(image2);

        book.setImageList(imageList);


    }

    @Test
    @DisplayName("책 등록 성공")
    public void bookEnrollTest(){

        BookEnrollParam book2 = new BookEnrollParam();
        book2.setBookName("연금술사");
        book2.setAuthor("코엘료 파울로");
        book2.setIsbn("9788982814473");
        book2.setPrice(10800);
        book2.setQuantity(500);

        assertThat(book2.getIsbn()).isNotEqualTo(book.getIsbn());

        bookService.bookEnroll(book2);

        verify(bookMapper).bookEnroll(book2);
    }


    @Test
    @DisplayName("책 정보 업데이트")
    public void bookUpdateTest(){
        BookUpdateParam bookUpdateParam = new BookUpdateParam();
        bookUpdateParam.setBookId(7);
        bookUpdateParam.setIsbn("9788982214471");
        bookUpdateParam.setBookName("연금");
        bookUpdateParam.setAuthor("파울로");
        bookUpdateParam.setPrice(10800);
        bookUpdateParam.setQuantity(500);
        bookUpdateParam.setDiscount(10);

        List<AttachImage> imageList = new ArrayList<AttachImage>();

        bookUpdateParam.setImageList(imageList);


        bookService.bookUpdate(bookUpdateParam);

        assertThat(bookUpdateParam.getBookName()).isNotEqualTo(book.getBookName());

        verify(bookMapper).bookUpdate(bookUpdateParam);

    }

    @Test
    @DisplayName("책 등록 삭제")
    public void bookDelete(){
        Book book = new Book();
        book.setBookId(1);
        book.setIsbn("9788982814471");

        bookService.bookDelete(book);

        verify(bookMapper).bookDelete(book);
    }

    /* 이미지 등록 */
    @Test
    @DisplayName("이미지 등록")
    public void imageEnrollTest() {

        AttachImage attachImage = new AttachImage();

        attachImage.setBookId(2);
        attachImage.setFileName("test");
        attachImage.setUploadPath("test");
        attachImage.setUuid("test2");

        imageService.imageEnroll(attachImage);

        verify(imageMapper).imageEnroll(attachImage);

    }

    @Test
    @DisplayName("책 등록 (이미지 추가)")
    public void bookEnrollTestWithImage() {

        // bookEnroll() 메서드 호출
        bookService.bookEnroll(book);

        verify(bookMapper).bookEnroll(book);

    }

    @Test
    @DisplayName("이미지 전체 제거")
    public void deleteImageAllTest() {

        int bookId = 3;

        imageService.deleteImageAll(bookId);

        verify(imageMapper).deleteImageAll(bookId);

    }

    /* 지정 상품 이미지 정보 얻기 */
    @Test
    @DisplayName("이미지 정보 get 테스트")
    public void getAttachInfoTest() {

        int bookId = 3;

        List<AttachImage> list = imageService.getAttachInfo(bookId);

        System.out.println("list : " + list);

        verify(imageMapper).getAttachInfo(bookId);

    }



}
