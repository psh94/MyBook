package com.psh.mybook.service;

import com.psh.mybook.model.Criteria;
import com.psh.mybook.model.reply.ReplyEnrollParam;
import com.psh.mybook.model.reply.ReplyPage;
import com.psh.mybook.model.reply.ReplyUpdateParam;

public interface ReplyService {

    /* 댓글 등록 */
    public int enrollReply(ReplyEnrollParam param);

    /* 댓글 페이징 */
    public ReplyPage replyList(Criteria cri);

    /* 댓글 수정 */
    public int updateReply(ReplyUpdateParam param);


    /* 댓글 삭제 */
    public int deleteReply(int replyId);
}
