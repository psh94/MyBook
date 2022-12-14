package com.psh.mybook.controller;

import com.psh.mybook.annotation.LoginRequired;
import com.psh.mybook.model.Criteria;
import com.psh.mybook.model.reply.Reply;
import com.psh.mybook.model.reply.ReplyEnrollParam;
import com.psh.mybook.model.reply.ReplyPage;
import com.psh.mybook.model.reply.ReplyUpdateParam;
import com.psh.mybook.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.psh.mybook.utill.HttpResponses.RESPONSE_BAD_REQUEST;
import static com.psh.mybook.utill.HttpResponses.RESPONSE_OK;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/replies")
public class ReplyController {

    private final ReplyService replyService;

    // ---------------------- 댓글 등록 -----------------------------
    @PostMapping("/enroll")
    @LoginRequired
    public ResponseEntity<Void> enrollReply(ReplyEnrollParam param) {

        try{

            replyService.enrollReply(param);
            return RESPONSE_OK;

        } catch(Exception e){

            return RESPONSE_BAD_REQUEST;

        }
    }

    // ---------------------- 댓글 조회 -----------------------------
    @GetMapping("/{replyId}")
    public ResponseEntity<ReplyPage> replyList(@PathVariable int replyId, Criteria cri, Model model) {
        ReplyPage replyPage = replyService.replyList(cri);
        model.addAttribute("replyPage", replyPage);
        return RESPONSE_OK;
    }

    // ---------------------- 댓글 수정 -----------------------------

    @PutMapping("/{replyId}")
    @LoginRequired
    public ResponseEntity<Void> replyModifyPOST(@PathVariable int replyId, ReplyUpdateParam param) {
        replyService.updateReply(param);
        return RESPONSE_OK;
    }

    // ---------------------- 댓글 삭제 -----------------------------
    @DeleteMapping("/{replyId}")
    @LoginRequired
    public ResponseEntity<Void> replyDelete(@PathVariable int replyId, Reply reply) {
        replyService.deleteReply(replyId);
        return RESPONSE_OK;
    }
}
