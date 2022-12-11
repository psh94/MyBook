package com.psh.mybook.controller;

import com.psh.mybook.model.Criteria;
import com.psh.mybook.model.Page;
import com.psh.mybook.model.book.*;
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
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final ImageService imageService;


    @PostMapping("/enroll")
    public ResponseEntity<Void> bookEnroll(@Valid @ModelAttribute BookEnrollParam bookEnrollParam, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return RESPONSE_BAD_REQUEST;

        }

        boolean existedBook = bookService.isExistBookId(bookEnrollParam.getBookId());

        if(existedBook) {
            return RESPONSE_CONFLICT;
        } else {
            bookService.bookEnroll(bookEnrollParam);
            return RESPONSE_OK;
        }
    }

    @GetMapping("/detail/{bookId}")
    public BookInfo bookDetail(@PathVariable int bookId, Model model){
        model.addAttribute("bookInfo", bookService.bookGet(bookId));
       return bookService.bookGet(bookId);
    }



    @PostMapping("/update")
    public ResponseEntity<Void> bookUpdate(@Valid @ModelAttribute BookUpdateParam param){
        boolean existedBook = bookService.isExistBookId(param.getBookId());

        if(!existedBook) {

            return RESPONSE_CONFLICT;

        } else{

            bookService.bookUpdate(param);
            return RESPONSE_OK;

        }

    }


    // image 테이블은 book 테이블의 bookId를 외래키로 받기 때문에 book 테이블의 데이터가 사라지면 에러가 발생한다.
    // 그렇기 때문에 bookId를 갖고 있는 book의 데이터를 지우기에 앞서 image의 데이터를 먼저 지워야 한다.

    @PostMapping("/delete")
    public ResponseEntity<Void> bookDelete(@Valid Book book){

        //이미지 정보를 먼저 지워준다.
        List<AttachImage> fileList = imageService.getAttachInfo(book.getBookId());
        System.out.println(imageService);
        System.out.println(fileList);

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

        System.out.println(book);


        // 상품을 제거한다.
        if(book.getBookId() != 0){
            bookService.bookDelete(book);
            return RESPONSE_OK;
        }


        return RESPONSE_BAD_REQUEST;
    }


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
