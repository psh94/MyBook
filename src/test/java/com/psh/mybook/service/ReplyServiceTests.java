package com.psh.mybook.service;

import com.psh.mybook.mapper.ReplyMapper;
import com.psh.mybook.model.reply.ReplyEnrollParam;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
public class ReplyServiceTests {

    @Mock
    ReplyMapper replyMapper;

    @InjectMocks
    ReplyServiceImpl replyService;

    @Test
    public void replyEnrollTest() {

        String memberId = "test3";
        int bookId = 3;
        double rating = 4;
        String content = "댓글 테스트";

        ReplyEnrollParam reply = new ReplyEnrollParam();
        reply.setMemberId(memberId);
        reply.setBookId(bookId);
        reply.setRating(rating);
        reply.setContent(content);

        replyMapper.enrollReply(reply);


    }
}
