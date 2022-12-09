package com.psh.mybook.service;

import com.psh.mybook.model.Criteria;
import com.psh.mybook.model.reply.Reply;
import com.psh.mybook.model.reply.ReplyPage;

public interface ReplyService {

    /* 댓글 등록 */
    public int enrollReply(Reply reply);

    /* 댓글 존재 체크 */
    public String checkReply(Reply reply);

    /* 댓글 페이징 */
    public ReplyPage replyList(Criteria cri);

    /* 댓글 수정 */
    public int updateReply(Reply reply);

    /* 댓글 한개 정보(수정페이지) */
    public Reply getUpdateReply(int replyId);

    /* 댓글 삭제 */
    public int deleteReply(Reply dto);
}
