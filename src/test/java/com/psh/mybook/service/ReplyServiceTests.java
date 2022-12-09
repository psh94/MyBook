package com.psh.mybook.service;

import com.psh.mybook.mapper.ReplyMapper;
import com.psh.mybook.model.reply.Reply;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith({MockitoExtension.class})
public class ReplyServiceTests {

    @Mock
    ReplyMapper replyMapper;

    @Autowired
    ReplyServiceImpl replyService;

    @Test
    public void replyEnrollTest() {

        String memberId = "test3";
        int bookId = 3;
        double rating = 4;
        String content = "댓글 테스트";

        Reply reply = new Reply();
        reply.setMemberId(memberId);
        reply.setBookId(bookId);
        reply.setRating(rating);
        reply.setContent(content);

        replyMapper.enrollReply(reply);


    }
}
