package com.psh.mybook.service;

import com.psh.mybook.mapper.ReplyMapper;
import com.psh.mybook.model.reply.Reply;
import com.psh.mybook.model.reply.ReplyEnrollParam;
import com.psh.mybook.model.reply.ReplyUpdateParam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
public class ReplyServiceTests {

    @Mock
    ReplyMapper replyMapper;

    @InjectMocks
    ReplyServiceImpl replyService;

    Reply reply;

    @BeforeEach
    void setUp(){
        reply = new Reply();
        String memberId = "test3";
        int bookId = 7;
        double rating = 4;
        String content = "댓글 테스트";

    }

    @Test
    @DisplayName("댓글 등록")
    public void replyEnrollTest() {

        ReplyEnrollParam reply = new ReplyEnrollParam();
        reply.setMemberId(reply.getMemberId());
        reply.setBookId(reply.getBookId());
        reply.setRating(reply.getRating());
        reply.setContent(reply.getContent());

        replyService.enrollReply(reply);

        verify(replyMapper).enrollReply(reply);


    }

    @Test
    @DisplayName("댓글 업데이트")
    public void replyUpdateTest(){

        ReplyUpdateParam reply = new ReplyUpdateParam();
        reply.setReplyId(1);
        reply.setRating(4.0);
        reply.setContent("abc");

        replyService.updateReply(reply);

        verify(replyMapper).updateReply(reply);
    }

    @Test
    @DisplayName("댓글 삭제")
    void replyDeleteTest(){

        replyService.deleteReply(reply.getReplyId());

        verify(replyMapper).deleteReply(reply.getReplyId());
    }
}
