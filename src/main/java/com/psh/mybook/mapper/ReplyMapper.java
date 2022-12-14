package com.psh.mybook.mapper;

import com.psh.mybook.model.Criteria;
import com.psh.mybook.model.reply.Reply;
import com.psh.mybook.model.reply.ReplyEnrollParam;
import com.psh.mybook.model.reply.ReplyUpdateParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {

    /* 댓글 등록 */
    public int enrollReply(ReplyEnrollParam param);

    /* 댓글 페이징 */
    public List<Reply> getReplyList(Criteria cri);

    /* 댓글 총 갯수 */
    public int getReplyTotal(int bookId);

    /* 댓글 수정 */
    public int updateReply(ReplyUpdateParam param);


    /* 댓글 삭제 */
    public int deleteReply(int replyId);
}
