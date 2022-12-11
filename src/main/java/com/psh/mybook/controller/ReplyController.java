package com.psh.mybook.controller;

import com.psh.mybook.model.Criteria;
import com.psh.mybook.model.reply.Reply;
import com.psh.mybook.model.reply.ReplyEnrollParam;
import com.psh.mybook.model.reply.ReplyPage;
import com.psh.mybook.model.reply.ReplyUpdateParam;
import com.psh.mybook.service.BookService;
import com.psh.mybook.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.psh.mybook.utill.HttpResponses.RESPONSE_BAD_REQUEST;
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
    public ResponseEntity<Void> enrollReply(ReplyEnrollParam param) {

        try{

            replyService.enrollReply(param);
            return RESPONSE_OK;

        } catch(Exception e){

            return RESPONSE_BAD_REQUEST;

        }

    }

    /* 댓글 페이징 */
    @GetMapping("/list")
    public ResponseEntity<ReplyPage> replyList(Criteria cri) {
        replyService.replyList(cri);
        return RESPONSE_OK;
    }

    /* 댓글 수정 */
    @PostMapping("/update")
    public ResponseEntity<Void> replyModifyPOST(ReplyUpdateParam param) {
        replyService.updateReply(param);
        return RESPONSE_OK;
    }

    /* 댓글 삭제 */
    @PostMapping("/delete")
    public ResponseEntity<Void> replyDelete(Reply reply) {
        replyService.deleteReply(reply);
        return RESPONSE_OK;
    }
}
