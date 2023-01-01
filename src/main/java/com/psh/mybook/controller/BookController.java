package com.psh.mybook.controller;

import com.psh.mybook.model.Criteria;
import com.psh.mybook.model.Page;
import com.psh.mybook.model.book.AttachImage;
import com.psh.mybook.model.book.Book;
import com.psh.mybook.model.book.BookEnrollParam;
import com.psh.mybook.model.book.BookUpdateParam;
import com.psh.mybook.service.BookService;
import com.psh.mybook.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.psh.mybook.utill.HttpResponses.*;


@RestController
@Slf4j
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final ImageService imageService;


    // 책 등록
    @PostMapping
    public ResponseEntity<Void> enrollBook(@Valid BookEnrollParam bookEnrollParam, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return RESPONSE_BAD_REQUEST;

        }

        boolean existedBook = bookService.isExistBookId(bookEnrollParam.getBookId());

        if(existedBook) {
            return RESPONSE_CONFLICT;
        } else {
            bookService.enrollBook(bookEnrollParam);
            return RESPONSE_OK;
        }
    }

    // 책 조회
    @GetMapping("/{bookId}")
    public Book bookDetail(@PathVariable int bookId, Model model){
        model.addAttribute("bookInfo", bookService.getBookInfo(bookId));
        return bookService.getBookInfo(bookId);
    }



    // 책 업데이트
    @PutMapping("/{bookId}")
    public ResponseEntity<Void> bookUpdate(@PathVariable String bookId, @Valid BookUpdateParam bookUpdateParam){
        boolean existedBook = bookService.isExistBookId(bookUpdateParam.getBookId());

        if(!existedBook) {

            return RESPONSE_CONFLICT;

        } else{

            bookService.bookUpdate(bookUpdateParam);
            return RESPONSE_OK;

        }

    }


    // image 테이블은 book 테이블의 bookId를 외래키로 받기 때문에 book 테이블의 데이터가 사라지면 에러가 발생한다.
    // 그렇기 때문에 bookId를 갖고 있는 book의 데이터를 지우기에 앞서 image 데이터를 먼저 지워야 한다.
    // 책 삭제
    @PostMapping("/{bookId}")
    public ResponseEntity<Void> bookDelete(@PathVariable String bookId, @Valid Book book){

        //이미지 정보를 먼저 지워준다.
        List<AttachImage> fileList = imageService.getAttachInfo(book.getBookId());

        if(fileList != null) {

            List<Path> pathList = new ArrayList();

            fileList.forEach(vo ->{

                // 원본 이미지
                Path path = Paths.get("C:\\upload", vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName());
                pathList.add(path);

                // 섬네일 이미지
                path = Paths.get("C:\\upload", vo.getUploadPath(), "s_" + vo.getUuid()+"_" + vo.getFileName());
                pathList.add(path);

            });

            pathList.forEach(path ->{
                path.toFile().delete();
            });
        } else {

        }

        // 상품을 제거한다.
        if(book.getBookId() != 0){
            bookService.bookDelete(book);
            return RESPONSE_OK;
        }


        return RESPONSE_BAD_REQUEST;
    }


    // 책 검색
    @GetMapping("/search")
    public ResponseEntity<Void> searchBook(Criteria cri, Model model){

        List<Book> list = bookService.getBookList(cri);
        if(!list.isEmpty()){

            model.addAttribute("list",list);

        }else {

            model.addAttribute("listcheck", "empty");
            return RESPONSE_CONFLICT;
        }

        model.addAttribute("pageMaker", new Page(cri, bookService.getBookTotal(cri)));

        return RESPONSE_OK;
    }



}
