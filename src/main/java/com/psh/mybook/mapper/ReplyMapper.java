package com.psh.mybook.mapper;

import com.psh.mybook.model.Criteria;
import com.psh.mybook.model.reply.Reply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {

    /* 댓글 등록 */
    public int enrollReply(Reply reply);

    /* 댓글 존재 체크 */
    public Integer checkReply(Reply reply);

    /* 댓글 페이징 */
    public List<Reply> getReplyList(Criteria cri);

    /* 댓글 총 갯수(페이징) */
    public int getReplyTotal(int bookId);

    /* 댓글 수정 */
    public int updateReply(Reply reply);

    /* 댓글 한개 정보(수정페이지) */
    public Reply getUpdateReply(int replyId);

    /* 댓글 삭제 */
    public int deleteReply(int replyId);
}
