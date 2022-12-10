package com.psh.mybook.controller;

import com.psh.mybook.model.Criteria;
import com.psh.mybook.model.book.Book;
import com.psh.mybook.model.reply.Reply;
import com.psh.mybook.model.reply.ReplyPage;
import com.psh.mybook.service.BookService;
import com.psh.mybook.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.psh.mybook.utill.HttpResponses.RESPONSE_OK;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;

    private final BookService bookService;

    /* 댓글 등록 */
    @PostMapping("/enroll")
    public ResponseEntity<Void> enrollReplyGET(Reply reply) {
        replyService.enrollReply(reply);

        return RESPONSE_OK;
    }

    /* 리뷰 쓰기 */
    @GetMapping("/enroll/{memberId}")
    public ResponseEntity<Void> enrollReplyPOST(@PathVariable String memberId, int bookId, Model model) {
        Book book = bookService.getBookNameById(bookId);
        model.addAttribute("bookInfo", book);
        model.addAttribute("memberId", memberId);

        return RESPONSE_OK;
    }

    /* 댓글 체크 */
    /* memberId, bookId 파라미터 */
    /* 존재 : 1 / 존재x : 0 */
    @PostMapping("/check")
    public String replyCheck(Reply reply) {
        return replyService.checkReply(reply);
    }

    /* 댓글 페이징 */
    @GetMapping("/list")
    public ReplyPage replyList(Criteria cri) {
        return replyService.replyList(cri);
    }

    /* 리뷰 수정 창 */
    @GetMapping("/update")
    public ResponseEntity<Void> replyModifyGET(Reply reply, Model model) {
        Book book = bookService.getBookNameById(reply.getBookId());
        model.addAttribute("bookInfo", book);
        model.addAttribute("replyInfo", replyService.getUpdateReply(reply.getReplyId()));
        model.addAttribute("memberId", reply.getMemberId());

        return RESPONSE_OK;
    }

    /* 댓글 수정 */
    @PostMapping("/update")
    public ResponseEntity<Void> replyModifyPOST(Reply reply) {
        replyService.updateReply(reply);
        return RESPONSE_OK;
    }

    /* 댓글 삭제 */
    @PostMapping("/delete")
    public ResponseEntity<Void> replyDelete(Reply reply) {
        replyService.deleteReply(reply);
        return RESPONSE_OK;
    }
}
